/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.UserPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
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
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class PackagePaymentContentController implements Initializable {

    int packageid, userId;
    int totals;
    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public PackagePaymentContentController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane payment;

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
    private RadioButton credit;

    @FXML
    private RadioButton debit;

    ToggleGroup paymentRadio = new ToggleGroup();

    SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");

    Pattern cardPattern = Pattern.compile("^[0-9]{16}$");
    Pattern cvvPattern = Pattern.compile("^[0-9]{3}$");

    @FXML
    void Cancel(ActionEvent event) {
        payment.getChildren().clear();
        try {
            payment.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/ViewPlacesContent.fxml")));
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
//update booked set paid="Yes" where packId=1 and userId=3;
        if (card.getText().isEmpty()) {
            isCard.setText("Card No. cannot be empty!");
        } else {
            isCard.setText("");
        }
        if (cvv.getText().isEmpty()) {
            isCvv.setText("Card No. cannot be empty!");
        } else {
            isCvv.setText("");
        }
        if (exp.getText().isEmpty()) {
            isExp.setText("Expiration Date cannot be empty!");
        } else {
            isExp.setText("");
        }
        if (card.getText().isEmpty() || cvv.getText().isEmpty() || exp.getText().isEmpty()) {
            return;
        } else {
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
                    }
                });
                taskdone.setActions(taskdonebtn);
                taskdonediag.show();
                payment.getChildren().clear();
                try {
                    payment.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/ViewPlacesContent.fxml")));
                    System.out.println("loaded");
                } catch (IOException ex) {
                    Logger.getLogger(PackagePaymentContentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException e) {
                System.out.println("Failed");
                e.printStackTrace();

            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        packageid = PackageBookingContentController.packageid;
        userId = PackageBookingContentController.userId;
        credit.setToggleGroup(paymentRadio);
        debit.setToggleGroup(paymentRadio);

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
//       exp.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9]*")) {
//                exp.setText(oldValue);
//            }
//            Matcher matcher = cvvPattern.matcher(exp.getText());
//            if (!matcher.matches()) {
//                isExp.setText("Invalid CVV Number!");
//            } else {
//                isExp.setText("");
//
//            }
//        });   
        exp.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            df.setLenient(false);
            Calendar now = Calendar.getInstance();
            try {
                df.parse(exp.getText());
                isExp.setText("True");

            } catch (ParseException ex) {
                isExp.setText("False");
            }

        });

    }

}
