/**
 * @file CalendarTableModel.java
 * @author Samuel Jenkins
 * @date 23/03/2012
 * @brief A TableModel to handle the data in the JTable YearView displays.
 */
package views;

import javax.swing.JFrame;

import javax.swing.JTable;

import javax.swing.table.AbstractTableModel;

import dateAndTime.SystemDate;

import hirondelle.date4j.DateTime;

import events.Event;
import events.EventList;

/**
 * 
 * This class handles the data of the Table the YearView displays.
 * The CalendarTableModel is an extension of the AbstractTableModel
 * with additional facilities for the handling of Dates, EventLists
 * and the placement of the Tables data to reflect the view of the
 * Calendar we desire to show.
 * 
 * Should anyone wish to change the data shown in the Table, then they
 * must do so through the Table's TableModel. Accessor Methods are provided
 * to allow anyone to change the data that is displayed. Once the changes are
 * made, call this Classes refreshData() method to update itself, the only
 * exception to this would be in the case of setting the Date. 
 * Please use the refreshData(DateTime) method instead, you may use the Classes
 * accessor getDate() to provide the Date to use for this method's parameter.
 * 
 * @author Samuel Jenkins
 * @brief A TableModel to handle the data in the JTable YearView displays.
 *
 */
public class CalendarTableModel extends AbstractTableModel{

