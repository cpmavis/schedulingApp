package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class for all the SQL statements relating to the Users
 */
public class UsersDAO {
    /**
     * Method to get all the users
     * @return Observable List of the users
     */
    public static ObservableList<Users> getAllUser(){
        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

        try {
            String sql = "select * from users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users users =  new Users(userID,userName,userPassword);
                usersObservableList.add(users);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersObservableList;
    }

    /**
     * Method to check if the user is valid
     * @param username username of the user
     * @param password password of the user
     * @return boolean value if the user was valid or not
     */
    public static boolean validUser (String username, String password){
        try {
            String sql = "select * from users where User_Name = ? and Password = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                return false;
            };

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            return true;
    }
}
