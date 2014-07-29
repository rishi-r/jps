package models;

import db.*;
import java.sql.*;
import db.Connect;

public class LoginModel {

    private Connection conn;
    private Connect c;
    PreparedStatement stmt;
    Statement cstmt;
    ResultSet rs;

    public void LoginModel() {
        c = new Connect();
    }

    public ResultSet login(String name, String pass) {
        c.connect();
        try {
            rs = null;
            PreparedStatement ps = conn.prepareStatement("select * from userlogin where userid=? and user_password=?");
            ps.setString(1, name);
            ps.setString(2, pass);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        c.close();
        return rs;
    }

    
}//end of Connection

