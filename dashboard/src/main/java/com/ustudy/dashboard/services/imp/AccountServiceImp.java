package com.ustudy.dashboard.services.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	// private SimpleDateFormat SDF = new SimpleDateFormat("yyy-MM-dd
	// HH:mm:ss");

	@Autowired
	private JdbcTemplate jdbcT;

	@Override
	public List<Account> getList(int id) {
		List<Account> accL = null;
		String sqlAcc = "select * from ustudy.users where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			accL = jdbcT.query(sqlAcc, new AccountRowMapper(), id);
			logger.debug("Fetched " + accL.size() + " items of user");

			for (Account acc : accL) {
				// Noted: for getList() service, front end doesn't need additional
				// permissions related information
				logger.debug(acc.toString());
			}

		} catch (DataAccessException e) {
			logger.warn("getList(), retrieve data from id " + id + " failed.");
			logger.warn(e.getMessage());
		}
		return accL;
	}

	public Account findUserByLoginName(String login) {
		String sqlDis = "SELECT * from ustudy.users where loginname = ?";
		Account item = jdbcT.queryForObject(sqlDis, new AccountRowMapper(), login);
		return item;
	}

	@Override
	public Account findUserById(int id) {
		String sqlDis = "select * from ustudy.users where id = ?";
		Account item = jdbcT.queryForObject(sqlDis, new AccountRowMapper(), id);

		// TODO: whether need to get permissions information.
		return item;
	}

	@Override
	@Transactional
	public int delItem(int id) {
		String sqlDel = "delete from ustudy.users where id = ?";
		return jdbcT.update(sqlDel, id);
	}

	@Override
	@Transactional
	public int delItemSet(String ids) {
		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;

		String sqlDel = "delete from ustudy.users where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			} else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("delItemSet(), sql for batch deletion --> " + sqlDel);
		return jdbcT.update(sqlDel);
	}

	@Override
	@Transactional
	public int createItem(Account item) {

		// Noted: Schema for table ustudy.user is as below,
		// id, loginname, fullname, passwd, ugroup, ctime, ll_time,
		// status, province, city, district
		String genAcc = "insert into ustudy.users values(?,?,?,?,?,?,?,?,?,?,?);";

		// insert record into dashoboard.users firstly, also auto generated keys
		// is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1; // auto generated id

		// need to retrieve auto id of school item which is returned back in
		// header location
		int num = jdbcT.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(genAcc, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getLoginname());
				psmt.setString(3, item.getFullname());
				psmt.setString(4, item.getPasswd());
				psmt.setString(5, item.getRoleName());

				// account creation time should be set to current time
				// psmt.setString(6,
				// SDF.format(Calendar.getInstance().getTime()));
				psmt.setString(6, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

				// last login time should be null at this point
				psmt.setNull(7, java.sql.Types.VARCHAR);

				psmt.setString(8, item.getStatus());
				psmt.setString(9, item.getProvince());
				psmt.setString(10, item.getCity());
				psmt.setString(11, item.getDistrict());
				// logger.debug("createItem(), account creation sql --> " +
				// psmt.toString());
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

		// TODO: for regular roles, equivalent permissions are already populated
		// into database,
		// only need to populate related roles
		String roleSql = "insert into ustudy.roles values (?,?,?);";
		num = jdbcT.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(roleSql, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, getRealRole(item.getRoleName()));
				psmt.setString(3, item.getLoginname());
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			String msg = "createItem(), return value for role creation is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		id = keyH.getKey().intValue();
		if (id < 0) {
			String msg = "createItem(), invalid id for role creation " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		logger.debug("createItem(), populate roles into database with id " + id);

		// for additional permissions, need to add a new role named
		// "addi_role_username" for the user.
		// Then populate related permissions into ustudy.perms.
		// This is to fit for shiro JDBC realm implementations.

		return id;
	}

	private final String getRealRole(String roleName) {
		String realRole = null;
		if (roleName != null && !roleName.isEmpty()) {
			if (roleName.compareTo("运维") == 0) {
				realRole = "admin";
			} else if (roleName.compareTo("市场") == 0) {
				realRole = "market";
			} else if (roleName.compareTo("代理商") == 0) {
				realRole = "reseller";
			} else
				realRole = "temp";
		}
		return realRole;
	}

	@Override
	@Transactional
	public int updateItem(Account item, int id) {
		String updateAcc = "update ustudy.users set ";
		Account ori = findUserById(id);
		Set<Map.Entry<String, String>> fields = null;
		Map<String, String> accDiff = item.compare(ori);
		if (accDiff == null || accDiff.size() == 0) {
			logger.info("updateItem(), no item changed, no need to update.");
			return 0;
		} else {
			// some stuff in account changed, need to populate changed fields
			fields = accDiff.entrySet();
			boolean first = true;
			for (Map.Entry<String, String> elem : fields) {
				if (first) {
					updateAcc += elem.getKey() + " = '" + elem.getValue() + "'";
					first = false;
				} else
					updateAcc += ", " + elem.getKey() + " = '" + elem.getValue() + "'";
			}
		}

		updateAcc += " where id = ?";
		logger.debug("updateItem(), update sql --> " + updateAcc);
		int num = jdbcT.update(updateAcc, id);
		if (num != 1) {
			logger.warn("updateItem(), returned value for account update is " + num);
		}
		// if role_name changed, need to populate here
		String roleN = accDiff.get("ugroup");
		if (roleN != null) {
			String updateRole = "update ustudy.roles set role_name = ? where user_name = ?";
			num = jdbcT.update(updateRole, this.getRealRole(roleN), item.getLoginname());
			if (num != 1) {
				logger.warn("updateItem(), returned value for roles update is " + num);
			} else
				logger.debug("updateItem(), populate role " + roleN
					+ " for user " + item.getLoginname() + "successfully");
		}
		return num;
	}
}
