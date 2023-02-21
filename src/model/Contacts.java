package model;
/**
 * Model of the Contacts
 * @author Corey
 */
public class Contacts {
    /**
     * ID of the contact
     */
    private int contactID;
    /**
     * Name of the contact
     */
    private String contactName;

    /**
     * Contact constructor
     * @param contactID ID of the contact
     * @param contactName Name of the contact
     */
    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Get contact ID
     * @return getContactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * contact ID
     * @param contactID contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * get contact name
     * @return getContactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * contact name
     * @param contactName contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
