/**
 * @file UserInterface.java
 *
 * @brief The application's main user interface.
 * 
 * <b>Justification for not using the G5 user interface code:</b><br/>
 * First of all I disliked their design. Secondly it was incomplete anyway.<br/>
 * Because it was incomplete it meant I had to do a lot of work anyway.<br/>
 * So instead, I decided to do the same amount of work on a better code base.<br/>
 * I took the decision after considering how much code I already have from them,
 * and how much code I would have to write to get the foundations down.<br/>
 * Being given that their code was:
 * <ul>
 * 	<li>incomplete</li>
 *  <li>not following coding conventions</li>
 *  <li>poorly documented</li>
 * </ul>
 * I felt that starting from scratch would be a worthy investment.<br/>
 * Note that no code from G7 A4 user interface was used either. 
 * 
 * @author Codrin Morhan
 * @date 6th March 2012
 */

/**
 * @package ui
 * @brief Contains main user interface & resources for it and for the events category icons. 
 */
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @brief Sets up and displays the application's main window.
 * 
 * The window is formed of two panels and a menu bar.<br/>
 * The panel on the left is initialised with many labels in it.<br/>
 * The panel on the right displays the calendar views.<br/>
 * The menu bar is designate to provide other functionality, such as
 * displaying the window with details about the application.<br/>
 * 
 * @author Codrin Morhan
 */
@SuppressWarnings("serial")
public class UserInterface extends JFrame {
	
	/**
	 * Gets the navigation panel.
	 * @return The panel.
	 */
	public NavigationPanel getNavigationPanel(){return m_NavigationPanel;}
	/**
	 * Sets the navigation panel.
	 * @param np The new navigation panel.
	 */
	private void setNavigationPanel(NavigationPanel np){m_NavigationPanel = np;} 
	
	/** 
	 * Gets the menu bar.<br/>
	 * Named liked this due to JFrame already having such a method.
	 * @return The menu bar for this frame. 
	 */
	private JMenuBar get_MenuBar(){return m_menuBar;}
	/** 
	 * Sets the menu bar.<br/>
	 * Named liked this due to JFrame already having such a method.
	 * @param bar The new menu bar widget.
	 */
	private void set_MenuBar(JMenuBar bar){m_menuBar = bar;}
	/**
	 * Gets the menu widget used for "About".
	 * @return The menu.
	 */
	private JMenu getAboutMenu(){return m_AboutMenu;}
	/** 
	 * Sets the "About" menu widget.
	 * @param jm The new menu.
	 */
	private void setAboutMenu(JMenu jm){m_AboutMenu = jm;}
	/** 
	 * Gets the "File" menu widget.
	 * @return The menu.
	 */
	private JMenu getFileMenu(){return m_FileMenu;}
	/**
	 * Sets the "File" menu widget. 
	 * @param jm The new menu.
	 */
	private void setFileMenu(JMenu jm){m_FileMenu = jm;} 
	/**
	 * Gets the quit menu item.
	 * @return The menu.
	 */
	private JMenuItem getQuitMenuItem(){return m_QuitMenuItem;}
	/** 
	 * Sets the quit menu item.
	 * @param jmi The new menu item.
	 */
	private void setQuitMenuItem(JMenuItem jmi){m_QuitMenuItem = jmi;}
	/**
	 * Gets the menu items separator.
	 * @return The separator.
	 */
	private Separator getMenuItemsSeparator(){return m_MenuItemsSeparator;}
	/**
	 * Sets the menu items separator. 
	 * @param s The new separator.*/
	private void setMenuItemsSeparator(Separator s){m_MenuItemsSeparator = s;}
	
	/** 
	 * Gets the panel designated to be a separating line between navigation and the calendar panels.
	 * @return The panel.
	 */
	private JPanel getSeparatingLine(){return m_SeparatingLine;}
	/** 
	 * Sets the panel designated to be a separating line.
	 * @param jp The new panel.
	 */
	private void setSeparatingLine(JPanel jp){m_SeparatingLine = jp;}
	
