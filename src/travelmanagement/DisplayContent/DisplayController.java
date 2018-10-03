package travelmanagement.DisplayContent;


import java.io.IOException;
import java.net.URL;


import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import travelmanagement.LoginRegister.LoginRegisterController;



public class DisplayController implements Initializable {
    static String usertype=LoginRegisterController.usertype;
    DisplayModel display=new DisplayModel();
    
    @FXML
    private Label mainHeading;
    
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Pane mainHeader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if("user".equals(usertype)){
                AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/UserPageContent.fxml"));
                mainAnchorPane.getChildren().add(newLoadedPane);
            }else if("admin".equals(usertype)){
                AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/AdminPage/AdminPageContent.fxml"));
                mainAnchorPane.getChildren().add(newLoadedPane);
            }
        } catch (IOException ex) {
            Logger.getLogger(DisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
