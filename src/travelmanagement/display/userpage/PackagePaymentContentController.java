/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.display.userpage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import travelmanagement.TravelManagement;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author komal
 */
public class PackagePaymentContentController implements Initializable {

    int packageid, userId;
    int totals;
    static Connection connection = TravelManagement.connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    ToggleGroup paymentRadio = new ToggleGroup();

    SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");

    Pattern cardPattern = Pattern.compile("^[0-9]{16}$");
    Pattern cvvPattern = Pattern.compile("^[0-9]{3}$");

    @FXML
    private AnchorPane payment;

    @FXML
    private StackPane root;

    @FXML
    private RadioButton credit;

    @FXML
    private RadioButton debit;

    @FXML
    private JFXTextField card;

    @FXML
    private JFXTextField cvv;

    @FXML
    private JFXTextField exp;

    @FXML
    private Label total;

    @FXML
    private Label isCard;

    @FXML
    private Label isCvv;

    @FXML
    private Label isExp;

    @FXML
    void Cancel(ActionEvent event) {

        payment.getChildren().clear();
        try {
            payment.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/display/userpage/ViewPlacesContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(PackagePaymentContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Pay(ActionEvent event) {
        if (credit.isSelected() || debit.isSelected()) {
            credit.setStyle("-fx-background-color: rgba(255,255,255, 0.7);");
            debit.setStyle("-fx-background-color: rgba(255,255,255, 0.7);");
        } else {
            credit.setStyle("-fx-background-color: RED;");
            debit.setStyle("-fx-background-color: RED;");
        }
        if (card.getText().length() != 16) {
            isCard.setText("Card No. can't be less than 16 nos!");
        } else {
            isCard.setText("");
        }
        if (cvv.getText().length() != 3) {
            isCvv.setText("CVV No. can't be less than 3 nos!");
        } else {
            isCvv.setText("");
        }
        if (exp.getText().isEmpty()) {
            isExp.setText("Expiration Date cannot be empty!");
        } else {
            isExp.setText("");
        }
        if (card.getText().length() != 16 || cvv.getText().length() != 3 || exp.getText().isEmpty()) {
            return;
        } else {
            try {
                if (connection.isClosed()) {
                    connection = SqliteConnection.connector();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PackagePaymentContentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query = "update booked set paid=\"Yes\" where packId=" + packageid + " and userId=" + userId + ";\n";
            System.out.println(query);
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                System.out.println("Done");
                JFXDialogLayout taskdone = new JFXDialogLayout();
                taskdone.setHeading(new Text("Successful!"));

                taskdone.setBody(new Text("Payment Done"));
                JFXDialog taskdonediag = new JFXDialog(root, taskdone, JFXDialog.DialogTransition.CENTER);
                JFXButton taskdonebtn = new JFXButton("Okay!");
                taskdonebtn.setId("buttons");
                taskdonebtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        taskdonediag.close();
                        payment.getChildren().clear();
                        try {
                            payment.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/display/userpage/ViewPlacesContent.fxml")));
                            System.out.println("loaded");
                        } catch (IOException ex) {
                            Logger.getLogger(PackagePaymentContentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                taskdone.setActions(taskdonebtn);
                taskdonediag.show();

            } catch (SQLException e) {
                System.out.println("Failed");
                JFXDialogLayout taskdone = new JFXDialogLayout();
                taskdone.setHeading(new Text("Failed!"));

                taskdone.setBody(new Text("Payment Failed"));
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
                e.printStackTrace();

            } finally {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PackagePaymentContentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        packageid = PackageBookingContentController.packageid;
        userId = PackageBookingContentController.userId;
        credit.setToggleGroup(paymentRadio);
        debit.setToggleGroup(paymentRadio);
        paymentRadio.selectToggle(credit);

        totals = PackageBookingContentController.total;
        total.setText(Integer.toString(totals));
        card.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                card.setText(oldValue);
            }
            Matcher matcher = cardPattern.matcher(card.getText());
            if (!matcher.matches()) {
                isCard.setText("Invalid Card Number!");
            } else {
                isCard.setText("");

            }
        });
        cvv.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                cvv.setText(oldValue);
            }
            Matcher matcher = cvvPattern.matcher(cvv.getText());
            if (!matcher.matches()) {
                isCvv.setText("Invalid CVV Number!");
            } else {
                isCvv.setText("");

            }
        });

        exp.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            df.setLenient(false);
            Calendar now = Calendar.getInstance();
            try {
                df.parse(exp.getText());
                isExp.setText("");

            } catch (ParseException ex) {
                isExp.setText("Incorrect! Format:MM/YYYY");
            }
        });
    }
}
