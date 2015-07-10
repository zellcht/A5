/**
 * @file ContactView.java
 * @author Yan Sun
 * @date 
 * @brief Contains the ContactView class.
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import common.TextFieldLimiter;

/**
 * @brief The view part of the Contact MVC architecture.
 * 
 * Consists of a panel upon which all widgets representing a contact's data are added.<br/>
 * Uses the FormLayout.
 * @see <a href="http://www.jgoodies.com/articles/forms.pdf">FormLayout weblink</a>
 * @author YAN SUN -A5 YAN SUN -A6
 */
public class ContactView implements ActionListener {
	/**
     * Sets the file chooser.
     * @param jfc The new image.
     * @return True on success, false on failure.
     */
    public boolean setButton(JButton jb){
    	m_Button = jb;
        return true;
    }
    /**
     * Gets fileChooser.
     * @return The widget.
     */
    public JButton getButton() {return m_Button;}
	/**
     * Sets the file chooser.
     * @param jfc The new image.
     * @return True on success, false on failure.
     */
    public boolean setFileChooser(JFileChooser jfc){
    	m_FileChooser = jfc;
        return true;
    }
    /**
     * Gets fileChooser.
     * @return The widget.
     */
    public JFileChooser getFileChooser() {return m_FileChooser;}
	/**
     * Sets the route.
     * @param route The new route.
     * @return True on success, false on failure.
     */
    public boolean setRoute(String route){
    	m_Route = route;
        return true;
    }
    /**
     * Gets route.
     * @return The widget.
     */
    public String getRoute() {return m_Route;}
	/**
     * Sets the image.
     * @param ii The new image.
     * @return True on success, false on failure.
     */
    public boolean setImage(ImageIcon ii){
    	m_Image = ii;
        return true;
    }
    /**
     * Gets image.
     * @return The widget.
     */
    public ImageIcon getImage() {return m_Image;}
	/**
     * Sets the image label.
     * @param label The new image label.
     * @return True on success, false on failure.
     */
    public boolean setImageLabel(JLabel label){
    	m_ImageLabel = label;
        return true;
    }
    /**
     * Gets image label.
     * @return The widget.
     */
    public JLabel getImageLabel() {return m_ImageLabel;}
	/**
     * Sets the first name text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setFirstNameTextField(JTextField tf){
    	m_FirstNameTextField = tf;
        return true;
    }
    /**
     * Gets first name text field.
     * @return The widget.
     */
    public JTextField getFirstNameTextField() {return m_FirstNameTextField;}
    
	/**
     * Sets the last name text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setLastNameTextField(JTextField tf){
    	m_LastNameTextField = tf;
        return true;
    }
    /**
     * Gets last name text field.
     * @return The widget.
     */
    public JTextField getLastNameTextField() {return m_LastNameTextField;}
    
	/**
     * Sets the nick name text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setNickNameTextField(JTextField tf){
    	m_NickNameTextField = tf;
        return true;
    }
    /**
     * Gets the nick name text field.
     * @return The widget.
     */
    public JTextField getNickNameTextField() {return m_NickNameTextField;}
    
    /**
     * Sets the address text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setAddressTextField(JTextField tf){
    	m_AddressTextField = tf;
        return true;
    }
    /**
     * Gets the address text field.
     * @return The widget
     */
    public JTextField getAddressTextField() {return m_AddressTextField;}
    
    /**
     * Sets the post code text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setPostCodeTextField(JTextField tf){
    	m_PostCodeTextField = tf;
        return true;
    }
    /**
     * Gets the post code text field.
     * @return The widget.
     */
    public JTextField getPostCodeTextField() {return m_PostCodeTextField;}
    
    /**
     * Sets the home number text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setHomeNumberTextField(JTextField tf){
    	m_HomeNumberTextField = tf;
        return true;
    }
    /**
     * Gets the home number text field.
     * @return The widget.
     */
    public JTextField getHomeNumberTextField() {return m_HomeNumberTextField;}
    
    /**
     * Sets the work number text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setWorkNumberTextField(JTextField tf){
    	m_WorkNumberTextField = tf;
        return true;
    }
    /**
     * Gets the work number text field.
     * @return The widget.
     */
    public JTextField getWorkNumberTextField() {return m_WorkNumberTextField;}
    
