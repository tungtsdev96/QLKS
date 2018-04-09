/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.connectDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlyks.controller.SettingController;

/**
 *
 * @author tungb_000
 */
public class ConnectionDB {

    private static Connection conn;
    public static SettingController setting = new SettingController();

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/qlks";
            String user = setting.getConnectionProperty().get(0);
            String pass = setting.getConnectionProperty().get(1);
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

}
