/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.UserPage;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import java.io.ByteArrayInputStream;
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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class PackageBookingContentController implements Initializable {

    
    int packageid = UserPageContentController.booking;
    int stay, food, bus, train, air, total, checkedTravel = 0;

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
    private DatePicker dDate;

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
        String query = "select * from package where packId="+packageid+";\n";
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPackageData();

        dBus.setToggleGroup(travelling);
        dTrain.setToggleGroup(travelling);
        dAir.setToggleGroup(travelling);
        
        dTravel.setOnAction(e -> {
            System.out.println("Check");
            if (dTravel.isSelected()) {
                checkedTravel=0;
                dBus.setDisable(false);
                dBus.setSelected(true);
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
                total=total-checkedTravel;
                if (dBus.isSelected()) {                  
                    checkedTravel = bus;
                    System.out.println("bus");
                } else if (dTrain.isSelected()) {
                    checkedTravel = train;
                    System.out.println("train");
                } else if (dAir.isSelected()) {
                    checkedTravel = air;
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
                total=total+food;
                dTotal.setText(Integer.toString(total));
            } else {
                dFood.setText("No");
                total=total-food;
                dTotal.setText(Integer.toString(total));
            }
        });
        dTotal.setText(Integer.toString(total = stay));

    }
}
