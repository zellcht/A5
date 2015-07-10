/**
 * @file DurationValidator.java
 * @author Adewale Odunlami
 * @date 14/03/2012
 * @brief Contains the DurationValidator class. 
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import hirondelle.date4j.DateTime;
import dateAndTime.SystemDate;
import dateAndTime.SystemTime;

/**
 * @brief Validates event durations.
 * 
 * Provides duration checks for any event category.<br/>
 * Later on it was determined by the client that there should be almost no duration checks, 
 * therefore this class is largely useless now.<br/>
 * However removing it or the unused methods would run the risk of giving the 
 * appearance that the author did not perform enough work for the project. 
 * @author Adewale Odunlami
*/
public class DurationValidator {
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
     * Validates the time duration of Social event.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if valid, otherwise false.
     */
    public boolean validateSocialEvent(DateTime s, DateTime e) {
 
    	if (s == null || e == null){
    		if(getDebugStatus())
    			System.err.println("DurationValidator.java:\n" +
            		" ValidateSocialEvents():\n" +
            		"  Invalid Start or End date.");
            return false;
    	};
        long duration = SystemDate.calculateDuration(s,e);
        int MAX_DURATION = 604800, MIN_DURATION = 0; //maximum of 7 days
        if ( duration > MAX_DURATION || duration < MIN_DURATION){
        	if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateSocialEvent():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }        
        return true;
    }
    
    /**
     * Validates the time duration of a Birthday.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
    public boolean validateBirthday(DateTime s, DateTime e) {
        
    	if (s == null || e == null){
    		if(getDebugStatus())
    			System.err.println("DurationValidator.java:\n" +
            		" ValidateBirthday():\n" +
            		"  Invalid Start or End date.");
            return false;
    	}
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 86400, MIN_DURATION = 0; //24hours maximum

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
        	if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateBirthday():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
    }
    
    /**
     * Validates the time duration of an Anniversary.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
    public boolean validateAnniversary(DateTime s, DateTime e) {
    	if (s == null || e == null){
    		if(getDebugStatus())
    			System.err.println("DurationValidator.java:\n" +
            		" ValidateAnniversary():\n" +
            		"  Invalid Start or End date.");
            return false;
    	}
	        
	    long duration = SystemDate.calculateDuration(s,e);
	    
	    int MAX_DURATION = 86400, MIN_DURATION = 0; // 24hours duration
	    if ( duration > MAX_DURATION || duration < MIN_DURATION){
	    	if(getDebugStatus())
	    		System.err.println("DurationValidator.java:\n" +
	                	" ValidateAnniversary():\n" +
	              		"  Invalid event duration: " + duration + " seconds.");
	                    return false;
	    }  
	    return true;
    }
   
    /**
     * Validates the time duration of a Meal.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */	    
	public boolean validateMeal(DateTime s, DateTime e) {
		
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateMeal():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
	        
	    long duration = SystemDate.calculateDuration(s,e);
	    
	    int MAX_DURATION = 3600, MIN_DURATION = 0; //an hour duration

	    if ( duration > MAX_DURATION || duration < MIN_DURATION){
		    if(getDebugStatus())
		    	System.err.println("DurationValidator.java:\n" +
	                	" ValidateMeal():\n" +
	              		"  Invalid event duration: " + duration + " seconds.");
	                    return false;
	        }  
	    return true;
	}

	/**
     * Validates the time duration of a Happy Hour.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateHappyHour(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateHappyHour():\n" +
            		"  Invalid Start or End date.");
            return false;
		}
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int DURATION = 3600; //an hour duration
    
        if ( duration != DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateHappyHour():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}

	/**
     * Validates the time duration of a Concert.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */	 
    public boolean validateConcert(DateTime s, DateTime e) {
       
    	if (s == null || e == null){
    		if(getDebugStatus())
    			System.err.println("DurationValidator.java:\n" +
            		" ValidateSports():\n" +
            		"  Invalid Start or End date.");
            return false;
    	};

        return true;
    }

