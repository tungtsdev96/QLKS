/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Tuyen Ti Ton
 */
public class SendMail {

    //Hostname and port connection
    String hostName = "smtp.gmail.com";
    int port = 465;
    //Input and Output Stream
    DataInputStream input;
    DataOutputStream output;
    //SSL Socket connect to server
    SSLSocketFactory sslSocketFactory;
    SSLSocket sslSocket;

    
    //Constructor a connection to server
    //Throw Exception if the code is not same the code is expect
    public SendMail() throws Exception {
        //sslSocket ????
        sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            //Create a Socket to hostName with port
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(hostName, port);
            //Input : read from hostName
            input = new DataInputStream(sslSocket.getInputStream());
            //Output: write to hostName
            output = new DataOutputStream(sslSocket.getOutputStream());
            //Display the result from server and check the code
            String serverReply = this.read();
            String codeReply = serverReply.substring(0, 3);
            //If not same the code throw an exception
            if (!check(codeReply, "220")) {
                throw new Exception();
            } else {
                System.out.println(serverReply);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Read from server in 2 seconds and unit is a Line
    private String read() {
        String serverReply = "";
        while (true) {
            //Call the method readLine in class util estimate have 1 bytes can read
            serverReply = serverReply + readLine();
            try {
                if (input.available() <= 1) {
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return serverReply;
    }

    //Read from server a line
    private String readLine() {
        int ch;
        String serverReply = "";
        try {
            //Read util the charater have ASCII code is 10
            do {
                ch = input.read();
                serverReply = serverReply + (char) ch;
            } while (ch != 10);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return serverReply;
    }

    //Write to server
    private void write(String toServer) {
        try {
            output.writeBytes(toServer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean check(String code, String expect) {
        if (code.equals(expect)) {
            return true;
        } else {
            return false;
        }
    }

    //Hello from client to the server. Introduce to server about client
    public boolean ehlo() {
        this.write("ehlo google\r\n");
        //The code is for the purpose is test
        System.out.println("ehlo google");
        String serverReply = this.read();
        String codeReply = serverReply.substring(0, 3);
        //Display the response from server
        System.out.println(serverReply);
        //return true if the code is expected
        return this.check(codeReply, "250");
    }

    public boolean authLogin() {
        //Write to server
        this.write("AUTH LOGIN\r\n");
        //Display response from server
        System.out.println("AUTH LOGIN");
        String serverReply = this.read();
        String codeReply = serverReply.substring(0, 3);
        System.out.println(serverReply);
        //Return check
        return check(codeReply, "334");
    }

    public boolean username(String username) {
        //Switch from String to byte[] for use encode method
        byte[] usernameInByte = new byte[username.length()];
        for (int i = 0; i < username.length(); i++) {
            usernameInByte[i] = (byte) username.charAt(i);
        }
        //Encode Base 64 for username
        String usernameEncode = Base64.encode(usernameInByte);
        //Write to the server the username is encoded in Base64
        this.write(usernameEncode + "\r\n");
        System.out.println(username);
        //Response from server
        String serverReply = this.read();
        System.out.println(serverReply);
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "334");
    }

    public boolean password(String password) {
        //Switch to byte[] form
        byte[] passwordInByte = new byte[password.length()];
        for (int i = 0; i < password.length(); i++) {
            passwordInByte[i] = (byte) password.charAt(i);
        }
        String passwordEncode = Base64.encode(passwordInByte);
        //Write the password in Base64 encode to server
        this.write(passwordEncode + "\r\n");
        System.out.println("**********");
        //Server Respond
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code from server
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "235");
    }

    //Inform send identification to the server
    public boolean mailFrom(String mailID) {
        //Write to the server
        this.write("MAIL FROM:<" + mailID + ">\r\n");
        System.out.println("MAIL FROM:<" + mailID + ">");
        //Respond from server
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "250");
    }

    //Inform receive identification to the server
    public boolean rcptTo(String mailID) {
        //Write to the server
        this.write("RCPT TO:<" + mailID + ">\r\n");
        System.out.println("RCPT TO:<" + mailID + ">");
        //Respond from server
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "250");
    }

    public boolean data() {
        //Write to the server
        this.write("DATA\r\n");
        System.out.println("DATA");
        //Respond from server
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "354");
    }

    //Write the Header in the mail after DATA command
    public boolean header(String mailFrom, String mailTo, String subject) {
        System.out.println("This is Header:");
        this.write("From: " + mailFrom + "\r\n");
        System.out.println("From: " + mailFrom);
        this.write("To: " + mailTo + "\r\n");
        System.out.println("To: " + mailTo);
        this.write("Subject: " + subject + "\r\n");
        System.out.println("Subject: " + subject);
        return true;
    }

    //Write the boby in mail
    public boolean body(String message) {
        //Write the body to server and send CRLF
        this.write(message + "\r\n");
        //Write <CRLF>.<CRLF> to inform end the message
        this.write("\r\n.\r\n");
        System.out.println("\nSend the body to the server:\n" + message);
        //Respond from server
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "250");
    }

    //COMMAND QUIT
    public boolean quit() {
        //Write command QUIT to the server
        this.write("QUIT\r\n");
        System.out.println("QUIT");
        //RESPONSE from server
        String serverReply = this.read();
        System.out.println(serverReply);
        //Check the code from server
        String codeReply = serverReply.substring(0, 3);
        return this.check(codeReply, "221");
    }

    public boolean close() {
        try {
            input.close();
            output.close();
            sslSocket.close();
            sslSocket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error close the connection");
            return false;
        }
        return true;
    }

    public boolean run(String username, String password, String mailTo, String subject, String bodyMail) {
        try {
            if (!this.ehlo()) {
                throw new Exception();
            } else if (!this.authLogin()) {
                throw new Exception();
            } else if (!this.username(username)) {
                throw new Exception();
            } else if (!this.password(password)) {
                throw new Exception();
            } else if (!this.mailFrom(username)) {
                throw new Exception();
            } else if (!this.rcptTo(mailTo)) {
                throw new Exception();
            } else if (!this.data()) {
                throw new Exception();
            } else if (!this.header(username, mailTo, subject)) {
                throw new Exception();
            } else if (!this.body(bodyMail)) {
                throw new Exception();
            } else if (!this.quit()) {
                throw new Exception();
            } else if (!this.close()) {
                throw new Exception();
            } else {
                System.out.println("OK");
            }
            
        } catch (Exception ex) {
            System.out.println("The code is returned that do not same as the code is expected");
            return false;
        }
        return true;
    }
}
