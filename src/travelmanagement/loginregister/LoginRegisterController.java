/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.loginregister;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import travelmanagement.TravelManagement;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author komal
 */
public class LoginRegisterController implements Initializable {

    public static String usertype = null;
    Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    Pattern usernamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
    
    static Connection connection = TravelManagement.connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    public static int userId;

    @FXML
    private Tab AdminLoginTab;

    @FXML
    private Label RisName;

    @FXML
    private JFXTabPane TypeTab;

    @FXML
    private Label RisEmail;

    @FXML
    private Tab UserRegisterTab;

    @FXML
    private Label RisUsername;

    @FXML
    private JFXButton Alogin_btn;

    @FXML
    private JFXTextField Lusername;

    @FXML
    private JFXPasswordField Rpassword;

    @FXML
    private Tab UserTab;

    @FXML
    private Label Aisusername;

    @FXML
    private Label Lispassword;

    @FXML
    private Label RisMobile;

    @FXML
    private JFXTextField Ausername;

    @FXML
    private JFXButton Rregister_btn;

    @FXML
    private Tab UserLoginTab;

    @FXML
    private JFXButton Llogin_btn;

    @FXML
    private JFXTextField Remail;

    @FXML
    private Label Lisusername;

    @FXML
    private JFXTextField Rname;

    @FXML
    private AnchorPane LrootPane;

    @FXML
    private JFXTabPane UserLoginRegisterTab;

    @FXML
    private Tab AdminTab;

    @FXML
    private StackPane root;

    @FXML
    private Label RisPassword;

    @FXML
    private JFXPasswordField Apassword;

    @FXML
    private JFXPasswordField Lpassword;

    @FXML
    private JFXTextField Rusername;

    @FXML
    private JFXTextField Rmobile;

    @FXML
    private JFXTabPane AdminLogin;

    @FXML
    private Label Aispassword;

    @FXML
    public void Login(ActionEvent event) throws SQLException, IOException {
        Stage stage;
        Parent loader;
        if (Lusername.getText().isEmpty() && Lpassword.getText().isEmpty()) {
            Lisusername.setText("Username cannot be Empty");
            Lispassword.setText("Password cannot be Empty");
            return;
        } else if (Lpassword.getText().isEmpty()) {
            Lispassword.setText("Password cannot be Empty");
            Lisusername.setText("");
            return;
        } else if (Lusername.getText().isEmpty()) {
            Lisusername.setText("Username cannot be Empty");
            Lispassword.setText("");
            return;
        } else {
            Lisusername.setText("");
            Lispassword.setText("");
            try {
                if (isLogin(Lusername.getText(), Lpassword.getText())) {
                    Lispassword.setText("Username and password is correct");
                    usertype = "user";
                    stage = (Stage) Llogin_btn.getScene().getWindow();
                    loader = FXMLLoader.load(getClass().getResource("/travelmanagement/display/DisplayPage.fxml"));
                    Scene scene = new Scene(loader);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    Lispassword.setText("Username or password is wrong");
                }
            } catch (SQLException e) {
                Lispassword.setText("Username or password is wrong");
                e.printStackTrace();
            }
        }
    }

    public void Register(ActionEvent event) throws SQLException {
        if (Rusername.getText().isEmpty()) {
            RisUsername.setText("Username cannot be blank!");
        } else if (ifUsernameExists(Rusername.getText())) {
            RisUsername.setText("Username already exists!");
        } else {
            RisUsername.setText("");
        }
        if (Rname.getText().isEmpty()) {
            RisName.setText("Name cannot be blank!");
        } else {
            RisName.setText("");
        }
        if (Rpassword.getText().isEmpty()) {
            RisPassword.setText("Password cannot be blank!");
        } else {
            RisPassword.setText("");
        }
        if (Remail.getText().isEmpty()) {
            RisEmail.setText("Email ID cannot be blank!");
        } else {
            RisEmail.setText("");
        }
        if (Rmobile.getText().isEmpty()) {
            RisMobile.setText("Mobile No. cannot be blank!");
        } else {
            RisMobile.setText("");
        }

        if (Rname.getText().isEmpty() || Rusername.getText().isEmpty() || Rpassword.getText().isEmpty()
                || Rmobile.getText().isEmpty() || Remail.getText().isEmpty()
                || ifUsernameExists(Rusername.getText())) {
            return;
        } else {
            if (isRegister(Rname.getText(), Rusername.getText(), Rpassword.getText(), Rmobile.getText(), Remail.getText())) {
                System.out.println("Done");
                Rusername.setText("");
                Rname.setText("");
                Rpassword.setText("");
                Rmobile.setText("");
                Remail.setText("");
                RisUsername.setText("");
                RisEmail.setText("");

                JFXDialogLayout taskdone = new JFXDialogLayout();
                taskdone.setHeading(new Text("Successful!"));

                taskdone.setBody(new Text("Account created successfully!"));
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
                UserLoginRegisterTab.getSelectionModel().select(0);
            }
        }
    }

