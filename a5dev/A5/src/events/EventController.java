/**
 * @file EventController.java
 * @author Codrin Morhan
 * @date 18th March 2012
 * @brief Contains the EventController, OkAction & CancelAction classes.
 * @see EventView, Event
 * 
 * <b>Justification for not using G5 code:</b><br/>
 * This is just a controller tailored for the EventView.<br/>
 * See the EventView file for the justification.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import hirondelle.date4j.DateTime;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.validation.ValidationResult;

import dateAndTime.SystemDate;
import digitalOrganiser.DigitalOrganiser;

/**
 * @brief The controller part of the Event MVC architecture.
 * 
 * Takes the view, adds reactivity and links up with the model.
 * @see <a href="http://jgoodies.com/">JGoodies</a> the source for the validation
 * @author Codrin Morhan
 */
public class EventController extends EventView{
	
	/**
	 * Get the debug switch.
	 * @return The switch status.
	 */
	public boolean getDebugStatus(){return m_debug;}	
	/**
	 * Sets the debug switch status.
	 * @param f The new switch status.
	 */
	public void setDebugStatus(boolean f){m_debug = f;}
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
	 * Sets the status of the switch tracking edit mode.<br/>
	 * If it is switched ON, the ID gets read as well.
	 * @param b The switch status.
	 */
	public boolean setEditMode(boolean b){
		if (getEvent() == null){
			if(getDebugStatus())
				System.err.println("EC: setEditMode(): "+
					"Set edit mode after setting and event!");
			return false;
		}
		m_EditMode = b;
		setEditedEventID(getEvent().getID());
		return true;			
	}
	/**
	 * Gets the status of the switch tracking the edit mode.
	 * @return The switch status.
	 */
	public boolean getEditMode(){return m_EditMode;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the variable holding the event object constructed by the controller.
	 * @param e The new event.	
	 */
	public void setConstructedEvent(Event e){m_ConstructedEvent = e;}
	/**
	 * Gets the event object constructed by the controller.
	 * @return The constructed event.
	 */
	public Event getConstuctedEvent(){return m_ConstructedEvent;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the event being displayed.
	 * @param e The event.
	 */
	public void setEvent(Event e){m_DisplayedEvent = e;}
	/**
	 * Gets the event being displayed.
	 * @return The displayed event.
	 */
	public Event getEvent(){return m_DisplayedEvent;}
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the ID for the event being edited.
	 * @param id The new ID.
	 */
	private void setEditedEventID(String id){m_ID = id;}
	/**
	 * Gets the ID for the event being edited.
	 * @return The ID of the event.
	 */
	private String getEditedEventID(){return m_ID;}
//////////////////////////////////////////////////////////////////////////////// 
    
///////////////////////DEFAULT/////CONSTRUCTOR//////////////////////////////////
    /**
     * Default constructor.<br/>
     * Loads the view.<br/>
     * Sets up the combo boxes.<br/>
     * Sets up limits for the date choosers.<br/>
     * Adds the buttons.<br/>
     * Makes the buttons reactive.<br/>
     */
	public EventController(){
		
		setAvailableRepeats(Event.REPEATS);
		
		setAvailableCategories(Event.CATEGORIES);
		
		addComboBoxesListeners();
		addTextFieldsListeners();
		
		setDateFormat(SystemDate.DATE_FORMAT_JAVA);
		
		int MILLISECONDS_IN_SECOND = 1000;
		getStartDateChooser().getDateEditor().setMinSelectableDate(new Date());
		getStartDateChooser().getDateEditor().setMaxSelectableDate(
				new Date(SystemDate.MaxDuration()*MILLISECONDS_IN_SECOND));
		
		getEndDateChooser().getDateEditor().setMinSelectableDate(new Date());
		getEndDateChooser().getDateEditor().setMaxSelectableDate(
				new Date(SystemDate.MaxDuration()*MILLISECONDS_IN_SECOND));
		
        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(
                new JButton(new OkAction()), new JButton(new CancelAction()));
        getFormBuilder().append(buttonBar, getFormBuilder().getColumnCount());
	}
////////////////////////////////////////////////////////////////////////////////
    
////////////////////////OTHER//////////METHODS//////////////////////////////////	
    /**
     * Defines the listeners for the widgets.
     * @return True on success, false on failure.
     */
    private boolean addComboBoxesListeners() {
        getCategoryComboBox()
        			.addActionListener(new CategoryComboBoxActionPerformed());
        getCategoryComboBox().addKeyListener(new KeysListener());
        getRepeatsComboBox()
        			.addActionListener(new RepeatsComboBoxActionPerformed());
        getRepeatsComboBox().addKeyListener(new KeysListener());
        return true;
    }
    
    /**
     * Defines the listeners for the text fields, including the date chooser ones.
     */
    private void addTextFieldsListeners(){
		//TextFields seem fine using the OKAction class directly 
		getTitleTextField().addActionListener(new OkAction());
		getLocationTextField().addActionListener(new OkAction());
		getPersonsTextField().addActionListener(new OkAction());
		final int TEXT_FIELD = 1;
		getStartDateChooser().getComponent(TEXT_FIELD)
			.addKeyListener(new KeysListener());
		getEndDateChooser().getComponent(TEXT_FIELD)
			.addKeyListener(new KeysListener());
    }
    
    /**
     * Tries to build up an event object out of the data taken from the view.
     * @return True on success, false on failure.
     */
    private Event buildEvent(){
        String title = getTitle();
        String location = getEventLocation();
        String persons = getPersons();
        String startTime = getStart();
        String endTime = getEnd();
        String description = getDescription();
        String category = getSelectedCategory();
        String repeats = getSelectedRepeat();
        
        Event e = new Event();
        e.setTitle(title);
        e.setCategory(category);
        e.setRepetition(repeats);
        e.setLocation(location);
        e.setPersonsInvolved(persons);
        e.setStartingDateTime(startTime);
        e.setEndingDateTime(endTime);
        e.setDescription(description);
        
        return e;
    }

    /**
     * Displays the event window.
     */
    public void showEventWindow(){
    	if (getEvent() == null) //defence against NPE crashing
    		return;
    	if (getEditMode())
    		getFrame().setTitle("Edit event");
    	else
    		getFrame().setTitle("Add new event");
    	
    	if (getEvent() == null){
    		if(getDebugStatus())
    			System.err.println("No event set.");
    	}
    	else 
    		populateView(getEvent());
    	getFrame().add(getPanel());
    	
    	ImageIcon image;
		image = new EventCategoryIcons()
				.getIconForCategory(getEvent().getCategory());			
		getFrame().setIconImage(image.getImage());
		if (getDebugStatus()){
			System.out.println("Icon file: "+image.toString());
			System.out.println("Event category: "+getEvent().getCategory());
		}					

    	getFrame().pack();
    	getFrame().setLocationRelativeTo(null);
    	getFrame().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	getFrame().setVisible(true);
    }
    /**
     * Populates the view with date from an event.
     * @param e Event to get data from.
     * @return True on success, false on failure.
     */
    private boolean populateView(Event e){
    	setTitle(e.getTitle());
    	setSelectedCategory(e.getCategory());
    	setSelectedRepeat(e.getRepetition());
    	setEventLocation(e.getLocation());
    	setPersons(e.getPersonsInvolved());
    	try{
    		setStart(e.getStartingDateTime().toString());
    	} catch (Exception e1){
    		setStart("");
    	}
    	try{
    		setEnd(e.getEndingDateTime().toString());
    	} catch (Exception e2){
    		setEnd("");
    	}
    	//System.out.println(e.getCategory());

    	setDescription(e.getDescription());
    	return true;
    }
    
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Performs the field validation.
     * @return The validation result.
     */
    private  ValidationResult validateFields() {
        ValidationResult validationResult = new ValidationResult();
        //validate the event title field
        String value = getTitleTextField().getText(); 
        if (value.isEmpty())
            validationResult.addWarning("Event title is blank.");
        	
        boolean startValid = true;
        //validate the starting date field
        try{
        	getStartDateChooser().getDate().toString();
        } catch (Exception e){
        	validationResult.addWarning("Invalid starting date and time.");
        	startValid = false;
        }
        
        boolean endValid = true;
        //validate the ending date field
        try{
        	getEndDateChooser().getDate().toString();
        	

        } catch (Exception e){
        	validationResult.addWarning("Invalid ending date and time.");
        	endValid = false;
        }
        
        
        if (startValid && endValid){
        	DateTime startDate = SystemDate.convertDateToDateTime
            		(getStartDateChooser().getDate());
            DateTime endDate = SystemDate.convertDateToDateTime
            		(getEndDateChooser().getDate());
        	if (startDate.gt(endDate))
        		validationResult.addError("Start date greater than end date");
        	if (!buildEvent().validateDuration())
        		validationResult.addWarning("Double check the duration.");
        }
        
        if (validationResult.hasMessages() && !validationResult.hasErrors())
        	validationResult.addInfo("Press OK again to confirm.");
        
        return validationResult;
    }
    
//////////////////////ACTION/////////LISTENERS//////////////////////////////////
    /**
     * @brief Category combo box action listener.
     * 
     * Creates an action listener that will be assigned to the category combo box.
     * @author Codrin Morhan
     * @date 18th March 2012
     */
     class CategoryComboBoxActionPerformed implements ActionListener {
         /**
         * Overrides the ActionListener's method.
         * @param e The action event.
         */
         public void actionPerformed(ActionEvent e) {
         	String selected =getCategoryComboBox().getSelectedItem().toString();
         	if(getDebugStatus())
         		System.out.println("Category combo box seleted item: "+
         				selected);
         	setSelectedCategory(selected);
         }
     }
     
     /**
      * @brief Repeats combo box action listener.
      * 
      * Creates an action listener that will be assigned to the repeats combo box.
      * @author Codrin Morhan
      * @date 18th March 2012
      */
      class RepeatsComboBoxActionPerformed implements ActionListener {
          /**
          * Overrides the ActionListener's method.
          * @param e The action event.
          */
          public void actionPerformed(ActionEvent e) {
          	String selected = getRepeatsComboBox().getSelectedItem().toString();
          	if(getDebugStatus())
          		System.out.println("Repeats combo box seleted item: "+
          				selected );
          	setSelectedRepeat(selected);
          }
      }

    
    /**
     * @brief OK button abstract action listener.
     * 
     * Builds the OK button.<br/>
     * Creates an action listener that gets assigned to the OK button.
     * @author Codrin Morhan
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
		 * Fires the event building routine.<br/>
		 * @param e The action event.
		 */
		public void actionPerformed(ActionEvent e) {
            ValidationResult validationResult = validateFields();
            getValidationResultModel().setResult(validationResult);
            final Event ev = buildEvent();
            if (getValidationResultModel().hasErrors())
            	return;
            if (getValidationResultModel().hasMessages()) 
		    	if (ev.equals(getConstuctedEvent())){
	    		   if(getEditMode()){
	    			   if(getDebugStatus())
	    				 System.out.println("EC: Editing: ["+ev.getTitle()+"]");
	            		DigitalOrganiser
	            			.getEventList()
	            			.editEvent(getEditedEventID(),ev);
	    		   }
	    		   else {
	    			   if(getDebugStatus())
	    				   System.out.println("EC: Adding invalidated event: ["+
	    					   ev.getTitle()+"]");
	    			   DigitalOrganiser.getEventList().addEvent(ev);
	    		   }
	            	
	    		   DigitalOrganiser.updateViews();
	    		   getFrame().dispose();
		    	}
		    	else
		    		setConstructedEvent(ev);
            else{
            	if(getEditMode()){
            		if(getDebugStatus())
            		 System.out.println("EC: No validation messages. Editing: ["
            				+ev.getTitle()+"]");
            		EventQueue.invokeLater(new Runnable() {
    					@Override
    					public void run() {
		            		DigitalOrganiser.getEventList()
		        				.editEvent(getEditedEventID(),ev);
    					}});
            	}
            	else{
            		if(getDebugStatus())
            		 System.out.println("EC:  No validation messages. Adding: ["
            				+ev.getTitle()+"]");
            		DigitalOrganiser.getEventList().addEvent(ev);
            	}
            DigitalOrganiser.updateViews();
    		getFrame().dispose();
            }
		}
	}
	
	/**
	 * @brief Cancel button abstract action listener.
	 * 
	 * Builds the cancel button.<br/>
	 * Creates an action listener that gets assigned to the cancel button.
	 * @author Codrin Morhan
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
		 * Set to dispose the event window.
		 * @param e The action event.
		 */
		public void actionPerformed(ActionEvent e) {
			getFrame().dispose();
		}
	}
	
