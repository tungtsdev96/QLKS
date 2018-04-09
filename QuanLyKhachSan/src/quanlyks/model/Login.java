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
public class Login {
    private String user;
    private String pass;
    private String login_type;

    public Login(){}
    
    public Login(String user, String pass, String login_type) {
        this.user = user;
        this.pass = pass;
        this.login_type = login_type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }
    
}
