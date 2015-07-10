/**
 * @file SearchTableMouseListener.java
 * @author Codrin Morhan
 * @date 31st March 2012
 * @brief Contains a the mouse listener used by the table in the search panel.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @brief Mouse listener for the table in the search panel.
 * 
 * Designed to open up an edit event window on a double click.
 * @author Codrin Morhan 
 */
public class SearchTableMouseListener implements MouseListener{
	
	/**
	 * Holds the code that will be executed upon double click.<br/>
	 * Override it to fit your purpose.
	 */
	public void perform(){}	
	

	/**
	 * Set to run the perform() method when a double click is registered.
	 * @param e The event.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		final int NECESSARY_CLICKS = 2;
		if (e.getClickCount() == NECESSARY_CLICKS)
			perform();
	}
	
	/** 
	 * Interface method; not overridden. 
	 * @param e The event.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {/*not used*/}
	
	/** 
	 * Interface method; not overridden. 
	 * @param e The event.
	 */
	@Override
	public void mouseExited(MouseEvent e) {/*not used*/}
	
	/** 
	 * Interface method; not overridden. 
	 * @param e The event.
	 */
	@Override
	public void mousePressed(MouseEvent e) {/*not used*/}
	
	/** 
	 * Interface method; not overridden. 
	 * @param e The event.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {/*not used*/}
}
