/**
 * @file DayView.java
 * @author Lloyd Woodroffe
 * @date 28/03/2012
 * @brief Contains the DayView class.
 */

package views;

import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
 * DayView is a class that is built on a JPanel which contains components 
 * such as JLabels, JButtons and a JScrollPane. 
 * 
 * These components may be used to set up a layout but mainly show events 
 * over the period of one day.
 * 
 * The JScrollPane contains JLists for each hour of the day, separated by hour.
 * 
 * The buttons at the top take you to the next or previous day, which is shown
 * to the user by a JLabel at the top of the view displaying the date. 
 * 
 * @author Lloyd Woodroffe
 * @brief Displays a Day View separated by hours within the day.
 */
@SuppressWarnings("serial")
public class DayView extends JPanel{//Begin DayView class
	
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
     * Every time the date is set, update view.
     * @return the current date 
     */
    public boolean setDate(DateTime oDate){//Begin setDate(Object)
    	if(!oDate.hasHourMinuteSecond()){
	    	String dateAndTime = oDate.toString() + " 00:00:00";
	    	if (getDebugStatus())
	    		System.out.println("Date set to: "+dateAndTime);
	    	m_Date = new DateTime(dateAndTime);
       } 
    	else{
    		if (getDebugStatus())
    			System.out.println("Date set to: "+oDate.toString());
    		m_Date = oDate;
    	}
       return true;  
       
     }//End setDate(Object)
    
    /**
     * Gets the DateTime for storing the Views date.
     * @return DateTime object.
     */
	public DateTime getDate(){//Begin getDate()
		return m_Date;
	}//End getDate()
	

