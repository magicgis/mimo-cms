package com.mimo.cms.interfaces.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author loudyn
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
}
