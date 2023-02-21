package controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * Controller class that provides the logic to update an appointment
 * Allows the user to update an appointment and checks that the appointment is valid
 * @author Corey
 */

public class modifyAppointmentController implements Initializable {
    /**
     * ComboBox with Appointment User ID
     */
    @FXML private ComboBox<Integer> appointmentUserIDComboBox;
    /**
     * ComboBox with Appointment Start Time Hour
     */
    @FXML private ComboBox<Integer> startTimeHourComboBox;
    /**
     * ComboBox with Appointment Start Time Minute
     */
    @FXML private ComboBox<Integer> startTimeMinuteComboBox;
    /**
     * ComboBox with Appointment End Time Hour
     */
    @FXML private ComboBox<Integer> endTimeHourComboBox;
    /**
     * ComboBox with Appointment End Time Minute
     */
    @FXML private ComboBox<Integer> endTimeMinuteComboBox;
    /**
     * ComboBox with Appointment Contact
     */
    @FXML private ComboBox<String> appointmentContactComboBox;
    /**
     * ComboBox with Appointment Customer ID
     */
    @FXML private ComboBox<Integer> appointmentCustomerIDComboBox;
    /**
     * TextField for Appointment ID
     */
    @FXML private TextField appointmentIDTextField;
    /**
     * TextField for Appointment Title
     */
    @FXML private TextField appointmentTitleTextField;
    /**
     * TextField for Appointment Description
     */
    @FXML private TextField appointmentDescriptionTextField;
    /**
     * TextField for Appointment Location
     */
    @FXML private TextField appointmentLocationTextField;
    /**
     * Button to save Appointment Information
     */
    @FXML private Button saveButton;
    /**
     * Button to Exit Appointment Screen
     */
    @FXML private Button cancelButton;
    /**
     * TextField for Appointment Type
     */
    @FXML private TextField appointmentTypeTextField;
    /**
     * DatePicker for Appointment Start Date
     */
    @FXML private DatePicker appointmentStartDateComboBox;
    /**
     * DatePicker for Appointment End Date
     */
    @FXML private DatePicker appointmentEndDateComboBox;
    /**
     * Instance of Appointment Observable list
     */
    ObservableList<Appointments> appointmentsObservableList = AppointmentDAO.getAllAppointments();
    /**
     * Instance of Customer Observable list
     */
    ObservableList<Customers> customersObservableList = CustomersDAO.getAllCustomers();
    /**
     * Instance of Contact Observable list
     */
    ObservableList<Contacts> contactsObservableList = ContactsDAO.getAllContacts();
    /**
     * Instance of Users Observable list
     */
    ObservableList<Users> usersObservableList = UsersDAO.getAllUser();
    /**
     * Instance of Integer Observable list
     */
    ObservableList<Integer> customersID = FXCollections.observableArrayList();
    /**
     * Instance of String Observable list
     */
    ObservableList<String> contactName = FXCollections.observableArrayList();
    /**
     * Instance of Integer Observable list
     */
    ObservableList<Integer> usersID = FXCollections.observableArrayList();
    /**
     * Instance of String
     */
    String loggedInUser = null;

