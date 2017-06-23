package com.ustudy.dashboard.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	/**
	 * login
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request) {
		String result = "login.jsp";
		// 取得用户名
		String username = request.getParameter("username");
		//取得 密码，并用MD5加密
		String password = request.getParameter("password");
		//String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();
		try {
			if (!currentUser.isAuthenticated()){//使用shiro来验证
				token.setRememberMe(true);
				currentUser.login(token);//验证角色和权限
			}
			result = "index";//验证成功
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			result = "login.jsp";//验证失败
		}
		LOGGER.info("result: " + result);
		return result;
	}
  
    /**
     * logout
     * @return
     */
    @RequestMapping(value = "/logout")  
    @ResponseBody  
    public void logout(HttpServletRequest request,HttpServletResponse response) {  
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "index";  
        currentUser.logout();  
        
        try {
			response.sendRedirect(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
}
