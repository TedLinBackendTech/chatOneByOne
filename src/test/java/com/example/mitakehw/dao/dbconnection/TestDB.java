package com.example.mitakehw.dao.dbconnection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import org.junit.Assert;
public class TestDB {
    @Test
    public void DatabaseConnectionTest(){
        DBConnectionImpl db = new DBConnectionImpl();
        Connection connection = db.getConnection();
        Assert.assertNotNull(connection);

    }
}
