package quanlyks.views;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import jxl.write.WriteException;
import quanlyks.controller.CustomerController;
import quanlyks.controller.ExportFile;
import quanlyks.controller.HotelVoucherController;
import quanlyks.controller.IconManager;
import quanlyks.controller.RoomController;
import quanlyks.controller.ServiceController;
import quanlyks.controller.StaffController;
import quanlyks.model.Customer;
import quanlyks.model.HotelVoucher;
import quanlyks.model.Room;
import quanlyks.model.Service;
import quanlyks.model.Staff;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Julian
 */
public class OderRoom extends javax.swing.JFrame {

    RoomController roomController = new RoomController();
    ServiceController serviceController = new ServiceController();
    HotelVoucherController voucherController = new HotelVoucherController();
    CustomerController cusController = new CustomerController();
    StaffController staffController = new StaffController();
    private ArrayList<Room> arrRoom = new ArrayList<>();
    private ArrayList<Room> arrRoomOder = new ArrayList<>();
    private ArrayList<Service> arrService = new ArrayList<Service>();
    private ArrayList<Service> arrServiceOder = new ArrayList<Service>();
    private IconManager icon = new IconManager();
    public static Room room = new Room();
    private Staff staff = new Staff();
    private ArrayList<JButton> arrBtn = new ArrayList<>();
    private HotelVoucher voucher = new HotelVoucher();
    private int type;

    /**
     * Creates new form DatPhong
     */
    public OderRoom() {
        initComponents();
    }

    public OderRoom(Room room, ArrayList<JButton> arrBtn, Staff staff, int type) {
        initComponents();
        this.room = room;
        this.staff = staff;
        this.arrBtn = arrBtn;
        this.type = type;
        arrRoomOder.add(room);
        initListRoom(arrRoomOder);
        edtNameSt.setText(staff.getNameStaff());
        edtTime.setText(MainScreen.getCurrentDate());
        arrService = serviceController.getListServices();
        bindingTableService(arrService);
        for (int i = 0; i < roomController.getEmptyRoom().size(); i++) {
            if ((roomController.getEmptyRoom().get(i).getIdRoom() != room.getIdRoom())) {
                arrRoom.add(roomController.getEmptyRoom().get(i));
            }
        }
        bindingTableRoom(arrRoom);
        rdMale.setSelected(true);
    }

    public OderRoom(HotelVoucher voucher, ArrayList<JButton> arrBtn, Staff staff, int type) throws ParseException {
        initComponents();
        this.arrBtn = arrBtn;
        this.voucher = voucher;
        this.type = type;
        this.staff = staff;
        edtNameSt.setText(staff.getNameStaff());
        edtTime.setText(MainScreen.getCurrentDate());
        Customer customer = cusController.getCustomerByIdCustomer(voucher.getIdCustomer());
        edtNameCus.setText(customer.getNameCustomer());
        edtNameCus.setEditable(false);
        edtDateOfBirth.setDate(MainScreen.stringToDaTe(customer.getDateOfBirth()));
        edtDateOfBirth.setEnabled(false);
        if (customer.getSex().equalsIgnoreCase("Nam")) {
            rdMale.setSelected(true);
            rdFemale.setEnabled(false);
        } else {
            rdFemale.setSelected(true);
            rdMale.setEnabled(false);
        }
        edtIdentifyCus.setText(customer.getIdentify());
        edtIdentifyCus.setEditable(false);
        edtNationalCus.setText(customer.getNational());
        edtNationalCus.setEditable(false);
        edtNameCus.setEditable(false);
        edtPhoneCus.setText(customer.getPhoneNumber());
        edtPhoneCus.setEditable(false);
        edtEmailCus.setText(customer.getEmail());
        edtEmailCus.setEditable(false);
        edtArrival.setDate(MainScreen.stringToDaTe(voucher.getArrival()));
        edtArrival.setEnabled(false);
        edtDepature.setDate(MainScreen.stringToDaTe(voucher.getDeparture()));
        edtDepature.setEnabled(false);
        spNumberCus.setValue(voucher.getNoCustomer());
        edtPrepaid.setText(voucher.getDeposit() + "");
        arrRoomOder = voucher.getArrRoom();
        arrService = serviceController.getListServices();
        arrServiceOder.addAll(voucher.getArrService());
        for (int i = 0; i < voucher.getArrService().size(); i++) {
            String idService = voucher.getArrService().get(i).getIdService();
            for (int j = 0; j < arrService.size(); j++) {
                String id = arrService.get(j).getIdService();
                if (idService.equalsIgnoreCase(id)) {
                    arrService.remove(j);
                }
            }
        }
        arrServiceOder.addAll(arrService);
        bindingTableService(arrServiceOder);
        initListRoom(arrRoomOder);
        for (int i = 0; i < roomController.getEmptyRoom().size(); i++) {
            if ((roomController.getEmptyRoom().get(i).getIdRoom() != room.getIdRoom())) {
                arrRoom.add(roomController.getEmptyRoom().get(i));
            }
        }
        bindingTableRoom(arrRoom);
        btnDelete.setEnabled(false);
        tbRoom.setEnabled(false);
    }

