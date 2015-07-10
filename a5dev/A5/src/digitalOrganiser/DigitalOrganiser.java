/**
 * \file DigitalOrganiser.java
 * \author Simon Maling
 * \date 23/02/12
 * \brief Contains the digital organiser class, the main class of the program.
 */

/**
 * @package digitalOrganiser
 * @brief The main package. Contains the class that runs the whole program.
 */
package digitalOrganiser;

import java.awt.EventQueue;

import testingFramework.TestData;
import ui.UserInterface;
import views.CalendarTableModel;
import views.DayView;
import views.MonthView;
import views.WeekView;
import views.YearView;
import addressBook.AddressBook;
import events.EventList;
import file.ContactFileManager;
import file.EventFileManager;

/**
 * @brief The main program.
 * 
 * This is the main class.<br/>
 * It stores the views,events, address book and file objects that are used throughout the program.<br/>
 * It initialises all the objects and provides access to them.
 * @author Simon Maling
 */
public class DigitalOrganiser {
	
	/**
	 * This is the accessor for the address book object.
	 * @return It returns the address book object.
	 */
	public static AddressBook getAddressBook(){
		return book;
	}
	
	/**
	 * This is the accessor for the event list object.
	 * @return It returns the event list object.
	 */
	public static EventList getEventList(){
		return list;
	}
	
	/**
	 * This is the accessor for the event file manager object.
	 * @return It returns the EventFileManager object.
	 */
	public static EventFileManager getEventFileManager(){
		return eventManager;
	}
	
	/**
	 * This is the accessor for the contact file manager object.
	 * @return It returns the ContactFileManager object.
	 */
	public static ContactFileManager getContactFileManager(){
		return contactManager;
	}	
	
	/**
     * This is the accessor method for the user interface object.
     * @return It returns the user interface object.
     */
    public static UserInterface getUserInterface(){
            return ui;
    }
    
    /**
     * Gets the day view object.
     * @return The object for the day view.
     */
    public static DayView getDayView(){
            return dv;
    }
    
    /**
     * Gets the month view object.
     * @return The object for the month view.
     */
    public static MonthView getMonthView(){
            return mv;
    }
    
    /**
     * Gets the week view object.
     * @return The object for the week view.
     */
    public static WeekView getWeekView(){
            return wv;
    }
    
    /**
     * Gets the year view object.
     * @return The object for the year view.
     */
    public static YearView getYearView(){
            return yv;
    }
	
    /**
	 * This is the accessor mutator method for the address book object.
	 * @param adbook This is the new address book object.
	 * @return Returns true if executed correctly.
	 */
	public static boolean setAddressBook(AddressBook adbook){
		book = adbook;
		return true;
	}
	
	/**
	 * This is the accessor mutator method for the event list object.
	 * @param thelist This is the new event list object.
	 * @return Returns true if executed correctly.
	 */
	public static boolean setEventList(EventList thelist){
		list = thelist;
		return true;
	}
	
	/**
	 * This is the accessor mutator method for the eventFileManager object.
	 * @param em This is the new EventFileManager object.
	 * @return Returns true if executed correctly.
	 */
	public static boolean setEventFileManager(EventFileManager em){
		eventManager = em;
		return true;
	}
	
	/**
	 * This is the accessor mutator method for the contactFileManager object.
	 * @param cm This is the new ContactFileManager object.
	 * @return Returns true if executed correctly.
	 */
	public static boolean setContactFileManager(ContactFileManager cm){
		contactManager = cm;
		return true;
	}
	
	/**
	 * This is accessor mutator method for day view object.
	 * @param d The day view object.
	 * @return True if executed correctly.
	 */
	public static boolean setDailyView(DayView d){
		dv = d;
		return true;
	}
	
	/**
	 * This is accessor mutator method for month view object.
	 * @param m The month view object.
	 * @return True if executed correctly.
	 */
	public static boolean setMonthView(MonthView m){
		mv = m;
		return true;
	}
	
	/**
	 * This is accessor mutator method for week view object.
	 * @param w The week view object.
	 * @return True if executed correctly.
	 */
	public static boolean setWeekView(WeekView w){
		wv = w;
		return true;
	}
	
	/**
	 * This is accessor mutator method for year view object.
	 * @param y The year view object.
	 * @return True if executed correctly.
	 */
	public static boolean setYearView(YearView y){
		yv = y;
		return true;
	}
	
