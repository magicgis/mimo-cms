package com.mimo.cms.interfaces.exception;

/**
 * 
 * @author loudyn
 * 
 */
@SuppressWarnings("serial")
public class MaliciousRequestException extends RuntimeException {

	public MaliciousRequestException(){
		super();
	}
	
	public MaliciousRequestException(String message) {
		super(message);
	}
}
