/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

/**
 *
 * @author tungb_000
 */
public class Room {
    private int idRoom;
    private String roomType;
    private String roomStatus;
    private int maxOccupancy; //so Khach Toi Da
    private long priceOfRoom; //gia phong

    public Room(int idRoom, String roomType, String roomStatus, int maxOccupancy, long priceOfRoom) {
        this.idRoom = idRoom;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.maxOccupancy = maxOccupancy;
        this.priceOfRoom = priceOfRoom;
    }
    public Room(){
        
    }
    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public long getPriceOfRoom() {
        return priceOfRoom;
    }

    public void setPriceOfRoom(long priceOfRoom) {
        this.priceOfRoom = priceOfRoom;
    }

    
}
