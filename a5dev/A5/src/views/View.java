/**
 * @file View.java
 * @author Samuel Jenkins
 * @date 19/02/2012
 * @brief The parent View class.
 */

package views;

import javax.swing.*;

import hirondelle.date4j.DateTime;

import dateAndTime.*;


/**
 * Instance of a View.
 * Specifies all the methods, variables and functionality
 * that all Views must have.
 *
 * This code uses the Conventions as stated in:
 * @see  <a href="http://cs.swan.ac.uk/~csbob/teaching/laramee09codeConvention.pdf">Bob's Coding Conventions</a> , page 117
 * 
 * @author Samuel Jenkins
 * @brief The parent View class.
 */
public class View extends JPanel{//Begin Class

	/**
     * Gets the Object to display.
     * @return Object to be displayed
     */
	public Object getToDisplay(){//Begin GetToDisplay()
		boolean test = false;
		if(test){
			System.out.println("Called View :: GetToDisplay()");
		}
		return m_ToDisplay;
	}//End GetToDisplay()
	
	/**
     * Sets the Object for the View to display.
     * @param oSetTo the Object to be conatined and displayed by the View.
     * @return true on success, false on failure.
     */
	public boolean setToDisplay(Object oSetTo){//Begin SetToDisplay(Object)
		boolean test = false;
		if(test){
			System.out.println("Called View :: SetToDisplay(object)");
		}
		if(oSetTo == null){
			return false;
		}
		m_ToDisplay = oSetTo;
		return true;
	}//End SetToDisplay(Object)
	
	/**
     * Gets the DateTime for storing the Views date.
     * @return DateTime object.
     */
	public DateTime getDate(){
		return m_Date;
	}
	
	/**
	 * Gets the DateTime for storing the Views date.
	 * @param date the new date 
	 * @return true on execution
	 */
	public boolean setDate(DateTime date){
		m_Date = date;
		return true;
	}
	
	/**
     * Constructs a default View.
     * @return a View Object.
     */
	public View(){//Begin View()
		boolean test = false;
		if(test){
			System.out.println("Called View :: View()");
		}
		SystemDate s = new SystemDate();
		this.setDate(s.getCurrentDate());
		this.setToDisplay(new Object());
	}//End View()
	
	/**
     * Constructs a View with an Object to contain and show.
	 * @param oToShow the Object to be shown by the View.
     * @return a View Object.
     */
	public View(Object oToShow){//Begin View(Object)
		boolean test = false;
		if(test){
			System.out.println("Called View :: View(Object)");
		}
		this.setToDisplay(oToShow);
	}//End View(Object)
	
	/**
     * Creates the default format to show an Object contained by the View.
     * @return a View Object with its contents displayed.
     */
	public View createDisplay(){//Begin CreateDisplay()
		boolean test = false;
		if(test){
			System.out.println("Called View :: CreateDisplay()");
		}
		if(getComponentCount() > 0){
			this.removeAll();
	  	}
		Object oShow = this.getToDisplay();
		if(oShow==null){
			oShow = new Object();
		}
		this.add(new JLabel(oShow.toString()));
		this.repaint();
		return this;
	}//End  CreateDisplay()
	
	/**
     * Creates the default format to show a specific Object contained by the View.
	 * @param oObject the Object to be displayed.
     * @return a View Object with oObject displayed in the format desired.
     */
	public View createDisplay(Object oObject){//Begin CreateDisplay(Object)
		boolean test = false;
		if(test){
			System.out.println("Called View :: CreateDisplay(object)");
		}	
		this.setToDisplay(oObject);
		if(getComponentCount() > 0){
	  		   this.removeAll();
	    }
		this.add(new JLabel(oObject.toString()));
		this.repaint();
		return this;
	}//End CreateDisplay(Object)
	
	/**
     * Shows the View.
	 * (Made void so that it would override the show() method in
	 *  the Parent Class Component)
     */
	public void show(){//Begin Show
		boolean test = false;
		if(test){
			System.out.println("Called View :: Show()");
		}
		this.removeAll();
		this.createDisplay();
	}//End Show
	
	/**
     * Shows/hides the View.
	 * @param bShow sets whether to show or hide the view, true being to show, false being to hide.
	 * @return true on success, false on failure.
     */
	public boolean show(Boolean bShow){//Begin Show(boolean)
		boolean test = false;
		if(test){
			System.out.println("Called View :: Show(boolean)");
		}
		if(bShow){
			this.createDisplay();
		}else{
			this.removeAll(); //JPanel method to remove everything from the View, to hide it
		}
		return true;
	}//End Show(boolean)
	
	/**
     * Tests the class.
	 * @param args the Command Line arguments
     */
	public static void main(String[] args){//Begin main(String[])
		boolean test = false;
		if(test){
			System.out.println("Called View :: main(boolean)");
		}	
		View oView = new View();
		JFrame test1 = new JFrame("Test1");
		oView.setToDisplay(test1);
		test1.add(oView.createDisplay());
		test1.repaint();
		JFrame test2 = new JFrame("Test2");
		test2.add(oView.createDisplay(test2));
		test1.pack();
		test2.pack();
		test1.setVisible(true);
		test2.setVisible(true);
	}//End main(String[])*/
		
	/** The Object that a View has to display within itself */
	private Object m_ToDisplay;
	
	/** The Object used by the DayView, WeekView and MonthView... */
	private DateTime m_Date;
	
}//End Class