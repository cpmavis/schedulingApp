package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class for all the SQL statements relating to the appointments
 */
public class AppointmentDAO {
    /**
     * Method to get all the appointments
     * @return Observable list of appointments
     */
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointmentsList= FXCollections.observableArrayList();

        try{

            String sql = "select appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, contacts.Contact_Name from appointments inner join contacts on appointments.Contact_ID= contacts.Contact_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDate appointmentStartDate = rs.getTimestamp("Start").toLocalDateTime().toLocalDate();
                LocalTime appointmentStartTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalDate appointmentEndDate = rs.getTimestamp("End").toLocalDateTime().toLocalDate();
                LocalTime appointmentEndTime =  rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                String contactID = rs.getString("Contact_Name");

                Appointments appointments = new Appointments(appointmentID,appointmentTitle,appointmentDescription,
                        appointmentLocation,appointmentType,appointmentStartDate,appointmentStartTime,
                        appointmentEndDate,appointmentEndTime,customerID,userID,contactID);
                appointmentsList.add(appointments);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Add an appointment to the database
     * @param appointmentTitle title of the appointment
     * @param appointmentDescription description of the appointment
     * @param appointmentLocation location of the appointment
     * @param appointmentType type of the appointment
     * @param appointmentStart Start of the appointment
     * @param appointmentEnd End of the appointment
     * @param appointmentCreateDate created date of the appointment
     * @param appointmentCreatedBy person who created the appointment
     * @param appointmentLastUpdate person who last updated the appointment
     * @param appointmentLastUpdatedBy appointed last updated by
     * @param customerID customerID of the appointment
     * @param userID userID of the customer
     * @param contactID contactID for the appointment
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation,
                                      String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd,
                                      Timestamp appointmentCreateDate, String appointmentCreatedBy,
                                      Timestamp appointmentLastUpdate, String appointmentLastUpdatedBy,
                                      int customerID, int userID, int contactID){
        try {
            String sql = "insert into appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, appointmentStart);
            ps.setTimestamp(6, appointmentEnd);
            ps.setTimestamp(7, appointmentCreateDate);
            ps.setString(8, appointmentCreatedBy);
            ps.setTimestamp(9, appointmentLastUpdate);
            ps.setString(10, appointmentLastUpdatedBy);
            ps.setInt(11, customerID);
            ps.setInt(12, userID);
            ps.setInt(13, contactID);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to update the appointment
     * @param appointmentID ID of the appointment
     * @param appointmentTitle title of the appointment
     * @param appointmentDescription description of the appointment
     * @param appointmentLocation location of the appointment
     * @param appointmentType type of the appointment
     * @param appointmentStart Start of the appointment
     * @param appointmentEnd end of the appointment
     * @param appointmentLastUpdate when appointment was last updated
     * @param appointmentLastUpdatedBy appointment last updated by
     * @param customerID customerID of the appointment
     * @param userID userID of the appointment user
     * @param contactID contactID for the appointment
     */
    public static void updateAppointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                                         String appointmentLocation, String appointmentType, Timestamp appointmentStart,
                                         Timestamp appointmentEnd, Timestamp appointmentLastUpdate,
                                         String appointmentLastUpdatedBy, int customerID, int userID, int contactID){
        try {
            String sql = "update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? where Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, appointmentStart);
            ps.setTimestamp(6, appointmentEnd);
            ps.setTimestamp(7, appointmentLastUpdate);
            ps.setString(8, appointmentLastUpdatedBy);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.setInt(12, appointmentID);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to delete the appointment from the database
     * @param appointmentID ID of the appointment
     */
    public static void deleteAppointment(int appointmentID){
        try{
            String sql = "delete from appointments where Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
