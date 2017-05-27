package com.ustudy.infocent;
/**
 * Initiated by Jared on May 9, 2017.
 * 
 */

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ustudy.datawrapper.ListAssemble;
import com.ustudy.datawrapper.InterStatement;
import com.ustudy.datawrapper.InterStatement.ItemType;
import com.ustudy.datawrapper.ItemManager;
import com.ustudy.datawrapper.OpResult;
import com.ustudy.datawrapper.OpResult.OpStatus;

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
    @Path("list/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@PathParam("type") String type, @PathParam("id") int id) {
    	logger.trace("List information for " + type);
    	ItemType it = supportedType(type);
    	if (it != ItemType.UNSUPPORTED && (ds != null)) {
    		OpResult res = ListAssemble.assemble(ds, it, id);
    		return Response.status(res.getStatus().getVal()).entity(res.getData()).build();
    	}
    
    	if (ds == null) {
        	return Response.status(OpStatus.Op_InterServerError.getVal()).
        		entity(InterStatement.ResultDBError).build();
        }
        else
        	return Response.status(OpStatus.OP_BadRequest.getVal()).
    			entity(InterStatement.ResultTypeNotSupported).build();
    }
    
    @GET
    @Path("item/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("type") String type, @PathParam("id") String id) {
    	logger.debug("Get item of " + type + " with id " + id);
    	ItemType it = supportedType(type);
    	if (it != ItemType.UNSUPPORTED && (ds != null)) {
    		OpResult res = ItemManager.getItem(ds, it, id);
    		return Response.status(res.getStatus().getVal()).
    			entity(res.getData()).build();
    	}
        if (ds == null) {
        	return Response.status(OpStatus.Op_InterServerError.getVal()).
        		entity(InterStatement.ResultDBError).build();
        }
        else
        	return Response.status(OpStatus.OP_BadRequest.getVal()).
        		entity(InterStatement.ResultTypeNotSupported).build();

    }
    
    @DELETE @Path("delete/{type}/{id}")
    public Response deleteItem(@PathParam("type") String type, @PathParam("id") String id) {
    	logger.debug("Item of " + type + " "+ id + " is to be deleted.");
    	ItemType it = supportedType(type);
    	if (it != ItemType.UNSUPPORTED && (ds != null)) {
    		OpResult res = ItemManager.deleteItem(ds, it, id);
    		return Response.status(res.getStatus().getVal()).entity(res.getData()).build();
    	}
    	
    	if (ds == null) {
        	return Response.status(OpStatus.Op_InterServerError.getVal()).
        		entity(InterStatement.ResultDBError).build();
        }
        else
        	return Response.status(OpStatus.OP_BadRequest.getVal()).
    			entity(InterStatement.ResultTypeNotSupported).build();
    }
    
    /**
     * 
     * @param type indicate which item should be created
     * @param data json string contains the information for constructing the item
     * @return 
     */
    @POST
    @Path("add/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(@PathParam("type") String type, final String data) {
    	logger.info("Create item for " + type + ":", data);
    	ItemType it = supportedType(type);
    	if (it != ItemType.UNSUPPORTED && ds != null) {
    		OpResult ret = ItemManager.buildItem(ds, it, data);
    		int id = ret.getKey();
        	if (id > 0) {
        		String loc = "item/" + type + "/" + String.valueOf(id);
        		return Response.status(OpStatus.OP_Created.getVal()).header("Location", loc).
        			entity(InterStatement.ResultItemCreated).build();
        	}
        	else
        		return Response.status(OpStatus.OP_BadRequest.getVal()).
        			entity(InterStatement.ResultDuplicated).build();
    	}
    	
    	if (ds == null) {
        	return Response.status(OpStatus.Op_InterServerError.getVal()).
        		entity(InterStatement.ResultDBError).build();
        }
        else
    		return Response.status(OpStatus.OP_BadRequest.getVal()).
    			entity(InterStatement.ResultTypeNotSupported).build();
    	
    }
    
    @POST
    @Path("update/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("type") String type,
    		@PathParam("id") String id, final String data) {
    	
    	logger.info("Update item for " + type + "-->" + data);
    	ItemType it = supportedType(type);
    	if (it != ItemType.UNSUPPORTED && ds != null) {
    		OpResult res = ItemManager.updateItem(ds, it, id, data);
    		return Response.status(res.getStatus().getVal()).entity(res.getData()).build();
    	}
    	
    	if (ds == null) {
        	return Response.status(OpStatus.Op_InterServerError.getVal()).
        		entity(InterStatement.ResultDBError).build();
        }
        else
        	return Response.status(OpStatus.OP_BadRequest.getVal()).
    			entity(InterStatement.ResultTypeNotSupported).build(); 
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
			logger.warn(e.getMessage());
			logger.fatal("Failed to get data source from configured information");
		}
    }
    
    private ItemType supportedType(final String type) {
    	if (type.compareTo(ItemType.STUDENT.getVal()) == 0)
    		return ItemType.STUDENT;
    	else if (type.compareTo(ItemType.TEACHER.getVal()) == 0)
    		return ItemType.TEACHER;
    	else if (type.compareTo(ItemType.EXAM.getVal()) == 0)
    		return ItemType.EXAM;
    	else if (type.compareTo(ItemType.SCHOOL.getVal()) == 0)
    		return ItemType.SCHOOL;
    	else
    		return ItemType.UNSUPPORTED;
    }
}
