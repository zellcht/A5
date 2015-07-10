/**
 * @file SystemTime.java
 * @author Ceris Land
 * @date 22/02/2012
 * @brief Contains the SystemTime class
 */

/** 
 * @package dateAndTime
 * @brief Utilities for working with DateTime, java Date. 
 */
package dateAndTime;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * @brief Utility class for working with time.
 * 
 * Contains tools for working with DateTime objects regarding time.
 * @author Ceris Land
 * @see <a href="http://cs.swan.ac.uk/~csbob/teaching/cs235-softEng/success/Group7designReport.pdf">Group7designReport.pdf</a> , page 17
 * @see <a href="http://www.date4j.net">Date4J weblink</a>
 */
public class SystemTime {

	/**
     * Checks if a date has hour, minutes and seconds.
     * @param time Time to be checked. Expected format: <tt>13:00:00</tt> or <tt>2000-01-30 13:00:00</tt>.
     * @return True if the time has all required fields, false otherwise.
     */
	public boolean validateTime(DateTime time){
		try{
            time.getHour();
          //doing it twice due to it not failing if did only once
            time.getHour();
        } catch (Exception e) {
            System.err.println("ST: validateTime(): "+
                    "Could not get hours from: "+time.toString());
            return false;
        };
        try{
            time.getMinute();
        }catch (Exception e) {
            System.err.println("ST: validateTime(): "+
                    "Could not get minutes from: "+time.toString());
            return false;
        };
        try{
            time.getSecond();
        }catch (Exception e) {
            System.err.println("ST: validateTime(): "+
                    "Could not get seconds from: "+time.toString());
            return false;
        };

        if (time.getHour() == null){
            System.err.println("ST: validateTime(): "+
                "Date incomplete: does not contain an hour." );
            return false;
        }
        if (time.getMinute() == null){
        	System.err.println("ST: validateTime(): "+
                "Date incomplete: does not contain minutes." );
        	return false;
        }
        if (time.getSecond() == null){
        	System.err.println("ST: validateTime(): "+
                "Date incomplete: does not contain seconds." );
        	return false;
        }
        return true;
    }

	/**
     * Checks if a string can be converted to a valid time.
     * @param ti The time string.
     * @return True on success, false on failure.
     * @see validateTime()
     */
	public boolean checkTime(String ti){
		if (ti.isEmpty())
			return false;
		String dummyDateString = "1000";
		DateTime date = new DateTime(dummyDateString);

		try{date = new DateTime(ti);}
		catch (Exception e){
			System.err.println("ST: checkTime(): " +
					"Could not set time from: "+date.toString());
			return false;
		}
    
		if (validateTime(date))
            return true;
  
		return false; 
	}
	
	/**
	 * Takes two strings, converts them to dates and checks if the first date is after the last.
	 * @param t1 The string of the time to check.
	 * @param t2 The string of the time to check it's after.
	 * @return True if the first time is after the second, false otherwise.
	 */
	public boolean isTimeAfter(String t1, String t2){
		String dummyDateString = "1000";
		DateTime time1 = new DateTime(dummyDateString);
		DateTime time2 = new DateTime(dummyDateString);
	
		if(checkTime(t1) && checkTime(t2)){
			time1 = new DateTime(t1);
			time2 = new DateTime(t2);
		} else 
			return false;
	
		if(time1.gt(time2))
			return true;
	
		return false;
	}

	/**
	 * Checks if two dates have the same hour.
	 * @author Lloyd Woodroffe
	 * @param d1 The first date.
	 * @param d2 The second date.
	 * @return True if both dates have the same hour, false otherwise.
	 */
	public static boolean hourEqual (DateTime d1, DateTime d2){
		if (d1 == null || d2 == null)
			return  false;
		if(!(d1.hasHourMinuteSecond() && d2.hasHourMinuteSecond()))
			return false;
		int a = d1.getHour();
		int b = d2.getHour();
		//System.out.println(a + " vs " + b);
		if(a == b)
				return true;
		return false;
				 
	}

	/**
	 * Takes 3 strings, converts them to dates and checks if the first date is between the other two. 
	 * @param t1 The string of the time to be checked.
	 * @param t2 The string of the time for start of range.
	 * @param t3 The string of the time for end of range.
	 * @return True if the first date is between the other two, false otherwise.
	 */
	public boolean isTimeBetween(String t1, String t2, String  t3){
		String dummyDateString = "1000";
		DateTime time = new DateTime(dummyDateString);
		DateTime startTime = new DateTime(dummyDateString);
		DateTime endTime = new DateTime(dummyDateString);
	
		if(checkTime(t1) && checkTime(t2) && checkTime(t3)){
			time = new DateTime(t1);
			startTime = new DateTime(t2);
			endTime = new DateTime(t3);
		} else 
			return false;
	
		if(endTime.lt(startTime))
			return false;
	
		if(time.lt(startTime))
			return false;
	
		if(time.gt(endTime))
			return false;
	 
		return true;
	}
	
