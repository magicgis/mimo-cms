package com.mimo.cms.domain.security;

import com.mimo.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 *
 */
public class Authority extends AbstractLifecycleAwareObject<Authority> {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String permission;

	public String getName() {
		return name;
	}

	public Authority setName(String name) {
		this.name = name;
		return this;
	}

	public String getPermission() {
		return permission;
	}

	public Authority setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	@Override
	protected Authority getCaller() {
		return this;
	}
}
