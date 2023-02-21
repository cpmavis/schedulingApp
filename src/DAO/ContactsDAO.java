package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class for all the SQL statements relating to the Contacts
 */
public class ContactsDAO {
    /**
     * Method to get all the contacts
     * @return Observable List of all the contacts
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "select * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName =  rs.getString("Contact_Name");
                Contacts contacts =  new Contacts(contactID,contactName);
                contactsObservableList.add(contacts);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsObservableList;
    }
}
