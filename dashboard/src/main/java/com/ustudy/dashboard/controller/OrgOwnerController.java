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

import com.ustudy.dashboard.model.OrgOwner;
import com.ustudy.dashboard.services.OrgOwnerService;

@RestController
@RequestMapping("/dashboard/owner/")
public class OrgOwnerController {

	private static final Logger logger = LogManager.getLogger(OrgOwnerController.class);
	
	@Autowired
	private OrgOwnerService ooS;
	
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public List<OrgOwner> getList(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /owner/list/" + id + " is visited");
		List<OrgOwner> res = null;
		try {
			res = ooS.getList(id);
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
	public OrgOwner displayItem(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /owner/view/" + id + " is visited.");
		OrgOwner item = null;
		String msg = null;
		try {
			item = ooS.displayItem(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			logger.warn("displayItem(), " + ie.getLocalizedMessage());
			msg = "No item found for specified id " + id;
		} catch (Exception e) {
			logger.warn("displayItem(), " + e.getMessage());
			msg = "Failed to retrieve item " + id;
		}
		
		if (item == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
				
		return item;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /owner/delete/" + id + " is visited.");
		String result = null;
		try {
			int numOfRows = ooS.deleteItem(id);
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
		logger.debug("endpoint /owner/delete is visited.");
		String result = null;
		try {
			int num = ooS.delItemSet(data);
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
	public String createItem(@RequestBody @Valid OrgOwner item, HttpServletResponse resp, UriComponentsBuilder builder) {
		logger.debug("endpoint /owner/add is visited.");
		logger.debug(item.toString());
		String result = null;
		try {
			// Before create item, need to check whether corresponding orgId is valid.
		    int index = ooS.createItem(item);
		    logger.debug("createItem(), item created successfully with id " + index);
		    //set header location
		    resp.setHeader("Location", 
		    	builder.path("/owner/view/{index}").buildAndExpand(index).toString());
		    result = "create item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "create item failed";
			resp.setStatus(500);
		}
		
		return result;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String updateItem(@RequestBody @Valid OrgOwner data, HttpServletResponse resp) {
		logger.debug("endpoint /owner/update" + data.getId() + " is visited.");
		String result = null;
		try {
			// TODO: if orgid is changed, whether or not need to validate it.
			int numOfRows = ooS.updateItem(data, Integer.parseInt(data.getId()));
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
}
