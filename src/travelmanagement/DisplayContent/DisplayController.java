package travelmanagement.DisplayContent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelmanagement.LoginRegister.LoginModel;
import travelmanagement.LoginRegister.LoginRegisterController;
import travelmanagement.TravelManagement;
import travelmanagement.database.SqliteConnection;

public class DisplayController implements Initializable {
    
    public static int userId ;
    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    public DisplayController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }
    public String getUsername() throws SQLException {
        String query = "SELECT * FROM users WHERE uid=" + userId + ";";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSet.getString("name");
        } catch (SQLException e) {
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    static String usertype;


    @FXML
    private Label mainHeading;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Pane mainHeader;

    public void Logout(MouseEvent event) {
        try {
            Stage stage;
            Parent loader;

            loader = FXMLLoader.load(getClass().getResource("/travelmanagement/LoginRegister/LoginRegisterPage.fxml"));
            Scene scene = new Scene(loader);
            scene.getStylesheets().addAll(getClass().getResource("/travelmanagement/style.css").toExternalForm());
            stage = TravelManagement.stage;
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(DisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainAnchorPane.getStylesheets().addAll(getClass().getResource("/travelmanagement/style.css").toExternalForm());
        userId = LoginModel.userId;
        usertype = LoginRegisterController.usertype;
        try {
            if ("user".equals(usertype)) {
                AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/UserPageContent.fxml"));
                mainAnchorPane.getChildren().add(newLoadedPane);
                mainHeading.setText("User Panel");
            } else if ("admin".equals(usertype)) {
                AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/AdminPage/AdminPageContent.fxml"));
                mainAnchorPane.getChildren().add(newLoadedPane);
                mainHeading.setText("Admin Panel");
            }
        } catch (IOException ex) {
            Logger.getLogger(DisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