    public static void bindingTableRoom(ArrayList<Room> arrRoom) {
        DefaultTableModel model = (DefaultTableModel) tbRoom.getModel();
        model.setRowCount(0);
        for (int i = 0; i < arrRoom.size(); i++) {
            model.addRow(new Object[]{
                arrRoom.get(i).getIdRoom(),
                arrRoom.get(i).getRoomType(),
                arrRoom.get(i).getMaxOccupancy()
            });
        }
    }

    private HotelVoucher getInformationVoucher() {
        HotelVoucher voucher = new HotelVoucher();
        String idStaff = staff.getIdStaff();
        String dateMade = MainScreen.getCurrentDate();
        double deposite = Double.parseDouble(edtPrepaid.getText());
        String arrival = MainScreen.utilDateToSqlDate(edtArrival.getDate()).toString();
        String depature = MainScreen.utilDateToSqlDate(edtDepature.getDate()).toString();
        String idVoucher = "VC" + MainScreen.getCurrentDateTime();
        int noCustomer = (int) spNumberCus.getValue();
        String idCustomer = "KH" + MainScreen.getCurrentDateTime();
        String nameCustomer = edtNameCus.getText();
        String dateOfBirth = MainScreen.utilDateToSqlDate(edtDateOfBirth.getDate()).toString();
        String sexCustomer = null;
        if (rdMale.isSelected()) {
            sexCustomer = "Nam";
        } else if (rdFemale.isSelected()) {
            sexCustomer = "Nữ";
        }
        String identify = edtIdentifyCus.getText();
        String national = edtNationalCus.getText();
        String phone = edtPhoneCus.getText();
        String email = edtEmailCus.getText();
        //Customer customer = new Customer(idCustomer, nameCustomer, dateOfBirth, sexCustomer, national, identify, phone, email);
        ArrayList<Customer> arrCustomer = cusController.getListCustomer();
        Customer customer = null;
        boolean flag = false;
        for (int i = 0; i < arrCustomer.size(); i++) {
            if (identify.equalsIgnoreCase(arrCustomer.get(i).getIdentify())) {
                customer = arrCustomer.get(i);
                flag = true;
                break;
            }
        }
        if (flag == true) {
            cusController.updateStatusCustomerInListCustomer(identify);
            // arrServiceOder = getListService();
            voucher = new HotelVoucher(idVoucher, dateMade, deposite, arrival, depature, noCustomer, customer.getIdCustomer(), idStaff, getListService(), arrRoomOder);
        } else {
            cusController.addCustomer(new Customer(idCustomer, nameCustomer, dateOfBirth, sexCustomer, national, identify, phone, email));
            CustomerPanel.analysisCustomer();
            //arrServiceOder = getListService();
            voucher = new HotelVoucher(idVoucher, dateMade, deposite, arrival, depature, noCustomer, idCustomer, idStaff, getListService(), arrRoomOder);
        }
        return voucher;
    }

