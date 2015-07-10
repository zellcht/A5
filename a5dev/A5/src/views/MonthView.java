/**
 * @file MonthView.java
 * @author Lloyd Woodroffe
 * @date 23/03/2012
 * @brief Contains the MonthView class
 */

package views;
import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import dateAndTime.SystemDate;
import events.Event;
import events.EventList;

/**
 * The class monthView works to create and display a month wide view for a set date.<br/>
 * The month view itself adds day boxes into a grid depending on the day.<br/>
 * It features next and previous buttons to take you to the next and previous months.<br/>
 * If the day is not in the month, the day box is grey.<br/>
 * You are able to click events from within the view and edit them from within the view as well.<br/>
 * It uses a FormLayout to set the layout of the view.
 * @see <a href="http://www.jgoodies.com/articles/forms.pdf">FormLayout weblink</a>
 * @author Lloyd Woodroffe
 * @brief Displays a month view.
 */
@SuppressWarnings("serial")
public class MonthView extends View{
	
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
     * Gets the Object to display.
     * @return Object to be displayed
     */
	public EventList getToDisplay(){//Begin getToDisplay()
	
		if(super.getToDisplay() instanceof EventList){
			return (EventList) super.getToDisplay();
		}
		
		else{
			if(getDebugStatus())
				System.out.println("Could not load a event list,"+
							   "using a blank list");
			EventList el = new EventList();
			return new EventList();
		}
	}//End GetToDisplay()
       
		/**
         * Every time the date is set, update view.
         * @return the current date 
         */
        public boolean setDate(DateTime oDate){
           super.setDate(oDate);
			updateDisplay();
           setClickedDate(null);
           return true;  
           
         }
        
        /**
         * Gets the date that is clicked upon 
         * @return the date that is clicked
         */
        public DateTime getClickedDate(){
        	return SELECTED_DATE;
        }
        
        /**
         * Sets the date that is clicked upon
         * @param dt 
         */
        public void setClickedDate(DateTime dt){
        	SELECTED_DATE = dt;
        }
        

        /**
         * Sets the layout for the MonthView
         */
        private void initialiseFormLayout(){
    		int Sunday_Column = 2, 
    			Monday_Column = 4, 
    			Tuesday_Column = 6, 
    			Wednesday_Column = 8, 
    			Thursday_Column = 10, 
    			Friday_Column = 12, 
    			Saturday_Column = 14;
        	MONTH_VIEW_LAYOUT.setColumnGroups(new int[][]{
        			   {Sunday_Column,
						Monday_Column,
						Tuesday_Column,
						Wednesday_Column,
						Thursday_Column,
						Friday_Column,
						Saturday_Column}
        	});
        	setLayout(MONTH_VIEW_LAYOUT);
        }
        
        
	 /**
	  * Sets up widgets for displaying a month and the events in it.
	  * @param oShow eventList to show
	  * @return boolean true upon success
	  */
     private boolean setUpMain(EventList oShow){
    	 Runnable runThis = new Runnable(){
    		 public void run(){
    			 initialiseFormLayout();
	          JButton previousMonthButton = new JButton("Previous");
	          previousMonthButton.addActionListener(
	        		  new PREVButtonActionPerformed());
	          String COL = "2", ROW = "2", COLSPAN = "5", ROWSPAN = "1";
	          add(previousMonthButton,COL + "," + ROW);
	          JLabel yearMonth = new JLabel(new SystemDate().getMonthName
	        		  		(getDate().getMonth())+" "+getDate().getYear());
	          COL = "6";
	          add(yearMonth, COL + "," + ROW + "," + COLSPAN + "," + ROWSPAN + 
	        	  ", center, default");
	          JButton nextMonthButton = new JButton("Next");
	          nextMonthButton.addActionListener(
	          new NEXTButtonActionPerformed());
	          COL = "14";
	          add(nextMonthButton, COL + "," + ROW);
	          String [] WeekDays = {"Sunday","Monday","Tuesday","Wednesday",
	        		  				"Thursday","Friday","Saturday" };
    	  	  int intCol = 2, intRow = 4, incrCol = 2;
	          for(int i=0;i<NUMBER_OF_WEEKDAYS;i++){       	  	  
	        	  	  CellConstraints cc = new CellConstraints();
	                  add(new JLabel(WeekDays[i]), cc.xy(intCol, intRow));
	                  intCol += incrCol;
	          }
	          String firstOfMonth = getDate().format("YYYY-MM-DD")
		   	  .replaceFirst("-[0-9]{2}$", "-01");
	          DateTime firstDay = new DateTime(firstOfMonth);
	          int skip = 0;
	    	  for ( int k = 0 ; k < firstDay.getWeekDay(); k++)
	    			skip++;	
	   		  int currentDay = 0;
	   		  int currentBox = 0;
	   		  intRow = 6;
	   		  int daysInMonth = getDate().getNumDaysInMonth();
	   		  int dayDifference = 2;
	          for(int i=0; i<ROWS; i++){
	        	  intCol = 2;
	        	  if(i == ROWS - 1){
	        		  String lastBox =
	        				MONTH_MATRIX [ROWS-dayDifference][COLOUMNS-1]
	        						.getTopLabelText();
	        		  //WARNING CHECK DAY BOX LABEL TEXT VALUES FOR EMPTY()
	        		  if(lastBox.equals(daysInMonth + " ")
	        				  || lastBox.equals(" ")){
	        			  //System.out.println("break at box: " +currentBox );
	        			  break;
	        		  }       			
	        	  }
	        	  for(int j = 0; j<COLOUMNS; j++){
	        		  DayBox db = new DayBox();
	        		  currentBox++;
	        		  currentDay = currentBox - skip + 1;
	        		  //System.out.println(currentDate);
	        		  //In case first of the month isn't sunday. 
	        		  if (currentBox < skip || currentDay > daysInMonth)
	        			 db.empty();
	        		  //Otherwise add normal calendar
	        		  else{
	        			  DateTime currentDate = 
	        					  firstDay.plusDays(currentDay -1);
	        	           //System.out.println("WOOjkhhjkdekdhehkeweweh" + currentDay);
	        			  EventList eel  = getToDisplay()
	        					  .getEventsForDay(currentDate);
	        			  db.setEventList(eel);
	        			  db.setDate(currentDate);
	        			  db.setTopLabelText( currentDay + " ");
	        		  }
	        		  MONTH_MATRIX [i][j] = db ; 
	        		  CellConstraints cc = new CellConstraints();  
	        		  add(MONTH_MATRIX [i][j], cc.xy(intCol, intRow));
	        		  intCol += incrCol;
	        	  }
	        	  intRow += incrCol;
	          }}};
        EventQueue.invokeLater(runThis);
	    return true;
	 }
	
	
	  
