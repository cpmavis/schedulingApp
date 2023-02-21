package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Model of the Appointments
 * @author Corey
 */
public class Appointments {
    /**
     * Id of the appointment
     */
    private int appointmentID;
    /**
     * title of the appointment
     */
    private String appointmentTitle;
    /**
     * description of the appointment
     */
    private String appointmentDescription;
    /**
     * location of the appointment
     */
    private String appointmentLocation;
    /**
     * type of the appointment
     */
    private String appointmentType;
    /**
     * start date of the appointment
     */
    private LocalDate appointmentStartDate;
    /**
     * start time of the appointment
     */
    private LocalTime appointmentStartTime;
    /**
     * end date of the appointment
     */
    private LocalDate appointmentEndDate;
    /**
     * end time of the appointment
     */
    private LocalTime appointmentEndTime;
    /**
     * ID of the customer
     */
    private int customerID;
    /**
     * ID of the user
     */
    private int userID;
    /**
     * ID of the contact
     */
    private String contactID;

    /**
     * Appointment constructor
     * @param appointmentID ID of the appointment
     * @param appointmentTitle title of the appointment
     * @param appointmentDescription description of the appointment
     * @param appointmentLocation location of the appointment
     * @param appointmentType type of the appointment
     * @param appointmentStartDate start date of the appointment
     * @param appointmentStartTime start time of the appointment
     * @param appointmentEndDate end date of the appointment
     * @param appointmentEndTime end time of the appointment
     * @param customerID ID of the customer
     * @param userID ID of the user
     * @param contactID ID of the contact
     */

    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, LocalDate appointmentStartDate,
                        LocalTime appointmentStartTime, LocalDate appointmentEndDate, LocalTime appointmentEndTime,
                        int customerID, int userID, String contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentEndTime = appointmentEndTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Get the appointment ID
     * @return getAppointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Appointment ID
     * @param appointmentID appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Get appointment title
     * @return getAppointmentTitle
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * Appointment title
     * @param appointmentTitle appointment title
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * Get appointment description
     * @return getAppointmentDescription
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Appointment Description
     * @param appointmentDescription appointment description
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Get appointment location
     * @return getAppointmentLocation
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * appointment location
     * @param appointmentLocation appointment location
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * Get appointment type
     * @return getAppointmentType
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * appointment type
     * @param appointmentType appointment type
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Get appointment start date
     * @return getAppointmentStartDate
     */
    public LocalDate getAppointmentStartDate() {
        return appointmentStartDate;
    }

    /**
     * appointment start date
     * @param appointmentStartDate appointment start date
     */
    public void setAppointmentStartDate(LocalDate appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    /**
     * Get appointment start time
     * @return getAppointmentStartTime
     */
    public LocalTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    /**
     * appointment start time
     * @param appointmentStartTime appointment start time
     */
    public void setAppointmentStartTime(LocalTime appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    /**
     * Get appointment end date
     * @return getAppointmentEndDate
     */
    public LocalDate getAppointmentEndDate() {
        return appointmentEndDate;
    }

    /**
     * appointment end date
     * @param appointmentEndDate appointment end date
     */
    public void setAppointmentEndDate(LocalDate appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    /**
     * Get appointment end time
     * @return getAppointmentEndTime
     */
    public LocalTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    /**
     * appointment end time
     * @param appointmentEndTime appointment end time
     */
    public void setAppointmentEndTime(LocalTime appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    /**
     * Get customer ID
     * @return getCustomerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * customer ID
     * @param customerID customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get user ID
     * @return getUserID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * user ID
     * @param userID user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get contact ID
     * @return getContactID
     */
    public String getContactID() {
        return contactID;
    }

    /**
     * contact ID
     * @param contactID contact ID
     */
    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

}