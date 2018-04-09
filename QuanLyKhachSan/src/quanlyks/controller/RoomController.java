/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.util.ArrayList;
import quanlyks.model.Room;
import quanlyks.model.RoomManager;

/**
 *
 * @author tungb_000
 */
public class RoomController {

    private RoomManager roomManager = new RoomManager();

    public ArrayList<Room> searchRoom(String key) {
        return roomManager.searchRoom(key);
    }

    public int getNumberRoomType(String type) {
        return roomManager.getNumberRoomType(type);
    }

    public int getNumberRoomStatus(String status) {
        return roomManager.getNumberRoomStatus(status);
    }

    public Room getRoomById(int id) {
        return roomManager.getRoomById(id);
    }

    public ArrayList<Room> getListRooms() {
        return roomManager.getListRooms();
    }

    public boolean addRoom(Room room) {
        return roomManager.addRoom(room);
    }

    public boolean updateRoom(Room room) {
        return roomManager.updateInformationOfRoom(room);
    }

    public boolean deleteRoom(int idRoom) {
        return roomManager.deleteRoom(idRoom);
    }

    public ArrayList<Room> getEmptyRoom() {
        return roomManager.getEmptyRoom();
    }

    public boolean changeRoomStatus(int idRoom, String status) {
        return roomManager.changeStatusRoom(idRoom, status);
    }

}
