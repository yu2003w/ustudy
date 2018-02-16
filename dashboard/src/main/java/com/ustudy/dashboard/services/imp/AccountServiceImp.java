package com.ustudy.dashboard.services.imp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.dashboard.mapper.AccountMapper;
import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;
import com.ustudy.dashboard.util.DashboardUtil;

@Service
public class AccountServiceImp implements AccountService {

	private static final Logger logger = LogManager.getLogger(AccountServiceImp.class);

	// private SimpleDateFormat SDF = new SimpleDateFormat("yyy-MM-dd
	// HH:mm:ss");
	
	@Autowired
	private AccountMapper acctM;

	@Override
	public List<Account> getList(int id) {
		List<Account> accL = null;
		try {
			if (id < 0)
				id = 0;
			accL = acctM.getAccountList(id);

			for (Account acc : accL) {
				// Noted: for getList() service, front end doesn't need additional
				// permissions related information
				acc.convertRole();
				logger.debug(acc.toString());
			}

		} catch (DataAccessException e) {
			logger.error("getList(), retrieve users list from id " + id + " failed." + e.getMessage());
			throw new RuntimeException("retrieve users list failed");
		}
		
		logger.debug("getList(), " + accL.size() + " users fetched");
		return accL;
	}

	public Account findUserByLoginName(String loginId) {
		Account item = acctM.getUserByLoginName(loginId);
		if (item != null)
			item.convertRole();
		return item;
	}

	@Override
	public Account findUserById(int id) {
		Account item = acctM.getUserById(id);
		if (item != null)
			item.convertRole();
		// TODO: whether need to get permissions information.
		return item;
	}

	@Override
	@Transactional
	public int delItem(int id) {		
		return acctM.deleteAccount(id);
	}

	@Override
	@Transactional
	public int delItemSet(String ids) {
		List<String> idsList = DashboardUtil.parseIds(ids);
		
		int len = idsList.size();
		if (len == 0)
			return 0;

		String sqlDel = null;
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel = idsList.get(0);
			} else
				sqlDel += "," + idsList.get(i);
		}
		
		logger.debug("delItemSet(), ids combined for batch deletion --> " + sqlDel);
		
		return acctM.deleteAccounts(sqlDel);
		
	}

	@Override
	@Transactional
	public int createAccount(Account item) {

		// set creation time as current timestamp
		item.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		int ret = acctM.createUser(item);
		
		if (ret < 0 || ret > 2) {
			logger.error("createAccount(), failed with ret->" + ret + 
					", detailed account->" + item.toString());
			throw new RuntimeException("create account failed with ret->" + ret);
		}
		
		logger.debug("createAccount(), detailed info->" + item.toString());

		clearUserRoles(item.getLoginname());
		
		// TODO: for regular roles, equivalent permissions are already populated
		// into database, only need to populate related roles
		String role = DashboardUtil.getAcctRoleMap().get(item.getRoleName());
		if (role == null || role.isEmpty()) {
			logger.error("createAccount(), invalid role value->" + item.getRoleName());
			throw new RuntimeException("invalid role value->" + item.getRoleName());
		}
		
		int rid = acctM.getRoleId(role);
		if (rid <= 0) {
			logger.error("createAccount(), invalid role id for role->" + role);
			throw new RuntimeException("invalid role id for role->" + role);
		}
		ret = acctM.populateRoles(rid, item.getLoginname());

		// for additional permissions, need to add a new role named
		// "addi_role_username" for the user.
		// Then populate related permissions into ustudy.perms.
		// This is to fit for shiro JDBC realm implementations.

		return item.getId();
		
	}

	
	/**
	 * clear roles for user
	 * @param uname -- login username
	 * @return
	 */
	private int clearUserRoles(String uname) {
		int ret = acctM.clearRolesForUser(uname);
		logger.debug("clearUserRoles(), " + ret + " roles cleared for " + uname);
		return ret;
	}
	
	@Override
	@Transactional
	public int updateAccount(Account item, int id) {
		// update account behaves similar as create new user
		int ret = acctM.createUser(item);
		
		if (ret < 0 || ret > 2) {
			logger.error("updateAccount(), failed with ret->" + ret + 
					", detailed account->" + item.toString());
			throw new RuntimeException("update account failed with ret->" + ret);
		}
		
		logger.debug("updateAccount(), detailed info->" + item.toString());

		clearUserRoles(item.getLoginname());
		
		// TODO: for regular roles, equivalent permissions are already populated
		// into database, only need to populate related roles
		String role = DashboardUtil.getAcctRoleMap().get(item.getRoleName());
		if (role == null || role.isEmpty()) {
			logger.error("updateAccount(), invalid role value->" + item.getRoleName());
			throw new RuntimeException("invalid role value->" + item.getRoleName());
		}
		
		int rid = acctM.getRoleId(role);
		if (rid <= 0) {
			logger.error("updateAccount(), invalid role id for role->" + role);
			throw new RuntimeException("invalid role id for role->" + role);
		}
		ret = acctM.populateRoles(rid, item.getLoginname());

		// for additional permissions, need to add a new role named
		// "addi_role_username" for the user.
		// Then populate related permissions into ustudy.perms.
		// This is to fit for shiro JDBC realm implementations.

		return item.getId();
	}

	@Override
	@Transactional
	public boolean updateLLTime(int id) {
		
		int num = acctM.updateLLTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), id);
		if (num != 1) {
			logger.error("updateLLTime(), set last login time failed for user " + id);
			return false;
		}
		
		logger.debug("updateLLTime(), set last login timestamp for user id->" + id);
		
		return true;
	}
	
}
