/**
 * @file EventView.java
 * @author Codrin Morhan
 * @date 17th March 2012 
 * @brief Contains the EventView class.
 * 
 * <b>Justification against using G5 code:</b><br/>
 * Actually used one of their widgets: the repeats.
 * Reasons:
 * <ul>
 * 	<li> View & Controller functionality was merged. </li>
 *  <li> Was interacting with the disk. </li>
 *  <li> The constructor was over 80 lines long (~223). </li>
 *  <li> Confusing date setting UI (see <a href="http://cs-sol.swan.ac.uk/~cs649909/a5/events/EventViewBefore.png">link</a>).</li>
 *  <li> No validation on the set dates. </li>
 *  <li> Date picking was nothing more than selecting dozens of combo boxes, with no link between them (e.g. could select 31th of any month). </li>
 *  <li> The allowed date interval was only 8 years, starting from 2012. </li>
 *  <li> Lack of documentation. </li>
 *  <li> High usage of magic numbers (~214). </li>
 * </ul>
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import com.toedter.calendar.JDateChooser;
import common.TextFieldLimiter;

/**
 * @brief The View part of the Event MVC architecture.
 * 
 * Consists of a panel upon which all widgets needed for event interaction are added.<br/>
 * The used widgets are:
 * <ul> 
 *  <li>7xJLabels</li>
 *  <li>2xJTextFields</li>
 *  <li>2xJDateChoosers</li>
 *  <li>1xJscrollPane</li>
 *  <li>1xJtextArea</li>
 *  <li>2xJButtons</li>
 * </ul>
 * 
 * @see <a href="http://www.toedter.com/en/jcalendar/index.html">JDateChooser weblink</a>
 * @see <a href="http://jgoodies.com/">JGoodies weblink</a> the source for the FormLayout and validation.
 * @author Codrin Morhan
 */
