package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for all the SQL statements relating to the Customers
 */
public class CustomersDAO {
    /**
     * Method to get all the customers
     * @return Observable List of all the customers
     */
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
        try {
            //String sql = "select * from customers";
            String sql = "select customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By, first_level_divisions.Division from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                LocalDateTime customerCreatedDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String customerCreatedBy = rs.getString("Created_By");
                LocalDateTime customerLastUpdateDate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String customerLastUpdateBy = rs.getString("Last_Updated_By");
                String customerFirstLevelDivisionID = rs.getString("Division");
                Customers customers = new Customers(customerID,customerName,customerAddress,customerPostalCode,
                        customerPhoneNumber,customerCreatedDate,customerCreatedBy,customerLastUpdateDate,
                        customerLastUpdateBy,customerFirstLevelDivisionID);
                customersObservableList.add(customers);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customersObservableList;
    }

    /**
     * Method to add customers to the database
     * @param customerName name of customer
     * @param customerAddress address of the customer
     * @param customerPostalCode postal code of the customer
     * @param customerPhoneNumber phone number of the customer
     * @param customerCreatedDate when customer was created
     * @param customerCreatedBy who created the customer
     * @param customerLastUpdateDate when customer was last updated
     * @param customerLastUpdateBy who last updated the customer
     * @param customerFirstLevelDivisionID state/province of the customer
     */
    public static void addCustomer (String customerName, String customerAddress,
                                    String customerPostalCode, String customerPhoneNumber,
                                    Timestamp customerCreatedDate, String customerCreatedBy,
                                    Timestamp customerLastUpdateDate, String customerLastUpdateBy,
                                    int customerFirstLevelDivisionID) {
        try {
            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhoneNumber);
            ps.setTimestamp(5, customerCreatedDate);
            ps.setString(6, customerCreatedBy);
            ps.setTimestamp(7, customerLastUpdateDate);
            ps.setString(8, customerLastUpdateBy);
            ps.setInt(9, customerFirstLevelDivisionID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to delete the customer from the database
     * @param customerID ID of the customer
     */
    public static void deleteCustomer(int customerID) {
        try {
            String sql = "delete from customers where Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to update the customer in the database
     * @param customerID ID of the customer
     * @param customerName name of the customer
     * @param customerAddress address of the customer
     * @param customerPostalCode postal code of the customer
     * @param customerPhoneNumber phone number of the customer
     * @param customerLastUpdateDate when customer was last updated
     * @param customerLastUpdateBy who last updated the customer
     * @param customerFirstLevelDivisionID state/province of the customer
     */
    public static void updateCustomer(int customerID, String customerName, String customerAddress,
                                      String customerPostalCode, String customerPhoneNumber,
                                      Timestamp customerLastUpdateDate, String customerLastUpdateBy,
                                      int customerFirstLevelDivisionID){
        try {
            String sql = "update customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update= ?, Last_Updated_By = ?, Division_ID = ? where Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhoneNumber);
            ps.setTimestamp(5, customerLastUpdateDate);
            ps.setString(6, customerLastUpdateBy);
            ps.setInt(7, customerFirstLevelDivisionID);
            ps.setInt(8, customerID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
