package controller;

import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.FirstLevelDivisionsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/**
 * Controller class that provides the logic to modify a customer
 * Allows the user to add a new customer and checks that the customer is valid
 * @author Corey
 */

public class modifyCustomerController implements Initializable {
    /**
     * ComboBox with Country Names
     */
    @FXML private ComboBox<String> countryComboBox;
    /**
     * ComboBox with State/Province Names
     */
    @FXML private ComboBox<String> stateComboBox;
    /**
     * Button to save customer information
     */
    @FXML private Button saveButton;
    /**
     * Button to exit the screen
     */
    @FXML private Button cancelButton;
    /**
     * Label to change state to province and province to state
     */
    @FXML private Label stateLabel;
    /**
     * TextField with customer ID
     */
    @FXML private TextField customerIDTextField;
    /**
     * TextField with customer Name
     */
    @FXML private TextField customerNameTextField;
    /**
     * TextField with customer Address
     */
    @FXML private TextField customerAddressTextField;
    /**
     * TextField with customer PostalCode
     */
    @FXML private TextField customerPostalCodeTextField;
    /**
     * TextField with customer Phone Number
     */
    @FXML private TextField customerPhoneNumberTextField;

    /**
     * Instance of Integer
     */
    int stateID = 0;
    /**
     * Instance of Observable List of FirstLevelDivisions
     */
    ObservableList<FirstLevelDivisions> firstLevelDivisionsObservableList = FirstLevelDivisionsDAO.getAllFirstLevelDivisions();
    /**
     *  Instance of Observable List of Countries
     */
    ObservableList<Countries> countriesObservableList = CountriesDAO.getAllCountries();
    /**
     * Instance of Observable List of Strings
     */
    ObservableList<String> countryNames = FXCollections.observableArrayList();
    /**
     * Instance of Observable List of Strings
     */
    ObservableList<String> stateNames = FXCollections.observableArrayList();
    /**
     * Instance of String
     */
    String loggedInUser = null;

    /**
     * Grabs the information from mainScreenCustomerController and populates the screen with all the information
     * Sets the information with the information from the previous screen
     * @param customers customer selected
     * @param username username of the logged in user
     */

    @FXML public void customerToModify(Customers customers, String username) {

        loggedInUser = username;
        customerIDTextField.setDisable(true);
        customerIDTextField.setText(String.valueOf(customers.getCustomerID()));
        customerNameTextField.setText(customers.getCustomerName());
        customerAddressTextField.setText(customers.getCustomerAddress());
        customerPostalCodeTextField.setText(customers.getCustomerPostalCode());
        customerPhoneNumberTextField.setText(customers.getCustomerPhoneNumber());
        stateComboBox.setValue(customers.getCustomerFirstLevelDivisionID());

        for (Countries cnt : countriesObservableList) {
            countryNames.add(cnt.getCountry());
        }
        for (FirstLevelDivisions fld : firstLevelDivisionsObservableList) {
            if (customers.getCustomerFirstLevelDivisionID().equals(fld.getDivision())) {
                stateID = fld.getCountryID();
                for (Countries countries : countriesObservableList) {
                    if (countries.getCountryID() == stateID) {
                        countryComboBox.setValue(countries.getCountry());
                    }
                }
            }
        }
        for (FirstLevelDivisions fld : firstLevelDivisionsObservableList) {
            if (fld.getCountryID() == stateID) {
                stateNames.add(fld.getDivision());
            }
        }
        stateComboBox.setItems(stateNames);
        countryComboBox.setItems(countryNames);
    }
    /**
     * Initialize the controller and populates all the ComboBoxes
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *
     *  #1
     *      Clears the stateName ComboBox when called.
     *      Sets the appropriate name to the stateLabel depending on the country.
     *      Populates the stateName ComboBox with the appropriate states/provinces.
     *      Populates the stateName ComboBox with the appropriate Country based on the state/province of the customer.
     *  #2
     *      Runs the saveCustomer method when the saveButton is pushed.
     *  #3
     *      Runs the screenCancel method when the cancelButton is pushed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        LAMBDA #1
         */
        countryComboBox.setOnAction(e->{
            stateNames.clear();
            for (Countries countries : countriesObservableList){
                if(countries.getCountry().equals(countryComboBox.getValue())){
                    stateID = countries.getCountryID();
                    if(stateID == 3){
                        stateLabel.setText("Province");
                    }
                    else {
                        stateLabel.setText("State");
                    }
                }
            }
            for (FirstLevelDivisions fld : firstLevelDivisionsObservableList) {
                if (fld.getCountryID() == stateID) {
                    stateNames.add(fld.getDivision());
                }
            }
            stateComboBox.setItems(stateNames);
        });
        /*
        LAMBDA #2
         */
        saveButton.setOnAction(e-> {
            updateCustomers();
        });
        /*
        LAMBDA #3
         */
        cancelButton.setOnAction(e-> screenCancel());
    }
    /**
     * Updates the customer in the SQL database
     * Validates that the customer is valid and that all the information is valid and correct
     */
    public void updateCustomers() {
        try {
            int customerID = Integer.parseInt(customerIDTextField.getText());
            String customerName = customerNameTextField.getText();
            if (customerNameTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The customer name is empty.\n Please add a name.");
                alert.showAndWait();
                return;
            }
            String customerAddress = customerAddressTextField.getText();
            if (customerAddressTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The customer address is empty.\n Please add an address.");
                alert.showAndWait();
                return;
            }
            String customerPostalCode = customerPostalCodeTextField.getText();
            if (customerPostalCodeTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The postal code is empty.\n Please add a postal code.");
                alert.showAndWait();
                return;
            }
            String customerPhoneNumber = customerPhoneNumberTextField.getText();
            if (customerPhoneNumberTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The phone number is empty.\n Please add a phone number.");
                alert.showAndWait();
                return;
            }
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedBy = loggedInUser;
            int divisionID = 0;

            for (FirstLevelDivisions firstLevelDivisions : firstLevelDivisionsObservableList) {
                if (firstLevelDivisions.getDivision().equals(stateComboBox.getValue())) {
                    divisionID = firstLevelDivisions.getDivisionID();
                }
            }
            CustomersDAO.updateCustomer(customerID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, lastUpdate, lastUpdatedBy, divisionID);
            screenCancel();
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please make sure that all the fields are filled");
            alert.showAndWait();
        }
    }
    /**
     * Exits the addCustomer screen
     */
    public void screenCancel (){
        final Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
