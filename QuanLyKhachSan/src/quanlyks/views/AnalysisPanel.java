package quanlyks.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import jxl.write.WriteException;
import quanlyks.controller.ExportFile;
import quanlyks.controller.HotelVoucherController;
import quanlyks.controller.StaffController;
import quanlyks.model.HotelVoucherManager;
import static quanlyks.views.RoomPanel.tbRoom;

/**
 *
 * @author Tuyen Ti Ton
 */
public class AnalysisPanel extends javax.swing.JPanel {

    /**
     * Creates new form StaffPanel
     */
    private String year;
    private int quarter;
    private String month;
    private StaffController staffController = new StaffController();
    private HotelVoucherController hotelVoucherController = new HotelVoucherController();

    public AnalysisPanel() {
        initComponents();
        DefaultComboBoxModel boxModelMonth = new DefaultComboBoxModel();
        for (int i = 0; i < 12; i++) {
            boxModelMonth.addElement((i + 1) + "");
        }
        cbMonth1.setModel(boxModelMonth);
        DefaultComboBoxModel boxModelQuarter = new DefaultComboBoxModel();
        for (int i = 0; i < 4; i++) {
            boxModelQuarter.addElement((i + 1));
        }

        year = getCurrentYear();
        month = getCurrentMonth();
        quarter = getCurrentQuater();
        cbQuarter.setModel(boxModelQuarter);
        cbMonth1.setSelectedItem(month);
        cbQuarter.setSelectedItem(quarter);
        tfYear.setText(year);
        tfYear2.setText(year);
        tfYear3.setText(year);
        hienThiListAnalysMonth(year, month);
        hienThiListAnalysQuarter(year, quarter);
        hienThiListAnalysYear(year);
        analysTotalYear(year);
        analysTotalMonth(year, month);
        analysTotalQuarter(year, quarter);
        tfYear3.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    year = tfYear3.getText();
                    hienThiListAnalysYear(year);
                    analysTotalYear(year);
                }
            }
        });
        tfYear.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    year = tfYear.getText();
                    int mon = cbMonth1.getSelectedIndex() + 1;
                    month = cbMonth1.getSelectedItem().toString();
                    if (mon < 10) {
                        month = "0" + month;
                    }
                    hienThiListAnalysMonth(year, month);
                    analysTotalMonth(year, month);
                }
            }
        });
        tfYear2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    year = tfYear2.getText();
                    quarter = cbQuarter.getSelectedIndex() + 1;
                    hienThiListAnalysQuarter(year, quarter);
                    analysTotalQuarter(year, quarter);
                }
            }
        });
        cbMonth1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    year = tfYear.getText();
                    int mon = cbMonth1.getSelectedIndex() + 1;
                    month = cbMonth1.getSelectedItem().toString();
                    if (mon < 10) {
                        month = "0" + month;
                    }
                    hienThiListAnalysMonth(year, month);
                    analysTotalMonth(year, month);
                }
            }
        });
        cbQuarter.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    year = tfYear2.getText();
                    quarter = cbQuarter.getSelectedIndex() + 1;
                    hienThiListAnalysQuarter(year, quarter);
                    analysTotalQuarter(year, quarter);
                }
            }
        });
    }

    private String getCurrentMonth() {
        SimpleDateFormat dfm = new SimpleDateFormat("MM");
        return dfm.format(Calendar.getInstance().getTime());
    }

    private String getCurrentYear() {
        SimpleDateFormat dfm = new SimpleDateFormat("yyyy");
        return dfm.format(Calendar.getInstance().getTime());
    }

    private int getCurrentQuater() {
        switch (getCurrentMonth()) {
            case "01":
            case "02":
            case "03": {
                return 1;
            }
            case "04":
            case "05":
            case "06": {
                return 2;
            }
            case "07":
            case "08":
            case "09": {
                return 3;
            }
            case "10":
            case "11":
            case "12": {
                return 4;
            }
        }
        return 0;
    }

    public void hienThiListAnalysYear(String year) {
        DefaultTableModel model = (DefaultTableModel) tbAnalysis3.getModel();
        model.setRowCount(0);
        Object[] row = new Object[7];
        ArrayList<String> listId = hotelVoucherController.listIdVoucherYear(year);
        for (int i = 0; i < listId.size(); i++) {
            row[0] = (i + 1);
            String idVoucher = listId.get(i);
            row[1] = idVoucher;
            row[2] = hotelVoucherController.StaffVoucher(idVoucher);
            row[3] = hotelVoucherController.dateMadeVoucher(idVoucher);
            row[4] = hotelVoucherController.totalPriceRoom(idVoucher) + "đ";
            row[5] = hotelVoucherController.totalPriceService(idVoucher) + "đ";
            row[6] = hotelVoucherController.totalPriceVoucher(idVoucher) + "đ";
            model.addRow(row);
        }
    }

    public void hienThiListAnalysMonth(String year, String month) {
        DefaultTableModel model = (DefaultTableModel) tbAnalysis1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[7];
        ArrayList<String> listId = hotelVoucherController.listIdVoucherMonth(year, month);
        for (int i = 0; i < listId.size(); i++) {
            row[0] = (i + 1);
            String idVoucher = listId.get(i);
            row[1] = idVoucher;
            row[2] = hotelVoucherController.StaffVoucher(idVoucher);
            row[3] = hotelVoucherController.dateMadeVoucher(idVoucher);
            row[4] = hotelVoucherController.totalPriceRoom(idVoucher) + "đ";
            row[5] = hotelVoucherController.totalPriceService(idVoucher) + "đ";
            row[6] = hotelVoucherController.totalPriceVoucher(idVoucher) + "đ";
            model.addRow(row);
        }
    }

    public void hienThiListAnalysQuarter(String year, int quarter) {
        DefaultTableModel model = (DefaultTableModel) tbAnalysis2.getModel();
        model.setRowCount(0);
        Object[] row = new Object[7];
        ArrayList<String> listId = hotelVoucherController.listIdVoucherQuarter(year, quarter);
        for (int i = 0; i < listId.size(); i++) {
            row[0] = (i + 1);
            String idVoucher = listId.get(i);
            row[1] = idVoucher;
            row[2] = hotelVoucherController.StaffVoucher(idVoucher);
            row[3] = hotelVoucherController.dateMadeVoucher(idVoucher);
            row[4] = hotelVoucherController.totalPriceRoom(idVoucher) + "đ";
            row[5] = hotelVoucherController.totalPriceService(idVoucher) + "đ";
            row[6] = hotelVoucherController.totalPriceVoucher(idVoucher) + "đ";
            model.addRow(row);
        }
    }

    public long totalSalary() {
//        Lễ tân
//        Nhân viên bàn Nhân viên phòng
//        Nhân viên dịch vụ
//        Nhân viên bảo vệ
//        Nhân viên vệ sinh
//        Quản lý khách sạn

        long total = 0;
        total += staffController.getNumberStaffByOffice("Lễ tân") * 5000000;
        total += staffController.getNumberStaffByOffice("Nhân viên bàn") * 4500000;
        total += staffController.getNumberStaffByOffice("Nhân viên phòng") * 4500000;
        total += staffController.getNumberStaffByOffice("Nhân viên dịch vụ") * 4500000;
        total += staffController.getNumberStaffByOffice("Nhân viên bảo vệ") * 3000000;
        total += staffController.getNumberStaffByOffice("Nhân viên vệ sinh") * 2500000;
        return total;
    }

    public void analysTotalYear(String year) {
        long totalPriceRoom = 0;
        long totalPriceService = 0;
        long totalPriceVoucher = 0;
        ArrayList<String> listId = hotelVoucherController.listIdVoucherYear(year);
        for (int i = 0; i < listId.size(); i++) {
            String idVoucher = listId.get(i);
            totalPriceRoom += hotelVoucherController.totalPriceRoom(idVoucher);
            totalPriceService += hotelVoucherController.totalPriceService(idVoucher);
        }
        totalPriceVoucher = totalPriceRoom + totalPriceService;
        edtSumSalary3.setText((totalSalary() * 12) + "đ");
        edtSumRoom3.setText(totalPriceRoom + "đ");
        edtService3.setText(totalPriceService + "đ");
        edtSumReceived3.setText(totalPriceVoucher + "đ");
        edtSumEarned3.setText((totalPriceVoucher - totalSalary() * 12) + "đ");
    }

    public void analysTotalMonth(String year, String month) {
        long totalPriceRoom = 0;
        long totalPriceService = 0;
        long totalPriceVoucher = 0;
        ArrayList<String> listId = hotelVoucherController.listIdVoucherMonth(year, month);
        for (int i = 0; i < listId.size(); i++) {
            String idVoucher = listId.get(i);
            totalPriceRoom += hotelVoucherController.totalPriceRoom(idVoucher);
            totalPriceService += hotelVoucherController.totalPriceService(idVoucher);
        }
        totalPriceVoucher = totalPriceRoom + totalPriceService;
        edtSumSalary1.setText(totalSalary() + "đ");
        edtSumRoom1.setText(totalPriceRoom + "đ");
        edtSumService1.setText(totalPriceService + "đ");
        edtSumReceived1.setText(totalPriceVoucher + "đ");
        edtSumEarned1.setText((totalPriceVoucher - totalSalary()) + "đ");
    }

    public void analysTotalQuarter(String year, int quarter) {
        long totalPriceRoom = 0;
        long totalPriceService = 0;
        long totalPriceVoucher = 0;
        ArrayList<String> listId = hotelVoucherController.listIdVoucherQuarter(year, quarter);
        for (int i = 0; i < listId.size(); i++) {
            String idVoucher = listId.get(i);
            totalPriceRoom += hotelVoucherController.totalPriceRoom(idVoucher);
            totalPriceService += hotelVoucherController.totalPriceService(idVoucher);
        }
        totalPriceVoucher = totalPriceRoom + totalPriceService;
        edtSumSalary2.setText((totalSalary() * 4) + "đ");
        edtSumRoom2.setText(totalPriceRoom + "đ");
        edtSumService2.setText(totalPriceService + "đ");
        edtSumReceived2.setText(totalPriceVoucher + "đ");
        edtSumEarned2.setText((totalPriceVoucher - totalSalary() * 4) + "đ");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbMonth1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAnalysis1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnPrintReport1 = new javax.swing.JButton();
        edtSumRoom1 = new javax.swing.JTextField();
        edtSumService1 = new javax.swing.JTextField();
        edtSumReceived1 = new javax.swing.JTextField();
        edtSumSalary1 = new javax.swing.JTextField();
        edtSumEarned1 = new javax.swing.JTextField();
        tfYear = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cbQuarter = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbAnalysis2 = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btnPrintReport2 = new javax.swing.JButton();
        edtSumRoom2 = new javax.swing.JTextField();
        edtSumService2 = new javax.swing.JTextField();
        edtSumReceived2 = new javax.swing.JTextField();
        edtSumSalary2 = new javax.swing.JTextField();
        edtSumEarned2 = new javax.swing.JTextField();
        tfYear2 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbAnalysis3 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        btnPrintReport3 = new javax.swing.JButton();
        edtSumRoom3 = new javax.swing.JTextField();
        edtService3 = new javax.swing.JTextField();
        edtSumReceived3 = new javax.swing.JTextField();
        edtSumSalary3 = new javax.swing.JTextField();
        edtSumEarned3 = new javax.swing.JTextField();
        tfYear3 = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1022, 525));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Tháng");

        cbMonth1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbMonth1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMonth1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMonth1ItemStateChanged(evt);
            }
        });
        cbMonth1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonth1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Năm");

        tbAnalysis1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbAnalysis1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Voucher", "Nhân viên ghi phiếu", "Ngày lập", "Tiền phòng", "Tiền dịch vụ", "Tổng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbAnalysis1.setRowHeight(25);
        jScrollPane1.setViewportView(tbAnalysis1);
        if (tbAnalysis1.getColumnModel().getColumnCount() > 0) {
            tbAnalysis1.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbAnalysis1.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbAnalysis1.getColumnModel().getColumn(2).setPreferredWidth(150);
            tbAnalysis1.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbAnalysis1.getColumnModel().getColumn(5).setResizable(false);
            tbAnalysis1.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tổng tiền phòng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tổng tiền dịch vụ:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Tổng tiền thu về:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Trả lương nhân viên");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Tổng doanh thu:");

        btnPrintReport1.setBackground(new java.awt.Color(0, 153, 255));
        btnPrintReport1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrintReport1.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintReport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/printer_white.png"))); // NOI18N
        btnPrintReport1.setText("In báo cáo");
        btnPrintReport1.setIconTextGap(15);
        btnPrintReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReport1ActionPerformed(evt);
            }
        });

        edtSumRoom1.setEditable(false);
        edtSumRoom1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumService1.setEditable(false);
        edtSumService1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumReceived1.setEditable(false);
        edtSumReceived1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumSalary1.setEditable(false);
        edtSumSalary1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumEarned1.setEditable(false);
        edtSumEarned1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfYear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtSumRoom1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtSumService1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtSumReceived1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(edtSumEarned1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(edtSumSalary1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addComponent(btnPrintReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 297, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfYear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumRoom1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumService1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumReceived1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumSalary1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumEarned1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(btnPrintReport1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Theo tháng", jPanel1);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Quý");

        cbQuarter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbQuarter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbQuarter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbQuarterItemStateChanged(evt);
            }
        });
        cbQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbQuarterActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Năm");

        tbAnalysis2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbAnalysis2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Voucher", "Nhân viên ghi phiếu", "Ngày lập", "Tiền phòng", "Tiền dịch vụ", "Tổng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbAnalysis2.setRowHeight(25);
        jScrollPane4.setViewportView(tbAnalysis2);
        if (tbAnalysis2.getColumnModel().getColumnCount() > 0) {
            tbAnalysis2.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbAnalysis2.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbAnalysis2.getColumnModel().getColumn(2).setPreferredWidth(150);
            tbAnalysis2.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbAnalysis2.getColumnModel().getColumn(5).setResizable(false);
            tbAnalysis2.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Tổng tiền phòng:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Tổng tiền dịch vụ:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Tổng tiền thu về:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Trả lương nhân viên");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Tổng doanh thu:");

        btnPrintReport2.setBackground(new java.awt.Color(0, 153, 255));
        btnPrintReport2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrintReport2.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintReport2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/printer_white.png"))); // NOI18N
        btnPrintReport2.setText("In báo cáo");
        btnPrintReport2.setIconTextGap(15);
        btnPrintReport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReport2ActionPerformed(evt);
            }
        });

        edtSumRoom2.setEditable(false);
        edtSumRoom2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumService2.setEditable(false);
        edtSumService2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumReceived2.setEditable(false);
        edtSumReceived2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumSalary2.setEditable(false);
        edtSumSalary2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumEarned2.setEditable(false);
        edtSumEarned2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfYear2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbQuarter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(tfYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtSumRoom2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtSumService2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtSumReceived2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(edtSumEarned2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(edtSumSalary2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addComponent(btnPrintReport2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 297, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbQuarter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfYear2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumRoom2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumService2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumReceived2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumSalary2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumEarned2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)))
                    .addComponent(btnPrintReport2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Theo quý", jPanel4);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Năm");

        tbAnalysis3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbAnalysis3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Voucher", "Nhân viên ghi phiếu", "Ngày lập", "Tiền phòng", "Tiền dịch vụ", "Tổng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbAnalysis3.setRowHeight(25);
        jScrollPane5.setViewportView(tbAnalysis3);
        if (tbAnalysis3.getColumnModel().getColumnCount() > 0) {
            tbAnalysis3.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbAnalysis3.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbAnalysis3.getColumnModel().getColumn(2).setPreferredWidth(150);
            tbAnalysis3.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbAnalysis3.getColumnModel().getColumn(5).setResizable(false);
            tbAnalysis3.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Tổng tiền phòng:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Tổng tiền dịch vụ:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Tổng tiền thu về:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Trả lương nhân viên");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Tổng doanh thu:");

        btnPrintReport3.setBackground(new java.awt.Color(0, 153, 255));
        btnPrintReport3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrintReport3.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintReport3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/printer_white.png"))); // NOI18N
        btnPrintReport3.setText("In báo cáo");
        btnPrintReport3.setIconTextGap(15);
        btnPrintReport3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReport3ActionPerformed(evt);
            }
        });

        edtSumRoom3.setEditable(false);
        edtSumRoom3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtService3.setEditable(false);
        edtService3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumReceived3.setEditable(false);
        edtSumReceived3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumSalary3.setEditable(false);
        edtSumSalary3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        edtSumEarned3.setEditable(false);
        edtSumEarned3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfYear3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(tfYear3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtSumRoom3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtService3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edtSumReceived3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(edtSumEarned3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(edtSumSalary3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addComponent(btnPrintReport3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 297, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(tfYear3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumRoom3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtService3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumReceived3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumSalary3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtSumEarned3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)))
                    .addComponent(btnPrintReport3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Theo năm", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbMonth1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonth1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMonth1ActionPerformed

    private void cbQuarterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbQuarterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbQuarterActionPerformed

    private void btnPrintReport3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReport3ActionPerformed
        ExportFile exp = new ExportFile();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int save = chooser.showSaveDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            try {
                File file = new File(chooser.getSelectedFile() + ".xls");
                exp.printTable(tbAnalysis3, file);
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (WriteException e) {
            }
        }
    }//GEN-LAST:event_btnPrintReport3ActionPerformed

    private void btnPrintReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReport1ActionPerformed

        ExportFile exp = new ExportFile();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int save = chooser.showSaveDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            try {
                File file = new File(chooser.getSelectedFile() + ".xls");
                exp.printTable(tbAnalysis1, file);
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (WriteException e) {
            }
        }
    }//GEN-LAST:event_btnPrintReport1ActionPerformed

    private void cbMonth1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMonth1ItemStateChanged
        // TODO add your handling code here:
        String year = tfYear.getText();
        String month = cbMonth1.getSelectedItem().toString();
        hienThiListAnalysMonth(year, month);
        analysTotalMonth(year, month);
    }//GEN-LAST:event_cbMonth1ItemStateChanged

    private void cbQuarterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbQuarterItemStateChanged
        // TODO add your handling code here:
        String year = tfYear2.getText();
        int quarter = Integer.parseInt(cbQuarter.getSelectedItem().toString());
        hienThiListAnalysQuarter(year, quarter);
        analysTotalQuarter(year, quarter);
    }//GEN-LAST:event_cbQuarterItemStateChanged

    private void btnPrintReport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReport2ActionPerformed
        // TODO add your handling code here:
        ExportFile exp = new ExportFile();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int save = chooser.showSaveDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            try {
                File file = new File(chooser.getSelectedFile() + ".xls");
                exp.printTable(tbAnalysis2, file);
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (WriteException e) {
            }
        }
    }//GEN-LAST:event_btnPrintReport2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintReport1;
    private javax.swing.JButton btnPrintReport2;
    private javax.swing.JButton btnPrintReport3;
    private javax.swing.JComboBox<String> cbMonth1;
    private javax.swing.JComboBox<String> cbQuarter;
    private javax.swing.JTextField edtService3;
    private javax.swing.JTextField edtSumEarned1;
    private javax.swing.JTextField edtSumEarned2;
    private javax.swing.JTextField edtSumEarned3;
    private javax.swing.JTextField edtSumReceived1;
    private javax.swing.JTextField edtSumReceived2;
    private javax.swing.JTextField edtSumReceived3;
    private javax.swing.JTextField edtSumRoom1;
    private javax.swing.JTextField edtSumRoom2;
    private javax.swing.JTextField edtSumRoom3;
    private javax.swing.JTextField edtSumSalary1;
    private javax.swing.JTextField edtSumSalary2;
    private javax.swing.JTextField edtSumSalary3;
    private javax.swing.JTextField edtSumService1;
    private javax.swing.JTextField edtSumService2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbAnalysis1;
    private javax.swing.JTable tbAnalysis2;
    private javax.swing.JTable tbAnalysis3;
    private javax.swing.JTextField tfYear;
    private javax.swing.JTextField tfYear2;
    private javax.swing.JTextField tfYear3;
    // End of variables declaration//GEN-END:variables
}
