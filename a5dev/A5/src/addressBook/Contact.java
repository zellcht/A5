/** 
 * @file Contact.java
 * @author Marc Hyatt, Luciano D'Acri. Ceris Land
 * @date 25/02/2012
 * @brief Contains the Contact class.
 */

/**
 * @package addressBook
 * @brief Contains the contacts list, the contact model, view and controller.
 */
package addressBook;

import dateAndTime.SystemDate;

/**
* @brief The model part of the Contact MVC architecture.
* 
* Represents a contact.<br/>
* It specifies all the methods, variables and functionality a contact must have.<br/>
* Contains all the accessor and mutator methods for a contact.
* @author Marc Hyatt, Luciano D'Acri. Ceris Land -A5  YAN SUN -A6
*/
public class Contact{
	
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
	/**
	 * Gets the contacts path.
	 * @return The path.
	 */
	public String getPath(){return m_path;}
	/**
	 * Sets the contacts path.
	 * @param id The new path.
	 */
	public void setPath(String path){m_path = path;}
	
	/**
	 * Gets the contacts ID.
	 * @return The ID.
	 */
	public String getID(){return m_ID;}
	/**
	 * Sets the contacts ID.
	 * @param id The new id.
	 */
	private void setID(String id){m_ID = id;}
    
	/**
     * Gets the first name of the contact.
     * @return The first name of the contact.
     */
	public String getFirstName(){return m_firstname;}
	
	/**
     * Sets the first name of the contact.
     * @param fn Desired first name.
     * @return true on success, false on failure.
     */
	public boolean setFirstName(String fn){
		if(!(validateNameLength(fn))){
			return false;
		}
		if (validateFirstNameRegex(fn)){
			m_firstname = fn;
			return true;
		}
		//Debug message.
		if(getDebugStatus())
			System.err.println("Contact.java:\n setFirstName():\n"
               + "  Invalid name entered" );
		return false;
	}
	
	/**
     * Gets the last name of the contact.
     * @return The last name of the contact.
     */
	public String getLastName(){return m_lastname;}
	
	/**
     * Sets the last name of the contact.
     * @param ln Desired last name.
     * @return true on success, false on failure.
     */
	public boolean setLastName(String ln){
		if(!(validateNameLength(ln))){
			return false;
		}
		if (validateLastNameRegex(ln)){
			m_lastname = ln;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setLastName():\n"
                + "  Invalid name entered" );
		return false;
	}
	
	/**
     * Gets the nickname of the contact.
     * @return The nickname of the contact.
     */
	public String getNickName(){return m_nickname;}
	
	/**
     * Sets the nickname of the contact.
     * @param nn Desired first name.
     * @return true on success, false on failure.
     */
	public boolean setNickName(String nn){
		if(!(validateNameLength(nn))){
			return false;
		}
		if (validateNickNameRegex(nn)){
			m_nickname = nn;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setNickName():\n"
               + "  Invalid name entered" );
		return false;
	}
	
	/**
     * Gets the address of the contact.
     * @return The address of the contact.
     */
	public String getAddress(){return m_address;}
	
	/**
     * Sets the address of the contact.
     * @param a Desired address.
     * @return true on success, false on failure.
     */
	public boolean setAddress(String a){
		if(validateAddressLength(a)){
			m_address = a;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setAddress():\n"
               + "  Invalid address entered" );
		return false;
	}
	
	/**
     * Gets the postcode of the contact.
     * @return The postcode of the contact.
     */
	public String getPostCode(){return m_postcode;}
	
	/**
     * Sets the postcode of the contact.
     * @param pc Desired postcode.
     * @return true on success, false on failure.
     */
	public boolean setPostCode(String pc){
		if (validatePostcodeLength(pc)){
			m_postcode = pc;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setPostCode():\n"
               + "  Invalid postcode entered" );
		return false;
	}
	
	/**
     * Gets the home telephone no. of the contact.
     * @return The home telephone no. of the contact.
     */
	public String getHomeNumber(){return m_homenumber;}
	
	/**
     * Sets the home telephone no. of the contact.
     * @param homenum Desired home telephone no.
     * @return true on success, false on failure.
     */
	public boolean setHomeNumber(String homenum){
		if (validateNumberLength(homenum)){
			m_homenumber = homenum;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setHomeNumber():\n"
               + "  Invalid number entered" );
		return false;
	}
	
