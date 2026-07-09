/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package org.apache.shiro.spring.boot.saml.utils;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;

import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;

/**
 * Utilities for creating OpenSAML objects.
 *
 * @author ： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@SuppressWarnings("unchecked")
public class OpenSAMLUtils {

	private static final XMLObjectBuilderFactory BUILDER_FACTORY = XMLObjectProviderRegistrySupport.getBuilderFactory();
	private static final RandomIdentifierGenerationStrategy SECURE_RANDOM_ID_GENERATOR =
			new RandomIdentifierGenerationStrategy();

	public static String generateSecureRandomId() {
		return SECURE_RANDOM_ID_GENERATOR.generateIdentifier();
	}

	public static <T> T buildSAMLObject(final Class<T> clazz) throws Exception {
		QName defaultElementName = (QName) clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
		return (T) BUILDER_FACTORY.getBuilder(defaultElementName).buildObject(defaultElementName);
	}

	public static <T> T create(final Class<T> clazz, final QName elementName) {
		return (T) BUILDER_FACTORY.getBuilder(elementName).buildObject(elementName);
	}
}
