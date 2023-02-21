package model;
/**
 * Model of the Appointments
 * @author Corey
 */

public class Users {
    /**
     * ID of the user
     */
    private int userID;
    /**
     * Username of the user
     */
    private String userName;
    /**
     * Password of the user
     */
    private String userPassword;

    /**
     * User Constructor
     * @param userID ID of the user
     * @param userName Username of the user
     * @param userPassword Password of the user
     */
    public Users(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * get user ID
     * @return getUserID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * userID
     * @param userID userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get username
     * @return getUserName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * username
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get user password
     * @return getUserPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * user password
     * @param userPassword user password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
