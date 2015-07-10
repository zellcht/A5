/**
 * @file EventTableFormat.java
 * @author Codrin Morhan
 * @date 20th March 2012
 * @brief Contains the EventTableFormat class.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import ca.odell.glazedlists.gui.TableFormat;
/**
 * @brief A custom table format.
 * 
 * Declares each column name and position.<br/>
 * Declares each column's values.<br/>
 * Needed for use by the glazed lists.<br/>
 * @see <a href="http://www.glazedlists.com/">GlazedLists weblink</a>
 * @author Codrin Morhan
 * 
 */  
public class EventTableFormat implements TableFormat<Event> {
    
	/** The number of columns to display. */
	public int getColumnCount() { return Event.ARRAY_SIZE; }


	/**
	 * Gets the title of the specified column.
	 * @param column The specified column.
	 */
    public String getColumnName(int column) {
    	if		(column == Event.ID_ARRAY_INDEX) 	  	 return "ID";
    	else if	(column == Event.TITLE_ARRAY_INDEX)	  	 return "Title";
        else if	(column == Event.CATEGORY_ARRAY_INDEX)	 return "Category";
        else if	(column == Event.START_ARRAY_INDEX)	  	 return "Starting";
        else if	(column == Event.END_ARRAY_INDEX)	  	 return "Ending";
        else if	(column == Event.LOCATION_ARRAY_INDEX)	 return "Location";
        else if	(column == Event.REPETITION_ARRAY_INDEX) return "Repeating";
        else if (column == Event.DESCRIPTION_ARRAY_INDEX)return "Description";
        else return "Persons Involved";
    	

    }

    /**
     * Gets the value of the specified field for the specified object.
     * @param e The specified object. It's a event object in our case.
     * @param column The specified field.
     */
    public Object getColumnValue(Event e, int column) {
    	if		(column == Event.ID_ARRAY_INDEX)		return e.getID();
    	else if	(column == Event.TITLE_ARRAY_INDEX)		return e.getTitle();
        else if	(column == Event.CATEGORY_ARRAY_INDEX) 	return e.getCategory();
        else if	(column == Event.START_ARRAY_INDEX)		return e.getStart();
        else if	(column == Event.END_ARRAY_INDEX)		return e.getEnd();
        else if	(column == Event.LOCATION_ARRAY_INDEX) 	return e.getLocation();
        else if	(column == Event.REPETITION_ARRAY_INDEX)
        	return e.getRepetition();
        else if (column == Event.DESCRIPTION_ARRAY_INDEX)
        	return e.getDescription();
        else return e.getPersonsInvolved();
    }
}
