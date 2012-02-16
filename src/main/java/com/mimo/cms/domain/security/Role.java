package com.mimo.cms.domain.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
public class Role extends AbstractLifecycleAwareObject<Role> {

	private static final long serialVersionUID = 1L;

	private String name;
	private String showName;

	private transient Map<String, String> authoritiesTrans = new HashMap<String, String>();
	private Set<Authority> authorities = new HashSet<Authority>(0);

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public String getShowName() {
		return showName;
	}

	public Role setShowName(String showName) {
		this.showName = showName;
		return this;
	}

	public Map<String, String> getAuthoritiesTrans() {
		return authoritiesTrans;
	}

	public void setAuthoritiesTrans(Map<String, String> authoritiesTrans) {
		this.authoritiesTrans = authoritiesTrans;
	}

	public Set<Authority> getAuthorities() {
		if (null == authorities) {
			return Collections.emptySet();
		}

		return authorities;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasAuthority() {
		return !getAuthorities().isEmpty();
	}

	public Role setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
		return this;
	}

	/**
	 * transit current authorities to authTrans map
	 * 
	 * @return
	 */
	public Role transitAuths() {
		Map<String, String> authTrans = new HashMap<String, String>();
		ConvertUtils.convertPropertyToMap(getAuthorities(), "id", "id", authTrans);
		setAuthoritiesTrans(authTrans);
		return this;
	}

	/**
	 * merge authority from authTrans map
	 * 
	 * @return
	 */
	protected Role mergeAuths() {
		if (null == getAuthoritiesTrans()) {
			return this;
		}

		// clear current authorities first
		getAuthorities().clear();
		for (String authId : getAuthoritiesTrans().values()) {
			if (StringUtils.isBlank(authId)) {
				continue;
			}

			Authority auth = new Authority();
			auth.setId(authId);
			getAuthorities().add(auth);
		}

		return this;
	}

	public String getAuthNamesAsString() {
		if (getAuthorities().isEmpty()) {
			return "";
		}

		return ConvertUtils.convertPropertyToString(getAuthorities(), "name", ",");
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAuthPermissions() {
		if (getAuthorities().isEmpty()) {
			return Collections.emptyList();
		}

		List<String> permissions = new ArrayList<String>();
		ConvertUtils.convertPropertyToList(getAuthorities(), "permission", permissions);
		return permissions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		mergeAuths();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {
		mergeAuths();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected Role getCaller() {
		return this;
	}
}
