package com.ustudy.dashboard.services.imp;

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

import com.ustudy.dashboard.model.Role;
import com.ustudy.dashboard.services.RoleService;

@Service
public class RoleServiceImp implements RoleService{

	private static final Log logger = LogFactory.getLog(RoleServiceImp.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<Role> list(Role role) {
		List<Role> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from sec_role");
		if(null != role){
			sql.append(" where 1=1");
			if(null != role.getName()){
				sql.append(" and name='" + role.getName() + "'");
			}
			if(null != role.getType()){
				sql.append(" and type='" + role.getType() + "'");
			}
		}
		try {
			logger.info(sql.toString());
			list = jdbcT.query(sql.toString(), new RowMapper<Role>(){	
				@Override
				public Role mapRow(ResultSet rs, int num) throws SQLException{
					Role r = new Role(rs);
					return r;
				}
		    });
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return list;
	}
	
	public Role findRoleByName(String name) {
		Role role = null;
		if(null != name){
			String sql = "SELECT * from sec_role where name='" + name + "';";
			try {
				logger.info(sql);
				List<Role> roles = jdbcT.query(sql, new RowMapper<Role>(){	
					@Override
					public Role mapRow(ResultSet rs, int num) throws SQLException{					
						Role role = new Role(rs);
						return role;
					}
				});
				if(null != roles && roles.size()>0){
					role = roles.get(0);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return role;
	}
	
	public Role findRoleById(int id) {
		Role role = null;
		String sql = "SELECT * from sec_role where id=" + id + ";";
		try {
			logger.info(sql);
			List<Role> roles = jdbcT.query(sql, new RowMapper<Role>(){	
				@Override
				public Role mapRow(ResultSet rs, int num) throws SQLException{					
					Role role = new Role(rs);
					return role;
				}
		    });
			if(null != roles && roles.size()>0){
				role = roles.get(0);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return role;
	}
	
	public boolean deleteRoleById(int id) {
		String r_sql = "DELETE from sec_role where id=" + id ;
		String ur_sql = "DELETE from sec_user_role where rid=" + id ;
		String pr_sql = "DELETE from sec_role_permission where rid=" + id ;
		try {
			logger.info(r_sql);
			jdbcT.execute(r_sql);
			logger.info(ur_sql);
			jdbcT.execute(ur_sql);
			logger.info(pr_sql);
			jdbcT.execute(pr_sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean deleteRoleByIds(String ids) {
		String r_sql = "DELETE from sec_role where id in (" + ids + ")";
		String ur_sql = "DELETE from sec_user_role where rid in (" + ids + ")";
		String pr_sql = "DELETE from sec_role_permission where rid in (" + ids + ")";
		try {
			logger.info(r_sql);
			jdbcT.execute(r_sql);
			logger.info(ur_sql);
			jdbcT.execute(ur_sql);
			logger.info(pr_sql);
			jdbcT.execute(pr_sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean insertRole(Role role) {
		
		if(null != role && null != role.getName()){
		}
		StringBuffer sql = new StringBuffer("insert into sec_role (");
		
		if(null != role.getType()){
			sql.append("type,");
		}
		if(null != role.getName()){
			sql.append("name");
		}
		
		sql.append(") values(");
		
		if(null != role.getType()){
			sql.append("'"+role.getType()+"',");
		}
		if(null != role.getName()){
			sql.append("'"+role.getName()+"'");
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
	
	public boolean updateRole(Role role) {
		if(null != role && role.getId()>0 && (null != role.getName() || null != role.getType())){
			StringBuffer sql = new StringBuffer("update sec_role set ");
			
			boolean flag = false;
			
			if(null != role.getName()){
				sql.append("name='" + role.getName() + "'");
				flag = true;
			}
			if(null != role.getType()){
				if(flag) sql.append(",");
				sql.append("type='" + role.getType() + "'");
			}
			
			sql.append(" where id=" + role.getId());
			
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
	
	public List<Role> getRoleByUserId(int userId) {
		List<Role> list = new ArrayList<>();
		if(userId > 0){
			StringBuffer sql = new StringBuffer("SELECT r.* from sec_user_role ur LEFT JOIN sec_role r ON ur.rid=r.id where ur.uid=" + userId);
			try {
				logger.info(sql.toString());
				list = jdbcT.query(sql.toString(), new RowMapper<Role>(){	
					@Override
					public Role mapRow(ResultSet rs, int num) throws SQLException{
						Role p = new Role(rs);
						return p;
					}
				});
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return list;
	}
	
	public List<Role> getRoleByUserName(String userName) {
		List<Role> list = new ArrayList<>();
		if(null != userName){
			StringBuffer sql = new StringBuffer("SELECT r.* from sec_user_role ur LEFT JOIN sec_role r ON ur.rid=r.id  LEFT JOIN sec_users u on ur.uid=u.id where u.name='"+userName+"'");
			try {
				logger.info(sql.toString());
				list = jdbcT.query(sql.toString(), new RowMapper<Role>(){	
					@Override
					public Role mapRow(ResultSet rs, int num) throws SQLException{
						Role p = new Role(rs);
						return p;
					}
				});
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return list;
	}
	
	public boolean addPermissions(int roleId,String permissionId) {
		if(roleId > 0 && null != permissionId){
			StringBuffer sql = new StringBuffer("insert into sec_role_permission (rid,pid) values");
			String[] permissionIds = permissionId.split(",");
			for (String id : permissionIds) {
				sql.append("("+roleId+","+id+"),");
			}
			try {
				logger.info(sql.toString());
				jdbcT.update(sql.substring(0,sql.length()-1));
				return true;
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		
		return false;
	}
	
	public boolean deletePermissions(int roleId,String permissionId) {
		if(roleId > 0 && null != permissionId){
			StringBuffer sql = new StringBuffer("delete from sec_role_permission where rid=" + roleId + " and pid in ("+permissionId+")");
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
	
}
