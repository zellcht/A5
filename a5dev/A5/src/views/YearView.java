/**
 * @file YearView.java
 * @author Samuel Jenkins
 * @date 23/03/2012
 * @brief Displays a Calendar with its events over the period of a year.
 */
package views;

import java.awt.EventQueue;
import javax.swing.*;
import ui.UserInterface;
import hirondelle.date4j.DateTime;
import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser;
import events.*;

/**
 * 
 * YearView is a class that behaves as a JPanel for the express purpose of holding
 * and managing the components it has to display. The components it displays are 
 * those necessary to show a Calendar over a year long period of time.
 * 
 * The YearView handles the layout of the components and uses other components to handle
 * the data, in this case the notable component being a JTable with specialised
 * CellRenderers, TableModels and a MouseListener for the Table to handle the User's
 * Interactions with the JTable.
 * 
 * A YearView is constructed through the Default Constructor and is dependent on the
 * DigitalOrganiser class in retrieving the EventList it uses to display next to each date
 * the number of events held on that date. It also relies on the DigitalOrganiser class
 * for some of the User Interactive responsibilities the YearView has.
 * 
 * If you are constructing a YearView and have a specific list you wish for it to use for
 * the Calendar, then you are recommended to call the YearView(EventList) constructor.
 * 
 * Both constructors rely on the SystemDate and DateTime Classes to handle the dates and
 * acquire the default date to use to determine the year to display the Calendar over.
 * 
 * If you wish to change the year the YearView displays through code then you must use
 * the createDisplay(DateTime) method to change the View, this method will also update the
 * View in the User Interface.
 * 
 * @author Samuel Jenkins
 * @brief Displays a Calendar with its events over the period of a year.
 *
 */
public class YearView extends View{
	
