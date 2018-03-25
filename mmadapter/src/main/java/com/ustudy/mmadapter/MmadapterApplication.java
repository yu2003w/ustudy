package com.ustudy.mmadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath*:applicationContext.xml"})
public class MmadapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmadapterApplication.class, args);
	}
}