    public void AdminLogin(ActionEvent event) throws SQLException, IOException {
        Stage stage;
        Parent loader;
        if (Ausername.getText().isEmpty() && Apassword.getText().isEmpty()) {
            Aisusername.setText("Username cannot be Empty");
            Aispassword.setText("Password cannot be Empty");
            return;
        } else if (Apassword.getText().isEmpty()) {
            Aispassword.setText("Password cannot be Empty");
            Aisusername.setText("");
            return;
        } else if (Ausername.getText().isEmpty()) {
            Aisusername.setText("Username cannot be Empty");
            Aispassword.setText("");
            return;
        } else {
            Aisusername.setText("");
            Aispassword.setText("");
            if (Ausername.getText().equals("admin") && Apassword.getText().equals("admin")) {
                Aispassword.setText("Username and password is correct");
                usertype = "admin";
                stage = (Stage) Alogin_btn.getScene().getWindow();
                loader = FXMLLoader.load(getClass().getResource("/travelmanagement/display/DisplayPage.fxml"));
                Scene scene = new Scene(loader);
                stage.setScene(scene);
                stage.show();
                //loginModel.setLoginTime();

            } else {
                Aispassword.setText("Username or password is wrong");
            }
        }
    }

    public boolean ifUsernameExists(String username) throws SQLException {
        if (connection.isClosed()) {
            connection = SqliteConnection.connector();
        }
        String query = "select * from users where username='" + username + "';\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            if (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
    }

    public boolean isRegister(String name, String username, String password, String mobile, String email) throws SQLException {
        if (connection.isClosed()) {
            connection = SqliteConnection.connector();
        }
        String query = "INSERT INTO `users` (name,username,password,mobile,email,userDate) VALUES ('" + name + "','" + username + "','" + password + "'," + mobile + ",'" + email + "',datetime('now', 'localtime'));\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error!");
            return false;
        } finally {
            preparedStatement.close();

            connection.close();
        }
    }

    public boolean isLogin(String username, String password) throws SQLException {
        if (connection.isClosed()) {
            connection = SqliteConnection.connector();
        }
        String query = "select * from users where username='" + username + "' and password ='" + password + "';\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("userId"));
                userId = resultSet.getInt("userId");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            TypeTab.setTabMinWidth(((TypeTab.getWidth()) / 2) - 5);
            TypeTab.setTabMaxWidth(((TypeTab.getWidth()) / 2) - 5);

        });
        UserLoginRegisterTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            UserLoginRegisterTab.setTabMinWidth(((UserLoginRegisterTab.getWidth()) / 2) - 5);
            UserLoginRegisterTab.setTabMaxWidth(((UserLoginRegisterTab.getWidth()) / 2) - 5);

        });
        AdminLogin.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            AdminLogin.setTabMinWidth(((AdminLogin.getWidth())) - 11);
            AdminLogin.setTabMaxWidth(((AdminLogin.getWidth())) - 11);

        });

        Rmobile.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                Rmobile.setText(oldValue);
            }
        });

        Rusername.textProperty().addListener((observable, oldValue, newValue) -> {

            Matcher matcher = usernamePattern.matcher(Rusername.getText());
            if (!matcher.matches()) {
                RisUsername.setText("Invalid Username!");
            } else {
                RisUsername.setText("");

            }

        });
        Remail.textProperty().addListener((observable, oldValue, newValue) -> {
            Matcher matcher = emailPattern.matcher(Remail.getText());
            if (!matcher.matches()) {
                RisEmail.setText("Invalid Email!");
            } else {
                RisEmail.setText("");
            }
        });
    }
}