  /**
   * Creates an action listener that will be assigned to the previous month button.
   * @brief Custom action listener used by the MonthView.
   */
   class PREVButtonActionPerformed implements ActionListener {
       /**
       * Overrides the ActionListener's method
       * @param e The action event.
       */
       public void actionPerformed(ActionEvent e) {
           //System.out.println("PREV button was pressed." );
           final int    YEARS = 0,
                                        MONTHS = 1,
                                        DAYS = 0,
                                        HOURS = 0,
                                        MINUTES = 0,
                                        SECONDS = 0;
                                        
           setDate(getDate().minus(
                           YEARS,
                           MONTHS,
                           DAYS,
                           HOURS,
                           MINUTES,
                           SECONDS,
                           DayOverflow.FirstDay));
        }
   }
   
   
   /**
   * Creates an action listener that will be assigned to the next month button.
   * @brief Custom action listener used by the MonthView.
   */
   class NEXTButtonActionPerformed implements ActionListener {
       /**
       * Overrides the ActionListener's method
       * @param e The action event.
       */
       public void actionPerformed(ActionEvent e) {
           //System.out.println("NEXT button was pressed.");
           final int    YEARS = 0,
                                        MONTHS = 1,
                                        DAYS = 0,
                                        HOURS = 0,
                                        MINUTES = 0,
                                        SECONDS = 0;
                                        
              setDate(getDate().plus(
                           YEARS,
                           MONTHS,
                           DAYS,
                           HOURS,
                           MINUTES,
                           SECONDS,
                           DayOverflow.FirstDay));
       }
   }
   
   
   
   
   

        /**
	     * Creates the default format to show a event list contained by the View.
	     * @param ev The event list to be displayed.
	     * @return View object with event list displayed in the format desired.
	     */
        public MonthView CreateDisplay(EventList ev){ //Begin CreateDisplay(Object)
                if(getDebugStatus())
                	System.out.println("Called MonthView :: " +
                			"CreateDisplay(object)");
                setToDisplay(ev);
                add(new JLabel(ev.toString()));
                try{
                        setUpMain(ev);
                }catch(Exception error){
                	if(getDebugStatus())
                        System.out.println("Cannot Typecast "+ev.toString()+
                        		" to an Eventlist");
                }
                return this;
        }   //End CreateDisplay(Object)

