package org.apache.shiro.saml.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when a SAML assertion is incorrect or malformed.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@SuppressWarnings("serial")
public class IncorrectSamlException extends AuthenticationException {
	
	public IncorrectSamlException() {
		super();
	}

	public IncorrectSamlException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectSamlException(String message) {
		super(message);
	}

	public IncorrectSamlException(Throwable cause) {
		super(cause);
	}
	
}
