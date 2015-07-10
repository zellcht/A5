/**
 * @file EventsTableListener.java
 * @author Codrin Morhan
 * @date 1st April 2012
 * @brief Contains a child of SearchTableMouseListener, designed for the events table.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import ui.NavigationPanel;
import common.SearchTableMouseListener;

/**
 * @brief Customised table listener.
 *
 * Overrides the parent's perform method.
 * @author Codrin Morhan
 */
public class EventsTableListener extends SearchTableMouseListener {
	
	/**
	 * Setting the performed action to be the opening of an edit event window.
	 */
	@Override
	public void perform(){
		new NavigationPanel().performEditEventClicked();
	}
}
