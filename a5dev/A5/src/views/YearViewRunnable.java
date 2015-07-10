/**
 * @file YearViewRunnable.java
 * @author Samuel Jenkins
 * @date 31/03/2012
 * @brief Creates a Runnable Object to for an EventQueue to handle the YearView's layout to display.
 *
 */
package views;

import hirondelle.date4j.DateTime;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

/**
 * 
 * This class was created to remove a class declaration in the middle of the YearView's
 * layoutToDisplay method.
 * 
 * This class was created after the unsanctioned alteration of my YearView Class by Codrin Morhan,
 * who declared it within the YearView, where it does not seem appropriate.
 * 
 * @author Samuel Jenkins
 * @brief Creates a Runnable Object to for an EventQueue to handle the YearView's layout to display.
 *
 */
public class YearViewRunnable implements Runnable {
	
	YearViewRunnable(YearView yv, DateTime date, YearViewGUIHandler handler){
		view = yv;
		this.handler = handler;
		this.date = date;
	}
	
	private YearView view;
	private DateTime date;
	private YearViewGUIHandler handler;
	
	/**
	 * The run methods required of all Runnable objects, it lays out the
	 * components for the YearView to Display.
	 */
	@Override
	public void run() {
		int HALF = YearView.HALF;
		int ELEVENTH = YearView.ELEVENTH;
		int DIVISOR = YearView.DIVISOR;
		int THIRD = YearView.THIRD;
		SpringLayout sl = (SpringLayout) view.getLayout();
		int typePrev = CalendarButton.PREVIOUS_BUTTON;
		int typeNext = CalendarButton.NEXT_BUTTON;
		CalendarButton prevYear = new CalendarButton(typePrev,"Previous Year");
		CalendarButton nextYear = new CalendarButton(typeNext,"Next Year");
		JLabel currentYear = new JLabel(date.getYear()+"");
		view.add(prevYear);
		prevYear.addActionListener(handler);
		nextYear.addActionListener(handler);
		sl.putConstraint(SpringLayout.NORTH, prevYear,
				 YearView.COMPONENT_GAP/DIVISOR,
				 SpringLayout.NORTH, view);
		sl.putConstraint(SpringLayout.WEST, prevYear,
				 HALF*HALF*(YearView.COMPONENT_GAP/DIVISOR),
				 SpringLayout.WEST, view);
		view.add(currentYear);
		sl.putConstraint(SpringLayout.NORTH, currentYear,
				 YearView.COMPONENT_GAP/DIVISOR,
				 SpringLayout.NORTH, view);
		sl.putConstraint(SpringLayout.WEST, currentYear,
				 THIRD*(YearView.COMPONENT_GAP/HALF),
				 SpringLayout.WEST, prevYear);
		view.add(nextYear);
		sl.putConstraint(SpringLayout.NORTH, nextYear,
				 YearView.COMPONENT_GAP/DIVISOR,
				 SpringLayout.NORTH, view);
		sl.putConstraint(SpringLayout.WEST, nextYear,
				ELEVENTH*(YearView.COMPONENT_GAP/DIVISOR),
				 SpringLayout.WEST, currentYear);
		JTable oTable = view.getToDisplay();
		JScrollPane temp = new JScrollPane();
		temp.setViewportView(oTable);
		view.add(temp);
		sl.putConstraint(SpringLayout.NORTH, temp,
						 YearView.COMPONENT_GAP/(HALF*HALF),
						 SpringLayout.NORTH, view);
		sl.putConstraint(SpringLayout.WEST, temp, 
				 YearView.COMPONENT_GAP/(HALF*HALF*HALF*HALF),
				 SpringLayout.WEST, view);
		sl.putConstraint(SpringLayout.SOUTH, view,
				 YearView.COMPONENT_GAP/(HALF*HALF*HALF),
				 SpringLayout.SOUTH, temp);
		sl.putConstraint(SpringLayout.EAST, view, 
						 YearView.COMPONENT_GAP/(HALF*HALF*HALF*HALF),
						 SpringLayout.EAST, temp);
		
	}

}