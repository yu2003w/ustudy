package com.ustudy.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;


/**
 * @author jared
 * 
 * Tips: For Spring boot applications, main package application should adhere to that in web.xml.
 * For example, following stuff in web.xml.
 *  <groupId>com.ustudy</groupId>
 *  <artifactId>dashboard</artifactId>
 *	<version>0.0.1</version>
 *	<packaging>war</packaging>
 *
 *	<name>dashboard</name>
 *	<description>Dashboard for ustudy administration</description>
 * 
 *  The main package should be com.ustudy.dashboard and other packages such as controller, model, 
 *  services and so on should be sub-packages of the main package.
 */


@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class Dashboard {

	public static void main(String[] args) throws Exception {
         SpringApplication.run(Dashboard.class, args);
    }
	
}
