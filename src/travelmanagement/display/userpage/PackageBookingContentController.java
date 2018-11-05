/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.display.userpage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import travelmanagement.TravelManagement;
import travelmanagement.database.SqliteConnection;
import travelmanagement.loginregister.LoginRegisterController;

/**
 *
 * @author komal
 */
public class PackageBookingContentController implements Initializable {

    public static int packageid, userId;
    static int stay, food, bus, train, air, total, checkedTravel=0;
    String travelmode = null;
    static Connection connection = TravelManagement.connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    ToggleGroup travelling = new ToggleGroup();

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane booking;

    @FXML
    private BorderPane imagepane;

    @FXML
    private ImageView dImage;

    @FXML
    private JFXToggleButton dFood;

    @FXML
    private Label dPlace;

    @FXML
    private Label dDetails;

    @FXML
    private Label dAdults;

    @FXML
    private Label dKids;

    @FXML
    private Label dStay;

    @FXML
    private JFXCheckBox dTravel;

    @FXML
    private JFXRadioButton dBus;

    @FXML
    private JFXRadioButton dTrain;

    @FXML
    private JFXRadioButton dAir;

    @FXML
    private JFXButton dBook;

    @FXML
    private JFXButton dPay;

    @FXML
    private Label dTotal;

    @FXML
    private Label dDays;

    @FXML
    private JFXDatePicker dDate;

    @FXML
    void BookPackage(ActionEvent event) {

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
            try {
                if (connection.isClosed()) {
                    connection = SqliteConnection.connector();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query = "INSERT INTO booked (packId,userId,foodFee,travelFee,travelMode,travelDate) VALUES (" + packageid + "," + userId + "," + food + "," + checkedTravel + ",'" + travelmode + "','" + dDate.getValue() + "');\n";
            System.out.println(query);
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                System.out.println("Done");
                JFXDialogLayout taskdone = new JFXDialogLayout();
                taskdone.setHeading(new Text("Successful!"));

                taskdone.setBody(new Text("Booking Done"));
                JFXDialog taskdonediag = new JFXDialog(root, taskdone, JFXDialog.DialogTransition.CENTER);
                JFXButton taskdonebtn = new JFXButton("Okay!");
                taskdonebtn.setId("buttons");
                taskdonebtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        taskdonediag.close();
                    }
                });
                taskdone.setActions(taskdonebtn);
                taskdonediag.show();
                dBook.setDisable(true);
                dPay.setDisable(false);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed");
                JFXDialogLayout taskdone = new JFXDialogLayout();
                taskdone.setHeading(new Text("Failed!"));

