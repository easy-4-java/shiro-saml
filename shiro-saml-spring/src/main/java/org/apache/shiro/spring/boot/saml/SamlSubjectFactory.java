/*
 * Copyright (c) 2018, Loong Wan (https://github.com/loong10k).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.shiro.spring.boot.saml;

import java.util.Objects;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.web.mgt.SessionCreationEnabledSubjectFactory;
import org.apache.shiro.spring.boot.saml.token.Saml2Token;
import org.apache.shiro.spring.boot.saml.token.SamlToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * SAML SubjectFactory that preserves remember-me semantics for SAML tokens.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
public class SamlSubjectFactory extends SessionCreationEnabledSubjectFactory {

	public SamlSubjectFactory(boolean sessionCreationEnabled) {
		super(sessionCreationEnabled);
	}

	@Override
	public Subject createSubject(SubjectContext context) {

		if (context.isAuthenticated()) {
			AuthenticationToken token = context.getAuthenticationToken();
			if (Objects.nonNull(token) && token instanceof SamlToken && ((SamlToken) token).isRememberMe()) {
				context.setAuthenticated(false);
			} else if (Objects.nonNull(token) && token instanceof Saml2Token && ((Saml2Token) token).isRememberMe()) {
				context.setAuthenticated(false);
			}
		}

		return super.createSubject(context);
	}
}
