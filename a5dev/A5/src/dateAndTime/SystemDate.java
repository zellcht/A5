/**
 * @file SystemDate.java
 * @author Ceris Land
 * @date 22/02/2012
 * @brief Contains the SystemDate class
 */

/** 
 * @package dateAndTime
 * @brief Utilities for working with DateTime, java Date. 
 */
package dateAndTime;

import hirondelle.date4j.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

/**
 * @brief Utility class for working with date.
 * 
 * Contains tools for working with DateTime objects.
 * @see <a href="http://cs.swan.ac.uk/~csbob/teaching/cs235-softEng/success/Group7designReport.pdf">Group7designReport.pdf</a> , page 15
 * @see <a href="http://www.date4j.net">Date4J weblink</a>
 * @author Ceris Land
 */
public class SystemDate {
	
	/**
     * Checks if the date passed is valid.<br/>
     * Reason: DateTime doesn't complain even when given an invalid string.     * 
     * @param date Date to be checked.
     * @return True if the date has all required fields, false otherwise.
     */
	public static boolean validateDate(DateTime date){
		try{         
            date.toString();
            date.getYear(); // double checking that
        }catch (Exception e) {
            System.err.println("SD: validateDate(): Invalid date.");
            return false;
        }
		return true;
	}
	
    /**
     * Checks if a DateTime has all fields.<br/>
     * Expected format: <tt>2000-01-01 00:00:00</tt>
     * @param date DateTime to be checked
     * @return True if the date has all required fields, false otherwise.
     */
	private boolean validateDateWithTime(DateTime date){
		if (date == null) return false;
		if (!validateDate(date)) return false;
		try{ date.getYear();}
		catch (Exception e) {
            System.err.println("SD: validateDateTime(): "+
            		"Could not get year from: "+date.toString());
            return false;
        };
        try{date.getMonth();}
        catch (Exception e) {
            System.err.println("SD: validateDateTime(): "+
                    "Could not get month from: "+date.toString());
            return false;
        };
		try{date.getHour();} catch (Exception e) {
            System.err.println("SD: validateDateTime(): "+
                    "Could not get hours from: "+date.toString());
            return false;
        };
        try{date.getMinute();}catch (Exception e) {
            System.err.println("SD: validateDateTime(): "+
                    "Could not get minutes from: "+date.toString());
            return false;
        };
        if (date.getHour() == null){
            System.err.println("SD: validateDateTime(): "+
                "Date incomplete: does not contain an hour." );
            return false;
        }
        if (date.getMinute() == null){
        	System.err.println("SD: validateDateTime(): "+
                "Date incomplete: does not contain minutes." );
        	return false;
        }
        
        return true;
    }	
	
	/**
     * Checks if a DateTime can be built out of a string and if it's inside the decided range.
     * @param dt String to parse. Format: <tt>2000-01-30</tt> or <tt>2000-01-30 00:00:00</tt>.
     * @return True on success, false on failure.
     * @see validateDate()
     * @see rangeCheck()
     */
	public boolean checkDate(String dt){
		if (dt.isEmpty())
            return false;
		String dummyDateString = "1000";
        DateTime date = new DateTime(dummyDateString);

        try{date = new DateTime(dt);}
        catch (Exception e){
            System.err.println("SD: checkDate(): Could not set date from: "+dt);
            return false;
        }
        if (validateDate(date))
            if (rangeCheck(date))
            	return true;
        
        return false; 
	}
	
    /**
     * Checks if a DateTime can be built out of a string and if it's inside the decided range.
     * @param dt String to parse. Format: <tt>2000-01-30 00:00:00</tt>.
     * @return True on success, false on failure.
     * @see validateDateWithTime()
     * @see rangeCheck()
     */
    public boolean checkDateWithTime(String dt){
		if (dt.isEmpty()) return false;
		String dummyDateString = "1000";
        DateTime date = new DateTime(dummyDateString);

        try{date = new DateTime(dt);}
        catch (Exception e){
            System.err.println("SD: validateDateWithTime(): "+
                    "Could not set starting date & time from: " + dt);
            return false;
        }
        //Debug message
        //System.out.println("Date that was range checked:" + date.toString());
        
        if (validateDateWithTime(date))
            if (rangeCheck(date))
                return true;
        
        return false; 
	}
	
	/**
     * Validates the range of a date.
     * @return True if passed, otherwise false.
     * @see MINIMUM_DATE
     * @see MAXIMUM_DATE
     */
	public boolean rangeCheck(DateTime d){
        if (d.lt(MINIMUM_DATE) || d.gt(MAXIMUM_DATE)){
            System.err.println("SystemDate.java:\n rangeCheck():\n"
                    + "  Date out of Range" );
            return false;
        }
        return true;
	}
	