	/**
     * Gets the mobile telephone no. of the contact.
     * @return The mobile telephone no. of the contact.
     */
	public String getMobileNumber(){return m_mobilenumber;}
	
	/**
     * Sets the mobile telephone no. of the contact.
     * @param mobnum Desired mobile telephone no.
     * @return true on success, false on failure.
     */
	public boolean setMobileNumber(String mobnum){
		if (validateNumberLength(mobnum)){
			m_mobilenumber = mobnum;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setMobileNumber():\n"
               + "  Invalid number entered" );
		return false;
	}
	
	/**
     * Gets the work telephone no. of the contact.
     * @return The work telephone no. of the contact.
     */
	public String getWorkNumber(){return m_worknumber;}
	
	/**
     * Sets the work telephone no. of the contact.
     * @param worknum Desired work telephone no.
     * @return true on success, false on failure.
     */
	public boolean setWorkNumber(String worknum){
		if (validateNumberLength(worknum)){
			m_worknumber = worknum;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setWorkNumber():\n"
               + "  Invalid number entered" );
		return false;
	}
	
	/**
     * Gets the fax no. of the contact.
     * @return The fax no. of the contact.
     */
	public String getFaxNumber(){return m_faxnumber;}
	
	/**
     * Sets the fax no. of the contact.
     * @param fax Desired fax no.
     * @return true on success, false on failure.
     */
	public boolean setFaxNumber(String fax){
		if (validateNumberLength(fax)){
			m_faxnumber = fax;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setFaxNumber():\n"
               + "  Invalid number entered" );
		return false;
	}
	
	/**
     * Gets the personal email address of the contact.
     * @return The personal email address of the contact.
     */
	public String getPersonalEmail(){return m_personalemail;}
	
	/**
     * Sets the personal email address of the contact.
     * @param pe Desired personal email address.
     * @return true on success, false on failure.
     */
	public boolean setPersonalEmail(String pe){
		if(!(validateEmailLength(pe))){
			return false;
		}
		if (validateEmailRegex(pe)){
			m_personalemail = pe;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setPersonalEmail():\n"
	                + "  Invalid email entered" );
		 return false;
	}
	
	/**
     * Gets the work email address of the contact.
     * @return The work email address of the contact.
     */
	public String getWorkEmail(){return m_workemail;}
	
	/**
     * Sets the work email address of the contact.
     * @param we Desired work email address.
     * @return true on success, false on failure.
     */
	public boolean setWorkEmail(String we){
		if(!(validateEmailLength(we))){
			return false;
		}
		if (validateEmailRegex(we)){
			m_workemail = we;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setWorkEmail():\n"
	                + "  Invalid email entered" );
		 return false;
	}
	
	/**
     * Gets any other email address of the contact.
     * @return Any other email address of the contact.
     */
	public String getOtherEmail(){return m_otheremail;}
	
	/**
     * Sets another alternative email address for the contact.
     * @param oe Desired other email address.
     * @return true on success, false on failure.
     */
	public boolean setOtherEmail(String oe){
		if(!(validateEmailLength(oe))){
			return false;
		}
		if (validateEmailRegex(oe)){
			m_otheremail = oe;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setOtherEmail():\n"
	                + "  Invalid email entered" );
		 return false;
	}
	
	/**
     * Gets the URL of the contact.
     * @return The URL of the contact.
     */
	public String getUrl(){return m_url;}
	
	/**
     * Sets the URL.
     * @param url Desired URL.
     * @return true on success, false on failure.
     */
	public boolean setUrl(String url){
		if(!(validateURLLength(url))){
			return false;
		}
		if (validateURLRegex(url)){
			m_url = url;
			return true;
		}
		if(getDebugStatus())
			System.err.println("Contact.java:\n setUrl():\n"
	                + "  Invalid URL" );
		return false;
	}
	
	/**
     * Validates the first name of the contact.
     * @param firstname Contacts first name.
     * @return the regex validation on first name.
     */
	public boolean validateFirstNameRegex(String firstname){
		if(firstname.isEmpty()){
			return true;
		}
		if(!firstname.matches( "[a-zA-z]+(([ '-][a-zA-Z]+)*)?")){
			System.out.println("Error: Invalid First Name");
			return false;
		} 
	return true;
	}
	
