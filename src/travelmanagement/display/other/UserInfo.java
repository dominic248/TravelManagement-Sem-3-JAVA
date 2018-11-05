/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.display.other;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author komal
 */
public class UserInfo extends RecursiveTreeObject<UserInfo> {

    public StringProperty id;
    public StringProperty name;
    public StringProperty username;
    public StringProperty mobile;
    public StringProperty email;
    public StringProperty date;

    public UserInfo(String id, String name, String username, String mobile, String email, String date) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.mobile = new SimpleStringProperty(mobile);
        this.email = new SimpleStringProperty(email);
        this.date = new SimpleStringProperty(date);
    }
}
