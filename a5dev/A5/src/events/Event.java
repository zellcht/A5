/**
 * @file Event.java
 * @author Adewale Odunlami
 * @date 14/03/2012
 * @brief Contains the Event class.
 * 
 *  <b>Justification for modifying the G5 code:</b>
 *  <ul>
 *   <li>Had constructors with too many parameters.</li>
 *   <li>Did not have starting and ending date and time.</li>
 *   <li>Did not have icons.</li>
 *   <li>Almost no self-tests.</li>
 *   <li>Needed protocols for interaction with the storage sub-system.</li>
 *   <li>Needed Event ID.</li>
 *  </ul>
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import javax.swing.ImageIcon;

import dateAndTime.SystemDate;

import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

/**
 * @brief Creates a new event by taking the inputs from the GUI 
 * 
 * This is the event model for the Event and MVC architecture.<br/>
 * Holds all the event data.<br/>
 * <span style="color =red; text-weight: bold">Empty title, starting and ending dates are allowed as requested by the client.</span>
 * @author Thomas Hambly and Ashley Hogarth; Adewale Odunlami
*/
public class Event {
///////////////////////////////ACCESSORS////////////////////////////////////////	
	/**
	 * Get the debug switch.
	 * @return The switch status.
	 */
	public boolean getDebugStatus(){
		return m_debug;
	}	
	/**
	 * Sets the debug switch status.
	 * @param f The new switch status.
	 */
	public void setDebugStatus(boolean f){
		m_debug = f;
	}
////////////////////////////////////////////////////////////////////////////////	
	/**
	 * Gets the event ID.
	 * @return The ID.
	 */
	public String getID(){
		return m_ID;
	}
	/**
	 * Sets the event ID.
	 * @param id The new id.
	 */
	private void setID(String id){
		m_ID = id;
	}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the frequency of the occurrence of an event.
	 * @param r The new frequency.
	 */
	public void setRepetition(String r){	m_Repetition = r;}  
	/**
	 * Gets the event frequency.
	 * @return The frequency.
	 */
	public String getRepetition(){ return  m_Repetition;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the event title.
	 * @return The title.
	 */
	public String getTitle() { return m_Title;}   
	/**
	 * Sets the event title.
	 * @param t The new title.
	 * @return True on success, false on failure.
	 */
	public boolean setTitle(String t) { 
		if (validateTitle(t)){
			m_Title = t;
			return true;
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the start date and time.
	 * @deprecated Beware! It can return null. If you need it as string anyway, use <tt>getStart()</tt>.
	 * @return The date and time.
	 */
	public DateTime getStartingDateTime() { return m_startingDateTime;}
	
	/**
	 * Gets the starting date & time as a string.
	 * @return Empty string if the date & time wasn't set, full date otherwise.
	 */
	public String getStart(){
		try{
			return getStartingDateTime().toString();
		} catch (Exception e) {
			return "";
		}
	}   
	/**
	 * Sets the start date and time.
	 * @param sd The new date and time.
	 * @return True on success, false on failure.
	 */
	public boolean setStartingDateTime(String sd){
		DateTime date = new DateTime("1000");
		try{
			date = new DateTime(sd);
			date.toString();
		} catch (Exception e){
			if(getDebugStatus())
			System.err.println("Event.java:\n setStartingDateTime():\n"
			+ "  Could not set starting date & time to: "+date.toString());
		return false;
		}
		
		//Debug message
		//System.out.println("Date that was range checked:" + date.toString());
		SystemDate sysd = new SystemDate();
        if (sysd.checkDateWithTime(date.toString())){
        	m_startingDateTime = date;
        	return true;
        }    
        return false; 
	}	
////////////////////////////////////////////////////////////////////////////////   
	/**
	 * Gets the ending date and time.
	 * @deprecated Beware! It can return null. If you need it as string anyway, use <tt>getEnd()</tt>.
	 * @return The date and time.
	 */
	public DateTime getEndingDateTime() { return m_endingDateTime;}
	
	/**
	 * Gets the ending date & time as a string.
	 * @return Empty string if the date & time wasn't set, full date otherwise.
	 */
	public String getEnd(){
		try{
			return getEndingDateTime().toString();
		} catch (Exception e) {
			return "";
		}
	}
   
	/**
	 * Sets the end date and time.
	 * @param ed The new date and time.
	 */
	public boolean setEndingDateTime(String ed){
		DateTime date = new DateTime("1000");
		try{
			date = new DateTime(ed);
			date.toString();
		} catch (Exception e){
			if(getDebugStatus())
				System.err.println("Event.java:\n setEndingTime():\n"
				+ "  Could not set ending date & time to: "+date.toString());
			return false;
		}
		//Debug message
		//System.out.println("Date that was range checked:" + date.toString());
		SystemDate sysd = new SystemDate();
		if (sysd.checkDateWithTime(date.toString())){
				m_endingDateTime = date;
				return true;
			}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the event description.
	 * @return The description.
	 */
	public String getDescription() { return m_Description;}   
	/**
	 * Sets the event description.
	 * @param d The new description.
	 * @return True if set, otherwise false.
	 */
	public boolean setDescription(String d){ 
		if (validateDescription(d)){
			m_Description = d;
			return true;
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////
	/** 
	 * Sets the event category.
	 * @param c The new category.
	 */
	public void setCategory(String c) { m_Category = c;}   
	/**
	 * Gets the event category.
	 * @return The category.
	 */
	public String getCategory() { return m_Category;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the event location.
	 * @param l The new location.
	 * @return True if set, otherwise false.
	 */
	public boolean setLocation(String l){ 
		if (validateLocation(l)){
			m_Location = l;
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the event location.
	 * @return The location.
	 */
	public String getLocation() { return m_Location;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the persons involved.
	 * @param p The persons. Expected format: Name1, Name2, Name3....
	 */
	public void setPersonsInvolved(String p){ m_PersonsInvolved = p; }
	/**
	 * Gets the persons involved.
	 * @return The persons.
	 */
	public String getPersonsInvolved(){ return m_PersonsInvolved; }
	
/////////////////////////OTHER//////////METHODS/////////////////////////////////
	/**
	 * Gets the event icon.
	 * @return The icon.
	 */
	public ImageIcon getIcon(){
		return new EventCategoryIcons().getIconForCategory(getCategory());
	}
	
	/**
	 * Sets the event to sensible default values.
	 */	
	public void setToDefaultValues() {
		setTitle("New Event");
		setCategory("Other");
		setStartingDateTime(SystemDate.getCurrentDateTime().toString());
		final int 	YEARS = 0, MONTHS = 0, DAYS = 0,
					HOURS = 1, MINUTES = 0,	SECONDS = 0;
		DateTime et =  getStartingDateTime().plus(	YEARS,	MONTHS,		DAYS,
													HOURS,	MINUTES,	SECONDS,
													DayOverflow.FirstDay);
		setEndingDateTime(et.toString());
		setLocation("");
		setRepetition("No");
		setDescription("");
	}

	/**
	 * Default constructor. Generates an ID.<br/>
	 * <span style="color =red; text-weight: bold">Title, starting & ending dates
	 *  default to empty strings as requested by the client.</span>
	 */
	public Event(){
		setID(SystemDate.generateTimeStamp());
		setTitle("");
		setStartingDateTime("");
		setEndingDateTime("");
		setLocation("");
		setDescription("");
		setCategory("Other");
		setRepetition("No");
	}
	

	/**
	 * Compares the event with a given event.
	 * @param e The event to compare against.
	 * @return True if all fields (except IDs) in both events are equal.
	 */
	public boolean equals(Event e){
		if (e == null)
			return false;
		if (getDebugStatus())
			System.out.println(getTitle()+" vs. "+e.getTitle());
		try{
			if (!getTitle().equals(e.getTitle()))
				return false;
		} catch (Exception e1){
			if (getTitle() != e.getTitle())
				return false;
		}
		if (getDebugStatus())	   
			System.out.println(getCategory()+" vs. "+e.getCategory());
		if(!getCategory().equals(e.getCategory()))
			return false;
   
		if (getDebugStatus())
		System.out.println(
							getStartingDateTime()
							+" vs. "+
							e.getStartingDateTime());
		try{
			if(!getStartingDateTime().equals(e.getStartingDateTime()))
				return false;		   
		} catch (Exception e2){
			if (getStartingDateTime() != e.getStartingDateTime())
				return false;
		}
		if (getDebugStatus())
			System.out.println(
								getEndingDateTime()
								+" vs. "+
								e.getEndingDateTime());
	   try{
		   if(!getEndingDateTime().equals(e.getEndingDateTime()))
			   return false;
	   } catch (Exception e3){
		   if(getEndingDateTime() != e.getEndingDateTime())
			   return false;
	   }
	   
	   if(!getLocation().equals(e.getLocation()))
		   return false;
	   if(!getRepetition().equals(e.getRepetition()))
		   return false;
	   if(!getDescription().equals(e.getDescription()))
			   return false;
	   return true;
	}
	
   /**
    * Equality check with a given event, including ID.
    * @param e The event to compare against.
    * @return True if all fields in both events are equal.
    */
   public boolean equalsWithID(Event e){
	   if (equals(e))
		   if(getID().equals(e.getID()))
			   return true;
	   return false;
   }
	
	
	
	/**
	 * This method returns a string array for the event referenced.
	 * It sets each value of the array to the value of that member variable.
	 * @return The string array containg the variables values.
	 */
	public String[] getAsStingArray(){
				
		String[] eventArray = new String[ARRAY_SIZE];
		
		eventArray[ID_ARRAY_INDEX] = this.getID();
		eventArray[TITLE_ARRAY_INDEX] = this.getTitle();
		eventArray[CATEGORY_ARRAY_INDEX] = this.getCategory();
		eventArray[START_ARRAY_INDEX] = this.getStart();
		eventArray[END_ARRAY_INDEX] = this.getEnd();
		eventArray[LOCATION_ARRAY_INDEX] = this.getLocation();
		eventArray[REPETITION_ARRAY_INDEX] = this.getRepetition();
		eventArray[DESCRIPTION_ARRAY_INDEX] = this.getDescription();
		eventArray[PERSONS_ARRAY_INDEX] = this.getPersonsInvolved();
				
		return eventArray;
	}
		
	/**
	 * This returns the string array containing the header values
	 * the file will have.
	 * @return The string array containing the headers for all the fields
	 * to be stored.
	 */
	public static String[] getHeaderStringArray(){
	
		String[] eventArray = new String[ARRAY_SIZE];
		
		eventArray[ID_ARRAY_INDEX] = "Event ID";
		eventArray[TITLE_ARRAY_INDEX] = "Title";
		eventArray[CATEGORY_ARRAY_INDEX] = "Category";
		eventArray[START_ARRAY_INDEX] = "Starting Date";
		eventArray[END_ARRAY_INDEX] = "Ending Date";
		eventArray[LOCATION_ARRAY_INDEX] = "Location";
		eventArray[REPETITION_ARRAY_INDEX] = "Repetition";
		eventArray[DESCRIPTION_ARRAY_INDEX] = "Description";
		eventArray[PERSONS_ARRAY_INDEX] = "Persons Involved";
		
		return eventArray;
	}
		
	/**
	 * This sets the events member variables from the values contained
	 * in the string array. If any values do not exist or are erroneous
	 * they will be set to default values.
	 * @param eventString This is an array string containing the values
	 * that the event object will take.
	 * @return Returns true if set correctly.
	 */
	public boolean setFromStringArray(String[] eventString){
		
		this.setID(eventString[ID_ARRAY_INDEX]);
		this.setTitle(eventString[TITLE_ARRAY_INDEX]);
		this.setCategory(eventString[CATEGORY_ARRAY_INDEX]);
		this.setStartingDateTime(eventString[START_ARRAY_INDEX]);
		this.setEndingDateTime(eventString[END_ARRAY_INDEX]);
		this.setLocation(eventString[LOCATION_ARRAY_INDEX]);
		this.setRepetition(eventString[REPETITION_ARRAY_INDEX]);
		this.setDescription(eventString[DESCRIPTION_ARRAY_INDEX]);
		this.setPersonsInvolved(eventString[PERSONS_ARRAY_INDEX]);
		
		return true;
	}
	
	/**
	 * Performs all the validations.
	 * @see DurationValidator
	 * @return A string containing the error message, otherwise "OK".
	 */
	public boolean validate() {
		if (!validateTitle(getTitle()))
			return false;
		if (!validateDescription(getDescription()))
			return false;
		if (!validateLocation(getLocation()))
			return false;
		return true;
	}
	
	/**
	 * Validates the title, allowing empty strings and
	 * checking the length.
	 * @param t The string to be validated.
	 * @return True if valid, otherwise false.
	 */
	public boolean validateTitle(String t){
		if(t.isEmpty()){
			return true;
		}
		if(validateTitleLength(t)){
			return true;
		}
		return false;
	}
	
	/**
	 * Validates the location, allowing empty strings and
	 * checking the length.
	 * @param l The string to be validated.
	 * @return True if valid, otherwise false.
	 */
	public boolean validateLocation(String l){
		if(l.isEmpty()){
			return true;
		}
		if(validateLocationLength(l)){
			return true;
		}
		return false;
	}
	
	/**
	 * Validates the description, allowing empty strings and
	 * checking the length.
	 * @param d The string to be validated.
	 * @return True if valid, otherwise false.
	 */
	public boolean validateDescription(String d){
		if(d.isEmpty()){
			return true;
		}
		if(validateDescriptionLength(d)){
			return true;
		}
		return false;
	}
	
	/**
	 * Validates the length of the title, making sure it is not
	 * longer than the max length.
	 * @param t The string to be evaluated.
	 * @return True if within the correct length, false otherwise.
	 */
	public boolean validateTitleLength(String t){
		if(!(t.length() <= TITLE_MAXLENGTH)){
		System.out.println("Error Title: Must be at maximum " 
				+ TITLE_MAXLENGTH + " characters long");
		return false;
		}
		return true;
	}
	
	/**
	 * Validates the length of the location, making sure it is not
	 * longer than the max length.
	 * @param l The string to be evaluated.
	 * @return True if within the correct length, false otherwise.
	 */

	public boolean validateLocationLength(String l){
		if(l.length() > LOCATION_MAXLENGTH){
			System.out.println("Error Location: Must be at maximum " 
				+ LOCATION_MAXLENGTH + " characters long");
			return false;
		}
		return true;
	}
	
	/**
	 * Validates the length of the description, making sure it is not
	 * longer than the max length.
	 * @param d The string to be evaluated.
	 * @return True if within the correct length, false otherwise.
	 */
	public boolean validateDescriptionLength(String d){
		if(!(d.length() <= DESCRIPTION_MAXLENGTH)){
		System.out.println("Error: Must be at maximum " 
				+ DESCRIPTION_MAXLENGTH + " characters long");
		return false;
		}
		return true;
	}
	
	/**
	 * Validates the duration.
	 * @return True on valid duration, false otherwise.
	 */
	public boolean validateDuration(){
		DurationValidator dv = new DurationValidator();
		DateTime s = getStartingDateTime();
		DateTime e = getEndingDateTime();
		if (s==null || e == null){
			if (getDebugStatus())
				System.err.println("Event.java:\n validateDuration():\n"+
						"  Shouldn't normally be able to get here."+
						" At least one of the dates isn't set.");
			return false;
		}
		
		if (getCategory().equalsIgnoreCase("HappyHour"))
			return dv.validateHappyHour(s,e);
		else
		return true;
	}
	
	/** Maximum number of characters for lengths of names. */
	public static final int TITLE_MAXLENGTH = 30;
	/** Maximum number of characters for lengths of names. */
	public static final int LOCATION_MAXLENGTH = 20;
	/** Maximum number of characters for lengths of names. */
	public static final int DESCRIPTION_MAXLENGTH = 200;
	/** The size of the array list to store events fields */
	public static final int ARRAY_SIZE = 9;
	/** The index position in the array for ID. */
	public static final int ID_ARRAY_INDEX = 0;
	/** The index position in the array for Title. */
	public static final int TITLE_ARRAY_INDEX = 1;
	/** The index position in the array for category. */
	public static final int CATEGORY_ARRAY_INDEX = 2;
	/** The index position in the array for start date. */
	public static final int START_ARRAY_INDEX = 3;
	/** The index position in the array for end date. */
	public static final int END_ARRAY_INDEX = 4;
	/** The index position in the array for location. */
	public static final int LOCATION_ARRAY_INDEX = 5;
	/** The index position in the array for repetition. */
	public static final int REPETITION_ARRAY_INDEX = 6;
	/** The index position in the array for description. */
	public static final int DESCRIPTION_ARRAY_INDEX = 7;
	/** The array index position of persons involved. */
	public static final int PERSONS_ARRAY_INDEX = 8;
	/** The ID for the event. */
	private String m_ID; 
    /** Tracks the debug status. Off by default. */
	private boolean m_debug = false;
	/** The Title for the event. */
    private String m_Title;
    /** The Category for the event. */
    private String m_Category;
    /** The Starting date and time for the event. */
    private DateTime m_startingDateTime;
    /** The Ending date and time for the event. */
    private DateTime m_endingDateTime;
    /** The Location for the event. */
    private String m_Location; 
    /** The Repetition period for the event. */
    private String m_Repetition;
    /** The Description for the event. */
    private String m_Description;
    /** The persons involved. */
    private String m_PersonsInvolved;
    /** The symbolic constant containing all the event categories. Some of them are G5 ideas. */
    public static final String[] CATEGORIES = 
	   {	"Other" , 		"Social event" , 	"Birthday" , 	"Anniversary" ,
    		"Meal" , 		"Happy hour" ,		"Concert" , 	"Sports" , 
    		"Work deadline","Meeting" , 		"Appointment" , "Bank holiday" ,
    		"Bill payment" ,"Class/Lecture" ,	"Accident" , "Previous Payment",
    		"Gym" , 		"Vacation"
	   };
    /** Symbolic constant containing all the repeat periods. */
    public static final String[] REPEATS =
    						{"No" , "Daily" , "Weekly" , "Monthly" , "Yearly"};
	/**
	 * Initializes the main method and creates a method
	 * @param args
	 */
	public static void main (String[] args){
		final String FILLER = "-----------------------------------------";  
		Event e = new Event();
		e.setToDefaultValues();

		/** @test If setToDefaultValues() sets indeed the expected values (no dates checked). */ 
		System.out.print("-[TEST1]"+FILLER);
		e.setToDefaultValues();
		boolean test1pass = true;
		if(!e.getTitle().equals("New Event"))
			test1pass = false;
		if(!e.getCategory().equals("Other"))
			test1pass = false;
		if(!e.getLocation().equals(""))
			test1pass = false;
		if(!e.getRepetition().equals("No"))
			test1pass = false;
		if(!e.getDescription().equals(""))
			test1pass = false;
		if(test1pass == true)	        	
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");

		/** @test Check the dates generated by setToDefaultValues(). */ 
		System.out.println("-[TEST2]"+FILLER);
		System.out.println(e.getStartingDateTime().toString());
		System.out.println(e.getEndingDateTime().toString());

		/** @test Setting the date to a corrupt date string. */
		Event ev2 = new Event();
		ev2.setStartingDateTime("2000-01-01 00:00:00 TROLL");
		//System.out.println(">"+ev2.getStartingDateTime());
		if (ev2.getStart().isEmpty())
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");

		
		/** @test If a newly created event has the expected default values. */ 
		System.out.println("-[TEST3]"+FILLER);
		boolean test3pass = true;
		Event e3 = new Event();
		
		if(!e3.setTitle(""))
			test3pass = false;
		if(!e3.setLocation(""))
			test3pass = false;
		if(!e3.setDescription(""))
			test3pass = false;
		if(!e3.getTitle().equals(""))
			test3pass = false;
		if(!e3.getLocation().equals(""))
			test3pass = false;
		if(!e3.getDescription().equals(""))
			test3pass = false;
		if(test3pass == true)	        	
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");

		
		/** @test Field limits. */
		System.out.println("-[TEST4]"+FILLER);
		boolean test4pass = true;
		Event e4 = new Event();
		
		if(e4.setTitle("LongerThanThirtyCharactersString"))
			test4pass = false;
		
		if(e4.setLocation("LongerThan20CharactersString"))
			test4pass = false;
		
		if(e4.setDescription("MoreThan200CharactersStringOOOOOOOOO" +
							 "MoreThan200CharactersStringOOOOOOOOO"	+
							 "MoreThan200CharactersStringOOOOOOOOO"	+
							 "MoreThan200CharactersStringOOOOOOOOO"	+
							 "MoreThan200CharactersStringOOOOOOOOO"	+
							 "MoreThan200CharactersStringOOOOOOOOO"))
			test4pass = false;
		
		if(!e4.getTitle().equals(""))
			test4pass = false;
		
		if(!e4.getLocation().equals(""))
			test4pass = false;
		
		if(!e4.getDescription().equals(""))
			test4pass = false;
		
		if(test4pass == true)	        	
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");
		
	}
}
