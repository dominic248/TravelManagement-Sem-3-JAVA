/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.LoginRegister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class RegisterModel {

    Connection connection;

    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public RegisterModel() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }

    public boolean ifUsernameExists(String username) throws SQLException {
        String query = "select * from users where username='"+username+"';\n";
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
        }

    }

    public boolean isRegister(String name, String username, String password, int mobile, String email) throws SQLException {
        String query = "INSERT INTO `users` (name,username,password,mobile,email,userDate) VALUES ('"+name+"','"+username+"','"+password+"',"+mobile+",'"+email+"',datetime('now', 'localtime'));\n";
        try {
            preparedStatement = connection.prepareStatement(query);

            System.out.println(query);
            System.out.println("Hey" + preparedStatement.execute());
            return true;

        } catch (SQLException e) {
            System.out.println("Error!");
            return false;
        }
    }

}
