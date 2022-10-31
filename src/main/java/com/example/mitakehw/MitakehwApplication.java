package com.example.mitakehw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MitakehwApplication {

    public static void main(String[] args) {
        SpringApplication.run(MitakehwApplication.class, args);
    }

}
