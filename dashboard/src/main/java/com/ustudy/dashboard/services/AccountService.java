package com.ustudy.dashboard.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.model.Account;

@Service
public class AccountService {

	private static final Log logger = LogFactory.getLog(AccountService.class);
	
	private SimpleDateFormat SDF = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<Account> list(Account account, String startTime, String endTime) {
		List<Account> acList = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from sec_users");
		if(null != account || null != startTime || null != endTime){
			sql.append(" where 1=1");
			if(null != account.getName()){
				sql.append(" and name='" + account.getName() + "'");
			}
			if(null != account.getLoginname()){
				sql.append(" and login_name='" + account.getLoginname() + "'");
			}
			if(null != account.getPswd()){
				sql.append(" and pswd='" + account.getPswd() + "'");
			}
			if(null != account.getPhone()){
				sql.append(" and phone='" + account.getPhone() + "'");
			}
			if(null != startTime){
				sql.append(" and create_time >='" + startTime + "'");
			}
			if(null != endTime){
				sql.append(" and create_time <='" + endTime + "'");
			}
			if(account.getStatus()>=0){
				sql.append(" and status=" + account.getStatus());
			}
		}
		try {
			logger.info(sql.toString());
			acList = jdbcT.query(sql.toString(), new RowMapper<Account>(){	
				@Override
				public Account mapRow(ResultSet rs, int num) throws SQLException{
					Account usr = new Account(rs);
					return usr;
				}
		    });
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return acList;
	}
	
	public Account findUserByLoginName(String username) {
		Account account = null;
		if(null != username){
			String sql = "SELECT * from sec_users where login_name='" + username + "';";
			try {
				logger.info(sql);
				List<Account> accounts = jdbcT.query(sql, new RowMapper<Account>(){	
					@Override
					public Account mapRow(ResultSet rs, int num) throws SQLException{					
						Account account = new Account(rs);
						return account;
					}
				});
				if(null != accounts && accounts.size()>0){
					account = accounts.get(0);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		return account;
	}
	
	public Account findUserById(int id) {
		Account account = null;
		String sql = "SELECT * from sec_users where id=" + id + ";";
		try {
			logger.info(sql);
			List<Account> accounts = jdbcT.query(sql, new RowMapper<Account>(){	
				@Override
				public Account mapRow(ResultSet rs, int num) throws SQLException{					
					Account account = new Account(rs);
					return account;
				}
		    });
			if(null != accounts && accounts.size()>0){
				account = accounts.get(0);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return account;
	}
	
	public boolean deleteUserById(int id) {
		String sql = "DELETE from sec_users where id=" + id;
		String ur_sql = "DELETE from sec_user_role where uid=" + id;
		try {
			logger.info(sql);
			jdbcT.execute(sql);
			logger.info(ur_sql);
			jdbcT.execute(ur_sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean deleteUserByIds(String ids) {
		String sql = "DELETE from sec_users where id in (" + ids + ")";
		String ur_sql = "DELETE from sec_user_role where uid in (" + ids + ")";
		try {
			logger.info(sql);
			jdbcT.execute(sql);
			logger.info(ur_sql);
			jdbcT.execute(ur_sql);
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	public boolean insertUser(Account account) {
		
		if(null != account && null != account.getLoginname() && null != account.getPswd()){
		}
		StringBuffer sql = new StringBuffer("insert into sec_users (");
		
		if(null != account.getName()){
			sql.append("name,");
		}
		if(null != account.getLoginname()){
			sql.append("login_name,");
		}
		if(null != account.getPswd()){
			sql.append("pswd,");
		}
		if(null != account.getPhone()){
			sql.append("phone,");
		}
		sql.append("status,");
		sql.append("create_time");
		
		sql.append(") values(");
		
		if(null != account.getName()){
			sql.append("'"+account.getName()+"',");
		}
		if(null != account.getLoginname()){
			sql.append("'"+account.getLoginname()+"',");
		}
		if(null != account.getPswd()){
			sql.append("'"+account.getPswd()+"',");
		}
		if(null != account.getPhone()){
			sql.append("'"+account.getPhone()+"',");
		}
		if(account.getStatus() == 0 || account.getStatus() == 1){
			sql.append(account.getStatus() + ",");
		}
		sql.append("'"+SDF.format(new Date())+"'");
		
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
	
	public boolean updateUser(Account account) {
		if(null != account && account.getId()>0){
			StringBuffer sql = new StringBuffer("update sec_users set ");
			
			if(null != account.getName()){
				sql.append("name='" + account.getName() + "',");
			}
			if(null != account.getLoginname()){
				sql.append("login_name='" + account.getLoginname() + "',");
			}
			if(null != account.getPswd()){
				sql.append("pswd='" + account.getPswd() + "',");
			}
			if(null != account.getPhone()){
				sql.append("phone='" + account.getPhone() + "',");
			}
			if(null != account.getCreateTime()){
				sql.append("create_time='" + account.getCreateTime() + "',");
			}
			if(null != account.getLastLoginTime()){
				sql.append("last_login_time='" + account.getLastLoginTime() + "',");
			}
			if(account.getStatus() == 0 || account.getStatus() == 1){
				sql.append("status=" + account.getStatus());
			}
			
			sql.append(" where id=" + account.getId());
			
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
	
	public boolean addRoles(int userId,String roleId) {
		if(userId > 0 && null != roleId){
			StringBuffer sql = new StringBuffer("insert into sec_user_role (uid,rid) values");
			String[] roleIds = roleId.split(",");
			for (String id : roleIds) {
				sql.append("("+userId+","+id+"),");
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
	
	public boolean deleteRoles(int userId,String roleId) {
		if(userId > 0 && null != roleId){
			StringBuffer sql = new StringBuffer("delete from sec_user_role where uid=" + userId + " and rid in ("+roleId+")");
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