package com.mimo.cms.interfaces.util;

/**
 * 
 * @author loudyn
 * 
 */
public class JsonMessage {
	
	public enum MessageType {
		SUCCESS, WARNNING, ERROR
	}

	private JsonMessage() {
	}

	public static JsonMessage me() {
		return new JsonMessage();
	}

	private MessageType type;
	private String message;

	public MessageType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public JsonMessage success() {
		this.type = MessageType.SUCCESS;
		return this;
	}

	public JsonMessage warnning() {
		this.type = MessageType.WARNNING;
		return this;
	}

	public JsonMessage error() {
		this.type = MessageType.ERROR;
		return this;
	}

	public JsonMessage message(String message) {
		this.message = message;
		return this;
	}
}
