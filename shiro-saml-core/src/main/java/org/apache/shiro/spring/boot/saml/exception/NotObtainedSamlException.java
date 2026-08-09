package org.apache.shiro.spring.boot.saml.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when a required SAML assertion is not present in the request.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@SuppressWarnings("serial")
public class NotObtainedSamlException extends AuthenticationException {
	
	public NotObtainedSamlException() {
		super();
	}

	public NotObtainedSamlException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotObtainedSamlException(String message) {
		super(message);
	}

	public NotObtainedSamlException(Throwable cause) {
		super(cause);
	}
	
}
