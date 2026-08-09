package org.apache.shiro.spring.boot.saml;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.biz.web.mgt.SessionCreationEnabledSubjectFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SamlSubjectFactory}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class SamlSubjectFactoryTest {

    @Test
    void shouldExtendSessionCreationEnabledSubjectFactory() {
        SamlSubjectFactory factory = new SamlSubjectFactory(true);
        assertThat(factory).isInstanceOf(SessionCreationEnabledSubjectFactory.class);
    }

    @Test
    void constructorShouldAcceptSessionCreationEnabled() {
        SamlSubjectFactory factoryEnabled = new SamlSubjectFactory(true);
        SamlSubjectFactory factoryDisabled = new SamlSubjectFactory(false);
        assertThat(factoryEnabled).isNotNull();
        assertThat(factoryDisabled).isNotNull();
    }
}
