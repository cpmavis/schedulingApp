package controller;

import DAO.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class that provides the logic to view all the appointments
 * Allows the user to update, add, or remove an appointment
 * Allows the user the ability to filter by week or month
 * @author Corey
 */
public class mainScreenController implements Initializable {
    /**
     * Radio button to view all appointments
     */
    @FXML
    private RadioButton viewAllButton;
    /**
     * Radio button to view by the week
     */
    @FXML
    private RadioButton viewByWeekRadioButton;
    /**
     * Radio button to view by the month
     */
    @FXML
    private RadioButton viewByMonthRadioButton;
    /**
     * Date Picker to pick a date
     */
    @FXML
    private DatePicker appointmentDatePicker;
    /**
     * Tableview for the appointments
     */
    @FXML
    private TableView<Appointments> appointmentTableView;
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
     * Column for the appointment Contact
     */
    @FXML
    private TableColumn<Appointments, String> apptContactColumn;
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
     * Button to add appointment
     */
    @FXML
    private Button addAppointmentButton;
    /**
     * Button to modify appointment
     */
    @FXML
    private Button modifyAppointmentButton;
    /**
     * Button to delete appointment
     */
    @FXML
    private Button deleteAppointmentButton;
    /**
     * Button to switch to reports
     */
    @FXML
    private Button reportAppointmentButton;
    /**
     * Button to exit screen
     */
    @FXML
    private Button exitAppointmentButton;
    /**
     * Button to switch to customers view
     */
    @FXML
    private Button viewCustomersButton;
    /**
     * Instance of a String
     */
    private String loggedInUser = null;
    /**
     * method to get the username of the logged in user
     * @param username username that was logged in
     */
    @FXML public void userLoggedInReceived (String username){
        this.loggedInUser = username;
    }
    /**
     * Initialize the controller and populates all the appointment fields
     * Initialize the buttons to run the methods when called
     * Initialize the ToggleGroup and sets the RadioButtons in the ToggleGroup
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDA
     *  #1
     *       Runs the customerView method when the viewCustomersButton is pushed.
     *  #2
     *       Runs the addAppointment method when the addAppointmentButton is pushed.
     *       Resets the TableView with the new ObservableList.
     *  #3
     *       Runs the modifyAppointment method when the modifyAppointmentButton is pushed.
     *       Resets the TableView with the new ObservableList.
     *  #4
     *       Runs the returnToLogin method when the exitAppointmentButton is pushed.
     *  #5
     *       Runs the deleteAppointment method when the deleteAppointmentButton is pushed.
     *       Resets the TableView with the new ObservableList.
     *  #6
     *       Runs the refreshAppointsTableView method when the viewAllButton is pushed.
     *  #7
     *       Runs the viewByWeek method when the viewByWeekRadioButton is pushed.
     *  #8
     *       Runs the viewByMonth method when the viewByMonthRadioButton is pushed.
     *  #9
     *       Clears all the RadioButtons from being selected whenever the appointmentDatePicker is clicked.
     *  #10
     *       Runs the reportView method when the reportAppointmentButton is pushed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointments = AppointmentDAO.getAllAppointments();

        appointmentTableView.setItems(appointments);
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        apptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentDatePicker.setValue(LocalDate.now());

        ToggleGroup tg = new ToggleGroup();
        viewByMonthRadioButton.setToggleGroup(tg);
        viewByWeekRadioButton.setToggleGroup(tg);
        viewAllButton.setToggleGroup(tg);

        /*
        LAMBDA #1
         */
        viewCustomersButton.setOnAction(e -> customersView());

        /*
        LAMBDA #2
         */
        addAppointmentButton.setOnAction(e -> {
            addAppointment();
            refreshAppointsTableView();
        });

        /*
        LAMBDA #3
         */
        modifyAppointmentButton.setOnAction(e -> {
            modifyAppointment();
            refreshAppointsTableView();
        });

        /*
        LAMBDA #4
         */
        exitAppointmentButton.setOnAction(e -> returnToLogin());

        /*
        LAMBDA #5
         */
        deleteAppointmentButton.setOnAction(e -> {
            deleteAppointment();
            refreshAppointsTableView();

        });

        /*
        LAMBDA $6
         */
        viewAllButton.setOnAction(e -> refreshAppointsTableView());

        /*
        LAMBDA #7
         */
        viewByWeekRadioButton.setOnAction(e -> viewByWeek());