	/** 
	 * Gets the current system time.<br/>
	 * @return A string of the format <tt>13:00:00</tt>
	 * @author Adewale Odunlami
	 */
    public static String getCurrentTime(){
        DateTime dt = DateTime.now(TimeZone.getDefault());
        String hour = "00";
        if (dt.getHour() < 10)
        	hour = "0" + dt.getHour();
        else 
        	hour = "" + dt.getHour();
        String min = "00";
        if(dt.getMinute() <10)
        	min = "0" + dt.getMinute();  
        else
            min = "" + dt.getMinute();
        
        String sec = "00";
        if(dt.getSecond() <10)
        	sec = "0" + dt.getSecond();  
        else
            sec = "" + dt.getSecond();
        
        return hour + ":" + min + ":" + sec;
    }
    
    /**
     * Sets the time to zero for the date. 
     * @param dt The date.
     * @return The same date but with the time set to <tt>00:00:00</tt>
     */
    public static DateTime setTimeZero(DateTime dt){
    	if (dt == null)
    		return new DateTime("00:00:00");
    	if(!dt.hasHourMinuteSecond())
    		return new DateTime(dt+" 00:00:00");
    	String newDateTime = dt.toString().replaceAll
    			("[0-9]{2}:[0-9]{2}:[0-9]{2}", "00:00:00");
    	dt = new DateTime(newDateTime);
    	return dt;
    }

	/**
	 * Main method tests the class.
	 * @param args Arguments will be ignored.
	 */
	public static void main(String[] args){
		final String FILLER = "-----------------------------------------------"; 
		
		SystemTime tst = new SystemTime();
		
		/** @test checkTime() response with a valid time string. */
		System.out.print("test1----------------------");
		if(tst.checkTime("12:38:00"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test checkTime() response with an invalid time string. */
		System.out.print("test2----------------------");
		if(tst.checkTime("25:09:00"))
			System.out.println("Failed");
		else
			System.out.println("Passed");
			
		
		/** @test isTimeAfter() response with invalid time strings. */
		System.out.print("test3----------------------");
		if(tst.isTimeAfter("09:54:00", "10:00:00"))
			System.out.println("Failed");
		else
			System.out.println("Passed");

		/** @test isTimeAfter() response with valid time strings. */
		System.out.print("test4----------------------");
		if(tst.isTimeAfter("20:17:00", "11:22:00"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isTimeBetween() response with valid time strings. */
		System.out.print("test5----------------------");
		if(tst.isTimeBetween("07:15:00", "05:39:00", "19:10:00" ))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		/** @test isTimeBetween() response with invalid time strings. */
		System.out.print("test6----------------------");
		if(tst.isTimeBetween("15:00:00", "14:30:00", "18:00:00" ))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
        /** @test Displaying the current time; testing it with checkTime(). */
        System.out.println("test7----------------------");
        System.out.println("The current time is: "+SystemTime.getCurrentTime());
        System.out.println("Current time is a valid time: "+
        		tst.checkTime(SystemTime.getCurrentTime()));
	  
        /** @test hourEqual() with valid, day unequal but hour equal dates. */ 
        System.out.print("test8----------------------");
        DateTime d1 = new DateTime("2012-03-26 00:10:28");      
        DateTime d2 = new DateTime("2012-03-28 00:10:28");  
        //System.out.println(SystemTime.hourEqual(d1, d2));
        if (SystemTime.hourEqual(d1, d2))
			System.out.println("Passed");
		else
			System.out.println("Failed");

        /** @test setTimeZero() on a valid date. */ 
	    System.out.print("test9----------------------");
        DateTime d9 = new DateTime("2012-03-26 11:10:28");   
        //System.out.println(  SystemTime.setTimeZero(d9).toString());
		if(SystemTime.setTimeZero(d9).toString().equals("2012-03-26 00:00:00"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
	    
	    /** @test validateDateTime() a date that has no date part, only time. */
	    System.out.print("-[TEST10]"+FILLER);
	    DateTime dt10 = new DateTime("00:00:00");
	    //System.out.println(dt10.toString());
	    if (tst.validateTime(dt10))
	    	System.out.println("[PASSED]");
	    else 
	    	System.out.println("[FAILED]!");
	    
		}

	

	
}
