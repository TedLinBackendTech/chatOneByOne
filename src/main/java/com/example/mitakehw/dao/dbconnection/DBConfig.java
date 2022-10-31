package com.example.mitakehw.dao.dbconnection;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.activation.DataSource;

//@ConfigurationProperties(prefix = "spring.datasource")//application.properties設定的資料庫連線前綴
//public class DBConfig  {
//    @Primary
//    @Bean(name = "customerDataSource") //(1)自訂義定義 DataSource 名稱
//
//    public DataSource customerDataSource() {
//        return (DataSource) DataSourceBuilder.create().build();
//    }
//
//}
