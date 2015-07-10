/**
 * @file EventList.java
 * @author Codrin Morhan
 * @date 21/02/2012
 * @brief Contains the EventList class.
 */

/**
 * @package events
 * @brief Contains the the Event list, the Event model, view and controller, 
 */
package events;

import hirondelle.date4j.DateTime;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.DefaultEventTableModel;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import dateAndTime.SystemDate;
import dateAndTime.SystemTime;
import digitalOrganiser.DigitalOrganiser;
import testingFramework.TestData;

/**
 * @brief Class for working with a list of events.
 * 
 * Basically a wrapper for a GlazedLists' BasicEventList (which is itself another wrapper for a list).<br/>
 * Keeps a list of events and allows the manipulation of the said list.<br/>
 * Can display itself as a JList or as a search-able table.<br/>
 * Can work with or without the File subsystem (the FileWritingMode).
 * @see <a href="http://www.glazedlists.com/">GlazedLists weblink</a> 
 * @author Codrin Morhan
 */
public class EventList{
	
	/**
	 * Gets the search table.
	 * @return The table.
	 */
	protected JTable getSearchTable(){return m_SearchTable;}
	/**
	 * Sets the search table.<br/>
	 * Automatically adds the custom listener to it.
	 * @param t The new table.
	 * @see The EventsTableListener class.
	 */
	protected void setSearchTable(JTable t){
		m_SearchTable = t;
		getSearchTable().addMouseListener(new EventsTableListener());
	}
	
	/**
	 * Gets the search string for the search field.
	 * @return The text.
	 */
	public String getSearchString(){return m_SearchString;}
	/**
	 * Sets the search string designated for the the search field.
	 * @param s The new string.
	 */
	public void setSearchString(String s){
		m_SearchString = s;
	}
	
	/**
	 * Gets the global mode status.
	 * @return The current status of the switch.
	 */
	public boolean getFileWritingMode(){return m_FileWritingMode;}
	/**
	 * Sets the global mode status
	 * @param b true for ON, false for OFF.
	 */
	public void setFileWritingMode(boolean b){m_FileWritingMode = b;}
	
    /**
     * Gets the currently selected event's ID.
     * @return The ID of the event. 
     */
    public String getSelectedEventID(){
    	return m_selectedEventID;
    }
    
    /**
     * Sets the currently selected event's ID.
     * @param id The new ID.
     * @return True on success, false if the ID had an invalid format.
     * @see SystemDate.validateTimeStamp()
     */
    public boolean setSelectedEventID(String id){
    	if (SystemDate.validateTimeStamp(id) || id.isEmpty()){
    		if (getDebugStatus())
    			System.out.println("EL: setSelectedEventID(): "+
    					"Selected: ["+id+"]");
    		m_selectedEventID = id;
    		return true;
    	}
    	else{
    		if (getDebugStatus())
    			System.err.println("EventList.java:\n" + 
    					" setSelectedEventID():\n" + 
    					"  Invalid ID.");
    		return false;
    	}
    		
    }
	
	/**
	 * Get the debug switch.
	 * @return The switch status.
	 */
	public boolean getDebugStatus(){return m_debug;}	
	/**
	 * Sets the debug switch status.
	 * @param f The new switch status.
	 */
	public void setDebugStatus(boolean f){m_debug = f;}
	
	
	/**
	 * Sets the JList of events.
	 * @param l The new list.
	 */
	private void setEventJList(JList l){m_eventJList = l;}
	/**
	 * Returns the JList from within this very same class.
	 * @return List with contacts.
	 */
	private JList getEventJList(){return m_eventJList;} 
	/**
	 * Gets the event list.
	 * @return The array list of events.
	 */
	private BasicEventList<Event> getEventList(){return m_eventList;}
	
