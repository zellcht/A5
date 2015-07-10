/**
 * @file WeekView.java
 * @author Lloyd Woodroffe
 * @date 28/03/2012
 * @brief The View class to show the Events in hourly periods by Week.
 */

package views;

import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import dateAndTime.SystemDate;
import dateAndTime.SystemTime;
import digitalOrganiser.DigitalOrganiser;
import events.Event;
import events.EventList;


/**
 * 
 * WeekView is a class that is built on a JPanel which contains components 
 * such as JLabels, JButtons and a JScrollPane. 
 * 
 * These components are vital to set up the main layout of the WeekView and 
 * show a calendar view over the period of seven days, making up a week.
 * 
 * The JScrollPane contains JLists for each hour of the day, separated by hour.
 * These hours are then duplicated into different days, so you can see what 
 * happens within that week. 
 * 
 * The week view itself starts from whatever day is selected, the days then 
 * wrap around to give you a full seven day view. 
 * 
 * The buttons at the top take you to the next or previous week, which is shown
 * to the user by a JLabel at the top of the view displaying the date. 
 * 
 * @author Lloyd Woodroffe
 * @brief Displays a Week View separated by hours and days.
 */
@SuppressWarnings("serial")
public class WeekView extends View{
	
	

    /**
     * Updates the current display when changed
     * @return true on success, false on failure
     */
    public boolean updateDisplay(){
    	EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//removeAll();
				setUpMain();
				//updateUI();
			}
    	});
   		
    	return true;
    }
    /**
     * Creates the main display and adds the components for the view. 
     */
	private void setUpMain(){
		removeAll();
		 
		
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				FormLayout layout = new FormLayout(
						DAYS_PANEL_LAYOUT_COL_SPEC,
						new RowSpec[]{});
				int COL_SUN = 1, COL_MON = 3, COL_TUE = 5, COL_WED = 7,	
						COL_THU = 9, COL_FRI = 11, COL_SAT = 13;
				layout.setColumnGroups(new int[][] 
						{{COL_SUN, COL_MON, COL_TUE, COL_WED,
						COL_THU, COL_FRI, COL_SAT
						}});
				
				 DefaultFormBuilder fbuilder = new DefaultFormBuilder
							(layout);
				 fbuilder.setDefaultDialogBorder();
				int dayNumber = getDate().getWeekDay();			
				for(int i=1;i<=NUMBER_OF_WEEKDAYS;i++){   
					String dayName = SystemDate.getDayName(i);
					fbuilder.append(new JLabel(dayName));
				}
				EventList el = (EventList) getToDisplay();
				DateTime time = SystemTime.setTimeZero(getDate());
				time = SystemTime.setTimeZero(getDate());
				time = time.plusDays(5);
				dayNumber = time.getWeekDay();
				//System.out.println("TODAY IS : "+ 
					//SystemDate.getDayName(dayNumber) + " " +time.toString());
				time = time.minusDays(dayNumber-1);
				//System.out.println("WV Starts from : "+ 
					//SystemDate.getDayName(time.getWeekDay()) + " " +
						//time.toString());
				int lsThanTen = 10;
				for(int i = 0; i < HOURS_IN_DAY; i++){
					String hour = "" + i;
					if(i<lsThanTen)	hour = "0" + i;
					fbuilder.appendSeparator(hour+":00" );
					for(int j = 1; j <= NUMBER_OF_WEEKDAYS; j++){
						//System.out.println("TIME:" + time.toString());
						//System.out.println(getDate().toString());
						HourBox hb = new HourBox();
						//System.out.println("EL Size: "+el.getSize());
						hb.setEventList(el);
						//hb.setDebugStatus(true);
						hb.setDate(time);
						time = time.plusDays(1);
						//System.out.println("HB EL size: "+ hb.getEventList().getSize());
						//System.out.println("HB list size: "+hb.getEventJList().getModel().getSize());
						//fbuilder.append(new JLabel("HOUR BOX"));
						fbuilder.append(hb);
					}
			        int YEARS = 0, MONTHS = 0, DAYS = 0,
	                    HOURS = 1, MINUTES = 0, SECONDS = 0;
					time = time.plus(YEARS,MONTHS,DAYS,HOURS,MINUTES,SECONDS,
							DateTime.DayOverflow.Abort);
					time = time.minusDays(7);
				}
				buildPanel(fbuilder.getPanel());
			}});

	}
	
	/**
	 * Builds up the WeekView.<br/>
	 * It gets passed the form builder's panel.
	 * @param p The panel which will be contained in the scroll pane.
	 */
	public void buildPanel(JPanel p){
		setLayout(WEEK_VIEW_PANEL_LAYOUT);
		JButton btnPreviousDay = new JButton("Previous week");
		btnPreviousDay.addActionListener(new PREVButtonActionPerformed());
		String COL = "2", ROW = "2";
		add(btnPreviousDay, COL + "," + ROW);
		DateTime firstDT = getDate().minusDays(getDate().getWeekDay()-1); 
		String firstDay = firstDT.format("DD/MM/YYYY");
		int lstDayDist = 6;
		DateTime lastDT =firstDT.plusDays(lstDayDist);
		String lastDay = lastDT.format("DD/MM/YYYY");
		String weekDate = firstDay +" - "+ lastDay;
		JLabel lblDate = new JLabel(weekDate);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4";
		add(lblDate, COL + "," + ROW);
		JButton btnNextDay = new JButton("Next week");
		btnNextDay.addActionListener(new NEXTButtonActionPerformed());
		COL = "6";
		add(btnNextDay, COL + ","+ ROW);
		JPanel jp = p;
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(jp);
		//jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		COL = "2"; ROW = "4";
		String COLSPAN = "5", ROWSPAN = "1";
		add(jsp, COL + "," + ROW + "," + COLSPAN + "," + ROWSPAN +
				", fill, fill");
		updateUI();
	}

	 /**
	   * Creates an action listener that will be assigned to the previous month button.
	   * @brief Custom action listener used by the WeekView.
	   */
	   class PREVButtonActionPerformed implements ActionListener {
	       /**
	       * Overrides the ActionListener's method
	       * @param e The action event.
	       */
	       public void actionPerformed(ActionEvent e) {
	           //System.out.println("PREV button was pressed." );
	           final int    YEARS = 0, MONTHS = 0, DAYS = 7,
                           HOURS = 0, MINUTES = 0, SECONDS = 0;
                           
	           setDate( getDate().minus(YEARS, MONTHS, DAYS,
	                           			HOURS, MINUTES, SECONDS,
	                           			DayOverflow.Abort));
	           System.out.println("After minus" + getDate().toString());
	           updateDisplay();
	        }
	   }
	   
	   /**
	   * Creates an action listener that will be assigned to the next month button.
	   * @brief Custom action listener used by the WeekView.
	   */
	   class NEXTButtonActionPerformed implements ActionListener {
	       /**
	       * Overrides the ActionListener's method
	       * @param e The action event.
	       */
	       public void actionPerformed(ActionEvent e) {
	           //System.out.println("NEXT button was pressed.");
	    	   setDate(getDate().plusDays(7));
              updateDisplay();
	       }
	      }
	/** number of hours in a day */   
	private static final int HOURS_IN_DAY = 24;
	
	/** number days in a week */  
	private static final int NUMBER_OF_WEEKDAYS = 7;

	/** The layout specification used by the WeekView. */
	private static final FormLayout WEEK_VIEW_PANEL_LAYOUT = new FormLayout(
		new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("left:pref"),
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("center:default:grow"),
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("right:default"),
			FormFactory.RELATED_GAP_COLSPEC,},
		new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("fill:default:grow"),
			FormFactory.RELATED_GAP_ROWSPEC,});
	
	/** The column specification used by the layout of the panel inside the scroll pane. */ 
	private static final ColumnSpec[] DAYS_PANEL_LAYOUT_COL_SPEC = 
		new ColumnSpec[] {
		//Dialog Units are based on the pixel size of the dialog font and so, 
		//they	grow and shrink with the font and resolution.
		//Better than pixels.
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("10dlu:grow"),};
	/**
	 * The main method, mainly used for adding events.
	 * Also tests the class. 
	 * @param args The command line arguments 
	 */
	public static void main (String args[]){
	      EventQueue.invokeLater(new Runnable() {
				
				@Override
				/**Override run method*/
				public void run() {
					int years,months,days,hours,minutes,seconds;
		        	years=months=days=hours=minutes=seconds=0;
					EventList evl = new EventList();
					DateTime dt =  SystemDate.getCurrentDateTime();
					dt = new DateTime(dt.toString().replaceAll
							("[0-9]{2}:[0-9]{2}:[0-9]{2}", "00:00:00"));
					        dt.plus(0, 0, 0, 1, 0, 0, DateTime.DayOverflow.Spillover);
					  //System.out.println(dt.toString()+">>>>>>>>>>>>>>>>>>>>>>>>");
			        for (int j=0; j< NUMBER_OF_WEEKDAYS;j++){
				        for(int i = 0; i < HOURS_IN_DAY; i++){
				        	//System.out.println("Adding an event starting at: "+dt.toString());
					        Event e = new Event();
					        e.setToDefaultValues(); 
					        e.setDescription(e.getID());
					        //System.out.println(dt.toString()+"<<<<<<<<<<<<<<");					      
				        	e.setStartingDateTime(dt.toString());
				        	e.setTitle(e.getStart());

				        	hours = 1;
				        	dt = dt.plus(years,months,days,hours,minutes,seconds 
				        			,DateTime.DayOverflow.Abort);
				        	evl.addEvent(e);
				        }
				       // System.out.println("Row " +(j+1)+" ended before date: "+dt.toString());
				        dt = dt.minus(years,months,days, HOURS_IN_DAY, 
				        		minutes, seconds,
				        		DateTime.DayOverflow.Spillover);
				    	dt = dt.plusDays(1);
			        }

					
					//evl.iterateEventList();
					
					Event e = new Event();
			        e.setToDefaultValues(); 
			        e.setDescription(e.getID());
			        //System.out.println(dt.toString()+"<<<<<<<<<<<<<<");
			        e.setStartingDateTime(SystemTime.setTimeZero
			        		(e.getStartingDateTime()).toString());
			        e.setTitle(e.getStart());
			       e.setTitle("123456789012345678901234567890");
			        evl.addEvent(e);
					DigitalOrganiser.setEventList(evl);
					
					WeekView wv = new WeekView();
					wv.setToDisplay(evl);
					wv.setUpMain();
					JFrame frame = new JFrame();
					frame.add(wv);
					frame.setDefaultCloseOperation(3);
					frame.setVisible(true);
					//frame.pack();
					int width = 1000, height = 700;
					frame.setSize(new Dimension(width, height));
				}});
	}
}
