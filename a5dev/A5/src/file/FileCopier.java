/**
 * \file FileCopier.java
 * \author Simon Maling
 * \date 19/03/12
 * \brief This copies one file to another file.
 */

package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class copies one file to another file, given the two file objects.
 * @author Simon Maling
 * @brief Copies one file to another file.
 */
public class FileCopier {
	/**
	 * This method copies one file to another file based on two file
	 * objects.
	 * @param src The source file object.
	 * @param dst The destination file object.
	 */
	public void copy(File src, File dst){
	    try{
			//Creating the input and output file objects.
			FileInputStream in = new FileInputStream(src);
		    FileOutputStream out = new FileOutputStream(dst);
	
		    // Transfer bytes from in to out
		    int numberOfBytes = 1024;
		    byte[] buf = new byte[numberOfBytes];
		    int len;
		    //Writing to the new file.
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    //Close the objects.
		    in.close();
		    out.close();
	    } catch(IOException ioe){
			System.out.println("IOException caught, the exception was: " +
					ioe);
		}
	}
	
	/**
	 * This is used for testing the class.
	 * @param args will not be used.
	 * @throws IOException Throws it.
	 */
	public static void main(String[] args) throws IOException{
		File src = new File("Contact.csv");
		File dst = new File("Error.csv");
		
		FileCopier c = new FileCopier();
		c.copy(src, dst);
	}
}
