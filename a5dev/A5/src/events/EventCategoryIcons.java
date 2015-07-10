/**
 * @file   EventCategoryIcons.java
 * @author Adewale Odunlami
 * @date   19/03/2012
 * @brief  Gets and returns an appropriate icon for the event type which the user specifies.
 */

/**
 * @package events
 * @brief Everything about calendar events, except icon resources.
 */
package events;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @brief Event categories icons keeper.
 * 
 * Holds the location of the icon for each event category.<br/>
 * Has a method that gives the icon for the asked category.
 * @author Adewale Odunlami
 */
public class EventCategoryIcons {

	/** The icon for the other event category. */
	private static ImageIcon OTHER_ICON;
	/** The icon for the social event category. */
	private static ImageIcon SOCIAL_EVENT_ICON;
	/** The icon for the other birthday event category. */
	private static ImageIcon BIRTHDAY_ICON;
	/** The icon for the other Anniversary event category. */
	private static ImageIcon ANNIVERSARY_ICON;
	/** The icon for the meal event category. */
	private static ImageIcon MEAL_ICON;
	/** The icon for the happy hour event category. */
	private static ImageIcon HAPPY_HOUR_ICON;
	/** The icon for the concert event category. */
	private static ImageIcon CONCERT_ICON;
	/** The icon for the sports event category. */
	private static ImageIcon SPORTS_ICON;
	/** The icon for the work deadline event category. */
	private static ImageIcon WORK_DEADLINE_ICON;
	/** The icon for the meeting event category. */
	private static ImageIcon MEETING_ICON;
	/** The icon for the appointment event category. */
	private static ImageIcon APPOINTMENT_ICON;
	/** The icon for the bank holiday event category. */
	private static ImageIcon BANK_HOLIDAY_ICON;
	/** The icon for the bill payment event category. */
	private static ImageIcon BILL_PAYMENT_ICON;
	/** The icon for the class/lecture event category. */
	private static ImageIcon CLASS_LECTURE_ICON;
	/** The icon for the accident event category. */
	private static ImageIcon ACCIDENT_ICON;
	/** The icon for the previous payment event category. */
	private static ImageIcon PREVIOUS_PAYMENT_ICON;
	/** The icon for the gym event category. */
	private static ImageIcon GYM_ICON;
	/** The icon for the vacation event category. */
	private static ImageIcon VACATION_ICON;
	
	/**
	 * Default constructor calls the icons initialiser.
	 */
	public EventCategoryIcons() {
		initialiseIcons();
	}
	
	/**
	 * Gets the icon for the requested category.
	 * @param c The category name.
	 * @return The icon for that category.
	 */
	public ImageIcon getIconForCategory(String c){
		if (c == null)
			return new ImageIcon();
		if (c.equalsIgnoreCase("Other")){
			//System.out.println("Matched:" + c);
			return OTHER_ICON;
		}
		else if (c.equalsIgnoreCase("Social Event")){
			//System.out.println("Matched:" + c);
			return SOCIAL_EVENT_ICON;
		}
		
		else if (c.equalsIgnoreCase("Birthday")){
			//System.out.println("Matched:" + c);
			return BIRTHDAY_ICON;
		}
		
		if (c.equalsIgnoreCase("Anniversary"))
			return ANNIVERSARY_ICON;
		
		if (c.equalsIgnoreCase("Meal"))
			return MEAL_ICON;
		
		if (c.equalsIgnoreCase("Happy Hour"))
			return HAPPY_HOUR_ICON;
		
		if (c.equalsIgnoreCase("Concert"))
			return CONCERT_ICON;
		
		if (c.equalsIgnoreCase("Sports"))
			return SPORTS_ICON;
		
		if (c.equalsIgnoreCase("Work Deadline"))
			return WORK_DEADLINE_ICON;
		
		if (c.equalsIgnoreCase("Meeting"))
			return MEETING_ICON;
		
		if (c.equalsIgnoreCase("Appointment"))
			return APPOINTMENT_ICON;
		
		if (c.equalsIgnoreCase("Bank Holiday"))
			return BANK_HOLIDAY_ICON;
		
		if (c.equalsIgnoreCase("Bill Payment"))
			return BILL_PAYMENT_ICON;
		
		if (c.equalsIgnoreCase("Class/Lecture"))
			return CLASS_LECTURE_ICON;
		
		if (c.equalsIgnoreCase("Accident"))
			return ACCIDENT_ICON;
		
		if (c.equalsIgnoreCase("Previous Payment"))
			return PREVIOUS_PAYMENT_ICON;
		
		if (c.equalsIgnoreCase("Gym"))
			return GYM_ICON;
		
		if (c.equalsIgnoreCase("Vacation"))
			return VACATION_ICON;
		
		return new ImageIcon();
	}
	
	private ImageIcon getIconResource(String res){
		try{
			ImageIcon ic = new ImageIcon(getClass().getResource(res));
			return ic;
		} catch (Exception e){
			//System.err.println("ECI: assignIcon(): Failed to assign resource: "
				//	+res);
			return new ImageIcon();
		}
	}
	
