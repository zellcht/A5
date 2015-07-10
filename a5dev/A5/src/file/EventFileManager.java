/**
 * \file EventFileManager.java
 * \author Simon Maling
 * \date 14/03/12
 * \see http://opencsv.sourceforge.net
 * \brief The Event Csv File manager which allows the user to create a an 
 * event CSV file, using the constructor, and to load and save from it.
 */

/**
 * @package file
 * @brief The package containing the file managers.
 */
package file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;

import javax.swing.JOptionPane;

import events.Event;
import events.EventList;
import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser; 
import addressBook.Contact;
import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Simon Maling
 * @brief This class handles creating the file, loading from it and writing to it.
 * 
 * This class extends the CsvFile to use its variables, constructor,
 * and accessor methods.
 * It allows the user to create an Event Csv file, to load from it,
 * or save the events to the file.
 */
public class EventFileManager extends FileManager{
	
	/**
	 * This is the accessor method for m_FileName.
	 * @param name The name the file object will be set to.
	 * @return True if set correctly.
	 */
	public boolean setFileName(String name){
		super.setFileName(name);
		return true;
	}
	
	/**
	 * This is the constructor for EventCsvFile, it calls the superclass
	 * Constructor with the parameter file name and then sets the file name
	 * of the object.
	 * @param name The name of the file, the object will be set to.
	 */
	public EventFileManager(String name){
		super(name);
		try{
			if(!this.getFile().exists()){
				System.out.println("Creating the file");
				this.getFile().createNewFile();
				this.writeHeader();
			} else{
				this.isEOFBlank();
			}
		} catch (IOException ioe){
			System.out.println("Exception caught, the exception was: " + ioe);
		}
	}
	
	/**
	 * This is the constructor for EventCsvFile, it calls the superclass
	 * Constructor with the default file name and then sets the file name
	 * of the object.
	 */
	public EventFileManager(){
		this(FILE_NAME);
	}
	
	/**
	 * This method loads the events details from the csv file, 
	 * then creates an event object and sets the objects fields
	 * to the fields loaded.
	 * Finally it adds the event to the event list.
	 * Accepts a max of 1,000 events.
	 * @return True if loaded successfully.
	 */
	public boolean load(){
		//This shows whether the file was loaded succesfully.
		boolean success = true;
		//Try catching IO exceptions.
		try{
			//Initialise the filereader,CSVReader objects.
			FileReader fileReader = new FileReader(this.getFile());
			CSVReader reader = new CSVReader(fileReader);
			StringArrayComparer csa = new StringArrayComparer();
			
			String[] headerArray = reader.readNext();
			
			//If the loaded header does not equal the expected header.
			if(!(csa.equalArrays(headerArray, 
					Event.getHeaderStringArray()))){
				//Show the error and copy the file.
				this.showFileError();
				this.copyFile();
			} else {
				//add the event to the event list if it has the correct numbers
				//of fields.
				addToEventList(reader);
			
				//Close the reader and writer.
				reader.close();
				fileReader.close();
				
			}
		} catch (IOException ioe){
			System.out.println("IOException caught, the exception was: " + ioe);
			success = false; 
		} catch (SecurityException se){
			System.out.println("SecurityException caught, the exception was: "
				+ se);
			success = false;
		}
		
		return success;
		
	}
	
	/**
	 * This method writes the contents of the array list 
	 * @return True is written correctly.
	 */
	public boolean write(){
		//A boolean flag set to false if there is an error.
		boolean success = true;
		try{
			//Creating a file writer,CSVwriter and CVSParser object.
			//File writer will overwrite the file and not append to it.
			FileWriter fileWriter = new FileWriter(getFile(),false);
			CSVWriter writer = new CSVWriter(fileWriter);
			
			//Rewriting the header to the file.
			String[] headerArray = Event.getHeaderStringArray();
			writer.writeNext(headerArray);
			
			//This array stores the details of the event to be written to the file.
			String[] toWriteArray; 
			
			//For every event in the event list.
			for(int i=0; i<DigitalOrganiser.getEventList().getSize(); i++){
				//Get the array of the events details.
				toWriteArray = DigitalOrganiser.getEventList().
								getEvent(i).getAsStingArray();
				//Write the events fields to file.								
				writer.writeNext(toWriteArray);
			}	
			//Close the writers.
			writer.close();
			fileWriter.close();	
				
		} catch (IOException ioe){
			System.out.println("Caught IOException, the exception was: " + ioe);
			success = false;
		}
	
		return success;
	}
	
