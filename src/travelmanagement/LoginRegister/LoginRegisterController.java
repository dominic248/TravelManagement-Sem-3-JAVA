/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.LoginRegister;

import javafx.scene.layout.Pane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import travelmanagement.database.DBConnected;
import javafx.stage.Stage;

/**
 *
 * @author dms
 */
public class LoginRegisterController implements Initializable {
    public static String usertype=null;
    Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    Pattern usernamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
    LoginModel loginModel = new LoginModel();
    DBConnected dbConnected = new DBConnected();
    RegisterModel registerModel = new RegisterModel();

     @FXML
    private Tab AdminLoginTab;

    @FXML
    private Label RisName;

    @FXML
    private JFXTabPane TypeTab;

    @FXML
    private Label LisConnected;

    @FXML
    private Label RisEmail;

    @FXML
    private Label LisConnected1;

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
    private Label RisConnected;

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
                if (loginModel.isLogin(Lusername.getText(), Lpassword.getText())) {
                    Lispassword.setText("Username and password is correct");
                    usertype="user";
                    stage = (Stage) Llogin_btn.getScene().getWindow();
                    loader = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/DisplayPage.fxml"));
                    Scene scene = new Scene(loader);
                    stage.setScene(scene);
                    stage.show();
                    loginModel.setLoginTime();
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
        } else if (registerModel.ifUsernameExists(Rusername.getText())) {
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
                || registerModel.ifUsernameExists(Rusername.getText())) {
            return;
        } else {
            if (registerModel.isRegister(Rname.getText(),Rusername.getText(), Rpassword.getText(),Integer.parseInt(Rmobile.getText()),Remail.getText())) {
                System.out.println("Done");
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
                usertype="admin";
                stage = (Stage) Alogin_btn.getScene().getWindow();
                loader = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/DisplayPage.fxml"));
                Scene scene = new Scene(loader);
                stage.setScene(scene);
                stage.show();
                //loginModel.setLoginTime();
                
                
            } else {
                Aispassword.setText("Username or password is wrong");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            TypeTab.setTabMinWidth(((TypeTab.getWidth()) / 2)-5);
            TypeTab.setTabMaxWidth(((TypeTab.getWidth()) / 2)-5);

        });
        UserLoginRegisterTab.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            UserLoginRegisterTab.setTabMinWidth(((UserLoginRegisterTab.getWidth()) / 2)-5);
            UserLoginRegisterTab.setTabMaxWidth(((UserLoginRegisterTab.getWidth()) / 2)-5);

        });
        AdminLogin.widthProperty().addListener((observable, oldValue, newValue)
                -> {
            AdminLogin.setTabMinWidth(((AdminLogin.getWidth()))-11);
            AdminLogin.setTabMaxWidth(((AdminLogin.getWidth()))-11);

        });
        

        Rmobile.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                Rmobile.setText(oldValue);
            }
        });

        if (dbConnected.isDbConnected()) {
            LisConnected.setText("Connected");
        } else {
            LisConnected.setText("Disconnected");
        }

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
