/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlyks.connectDataBase.ConnectionDB;

/**
 *
 * @author tungb_000
 */
public class LoginManager {
    
    Connection conn;
    
    public LoginManager(){
        conn = ConnectionDB.getConnection();
    }
    
    public int checkLogin(String user, String pass){
        try {
            PreparedStatement stmt = conn.prepareStatement("Select * from Nhan_Vien Where lower(user_name) =? and lower(password)=?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Login login = new Login(rs.getString("user_name"), rs.getString("password"), rs.getString("Bo_phan_lam_viec"));
                String login_Type = rs.getString("Bo_phan_lam_viec");
                if (login_Type.equalsIgnoreCase("Quản lý khách sạn")){
                    return 1;
                } else if (login_Type.equalsIgnoreCase("Lễ tân")){
                    return 2;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
