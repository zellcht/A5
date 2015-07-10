/**
 * @file TextFieldLimiter.java
 * @author Codrin Morhan
 * @date 27th March 2012
 * @brief Contains the TextFieldLimiter class.
 */

/**
 * @package common
 * @brief Things used in common, so far only between addressBook and events.
 */
package common;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @brief Provides a way to limit text fields input length.
 * 
 * Usage: <i>JTextField variable</i>.setDocument(new TextFieldLimiter(<i>character limit</i>))
 * @author Codrin Morhan
 */
@SuppressWarnings("serial")
public class TextFieldLimiter extends PlainDocument {
    private int limit;
    // optional uppercase conversion
    private boolean toUppercase = false;
    
    public TextFieldLimiter(int limit) {
        super();
        this.limit = limit;
    }
    
    TextFieldLimiter(int limit, boolean upper) {
        super();
        this.limit = limit;
        toUppercase = upper;
    }
    
    public void insertString
            (int offset, String  str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) return;
        
        if ((getLength() + str.length()) <= limit) {
            if (toUppercase) str = str.toUpperCase();
            super.insertString(offset, str, attr);
        }
    }
    
    public static void main(String[] args){
    	JFrame jj = new JFrame();
    	jj.setDefaultCloseOperation(3);
    	jj.setVisible(true);
    	JTextField jtf = new JTextField();
    	jtf.setDocument(new TextFieldLimiter(5));
    	jj.add(jtf);
    	jj.pack();
    }
}