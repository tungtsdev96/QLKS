package quanlyks.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import quanlyks.controller.HotelVoucherController;
import quanlyks.controller.IconManager;
import quanlyks.controller.RoomController;
import quanlyks.model.HotelVoucher;
import quanlyks.model.Room;
import quanlyks.model.Staff;

/**
 *
 * @author Tuyen Ti Ton
 */
public class MainPanel extends javax.swing.JPanel {

    private GridLayout grid;
    private RoomController roomController = new RoomController();
    private ArrayList<Room> arrRoom = new ArrayList<Room>();
    private Room room = new Room();
    private Staff staff = new Staff();
    private IconManager icon = new IconManager();
    private HotelVoucherController hotelVoucherController = new HotelVoucherController();
    private ArrayList<JButton> arrBtnUsed = new ArrayList<>();

    /**
     * Creates new form NewJPanel
     */
    public MainPanel() {
        initComponents();
    }

    public MainPanel(Staff staff) {
        initComponents();
        this.staff = staff;
        arrRoom = roomController.getListRooms();
        int numberRoom = arrRoom.size();
        grid = new GridLayout(5, 12, 10, 10);
        this.setLayout(grid);
        JButton[] arrBtn = new JButton[60];
        for (int i = 0; i < arrBtn.length; i++) {
            if (i < numberRoom) {
                room = arrRoom.get(i);
                arrBtn[i] = new JButton(room.getIdRoom() + "");
                if (getRoomStatus(room) == 1) {
                    arrBtn[i].setIcon(icon.iconEmpty());
                } else if (getRoomStatus(room) == 2) {
                    arrBtn[i].setIcon(icon.iconHired());
                } else if (getRoomStatus(room) == 3) {
                    arrBtn[i].setIcon(icon.iconBooked());
                } else if (getRoomStatus(room) == 4) {
                    arrBtn[i].setIcon(icon.iconRepair());
                }
                arrBtn[i].setVisible(true);
                arrBtnUsed.add(arrBtn[i]);
            } else {
                arrBtn[i] = new JButton();
                arrBtn[i].setVisible(false);
            }
            arrBtn[i].setHorizontalTextPosition(SwingConstants.CENTER);
            arrBtn[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            arrBtn[i].setFont(new Font("Tahoma", 1, 16));
            arrBtn[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            arrBtn[i].setIconTextGap(5);
            arrBtn[i].addActionListener(listener);
            this.add(arrBtn[i]);
        }

    }

    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                String text = ((JButton) e.getSource()).getText();
                int id = Integer.parseInt(text);
                Room room = roomController.getRoomById(id);
                if (getRoomStatus(room) == 1) {
                    OderRoom bookRoom = new OderRoom(room, arrBtnUsed, staff, 1);
                    bookRoom.setVisible(true);
                    bookRoom.setLocationRelativeTo(null);
                    bookRoom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else if (getRoomStatus(room) == 2) {
                    HotelVoucher voucher = hotelVoucherController.getVoucherByIdRoom(id);
                    ReceivedRoomInfo receivedRoom = new ReceivedRoomInfo(voucher, arrBtnUsed, room);
                    receivedRoom.setVisible(true);
                    receivedRoom.setLocationRelativeTo(null);
                    receivedRoom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else if (getRoomStatus(room) == 3) {
                    try {
                        HotelVoucher voucher = hotelVoucherController.getVoucherByIdRoom(id);
                        OderRoom bookRoom = new OderRoom(voucher, arrBtnUsed,staff, 3);
                        bookRoom.setVisible(true);
                        bookRoom.setLocationRelativeTo(null);
                        bookRoom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } catch (ParseException ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Phòng đang sửa chữa", "Lỗi", JOptionPane.INFORMATION_MESSAGE, icon.iconError());
                }
            }
        }

    };

    private int getRoomStatus(Room room) {
        if (room.getRoomStatus().equalsIgnoreCase("Đang trống")) {
            return 1;
        } else if (room.getRoomStatus().equalsIgnoreCase("Đang thuê")) {
            return 2;
        } else if (room.getRoomStatus().equalsIgnoreCase("Đang đặt")) {
            return 3;
        } else if (room.getRoomStatus().equalsIgnoreCase("Đang sửa chữa")) {
            return 4;
        }
        return 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1022, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
