/**
 * @file ContactsTableListener.java
 * @author Codrin Morhan
 * @date 1st April 2012
 * @brief Contains a child of SearchTableMouseListener, designed for the contacts table.
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;

import ui.NavigationPanel;
import common.SearchTableMouseListener;

/**
 * @brief Customised table listener.
 *
 * Overrides the parent's perform method.
 * @author Codrin Morhan
 */
public class ContactsTableListener extends SearchTableMouseListener {
	
	/**
	 * Setting the performed action to be the opening of an edit contact window.
	 */
	@Override
	public void perform(){
		new NavigationPanel().performEditContactClicked();
	}
}