	/**
	 * Accessor method to the Table's Data.
	 * The Array provided will be read as myArray[row][col].
	 * @return Table's Data as a 2 Dimensional Array of Objects.
	 */
	public Object[][] getRowData(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getRowData()");
		}
		return m_RowData;
	}
			
	/**
	 * Mutator method for the Table's Data.
	 * @param data is the new 2D Array of Objects to be in the table.
	 * @return true on execution.
	 */
	public boolean setRowData(Object[][] data){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "setRowData()");
		}
		 m_RowData = data;
		 return true;
	}
	
	/**
	 * Accessor method to the Table's Column Name array.
	 * @return Table's Column names as an Array of Objects.
	 */
	public Object[] getColData(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getColData()");
		}
		return m_ColumnNames;
	}
			
	/**
	 * Mutator method for the Table's Column Names.
	 * @param data is the new Array of Objects to be the table's column names.
	 * @return true on execution.
	 */
	public boolean setColData(Object[] data){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "setColData()");
		}
		m_ColumnNames = data;
		return true;
	}
	
	/**
	 * Accessor method to the Table's EventData.
	 * @return the EventList of Events for the Table to hold.
	 */
	public EventList getEventData(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getEventData()");
		}
		return m_EventData;
	}
	
	/**
	 * Mutator method for the Table's EventData.
	 * @param oEvents the EventList for the Table to map events to days.
	 * @return true on execution.
	 */
	public boolean setEventData(EventList oEvents){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "setEventData()");
		}
		this.m_EventData = oEvents;
		return true;
	}
	
	/**
	 * Gets the column's name.
	 * @param col is the column to get the name for.
	 * @return the Columns name.
	 */
	public String getColumnName(int col) {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getColumnNames()");
			System.out.println("col: "+ col+ " "+
							   getColData()[col].toString());
		}
        return getColData()[col].toString();
    }
	
	/**
	 * Gets the number of rows in the Table.
	 * @return the number of rows in the Table.
	 */
    public int getRowCount() {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getRowCount()");
		}
		return m_RowData.length;
	}
    
	/**
	 * Gets the number of Columns in the Table.
	 * @return the number of Columns in the Table.
	 */
	public int getColumnCount() {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getColumnCount()");
		}
		return m_ColumnNames.length;
	}
    
	/**
	 * Gets a cell in the Table.
	 * @param row is the row number of the Cell.
	 * @param col is the column number of the Cell.
	 * @return the cell in the Table.
	 */
	public Object getValueAt(int row, int col) {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getValueAt(int,int)");
		}
        return m_RowData[row][col];
    }
	
	/**
	 * Accessor method to the Table's DateTime member.
	 * @return Table's DateTime.
	 */
	public DateTime getDate(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "getDate()");
		}
		return m_Date;
	}
			
	/**
	 * Mutator method for the Table's DateTime member.
	 * @param date is the new DateTime for the table.
	 * @return true on execution.
	 */
	public boolean setDate(DateTime date){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "setDate(DateTime)");
		}
		if(date==null)
			date=SystemDate.getCurrentDate();
		 m_Date = date;
		return true;
	}
	
	/**
	 * Sets whether the Cells in the Table are editable.
	 * @param bool is true to make the cells editable, false otherwise.
	 * @return true on execution.
	 */
	public boolean setEditable(boolean bool){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "setEditable(bool)");
		}
		m_Editable = bool;
		return true;
	}
	
	/**
	 * Constructs the default CalendarTableModel.
	 */
	public CalendarTableModel(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "CalendarTableModel()");
		}
		setDate(SystemDate.getCurrentDate());
		setEventData(new EventList());
		setColData(CalendarTableModel.MONTH_NAMES);
		setRowData(collectDataForYear(this.getDate()));
	}
	
	/**
	 * Constructs a CalendarTableModel for a YearView and a particular year.
	 * @param oView the YearView displaying the table and holding the EventList to have the Table contain. 
	 * @param date the date holding the year the YearView will display.
	 */
	public CalendarTableModel(YearView oView, DateTime date)
	{
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel :: "+
							   "CalendarTableModel(YearView,"+
							   "EventList,DateTime)");
		}
		setDate(date);
		setEventData(oView.getEventList());
		setColData(CalendarTableModel.MONTH_NAMES);
		if (getEventData() == null){
			System.out.println("Error list is null");
		}
		setRowData(collectDataForYear(date));
	}

	/**
	 * Returns whether the Cells in the Table are editable.
	 * @param row is the cell's row.
	 * @param col is the cell's column.
	 * @return true if the cell is editable, false otherwise.
	 */
	public boolean isCellEditable(int row, int col) {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
					   " :: isCellEditable(int,int)");
		}
		return m_Editable;
	}

	/**
	 *	Collects the data from the EventList to be shown within the Table.
	 *  @param date the Date holding the year to collect the table data for.
	 *	@return A 2 dimensional Object array of the events.
	 */
	private Object[][] collectDataForYear(DateTime date)
	{
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: collectDataForYear(EventList,DateTime)");
		}
		if(date==null){
			date = this.getDate();
		}
		int day, rowNum, colNum, location;
		DateTime tempDate;
		Event e;
		int numRows = MAX_NUMBER_OF_DAYS_IN_A_MONTH;
		int numCols = NUMBER_OF_MONTHS;
		Object[][] result = new Object[numRows][numCols];
		int[] counter = new int[numRows*numCols];
		for(int i=0;i<getEventData().getSize();i++){
			e = getEventData().getEvent(i);
			if(e != null){
				if(e.getStartingDateTime() instanceof DateTime)
					tempDate = (DateTime) e.getStartingDateTime();
				else{
					if(e.getStart() instanceof String){
					if(e.getStart()!=""){
					try{
					tempDate = new DateTime(e.getStart());
					}catch(Exception error){
					tempDate = null;
					}
					}
					else
					tempDate = null;
					}else{
					tempDate = null;
					}
				}
				if(tempDate!=null){
					day = tempDate.getDay().intValue();
					colNum = tempDate.getMonth().intValue()-1;
					rowNum = day-1;
					if(tempDate.getYear().intValue()==date.getYear().intValue())
					{
					location = colNum+numCols*rowNum;
					counter[location]++;
					}
					if(!e.getRepetition().equals(Event.REPEATS[NO_REPEATS]))
					sortRepeats(counter, date, e, rowNum, colNum);
				}
			}
		}
		for(int i=0;i<NUMBER_OF_MONTHS;i++)
			this.monthColumn(result, i, counter, date.getYear());
		return result;
	}
	
	/**
	 * Sorts out the repeatable events in the YearView.
	 * @param counter array to store the number of Events on a Day.
	 * @param de the DateTime of the Year to be displayed.
	 * @param e the repeatable Event.
	 * @param rowNum the row number of where the Event goes.
	 * @param colNum the Column Number of where the Event goes.
	 * @return true on execution.
	 */
	private boolean sortRepeats(int[] counter, DateTime de, Event e, int rowNum,
								int colNum)
	{
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
					   " :: sortRepeats(int[],DateTime,Event,int,int)");
		}
		DateTime d, tempDate;
		if(e.getStart()!=null&&e.getStart()!="") 
			d = new DateTime(e.getStart());
		else
			d = e.getStartingDateTime();
		int week = NUMBER_OF_DAYS_IN_A_WEEK, days;
		int position, a_month=NUMBER_OF_WEEKS_IN_A_MONTH*week;
		if(e.getRepetition().equals(Event.REPEATS[DAILY_REPEAT])){
			if(d.getYear().intValue()!=de.getYear().intValue()){
				tempDate = new DateTime((de.getYear()-1)+"-12-31");
			}else
				tempDate = new DateTime(d.getRawDateString());
			do
			{
				tempDate = tempDate.plusDays(1);
				position = (tempDate.getMonth()-1)+
						    NUMBER_OF_MONTHS*(tempDate.getDay()-1);
				counter[position]++;
			}while(tempDate.getYear().intValue()==de.getYear().intValue()&&
		tempDate.plusDays(1).getYear().intValue()==de.getYear().intValue());
		}
		else if(e.getRepetition().equals(Event.REPEATS[WEEKLY_REPEAT])){
			if(d.getYear().intValue()!=de.getYear().intValue()){
				tempDate = new DateTime((de.getYear()-1)+"-12-31");
				while(tempDate.getWeekDay().intValue()!=
					  d.getWeekDay().intValue()){
					tempDate=tempDate.plusDays(new Integer(-1));
				}
			}else
				tempDate = new DateTime(d.getRawDateString());
			do
			{
				tempDate=tempDate.plusDays(week);
				position = (tempDate.getMonth()-1)+
						    NUMBER_OF_MONTHS*(tempDate.getDay()-1);
				counter[position]++;
			}while(tempDate.getYear().intValue()==de.getYear().intValue()&&
		   tempDate.plusDays(week).getYear().intValue()==de.getYear().intValue());
		}
		else if(e.getRepetition().equals(Event.REPEATS[MONTHLY_REPEAT])){
			if(d.getYear().intValue()!=de.getYear().intValue()){
				tempDate = new DateTime((de.getYear()-1)+"-12"+
						     d.getRawDateString().substring(7));
			}else
				tempDate = new DateTime(d.getRawDateString());
			do{
				days = tempDate.getNumDaysInMonth();
				tempDate=tempDate.plusDays(days);
				position = (tempDate.getMonth()-1)+
						    NUMBER_OF_MONTHS*(tempDate.getDay()-1);
				counter[position]++;
			}while(tempDate.getYear().intValue()==de.getYear().intValue()&&
		 tempDate.plusDays(days).getYear().intValue()==de.getYear().intValue());
		}
		else if(e.getRepetition().equals(Event.REPEATS[YEARLY_REPEAT])){
			position = colNum+rowNum*NUMBER_OF_MONTHS;
			if(rowNum==a_month&&colNum==1&&d.isLeapYear()){
				System.err.println("Cannot show a repeatable event on the 29th"
								  +" February, as it is not a Leap Year");
				return true;
			}
				counter[position]++;
		}
		return true;
	}
	
	/**
	 * Fills the rows for a particular Month with the date and the number of Events
	 * a particular day has in that month.
	 * @param table the table data to be filled.
	 * @param month the month being filled in.
	 * @param counter the array holding the numbers of Events for a particular day.
	 * @param year the year the TableModel is holding the data for.
	 * @return true on execution
	 */
	private boolean monthColumn(Object[][] table, int month, int[] counter,
								int year)
	{
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: monthColumn(Object[][], int, int, int[], int)");
		}
		String d, s;
		int UNITS_MAX = 9;
		if(month < UNITS_MAX){
			d = year+"-0"+(month+1)+"-01";
		}
		else{
			d = year+"-"+(month+1)+"-01";
		}
		DateTime date = new DateTime(d);
		int numRows = table.length, numCols = NUMBER_OF_MONTHS;
		int numDays = date.getNumDaysInMonth();
		for(int i=0,dayCount=1;i<numRows&&dayCount<numDays+1;i++){
			if(dayCount>UNITS_MAX+1){
				s="";
			}else{
				s="  ";
			}
			if(counter[month+numCols*i]>1){
				table[i][month] = ""+dayCount+appendth(dayCount)+"\n "+
								  s+counter[month+numCols*i]+" Events";
			}else if(counter[month+numCols*i]>0){
				table[i][month] = ""+dayCount+appendth(dayCount)+"\n "+
						 		  s+counter[month+numCols*i]+" Event";
			}else{
				table[i][month] = ""+dayCount+appendth(dayCount);
			}
			dayCount++;
		}
		return true;
	}
	
	/**
	 * Updates the TableModel's data to be displayed in the YearView of the date
	 * currently held in the TableModel's m_Date field.
	 * @return true on execution.
	 */
	public boolean refreshData(){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: refreshData()");
		}
		this.setRowData(this.collectDataForYear(this.getDate()));
		return true;
	}
	
	/**
	 * Updates the TableModel's data to be displayed in the YearView of the date
	 * desired.
	 * @param date the Date desired to be shown in the YearView.
	 * @return true on execution.
	 */
	public boolean refreshData(DateTime date){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: refreshData(DateTime)");
		}
		if(date == null){
			date = this.getDate();
			System.err.println("Invalid Date, default Date being used.");
		}
		this.setRowData(this.collectDataForYear(date));
		return true;
	}
	
	/**
	 * Appends the appropriate suffix to a number to denote whether it is the nth, 1st, 2nd etc.
	 * @param r the number to have its suffix appended.
	 * @return A string of the appropriate suffix for a number.
	 */
	private String appendth(int r) {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: appendth(int)");
		}
		if(r==FIRST)
			return "st ";
		else if(r==SECOND)
			return "nd";
		else if(r==THIRD)
			return "rd ";
		else if(r>20){
			if(r%DIGIT_LIMIT==FIRST)
				return "st ";
			if(r%DIGIT_LIMIT==SECOND)
				return "nd";
			if(r%DIGIT_LIMIT==THIRD)
				return "rd ";
		}
		return "th ";
	}

	/**
	 * Tests the class
	 * @param args command line arguments
	 */
	public static void main(String[] args){
		boolean test = false;
		if(test){
			System.out.println("Called CalendarTableModel"+
							   " :: main(String[])");
		}
		System.out.println("Called CalendarTableModel"+
				   " :: Constructor for dtm");
		CalendarTableModel dtm = new CalendarTableModel();
		EventList el = dtm.getEventData();
		
		DateTime d = new DateTime("2012-05-01");
		int NUMBER_OF_DAYS_TO_ADD_EVENTS_TO = 28;
		for(int i=0;i<NUMBER_OF_DAYS_TO_ADD_EVENTS_TO;i++){
			Event e = new Event();
			e.setTitle("I am a"+ i);
			//System.out.println(e.getTitle());
			if(i<9){
				e.setStartingDateTime("2012-01-0"+(i+1)+" 00:00:00");
			}
			else{
				e.setStartingDateTime("2012-01-"+(i+1)+" 00:00:00");
			}
			el.addEvent(e);
		}
		Event g = new Event(), h = new Event(), i=new Event(), j=new Event();
		g.setRepetition(Event.REPEATS[YEARLY_REPEAT]);
		g.setStartingDateTime("2012-02-29 00:00:00");
		el.addEvent(g);/**/
		h.setRepetition(Event.REPEATS[DAILY_REPEAT]);
		h.setStartingDateTime("2012-02-28 00:00:00");
		el.addEvent(h);/**/
		i.setRepetition(Event.REPEATS[WEEKLY_REPEAT]);
		i.setStartingDateTime("2012-02-28 00:00:00");
		el.addEvent(i);/**/
		j.setRepetition(Event.REPEATS[MONTHLY_REPEAT]);
		j.setStartingDateTime("2012-02-28 00:00:00");
		el.addEvent(j);/**/
		System.out.println("Called CalendarTableModel"+
				   " :: setEventData for dtm");
		d=new DateTime("2013-05-01");
		dtm.setEventData(el);
		dtm.setRowData(dtm.collectDataForYear(d));
		/*for(int i=0; i<29;i++){
			if(el.getEvent(i)==null)
				System.out.println("Nothing in List!");
		}*/
		JFrame f = new JFrame();
		javax.swing.JScrollPane sp = new javax.swing.JScrollPane();
		//CalendarTableModel ctm = new CalendarTableModel(/*new YearView(),el,d*/);
		JTable t = new JTable(dtm);
		System.out.println("EventList: "+el.toString()+" Size: "+el.getSize());
		sp.setViewportView(t);
		f.add(sp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);	
	}
	
	/** An array containing the names of each weekday */
	private static final String[] MONTH_NAMES = {	 
												 "Jan","Feb","Mar","Apr",
												 "May","Jun","Jul","Aug",
												 "Sep","Oct","Nov", "Dec"
											  	};
	
	/** The number of days in a week */
	private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
	
	/** The minimal number of weeks in a month */
	private static final int NUMBER_OF_WEEKS_IN_A_MONTH = 4;
	
	/** The number of days in a year */
	private static final int DAYS_IN_YEAR = 365;
	
	/** The the successor of the maximum value a digit can be */
	private static final int DIGIT_LIMIT = 10;
	
	/** The number that is first */
	private static final int FIRST = 1;
	
	/** The number that is second */
	private static final int SECOND = 2;
	
	/** The number that is third */
	private static final int THIRD = 3;
	
	/** The number of Months in a year */
	private static final int NUMBER_OF_MONTHS = 12;
	
	/** The maximum number of days a month could have  */
	private static final int MAX_NUMBER_OF_DAYS_IN_A_MONTH = 31;
	
	/** The EventList that the TableModel uses to map events to days on the Calendar */
	private EventList m_EventData;
	
	/** Determines whether the table is editable or not */
	private boolean m_Editable;
	
	/** The Object Array to hold the Table's Data */
	private Object[][] m_RowData;
	
	/** The Object Array to hold the Column names for the Table */
	private Object[] m_ColumnNames;
	
	/** The DateTime object to hold the date information for
	 * the period the Table is showing
	 */
	private DateTime m_Date;
	
	/** The index for a non-repeatable Event */
	private final static int NO_REPEATS=0;
	
	/** The index for an Event that occurs daily */
	private final static int DAILY_REPEAT=1;
	
	/** The index for an Event that occurs weekly */
	private final static int WEEKLY_REPEAT=2;
	
	/** The index for an Event that occurs monthly */
	private final static int MONTHLY_REPEAT=3;
	
	/** The index for an Event that occurs yearly */
	private final static int YEARLY_REPEAT=4;
	
}