                taskdone.setBody(new Text("Booking Failed"));
                JFXDialog taskdonediag = new JFXDialog(root, taskdone, JFXDialog.DialogTransition.CENTER);
                JFXButton taskdonebtn = new JFXButton("Okay!");
                taskdonebtn.setId("buttons");
                taskdonebtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        taskdonediag.close();
                    }
                });
                taskdone.setActions(taskdonebtn);
                taskdonediag.show();
            } finally {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        getPackageData();
        booking.getChildren().clear();
        try {
            booking.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/display/userpage/ViewPlacesContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @FXML
    void PayPackage(ActionEvent event) {
        try {
            if (connection.isClosed()) {
                connection = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!dFood.isSelected()){
            food=0;
        }
        if(!dTravel.isSelected()){
            checkedTravel=0;
            travelmode="None";
        }
        String query = "update booked SET foodFee="+food+", travelFee="+checkedTravel+", travelMode='"+travelmode+"', travelDate='"+dDate.getValue()+"' where packId="+packageid+" and userId="+userId+";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Done");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed");
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            getPackageData();
        }
        booking.getChildren().clear();
        try {
            booking.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/display/userpage/PackagePaymentContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPackageData() {
        stay=food=bus=train=air=total=checkedTravel=0;

        try {
            if (connection.isClosed()) {
                connection = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                System.out.println("Stay DB" + stay);
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
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bookingExists(packageid, userId)) {
            try {
                if (connection.isClosed()) {
                    connection = SqliteConnection.connector();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String queryExist = "select * from booked where packId=" + packageid + " and " + "userId=" + userId + ";\n";
            System.out.println(queryExist);
            try {
                preparedStatement = connection.prepareStatement(queryExist);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getInt("foodFee") == 0) {
                        dFood.setSelected(false);
                        dFood.setText("No");
                    } else {
                        dFood.setSelected(true);
                        dFood.setText("Yes");
                    }
                    if ("None".equals(resultSet.getString("travelMode"))) {
                        dTravel.setSelected(false);
                    } else {
                        dTravel.setSelected(true);
                        dBus.setDisable(false);
                        dTrain.setDisable(false);
                        dAir.setDisable(false);
                        if ("Bus".equals(resultSet.getString("travelMode"))) {
                            dBus.setSelected(true);
                            dTrain.setSelected(false);
                            dAir.setSelected(false);
                            checkedTravel=bus;
                            travelmode="Bus";
                        } else if ("Train".equals(resultSet.getString("travelMode"))) {
                            dBus.setSelected(false);
                            dTrain.setSelected(true);
                            dAir.setSelected(false);
                            checkedTravel=train;
                            travelmode="Train";
                        } else if ("Flight".equals(resultSet.getString("travelMode"))) {
                            dBus.setSelected(false);
                            dTrain.setSelected(false);
                            dAir.setSelected(true);
                            checkedTravel=air;
                            travelmode="Flight";
                        }
                    }
                    dDate.setValue(LocalDate.parse(resultSet.getString("travelDate")));
                    total = resultSet.getInt("travelFee") + resultSet.getInt("foodFee") + Integer.valueOf(dStay.getText());
                    dTotal.setText(Integer.toString(total));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    resultSet.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //START check if booking exists
    public boolean paymentDone(int packId, int userId) {
        try {
            if (connection.isClosed()) {
                connection = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END check if booking exists        

    //START check if booking exists
    public boolean bookingExists(int packId, int userId) {
        try {
            if (connection.isClosed()) {
                connection = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PackageBookingContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END check if booking exists

    void Toggling() {
        System.out.println("Toggle");
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
        System.out.println("While toggle");
        total = total + checkedTravel;
        System.out.println(total);
        dTotal.setText(Integer.toString(total));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dPay.setDisable(true);
        packageid = ViewPlacesContentController.booking;
        userId = LoginRegisterController.userId;
        getPackageData();

        dBus.setToggleGroup(travelling);
        dTrain.setToggleGroup(travelling);
        dAir.setToggleGroup(travelling);
        if (total == 0) {
            total = stay;
        }
        dTotal.setText(Integer.toString(total));
        System.out.println(total);
        dTravel.setOnAction(e -> {
            
            System.out.println("Check");
            if (dTravel.isSelected()) {

                checkedTravel = 0;
                dBus.setDisable(false);
                dBus.setSelected(true);
                Toggling();
                dTrain.setDisable(false);
                dAir.setDisable(false);
                System.out.println("While tick");
                System.out.println(total);
            } else {
                dBus.setDisable(true);
                dTrain.setDisable(true);
                dAir.setDisable(true);
                total = total - checkedTravel;
                System.out.println(total);
                dTotal.setText(Integer.toString(total));
            }
        });

        travelling.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                Toggling();
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

        dDate.setOnAction(e -> {
            System.out.println(dDate.getValue());
        });
        imagepane.setCenter(dImage);
        if (bookingExists(packageid, userId)) {
            dBook.setDisable(true);
            dPay.setDisable(false);
        }
        if (paymentDone(packageid, userId)) {
            dPay.setDisable(true);
            dBook.setDisable(true);
        }
    }
}
