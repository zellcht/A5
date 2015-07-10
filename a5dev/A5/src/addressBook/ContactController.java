/**
 * @file ContactController.java
 * @author SUN YAN
 * @date 18th March 2012
 * @brief Contains the ContactController, OkAction & CancelAction classes.
 * @see ContactView, Contact
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;


import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.validation.ValidationResult;

import digitalOrganiser.DigitalOrganiser;

/**
 * Extends the view, adding widget reactivity and linking with the model.
 * 
 * @author YAN SUN -A5 YAN SUN -A6
 * @brief The controller part of the Contact MVC architecture.
 * 
 * Takes the view, adds reactivity and links up with the model.
 * @see <a href="http://jgoodies.com/">JGoodies</a> the source for the validation
 */
public class ContactController extends ContactView{
////////////////////////////////////////////////////////////////////////////////	
	/**
	 * Sets the frame used for displaying the controller.
	 * @param f The new frame.
	 */
	public void setFrame(JFrame f){m_Frame = f;}
	/**
	 * Gets the frame used for displaying the controller.
	 * @return The frame.
	 */
	public JFrame getFrame(){return m_Frame;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the status of the switch tracking edit mode.
	 * @param b The switch status.
	 */
	public boolean setEditMode(boolean b){
		if (getContact() == null){
			System.err.println("Set edit mode after setting and event!");
			return false;
		}
		m_EditMode = b;
		setEditedContactID(getContact().getID());
		return true;		
	}
	/**
	 * Gets the status of the switch tracking the edit mode.
	 * @return The switch status.
	 */
	public boolean getEditMode(){return m_EditMode;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the variable holding the contact object constructed by the controller.
	 * @param c The new contact.	
	 */
	public void setConstructedContact(Contact c){m_ConstructedContact = c;}
	/**
	 * Gets the contact object constructed by the controller.
	 * @return The constructed contact.
	 */
	public Contact getConstuctedContact(){return m_ConstructedContact;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the contact being displayed.
	 * @param c The contact.
	 */
	public void setContact(Contact c){m_DisplayedContact = c;}
	/**
	 * Gets the contact being displayed.
	 * @return The displayed contact.
	 */
	public Contact getContact(){return m_DisplayedContact;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the ID for the contact being edited.
	 * @param id The new ID.
	 */
	private void setEditedContactID(String id){m_ID = id;}
	/**
	 * Gets the ID for the contact being edited.
	 * @return The ID of the event.
	 */
	private String getEditedContactID(){return m_ID;}
//////////////////////ACTION/////////LISTENERS//////////////////////////////////
    /**
     * @brief OK button abstract action listener.
     * 
     * Builds the OK button.<br/>
     * Creates an action listener that gets assigned to the OK button.
     * @author SUN YAN
     * @date 18th March 2012
     */
	@SuppressWarnings("serial")
	class OkAction extends AbstractAction {
		
		/** Builds the OK button. */
		private OkAction(){
			super("OK");
		}

		/** 
		 * Overrides the action performed method.<br/>
		 * Fires the validation routine.<br/>
		 * Fires the contact building routine.<br/>
		 * @param e The event.
		 */
		public void actionPerformed(ActionEvent e) {
            ValidationResult validationResult = validateFields();
            getValidationResultModel().setResult(validationResult);
            final Contact c = buildContact();
            if (getValidationResultModel().hasErrors())
            	return;
            if (getValidationResultModel().hasMessages()) 
		    	if (c.equals(getConstuctedContact())){
	    		   if(getEditMode()){
	    		      System.out.println("CC: Editing: ["+c.getFirstName()+"]");
	            		DigitalOrganiser
	            			.getAddressBook()
	            			.editContact(getEditedContactID(),c);
	            		
	    		   }
	    		   else {
	    			   System.out.println("CC: Adding invalidated Contact: ["+
	    					   c.getFirstName()+"]");
	    			   DigitalOrganiser.getAddressBook().addContact(c);
	    			   
	    		   }
	            	
	    		   getFrame().dispose();
		    	}
		    	else
		    		setConstructedContact(c);
            else{
            	if(getEditMode()){
            		System.out.println("CC: No validation messages. Editing: ["
            				+c.getFirstName()+"]");
            		EventQueue.invokeLater(new Runnable() {
    					@Override
    					public void run() {
		            		DigitalOrganiser.getAddressBook()
		        				.editContact(getEditedContactID(),c);
    					}});
            	}
            	else{
            		System.out.println("CC:  No validation messages. Adding: ["
            				+c.getFirstName()+"]");
            		DigitalOrganiser.getAddressBook().addContact(c);
            	}
    		getFrame().dispose();
            }
		}
	}
	
	
	/**
	 * @brief Cancel button abstract action listener.
	 * 
	 * Builds the cancel button.<br/>
	 * Creates an action listener that gets assigned to the cancel button.
	 * @author SUN YAN
	 * @date 18th March 2012
	 */
	@SuppressWarnings("serial")
	class CancelAction extends AbstractAction {
		
	    /** Builds the cancel button. */
		private CancelAction() {
			super("Cancel");
	    }
		
		/** 
		 * Overrides the action performed method.<br/>
		 * Set to dispose the contact window.
		 * @param e The action event.
		 */
		public void actionPerformed(ActionEvent e) {
			getFrame().dispose();
		}
	}
 
//////////////////////////////////////////////////////////////////////////////// 
    
///////////////////////DEFAULT/////CONSTRUCTOR//////////////////////////////////
    /**
     * Default constructor.<br/>
     * Loads the view.<br/>
     * Adds the text fields.<br/>
     * Adds the buttons.<br/>
     * Makes the buttons reactive.
     */
	public ContactController(){
		super();
		
		addTextFieldsListeners();
		
        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(
                new JButton(new OkAction()),
                new JButton(new CancelAction()));
        getFormBuilder().append(buttonBar, getFormBuilder().getColumnCount());
        getFormBuilder().appendSeparator();
        getFormBuilder().appendSeparator();
	}
////////////////////////////////////////////////////////////////////////////////
    
////////////////////////OTHER//////////METHODS//////////////////////////////////	
    /**
     * Tries to build up a contact object out of the data taken from the view.
     * @return True on success, false on failure.
     */
    private Contact buildContact(){
        String firstName = getFirstNameTextField().getText();
        String lastName = getLastNameTextField().getText();
        String nickName = getNickNameTextField().getText();       
        String address = getAddressTextField().getText();
        String postCode = getPostCodeTextField().getText();
        String homeNumber = getHomeNumberTextField().getText();
        String workNumber = getWorkNumberTextField().getText();
        String mobileNumber = getMobileNumberTextField().getText();
        String faxNumber = getFaxTextField().getText();
        String pEmail = getPersonalEmailTextField().getText();
        String oEmail = getOtherEmailTextField().getText();
        String wEmail = getWorkEmailTextField().getText();
        String url = getUrlTextField().getText();
        String path = getPhotoTextField().getText();
        
        Contact c = new Contact();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setNickName(nickName);
        c.setAddress(address);
        c.setPostCode(postCode);
        c.setHomeNumber(homeNumber);
        c.setWorkNumber(workNumber);
        c.setMobileNumber(mobileNumber);
        c.setFaxNumber(faxNumber);
        c.setPersonalEmail(pEmail);
        c.setOtherEmail(oEmail);
        c.setWorkEmail(wEmail);
        c.setUrl(url);
        c.setPath(path);
        
        return c;
    }

    /**
     * Displays the contact window.
     */
    public void showContactWindow(){
    	if (getEditMode())
    		getFrame().setTitle("Edit contact");
    	else
    		getFrame().setTitle("Add new contact");
    	
    	if (getContact() == null)
    		System.err.println("No contact set.");
    	else 
    		populateView(getContact());
    	getFrame().add(getPanel());
    	
    	getFrame().pack();
    	getFrame().setLocationRelativeTo(null);
    	getFrame().setResizable(false);
    	getFrame().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	getFrame().setVisible(true);
    	
    }
    /**
     * Populates the view with date from a contact.
     * @param c contact to get data from.
     * @return True on success, false on failure.
     */
    private boolean populateView(Contact c){
    	getFirstNameTextField().setText(c.getFirstName());
        getLastNameTextField().setText(c.getLastName());
        getNickNameTextField().setText(c.getNickName());       
        getAddressTextField().setText(c.getAddress());
        getPostCodeTextField().setText(c.getPostCode());
        getHomeNumberTextField().setText(c.getHomeNumber());
        getWorkNumberTextField().setText(c.getWorkNumber());
        getMobileNumberTextField().setText(c.getMobileNumber());
        getFaxTextField().setText(c.getFaxNumber());
        getPersonalEmailTextField().setText(c.getPersonalEmail());
        getOtherEmailTextField().setText(c.getOtherEmail());
        getWorkEmailTextField().setText(c.getWorkEmail());
        getUrlTextField().setText(c.getUrl());
        getPhotoTextField().setText(c.getPath());
        ImageIcon icon = new ImageIcon(c.getPath());
        icon.setImage(icon.getImage().getScaledInstance(PIC_SIZE,PIC_SIZE,Image.SCALE_AREA_AVERAGING));
        getImageLabel().setIcon(icon);
    	return true;
    }
    
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Performs the field validation.
     * @return The validation result.
     */
    private  ValidationResult validateFields() {
        ValidationResult validationResult = new ValidationResult();
        Contact contactChecker = new Contact();
        //validate the contact field
        if (getFirstNameTextField().getText().isEmpty()) {
            validationResult.addWarning(
            		"First name is blank.");
        }
        if (!(contactChecker.setFirstName(getFirstNameTextField().getText()))) {
            validationResult.addError(
            		"First name is invalid.");
        }
        if (getLastNameTextField().getText().isEmpty()) {
            validationResult.addWarning(
            		"Last name is blank.");
        }
        if (!(contactChecker.setLastName(getLastNameTextField().getText()))) {
            validationResult.addError(
            		"Last name is invalid.");
        }
        if (!(contactChecker.setNickName(getNickNameTextField().getText()))) {
            validationResult.addError(
            		"Nick name is invalid.");
        }
        if (!(contactChecker.setAddress(getAddressTextField().getText()))) {
            validationResult.addWarning(
            		"Address is invalid.");
        }
        if (!(contactChecker.setPostCode(getPostCodeTextField().getText()))) {
            validationResult.addError(
            		"Post code is invalid.");
        }
        if (!(contactChecker.setHomeNumber(getHomeNumberTextField().getText()))) {
            validationResult.addWarning(
            		"Home Number is invalid.");
        }
        if (!(contactChecker.setWorkNumber(getWorkNumberTextField().getText()))) {
            validationResult.addError(
            		"Work Number is invalid.");
        }
        if (!(contactChecker.setMobileNumber(getMobileNumberTextField().getText()))) {
            validationResult.addWarning(
            		"Mobile Number is invalid.");
        }
        if (!(contactChecker.setFaxNumber(getFaxTextField().getText()))) {
            validationResult.addError(
            		"Fax Number is invalid.");
        }
        if (!(contactChecker.setPersonalEmail(getPersonalEmailTextField().getText()))) {
            validationResult.addError(
            		"Personal Email is invalid.");
        }
        if (!(contactChecker.setOtherEmail(getOtherEmailTextField().getText()))) {
            validationResult.addError(
            		"Other Email is invalid.");
        }
        if (!(contactChecker.setWorkEmail(getWorkEmailTextField().getText()))) {
            validationResult.addError(
            		"Work Email is invalid.");
        }
        if (!(contactChecker.setUrl(getUrlTextField().getText()))) {
            validationResult.addError(
            		"Web page is invalid.");
        }
        if (validationResult.hasMessages() && !validationResult.hasErrors())
        	validationResult.addInfo("Press OK again to confirm.");
        
        return validationResult;
    }    
    
    /**
     * Defines the listeners for the text fields, including the date chooser ones.
     */
    private void addTextFieldsListeners(){
		//TextFields seem fine using the OKAction class directly 
		getFirstNameTextField().addActionListener(new OkAction());
		getLastNameTextField().addActionListener(new OkAction());
		getNickNameTextField().addActionListener(new OkAction());
		
		getAddressTextField().addActionListener(new OkAction());
		getPostCodeTextField().addActionListener(new OkAction());
		
		getHomeNumberTextField().addActionListener(new OkAction());
		getMobileNumberTextField().addActionListener(new OkAction());
		getWorkNumberTextField().addActionListener(new OkAction());
		getFaxTextField().addActionListener(new OkAction());
		
		getPersonalEmailTextField().addActionListener(new OkAction());
		getWorkEmailTextField().addActionListener(new OkAction());
		getUrlTextField().addActionListener(new OkAction());
		getOtherEmailTextField().addActionListener(new OkAction());
		getPhotoTextField().addActionListener(new OkAction());
    }
	
    /** The contact that got constructed. */
    private Contact m_ConstructedContact;
    /** Tracks if it's the add new contact or the edit contact window. */
    private boolean m_EditMode = false;
    /** The JFrame in which the contact panel can be displayed if needed. */
    private JFrame m_Frame = new JFrame();
    /** The contact being displayed. */
    private Contact m_DisplayedContact;
    /** Tracks the ID of the event being edited. */
    private String m_ID;
    private int PIC_SIZE = 80;
    /**
     * Tests the class.
     * @param args Arguments will be ignored.
     */
	public static void main (String[] args){
		DigitalOrganiser.setAddressBook(new AddressBook());
		//DigitalOrganiser.initialiseViews();
		ContactController cc = new ContactController();

		/** @test displaying an existing empty contact. */
		cc.getFrame().setTitle("ContactController Test#2");
		cc.setContact(new Contact());
		cc.showContactWindow(); 
	}
}

