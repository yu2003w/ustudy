package com.ustudy.dashboard;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.ustudy.dashboard.util.DashboardUtil;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		DashboardUtil.initRoleMapping();
		return application.sources(Dashboard.class);
	}

}
