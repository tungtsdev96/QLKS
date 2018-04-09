/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.model;

/**
 *
 * @author tungb_000
 */
public class Service {

    private String idService;
    private String nameService;
    private long priceOfService;
    private int noService;

    public Service() {
    }

    // hàm dịch vụ để quản lý
    public Service(String idService, String nameService, long priceOfService) {
        this.idService = idService;
        this.nameService = nameService;
        this.priceOfService = priceOfService;
    }

    public int getNoService() {
        return noService;
    }

    public void setNoService(int noService) {
        this.noService = noService;
    }
    // hàm dịch vụ cho phiếu đặt phòng
    public Service(String idService, String nameService, long priceOfService, int noService) {
        this.idService = idService;
        this.nameService = nameService;
        this.priceOfService = priceOfService;
        this.noService = noService;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public long getPriceOfService() {
        return priceOfService;
    }

    public void setPriceOfService(long priceOfService) {
        this.priceOfService = priceOfService;
    }

}
