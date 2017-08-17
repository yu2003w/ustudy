package com.ustudy.infocenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath*:applicationContext.xml"})
public class InfoCenterApp {

	public static void main(String[] args) {
		SpringApplication.run(InfoCenterApp.class, args);
	}
}