public class EventView{
	
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
	 * Sets the label used for title.
	 * @param l The new label.
	 */
	protected void setTitleLabel(JLabel l){m_TitleLabel = l;}
	/**
	 * Gets the label used for title. 
	 * @return The label widget.
	 */
	protected JLabel getTitleLabel(){return m_TitleLabel;}
////////////////////////////////////////////////////////////////////////////////	
	/**
     * Sets the text field used for title.
     * @param tf The new text field.
     */
	protected void setTitleTextField(JTextField tf){m_TitleTextField = tf;}
    /**
     * Gets the text field used for title.
     * @return The text field widget.
     */
	protected JTextField getTitleTextField(){return m_TitleTextField;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the label used for categories.
     * @param l The new label.
     */
	protected void setCategoryLabel(JLabel l){m_CategoryLabel = l;}
    /**
     * Gets the label used for categories.
     * @return The label. 
     */
	protected JLabel getCategoryLabel(){return m_CategoryLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the event categories combo box.
     * @param cb The new combo box.
     */
	protected void setCategoryComboBox(JComboBox cb){m_CategoryComboBox = cb;}
    /**
     * Gets the event categories combo box.
     * @return The combo box holding the event categories.
     */
	protected JComboBox getCategoryComboBox(){return m_CategoryComboBox;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the label used for repeats.
     * @param l The new label.  
     */
	protected void setRepeatsLabel(JLabel l){m_RepeatsLabel = l;}
    /**
     * Gets the label used for repeats.
     * @return The label.
     */
	protected JLabel getRepeatsLabel(){return m_RepeatsLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the event categories combo box.
     * @param cb The new combo box.
     */
	protected void setRepeatsComboBox(JComboBox cb){m_RepeatsComboBox = cb;}
    /**
     * Gets the event categories combo box.
     * @return The combo box holding the event categories.
     */
	protected JComboBox getRepeatsComboBox(){return m_RepeatsComboBox;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the label used for location.
     * @param l The new JLabel.
     */
	protected void setLocationLabel(JLabel l){m_LocationLabel = l;}
    /**
     * Gets the label used for location.
     * @return The label.
     */
	protected JLabel getLocationLabel(){return m_LocationLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the text field used for location.
     * @param tf The new text field.
     */
	protected void setLocationTextField(JTextField tf){m_LocationTextField = tf;}
    /**
     * Gets the text field used for location.
     * @return The text field.
     */
	protected JTextField getLocationTextField(){return m_LocationTextField;}
////////////////////////////////////////////////////////////////////////////////    
	/**
	* Sets the text field used for persons involved.
	* @param tf The new text field.
	*/
	protected void setPersonsTextField(JTextField tf){m_PersonsTextField = tf;}
	/**
	* Gets the text field used for location.
	* @return The text field.
	*/
	protected JTextField getPersonsTextField(){return m_PersonsTextField;}
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the label used for starting date & time.
     * @param l The new label.
     */
	protected void setStartLabel(JLabel l){m_StartLabel = l;}
    /**
     * Gets the label used for starting date & time.
     * @return The label.
     */
	protected JLabel getStartLabel(){return m_StartLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the date chooser designated for the starting date & time.
     * @param dc The new date chooser.
     */
	protected void setStartDateChooser(JDateChooser dc){m_StartDateChooser=dc;}
    /**
     * Gets the date chooser designated for the starting date & time.
     * @return The date chooser.
     */
	protected JDateChooser getStartDateChooser(){return m_StartDateChooser;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the label used for ending date & time.
     * @param l The new label.
     */
	protected void setEndLabel(JLabel l){m_EndLabel = l;}
    /**
     * Gets the label used for ending date & time.
     * @return The label.
     */
	protected JLabel getEndLabel(){return m_EndLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the date chooser designated for the starting date & time.
     * @param dc The new date chooser.
     */
	protected void setEndDateChooser(JDateChooser dc){m_EndDateChooser=dc;}
    /**
     * Gets the date chooser designated for the starting date & time.
     * @return The date chooser.
     */
	protected JDateChooser getEndDateChooser(){return m_EndDateChooser;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the label used for description.
     * @param l The new label.
     */
	protected void setDescriptionLabel(JLabel l){m_DescriptionLabel = l;}
    /**
     * Gets the label used for description.
     * @return The label.
     */
	protected JLabel getDescriptionLabel(){return m_DescriptionLabel;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the scroll pane used for the description text area.
     * @param sp The new scroll pane.
     */
	protected void setDescriptionScrollPane(JScrollPane sp){
    	m_DescriptionScrollPane = sp;
    }
    /**
     * Gets the scroll pane used for the description text area.
     * @return The scroll pane.
     */
	protected JScrollPane getDescriptionScrollPane(){
    	return m_DescriptionScrollPane;
    }
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the description text area.
     * @param ta The new text area.
     */
	protected void setDescriptionTextArea(JTextArea ta){
		m_DescriptionTextArea = ta;
	}
    /**
     * Gets the description text area.
     * @return The text area.
     */
	protected JTextArea getDescriptionTextArea(){return m_DescriptionTextArea;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the button used for confirming.
     * @param b The new button.
     */
	protected void setOKButton(JButton b){m_OKButton = b;}
    /**
     * Gets the button used for confirming.
     * @return The button.
     */
	protected JButton getOKButton(){return m_OKButton;}
////////////////////////////////////////////////////////////////////////////////    
    /**
     * Sets the button used for cancelling.
     * @param b The new button.
     */
	protected void setCancelButton(JButton b){m_CancelButton = b;}
    /**
     * Gets the button used for cancelling.
     * @return The button.
     */
	protected JButton getCancelButton(){return m_CancelButton;}    
////////////////////////////////////////////////////////////////////////////////    
	/**
	 * Sets the date format that used by the date choosers.
	 * @param df The new date format.
	 * @return True on success, false otherwise.
	 */
	public boolean setDateFormat(String df){
		try{
			new SimpleDateFormat(df);
		} catch (Exception e){
			if(getDebugStatus())
				System.err.println("Bad date format: "+df);
			return false;
		}
		m_JavaDateFormat = df;
		setDateChoosersDateFormat(df);
		return true;
	}
	/**
	 * Gets the date format used by the date choosers.
	 * @return The date format.
	 */
	public String getDateFormat(){return m_JavaDateFormat;}
////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the icon used by the date choosers.
	 * @param i The new icon.
	 */
	private void setDCIcon(ImageIcon i){m_DCIcon = i;}
	/**
	 * Gets the icon used by the date choosers.
	 * @return The icon.
	 */
	private ImageIcon getDCIcon(){return m_DCIcon;}
////////////////////////////////////////////////////////////////////////////////
	
///////////////INSIDE///////VALUES////////ACCESSORS/////////////////////////////    
    /**
     * Sets the text inside the title text field.
     * @param t Text to be put inside the field.
     */
    public void setTitle(String t) {getTitleTextField().setText(t);}
    /**
     * Gets the text that is inside the title text field.
     * @return The content of the field.
     */
    public String getTitle() {return getTitleTextField().getText();}
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets up the categories in the event categories combo box.
     * @param items The categories.
     * @return True on success, false on failure.
     */
    public boolean setAvailableCategories(String[] items){
        DefaultComboBoxModel m;
        m = new DefaultComboBoxModel(items);
        getCategoryComboBox().setModel(m);
        return true;
    }
    /**
     * Sets the category at the specified index in the category combo box to be selected. 
     * @param i The index of the category.
     * @return True on success, false on failure.
     */
    public boolean setSelectedCategory(int i){
    	try{
    		getCategoryComboBox().setSelectedIndex(i);
    		if(getDebugStatus())
    			System.out.println("Setting selection to index: "+i);
    	} catch (Exception e){
    		if(getDebugStatus())
    			System.err.println("Couldn't set category at index "+i);
    		return false;
    	}
    	return true;
    }
    /**
     * Sets the currently selected item in the category combo box to the specified category, if such category exists. 
     * @param c The category name.
     * @return True on success, false on failure.
     */
    public boolean setSelectedCategory(String c){
    	for (int i=0; i<getCategoryComboBox().getModel().getSize();i++){
   			if (getCategoryComboBox().getItemAt(i).toString()
   					.equalsIgnoreCase(c))
   			{
    			setSelectedCategory(i);
    			if(getDebugStatus())
    				System.out.println(c +" found at index "+ i);
    			return true;
    			
    		}
    	}
    	if(getDebugStatus())
    		System.err.println("No such category: "+c);
    	return false;
    }
    /**
     * Gets the selected event category.
     * @return Name of the category.
     */
    public String getSelectedCategory(){
        return getCategoryComboBox().getSelectedItem().toString();
    }
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets up the periods in the event repeats combo box.
     * @param repeats The names of the periods.
     * @return True on success, false on failure.
     */
    public boolean setAvailableRepeats(String[] repeats){
        DefaultComboBoxModel m;
        m = new DefaultComboBoxModel(repeats);
        getRepeatsComboBox().setModel(m);
        return true;
    }
    /**
     * Gets the available repeats.
     * @return The period names.
     */
    public String[] getAvailableRepeats(){
    	int periodsNum = getRepeatsComboBox().getModel().getSize();
    	String[] periods = new String[periodsNum];
    	for (int i=0; i<periodsNum;i++)
    		periods[i] = 	getRepeatsComboBox()
    						.getModel()
    						.getElementAt(i)
    						.toString();
    	return periods;    		
    }
    /**
     * Sets the category at the specified index in the category combo box to be selected. 
     * @param i The index of the category.
     * @return True on success, false on failure.
     */
    public boolean setSelectedRepeat(int i){
    	try{
    		getRepeatsComboBox().setSelectedIndex(i);
    	} catch (Exception e){
    		if(getDebugStatus())
    			System.err.println("Couldn't set repeat period at index "+i);
    		return false;
    	}
    	return true;
    }
    /**
     * Sets the currently selected item in the category combo box to the specified category, if such category exists. 
     * @param r The period name.
     * @return True on success, false on failure.
     */
    public boolean setSelectedRepeat(String r){
    	int periodsNum = getRepeatsComboBox().getModel().getSize();
    	if(getDebugStatus())
    		System.out.println("number of repeat periods: "+periodsNum);
   		for(int i=0; i<periodsNum;i++){
   			if(getDebugStatus())
   				System.out.println("Comparing with: "+
   							getRepeatsComboBox().getItemAt(i).toString());
			if (getRepeatsComboBox().getItemAt(i).toString().equals(r)){
				setSelectedRepeat(i);
				return true;
			}
		}
   		if(getDebugStatus())
   			System.err.println("No such repeat period: "+r);
    	return false;
    }
    /**
     * Gets the selected repeat.
     * @return Name of the repeat.
     */
    public String getSelectedRepeat(){
        return getRepeatsComboBox().getSelectedItem().toString();
    }
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the location for the event.
     * @param l Location name.
     * @return True on success, false on failure.
     */
    public boolean setEventLocation(String l){
        getLocationTextField().setText(l);
        return true;
    }
    /**
     * Gets the location for the event.
     * @return Location name.
     */
    public String getEventLocation(){return getLocationTextField().getText();}
////////////////////////////////////////////////////////////////////////////////
	/**
	* Sets the text inside the text field for persons involved.
	* @param l Persons.
	* @return True on success, false on failure.
	*/
	public boolean setPersons(String l){
		getPersonsTextField().setText(l);
		return true;
	}
	/**
	* Gets the text inside the text field for persons involved.
	* @return Location name.
	*/
	public String getPersons(){return getPersonsTextField().getText();}
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the text inside the start text field, if it's a valid date.
     * @param dt The date text to be put inside the field.
     * @return True on success, false on failure.
     */
    public  boolean setStart(String dt){
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
    	try {
    		d = sdf.parse(dt);
    	} catch (Exception e){
    		if(getDebugStatus())
    			System.err.println("EventView.java:\n setStart():\n" +
    				"  Couldn't set the starting date & time from: "+dt+
    				". Set to null.");
    		return false;
    	}
    	getStartDateChooser().setDate(d);
    	return true;
    }
    /**
     * Gets the text inside the start text field.
     * @return The content of the field.
     */
    public String getStart(){
    	Date d = getStartDateChooser().getDate();
    	try{
    		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
    		return sdf.format(d);
    	} catch (Exception e){
    		return "";
    	}
    	
    }
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the text inside the end text field, if it's a valid date.
     * @param dt The date text to be put inside the field.
     * @return True on success, false on failure.
     */
    public  boolean setEnd(String dt){
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
    	try {
    		d = sdf.parse(dt);
    	} catch (Exception e){
    		if(getDebugStatus())
    			System.err.println("EventView.java:\n setEnd():\n" +
    				"  Couldn't set the ending date & time from: "+dt+
    				". Set to null.");
    		return false;
    	}
    	getEndDateChooser().setDate(d);
    	return true;
    }
    /**
     * Gets the text that is inside the end text field.
     * @return The content of the field.
     */
    public String getEnd(){
    	Date d = getEndDateChooser().getDate();
    	try{
	    	SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
	    	return sdf.format(d);
    	} catch (Exception e){
    		return "";
    	}
    }
    /**
     * Sets the date format of the date presented by the date choosers
     * @param df The new date format.
     */
    public void setDateChoosersDateFormat(String df){
		getStartDateChooser().setDateFormatString(df);
		getEndDateChooser().setDateFormatString(df);
    }
////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the text inside the description editor pane.
     * @param d The text to be put inside the editor pane.
     * @return True on success, false on failure.
     */
    public boolean setDescription(String d){
        getDescriptionTextArea().setText(d);
        return true;
    }
    
    /**
     * Gets the text that is inside the description editor pane.
     * @return The content of the editor pane.
     */
    public String getDescription() {return getDescriptionTextArea().getText();}
////////////////////////////////////////////////////////////////////////////////

    /**
     * Default constructor.<br/>
     * Sets the panel layout.<br/>
     * Initialises all the widgets.<br/>
     */
	public EventView() {
		final String RESIZE_BEHAVIOUR_NONE = "n";
		final String RESIZE_BEHAVIOUR_GROW = "grow";
		//Dialog Units are based on the pixel size of the dialog font and so, 
		//they	grow and shrink with the font and resolution.
		//Better than pixels.
		final String COL2_WIDTH = "10dlu"; //DLU = dialog units
		final String COL2_SPEC = COL2_WIDTH +":"+ RESIZE_BEHAVIOUR_NONE;
		final String COL3_WIDTH = "100dlu"; //DLU = dialog units
		final String COL3_SPEC = COL3_WIDTH +":"+ RESIZE_BEHAVIOUR_GROW;
        FormLayout layout = new FormLayout("pref, "+ //preferred
				        					COL2_SPEC + ","+
				        					COL3_SPEC + 
				        					", pref");
        
		setCategoryLabel(new JLabel("Category:"));
		getCategoryLabel().setIcon(new ImageIcon());
		getCategoryLabel().setHorizontalTextPosition(SwingConstants.LEFT);
		
		setCategoryComboBox(new JComboBox());
		EventsCategoriesComboBoxRenderer  renderer =
				new EventsCategoriesComboBoxRenderer();
		getCategoryComboBox().setRenderer(renderer);
		

        
        setFormBuilder(new DefaultFormBuilder(layout));
        int columnCount = getFormBuilder().getColumnCount();
        getFormBuilder().setDefaultDialogBorder();
        
        ImageIcon ic = new ImageIcon();
        try{
        	ic = new ImageIcon(
        		getClass().getResource("/ui/resources/calendar--clock.png"));
		} catch (Exception e){
			if(getDebugStatus())
				System.err.println("EventView.java:\n" +
					" EventView():\n"+
					"  Problem loading the custom icon for the date choosers.");
		}
        setDCIcon(ic);
        
        initialiseTitle();
        initialiseCategory();
        initialiseRepeats();
        initialiseLocation();
        initialisePersonsInvolved();
        initialiseStartDate();
        initialiseEndDate();
        initialiseDescription();
        
        setValidationResultModel(new DefaultValidationResultModel());
        JComponent validationResultsComponent =
                ValidationResultViewFactory.createReportList(
                        getValidationResultModel());
        getFormBuilder().appendUnrelatedComponentsGapRow();
        final String ROW_ALIGNMENT = "fill";
        final String COL_WIDTH = "50dlu";//dialog units
        final String ROW_SPEC = ROW_ALIGNMENT +":"+
        						COL_WIDTH +":"+
        						RESIZE_BEHAVIOUR_GROW;
        getFormBuilder().appendRow(ROW_SPEC);
        final int LINES_DOWN = 2;
        getFormBuilder().nextLine(LINES_DOWN);
        getFormBuilder().append(validationResultsComponent, columnCount);
    } 
	
	/** Sets up the title label and text field. */
	private void initialiseTitle(){
		setTitleLabel(new JLabel("Title:"));
		JTextField textField = new JTextField();
		textField.setDocument(new TextFieldLimiter(Event.TITLE_MAXLENGTH));
		setTitleTextField(textField);
        getFormBuilder().append(getTitleLabel(),getTitleTextField());
	}
	
	/** Sets up the category label and combo box. */
	private void initialiseCategory(){
		getFormBuilder().append(getCategoryLabel(),getCategoryComboBox());	
	}
	
	/** Sets up the repeat label and combo box. */
	private void initialiseRepeats(){
        setRepeatsLabel(new JLabel("Repeats:"));
        setRepeatsComboBox(new JComboBox());
        getFormBuilder().append(getRepeatsLabel(), getRepeatsComboBox());
    }
	
	/** Sets up the location label and text field. */
	private void initialiseLocation(){
        setLocationLabel(new JLabel("Location:"));
		JTextField textField = new JTextField();
		textField.setDocument(new TextFieldLimiter(Event.LOCATION_MAXLENGTH));
        setLocationTextField(textField);
        getFormBuilder().append(getLocationLabel(),getLocationTextField());
	}
	
	/** Sets up the starting date & time label and date chooser. */
	private void initialiseStartDate(){
		setStartLabel(new JLabel("Starting date & time:"));
		setStartDateChooser(new JDateChooser());
		getStartDateChooser().setIcon(getDCIcon());
		getStartDateChooser().setDateFormatString(getDateFormat());
		getFormBuilder().append(getStartLabel(),getStartDateChooser());
	}
	
	/** Sets up the ending date & time label and date chooser. */
	private void initialiseEndDate(){
        setEndLabel(new JLabel("Ending date & time:"));
        setEndDateChooser(new JDateChooser());
        getEndDateChooser().setIcon(getDCIcon());
        getEndDateChooser().setDateFormatString(getDateFormat());
        getFormBuilder().append(getEndLabel(),getEndDateChooser());
	}
	
	/** Sets up the description label, the text area scroll pane, and the text area. */
	private void initialiseDescription(){
        setDescriptionLabel(new JLabel("Description:"));
		JTextArea textArea = new JTextArea();
		textArea.setDocument(new TextFieldLimiter(Event.DESCRIPTION_MAXLENGTH));
        setDescriptionTextArea(textArea);
        setDescriptionScrollPane(new JScrollPane(getDescriptionTextArea()));
        getDescriptionScrollPane().setVerticalScrollBarPolicy(
        		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        getDescriptionScrollPane().setHorizontalScrollBarPolicy(
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        getFormBuilder().append(	getDescriptionLabel());
        getFormBuilder().appendUnrelatedComponentsGapRow();
    	final String RESIZE_BEHAVIOUR_GROW = "grow";
    	final String ROW_ALIGNMENT = "fill";
        final String COL_WIDTH = "50dlu";//dialog units
        final String ROW_SPEC = ROW_ALIGNMENT +":"+
        						COL_WIDTH +":"+
        						RESIZE_BEHAVIOUR_GROW;
        getFormBuilder().appendRow(ROW_SPEC);
        final int LINES_DOWN = 2;
        getFormBuilder().nextLine(LINES_DOWN);
        getFormBuilder().append
        	(getDescriptionScrollPane(),getFormBuilder().getColumnCount());        
	}
	
	/** Sets up the persons involved label. */
	private void initialisePersonsInvolved(){
		setPersonsTextField(new JTextField());
        getFormBuilder().append("Persons Involved:&",getPersonsTextField());
	}
    
    /**
     * Gets the built panel that is the view.
     * @return The panel.
     */
    public JPanel getPanel(){
		JPanel jp = getFormBuilder().getPanel();
        //jp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return jp;
    }
    


	/** Label for the title text field. */	
	private JLabel m_TitleLabel;
	/** Text field containing the event's title. */
    private JTextField m_TitleTextField;
    /** Label for the event category combo box */
	private JLabel m_CategoryLabel;
	/** Combo box containing all the event categories. */
	private JComboBox m_CategoryComboBox;
    /** The label for the repeats combo box. */
    private JLabel m_RepeatsLabel;
	/** The combo box of available repeats. */
    private JComboBox m_RepeatsComboBox;
	/** Label for the location text field. */
	private JLabel m_LocationLabel;
	/** Text field containing the location for the event. */
	private JTextField m_LocationTextField;
	/** Text field containing the location for the event. */
	private JTextField m_PersonsTextField;
	/** Label for the start text field. */
	private JLabel m_StartLabel;
    /** The date chooser for starting date & time. */
    private JDateChooser m_StartDateChooser;
	/** Label for the end text field. */
	private JLabel m_EndLabel;
	/** The date chooser for ending date & time. */
    private JDateChooser m_EndDateChooser;
	/** Label for the description editor pane. */
    private JLabel m_DescriptionLabel;
    /**
	* Scroll pane containing the editor pane.<br/>
	* Adds scrolling functionality to the description text box.
	*/
	private JScrollPane m_DescriptionScrollPane;
    /** Editor pane containing the description for the event. */
    private JTextArea m_DescriptionTextArea;
	/** The button for confirmation. */
	private JButton m_OKButton;
    /** The button for cancelling. */
    private JButton m_CancelButton;
    
    /** The custom icon for the date choosers. */
    private ImageIcon m_DCIcon = new ImageIcon();
    	
    
    /** Keeps the date format used. */
    private String m_JavaDateFormat;
    
    /** The form builder for the layout. */
    private DefaultFormBuilder m_FormBuilder;
    
    /** The validation result model used. */
    private ValidationResultModel m_ValidationResultModel;
    
    /** The debug switch. */
    private boolean m_debug = false;
    
   /**
    * Main displays the view in a window as a test.
    * @param args Arguments are ignored.
    */
	public static void main (String[] args){
		/** @test Displaying the view. */
		  JFrame jf = new JFrame();
		  jf.setTitle("EventView Test");
	      EventView ev = new EventView();
	      jf.add(ev.getPanel());
	      jf.pack();
	      jf.setVisible(true);
	      jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
} 