	/**
	 * Initialises the icons.
	 */
	private void initialiseIcons(){
		final String PATH = "/ui/resources/events/";
		OTHER_ICON = getIconResource(PATH + "question-button.png");
		SOCIAL_EVENT_ICON = getIconResource(PATH + "cup.png");
		BIRTHDAY_ICON = getIconResource(PATH + "rubber-balloons.png");
		ANNIVERSARY_ICON = getIconResource(PATH + "present.png");
		MEAL_ICON = getIconResource(PATH + "bread.png");
		HAPPY_HOUR_ICON = getIconResource(PATH + "dummy-happy.png");
		CONCERT_ICON = getIconResource(PATH + "music.png");
		SPORTS_ICON = getIconResource(PATH + "sport-tennis.png");
		WORK_DEADLINE_ICON = getIconResource(PATH+"briefcase--exclamation.png");
		MEETING_ICON = getIconResource(PATH + "users.png");
		APPOINTMENT_ICON = getIconResource(PATH + "user-business-boss.png");
		BANK_HOLIDAY_ICON = getIconResource(PATH + "rosette-label.png");
		BILL_PAYMENT_ICON = getIconResource(PATH + "money--minus.png");
		CLASS_LECTURE_ICON = getIconResource(PATH + "quill.png");
		ACCIDENT_ICON = getIconResource(PATH + "exclamation.png");
		PREVIOUS_PAYMENT_ICON = getIconResource(PATH + "currency-pound.png");
		GYM_ICON = getIconResource(PATH + "t-shirt-print.png");
		VACATION_ICON = getIconResource(PATH + "luggage-tag.png");
	}
	
	public static void main(String args[]){
		final EventCategoryIcons eci = new EventCategoryIcons();	
    	EventQueue.invokeLater(new Runnable() {
			
			@Override
			/**
			 * Implementing the method.
			 */
			public void run() {
				
				FormLayout fl = new FormLayout(
						new ColumnSpec[] {
								FormFactory.DEFAULT_COLSPEC,
						},
						new RowSpec[]{}
					);
				
				DefaultFormBuilder fb = new DefaultFormBuilder(fl);
				/** @test Displaying the icon for category other. */
		    	JLabel other = new JLabel("Other label");
		    	other.setIcon(eci.getIconForCategory("other"));
		    	fb.append(other);
		    	/** @test Displaying the icon for category social event. */
		    	JLabel social = new JLabel("Social Event label");
		    	social.setIcon(eci.getIconForCategory("social event"));		    	
		    	fb.append(social);
		    	/** @test Displaying the icon for category birthday. */
		    	JLabel bday = new JLabel("Birthday label");
		    	bday.setIcon(eci.getIconForCategory("birthday"));
		    	fb.append(bday);
		    	/** @test Displaying the icon for category anniversary. */
		    	JLabel anni = new JLabel("Anniversary label");
		    	anni.setIcon(eci.getIconForCategory("anniversary"));
		    	fb.append(anni);
		    	/** @test Displaying the icon for category meal. */
		    	JLabel meal = new JLabel("Meal label");
		    	meal.setIcon(eci.getIconForCategory("meal"));
		    	fb.append(meal);
		    	/** @test Displaying the icon for category happy hour. */
		    	JLabel HH = new JLabel("Happy hour label");
		    	HH.setIcon(eci.getIconForCategory("happy hour"));
		    	fb.append(HH);
		    	/** @test Displaying the icon for category concert. */
		    	JLabel concert = new JLabel("Concert label");
		    	concert.setIcon(eci.getIconForCategory("concert"));
		    	fb.append(concert);
		    	/** @test Displaying the icon for category sports. */
		    	JLabel  sports = new JLabel("Sports label");
		    	sports.setIcon(eci.getIconForCategory("sports"));
		    	fb.append(sports);
		    	/** @test Displaying the icon for category work deadline. */
		    	JLabel workDL = new JLabel("Work deadline label");
		    	workDL.setIcon(eci.getIconForCategory("work deadline"));
		    	fb.append(workDL);
		    	/** @test Displaying the icon for category meeting. */
		    	JLabel meeting = new JLabel("Meeting label");
		    	meeting.setIcon(eci.getIconForCategory("meeting"));
		    	fb.append(meeting);
		    	/** @test Displaying the icon for category appointment. */
		    	JLabel appo = new JLabel("Appointment label");
		    	appo.setIcon(eci.getIconForCategory("appointment"));
		    	fb.append(appo);
		    	/** @test Displaying the icon for category bank holiday. */
		    	JLabel BH = new JLabel("Bank holiday label");
		    	BH.setIcon(eci.getIconForCategory("bank holiday"));
		    	fb.append(BH);
		    	/** @test Displaying the icon for category bill payment. */
		    	JLabel BP = new JLabel("Bill payment label");
		    	BP.setIcon(eci.getIconForCategory("bill payment"));
		    	fb.append(BP);
		    	/** @test Displaying the icon for category class/lecture. */
		    	JLabel CL = new JLabel("Class/Lecture label");
		    	CL.setIcon(eci.getIconForCategory("Class/lecture"));
		    	fb.append(CL);
		    	/** @test Displaying the icon for category accident. */
		    	JLabel accident = new JLabel("Accident label");
		    	accident.setIcon(eci.getIconForCategory("accident"));
		    	fb.append(accident);
		    	/** @test Displaying the icon for category previous payment. */
		    	JLabel ppay = new JLabel("Previous payment label");
		    	ppay.setIcon(eci.getIconForCategory("previous payment"));
		    	fb.append(ppay);
		    	/** @test Displaying the icon for category gym. */
		    	JLabel gym = new JLabel("Gym label");
		    	gym.setIcon(eci.getIconForCategory("gym"));
		    	fb.append(gym);
		    	/** @test Displaying the icon for category vacation. */
		    	JLabel vac = new JLabel("Vacation label");
		    	vac.setIcon(eci.getIconForCategory("vacation"));
		    	fb.append(vac);		    	

		    	JFrame jf = new JFrame();
		    	jf.add(fb.getContainer());
		    	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		       	jf.pack();
		    	jf.setVisible(true);
			}
		});
	}
}
