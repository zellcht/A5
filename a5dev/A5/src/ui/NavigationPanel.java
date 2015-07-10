/**
 * @file NavigationPanel.java
 * @author Codrin Morhan
 * @date 9th March 2012
 * @brief Contains the class managing the navigation panel and its widgets.
 */
package ui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import addressBook.AddressBook;
import addressBook.Contact;
import addressBook.ContactController;

import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListClickListener;
import com.explodingpixels.macwidgets.SourceListClickListener.Button;
import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import digitalOrganiser.DigitalOrganiser;
import events.Event;
import events.EventController;
import events.EventList;

/**
 * @brief The navigation panel and all its widgets.
 * 
 * The class itself is a JPanel and it adds to itself a SourceList.<br/>
 * The SourceList gets initialised with 3 categories of four items each.<br/>
 * Uses SourceList from the Mac Widgets library.
 * @see <a href="https://code.google.com/p/macwidgets/">Mac Widgets weblink</a>
 * @author Codrin Morhan
 */
@SuppressWarnings("serial")
public class NavigationPanel extends JPanel{
	
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
	 * Gets the source list used by this panel.
	 * @return The source list. 
	 */
	private SourceList getSourceList(){return m_SourceList;}
	/** 
	 * Sets the source list. 
	 * @param sl The new source list.
	 */
	private void setSourceList(SourceList sl){ m_SourceList = sl;}
	
	/** 
	 * Gets the index of the category that was last selected in the source list.
	 * @return The index.
	 */
	private int getLastSelectedCategoryIndex(){
		return m_LastSelectedCategoryIndex;
	}
	/**
	 * Sets the index of the last selected category in the source list.
	 * @param index The index of the category that was last selected.
	 * @return True on success, false on failure.
	 */
	public boolean setLastSelectedCategoryIndex(int index){
		if (getDebugStatus())
			System.out.println("NP: setLastSelectedCategoryIndex: "+
					"Passed argument: "+ index);
		if (index >= getIndexOf("calendar") && index <= getIndexOf("Contacts")){
			m_LastSelectedCategoryIndex = index;
			return true;
		}
		return false;		
	}
	
