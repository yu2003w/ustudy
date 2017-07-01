package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;

@RestController
@RequestMapping(value="/user")
public class AccountController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private AccountService accS;
	
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public List<Account> getList(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /user/list/" + id + " is visited");
		List<Account> res = null;
		try {
			res = accS.getList(id);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			String msg = "Failed to retrieve item list from " + id;
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("Failed to set error status in response");
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public Account displayItem(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /user/view/" + id + " is visited.");
		Account item = null;
		String msg = null;
		try {
			item = accS.findUserById(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			logger.warn("displayItem(), " + ie.getLocalizedMessage());
			msg = "No items found for specified id = " + id;
		} catch (Exception e) {
			logger.warn("displayItem(), " + e.getMessage());
			msg = "Failed to retrieve item " + id;
		}
		
		if (item == null) {
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("displayItem(), Failed to set error status in response");
			}
		}
				
		return item;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /user/delete/" + id + " is visited.");
		String result = null;
		try {
			int numOfRows = accS.delItem(id);
			if (numOfRows != 1)
				throw new RuntimeException("Number of affected rows is " + numOfRows);
			else
				result = "item deleted";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "delete item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
	/**
	 * endpoint for batch delete operation
	 * @param data
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delSet(@RequestBody @Valid String data, HttpServletResponse resp) {
		logger.debug("endpoint /user/delete is visited.");
		String result = null;
		try {
			int num = accS.delItemSet(data);
			if (num < 1)
				throw new RuntimeException("Number of deleted items is " + num);
			else {
				result = num + " items deleted";
			}
				
		} catch (Exception e) {
			result = "delete items failed";
			logger.warn("delSet()," + result + " --> " + e.getMessage());
			resp.setStatus(500);
		}
		return result;
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String createItem(@RequestBody @Valid Account item, HttpServletResponse resp, UriComponentsBuilder builder) {
		logger.debug("endpoint /user/add is visited.");
		logger.debug(item.toString());
		String result = null;
		try {
		    int index = accS.createItem(item);
		    logger.debug("createItem(), item created successfully with id " + index);
		    //set header location
		    resp.setHeader("Location", 
		    	builder.path("/user/view/{index}").buildAndExpand(index).toString());
		    result = "create item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "create item failed";
			resp.setStatus(500);
		}
		
		return result;
	}
	
	@RequestMapping(value="/update/{id}", method = RequestMethod.POST)
	public String updateItem(@RequestBody @Valid Account data, @PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /user/update/" + id + " is visited.");
		String result = null;
		try {
			int numOfRows = accS.updateItem(data, id);
			if (numOfRows == 1)
				logger.debug("updateItem(), update item successfully");
			else {
				String msg = numOfRows + " items are updated, maybe something goes wrong";
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			result = "update item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "update item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
	/*
		
	@RequestMapping(value = "/detailByName/{username}", method = RequestMethod.GET)
	public Account detailByName(@PathVariable("username")String username,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserByLoginName(username);
	}
	
	@RequestMapping(value = "/addRole/{id}/{roleId}", method = RequestMethod.GET)
	public boolean addRole(@PathVariable("id")int id,@PathVariable("roleId")int roleId,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
	@RequestMapping(value = "/deleteRole/{id}/{roleIds}", method = RequestMethod.GET)
	public boolean deleteRole(@PathVariable("id")int id,@PathVariable("roleIds")String roleIds,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	*/
	
}