	/**
	 * Default constructor; populates the JList with events.
	 */
	public EventList(){
		setSelectedEventID("");
		setSearchTable(new JTable());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventJList(new JList());
			}
		});
		this.populateList();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getEventJList().addListSelectionListener
					(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							int index = getEventJList().getSelectedIndex();
							String choice = getEventJList().getModel()
											.getElementAt(index).toString();
							if (getDebugStatus())
					        	System.out.println("Selected name: "+choice );
					        int which = getEventJList().getSelectedIndex();
					        if (getDebugStatus())
					        	System.out.println("Selected index: "+ which );
						}
		});
			}
		});
	}
	
	/**
	 * Constructs a panel with the list.
	 * @return The constructed panel.
	 */
	public JScrollPane getJScrollPane(){
		JScrollPane p = new JScrollPane();
		//Useful only for the testing; irrelevant for the main program tab
		int WIDTH = 200, HEIGHT = 500;
		p.setPreferredSize( new Dimension(WIDTH,HEIGHT));
		
		p.setViewportView(getEventJList());
		p.updateUI();
		return p;
	}
	/**
	 * Gets the list selected item's index.
	 * @return int The index
	 */
	public int getSelectedItemIndex(){
		return getEventJList().getSelectedIndex();
	}

	
	
	/**
	 * Populates the list with contact names.
	 * @return True on success, false on failure.
	 */
	private boolean populateList (){
		final DefaultListModel dlm = new DefaultListModel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getEventJList().setModel(dlm);
			}
		});
		if (this.getSize()<=0){
			//dlm.addElement("NO CONTACTS AVAILABLE");
			return false;
		}
		
		for(int i=0; i< this.getSize();i++)
			dlm.addElement( this.getEvent(i).getTitle());
		//getContactList().setSelectedIndex(0);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getEventJList()
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
		});
		return true;
	}
	


	/**
	 * Refreshes the list.
	 * @return True on success, false on failure.
	 */
	public boolean update(){
		if (populateList()){
			//System.out.println("Event JList updated.");
			return true;
		}
		//System.out.println("Event JList didn't update.");
		return false;
	}

	/**
	 * Adds an event to the list.
	 * @param e The event to be stored.
	 * @return True on success, false on failure.
	 */
	public boolean addEvent(Event e){
		if (!SystemDate.validateTimeStamp(e.getID())){
			if(getDebugStatus())
				System.err.println("EL: addEvent(Event): "+
						"  Invalid ID format. Event not added.");
			return false;
		}
		if (findEventIndex(e.getID()) != -1){
			if(getDebugStatus())
			System.err.println("EL: addEvent(Event): "+
					"ID already exists. Event not added.");
			return false;
		}
		if(!INNER_ADD_CALL)
			if (getSize() >= SIZE_LIMIT){
				JOptionPane.showMessageDialog(	null,
											"Sorry, the limit is 1000 events.",
											"Cannot add event",
											JOptionPane.ERROR_MESSAGE);
				System.out.println("Size: "+getSize());
				return false;
			} else {
				getEventList().getReadWriteLock().writeLock().lock();
				getEventList().add(e);
				getEventList().getReadWriteLock().writeLock().unlock();
				update();
				if(getDebugStatus())
					System.out.println("EL: addEvent(): Added an event: " +
							"["+e.getTitle()+"].");
				if(getFileWritingMode()){
					if(getDebugStatus())
						System.out.println("EL: addEvent(): " +
								"Writing it to file...");
					DigitalOrganiser.getEventFileManager().write(e);
			        if (getDebugStatus())
			        	System.out.println("EL: addEvent(): " +
			        			"Finished write call.");
				}
				return true;
			}
		else 
			getEventList().add(e);
		if(getDebugStatus())
			System.out.println("EL: addEvent(): Added an event without writing to file: ["+e.getTitle()+"]");
		return true;
		
	}
	/**
	 * Adds an event based on a string array.
	 * @param eArray The array with the event values.
	 * @return True on success, false on failure.
	 */
	public boolean addEvent(String[] eArray){
		if (getSize() >= SIZE_LIMIT){
			if(getDebugStatus())
				System.err.println("EventList.java:\n addEvent(String[]):\n"+
						"  Sorry, the limit is " + SIZE_LIMIT +" events."+
						"Cannot add event.");
			return false;
		}
		Event e = new Event();
		if (!e.setFromStringArray(eArray))
			return false;
		INNER_ADD_CALL = true;
		if(!addEvent(e)){
			INNER_ADD_CALL = false;
			return false;
		}
		INNER_ADD_CALL = false;
		return true;
	}
	
	/**
     * Updates an event in the list based on the index in the list.
     * @param i The index of the event to be updated.
     * @param event The updated event.
     * @return True on success, false on failure.
     */
	public boolean editEvent(int i, Event event){
		if (i >= 0 && i < getSize()){
			if (getDebugStatus())
				System.out.println("EL: editEvent(): " +
						"Editing the event at index: "+i);
			getEventList().getReadWriteLock().writeLock().lock();
			getEventList().add(i, event);
			removeEvent(i+1);
			getEventList().getReadWriteLock().writeLock().unlock();
			if (getDebugStatus())
				System.out.println("EL: editEvent(): Replaced the event.");
			if(getFileWritingMode()){
				if(getDebugStatus())
					System.out.println("EL: editEvent(): " +
							"ReWriting the file...");
				DigitalOrganiser.getEventFileManager().write();
		        if (getDebugStatus())
		        	System.out.println("EL: editEvent(): " +
		        			"Finished rewrite call.");
			}
			this.update();

			return true;
		} else {
			if (getDebugStatus())
				System.out.println("EL: editEvent(): "+
						"Bad index: [" + i + "]");
			return false;
		}
    }
	
	/**
     * Updates an event in the list based on the ID.
     * @param id The ID of the event to be updated.
     * @param e2 The new/updated event.
     * @return True on success, false on failure.
     * @see editEvent(int,Event)
     */
	public boolean editEvent(String id, Event e2){
		int index = findEventIndex(id);
		if (getDebugStatus())
			System.out.println("EL: editContact(ID,Event): " +
					"A call to editEvent() is to follow after this.");
		if (editEvent(index, e2))
			return true;
    	
		return false;
    }
	
	/**
	 * Finds the list index of an event based on the ID.
	 * @param id The ID of the event.
	 * @return The index or -1 if it wasn't found.
	 */
	public int findEventIndex(String id){
		for (int i = 0; i < getSize() ; i++){
			Event e = getEvent(i);
			
			if (getDebugStatus())
				System.out.println("EL: findEvent(): "+
						"Comparing: [" + e.getID() + "] vs [" + id + "]");

			if (e.getID().equals(id)){
				if (getDebugStatus())
					System.out.println("EL: findEvent(): "+
							"Found: [" + e.getID() + "] at index [" + i + "]");
				return i;
			}
		}
		if (getDebugStatus())
			System.out.println("EL: findEventIndex(): "+
								"Could not find ID : [" + id + "]");
		return -1;
	}
	
    /**
     * Gets an event from the list based on the index.
     * @param index The index of the desired event.
     * @return The event or null if unsuccessful.
     */
    public Event getEvent(int index){
        try{
            getEventList().get(index);
        } catch (Exception e){
            System.err.println("EL: getEvent(): Failed to get the event.");
            return null;
        }
        //Debug message checking what event type it got back
        //System.out.println("Returning a "+m_eventList.get(index).getEventType());
        return (Event) getEventList().get(index);
    }
    
    /** 
     * Gets an event based on a given id.
     * @param id The id of the event to be returned.
     * @return The event or null if no matching event was found.
     * @see getEvent(int)
     */
    public Event getEvent(String id){
    	if (getDebugStatus())
			System.out.println("EL: getEvent(String): " +
					"A call to getEvent(int) is to follow this.");
    	return getEvent(findEventIndex(id));    	
    }
    
    /**
     * Gets the size of the list.
     * @return Number of events stored.
     */
    public int getSize(){return getEventList().size();}
    
    /**
     * Removes an event based on position in the list.
     * @param index Index of the event to be removed.
     */
    public boolean removeEvent(int index){
    	if (getDebugStatus())
    		System.out.println("EL: removeEvent(): Passed index: ["+index+"].");
    	if (index < 0 || index > getSize())	return false;
		getEventList().getReadWriteLock().writeLock().lock();
		getEventList().remove(index);
		getEventList().getReadWriteLock().writeLock().unlock();

        this.update();
        if (getDebugStatus())
        	System.out.println("EL: removeEvent(): Removed event at index: " +
        			"["+index+"].");
		if(getFileWritingMode()){
			if(getDebugStatus())
				System.out.println("EL: removeEvent(): ReWriting the file...");
			DigitalOrganiser.getEventFileManager().write();
	        if (getDebugStatus())
	        	System.out.println("EL: removeEvent(): Finished rewrite call.");
		}
        return true;
    }
    /**
     * Removes an event based on ID.
     * @param id The ID of the event to be removed.
     */
    public boolean removeEvent(String id){
    	if (getDebugStatus())
			System.out.println("EL: removeEvent(String): " +
					"A call to removeEvent(int) is to follow this.");
    	return (removeEvent(findEventIndex(id)));
    }
    
    /**
     * Clears the list and updates the JList. 
     * @return True on success, false on failure.
     */
    public boolean clear(){
    	getEventList().clear();
    	this.update();
    	
    	return true;
    }
    /**
     * Sets the debug switch.
     * @param b True for debug on, false for off.
     * @return The debug switch value.
     */
    public boolean setDebugOn(boolean b){
    	return m_debug = b;
    }
    /**
	 * Disables the list widget.
	 * @param b True for disabling, false for enabling.
	 * @return True on success, false on failure.
	 */
	public boolean disable(boolean b){
		getEventJList().setEnabled(!b);
		update();
		return true;
	}
	
	/**
	 * Creates a panel with a search field, a date chooser button and the event list table.
	 * @return The constructed panel.
	 */
	public JPanel getSearchPanel(){        
		JPanel jp = new JPanel();
        jp.setLayout(SEARCH_PANEL_LAYOUT);        
        final JTextField searchField = new JTextField(); 
        searchField.setText(getSearchString());
        searchField.addCaretListener(new CaretListener() {			
			@Override
			public void caretUpdate(CaretEvent e) {
				if(getDebugStatus())
					System.out.println("EL: getSearchPanel(): "+
							"Search field caret changed.");
				setSearchString(searchField.getText());				
			}
		});
        searchField.requestFocus();
        String COL, ROW;
        COL = "2"; ROW = "2";
		jp.add(searchField,  COL+", "+ROW+", fill, default");
		JLabel searchFieldLabel = new JLabel("Search");
		searchFieldLabel.setEnabled(false);
		COL = "2"; ROW = "3";
		jp.add(searchFieldLabel, COL+", "+ROW+", center, default");
		final JDateChooser dc = new JDateChooser();
		ImageIcon ic = null;
		try{
			ic = new ImageIcon(
				getClass().getResource("/ui/resources/calendar--clock.png"));
		} catch (Exception e){
			System.err.println("EL: getSearchPanel(): "+
					"Problem loading the custom icon for the date chooser.");
		}
		if (ic != null)
			dc.setIcon(ic);
		//The index positions of the start and end of the yyyy-MM-dd substring 
		//from the used date format for java.
		int startIndex 	= 0,
			endIndex	= 10;
		String onlyYMD = SystemDate.DATE_FORMAT_JAVA.substring(	startIndex,
																endIndex);
		//System.out.println("Substring:"+YMD);
		dc.setDateFormatString(onlyYMD);
		dc.addPropertyChangeListener(new PropertyChangeListener() {			
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if(getDebugStatus())
					System.out.println("" +
							"EL: getSearchPanel(): JDateChooser:" +
							" Property changed");				
				if (dc.getDate() != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
					searchField.setText(sdf.format(dc.getDate()));
				}
			}
		});		
		dc.getCalendarButton().setToolTipText("Date search helper");
        COL = "4"; ROW = "2";
		jp.add(dc.getCalendarButton(), COL+", "+ROW+", fill, default");		
		TextFilterator<Event> tf = new MyTextFilterator();		
		TextComponentMatcherEditor<Event> matchEditor = 
				new TextComponentMatcherEditor<Event>( searchField , tf );		
		FilterList<Event> filteredEvents = 
				new FilterList<Event>( getEventList() , matchEditor ); 		
		EventTableFormat columns = new EventTableFormat();		
	    DefaultEventTableModel<Event> tableModel = 
	    		new DefaultEventTableModel<Event>( filteredEvents , columns );
        
	    setSearchTable(new JTable(tableModel));
        formatTable();  
        COL = "2"; ROW = "5";
        String COLSPAN = "5", ROWSPAN = "1";
        String layoutSpec = COL+","+ROW+","+COLSPAN+","+ROWSPAN+",fill,fill";
        jp.add(new JScrollPane(getSearchTable()),layoutSpec);
		return jp;
	}
	
	/**
	 * Formats the search table a bit. Adds a selection listener.
	 */
	private void formatTable(){
	    getSearchTable().getColumnModel().setColumnSelectionAllowed(false);
	    getSearchTable().setRowSelectionAllowed(true);
	    getSearchTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    getSearchTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		int IDColumn = 0;
		TableColumn column = getSearchTable().getColumnModel()
							.getColumn(IDColumn);
		int WIDTH = 30;
		column.setMinWidth(WIDTH);
		column.setPreferredWidth(WIDTH);
		//column.setMaxWidth(WIDTH);
		
		int categoryColumn = 2; 
		column = getSearchTable().getColumnModel().getColumn(categoryColumn);
		WIDTH = 100;
		column.setMinWidth(WIDTH);
		column.setPreferredWidth(WIDTH);
		
		int startColumn = 3;
		column = getSearchTable().getColumnModel().getColumn(startColumn);
		WIDTH = 120;
		column.setMinWidth(WIDTH);
		column.setPreferredWidth(WIDTH);
		
		int endColumn = 4;
		column = getSearchTable().getColumnModel().getColumn(endColumn);
		WIDTH = 120;
		column.setMinWidth(WIDTH);
		column.setPreferredWidth(WIDTH);
		
		int repeatColumn = 5;
		column = getSearchTable().getColumnModel().getColumn(repeatColumn);
		WIDTH = 20;
		column.setMinWidth(WIDTH);
		column.setPreferredWidth(WIDTH);
		
		SelectionListener listener = new SelectionListener(getSearchTable());
		
		getSearchTable().getSelectionModel().addListSelectionListener(listener);
		getSearchTable().getColumnModel().getSelectionModel()
		    .addListSelectionListener(listener);
	}
	
	/**
	 * @brief Listener for the row selection in the table.
	 * 
	 * Sets selected event ID in EventList once a row is selected.<br/>
	 * Un-sets it on row de-selection or when the table changes.
	 * @author Codrin Morhan
	 */
	public class SelectionListener implements ListSelectionListener {
        JTable table;

        /**
         * Default constructor.<br/>
         * It is necessary to keep the table since it is not possible 
         * to determine the table from the event's source.
         * @param table The table passed.
         */
        SelectionListener(JTable table) {
            this.table = table;
        }
        
        /**
         * Implements the interface method to detect row selections.<br/>
         * When a row is selected it sets the currently selected ID to the one 
         * of the event in the selected row.
         * @param e The event.
         */
        public void valueChanged(ListSelectionEvent e) {
            // If cell selection is enabled, both row and column change events are fired
            if (	e.getSource() == table.getSelectionModel()
            		&&
            		table.getRowSelectionAllowed()) {
            	//Row selection changed
            	int index = table.getSelectedRow();
				if(getDebugStatus())
					System.out.println("Row selected:" + index);
            	String id;
                if (index >= 0){
                	int IDColumn = 0;
                	try{
                		id = (String) table.getModel().getValueAt(
                								index , IDColumn );
                	} catch (Exception ex){
                		if(getDebugStatus())
                			System.out.println("EL: SelectionListener():"+
                					"Table probably changed.");
                		id = "";
                	}
    				if(getDebugStatus())
    					System.out.println("Selected ID: ["+id+"]");
                } else 
                	id = "";
                DigitalOrganiser.getEventList().setSelectedEventID(id);
            } else if (
            	e.getSource() == table.getColumnModel().getSelectionModel()
                &&
                table.getColumnSelectionAllowed()){
                // Column selection changed
            }

            if (e.getValueIsAdjusting()) {
                // The mouse button has not yet been released
            }
        }
    }
	
	/**
	 * @brief Own implementation of a TextFilterator class.
	 * @author Codrin Morhan
	 *
	 */
	public class MyTextFilterator implements TextFilterator<Event>{

		@Override
		/**
		 * Overrides the method. Specifies which fields are sought.
		 * @param baseList A list that the implementor shall add their filter strings to.
		 * @param element The object to extract the filter strings from. 
		 */
		public void getFilterStrings(List<String> baseList, Event element) {
			baseList.add(element.getTitle());
			baseList.add(element.getStart());
			baseList.add(element.getEnd());
			baseList.add(element.getLocation());
			baseList.add(element.getDescription());
			baseList.add(element.getPersonsInvolved());
		}
		
	}
	
	/**
	 * Makes the search table to have nothing selected.
	 */
	public void deselectSearchTable(){
		if (getSearchTable() != null)
			getSearchTable().clearSelection();
	}
	
	/**
	 * Prints out the event list to the console.<br/>
	 * Mainly used for debugging.
	 */
	public void iterateEventList(){
		if(getDebugStatus())
			System.out.println("EL: iterateEventList:\n"+
		"--------------------------------------------------------------------");
		for (int i = 0; i < getSize(); i++){
			Event e = getEventList().get(i);
			System.out.print("\nIndex ["+		i+"] ");
			System.out.print(" ID ["+			e.getID()+"]");
			System.out.print(" Title ["+		e.getTitle()+"] ");
			System.out.print(" Category ["+		e.getCategory()+"] ");
			System.out.print(" Start ["+		e.getStart()+"] ");
			System.out.print(" End ["+			e.getEnd()+"] ");
			System.out.print(" Location ["+		e.getLocation()+"] ");
			System.out.print(" Repeats ["+		e.getRepetition()+"] ");
			System.out.print(" Description ["+	e.getDescription()+"] ");
		}
		System.out.println("\n"+
		"--------------------------------------------------------------------");
	}
	
	/**
	 * Gets an event list containing only the events occurring on the specified day.
	 * @param d The date. The day gets extracted from this.
	 * @return The event list which is empty if the date was invalid.
	 */
	public EventList getEventsForDay(DateTime d){
		d = SystemTime.setTimeZero(d);
		EventList el = new EventList();
		if (d == null)
			return el;
		if (!d.hasYearMonthDay())
			return el;
		for(int i = 0; i < getSize(); i++){
			Event e = getEvent(i);
			if(e.getStart().isEmpty())
				continue;
			DateTime s = e.getStartingDateTime();
			s = SystemTime.setTimeZero(s);
			if (getDebugStatus())
				System.out.println(s.toString() + " | "+d.toString());
			if (d.lt(s)) {
				if (getDebugStatus())
				   System.out.println(s.toString()+" less than "+ d.toString());
				continue;
			}
			if (e.getRepetition().equalsIgnoreCase("no")){
				if (d.gt(s)) continue;
				if (SystemDate.YMDEqual(s, d)){
					el.addEvent(e);
					continue;
				}
				else
					continue;
			}
			else if (e.getRepetition().equalsIgnoreCase("daily")){
				el.addEvent(e);
				continue;
			}
			else if (e.getRepetition().equalsIgnoreCase("weekly")){
				if (s.getWeekDay() == d.getWeekDay()){
					el.addEvent(e);
					continue;
				}
			}
			else if (e.getRepetition().equalsIgnoreCase("monthly")){
				if (s.getDay() == d.getDay()){
					el.addEvent(e);
					continue;
				}
			}
			else if (e.getRepetition().equalsIgnoreCase("yearly")){
				if (s.getMonth() == d.getMonth() && s.getDay() == d.getDay()){
					el.addEvent(e);
					continue;
				}
			}
		}
		return el;
	}
	
	/** The list of events. */
    private BasicEventList<Event> m_eventList = new BasicEventList<Event>();
    /** The debug switch. */
    private boolean m_debug = false;
    /** The JList widget */
	private JList m_eventJList;
	/** Tracks if the addEvent(Event) was called by the add(String[]). */
	private static boolean INNER_ADD_CALL = false;
	/** The limit of the event list size. */
	public static final int SIZE_LIMIT = 1000;
	
	/** 
	 * Declares if the EL is to be used in junction with the file package.<br/>
	 * It is used to differentiate the class used by DO from just another class instance.<br/>
	 * The file writing routines get called or not based on this switch. 
	 */
	private boolean m_FileWritingMode = false;
	
    /** 
     * Tracks the selected event's ID.
     * Used for linking UI actions, intended for an event, to the actual event object. */
    private String m_selectedEventID = "";
    
    /** The layout used by the search panel. */
    private final static FormLayout SEARCH_PANEL_LAYOUT = new FormLayout(
        	new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(86dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(152dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(143dlu;pref):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,});      
    
    /** Keeps the text in the search field. Makes it not reset every time it's initialised. */
    private String m_SearchString = "";
	
	/** The search table. */
    private JTable m_SearchTable;
    /**
     * Main method performs tests upon EventsList.
     * @param args Arguments will be ignored
     */
    public static void main(String[] args){         
        

    	/** @test Adding 150 events; adding another one manually; checking the output. */
        EventList el = TestData.getFilledEventList(150);
        //el.setDebugOn(true);
        System.out.println("Size before: "+el.getSize());
        el.addEvent(new Event());
        System.out.println("Size after: "+el.getSize());
        System.out.println(el.getEvent(0).getTitle());
        
        
        DigitalOrganiser.setEventList(el);
        //DigitalOrganiser.getEventList().setDebugOn(true);
     
        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame jf = new JFrame ();
		        
		        jf.setTitle("EventsList as a table");

		        /** @test Displaying the search panel; testing search functionality. */
		        jf.add(DigitalOrganiser.getEventList().getSearchPanel());
		        jf.pack();
		        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		        jf.setLocationRelativeTo(null);
		        jf.setVisible(true);
		        
		        /** @test Displaying the event list as a JList. */
		        //jf.setTitle( "EventsList as JList");
		        //jf.add(el.getJScrollPane());
			}
		});
      
    }
}