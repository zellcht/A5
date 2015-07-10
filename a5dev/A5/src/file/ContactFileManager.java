/**
 * \file ContactFileManager.java
 * \author Simon Maling
 * \date 07/03/12
 * \see http://opencsv.sourceforge.net
 * \brief The contact Csv File manager which allows the user to create a an contact
 * CSV file, using the constructor, and to load and save from it.
 */

/**
 * @package file
 * @brief The package containing the file managers.
 */
package file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser;

import events.Event;
import events.EventList;
import addressBook.AddressBook;
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
 * It allows the user to create an Contact Csv file, to load from it,
 * or save the contacts to the file.
 */
public class ContactFileManager extends FileManager{
	
	/**
	 * This is the accessor method for m_FileName.
	 * @param name The new name of the filename variable.
	 * @return True if set correctly.
	 */
	public boolean setFileName(String name){
		super.setFileName(name);
		return true;
	}
	
	/**
	 * This is the constructor for ContactCsvFile, it calls the superclass
	 * Constructor with the default file name and then sets the file name
	 * of the object.
	 */
	public ContactFileManager(){
		this(FILE_NAME);
	}
	
	/**
	 * This is the constructor for ContactCsvFile, it calls the superclass
	 * Constructor with the parameter file name and then sets the file name
	 * of the object.
	 * @param name The name the file object will be set to.
	 */
	public ContactFileManager(String name){
		super(name);
		try{
			//If file dosent exist create it and writ the header to it.
			if(!this.getFile().exists()){
				System.out.println("Creating the file");
				this.getFile().createNewFile();
				this.writeHeader();
			} else {
				this.isEOFBlank();
			}
		} catch (IOException ioe){
			System.out.println("Exception caught, the exception was: " + ioe);
		}	
	}
	
