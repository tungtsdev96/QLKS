/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.util.ArrayList;
import quanlyks.model.HotelVoucher;
import quanlyks.model.HotelVoucherManager;
import quanlyks.model.Service;

/**
 *
 * @author tungb_000
 */
public class HotelVoucherController {

    HotelVoucherManager hotelVoucherManager;

    public HotelVoucherController() {
        hotelVoucherManager = new HotelVoucherManager();
    }

    public int getDaysHired(String idVoucher) {
        return hotelVoucherManager.getDaysHired(idVoucher);
    }

    public int getNumberDays(String start, String end) {
        return hotelVoucherManager.getNumberDays(start, end);
    }

    public ArrayList<Service> getListServiceByIdvoucher(String idVoucher) {
        return hotelVoucherManager.getListServiceInVoucher(idVoucher);
    }

    public ArrayList<HotelVoucher> getVoucherByIdCustomer(String idCustomer) {
        return hotelVoucherManager.getVoucherByIdCustomer(idCustomer);
    }

    public ArrayList<HotelVoucher> getListHotelVoucher() {
        return hotelVoucherManager.getListHotelVoucher();
    }

    public boolean updateVoucher(HotelVoucher voucher, String status) {
        return hotelVoucherManager.updateVoucher(voucher, status);
    }

    public boolean cancelBookRoom(HotelVoucher voucher) {
        return hotelVoucherManager.cancelBookRoom(voucher);
    }

    public boolean checkArrivalDate(String arrival, String depature, int idRoom) {
        return hotelVoucherManager.checkArrivalDate(arrival, depature, idRoom);
    }

    public boolean receiveRoom(HotelVoucher voucher, String status) {
        return hotelVoucherManager.receiveRoom(voucher, status);
    }

    public boolean deleteListServicce(String idVoucher, String idService) {
        return hotelVoucherManager.deleteListService(idVoucher, idService);
    }

    public boolean addHotelVoucherManager(HotelVoucher hotelVoucher) {
        return hotelVoucherManager.addHotelVoucherManager(hotelVoucher);
    }

    public boolean deleteHotelVoucherManager(String idVoucher) {
        return hotelVoucherManager.deleteHotelVoucherManager(idVoucher);
    }

    public HotelVoucher getVoucherByIdRoom(int idRoom) {
        return hotelVoucherManager.getHotelVoucherByIdRoom(idRoom);
    }

    public ArrayList<String> listIdVoucherYear(String year) {
        return hotelVoucherManager.listIdVoucherYear(year);
    }

    public String StaffVoucher(String idVoucher) {
        return hotelVoucherManager.StaffVoucher(idVoucher);
    }

    public String dateMadeVoucher(String idVoucher) {
        return hotelVoucherManager.dateMadeVoucher(idVoucher);
    }

    public long totalPriceRoom(String idVoucher) {
        return hotelVoucherManager.totalPriceRoom(idVoucher);
    }

    public long totalPriceService(String idVoucher) {
        return hotelVoucherManager.totalPriceService(idVoucher);
    }

    public long totalPriceVoucher(String idVoucher) {
        return hotelVoucherManager.totalPriceVoucher(idVoucher);
    }

    public ArrayList<String> listIdVoucherMonth(String year, String month) {
        return hotelVoucherManager.listIdVoucherMonth(year, month);
    }

    public ArrayList<String> listIdVoucherQuarter(String year, int quarter) {
        return hotelVoucherManager.listIdVoucherQuarter(year, quarter);
    }

}
