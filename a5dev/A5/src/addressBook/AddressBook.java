/**
 * @file AddressBook.java
 * @author 
 * @date 21/02/2012
 * @brief Contains the AddressBook class.
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
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

import testingFramework.TestData;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.DefaultEventTableModel;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser;

/**
 * @brief Class for working with a list of contacts.
 * 
 * Basically a wrapper for a GlazedLists' BasicEventList (which is itself another wrapper for a list).<br/>
 * Keeps a list of contacts and allows the manipulation of the said list.<br/>
 * Can display itself as a JList or as a search-able table.<br/>
 * Can work with or without the File subsystem (the FileWritingMode).
 * @see <a href="http://www.glazedlists.com/">GlazedLists weblink</a> 
 * @author Yan Sun
 */
public class AddressBook{
	
	
	/**
	 * Gets the search table.
	 * @return The table.
	 */
	protected JTable getSearchTable(){return m_SearchTable;}
	/**
	 * Sets the search table.<br/>
	 * Automatically adds the custom listener to it.
	 * @param t The new table.
	 * @see ContactsTableListener
	 */
	protected void setSearchTable(JTable t){
		m_SearchTable = t;
		getSearchTable().addMouseListener(new ContactsTableListener());
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
	 * Gets the file writing mode status.
	 * @return The current status of the switch.
	 */
	public boolean getFileWritingMode(){return m_FileWritingMode;}
	/**
	 * Sets the file writing mode status
	 * @param b true for ON, false for OFF.
	 */
	public void setFileWritingMode(boolean b){m_FileWritingMode = b;}
	
    /**
     * Gets the currently selected contact's ID.
     * @return The ID of the contact. 
     */
    public String getSelectedContactID(){
    	return m_selectedContactID;
    }
    
    /**
     * Sets the currently selected Contact's ID.
     * @param id The new ID.
     * @return True on success, false if the ID had an invalid format.
     * @see SystemDate.validateTimeStamp()
     */
    public boolean setSelectedContactID(String id){
    	if (SystemDate.validateTimeStamp(id) || id.isEmpty()){
    		if (getDebugStatus())
    			System.out.println("AB: setSelectedContactID(): "+
    					"Selected: ["+id+"]");
    		m_selectedContactID = id;
    		return true;
    	}
    	else{
    		if (getDebugStatus())
    			System.err.println("AddressBook.java:\n" + 
    					" setSelectedContactID():\n" + 
    					"  Invalid ID.");
    		return false;
    	}
    		
    }

	/**
	 * Get the debug switch status.
	 * @return The switch status.
	 */
	public boolean getDebugStatus(){return m_debug;}	
	/**
	 * Sets the debug switch status.
	 * @param f The new switch status.
	 */
	public void setDebugStatus(boolean f){m_debug = f;}
	
	
	/**
	 * Sets the JList of Contacts.
	 * @param l The new list.
	 */
	private void setContactJList(JList l){m_contactJList = l;}
	/**
	 * Returns the JList from within this very same class.
	 * @return List with contacts.
	 */
	private JList getContactJList(){return m_contactJList;} 
	
	/**
	 * Gets the contact list.
	 * @return The list of contacts.
	 */
	private BasicEventList<Contact> getAddressBook(){return m_AddressBook;}
	

	/**
	 * Default constructor; populates the JList with contacts.
	 */
	public AddressBook(){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				setContactJList(new JList());
			}
		});
		this.populateList();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getContactJList().addListSelectionListener
					(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							int index = getContactJList().getSelectedIndex();
							String choice = getContactJList().getModel()
											.getElementAt(index).toString();
							if (getDebugStatus())
								System.out.println("Selected name: "+choice );
					        int which = getContactJList().getSelectedIndex();
					        if (getDebugStatus())
					        	System.out.println("Selected index: "+ which );
						}
					});
			}
		});
	}
	
	/**
	 * Constructs a panel with the JList.
	 * @return The constructed panel.
	 */
	public JScrollPane getJScrollPane(){
		JScrollPane p = new JScrollPane();
		//Useful only for the testing; irrelevant for the main program tab
		int WIDTH = 200, HEIGHT = 500;
		p.setPreferredSize( new Dimension(WIDTH,HEIGHT));
		
		p.setViewportView(getContactJList());
		p.updateUI();
		return p;
	}
	
	/**
	 * Gets the JList selected item's index.
	 * @return The index.
	 */
	public int getSelectedItemIndex(){
		return getContactJList().getSelectedIndex();
	}
	
	/**
	 * Populates the JList with contact names.
	 * @return True on success, false on failure.
	 */
	private boolean populateList (){
		final DefaultListModel dlm = new DefaultListModel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getContactJList().setModel(dlm);
			}
		});
		if (this.getSize()<=0){
			//dlm.addElement("NO CONTACTS AVAILABLE");
			return false;
		}
		
		for(int i=0; i< this.getSize();i++)
			dlm.addElement( this.getContact(i).getFirstName());
		//getAddressBook().setSelectedIndex(0);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getContactJList().setSelectionMode
					(ListSelectionModel.SINGLE_SELECTION);
			}
		});
		return true;
	}
	
	

	/**
	 * Refreshes the JList.
	 * @return True on success, false on failure.
	 */
	public boolean update(){
		if (populateList()){
			if (getDebugStatus())
				System.out.println("AB: update(): Contact JList updated.");
			return true;
		}
		if (getDebugStatus())
			System.out.println("AB: update(): Contact JList didn't update.");
		return false;
	}

	/**
	 * Adds a contact to the list.
	 * @param c The contact to be stored.
	 * @return True on success, false on failure.
	 */
	public boolean addContact(Contact c){
		if (!SystemDate.validateTimeStamp(c.getID())){
			if(getDebugStatus())
				System.err.println("AB: addContact(Contact): "+
						"Invalid ID format. Contact not added.");
			return false;
		}
		int NOT_FOUND = -1;
		if (findContactIndex(c.getID()) != NOT_FOUND){
			if(getDebugStatus())
			System.err.println("AB: addContact(Contact): "+
					"ID already exists. Contact not added.");
			return false;
		}
		if(!INNER_ADD_CALL)
			if (getSize() >= SIZE_LIMIT){
				JOptionPane.showMessageDialog(	null,
										"Sorry, the limit is 1000 Contacts.",
										"Cannot add Contact",
										JOptionPane.ERROR_MESSAGE);
				if(getDebugStatus())
					System.out.println("AB: addContact(Contact): " +
							"Popped size limit message." +
							"My size is: "+getSize());
				return false;
			} else {
				getAddressBook().getReadWriteLock().writeLock().lock();
				getAddressBook().add(c);
				getAddressBook().getReadWriteLock().writeLock().unlock();
				update();
				if(getDebugStatus())
					System.out.println("AB: addContact(): Added a contact: " +
							"["+c.getFirstName()+"].");
				if(getFileWritingMode()){
					if(getDebugStatus())
						System.out.println("AB: addContact(): " +
								"Writing it to file...");
					DigitalOrganiser.getContactFileManager().write(c);
			        if (getDebugStatus())
			        	System.out.println("AB: addContact(): " +
			        			"Finished write call.");
				}
				return true;
			}
		else 
			getAddressBook().add(c);
		if(getDebugStatus())
			System.out.println("AB: addContact(): Added a Contact without" +
					" writing to file: ["+c.getFirstName()+"]");
		return true;
		
	}
	
	/**
	 * Adds an contact based on a string array.
	 * @param eArray The array with the Contact values.
	 * @return True on success, false on failure.
	 */
	public boolean addContact(String[] eArray){
		if (getSize() >= SIZE_LIMIT){
			if(getDebugStatus())
				System.err.println("AB: addContact(String[]): "+
						"Sorry, the limit is " + SIZE_LIMIT +" contacts. "+
						"My current size is: "+getSize()+". "+
						"Cannot add Contact.");
			return false;
		}
		Contact c = new Contact();
		if (!c.setFromStringArray(eArray))
			return false;
		INNER_ADD_CALL = true;
		if(!(addContact(c))){
			INNER_ADD_CALL = false;
			return false;
		}
		INNER_ADD_CALL = false;
		return true;
	}
	
	/**
     * Updates a contact in the list based on the index in the list.
     * @param i The index of the Contact to be updated.
     * @param contact The updated contact.
     * @return True on success, false on failure.
     */
	public boolean editContact(int i, Contact contact){
		if (i >= 0 && i < getSize()){
			if (getDebugStatus())
				System.out.println("AB: editContact(): " +
						"Editing the Contact at index: "+i);
			getAddressBook().getReadWriteLock().writeLock().lock();
			getAddressBook().add(i, contact);
			removeContact(i+1);
			getAddressBook().getReadWriteLock().writeLock().unlock();
			if (getDebugStatus())
				System.out.println("AB: editContact(): Replaced the Contact.");
			if(getFileWritingMode()){
				if(getDebugStatus())
					System.out.println("AB: editContact(): " +
							"ReWriting the file...");
				DigitalOrganiser.getContactFileManager().write();
		        if (getDebugStatus())
		        	System.out.println("AB: editContact(): " +
		        			"Finished rewrite call.");
			}
			this.update();
			return true;
		} else {
			if (getDebugStatus())
				System.out.println("AB: editContact(): "+
						"Cannot edit. "+
						"Bad index: [" + i + "]");
			return false;
		}
    }
	
	/**
     * Updates an contact in the list based on the ID.
     * @param id The ID of the contact to be updated.
     * @param c2 The new/updated contact.
     * @return True on success, false on failure.
     * @see editContact(int,Contact)
     */
	public boolean editContact(String id, Contact c2){
		int index = findContactIndex(id);
		if (getDebugStatus())
			System.out.println("AB: editContact(ID,Contact): " +
					"A call to editContact() is to follow after this.");
		if (editContact(index, c2))
			return true;
    	
		return false;
    }
	
	/**
	 * Finds the list index of a contact based on the ID.
	 * @param id The ID of the contact.
	 * @return The index or -1 if it wasn't found.
	 */
	public int findContactIndex(String id){
		for (int i = 0; i < getSize() ; i++){
			Contact c = getContact(i);
			
			if (getDebugStatus())
				System.out.println("AB: findContact(): "+
						"Comparing: [" + c.getID() + "] vs [" + id + "]");

			if (c.getID().equals(id)){
				if (getDebugStatus())
					System.out.println("AB: findContact(): "+
							"Found: [" + c.getID() + "] at index [" + i + "]");
				return i;
			}
		}
		if (getDebugStatus())
			System.out.println("AB: findContactIndex(): "+
								"Could not find ID : [" + id + "]");
		return -1;
	}
	
    /**
     * Gets a contact from the list based on the index.
     * @param index The index of the desired Contact.
     * @return The contact or null if unsuccessful.
     */
    public Contact getContact(int index){
        try{
            getAddressBook().get(index);
        } catch (Exception e){
            System.err.println("AB: getContact(): Failed to get the contact.");
            return null;
        }
        //Debug message checking what Contact type it got back
        //System.out.println("Returning a "+m_AddressBook.get(index).getContactType());
        return (Contact) getAddressBook().get(index);
    }
    
    /** 
     * Gets a contact based on a given id.
     * @param id The ID of the contact to be returned.
     * @return The contact or null if no matching Contact was found.
     * @see getContact(int)
     */
    public Contact getContact(String id){
    	if (getDebugStatus())
			System.out.println("AB: getContact(String): " +
					"A call to getContact(int) is to follow after this.");
    	return getContact(findContactIndex(id));    	
    }
    
    /**
     * Gets the size of the list.
     * @return Number of contacts stored.
     */
    public int getSize(){return getAddressBook().size();}
    
    /**
     * Removes a contact based on position in the list.
     * @param index Index of the contact to be removed.
     */
    public boolean removeContact(int index){
    	if (getDebugStatus())
    		System.out.println("AB: removeContact(): Passed index: " +
    				"["+index+"].");
    	if (index < 0 || index > getSize()) return false;
		getAddressBook().getReadWriteLock().writeLock().lock();
		getAddressBook().remove(index);
		getAddressBook().getReadWriteLock().writeLock().unlock();

        this.update();
        if (getDebugStatus())
        	System.out.println("AB: removeContact(): Removed Contact at index: " +
        			"["+index+"].");
		if(getFileWritingMode()){
			if(getDebugStatus())
				System.out.println("AB: removeContact(): " +
						"ReWriting the file...");
			DigitalOrganiser.getContactFileManager().write();
	        if (getDebugStatus())
	        	System.out.println("AB: removeContact(): " +
	        			"Finished rewrite call.");
		}
        return true;
    }
    
    /**
     * Removes a contact based on the ID.
     * @param id The ID of the contact to be removed.
     */
    public boolean removeContact(String id){
    	if (getDebugStatus())
			System.out.println("AB: removeContact(String): "+
					"A call to removeContact(int) is to follow after this.");
    	return (removeContact(findContactIndex(id)));
    }
    
    /**
     * Clears the list and updates the JList. 
     * @return True on success, false on failure.
     */
    public boolean clear(){
    	getAddressBook().clear();
    	this.update();
    	
    	return true;
    }
    

    /**
	 * Disables the JList.
	 * @param b True for disabling, false for enabling.
	 * @return True on success, false on failure.
	 */
	public boolean disable(boolean b){
		getContactJList().setEnabled(!b);
		update();
		return true;
	}
	
	/**
	 * Creates a panel with a search field, and the contact list table.
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
					System.out.println("AB: getSearchPanel(): "+
							"Search field caret changed.");
				setSearchString(searchField.getText());				
			}
		});
        searchField.requestFocus();
        String COL, ROW;
        COL = "2"; ROW = "2";
		jp.add(searchField, COL+", "+ROW+", fill, default");
		JLabel searchFieldLabel = new JLabel("Search");
		searchFieldLabel.setEnabled(false);
		COL = "2"; ROW = "3";
		jp.add(searchFieldLabel, COL+", "+ROW+", center, default");
	
		TextFilterator<Contact> tf = new MyTextFilterator();		
		TextComponentMatcherEditor<Contact> matchEditor = 
				new TextComponentMatcherEditor<Contact>( searchField , tf );		
		FilterList<Contact> filteredContacts = 
				new FilterList<Contact>( getAddressBook() , matchEditor ); 		
		ContactTableFormat columns = new ContactTableFormat();		
	    DefaultEventTableModel<Contact> tableModel = 
	    		new DefaultEventTableModel<Contact>( filteredContacts , columns );

	    setSearchTable(new JTable(tableModel));
        formatTable();  
        COL = "2"; ROW = "5";
        String COLSPAN = "5", ROWSPAN = "1";
        String layoutSpec = COL+","+ROW+","+COLSPAN+","+ROWSPAN+",fill,fill";
        jp.add(new JScrollPane(getSearchTable()), layoutSpec);
		return jp;
	}
	
	/**
	 * Formats the search table a bit. Adds a selection listener.
	 */
	private void formatTable(){
		if (getSearchTable() == null) return;
	    getSearchTable().getColumnModel().setColumnSelectionAllowed(false);
	    getSearchTable().setRowSelectionAllowed(true);
	    getSearchTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    getSearchTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		SelectionListener listener = new SelectionListener(getSearchTable());
		
		getSearchTable().getSelectionModel().addListSelectionListener(listener);
		getSearchTable().getColumnModel().getSelectionModel()
		    .addListSelectionListener(listener);		    
	}
	
	/**
	 * @brief Listener for the row selection in the table.
	 * 
	 * Sets selected contact ID in AddressBook once a row is selected.<br/>
	 * Un-sets it on row de-selection or when the table changes.
	 * @author Codrin Morhan; Yan Sun
	 */
	public class SelectionListener implements ListSelectionListener {
        JTable table;

        /**
         * Default constructor.<br/>
         * It is necessary to keep the table since it is not possible 
         * to determine the table from the event's source.
         * @param table The table passed.
         */
        SelectionListener(JTable table) {this.table = table;}
        
        /**
         * Implements the interface method to detect row selections.<br/>
         * When a row is selected it sets the currently selected ID to the one 
         * of the contact in the selected row.
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
                			System.out.println("AB: SelectionListener():"+
                					"Table probably changed.");
                		id = "";
                	}
    				if(getDebugStatus())
    					System.out.println("Selected ID: ["+id+"]");
                } else 
                	id = "";
                setSelectedContactID(id);
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
	 * @author Codrin Morhan; Yan Sun
	 */
	public class MyTextFilterator implements TextFilterator<Contact>{

		@Override
		/**
		 * Overrides the method. Specifies which fields are sought.
		 * @param baseList A list that the implementor shall add their filter strings to.
		 * @param element The object to extract the filter strings from. 
		 */
		public void getFilterStrings(List<String> baseList, Contact element) {
		    baseList.add(element.getFirstName());
			baseList.add(element.getLastName());
			baseList.add(element.getNickName());
			baseList.add(element.getAddress());
			baseList.add(element.getPostCode());			
			baseList.add(element.getWorkNumber());
			baseList.add(element.getMobileNumber());
			baseList.add(element.getFaxNumber());
			baseList.add(element.getPersonalEmail());
			baseList.add(element.getOtherEmail());
			baseList.add(element.getWorkEmail());
			baseList.add(element.getUrl());
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
	 * Prints out the Contact list to the console.<br/>
	 * Mainly used for debugging.
	 */
	public void iterateAddressBook(){
		if(getDebugStatus())
			System.out.println("AB: iterateAddressBook:\n"+
		"--------------------------------------------------------------------");
		for (int i = 0; i < getSize(); i++){
			Contact c = getAddressBook().get(i);
			System.out.print("\nIndex ["+		i+"] ");
			System.out.print(" ID ["+			c.getID()+"]");
			System.out.print(" FirstName ["+	c.getFirstName()+"]");
			System.out.print(" LastName ["+		c.getLastName()+"]");
			System.out.print(" NickName ["+		c.getNickName()+"]");
			System.out.print(" Address ["+		c.getAddress()+"]");
			System.out.print(" PostCode ["+		c.getPostCode()+"]");
			System.out.print(" HomeNumber ["+	c.getHomeNumber()+"]");
			System.out.print(" MobileNumber ["+	c.getMobileNumber()+"]");
			System.out.print(" WorkNumber ["+	c.getWorkNumber()+"]");
			System.out.print(" FaxNumber ["+	c.getFaxNumber()+"]");
			System.out.print(" PersonalEmail ["+c.getPersonalEmail()+"]");
			System.out.print(" WorkEmail ["+	c.getWorkEmail()+"]");
			System.out.print(" OtherEmail ["+	c.getOtherEmail()+"]");
			System.out.print(" URL ["+			c.getUrl()+"]");
		}
		System.out.println("\n"+
		"--------------------------------------------------------------------");
	}
	
	/** The list of contacts. */
    private BasicEventList<Contact> m_AddressBook = new BasicEventList<Contact>();
    /** Debug switch */
    private boolean m_debug = false;
    /** The JList widget. */
	private JList m_contactJList;
	/** Tracks if the addContact(Contact) was called by the add(String[]). */
	private static boolean INNER_ADD_CALL = false;
	/** The limit of the Contact list size. */
	public static final int SIZE_LIMIT = 1000;
	
	/** 
	 * Declares if the class is to be used in junction with the file package.<br/>
	 * It is used to differentiate the class used by DigitalOrganiser from just another class instance.<br/>
	 * The file writing routines get called or not based on this switch. 
	 */
	private boolean m_FileWritingMode = false;
	
    /** 
     * Tracks the selected Contact's ID.
     * Used for linking UI actions, intended for a Contact, to the actual Contact object. 
     */
    private String m_selectedContactID = "";
    
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
     * Main method performs tests upon ContactsList.
     * @param args Arguments will be ignored.
     */
    public static void main(String[] args){         
        
    	int NUMBER_OF_CONTACTS = 150;
    	/** @test Adding 150 contacts; adding another one manually; checking the output. */
        AddressBook ab = TestData.getFilledAddressBook(NUMBER_OF_CONTACTS);
        ab.setDebugStatus(true);
        System.out.println("Size before: "+ab.getSize());
        ab.addContact(new Contact());
        System.out.println("Size after: "+ab.getSize());
        int FIRST_CONTACT = 0;
        System.out.println(ab.getContact(FIRST_CONTACT).getFirstName());
        
        DigitalOrganiser.setAddressBook(ab);
        DigitalOrganiser.getAddressBook().setDebugStatus(true);
        
        /** @test Displaying the search panel; testing search functionality. */
        JFrame jf = new JFrame ();
        
        jf.setTitle("ContactsList: searchPanel");


        jf.add(DigitalOrganiser.getAddressBook().getSearchPanel());
        jf.pack();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        
        /** @test Displaying the address book as a JList. */
        //jf.setTitle( "ContactsList as JList");
        //jf.add(el.getJScrollPane());

    }
}