    /**
     * Sets the mobile number text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setMobileNumberTextField(JTextField tf){
    	m_MobileNumberTextField = tf;
        return true;
    }
    /**
     * Gets the mobile number text field.
     * @return The widget.
     */
    public JTextField getMobileNumberTextField() {
    	return m_MobileNumberTextField;
    }
    
    /**
     * Sets the fax number text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setFaxTextField(JTextField tf){
    	m_FaxTextField = tf;
        return true;
    }
    /**
     * Gets the fax number text field.
     * @return The widget.
     */
    public JTextField getFaxTextField() {return m_FaxTextField;}
    
    /**
     * Sets the personal email text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setPersonalEmailTextField(JTextField tf){
    	m_PersonalEmailTextField = tf;
        return true;
    }
    /**
     * Gets the personal email text field.
     * @return The widget.
     */
    public JTextField getPersonalEmailTextField() {
    	return m_PersonalEmailTextField;
    }
    
    /**
     * Sets the work email text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setWorkEmailTextField(JTextField tf){
    	m_WorkEmailTextField = tf;
        return true;
    }
    /**
     * Gets the work email text field.
     * @return The widget.
     */
    public JTextField getWorkEmailTextField() {return m_WorkEmailTextField;}
    
    /**
     * Sets the other email text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setOtherEmailTextField(JTextField tf){
    	m_OtherEmailTextField = tf;
        return true;
    }
    /**
     * Gets the other email text field.
     * @return The widget.
     */
    public JTextField getOtherEmailTextField() {return m_OtherEmailTextField;}
    
    /**
     * Sets the url text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setUrlTextField(JTextField tf){
    	m_UrlTextField = tf;
        return true;
    }
    /**
     * Gets the url text field.
     * @return The widget..
     */
    public JTextField getUrlTextField() {return m_UrlTextField;}
    
    /**
     * Sets the photo text field.
     * @param tf The new text field.
     * @return True on success, false on failure.
     */
    public boolean setPhotoTextField(JTextField tf){
    	m_PhotoTextField = tf;
        return true;
    }
    /**
     * Gets the url text field.
     * @return The widget.
     */
    public JTextField getPhotoTextField() {return m_PhotoTextField;}
    
    /**
     * Gets the content panel.
     * @return The content panel.
     */
    public JPanel getContentPanel() {return m_ContentPanel;}
    
	/**
	 * Sets the form builder used to construct the layout. 
	 * @param fb The new form builder.
	 */
	private void setFormBuilder(DefaultFormBuilder fb){m_FormBuilder = fb;}
	/**
	 * Gets the form builder used to construct the layout.
	 * @return The form builder.
	 */
	protected DefaultFormBuilder getFormBuilder(){return m_FormBuilder;}
	
	/**
	 * Sets the validation result model.
	 * @param vrm The new validation form model.
	 */
	public void setValidationResultModel(ValidationResultModel vrm){
		m_ValidationResultModel = vrm;
	}
	/**
	 * Gets the validation result mode.
	 * @return The validation result model.
	 */
	public ValidationResultModel getValidationResultModel(){
		return m_ValidationResultModel;
	}
	
    /**
     * Gets the built panel, that is, the view.
     * @return The panel.
     */
    public JPanel getPanel(){
		JPanel jp = getFormBuilder().getPanel();
        return jp;
    }
    
