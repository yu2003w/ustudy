package com.ustudy.infocenter;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.ustudy.infocenter.util.InfoUtil;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		InfoUtil.initRoleMapping();
		return application.sources(InfoCenterApp.class);
	}

}
