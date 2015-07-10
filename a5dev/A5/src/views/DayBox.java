/**
 * @file DayBox.java
 * @author Lloyd Woodroffe
 * @date 21st March 2012
 * @brief Contains the DayBox class.
 */

/**
 * @package views
 * @brief contains all the calendar views.
 */
package views;

import hirondelle.date4j.DateTime;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListClickListener.Button;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser;

import events.Event;
import events.EventController;
import events.EventList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ui.NavigationPanel;
import ui.NavigationPanel.SLClickListener;

/**
 * Shows the events in the period of a day.<br/>
 * It also features two labels, one at the top and one at the bottom, 
 * which could be set to display relevant information, like day number. 
 * @author Lloyd Woodroffe
 * @brief A class to contain the events within a day.
 */
@SuppressWarnings("serial")
public class DayBox extends JPanel{
	
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
	 * Sets the top label of the box to what is required 
	 * @param l A JLabel to be set. 
	 */
	public void setTopLabel(JLabel l){
		m_TopLabel = l;
		m_TopLabel.updateUI();
	}
	
	/**
	 * Gets the value of the top Label 
	 * @return m_TopLabel
	 */
	public JLabel getTopLabel(){
		return m_TopLabel;
	}
	
	/**
	 * Sets the value of the boxes bottom label. 
	 * @param l The JLabel of the bottom label. 
	 */
	public void setBottomLabel(JLabel l){
		m_BottomLabel = l;
	}
	
	/**
	 * Gets the value of the bottom label 
	 * @return m_BottomLabel 
	 */
	public JLabel getBottomLabel(){
		return m_BottomLabel;
	}
	
	/**
	 * Sets the events displayed within the JList
	 * @param jl the JList
	 */
	public void setEventJList(JList jl){
		m_EventJList = jl;
	}
	
	/**
	 * gets the values within the JList
	 * @return m_EventJList
	 */
	public JList getEventJList(){
		return m_EventJList;
	}
	
	/**
	 * sets the values of the events 
	 * @param el The EventList of events 
	 */
	public void setEventList(EventList el){
		//System.out.println("DayBox: "+getClass().getName()+
			//": I was set to a EL of size: "+el.getSize());
		this.m_EventList = el;
	}
	
	/**
	 * gets the values of the events
	 * @return m_EventList
	 */
	public EventList getEventList(){
		//System.out.println("DayBox: "+getClass().getName()+
		 //": I was told to return an EL, which now is of size: "+
			//m_EventList.getSize());
		return this.m_EventList;
	}
	
	/**
	 * Sets the text displayed within the top label of the box. 
	 * @param s the value which the text is set too. 
	 */
	public void setTopLabelText(String s){
		m_TopLabel.setText(s);
	}
	/**
	 * gets the text displayed within the top label of the box.
	 * @return the text of the top label
	 */
	public String getTopLabelText(){
		return m_TopLabel.getText();
	}
	
	/**
	 * Sets the text displayed within the bottom label of the box.
	 * @param s the value which the text is set too. 
	 */
	public void setBottomLabelText(String s){
		m_BottomLabel.setText(s);
	}
	
	/**
	 * gets the text displayed within the top label of the box.
	 * @return the text of the top label
	 */
	public String getBottomLabelText(){
		return m_BottomLabel.getText();
	}
	
	/**
	 * gets the Date and time 
	 * @return m_Date
	 */
	public DateTime getDate(){
		return m_Date;
	}
	
	/**
	 * Sets the date
	 * @param dt The date of which to set. 
	 */
	public void setDate(DateTime dt){
		if (dt == null) return;
		//m_Date = new DateTime(dt.format(SystemDate.DATE_FORMAT));
		m_Date = dt;
		populateDayBox();
		
	}
	
