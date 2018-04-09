/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

import java.sql.Date;

/**
 *
 * @author tungb_000
 */
public class Staff {

    private String idStaff;
    private String nameStaff;
    private String dateOfBirth;
    private String country; //que
    private String sex;
    private String identify;
    private String phoneNumber;
    private String email;
    private String office; //chuc vu
    private String userName;
    private String password;

    public Staff(String idStaff, String nameStaff, String dateOfBirth, String country, String sex, String identify, String phoneNumber, String email, String office, String userName, String password) {
        this.idStaff = idStaff;
        this.nameStaff = nameStaff;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.sex = sex;
        this.identify = identify;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.office = office;
        this.userName = userName;
        this.password = password;
    }

    public Staff(String idStaff, String nameStaff, String dateOfBirth, String country, String sex, String identify, String phoneNumber, String email, String office) {
        this.idStaff = idStaff;
        this.nameStaff = nameStaff;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.sex = sex;
        this.identify = identify;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.office = office;
    }

    public Staff() {

    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