	/**
     * Gets the Object to display.
     * @return Object to be displayed
     */
	public JTable getToDisplay(){//Begin GetToDisplay()
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: GetToDisplay()");
		}
		if(super.getToDisplay() instanceof JTable){
			return (JTable) super.getToDisplay();
		}else{
			System.out.println("Could not load a JTable Array,"+
							   "creating a default one");
			DateTime sd = SystemDate.getCurrentDate();
			return this.createTable(sd,getEventList());
		}
	}//End GetToDisplay()

	/**
     * Sets the Object for the View to display.
     * @param oSetTo the Object to be contained and displayed by the View.
     * @return true on success, false on failure.
     */
	public boolean setToDisplay(JTable oSetTo){//Begin SetToDisplay(Object)
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: SetToDisplay(object)");
		}
		return super.setToDisplay(oSetTo);
	}//End SetToDisplay(Object)
	
	/**
	 * Accessor method to the m_EventList field.
	 * @return The EventList displayed in the Calendar the YearView displays
	 */
	public EventList getEventList() {
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: GetEventList()");
		}
		return m_EventList;
	}
	
	/**
	 * Mutator method for the m_Eventlist field.
	 * @param el the EventList the YearView will display in the Calendar
	 * @return true on execution
	 */
	public boolean setEventList(EventList el) {
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: SetEventList(EventList)");
		}
		m_EventList = el;
		return true;
	}
	
	/**
     * Constructs a default YearView.
     * @return a View Object.
     */
	public YearView(){//Begin View()
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: YearView()");
		}
		this.setLayout(new SpringLayout());
		try{
			EventList el = DigitalOrganiser.getEventList();
			if(el==null){
				el = new EventList();
			}
			this.setEventList(el);
		}catch(Exception e){
			this.setEventList(new EventList());
		}
		this.setToDisplay(createTable(SystemDate.getCurrentDate(),
				   					   this.getEventList()));
	}//End View()
	
	/**
     * Constructs a View with an Object to contain and show.
	 * @param oToShow the Object to be shown by the View.
     * @return a View Object.
     */
	public YearView(EventList oToShow){//Begin View(Object)
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: View(JTable)");
		}
		this.setLayout(new SpringLayout());
		this.setEventList(oToShow);
		this.setToDisplay(createTable(SystemDate.getCurrentDate(),
									  oToShow));
	}//End View(Object)
	
	/**
     * Creates the default format to show an Object contained by the YearView.
     * @return a View Object with its contents displayed.
     */
	public View createDisplay(){//Begin createDisplay()
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: createDisplay()");
		}
		JTable oTable;
		DateTime date = SystemDate.getCurrentDate();
		oTable = this.createTable(date,this.getEventList());
		this.setToDisplay(oTable);
		if(getComponentCount() > 0){
	  		this.removeAll();
	  	}
		layoutToDisplay(date);
		this.repaint();
		return this;
	}//End  createDisplay()
	
	/**
     * Creates the default format to show a specific JTable contained by the YearView.
	 * @param oDate the date within the year range to display.
     * @return a View Object with oTable displayed in the format desired.
     */
	public View createDisplay(DateTime oDate){//Begin createDisplay(Object)
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: createDisplay(DateTime)");
		}
		JTable oTable;
		oTable = this.createTable(oDate,this.getEventList());
		this.setToDisplay(oTable);
		if(getComponentCount() > 0){
			this.removeAll();
		}
		layoutToDisplay(oDate);
		this.repaint();
		return this;
	}//End createDisplay(Object)
	
	/**
	 * Initialises the JTables the YearView will display.
	 * @param date the Date which will give the year the YearView will display
	 * @param el The Eventlist that will hold the events to be
	 * @return an Array of JTables for the YearView to Display
	 */
	private JTable createTable(DateTime date, EventList el){
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: createTable(DateTime,"+
							   "Eventlist)");
		}
		CalendarTableModel ctm = new CalendarTableModel(this,date);
		ctm.setEventData(el);
		ctm.refreshData(date);
		JTable result = new JTable(ctm);
		TableGUIHandler handler = new TableGUIHandler();
		result.setDefaultRenderer(Object.class, new CalendarCellRenderer());
		result.getTableHeader().setResizingAllowed(false);
		result.getTableHeader().setReorderingAllowed(false);
		result.setPreferredScrollableViewportSize(result.getPreferredSize());
		result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		result.addMouseListener(handler);
		result.getTableHeader().addMouseListener(handler);
		return result;
	}
	
	/**
	 * Sets up all the components that the View will have, including the object
	 * to Display.
	 * @param date the Year the YearView will display.
	 * @return true on execution.
	 */
	private boolean layoutToDisplay(final DateTime date){
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: layoutToDisplay()");
		}
		final YearViewGUIHandler handler = new YearViewGUIHandler(this);
		EventQueue.invokeLater(new YearViewRunnable(this, date, handler));
		return true;
	}
	
	/**
     * Shows the YearView.
	 * @return  true on success, false on failure.
     */
	public boolean Show(){//Begin Show
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: Show()");
		}
		try{
			this.createDisplay();
			return true;
		}catch(Throwable t){
			System.out.println(t.getMessage());
			return false;
		}
	}//End Show
	
	/**
     * Shows/hides the YearView.
	 * @param bShow sets whether to show or hide the view, true being to show, false being to hide.
	 * @return true on success, false on failure.
     */
	public boolean Show(Boolean bShow){//Begin Show(boolean)
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: Show(boolean)");
		}
		try{
			if(bShow){
				this.createDisplay();
			}else{
				this.removeAll(); //JPanel method to remove everything from the View, to hide it
			}
			return true;
		}catch(Throwable t){
			System.out.println(t.getMessage());
			return false;
		}
	}//End Show(boolean)
	
	/**
     * Tests the class.
	 * @param args the Command Line arguments
     */
	public static void main(String[] args){//Begin main(String[])
		boolean test = false;
		if(test){
			System.out.println("Called YearView :: main(String[])");
		}
		System.out.println("Creating frames");
		JFrame frame1 = new JFrame();
		JFrame frame2 = new JFrame();
		JFrame frame3 = new JFrame();
		JFrame frame4 = new JFrame();
		System.out.println("making YearViews and adding them to frames");
		YearView y = new YearView(), y1 = new YearView(), y2 = new YearView(),
				 y3;
		frame1.add(y);
		System.out.println("creating display, reseting View and adding"+
						   " it to frame2");
		DateTime d = new DateTime("2010-03-20");
		y1.createDisplay(d);
		frame2.add(y1);
		System.out.println("creating an eventlist and adding it to frame3");
		EventList el = new EventList();
		Event e = new Event();
		e.setTitle("test");
		e.setStartingDateTime("2012-01-20 00:00:00");
		el.addEvent(e);
		y2.setEventList(el);
		System.out.println("Constructing y3 with an eventlist");
		y3 = new YearView(el);
		d = new DateTime("2011-03-20");
		y2.createDisplay(d);
		frame3.add(y2);
		frame4.add(y3);
		System.out.println("showing the frames");
		frame1.pack();
		frame1.setVisible(true);
		frame1.setTitle("frame1");
		frame2.pack();
		frame2.setVisible(true);
		frame2.setTitle("frame2");
		frame3.pack();
		frame3.setVisible(true);
		frame3.setTitle("frame3");
		frame4.pack();
		frame4.setVisible(true);
		frame4.setTitle("frame4");
		System.out.println("Creating the display for frame1");
		y.createDisplay();
	}//End main(String[])
	
	
	/** The gaps to be between components in a YearView */
	public static final int COMPONENT_GAP = 200;
	
	/** Constants used in the createTables method to define multiples of
	    component gaps */
	public static final int DIVISOR = 10, HALF = 2, ELEVENTH = 11, THIRD = 3;
	
	/** The EventList that holds all the events the Calendar must display */
	private EventList m_EventList;

}
