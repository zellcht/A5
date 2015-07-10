/**
 * @file CalendarButton.java
 * @author Samuel Jenkins
 * @date 23/03/2012
 * @brief A CalendarButton that allows you to set what type of button it is.
 */
package views;

import javax.swing.JButton;

/**
 * 
 * This class provides a means to create a button that can identify what
 * kind of button it is. By which a numerical value it holds is used
 * to identify the kind of Button it is. 
 * 
 * @author Samuel Jenkins
 * @brief A CalendarButton that allows you to set what type of button it is.
 */
public class CalendarButton extends JButton{

	/**
	 * Gets the type of the CalendarButton
	 * @return the type of the Button.
	 */
	public int getType(){
		return m_Type;
	}
	
	/**
	 * Sets the type of the CalendarButton.
	 * @param type the type to set the Button to.
	 * @return
	 */
	public boolean setType(int type){
		m_Type = type;
		return true;
	}
	
	/**
	 * Constructs a CalendarButton of Default Type.
	 */
	CalendarButton(){
		super();
		setType(DEFAULT_BUTTON);
	}
	
	/**
	 * Constructs a CalendarButton and sets its type.
	 * @param type the type of CalendarButton it is.
	 */
	CalendarButton(int type){
		super();
		setType(type);
	}
	
	/**
	 * Constructs a CalendarButton with text for the Button to contain
	 * and its type.
	 * @param type the type of CalendarButton it is.
	 * @param name The text you want to appear in the Button when it is rendered.
	 */
	CalendarButton(int type, String name){
		super(name);
		setType(type);
	}
	
	/** The value to set the button to being the same type as a JButton. */
	public static final int DEFAULT_BUTTON = 0;
	
	/** The value to set the button to being the a JButton that
	 *  indicates a "next" action. */
	public static final int NEXT_BUTTON = 1;
	
	/** The value to set the button to being the a JButton that
	 *  indicates a "previous" action. */
	public static final int PREVIOUS_BUTTON = 2;
	
	/** Holds the type of the button. */
	private int m_Type;
	
}
