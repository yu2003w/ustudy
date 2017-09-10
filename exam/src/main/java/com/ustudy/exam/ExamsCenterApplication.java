package com.ustudy.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath*:applicationContext.xml"})
public class ExamsCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamsCenterApplication.class, args);
	}
}
