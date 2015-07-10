/**
 * @file CalendarCellRenderer.java
 * @author Samuel Jenkins
 * @date 23/03/2012
 * @brief Renders the cells to be placed into a Table.
 */
package views;

import hirondelle.date4j.DateTime;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import events.Event;
import events.EventList;

/**
 * 
 * This class renders the cells to be placed into a table. In this instance
 * for the JTable to be displayed by a YearView. It essentially takes in an
 * object and taking the required details from it, then creates a component 
 * to represent the object in the table.
 * 
 * @author Samuel Jenkins
 * @brief Renders the cells to be placed into a Table.
 * 
 */
public class CalendarCellRenderer implements TableCellRenderer {
	
	/**
	 * Creates and initialises the component that will be presented as a Table's cell
	 * in the Calendar. Due to the interface's specification and the dependent classes
	 * on this specification, the number of parameter's cannot be reduced to conform
	 * to Bob's coding Conventions without a substantial change in the classes used here
	 * that rely on this renderer.
	 * @param table the Table asking the renderer to render a cell
	 * @param value the value to be rendered
	 * @param isSelected true if the cell is to be rendered with the selection highlighted; otherwise false
	 * @param hasFocus if true, signifies the cell has been selected.
	 * @param row the row index of the cell being drawn.
	 * @param column the column index of the cell being drawn.
	 * @return The Component to be the cell in the table.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		boolean test = false;
		if(test){
			System.out.println("Called CalendarCellRenderer :: "+
						   	   "getTableCellRendererComponent(JTable, Object,"+
						       " bool, bool, int, int)");
		}
		if(value instanceof String){
			String s = value.toString();
			JLabel result = new JLabel(s);
			if(s.endsWith("Event")){
				result.setForeground(Color.RED);
				System.out.println("Event");
			}else if(s.endsWith("Events")){
				result.setForeground(Color.BLUE);
				System.out.println("Events");
			}
			String temp=s.substring(0, 1);
			CalendarTableModel ctm = (CalendarTableModel) table.getModel();
			String day=""+(row+1), month = ""+(column+1);
			if(day.length()==1)
				day = "0"+day;
			if(month.length()==1)
				month = "0"+month;
			String year=""+ctm.getDate().getYear();
			DateTime d = new DateTime(year+"-"+month+"-"+day);
			int weekday = d.getWeekDay();
			if(weekday == 1 || weekday == 7){
				result.setOpaque(true);
				result.setBackground(Color.LIGHT_GRAY);
			}
			return result;
		}
		if(value instanceof EventList){
			JList result = new JList();
			EventList el = (EventList) value;
			Event[] e = new Event[el.getSize()];
			for(int i=0; i<e.length;i++)
				e[i]=el.getEvent(i);
			result.setListData(e);
			return result;
		}
		return null;
	}

}
