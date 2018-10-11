/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.UserPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import travelmanagement.LoginRegister.LoginModel;

import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class PackageBookingContentController implements Initializable {

    static int packageid;
    static int userId;
    static int stay, food, bus, train, air, total, checkedTravel = 0;
    String travelmode = null;

    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public PackageBookingContentController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }
    
    @FXML
    private AnchorPane booking;

    @FXML
    private BorderPane imagepane;

    @FXML
    private JFXToggleButton dFood;

    @FXML
    private JFXCheckBox dTravel;

    @FXML
    private JFXRadioButton dAir;

    @FXML
    private ImageView dImage;

    @FXML
    private Label dAdults;

    @FXML
    private JFXRadioButton dBus;

    @FXML
    private JFXDatePicker dDate;

    @FXML
    private JFXButton dPay, dBook,Cancel;

    @FXML
    private Label dKids;

    @FXML
    private Label dStay;

    @FXML
    private Label dTotal;

    @FXML
    private Label dDetails;

    @FXML
    private Label dDays;

    @FXML
    private Label dPlace;

    @FXML
    private JFXRadioButton dTrain;

    ToggleGroup travelling = new ToggleGroup();

    public void getPackageData() {
        String query = "select * from package where packId=" + packageid + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                dPlace.setText(resultSet.getString("place"));
                dDetails.setText(resultSet.getString("pDetails"));
                dAdults.setText(Integer.toString(resultSet.getInt("noAdult")));
                dKids.setText(Integer.toString(resultSet.getInt("noChild")));
                dStay.setText(Integer.toString(resultSet.getInt("stayFee")));
                stay = resultSet.getInt("stayFee");
                food = resultSet.getInt("foodFee");
                bus = resultSet.getInt("busFee");
                train = resultSet.getInt("trainFee");
                air = resultSet.getInt("airlinesFee");
                dDays.setText(Integer.toString(resultSet.getInt("noDays")) + "day and " + Integer.toString(resultSet.getInt("noNights")) + "night");
                InputStream input = new ByteArrayInputStream(resultSet.getBytes("pImage"));
                Image imge = new Image(input);
                dImage.setImage(imge);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //START check if booking exists
    public boolean paymentDone(int packId, int userId) {
        String query = "select paid from booked where packId=" + packId + " and userId=" + userId + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if ("Yes".equals(resultSet.getString("paid"))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //END check if booking exists        

    //START check if booking exists
    public boolean bookingExists(int packId, int userId) {
        String query = "select * from booked where packId=" + packId + " and userId=" + userId + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //END check if booking exists

    public void BookPackage(ActionEvent event) {

        if (dFood.getText() != "Yes") {
            food = 0;
        }
        if (!dTravel.isSelected()) {
            checkedTravel = 0;
            travelmode = "None";
        }
        if (dDate.getValue() == null) {
            System.out.println("Date is null");
            dDate.setStyle("-fx-background-color: yellow;");
            dDate.setPromptText("Please select a date!");
        } else if (bookingExists(packageid, userId)) {
            dBook.setDisable(true);
        } else {
            String query = "INSERT INTO booked (packId,userId,foodFee,travelFee,travelMode,travelDate) VALUES (" + packageid + "," + userId + "," + food + "," + checkedTravel + ",'" + travelmode + "','" + dDate.getValue() + "');\n";
            System.out.println(query);
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                System.out.println("Done");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed");
            }
        }
    }
    
    
     @FXML
    void Cancel(ActionEvent event) {
        booking.getChildren().clear();
        try {
            booking.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/ViewPlacesContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void PayPackage(ActionEvent event){
        booking.getChildren().clear();
        try {
            booking.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/PackagePaymentContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        packageid = ViewPlacesContentController.booking;
        userId = LoginModel.userId;
        getPackageData();

        dBus.setToggleGroup(travelling);
        dTrain.setToggleGroup(travelling);
        dAir.setToggleGroup(travelling);

        dTravel.setOnAction(e -> {
            System.out.println("Check");
            if (dTravel.isSelected()) {
                checkedTravel = 0;
                dBus.setDisable(false);
                dBus.setSelected(true);
                travelmode = dBus.getText();
                dTrain.setDisable(false);
                dAir.setDisable(false);

            } else {
                dBus.setDisable(true);
                dTrain.setDisable(true);
                dAir.setDisable(true);
                total = total - checkedTravel;
                dTotal.setText(Integer.toString(total));
            }
        });

        travelling.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                total = total - checkedTravel;
                if (dBus.isSelected()) {
                    checkedTravel = bus;
                    travelmode = dBus.getText();
                    System.out.println("bus");
                } else if (dTrain.isSelected()) {
                    checkedTravel = train;
                    travelmode = dTrain.getText();
                    System.out.println("train");
                } else if (dAir.isSelected()) {
                    checkedTravel = air;
                    travelmode = dAir.getText();
                    System.out.println("air");
                }
                total = total + checkedTravel;
                dTotal.setText(Integer.toString(total));
            }
        });

        dFood.setOnAction(e -> {
            System.out.println("Toggle");
            if (dFood.isSelected()) {
                dFood.setText("Yes");
                total = total + food;
                dTotal.setText(Integer.toString(total));
            } else {
                dFood.setText("No");
                total = total - food;
                dTotal.setText(Integer.toString(total));
            }
        });
        dTotal.setText(Integer.toString(total = stay));

        dDate.setOnAction(e -> {
            System.out.println(dDate.getValue());
        });
        imagepane.setCenter(dImage);
        if (bookingExists(packageid, userId)) {
            dBook.setDisable(true);
        }
        if (paymentDone(packageid, userId)) {
            dPay.setDisable(true);
        }
    }
}