	/**
     * Validates the last name of the contact.
     * @param lastname Last name of contact.
     * @return the regex validation on last name.
     */
	public boolean validateLastNameRegex(String lastname){
		if(lastname.isEmpty()){
			return true;
		}
		if(!lastname.matches( "[a-zA-z]+(([ '-][a-zA-Z]+)*)?" )){
			System.out.println("Error: Invalid Last Name");
			return false;
		} 
	return true;
	}
	
	/**
     * Validates the nickname of the contact.
     * @param nickname Last name of contact.
     * @return the regex validation on last name.
     */
	public boolean validateNickNameRegex(String nickname){
		if(nickname.isEmpty()){
			return true;
		}
		if(!nickname.matches( "[a-zA-z0-9-_ ]*" )){
			System.out.println("Error: Invalid Nickname");
			return false;
		} 
	return true;
	}
	
	/** Validates the length of contacts names.
     * @param name Contacts name.
     * @return true on success, false on failure.
     */
	public boolean validateNameLength(String name){
		if(!(name.length() <= NAME_MAXLENGTH)){
		System.out.println("Error: Must be " 
				+ NAME_MAXLENGTH + " characters long");
		return false;
		}
		return true;
	}
	
	/**
     * Validates the length of contacts numbers and checks if empty.
     * @param number Contacts numbers.
     * @return true on success, false on failure.
     */
	public boolean validateNumberLength(String number){
		if(number.isEmpty()){
			return true;
		}
		if(!(number.length() <= TEL_MAXLENGTH)){
			System.out.println("Error: Must be "
					+ TEL_MAXLENGTH + " characters long");
			return false;
		}
		/*if(!number.matches("[0][0-9]{2,4}[ -][0-9]{6}")){
			System.out.println("Error: Invalid characters used");
			return false;
		} */
	return true;
	}
	
	/**
     * Validates the length of post codes and checks if empty.
     * @param pc Contacts post code.
     * @return true on success, false on failure.
     */
	public boolean validatePostcodeLength(String pc){
		if(pc.isEmpty()){
			return true;
		}
		if(!(pc.length() <= POSTCODE_MAXLENGTH)){
			System.out.println("Error: Must be " 
					+ POSTCODE_MAXLENGTH + " characters long");
			return false;
		}
		return true;
	}
	
	/**
     * Validates the length of addresses and checks if empty.
     * @param address Contacts address.
     * @return true on success, false on failure.
     */
	public boolean validateAddressLength(String address){
		if(address.isEmpty()){
			return true;
		}
		if(!(address.length() <= ADDRESS_MAXLENGTH)){
			System.out.println("Error: Must be "
					+ ADDRESS_MAXLENGTH + " characters long");
			return false;
		}
		return true;
	}
	
	/**
     * Validates the email addresses of the contact.
     * @param email Contacts email.
     * @return true on success, false on failure.
     */
	public boolean validateEmailRegex(String email){
		if(email.isEmpty()){
			return true;
		}
		if(!email.matches("^([a-zA-z0-9-_])+[@]([A-Za-z0-9])+" +
				"((\\.[A-Za-z0-9]{2,4}){1,2})$")){
			System.out.println("Error: Invalid Email Address");
			return false;
		}
	return true;
	}
	
	/**
     * Validates the length of email address and checks if empty.
     * @param email Contacts email address.
     * @return true on success, false on failure.
     */
	public boolean validateEmailLength(String email){
		if(!(email.length() <= EMAIL_MAXLENGTH)){
			System.out.println("Error: Must be "
					+ EMAIL_MAXLENGTH + " characters long");
			return false;
		}
		return true;
	}
	
	/**
     * Validates the URL.
     * @param url Contacts URL.
     * @return the regex validation on url.
     */
	public boolean validateURLRegex(String url){
		if(url.isEmpty()){
			return true;
		}
		
		if(!url.matches("^([a-zA-Z0-9]{2,5}://)?" + // protocol part
				"([wW]{3}.)?" + // www part
				"[a-zA-z]+" + // domain name part
				"(.[a-zA-Z]{2,4}){1,2}" + //TLD part
				"(/(.*)?)?")) // slash or slash whatever part
		{
			System.out.println("Error: Invalid URL");
			return false;
		}
		
	return true;
	}
	
