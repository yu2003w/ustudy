package com.ustudy.dashboard.services.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;
import com.ustudy.dashboard.util.AccountRowMapper;
import com.ustudy.dashboard.util.DashboardUtil;

@Service
public class AccountServiceImp implements AccountService {

	private static final Logger logger = LogManager.getLogger(AccountServiceImp.class);
	
	private SimpleDateFormat SDF = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	@Override
	public List<Account> getList(int id) {
		List<Account> accL = null;
		String sqlAcc = "select * from dashboard.users where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			accL = jdbcT.query(sqlAcc, new AccountRowMapper(), id);
			logger.debug("Fetched " + accL.size() + " items of user");
			
			for (Account acc: accL) {
				// Noted: for getList() service, front end doesn't need grades related information
				// to display, so here, no need to assemble grades information. To do so, whole 
				// processing could be speedup.
				// assembleGrades(sch);
				logger.debug(acc.toString());
			}
			
	    } catch (DataAccessException e) {
			logger.warn("getList() retrieve data from id " + id + " failed.");
			logger.warn(e.getMessage());
		} 
		return accL;
	}
	
	public Account findUserByLoginName(String login) {
		
		String sqlDis = "SELECT * from dashboard.users where loginname = ?";
		Account item = jdbcT.queryForObject(sqlDis, new AccountRowMapper(), login);
		return item;
	}
	
	@Override
	public Account findUserById(int id) {
		
		String sqlDis = "select * from dashboard.users where id = ?";
		Account item = jdbcT.queryForObject(sqlDis, new AccountRowMapper(), id);
		
		// TODO: whether need to get roles and permissions information.
		
		return item;
	}
	
	@Override
	@Transactional
	public int delItem(int id) {
		String sqlDel = "delete from dashboard.users where id = ?";
		return jdbcT.update(sqlDel, id);
	}
	
	@Override
	@Transactional
	public int delItemSet(String ids) {
		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;
		
		String sqlDel = "delete from dashboard.users where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			}
			else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("Assembled sql for batch deletion --> " + sqlDel);
		return jdbcT.update(sqlDel);
	}
	
	@Override
	@Transactional
	public int createItem(Account item) {
		
		// Noted: Schema for table dashboard.user is as below,
		// id, loginname, fullname, phone, passwd, ugroup, ctime, ll_time, status
		String genAcc = "insert into dashboard.school values(?,?,?,?,?,?,?);";

		// insert record into dashoboard.users firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		// need to retrieve auto id of school item which is returned back in header location
		int num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(genAcc, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getLoginname());
				psmt.setString(3, item.getFullname());
				psmt.setString(4, item.getPhone());
				psmt.setString(5, item.getPasswd());
				psmt.setString(6, item.getUserGroup());
				psmt.setString(7, item.getCreateTime());
				psmt.setString(8, item.getLastLoginTime());
				psmt.setString(9, item.getStatus());
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			String msg = "createItem(), return value for insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			String msg = "createItem() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}

		// TODO: also need to populate roles, permissions for the user
		
		return id;
	}
	
	@Override
	@Transactional
	public int updateItem(Account item, int id) {
		return 0;
	}
}