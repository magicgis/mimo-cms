package com.mimo.cms.domain.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.mimo.core.domain.event.AbstractLifecycleAwareObject;
import com.mimo.util.ConvertUtils;

/**
 * 
 * @author loudyn
 * 
 */
public class User extends AbstractLifecycleAwareObject<User> {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private boolean accountNonLocked = true;

	private transient Map<String, String> rolesTrans = new HashMap<String, String>();
	private Set<Role> roles = new HashSet<Role>(0);

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public User setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
		return this;
	}
	
	public User lock() {
		return setAccountNonLocked(false);
	}

	public User notLock() {
		return setAccountNonLocked(true);
	}

	public Set<Role> getRoles() {
		if (null == roles) {
			return Collections.emptySet();
		}

		return roles;
	}

	/**
	 * 
	 * @return
	 */
	public Role[] getRolesAsArray() {
		if (null == getRoles() || getRoles().isEmpty()) {
			return new Role[] {};
		}

		return getRoles().toArray(new Role[] {});
	}

	public String getRoleNamesAsString() {
		if (getRoles().isEmpty()) {
			return "";
		}

		return ConvertUtils.convertPropertyToString(getRoles(), "name", ",");
	}

	public User transitRoles() {
		Map<String, String> rolesTrans = new HashMap<String, String>();
		ConvertUtils.convertPropertyToMap(getRoles(), "id", "id", rolesTrans);
		setRolesTrans(rolesTrans);
		return this;
	}

	protected User mergeRoles() {
		if (null == getRolesTrans()) {
			return this;
		}

		// clear current roles first
		getRoles().clear();
		for (String roleId : getRolesTrans().values()) {
			if (StringUtils.isBlank(roleId)) {
				continue;
			}

			Role role = new Role();
			role.setId(roleId);
			getRoles().add(role);
		}

		return this;
	}

	public Map<String, String> getRolesTrans() {
		return rolesTrans;
	}

	public User setRolesTrans(Map<String, String> rolesTrans) {
		this.rolesTrans = rolesTrans;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasRole() {
		return !getRoles().isEmpty();
	}

	public User setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getRoleNames() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		List<String> namesList = new LinkedList<String>();
		ConvertUtils.convertPropertyToList(getRoles(), "name", ",", namesList);
		return new HashSet<String>(namesList);
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getPermissions() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		Set<String> permissions = new HashSet<String>();
		for (Role role : getRoles()) {
			permissions.addAll(role.getAuthPermissions());
		}

		return permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		mergeRoles();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {
		mergeRoles();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected User getCaller() {
		return this;
	}

}
