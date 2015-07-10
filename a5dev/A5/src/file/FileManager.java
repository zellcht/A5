/**
 * \file FileManager.java
 * \author Simon Maling
 * \date 07/03/12
 * \see http://opencsv.sourceforge.net
 * \brief The Csv File manager which allows the user to create a CSV file, 
 * using the constructor.
 * 
 * Changes Made:
 * Renamed EventCSVFile to conform with general class naming convention.
 * Redone constructor as constructor broke rule 9, also wasnt really what
 * a constructor should do.
 * Changed the way it checks if a file does not exist.
 * Stopped the file being saved in a new directory as this will only work
 * on windows due to the way this is done.
 *
 * Their Storage problems:
 * They have createCsvFile method in contact details this does not make 
 * sense for this to be here.
 * Aswell as an addContactMethod
 * CsvFileReader seems to only read contacts, also has a delete method 
 * which makes no sense.
 * EventCsvFile is actually just a csv file writer for events.
 *
 * None of these above classes seem to link together.
 * I will use the same principle as they have used for loading and saving to 
 * CSV's however will have to change it dramatically.
 * I will have a hierachy. The FileManager will be the top, and inheriting from 
 * it will be an EventFileManager, and ContactFileManager.
 * These file managers will handle the loading of and writing to of the files.
 */

/**
 * @package file
 * @brief The package containing the file managers.
 */
package file;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @brief Main class for file managers, its abstract and its constructors and accessors will be used by other classes.
 * @author Simon Maling
 * 
 * This class includes some of the methods and variables needed in the classes that
 * inherit from it. It includes accessors and mutators for all of them.
 * It allows you to create the CSV file if is doesn't already exist, and allows
 * the you to access the file names and file objects.
 * Class is abstract so no test cases can be carried out.
 */
public abstract class FileManager {
	/**
	 * This is an accessor method for corrupted input file.
	 * @return the m_corrupted file boolean.
	 */
	public boolean getCorruptInputFile(){
		return m_CorruptInputFile;
	}
	
	/**
	 * This is an accessor mutator method for corrupted input file.
	 * It sets the value to false.
	 * @return True if set correctly.
	 */
	public boolean setCorruptInputFile(){
		m_CorruptInputFile = false;
		return true;
	}
	
	/**
	 * This is an accessor mutator method for corrupted input file.
	 * @param corrupt The boolean corrupted input file will be set to.
	 * @return True if set correctly.
	 */
	public boolean setCorruptInputFile(Boolean corrupt){
		m_CorruptInputFile = corrupt;
		return true;
	}
	
	/**
	 * This is an accessor method for the file object.
	 * @return The file object of the file to be written to/
	 * loaded from.
	 */
	public File getFile(){
		return m_File;
	}
	
	/**
	 * This is an accessor method for the file name.
	 * @return The file name of the file to be loaded.
	 */
	public String getFileName(){
		return this.m_FileName;
	}
	
	/**
	 * This is an accessor mutator method for the file object.
	 * @param file The file the objects file will be set to.
	 * @return Returns true if set correctly.
	 */
	public boolean setFile(File file){
		this.m_File = file;
		return true;
	}
	
	/**
	 * This is an accessor mutator method for the file name.
	 * @param s The name the file will be set to.
	 * @return True if set correctly.
	 */
	public boolean setFileName(String s){
		this.m_FileName = s;
		return true;
	}
	
	/**
	 * This is an accessor method for max loaded.
	 * @return m_maxLoaded loaded boolean value.
	 */
	public boolean getMaxLoaded(){
		return m_maxLoaded;
	}
	
	/**
	 * This is an accessor mutator method for max loaded.
	 * It sets the value to false.
	 * @return True if set correctly.
	 */
	public boolean setMaxLoaded(){
		m_maxLoaded = false;
		return true;
	}
	
	/**
	 * This is an accessor mutator method for max loaded.
	 * @param max The boolean max loaded will be set to.
	 * @return True if set correctly.
	 */
	public boolean setMaxLoaded(boolean max){
		m_maxLoaded = max;
		return true;
	}
	
	/**
	 * This is the constructor for the CsvFile object.
	 * It will create a new file object using the classes default 
	 * filename, and create this file if it does not already exist.
	 * It also sets the CsvFile's file object to this object.
	 * @param fileName The name of the filename as a string.
	 */
	public FileManager(String fileName){
			this.setFileName(fileName);
			//Create a file object for the file.
			File file = new File(this.getFileName());
			
			//Set the file to the object.
			this.setFile(file);
			
			this.setCorruptInputFile();
			
	}
	
	/**
	 * This method counts the number of lines in the file.
	 * @return int containing the number of lines.
	 */
	protected int countNumberOfLines(){
		try {
			//Creating an buffered input stream object.
			BufferedInputStream is = new BufferedInputStream(
				new FileInputStream(getFileName()));
			//Declaring the number of bytes and creating a byte
			//array to store it.
			int numberOfBytes = 1024;
			byte[] c = new byte[numberOfBytes];
			//Initialising the count and readChars.
			int count = 0;
			int readChars = 0;
			
			//while not every characters has been read.
			while ((readChars = is.read(c)) != -1) {
				for (int i = 0; i < readChars; ++i) {
					//if you see the new line symbol, add to the count.
					if (c[i] == '\n')
						++count;
					}
				}
				//close input stream.
				is.close();
				
				return count;
				
		} catch(IOException ioe) {
			System.out.println("IOException caught," +
					"the exception was: " + ioe);
		}
		
		return 0;
	}
	
	/**
	 * This method decides whether the end of the file is a blank
	 * line or not, if not it writes a blank line to the end of the file.
	 * @return True if executed correctly.
	 */
	protected boolean isEOFBlank(){
		boolean success = true;
		int lineCount =0;
		
		try{
			//Creating the scanner object.
			Scanner inStream = new Scanner(getFile());
			String line = "";
				//Count the number of lines in the file.
				//This will ignore empty lines.
		        while(inStream.hasNextLine()){
		            line = inStream.nextLine();
		        	lineCount++;
		        }
		        inStream.close();
		   			
		        //if the number of actual lines is not equal to the number
		        //of loaded lines then it must be missing the space at the 
		        //end. So write an empty line to the EOF.
		        if(lineCount != countNumberOfLines()){
		        	System.out.println("Error Empty Line at the end of the " +
		        			"file.");
		        	System.out.println("One will now be added");
		        	//Create the writing objects.
		        	FileWriter fileWriter = new FileWriter(getFile(),true);
		        	BufferedWriter writer = new BufferedWriter(fileWriter);
		        	//Write an empty line.
		        	writer.newLine();
		        	//Close the writers.
		        	writer.close();
		        	fileWriter.close();
		        }
		        
		} catch(IOException ioe){
			System.out.println("IOException caught, " +
					"the exception was: " + ioe);
			success = false;
		}
		
		return success;
	}
	
	/**
	 * This file name of the file to be used.
	 */
	private String m_FileName;
	/**
	 * The file object for the file that will be loaded from/saved to.
	 */
	private File m_File; 
	/**
	 * The name of the symbollic constant for the file name.
	 */
	public static final String FILE_NAME = "DEFAULT.csv";
	
	/**
	 * This indicates whether the file is corrupt,
	 * True is it is corrupt.
	 */
	private boolean m_CorruptInputFile = false;;
	
	/**
	 * This indicates whether over 1000 events/contacts have been loaded and that
	 * no more can be loaded.
	 */
	private boolean m_maxLoaded = false;;
	
}
