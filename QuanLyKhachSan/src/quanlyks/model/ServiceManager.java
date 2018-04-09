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
 * @author trung
 */
public class ServiceManager {

    private Connection conn;
    private ArrayList<Service> arrService = new ArrayList<>();
    private PreparedStatement stmt;

    public ServiceManager() {
        conn = ConnectionDB.getConnection();
    }

    // Hàm lấy data từ database
    public ArrayList<Service> getListServices() {
        try {
            stmt = conn.prepareStatement("SELECT * FROM dich_vu WHERE Trang_Thai = 1");
            arrService.clear();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idService = rs.getString(1);
                String nameService = rs.getString(2);
                long priceOfService = rs.getLong(3);
                Service service = new Service(idService, nameService, priceOfService);
                arrService.add(service);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrService;
    }

    public int getNumberService() {
        int number = 0;
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM dich_vu WHERE Trang_Thai = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return number;
    }

    // Hàm tìm kiếm dịch vụ
    public ArrayList<Service> searchService(String key) {
        try {
            arrService.clear();
            stmt = conn.prepareStatement("SELECT * FROM dich_vu WHERE ID_DichVu LIKE '%" + key
                    + "%' OR Ten_Dich_Vu LIKE '%" + key + "%'" + "AND Trang_Thai = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idService = rs.getString(1);
                String nameService = rs.getString(2);
                long priceOfService = rs.getLong(3);
                Service service = new Service(idService, nameService, priceOfService);
                arrService.add(service);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrService;
    }

    // Hàm thêm dịch vụ
    public boolean addService(Service service) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("INSERT INTO dich_vu(ID_DichVu, Ten_Dich_Vu, Don_Gia,Trang_Thai) VALUES(?,?,?,?)");
            stmt.setString(1, service.getIdService());
            stmt.setString(2, service.getNameService());
            stmt.setDouble(3, service.getPriceOfService());
            stmt.setInt(4, 1);
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    // Hàm sửa thông tin phòng
    public boolean updateInformationOfService(Service service) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE dich_vu SET Ten_Dich_Vu = ?, Don_Gia = ? WHERE ID_DichVu = ?");
            stmt.setString(1, service.getNameService());
            stmt.setDouble(2, service.getPriceOfService());
            stmt.setString(3, service.getIdService());
            int result = stmt.executeUpdate();
            check = result != 0;

        } catch (SQLException ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    // Hàm xóa dịch vụ
    public boolean deleteService(String idService) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE dich_vu SET Trang_Thai =0 WHERE ID_DichVu = ?");
            stmt.setString(1, idService);
            int result = stmt.executeUpdate();
            check = result != 0;

        } catch (SQLException ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

}
