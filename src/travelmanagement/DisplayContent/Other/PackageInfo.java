/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.Other;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dms
 */
//START Class for View Package Table
public class PackageInfo extends RecursiveTreeObject<PackageInfo> {
    public StringProperty packageId;
    public StringProperty place;
    //StringProperty details;
    public StringProperty noAdult;
    public StringProperty noChild;
    public StringProperty stayAm;
    public StringProperty foodAm;
    public StringProperty busAm;
    public StringProperty trainAm;
    public StringProperty airAm;
    public StringProperty noDays;

    public PackageInfo(String packageId,String place, String noAdult, String noChild, String stayAm, String foodAm, String busAm, String trainAm, String airAm, String noDays) {
        this.packageId = new SimpleStringProperty(packageId);
        this.place = new SimpleStringProperty(place);
        //this.details = new SimpleStringProperty(details);
        this.noAdult = new SimpleStringProperty(noAdult);
        this.noChild = new SimpleStringProperty(noChild);
        this.stayAm = new SimpleStringProperty(stayAm);
        this.foodAm = new SimpleStringProperty(foodAm);
        this.busAm = new SimpleStringProperty(busAm);
        this.trainAm = new SimpleStringProperty(trainAm);
        this.airAm = new SimpleStringProperty(airAm);
        this.noDays = new SimpleStringProperty(noDays);
    }

    public StringProperty getId() {
        return packageId;
    }
}
    //END Class for View Package Table
