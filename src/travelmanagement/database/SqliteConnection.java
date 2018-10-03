/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.JDBC.*;

/**
 *
 * @author dms
 */
public class SqliteConnection {

    public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:travel.db");

            return conn;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
            //123
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Connection createdb() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:travel.db");

//            Statement stmt = conn.createStatement();
//            String users = "CREATE TABLE IF NOT EXISTS `users` (\n"
//                    + "`name`	TEXT NOT NULL,	"
//                    + "`username`	TEXT NOT NULL,\n"
//                    + "	`password`	TEXT NOT NULL,\n"
//                    + "	`uid`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n"
//                    + "	`address`	TEXT NOT NULL,\n"
//                    + "	`email`	TEXT NOT NULL,\n"
//                    + "	`mobile`	INTEGER NOT NULL\n"
//                    + ");";
//            stmt.execute(users);
//            String accounts = "CREATE TABLE IF NOT EXISTS `accounts` (\n"
//                    + "	`acc_no`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE  NOT NULL ,\n"
//                    + "	`acc_type`	TEXT NOT NULL,\n"
//                    + "	`acc_details`	TEXT,\n"
//                    + "	`acc_id`	INTEGER NOT NULL,\n"
//                    + "	FOREIGN KEY (acc_id) REFERENCES users(uid)\n"
//                    + ");";
//            stmt.execute(accounts);
//            String withdraw = "CREATE TABLE IF NOT EXISTS `withdraw` (\n"
//                    + "	`wAmount`	TEXT NOT NULL,\n"
//                    + "	`waid`	INTEGER NOT NULL,\n"
//                    + "	FOREIGN KEY (waid) REFERENCES accounts(acc_no)\n"
//                    + ");";
//            stmt.execute(withdraw);
//            String deposit = "CREATE TABLE IF NOT EXISTS `deposit` (\n"
//                    + "	`dAmount`	TEXT NOT NULL,\n"
//                    + "	`daid`	INTEGER NOT NULL,\n"
//                    + "	FOREIGN KEY (daid) REFERENCES accounts(acc_no)\n"
//                    + ");";
//            stmt.execute(deposit);
//
//            String sql1 = "INSERT OR IGNORE INTO `users` (username,uid,password,address,email,mobile) VALUES ('dms',12,'123','asd','@gmail',768),\n"
//                    + " ('dm','123','asd','@gmail',768);";
//            stmt.execute(sql1);
            return conn;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
