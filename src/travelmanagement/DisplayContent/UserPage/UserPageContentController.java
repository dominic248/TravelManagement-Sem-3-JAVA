/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.UserPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import travelmanagement.DisplayContent.Other.BookingInfo;

import travelmanagement.DisplayContent.Other.PackageInfo;
import travelmanagement.DisplayContent.Other.TransInfo;
import travelmanagement.LoginRegister.LoginModel;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class UserPageContentController implements Initializable {

    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public UserPageContentController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }
    public static int userId = LoginModel.userId;

    @FXML
    private Label dFood;

    @FXML
    private Label dAir;

    @FXML
    private JFXTabPane mainUserTab;

    @FXML
    private ImageView dImage;

    @FXML
    private Label dAdults;

    @FXML
    private Label dBus;

    @FXML
    private Tab ViewPlacesTab;

    @FXML
    private AnchorPane ViewPlaces;

    @FXML
    private Label dKids;

    @FXML
    private Label dStay;

    @FXML
    private Tab ViewBuyedTab;

    @FXML
    private Label dDetails;

    @FXML
    private Label dDays;

    @FXML
    private JFXTreeTableView<PackageInfo> PackageInfoTable;

    @FXML
    private JFXTreeTableView<TransInfo> TransInfoTable;

    @FXML
    private Label dPlace;

    @FXML
    private Label dTrain;

    @FXML
    private JFXButton dBook;

    static ObservableList<PackageInfo> packagedata = FXCollections.observableArrayList();
    static ObservableList<TransInfo> transdata = FXCollections.observableArrayList();

    public static int booking;

    //START add packages data to TreeTable in view packages
    public void getPackageData() {
        String query = "select * from package;\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                packagedata.add(new PackageInfo(resultSet.getString("place"),
                        Integer.toString(resultSet.getInt("noAdult")),
                        Integer.toString(resultSet.getInt("noChild")),
                        Integer.toString(resultSet.getInt("stayFee")),
                        Integer.toString(resultSet.getInt("foodFee")),
                        Integer.toString(resultSet.getInt("busFee")),
                        Integer.toString(resultSet.getInt("trainFee")),
                        Integer.toString(resultSet.getInt("airlinesFee")),
                        Integer.toString(resultSet.getInt("noDays")) + "day and " + Integer.toString(resultSet.getInt("noNights")) + "night"
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //END add packages data to TreeTable in view packages
    //START load data of selected packages from TreeTable to view label in view packages
    public void setPackageToLabel(String place) {
        String query = "select * from package where place='" + place + "';\n";
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
                dFood.setText(Integer.toString(resultSet.getInt("foodFee")));
                dBus.setText(Integer.toString(resultSet.getInt("busFee")));
                dTrain.setText(Integer.toString(resultSet.getInt("trainFee")));
                dAir.setText(Integer.toString(resultSet.getInt("airlinesFee")));
                dDays.setText(Integer.toString(resultSet.getInt("noDays")) + "day and " + Integer.toString(resultSet.getInt("noNights")) + "night");
                InputStream input = new ByteArrayInputStream(resultSet.getBytes("pImage"));
                Image imge = new Image(input);
                dImage.setImage(imge);
                booking = resultSet.getInt("packId");
                System.out.println("booking" + booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END load data of selected packages from TreeTable to view label in view packages

    public void Book() {
        ViewPlaces.getChildren().clear();
        try {
            ViewPlaces.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/PackageBookingContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //START prep transaction table
    public void getTransData() {

        String query = "select place,stayFee,booked.foodFee,travelFee,travelMode,travelDate,paid from package,booked where userId=" + userId + ";\n";
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
            } catch (SQLException ex) {
                Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //END prep transaction table

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainUserTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            mainUserTab.setTabMinWidth(((mainUserTab.getWidth()) / 2) - 5);
            mainUserTab.setTabMaxWidth(((mainUserTab.getWidth()) / 2) - 5);

        });

        //START for view packages.....................................................
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

        //END for view packages.....................................................
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
                JFXTreeTableColumn<TransInfo, String> tStayAm = new JFXTreeTableColumn<>("No. of Adults");
                tStayAm.setPrefWidth(80);
                tStayAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().stayFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tFoodAm = new JFXTreeTableColumn<>("No. of Kids");
                tFoodAm.setPrefWidth(80);
                tFoodAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().foodFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelAm = new JFXTreeTableColumn<>("Stay Cost");
                tTravelAm.setPrefWidth(80);
                tTravelAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelFee;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTotalAm = new JFXTreeTableColumn<>("Food Cost");
                tTotalAm.setPrefWidth(80);
                tTotalAm.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().total;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelMode = new JFXTreeTableColumn<>("Bus Cost");
                tTravelMode.setPrefWidth(80);
                tTravelMode.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelMode;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tTravelDate = new JFXTreeTableColumn<>("Train Cost");
                tTravelDate.setPrefWidth(80);
                tTravelDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TransInfo, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TransInfo, String> param) {
                        return param.getValue().getValue().travelDate;
                    }
                });
                JFXTreeTableColumn<TransInfo, String> tPaid = new JFXTreeTableColumn<>("Flight Cost");
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
                //END for view transactions.....................................................
            }
        });

        //START get place name of select row from tree table
        PackageInfoTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            TreeItem<PackageInfo> selectedItem = PackageInfoTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            TreeItem<PackageInfo> tpackage = PackageInfoTable.getSelectionModel().getSelectedItem();
            String stpackage = tpackage.getValue().getName().toString();
            stpackage = stpackage.substring(23, stpackage.length() - 1);
            setPackageToLabel(stpackage);
        });
        //END get place name of select row from tree table
    }
}
