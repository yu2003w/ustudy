package com.ustudy.dashboard.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.model.Permission;
import com.ustudy.dashboard.model.Role;
import com.ustudy.dashboard.services.AccountService;
import com.ustudy.dashboard.services.PermissionService;
import com.ustudy.dashboard.services.RoleService;

public class ShiroDbRealm extends AuthorizingRealm {

	private static Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);

	@Autowired
	private AccountService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;

	public ShiroDbRealm() {
		super();
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		LOGGER.info(token.getUsername());
		Account user = userService.findUserByLoginName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getLoginname(),
					user.getPswd(), getName());
		} else {
			throw new AuthenticationException();
		}
	}

	/**
	 * role and permission set
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		String username = (String) principals.getPrimaryPrincipal();
		Account user = userService.findUserByLoginName(username);
		
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		if (user != null) {
			
			List<Role> roles = roleService.getRoleByUserName(username);
			Set<String> roleNames = new HashSet<String>();
			if(null != roles){
				for (Role role : roles) {
					roleNames.add(role.getName());
				}
			}
			auth.setRoles(roleNames);
			
			List<Permission> Permissions = permissionService.getPermissionByUserName(username);
			Set<String> permissions = new HashSet<String>();
			if(null != Permissions){
				for (Permission permission : Permissions) {
					permissions.add(permission.getName());
				}
			}
			auth.setStringPermissions(permissions);
			
		}
		return auth;
	}

	/**
	 * clear cached AuthorizationInfo
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * clear all cached AuthorizationInfo
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	// @PostConstruct
	// public void initCredentialsMatcher() {
	// HashedCredentialsMatcher matcher = new
	// HashedCredentialsMatcher(ALGORITHM);
	// setCredentialsMatcher(matcher);
	// }
}