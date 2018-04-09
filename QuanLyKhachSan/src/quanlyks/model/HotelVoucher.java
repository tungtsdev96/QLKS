/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author tungb_000
 */
public class HotelVoucher implements Serializable {

    private String idVoucher;
    private String dateMadeVoucher; //ngay lap
    private double deposit; // tien dat trc
    private String arrival; // ngay dat
    private String departure; // ngay tra
    private int noCustomer;
    private String idCustomer;
    private String idStaff;
    private ArrayList<Service> arrService;
    private ArrayList<Room> arrRoom;

    public HotelVoucher(String idVoucher, String dateMadeVoucher, double deposit, String arrival, String departure, int noCustomer, String idCustomer, String idStaff, ArrayList<Service> arrService, ArrayList<Room> arrRoom) {
        this.idVoucher = idVoucher;
        this.dateMadeVoucher = dateMadeVoucher;
        this.deposit = deposit;
        this.arrival = arrival;
        this.departure = departure;
        this.noCustomer = noCustomer;
        this.idCustomer = idCustomer;
        this.idStaff = idStaff;
        this.arrService = arrService;
        this.arrRoom = arrRoom;
    }
     public HotelVoucher(String idVoucher, String dateMadeVoucher, double deposit, String arrival, String departure, int noCustomer, String idCustomer, String idStaff) {
        this.idVoucher = idVoucher;
        this.dateMadeVoucher = dateMadeVoucher;
        this.deposit = deposit;
        this.arrival = arrival;
        this.departure = departure;
        this.noCustomer = noCustomer;
        this.idCustomer = idCustomer;
        this.idStaff = idStaff;
    }

    public HotelVoucher() {
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getDateMadeVoucher() {
        return dateMadeVoucher;
    }

    public void setDateMadeVoucher(String dateMadeVoucher) {
        this.dateMadeVoucher = dateMadeVoucher;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getNoCustomer() {
        return noCustomer;
    }

    public void setNoCustomer(int numberOfRoom) {
        this.noCustomer = numberOfRoom;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public ArrayList<Service> getArrService() {
        return arrService;
    }

    public void setArrService(ArrayList<Service> arrService) {
        this.arrService = arrService;
    }

    public ArrayList<Room> getArrRoom() {
        return arrRoom;
    }

    public void setArrRoom(ArrayList<Room> arrRoom) {
        this.arrRoom = arrRoom;
    }

    
}