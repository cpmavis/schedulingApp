package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class for all the SQL statements relating to the Reports
 */
public class ReportsDAO {
    /**
     * Method to get all the appointment types
     * @return Observable List of all the appointments
     */
    public static ObservableList<String> getAllAppointmentTypes() {
        ObservableList<String> appointmentType = FXCollections.observableArrayList();
        try {
            String sql = "select distinct type from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                appointmentType.add(type);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentType;
    }

    /**
     * Method to get all customers by type of appointment
     * @param type type of appointment
     * @return Observable List of all customers
     */
    public static ObservableList<Customers> getCustomersByType(String type) {
        ObservableList<Customers> customerType = FXCollections.observableArrayList();
        try {
            String sql = "select customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code," +
                    " customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, " +
                    "customers.Last_Updated_By, first_level_divisions.Division from customers inner join " +
                    "first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID inner join " +
                    "appointments on appointments.Customer_ID = customers.Customer_ID where type = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                Customers customers = new Customers(customerID, customerName, customerAddress, customerPostalCode,
                        customerPhoneNumber, customerCreatedDate, customerCreatedBy, customerLastUpdateDate,
                        customerLastUpdateBy, customerFirstLevelDivisionID);
                customerType.add(customers);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerType;
    }

    /**
     * Method to get all customers by country
     * @param country country search
     * @return Observable List of customers
     */
    public static ObservableList<Customers> getCustomersByCountry(String country) {
        ObservableList<Customers> customerCountry = FXCollections.observableArrayList();
        try {
            String sql = "select customers.Customer_ID, customers.Customer_Name, customers.Address, " +
                    "customers.Postal_Code, customers.Phone, customers.Create_Date, customers.Created_By, " +
                    "customers.Last_Update, customers.Last_Updated_By, first_level_divisions.Division from " +
                    "customers inner join first_level_divisions on customers.Division_ID = " +
                    "first_level_divisions.Division_ID inner join countries on countries.Country_ID " +
                    "= first_level_divisions.Country_ID where Country = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                Customers customers = new Customers(customerID, customerName, customerAddress, customerPostalCode,
                        customerPhoneNumber, customerCreatedDate, customerCreatedBy, customerLastUpdateDate,
                        customerLastUpdateBy, customerFirstLevelDivisionID);
                customerCountry.add(customers);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerCountry;
    }
}