package quanlyks.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import quanlyks.controller.IconManager;
import quanlyks.controller.SettingController;
import quanlyks.controller.StaffController;
import quanlyks.model.Staff;

/**
 *
 * @author Tuyen Ti Ton
 */
public class MainScreen extends javax.swing.JFrame implements KeyListener {

    private GridBagLayout grid;
    public static MainPanel mainPanel;
    private AnalysisPanel analysisPanel;
    private CustomerPanel customerPanel;
    private PersonalPanel personalPanel;
    private RoomPanel roomPanel;
    private ServicePanel servicePanel;
    private StaffPanel staffPanel;
    public static int type;
    private String user;
    private IconManager icon = new IconManager();
    private SettingController settingControoler = new SettingController();
    private StaffController staffController;
    private ArrayList<Staff> listStaff;

    /**
     * Creates new form MainScreen
     */
    public MainScreen() {
        initComponents();
    }

    public MainScreen(int type, String user) throws FileNotFoundException {
        staffController = new StaffController();
        listStaff = staffController.getListStaff();
        this.type = type;
        this.user = user;
        // type == 1 tương ứng với màn hình quản lý
        // type == 2 tương ứng với màn hình nhân viên
        if (type == 1) {
            getTimeSystem();
            initComponents();
            setNavigation(1);
            addPanels();
            setContentsPanel(1);
            settingSystem();
            // sự kiện enter trong ô tìm kiếm
        } else if (type == 2) {
            getTimeSystem();
            initComponents();
            setNavigation(1);
            btnCustomer.setText("Thông tin khách hàng");
            btnRoom.setText("Thông tin phòng");
            btnService.setText("Thông tin dịch vụ");
            btnStaff.setText("Thông tin nhân viên");
            btnSettings.setEnabled(false);
            addPanels();
            setContentsPanel(1);
            settingSystem();

        }
        lbName.setText("Xin Chào: " + getStaffFromUser(user.toLowerCase()).getNameStaff());
    }

    private Staff getStaffFromUser(String user) { //public static
        Staff staff = null;
        for (int i = 0; i < listStaff.size(); i++) {
            if (user.equalsIgnoreCase(listStaff.get(i).getUserName())) {
                staff = listStaff.get(i);
                break;
            }
        }
        return staff;
    }

    private void addPanels() {
        grid = new GridBagLayout();
        panelContents.setLayout(grid);
        mainPanel = new MainPanel(getStaffFromUser(user));
        analysisPanel = new AnalysisPanel();
        customerPanel = new CustomerPanel();
        personalPanel = new PersonalPanel(getStaffFromUser(user));
        roomPanel = new RoomPanel();
        servicePanel = new ServicePanel();
        staffPanel = new StaffPanel();
        panelContents.add(mainPanel);
        mainPanel.setVisible(false);
        mainPanel.setPreferredSize(new Dimension(1025, 550));
        panelContents.add(analysisPanel);
        analysisPanel.setVisible(false);
        analysisPanel.setPreferredSize(new Dimension(1025, 550));
        panelContents.add(customerPanel);
        customerPanel.setVisible(false);
        customerPanel.setPreferredSize(new Dimension(1025, 565));
        panelContents.add(personalPanel);
        personalPanel.setVisible(false);
        personalPanel.setPreferredSize(new Dimension(1025, 550));
        panelContents.add(roomPanel);
        roomPanel.setVisible(false);
        roomPanel.setPreferredSize(new Dimension(1025, 565));
        panelContents.add(servicePanel);
        servicePanel.setVisible(false);
        servicePanel.setPreferredSize(new Dimension(1025, 565));
        panelContents.add(staffPanel);
        staffPanel.setVisible(false);
        staffPanel.setPreferredSize(new Dimension(1025, 565));
    }

