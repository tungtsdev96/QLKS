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
import quanlyks.model.Room;
import quanlyks.connectDataBase.ConnectionDB;

/**
 *
 * @author tungb_000
 */
public class RoomManager {

    private Connection conn;
    private ArrayList<Room> arrRoom = new ArrayList<>();
    private PreparedStatement stmt;

    public RoomManager() {
        conn = ConnectionDB.getConnection();
    }

    public ArrayList<Room> getEmptyRoom() {
        try {
            stmt = conn.prepareStatement("SELECT * FROM phong WHERE Tinh_Trang_Phong=?");
            stmt.setString(1, "Đang trống");
            arrRoom.clear();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idRoom = rs.getInt(1);
                String roomType = rs.getString(2);
                String roomStatus = rs.getString(3);
                int maxOccupancy = rs.getInt(4);
                long priceOfRoom = rs.getLong(5);
                Room room = new Room(idRoom, roomType, roomStatus, maxOccupancy, priceOfRoom);
                arrRoom.add(room);
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return arrRoom;
    }

    public ArrayList<Room> searchRoom(String key) {
        try {
            arrRoom.clear();
            stmt = conn.prepareStatement("SELECT * FROM phong WHERE ID_Phong LIKE '%" + key + "%' OR Loai_Phong LIKE '%" + key + "%'" + "AND Tinh_Trang_Phong <> 'Đã xóa'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idRoom = rs.getInt(1);
                String roomType = rs.getString(2);
                String roomStatus = rs.getString(3);
                int maxOccupancy = rs.getInt(4);
                long priceOfRoom = rs.getLong(5);
                Room room = new Room(idRoom, roomType, roomStatus, maxOccupancy, priceOfRoom);
                arrRoom.add(room);
            }
        } catch (SQLException e) {
        }
        return arrRoom;
    }

    public int getNumberRoomType(String typeRoom) {
        int number = 0;
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM phong WHERE Tinh_Trang_Phong <> 'Đã xóa' AND Loai_Phong=?");
            stmt.setString(1, typeRoom);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return number;
    }

    public int getNumberRoomStatus(String statusRoom) {
        int number = 0;
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM phong GROUP BY(Tinh_Trang_Phong) HAVING Tinh_Trang_Phong=?");
            stmt.setString(1, statusRoom);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return number;
    }

    public Room getRoomById(int id) {
        Room room = new Room();
        try {
            stmt = conn.prepareStatement("SELECT * FROM phong WHERE ID_Phong = ? AND Tinh_Trang_Phong <> 'Đã xóa'");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idRoom = rs.getInt(1);
                String roomType = rs.getString(2);
                String roomStatus = rs.getString(3);
                int maxOccupancy = rs.getInt(4);
                long priceOfRoom = rs.getLong(5);
                room.setIdRoom(idRoom);
                room.setRoomType(roomType);
                room.setRoomStatus(roomStatus);
                room.setMaxOccupancy(maxOccupancy);
                room.setPriceOfRoom(priceOfRoom);
            }
        } catch (Exception e) {
        }
        return room;
    }

    public ArrayList<Room> getListRooms() {
        try {
            stmt = conn.prepareStatement("SELECT * FROM phong WHERE Tinh_Trang_Phong <> 'Đã xóa'");
            arrRoom.clear();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idRoom = rs.getInt(1);
                String roomType = rs.getString(2);
                String roomStatus = rs.getString(3);
                int maxOccupancy = rs.getInt(4);
                long priceOfRoom = rs.getLong(5);
                Room room = new Room(idRoom, roomType, roomStatus, maxOccupancy, priceOfRoom);
                arrRoom.add(room);
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return arrRoom;
    }

    public boolean addRoom(Room room) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("INSERT INTO phong(ID_Phong,Loai_Phong,Tinh_Trang_Phong,So_Khach_Toi_Da,Gia_Phong) VALUES(?,?,?,?,?)");
            stmt.setInt(1, room.getIdRoom());
            stmt.setString(2, room.getRoomType());
            stmt.setString(3, room.getRoomStatus());
            stmt.setInt(4, room.getMaxOccupancy());
            stmt.setDouble(5, room.getPriceOfRoom());
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return check;
    }

    public boolean updateInformationOfRoom(Room room) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE phong SET Loai_Phong=?, Tinh_Trang_Phong=?, So_Khach_Toi_Da=?, Gia_Phong=? WHERE ID_Phong=?");
            stmt.setString(1, room.getRoomType());
            stmt.setString(2, room.getRoomStatus());
            stmt.setInt(3, room.getMaxOccupancy());
            stmt.setDouble(4, room.getPriceOfRoom());
            stmt.setInt(5, room.getIdRoom());
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }

    public boolean changeStatusRoom(int idRoom, String status) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE phong SET Tinh_Trang_Phong=? WHERE ID_Phong=?");
            stmt.setString(1, status);
            stmt.setInt(2, idRoom);
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (Exception e) {
        }
        return check;
    }

    public boolean deleteRoom(int idRoom) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("DELETE FROM phong WHERE ID_Phong=?");
            stmt.setInt(1, idRoom);
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return check;
    }
}