	/**
	 * This method loads the contacts details from the csv file, 
	 * then creates an contact object and sets the objects fields
	 * to the fields loaded.
	 * Finally it adds the event to the contact list.
	 * @return True if loaded successfully.
	 */
	public boolean load(){
		//This shows whether the file was loaded succesfully.
		boolean success = true;
		//Try catching IO exceptions.
		try{
			//Initialise the filereader,CSVReader objects.
			FileReader fileReader = new FileReader(getFile());
			CSVReader reader = new CSVReader(fileReader);
			StringArrayComparer csa = new StringArrayComparer();
			
			String[] headerArray = reader.readNext();
			
			//If the loaded header does not equal the expected header.
			if(!(csa.equalArrays(headerArray, 
					Contact.getHeaderStringArray()))){
				//Showing the error and copying the file.
				this.showFileError();
				this.copyFile();
			
			} else {
				addToAddressBook(reader);
				
				//Close the reader and writer.
				reader.close();
				fileReader.close();
			}
			
		} catch (IOException ioe){
			System.out.println("IOException caught, the exception was: "
											+ ioe);
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
			
			//This array stores the details of the contact to be written to the file.
			String[] toWriteArray; 
			
			//Rewriting the header to the file.
			String[] headerArray = Contact.getHeaderStringArray();
			writer.writeNext(headerArray);
			
			//For every contact in the address book.
			for(int i=0; i<DigitalOrganiser.getAddressBook().getSize(); i++){
				//Get the array of the contact details.
				toWriteArray = DigitalOrganiser.getAddressBook().
								getContact(i).getAsStingArray();
				//Write the contact fields to file.								
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
	 * This method will write a single contact to the file.
	 * @param c This is the contact object to be written.
	 * @return True if written correctly.
	 */
	public boolean write(Contact c){
		boolean success = true;
		
		try{
		//Creating a file writer,CSVwriter and CVSParser object.
		//File writer will overwrite the file and not append to it.
		FileWriter fileWriter = new FileWriter(getFile(),true);
		CSVWriter writer = new CSVWriter(fileWriter);
		//This array stores the details of the contact to be written to the file.
		String[] toWriteArray = c.getAsStingArray();

		//Write the contact fields to file.								
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
	 * This method tests if the contact array loaded is of the correct size
	 * and if so passes it to the address book.
	 * It also lets you load a max of 1000 contacts.
	 * @param reader This read object created in load.
	 * @return True if executed correctly.
	 */
	private boolean addToAddressBook(CSVReader reader){
		boolean success = true; 
		int loadedContacts = 0;
		int totalContacts =0;
		
		try{
			//This is an array containing all the fields loaded from the file.
			String[] nextLineArray;
	
			//Loading the contact array one by one.
			while (((nextLineArray = reader.readNext()) != null)
					& loadedContacts <AddressBook.SIZE_LIMIT) {	
				try{
					//If the loaded array is of the expected size.
					if(correctArraySize(nextLineArray)){
						if(DigitalOrganiser.getAddressBook().
								addContact(nextLineArray)){
							loadedContacts++;
						}
						totalContacts++;
					} else {
						System.err.println("Loaded contact has not got the " +
								"correct number of fields");
					}
					
				} catch (NullPointerException npe){
					System.out.println("NullPointerException caught, " +
							"the exception was: " + npe);
					success = false;
				}
	
			}
			//If too many contacts have tried to be loaded.
			if (loadedContacts == AddressBook.SIZE_LIMIT){
				System.out.println("1000 contacts loaded, Cannot load " +
						"anymore contacts");
				this.setMaxLoaded(true);
			}
			if(getMaxLoaded()){
				this.showMaxError();
			}
			if(loadedContacts != totalContacts){
				showContactError(loadedContacts, totalContacts);
			}
		} catch(IOException ioe){
			System.out.println("IOException caught, the exception was: " + ioe);
		}
		
		return success;
		
	}
	
	/**
	 * This method tells the user its copying the file, sets the
	 * corruptInputFile variable to true, then copies the old
	 * file to a new file, deletes the old file, recreates 
	 * it and then writes the header to it. 
	 * @return True if copied successfully.
	 */
	private boolean copyFile(){
		boolean success = true;
		try{
			System.out.println("Error: The header of the file is" 
				     + " not correct, ");
			System.out.println("the file will not be loaded.");
			System.out.println("The file has been saved as a new " +
							   "file called,");
			System.out.println("ErroneousContact.csv.");
			
			//Setting the fact that the input file is invalid.
			this.setCorruptInputFile(true);			
			
			//Copying the source file under the new name indicated by the
			//destination file.
			File src = new File ("Contact.csv");
			File dst = new File ("ErroneousContact.csv");
			dst.createNewFile();
			FileCopier c = new FileCopier();
			c.copy(src,dst);
			//Creating a new source file.
			src.delete();
			src.createNewFile();
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
	 * it uses the global variable from contact to check this.
	 * @param sa The string array to be tested.
	 * @return True if correct size, otherwise false;
	 */
	private boolean correctArraySize(String[] sa){
		if(!(sa.length==Contact.ARRAY_SIZE)){
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * This method gets the header for the contact class, then
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
			String[] headerArray = Contact.getHeaderStringArray();
	
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
	 * This method produces an error pane telling the user that not 
	 * all the contacts were loaded.
	 * @param loaded Number of contacts loaded.
	 * @param total Total numbers of contacts.
	 * @return True if executed correctly.
	 */
	public boolean showContactError(int loaded, int total){
		JOptionPane.showMessageDialog(null, 
				"Error some of the contacts were" + "\n" +
				"erroneous. Only " + loaded +
				" out of " + total + "\n" + 
				"were " + "loaded.",
				"Erroneous Contact File"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	/**
	 * This method produces a error pane telling the user
	 * that the contact file was erroneous and that is has been
	 * copied under a new name.
	 * @return True is executed correctly.
	 */
	private boolean showFileError(){
		JOptionPane.showMessageDialog(null, 
				"The file is erroneous, it has been" + "\n" +
				"copied to ErroneousContact.csv," + "\n" +
				"and a new empty file is being used.",
				"Erroneous Contact File"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	/**
	 * This method produces a error pane telling the user
	 * that the contact file is too large and only the first 1000
	 * contacts have been loaded.
	 * @return True if executed correctly.
	 */
	private boolean showMaxError(){
		JOptionPane.showMessageDialog(null, 
				"The file is too large, only the" + "\n" +
				"first 1000 contacts have been," + "\n" +
				"loaded.",
				"Contact File Too Large"
				, JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	
	/**
	 * This is the main method used for testing the class.
	 * It will test loading, writing creating the file.
	 * @param args Parameter for file input.
	 */
	public static void main(String[] args){
		/*
		 * The unit tests to be done on contact file manager are as follows: 
		 * 1. Writing typical non erroneous data to the file.
		 * 2. Loading the data just written to the file and proving it is 
		 *    the same data that was written.
		 * 3. Creating and writing single contacts to the file, then loading them
		 *    again to prove they were written/loaded correctly.
		 * 4. Testing loading a file with an incorrect header.
		 * 5. Testing loading a file with extra collumns in it.
		 * 6. Testing loading a completely empty file.
		 * 7. Testing loading an extremely large file with over 1000 contacts.
		 * 8. Testing loading a erroneous file in the form of a JPG file which
		 *    is renamed to the csv format.
		 * 9. Testing loading a file which dosent exist. (Choosing a filename
		 *    of a file which dosent exist, and showing the file is created).
		 * 10.Testing an erroneous file to make sure it tells the user how
		 *    contacts were loaded (if not all of them).   
		 * 
		 *	Erroneous data and random missing data (excluding headers) will
		 *  be dealt with by the AddressBook and contact classes. ContactFileManager
		 *  only does the basic checks then passes the data on, therefore test
		 *  cases for invalid data are covered by addressbook and contact
		 *  not contact file manager.
		 *  Empty collumns will fail the header check.
		 *  
		 */
		
		//Initialising the objects for testing purposes.
		System.out.println("Now testing Contact File Manager.");
		System.out.println("First initialising the objects");
		final String FILLER = "-----------------------------------------";
		ContactFileManager cfm = new ContactFileManager();
		DigitalOrganiser.setContactFileManager(cfm);
		DigitalOrganiser.setAddressBook(new AddressBook());
		int firstContact =0; 
		int secondContact = 1;
		int thirdContact = 2;
		int fourthContact = 3;
		int fifthContact = 4;
		
	///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 ///// TEST1 /////
		/** @test write() with typical data */
		System.out.println("-[Test 1] " + FILLER);
		System.out.println("Testing writing to the file, using typical data");
		System.out.println("Creating 3 default contacts to write to the file");
		System.out.println("Leaving the 1st contact default");
		System.out.println("Setting the seconds contact surname to Jones");
		System.out.println("Setting the third contact telephone " +
				"number to 07654 434213");
		DigitalOrganiser.getAddressBook().addContact(new Contact());
		DigitalOrganiser.getAddressBook().addContact(new Contact());
		DigitalOrganiser.getAddressBook().addContact(new Contact());
		DigitalOrganiser.getAddressBook().getContact(secondContact)
			.setLastName("Jones");
		DigitalOrganiser.getAddressBook().getContact(thirdContact).
			setMobileNumber("07654 434213");
		
		System.out.println("Now writing to the file");
		cfm.write();
		System.out.println("The test is now complete and the " +
				"contacts have been written to the file");
		System.out.println("Test 2 will prove this");
		System.out.println();
		
		
	///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 ///// TEST2 /////
		/** @test load() loading data written to file in test 1. */
		System.out.println("-[Test 2] " + FILLER);
		System.out.println("Testing loading from the file");
		System.out.println("Loading the contacts added to the file in test 1");
		System.out.println("Clearing the address book");
		DigitalOrganiser.getAddressBook().clear();
		System.out.println("Now loading from the file");
		cfm.load();
		System.out.println("The address book size is: " +
				DigitalOrganiser.getAddressBook().getSize());
		System.out.println("This shows all 3 contacts have been loaded");
		System.out.println("Printing the 3 set fields from the " +
				"contact to prove they have been loaded: ");
		System.out.println("Contact 1 First Name is: " +
				DigitalOrganiser.getAddressBook().
				getContact(firstContact).getFirstName());
		System.out.println("Contact 2 Surname is: " +
				DigitalOrganiser.getAddressBook().
				getContact(secondContact).getLastName());
		System.out.println("Contact 3  Mobile Number is: " +
				DigitalOrganiser.getAddressBook().
				getContact(thirdContact).getMobileNumber());
		System.out.println("This proves the contacts have been loaded " +
				"correctly");
		System.out.println();
		
		
	///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 ///// TEST3 /////
		/** @test write(e) and load() Testing writing a single contact 
		 * to the file, then loading it afterwards */
		System.out.println("-[Test 3] " + FILLER);
		System.out.println("Now testing writing single contacts to the file");
		String[] toWrite ={SystemDate.generateTimeStamp(),"Bob",
				"Customer","","Somewhere near you",
				"SA2 8IK","","09876 433344","","","","csbob@swansea.ac.uk",
				"",""};
		System.out.println("Creating an contact with the following fields");
		System.out.println("ID is: " + toWrite[Contact.ID_ARRAY_INDEX]);
		System.out.println("First Name is: " + 
				toWrite[Contact.FIRSTNAME_ARRAY_INDEX]);
		System.out.println("Last Name is: " + 
				toWrite[Contact.LASTNAME_ARRAY_INDEX]);
		System.out.println("Nickname is: " + 
				toWrite[Contact.NICKNAME_ARRAY_INDEX]);
		System.out.println("Address is: " + 
				toWrite[Contact.ADDRESS_ARRAY_INDEX]);
		System.out.println("Postcode is: " + 
				toWrite[Contact.POSTCODE_ARRAY_INDEX]);
		System.out.println("Home Number is: " + 
				toWrite[Contact.HOMENUMBER_ARRAY_INDEX]);
		System.out.println("Mobile Number is: " + 
				toWrite[Contact.MOBILENUMBER_ARRAY_INDEX]);
		System.out.println("Work Number is: " + 
				toWrite[Contact.WORKNUMBER_ARRAY_INDEX]);
		System.out.println("Fax Number is: " + 
				toWrite[Contact.FAXNUMBER_ARRAY_INDEX]);
		System.out.println("Personal Email is: " + 
				toWrite[Contact.PERSONALEMAIL_ARRAY_INDEX]);
		System.out.println("Work Email is: " + 
				toWrite[Contact.WORKEMAIL_ARRAY_INDEX]);
		System.out.println("Other Email is: " + 
				toWrite[Contact.OTHEREMAIL_ARRAY_INDEX]);
		System.out.println("URL is: " + 
				toWrite[Contact.URL_ARRAY_INDEX]);
		
		System.out.println("Now creating the contact and " +
				"writing it to the file");
		DigitalOrganiser.getAddressBook().addContact(toWrite);
		cfm.write(DigitalOrganiser.getAddressBook().getContact(fourthContact));
		System.out.println("This test has written the contact to the file, " +
				"now i will prove it has been written");
		
		System.out.println("Now clearing the address book, so i can load " +
				"the contacts again");
		DigitalOrganiser.getAddressBook().clear();
		
		System.out.println("Now loading from the file");
		cfm.load();
		
		System.out.println("The address book should be size 4, including " +
				"the 3 " +
				"previously added contacts and the contact we just added");
		System.out.println("The address book size is: " + 
				DigitalOrganiser.getAddressBook().getSize());
		
		System.out.println("Now printing out the details of the loaded " +
				"contact to prove it loaded correctly");
		Contact c = DigitalOrganiser.getAddressBook().getContact(fourthContact);
		System.out.println("ID is: " + c.getID());
		System.out.println("First Name is: " + c.getFirstName());
		System.out.println("Last Name is: " + c.getLastName());
		System.out.println("Nickname is: " + c.getNickName());
		System.out.println("Address is: " + c.getAddress());
		System.out.println("Postcode is: " + c.getPostCode());
		System.out.println("Home Number is: " + c.getHomeNumber());
		System.out.println("Mobile Number is: " + c.getMobileNumber());
		System.out.println("Work Number is: " + c.getWorkNumber());
		System.out.println("Fax Number is: " + c.getFaxNumber());
		System.out.println("Personal Email is: " + c.getPersonalEmail());
		System.out.println("Work Email is: " + c.getWorkEmail());
		System.out.println("Other Email is: " + c.getOtherEmail());
		System.out.println("URL is: " + c.getUrl());
		
		System.out.println("Because the details are the same it proves " +
				"the contact has been loaded correctly");
		System.out.println();
		
		
		
	///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 ///// TEST4 /////
		/** @test load() Testing loading a file with an incorrect header */
		System.out.println("-[Test 4] " + FILLER);
		System.out.println("Testing a file with an incorrect header");
		ContactFileManager cfm4 = new ContactFileManager
				("IncorrectHeaderContact.csv");
		DigitalOrganiser.setContactFileManager(cfm4);
		
		cfm4.load();
		
		System.out.println("The invalid input file flag" +
				"should also have been set to true");
		System.out.println("CorruptInputFile flag is " + 
				cfm4.getCorruptInputFile());
		System.out.println();
		
		
	///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 ///// TEST5 /////
		/** @test load() Testing loading a file with extra columns in it */
		System.out.println("-[Test 5] " + FILLER);
		System.out.println("Now testing a file with extra columns in it");
		System.out.println("(File still has a correct header)");
		ContactFileManager cfm5 = new ContactFileManager("ExtraCollumnsContact.csv");
		DigitalOrganiser.setContactFileManager(cfm5);
		
		System.out.println("Clearing the address book");
		DigitalOrganiser.getAddressBook().clear();
		
		cfm5.load();
		
		System.out.println("The address book should be 0, no contacts should " +
				"be loaded due to the extra collumns");
		System.out.println("The address book size is: " + 
				DigitalOrganiser.getAddressBook().getSize());
		System.out.println();
		
		
	///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 ///// TEST6 /////
		/** @test load() Testing loading a completely empty file */
		System.out.println("-[Test 6] " + FILLER);
		System.out.println("Testing a completely empty file.");
		ContactFileManager cfm6 = new ContactFileManager("EmptyContact.csv");
		DigitalOrganiser.setContactFileManager(cfm6);
		System.out.println("Due to it not having a correct header, " +
				"it will be treated as an erroneous file");
		System.out.println("Now loading from the file");
		System.out.println();
		
		cfm6.load();
		
		System.out.println("The invalid input file flag " +
				"should also have been set to true");
		System.out.println("CorruptInputFile flag is " + 
				cfm6.getCorruptInputFile());
		System.out.println();
		
		
	///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 ///// TEST7 /////
		/** @test load() Testing loading a file with over 1000 contacts in it */
		System.out.println("-[Test 7] " + FILLER);
		System.out.println("Testing a extremely large file " +
				"this means over 1000 contacts");
		ContactFileManager cfm7 = new ContactFileManager("VeryLargeContact.csv");
		DigitalOrganiser.setContactFileManager(cfm7);
		
		System.out.println("Clearing the address book");
		DigitalOrganiser.getAddressBook().clear();
		
		cfm7.load();
		
		System.out.println("Due to this file having more than 1000 contacts " +
				"only the first 1000 should be loaded and an error message " +
				"should appear");
		
		System.out.println("The address book should be size 1000, only " +
				"1000 contacts should have been allowed into " +
				"the system.");
		System.out.println("The address book size is: " + 
				DigitalOrganiser.getAddressBook().getSize());	
		System.out.println();
	
	///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 ///// TEST8 /////
		/** @test load() Testing loading a JPG file */
		System.out.println("-[Test 8] " + FILLER);
		System.out.println("Testing a JPG file renamed as" +
				"a csv file.");
		ContactFileManager cfm8 = new ContactFileManager("Red_AppleJPG.csv");
		DigitalOrganiser.setContactFileManager(cfm8);
		
		System.out.println("Clearing the address book");
		DigitalOrganiser.getAddressBook().clear();

		System.out.println("Since a JPG File have an invalid header, " +
				"it will fail on that check as as shown above it will " +
				"not load any contacts.");
		cfm8.load();
		
		System.out.println("The address book should stay at size 0, no " +
				"contacts should be loaded.");
		System.out.println("The address book size is: " + 
				DigitalOrganiser.getAddressBook().getSize());	
		System.out.println();
		
		
	///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 ///// TEST9 /////
		/** @test load() Testing loading a non existant file. */
		System.out.println("-[Test 9] " + FILLER);
		System.out.println("Testing when the chosen " +
				"file does not exist.");
		ContactFileManager cfm9 = new ContactFileManager("NonExistantContact.csv");
		DigitalOrganiser.setContactFileManager(cfm9);
		
		System.out.println("As the file did not exist before, the constructor " +
				"will create the file");
	
		System.out.println("File exists: " +
				cfm9.getFile().exists());
		System.out.println();
		
		///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 ///// TEST10 /////
		/** @test load()  Testing loading a file with some erroneous contacts
		 * in it */
		System.out.println("-[Test 10] " + FILLER);
		System.out.println("Testing when the chosen " +
				"file has erroneous and correct contacts.");
		ContactFileManager cfm10 = new ContactFileManager("ErrorContact.csv");
		DigitalOrganiser.setContactFileManager(cfm10);
		
		System.out.println("As the file is erroneous we will expect to see " +
				"an error message telling us how many contacts were erroneous");
	
		cfm10.load();
		
		System.out.println();
		System.out.println("-[Testing Complete] " + FILLER);
	}
	
	/**
	 * This is the file name all contact files will be set to.
	 */
	public static final String FILE_NAME = "Contact.csv";
	
}