    private void setContentsPanel(int position) {
        switch (position) {
            case 1: {
//                mainPanel = new MainPanel(getStaffFromUser(user));
//                panelContents.add(mainPanel);
//                mainPanel.setPreferredSize(new Dimension(1025, 550));
                mainPanel.setVisible(true);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(false);
                personalPanel.setVisible(false);
                roomPanel.setVisible(false);
                servicePanel.setVisible(false);
                staffPanel.setVisible(false);
                break;
            }
            case 2: {
//                roomPanel = new RoomPanel();
//                panelContents.add(roomPanel);
//                roomPanel.setPreferredSize(new Dimension(1025, 565));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(false);
                personalPanel.setVisible(false);
                roomPanel.setVisible(true);
                servicePanel.setVisible(false);
                staffPanel.setVisible(false);
                break;
            }
            case 3: {
//                servicePanel = new ServicePanel();
//                panelContents.add(servicePanel);
//                servicePanel.setPreferredSize(new Dimension(1025, 565));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(false);
                personalPanel.setVisible(false);
                roomPanel.setVisible(false);
                servicePanel.setVisible(true);
                staffPanel.setVisible(false);
                break;
            }
            case 4: {
//                customerPanel = new CustomerPanel();
//                panelContents.add(customerPanel);
//                customerPanel.setPreferredSize(new Dimension(1025, 565));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(true);
                personalPanel.setVisible(false);
                roomPanel.setVisible(false);
                servicePanel.setVisible(false);
                staffPanel.setVisible(false);
                break;
            }
            case 5: {
//                staffPanel = new StaffPanel();
//                panelContents.add(staffPanel);
//                staffPanel.setPreferredSize(new Dimension(1025, 565));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(false);
                personalPanel.setVisible(false);
                roomPanel.setVisible(false);
                servicePanel.setVisible(false);
                staffPanel.setVisible(true);
                break;
            }
            case 6: {
//                analysisPanel = new AnalysisPanel();
//                panelContents.add(analysisPanel);
//                analysisPanel.setPreferredSize(new Dimension(1025, 550));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(true);
                customerPanel.setVisible(false);
                personalPanel.setVisible(false);
                roomPanel.setVisible(false);
                servicePanel.setVisible(false);
                staffPanel.setVisible(false);
                break;
            }
            case 7: {
//                personalPanel = new PersonalPanel(getStaffFromUser(user));
//                panelContents.add(personalPanel);
//                personalPanel.setPreferredSize(new Dimension(1025, 550));
                mainPanel.setVisible(false);
                analysisPanel.setVisible(false);
                customerPanel.setVisible(false);
                personalPanel.setVisible(true);
                roomPanel.setVisible(false);
                servicePanel.setVisible(false);
                staffPanel.setVisible(false);
                break;
            }
        }
    }