    /**
     * Grabs the information from mainScreenController and populates the screen with all the information
     * Sets the information with the information from the previous screen
     * @param appointments appointment selected
     * @param username username of the logged in user
     */
    @FXML public void appointmentToModify(Appointments appointments, String username ){
        loggedInUser = username;
        appointmentIDTextField.setDisable(true);
        appointmentCustomerIDComboBox.setValue(appointments.getCustomerID());
        appointmentIDTextField.setText(String.valueOf(appointments.getAppointmentID()));
        appointmentTitleTextField.setText(appointments.getAppointmentTitle());
        appointmentDescriptionTextField.setText(appointments.getAppointmentDescription());
        appointmentLocationTextField.setText(appointments.getAppointmentLocation());
        appointmentContactComboBox.setValue(String.valueOf(appointments.getContactID()));
        appointmentTypeTextField.setText(appointments.getAppointmentType());
        appointmentUserIDComboBox.setValue(appointments.getUserID());
        appointmentStartDateComboBox.setValue(appointments.getAppointmentStartDate());
        appointmentEndDateComboBox.setValue(appointments.getAppointmentEndDate());
        startTimeHourComboBox.setValue(appointments.getAppointmentStartTime().getHour());
        startTimeMinuteComboBox.setValue(appointments.getAppointmentStartTime().getMinute());
        endTimeHourComboBox.setValue(appointments.getAppointmentEndTime().getHour());
        endTimeMinuteComboBox.setValue(appointments.getAppointmentEndTime().getMinute());
    }
    /**
     * Initialize the controller and populates all the ComboBoxes
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *  #1
     *       Runs the updateAppointment method when the save button is pushed.
     *  #2
     *       Runs the screenCancel method when the cancelButton is pushed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (Customers customers : customersObservableList){
            customersID.add(customers.getCustomerID());
        }
        for (Contacts contacts : contactsObservableList){
            contactName.add(contacts.getContactName());
        }
        for (Users users : usersObservableList){
            usersID.add(users.getUserID());
        }
        appointmentCustomerIDComboBox.setItems(customersID);
        appointmentContactComboBox.setItems(contactName);
        appointmentUserIDComboBox.setItems(usersID);

        startTimeHourComboBox.getItems().setAll(IntStream.rangeClosed(0,23).boxed().collect(Collectors.toList()));
        endTimeHourComboBox.getItems().setAll(IntStream.rangeClosed(0,23).boxed().collect(Collectors.toList()));
        startTimeMinuteComboBox.getItems().setAll(IntStream.rangeClosed(0,59).boxed().collect(Collectors.toList()));
        endTimeMinuteComboBox.getItems().setAll(IntStream.rangeClosed(0,59).boxed().collect(Collectors.toList()));

        /*
        LAMBDA #1
         */
        saveButton.setOnAction(e-> {
            updateAppointment();
        });
        /*
        LAMBDA #2
         */
        cancelButton.setOnAction(e-> screenCancel());
    }
    /**
     * Updates the appointment in the SQL database
     * Validates that the appointment is valid and that all the information is valid and correct
     */
    public void updateAppointment() {

        try {

            int startYear = appointmentStartDateComboBox.getValue().getYear();
            int startMonth = appointmentStartDateComboBox.getValue().getMonthValue();
            int startDay = appointmentStartDateComboBox.getValue().getDayOfMonth();
            int startHour = startTimeHourComboBox.getValue();
            int startMin = startTimeMinuteComboBox.getValue();
            LocalDateTime localDateTimeStart = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);

            int endYear = appointmentEndDateComboBox.getValue().getYear();
            int endMonth = appointmentEndDateComboBox.getValue().getMonthValue();
            int endDay = appointmentEndDateComboBox.getValue().getDayOfMonth();
            int endHour = endTimeHourComboBox.getValue();
            int endMin = endTimeMinuteComboBox.getValue();
            LocalDateTime localDateTimeEnd = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);

            LocalDateTime appointmentStartChecker;
            LocalDateTime appointmentEndChecker;

            if (localDateTimeStart.isAfter(localDateTimeEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The start time is after the appointment ends");
                alert.showAndWait();
                return;
            }

            ZonedDateTime localStart = ZonedDateTime.of(localDateTimeStart, ZoneId.systemDefault());
            ZonedDateTime localEnd = ZonedDateTime.of(localDateTimeEnd, ZoneId.systemDefault());

            ZonedDateTime startChecker = localStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endChecker = localEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

            ZonedDateTime businessStartHour = ZonedDateTime.of(LocalDate.of(startYear, startMonth, startDay), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
            ZonedDateTime businessEndHour = ZonedDateTime.of(LocalDate.of(endYear, endMonth, endDay), LocalTime.of(22, 0), ZoneId.of("America/New_York"));


            if (startChecker.isBefore(businessStartHour)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Start time is before business hours");
                alert.showAndWait();
                return;
            }
            if (endChecker.isAfter(businessEndHour)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("End time is after business hours");
                alert.showAndWait();
                return;
            }

            for (Appointments appointments : appointmentsObservableList) {
                if ((appointmentCustomerIDComboBox.getValue() == appointments.getCustomerID()) &&
                        (appointments.getAppointmentID() != Integer.parseInt(appointmentIDTextField.getText()))){
                    appointmentStartChecker = appointments.getAppointmentStartTime().atDate(appointments.getAppointmentStartDate());
                    appointmentEndChecker = appointments.getAppointmentEndTime().atDate(appointments.getAppointmentEndDate());
                    if ((appointmentStartChecker.isAfter(localDateTimeStart) && appointmentStartChecker.isBefore(localDateTimeEnd))
                            || (appointmentStartChecker.isEqual((localDateTimeEnd)) ||
                            (appointmentEndChecker.isAfter(localDateTimeStart) && appointmentEndChecker.isBefore(localDateTimeEnd))
                    || (appointmentEndChecker.isEqual(localDateTimeStart))) || (appointmentStartChecker.isEqual(localDateTimeStart))
                    || (appointmentEndChecker.isEqual(localDateTimeEnd))){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Customer already has an appointment during this time!");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            int appointmentID = Integer.parseInt(appointmentIDTextField.getText());
            String appointmentTitle = appointmentTitleTextField.getText();
            if (appointmentTitleTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The title is empty.\n Please fill in with a title");
                alert.showAndWait();
                return;
            }
            String appointmentDescription = appointmentDescriptionTextField.getText();
            if (appointmentDescriptionTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The description is empty.\n Please fill in with a description");
                alert.showAndWait();
                return;
            }
            String appointmentLocation = appointmentLocationTextField.getText();
            if (appointmentLocationTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The location is empty.\n Please fill in with a location");
                alert.showAndWait();
                return;
            }
            String appointmentType = appointmentTypeTextField.getText();
            if (appointmentTypeTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The appointment type is empty.\n Please fill in with an appointment type.");
                alert.showAndWait();
                return;
            }
            Timestamp appointmentStartTime = Timestamp.valueOf(localDateTimeStart.atOffset(ZoneOffset.UTC).toLocalDateTime());
            Timestamp appointmentEndDate = Timestamp.valueOf(localDateTimeEnd.atOffset(ZoneOffset.UTC).toLocalDateTime());
            Timestamp appointmentLastUpdateDate = Timestamp.valueOf(LocalDateTime.now());
            String appointmentLastUpdatedBy = loggedInUser;
            int customerID = appointmentCustomerIDComboBox.getValue();
            int userID = appointmentUserIDComboBox.getValue();
            int appointmentContactID = 0;

            for (Contacts contacts : contactsObservableList) {
                if (contacts.getContactName().equals(appointmentContactComboBox.getValue())) {
                    appointmentContactID = contacts.getContactID();
                }
            }
            AppointmentDAO.updateAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStartTime, appointmentEndDate, appointmentLastUpdateDate, appointmentLastUpdatedBy,
                    customerID, userID, appointmentContactID);
            screenCancel();


        } catch (NullPointerException e) {
            System.out.println("Did not work");
        }
    }

    /**
     * Exits the modifyAppointment screen
     */
    public void screenCancel (){
        final Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