	/**
	 * This method will write a single event to the file.
	 * @param e This is the event object to be written.
	 * @return True if written correctly.
	 */
	public boolean write(Event e){
		boolean success = true;
		
		try{
		//Creating a file writer,CSVwriter and CVSParser object.
		//File writer will not overwrite the file and append to it.
		FileWriter fileWriter = new FileWriter(getFile(),true);
		CSVWriter writer = new CSVWriter(fileWriter);
		//This array stores the details of the event to be written to the file.
		String[] toWriteArray = e.getAsStingArray();

		//Write the events fields to file.								
		writer.writeNext(toWriteArray);
		
		//Close the writers.
		writer.close();
		fileWriter.close();	
			
		}catch(IOException ioe){
			System.out.println("IOException caught, the exception was: " + ioe);
			success = false;
		}
		
		return success;
	}
	
	/**
	 * This method tests if the event array loaded is of the correct size
	 * and if so passes it to the event list.
	 * It also lets you load a max of 1000 events.
	 * @param reader This read object created in load.
	 * @return True if executed correctly.
	 */
	private boolean addToEventList(CSVReader reader){
		boolean success = true;
		int loadedEvents = 0;
		int totalEvents = 0;
		
		try{
			//This is an array containing all the fields loaded from the file.
			String[] nextLineArray;
			//Reading the events from the file line by line.
			while (((nextLineArray = reader.readNext()) != null)
					& (loadedEvents < EventList.SIZE_LIMIT)){
				try{
					//If the array loaded is of the correct size.
					if(correctArraySize(nextLineArray)){
						//Add the event to the event list.
						if(DigitalOrganiser.getEventList().
								addEvent(nextLineArray)){
							loadedEvents ++;
						}
						totalEvents ++;
					} else {
						System.err.println("Loaded event has " +
								"not got the correct number of fields");
					}
				} catch(NullPointerException npe){
					System.out.println("NullPointerException caught, " +
							"the exception was: " + npe);
					success = false;
				}
				
		    }
			if (loadedEvents == EventList.SIZE_LIMIT){
				System.out.println(EventList.SIZE_LIMIT + " events loaded, " +
						"Cannot load anymore events");
				this.setMaxLoaded(true);
			}
			//If 1000 events have been loaded so some have been left off.
			if(this.getMaxLoaded()){
				this.showMaxError();
			}
			if(loadedEvents != totalEvents){
				showEventError(loadedEvents, totalEvents);
			}
		} catch(IOException ioe){
			System.out.println("IOException caught, the exception was " + ioe);
		}
		
		return success;

	}
	
	/**
	 * This method tells the user its copying the file, sets the
	 * corruptInputFile variable to true, then copies the old
	 * file to a new file, deletes the old file, recreates 
	 * it and then writes the header to it. 
	 * @return True if successful.
	 */
	private boolean copyFile(){
		boolean success = true;
		try{
			//Print error messages.
			System.out.println("Error: The header of the file is" 
				     + " not correct, ");
			System.out.println("the file will not be loaded.");
			System.out.println("The file has been save as a new " +
							   "file called,");
			System.out.println("ErroneousEvent.csv.");
			
			//Setting the fact that the input file is invalid.
			this.setCorruptInputFile(true);			
			
			//Copying the source file under the new name indicated by the
			//destination file.
			File src = new File ("Event.csv");
			File dst = new File ("ErroneousEvent.csv");
			dst.createNewFile();
			FileCopier c = new FileCopier();
			c.copy(src,dst);
			
			//Creating a new source file.
			src.delete();
			src.createNewFile();
			
			//Writing the header to the new file.
			this.writeHeader();
			
		} catch (IOException ioe){
			System.out.println("IOException caught, the exception was: " 
							  + ioe);
			success = false;
		}
		
		return success;
	}
	