	/** 
	 * Gets the index of the item that was last selected in the calendar category.
	 * @return The index.
	 */
	private int getLastSelectedItemIndex(){
		return m_LastSelectedItemIndex;
	}
	/** 
	 * Sets the index of the last selected item in the calendar category.
	 * @param index The index of the item that was last selected.
	 * @return True on success, false on default.
	 */
	public boolean setLastSelectedItemIndex(int index){
		if (getLastSelectedCategoryIndex() == getIndexOf("calendar")){
			if (index >= getIndexOf("day") && index <= getIndexOf("Year")){
				m_LastSelectedItemIndex = index;
				return true;
			} else {
				if (getDebugStatus())
					System.err.println(
						"Bad last selected item in Calendar category.");
				return false;
			}
		} else if (getLastSelectedCategoryIndex() == getIndexOf("events")){
			if (index == getIndexOf("view all events")){
				m_LastSelectedItemIndex = index;
				return true;
			} else {
				if (getDebugStatus())
					System.err.println(
						"Bad last selected item in Events category.");
				return false;
			}
		} else if (getLastSelectedCategoryIndex() == getIndexOf("contacts"))
			if (index == getIndexOf("view all contacts")){
				m_LastSelectedItemIndex = index;
				return true;
			} else {
				if (getDebugStatus())
					System.err.println(
						"Bad last selected item in Contacts category.");
				return false;
			}
		else
			if (getDebugStatus())
				System.err.println("Bad last selected category.");
		return false;
	}
	/** 
	 * Gets the index of the requested category or item.
	 * @param s The name of the item or category to get index of, case insensitive.<br/>
	 * Accepted arguments:<br/>
	 * <ul>
	 * <li><tt>"calendar"</tt></li>
	 * <li><tt>"events"</tt></li>
	 * <li><tt>"contacts"</tt></li>
	 * <li><tt>"day"</tt></li>
	 * <li><tt>"week"</tt></li>
	 * <li><tt>"month"</tt></li>
	 * <li><tt>"year"</tt></li>
	 * <li><tt>"view all events"</tt></li>
	 * <li><tt>"view all contacts"</tt></li>
	 * @return The index number.
	 */
	public int getIndexOf(String s){
		//categories
		if (s.equalsIgnoreCase("calendar"))
			return m_CALENDAR_INDEX;
		else if (s.equalsIgnoreCase("events"))
			return m_EVENTS_INDEX;
		else if (s.equalsIgnoreCase("contacts"))
			return m_CONTACTS_INDEX;
		//items
		else if (s.equalsIgnoreCase("day"))
			return m_DAY_INDEX;
		else if (s.equalsIgnoreCase("week"))
			return m_WEEK_INDEX;
		else if (s.equalsIgnoreCase("month"))
			return m_MONTH_INDEX;
		else if (s.equalsIgnoreCase("year"))
			return m_YEAR_INDEX;
		else if (s.equalsIgnoreCase("view all events"))
			return m_VIEW_ALL_EVENTS_INDEX;
		else if (s.equalsIgnoreCase("view all contacts"))
			return m_VIEW_ALL_CONTACTS_INDEX;
		else 
			if (getDebugStatus())
				System.err.println("No index stored for that argument");
		int INVALID_INDEX = -2;
		return INVALID_INDEX;
	}
	/**
	 * Resets the selected item in the source list to the last selected item in the calendar category.
	 */
	public void resetSelection(){
		try{
		getSourceList().setSelectedItem(getSourceList()
										.getModel()
										.getCategories()
										.get(getLastSelectedCategoryIndex())
										.getItems()
										.get(getLastSelectedItemIndex()));
		}catch(Exception e){
			if (getDebugStatus())
				System.out.println("NP: resetSelection(): couldn't reset.");
			return;
		}
	}

