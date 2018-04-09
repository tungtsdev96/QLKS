/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Tuyen Ti Ton
 */
public class SettingController {

    Properties pro = new Properties();
    InputStream input = null;
    OutputStream output = null;

    public SettingController() {

    }

    public boolean setConnectionProperty(String user, String pass) {
        try {
            output = new FileOutputStream("connect.properties");
            pro.setProperty("user", user);
            pro.setProperty("pass", pass);
            pro.store(output, null);
        } catch (IOException e) {
        } finally {
            if (output != null) {
                try {
                    output.close();
                    return true;
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    public boolean setProperties(String name, String welcom, String address, String phone, String notification) {
        try {
            output = new FileOutputStream("config.properties");
            pro.setProperty("name", name);
            pro.setProperty("welcom", welcom);
            pro.setProperty("address", address);
            pro.setProperty("phone", phone);
            pro.setProperty("notification", notification);
            pro.store(output, null);
        } catch (IOException e) {
        } finally {
            if (output != null) {
                try {
                    output.close();
                    return true;
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    public ArrayList<String> getProperties() throws FileNotFoundException {
        ArrayList<String> setting = new ArrayList<>();
        try {
            input = new FileInputStream("config.properties");
            pro.load(input);
            setting.add(pro.getProperty("name"));
            setting.add(pro.getProperty("welcom"));
            setting.add(pro.getProperty("address"));
            setting.add(pro.getProperty("phone"));
            setting.add(pro.getProperty("notification"));
        } catch (IOException e) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return setting;
    }

    public ArrayList<String> getConnectionProperty() {
        ArrayList<String> info = new ArrayList<>();
        try {
            input = new FileInputStream("connect.properties");
            pro.load(input);
            info.add(pro.getProperty("user"));
            info.add(pro.getProperty("pass"));
        } catch (IOException e) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return info;
    }

}
