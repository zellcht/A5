/**
 * @file TableGUIHandler.java
 * @author Samuel Jenkins
 * @date 23/03/2012
 * @brief Handles the User Interactions with the Table in YearView.
 */

package views;

import hirondelle.date4j.DateTime;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import ui.NavigationPanel;
import ui.UserInterface;

import digitalOrganiser.DigitalOrganiser;
import events.EventList;

/**
 * The TableGUIHandler class handles the User Interactions with the Table 
 * in YearView to do with Mouse input. The only method used is the mouseClicked
 * method, the other methods here can be overwritten in a child class of this
 * one without consequence.
 * 
 * @author Samuel Jenkins
 * @brief Handles the User Interactions with the Table in YearView.
 */
public class TableGUIHandler implements MouseListener{
	
	/**
	 * The Method that responds to the Left-Click on components
	 * this MouseListener listens to.
	 * @param event the MouseEvent that triggered the Listener.
	 */
	public void mouseClicked(MouseEvent event){
		Object source = event.getSource();
		int DIGIT_LIMIT = 9;
		if(source instanceof JTable){
			JTable j = (JTable) source;
			System.out.println("Table!");
			DayView dv = DigitalOrganiser.getDayView();
			int row = j.getSelectedRow(), col = j.getSelectedColumn();
			CalendarTableModel ctm = (CalendarTableModel) j.getModel();
			int year = ctm.getDate().getYear();
			String p = "", q = "-";
			if(row < DIGIT_LIMIT)
				 p = "0";
			if(col < DIGIT_LIMIT)
				 q = "-0";
			String s = year + q + (col+1) + "-" + p + (row+1);
			DateTime date = new DateTime(s);
			dv.setDate(date);
			DigitalOrganiser.getUserInterface().setViewPanel(dv);
			
		  	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.setLastSelectedCategoryIndex(
        			new NavigationPanel().getIndexOf("calendar"));
        	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.setLastSelectedItemIndex(new NavigationPanel()
        		.getIndexOf("day"));
        	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.resetSelection();
			
			dv.updateDisplay();
			return;
		}
		if(source instanceof JTableHeader){
			JTableHeader j = (JTableHeader) source;
			int column = j.columnAtPoint(event.getPoint());
			JTable table = j.getTable();
			CalendarTableModel ctm = (CalendarTableModel) table.getModel();
			EventList el = ctm.getEventData();
			int year = ctm.getDate().getYear();
			String p = "";
			if(column < DIGIT_LIMIT)
				 p = "0";
			String s = year + "-" + p+(column+1) + "-01";
			MonthView mv = DigitalOrganiser.getMonthView();
			DateTime oDate = new DateTime(s);
			mv.setDate(oDate);
			mv.setToDisplay(el);
			UserInterface ui = DigitalOrganiser.getUserInterface();
			ui.setViewPanel(mv);
			
		  	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.setLastSelectedCategoryIndex(
        			new NavigationPanel().getIndexOf("calendar"));
        	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.setLastSelectedItemIndex(new NavigationPanel()
        		.getIndexOf("month"));
        	DigitalOrganiser.getUserInterface().getNavigationPanel()
        	.resetSelection();
        	
			
			return;
		}
	}
	
	/**
	 * Not implemented, simply here to satisfy the requirements 
	 * of the MouseListener Interface.
	 * @param e the MouseEvent that triggered the Listener.
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Not implemented, simply here to satisfy the requirements 
	 * of the MouseListener Interface.
	 * @param e the MouseEvent that triggered the Listener.
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Not implemented, simply here to satisfy the requirements 
	 * of the MouseListener Interface.
	 * @param e the MouseEvent that triggered the Listener.
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * Not implemented, simply here to satisfy the requirements 
	 * of the MouseListener Interface.
	 * @param e the MouseEvent that triggered the Listener.
	 */
	public void mouseReleased(MouseEvent e) {}
}
