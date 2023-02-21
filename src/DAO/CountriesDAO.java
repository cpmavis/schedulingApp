package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for all the SQL statements relating to the Countries
 */
public class CountriesDAO {
    /**
     * Method to get all the countries
     * @return Observable List of all the countries
     */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();
        try {
            String sql = "select * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Countries countries = new Countries(countryID,country);
                countriesObservableList.add(countries);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countriesObservableList;
    }
}
