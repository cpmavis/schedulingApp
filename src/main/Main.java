package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * C195 Java Application Development
 * @author Corey Mavis
 */
public class Main extends Application {
    /**
     * Creates and loads up the initial stage
     * @param primaryStage first stage to be loaded
     * @throws Exception throws an exception if any errors occurs
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("languages", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginScreenView.fxml"));
        primaryStage.setTitle(rb.getString("title"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Launches the application
     * Connects and closes the database connection
     * @param args arguments
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}

