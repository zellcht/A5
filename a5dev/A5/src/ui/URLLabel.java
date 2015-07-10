/**
 * @file URLLabel.java
 * @author Codrin Morhan
 * @date 14th March 2012
 * @brief Contains the URLLabel class.
 */

package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JLabel;

/**
 * @brief A label that is clickable and made to look like a link.
 * 
 * Clicking this label fires up a browser with the link address.<br/>
 * The label is set to a blue colour and underlined.
 * @author ludovicianul ; Codrin Morhan
 * @see <a href="http://www.codeproject.com/Articles/250101/Creating-a-URL-JLabel-in-Swing"> Source </a>
 */
@SuppressWarnings("serial")
public class URLLabel extends JLabel {
    /** 
     * Sets the url text. 
     * @param url The text for the url.
     */
    public void setURL(String url) {
        this.m_url = url;
    }
    /** 
     * Gets the url text.
     * @return The text of the url.
     */
    public String getURL(){return this.m_url;}
    /**
     * Default constructor for no arguments passed.
     * Calls the argumented constructor with default values. 
     */
    public URLLabel() {this("","");}
    /**
     * Default constructor
     * @param label The label text.
     * @param url The URL text.
     */
    public URLLabel(String label, String url) {
        super(label);
        setURL(url);
		int R = 132, G = 169, B = 255;
		Color c = new Color(R,G,B);
        setForeground(c);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new URLOpenAdapter());
    }

    

    /** 
     * Underlines the text.
     * @param g The widget's graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		int R = 132, G = 169, B = 255;
		Color c = new Color(R,G,B);
        g.setColor(c);
        Insets insets = getInsets();
        int left = insets.left;
        if (getIcon() != null) {
            left += getIcon().getIconWidth() + getIconTextGap();
        }
        int X1 = left;
        int Y1 = getHeight() - 1 - insets.bottom;
        int X2 = (int) getPreferredSize().getWidth()- insets.right;
        int Y2 = getHeight() - 1 - insets.bottom;
        
        g.drawLine(	X1, 
        			Y1, 
                    X2,
                    Y2);
    }

    /** @brief Listener class for the URL label.
     * 
     * Makes clicking the label to open a browser with the label link. 
     * @author ludovicianul; Codrin Morhan
     */    
    private class URLOpenAdapter extends MouseAdapter {

        @Override
        /**
         * Overrides the default listener. 
         * @param e The mouse event.
         */
        public void mouseClicked(MouseEvent e) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(getURL()));
                } catch (Throwable t) {
                    System.err.println("URLLabel: +" +
                    		"Couldn't launch browser window.");
                }
            }
        }
    }
    
    /** The variable holding the url text. */
    private String m_url;
}
