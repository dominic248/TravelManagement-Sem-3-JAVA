/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.display.userpage;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import travelmanagement.TravelManagement;
import travelmanagement.display.other.TransInfo;
import travelmanagement.database.SqliteConnection;
import travelmanagement.loginregister.LoginRegisterController;

/**
 *
 * @author komal
 */
public class UserPageContentController implements Initializable {

    static Connection connection = TravelManagement.connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    public static int userId;
    static ObservableList<TransInfo> transdata = FXCollections.observableArrayList();
    public static int booking;

    @FXML
    private JFXTabPane mainUserTab;

    @FXML
    private Tab ViewPlacesTab;

    @FXML
    private AnchorPane ViewPlaces;

    @FXML
    private Tab ViewBuyedTab;

    @FXML
    private JFXTreeTableView<TransInfo> TransInfoTable;

    //START prep transaction table
    public void getTransData() {

        try {
            if (connection.isClosed()) {
                connection = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "select package.packId,booked.packId,userId,place,stayFee,booked.foodFee,travelFee,travelMode,travelDate,paid from package, booked where package.packId=booked.packId and userId=" + userId + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                transdata.add(new TransInfo(
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
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END prep transaction table

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId = LoginRegisterController.userId;
        mainUserTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            mainUserTab.setTabMinWidth(((mainUserTab.getWidth()) / 2) - 5);
            mainUserTab.setTabMaxWidth(((mainUserTab.getWidth()) / 2) - 5);

        });

        ViewPlaces.getChildren().clear();
        try {
            ViewPlaces.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/display/userpage/ViewPlacesContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //START for view transactions.....................................................
        ViewBuyedTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Set View");
                TransInfoTable.setRoot(null);
                transdata.clear();
                transdata.removeAll(transdata);
                JFXTreeTableColumn<TransInfo, String> tPlace = new JFXTreeTableColumn<>("Place");
                tPlace.setPrefWidth(130);
                tPlace.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().place;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tStayAm = new JFXTreeTableColumn<>("Stay Cost");
                tStayAm.setPrefWidth(80);
                tStayAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().stayFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tFoodAm = new JFXTreeTableColumn<>("Food Cost");
                tFoodAm.setPrefWidth(80);
                tFoodAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().foodFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelAm = new JFXTreeTableColumn<>("Travel Cost");
                tTravelAm.setPrefWidth(80);
                tTravelAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTotalAm = new JFXTreeTableColumn<>("Total Cost");
                tTotalAm.setPrefWidth(80);
                tTotalAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().total;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelMode = new JFXTreeTableColumn<>("Travel Mode");
                tTravelMode.setPrefWidth(80);
                tTravelMode.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelMode;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelDate = new JFXTreeTableColumn<>("Travel Date");
                tTravelDate.setPrefWidth(80);
                tTravelDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelDate;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tPaid = new JFXTreeTableColumn<>("Paid");
                tPaid.setPrefWidth(90);
                tPaid.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().paid;
                    }
                });
                getTransData();
                final TreeItem<TransInfo> root = new RecursiveTreeItem<TransInfo>(transdata, RecursiveTreeObject::getChildren);
                TransInfoTable.getColumns().setAll(tPlace, tStayAm, tFoodAm, tTravelAm, tTotalAm, tTravelMode, tTravelDate, tPaid);

                TransInfoTable.setRoot(root);
                TransInfoTable.setShowRoot(false);
            }
        });
        //END for view transactions.....................................................
    }
}