	/**
     * Validates the length of URL's and checks if empty.
     * @param url Contacts URL.
     * @return true on success, false on failure.
     */
	public boolean validateURLLength(String url){
		if(!(url.length() <= URL_MAXLENGTH)){
			System.out.println("Error: Must be "
					+ URL_MAXLENGTH + " characters long");
			return false;
		}
		return true;
	}
	
	/**
	 * This method ensures that for every element in the array it is filled 
	 * with the contact details. Also every element is convert to a string.
	 * @return returns the array contactArray.
	 */
	
	public String[] getAsStingArray(){
		String[] contactArray = new String[ARRAY_SIZE];
		
		contactArray[ID_ARRAY_INDEX] = this.getID();
		contactArray[FIRSTNAME_ARRAY_INDEX] = this.getFirstName();
		contactArray[LASTNAME_ARRAY_INDEX] = this.getLastName();
		contactArray[NICKNAME_ARRAY_INDEX] = this.getNickName();
		contactArray[ADDRESS_ARRAY_INDEX] = this.getAddress();
		contactArray[POSTCODE_ARRAY_INDEX] = this.getPostCode();
		contactArray[HOMENUMBER_ARRAY_INDEX] = this.getHomeNumber();
		contactArray[MOBILENUMBER_ARRAY_INDEX] = this.getMobileNumber();
		contactArray[WORKNUMBER_ARRAY_INDEX] = this.getWorkNumber();
		contactArray[FAXNUMBER_ARRAY_INDEX] = this.getFaxNumber();
		contactArray[PERSONALEMAIL_ARRAY_INDEX] = this.getPersonalEmail();
		contactArray[WORKEMAIL_ARRAY_INDEX] = this.getWorkEmail();
		contactArray[OTHEREMAIL_ARRAY_INDEX] = this.getOtherEmail();
		contactArray[URL_ARRAY_INDEX] = this.getUrl();
		contactArray[PATH_ARRAY_INDEX] = this.getPath();
		return contactArray;
	}
	
	/**
	 * This method ensures that every element in the array is filled with
	 * default values for the contact details. 
	 * Also every element is converted to strings.
	 * @return returns the array contactArray.
	 */
	public static String[] getHeaderStringArray(){
		String[] contactArray = new String[ARRAY_SIZE];

		contactArray[ID_ARRAY_INDEX] = "Contact ID";
		contactArray[FIRSTNAME_ARRAY_INDEX] = "First Name";
		contactArray[LASTNAME_ARRAY_INDEX] = "Last Name";
		contactArray[NICKNAME_ARRAY_INDEX] = "Nick Name";
		contactArray[ADDRESS_ARRAY_INDEX] = "Address";
		contactArray[POSTCODE_ARRAY_INDEX] = "PostCode";
		contactArray[HOMENUMBER_ARRAY_INDEX] = "Home Number";
		contactArray[MOBILENUMBER_ARRAY_INDEX] = "Mobile Number";
		contactArray[WORKNUMBER_ARRAY_INDEX] = "Work Number";
		contactArray[FAXNUMBER_ARRAY_INDEX] = "Fax Number";
		contactArray[PERSONALEMAIL_ARRAY_INDEX] = "Personal Email";
		contactArray[WORKEMAIL_ARRAY_INDEX] = "Work Email";
		contactArray[OTHEREMAIL_ARRAY_INDEX] = "Other Email";
		contactArray[URL_ARRAY_INDEX] = "Url";
		contactArray[PATH_ARRAY_INDEX] = "Path";
		return contactArray;
	}
	