	/**
	 * @brief Key listener for the text fields.
	 * 
	 * If <tt>ENTER</tt> is pressed in the text field it assumes the user  
	 * wants to press OK, so it calls up the method for pressing OK from the OKAction class,.
	 * @see OKAction()
	 * @author Codrin Morhan
	 * @date 31st March 2012
	 */
	class KeysListener implements KeyListener{

		/**
		 * Implementation of the listeners interface method.<br/>
		 * If the ENTER was pressed, call up OKAction method for OK button1.
		 * @param e The event.
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == KeyEvent.VK_ENTER){
				//dummy ID; ActionEvent constructor needs one
				final int ID = 0;
				//dummy event just to trigger the OKAction
				ActionEvent ev = new ActionEvent(this,ID,"");
				new OkAction().actionPerformed(ev);
			}
			
		}

		/** 
		 * Interface method; not overridden. 
		 * @param e The event.
		 */
		@Override
		public void keyReleased(KeyEvent e) {/*not used*/}

		/** 
		 * Interface method; not overridden. 
		 * @param e The event.
		 */
		@Override
		public void keyTyped(KeyEvent e) {/*not used*/}
	}
    
	
	
    /** The event that got constructed. */
    private Event m_ConstructedEvent;
    /** Tracks if it's the add new event or the edit event window. */
    private boolean m_EditMode = false;
    /** The JFrame in which the event panel can be displayed if needed. */
    private JFrame m_Frame = new JFrame();
    /** The event being displayed. */
    private Event m_DisplayedEvent;
    /** Tracks the ID of the event being edited. */
    private String m_ID;
    /** The debug switch. */
    private boolean m_debug = false;

