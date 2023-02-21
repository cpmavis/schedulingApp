package model;

import java.time.LocalDateTime;

/**
 * Model of the Customers
 * @author Corey
 */
public class Customers {
    /**
     * ID of the customer
     */
    private int customerID;
    /**
     * Name of the customer
     */
    private String customerName;
    /**
     * Address of the customer
     */
    private String customerAddress;
    /**
     * Postal code of the customer
     */
    private String customerPostalCode;
    /**
     * Phone number of the customer
     */
    private String customerPhoneNumber;
    /**
     * Date the customer was created
     */
    private LocalDateTime customerCreatedDate;
    /**
     * Person who created customer
     */
    private String customerCreatedBy;
    /**
     * Date customer was last updated
     */
    private LocalDateTime customerLastUpdateDate;
    /**
     * Person who last updated customer
     */
    private String customerLastUpdateBy;
    /**
     * FirstLevelDivision of the customer
     */
    private String customerFirstLevelDivisionID;

    /**
     * Customer Constructor
     * @param customerID ID of the customer
     * @param customerName Address of the customer
     * @param customerAddress Address of the customer
     * @param customerPostalCode Postal code of the customer
     * @param customerPhoneNumber Phone number of the customer
     * @param customerCreatedDate Date the customer was created
     * @param customerCreatedBy Person who created customer
     * @param customerLastUpdateDate Date customer was last updated
     * @param customerLastUpdateBy Person who last updated customer
     * @param customerFirstLevelDivisionID FirstLevelDivision of the customer
     */
    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, LocalDateTime customerCreatedDate, String customerCreatedBy,
                     LocalDateTime customerLastUpdateDate, String customerLastUpdateBy,
                     String customerFirstLevelDivisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCreatedDate = customerCreatedDate;
        this.customerCreatedBy = customerCreatedBy;
        this.customerLastUpdateDate = customerLastUpdateDate;
        this.customerLastUpdateBy = customerLastUpdateBy;
        this.customerFirstLevelDivisionID = customerFirstLevelDivisionID;
    }
    /**
     * get customer ID
     * @return getCustomerID
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * customer ID
     * @param customerID customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * get customer name
     * @return getCustomerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * customer name
     * @param customerName customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * get customer address
     * @return getCustomerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * customer address
     * @param customerAddress customer address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    /**
     * get customer postal code
     * @return getCustomerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    /**
     * customer postal code
     * @param customerPostalCode customer postal code
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }
    /**
     * get customer phone number
     * @return getCustomerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    /**
     * customer phone number
     * @param customerPhoneNumber customer phone number
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
    /**
     * get customer created date
     * @return getCustomerCreatedDate
     */
    public LocalDateTime getCustomerCreatedDate() {
        return customerCreatedDate;
    }
    /**
     * customer created date
     * @param customerCreatedDate customer created date
     */
    public void setCustomerCreatedDate(LocalDateTime customerCreatedDate) {
        this.customerCreatedDate = customerCreatedDate;
    }
    /**
     * get customer created by
     * @return getCustomerCreatedBy
     */
    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }
    /**
     * customer created by
     * @param customerCreatedBy customer created by
     */
    public void setCustomerCreatedBy(String customerCreatedBy) {
        this.customerCreatedBy = customerCreatedBy;
    }
    /**
     * get customer last update date
     * @return getCustomerLastUpdateDate
     */
    public LocalDateTime getCustomerLastUpdateDate() {
        return customerLastUpdateDate;
    }
    /**
     * customer last update date
     * @param customerLastUpdateDate customer last update date
     */
    public void setCustomerLastUpdateDate(LocalDateTime customerLastUpdateDate) {
        this.customerLastUpdateDate = customerLastUpdateDate;
    }
    /**
     * get customer last update by
     * @return getCustomerLastUpdateBy
     */
    public String getCustomerLastUpdateBy() {
        return customerLastUpdateBy;
    }
    /**
     * customer last update by
     * @param customerLastUpdateBy customer last update by
     */
    public void setCustomerLastUpdateBy(String customerLastUpdateBy) {
        this.customerLastUpdateBy = customerLastUpdateBy;
    }
    /**
     * get customer first level division ID
     * @return getCustomerFirstLevelDivisionID
     */
    public String getCustomerFirstLevelDivisionID() {
        return customerFirstLevelDivisionID;
    }
    /**
     * customer first level division ID
     * @param customerFirstLevelDivisionID customer first level division ID
     */
    public void setCustomerFirstLevelDivisionID(String customerFirstLevelDivisionID) {
        this.customerFirstLevelDivisionID = customerFirstLevelDivisionID;
    }

}
