package com.mimo.cms.interfaces.util;

/**
 * 
 * @author loudyn
 * 
 */
public class JsonMessage {
	
	public enum State {
		SUCCESS, WARNNING, ERROR
	}

	private JsonMessage() {
	}

	private State state;
	private String message;

	public State getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public static JsonMessage success() {
		JsonMessage me = new JsonMessage();
		me.state = State.SUCCESS;
		return me;
	}

	public static JsonMessage warnning() {
		JsonMessage me = new JsonMessage();
		me.state = State.WARNNING;
		return me;
	}

	public static JsonMessage error() {
		JsonMessage me = new JsonMessage();
		me.state = State.ERROR;
		return me;
	}

	public JsonMessage message(String message) {
		this.message = message;
		return this;
	}
}
