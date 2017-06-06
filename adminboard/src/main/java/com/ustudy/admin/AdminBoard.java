package com.ustudy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AdminBoard {

	public static void main(String[] args) throws Exception {
         SpringApplication.run(AdminBoard.class, args);
    }
	
}
