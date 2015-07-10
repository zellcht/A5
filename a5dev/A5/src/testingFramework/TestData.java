/**
 * @file TestData.java
 * @author Codrin Morhan
 * @date 26/02/12
 * @brief Testing class
 */

/**
 * @package testingFramework
 * @brief Helper tools for easy testing.
 */
package testingFramework;

import digitalOrganiser.DigitalOrganiser;
import events.*;
import addressBook.*;

/**
 * @brief Provider of testing tools.
 * 
 * Has methods for filling in event lists, contact lists, enumerating them.
 * @author Codrin Morhan
 */
public class TestData {
	/**
	 * Goes through the global event list and prints the event titles.
	 * @return True on success, false on failure.
	 */
	public boolean enumerateEvents(){
		for(int i=0;i<DigitalOrganiser.getEventList().getSize();i++)
			System.out.println(
					DigitalOrganiser
					.getEventList()
					.getEvent(i)
					.getTitle()
			);
		return true;
	}

	/**
	 * Automatically populates an event list with events.
	 * @param insertions Number of events to add.
	 * @return The filled event list.
	 */
	public static EventList getFilledEventList(int insertions){
		EventList el = new EventList();
		addDeadline(el);
		for (int i=1; i<insertions;i++){
			Event ev = new Event();
			ev.setToDefaultValues();
			ev.setTitle("Filler event #"+(i+1));
			ev.setDescription("ID: "+ev.getID());
			el.addEvent(ev.getAsStingArray());
		}
		return el;
	}
	
	/**
	 * Automatically populates an address book with contacts.
	 * @param insertions Number of contacts to add.
	 * @return The filled address book.
	 */
	public static AddressBook getFilledAddressBook(int insertions){
		AddressBook ab = new AddressBook();
		for (int i=0; i<insertions;i++){
			Contact c = new Contact();
			c.setFirstName(intToString(i+1));
			ab.addContact(c);
		}
		return ab;
	}
	
	/**
	 * Adds the assignment 5 deadline to an event list.
	 */
	public static void addDeadline(EventList EL){
		Event e = new Event();
		e.setTitle("Assignment 5 Deadline");
		e.setCategory("Work Deadline");
		e.setLocation("Online & 2nd Floor Faraday Building");
		e.setDescription("It is not compatible with " +
				"Hofstadter's law for the assignment.");
		e.setStartingDateTime("2012-04-02 11:00:00");
		e.setEndingDateTime("2012-04-02 11:00:00");
		EL.addEvent(e);
	}
	
	/**
	 * Test data loader. Loads complete contacts and events into the program.
	 */
	private static void loadTestData(){
		Event e1 = new Event();
		e1.setTitle("My Social Event");
		e1.setCategory("Social Event");
		e1.setLocation("Somewhere");
		e1.setDescription("No description");
		e1.setStartingDateTime("2012-02-01 01:01");
		e1.setEndingDateTime("2012-02-01 12:01");
		DigitalOrganiser.getEventList().addEvent(e1);
		
		Event e2 = new Event();
		e2.setTitle("My Happy Hour");
		e2.setCategory("Happy Hour");
		e2.setLocation("Somewhere else");
		e2.setDescription("No description");
		e2.setStartingDateTime("2012-02-02 01:01");
		e2.setEndingDateTime("2012-02-02 02:01");
		DigitalOrganiser.getEventList().addEvent(e2);
		
		Event e3 = new Event();
		e3.setTitle("My Lecture");
		e3.setCategory("Lecture");
		e3.setLocation("College");
		e3.setDescription("Must pay attention to it.");
		e3.setStartingDateTime("2012-02-03 01:00");
		e3.setEndingDateTime("2012-02-03 01:51");
		DigitalOrganiser.getEventList().addEvent(e3);
		

		
		/* Must be reworked by whoever is interested in testing AB
		c1.setName("Adewale");
		c1.setAddress("Code Land");
		c1.setEmail("email@email.com"); 
		c1.setFaxNo("0123456789");
		c1.setHomeTel("0123456789"); 
		c1.setOther("Other info");
		c1.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c1);
		c2.setName("Ceris"); 
		c2.setAddress("Code Land");
		c2.setEmail("email@email.com");
		c2.setFaxNo("0123456789");
		c2.setHomeTel("0123456789");
		c2.setOther("Other info");
		c2.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c2);
		c3.setName("Codrin");
		c3.setAddress("Code Land");
		c3.setEmail("email@email.com");
		c3.setFaxNo("0123456789");
		c3.setHomeTel("0123456789"); 
		c3.setOther("Other info");
		c3.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c3);
		c4.setName("Lloyd");
		c4.setAddress("Code Land");
		c4.setEmail("email@email.com");
		c4.setFaxNo("0123456789");
		c4.setHomeTel("0123456789"); 
		c4.setOther("Other info");
		c4.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c4);
		c5.setName("Samuel");
		c5.setAddress("Code Land");
		c5.setEmail("emai@emai.com");
		c5.setFaxNo("0123456789");
		c5.setHomeTel("0123456789"); 
		c5.setOther("Other info");
		c5.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c5);
		c6.setName("Simon");
		c6.setAddress("Code Land");
		c6.setEmail("emai@emai.com");
		c6.setFaxNo("0123456789");
		c6.setHomeTel("0123456789"); 
		c6.setOther("Other info");
		c6.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c6);
		c7.setName("Yan");
		c7.setAddress("Code Land");
		c7.setEmail("emai@emai.com");
		c7.setFaxNo("0123456789");
		c7.setHomeTel("0123456789"); 
		c7.setOther("Other info");
		c7.setURL("http://www.example.com");
		DigitalOrganiser.getAddressBook().addContact(c7);
		*/
	}
	
	/**
	 * Converts a numeric value to words.
	 * @param number Number to be converted.
	 * @return The number's value in words, with no spaces in between.
	 */
	private static String intToString(int number){
		String numberString = Integer.toString(number);
		System.out.println("Got number string:"+numberString);
		String numberWords = "";
		for (int i=0;i<numberString.length();i++)
			switch (numberString.charAt(i)){
				case '0':
					numberWords+="Zero ";
					break;
				case '1':
					numberWords+="One ";
					break;
				case '2':
					numberWords+="Two ";
					break;
				case '3':
					numberWords+="Three ";
					break;
				case '4':
					numberWords+="Four ";
					break;
				case '5':
					numberWords+="Five ";
					break;
				case '6':
					numberWords+="Six ";
					break;
				case '7':
					numberWords+="Seven ";
					break;
				case '8':
					numberWords+="Eight ";
					break;
				case '9':
					numberWords+="Nine ";
					break;
				default:
					break;
			}
		//System.out.println("Returned word: "+numberWords);
		return numberWords.trim();
	}
	
}