    /**
     * Default constructor.<br/>
     * Sets the panel layout.<br/>
     * Initializes all the widgets.
     */
	public ContactView() {
		final String RESIZE_BEHAVIOUR_NONE = "n";
		//Dialog Units are based on the pixel size of the dialog font and so, 
		//they	grow and shrink with the font and resolution.
		//Better than pixels.
		final String COL2_WIDTH = "10dlu"; //Dialog Units
		final String COL2_SPEC = COL2_WIDTH+":"+RESIZE_BEHAVIOUR_NONE;
		final String COL3_SPEC = "90dlu";//Dialog Units
		final String COL4_SPEC = "10dlu";//Dialog Units
		final String COL5_SPEC = "50dlu";//Dialog Units
		final String COL6_SPEC = "1dlu";//Dialog Units
		final String COL7_SPEC = "90dlu";//Dialog Units
        FormLayout layout = new FormLayout(	"pref, "+
        			COL2_SPEC +","+ COL3_SPEC +","+ COL4_SPEC +","+
        			COL5_SPEC +","+ COL6_SPEC +","+ COL7_SPEC +
        			",pref");
        
        setFormBuilder(new DefaultFormBuilder(layout));
        int columnCount = getFormBuilder().getColumnCount();
        getFormBuilder().setDefaultDialogBorder();
        
        initNameField();
        initAddressField();
        initNumberField();
        initOtherField();
        
        setValidationResultModel(new DefaultValidationResultModel());
        JComponent validationResultsComponent =
                ValidationResultViewFactory.createReportList(
                        getValidationResultModel());
        getFormBuilder().appendUnrelatedComponentsGapRow();
        final String ROW_ALIGNMENT = "fill";
        final String COL_WIDTH = "50dlu";//dialog units
        final String RESIZE_BEHAVIOUR_GROW = "grow";
        final String ROW_SPEC = ROW_ALIGNMENT +":"+
        						COL_WIDTH +":"+
        						RESIZE_BEHAVIOUR_GROW;
        getFormBuilder().appendRow(ROW_SPEC);
        final int LINES_DOWN = 2;
        getFormBuilder().nextLine(LINES_DOWN);
        getFormBuilder().append(validationResultsComponent, columnCount);
    }
	
	/** Sets up the name section text fields. */
	private void initNameField(){
		getFormBuilder().appendSeparator();
		getFormBuilder().appendSeparator();
		JTextField fName = new JTextField();
		fName.setDocument(new TextFieldLimiter(Contact.NAME_MAXLENGTH));
        setFirstNameTextField(fName);
        getFormBuilder().append("First Name:",getFirstNameTextField());
        
		JTextField nName = new JTextField();
		nName.setDocument(new TextFieldLimiter(Contact.NAME_MAXLENGTH));
        setNickNameTextField(nName);
        getFormBuilder().append("Nick Name:",getNickNameTextField());
        
		JTextField lName = new JTextField();
		lName.setDocument(new TextFieldLimiter(Contact.NAME_MAXLENGTH));
        setLastNameTextField(lName);
        getFormBuilder().append("Last Name:",getLastNameTextField());
        getFormBuilder().nextLine(1);
        
        getFormBuilder().appendSeparator();
	}
	
	/** Sets up the address section text fields. */
	private void initAddressField(){
		JTextField address = new JTextField();
		address.setDocument(new TextFieldLimiter(Contact.ADDRESS_MAXLENGTH));
        setAddressTextField(address);
        getFormBuilder().append("Address:",getAddressTextField());
        
    	JTextField postCode = new JTextField();
    	postCode.setDocument(new TextFieldLimiter(Contact.POSTCODE_MAXLENGTH));
        setPostCodeTextField(postCode);
        getFormBuilder().append("Post Code:",getPostCodeTextField());
        
        getFormBuilder().appendSeparator();
	}
	
	/** Sets up the phone numbers section text fields. */
	private void initNumberField(){
		JTextField hNumber = new JTextField();
		hNumber.setDocument(new TextFieldLimiter(Contact.TEL_MAXLENGTH));
        setHomeNumberTextField(hNumber);
        getFormBuilder().append("Home Number:",getHomeNumberTextField());
        
		JTextField wNumber = new JTextField();
		wNumber.setDocument(new TextFieldLimiter(Contact.TEL_MAXLENGTH));
        setWorkNumberTextField(wNumber);
        getFormBuilder().append("Work Number:",getWorkNumberTextField());
        
		JTextField mNumber = new JTextField();
		mNumber.setDocument(new TextFieldLimiter(Contact.TEL_MAXLENGTH));
        setMobileNumberTextField(mNumber);
        getFormBuilder().append("Mobile Number:",getMobileNumberTextField());
        
		JTextField fNumber = new JTextField();
		fNumber.setDocument(new TextFieldLimiter(Contact.TEL_MAXLENGTH));
        setFaxTextField(fNumber);
        getFormBuilder().append("Fax Number:",getFaxTextField());
        
        getFormBuilder().appendSeparator();
	}
	