	/**
	 * Takes two strings, converts them to dates and checks if the first date is after the second date.
	 * @param d1 First date string.
	 * @param d2 Second date string.
	 * @return true on success, false on failure
	 */
	public boolean isDateAfter(String d1, String d2){		
		DateTime date1 = new DateTime("1000");
		DateTime date2 = new DateTime("1000");
		
		if(checkDate(d1) && checkDate(d2)){
			date1 = new DateTime(d1);
			date2 = new DateTime(d2);
		} else 
			return false;
		//If date2 is a later date than date1, return true
		if(date1.lt(date2))
			return true;
		
		return false;
	}
	
	/**
	 * Takes two dates and checks if the first date is after the second date.
	 * @param dt1 First date.
	 * @param dt2 Second date.
	 * @return True if the first date is less than the second date, false otherwise.
	 */
	public boolean isDateAfter(DateTime dt1, DateTime dt2){		
		if(validateDate(dt1) && validateDate(dt2))
			//If date2 is a later date than date1, return true
			if(dt1.lt(dt2))
				return true;
		return false;
	}
	
	/**
	 * Takes 3 strings, converts them to dates and checks if the first date is between the second and third dates.
	 * @param d1 String date to be checked.
	 * @param d2 String date for start of range.
	 * @param d3 String date for end of range.
	 * @return True if the first date is between the second and third date, false otherwise.
	 */
	public boolean isDateBetween(String d1, String d2, String  d3){
		DateTime date = new DateTime("1000");
		DateTime startDate = new DateTime("1000");
		DateTime endDate = new DateTime("1000");
		
		if(checkDate(d1) && checkDate(d2) && checkDate(d3)){
			date = new DateTime(d1);
			startDate = new DateTime(d2);
			endDate = new DateTime(d3);
		} else 
			return false;
		
		if(endDate.lt(startDate))
			return false;
		
		if(date.lt(startDate))
			return false;
		
		if(date.gt(endDate))
			return false;
		
		return true;
    }
        
    /**
     * @deprecated Should use this with DateTime arguments instead.<br/>
     * Don't need to convert them to strings anymore.<br/>
     * Also return value was detected as completely wrong under certain durations.<br/>
     * Strongly advised to stop using it.
     * @see calculateDuration(DateTime,DateTime)
     * 
	 * Calculates the difference between two date strings
	 * of the form: yyyy-mm-dd with optional HH:mm added
	 * and returns the duration between them in milliseconds.
	 * @param d1 A date.
	 * @param d2 A date.
	 * @return The duration in seconds. Positive if d1<d2, otherwise negative. 
	 * Divide by:
	 * 60 for minutes,
	 * 3,600 for hours,
	 * 86,400 for days, etc.
	 */
	public long calculateDuration(String d1, String d2){
		DateTime date1 = new DateTime("1000");
		DateTime date2 = new DateTime("1000");
		
		if(checkDate(d1) && checkDate(d2)){
			date1 = new DateTime(d1);
			date2 = new DateTime(d2);
		} else 
			return (Long) null;
		
		long difference = date2.numSecondsFrom(date1);
		
		return difference;
	}
	
    /**
	 * Calculates the difference between two date times and returns the duration between them in seconds.
	 * @param dt1 A date time.
	 * @param dt2 A date time.
	 * @return The duration in seconds. 
	 * Divide by:
	 * 60 for minutes,
	 * 3,600 for hours,
	 * 86,400 for days, etc.
	 */
	public static long calculateDuration(DateTime dt1, DateTime dt2){
		SystemDate sd = new SystemDate();
		if (!(sd.rangeCheck(dt1) && sd.rangeCheck(dt2))){
			System.err.println("SD: calculateDuration(): " +
					"One date is out of my range; don't bother me");
			return -1;
		}
		
		long difference = dt2.numSecondsFrom(dt1);
		if (difference < 0) difference *=-1; // sometimes it's correct, but negative
		long DAYS_FROM_SECONDS = difference/86400;
		
		long daysDifferenceDoubleChecker = dt1.numDaysFrom(dt2);
		//System.out.println(DAYS_FROM_SECONDS);
		//System.out.println(daysDifferenceDoubleChecker);

		/* Backup kicks in in case numSecondsFrom() fails.
		 * Failure is detected by comparing seconds got with seconds from days number
		 */
		if (daysDifferenceDoubleChecker-DAYS_FROM_SECONDS > 1){
			System.err.println("DATE4J failure detected. Using alternative.");
			//deriving the seconds from the day instead
			difference = daysDifferenceDoubleChecker*86400;			
		}
		return difference;
	}
	
