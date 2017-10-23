package com.ustudy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.ustudy.exam.utility.ExamUtil;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		ExamUtil.initRoleMapping();
		return application.sources(ExamsCenterApplication.class);
	}

}
