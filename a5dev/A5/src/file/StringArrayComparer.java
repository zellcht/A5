/**
 * \file StringArrayComparer.java
 * \author Simon Maling
 * \date 19/03/12
 * \brief Compares two string array to see if they are equal.
 * 
 * I could have extended array, but it was not worth in the time period
 * available, and because only one method is added it was deemed not worthwhile.
 */

package file;

/**
 * This class allows the user to compare two string arrays passed across as
 * parameters and determine if they contain the same elements.
 * @author Simon Maling 
 * @brief Compares two string arrays.
 */
public class StringArrayComparer {
	
	/**
	 * Checks the elements of two string arrays to see if they
	 * contain exactly the same elements.
	 * @param a String array.
	 * @param b String array.
	 * @return True if the two arrays are the same, false otherwise.
	 */
	public boolean equalArrays(String[] a, String[] b){
		try{
			//If the two arrays are different lengths.
			if (!(a.length == b.length)){
				System.out.println("Error: The two arrays are of " +
						"different lengths");
				return false;
			} else {
				//For every element.
				for(int i=0; i<a.length; i++){
					//If the elements are not the same.
					//System.out.println(a[i] + " " + b[i]);
					if(!a[i].equals(b[i])){
						System.out.println("Error: The elements at location "
								+ i + " are different");
						return false;
					}
				}
				
			}
		} catch (NullPointerException npe){
			System.out.println("NullPointerException caught," +
					"the exception was : " + npe);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Main method used for testing.
	 * @param args will not be used.
	 */
	public static void main(String[] args){
		String[] a = {"a","d","c"};
		String[] b = {"f","d","d"};
		String[] c = {"a","d","c"};
		String[] d = {"dd","fff"};
		
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));
		
		StringArrayComparer csa = new StringArrayComparer();
		System.out.println(csa.equalArrays(a,b));
		System.out.println(csa.equalArrays(a,c));
		System.out.println(csa.equalArrays(a,d));
	
	}

}