    /**
     * Creates the default format to show an event list contained by the view.
     * @return View object with its contents displayed.
     */
         public MonthView createDisplay(){   //Begin CreateDisplay()
                  boolean test = false;
                  if(test){
                   System.out.println("Called MonthView :: CreateDisplay()");
                  }
                  
                  if(getComponentCount() > 0){
                   removeAll();
                  }
                  
                  EventList oShow = (EventList) getToDisplay();
                  
                  if(oShow==null){
                   oShow = new EventList();
                  }
                  setUpMain(oShow);
                  EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						updateUI();
					}
				});
                  
                  //System.out.println("c: "+monthGrid.getColumns()+" r: "+monthGrid.getRows());

                  return this;
                 }   //End CreateDisplay()

        /**
         * Shows the View.
         * @return  true on success, false on failure.
         */
        public boolean Show(){  //Begin Show()
                boolean test = false;
                if(test){
                        System.out.println("Called View :: Show()");
                }
                if(getComponentCount() > 0){
                        return true;
                }
                createDisplay();
                return true;
        }    //End Show()
 

        /**
         * Shows/hides the View.
         * @param bShow sets whether to show or hide the view, true being to show, false being to hide.
         * @return true on success, false on failure.
         */
        public boolean Show(Boolean bShow){ //Begin Show(boolean)
                boolean test = true;
                if(test){
                        System.out.println("Called View :: Show(boolean)");
                }
                if(bShow)
                        createDisplay();
                else
                        removeAll();  //JPanel method to remove everything from the View, to hide it
  
                return true;
        }     //End Show(boolean)

        /**
         * Updates the current display when changed
         * @return true on success, false on failure
         */
        public boolean updateDisplay(){
        	EventQueue.invokeLater(new Runnable() {
    			
				@Override
				public void run() {
					removeAll();
					createDisplay();
					updateUI();
				}
        	});
       		
        	return true;
        }
        /** The month week grid x */
        static final int NUMBER_OF_WEEKDAYS = 7;

        /** The month week grid y */
        static final int NUMBER_OF_WEEKS_IN_A_MONTH = 5;
        
        /** The gap between components within the panel */
        static final int GAP_BETWEEN_COMPONENTS = 20;
        /** The rows within the tables */
        private static final int ROWS = 6;
        /** The columns within the tables */
        private static final int COLOUMNS = 7;
        /** The initialisation of the DayBox Matrix  */
        private static final DayBox [][] MONTH_MATRIX = 
        new DayBox [ROWS][COLOUMNS];
        private static DateTime SELECTED_DATE;
        

        
        /** Tracks the debug status. Off by default. */
    	private boolean m_debug = false;
       	
    	private static final FormLayout MONTH_VIEW_LAYOUT = new FormLayout(
    		new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				ColumnSpec.decode("1px"),
				ColumnSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
		
				
				
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("1px"),
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("1px"),
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("1px"),
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("1px"),
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("1px"),
				RowSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,});
 
	 /**
	  * A method to perform class tests
	  * @param args command line arguments
	  */	 
	 public static void main(String[] args){
		  boolean test = true;
		  MonthView oView = new MonthView();
		  if(test){
		   System.out.println("Called MonthView :: main(boolean)");
		  }
		  
		  EventList e = new EventList();
		  for(int i =0; i< 100;i++)
			  for(int j =0; j< 2;j++){
		           Event event = new Event();
		           event.setToDefaultValues();
		           event.setDebugStatus(true);
		           DateTime dt = SystemDate.getCurrentDateTime().plusDays(i);
		           event.setStartingDateTime(dt.toString());
		           event.setTitle(new DateTime 
		        		   (event.getID()).getNanoseconds() + "");
		           //System.out.println(event.getStart());
		           e.addEvent(event);
			  }
		  System.out.println(e.getSize());
		  oView.setToDisplay(e);
		  System.out.println("------------------------------------------------");
		  oView.setDate(SystemDate.getCurrentDate());
		  System.out.println("Date after set: "+ oView.getDate().toString());
		  oView.updateDisplay();
		  JFrame test1 = new JFrame();
		  test1.add(oView);
		  test1.pack();
		  test1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  test1.setVisible(true);
	  
	 }
}