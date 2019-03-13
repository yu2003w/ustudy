package com.ustudy.exam.clientservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustudy.exam.model.template.PaperTemplate;
import com.ustudy.exam.service.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTests {

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ClientService cliSrv;
	
	@Test
	public void testParsePaperTemplate() throws Exception {
		ObjectMapper objM = new ObjectMapper();
		objM.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objM.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		PaperTemplate paperT = objM.readValue(resourceLoader.getResource("classpath:papermixed.json").getInputStream(), PaperTemplate.class);
		cliSrv.saveTemplates((long)241, paperT);
	}
}