	/**
     * This is the accessor mutator method for User Interface,
     * @param u What the user interface object will be set to.
     * @return True if executed correctly.
     */
    public static boolean setUserInterface(UserInterface u){
            ui = u;
            return true;
    }
	/**
	 * This method initialises the application. This invovles:
	 * loading from the files, and if no objects are selected creating them,
	 * showing the user frame which will get the user to enter there username and password
	 * and showing the calendar application GUI.
	 * @return true if executed correctly.
	 */
	public static boolean initialise(){
		UserInterface.setUpLNF();
		initialiseStorage();
		initialiseViews();

    	EventQueue.invokeLater(new Runnable() {
			
    		/**
    		 * Overrides the run method to set up the user interface and switch to day view.
    		 */
			@Override
			public void run() {
				/*
				 * Creates a user interface object.
				 * To be done last as instantiation of UserInterface pops up the UI.
				 */
				setUserInterface(new UserInterface());
				getUserInterface().setViewPanel(getDayView());
			}
    	});
		
		return true;
	}
	
	/**
	 * This method initialises the storage for the digital organiser.
	 * Including initialising the addressbook,contactlist and addressbooklist,
	 * also initialising the file managers, and loading from the files.
	 */
	public static void initialiseStorage(){
		//Initialising the address book, event list and address
		//book list.
		setAddressBook(new AddressBook());
		setEventList(new EventList());
		
		getEventList().setFileWritingMode(true);
		
		getAddressBook().setFileWritingMode(true);
		
		//Creating the file manager objects.
		setEventFileManager(new EventFileManager());
		setContactFileManager(new ContactFileManager());
		
		//Loading from the default files.
		getEventFileManager().load();
		getContactFileManager().load();
	}
	
	/**
	 * This method initialises the views of the program.
	 * It initialises the month, week, day and year views.
	 */
	public static void initialiseViews(){
		//Initialise week, month, day and year views.
		setDailyView(new DayView());
		setWeekView(new WeekView());
		setMonthView(new MonthView());
		setYearView(new YearView());
		//Needed to get the View to create the display
		// this isn't handled by the default constructor.
		getYearView().createDisplay();
		
	}
	
	/**
	 * Loads events into the views.
	 * @return True on success, false on failure.
	 */
	private static boolean loadViews(){
		if (getEventList() == null){
			System.out.println("DO: loadViews(): EL is null.");
			setEventList(new EventList());
		}
		try{
			getDayView().setToDisplay(getEventList());			
			getWeekView().setToDisplay(getEventList());
			getMonthView().setToDisplay(getEventList());
			getYearView().setEventList(getEventList());
			
			CalendarTableModel ctm = (CalendarTableModel) 
					getYearView().getToDisplay().getModel();	
			ctm.refreshData();
		} catch (Exception ex){
			System.err.println("DO: loadViews(): Could not tell the views " +
					"to make use of the EL. " +
					"Check that all views are initialised.");
			return false;
		}
	
		return true;
	}
	
	/**
	 * Updates the views.
	 * @return True on success, false on failure.
	 */
	public static boolean updateViews(){
		loadViews();

		try{
			getDayView().updateDisplay();
			getWeekView().updateDisplay();
			getMonthView().updateDisplay();
		} catch (Exception ex){
			System.err.println("DO: updateViews(): Could not tell the views " +
					"to update their displays. " +
					"Check that all views are initialised.");
			return false;
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try{getUserInterface().getViewPanel().updateUI();}
				catch(Exception e){
					System.err.println("DO: Couldn't update UI panel. " +
							"This is normal if DO is used for testing " +
							"without UI.");
				}
					
			}
		});
		return true;
	}
	

	
	//These are the object declarations for the digital organiser. They are all static as
	//the class has no instance.
	/** Global event list. */
	private static EventList list;
	/** Global address book. */
	private static AddressBook book;
	/** Global event file. */
	private static EventFileManager eventManager;
	/** Global contact file. */
	private static ContactFileManager contactManager;
	/** Global variable for the user interface. */
	private static UserInterface ui;
	/** Global variable holding the day view to display. */
	private static DayView dv;
	/** Global variable holding the month view to display. */
	private static MonthView mv;
	/** Global variable holding the week view to display. */
	private static WeekView wv;
	/** Global variable holding the year view to display. */
	private static YearView yv;

	/**
	 * The main method all this does is calls the initialise method which initialises the program.
	 * @param args0 This is used for inputing a file to the program.
	 */
	public static void main(String args0[]){
		initialise();
		
		
		/** @test generating 1000 events. */
		/*
		DigitalOrganiser.setEventList(TestData.getFilledEventList(1000));
		System.out.println("No of events: "+
				DigitalOrganiser.getEventList().getSize());
		//td.enumerateEvents();
		/**/
		/** @test saving 1000 events. */
		/*
		//forcing to write them to file
		DigitalOrganiser.getEventFileManager().write();
		updateViews();
		/**/
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				DigitalOrganiser.getEventList().update();
				updateViews();
				
			}
		});
	}
}

