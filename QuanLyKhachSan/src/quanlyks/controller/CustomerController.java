/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.util.ArrayList;
import quanlyks.model.*;
import quanlyks.model.CustomerManager;

/**
 *
 * @author tungb_000
 */
public class CustomerController {

    CustomerManager customerManager;

    public CustomerController() {
        customerManager = new CustomerManager();
    }

    public ArrayList<String> getListNational() {
        return customerManager.getListNational();
    }

    public int countCustomerByNational(String national) {
        return customerManager.countCustomerByNational(national);
    }

    public boolean addCustomer(Customer customer) {
        return customerManager.addCustomer(customer);
    }

    public ArrayList<Customer> getListCustomer() {
        return customerManager.getListCustomer();
    }

    public Customer getInfoCustomer(ArrayList<Customer> listCustomer, int row) {
        Customer ctm = null;
        ctm = listCustomer.get(row);
        return ctm;
    }

    public boolean updateCustomer(Customer customer) {
        return customerManager.updateInformationOfCustomer(customer);
    }

    public ArrayList<Customer> searchCustomer(String query) {
        return customerManager.listSearchCustomer(query);
    }

    public Customer getCustomerByIdCustomer(String idCustomer) {
        return customerManager.getCustomerById(idCustomer);
    }

    public boolean deleteCustomer(String idCustomer) {
        return customerManager.deleteCustomer(idCustomer);
    }

    public boolean updateStatusCustomerInListCustomer(String identify) {
        return customerManager.updateStatusCustomerInListCustomer(identify);
    }
}