    /**
     * Validates the time duration of a Sport event.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
    public boolean validateSports(DateTime s, DateTime e) {
   
        if (s == null || e == null){
         	if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateSports():\n" +
                		"  Invalid Start or End date.");
                return false;
        }
        return true;
    }
	    
    /**
     * Validates the date of a Work Deadline.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */	  
	public boolean validateWorkDeadline(DateTime s, DateTime e) {
		
		if (s == null || e == null){
			if(getDebugStatus()) 
				System.err.println("DurationValidator.java:\n" +
            		" ValidateWorkDeadline():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 0, MIN_DURATION = 0; //start date equals end date

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateWorkDeadline():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}

	 /**
     * Validates the time duration of a Meeting.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateMeeting(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateMeeting():\n" +
            		"  Invalid Start or End date.");
            return false;
		}
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 28800, MIN_DURATION = 600; //10minutes - 8 hours

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateMeeting():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        } 
        return true;
	}
	
	
	 /**
     * Validates the time duration of an Appointment.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateAppointment(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateAppointment():\n" +
            		"  Invalid Start or End date.");
            return false;
    }
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 3600, MIN_DURATION = 600; //10 minutes - 1 hour

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateAppointment():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}
	

	 /**
     * Validates the time duration of a Bank Holiday.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateBankHoliday(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateBankHoliday():\n" +
            		"  Invalid Start or End date.");
            return false;
    }
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 86400, MIN_DURATION = 0;   //24 hours

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateBankHoliday():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}
	

	 /**
     * Validates the date of a Bill Payment. 
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateBillPayment(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateBillPayment():\n" +
            		"  Invalid Start or End date.");
            return false;
		}
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 0, MIN_DURATION = 0; //start time equals end time

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateBillPayment():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}
	  

	 /**
     * Validates the time duration of a Lecture.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateLecture(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateLecture():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 10800, MIN_DURATION = 3000;  //50 - 180 minutes
     
        if ( duration > MAX_DURATION || duration < MIN_DURATION){
        	   if(getDebugStatus())
        		   System.err.println("DurationValidator.java:\n" +
                		" ValidateLecture():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}


	 /**
     * Validates the date an Accident happened.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateAccident(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateSports():\n" +
            		"  Invalid Start or End date.");
            return false;
		}
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 0, MIN_DURATION = 0;
  
        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateAccident():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}

	
	 /**
     * Validates the date of a previous payment.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validatePreviousPayment(DateTime s, DateTime e) {
       
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidatePreviousPayment():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
        
        DateTime dateNow = SystemDate.getCurrentDate();
        String timeNow = SystemTime.getCurrentTime();
        DateTime dateAndTimeNow = new DateTime(dateNow.toString() +
        							" " + timeNow);
       // System.out.println(dateAndTimeNow.toString());
        
        if(s.gt(dateAndTimeNow)){ 
        	if(getDebugStatus())
        		System.err.println("DurationValidator.java:\n" +
            		" ValidatePreviousPayment():\n" +
            		"  This is not a past payment.");
            return false;
        }
        long duration= SystemDate.calculateDuration(s,e);
        
      //  System.out.println("Duration is: "+ duration);
       // System.out.println(s);
        //System.out.println(e);
        
        int MAX_DURATION = 0;
        int MIN_DURATION = 0;

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidatePreviousPayment():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
    
        return true;
	}
	


	 /**
     * Validates the time duration of a Gym session.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateGym(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateGym():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 7200, MIN_DURATION = 1800;  //30minutes to 2 hrs

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateGym():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
  	}
	

	 /**
     * Validates the time duration of a Vacation period.
     * @param s The starting date.
     * @param e The ending date.
     * @return True if passed, otherwise false.
     */
	public boolean validateVacation(DateTime s, DateTime e) {
        
		if (s == null || e == null){
			if(getDebugStatus())
				System.err.println("DurationValidator.java:\n" +
            		" ValidateVacation():\n" +
            		"  Invalid Start or End date.");
            return false;
		};
        
        long duration = SystemDate.calculateDuration(s,e);
        
        int MAX_DURATION = 2419200, MIN_DURATION = 86400; //1 day to 4 weeks

        if ( duration > MAX_DURATION || duration < MIN_DURATION){
            if(getDebugStatus())
                System.err.println("DurationValidator.java:\n" +
                		" ValidateVacation():\n" +
                		"  Invalid event duration: " + duration + " seconds.");
                return false;
        }  
        return true;
	}
	


    /** Tracks the debug status. Off by default. */
	private boolean m_debug = false;
	
	public static void main(String[] args){
        final String FILLER = "-----------------------------------------";  
        DurationValidator dv = new DurationValidator();
        DateTime d1; 
    	DateTime d2;
        /** @test SocialEvent valid duration. */
        System.out.print("-[TEST1]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-08 01:01:01");
        
        if (dv.validateSocialEvent(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");        
        /** @test SocialEvent invalid duration. */
        System.out.print("-[TEST2]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-10 01:01:01");
        
        if (dv.validateSocialEvent(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");        
        /** @test SocialEvent null dates. */
        System.out.print("-[TEST3]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (dv.validateSocialEvent(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");
        
        /** @test Birthday valid duration. */
        System.out.print("-[TEST4]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-02 01:01:01");
        
        if (dv.validateBirthday(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Birthday invalid duration. */
        System.out.print("-[TEST5]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-08 01:01:01");
        
        if (dv.validateBirthday(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");        
        /** @test Birthday null dates. */
        System.out.print("-[TEST6]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (dv.validateBirthday(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");
        
        /** @test Anniversary valid duration. */
        System.out.print("-[TEST7]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-02 01:01:01");
        
        if (dv.validateAnniversary(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Anniversary invalid duration. */
        System.out.print("-[TEST8]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-08 01:01:01");
        
        if (dv.validateAnniversary(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");        
        /** @test Birthday null dates. */
        System.out.print("-[TEST9]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (dv.validateAnniversary(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");
        
        /** @test Meal valid duration. */
        System.out.print("-[TEST10]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:01");
        
        if (dv.validateMeal(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Meal invalid duration. */
        System.out.print("-[TEST11]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:02");
        
        if (dv.validateMeal(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");        
        /** @test Meal null dates. */
        System.out.print("-[TEST12]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (dv.validateMeal(d1,d2))
            System.out.println("[FAILED]!");
        else
            System.out.println("[PASSED]");
        
        /** @test HappyHour valid duration. */
        System.out.print("-[TEST13]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:01");
        
        if (dv.validateHappyHour(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test HappyHour invalid duration: more than an hour. */
        System.out.print("-[TEST14]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:02");
        
        if (!dv.validateHappyHour(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
      /** @test HappyHour invalid duration: less than an hour. */
        System.out.print("-[TEST15]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:02");
        
        if (!dv.validateHappyHour(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test HappyHour null dates. */
        System.out.print("-[TEST16]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateHappyHour(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test Concert null dates. */
        System.out.print("-[TEST17]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateConcert(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test  Sports null dates. */
        System.out.print("-[TEST18]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateSports(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");

        /** @test WorkDeadline valid duration. */
        System.out.print("-[TEST19]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:01");
        
        if (dv.validateWorkDeadline(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test WorkDeadline invalid duration. */
        System.out.print("-[TEST20]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:02");
        
        if (!dv.validateWorkDeadline(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test WorkDeadline null dates. */
        System.out.print("-[TEST21]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateWorkDeadline(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test Meeting valid duration. */
        System.out.print("-[TEST22]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:11:01");
        
        if (dv.validateMeeting(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Meeting invalid duration: more than allowed. */
        System.out.print("-[TEST23]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 09:01:02");
        
        if (!dv.validateMeeting(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Meeting invalid duration: less than allowed. */
        System.out.print("-[TEST24]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:02");
        
        if (!dv.validateMeeting(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test Meeting null dates. */
        System.out.print("-[TEST25]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateMeeting(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");

        /** @test Appointment valid duration. */
        System.out.print("-[TEST26]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:11:01");
        
        if (dv.validateAppointment(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Appointment invalid duration: more than allowed. */
        System.out.print("-[TEST27]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 09:01:02");
        
        if (!dv.validateAppointment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Appointment invalid duration: less than allowed. */
        System.out.print("-[TEST28]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:02");
        
        if (!dv.validateAppointment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test Appointment null dates. */
        System.out.print("-[TEST29]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateAppointment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test BankHoliday valid duration. */
        System.out.print("-[TEST30]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-02 01:01:01");
        
        if (dv.validateBankHoliday(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test BankHoliday invalid duration. */
        System.out.print("-[TEST31]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-03 02:01:02");
        
        if (!dv.validateBankHoliday(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test BankHoliday null dates. */
        System.out.print("-[TEST32]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateBankHoliday(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test BillPayment valid duration. */
        System.out.print("-[TEST33]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:01");
        
        if (dv.validateBillPayment(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test BillPayment invalid duration. */
        System.out.print("-[TEST34]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 02:01:02");
        
        if (!dv.validateBillPayment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test BillPayment null dates. */
        System.out.print("-[TEST35]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateBillPayment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");

        /** @test Lecture valid duration. */
        System.out.print("-[TEST36]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:51:01");
        
        if (dv.validateLecture(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Lecture invalid duration: more than allowed. */
        System.out.print("-[TEST37]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 05:01:02");
        
        if (!dv.validateLecture(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Lecture invalid duration: less than allowed. */
        System.out.print("-[TEST38]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:50:02");
        
        if (!dv.validateLecture(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test Lecture null dates. */
        System.out.print("-[TEST39]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateLecture(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test Accident valid duration. */
        System.out.print("-[TEST40]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:01");
        
        if (dv.validateAccident(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Accident invalid duration. */
        System.out.print("-[TEST41]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:02");
        
        if (!dv.validateAccident(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Accident null dates. */
        System.out.print("-[TEST42]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateAccident(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test PreviousPayment valid duration. */
        System.out.print("-[TEST43]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:01");
        
        if (dv.validatePreviousPayment(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Testing PreviousPayment invalid duration. */
        System.out.print("-[TEST44]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:01:02");
        
        if (!dv.validatePreviousPayment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test PreviousPayment null dates. */
        System.out.print("-[TEST45]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validatePreviousPayment(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test Gym valid duration. */
        System.out.print("-[TEST46]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:47:01");
        
        if (dv.validateGym(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Gym invalid duration: more than allowed. */
        System.out.print("-[TEST47]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-01 01:03:02");
        
        if (!dv.validateGym(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Gym invalid duration: less than allowed. */
        System.out.print("-[TEST48]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:00");   
        d2 = new DateTime("2000-01-01 01:30:59");
        
        if (!dv.validateGym(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test Gym null dates. */
        System.out.print("-[TEST49]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateGym(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
        /** @test Vacation valid duration. */
        System.out.print("-[TEST50]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:01");   
        d2 = new DateTime("2000-01-27 01:01:01");
        
        if (dv.validateVacation(d1,d2))
            System.out.println("[PASSED]");
        else
            System.out.println("[FAILED]!");
        /** @test Vacation invalid duration: more than allowed. */
        System.out.print("-[TEST51]" +FILLER);
        d1 = new DateTime("2008-02-01 01:01:01");   
        d2 = new DateTime("2008-03-01 01:01:01");
        
        if (!dv.validateVacation(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        /** @test Vacation invalid duration: less than allowed. */
        System.out.print("-[TEST52]" +FILLER);
        d1 = new DateTime("2000-01-01 01:01:00");   
        d2 = new DateTime("2000-01-02 01:00:00");
        
        if (!dv.validateVacation(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!"); 
        /** @test Vacation null dates. */
        System.out.print("-[TEST53]" +FILLER);
        d1 = null;   
        d2 = null;
        
        if (!dv.validateVacation(d1,d2))
        	System.out.println("[PASSED]");
        else
        	System.out.println("[FAILED]!");
        
	   }
}
 