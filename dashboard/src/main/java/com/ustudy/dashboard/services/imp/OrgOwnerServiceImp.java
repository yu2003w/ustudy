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

import com.ustudy.dashboard.mapper.OrgOwnerMapper;
import com.ustudy.dashboard.model.OrgOwner;
import com.ustudy.dashboard.model.Teacher;
import com.ustudy.dashboard.services.OrgOwnerService;
import com.ustudy.dashboard.util.DashboardUtil;

@Service
public class OrgOwnerServiceImp implements OrgOwnerService {

	private static final Logger logger = LogManager.getLogger(OrgOwnerServiceImp.class);
	
	@Autowired
	private OrgOwnerMapper ooM;
	
	@Override
	public List<OrgOwner> getOwnerList(long id) {
		List<OrgOwner> ooL = null;
		try {
			if (id < 0)
				id = 0;
			ooL = ooM.getOwnerList(id);
			logger.debug("getOwnerList(), fetched " + ooL.size() + " items of user");
			logger.trace("getOwnerList(), " + ooL.toString());
		} catch (DataAccessException e) {
			logger.error("getOwnerList(), retrieve data from id " + id + " failed with exception->" + e.getMessage());
		}
		return ooL;
	}

	@Override
	@Transactional
	public long createItem(OrgOwner item) {
		logger.debug("createItem(), orgowner to be created->" + item.toString());
		if (item.getRole() == null || !((item.getRole().compareTo("校长") == 0) ||
				item.getRole().compareTo("考务老师") == 0)) {
			logger.error("createItem(), unsupported role->" + item.toString());
			throw new RuntimeException("createItem(), unsupported role type " + item.getRole());
		}
		
		item.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		int ret = ooM.createOrgOwner(item);
		if (ret < 0 || ret > 2) {
			logger.error("createItem(), created item with ret = " + ret + " for " + item.getLoginname());
			throw new RuntimeException("failed to create orgowner " + item.getLoginname());
		}
		populateTeacher(item, 0);
		
		logger.debug("createItem(), populated teacher information");
		return item.getId();
	}

	@Transactional
	@Override
	public int updateOwner(OrgOwner item) {
		// firstly retrieve teacher related information for the item to be updated
		Teacher tea = ooM.getTeaByOwnerId(item.getId());
		logger.debug("updateOwner(), corresponding teacher info->" + tea.toString());
		int ret = ooM.updateOrgOwner(item);
		if (ret < 0 || ret > 2) {
			logger.error("updateOwner(), failed with ret " + ret + " for " + item.getId());
			throw new RuntimeException("failed update item " + item.getId());
		}
		long teaid = (tea == null ? 0: tea.getId());
		populateTeacher(item, teaid);
		
		logger.debug("updateOwner(), update orgowner " + item.getLoginname() + " with ret " + ret);
		return ret;
	}

	@Override
	public int delItemSet(String ids) {
		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;
		
		String idL = null;
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				idL = idsList.get(0);
			}
			else
				idL += "," + idsList.get(i);
		}
		logger.debug("delItemSet(), ids->" + idL);
		
		return ooM.deleteOwnerList(idL);
	}

	@Override
	@Transactional
	public int deleteItem(long id) {
		// also cleaned teachers related
		int ret = ooM.deleteOwner(id);
		
		if (ret < 0) {
			logger.error("deleteItem(), failed to delete item with id->" + id);
			throw new RuntimeException("failed to delete item");
		}

		return ret;
	}
	
	@Override
	public OrgOwner getOwner(long id) {
		OrgOwner item = ooM.getOwnerById(id);
		logger.trace("getOwner(), retrieved orgowner->" + item.toString());
		return item;
	}

	@Transactional
	private void populateTeacher(OrgOwner item, long teaId) {
		
		Teacher tea = new Teacher(item.getLoginname(), item.getName(), item.getPasswd(), item.getOrgType(), 
				item.getOrgId(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		int ret = -1;
		if (teaId > 0) {
			// update teacher
			tea.setId(teaId);
			ret = ooM.updateTeacher(tea);
		} else
			ret = ooM.createTeacher(tea);
		
		logger.debug("populateTeacher(), teacher created->" + tea.toString() + ", ret=" + ret);
		
		// clean old role values, maybe teacid changed before update orgowner
		if (teaId > 0) {
			ret = ooM.cleanRoleValues(item.getLoginname());
			logger.debug("populateTeacher(), cleaned role values with ret=" + ret);
		}
		
		// add default role_name "org_owner"
		String role = null;
		if (item.getRole().compareTo("校长") == 0) {
			role = "org_owner";
		} else if (item.getRole().compareTo("考务老师") == 0) {
			role = "leader";
		} else {
			logger.error("populateTeacher(), unsupported role->" + item.getRole());
			throw new RuntimeException("unsupported role->" + item.getRole());
		}
		
		ret = ooM.saveRoles(role, item.getLoginname());

		logger.debug("populateTeachers(), default role populated for " + item.getLoginname() + 
				" with ret " + ret);
	}

}
