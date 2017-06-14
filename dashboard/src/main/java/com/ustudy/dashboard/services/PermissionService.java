package com.ustudy.dashboard.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.model.Permission;

@Service
public class PermissionService {

	private static final Log logger = LogFactory.getLog(PermissionService.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<Permission> list(Permission permission) {
		List<Permission> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from sec_permission");
		if(null != permission){
			sql.append(" where 1=1");
			if(null != permission.getName()){
				sql.append(" and name='" + permission.getName() + "'");
			}
			if(null != permission.getUrl()){
				sql.append(" and url='" + permission.getUrl() + "'");
			}
		}
		try {
			logger.info(sql.toString());
			list = jdbcT.query(sql.toString(), new RowMapper<Permission>(){	
				@Override
				public Permission mapRow(ResultSet rs, int num) throws SQLException{
					Permission p = new Permission(rs);
					return p;
				}
		    });
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return list;
	}
	
	public Permission findPermissionByName(String name) {
		Permission permission = null;
		if(null != name){
			String sql = "SELECT * from sec_permission where name='" + name + "';";
			try {
				logger.info(sql);
				List<Permission> permissions = jdbcT.query(sql, new RowMapper<Permission>(){	
					@Override
					public Permission mapRow(ResultSet rs, int num) throws SQLException{					
						Permission permission = new Permission(rs);
						return permission;
					}
				});
				if(null != permissions && permissions.size()>0){
					permission = permissions.get(0);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return permission;
	}
	
	public Permission findPermissionById(int id) {
		Permission permission = null;
		String sql = "SELECT * from sec_permission where id=" + id + ";";
		try {
			logger.info(sql);
			List<Permission> permissions = jdbcT.query(sql, new RowMapper<Permission>(){	
				@Override
				public Permission mapRow(ResultSet rs, int num) throws SQLException{					
					Permission permission = new Permission(rs);
					return permission;
				}
		    });
			if(null != permissions && permissions.size()>0){
				permission = permissions.get(0);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return permission;
	}
	
	public boolean deletePermissionById(int id) {
		String sql = "DELETE from sec_permission where id=" + id + ";";
		try {
			logger.info(sql);
			jdbcT.execute(sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean deletePermissionByIds(String ids) {
		String sql = "DELETE from sec_permission where id in (" + ids + ");";
		try {
			logger.info(sql);
			jdbcT.execute(sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean insertPermission(Permission permission) {
		
		if(null != permission && null != permission.getName()){
		}
		StringBuffer sql = new StringBuffer("insert into sec_permission (");
		
		if(null != permission.getUrl()){
			sql.append("url,");
		}
		if(null != permission.getName()){
			sql.append("name");
		}
		
		sql.append(") values(");
		
		if(null != permission.getUrl()){
			sql.append("'"+permission.getUrl()+"',");
		}
		if(null != permission.getName()){
			sql.append("'"+permission.getName()+"'");
		}
		
		sql.append(")");
		
		try {
			logger.info(sql.toString());
			jdbcT.update(sql.toString());
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean updatePermission(Permission permission) {
		if(null != permission && permission.getId()>0 && (null != permission.getName() || null != permission.getUrl())){
			StringBuffer sql = new StringBuffer("update sec_permission set ");
			
			boolean flag = false;
			
			if(null != permission.getName()){
				sql.append("name='" + permission.getName() + "'");
				flag = true;
			}
			if(null != permission.getUrl()){
				if(flag) sql.append(",");
				sql.append("url='" + permission.getUrl() + "'");
			}
			
			sql.append(" where id=" + permission.getId());
			
			try {
				logger.info(sql.toString());
				jdbcT.update(sql.toString());
				return true;
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		
		return false;
	}
	
	public List<Permission> getPermissionByUserId(int userId) {
		List<Permission> list = new ArrayList<>();
		if(userId > 0){
			StringBuffer sql = new StringBuffer("SELECT p.* from sec_user_role ur ");
			sql.append("LEFT JOIN sec_role r ON ur.rid=r.id ");
			sql.append("LEFT JOIN sec_role_permission rp on r.id=rp.rid ");
			sql.append("LEFT JOIN sec_permission p ON rp.pid=p.id ");
			sql.append("where ur.uid=" + userId);
			try {
				logger.info(sql.toString());
				list = jdbcT.query(sql.toString(), new RowMapper<Permission>(){	
					@Override
					public Permission mapRow(ResultSet rs, int num) throws SQLException{
						Permission p = new Permission(rs);
						return p;
					}
				});
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return list;
	}
	
	public List<Permission> getPermissionByUserName(String userName) {
		List<Permission> list = new ArrayList<>();
		if(null != userName){
			StringBuffer sql = new StringBuffer("SELECT p.* from sec_user_role ur ");
			sql.append("LEFT JOIN sec_role r ON ur.rid=r.id ");
			sql.append("LEFT JOIN sec_users u on ur.uid=u.id ");
			sql.append("LEFT JOIN sec_role_permission rp on r.id=rp.rid ");
			sql.append("LEFT JOIN sec_permission p ON rp.pid=p.id ");
			sql.append("where u.name='"+userName+"'");
			try {
				logger.info(sql.toString());
				list = jdbcT.query(sql.toString(), new RowMapper<Permission>(){	
					@Override
					public Permission mapRow(ResultSet rs, int num) throws SQLException{
						Permission p = new Permission(rs);
						return p;
					}
				});
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return list;
	}
	
}