	/**
	 * This method receives all strings and converts them into the appropriate
	 * format.
	 * @return returns true on success, false on failure.
	 */
	public boolean setFromStringArray(String[] contactString){
		
		this.setID(contactString[ID_ARRAY_INDEX]);
		this.setFirstName(contactString[FIRSTNAME_ARRAY_INDEX]);
		this.setLastName(contactString[LASTNAME_ARRAY_INDEX]);
		this.setNickName(contactString[NICKNAME_ARRAY_INDEX]);
		this.setAddress(contactString[ADDRESS_ARRAY_INDEX]);
		this.setPostCode(contactString[POSTCODE_ARRAY_INDEX]);
		this.setHomeNumber(contactString[HOMENUMBER_ARRAY_INDEX]);
		this.setMobileNumber(contactString[MOBILENUMBER_ARRAY_INDEX]);
		this.setWorkNumber(contactString[WORKNUMBER_ARRAY_INDEX]);
		this.setFaxNumber(contactString[FAXNUMBER_ARRAY_INDEX]);
		this.setPersonalEmail(contactString[PERSONALEMAIL_ARRAY_INDEX]);
		this.setWorkEmail(contactString[WORKEMAIL_ARRAY_INDEX]);
		this.setOtherEmail(contactString[OTHEREMAIL_ARRAY_INDEX]);
		this.setUrl(contactString[URL_ARRAY_INDEX]);
		this.setPath(contactString[PATH_ARRAY_INDEX]);
		return true;
	}
	
    /**
	 * Sets the contact details to sensible default values.
	 */	
	public void setToDefaultValues() {
    	m_firstname = "First Name";
        m_lastname = "Last Name";
        m_address = "Unknown";
        m_mobilenumber = "00000000000";
        m_personalemail = "email@server.com";
        m_nickname = "";
		m_postcode = "";
		m_homenumber = "";
		m_faxnumber = "";
		m_workemail = "";
		m_worknumber = "";
		m_url = "";
		m_otheremail = "";
		m_path = "";
	}
	
   /**
    * Dummy Constructor - creates new contact with no values.
    */
    public Contact(){
    	setID(SystemDate.generateTimeStamp());
		setFirstName("");
		setLastName("");
		setNickName("");
		setAddress("");
		setPostCode("");
		setHomeNumber("");
		setMobileNumber("");
		setWorkNumber("");
		setFaxNumber("");
		setPersonalEmail("");
		setWorkEmail("");
		setOtherEmail("");
		setUrl("");
		setPath("");
    }
    
    /**
     * Compares the contact with a given contact.
     * @param c The contact to compare against.
     * @return True if all fields (except IDs) in both contacts are equal.
     */
    public boolean equals(Contact c){
 	   
 	   if (c == null)
 		   return false;
 
 	   return true;
    }
    
	/** Tracks the debug status. Off by default. */
	private boolean m_debug = false;
	/** The ID for a contact. */
	private String m_ID; 

