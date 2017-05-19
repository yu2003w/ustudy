package com.ustudy.infocent;
/**
 * Initiated by Jared on May 9, 2017.
 * 
 */

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ustudy.datawrapper.InfoList;
import com.ustudy.datawrapper.InterStatement;
import com.ustudy.datawrapper.ItemBuilder;

/**
 * Root resource (exposed at root "/" path)
 */
@Path("/")
public class InfoCenter {

	private static final Logger logger = LogManager.getLogger(InfoCenter.class);
	
	private DataSource ds = null;
	
	public InfoCenter() {
		initDataSource();
	}
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     * This method is handle request from "/"
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "This is simple Jersey servelet program.\nFirst page to be displayed";
    }
     
    /**
     * 
     * @param type indicate the type of information will be retrieved.
     *        for example, teachers, students, examinations, schools
     * @return String that will be returned as application/json response.
     *         list of information related with parameter "type"
     */
    @GET
    @Path("list/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getList(@PathParam("type") String type) {
    	logger.trace("List information for " + type);
    	if (isSupportedType(type) && (ds != null))
    		return InfoList.getList(ds, type);
    	
    	return "{\"result\":\"No Data\"}";
    }
    
    @GET
    @Path("item/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItem(@PathParam("type") String type, @PathParam("id") int id) {
    	logger.debug("Get item of " + type + " with id " + id);
    	if (isSupportedType(type) && (ds != null))
    		return ItemBuilder.getItem(ds, type, id);
    	return null;
    }
    
    /**
     * 
     * @param type indicate which item should be created
     * @param data json string contains the information for constructing the item
     * @return 
     */
    @POST
    @Path("item/add/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItem(@PathParam("type") String type, final String data) {
    	logger.info("data received for " + type + ":", data);
    	if (!isSupportedType(type))
    		return "{\"result\":\"Not supported\"}";
    	if (ItemBuilder.buildItem(ds, type, data)) {
    		return "{\"result\":\"ok\"}";
    	}
    	else
    		return "{\"result\":\"false\"}";
    }
    
    /**
     * Init DataSource when the application is started up
     */
    private void initDataSource() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds = (DataSource)envCtx.lookup(InterStatement.INFO_CENTER_DS);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private boolean isSupportedType(final String type) {
    	if (type.compareTo(InterStatement.STU_TYPE) == 0 || type.compareTo("exam") == 0 ||
    		type.compareTo("teach") == 0 || type.compareTo("sch") == 0)
    		return true;
    	
    	return false;
    }
}