	/**
	 * Gets the current date.
	 * @return The current date in a <tt>2000-01-30</tt> format.
	 */
	public static DateTime getCurrentDate(){
		return DateTime.today(TimeZone.getDefault());
	}
	
	/**
	 * Gets the current date and time.
	 * @return The date and time in a <tt>2000-01-30 00:00:00</tt> format.
	 * @author Codrin Morhan
	 */
	public static DateTime getCurrentDateTime(){
		DateTime dt = DateTime.now(TimeZone.getDefault());
		dt = new DateTime(dt.format(DATE_FORMAT));
		return dt;
	}
	
	/**
	 * Gets a string with the current date and time.
	 * @return The current date and time.
	 * @author Codrin Morhan
	 * @see DateTime getCurrentDateTime()
	 */
	public static String getCurrentDateTimeString(){
		return getCurrentDateTime().toString();		
	}
	
	/**
	 * Converts a month number to a month name.
	 * @param i The month number.
	 * @return The month name.
	 */
	public String getMonthName(int i){
		int FIRST_MONTH = 1, LAST_MONTH = 12;
		if ( i<FIRST_MONTH || i>LAST_MONTH)
			return "SD: getMonthName(): Invalid month number.";
		String[] months = {
				"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"
		};

		return months[i-1];
	}
	
	
	/**
	 * Converts a day number to a day name.
	 * @param i The day number.
	 * @return The day name.
	 */
	public static String getDayName(int i){
		int 	FIRST_DAY_OF_WEEK = 1,
				LAST_DAY_OF_WEEK = 7;
		if (i<FIRST_DAY_OF_WEEK || i>LAST_DAY_OF_WEEK)
			return "SD: getDayName(): Invalid day number.";
        String [] WeekDays = {"Sunday", "Monday", "Tuesday", "Wednesday",
  							"Thursday",	"Friday", "Saturday" };
		return WeekDays[i-1];
	}
	