    /**
     * Updates the current display when changed
     * @return true on success, false on failure
     */
    public boolean updateDisplay(){//Begin updateDisplay()
    	EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				removeAll();
				setUpMain();
				updateUI();
			}
    	});
   		
    	return true;
    }//End updateDisplay()

		/**
	     * Gets the Object to display.
	     * @return Object to be displayed
	     */
		public EventList getToDisplay(){//Begin GetToDisplay()
			if (getDebugStatus())
				System.out.println("DV: getToDisplay(): Called.");
			return m_ToDisplay;
		}//End GetToDisplay()
		
		/**
	     * Sets the Object for the View to display.
	     * @param oSetTo the Object to be contained and displayed by the View.
	     * @return true on success, false on failure.
	     */
		public boolean setToDisplay(EventList oSetTo){//Begin SetToDisplay(Object)
			if (getDebugStatus())
				System.out.println("DV: setToDisplay(object): Called.");
			if(oSetTo == null)
				return false;
			m_ToDisplay = oSetTo;
			return true;
		}//End SetToDisplay(Object)
		
	/**
	 * Default constructor for the DayView()
	 */
	public DayView(){
		setToDisplay(new EventList());
		setDate(SystemDate.getCurrentDate());
		//setUpMain();
	}
	/**
	 * Implements the Panel layout 
	 */
	 public void setUpMain(){//Begin setUpMain()
		 createHoursPanel();
		 //add(new JLabel("?????"));
	 }//End setUpMain()
	 /**
	  * Creates and Initialises the main display, Also adds buttons and JLabels
	  */
	public void createHoursPanel(){//Begin createHoursPanel()
		if (getDebugStatus())
			System.out.println("DV: getHoursPanel(): called by "+
					getClass().getName());
		final FormLayout layout = new FormLayout(new ColumnSpec[]{
				ColumnSpec.decode("default:grow"),},
				new RowSpec[]{}
		);
		final DefaultFormBuilder fbuilder = new DefaultFormBuilder(layout);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				DateTime resetDT =  SystemTime.setTimeZero(getDate());
		
				int hourTen = 10;
				int borderSize = 5;
				
				for(int i= 0; i<HOURS_IN_DAY; i++){
					String hour = "" + i;
					if(i<hourTen)
						hour = "0" + i;
					fbuilder.appendSeparator(hour+":00" );
					fbuilder.nextLine();
					fbuilder.setBorder(BorderFactory.createEmptyBorder(
							borderSize,
							borderSize,
							borderSize,
							borderSize
					));
					HourBox hb = new HourBox();
					hb.setEventList((EventList)getToDisplay());
					hb.setDate(resetDT);
					fbuilder.append(hb);
					//System.out.println("get Day View " + getDate().toString());
					final int 	YEARS = 0 , MONTHS = 0 , DAYS = 0 ,
								HOURS = 1 , MINUTES = 0 , SECONDS = 0;
					resetDT = resetDT.plus(	YEARS,MONTHS,DAYS,
											HOURS,MINUTES,SECONDS,
											DateTime.DayOverflow.Abort);
					//System.out.println("get Day View 2 " + getDate().toString());
					//System.out.println(i);
				}
				JScrollPane jsp = new JScrollPane();
				jsp.setViewportView(fbuilder.getPanel());
				
				FormLayout formLayout = new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,});
				
				int COL6 = 6;
				int COL4 = 4;
				int COL2 = 2;
				
				formLayout.setColumnGroups(new int[][]{new int[]{
											COL6, COL4, COL2
										  }
				});
				setLayout(formLayout);

				String COL = "2",ROW = "2",COLSPAN ="5",ROWSPAN ="1";
				JButton btnPreviousDay = new JButton("Previous day");
				btnPreviousDay.addActionListener
					(new PREVButtonActionPerformed());
				add(btnPreviousDay,COL + "," + ROW);
				
				JLabel lblDate = new JLabel(getDate().format("DD-MM-YYYY"));
				lblDate.setHorizontalAlignment(SwingConstants.CENTER);
				COL = "4";
				add(lblDate, COL + "," + ROW);
				
				JButton btnNextDay = new JButton("Next day");
				btnNextDay.addActionListener(new NEXTButtonActionPerformed());
				COL = "6";
				add(btnNextDay,COL + "," + ROW);
				COL = "2"; ROW = "4"; 
				add(jsp, COL + "," + ROW + "," + COLSPAN + "," + ROWSPAN +
						", fill, fill");
				updateUI();
				}
			});
	}//End createHoursPanel()
	
	 /**
	   * Creates an action listener that will be assigned to the previous month button.
	   * @brief Custom action listener used by the DayView.
	   */
	   class PREVButtonActionPerformed implements ActionListener {
	       /**
	       * Overrides the ActionListener's method
	       * @param e The action event.
	       */
	       public void actionPerformed(ActionEvent e) {
	           //System.out.println("PREV button was pressed." );
	           final int    YEARS = 0, MONTHS = 0, DAYS = 1,
                            HOURS = 0, MINUTES = 0, SECONDS = 0;
                            
	           setDate( getDate().minus(YEARS, MONTHS, DAYS,
	                           			HOURS, MINUTES, SECONDS,
	                           			DayOverflow.Spillover));
	           System.out.println("After minus" + getDate().toString());
	           updateDisplay();
	        }
	   }
	   
	   /**
	   * Creates an action listener that will be assigned to the next month button.
	   * @brief Custom action listener used by the DayView.
	   */
	   class NEXTButtonActionPerformed implements ActionListener {
	       /**
	       * Overrides the ActionListener's method
	       * @param e The action event.
	       */
	       public void actionPerformed(ActionEvent e) {
	           //System.out.println("NEXT button was pressed.");
	    	   final int    YEARS = 0, MONTHS = 0, DAYS = 1,
                       HOURS = 0, MINUTES = 0, SECONDS = 0;
                       
          setDate(getDate().plus(YEARS, MONTHS, DAYS,
                          			HOURS, MINUTES, SECONDS,
                          			DayOverflow.Spillover));
       
	              updateDisplay();
	       }
	      }

		/** The Object that a View has to display within itself */
		private EventList m_ToDisplay;
		
		/** The Object used by the DayView, WeekView and MonthView... */
		private DateTime m_Date;
	    /** The debug switch. */
	    private boolean m_debug = false;
		
		
		/**The number of hours in a day*/
		private static int HOURS_IN_DAY = 24;

	/**
	 * The main method, mainly used for adding events.
	 * Also tests the class. 
	 * @param args The command line arguments 
	 */
	public static void main (String args[]){//Begin main(String[])
        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final EventList evl = new EventList();
				int    YEARS = 0, MONTHS = 0, DAYS = 0,
	                   HOURS = 1, MINUTES = 0, SECONDS = 0;
				DateTime dt =  SystemDate.getCurrentDateTime();
				dt = new DateTime(dt.toString().replaceAll
						("[0-9]{2}:[0-9]{2}:[0-9]{2}", "00:00:00"));
				        dt.plus(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS,
				        		DateTime.DayOverflow.Spillover);
				  //System.out.println(dt.toString()+">>>>>>>>>>>>>>>>>>>>>>>>");
				/** @test Adding 1 event for each hour. */
		        for(int i = 0; i < HOURS_IN_DAY; i++){
			        Event e = new Event();
			        e.setToDefaultValues(); 
			        e.setDescription(e.getID());
			       // System.out.println(dt.toString()+"<<<<<<<<<<<<<<");
			        e.setStartingDateTime(dt.toString());
			        e.setTitle(e.getStart());
			        dt = dt.plus(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS,
			        		DateTime.DayOverflow.Spillover);
			        evl.addEvent(e);
		        }
		        
		        /** @test Adding 1 event 3 days and 20 hours later. */
		        Event e = new Event();
		        e.setToDefaultValues(); 
		        e.setDescription(e.getID());
		        DAYS = 3; HOURS = 20;
		        dt = dt.plus(0, 0, 3, 20, 0, 0, DateTime.DayOverflow.Spillover);
		        System.out.println(dt.toString()+"<<<<<<<<<<<<<<");
		        e.setStartingDateTime(dt.toString());
		        e.setTitle(e.getStart());
		        evl.addEvent(e);
		        
				DigitalOrganiser.setEventList(evl);

				/** @test Displaying the view. */
				final DayView dv2 = new DayView();
				dv2.setToDisplay(evl);
				dv2.setDate(SystemDate.getCurrentDate());
				dv2.updateDisplay();
				//dv2.setDate(new DateTime("2012-03-26 14:00:00"));

				final JFrame frame = new JFrame();
				frame.add(dv2);
				int clOp = 3;
				frame.setDefaultCloseOperation(clOp);
				frame.setVisible(true);
				//frame.pack();
				int dimSizeW = 1000;
				int dimSizeH = 700;
				frame.setSize(new Dimension(dimSizeW,dimSizeH));
				
				//DigitalOrganiser.getEventList().iterateEventList();
			}});
	}//End main

}//End DayView class
