/**
 * @file ContactTableFormat.java
 * @author Yan Sun
 * @date 
 * @brief Contains the ContactTableFormat class.
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;

import ca.odell.glazedlists.gui.TableFormat;

/**
 * @brief A custom table format.
 * 
 * Declares each column name and position.<br/>
 * Declares each column's values.<br/>
 * Needed for use by the glazed lists.<br/>
 * @see <a href="http://www.glazedlists.com/">GlazedLists weblink</a>  
 * @author Yan Sun
 */
public class ContactTableFormat implements TableFormat<Contact> {
    
	/** The number of columns to display. */
	public int getColumnCount() { return Contact.ARRAY_SIZE; }

	/**
	 * Gets the title of the specified column.
	 * @param column The specified column.
	 */
    public String getColumnName(int column) {
        if(column == Contact.FIRSTNAME_ARRAY_INDEX)		  return "Frist Name";
        else if(column == Contact.LASTNAME_ARRAY_INDEX)   return "Last Name";
        else if(column == Contact.NICKNAME_ARRAY_INDEX)	  return "Nick Name";
        else if(column == Contact.ADDRESS_ARRAY_INDEX)	  return "Address";
        else if(column == Contact.POSTCODE_ARRAY_INDEX)   return "Post Code";
        else if(column == Contact.HOMENUMBER_ARRAY_INDEX) return "Home Number";
        else if(column == Contact.WORKNUMBER_ARRAY_INDEX) return "Work Number";
        else if(column == Contact.MOBILENUMBER_ARRAY_INDEX)
        	return "Mobile Number";
        else if(column == Contact.FAXNUMBER_ARRAY_INDEX)  return "Fax Number";
        else if(column == Contact.PERSONALEMAIL_ARRAY_INDEX)
        	return "Personal Email";
        else if(column == Contact.OTHEREMAIL_ARRAY_INDEX) return "Other Email";
        else if(column == Contact.WORKEMAIL_ARRAY_INDEX)  return "Work Email";
        else if(column == Contact.URL_ARRAY_INDEX) 	      return "Web Page";
        else											  return "ID";

    }

    /**
     * Gets the value of the specified field for the specified object.
     * @param c The specified object. It's a contact object in our case.
     * @param column The specified field.
     */
    public Object getColumnValue(Contact c, int column) {
    	if (column == Contact.ID_ARRAY_INDEX)			return c.getID();
    	else if(column == Contact.FIRSTNAME_ARRAY_INDEX)return c.getFirstName();
        else if(column == Contact.LASTNAME_ARRAY_INDEX) return c.getLastName();
        else if(column == Contact.NICKNAME_ARRAY_INDEX)	return c.getNickName();
        else if(column == Contact.ADDRESS_ARRAY_INDEX)	return c.getAddress();
        else if(column == Contact.POSTCODE_ARRAY_INDEX) return c.getPostCode();
        else if(column == Contact.HOMENUMBER_ARRAY_INDEX)
        	return c.getHomeNumber();
        else if(column == Contact.WORKNUMBER_ARRAY_INDEX)
        	return c.getWorkNumber();
        else if(column == Contact.MOBILENUMBER_ARRAY_INDEX)
        	return c.getMobileNumber();
        else if(column == Contact.FAXNUMBER_ARRAY_INDEX)return c.getFaxNumber();
        else if(column == Contact.PERSONALEMAIL_ARRAY_INDEX)
        	return c.getPersonalEmail();
        else if(column == Contact.OTHEREMAIL_ARRAY_INDEX)
        	return c.getOtherEmail();
        else if(column == Contact.WORKEMAIL_ARRAY_INDEX)return c.getWorkEmail();
        else 											return c.getUrl();
    }
}
