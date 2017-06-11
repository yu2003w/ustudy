package com.ustudy.dashboard.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class Dashboard {

	public static void main(String[] args) throws Exception {
         SpringApplication.run(Dashboard.class, args);
    }
	
}
