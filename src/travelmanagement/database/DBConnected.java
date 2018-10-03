/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.database;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author dms
 */
public class DBConnected {

    Connection connection;

    public DBConnected() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }

    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
