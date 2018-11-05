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
public class TransInfo extends RecursiveTreeObject<TransInfo> {

    public StringProperty place;
    public StringProperty stayFee;
    public StringProperty foodFee;
    public StringProperty travelFee;
    public StringProperty total;
    public StringProperty travelMode;
    public StringProperty travelDate;
    public StringProperty paid;

    public TransInfo(String place, String stayFee, String foodFee, String travelFee, String total, String travelMode, String travelDate, String paid) {
        this.place = new SimpleStringProperty(place);
        this.stayFee = new SimpleStringProperty(stayFee);
        this.foodFee = new SimpleStringProperty(foodFee);
        this.travelFee = new SimpleStringProperty(travelFee);
        this.total = new SimpleStringProperty(total);
        this.travelMode = new SimpleStringProperty(travelMode);
        this.travelDate = new SimpleStringProperty(travelDate);
        this.paid = new SimpleStringProperty(paid);
    }
}