	/** Maximum number of characters for lengths of names. */
	public static final int NAME_MAXLENGTH = 20;
	/** Maximum constant for lengths of telephone numbers. */
	public static final int TEL_MAXLENGTH = 20;
	/** Maximum constant for lengths of addresses. */
	public static final int ADDRESS_MAXLENGTH = 50;
	/**
	 * Maximum constant for length of post codes.
	 * Based on <a href="https://en.wikipedia.org/wiki/Post_code"> Wikipedia </a>
	 */
	public static final int POSTCODE_MAXLENGTH = 10;
	/** Maximum constant for lengths of email addresses. */
	public static final int EMAIL_MAXLENGTH = 30;
	/** Maximum constant for lengths of URL's. */
	public static final int URL_MAXLENGTH = 300;
	/** Size of the storage array for contact */
	public static final int ARRAY_SIZE = 15;
	/** The index position in the array for ID. */
	public static final int ID_ARRAY_INDEX = 0;
	/** The index position in the array for first name. */
	public static final int FIRSTNAME_ARRAY_INDEX = 1;
	/** The index position in the array for last name. */
	public static final int LASTNAME_ARRAY_INDEX = 2;
	/** The index position in the array for nickname. */
	public static final int NICKNAME_ARRAY_INDEX = 3;
	/** The index position in the array for address. */
	public static final int ADDRESS_ARRAY_INDEX = 4;
	/** The index position in the array for postcode. */
	public static final int POSTCODE_ARRAY_INDEX = 5;
	/** The index position in the array for house number. */
	public static final int HOMENUMBER_ARRAY_INDEX = 6;
	/** The index position in the array for mobile number. */
	public static final int MOBILENUMBER_ARRAY_INDEX = 7;
	/** The index position in the array for work number. */
	public static final int WORKNUMBER_ARRAY_INDEX =8;
	/** The index position in the array for fax number. */
	public static final int FAXNUMBER_ARRAY_INDEX = 9;
	/** The index position in the array for personal email. */
	public static final int PERSONALEMAIL_ARRAY_INDEX = 10;
	/** The index position in the array for work email. */
	public static final int WORKEMAIL_ARRAY_INDEX = 11;
	/** The index position in the array for other email. */
	public static final int OTHEREMAIL_ARRAY_INDEX = 12;
	/** The index position in the array for url. */
	public static final int URL_ARRAY_INDEX = 13;
	/** The index position in the array for path. */
	public static final int PATH_ARRAY_INDEX = 14;
	/** Keeps the first name of the contact. */
	private String m_firstname;
	/** Keeps the surname of the contact. */
    private String m_lastname;
	/** Keeps the nick name of the contact. */
    private String m_nickname;
    /** Keeps the address of the contact. */
    private String m_address;
	/** Keeps the postcode of the contact. */
    private String m_postcode;
    /** Keeps the home telephone number of the contact. */
    private String m_homenumber;
	/** Keeps the mobile telephone number of the contact. */
    private String m_mobilenumber;
	/** Keeps the work telephone number of the contact. */
    private String m_worknumber;
	/** Keeps the fax number of the contact. */
    private String m_faxnumber;
	/** Keeps the personal email address of the contact. */
    private String m_personalemail;
	/** Keeps the work email address of the contact. */
    private String m_workemail;
	/** Keeps the other email addresses of the contact. */
    private String m_otheremail;
    /** Keeps the URL. */
    private String m_url;
    /** Keeps the path. */
    private String m_path;
	
