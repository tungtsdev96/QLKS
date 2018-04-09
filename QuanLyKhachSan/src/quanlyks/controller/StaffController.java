/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.util.ArrayList;
import quanlyks.model.Staff;
import quanlyks.model.StaffManager;

/**
 *
 * @author tungb_000
 */
public class StaffController {

    StaffManager staffManager = new StaffManager();

    ;
    
    public ArrayList<Staff> getListStaff() {
        return staffManager.getListStaff();
    }

    public int getNumberStaff() {
        return staffManager.getNumberStaff();
    }

    public ArrayList<String> getListOffice() {
        return staffManager.getListOffice();
    }

    public int getNumberStaffByOffice(String office) {
        return staffManager.getNumberStaffByOffice(office);
    }

    public ArrayList<Staff> searchRoom(String key) {
        return staffManager.searchStaff(key);
    }

    public boolean addStaff(Staff staff) {
        return staffManager.addStaff(staff);
    }

    public boolean updateStaff(Staff staff) {
        return staffManager.updateInformationOfStaff(staff);
    }

    public boolean deleteStaff(String idStaff) {
        return staffManager.deleteStaff(idStaff);
    }

    public Staff getStaffByIdStaff(String idStaff) {
        return staffManager.getStaffByIdStaff(idStaff);
    }
}
