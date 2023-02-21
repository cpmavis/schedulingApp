package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class for all the SQL statements relating to the FirstLevelDivisions
 */
public class FirstLevelDivisionsDAO {
    /**
     * Method to get all the first level divisions
     * @return Observable List of all the first level divisions
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "select * from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID,division,countryID);
                firstLevelDivisionsObservableList.add(firstLevelDivisions);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return firstLevelDivisionsObservableList;
    }
}
