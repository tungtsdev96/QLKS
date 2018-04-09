/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import com.itextpdf.text.Anchor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlyks.connectDataBase.ConnectionDB;

/**
 *
 * @author tungb_000
 */
public class HotelVoucherManager {

    //1 phieu dat phong co 3 trang thai Dang ở <UPDATE>, đang đặt<add>, đã xóa<delete>
    Connection conn;
    private ArrayList<String> listIdVoucher = new ArrayList<>();
    ArrayList<HotelVoucher> listVoucher = new ArrayList<>();
    private PreparedStatement stmt;

    public HotelVoucherManager() {
        conn = ConnectionDB.getConnection();
    }

    public ArrayList<String> listIdVoucherYear(String year) {
        listIdVoucher.clear();
        try {
            stmt = conn.prepareStatement("select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "%' and Trang_Thai = 'Đã xóa'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                listIdVoucher.add(idVoucher);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return listIdVoucher;
    }

    public int getNumberDays(String start, String end) {
        try {
            stmt = conn.prepareStatement("SELECT DATEDIFF(?, ?)");
            stmt.setString(1, end);
            stmt.setString(2, start);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int getDaysHired(String idVoucher) {
        try {
            stmt = conn.prepareStatement("SELECT Ngay_Tra_Phong, Ngay_Nhan_Phong FROM phieu_dat_phong WHERE ID_Phieu_Dat_Phong=? ");
            stmt.setString(1, idVoucher);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String end = rs.getString(1);
                String start = rs.getString(2);
                return getNumberDays(start, end);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public ArrayList<String> listIdVoucherMonth(String year, String month) {
        listIdVoucher.clear();
        try {
            stmt = conn.prepareStatement("select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "-" + month + "%' and Trang_Thai = 'Đã xóa'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                listIdVoucher.add(idVoucher);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return listIdVoucher;
    }

    public ArrayList<String> listIdVoucherQuarter(String year, int quarter) {
        listIdVoucher.clear();
        String query = "";
        if (quarter == 1) {
            query = "select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "-" + "01" + "%' or "
                    + "Ngay_Tra_Phong like '" + year + "-" + "02" + "%' or " + "Ngay_Tra_Phong like '" + year + "-" + "03" + "%' " + " and Trang_Thai = 'Đã Xóa'";
        } else if (quarter == 2) {
            query = "select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "-" + "04" + "%' or "
                    + "Ngay_Tra_Phong like '" + year + "-" + "05" + "%' or " + "Ngay_Tra_Phong like '" + year + "-" + "06" + "%' " + " and Trang_Thai = 'Đã Xóa'";
        } else if (quarter == 3) {
            query = "select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "-" + "07" + "%' or "
                    + "Ngay_Tra_Phong like '" + year + "-" + "08" + "%' or " + "Ngay_Tra_Phong like '" + year + "-" + "09" + "%' " + " and Trang_Thai = 'Đã Xóa'";
        } else {
            query = "select ID_Phieu_Dat_Phong from Phieu_Dat_Phong where Ngay_Tra_Phong like '" + year + "-" + "10" + "%' or "
                    + "Ngay_Tra_Phong like '" + year + "-" + "11" + "%' or " + "Ngay_Tra_Phong like '" + year + "-" + "12" + "%' " + " and Trang_Thai = 'Đã Xóa'";
        }
        try {
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                listIdVoucher.add(idVoucher);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return listIdVoucher;
    }

    public long totalPriceService(String idVoucher) {
        long totalPriceService = 0;
        try {
            stmt = conn.prepareStatement("SELECT So_Luong,Dich_Vu.Don_Gia FROM Dich_Vu,Phong_Su_Dung_Dich_Vu,Phieu_Dat_Phong WHERE Phieu_Dat_Phong.ID_Phieu_Dat_Phong =? AND Phieu_Dat_Phong.ID_Phieu_Dat_Phong = Phong_Su_Dung_Dich_Vu.ID_Phieu_Dat_Phong and Phong_Su_Dung_Dich_Vu.ID_DichVu = Dich_Vu.ID_DichVu;");
            stmt.setString(1, idVoucher);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int totalStaff = rs.getInt(1);
                float priceStaff = rs.getFloat(2);
                totalPriceService += (long)(totalStaff * priceStaff);
            }
        } catch (SQLException ex) {
        }
        return totalPriceService;
    }

    public long totalPriceRoom(String idVoucher) {
        long totalPriceRoom = 0;
        int totalDay = getDaysHired(idVoucher);
        try {
            stmt = conn.prepareStatement("SELECT phong.Gia_Phong FROM Phieu_Dat_Phong,Chi_Tiet_Dat_Phong,Phong \n"
                    + "WHERE phieu_dat_phong.ID_Phieu_Dat_Phong =? AND chi_tiet_dat_phong.ID_Phieu_Dat_Phong = phieu_dat_phong.ID_Phieu_Dat_Phong \n"
                    + "AND  chi_tiet_dat_phong.ID_Phong=phong.ID_Phong;");
            stmt.setString(1, idVoucher);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double priceRoom = rs.getDouble(1);
                if (totalDay != 0) {
                    totalPriceRoom += priceRoom * totalDay;
                } else {
                    totalPriceRoom += priceRoom;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return totalPriceRoom;
    }

    public String StaffVoucher(String idVoucher) {
        try {
            stmt = conn.prepareStatement("select ID_Nhan_Vien,Ho_Ten from Phieu_Dat_Phong,Nhan_Vien where "
                    + "ID_Nhan_Vien_Nhan_Phieu = ID_Nhan_Vien and ID_Phieu_Dat_Phong = '" + idVoucher + "'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nameStaff = rs.getString(2);
                return nameStaff;
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return null;
    }

    public String dateMadeVoucher(String idVoucher) {
        String dateMadeVoucher = "";
        try {
            stmt = conn.prepareStatement("select Ngay_Lap from Phieu_Dat_Phong where ID_Phieu_Dat_Phong = '" + idVoucher + "'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dateMadeVoucher = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }
        return dateMadeVoucher;
    }

    public long totalPriceVoucher(String idVoucher) {
        long totalPriceService = 0;
        long totalPriceRoom = 0;
        long totalPriceVoucher = 0;
        totalPriceService = totalPriceService(idVoucher);
        totalPriceRoom = totalPriceRoom(idVoucher);
        totalPriceVoucher = totalPriceRoom + totalPriceService;
        return totalPriceVoucher;
    }

    //lay list voucher tu CSDL
    public ArrayList<HotelVoucher> getListHotelVoucher() {
        listVoucher.clear();
        try {
            stmt = conn.prepareStatement("SELECT * from phieu_dat_phong WHERE TrangThai<>'Đã Xóa'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                String dateMadeVoucher = rs.getDate(2).toString(); //ngay lap
                double deposit = rs.getDouble(3); // tien dat trc
                String arrival = rs.getString(4); // ngay nhan
                String departure = rs.getString(5); // ngay tra
                int numberOfPeople = rs.getInt(6);
                String idCustomer = rs.getString(7);
                String idStaff = rs.getString(8);
                ArrayList<Service> arrService = getListServiceInVoucher(idVoucher);
                ArrayList<Room> arrRoom = getListRoomInVoucher(idVoucher);
                listVoucher.add(new HotelVoucher(idVoucher, dateMadeVoucher, deposit, arrival, departure, numberOfPeople, idCustomer, idStaff, arrService, arrRoom));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVoucher;
    }

    //list room trong voucher tu CSDL 
    public ArrayList<Room> getListRoomInVoucher(String idVoucher) {  //lay danh sach room có trong voucher
        ArrayList<Room> listRoomInVoucher = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT phong.ID_Phong,phong.Loai_Phong,phong.Tinh_Trang_Phong,phong.So_Khach_Toi_Da,phong.Gia_Phong\n"
                    + "FROM phong,phieu_dat_phong,chi_tiet_dat_phong\n"
                    + "where phieu_dat_phong.ID_Phieu_Dat_Phong=? AND chi_tiet_dat_phong.ID_Phieu_Dat_Phong = phieu_dat_phong.ID_Phieu_Dat_Phong AND phong.ID_Phong = chi_tiet_dat_phong.ID_Phong");
            stmt.setString(1, idVoucher);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idRoom = rs.getInt(1);
                String roomType = rs.getString(2);
                String roomStatus = rs.getString(3);
                int numberOfRoom = rs.getInt(4);
                long priceOfRoom = rs.getLong(5);
                Room r = new Room(idRoom, roomType, roomStatus, numberOfRoom, priceOfRoom);
                listRoomInVoucher.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRoomInVoucher;
    }
    // check tgian dat phong

    public boolean checkArrivalDate(String arrival, String depature, int idRoom) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("Select * from phieu_dat_phong,chi_tiet_dat_phong \n"
                    + "WHERE  (phieu_dat_phong.Trang_Thai='Đang Đặt') AND  (phieu_dat_phong.Ngay_Nhan_Phong between cast(? as date) and cast(? as date))\n"
                    + "AND (phieu_dat_phong.ID_Phieu_Dat_Phong = chi_tiet_dat_phong.ID_Phieu_Dat_Phong) AND (chi_tiet_dat_phong.ID_Phong=?)");
            stmt.setString(1, arrival);
            stmt.setString(2, depature);
            stmt.setInt(3, idRoom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    //list dich vu trong voucher tu CSDL
    public ArrayList<Service> getListServiceInVoucher(String idVoucher) { // lấy danh sách dịch bu trong voucher
        ArrayList<Service> listServiceInVoucher = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT dich_vu.ID_DichVu,dich_vu.Ten_Dich_Vu,dich_vu.Don_Gia, So_Luong FROM dich_vu,phong_su_dung_dich_vu,phieu_dat_phong WHERE  phieu_dat_phong.ID_Phieu_Dat_Phong =? and phieu_dat_phong.ID_Phieu_Dat_Phong = phong_su_dung_dich_vu.ID_Phieu_Dat_Phong AND phong_su_dung_dich_vu.ID_DichVu = dich_vu.ID_DichVu");
            stmt.setString(1, idVoucher);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idService = rs.getString(1);
                String nameService = rs.getString(2);
                long priceOfService = rs.getLong(3);
                int noService = rs.getInt(4);
                listServiceInVoucher.add(new Service(idService, nameService, priceOfService, noService));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listServiceInVoucher;
    }

    //lay voucher tu id 
    public HotelVoucher getHotelVoucherByIdVoucher(String id) { //lay voucher từ id voucher
        HotelVoucher voucher = new HotelVoucher();
        try {
            stmt = conn.prepareStatement("SELECT * FROM phieu_dat_phong WHERE ID_Phieu_Dat_Phong =? AND Trang_Thai<>'Đã Xóa'");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                String dateMadeVoucher = rs.getString(2);
                double deposit = rs.getDouble(3);
                String arrival = rs.getString(4);
                String departure = rs.getString(5); // ngay tra
                int numberOfPeople = rs.getInt(6);
                String idCustomer = rs.getString(7);
                String idStaff = rs.getString(8);
                ArrayList<Service> arrService = getListServiceInVoucher(idVoucher);
                ArrayList<Room> arrRoom = getListRoomInVoucher(idVoucher);
                voucher = new HotelVoucher(idVoucher, dateMadeVoucher, deposit, arrival, departure, numberOfPeople, idCustomer, idStaff, arrService, arrRoom);
            }
        } catch (SQLException ex) {
        }
        return voucher;
    }

    //lay voucher tu idRoom
    public HotelVoucher getHotelVoucherByIdRoom(int idRoom) { //lau voucher tu id room
        HotelVoucher voucher = new HotelVoucher();
        try {
            stmt = conn.prepareStatement("SELECT chi_tiet_dat_phong.ID_Phieu_Dat_Phong FROM chi_tiet_dat_phong, phieu_dat_phong WHERE chi_tiet_dat_phong.ID_Phieu_Dat_Phong = phieu_dat_phong.ID_Phieu_Dat_Phong AND Trang_Thai <> 'Đã xóa' AND ID_Phong=?");
            stmt.setInt(1, idRoom);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                voucher = getHotelVoucherByIdVoucher(rs.getString("ID_Phieu_Dat_Phong"));
            }
        } catch (SQLException ex) {
        }
        return voucher;
    }

    public ArrayList<HotelVoucher> getVoucherByIdCustomer(String idCustomer) {
        ArrayList<HotelVoucher> arrVoucher = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * from phieu_dat_phong WHERE ID_Khach_Hang_Dat_Phong=?");
            stmt.setString(1, idCustomer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idVoucher = rs.getString(1);
                System.out.println(idVoucher);
                String dateMadeVoucher = rs.getDate(2).toString(); //ngay lap
                double deposit = rs.getDouble(3); // tien dat trc
                String arrival = rs.getString(4); // ngay nhan
                String departure = rs.getString(5); // ngay tra
                int numberOfPeople = rs.getInt(6);
                String idCus = rs.getString(7);
                String idStaff = rs.getString(8);
                System.out.println("Hello");
                ArrayList<Service> arrService = getListServiceInVoucher(idVoucher);
                System.out.println(arrService.size());
                ArrayList<Room> arrRoom = getListRoomInVoucher(idVoucher);
                System.out.println(arrRoom.size());
                arrVoucher.add(new HotelVoucher(idVoucher, dateMadeVoucher, deposit, arrival, departure, numberOfPeople, idCus, idStaff, arrService, arrRoom));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucher;
    }

    //them phieu dat phong <them bang dich vu + bảng chi tiet phong>
    public boolean addHotelVoucherManager(HotelVoucher hotelVoucher) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("INSERT INTO Phieu_Dat_Phong(ID_Phieu_Dat_Phong,Ngay_Lap,So_Tien_Tra_Truoc,Ngay_Nhan_Phong,Ngay_Tra_Phong,So_Nguoi_o,ID_Nhan_Vien_Nhan_Phieu,ID_Khach_Hang_Dat_Phong,Trang_Thai) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, hotelVoucher.getIdVoucher());
            stmt.setString(2, hotelVoucher.getDateMadeVoucher());
            stmt.setDouble(3, hotelVoucher.getDeposit());
            stmt.setString(4, hotelVoucher.getArrival());
            stmt.setString(5, hotelVoucher.getDeparture());
            stmt.setString(6, hotelVoucher.getNoCustomer() + "");
            stmt.setString(7, hotelVoucher.getIdStaff());
            stmt.setString(8, hotelVoucher.getIdCustomer());
            stmt.setString(9, "Đang đặt");
            int result = stmt.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    //nhan phong luon
    public boolean receiveRoom(HotelVoucher voucher, String status) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("INSERT INTO Phieu_Dat_Phong(ID_Phieu_Dat_Phong,Ngay_Lap,So_Tien_Tra_Truoc,Ngay_Nhan_Phong,Ngay_Tra_Phong,So_Nguoi_o,ID_Nhan_Vien_Nhan_Phieu,ID_Khach_Hang_Dat_Phong,Trang_Thai) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, voucher.getIdVoucher());
            stmt.setString(2, voucher.getDateMadeVoucher());
            stmt.setDouble(3, voucher.getDeposit());
            stmt.setString(4, voucher.getArrival());
            stmt.setString(5, voucher.getDeparture());
            stmt.setString(6, voucher.getNoCustomer() + "");
            stmt.setString(7, voucher.getIdStaff());
            stmt.setString(8, voucher.getIdCustomer());
            stmt.setString(9, status);
            int result = stmt.executeUpdate();
            check = result != 0;
            if (check) {
                addListRoom(voucher.getArrRoom(), voucher.getIdVoucher());
                addListService(voucher.getArrService(), voucher.getIdVoucher());
                ChangeRoomStatus(voucher.getIdVoucher(), status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public boolean deleteListService(String idVoucher, String idService) {
        try {
            stmt = conn.prepareStatement("DELETE FROM phong_su_dung_dich_vu WHERE ID_Phieu_Dat_Phong=? AND ID_DichVu=?");
            stmt.setString(1, idVoucher);
            stmt.setString(2, idService);
            int result = stmt.executeUpdate();
            if(result !=0){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean updateVoucher(HotelVoucher voucher, String status) {
        try {
            stmt = conn.prepareStatement("UPDATE phieu_dat_phong SET Ngay_Lap=?, So_Tien_Tra_Truoc=?, So_Nguoi_o =?,ID_Nhan_Vien_Nhan_Phieu=?,Trang_Thai=? WHERE ID_Phieu_Dat_Phong=?");
            stmt.setString(1, voucher.getDateMadeVoucher());
            stmt.setDouble(2, voucher.getDeposit());
            stmt.setInt(3, voucher.getNoCustomer());
            stmt.setString(4, voucher.getIdStaff());
            stmt.setString(5, status);
            stmt.setString(6, voucher.getIdVoucher());
            int result = stmt.executeUpdate();
            if (result == 1) {
                addListService(voucher.getArrService(), voucher.getIdVoucher());
                ChangeRoomStatus(voucher.getIdVoucher(), status);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    //them list phong vao bang chi tiet phong
    public void addListRoom(ArrayList<Room> listRoomInVoucher, String idVoucher) {
        try {
            for (Room room : listRoomInVoucher) {
                stmt = conn.prepareStatement("INSERT INTO chi_tiet_dat_phong(ID_Phong,ID_Phieu_Dat_Phong) VALUES (?,?)");
                stmt.setString(1, room.getIdRoom() + "");
                stmt.setString(2, idVoucher);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //them list dich vu vao bang dich vu
    public void addListService(ArrayList<Service> listService, String idVoucher) {
        try {
            for (Service service : listService) {
                stmt = conn.prepareStatement("INSERT INTO phong_su_dung_dich_vu(Ngay_Su_Dung,So_Luong,ID_Phieu_Dat_Phong,ID_DichVu) VALUES (?,?,?,?)");
                stmt.setDate(1, dateutil_to_datesql(getDate()));
                stmt.setInt(2, service.getNoService());
                stmt.setString(3, idVoucher);
                stmt.setString(4, service.getIdService());
                int rs = stmt.executeUpdate();
                if (rs == 1) {
                    ChangeRoomStatus(idVoucher, "Đang trống");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //xoa phieu dat phong
    public boolean deleteHotelVoucherManager(String idVoucher) {
        boolean check = false;
        try {
            stmt = conn.prepareStatement("UPDATE phieu_dat_phong SET Trang_Thai='Đã Xóa' WHERE ID_Phieu_Dat_Phong =?");
            stmt.setString(1, idVoucher);
            int result = stmt.executeUpdate();
            check = result != 0;
            if (check) {
                ChangeRoomStatus(idVoucher, "Đang trống");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    //update trag thai phong
    private boolean ChangeRoomStatus(String idVoucher, String status) {
        RoomManager roomManager = new RoomManager();
        ArrayList<Room> listRoomInVoucher = getListRoomInVoucher(idVoucher);
        for (Room room : listRoomInVoucher) {
            room.setRoomStatus(status);
            if (!roomManager.changeStatusRoom(room.getIdRoom(), status)) {
                return false;
            } else {
                continue;
            }
        }
        return true;
    }

    public boolean cancelBookRoom(HotelVoucher voucher) {
        try {
            stmt = conn.prepareStatement("UPDATE phieu_dat_phong SET Ngay_Nhan_Phong=?, Ngay_Tra_Phong =? WHERE ID_Phieu_Dat_Phong=?");
            stmt.setString(1, voucher.getDateMadeVoucher());
            stmt.setString(2, voucher.getDateMadeVoucher());
            stmt.setString(3, voucher.getIdVoucher());
            int result = stmt.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    //xoa khach hang
//    private boolean deleteCustomerOfVoucher(String idVoucher) {
//         CustomerManager customerManager = new CustomerManager();
//         Customer cus = customerManager.getCustomerById(getHotelVoucherByIdVoucher(idVoucher).getIdCustomer());
//         if (customerManager.deleteCustomer(cus.getIdCustomer())) return true;
//         return false;
//     }
    //xoa phong trc khi xoa phieu 
//    private void deleteInforBookRoom(String idVoucher) {
//        try {
//            boolean checkToChangeRoomStatus = ChangeRoomStatus(idVoucher);
//            if (checkToChangeRoomStatus) {
//                st = conn.prepareStatement("DELETE from chi_tiet_dat_phong WHERE ID_Phieu_Dat_Phong=?");
//                st.setString(1, idVoucher);
//                st.executeUpdate();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    //xoa dich vu trong phieu 
//    private void deleteInforServiceOfBookRoom(String idVoucher) {
//        try {
//            st = conn.prepareStatement("DELETE from phong_su_dung_dich_vu WHERE ID_Phieu_Dat_Phong=?");
//            st.setString(1, idVoucher);
//            int result = st.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(HotelVoucherManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public java.util.Date getDate() {
        java.util.Date utilDate = new java.util.Date();
        return utilDate;
    }

    public java.sql.Date dateutil_to_datesql(java.util.Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
}