	/**
	 * populateDayBox() fills the DayBox with the Event titles. 
	 */
	private void populateDayBox(){
		trimEventList();
		//System.out.println("Someone called me: "+getClass().getName());
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				DefaultListModel m = new DefaultListModel();

				for(int i = 0; i < getEventList().getSize(); i++){
					Event e = getEventList().getEvent(i);
					if(!e.getStart().isEmpty()){
						m.addElement(e.getTitle());
					}	        
				}
				getEventJList().setModel(m);
				getEventJList().updateUI();  
			}
		};
		EventQueue.invokeLater(runnable);               
	}
	
	/**
	 * Code to be executed if focus was lost. 
	 */
    private void eventJListFocusLost() {
        getEventJList().clearSelection();
    }
    
    /**
     * A mouse listener to find the event that has been clicked on. 
     * @param ma the event that has been clicked on. 
     */
    public void setEventJListMouseListener(MouseAdapter ma){
    	getEventJList().addMouseListener(ma);
    }
    
    /**
     * The class which gets and displays the event that has been clicked. 
     * @brief Custom listener for the DayBox JList.
     */
  	public class JListMouseAdapter extends MouseAdapter{
        public void mouseClicked(MouseEvent evt) {
        	int index = getEventJList().getSelectedIndex();
        	if (index < 0 || index > getEventList().getSize())
        		return;
        	final Event e = getEventList().getEvent(index);
        	if (getDebugStatus())
        		System.out.println("DB: JListMouseAdapter: Index ["+index+"] " +
        					   "clicked. Setting ID ["+e.getID()+"]");
        	if (e != null)
        		EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
		        		DigitalOrganiser
		        		.getEventList()
		        		.setSelectedEventID(e.getID());
					}
				});

        	//System.out.println(e.getID());
        	
        	int numberOfClicks = 2;
        	if (evt.getClickCount()==numberOfClicks && e != null){        		
	            EventController ec = new EventController();
	            ec.setEvent(e);
	            ec.setEditMode(true);
	            ec.showEventWindow();
        	}
        }
    }
  	
  	/**
  	 * Calls up the function with code to perform upon losing focus.
  	 * @brief Custom focus listener for the DayBox JList.
  	 */
  	public class EventJListFocusAdapter extends FocusAdapter {
  		/**
  		 * Hold what to do on focus lost.
  		 * @param evt The event.
  		 */
  		 public void focusLost(FocusEvent evt) {
             eventJListFocusLost();
         }
    }	
    
  	/**
  	 * Fills non-days with a grey fill. 
  	 */
    public void empty(){
    	getEventJList().setBackground(Color.LIGHT_GRAY);
    	setBackground(Color.LIGHT_GRAY);
    	setTopLabelText(" ");
    	getTopLabel().setOpaque(false);
    }
	
	/**
	 * Creates the panel.
	 */
    public DayBox() {
		Runnable runThis = new Runnable(){
			public void run(){
				setLayout(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("fill:default:grow"),},
						new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:default:grow"),
						FormFactory.DEFAULT_ROWSPEC,}));
		
				//setTopLabelText("Day Number");
		
				getTopLabel().setHorizontalAlignment(JLabel.RIGHT);
				getTopLabel().setBackground(Color.red);
				getTopLabel().setOpaque(true);
		
				getTopLabel().addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						DigitalOrganiser.getDayView().setDate(getDate());
						DigitalOrganiser.getUserInterface().setViewPanel(
								DigitalOrganiser.getDayView());
						
			        	DigitalOrganiser.getUserInterface().getNavigationPanel()
			        	.setLastSelectedCategoryIndex(
			        			new NavigationPanel().getIndexOf("calendar"));
			        	DigitalOrganiser.getUserInterface().getNavigationPanel()
			        	.setLastSelectedItemIndex(new NavigationPanel()
			        		.getIndexOf("day"));
			        	DigitalOrganiser.getUserInterface().getNavigationPanel()
			        	.resetSelection();
			        	
						DigitalOrganiser.updateViews();
						
					}
				});
				
				
				String COL = "1", ROW = "1";
				add(getTopLabel(), COL + "," + ROW);
		
				getEventJList().setSelectionMode
					(ListSelectionModel.SINGLE_SELECTION);
				setEventJListMouseListener(new JListMouseAdapter());
				getEventJList().addFocusListener(new EventJListFocusAdapter());
       
				JScrollPane sp = new JScrollPane();
				sp.setViewportView(getEventJList());
				sp.setHorizontalScrollBarPolicy
					(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				sp.setVerticalScrollBarPolicy
					(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				ROW = "2"; 
				add(sp, COL + "," + ROW + ",fill,fill");
				ROW = "3";
				add(getBottomLabel(), COL + "," + ROW);
				updateUI();
			}
		};
		EventQueue.invokeLater(runThis);
		
	}
	
	/**
	 * Cuts the event list down to display efficiently in the view. 
	 */
	private void trimEventList(){
		EventList trimmedEL = new EventList();
		for (int i=0; i< getEventList().getSize(); i++){
			Event e = getEventList().getEvent(i);
			if(this.checkEventDateTime(e))
				trimmedEL.addEvent(e);
		}
		setEventList(trimmedEL);
	}
	
	/**
	 * Tests if the event occurs on the date of the DayBox.
	 * @param e The events starting Date and Time
	 * @return True if the event is equal to the date. 
	 */
	protected boolean checkEventDateTime(Event e){
		//System.out.println("DayBox: checkEventDateTime(): called by " +
			//getClass().getName());
		DateTime s = e.getStartingDateTime();
		DateTime d = getDate();
		if (s == null || d == null)
			return false;
		final int ONE_DAY = 1;
		if (d.lt(s.minusDays(ONE_DAY)))
			return false;
		if (getDebugStatus())
			System.out.println("DB: checkEventDateTime(): S:["+ s.toString()
							+ "] | D:["+d.toString()+"]");
		if (e.getRepetition().equalsIgnoreCase("no")){
			if (s.numDaysFrom(d) > ONE_DAY || s.numDaysFrom(d) < -ONE_DAY)
				return false;
			if(SystemDate.YMDEqual(s, d))
				return true;
			else
				return false;
		}
		else if (e.getRepetition().equalsIgnoreCase("daily"))
			return true;
		else if (e.getRepetition().equalsIgnoreCase("weekly")){
			if (s.getWeekDay() == d.getWeekDay())
				return true;
		}
		else if (e.getRepetition().equalsIgnoreCase("monthly")){
			if (s.getDay() == d.getDay())
				return true;
		}
		else if (e.getRepetition().equalsIgnoreCase("yearly")){
			if (s.getMonth() == d.getMonth() && s.getDay() == d.getDay())
				return true;
		}
		return false;
	}
	
	/**	The method name for the TopLabel*/
	private JLabel m_TopLabel = new JLabel();
	/**	The method name for the BottomLabel*/
	private JLabel m_BottomLabel = new JLabel();
	/**	The method name for the EventJList*/
	private JList m_EventJList = new JList();
	/**	The method name for the Date*/
	private DateTime m_Date;
	/**	The method name for the EventList*/
	private EventList m_EventList = new EventList();
    /** The debug switch. */
    private boolean m_debug = false;
	
	/**
	 * The main method, used for testing. 
	 * @param args Command line arguments
	 */
	public static void main (String args[]){
        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				EventList evl = new EventList();
		        /** @test adding 20 events occurring today. */
		        for(int i = 0; i < 20; i++){
			        Event e = new Event();
			        e.setToDefaultValues(); 
			        //ID is too large for title; keeping only the nanoseconds part
			        e.setTitle((i+1)+ e.getID().replaceAll(
			        		"[0-9]{4}-[0-9]{2}-[0-9]{2} "+
			        		"[0-9]{2}:[0-9]{2}:[0-9]{2}.", "-ID:"));
			        evl.addEvent(e);
		        }
		        
		        /** @test adding a monthly occurring event. */
		        Event e = new Event();
		        int YEARS=0,MONTHS=2,DAYS=0,HOURS=0,MINUTES=0,SECONDS=0;
		        DateTime todayLastMonth = SystemDate.getCurrentDate().minus
		        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
		        				DateTime.DayOverflow.LastDay);
		        e.setStartingDateTime(todayLastMonth.toString());
		        e.setRepetition("Monthly");
		        e.setTitle(e.getRepetition());
		        evl.addEvent(e);
		        /** @test adding a yearly occurring event. */
		        Event e2 = new Event();
		        YEARS=1;MONTHS=0;DAYS=0;HOURS=0;MINUTES=0;SECONDS=0;
		        DateTime todayLastYear = SystemDate.getCurrentDate().minus
		        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
		        				DateTime.DayOverflow.LastDay);
		        e2.setStartingDateTime(todayLastYear.toString());
		        e2.setRepetition("Yearly");
		        e2.setTitle(e2.getRepetition());
		        evl.addEvent(e2);
				/** @test adding a weekly occurring event. */
				Event e3 = new Event();
		        YEARS=0;MONTHS=0;DAYS=7;HOURS=0;MINUTES=0;SECONDS=0;
		        DateTime todayLastWeek = SystemDate.getCurrentDate().minus
		        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
		        				DateTime.DayOverflow.Spillover);
		        e3.setStartingDateTime(todayLastWeek.toString());
		        e3.setRepetition("Weekly");
		        e3.setTitle(e3.getRepetition());
		        evl.addEvent(e3);
		        /** @test adding a daily occurring event. */
				Event e4 = new Event();
		        YEARS=0;MONTHS=0;DAYS=1;HOURS=0;MINUTES=0;SECONDS=0;
		        DateTime yesterday = SystemDate.getCurrentDate().minus
		        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
		        				DateTime.DayOverflow.Spillover);
		        e4.setStartingDateTime(yesterday.toString());
		        e4.setRepetition("Daily");
		        e4.setTitle(e4.getRepetition());
		        evl.addEvent(e4);

		        DigitalOrganiser.setEventList(evl);
				DayBox db = new DayBox();
				db.setEventList(DigitalOrganiser.getEventList());
				db.setDate(SystemDate.getCurrentDateTime());
				//db.empty();
				JFrame frame = new JFrame();
				frame.add(db);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setVisible(true);
				int WIDTH = 500, HEIGHT = 500;
				frame.setSize(new Dimension(WIDTH, HEIGHT));
			}});
	}
}