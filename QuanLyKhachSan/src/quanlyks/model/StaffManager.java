/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlyks.connectDataBase.ConnectionDB;

/**
 *
 * @author tungb_000
 */
public class StaffManager {

    Connection conn;
    private ArrayList<Staff> listStaff = new ArrayList<>();
    private PreparedStatement stmt;

    public StaffManager() {
        conn = ConnectionDB.getConnection();
    }

    public int getNumberStaff() {
        int number = 0;
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM nhan_vien");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return number;
    }

    public ArrayList<String> getListOffice() {
        ArrayList<String> arrOffice = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT DISTINCT Bo_phan_lam_viec FROM nhan_vien WHERE TrangThai = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String office = rs.getString(1);
                arrOffice.add(office);
            }
        } catch (Exception e) {
        }
        return arrOffice;
    }

    public int getNumberStaffByOffice(String office) {
        int number = 0;
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM nhan_vien WHERE TrangThai = 1 AND Bo_phan_lam_viec = ?");
            stmt.setString(1, office);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return number;
    }

    public Staff getStaffByIdStaff(String idStaff) {
        Staff staff = null;
        try {
            stmt = conn.prepareStatement("SELECT * from Nhan_Vien WHERE ID_Nhan_Vien =?");
            stmt.setString(1, idStaff);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = idStaff;
                String nameStaff = rs.getString(2);
                String dateOfBirth = rs.getString(3);
                String country = rs.getString(4);
                String sex = rs.getString(5);
                String identify = rs.getString(6);
                String phoneNumber = rs.getString(7);
                String email = rs.getString(8);
                String office = rs.getString(9);
                String userName = rs.getString(10);
                String password = rs.getString(11);
                staff = new Staff(idStaff, nameStaff, dateOfBirth, country, sex, identify, phoneNumber, email, office, userName, password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }

    public ArrayList<Staff> getListStaff() {
        try {
            listStaff.clear();
            stmt = conn.prepareStatement("SELECT * FROM qlks.nhan_vien WHERE TrangThai ='1'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idStaff = rs.getString(1);
                String nameStaff = rs.getString(2);
                String dateOfBirth = rs.getString(3);
                String country = rs.getString(4);
                String sex = rs.getString(5);
                String identify = rs.getString(6);
                String phoneNumber = rs.getString(7);
                String email = rs.getString(8);
                String office = rs.getString(9);
                String userName = rs.getString(10);
                String password = rs.getString(11);
                Staff s = new Staff(idStaff, nameStaff, dateOfBirth, country, sex, identify, phoneNumber, email, office, userName, password);
                listStaff.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listStaff;
    }

    public ArrayList<Staff> searchStaff(String key) {
        try {
            listStaff.clear();
            stmt = conn.prepareStatement("SELECT * FROM nhan_vien WHERE ID_Nhan_Vien LIKE '%" + key + "%' OR Ho_Ten LIKE '%" + key + "%'" + "AND TrangThai='1'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idStaff = rs.getString(1);
                String nameStaff = rs.getString(2);
                String dateOfBirth = rs.getString(3);
                String country = rs.getString(4);
                String sex = rs.getString(5);
                String identify = rs.getString(6);
                String phoneNumber = rs.getString(7);
                String email = rs.getString(8);
                String office = rs.getString(9);
                String userName = rs.getString(10);
                String password = rs.getString(11);
                Staff s = new Staff(idStaff, nameStaff, dateOfBirth, country, sex, identify, phoneNumber, email, office, userName, password);
                listStaff.add(s);
            }
        } catch (SQLException e) {
        }
        return listStaff;
    }

    public boolean addStaff(Staff staff) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("INSERT INTO nhan_vien(ID_Nhan_Vien,Ho_Ten,Ngay_Sinh,Que_Quan,Gioi_Tinh,SoCMT,So_Dien_Thoai,Email,Bo_phan_lam_viec,user_name,Password,TrangThai) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, staff.getIdStaff());
            stmt.setString(2, staff.getNameStaff());
            stmt.setString(3, staff.getDateOfBirth());
            stmt.setString(4, staff.getCountry());
            stmt.setString(5, staff.getSex());
            stmt.setString(6, staff.getIdentify());
            stmt.setString(7, staff.getPhoneNumber());
            stmt.setString(8, staff.getEmail());
            stmt.setString(9, staff.getOffice());
            stmt.setString(10, staff.getUserName());
            stmt.setString(11, staff.getPassword());
            stmt.setInt(12, 1);
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }

    public boolean updateInformationOfStaff(Staff staff) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE nhan_vien SET Ho_Ten=?, Ngay_Sinh=?,Que_Quan=?,Gioi_Tinh=?,SoCMT=?,So_Dien_Thoai=?,Email=?,Bo_phan_lam_viec=? WHERE ID_Nhan_Vien=?");
            stmt.setString(1, staff.getNameStaff());
            stmt.setString(2, staff.getDateOfBirth());
            stmt.setString(3, staff.getCountry());
            stmt.setString(4, staff.getSex());
            stmt.setString(5, staff.getIdentify());
            stmt.setString(6, staff.getPhoneNumber());
            stmt.setString(7, staff.getEmail());
            stmt.setString(8, staff.getOffice());
            stmt.setString(9, staff.getIdStaff());
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }

    public boolean deleteStaff(String idStaff) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE nhan_vien SET TrangThai ='0'"
                    + " WHERE ID_Nhan_Vien=?");
            stmt.setString(1, idStaff);
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }

}