    private void setNavigation(int position) {
        switch (position) {
            case 1: {
                navi1.setVisible(true);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 2: {
                navi1.setVisible(false);
                navi2.setVisible(true);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 3: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(true);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 4: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(true);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 5: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(true);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 6: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(true);
                navi7.setVisible(false);
                navi10.setVisible(false);
                break;
            }
            case 7: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(true);
                navi10.setVisible(false);
                break;
            }

            case 10: {
                navi1.setVisible(false);
                navi2.setVisible(false);
                navi3.setVisible(false);
                navi4.setVisible(false);
                navi5.setVisible(false);
                navi6.setVisible(false);
                navi7.setVisible(false);
                navi10.setVisible(true);
                break;
            }
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    public static java.sql.Date utilDateToSqlDate(java.util.Date date) {
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        return sqldate;
    }

    public static Date stringToDaTe(String s) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = formatter.parse(s);
        //java.sql.Date date1 = new java.sql.Date(date.getTime());
        return date;
    }

    private void getTimeSystem() {
        Timer time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat tfm = new SimpleDateFormat("HH:mm:ss");
                lbTimeSystem.setText("Giờ: " + tfm.format(new Date()));
                SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
                lbDateSystem.setText("Ngày: " + dfm.format(new Date()));
            }
        });
        time.start();
    }

    public void settingSystem() throws FileNotFoundException {
        try {
            ArrayList<String> settings = settingControoler.getProperties();
            if (settings.isEmpty()) {
                String name = "Smart Hotel";
                String welcom = "Smart Hotel-Phần mềm quản lý khách sạn chuyên nghiệp";
                String address = "Trường Đại học Bách Khoa Hà Nội";
                String phone = "00000000000000";
                String noti = "Chương trình được xây dựng bởi nhóm sinh viên Bách Khoa, trong khuôn khổ môn học Project I";
                txtHotelName.setText(name);
                txtNotification.setText("Thông báo: " + noti);
                txtSologan.setText(welcom);
                txtSologan.setText("<html>" + welcom + "</html>");
                if (settingControoler.setProperties(name, welcom, address, phone, noti)) {
                    JOptionPane.showMessageDialog(panelContents, "Hệ thống được thiết lập mặc định", "Thiết lập", JOptionPane.INFORMATION_MESSAGE, icon.iconDone());
                }
            } else {
                txtHotelName.setText(settings.get(0));
                txtSologan.setText("<html>" + settings.get(1) + "</html>");
                txtNotification.setText("Thông báo: " + settings.get(4));

            }
        } catch (HeadlessException | FileNotFoundException e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelTitleBar = new javax.swing.JPanel();
        txtHotelName = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        lbDateSystem = new javax.swing.JLabel();
        lbTimeSystem = new javax.swing.JLabel();
        txtSologan = new javax.swing.JLabel();
        lbName1 = new javax.swing.JLabel();
        panelContents = new javax.swing.JPanel();
        panelMenu = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnMainScreen = new javax.swing.JButton();
        btnRoom = new javax.swing.JButton();
        btnService = new javax.swing.JButton();
        btnCustomer = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        btnAnalysis = new javax.swing.JButton();
        btnPersonal = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        navi1 = new javax.swing.JLabel();
        navi2 = new javax.swing.JLabel();
        navi3 = new javax.swing.JLabel();
        navi4 = new javax.swing.JLabel();
        navi5 = new javax.swing.JLabel();
        navi6 = new javax.swing.JLabel();
        navi7 = new javax.swing.JLabel();
        navi10 = new javax.swing.JLabel();
        txtNotification = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(900, 650));

        panelTitleBar.setBackground(new java.awt.Color(0, 153, 255));

        txtHotelName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtHotelName.setForeground(new java.awt.Color(255, 255, 255));
        txtHotelName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/hotel.png"))); // NOI18N
        txtHotelName.setText("Khách sạn BK");
        txtHotelName.setIconTextGap(15);

        lbName.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbName.setText("Xin chào: Nguyễn Văn Tuyển");

        lbDateSystem.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbDateSystem.setForeground(new java.awt.Color(255, 255, 255));
        lbDateSystem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbTimeSystem.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbTimeSystem.setForeground(new java.awt.Color(255, 255, 255));
        lbTimeSystem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtSologan.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txtSologan.setForeground(new java.awt.Color(255, 255, 255));
        txtSologan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSologan.setText("Sologan");
        txtSologan.setAutoscrolls(true);

        lbName1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbName1.setForeground(new java.awt.Color(255, 255, 255));
        lbName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbName1.setText("Chúc bạn 1 ngày làm việc vui vẻ!");

        javax.swing.GroupLayout panelTitleBarLayout = new javax.swing.GroupLayout(panelTitleBar);
        panelTitleBar.setLayout(panelTitleBarLayout);
        panelTitleBarLayout.setHorizontalGroup(
            panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTitleBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHotelName, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSologan, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbTimeSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(lbDateSystem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTitleBarLayout.createSequentialGroup()
                        .addComponent(lbName1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelTitleBarLayout.setVerticalGroup(
            panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTitleBarLayout.createSequentialGroup()
                .addGroup(panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelTitleBarLayout.createSequentialGroup()
                        .addGroup(panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbTimeSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbDateSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lbName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtSologan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHotelName, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContentsLayout = new javax.swing.GroupLayout(panelContents);
        panelContents.setLayout(panelContentsLayout);
        panelContentsLayout.setHorizontalGroup(
            panelContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1025, Short.MAX_VALUE)
        );
        panelContentsLayout.setVerticalGroup(
            panelContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setBackground(new java.awt.Color(0, 153, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Menu");
        jLabel2.setOpaque(true);

        btnMainScreen.setBackground(new java.awt.Color(0, 153, 255));
        btnMainScreen.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMainScreen.setForeground(new java.awt.Color(255, 255, 255));
        btnMainScreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/mainscreen.png"))); // NOI18N
        btnMainScreen.setText("Màn hình chính");
        btnMainScreen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMainScreen.setIconTextGap(15);
        btnMainScreen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMainScreenMouseClicked(evt);
            }
        });
        btnMainScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainScreenActionPerformed(evt);
            }
        });

        btnRoom.setBackground(new java.awt.Color(0, 153, 255));
        btnRoom.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnRoom.setForeground(new java.awt.Color(255, 255, 255));
        btnRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/home_white.png"))); // NOI18N
        btnRoom.setText("Quản lý phòng");
        btnRoom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRoom.setIconTextGap(15);
        btnRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoomActionPerformed(evt);
            }
        });

        btnService.setBackground(new java.awt.Color(0, 153, 255));
        btnService.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnService.setForeground(new java.awt.Color(255, 255, 255));
        btnService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/dropbox_logo.png"))); // NOI18N
        btnService.setText("Quản lý dịch vụ");
        btnService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnService.setIconTextGap(15);
        btnService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServiceActionPerformed(evt);
            }
        });

        btnCustomer.setBackground(new java.awt.Color(0, 153, 255));
        btnCustomer.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/customer_white.png"))); // NOI18N
        btnCustomer.setText("Quản lý khách hàng");
        btnCustomer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCustomer.setIconTextGap(15);
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });

        btnStaff.setBackground(new java.awt.Color(0, 153, 255));
        btnStaff.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnStaff.setForeground(new java.awt.Color(255, 255, 255));
        btnStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/employee.png"))); // NOI18N
        btnStaff.setText("Quản lý nhân viên");
        btnStaff.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStaff.setIconTextGap(15);
        btnStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffActionPerformed(evt);
            }
        });

        btnAnalysis.setBackground(new java.awt.Color(0, 153, 255));
        btnAnalysis.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnAnalysis.setForeground(new java.awt.Color(255, 255, 255));
        btnAnalysis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/analysis.png"))); // NOI18N
        btnAnalysis.setText("Thống kê, báo cáo");
        btnAnalysis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAnalysis.setIconTextGap(15);
        btnAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalysisActionPerformed(evt);
            }
        });

        btnPersonal.setBackground(new java.awt.Color(0, 153, 255));
        btnPersonal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnPersonal.setForeground(new java.awt.Color(255, 255, 255));
        btnPersonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/info.png"))); // NOI18N
        btnPersonal.setText("Thông tin cá nhân");
        btnPersonal.setToolTipText("");
        btnPersonal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPersonal.setIconTextGap(15);
        btnPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonalActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 153, 255));
        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/logout_white.png"))); // NOI18N
        btnLogout.setText("Đăng xuất");
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.setIconTextGap(15);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(0, 153, 255));
        btnExit.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/cancel_white.png"))); // NOI18N
        btnExit.setText("Thoát");
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExit.setIconTextGap(15);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnSettings.setBackground(new java.awt.Color(0, 153, 255));
        btnSettings.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/settings.png"))); // NOI18N
        btnSettings.setText("Tùy chỉnh hệ thống");
        btnSettings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSettings.setIconTextGap(15);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        navi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        navi10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlyks/images/navigation.png"))); // NOI18N

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPersonal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnalysis, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMainScreen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRoom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnService, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCustomer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(btnStaff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addComponent(navi3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(navi5)
                                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navi2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(navi4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(navi6))
                            .addComponent(navi7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(navi10, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(2, 2, 2))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMainScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(navi1)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(navi2)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnService, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(navi3)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(navi4)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(navi5)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAnalysis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(navi6)))
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(navi7)))
                .addGap(2, 2, 2)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(navi10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtNotification.setBackground(new java.awt.Color(0, 153, 255));
        txtNotification.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        txtNotification.setForeground(new java.awt.Color(255, 255, 255));
        txtNotification.setText("Thông báo:");
        txtNotification.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelContents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelContents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotification, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMainScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainScreenActionPerformed
        // TODO add your handling code here:
        this.revalidate();
        this.repaint();
        setNavigation(1);
        setContentsPanel(1);
    }//GEN-LAST:event_btnMainScreenActionPerformed

    private void btnRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomActionPerformed
        // TODO add your handling code here:
        setNavigation(2);
        setContentsPanel(2);

    }//GEN-LAST:event_btnRoomActionPerformed

    private void btnServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServiceActionPerformed
        // TODO add your handling code here:
        setNavigation(3);
        setContentsPanel(3);
    }//GEN-LAST:event_btnServiceActionPerformed

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
        // TODO add your handling code here:
        setNavigation(4);
        setContentsPanel(4);
    }//GEN-LAST:event_btnCustomerActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed
        // TODO add your handling code here:
        setNavigation(5);
        setContentsPanel(5);
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalysisActionPerformed
        // TODO add your handling code here:
        setNavigation(6);
        setContentsPanel(6);
    }//GEN-LAST:event_btnAnalysisActionPerformed

    private void btnPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonalActionPerformed
        // TODO add your handling code here:
        setNavigation(7);
        setContentsPanel(7);
    }//GEN-LAST:event_btnPersonalActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        LoginScreen login = new LoginScreen();
        login.setExtendedState(JFrame.MAXIMIZED_BOTH);
        login.setVisible(true);
        login.pack();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        int output = JOptionPane.showConfirmDialog(this, "Thoát chương trình?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon.iconExit());
        if (output == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        // TODO add your handling code here:
        setNavigation(10);
        SettingsSystem settings = new SettingsSystem(this);
        settings.setVisible(true);
        settings.pack();
        settings.setLocationRelativeTo(null);
        settings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnMainScreenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMainScreenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMainScreenMouseClicked

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
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalysis;
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMainScreen;
    private javax.swing.JButton btnPersonal;
    private javax.swing.JButton btnRoom;
    private javax.swing.JButton btnService;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnStaff;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbDateSystem;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbName1;
    private javax.swing.JLabel lbTimeSystem;
    private javax.swing.JLabel navi1;
    private javax.swing.JLabel navi10;
    private javax.swing.JLabel navi2;
    private javax.swing.JLabel navi3;
    private javax.swing.JLabel navi4;
    private javax.swing.JLabel navi5;
    private javax.swing.JLabel navi6;
    private javax.swing.JLabel navi7;
    public static javax.swing.JPanel panelContents;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelTitleBar;
    public javax.swing.JLabel txtHotelName;
    public javax.swing.JLabel txtNotification;
    public javax.swing.JLabel txtSologan;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            int output = JOptionPane.showConfirmDialog(this, "Thoát chương trình?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon.iconLogout());
            if (output == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