	/**
	 * Converts a java date to a DateTime date & time.
	 * @param d A java date in the <tt>2000-01-30 13:00:00</tt> or <tt>2000-01-30</tt> format.
	 * @return The converted date. If the orignal date didn't have time set, the returned time is <tt>00:00:00</tt>.
	 * @author Codrin Morhan
	 */
	public static DateTime convertDateToDateTime(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_JAVA);		
		return new DateTime(sdf.format(d));
	}
	
	/**
	 * Gets the maximum allowed duration.<br/>
	 * That is, the duration of the allowed date range.
	 * @return The duration, in milliseconds.
	 */
	public static long MaxDuration(){
		return calculateDuration(MINIMUM_DATE,MAXIMUM_DATE);
	}
	
	/**
	 * Generates an accurate time stamp, down to the milliseconds.<br/>
	 * The last 6 digits of the nanoseconds are artificially generated.<br/>
	 * The general purpose of the class is to generate unique strings every time it's run. 
	 * @return A string of the format <tt>2012-03-20 22:42:39.529123456</tt>
	 * @author Codrin Morhan
	 * @see validateTimeStamp() for a regex version.
	 */
	public static String generateTimeStamp(){
		DateTime stamp = DateTime.now(TimeZone.getDefault());
		//System.out.println(stamp.toString());
		int nanoseconds = new Random().nextInt(999999);
		//The last 6 digits are always zero due to DateTime not having 
		//such precision by default
		return stamp.toString().replaceFirst("[0]{6}$", nanoseconds+"");
	}
	
	/**
	 * Validates a time stamp.
	 * @param ts The time stamp to be validated.
	 * @return True if the time stamp fits the regex <tt>(((19[7-9][0-9])|2[0-1][0-9]{2}))-((1[0-2])|(0[1-9]))-(([0-2][1-9])|(3[0-1])) (([0-1][0-9])|(2[0-4])):[0-5][0-9]:[0-5][0-9].[0-9]{3}[0-9]{1,6}</tt> 
	 * , in essence, any date of the format 2000-01-30 00:00:00 that is between 1970 and 2200; false otherwise.
	 * @author Codrin Morhan
	 * @see generateTimeStamp
	 */
	public static boolean validateTimeStamp(String ts){
		return ts.matches(
	"(((19[7-9][0-9])|2[0-1][0-9]{2}))-((1[0-2])|(0[1-9]))-(([0-2][1-9])|" +
	"(3[0-1])) (([0-1][0-9])|(2[0-4])):[0-5][0-9]:[0-5][0-9].[0-9]{3}[0-9]{1,6}"
		); //super long regex
	}
	
	/**
	 * Checks if two dates have the same year, month and day.
	 * @author Lloyd Woodroffe
	 * @param d1 The first date.
	 * @param d2 The second date.
	 * @return True if their year, month and day are the same, false otherwise.
	 */
	public static boolean YMDEqual (DateTime d1, DateTime d2){
		if (d1 == null || d2 == null )
			return false;
		if (!(d1.hasYearMonthDay() && d2.hasYearMonthDay()))
			return false;
		int a = d1.getYear();
		int b = d2.getYear();
		//System.out.println(d1.toString() + " vs " + d2.toString());
		if(a == b) //year check
			if(	(d1.getMonth()	== d2.getMonth()) && //month check				
				(d1.getDay() == d2.getDay()))	//day check
				return true;
		return false;
				 
	}


	/** The date format standard for the application, in DateTime format. */
	public static String DATE_FORMAT = "YYYY-MM-DD hh:mm:ss";
	/** The date format standard for the application, in java format. */
	public static String DATE_FORMAT_JAVA = "yyyy-MM-dd HH:mm:ss";
	/** The date lower limit. */
	private final static DateTime MINIMUM_DATE = new DateTime("1970-01-01");
	/** The date upper limit. */
    private final static DateTime MAXIMUM_DATE = new DateTime("2200-01-01");
	
	/**
	 * Main method performs tests on the class.
	 * @param args Arguments will be ignored.
	 */
	public static void main(String[] args){
		final String FILLER = "-----------------------------------------";
				
		SystemDate cl = new SystemDate();
	
		
		/** @test checkDate() response with a valid date. */
		System.out.print("test1----------------------");
		if(cl.checkDate("2000-02-19"))
			System.out.println("Date set successfully");
		else
			System.out.println("Date not valid");
		
		/** @test checkDate() response with an invalid date. */
		System.out.print("test2----------------------");
		if(cl.checkDate("2013-02-29"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isDateAfter() response with valid dates. */
		System.out.println("test3----------------------");
		if(cl.isDateAfter("2000-03-04", "2010-02-21"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isDateAfter() response with invalid dates. */
		System.out.print("test4----------------------");
		if(cl.isDateAfter("2040-03-16", "2010-02-21"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isDateBetween() with valid dates. */
		System.out.print("test5----------------------");
		if(cl.isDateBetween("2006-05-04", "1999-02-21", "2010-03-12" ))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isDateBetween() with invalid dates. */
		System.out.print("test6----------------------");
		if(cl.isDateBetween("2019-05-04", "1999-02-21", "2010-03-12" ))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		/** @test calculateDuration() check test. */
        System.out.print("-[TEST7]-------------------------------------------");
		String date1 = "2000-01-01";
		String date2 = "2000-01-02";
		long dur = cl.calculateDuration(date2,date1);
		//System.out.println("|"+dur);
		if (dur == 86400)
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");
		/** @test Checking what time zone the system thinks it's in. */
		System.out.println("-[TEST8]-----------------------------------------");
		System.out.println("Time zone is: "
									+ TimeZone.getDefault().getDisplayName());
		/** @test Manually verifying the returned date from getCurrentDate(). */
		System.out.println("-[TEST9]-----------------------------------------");
		System.out.println("Date is: "
									+ SystemDate.getCurrentDate());
		
		/** @test convertDateToDateTime(): checking conversion; checking format. */
		System.out.println("-[TEST10]--------"+FILLER);
		Date d10 = new Date();
		System.out.println("Java Date: "+d10.toString());
		DateTime dt10 = convertDateToDateTime(d10);
		System.out.println("Converted to DateTime: "+ dt10.toString());

		//System.out.println(dt10.format(DATE_FORMAT));
		if (dt10.format(DATE_FORMAT).equals(dt10.toString()))
			System.out.println(FILLER + "---------[PASSED]");
		else
			System.out.println(FILLER + "---------[FAILED]!");
					
		/** @test Checking how a java date looks when formatted using DATE_FORMAT_JAVA. */
		System.out.println("-[TEST11]--------"+FILLER);
		boolean passed = true;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_JAVA);
			DateTime dt11 = convertDateToDateTime(new Date());
			Date d11 = sdf.parse(dt11.toString());
			System.out.println("DT:" + dt11.toString());
			System.out.println("JD:" + sdf.format(d11));			
		} catch (Exception e){
			System.out.println(FILLER + "---------[FAILED]!");
			passed = false;
		} finally {
			if (passed)
				System.out.println(FILLER + "---------[PASSED]");
		}
		
		/** @test Displaying duration got from MaxDuration() vs. duration got using the DateTime numDaysFrom() directly. */
		System.out.println("-[TEST12]--------"+FILLER);
		long d12 = MaxDuration();
				
		System.out.println("Range: "+MINIMUM_DATE+ " to " + MAXIMUM_DATE);
		System.out.println("Milliseconds: "+ d12*1000);
		System.out.println("Seconds: "+ d12);
		System.out.println("Minutes: "+ d12/60);
		System.out.println("Hours: " + d12/3600);
		System.out.println("Days: " + d12/86400);
		System.out.println("Years: ~"+(d12/86400)/365);
		
		System.out.println(">"+FILLER+"<");
		
		long d12_2 = MINIMUM_DATE.numDaysFrom(MAXIMUM_DATE);
		System.out.println("Years: ~" + d12_2/365);
		System.out.println("Days: " + d12_2);
		System.out.println("Hours: " + d12_2*24);
		System.out.println("Minutes: " + d12_2*24*60);
		System.out.println("Seconds: " + d12_2*24*60*60);
		System.out.println("MilliSeconds: " + d12_2*24*60*60*1000);
		
		/** @test Time stamp generation and uniqueness.<br/>
		 * <b>!WARNING!</b> can be resource intensive.<br/>
		 * Skipped by default.<br/>
		 * Last result :
		 * 14999969 unique timestamps out of 15000000 generated.
		 * Reliability: 99.999793%
		 */ 
		System.out.print("-[TEST13]"+FILLER);
		boolean TEST_13 = false;
		if (TEST_13){
			System.out.print("-------\n");
			int SETS = 1000, //how many sets to run
				CYCLES = 15000,//how many runs is each set
				DUPLICATES = 0;//how many duplicates were generated
			for (int j = 0; j<SETS; j++){			
				System.out.println("Set "+j);
				HashMap<String, Integer> hm = new HashMap<String, Integer>();
				for (int i = 0; i<CYCLES; i++){
					//System.out.println(generateTimeStamp());
					String timeStamp = generateTimeStamp();
					if (hm.containsKey(timeStamp)){
						System.out.println(
						"Duplicate time stamps generated after "+i+" cycles.");
						DUPLICATES++;
					}
					hm.put(timeStamp, 1);
					if (validateTimeStamp(timeStamp) == false)
						System.out.println("Failed regex on: "+timeStamp);
				}
			}
			int uniques = SETS*CYCLES-DUPLICATES;
			System.out.println(	uniques+" unique timestamps out of "+
								SETS*CYCLES + " generated.");
			double percentage = uniques / ((SETS*CYCLES)/new Double(100));
			System.out.printf("Reliability: %1f%%\n", percentage);

			System.out.println(FILLER+"----------------");
		} else
			System.out.print("[SKIPPED]\n");
		
		/** @test getDayName() responses for the number range [0-8]. */
		System.out.print("-[TEST14]"+FILLER);
		boolean passT14 = true;
		if (!SystemDate.getDayName(0).equals
				("SD: getDayName(): Invalid day number."))
			passT14 = false;
		if (!SystemDate.getDayName(1).equals("Sunday"))
				passT14 = false;
		if (!SystemDate.getDayName(2).equals("Monday"))
			passT14 = false;
		if (!SystemDate.getDayName(3).equals("Tuesday"))
			passT14 = false;
		if (!SystemDate.getDayName(4).equals("Wednesday"))
			passT14 = false;
		if (!SystemDate.getDayName(5).equals("Thursday"))
			passT14 = false;
		if (!SystemDate.getDayName(6).equals("Friday"))
			passT14 = false;
		if (!SystemDate.getDayName(7).equals("Saturday"))
			passT14 = false;
		if (!SystemDate.getDayName(8).equals
				("SD: getDayName(): Invalid day number."))
			passT14 = false;
		
		if (passT14)
			System.out.println("[PASSED]");
		else 
			System.out.println("[FAILED]!");
		
		/** @test convertDateToDateTime() with a date without time. */
		System.out.print("-[TEST15]"+FILLER);
		Date dd = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			dd = sf.parse("2000-02-03");
		} catch (ParseException e) {
			System.err.print("[TEST INITIALIZATION FAILURE]");
		}
		if(convertDateToDateTime(dd).toString().equals("2000-02-03 00:00:00"))
			System.out.println("[PASSED]");
		else
			System.out.println("[FAILED]!");
	}

}
