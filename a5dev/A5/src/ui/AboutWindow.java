/**
 * @file AboutWindow.java
 * @author Codrin Morhan
 * @date 24th March 2012
 * @brief Contains the AboutWindow class.
 * @version 3.1
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.explodingpixels.macwidgets.HudWindow;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @brief The about window.
 * 
 * Displays the team logo, team members and credits for things used in the project.<br/>
 * Might display incorrectly on Linux.<br/>
 * Uses the FormLayout, Mac Widgets.
 * @see <a href="http://www.jgoodies.com/articles/forms.pdf">FormLayout weblink</a>
 * @see <a href="https://code.google.com/p/macwidgets/">Mac Widgets weblink</a>
 * @author Codrin Morhan
 */
public class AboutWindow extends HudWindow {

	/**
	 * Create the panel.
	 */
	public AboutWindow() {
		getContentPane().setLayout(LAYOUT);		
		JLabel lblLogo = HudWidgetFactory.createHudLabel("");
		lblLogo.setBackground(Color.WHITE);
		lblLogo.setOpaque(true);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon ic = new ImageIcon();
		try{ ic = new ImageIcon(AboutWindow.class.getResource(
					"/ui/resources/logo3.png"));
		} catch (Exception e){ System.err.println("Error loading the logo.");}
		lblLogo.setIcon(ic);
		String COL = "1", ROW = "1", COLSPAN = "7", ROWSPAN = "2";
		getContentPane().add(lblLogo, COL+","+ROW+","+COLSPAN+","+ROWSPAN);
		
		
		addTeamSection();
		addCreditsSection();
		
		JButton btnClose = HudWidgetFactory.createHudButton("Close");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getJDialog().dispose();
			}
		});
		COL = "2"; ROW = "32"; COLSPAN = "5"; ROWSPAN = "1";
		getContentPane().add(btnClose, COL+","+ROW+","+COLSPAN+","+ROWSPAN );
		JLabel lblNewLabel = new JLabel("Group Seven :: CS235 "+
				":: Swansea University :: 2012");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setEnabled(false);
		COL = "1"; ROW = "34"; COLSPAN = "7"; ROWSPAN = "1";
		getContentPane().add(lblNewLabel, COL+","+ROW+","+COLSPAN+","+ROWSPAN);
		
		getJDialog().pack();
		getJDialog().setLocationRelativeTo(null);
		getJDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getJDialog().setVisible(true);
	}

	/**
	 * Adds the team members and their positions to the about window.
	 */
	private void addTeamSection(){
		JLabel lblTheTeam = HudWidgetFactory.createHudLabel("The Team:");
		String COL = "2", ROW = "4";	
		getContentPane().add(lblTheTeam, COL+","+ROW);	
		
		Font f = new JLabel().getFont();		
		f = f.deriveFont(Font.PLAIN);
		
		JLabel lblPnQ = HudWidgetFactory.createHudLabel
				("Planning and Quality Manager");
		lblPnQ.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "6";
		getContentPane().add(lblPnQ, COL+","+ROW);		
		JLabel lblSimon = HudWidgetFactory.createHudLabel("Simon Maling");
		lblSimon.setFont(f);
		lblSimon.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "8";
		getContentPane().add(lblSimon, COL+","+ROW);
		
		JLabel lblCIM = HudWidgetFactory.createHudLabel
				("Customer Interface Manager");
		lblCIM.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "10";
		getContentPane().add(lblCIM, COL+","+ROW);
		JLabel lblLloyd = HudWidgetFactory.createHudLabel("Lloyd Woodroffe");
		lblLloyd.setFont(f);
		lblLloyd.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "12";
		getContentPane().add(lblLloyd, COL+","+ROW);
		
		JLabel lblDM = HudWidgetFactory.createHudLabel("Design Manager");		
		lblDM.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "6"; ROW = "10";
		getContentPane().add(lblDM, COL+","+ROW);
		JLabel lblAde = HudWidgetFactory.createHudLabel("Adewale Odunlami");
		lblAde.setFont(f);
		lblAde.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "6"; ROW = "12";
		getContentPane().add(lblAde, COL+","+ROW);
		
		JLabel lblIM = HudWidgetFactory.createHudLabel
				("Implementation Manager");
		lblIM.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "14";
		getContentPane().add(lblIM, COL+","+ROW);
		JLabel lblSam = HudWidgetFactory.createHudLabel("Samuel Jenkins");
		lblSam.setFont(f);
		lblSam.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "16";
		getContentPane().add(lblSam, COL+","+ROW);
		JLabel lblYan = HudWidgetFactory.createHudLabel("Yan Sun");
		lblYan.setFont(f);
		lblYan.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "4"; ROW = "18";
		getContentPane().add(lblYan, COL+","+ROW);		
		
		JLabel lblTM = HudWidgetFactory.createHudLabel("Test Manager");
		lblTM.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "6"; ROW = "14";
		getContentPane().add(lblTM, COL+","+ROW);
		JLabel lblCeris = HudWidgetFactory.createHudLabel("Ceris Land");
		lblCeris.setFont(f);
		lblCeris.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "6"; ROW = "16";
		getContentPane().add(lblCeris, COL+","+ROW);
		JLabel lblCodrin = HudWidgetFactory.createHudLabel("Codrin Morhan");
		lblCodrin.setFont(f);
		lblCodrin.setHorizontalAlignment(SwingConstants.CENTER);
		COL = "6"; ROW = "18";
		getContentPane().add(lblCodrin, COL+","+ROW);			
	}
	
	/**
	 * Adds the credits to the about window.
	 */
	private void addCreditsSection(){
		JLabel lblCredits = HudWidgetFactory.createHudLabel("Credits:");
		String COL = "2", ROW = "20";
		getContentPane().add(lblCredits, COL+","+ROW);
		COL = "4"; ROW = "22";
		getContentPane().add(DATE4J_LINK, COL+","+ROW);
		COL = "6"; ROW = "22";
		getContentPane().add(GLAZEDLISTS_LINK, COL+","+ROW);
		COL = "4"; ROW = "24";
		getContentPane().add(JCALENDAR_LINK, COL+","+ROW);
		JGOODIES_LINK.setToolTipText(
				"forms-1.2.1, validation-2.4.0, common-1.3.0");
		COL = "6"; ROW = "24";
		getContentPane().add(JGOODIES_LINK, COL+","+ROW);
		COL = "4"; ROW = "26";
		getContentPane().add(MACWIDGETS_LINK, COL+","+ROW);
		COL = "6"; ROW = "26";
		getContentPane().add(OPENCSV_LINK, COL+","+ROW);
		ICONS_LINK.setToolTipText("Creator of the icons.");
		COL = "4"; ROW = "28";
		getContentPane().add(ICONS_LINK, COL+","+ROW);
		COL = "6"; ROW = "28";
		getContentPane().add(ABOUT_LOGO_URL, COL+","+ROW);
	}
	
	/** The layout used by the about panel. */
	private final static FormLayout LAYOUT = new FormLayout(new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.UNRELATED_GAP_COLSPEC,
			ColumnSpec.decode("max(96dlu;default)"), 
			//Dialog Units are based on the pixel size of the dialog font and so, 
			//they	grow and shrink with the font and resolution. 
			FormFactory.RELATED_GAP_COLSPEC,},
		new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("110px"), //not using dlu because it's image size
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.UNRELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.UNRELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.UNRELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.UNRELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC});
	
	/** The date4j link label. */
    private final static URLLabel DATE4J_LINK =
    		new URLLabel("Date4J 1.2.0",	"http://www.date4j.net/");
    /** The glazedlists link label. */
    private final static URLLabel GLAZEDLISTS_LINK = 
    		new URLLabel("GlazedLists 1.9",	"http://www.glazedlists.com/");
    /** The jcalendar link label. */
    private final static URLLabel JCALENDAR_LINK = 
    		new URLLabel(	"JCalendar 1.4",
    						"http://www.toedter.com/en/jcalendar/");
    /** The jgoodies link label. */
    private final static URLLabel JGOODIES_LINK = 
    		new URLLabel(	"JGoodies libraries",	
    						"http://www.jgoodies.com/downloads/libraries.html");
    /** The mac widgets link label. */
    private final static URLLabel MACWIDGETS_LINK = 
    		new URLLabel(	"Mac Widgets 0.9.5",	
    						"https://code.google.com/p/macwidgets/");
    /** The open csv link label. */
    private final static URLLabel OPENCSV_LINK = 
    		new URLLabel(	"OpenCSV 2.3",	
    						"http://opencsv.sourceforge.net/");
    
    /** The icons link label. */
    private final static URLLabel ICONS_LINK =
    		new URLLabel(	"Yusuke Kamiyamane",
							"http://p.yusukekamiyamane.com/");
    /** The about logo link label. */
    private final static URLLabel ABOUT_LOGO_URL = 
    		new URLLabel("Logo inspiration",
            	"http://finalfantasy.wikia.com/wiki/Logos_of_Final_Fantasy");
    
	public static void main (String[] args){
		new AboutWindow();		
	}
}
