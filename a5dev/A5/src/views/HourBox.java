/**
 * @file HourBox.java
 * @author Lloyd Woodroffe
 * @date 23/03/2012
 * @brief Contains the HourBox class.
 */

package views;

import hirondelle.date4j.DateTime;

import java.awt.Dimension;
import dateAndTime.SystemDate;
import dateAndTime.SystemTime;
import digitalOrganiser.DigitalOrganiser;

import events.Event;
import events.EventList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
/**
 * It displays events that match the specified date down to the hours.<br/>
 * It supports repeating events as well.<br/>
 * @author Lloyd Woodroffe
 * @brief Displays events that occur in a specified hour. 
 */
@SuppressWarnings("serial")
public class HourBox extends DayBox{

	/**
	 * Checks if the event occurs on the date and hour of the HourBox.
	 * @param e The event.
	 * @return True if date and hour of the event is the same with the one for the HourBox, 
	 * false otherwise.
	 */
	protected boolean checkEventDateTime(Event e){
		//System.out.println("HourBox: checkEventDateTime(): called by " + 
				//getClass().getName());
		DateTime s = e.getStartingDateTime();
		DateTime d = getDate();
		if(super.checkEventDateTime(e)){
			if (getDebugStatus())
			   System.out.println("HB: checkEventDateTime(): Date is good");
			if (SystemTime.hourEqual(s, d)){
				if (getDebugStatus())
					System.out.println("HB: checkEventDateTime(): " +
							"Hours are good");
				return true;
			}
		}
		else
			return false;
		return false;
	}
	
	/**
	 * The main method which tests the class. 
	 * @param args The command line arguments
	 */
	public static void main (String args[]){
		
		EventList evl = new EventList();
        /** @test adding 20 events that should show up. */
        for(int i = 0; i < 2; i++){
	        Event e = new Event();
	        e.setToDefaultValues(); 
	        e.setTitle(e.getStart().substring(11));
	        evl.addEvent(e);
        }
        

        /** @test adding an event that should not show up. */
        Event e = new Event();
        e.setDebugStatus(true);
        e.setToDefaultValues();        
        e.setStartingDateTime("2012-03-27 17:00:00");
        e.setTitle(e.getStart());
        System.out.println("Event set to : "+e.getStart());
        evl.addEvent(e);
        
        
        int YEARS=0,MONTHS=0,DAYS=0,HOURS=0,MINUTES=0,SECONDS=0;     
        
        /** @test adding a daily occurring event. Must show up. */
		Event e1 = new Event();
		DAYS=1;
        DateTime yesterday = SystemDate.getCurrentDateTime().minusDays(1);
        System.out.println("Daily event start time : "+yesterday.toString());
        e1.setStartingDateTime(yesterday.toString());
        e1.setRepetition("Daily");
        e1.setTitle(e1.getRepetition());
        evl.addEvent(e1);
        
        /** @test adding a weekly occurring event. Must show up. */
		Event e2 = new Event();
		DAYS=7;
        DateTime todayLastWeek = SystemDate.getCurrentDateTime().minus
        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
        				DateTime.DayOverflow.Spillover);
        e2.setStartingDateTime(todayLastWeek.toString());
        e2.setRepetition("Weekly");
        e2.setTitle(e2.getRepetition());
        evl.addEvent(e2);
        
        /** @test adding a monthly occurring event. Must show up. */
        Event e3 = new Event();
        DAYS = 0; MONTHS=2;
        DateTime todayLastMonth = SystemDate.getCurrentDateTime().minus
        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
        				DateTime.DayOverflow.LastDay);
        e3.setStartingDateTime(todayLastMonth.toString());
        e3.setRepetition("Monthly");
        e3.setTitle(e3.getRepetition());
        evl.addEvent(e3);
        
        /** @test adding a yearly occurring event. Must show up. */
        Event e4 = new Event();
        DAYS = 0; MONTHS=0; YEARS=1;
        DateTime todayLastYear = SystemDate.getCurrentDateTime().minus
        		(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, 
        				DateTime.DayOverflow.LastDay);
        e4.setStartingDateTime(todayLastYear.toString());
        e4.setRepetition("Yearly");
        e4.setTitle(e4.getRepetition());
        evl.addEvent(e4);
        
		DigitalOrganiser.setEventList(evl);
		
		/** @test Displaying the view. */
		HourBox hb = new HourBox();
		hb.setDebugStatus(true);
		hb.setEventList(DigitalOrganiser.getEventList());
		hb.setDate(SystemDate.getCurrentDateTime());
		//db.empty();
		JFrame frame = new JFrame();
		frame.add(hb);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		final int WIDTH = 500, HEIGHT = 500;
		frame.setSize(new Dimension(WIDTH, HEIGHT));
	}
}
