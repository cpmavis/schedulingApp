package controller;

import DAO.AppointmentDAO;
import DAO.CustomersDAO;
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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class that provides the logic to view all the customers
 * Allows the user to update, add, or remove a customer
 * @author Corey
 */
public class mainScreenCustomerController implements Initializable {
    /**
     * Button to switch to appointment view
     */
    @FXML
    private Button viewAppointmentsButton;
    /**
     * Tableview for the customers
     */
    @FXML
    private TableView<Customers> customerTableView;
    /**
     * Column for customer ID
     */
    @FXML
    private TableColumn<Customers, Integer> custIDColumn;
    /**
     * Column for customer Name
     */
    @FXML
    private TableColumn<Customers, String> custNameColumn;
    /**
     * Column for customer Address
     */
    @FXML
    private TableColumn<Customers, String> custAdressColumn;
    /**
     * Column for customer Postal Code
     */
    @FXML
    private TableColumn<Customers, String> custPostalCodeColumn;
    /**
     * Column for customer Phone Number
     */
    @FXML
    private TableColumn<Customers, String> custPhoneNumberColumn;
    /**
     * Column for customer Created Date
     */
    @FXML
    private TableColumn<Customers, LocalDateTime> custDateCreatedColumn;
    /**
     * Column for customer Created By
     */
    @FXML
    private TableColumn<Customers, String> custCreatedByColumn;
    /**
     * Column for customer Last Update Date
     */
    @FXML
    private TableColumn<Customers, LocalDateTime> custLastUpdateDateColumn;
    /**
     * Column for customer Last Updated By
     */
    @FXML
    private TableColumn<Customers, String> custLastUpdateByColumn;
    /**
     * Column for customer State/Province
     */
    @FXML
    private TableColumn<Customers, String> custStateProvinceColumn;
    /**
     * Button to add customer
     */
    @FXML
    private Button addCustomerButton;
    /**
     * Button to modify customer
     */
    @FXML
    private Button modifyCustomerButton;
    /**
     * Button to delete customer
     */
    @FXML
    private Button deleteCustomerButton;
    /**
     * Button to view reports
     */
    @FXML
    private Button reportAppointmentButton;
    /**
     * Button to exit the screen
     */
    @FXML
    private Button exitAppointmentButton;
    /**
     * Instance of a String
     */
    String loggedInUser = null;
    /**
     * method to get the username of the logged in user
     * @param username username that was logged in
     */
    public void userLoggedIn (String username){
        loggedInUser = username;
    }

    /**
     * Initialize the controller and populates all the customer fields
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *  #1
     *      Runs the mainScreen method when the viewAppointmentsButton is pushed.
     *  #2
     *      Runs the addCustomers method when the addCustomerButton is pushed.
     *      Resets the TableView with the new ObservableList.
     *  #3
     *      Runs the modifyCustomers method when the modifyCustomerButton is pushed.
     *      Resets the TableView with the new ObservableList.
     *  #4
     *      Runs the deleteCustomer method when the deleteCustomerButton is pushed.
     *      Resets the TableView with the new ObservableList.
     *  #5
     *      Runs the returnToLogin method when the exitAppointmentButton is pushed.
     *  #6
     *      Runs the reportView method when the reportAppointmentButton is pushed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> customersObservableList = CustomersDAO.getAllCustomers();
        customerTableView.setItems(customersObservableList);
        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAdressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        custDateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreatedDate"));
        custCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        custLastUpdateDateColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdateDate"));
        custLastUpdateByColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdateBy"));
        custStateProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("customerFirstLevelDivisionID"));

        /*
        LAMBDA #1
         */
        viewAppointmentsButton.setOnAction(e -> mainScreen());

        /*
        LAMBDA #2
         */
        addCustomerButton.setOnAction(e -> {
            addCustomers();
            refreshTableView();
        });

        /*
        LAMBDA #3
         */
        modifyCustomerButton.setOnAction(e -> {
            modifyCustomers();
            refreshTableView();
        });

        /*
        LAMBDA #4
         */
        deleteCustomerButton.setOnAction(e -> {
            deleteCustomer();
            refreshTableView();
        });

        /*
        LAMBDA #5
         */
        exitAppointmentButton.setOnAction(e -> returnToLogin());

        /*
        LAMBDA #6
         */
        reportAppointmentButton.setOnAction(e-> reportView());
    }
    /**
     * Resets the appointmentTableView with the new observable List of Customers
     */
    public void refreshTableView(){
        ObservableList<Customers> customersObservableList = CustomersDAO.getAllCustomers();
        customerTableView.setItems(customersObservableList);
    }
    /**
     * Loads up the mainScreenView stage and sends the information to the controller
     */
    public void mainScreen() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/view/mainScreenView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage) viewAppointmentsButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Loads up the addCustomerView stage and sends the information to the controller
     */
    public void addCustomers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/addCustomerView.fxml"));
            loader.load();

            addCustomerController user = loader.getController();
            user.userLoggedIn(loggedInUser);

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setTitle("Add Customers");
            stage.setScene(new Scene(scene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to add a customer!");
            alert.show();
        }
    }
    /**
     * Loads up the ModifyCustomerView stage and sends the information to the controller
     */
    public void modifyCustomers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomerView.fxml"));
            loader.load();

            modifyCustomerController MCC = loader.getController();
            MCC.customerToModify(customerTableView.getSelectionModel().getSelectedItem(), loggedInUser);

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Customers");
            stage.setScene(new Scene(scene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Customer before you can modify it!");
            alert.show();
        }
    }
    /**
     * Gets the selected customer from the table and deletes it from the database
     * Verifies that a customer is selected and confirms that you want to delete it
     */
    public void deleteCustomer () {
        ObservableList<Appointments> appointmentsObservableList = AppointmentDAO.getAllAppointments();
        Customers customers = customerTableView.getSelectionModel().getSelectedItem();
        Boolean hasAppointment = true;

        try {
            int customerID = customers.getCustomerID();
            aa:
            for (Appointments appointments : appointmentsObservableList) {
                if (customerID == appointments.getCustomerID()) {
                    hasAppointment = true;
                    break aa;
                } else {
                    hasAppointment = false;
                }
            }
            if (hasAppointment) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Customer " + customerID + " has an appointment scheduled" +
                        "\nDelete the appointment first to remove the customer.");
                alert.showAndWait();
            } else if (!hasAppointment) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure that you want to remove the customer?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    CustomersDAO.deleteCustomer(customerID);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("Customer Removed");
                    alert1.setContentText("Customer " + customerID + " has been removed");
                    alert1.showAndWait();
                }
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Must select a Customer");
            alert.showAndWait();
        }
    }
    /**
     * Loads up the loginScreenView stage and closes the Main Stage
     */
    public void returnToLogin() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/view/loginScreenView.fxml"));
        } catch (IOException e)
        {        e.printStackTrace();    }
        Scene scene = new Scene(parent);
        Stage stage = (Stage) exitAppointmentButton.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Loads up the reportViewCustomer stage and sends the information to the controller
     */
    public void reportView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/reportViewCustomer.fxml"));
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
