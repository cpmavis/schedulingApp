package controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class that provides the logic to view the contact reports
 * Allows the user to view customers by the name of each contact
 * @author Corey
 */
public class reportViewController  implements Initializable {
    /**
     * Button to view Customer Reports
     */
    @FXML
    private Button customerReportButton;
    /**
     * ComboBox with the search criteria
     */
    @FXML
    private ComboBox<String> reportSearchComboBox;
    /**
     * Button to exit the screen
     */
    @FXML
    private Button exitButton;
    /**
     * TableView of appointments
     */
    @FXML
    private TableView<Appointments> reportTableView;
    /**
     * Column for the appointment ID
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIDColumn;
    /**
     * Column for the appointment Title
     */
    @FXML
    private TableColumn<Appointments, String> apptTitleColumn;
    /**
     * Column for the appointment Description
     */
    @FXML
    private TableColumn<Appointments, String> apptDescriptionColumn;
    /**
     * Column for the appointment Location
     */
    @FXML
    private TableColumn<Appointments, String> apptLocationColumn;

    /**
     * Column for the appointment Type
     */
    @FXML
    private TableColumn<Appointments, String> apptTypeColumn;
    /**
     * Column for the appointment Start Date
     */
    @FXML
    private TableColumn<Appointments, Date> apptStartDateColumn;
    /**
     * Column for the appointment End Date
     */
    @FXML
    private TableColumn<Appointments, Date> apptEndDateColumn;
    /**
     * Column for the appointment Start Time
     */
    @FXML
    private TableColumn<Appointments, Time> apptStartTimeColumn;
    /**
     * Column for the appointment End Time
     */
    @FXML
    private TableColumn<Appointments, Time> apptEndTimeColumn;
    /**
     * Column for the appointment Customer ID
     */
    @FXML
    private TableColumn<Appointments, Integer> apptCustomerIDColumn;
    /**
     * Column for the appointment User ID
     */
    @FXML
    private TableColumn<Appointments, Integer> apptUserIDColumn;
    /**
     * Instance of Observable List of Appointments
     */
    ObservableList<Appointments> appointmentsObservableList = AppointmentDAO.getAllAppointments();
    /**
     * Instance of Observable List of Contacts
     */
    ObservableList<Contacts> contactsObservableList = ContactsDAO.getAllContacts();
    /**
     * Instance of Observable List of Strings
     */
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    /**
     * Initialize the controller and populates all the customer fields
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *  #1
     *       Runs the appointmentSearch method when reportSearchComboBox is clicked.
     *  #2
     *       Runs the customerReport method when the customerReportButton is clicked.
     *  #3
     *       Runs the exitScreen method when the exitButton is clicked.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        for(Contacts contacts: contactsObservableList){
            contactNames.add(contacts.getContactName());
        }
        reportSearchComboBox.setItems(contactNames);

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        apptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        /*
        LAMBDA #1
         */
        reportSearchComboBox.setOnAction(e-> appointmentSearch());

        /*
        LAMBDA #2
         */
        customerReportButton.setOnAction(e-> customerReport());

        /*
        LAMBDA #3
         */
        exitButton.setOnAction(e-> exitScreen());
    }

    /**
     * Gets the string value from the ComboBox and searches for all appointments with that name as the contact
     */
    public void appointmentSearch(){
        try {
            String contactName = reportSearchComboBox.getValue();
            ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
            for (Appointments appointments: appointmentsObservableList){
                if (appointments.getContactID().equals(contactName)){
                    filteredAppointments.add(appointments);
                }
            }
            reportTableView.setItems(filteredAppointments);
            if (filteredAppointments.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No appointments found");
                alert.showAndWait();
            }
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Must select a contact");
            alert.showAndWait();
        }

    }
    /**
     * Loads up the reportViewCustomer stage and sends the information to the controller
     */
    public void customerReport (){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/reportViewCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage  = (Stage) customerReportButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        } catch (NullPointerException | IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to view customers.");
            alert.show();

        }
    }

    /**
     * Exits the screen
     */
    public void exitScreen(){
        final Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
