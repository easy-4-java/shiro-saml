package org.apache.shiro.saml.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when a required SAML assertion is not present in the request.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
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
