/**
 * @file YearViewGUIHandler.java
 * @author Samuel Jenkins
 * @date 19/02/2012
 * @brief A class to handle the User Interaction with the YearView.
 */
package views;

import hirondelle.date4j.DateTime;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * 
 * This class implements the behaviour required of the YearView's User
 * Interactions, please note that this class is separate from the Object
 * the YearView displays. This is primarily to reduce the intelligence of
 * this class and restrict it to the elements that require the same
 * functionality, particularly the Buttons on the YearView.
 * 
 * @author Sam
 * @brief A class to handle the User Interaction with the YearView.
 */
public class YearViewGUIHandler implements ActionListener{
	
	/**
	 * Accessor method to the m_View field.
	 * @return The YearView which holds the component this listens to.
	 */
	public YearView getView(){
		return m_View;
	}
	
	/**
	 * Mutator method for the m_View field.
	 * @param view the YearView which holds the component this listens to.
	 * @return true on execution.
	 */
	public boolean setView(YearView view){
		m_View = view;
		return true;
	}
	
	/**
	 * Constructs a YearViewGUIHandler.
	 * @param oView The YearView which holds the component it listens to.
	 */
	public YearViewGUIHandler(YearView oView){
		setView(oView);
	}
	
	/**
	 * The method that is called when a component listened to generates an event.
	 * @param event the GUI event generated.
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		YearView view = this.getView();
		JTable t = view.getToDisplay();
		CalendarTableModel ctm = (CalendarTableModel) t.getModel();
		DateTime d = ctm.getDate();
		int time = d.getYear().intValue(), tp=-1;
		JLabel temp;
		Component[] comps = view.getComponents();
		for(int i=0;i<comps.length;i++)
			if(comps[i] instanceof JLabel)
				tp = i;
		if(tp<0){
			System.err.println("Error in View," +
					" cannot display the Year above Calendar");
		}
		temp = (JLabel) comps[tp];
		if(source instanceof CalendarButton){
			CalendarButton b = (CalendarButton) source;
			switch(b.getType()){
				case CalendarButton.NEXT_BUTTON:
					updateCalendar(view,time+1,ctm,temp);
					break;
				case CalendarButton.PREVIOUS_BUTTON:
					updateCalendar(view,time-1,ctm,temp);
					break;
				default:
					
					break;
			}
		}
	}
	
	/**
	 * Updates the YearView's display when invoked to a specific year.
	 * @param view YearView holding the components.
	 * @param year The new year that the view is being updated to.
	 * @param ctm The CalendarTableModel that holds the data for the YearView.
	 * @param temp The JLabel that the YearView has to show what Year it displays.
	 * @return true on success, false otherwise.
	 */
	private boolean updateCalendar(YearView view, int year, 
								   CalendarTableModel ctm, JLabel temp)
	{
		DateTime d = new DateTime(year+"-01-01");
		ctm.refreshData(d);
		ctm.setDate(d);
		if(temp==null){
			view.repaint();
			return false;
		}
		temp.setText(""+d.getYear());
		view.repaint();
		return true;
	}
	
	/** The YearView which holds the component that are listened to by this GUIHandler. */
	private YearView m_View;
	
}