    private boolean checkEmpty() {
        if (edtNameCus.getText().isEmpty() || edtArrival.getDate() == null || edtDateOfBirth.getDate() == null || edtDepature.getDate() == null
                || edtEmailCus.getText().isEmpty() || edtIdentifyCus.getText().isEmpty() || edtNationalCus.getText().isEmpty() || edtPhoneCus.getText().isEmpty() || edtPrepaid.getText().isEmpty()) {

            return false;
        } else {
            try {
                double deposite = Double.parseDouble(edtPrepaid.getText());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private ArrayList<Service> getListService() {
        ArrayList<Service> arrService = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tbService.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idService = model.getValueAt(i, 0).toString();
            String nameService = model.getValueAt(i, 1).toString();
            long priceService = Long.parseLong(model.getValueAt(i, 2).toString());
            int noService = Integer.parseInt(model.getValueAt(i, 3).toString());
            if (noService == 0) {
                continue;
            } else {
                Service service = new Service(idService, nameService, priceService, noService);
                arrService.add(service);
            }
        }
        return arrService;
    }

    private void initListRoom(ArrayList<Room> arrRoom) {
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < arrRoom.size(); i++) {
            model.addElement(arrRoom.get(i).getIdRoom());
        }
        edtListRoom.setModel(model);
    }

    private void bindingTableService(ArrayList<Service> arrService) {
        DefaultTableModel tmodel = (DefaultTableModel) tbService.getModel();
        tmodel.setRowCount(0);
        for (int i = 0; i < arrService.size(); i++) {
            tmodel.addRow(new Object[]{
                arrService.get(i).getIdService(),
                arrService.get(i).getNameService(),
                arrService.get(i).getPriceOfService(),
                arrService.get(i).getNoService()
            });
        }
        tbService.setModel(tmodel);
        Integer[] number = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox cbxNumberService = new JComboBox<Integer>(number);
//        cbxNumberService.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, cbxNumberService.getSelectedItem());
//            }
//        });
        TableColumn col = tbService.getColumnModel().getColumn(3);
        col.setCellEditor(new DefaultCellEditor(cbxNumberService));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        btnGetRoom = new javax.swing.JButton();
        btnBookRoom = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbService = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        edtArrival = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        edtPrepaid = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        spNumberCus = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        edtEmailCus = new javax.swing.JTextField();
        edtPhoneCus = new javax.swing.JTextField();
        edtNationalCus = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbRoom = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        edtIdentifyCus = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rdMale = new javax.swing.JRadioButton();
        rdFemale = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        edtNameCus = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edtNameSt = new javax.swing.JTextField();
        edtTime = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        edtDateOfBirth = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        edtDepature = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        edtListRoom = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 255));
        setUndecorated(true);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));

        btnGetRoom.setBackground(new java.awt.Color(0, 153, 255));
        btnGetRoom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGetRoom.setForeground(new java.awt.Color(255, 255, 255));
        btnGetRoom.setText("Nhận phòng");
        btnGetRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetRoomActionPerformed(evt);
            }
        });

        btnBookRoom.setBackground(new java.awt.Color(0, 153, 255));
        btnBookRoom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBookRoom.setForeground(new java.awt.Color(255, 255, 255));
        btnBookRoom.setText("Đặt phòng");
        btnBookRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookRoomActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(0, 153, 255));
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy ");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tbService.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã DV", "Tên dịch vụ", "Giá", "SL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbService.setRowHeight(20);
        tbService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbServiceMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbService);
        if (tbService.getColumnModel().getColumnCount() > 0) {
            tbService.getColumnModel().getColumn(0).setResizable(false);
            tbService.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbService.getColumnModel().getColumn(2).setResizable(false);
            tbService.getColumnModel().getColumn(2).setPreferredWidth(20);
            tbService.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Chọn dịch vụ ");

        edtArrival.setDateFormatString("dd-MM-yyyy");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Ngày nhận phòng");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Số tiền trả trước");

        edtPrepaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("VND");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Số người ");

        spNumberCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        spNumberCus.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel25.setBackground(new java.awt.Color(51, 153, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/home_white.png"))); // NOI18N
        jLabel25.setText("Nhập thông tin phòng");
        jLabel25.setOpaque(true);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Email ");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Số điện thoại");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Quốc tịch");

        edtEmailCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtPhoneCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtNationalCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tbRoom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã phòng", "Loại phòng", "Số người tối đa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbRoom.setRowHeight(25);
        tbRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRoomMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbRoom);
        if (tbRoom.getColumnModel().getColumnCount() > 0) {
            tbRoom.getColumnModel().getColumn(0).setResizable(false);
            tbRoom.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbRoom.getColumnModel().getColumn(1).setResizable(false);
            tbRoom.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setAlignmentX(0.0F);
        jSeparator1.setAlignmentY(0.0F);

        edtIdentifyCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Số CMND");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Giới tính");

        buttonGroup1.add(rdMale);
        rdMale.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdMale.setText("Nam");

        buttonGroup1.add(rdFemale);
        rdFemale.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdFemale.setText("Nữ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Ngày sinh");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Họ và tên");

        edtNameCus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setBackground(new java.awt.Color(0, 153, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/customer_white.png"))); // NOI18N
        jLabel6.setText("Thông tin khách hàng");
        jLabel6.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(0, 153, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/info.png"))); // NOI18N
        jLabel8.setText("Danh sách phòng");
        jLabel8.setOpaque(true);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Phòng thuê");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nhân viên ");

        edtNameSt.setEditable(false);
        edtNameSt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtTime.setEditable(false);
        edtTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        edtTime.setText("20/11/2016  14:30");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Ngày giờ ");

        jLabel2.setBackground(new java.awt.Color(0, 153, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/house_key.png"))); // NOI18N
        jLabel2.setText("Đặt phòng");
        jLabel2.setOpaque(true);

        btnMinimize.setBackground(new java.awt.Color(0, 153, 255));
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/minimize.png"))); // NOI18N
        btnMinimize.setBorder(null);
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(0, 153, 255));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/close_white.png"))); // NOI18N
        btnClose.setToolTipText("");
        btnClose.setBorder(null);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        edtDateOfBirth.setDateFormatString("dd-MM-yyyy");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Ngày trả phòng");

        edtDepature.setDateFormatString("dd-MM-yyyy");

        edtListRoom.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(edtListRoom);

        btnDelete.setBackground(new java.awt.Color(0, 153, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnGetRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spNumberCus, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(edtDepature, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(btnBookRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(edtArrival, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(edtPrepaid, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(edtNameSt)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(edtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdMale)
                                .addGap(43, 43, 43)
                                .addComponent(rdFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(edtNameCus, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                            .addComponent(edtDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(edtIdentifyCus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtNationalCus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtPhoneCus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtEmailCus, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane4))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap())))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(305, 305, 305)))
                .addGap(76, 76, 76))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtNameSt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtNameCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(edtDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rdMale, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(rdFemale)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtIdentifyCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtNationalCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtPhoneCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtEmailCus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnDelete))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)))
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edtArrival, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edtDepature, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spNumberCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtPrepaid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBookRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGetRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizeActionPerformed
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if (type == 1) {
            this.setVisible(false);
        } else if (type == 3) {
            int output = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy đặt phòng?", "Hủy phòng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon.iconExit());
            if (output == JOptionPane.YES_OPTION) {
                HotelVoucher voucher = getInformationVoucher();
                voucher.setIdVoucher(this.voucher.getIdVoucher());
                voucherController.cancelBookRoom(voucher);
                boolean check = voucherController.deleteHotelVoucherManager(this.voucher.getIdVoucher());
                if (check) {
                    JOptionPane.showMessageDialog(null, "Đã trả phòng", "Thành công", JOptionPane.INFORMATION_MESSAGE, icon.iconDone());
                    for (int i = 0; i < voucher.getArrRoom().size(); i++) {
                        String idRoom = voucher.getArrRoom().get(i).getIdRoom() + "";
                        for (int j = 0; j < arrBtn.size(); j++) {
                            if (idRoom.equalsIgnoreCase(arrBtn.get(j).getText())) {
                                arrBtn.get(j).setIcon(icon.iconEmpty());
                            }
                        }
                    }
                    RoomPanel.bindingRoomTable(roomController.getListRooms());
//                    try {
//                        ExportFile exp = new ExportFile();
//                        File file = new File("Pdf\\" + MainScreen.getCurrentDateTime() + ".pdf");
//                        exp.printBill(voucher, file);
//                        this.setVisible(false);
//                        int output1 = JOptionPane.showConfirmDialog(rootPane, "Mở hóa đơn?", "Hóa đơn", JOptionPane.YES_NO_OPTION);
//                        if (output1 == JOptionPane.YES_OPTION) {
//                            try {
//                                Desktop.getDesktop().open(file);
//                            } catch (IOException ex) {
//                                Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    } catch (HeadlessException e) {
//                    }
                    this.dispose();
                    //update lại man hinh chinh
                } else {
                    JOptionPane.showMessageDialog(null, "Trả phòng không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
                }
            }
        }

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tbRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRoomMouseClicked
        // TODO add your handling code here:
        boolean duplicate = false;
        int row = tbRoom.getSelectedRow();
        if (row != -1) {
            Room room = arrRoom.get(row);
            for (int i = 0; i < arrRoomOder.size(); i++) {
                if (room.getIdRoom() == arrRoomOder.get(i).getIdRoom()) {
                    duplicate = true;
                }
            }
            if (duplicate == true) {
                JOptionPane.showMessageDialog(rootPane, "Phòng đã được thêm");
            } else {
                arrRoomOder.add(room);
                // edtListRoom.setText(displayListRoom(arrRoomOder));
                initListRoom(arrRoomOder);
            }

        }
    }//GEN-LAST:event_tbRoomMouseClicked

    private void tbServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbServiceMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbServiceMouseClicked

    private void btnBookRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookRoomActionPerformed
        // Dat Phong
        if (checkEmpty()) {
            if (type == 1) {
                boolean flag = false;
                ArrayList<Integer> listIdRoom = new ArrayList<>();
                String arrival = MainScreen.utilDateToSqlDate(edtArrival.getDate()).toString();
                String depature = MainScreen.utilDateToSqlDate(edtDepature.getDate()).toString();
                for (Room room : arrRoomOder) {
                    if (voucherController.checkArrivalDate(arrival, depature, room.getIdRoom())) {
                        listIdRoom.add(room.getIdRoom());
                        flag = true;
                    } else {
                        continue;
                    }
                }
                if (flag) {
                    String thongBao = "";
                    for (int i = 0; i < listIdRoom.size(); i++) {
                        thongBao += listIdRoom.get(i) + ",";
                    }
                    JOptionPane.showMessageDialog(null, "Các Phòng đã có người đặt" + thongBao, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    HotelVoucher voucher = getInformationVoucher();
                    if (voucherController.getNumberDays(voucher.getArrival(), voucher.getDeparture()) >= 0) {
                        boolean check = false;
                        if (type == 1) {
                            check = voucherController.receiveRoom(voucher, "Đang đặt");
                        } else if (type == 3) {
                            voucher.setIdVoucher(this.voucher.getIdVoucher());
                            check = voucherController.updateVoucher(voucher, "Đang đặt");
                        }
                        if (check == true) {
                            JOptionPane.showMessageDialog(rootPane, "Đặt phòng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE, icon.iconDone());
                            for (int i = 0; i < voucher.getArrRoom().size(); i++) {
                                String idRoom = voucher.getArrRoom().get(i).getIdRoom() + "";
                                for (int j = 0; j < arrBtn.size(); j++) {
                                    if (idRoom.equalsIgnoreCase(arrBtn.get(j).getText())) {
                                        arrBtn.get(j).setIcon(icon.iconBooked());
                                    }
                                }
                            }
                            RoomPanel.bindingRoomTable(roomController.getListRooms());
                            CustomerPanel.bindingTableCustomer(cusController.getListCustomer());
                            ExportFile exp = new ExportFile();
                            File file = new File("Pdf\\" + MainScreen.getCurrentDateTime() + ".pdf");
                            exp.printOrderRoom(voucher, file);
                            this.setVisible(false);
                            int output = JOptionPane.showConfirmDialog(rootPane, "Mở phiếu đặt phòng", "Đặt phòng", JOptionPane.YES_NO_OPTION);
                            if (output == JOptionPane.YES_OPTION) {
                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException ex) {
                                    Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Xảy ra lỗi! Kiểm trạ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
                        }

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Kiểm tra lại ngày đặt và nhận phòng", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
                    }

                }
            } else if (type == 3) {
                for (int i = 0; i < this.voucher.getArrService().size(); i++) {
                    voucherController.deleteListServicce(this.voucher.getIdVoucher(), this.voucher.getArrService().get(i).getIdService());
                }
                this.voucher.setArrService(getListService());
                this.voucher.setDeposit(Double.parseDouble(edtPrepaid.getText()));
                this.voucher.setNoCustomer((int) spNumberCus.getValue());
                if (voucherController.updateVoucher(this.voucher, "Đang đặt")) {
                    JOptionPane.showMessageDialog(rootPane, "Đã cập nhật thông tin đặt phòng", "Thành công", JOptionPane.INFORMATION_MESSAGE, icon.iconDone());
                    ExportFile exp = new ExportFile();
                    File file = new File("Pdf\\" + MainScreen.getCurrentDateTime() + ".pdf");
                    exp.printOrderRoom(this.voucher, file);
                    this.setVisible(false);
                    int output = JOptionPane.showConfirmDialog(rootPane, "Mở phiếu đặt phòng", "Đặt phòng", JOptionPane.YES_NO_OPTION);
                    if (output == JOptionPane.YES_OPTION) {
                        try {
                            Desktop.getDesktop().open(file);
                        } catch (IOException ex) {
                            Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Lỗi cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
        }
    }//GEN-LAST:event_btnBookRoomActionPerformed

    private void btnGetRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetRoomActionPerformed
        // Nhan Phong
        if (checkEmpty()) {
            HotelVoucher voucher = getInformationVoucher();
            if (voucherController.getNumberDays(voucher.getArrival(), voucher.getDeparture()) >= 0) {
                boolean check = false;
                if (type == 1) {
                    voucher.setArrival(MainScreen.getCurrentDate());
                    check = voucherController.receiveRoom(voucher, "Đang thuê");
                } else if (type == 3) {
                    for (int i = 0; i < this.voucher.getArrService().size(); i++) {
                        if (voucherController.deleteListServicce(this.voucher.getIdVoucher(), this.voucher.getArrService().get(i).getIdService())) {
                            System.out.println("true");
                        }
                    }
                    voucher.setIdVoucher(this.voucher.getIdVoucher());
                    check = voucherController.updateVoucher(voucher, "Đang thuê");
                }

                if (check == true) {
                    JOptionPane.showMessageDialog(rootPane, "Nhận phòng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE, icon.iconDone());
                    for (int i = 0; i < voucher.getArrRoom().size(); i++) {
                        String idRoom = voucher.getArrRoom().get(i).getIdRoom() + "";
                        for (int j = 0; j < arrBtn.size(); j++) {
                            if (idRoom.equalsIgnoreCase(arrBtn.get(j).getText())) {
                                arrBtn.get(j).setIcon(icon.iconHired());
                            }
                        }
                    }
                    RoomPanel.bindingRoomTable(roomController.getListRooms());
                    CustomerPanel.bindingTableCustomer(cusController.getListCustomer());
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Xảy ra lỗi! Kiểm trạ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Xảy ra lỗi! Kiểm trạ thông tin ngày nhận, ngày trả", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
        }

    }//GEN-LAST:event_btnGetRoomActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int index = edtListRoom.getSelectedIndex();
        if (index != 0) {
            arrRoomOder.remove(index);
            initListRoom(arrRoomOder);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Không được xóa phòng này", "Lỗi", JOptionPane.ERROR_MESSAGE, icon.iconError());
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OderRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OderRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OderRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OderRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OderRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBookRoom;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGetRoom;
    private javax.swing.JButton btnMinimize;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser edtArrival;
    private com.toedter.calendar.JDateChooser edtDateOfBirth;
    private com.toedter.calendar.JDateChooser edtDepature;
    private javax.swing.JTextField edtEmailCus;
    private javax.swing.JTextField edtIdentifyCus;
    private javax.swing.JList<String> edtListRoom;
    private javax.swing.JTextField edtNameCus;
    private javax.swing.JTextField edtNameSt;
    private javax.swing.JTextField edtNationalCus;
    private javax.swing.JTextField edtPhoneCus;
    private javax.swing.JTextField edtPrepaid;
    private javax.swing.JTextField edtTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JRadioButton rdFemale;
    private javax.swing.JRadioButton rdMale;
    private javax.swing.JSpinner spNumberCus;
    public static javax.swing.JTable tbRoom;
    private javax.swing.JTable tbService;
    // End of variables declaration//GEN-END:variables
}