	/** Sets up the navigation panel. Loads all the labels in it. */
	public NavigationPanel(){
		
		initialiseSourceList();
		
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("180px"),},
			new RowSpec[] {
				RowSpec.decode("fill:default:grow"),}));
    	EventQueue.invokeLater(new Runnable() {
			
				@Override
				public void run() {
					final String COL = "1", ROW = "1";
					add(getSourceList().getComponent(), COL+","+ROW+
														", fill, fill");
				}
    	});
	}
	
	/** Initialises the source list. */
	private void initialiseSourceList(){
        final SourceListModel model = new SourceListModel();
        
        createCalendarCategory(model);
        createEventsCategory(model);
        createAddressBookCategory(model);
        
        setSourceList(new SourceList(model));
        getSourceList().getComponent().revalidate();
        getSourceList().addSourceListClickListener(new SLClickListener());
        int NOTHING = -1;
        if (getLastSelectedCategoryIndex() == NOTHING){//if nothing was last selected
        	setLastSelectedCategoryIndex(getIndexOf("calendar"));
        	setLastSelectedItemIndex(getIndexOf("day"));
        }
        resetSelection();
        	
	}
	/** 
	 * Creates the calendar category.<br/>
	 * Creates the items for that category.<br/>
	 * Puts those items in the category.<br/>
	 * @param m The model in which all this is to be achieved.
	 */
	private void createCalendarCategory(SourceListModel m){
		ImageIcon icon1 = new ImageIcon();
		try{
			icon1 = new ImageIcon (
	        	getClass().getResource("/ui/resources/calendar-day.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createCalendarCategory(): "+
						"Problem loading the icon for [Day].");
		}
		ImageIcon icon2 = new ImageIcon();
		try{
			icon2 = new ImageIcon (
	        	getClass().getResource("/ui/resources/calendar-select.png"));
		} catch (Exception e){
			System.err.println("NP: createCalendarCategory(): "+
					"Problem loading the icon for [Week].");
		}
		ImageIcon icon3 = new ImageIcon();
		try{
			icon3 = new ImageIcon (
	        	getClass()
	        	.getResource("/ui/resources/calendar-select-days-span.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createCalendarCategory(): "+
					"Problem loading the icon for [Month].");
		}
		ImageIcon icon4 = new ImageIcon();
		try{
			icon4 = new ImageIcon (
        		getClass()
        		.getResource("/ui/resources/calendar-select-month.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createCalendarCategory(): "+
					"Problem loading the icon for [Year].");
		}
        
        SourceListCategory category = new SourceListCategory("Calendar");  
		
		SourceListItem item1 = new SourceListItem("Day", 	icon1);
        SourceListItem item2 = new SourceListItem("Week", 	icon2);
        SourceListItem item3 = new SourceListItem("Month", 	icon3);
        SourceListItem item4 = new SourceListItem("Year", 	icon4);

        SourceListItem[] items = {item1,item2,item3,item4}; 
        addSection(	m, category, items );
	}
	/** 
	 * Creates the events category.<br/>
	 * Creates the items for that category.<br/>
	 * Puts those items in the category.<br/>
	 * @param m The model in which all this is to be achieved.
	 */
	private void createEventsCategory(SourceListModel m){
		ImageIcon icon1 = new ImageIcon();
		try{
			icon1 = new ImageIcon (
        		getClass().getResource("/ui/resources/calendar--plus.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createEventsCategory(): "+
					"Problem loading the icon for [Add event].");
		}
		ImageIcon icon2 = new ImageIcon();
		try{
			icon2 = new ImageIcon (
        		getClass().getResource("/ui/resources/calendar--pencil.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createEventsCategory(): "+
					"Problem loading the icon for [Edit event].");
		}
		ImageIcon icon3 = new ImageIcon();
		try{
			icon3 = new ImageIcon (
	       		getClass().getResource("/ui/resources/calendar--minus.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createEventsCategory(): "+
					"Problem loading the icon for [Delete event].");
		}
		ImageIcon icon4 = new ImageIcon();
		try{
			icon4 = new ImageIcon (
	       		getClass().getResource("/ui/resources/calendar-list.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createEventsCategory(): "+
					"Problem loading the icon for [View all events].");
		}
		
		SourceListCategory category = new SourceListCategory("Events");   
		
		SourceListItem item1 = new SourceListItem("Add event",			icon1);
		SourceListItem item2 = new SourceListItem("Edit event",			icon2);
		SourceListItem item3 = new SourceListItem("Delete event", 		icon3);
		SourceListItem item4 = new SourceListItem("View all events",	icon4);
		
		SourceListItem[] items = {item1,item2,item3,item4}; 
        addSection(	m, category, items );
	}
	/** 
	 * Creates the address book category.<br/>
	 * Creates the items for that category.<br/>
	 * Puts those items in the category.<br/>
	 * @param m The model in which all this is to be achieved.
	 */
	private void createAddressBookCategory(SourceListModel m){
		ImageIcon icon1 = new ImageIcon();
		try{
			icon1 = new ImageIcon (
        		getClass()
        		.getResource("/ui/resources/book-open-text-image.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createAddressBookCategory(): "+
					"Problem loading the icon for [View all contacts].");
		}
		ImageIcon icon2 = new ImageIcon();
		try{
			icon2 = new ImageIcon (
        		getClass().getResource("/ui/resources/contact--add.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createAddressBookCategory(): "+
					"Problem loading the icon for [Add contact].");
		}
		ImageIcon icon3 = new ImageIcon();
		try{
			icon3 = new ImageIcon (
        		getClass().getResource("/ui/resources/contact--edit.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createAddressBookCategory(): "+
					"Problem loading the icon for [Edit contact].");
		}
		ImageIcon icon4 = new ImageIcon();
		try{
			icon4 = new ImageIcon (
        		getClass().getResource("/ui/resources/contact--delete.png"));
		} catch (Exception e){
			if (getDebugStatus())
				System.err.println("NP: createAddressBookCategory(): "+
					"Problem loading the icon [Delete contact].");
		}
		
        SourceListCategory category = new SourceListCategory("Address Book"); 
        
        SourceListItem item1 = new SourceListItem("View all contacts",	icon1);
        SourceListItem item2 = new SourceListItem("Add contact", 		icon2);
        SourceListItem item3 = new SourceListItem("Edit contact", 		icon3);
        SourceListItem item4 = new SourceListItem("Delete contact", 	icon4);
        SourceListItem item5 = new SourceListItem("Auto Contacts", 	icon1);
        
        SourceListItem[] items = {item1,item2,item3,item4,item5}; 
        addSection(	m, category, items );
	}

	/**
	 * Adds a category with, specified items inside, to a source list model.
	 * @param m The model in which all this is done.
	 * @param category The category.
	 * @param items The array with the items that will be added to the category.
	 */
	 private void addSection( 	SourceListModel m,
			 					SourceListCategory category,
			 					SourceListItem[] items)
	 {		 
	        m.addCategory(category);
	        
	        for (SourceListItem item : items)
	        	m.addItemToCategory(item, category);
	 }
	/**
	 * @brief Custom source list listener.
	 * 
	 * Creates a custom source list listener.<br/>
	 * Implements click reactions for all items in the source list.
	 * @author Codrin Morhan
	 * @date 13th March 2012
	 *
	 */
	public class SLClickListener implements SourceListClickListener{
		
		/** 
		 * Had to be implemented too, although it's not used.
		 * @param category The category clicked.
		 * @param button The mouse button clicked.
		 * @param clickCount The number of clicks.
		 */
		@Override
		public void sourceListCategoryClicked(	SourceListCategory category,
												Button button,
												int clickCount){/*not used*/}	
		
		/** 
		 * Overrides the method for our purposes.
		 * Handles the clicking of items in the source list.
		 * @param item The item clicked.
		 * @param button The mouse button clicked.
		 * @param clickCount The number of clicks.
		 */
		@Override
		public void sourceListItemClicked(	SourceListItem item,
											Button button,
											int clickCount){
			
			if (getDebugStatus())
				System.out.println("NP: SLClickListener: called");
			if (checkDependencies() == false){
				if (getDebugStatus())
					System.err.println("NP: sourceListItemClicked(): " +
						"Critical subsystem missing. " +
						"The menu navigation panel will not be reactive");
				return;
			}
			
			String i = item.getText();
			
			if(i.equalsIgnoreCase("day")) performDayClicked();
			else if (i.equalsIgnoreCase("week")) performWeekClicked();
			else if (i.equalsIgnoreCase("month")) performMonthClicked();
			else if (i.equalsIgnoreCase("year")) performYearClicked();
			
			else if (i.equalsIgnoreCase("add event")) performAddEventClicked();
			else if (i.equalsIgnoreCase("edit event"))performEditEventClicked();
			else if (i.equalsIgnoreCase("delete event"))
				performDeleteEventClicked();
			else if (i.equalsIgnoreCase("view all events"))
				performViewAllEventsClicked();		
			
			else if (i.equalsIgnoreCase("view all contacts"))
				performViewAllContactsClicked();
			else if (i.equalsIgnoreCase("add contact")) 
				performAddContactClicked();
			else if (i.equalsIgnoreCase("edit contact"))
				performEditContactClicked();
			else if (i.equalsIgnoreCase("delete contact"))
				performDeleteContactClicked();
			else if (i.equalsIgnoreCase("auto contacts"))
				performAutoContactsClicked();
		}
		
	}
	
	/**
	 * Checks if DigitalOrganiser's EventList, AddressBook and Views are initialised.
	 * @return True if none are null, false otherwise.
	 */
	private boolean checkDependencies(){
		if (DigitalOrganiser.getEventList() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO EL not initialised.");
			return false;
		}
		if (DigitalOrganiser.getAddressBook() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO AB not initialised.");
			return false;
		}
		if (DigitalOrganiser.getDayView() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO DV not initialised.");
			return false;
		}
		if (DigitalOrganiser.getWeekView() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO WV not initialised.");
			return false;
		}
		if (DigitalOrganiser.getMonthView() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO MV not initialised.");
			return false;
		}
		if (DigitalOrganiser.getYearView() == null){
			if (getDebugStatus())
				System.err.println("NP: checkDependencies(): " +
					"DO YV not initialised.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Clears the selected ID in the DigitalOrganiser's EventList and AddressBook.<br/>
	 * This behaviour is due to the fact click somewhere else should lose the event/contact selection.
	 * @return True if they were both cleared, false otherwise.
	 */
	private boolean clearIDs(){
		try{DigitalOrganiser.getEventList().setSelectedEventID("");}
		catch (Exception ex){
			if (getDebugStatus())
				System.err.println("NP: clearIDs(): " +
					"Couldn't clear DO EL id. " +
					"Check if DO EL is initialised.");
			return false;
		}
		try{DigitalOrganiser.getAddressBook().setSelectedContactID("");}
		catch (Exception ex){
			if (getDebugStatus())
				System.err.println("NP: clearIDs(): " +
					"Couldn't clear DO AB id. " +
					"Check if DO AB is initialised.");
			return false;
		}
		return true;
	}
	
	/**
	 * Switched the DigitalOrganiser's UserInterface's view panel to the specified one.
	 * @param view The specified view. Valid arguments: day,week,month,year, all case insensitive.
	 * @return True if the switch was successful, false otherwise.
	 */
	private boolean switchToView(String view){
		if (view == null) return false;
		else if (view.isEmpty()) return false;
		
		if (view.equalsIgnoreCase("day"))
			try{
				DigitalOrganiser.getUserInterface().setViewPanel
					(DigitalOrganiser.getDayView());
			} catch (Exception ex){
				if (getDebugStatus())
					System.err.println("NP: switchToView(): " +
						"Couldn't switch to day view. " +
						"Check if DO DV & DO UI is initialised.");
				return false;
			}
		else if (view.equalsIgnoreCase("week"))
			try{
				DigitalOrganiser.getUserInterface().setViewPanel
					(DigitalOrganiser.getWeekView());
			} catch (Exception ex){
				if (getDebugStatus())
					System.err.println("NP: switchToView(): " +
						"Couldn't switch to week view. " +
						"Check if DO WV & DO UI is initialised.");
				return false;
			}
		else if (view.equalsIgnoreCase("month"))
			try{
				DigitalOrganiser.getUserInterface().setViewPanel
					(DigitalOrganiser.getMonthView());
			} catch (Exception ex){
				if (getDebugStatus())
					System.err.println("NP: switchToView(): " +
						"Couldn't switch to month view. " +
						"Check if DO MV & DO UI is initialised.");
				return false;
			}
		else if (view.equalsIgnoreCase("year"))
			try{
				DigitalOrganiser.getUserInterface().setViewPanel
					(DigitalOrganiser.getYearView());
			} catch (Exception ex){
				if (getDebugStatus())
					System.err.println("NP: switchToView(): " +
						"Couldn't switch to week view. " +
						"Check if DO YV & DO UI is initialised.");
				return false;
			}
		
		return true;
	}
	
	/** The set of actions to be performed if the Day item is clicked. */
	private void performDayClicked(){
		if (getDebugStatus())
			System.out.println("Day clicked.");
		setLastSelectedCategoryIndex(getIndexOf("calendar"));
		setLastSelectedItemIndex(getIndexOf("day"));

		clearIDs();
		switchToView("day");
		
		DigitalOrganiser.getUserInterface().setViewPanel(
				DigitalOrganiser.getDayView());
		//DigitalOrganiser.updateViews();
	}
	
	/** The set of actions to be performed if the Week item is clicked. */
	private void performWeekClicked(){
		if (getDebugStatus())
			System.out.println("Week clicked.");
		setLastSelectedCategoryIndex(getIndexOf("calendar"));
		setLastSelectedItemIndex(getIndexOf("week"));
		
		clearIDs();
		switchToView("week");
		//DigitalOrganiser.updateViews();
	}
	
	/** The set of actions to be performed if the Month item is clicked. */
	private void performMonthClicked(){
		if (getDebugStatus())
			System.out.println("Month clicked.");
		setLastSelectedCategoryIndex(getIndexOf("calendar"));
		setLastSelectedItemIndex(getIndexOf("month"));
		
		clearIDs();
		switchToView("month");
		//DigitalOrganiser.updateViews();
	}
	
	/** The set of actions to be performed if the Year item is clicked. */
	private void performYearClicked(){
		if (getDebugStatus())
			System.out.println("Year clicked.");
		setLastSelectedCategoryIndex(getIndexOf("calendar"));
		setLastSelectedItemIndex(getIndexOf("year"));
		
		clearIDs();
		switchToView("year");
		//DigitalOrganiser.updateViews();
	}
    
	/** The set of actions to be performed if the Add event item is clicked. */
	private void performAddEventClicked(){
		if (getDebugStatus())
			System.out.println("Add event clicked.");
		if(	DigitalOrganiser.getEventList().getSize()
				>=
			EventList.SIZE_LIMIT)
		{
			if (getDebugStatus())
				System.out.println("NP: Limit reached. "+
					"Forcing EL to pop up the error message.");
			DigitalOrganiser.getEventList().addEvent(new Event());
			return;
		}
		EventController ec = new EventController();
		Event e = new Event();
		e.setToDefaultValues();
		ec.setEvent(e);
		ec.showEventWindow();
		resetSelection();
		clearIDs();
	}
	
	/** The set of actions to be performed if the Edit event item is clicked. */
	public void performEditEventClicked(){
		if (getDebugStatus())
			System.out.println("Edit event clicked.");
		String ID = "";
		try{ ID = DigitalOrganiser.getEventList().getSelectedEventID(); }
		catch (Exception ex){
			if (getDebugStatus())
				System.err.println("NP: performEditEventClicked(): " +
					"Couldn't get selected ID from DO EL. " +
					"Might not be instantiated.");
		}
		if (ID.isEmpty()){
			JOptionPane.showMessageDialog(	null,
					"Cannot edit: you must have an event selected.",
					"No event selected",
					JOptionPane.ERROR_MESSAGE);
			SourceListItem i2 = new SourceListItem("view all events");
        	setLastSelectedCategoryIndex(getIndexOf("events"));
        	setLastSelectedItemIndex(getIndexOf("view all events"));
			Button button = Button.LEFT;
			int clickCount = 1;
        	new SLClickListener().sourceListItemClicked(i2,button,clickCount);
        	resetSelection();
			return;
		}
		EventController ec = new EventController();
		ec.setEvent(	DigitalOrganiser
						.getEventList()
						.getEvent(ID));
		ec.setEditMode(true);
		ec.showEventWindow();
		clearIDs();
		DigitalOrganiser.getEventList().deselectSearchTable();

	}
	
	/** The set of actions to be performed if the Delete event item is clicked. */
	private void performDeleteEventClicked(){
		if (getDebugStatus())
			System.out.println("Delete event clicked.");
		String ID = 	DigitalOrganiser
						.getEventList()
						.getSelectedEventID();
		
		if (ID.isEmpty()){
			JOptionPane.showMessageDialog(	null,
					"Cannot delete: you must have an event selected.",
					"No event selected",
					JOptionPane.ERROR_MESSAGE);
			SourceListItem i2 = new SourceListItem("view all events");
        	setLastSelectedCategoryIndex(getIndexOf("events"));
        	setLastSelectedItemIndex(getIndexOf("view all events"));
        	Button button = Button.LEFT;
			int clickCount = 1;
        	new SLClickListener().sourceListItemClicked(i2,button,clickCount);
        	resetSelection();
			return;
		}
		
		int result = 1;//option pane answer is NO by default
		if (!DigitalOrganiser.getEventList().getEvent(ID).getRepetition()
				.equalsIgnoreCase("no")){
			result = JOptionPane.showConfirmDialog(	null,
				"Beware, this will delete all occurences of the event.",
				"Deleting a repeating event",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
			if (result == 0){
				DigitalOrganiser.getEventList().removeEvent(ID);
				DigitalOrganiser.updateViews();
			}
			return;
		}
		DigitalOrganiser.getEventList().removeEvent(ID);
		DigitalOrganiser.updateViews();
		
		resetSelection();
		
		DigitalOrganiser.getEventList().setSelectedEventID("");
		DigitalOrganiser.getAddressBook().setSelectedContactID("");
		
	}
	
	/** The set of actions to be performed if the View all events item is clicked. */
	private void performViewAllEventsClicked(){
		if (getDebugStatus())
			System.out.println("View all events clicked.");
		setLastSelectedCategoryIndex(getIndexOf("events"));
		setLastSelectedItemIndex(getIndexOf("view all events"));
		try{DigitalOrganiser.getEventList().setSelectedEventID("");}
		catch(Exception ex){
			if (getDebugStatus())
				System.err.println("NP: performViewAllEventsClicked(): " +
					"Could not set selected ID for DO EL." +
					"Might not be initialised.");
		}
		try{
		DigitalOrganiser.getUserInterface().setViewPanel
			(DigitalOrganiser.getEventList().getSearchPanel());
		} catch (Exception ex){
			if (getDebugStatus())
				System.err.println("NP: performViewAllEventsClicked(): " +
					"Could not set DO UI view panel." +
					"Might not be initialised.");
		}
	}
	
	/** The set of actions to be performed if the View all contacts item is clicked. */
	private void performViewAllContactsClicked(){
		if (getDebugStatus())
			System.out.println("View all contacts clicked.");
		setLastSelectedCategoryIndex(getIndexOf("contacts"));
		setLastSelectedItemIndex(getIndexOf("view all contacts"));
		DigitalOrganiser.getEventList().setSelectedEventID("");
		DigitalOrganiser.getAddressBook().setSelectedContactID("");
		
		DigitalOrganiser.getUserInterface().setViewPanel
			(DigitalOrganiser.getAddressBook().getSearchPanel());
	}
	
	private void performAddContactClicked(){
		if (getDebugStatus())
			System.out.println("Add contact clicked.");
		resetSelection();
		
		if(	DigitalOrganiser.getAddressBook().getSize()
				>=
			AddressBook.SIZE_LIMIT)
		{
			if (getDebugStatus())
				System.out.println("NP: Limit reached. "+
					"Forcing AB to pop up the error message.");
			DigitalOrganiser.getAddressBook().addContact(new Contact());
			return;
		}
		ContactController cc = new ContactController();
		Contact c = new Contact();
		//c.setToDefaultValues();
		cc.setContact(c);
		cc.showContactWindow();
		resetSelection();
		
		DigitalOrganiser.getEventList().setSelectedEventID("");
		DigitalOrganiser.getAddressBook().setSelectedContactID("");
	}
	
	/** The set of actions to be performed if the Edit contact item is clicked. */
	public void performEditContactClicked(){
		if (getDebugStatus())
			System.out.println("Edit contact clicked.");
		
		ContactController cc = new ContactController();
		String ID = 	DigitalOrganiser
						.getAddressBook()
						.getSelectedContactID();
		if (ID.isEmpty()){
			JOptionPane.showMessageDialog(	null,
					"Cannot edit: you must have a contact selected.",
					"No contact selected",
					JOptionPane.ERROR_MESSAGE);
			SourceListItem i2 = new SourceListItem("view all contacts");
        	setLastSelectedCategoryIndex(getIndexOf("address book"));
        	setLastSelectedItemIndex(getIndexOf("view all contacts"));
        	Button button = Button.LEFT;
			int clickCount = 1;
        	new SLClickListener().sourceListItemClicked(i2,button,clickCount);
        	resetSelection();
			return;
		}
		cc.setContact(	DigitalOrganiser
						.getAddressBook()
						.getContact(ID));
		cc.setEditMode(true);
		cc.showContactWindow();
		
		clearIDs();
		
	}
	
	/** The set of actions to be performed if the Delete contact item is clicked. */
	private void performDeleteContactClicked(){
		if (getDebugStatus())
			System.out.println("Delete contact clicked.");
		resetSelection();
		
		String ID = 	DigitalOrganiser
				.getAddressBook()
				.getSelectedContactID();
		
		if (ID.isEmpty()){
			JOptionPane.showMessageDialog(	null,
					"Cannot delete: you must have a contact selected.",
					"No contact selected",
					JOptionPane.ERROR_MESSAGE);
			SourceListItem i2 = new SourceListItem("view all contacts");
        	setLastSelectedCategoryIndex(getIndexOf("address book"));
        	setLastSelectedItemIndex(getIndexOf("view all contacts"));
        	Button button = Button.LEFT;
			int clickCount = 1;
        	new SLClickListener().sourceListItemClicked(i2,button,clickCount);
        	resetSelection();
			return;
		}
	
		DigitalOrganiser.getAddressBook().removeContact(ID);
		
		DigitalOrganiser.getEventList().setSelectedEventID("");
		DigitalOrganiser.getAddressBook().setSelectedContactID("");
	}
	/** The set of actions to be performed if the auto contacts item is clicked. */
	private void performAutoContactsClicked(){
		if (getDebugStatus())
			System.out.println("Auto contacts clicked.");
		setLastSelectedCategoryIndex(getIndexOf("contacts"));
		setLastSelectedItemIndex(getIndexOf("auto contacts"));
		new AutoContacts();
		/**
		DigitalOrganiser.getEventList().setSelectedEventID("");
		DigitalOrganiser.getAddressBook().setSelectedContactID("");
		
		DigitalOrganiser.getUserInterface().setViewPanel
			(DigitalOrganiser.getAddressBook().getSearchPanel());*/
	}
    /** The source list that holds all the selectable items. */
    private SourceList m_SourceList = new SourceList();
    
    /** Tracks which category was last clicked. */
    private int m_LastSelectedCategoryIndex = -1;
     
    /** Tracks which item in calendar category was last clicked. */
    private int m_LastSelectedItemIndex = -1;
    
    /** Index of the Calendar category. */
    private final int m_CALENDAR_INDEX = 0;
    /** Index of the Events category. */
    private final int m_EVENTS_INDEX = 1;
    /** Index of the Contacts category. */
    private final int m_CONTACTS_INDEX = 2;
    
    /** Index of the Day item. */ 
    private final int m_DAY_INDEX = 0;
    /** Index of the Week item. */ 
    private final int m_WEEK_INDEX = 1;
    /** Index of the Month item. */ 
    private final int m_MONTH_INDEX = 2;
    /** Index of the Year item. */ 
    private final int m_YEAR_INDEX = 3;

    /** Index of the View all events item. */
    private final int m_VIEW_ALL_EVENTS_INDEX = 3;
    
    /** Index of the View all contacts item. */
    private final int m_VIEW_ALL_CONTACTS_INDEX = 0;
    
    /** The debug switch. */
    private boolean m_debug = false;
    
    /**
     * Visual tests for the class.
     * @param args Arguments are ignored.
     */
    public static void main (String[] args){
    	//DigitalOrganiser.initialise();
    	/** @test Displaying the panel. */
    	EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		    	JFrame jf = new JFrame();
		    	jf.setTitle("NP Test");
			 	NavigationPanel np = new NavigationPanel();
		    	jf.add(np);
		    	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		       	jf.pack();
		       	final int WIDTH = 200, HEIGHT = 400;
		       	jf.setSize(new Dimension(WIDTH,HEIGHT));
		    	jf.setVisible(true);
				
			}
		});

    }
}
