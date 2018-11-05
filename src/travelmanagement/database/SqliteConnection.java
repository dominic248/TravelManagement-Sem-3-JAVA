/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author komal
 */
public class SqliteConnection {

    static Connection conn = null;
    static PreparedStatement preparedStatement = null;

    public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:travel.db");

            return conn;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void createdb() {

        try {
            if (conn.isClosed()) {
                conn = SqliteConnection.connector();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String users = "CREATE TABLE IF NOT EXISTS `users` (\n"
                    + "	`userId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                    + "	`name`	TEXT NOT NULL,\n"
                    + "	`username`	TEXT NOT NULL,\n"
                    + "	`password`	TEXT NOT NULL,\n"
                    + "	`mobile`	TEXT NOT NULL,\n"
                    + "	`email`	TEXT NOT NULL,\n"
                    + "	`userDate`	TEXT NOT NULL\n"
                    + ");";
            preparedStatement = conn.prepareStatement(users);
            preparedStatement.execute();

            String packages = "CREATE TABLE IF NOT EXISTS `package` (\n"
                    + "	`packId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                    + "	`place`	TEXT NOT NULL UNIQUE,\n"
                    + "	`pDetails`	TEXT NOT NULL,\n"
                    + "	`noAdult`	INTEGER NOT NULL,\n"
                    + "	`noChild`	INTEGER NOT NULL,\n"
                    + "	`stayFee`	INTEGER NOT NULL,\n"
                    + "	`foodFee`	INTEGER NOT NULL,\n"
                    + "	`busFee`	INTEGER NOT NULL,\n"
                    + "	`trainFee`	INTEGER NOT NULL,\n"
                    + "	`airlinesFee`	INTEGER NOT NULL,\n"
                    + "	`noDays`	INTEGER NOT NULL,\n"
                    + "	`noNights`	INTEGER NOT NULL,\n"
                    + "	`pImage`	BLOB NOT NULL\n"
                    + ");";
            preparedStatement = conn.prepareStatement(packages);
            preparedStatement.execute();

            String booked = "CREATE TABLE IF NOT EXISTS `booked` (\n"
                    + "	`packId`	INTEGER NOT NULL,\n"
                    + "	`userId`	INTEGER NOT NULL,\n"
                    + "	`foodFee`	INTEGER NOT NULL,\n"
                    + "	`travelMode`	TEXT NOT NULL,\n"
                    + "	`travelFee`	INTEGER NOT NULL,\n"
                    + "	`travelDate`	text NOT NULL,\n"
                    + "	`paid`	TEXT NOT NULL DEFAULT No,\n"
                    + "	FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) on delete cascade on update cascade\n"
                    + ");";
            preparedStatement = conn.prepareStatement(booked);
            preparedStatement.execute();

            String sql = "INSERT INTO `users` (userId,name,username,password,mobile,email,userDate) VALUES \n"
                    + " (1,'Komal','komal999','123','7299872857','tkomal999@gmail.com',datetime('now', 'localtime'));";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
            conn.commit();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
