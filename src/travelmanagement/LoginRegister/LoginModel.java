/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.LoginRegister;

import java.sql.*;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class LoginModel {

    Connection connection;
    public static int userId;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    
    
    public LoginModel() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }

    public boolean isLogin(String username, String password) throws SQLException {
        
        String query = "select * from users where username='"+username+"' and password ='"+password+"';\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("userId"));
                userId=resultSet.getInt("userId");
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
    
    public void setLoginTime(){
        String query = "INSERT INTO `activity` (aid,aDate) VALUES ("+userId+",datetime('now', 'localtime'));\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error in DateTime");
        } finally {
            System.out.println("Executed!");
        }
    }
}
