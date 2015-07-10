/**
 * @file EventsCategoriesComboBoxRenderer.java
 * @author Codrin Morhan
 * @date 27th March 2012
 * @brief Contains the EventsCategoriesComboBoxRenderer class.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * @brief A custom combo box renderer.
 * 
 * Made so that a combo box gets icons an text as items.
 * @author Codrin Morhan
 */
@SuppressWarnings("serial")
public class EventsCategoriesComboBoxRenderer 
	extends JLabel
    implements ListCellRenderer {
	

	/**
	 * Default constructor; sets up the alignment and opacity.
	 */
	public  EventsCategoriesComboBoxRenderer() {
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}
	
	/**
	 * Gets the cell renderer.<br/>
	 * Makes every object in the list have an icon.<br/>
	 * Icons are got using the EventCategoryIcons class.<br/>
	 * @see EventCategoryIcons
	 * @param list The JList we're painting.
	 * @param value The value returned by list.getModel().getElementAt(index).
	 * @param index The cells index.
	 * @param isSelected True if the specified cell was selected.
	 * @param cellHasFocus True if the specified cell has the focus. 
	 */
	public Component getListCellRendererComponent(
	                    JList list,
	                    Object value,
	                    int index,
	                    boolean isSelected,
	                    boolean cellHasFocus) {
		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)
		String selectedIndex = ((String)value);
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		ImageIcon icon = new EventCategoryIcons().getIconForCategory
				(selectedIndex);
		String pet = selectedIndex;
		setIcon(icon);
		if (icon != null)
			setText(pet);
	
		return this;
	}
	
	/**
	 * Main class; performs class tests.
	 * @param args Arguments will be ignored.
	 */
	public static void main (String[] args){
		/** @test Displaying two combo boxes, one rendered normally and one using the class as renderer. */
        //Create the combo box.
        JComboBox eventsListWithIcons = new JComboBox(Event.CATEGORIES);
        JComboBox eventsListSimple = new JComboBox(Event.CATEGORIES);
        EventsCategoriesComboBoxRenderer  renderer =
        		new EventsCategoriesComboBoxRenderer ();
       // renderer.setPreferredSize(new Dimension(120, 16));
        eventsListWithIcons.setRenderer(renderer);
        //petList.setMaximumRowCount(3);
        
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(eventsListWithIcons, BorderLayout.PAGE_START);
        jp.add(eventsListSimple);
        
        
        JFrame jf = new JFrame ();
        jf.setDefaultCloseOperation(3);
        jf.add(jp);
        jf.setVisible(true);
        jf.pack();
	}
}