	/**
	 * This method simply checks if the passed across array is of the expected size
	 * it uses the global variable from event to check this.
	 * @param sa The string array to be tested.
	 * @return True if correct size, otherwise false;
	 */
	private boolean correctArraySize(String[] sa){
		if(!(sa.length==Event.ARRAY_SIZE)){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * This method produces an error pane telling the user that not 
	 * all the events were loaded.
	 * @param loaded Number of events loaded.
	 * @param total Total numbers of events.
	 * @return True if executed correctly.
	 */
	public boolean showEventError(int loaded, int total){
		JOptionPane.showMessageDialog(null, 
				"Error some of the events were" + "\n" +
				"erroneous. Only " + loaded +
				" out of " + total + "\n" + 
				"were " + "loaded.",
				"Erroneous Event File"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	/**
	 * This method produces a error pane telling the user
	 * that the event file was erroneous and that is has been
	 * copied under a new name.
	 * @return True is executed correctly.
	 */
	private  boolean showFileError(){
		JOptionPane.showMessageDialog(null, 
				"The file is erroneous, it has been" + "\n" +
				"copied to ErroneousEvent.csv," + "\n" +
				"and a new empty file is being used.",
				"Erroneous Event File"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	/**
	 * This method produces a error pane telling the user
	 * that the event file is too large and that only the first
	 * 1000 events have been loaded.
	 * @return True if executed correctly.
	 */
	private boolean showMaxError(){
		JOptionPane.showMessageDialog(null, 
				"The file is too large.\n" +
				"Only the first "+
				EventList.SIZE_LIMIT+
				" events have been loaded.",
				"Event File Too Large"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	/**
	 * This method gets the header for the event class, then
	 * writes it to the csv file.
	 * @return True if executed correctly.
	 */
	private boolean writeHeader(){
		boolean success = true;;
		try{
			//Creating a file writer,CSVwriter and CVSParser object.
			//File writer will overwrite the file and not append to it.
			FileWriter fileWriter = new FileWriter(getFile(),false);
			CSVWriter writer = new CSVWriter(fileWriter);
			//This array stores the details of the event to be written to the file.
			String[] headerArray = Event.getHeaderStringArray();
	
			//Write the events fields to file.								
			writer.writeNext(headerArray);
			
			writer.close();
			fileWriter.close();
			
		} catch (IOException ioe){
			System.out.println("Exception caught, the exception was: " + ioe);	
			success = false;
		}
		
		return success;
	}
	
	/**
	 * This is the main method used for testing the class.
	 * It will test loading, writing creating the file.
	 * @param args Parameter for file input.
	 */
	public static void main(String[] args){
		
		/*
		 * The unit tests to be done on event file manager are as follows: 
		 * 1. Writing typical non erroneous data to the file.
		 * 2. Loading the data just written to the file and proving it is 
		 *    the same data that was written.
		 * 3. Creating and writing single events to the file, then loading them
		 *    again to prove they were written/loaded correctly.
		 * 4. Testing loading a file with an incorrect header.
		 * 5. Testing loading a file with extra collumns in it.
		 * 6. Testing loading a completely empty file.
		 * 7. Testing loading an extremely large file with over 1000 events.
		 * 8. Testing loading a erroneous file in the form of a JPG file which
		 *    is renamed to the csv format.
		 * 9. Testing loading a file which dosent exist. (Choosing a filename
		 *    of a file which dosent exist, and showing the file is created).
		 * 10.Testing an erroneous file, so the error message will tell the user
		 *    how many of the events have been loaded.   
		 * 
		 *	Erroneous data and random missing data (excluding headers) will
		 *  be dealt with by the EventList and event classes. EventFileManager
		 *  only does the basic checks then passes the data on, therefore test
		 *  cases for invalid data are covered by eventlist and event
		 *  not event file manager.
		 *  Empty collumns will fail the header check.
		 *  
		 */
		
		//Initialising the objects for testing purposes.
		System.out.println("Now testing Event File Manager.");
		System.out.println("First initialising the objects");
		final String FILLER = "-----------------------------------------";
		EventFileManager efm = new EventFileManager();
		DigitalOrganiser.setEventFileManager(efm);
		DigitalOrganiser.setEventList(new EventList());
		DigitalOrganiser.getEventList().setFileWritingMode(true);
		int firstEvent = 0;
		int secondEvent = 1;
		int thirdEvent = 2;
		int fourthEvent = 3;
		int fifthEvent = 4;
		
	///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 /////
		/** @test write() Writing using typical data */
		System.out.println("-[Test 1] " + FILLER);
		System.out.println("Testing writing to the file, using typical data");
		System.out.println("Creating 3 default events to write to the file");
		System.out.println("Leaving the 1st event default");
		System.out.println("Setting the seconds events repetition to daily");
		System.out.println("Setting the third events category to Social Event");
		DigitalOrganiser.getEventList().addEvent(new Event());
		DigitalOrganiser.getEventList().addEvent(new Event());
		DigitalOrganiser.getEventList().addEvent(new Event());
		DigitalOrganiser.getEventList().getEvent(secondEvent).
			setRepetition("Daily");
		DigitalOrganiser.getEventList().getEvent(thirdEvent).
			setCategory("Social Event");
		
		System.out.println("Now writing to the file");
		efm.write();
		System.out.println("The test is now complete and the " +
				"events have been written to the file");
		System.out.println("Test 2 will prove this");
		System.out.println();
		
	///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 /////
		/** @test load() Loading the data written in test 1 */
		System.out.println("-[Test 2] " + FILLER);
		System.out.println("Testing loading from the file");
		System.out.println("Loading the events added to the file in test 1");
		System.out.println("Clearing the event list");
		DigitalOrganiser.getEventList().clear();
		System.out.println("Now loading from the file");
		efm.load();
		System.out.println("The event list size is: " +
				DigitalOrganiser.getEventList().getSize());
		System.out.println("This shows all 3 events have been loaded");
		System.out.println("Printing out 3 random fields from the " +
				"events to prove they have been loaded: ");
		System.out.println("Event 1 ID is: " +
				DigitalOrganiser.getEventList().getEvent(firstEvent).
				getID());
		System.out.println("Event 2 Category is: " +
				DigitalOrganiser.getEventList().getEvent(secondEvent).
				getRepetition());
		System.out.println("Event 3 repetition is: " +
				DigitalOrganiser.getEventList().getEvent(thirdEvent).
				getCategory());
		System.out.println("This proves the events have been loaded " +
				"correctly");
		System.out.println();
		
	///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 /////
		/** @test write(e) and load() Testing writing from a string array and 
		 * loading the data from it.
		 */
		System.out.println("-[Test 3] " + FILLER);
		System.out.println("Now testing writing single events to the file");
		String[] toWrite ={SystemDate.generateTimeStamp(),"The event",
				"Birthday","2000-01-01 06:00:00","2000-01-01 00:00:00",
				"My house","No","A very good event","No-One"};
		System.out.println("Creating an event with the following fields");
		System.out.println("ID is: " + 
				toWrite[Event.ID_ARRAY_INDEX]);
		System.out.println("Title is: " + 
				toWrite[Event.TITLE_ARRAY_INDEX]);
		System.out.println("Category is: " + 
				toWrite[Event.CATEGORY_ARRAY_INDEX]);
		System.out.println("Starting Date is: " + 
				toWrite[Event.START_ARRAY_INDEX]);
		System.out.println("Ending Date is: " + 
				toWrite[Event.END_ARRAY_INDEX]);
		System.out.println("Location is: " + 
				toWrite[Event.LOCATION_ARRAY_INDEX]);
		System.out.println("Repetition is: " + 
				toWrite[Event.REPETITION_ARRAY_INDEX]);
		System.out.println("Desciption is: " + 
				toWrite[Event.DESCRIPTION_ARRAY_INDEX]);
		System.out.println("Persons Involved are: " + 
				toWrite[Event.PERSONS_ARRAY_INDEX]);
		
		System.out.println("Now creating the event and " +
				"writing this event to the file");
		DigitalOrganiser.getEventList().addEvent(toWrite);
		efm.write(DigitalOrganiser.getEventList().getEvent(fourthEvent));
		System.out.println("This test has written the event to the file," +
				"now i will prove it has been written");
		
		System.out.println("Now clearing the event list, so i can load " +
				"the events again");
		DigitalOrganiser.getEventList().clear();
		
		System.out.println("Now loading from the file");
		efm.load();
		
		System.out.println("The event list should be 4, including the 3 " +
				"previously added events and the event we just added");
		System.out.println("The event list size is: " + 
				DigitalOrganiser.getEventList().getSize());
		
		System.out.println("Now printing out the details of the loaded" +
				"event to prove it loaded correctly");
		Event e = DigitalOrganiser.getEventList().getEvent(fourthEvent);
		System.out.println("ID is: " + e.getID());
		System.out.println("Title is: " + e.getTitle());
		System.out.println("Category is: " + e.getCategory());
		System.out.println("Starting Date is: " + e.getStart());
		System.out.println("Ending Date is: " + e.getEnd());
		System.out.println("Location is: " + e.getLocation());
		System.out.println("Repetition is: " + e.getRepetition());
		System.out.println("Desciption is: " + e.getDescription());
		System.out.println("Persons Involved are: " + e.getPersonsInvolved());
		
		System.out.println("Because the details are the same it proves " +
				"the event has been loaded correctly");
		System.out.println();
		
	///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 /////
		/** @test load() Testing incorrect header */
		System.out.println("-[Test 4] " + FILLER);
		System.out.println("Testing a file with an incorrect header");
		EventFileManager efm4 = new EventFileManager("IncorrectHeaderEvent.csv");
		DigitalOrganiser.setEventFileManager(efm4);
		
		efm4.load();
		
		System.out.println("The invalid input file flag" +
				"should also have been set to true");
		System.out.println("CorruptInputFile flag is " + 
				efm4.getCorruptInputFile());
		System.out.println();
		
	///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 /////
		/** @test load() Testing file with extra columns */
		System.out.println("-[Test 5] " + FILLER);
		System.out.println("Now testing a file with extra collumns in it");
		System.out.println("(File still has a correct header)");
		EventFileManager efm5 = new EventFileManager("ExtraCollumnsEvent.csv");
		DigitalOrganiser.setEventFileManager(efm5);
		
		System.out.println("Clearing the event list");
		DigitalOrganiser.getEventList().clear();
		
		efm5.load();
		
		System.out.println("The event list should be 0, no events should" +
				"be loaded due to the extra collumns");
		System.out.println("The event list size is: " + 
				DigitalOrganiser.getEventList().getSize());
		
	///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 /////
		/** @test load() Testing loading from an empty file */
		System.out.println("-[Test 6] " + FILLER);
		System.out.println("Testing a completely empty file.");
		EventFileManager efm6 = new EventFileManager("EmptyEvent.csv");
		DigitalOrganiser.setEventFileManager(efm6);
		System.out.println("Due to it not having a correct header, " +
				"it will be treated as an erroneous file");
		System.out.println("Now loading from the file");
		System.out.println();
		
		efm6.load();
		
		System.out.println("The invalid input file flag " +
				"should also have been set to true");
		System.out.println("CorruptInputFile flag is " + 
				efm6.getCorruptInputFile());
		System.out.println();
		
	///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 /////
		/** @test load() Testing loading a file with over 1000 events in */
		System.out.println("-[Test 7] " + FILLER);
		System.out.println("Testing a extremely large file " +
				"this means over 1000 events");
		EventFileManager efm7 = new EventFileManager("VeryLargeEvent.csv");
		DigitalOrganiser.setEventFileManager(efm7);
		
		System.out.println("Clearing the event list");
		DigitalOrganiser.getEventList().clear();
		
		efm7.load();
		
		System.out.println("Due to this file having more than 1000 events " +
				"only the first 1000 should be loaded and an error message " +
				"should appear");
		
		System.out.println("The event list should be size 1000, only " +
				"1000 events should have been allowed into " +
				"the system.");
		System.out.println("The event list size is: " + 
				DigitalOrganiser.getEventList().getSize());	
		System.out.println();
	
	///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 /////
		/** @test load() Testing loading a JPEG file */
		System.out.println("-[Test 8] " + FILLER);
		System.out.println("Testing a JPG file renamed as" +
				"a csv file.");
		EventFileManager efm8 = new EventFileManager("Red_AppleJPG.csv");
		DigitalOrganiser.setEventFileManager(efm8);
		
		System.out.println("Clearing the event list");
		DigitalOrganiser.getEventList().clear();

		System.out.println("Since a JPG File have an invalid header, " +
				"it will fail on that check as as shown above it will " +
				"not load any events.");
		efm8.load();
		
		System.out.println("The event list should stay at size 0, no " +
				"events should be loaded.");
		System.out.println("The event list size is: " + 
				DigitalOrganiser.getEventList().getSize());	
		System.out.println();
		
	///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 /////
		/** @test load() Testing loading a file which does not exist */
		System.out.println("-[Test 9] " + FILLER);
		System.out.println("Testing when the chosen " +
				"file does not exist.");
		EventFileManager efm9 = new EventFileManager("NonExistantEvent.csv");
		DigitalOrganiser.setEventFileManager(efm9);
		
		System.out.println("As the file did not exist before, the constructor " +
				"will create the file");
	
		System.out.println("File exists: " +
				efm9.getFile().exists());
		System.out.println();
		
	///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 /////
			/** @test load() Testing loading a file containing some erroneous events */
			System.out.println("-[Test 10] " + FILLER);
			System.out.println("Testing when the chosen " +
					"file has erroneous and correct events.");
			EventFileManager efm10 = new EventFileManager("ErrorEvent.csv");
			DigitalOrganiser.setEventFileManager(efm10);
			
			System.out.println("As the file is erroneous we will expect to see " +
					"an error message telling us how many events were loaded");
		
			efm10.load();
			
			System.out.println();
			System.out.println("-[Testing Complete] " + FILLER);
		
	}
	
	/**
	 * This is the file name all event files will be set to.
	 */
	public static final String FILE_NAME = "Event.csv";
	
}
