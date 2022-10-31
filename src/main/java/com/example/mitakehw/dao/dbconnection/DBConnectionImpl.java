package com.example.mitakehw.dao.dbconnection;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionImpl implements DBConnection{
    // TODO: 2022/11/1 revise it to read from datasource
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

