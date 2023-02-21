package controller;

import DAO.AppointmentDAO;
import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.ReportsDAO;
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
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Controller class that provides the logic to view all the customer reports
 * Allows the user to view customers by Type, Month, or Country
 * Gets a count of all the  by each criteria
 * @author Corey
 */
public class reportViewCustomerController implements Initializable {
    /**
     * Label to count the number of customers
     */
    @FXML
    private Label customerCountLabel;
    /**
     * Button to switch to contact reports
     */
    @FXML
    private Button contactReportButton;
    /**
     * Radio Button for customers by type
     */
    @FXML
    private RadioButton customerByTypeRB;
    /**
     * Radio Button for customers by Month
     */
    @FXML
    private RadioButton customersByMonthRB;
    /**
     * Radio Button for Customers by Country
     */
    @FXML
    private RadioButton customReportRB;
    /**
     * Label to change the report search criteria
     */
    @FXML
    private Label reportSearchLabel;
    /**
     * Combo Box for search criteria
     */
    @FXML
    private ComboBox<String> reportSearchComboBox;
    /**
     * Button to exit the screen
     */
    @FXML
    private Button exitButton;
    /**
     * Table View for Customers
     */
    @FXML
    private TableView<Customers> reportTableView;
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
     * Instance of Observable List of Appointments
     */
    ObservableList<Appointments> appointmentsObservableList = AppointmentDAO.getAllAppointments();
    /**
     * Instance of Observable List of Customers
     */
    ObservableList<Customers> customersObservableList = CustomersDAO.getAllCustomers();
    /**
     * Instance of Observable List of Countries
     */
    ObservableList<Countries> countriesObservableList = CountriesDAO.getAllCountries();
    /**
     * Instance of Observable List of String
     */
    ObservableList<String> countryNames = FXCollections.observableArrayList();

    /**
     * Initialize the controller and populates all the customer fields
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *  #1
     *       Resets the customer count label to a null value.
     *       Sets the search label to show the Appointment Type.
     *       Sets the ComboBox to the appointment types.
     *       Runs the viewByType method.
     *  #2
     *       Resets the customer count label to a null value.
     *       Sets the search label to show Months.
     *       Creates an Observable List of all the months.
     *       Sets the ComboBox to the Observable List of months.
     *       Runs the viewByMonth method.
     *  #3
     *       Resets the customer count label to a null value.
     *       Sets the search label to show Country.
     *       Sets the ComboBox to the Observable List of country names.
     *       Runs the viewByCountry method.
     *  #4
     *       Runs the viewContact method when contactReportButton is pressed.
     *  #5
     *       Runs the returnToScreen when the exitButton is pressed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        ToggleGroup tg = new ToggleGroup();
        customerByTypeRB.setToggleGroup(tg);
        customersByMonthRB.setToggleGroup(tg);
        customReportRB.setToggleGroup(tg);


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

        for (Countries countries: countriesObservableList){
            countryNames.add(countries.getCountry());
        }

        /*
        LAMBDA #1
        */
        customerByTypeRB.setOnAction(e-> {
            customerCountLabel.setText(null);
            reportSearchLabel.setText("Appointment Type");
            ObservableList<String> appointmentType = ReportsDAO.getAllAppointmentTypes();
            reportSearchComboBox.setItems(appointmentType);
            viewByType();
        });

        /*
        LAMBDA #2
         */
        customersByMonthRB.setOnAction(e->  {
            customerCountLabel.setText(null);
            reportSearchLabel.setText("Month");
            ObservableList<String> months = FXCollections.observableArrayList();
            months.addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
            reportSearchComboBox.setItems(months);
            viewByMonth();
        });

        /*
        LAMBDA #3
         */
        customReportRB.setOnAction(e-> {
            customerCountLabel.setText(null);
            reportSearchLabel.setText("Country");
            reportSearchComboBox.setItems(countryNames);
            viewByCountry();
        });

         /*
        LAMBDA #4
         */
        contactReportButton.setOnAction(e-> viewContact());

         /*
        LAMBDA #5
         */
        exitButton.setOnAction(e-> returnToScreen());
    }

    /**
     * Returns an Observable List of Customers by Type
     * LAMBDA
     *  #6
     *          Does each on every new value.
     *          Gets the value from the reportSearchComboBox.
     *          Gets an Observable List from the getCustomersByType method.
     *          Sets the TableView with the Observable List.
     *          Gets the count and sets the customerCountLabel.
     */
    public void viewByType(){
        /*
        LAMBDA #6
         */
        reportSearchComboBox.setOnAction(e->{
        try {
            String type= reportSearchComboBox.getValue();
            ObservableList<Customers> customers = ReportsDAO.getCustomersByType(type);
            reportTableView.setItems(customers);
            customerCountLabel.setText(String.valueOf(customers.size()));
        } catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Select a type to search");
            alert.showAndWait();
        }
    });
    }

    /**
     * Returns an Observable List of Customers by Month
     * LAMBDA
     *  #7
     *          Does each on every new value.
     *          Gets the value from the reportSearchComboBox.
     *          Looks for a matching month value in the appointment Observable List.
     *          Gets the customer ID with the same month value.
     *          Add customer who has that ID to a Customer Observable List.
     *          Sets the Table View with that Observable List.
     */
    public void viewByMonth() {
        /*
        LAMBDA #7
         */
        reportSearchComboBox.setOnAction(e-> {
            try {
                ObservableList<Customers> filteredCustomers = FXCollections.observableArrayList();
                String country = reportSearchComboBox.getValue();
                for (Appointments appointments: appointmentsObservableList){
                    int customerID = 0;
                    if (appointments.getAppointmentStartDate().getMonth().toString().equals(country)){
                        customerID = appointments.getCustomerID();
                        for (Customers customers :customersObservableList){
                            if (customers.getCustomerID() == customerID){
                                filteredCustomers.add(customers);
                            }
                        }
                    }
                }
                customerCountLabel.setText(String.valueOf(filteredCustomers.size()));
                reportTableView.setItems(filteredCustomers);
            } catch (NullPointerException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Select a country to search");
                alert.showAndWait();
            }
        });
        }

    /**
     * Returns an Observable List of Customers by Country
     * LAMBDA
     *  #8
     *          Does each on every new value.
     *          Gets the value from the reportSearchComboBox.
     *          Gets an Observable List from the getCustomersByCountry method.
     *          Sets the TableView with the Observable List.
     *          Gets the count and sets the customerCountLabel.
     */
    public void viewByCountry(){
        /*
        LAMBDA #8
         */
        reportSearchComboBox.setOnAction(e->{
            try {
                String country = reportSearchComboBox.getValue();
                ObservableList<Customers> customers = ReportsDAO.getCustomersByCountry(country);
                reportTableView.setItems(customers);
                customerCountLabel.setText(String.valueOf(customers.size()));
            } catch (NullPointerException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Select a country to search");
                alert.showAndWait();
            }
        });
    }

    /**
     * Loads up the reportView stage and sends the information to the controller
     */
    public void viewContact(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/reportView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage  = (Stage) contactReportButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to view contact.");
            alert.show();

        }
    }

    /**
     * Exits the screen
     */
    public void returnToScreen(){
        final Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
