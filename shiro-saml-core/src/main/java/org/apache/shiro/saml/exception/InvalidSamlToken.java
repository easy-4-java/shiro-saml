package org.apache.shiro.saml.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception thrown when a SAML token is invalid or cannot be parsed.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@SuppressWarnings("serial")
public class InvalidSamlToken extends AuthenticationException {
	
	public InvalidSamlToken() {
		super();
	}

	public InvalidSamlToken(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSamlToken(String message) {
		super(message);
	}

	public InvalidSamlToken(Throwable cause) {
		super(cause);
	}
	
}
