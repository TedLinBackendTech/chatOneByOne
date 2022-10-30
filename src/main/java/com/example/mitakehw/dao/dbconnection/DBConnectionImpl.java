package com.example.mitakehw.dao.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionImpl implements DBConnection{
    private DBproperties properties = new DBproperties();
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mitake?user=test&password=test123&useSSL=false&serverTimezone=CST&createDatabaseIfNotExist=true";
    @Override
    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return conn;
    }
}
