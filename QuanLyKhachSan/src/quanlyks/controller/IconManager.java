/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import javax.swing.ImageIcon;

/**
 *
 * @author Tuyen Ti Ton
 */
public class IconManager {

    public IconManager() {

    }

    public ImageIcon getIcon(String name) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/quanlyks/images/" + name + ".png"));
        return icon;
    }
    public ImageIcon iconEmpty(){
        return getIcon("empty_house");
    }
    public ImageIcon iconHired(){
        return getIcon("people_lock");
    }
    public ImageIcon iconBooked(){
        return getIcon("booked_room");
    }

    public ImageIcon iconRepair(){
        return getIcon("home_repair");
    }
    public ImageIcon iconBack(){
        return getIcon("back_black");
    }
    public ImageIcon iconSearch(){
        return getIcon("search_black");
    }
    public ImageIcon iconDone(){
        return getIcon("check_done");
    }
    public ImageIcon iconDelete(){
        return getIcon("delete_blue");
    }
    public ImageIcon iconLogout(){
        return getIcon("logout_blue");
    }
    public ImageIcon iconExit(){
        return getIcon("cancel_blue");
    }
    public ImageIcon iconError(){
        return getIcon("error_red");
    }
    public ImageIcon iconSave(){
        return getIcon("save_blue");
    }
}
