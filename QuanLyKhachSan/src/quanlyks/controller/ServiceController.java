/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import java.util.ArrayList;
import quanlyks.model.Service;
import quanlyks.model.ServiceManager;

/**
 *
 * @author Tuyen Ti Ton
 */
public class ServiceController {

    private ServiceManager serviceManager = new ServiceManager();

    public ArrayList<Service> getListServices() {
        return serviceManager.getListServices();
    }

    public int getNumberService() {
        return serviceManager.getNumberService();
    }

    public ArrayList<Service> searchServices(String key) {
        return serviceManager.searchService(key);
    }

    public boolean addService(Service service) {
        return serviceManager.addService(service);
    }

    public boolean updateService(Service service) {
        return serviceManager.updateInformationOfService(service);
    }

    public boolean deleteService(String idService) {
        return serviceManager.deleteService(idService);
    }
}
