package controller;

import DAO.AppointmentDAO;
import DAO.UsersDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.Appointments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class that provides the logic to confirm a valid login
 * Writes in the login was successful or not
 * Checks for upcoming appointments
 * @author Corey
 */
public class loginScreenController implements Initializable {
    /**
     * Label for username
     */
    @FXML
    private Label usernameLabel;
    /**
     * Label for the password
     */
    @FXML
    private Label passwordLabel;
    /**
     * Label for Login Screen
     */
    @FXML
    private Label loginScreenLabel;
    /**
     * Label for the TimeZone
     */
    @FXML
    private Label timeZoneLabel;
    /**
     * TextField for the Username
     */
    @FXML
    private TextField loginScreenUserNameTextField;
    /**
     * TextField for the password
     */
    @FXML
    private TextField loginScreenPasswordTextField;
    /**
     * Button to login
     */
    @FXML
    private Button loginScreenLoginButton;
    /**
     * Button to clear the text fields
     */
    @FXML
    private Button loginScreenClearButton;
    /**
     * Label to show the TimeZone
     */
    @FXML
    private Label loginScreenTimeZoneLabel;
    /**
     * Instance of Observable List of Appointments
     */

    ObservableList<Appointments> appointmentsObservableList = AppointmentDAO.getAllAppointments();
    /**
     * Instance of a ResourceBundle
     */
    ResourceBundle rb = ResourceBundle.getBundle("languages", Locale.getDefault());
/**
 * Initialize the controller and populates all the customer fields
 * Initialize the buttons to run the methods when called
 * @param url location for the program to load the root object
 * @param resourceBundle resources used to locate the root object
 *
 * LAMBDAS
 *  #1
 *      Runs the mainScreen method when loginScreenLoginButton is pushed.
 *      Catches an IOException.
 *
 *  #2
 *      Runs the clearFields method when the loginScreenClearButton is pushed.
 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginScreenLabel.setText(rb.getString("title"));
        timeZoneLabel.setText(rb.getString("timezone"));
        loginScreenLoginButton.setText(rb.getString("login"));
        loginScreenClearButton.setText(rb.getString("clear"));

        loginScreenTimeZoneLabel.setText(ZoneId.systemDefault().toString());
        /*
        LAMBDA #1
        */
        loginScreenLoginButton.setOnAction(e -> {
            try {
                mainScreen();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        /*
        LAMBDA #2
         */
        loginScreenClearButton.setOnAction(e -> clearFields());


    }

    /**
     * Loads up the mainScreenView stage and sends the information to the controller
     * Notifies the user if the login was successful or not
     * Notifies the user of any upcoming appointments
     * Writes the success/failures of the login to a file
     * @throws IOException connection did not work
     */
    public void mainScreen() throws IOException {
        //String username = "test";
        //String password = "test";
        String username = loginScreenUserNameTextField.getText();
        String password = loginScreenPasswordTextField.getText();
        boolean success = false;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        success = UsersDAO.validUser(username, password);
        if (success == true) {
            try {
                loginTracker(username, timestamp, success);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/mainScreenView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                mainScreenController user = loader.getController();
                user.userLoggedInReceived(username);
                Stage stage = (Stage) loginScreenLoginButton.getScene().getWindow();
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                appointmentCheck();

            } catch (NullPointerException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Unable to login.");
                alert.show();

            }

        } else {
            loginTracker(username, timestamp, success);
            System.out.println("Failure");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("loginFailed"));
            alert.showAndWait();
        }

    }

    /**
     * Checks if there are any appointments coming up in the next 15 minutes
     * Notifies the user if there are any appointments coming up in the next 15 minutes
     */
    public void appointmentCheck() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime appointmentTime;
        LocalDate today = LocalDate.now();
        int numberOfAppointments = 0;
        Boolean hasAppointment = false;
        LocalTime customerAppointmentTime = null;
        LocalDate customerAppointmentDate = null;
        int appointmentID = 0;
        for (Appointments appointments : appointmentsObservableList) {
            appointmentTime = appointments.getAppointmentStartTime();
            LocalDateTime appointTime = appointmentTime.atDate(today);
            if (appointTime.isAfter(currentTime) && appointTime.isBefore(currentTime.plusMinutes(15))) {
                hasAppointment = true;
                numberOfAppointments += 1;
                customerAppointmentTime = appointments.getAppointmentStartTime();
                customerAppointmentDate = appointments.getAppointmentStartDate();
                appointmentID = appointments.getAppointmentID();
            }
        }
        if (hasAppointment != false) {
            if (numberOfAppointments == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You have an appointment in the next 15 minutes: " +
                        "\nTime: " + customerAppointmentTime + "\nDate: " + customerAppointmentDate +
                        "\nAppointment ID: " + appointmentID);
                alert.showAndWait();
            } else if (numberOfAppointments > 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You have " + numberOfAppointments + " appointments coming up");
                alert.showAndWait();
            }
        } else if (hasAppointment == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have no appointments scheduled in the next 15 minutes");
            alert.showAndWait();
        }

    }

    /**
     * Clears all the text fields
     */
    public void clearFields() {
        loginScreenUserNameTextField.setText(null);
        loginScreenPasswordTextField.setText(null);
    }

    /**
     * Writes to login_activity.txt if the login was succesful or not
     * @param username username of the user
     * @param timestamp password of the user
     * @param successfulLogin boolean value for success or failure
     * @throws IOException IO Exception
     */
    public void loginTracker(String username, Timestamp timestamp, Boolean successfulLogin) throws IOException {
        String loginRecord = "Username: " + username + " TimeStamp: " + timestamp + " Successful Login: " + successfulLogin + "\n";
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            fw.append(loginRecord);
            fw.flush();
            fw.close();

        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }
}
