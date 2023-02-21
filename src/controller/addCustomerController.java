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
import model.FirstLevelDivisions;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/**
 * Controller class that provides the logic to add a customer
 * Allows the user to add a new customer and checks that the customer is valid
 * @author Corey
 */

public class addCustomerController implements Initializable {
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
     * method to get the username of the logged in user
     * @param username username that was logged in
     */
    public void userLoggedIn (String username){
        loggedInUser = username;
    }
    /**
     * Initialize the controller and populates all the ComboBoxes
     * Initialize the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     *
     * LAMBDAS
     *  #1
     *       Clears the stateName ComboBox when called.
     *       Sets the appropriate name to the stateLabel depending on the country.
     *       Populates the stateName ComboBox with the appropriate states/provinces.
     *       Populates the stateName ComboBox with the appropriate Country based on the state/province of the customer.
     *  #2
     *       Runs the saveCustomer method when the saveButton is pushed.
     *  #3
     *       Runs the screenCancel method when the cancelButton is pushed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDTextField.setText("Auto-Generated");
        customerIDTextField.setDisable(true);
        for (Countries countries : countriesObservableList){
            countryNames.add(countries.getCountry());
        }
        countryComboBox.setItems(countryNames);
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
            saveCustomer();
        });
        /*
        LAMBDA #3
         */
        cancelButton.setOnAction(e-> screenCancel());

    }
    /**
     * Saves the customer to the SQL database
     * Validates that the customer is valid and that all the information is valid and correct
     */

    public void saveCustomer() {
        try {
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
            Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = loggedInUser;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedBy = loggedInUser;
            int divisionID = 0;

            if (countryComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No country selected.\n Please select a country.");
                alert.showAndWait();
                return;
            }
            if (stateComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No state/province selected.\n Please select a state/province.");
                alert.showAndWait();
                return;
            }
                for (FirstLevelDivisions firstLevelDivisions : firstLevelDivisionsObservableList) {
                    if (firstLevelDivisions.getDivision().equals(stateComboBox.getValue())) {
                        divisionID = firstLevelDivisions.getDivisionID();
                    }
                }
                CustomersDAO.addCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber,
                        createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            screenCancel();
            } catch(NullPointerException e){
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