        /*
        LAMBDA #8
         */
        viewByMonthRadioButton.setOnAction(e -> viewByMonth());

        /*
        LAMBDA #9
         */
        appointmentDatePicker.setOnMouseClicked(e-> {
            viewByMonthRadioButton.setSelected(false);
            viewByWeekRadioButton.setSelected(false);
            viewAllButton.setSelected(false);
        });

        /*
        LAMBDA #10
         */
        reportAppointmentButton.setOnAction(e-> reportView());

    }

    /**
     * Resets the appointmentTableView with the new observable List of appointments
     */
    public void refreshAppointsTableView() {
        ObservableList<Appointments> appointments = AppointmentDAO.getAllAppointments();
        appointmentTableView.setItems(appointments);
    }

    /**
     * Loads up the addAppointmentView stage and sends the information to the controller
     */

     public void addAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/addAppointmentView.fxml"));
            loader.load();

            addAppointmentController user = loader.getController();
            user.userLoggedIn(loggedInUser);

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Add Appointments");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to add an appointment!");
            alert.show();

        }
            }
    /**
     * Loads up the ModifyAppointmentView stage and sends the information to the controller
     */
    public void modifyAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointmentView.fxml"));
            loader.load();

            modifyAppointmentController MAC = loader.getController();
            MAC.appointmentToModify(appointmentTableView.getSelectionModel().getSelectedItem(), loggedInUser);

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Appointments");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an Appointment before you can modify it!");
            alert.show();

        }
    }
    /**
     * Loads up the mainScreenCustomerView stage and sends the information to the controller
     */
    public void customersView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainScreenCustomerView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            mainScreenCustomerController user = loader.getController();
            user.userLoggedIn(loggedInUser);
            Stage stage = (Stage) viewCustomersButton.getScene().getWindow();
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();


        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to view customers!");
            alert.show();
        }
    }
    /**
     * Loads up the loginScreenView stage and closes the Main Stage
     */
        public void returnToLogin () {
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("/view/loginScreenView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);
            Stage stage = (Stage) exitAppointmentButton.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Gets the selected appointment from the table and deletes it from the database
     * Verifies that an appointment is selected and confirms that you want to delete it
     */
    public void deleteAppointment() {
        Appointments appointments = appointmentTableView.getSelectionModel().getSelectedItem();
        try{
            int appointmentID = appointments.getAppointmentID();
            String appointmentType = appointments.getAppointmentType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete the appointment?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                AppointmentDAO.deleteAppointment(appointmentID);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText("Appointment Removed");
                alert1.setContentText("Appointment successfully removed" + "\nAppointment ID: " +
                        appointmentID + "\nAppointment Type: " + appointmentType);
                alert1.showAndWait();
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Must select an appointment");
            alert.showAndWait();
        }
    }
    /**
     * Filters the selected appointments by day selected and all appointments within a week from that day
     * Sets the TableView with the selected appointments
     */
    public void viewByWeek() {
        ObservableList<Appointments> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
        LocalDateTime day = appointmentDatePicker.getValue().atStartOfDay();
        LocalDateTime weekEnd = day.plusDays(7);
        LocalDateTime weekStart = day.minusDays(7);


        for (Appointments apt : appointments) {
            if (apt.getAppointmentStartDate().isAfter(weekStart.toLocalDate()) &&
                    apt.getAppointmentStartDate().isBefore(weekEnd.toLocalDate())) {
                filteredAppointments.add(apt);
                appointmentTableView.setItems(filteredAppointments);
            }
        }
        if (filteredAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No appointments found");
            alert.showAndWait();
        }
    }

    /**
     * Filters the selected appointments by month selected and all appointments in that month
     * Sets the TableView with the selected appointments
      */
    public void viewByMonth() {
        ObservableList<Appointments> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
        int month = appointmentDatePicker.getValue().getMonthValue();
        for (Appointments apt : appointments) {
            if (apt.getAppointmentStartDate().getMonthValue() == month) {
                filteredAppointments.add(apt);
                appointmentTableView.setItems(filteredAppointments);
            }
        }
        if (filteredAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No appointments found");
            alert.showAndWait();
        }
    }
    /**
     * Loads up the reportView stage and sends the information to the controller
     */
    public void reportView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/reportView.fxml"));
            loader.load();

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setTitle("Reports");
            stage.setScene(new Scene(scene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to view Reports");
            alert.show();

        }
    }
}