	/** Sets up the email text fields. */
	private void initOtherField(){
		JTextField pEmail = new JTextField();
		pEmail.setDocument(new TextFieldLimiter(Contact.EMAIL_MAXLENGTH));
        setPersonalEmailTextField(pEmail);
        getFormBuilder().append("Personal Email:",getPersonalEmailTextField());
        
		JTextField oEmail = new JTextField();
		oEmail.setDocument(new TextFieldLimiter(Contact.EMAIL_MAXLENGTH));
        setOtherEmailTextField(oEmail);
        getFormBuilder().append("Other Email:",getOtherEmailTextField());
        getFormBuilder().nextLine();
        
		JTextField wEmail = new JTextField();
		wEmail.setDocument(new TextFieldLimiter(Contact.EMAIL_MAXLENGTH));
        setWorkEmailTextField(wEmail);
        getFormBuilder().append("Work Email:",getWorkEmailTextField());
        getFormBuilder().nextLine();

		JTextField url = new JTextField();
		url.setDocument(new TextFieldLimiter(Contact.EMAIL_MAXLENGTH));
        setUrlTextField(url);
        getFormBuilder().append("Web Page:",getUrlTextField());
        getFormBuilder().nextLine();
        
        JTextField imgpath = new JTextField();
        imgpath.setEditable(false);
        setPhotoTextField(imgpath);
        getFormBuilder().append("Photo:",getPhotoTextField());
        
		JLabel image = new JLabel();
		setImageLabel(image);
		ImageIcon ii = new ImageIcon();
		setImage(ii);
        
		JButton btnBrowse = new JButton("Browse");
		setButton(btnBrowse);
		//btnBrowse.setVisible(false);
		getButton().addActionListener(this);
		getFormBuilder().append(btnBrowse);
		getFormBuilder().append(getImageLabel());
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == getButton())
		open();
		getPhotoTextField().setText(getRoute());
		}
	
	public void open(){
		if(getFileChooser().showOpenDialog(getFileChooser()) == JFileChooser.APPROVE_OPTION)
		open(getFileChooser().getSelectedFile());
		setRoute(getFileChooser().getSelectedFile().getPath());
		}
	
	public void open(File file){
		 try{
		  BufferedImage bi = ImageIO.read(file); 
		  ImageIcon icon = new ImageIcon(bi);
		  icon.setImage(icon.getImage().getScaledInstance(PIC_SIZE,PIC_SIZE,Image.SCALE_AREA_AVERAGING));
		  getImageLabel().setIcon(icon);   
		  }
		  catch(IOException ex){
		  JOptionPane.showMessageDialog(null,"This image is not supported!",
		  "Error",JOptionPane.INFORMATION_MESSAGE);
		  }
		}
	/** main panel**/
	private JPanel m_ContentPanel = new JPanel(); 
	/** First name text fields**/
	private JTextField m_FirstNameTextField;
	/** Last name text fields**/
	private JTextField m_LastNameTextField;
	/** Address text fields**/
	private JTextField m_AddressTextField;
	/** Home number text fields**/
	private JTextField m_HomeNumberTextField;
	/** Work number text fields**/
	private JTextField m_WorkNumberTextField;
	/** personal email text fields**/
	private JTextField m_PersonalEmailTextField;
	/** work email text fields**/
	private JTextField m_WorkEmailTextField;
	/** Other email text fields**/
	private JTextField m_OtherEmailTextField;
	/** Fax text fields**/
	private JTextField m_FaxTextField;
	/** Mobile number text fields**/
	private JTextField m_MobileNumberTextField;
	/** Post code text fields**/
	private JTextField m_PostCodeTextField;
	/** Nick name text fields**/
	private JTextField m_NickNameTextField;
	/** Url text fields**/
	private JTextField m_UrlTextField;
	/** Photo text fields**/
	private JTextField m_PhotoTextField;
	/** Image Label**/
	private JLabel m_ImageLabel;
    /** The form builder for the layout. */
    private DefaultFormBuilder m_FormBuilder;
    /** The validation result box. */
    private ValidationResultModel m_ValidationResultModel;
    private JFileChooser m_FileChooser = new JFileChooser();
    private ImageIcon m_Image;
    private String m_Route;
    private JButton m_Button;
    private int PIC_SIZE = 80;
    
    /**
     * Main displays the view in a window as a test.
     * @param args Arguments are ignored.
     */
 	public static void main (String[] args){
 		/** @test Displaying the built panel. */
 		  JFrame jf = new JFrame();
 		  jf.setTitle("ContactView Test");
 	      ContactView cv = new ContactView();
 	      jf.add(cv.getPanel());
 	      jf.pack();
 	      jf.setVisible(true);
 	      jf.setResizable(false);
 	      jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 	}
}
