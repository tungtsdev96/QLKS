/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import java.sql.Connection;
import java.sql.Date;
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
public class CustomerManager {

    Connection conn;
    private ArrayList<Customer> listCT = new ArrayList<Customer>();
    private PreparedStatement st;

    public CustomerManager() {
        conn = new ConnectionDB().getConnection();
    }

    public boolean updateStatusCustomerInListCustomer(String identify) {
        boolean check = false;
        try {
            st = conn.prepareStatement("UPDATE khach_hang SET TrangThai = '1' WHERE So_CMT =?");
            st.setString(1, identify);
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public boolean addCustomer(Customer customer) {
        boolean check = false;
        try {
            Customer Customer = customer;
            st = conn.prepareStatement("insert into khach_hang values(?,?,?,?,?,?,?,?,?)");
            st.setString(1, Customer.getIdCustomer());
            st.setString(2, Customer.getNameCustomer());
            st.setString(3, Customer.getDateOfBirth());
            st.setString(4, Customer.getSex());
            st.setString(5, Customer.getNational());
            st.setString(6, Customer.getIdentify());
            st.setString(7, Customer.getPhoneNumber());
            st.setString(8, Customer.getEmail());
            st.setInt(9, 1);
            int result = st.executeUpdate();
            if (check) {
                System.out.println("ddddd");
            }
            check = result != 0;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return check;
    }

    public Customer getCustomerById(String idCustomer) {
        Customer customer = null;
        try {
            st = conn.prepareStatement("select * from khach_hang where ID_Khach_Hang = ? ");
            st.setString(1, idCustomer);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String hoTen = rs.getString(2);
                String ngaySinh = rs.getString(3);
                String gioiTinh = rs.getString(4);
                String quocTich = rs.getString(5);
                String soCMT = rs.getString(6);
                String soDienThoai = rs.getString(7);
                String email = rs.getString(8);
                customer = new Customer(id, hoTen, ngaySinh, gioiTinh, quocTich, soCMT, soDienThoai, email);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return customer;
    }

    public int countCustomerByNational(String national) {
        int count = 0;
        try {
            st = conn.prepareStatement("SELECT COUNT(*) FROM khach_hang WHERE TrangThai = 1 AND Quoc_Tich =?");
            st.setString(1, national);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return count;
    }

    public ArrayList<String> getListNational() {
        ArrayList<String> arrNational = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT DISTINCT Quoc_Tich FROM khach_hang WHERE TrangThai =1");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String national = rs.getString(1);
                arrNational.add(national);
            }
        } catch (Exception e) {
        }
        return arrNational;
    }

    public ArrayList<Customer> getListCustomer() {
        try {
            listCT.clear();
            st = conn.prepareStatement("select * from khach_hang WHERE TrangThai = 1");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String idCustomer = rs.getString(1);
                String hoTen = rs.getString(2);
                String ngaySinh = rs.getString(3);
                String gioiTinh = rs.getString(4);
                String quocTich = rs.getString(5);
                String soCMT = rs.getString(6);
                String soDienThoai = rs.getString(7);
                String email = rs.getString(8);
                Customer customer = new Customer(idCustomer, hoTen, ngaySinh, gioiTinh, quocTich, soCMT, soDienThoai, email);
                listCT.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return listCT;
    }

    public boolean updateInformationOfCustomer(Customer customer) {
        boolean check = false;
        try {
            st = conn.prepareStatement("UPDATE khach_hang SET Ho_Ten=?, Ngay_Sinh=?, Gioi_Tinh=?, Quoc_Tich=?, So_CMT = ?, So_Đien_Thoai = ?,Email = ? WHERE ID_Khach_Hang=?");
            st.setString(1, customer.getNameCustomer());
            st.setString(2, customer.getDateOfBirth());
            st.setString(3, customer.getSex());
            st.setString(4, customer.getNational());
            st.setString(5, customer.getIdentify());
            st.setString(6, customer.getPhoneNumber());
            st.setString(7, customer.getEmail());
            st.setString(8, customer.getIdCustomer());
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }

    public boolean deleteCustomer(String idCustomer) {
        boolean check = false;
        try {
            st = conn.prepareStatement("UPDATE khach_hang SET TrangThai = '0' WHERE ID_Khach_Hang =?");
            st.setString(1, idCustomer);
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public ArrayList<Customer> listSearchCustomer(String query) {
        try {
            listCT.clear();
            st = conn.prepareStatement("SELECT * FROM khach_hang WHERE ID_Khach_Hang LIKE '%" + query + "%' OR Ho_Ten LIKE '%" + query + "%' and TrangThai ='1'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String idCustomer = rs.getString(1);
                String hoTen = rs.getString(2);
                String ngaySinh = rs.getString(3);
                String gioiTinh = rs.getString(4);
                String quocTich = rs.getString(5);
                String soCMT = rs.getString(6);
                String soDienThoai = rs.getString(7);
                String email = rs.getString(8);
                Customer customer = new Customer(idCustomer, hoTen, ngaySinh, gioiTinh, quocTich, soCMT, soDienThoai, email);
                listCT.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return listCT;
    }
}
