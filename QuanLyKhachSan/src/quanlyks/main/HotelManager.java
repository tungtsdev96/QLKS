/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import quanlyks.controller.SettingController;
import quanlyks.views.LoginScreen;
import quanlyks.views.SetupConnection;

/**
 *
 * @author Tuyen Ti Ton
 */
public class HotelManager {

    public static SettingController setting = new SettingController();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (setting.getConnectionProperty().size() == 0) {
                        SetupConnection setup = new SetupConnection();
                        setup.setVisible(true);
                        setup.setLocationRelativeTo(null);
                        setup.setTitle("Thiết lập");
                        setup.pack();
                        setup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else {
                    if (setting.getConnectionProperty().get(0) == null || setting.getConnectionProperty().get(1) == null) {
                        SetupConnection setup = new SetupConnection();
                        setup.setVisible(true);
                        setup.setLocationRelativeTo(null);
                        setup.setTitle("Thiết lập");
                        setup.pack();
                        setup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } else {
                        LoginScreen login = new LoginScreen();
                        login.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        login.setVisible(true);
                        login.pack();
                        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        login.setLocationRelativeTo(null);
                    }
                }
            }
        }
        );
    }

}
