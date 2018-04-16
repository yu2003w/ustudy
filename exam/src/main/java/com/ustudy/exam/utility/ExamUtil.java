package com.ustudy.exam.utility;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ExamUtil {

	private final static Logger logger = LogManager.getLogger(ExamUtil.class);
	
	/*
	 * Calling this method should catch exceptions throws by shiro
	 */
	public static String retrieveSessAttr(String key) {
		Session ses = SecurityUtils.getSubject().getSession();
		Object obj = ses.getAttribute(key);
		if (obj != null)
			return obj.toString();
		else
			return null;
	}
	
	public static String getCurrentUserId() {
		Subject cUser = null;
		String uid = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("getCurrentUserId(),Failed to get subject --> " + e.getMessage());
			return uid;
		}
		if (cUser.getPrincipal() == null) {
			logger.warn("getCurrentUserId(), User didn't log in");
		} else {
			uid = cUser.getPrincipal().toString();
		}
		
		return uid;
	}
	
	private static ConcurrentHashMap<String, String> rolemapping = 
			new ConcurrentHashMap<String, String>();

	public static ConcurrentHashMap<String, String> getRolemapping() {
		if(null == rolemapping || rolemapping.size()==0){
			initRoleMapping();
		}
		return rolemapping;
	}

	// this method is for role mapping between frontend input and backend logic
	public static void initRoleMapping() {
		rolemapping.put("任课老师", "teacher");
		rolemapping.put("班主任", "cteacher");
		rolemapping.put("备课组长", "pleader");
		rolemapping.put("学科组长", "sleader");
		rolemapping.put("年级主任", "gleader");
		rolemapping.put("校长", "org_owner");
		rolemapping.put("考务老师", "leader");
		rolemapping.put("清道夫", "cleaner");
		
		rolemapping.put("teacher", "任课老师");
		rolemapping.put("cteacher", "班主任");
		rolemapping.put("pleader", "备课组长");
		rolemapping.put("sleader", "学科组长");
		rolemapping.put("gleader", "年级主任");
		rolemapping.put("org_owner", "校长");
		rolemapping.put("leader", "考务老师");
		rolemapping.put("cleaner", "清道夫");
	}
	
	/*public static int getStudentCorrectCount(String stuAnswer, String correctAnswer){
        
        int studentCorrectCount = 0;
        
        if (null != stuAnswer && stuAnswer.length()>0 && null != correctAnswer && correctAnswer.length()>0) {
            if (stuAnswer.indexOf(",") >= 0) {
                stuAnswer = stuAnswer.replaceAll(",", "");
            }
            
            if(stuAnswer.length()>0){
                stuAnswer = stuAnswer.toUpperCase();
                correctAnswer = correctAnswer.toUpperCase();
                
                Set<String> stuAnswers = new HashSet<>();
                
                byte[] bytes = stuAnswer.getBytes();
                for (int i=0; i<bytes.length; i++) {
                    stuAnswers.add(new String(bytes, i, 1));
                }
                
                for (String answer : stuAnswers) {
                    if(correctAnswer.contains(answer)){
                        studentCorrectCount++;
                    }else {
                        studentCorrectCount = 0;
                        break;
                    }
                }
            }
        }
        
        return studentCorrectCount;
        
    }*/
	
}