	/**
	 * Sets the view panel.
	 * @param p The new panel.
	 */
	public void setViewPanel(JPanel p){
		m_ViewPanel.removeAll();
		m_ViewPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("fill:default:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:default:grow"),}));
		m_ViewPanel.add(p, "1, 1");
		//System.out.println(m_ViewPanel.getSize());
		m_ViewPanel.updateUI();
		

	}
	/**
	 * Gets the view panel.
	 * @return The panel
	 */
	public JPanel getViewPanel(){return m_ViewPanel;}
	
    /**
     * Creates the user interface.
     */
    public UserInterface() {
    	super();

    	setUpLNF();
        setSeparatingLine(new JPanel());
        set_MenuBar(new JMenuBar());
        setAboutMenu(new JMenu());
        setFileMenu(new JMenu());
        setQuitMenuItem(new JMenuItem());
        setMenuItemsSeparator(new Separator());
        
        setTitle("Digital Organiser - G7");
    	initComponents();
    	
    	EventQueue.invokeLater(new Runnable() {
			
				@Override
				public void run() {
					setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					setVisible(true);
					pack();
				}
    	});
    }


    
    /** Sets up the menu bar. */
    private void initialiseMenuBar(){
    	getFileMenu().setText("File");

        getFileMenu().add(getMenuItemsSeparator());

        getQuitMenuItem().setText("Quit");
        getQuitMenuItem().addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent evt) {
                quitMenuItemMousePressed(evt);
            }
        });
        getFileMenu().add(getQuitMenuItem());

        get_MenuBar().add(getFileMenu());
        
        get_MenuBar().add(Box.createHorizontalGlue());

        getAboutMenu().setText("About");
        getAboutMenu().addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent evt) {
                aboutMenuMouseClicked(evt);
            }
        });
        get_MenuBar().add(getAboutMenu());

        setJMenuBar(get_MenuBar());
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        initialiseMenuBar();
        
        setNavigationPanel(new NavigationPanel());
        JPanel line = getSeparatingLine();
        initialiseSeparatingLine(line);
        
        //setting some initial width to the view panel
        int WIDTH = 800,
        //height is dictated by the nav panel anyway	
        	HEIGHT = 200;
        getViewPanel().setPreferredSize(new Dimension(WIDTH,HEIGHT));
       
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout); 
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(	getNavigationPanel(), 
                    				GroupLayout.PREFERRED_SIZE, 
                    				GroupLayout.DEFAULT_SIZE, 
                    				GroupLayout.PREFERRED_SIZE)
                    .addComponent(	line,
    		                		GroupLayout.PREFERRED_SIZE, 
    		        				GroupLayout.DEFAULT_SIZE, 
    		        				GroupLayout.PREFERRED_SIZE)
                    .addComponent(	getViewPanel()))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(	getNavigationPanel(),
                				GroupLayout.DEFAULT_SIZE, 
                				GroupLayout.DEFAULT_SIZE,
                				Short.MAX_VALUE)
                .addComponent(  line,
                				GroupLayout.Alignment.TRAILING, 
                				GroupLayout.DEFAULT_SIZE, 
                				GroupLayout.DEFAULT_SIZE,
                				Short.MAX_VALUE)
                .addComponent(	getViewPanel())
            );
    }
    
    /** 
     * Listener for the "Quit" menu item in the menu bar.
     * @param evt The event.
     */
    private void quitMenuItemMousePressed(MouseEvent evt) {                                            
        //System.out.println("Quit pressed.");
        System.exit(0);
    } 
    
    /** 
     * Listener for the "About" menu in the menu bar.
     * @param evt The event.
     */
    private void aboutMenuMouseClicked(MouseEvent evt) {
        //System.out.println("About clicked.");
        new AboutWindow();
    }

    /**  
     * Sets up a panel to look like a line of a certain width and colour.
     * @param jp The panel to be transformed.
     */
    private void initialiseSeparatingLine(JPanel jp){
    	int R,G,B;
    	R=G=B=108;
    	jp.setBackground(new Color(R, G, B));
    	int min = 0,
    		width = 1; //pixels
        GroupLayout gl = new GroupLayout(jp);
        jp.setLayout(gl);
        gl.setHorizontalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(min, width, Short.MAX_VALUE)
        );
        gl.setVerticalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(min, min, Short.MAX_VALUE)
        );
    }
    
	/**
	 * Tries to set the look and feel to the platform's native.<br/>
	 * Otherwise sets it to Nimbus.
	 */
    public static final void setUpLNF(){
        try {
            for
            	(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                	if ("Nimbus".equals(info.getName()))
                		UIManager.setLookAndFeel(info.getClassName());
                	
                    if ("GTK+".equals(info.getName())) {
                    		UIManager.setLookAndFeel(info.getClassName());
                    		break;
                    }
                    if ("Windows".equals(info.getName())) {
                        		UIManager.setLookAndFeel(info.getClassName());
                        		break;
                    }
                	if ("Mac OS X".equals(info.getName())) {
                		UIManager.setLookAndFeel(info.getClassName());
                		break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(	UserInterface.class.getName())
            					.log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(	UserInterface.class.getName())
            					.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(	UserInterface.class.getName())
            					.log(Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(	UserInterface.class.getName())
            					.log(Level.SEVERE, null, ex);
        }        
    }

    /** The panel on the right. */
    private JPanel m_ViewPanel = new JPanel();
    
    /** The window's menu bar widget. */
    private JMenuBar m_menuBar; 
	    /** The file menu widget. */
		private JMenu m_FileMenu;
			/** The separator in front of the "Quit" menu item. */
		    private Separator m_MenuItemsSeparator;
		    /** The "Quit" menu item. */
		    private JMenuItem m_QuitMenuItem;
	    /** The about menu widget. */    
	    private JMenu m_AboutMenu;
    
    /** The line between navigation and calendar panel. */
    private JPanel m_SeparatingLine;
    
    /** The navigation panel. */
    private NavigationPanel m_NavigationPanel;
   
    /**
     * Tests the class.
     * @param args Arguments are ignored.
     */
    public static void main(String args[]) {
    	/** @test Displays the user interface. */
    	new UserInterface();
    
    }
}