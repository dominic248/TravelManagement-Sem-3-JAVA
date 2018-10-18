/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.AdminPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import travelmanagement.DisplayContent.Other.BookingInfo;
import travelmanagement.database.SqliteConnection;
import travelmanagement.DisplayContent.Other.PackageInfo;
import travelmanagement.DisplayContent.Other.UserInfo;

/**
 *
 * @author dms
 */
public class AdminPageContentController implements Initializable {

    int intpackage;
    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public AdminPageContentController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }

    @FXML
    private Label dFood;

    @FXML
    private Tab addPackageTab;

    @FXML
    private JFXButton addPackageBtn,upPackageBtn;

    @FXML
    private Label isaddNoNight;

    @FXML
    private ImageView dImage;

    @FXML
    private Label isaddPlace;

    @FXML
    private Label isaddBusAm;

    @FXML
    private JFXTextField addNoAdult;

    @FXML
    private JFXTextField addNoChild;

    @FXML
    private Label dBus;

    @FXML
    private JFXTextField addTrainAm;

    @FXML
    private JFXTextField addBusAm;

    @FXML
    private Label dKids;

    @FXML
    private Label isaddAirAm;

    @FXML
    private Tab ViewUserTab;

    @FXML
    private JFXTextField addPlace;

    @FXML
    private JFXTextField addAirAm;

    @FXML
    private JFXComboBox<String> addNoDay;

    @FXML
    private Label isaddImage;

    @FXML
    private Label isaddFoodAm;

    @FXML
    private Tab ViewPackageTab;

    @FXML
    private JFXTextField addStayAm;

    @FXML
    private Label isaddDetails;

    @FXML
    private Label isaddNoDay;

    @FXML
    private JFXButton addBrowse;

    @FXML
    private JFXComboBox<String> addNoNight;

    @FXML
    private ImageView addImage;

    @FXML
    private Label isaddTrainAm;

    @FXML
    private Label dAir;

    @FXML
    private Tab ViewBookingTab;

    @FXML
    private Label isaddNoChild;

    @FXML
    private Label isaddStayAm;

    @FXML
    private Label dAdults;

    @FXML
    private Label isaddNoAdult;

    @FXML
    private Label dStay;

    @FXML
    private JFXTabPane mainAdminTab;

    @FXML
    private JFXTextArea addDetails;

    @FXML
    private Label dDetails;

    @FXML
    private Label dDays;

    @FXML
    private StackPane root;

    @FXML
    private JFXTreeTableView<PackageInfo> PackageInfoTable;

    @FXML
    private JFXTreeTableView<UserInfo> UserInfoTable;

    @FXML
    private JFXTreeTableView<BookingInfo> BookingInfoTable;

    @FXML
    private JFXTextField addFoodAm;

    @FXML
    private Label dPlace;

    @FXML
    private Label dTrain;

    static ObservableList<PackageInfo> packagedata = FXCollections.observableArrayList();
    static ObservableList<UserInfo> userdata = FXCollections.observableArrayList();
    static ObservableList<BookingInfo> bookingdata = FXCollections.observableArrayList();
    Stage stage;
    FileChooser fileChoose;
    private Image image;
    private File file;
    private FileInputStream imagefis;

    ObservableList<String> nodaynight = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    public void PopUp(String task, String status) {
        String Heading, Body;
        if (("Deleted".equals(task)) && ("Success".equals(status))) {
            Heading = "Successful!";
            Body = "Package deleted successfully!";
        } else if ("Deleted".equals(task) && "Failed".equals(status)) {
            Heading = "Failed!";
            Body = "Failed to delete Package!";
        } else if ("Added".equals(task) && "Success".equals(status)) {
            Heading = "Successful!";
            Body = "Package added successfully!";
        } else if ("Added".equals(task) && "Failed".equals(status)) {
            Heading = "Failed!";
            Body = "Failed to add Package!";
        } else if ("Updated".equals(task) && "Success".equals(status)) {
            Heading = "Successful!";
            Body = "Package updated successfully!";
        } else if ("Updated".equals(task) && "Failed".equals(status)) {
            Heading = "Failed!";
            Body = "Failed to update Package!";
        }  else {
            Heading = null;
            Body = null;
        }

        JFXDialogLayout taskdone = new JFXDialogLayout();
        taskdone.setHeading(new Text(Heading));

        taskdone.setBody(new Text(Body));
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
    }

    @FXML
    void UpdatePackage(ActionEvent event) {
        String query = "select * from package where packId=" + intpackage + ";";

        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                addPlace.setText(resultSet.getString("place"));
                addDetails.setText(resultSet.getString("pDetails"));
                addNoAdult.setText(Integer.toString(resultSet.getInt("noAdult")));
                addNoChild.setText(Integer.toString(resultSet.getInt("noChild")));
                addStayAm.setText(Integer.toString(resultSet.getInt("stayFee")));
                addFoodAm.setText(Integer.toString(resultSet.getInt("foodFee")));
                addBusAm.setText(Integer.toString(resultSet.getInt("busFee")));
                addTrainAm.setText(Integer.toString(resultSet.getInt("trainFee")));
                addAirAm.setText(Integer.toString(resultSet.getInt("airlinesFee")));
                addNoDay.setValue(Integer.toString(resultSet.getInt("noDays")));
                addNoNight.setValue(Integer.toString(resultSet.getInt("noNights")));
                InputStream input = new ByteArrayInputStream(resultSet.getBytes("pImage"));
                Image image = new Image(input);
                addImage.setImage(image);
                mainAdminTab.getSelectionModel().select(0);
                upPackageBtn.setVisible(true);
                addPackageBtn.setVisible(false);
            }

        } catch (SQLException e) {
            System.out.println("Failed");

            e.printStackTrace();

        }
    }

    @FXML
    void DeletePackage(ActionEvent event) {
        String query = "delete from package where packId=" + intpackage + ";";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            loadViewPackages();
            System.out.println("Done deleting");
            PopUp("Deleted", "Success");

        } catch (SQLException e) {
            System.out.println("Failed");
            PopUp("Deleted", "Failed");
            e.printStackTrace();

        }

    }
    public boolean isPackageUpdated(String place, String details, String noAdult, String noChild, String stayAm, String foodAm, String busAm, String trainAm, String airAm, String day, String night) throws FileNotFoundException {

       
        String query = "update package SET place='" + place + "',pDetails='" + details + "',noAdult=" + noAdult + ",noChild=" + noChild + ",stayFee=" + stayAm + ",foodFee=" + foodAm + ",busFee=" + busAm + ",trainFee=" + trainAm + ",airlinesFee=" + airAm + ",noDays=" + day + ",noNights=" + night + " where packId="+intpackage+";";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Done");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }
    public boolean isPackageUpdatedImg(String place, String details, String noAdult, String noChild, String stayAm, String foodAm, String busAm, String trainAm, String airAm, String day, String night) throws FileNotFoundException {

        imagefis = new FileInputStream(file);
        String query = "update package SET place='" + place + "',pDetails='" + details + "',noAdult=" + noAdult + ",noChild=" + noChild + ",stayFee=" + stayAm + ",foodFee=" + foodAm + ",busFee=" + busAm + ",trainFee=" + trainAm + ",airlinesFee=" + airAm + ",noDays=" + day + ",noNights=" + night + ",pImage=? where packId="+intpackage+";";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBinaryStream(1, (InputStream) imagefis, (int) file.length());
            preparedStatement.execute();
            System.out.println("Done");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }
    

    public void upPackageDB(ActionEvent event) {
        if (addPlace.getText().isEmpty()) {
            isaddPlace.setText("Place cannot be empty!");
        }else {
            isaddPlace.setText("");
        } 
        if (addDetails.getText().isEmpty()) {
            isaddDetails.setText("Description cannot be empty!");
        } else {
            isaddDetails.setText("");
        }
        if (addNoAdult.getText().isEmpty()) {
            isaddNoAdult.setText("No. of Adults cannot be empty!");
        } else {
            isaddNoAdult.setText("");
        }
        if (addNoChild.getText().isEmpty()) {
            isaddNoChild.setText("No. of Kids cannot be empty!");
        } else {
            isaddNoChild.setText("");
        }
        if (addStayAm.getText().isEmpty()) {
            isaddStayAm.setText("Stay Cost cannot be empty!");
        } else {
            isaddStayAm.setText("");
        }
        if (addFoodAm.getText().isEmpty()) {
            isaddFoodAm.setText("Food Cost cannot be empty!");
        } else {
            isaddFoodAm.setText("");
        }
        if (addBusAm.getText().isEmpty()) {
            isaddBusAm.setText("Bus Cost cannot be empty!");
        } else {
            isaddBusAm.setText("");
        }
        if (addTrainAm.getText().isEmpty()) {
            isaddTrainAm.setText("Train Cost cannot be empty!");
        } else {
            isaddTrainAm.setText("");
        }
        if (addAirAm.getText().isEmpty()) {
            isaddAirAm.setText("Airlines Cost cannot be empty!");
        } else {
            isaddAirAm.setText("");
        }
        
        if (addNoDay.getValue() == null) {
            isaddNoDay.setText("No. of Days cannot be empty!");
        } else {
            isaddNoDay.setText("");
        }
        if (addNoNight.getValue() == null) {
            isaddNoNight.setText("No. of Nights cannot be empty!");
        } else {
            isaddNoNight.setText("");
        }
        
        if (addPlace.getText().isEmpty() || addDetails.getText().isEmpty() || addNoAdult.getText().isEmpty() || addNoChild.getText().isEmpty()
                || addStayAm.getText().isEmpty() || addFoodAm.getText().isEmpty() 
                || addBusAm.getText().isEmpty() || addTrainAm.getText().isEmpty() || addAirAm.getText().isEmpty()
                || addNoDay.getValue() == null || addNoNight.getValue() == null ) {
            return;
        } else if(addPlace.getText().isEmpty() || addDetails.getText().isEmpty() || addNoAdult.getText().isEmpty() || addNoChild.getText().isEmpty()
                || addStayAm.getText().isEmpty() || addFoodAm.getText().isEmpty() || file == null
                || addBusAm.getText().isEmpty() || addTrainAm.getText().isEmpty() || addAirAm.getText().isEmpty()
                || addNoDay.getValue() == null || addNoNight.getValue() == null){
            try {
                if (isPackageUpdated(addPlace.getText(), addDetails.getText(), addNoAdult.getText(), addNoChild.getText(), addStayAm.getText(), addFoodAm.getText(), addBusAm.getText(), addTrainAm.getText(), addAirAm.getText(), addNoDay.getValue(), addNoNight.getValue())) {
                    System.out.println("Done");
                    PopUp("Updated", "Success");
                    addPlace.setText("");
                    addDetails.setText("");
                    addNoAdult.setText("");
                    addNoChild.setText("");
                    addStayAm.setText("");
                    addFoodAm.setText("");
                    addBusAm.setText("");
                    addTrainAm.setText("");
                    addAirAm.setText("");
                    addNoDay.setValue("");
                    addNoNight.setValue("");
                    addImage.setImage(null);
                    mainAdminTab.getSelectionModel().select(1);
                    
                } else {
                    PopUp("Updated", "Failed");
                }
            } catch (FileNotFoundException ex) {
                PopUp("Updated", "Failed");
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }else{
            try {
                if (isPackageUpdatedImg(addPlace.getText(), addDetails.getText(), addNoAdult.getText(), addNoChild.getText(), addStayAm.getText(), addFoodAm.getText(), addBusAm.getText(), addTrainAm.getText(), addAirAm.getText(), addNoDay.getValue(), addNoNight.getValue())) {
                    System.out.println("Done");
                    PopUp("Updated", "Success");
                    addPlace.setText("");
                    addDetails.setText("");
                    addNoAdult.setText("");
                    addNoChild.setText("");
                    addStayAm.setText("");
                    addFoodAm.setText("");
                    addBusAm.setText("");
                    addTrainAm.setText("");
                    addAirAm.setText("");
                    addNoDay.setValue("");
                    addNoNight.setValue("");
                    addImage.setImage(null);
                    mainAdminTab.getSelectionModel().select(1);

                } else {
                    PopUp("Updated", "Failed");
                }
            } catch (FileNotFoundException ex) {
                PopUp("Updated", "Failed");
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    //START select image from disk
    public void browseImage(ActionEvent event) {
        fileChoose = new FileChooser();
        fileChoose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = fileChoose.showOpenDialog(this.stage);
        if (file != null) {
            String s = file.getAbsolutePath();
            System.out.println(s);
            image = new Image(file.toURI().toString());
            addImage.setImage(image);
        }
    }
    //END select image from disk

    boolean placeExists(String place){
        String query = "select * from package where place='" + place + "';";

        System.out.println(query);    
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
            
        
    }
    
    
    //START check conditions of null before adding packages data to database
    public void addPackageDB(ActionEvent event) {
        if (addPlace.getText().isEmpty()) {
            isaddPlace.setText("Place cannot be empty!");
        }else if (placeExists(addPlace.getText())) {
            isaddPlace.setText("Place cannot be empty!");
        }else {
            isaddPlace.setText("");
        } 
        if (addDetails.getText().isEmpty()) {
            isaddDetails.setText("Description cannot be empty!");
        } else {
            isaddDetails.setText("");
        }
        if (addNoAdult.getText().isEmpty()) {
            isaddNoAdult.setText("No. of Adults cannot be empty!");
        } else {
            isaddNoAdult.setText("");
        }
        if (addNoChild.getText().isEmpty()) {
            isaddNoChild.setText("No. of Kids cannot be empty!");
        } else {
            isaddNoChild.setText("");
        }
        if (addStayAm.getText().isEmpty()) {
            isaddStayAm.setText("Stay Cost cannot be empty!");
        } else {
            isaddStayAm.setText("");
        }
        if (addFoodAm.getText().isEmpty()) {
            isaddFoodAm.setText("Food Cost cannot be empty!");
        } else {
            isaddFoodAm.setText("");
        }
        if (addBusAm.getText().isEmpty()) {
            isaddBusAm.setText("Bus Cost cannot be empty!");
        } else {
            isaddBusAm.setText("");
        }
        if (addTrainAm.getText().isEmpty()) {
            isaddTrainAm.setText("Train Cost cannot be empty!");
        } else {
            isaddTrainAm.setText("");
        }
        if (addAirAm.getText().isEmpty()) {
            isaddAirAm.setText("Airlines Cost cannot be empty!");
        } else {
            isaddAirAm.setText("");
        }
        if (file == null) {
            isaddImage.setText("Image cannot be empty!");
        } else {
            isaddImage.setText("");
        }
        if (addNoDay.getValue() == null) {
            isaddNoDay.setText("No. of Days cannot be empty!");
        } else {
            isaddNoDay.setText("");
        }
        if (addNoNight.getValue() == null) {
            isaddNoNight.setText("No. of Nights cannot be empty!");
        } else {
            isaddNoNight.setText("");
        }
        
        if (addPlace.getText().isEmpty() || addDetails.getText().isEmpty() || addNoAdult.getText().isEmpty() || addNoChild.getText().isEmpty()
                || addStayAm.getText().isEmpty() || addFoodAm.getText().isEmpty() || file == null
                || addBusAm.getText().isEmpty() || addTrainAm.getText().isEmpty() || addAirAm.getText().isEmpty()
                || addNoDay.getValue() == null || addNoNight.getValue() == null || placeExists(addPlace.getText())) {
            return;
        } else {
            try {
                if (isPackageAdded(addPlace.getText(), addDetails.getText(), addNoAdult.getText(), addNoChild.getText(), addStayAm.getText(), addFoodAm.getText(), addBusAm.getText(), addTrainAm.getText(), addAirAm.getText(), addNoDay.getValue(), addNoNight.getValue())) {
                    System.out.println("Done");
                    PopUp("Added", "Success");
                    addPlace.setText("");
                    addDetails.setText("");
                    addNoAdult.setText("");
                    addNoChild.setText("");
                    addStayAm.setText("");
                    addFoodAm.setText("");
                    addBusAm.setText("");
                    addTrainAm.setText("");
                    addAirAm.setText("");
                    addNoDay.setValue("");
                    addNoNight.setValue("");
                    addImage.setImage(null);

                } else {
                    PopUp("Added", "Failed");
                }
            } catch (FileNotFoundException ex) {
                PopUp("Added", "Failed");
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END check conditions of null before adding packages data to database

    //START add packages data to database
    public boolean isPackageAdded(String place, String details, String noAdult, String noChild, String stayAm, String foodAm, String busAm, String trainAm, String airAm, String day, String night) throws FileNotFoundException {

        imagefis = new FileInputStream(file);
        String query = "INSERT INTO package (place,pDetails,noAdult,noChild,stayFee,foodFee,busFee,trainFee,airlinesFee,noDays,noNights,pImage) VALUES ('" + place + "','" + details + "'," + noAdult + "," + noChild + "," + stayAm + "," + foodAm + "," + busAm + "," + trainAm + "," + airAm + "," + day + "," + night + ",?);";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBinaryStream(1, (InputStream) imagefis, (int) file.length());
            preparedStatement.execute();
            System.out.println("Done");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }
    //END add packages data to database

    //START add packages data to TreeTable in view packages
    public void getPackageData() {
        String query = "select * from package;\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                packagedata.add(new PackageInfo(Integer.toString(resultSet.getInt("packId")),
                        resultSet.getString("place"),
                        Integer.toString(resultSet.getInt("noAdult")),
                        Integer.toString(resultSet.getInt("noChild")),
                        Integer.toString(resultSet.getInt("stayFee")),
                        Integer.toString(resultSet.getInt("foodFee")),
                        Integer.toString(resultSet.getInt("busFee")),
                        Integer.toString(resultSet.getInt("trainFee")),
                        Integer.toString(resultSet.getInt("airlinesFee")),
                        Integer.toString(resultSet.getInt("noDays")) + "day and " + Integer.toString(resultSet.getInt("noNights")) + "night")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END add packages data to TreeTable in view packages

    //START add packages data to TreeTable in view booking
    public void getBookingData() {

        String query = "select package.packId,booked.packId,userId,place,stayFee,booked.foodFee,travelFee,travelMode,travelDate,paid from package, booked where package.packId=booked.packId;\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                bookingdata.add(new BookingInfo(Integer.toString(resultSet.getInt("packId")),
                        Integer.toString(resultSet.getInt("userId")),
                        resultSet.getString("place"),
                        Integer.toString(resultSet.getInt("stayFee")),
                        Integer.toString(resultSet.getInt("foodFee")),
                        Integer.toString(resultSet.getInt("travelFee")),
                        Integer.toString(resultSet.getInt("stayFee") + resultSet.getInt("foodFee") + resultSet.getInt("travelFee")),
                        resultSet.getString("travelMode"),
                        resultSet.getString("travelDate"),
                        resultSet.getString("paid"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END add packages data to TreeTable in view booking

    //START add user data to TreeTable in view users
    public void getUserData() {
        String query = "select * from users;\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userdata.add(new UserInfo(Integer.toString(resultSet.getInt("userId")),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("mobile"),
                        resultSet.getString("email"),
                        resultSet.getString("userDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END add user data to TreeTable in view users

    //START load data of selected packages from TreeTable to view label in view packages
    public void setPackageToLabel(int intpackage) {
        String query = "select * from package where packId=" + intpackage + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("pass1");
            if (resultSet.next()) {
                System.out.println("pass");
                dPlace.setText(resultSet.getString("place"));
                dDetails.setText(resultSet.getString("pDetails"));
                dAdults.setText(Integer.toString(resultSet.getInt("noAdult")));
                dKids.setText(Integer.toString(resultSet.getInt("noChild")));
                dStay.setText(Integer.toString(resultSet.getInt("stayFee")));
                dFood.setText(Integer.toString(resultSet.getInt("foodFee")));
                dBus.setText(Integer.toString(resultSet.getInt("busFee")));
                dTrain.setText(Integer.toString(resultSet.getInt("trainFee")));
                dAir.setText(Integer.toString(resultSet.getInt("airlinesFee")));
                dDays.setText(Integer.toString(resultSet.getInt("noDays")) + "day and " + Integer.toString(resultSet.getInt("noNights")) + "night");
                InputStream input = new ByteArrayInputStream(resultSet.getBytes("pImage"));
                Image image = new Image(input);
                dImage.setImage(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END load data of selected packages from TreeTable to view label in view packages

    public void loadViewPackages() {
        
        System.out.println("Set View");
        PackageInfoTable.setRoot(null);
        packagedata.clear();
        packagedata.removeAll(packagedata);
        JFXTreeTableColumn<PackageInfo, String> viewPlace = new JFXTreeTableColumn<>("Place");
        viewPlace.setPrefWidth(130);
        viewPlace.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().place;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewNoAdult = new JFXTreeTableColumn<>("No. of Adults");
        viewNoAdult.setPrefWidth(80);
        viewNoAdult.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().noAdult;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewNoChild = new JFXTreeTableColumn<>("No. of Kids");
        viewNoChild.setPrefWidth(80);
        viewNoChild.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().noChild;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewStayAm = new JFXTreeTableColumn<>("Stay Cost");
        viewStayAm.setPrefWidth(80);
        viewStayAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().stayAm;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewFoodAm = new JFXTreeTableColumn<>("Food Cost");
        viewFoodAm.setPrefWidth(80);
        viewFoodAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().foodAm;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewBusAm = new JFXTreeTableColumn<>("Bus Cost");
        viewBusAm.setPrefWidth(80);
        viewBusAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().busAm;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewTrainAm = new JFXTreeTableColumn<>("Train Cost");
        viewTrainAm.setPrefWidth(80);
        viewTrainAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().trainAm;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewAirAm = new JFXTreeTableColumn<>("Flight Cost");
        viewAirAm.setPrefWidth(90);
        viewAirAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().airAm;
            }
        });
        JFXTreeTableColumn<PackageInfo, String> viewNoDays = new JFXTreeTableColumn<>("No. of Days");
        viewNoDays.setPrefWidth(110);
        viewNoDays.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PackageInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PackageInfo, String> param) {
                return param.getValue().getValue().noDays;
            }
        });

        getPackageData();
        final TreeItem<PackageInfo> root = new RecursiveTreeItem<PackageInfo>(packagedata, RecursiveTreeObject::getChildren);
        PackageInfoTable.getColumns().setAll(viewPlace, viewNoAdult, viewNoChild, viewStayAm, viewFoodAm, viewBusAm, viewTrainAm, viewAirAm, viewNoDays);

        PackageInfoTable.setRoot(root);
        PackageInfoTable.setShowRoot(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addPackageBtn.setVisible(true);
        upPackageBtn.setVisible(false);
        addPackageTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                addPackageBtn.setVisible(true);
                upPackageBtn.setVisible(false);
            }
        });
        mainAdminTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            mainAdminTab.setTabMinWidth(((mainAdminTab.getWidth()) / 4) - 3);
            mainAdminTab.setTabMaxWidth(((mainAdminTab.getWidth()) / 4) - 3);

        });

        addNoDay.setItems(nodaynight);
        addNoNight.setItems(nodaynight);

        //Start Validation
        addNoAdult.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addNoAdult.setText(oldValue);
            }
        });
        addNoChild.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addNoChild.setText(oldValue);
            }
        });
        addStayAm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addStayAm.setText(oldValue);
            }
        });
        addFoodAm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addFoodAm.setText(oldValue);
            }
        });
        addBusAm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addBusAm.setText(oldValue);
            }
        });
        addTrainAm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addTrainAm.setText(oldValue);
            }
        });
        addAirAm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                addAirAm.setText(oldValue);
            }
        });
        //end Validation

        //START for view packages.....................................................
        ViewPackageTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                loadViewPackages();
            }
        });
        //END for view packages.....................................................

        //START get place name of select row from tree table
        PackageInfoTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            TreeItem<PackageInfo> selectedItem = PackageInfoTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            TreeItem<PackageInfo> tpackage = PackageInfoTable.getSelectionModel().getSelectedItem();
            String stpackage = tpackage.getValue().getId().toString();
            stpackage = stpackage.substring(23, stpackage.length() - 1);
            intpackage = Integer.valueOf(stpackage);
            System.out.println(intpackage);
            setPackageToLabel(intpackage);
        });
        //END get place name of select row from tree table

        //START for view booking.....................................................
        ViewBookingTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Set View");
                BookingInfoTable.setRoot(null);
                bookingdata.clear();
                bookingdata.removeAll(bookingdata);
                JFXTreeTableColumn<BookingInfo, String> bPackId = new JFXTreeTableColumn<>("Package ID");
                bPackId.setPrefWidth(130);
                bPackId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().packId;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bUserId = new JFXTreeTableColumn<>("User ID");
                bUserId.setPrefWidth(80);
                bUserId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().userId;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bPlace = new JFXTreeTableColumn<>("Place");
                bPlace.setPrefWidth(80);
                bPlace.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().place;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bStayAm = new JFXTreeTableColumn<>("Stay Cost");
                bStayAm.setPrefWidth(80);
                bStayAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().stayFee;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bFoodAm = new JFXTreeTableColumn<>("Food Cost");
                bFoodAm.setPrefWidth(80);
                bFoodAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().foodFee;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bTravelAm = new JFXTreeTableColumn<>("Travel Cost");
                bTravelAm.setPrefWidth(80);
                bTravelAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().travelFee;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bTotal = new JFXTreeTableColumn<>("Total Cost");
                bTotal.setPrefWidth(80);
                bTotal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().total;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bTravelMode = new JFXTreeTableColumn<>("Travel Mode");
                bTravelMode.setPrefWidth(80);
                bTravelMode.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().travelMode;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bTravelDate = new JFXTreeTableColumn<>("Travelling Date");
                bTravelDate.setPrefWidth(90);
                bTravelDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().travelDate;
                    }
                });
                JFXTreeTableColumn<BookingInfo, String> bPaid = new JFXTreeTableColumn<>("Paid");
                bPaid.setPrefWidth(110);
                bPaid.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookingInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookingInfo, String> param) {
                        return param.getValue().getValue().paid;
                    }
                });

                getBookingData();
                final TreeItem<BookingInfo> root = new RecursiveTreeItem<BookingInfo>(bookingdata, RecursiveTreeObject::getChildren);
                BookingInfoTable.getColumns().setAll(bPackId, bUserId, bPlace, bStayAm, bFoodAm, bTravelAm, bTotal, bTravelMode, bTravelDate, bPaid);

                BookingInfoTable.setRoot(root);
                BookingInfoTable.setShowRoot(false);
            }
        });
        //END for view booking.....................................................

        //START for view Users.....................................................
        ViewUserTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Set View");
                UserInfoTable.setRoot(null);
                userdata.clear();
                userdata.removeAll(userdata);
                JFXTreeTableColumn<UserInfo, String> viewID = new JFXTreeTableColumn<>("User ID");
                viewID.setPrefWidth(130);
                viewID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().id;
                    }
                });
                JFXTreeTableColumn<UserInfo, String> viewName = new JFXTreeTableColumn<>("Name");
                viewName.setPrefWidth(80);
                viewName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().name;
                    }
                });
                JFXTreeTableColumn<UserInfo, String> viewUser = new JFXTreeTableColumn<>("Username");
                viewUser.setPrefWidth(80);
                viewUser.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().username;
                    }
                });
                JFXTreeTableColumn<UserInfo, String> viewMobile = new JFXTreeTableColumn<>("Mobile");
                viewMobile.setPrefWidth(80);
                viewMobile.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().mobile;
                    }
                });
                JFXTreeTableColumn<UserInfo, String> viewEmail = new JFXTreeTableColumn<>("E-Mail");
                viewEmail.setPrefWidth(80);
                viewEmail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().email;
                    }
                });
                JFXTreeTableColumn<UserInfo, String> viewDate = new JFXTreeTableColumn<>("Date of joining");
                viewDate.setPrefWidth(80);
                viewDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserInfo, String> param) {
                        return param.getValue().getValue().date;
                    }
                });

                getUserData();
                final TreeItem<UserInfo> root = new RecursiveTreeItem<UserInfo>(userdata, RecursiveTreeObject::getChildren);
                UserInfoTable.getColumns().setAll(viewID, viewName, viewUser, viewMobile, viewEmail, viewDate);
                UserInfoTable.setRoot(root);
                UserInfoTable.setShowRoot(false);
            }
        });
        //END for view Users.....................................................       
    }
}
