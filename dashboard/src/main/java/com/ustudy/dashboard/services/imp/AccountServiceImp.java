package com.ustudy.dashboard.services.imp;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}