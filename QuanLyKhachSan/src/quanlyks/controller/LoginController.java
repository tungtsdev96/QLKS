/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import quanlyks.model.LoginManager;

/**
 *
 * @author tungb_000
 */
public class LoginController {
    LoginManager login;
    
    public LoginController(){
        login = new LoginManager();
    }
    
    public int checkLogin(String user, String pass){
        return login.checkLogin(user, pass);
    }

}