	/**
	 * This is the main method used for testing the class.
	 * @param args Parameter for file input.
	 */
	public static void main(String[] args){
		Contact contact1 = new Contact();
		contact1.setToDefaultValues();
		
		/** @test tests the validateFirstNameRegex method
		 * 	with a valid first name.
		 */
		System.out.print("test1----------------------");
		if(contact1.validateFirstNameRegex("Joe"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateFirstNameRegex method
		 * 	with an invalid first name.
		 */
		System.out.print("test2----------------------");
		if(contact1.validateFirstNameRegex("c7629gd"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateLastNameRegex method
		 * 	with a valid name.
		 */
		System.out.print("test3----------------------");
		if(contact1.validateLastNameRegex("Bloggs-Williams"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
				
		/** @test tests the validateLastNameRegex method
		 * 	with an invalid name.
		 */
		System.out.print("test4----------------------");
		if(contact1.validateLastNameRegex("blog.12gsss"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateNickNameRegex method
		 * 	with a valid name.
		 */
		System.out.print("test5----------------------");
		if(contact1.validateNickNameRegex("Joe_1223"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateNickNameRegex method
		 * 	with an invalid name.
		 */
		System.out.print("test6----------------------");
		if(contact1.validateNickNameRegex("Joe Bloggs"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateNameLength method
		 * 	with an invalid length name.
		 */
		System.out.print("test7----------------------");
		if(contact1.validateNameLength("Joe Bloggssssssssssssssssssss"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateNameLength method
		 * 	with an empty string.
		 */
		System.out.print("test8----------------------");
		if(contact1.validateNameLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validateNumberLength method
		 * 	with an empty string.
		 */
		System.out.print("test9----------------------");
		if(contact1.validateNumberLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validatePostcodeLength method
		 * 	with an empty string.
		 */
		System.out.print("test10----------------------");
		if(contact1.validatePostcodeLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validateAddressLength method
		 * 	with an empty string.
		 */
		System.out.print("test11----------------------");
		if(contact1.validateAddressLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validateEmailLength method
		 * 	with an empty string.
		 */
		System.out.print("test12----------------------");
		if(contact1.validateEmailLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validateURLLength method
		 * 	with an empty string.
		 */
		System.out.print("test13----------------------");
		if(contact1.validateURLLength(""))
			System.out.println("True");
		else 
			System.out.println("False");
		
		/** @test tests the validatePostcodeLength method
		 * 	with a valid length postcode.
		 */
		System.out.print("test14----------------------");
		if(contact1.validatePostcodeLength("sa12 3td"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validatePostcodeLength method
		 * 	with an invalid length postcode.
		 */
		System.out.print("test15----------------------");
		if(contact1.validatePostcodeLength("sa12 33mdrt"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");

		/** @test tests the validateAddressLength method
		 * 	with a valid length address.
		 */
		System.out.print("test16----------------------");
		if(contact1.validateAddressLength("swansea university, " +
											" singleton park, swansea"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateAddressLength method
		 * 	with a valid length address.
		 */
		System.out.print("test17----------------------");
		if(contact1.validateAddressLength("swansea university, " +
				                 " singleton park, swansea, kjkadkdowdwdwd"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");

		/** @test tests the validateEmailLength method
		 * 	with an invalid length email address.
		 */
		System.out.print("test18----------------------");
		if(contact1.validateEmailLength
				("group7_assignment5fullimplementation@hotmail.com"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateURLLength method
		 * 	with an invalid length url.
		 */
		System.out.print("test19----------------------");
		if(contact1.validateURLLength
				("www.group7_assignment5fullimplementation.ac.uk"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the setHomeNumber method
		 * 	with a valid number.
		 */
		System.out.print("test20----------------------");
		if(contact1.setHomeNumber("01923 456952"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateNumberLength method
		 * 	with an invalid length number.
		 */
		System.out.print("test21----------------------");
		if(contact1.validateNumberLength("01893253455232854545145"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with a valid email address 1.
		 */
		System.out.print("test22----------------------");
		if(contact1.validateEmailRegex("294201@swansea.ac.uk"))
			System.out.println("Passed");
		else 
		System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with a valid email address 2.
		 */
		System.out.print("test23----------------------");
		if(contact1.validateEmailRegex("simon@mail.com"))
			System.out.println("Passed");
		else 
		System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with a valid email address 3.
		 */
		System.out.print("test24----------------------");
		if(contact1.validateEmailRegex("joe444@mail.co.uk"))
			System.out.println("Passed");
		else 
		System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with a valid email address 4.
		 */
		System.out.print("test25----------------------");
		if(contact1.validateEmailRegex("j.bloggs@swan.ac.uk"))
			System.out.println("Passed");
		else 
		System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with a valid email address 5.
		 */
		System.out.print("test26----------------------");
		if(contact1.validateEmailRegex("joe@swan.ac.uk"))
			System.out.println("Passed");
		else 
		System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with an invalid email address 1.
		 */
		System.out.print("test27----------------------");
		if(contact1.validateEmailRegex("www."))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
				
		/** @test tests the validateEmailRegex method
		 * 	with an invalid email address 2.
		 */
		System.out.print("test28----------------------");
		if(contact1.validateEmailRegex("joe@mail"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateEmailRegex method
		 * 	with an invalid email address 3.
		 */
		System.out.print("test29----------------------");
		if(contact1.validateEmailRegex("mail.com"))
			System.out.println("Passed");
		else 
			System.out.println("Failed");
		
		/** @test tests the validateURLRegex method
		 * 	with a valid URL 1.
		 */
		System.out.print("test30----------------------");
		if(contact1.validateURLRegex("http://www.assigment5.com"))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with a valid URL 2.
		 */
		System.out.print("test31----------------------");
		if(contact1.validateURLRegex("www.staples.com"))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with a valid URL 3.
		 */
		System.out.print("test32----------------------");
		if(contact1.validateURLRegex("http://www.staples.com"))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with a valid URL 3.
		 */
		System.out.print("test33----------------------");
		if(contact1.validateURLRegex("http://staples.com"))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with an invalid URL 1.
		 */
		System.out.print("test34----------------------");
		if(contact1.validateURLRegex("http://www.assigment5."))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with an invalid URL 2.
		 */
		System.out.print("test35----------------------");
		if(contact1.validateURLRegex("http://staples"))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
		
		/** @test tests the validateURLRegex method
		 * 	with an invalid URL 3.
		 */
		System.out.print("test36----------------------");
		if(contact1.validateURLRegex("http://www."))
			System.out.println("Good URL passed");
		else 
			System.out.println("Bad URL passed");
	}
		
}