    /**
     * Tests the class. Some functionality will fail in absence of DigitalOrganiser.
     * @param args Arguments will be ignored.
     */
	public static void main (String[] args){
		//DigitalOrganiser.initialise();
		DigitalOrganiser.setEventList(new EventList());
		DigitalOrganiser.initialiseViews();
		
		final String FILLER = "-----------------------------------------";
		EventController ec = new EventController();
		ec.setDebugStatus(true);

		/** @test Displaying an existing event. */
		System.out.println("-[TEST1]"+FILLER);
		ec.getFrame().setTitle("EventController Test#1");
		Event e = new Event();
		e.setTitle("My Event");
		e.setCategory("Happy Hour");
		e.setLocation("Nowhere");
		e.setRepetition("Daily");
		e.setDescription("A test event");
		e.setEndingDateTime("2014-01-01 01:01:01");
		ec.setEvent(e);
		ec.showEventWindow();
		System.out.println("--------"+FILLER);
		/** @test Displaying a default new event. */
		System.out.println("-[TEST2]"+FILLER);
		ec.getFrame().setTitle("EventController Test#2");
		ec.setEvent(new Event());
		ec.showEventWindow(); 
		System.out.println("--------"+FILLER);
		/** @test Displaying an event with important fields missing; press OK. */
		System.out.println("-[TEST2]"+FILLER);
		ec.getFrame().setTitle("EventController Test#2");
		ec.showEventWindow();
		ec.getTitleTextField().setText("");
		ec.getStartDateChooser().setDate(null);
		ec.getEndDateChooser().setDate(null);
		System.out.println("--------"+FILLER);
	}
}
