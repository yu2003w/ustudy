package com.ustudy.exam.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.MarkProgService;
import com.ustudy.exam.utility.ExamUtil;

@RestController
@RequestMapping(value = "/exam")
public class MarkProgressController {

	private static final Logger logger = LogManager.getLogger(MarkProgressController.class);
	
	@Autowired
	private MarkProgService mpS;
	
	@RequestMapping(value = "/mark/progress/",  method = RequestMethod.GET)
	public UResp getMarkProgress(HttpServletResponse resp) {
		logger.debug("getMarkProgress(), end point /mark/progress/ visited");
		
		UResp res = new UResp();
		
		// retrieve school id of current login user
		String sid = ExamUtil.retrieveSessAttr("orgId");
		if (sid == null || sid.isEmpty()) {
			logger.error("getMarkProgress(), failed to retrieve school id for logined user.");
			res.setMessage("Probably user not log in");

			return res;	
		}
		
		try {
			res.setData(mpS.getExamMarkProg(sid));
			res.setRet(true);
			
		} catch (Exception e) {
			logger.error("getMarkProgress(), failed with exception->" + e.getMessage());
			resp.setStatus(500);
			res.setMessage("Failed to retrieve mark progress with->" + e.getMessage());
		}
		
		return res;
	}
	
	@RequestMapping(value = "/teacher/mark/progress/{egsId}",  method = RequestMethod.GET)
    public UResp getTeaMarkProgress(@PathVariable Integer egsId, HttpServletResponse resp) {
        logger.debug("getTeacherMarkProgress(), end point /teacher/mark/progress/"+egsId+" visited");
        
        UResp res = new UResp();
        
        // retrieve school id of current login user
        String orgId = ExamUtil.retrieveSessAttr("orgId");
        if (orgId == null || orgId.isEmpty()) {
            logger.error("getTeacherMarkProgress(), failed to retrieve school id for logined user.");
            res.setMessage("Probably user not log in");
            return res; 
        }
        
        try {
            res.setData(mpS.getTeaMarkProg(orgId, egsId));
            res.setRet(true);            
        } catch (Exception e) {
            logger.error("getTeacherMarkProgress(), failed with exception->" + e.getMessage());
            resp.setStatus(500);
            res.setMessage("Failed to retrieve mark progress with->" + e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
            
        }
        
        return res;
    }
	
	@RequestMapping(value = "/markstart/{egsid}", method = RequestMethod.POST)
	public UResp startMark(@PathVariable("egsid") int egsid, HttpServletResponse resp) {
		UResp res = new UResp();
		
		return res;
	}
	
	@RequestMapping(value = "/markpause/{egsid}", method = RequestMethod.POST)
	public UResp pauseMark(@PathVariable("egsid") int egsid, HttpServletResponse resp) {
		UResp res = new UResp();
		
		return res;
	}
	
}
