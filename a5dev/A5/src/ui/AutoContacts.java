package ui;
/** 
 * @file Contact.java
 * @author YAN SUN
 * @date 12/05/2012
 * @brief Contains the AutoContacts class.
 */

/**
 * @package ui
 * @brief Contains ui
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import digitalOrganiser.DigitalOrganiser;
/**
 * @brief The view of autocontacts.
 * 
 * Consists of a frame upon which all widgets representing a contact's data are added.<br/>
 * @see <a href="http://www.jgoodies.com/articles/forms.pdf">FormLayout weblink</a>
 * @author YAN SUN
 */
public class AutoContacts implements ActionListener {
 
	CardLayout cl = new CardLayout();
	JPanel jp = new JPanel();
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	int size;
	int m_time = 4000;
	Timer t;
    /**
     * Default constructor.<br/>
     * Sets the panel layout.<br/>
     * Initializes all the widgets.
     */
	public AutoContacts() {
		JFrame jf = new JFrame("Auto contacts");
		jp.setLayout(cl);
		jp2.setLayout(cl);

		size = DigitalOrganiser.getAddressBook().getSize();
		JLabel []jl = new JLabel[size];
		JLabel []jl2 = new JLabel[size];
		
		for(int i=1; i<jl.length; i++) {
			ImageIcon ii = new ImageIcon(DigitalOrganiser.getAddressBook().getContact(i).getPath());
			ii.setImage(ii.getImage().getScaledInstance(120,120,Image.SCALE_AREA_AVERAGING));
			jl[i] = new JLabel(ii);
			jp.add(jl[i],i+"");
		}
		
		for(int i=0; i<jl2.length; i++) {
			ImageIcon ii2 = new ImageIcon(DigitalOrganiser.getAddressBook().getContact(i).getPath());
			ii2.setImage(ii2.getImage().getScaledInstance(120,120,Image.SCALE_AREA_AVERAGING));
			jl2[i]= new JLabel(ii2);
			jp2.add(jl2[i], i+"");
		}
		
		String []buttonName = {"Next","Previous","Start","Stop","Set Time"};
		for(int i=0; i<buttonName.length; i++) {
			JButton jb = new JButton(buttonName[i]);
			jp1.add(jb);
			jb.addActionListener(this);
		}
		jf.add(jp, BorderLayout.WEST);
		jf.add(jp2, BorderLayout.EAST);
		jf.add(jp1, BorderLayout.SOUTH);
		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setResizable(false);
	}
    /**
     * Gets the time.
     * @return the time.
     */
	public int getTime(){return m_time;}
	/**
	 * Sets the time.
	 * @param t The time.
	 */
	public void setTime(int t){m_time = t;}
	 /** The method tell if the input is a integer
	  *  @param String value
	  *  @return Boolean 
	  */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	 /**
	 * set the time.
	 */
	public void setTime(){
		String input = JOptionPane.showInputDialog(
				"Enter a number between 1 to 300 (sec)");
		if(isInteger(input)==true){
			if(Integer.parseInt(input) <1 || Integer.parseInt(input) > 300){
				JOptionPane.showMessageDialog(null,"Your input is out of range", "Fail!", JOptionPane.INFORMATION_MESSAGE);
			} 
			else{
				setTime(Integer.parseInt(input)*1000);
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Your input is invalid", "Fail!", JOptionPane.INFORMATION_MESSAGE);
		 }
	 }
	@Override
	 /**
	 * Action listener
	 * @param ActionEvent e the action being performed
	 */
	public void actionPerformed(ActionEvent e) {
		String comm = e.getActionCommand();
		System.out.println(comm);
		if("Next".equals(comm)) {
			cl.next(jp);
			cl.next(jp2);
		} else if("Previous".equals(comm)) {
			cl.previous(jp);
			cl.previous(jp2);
		} else if("Start".equals(comm)) {
			t = new Timer(getTime(),this);
			t.start();
		} else if("Stop".equals(comm)) {
			t.stop();
		} else if("Set Time".equals(comm)) {
			setTime();
		}else {
			cl.next(jp);
			cl.next(jp2);
		}
	}
}
