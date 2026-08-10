package org.apache.shiro.saml.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SamlToken}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class SamlTokenTest {

    @Test
    void shouldImplementHostAuthenticationToken() {
        SamlToken token = new SamlToken("127.0.0.1", "saml-request", false);
        assertThat(token).isInstanceOf(HostAuthenticationToken.class);
    }

    @Test
    void principalShouldReturnSAMLRequest() {
        SamlToken token = new SamlToken("127.0.0.1", "saml-req-value", false);
        assertThat(token.getPrincipal()).isEqualTo("saml-req-value");
    }

    @Test
    void credentialsShouldReturnSAMLRequest() {
        SamlToken token = new SamlToken("127.0.0.1", "saml-req-value", false);
        assertThat(token.getCredentials()).isEqualTo("saml-req-value");
    }

    @Test
    void hostShouldReturnConstructorValue() {
        SamlToken token = new SamlToken("192.168.1.1", "req", true);
        assertThat(token.getHost()).isEqualTo("192.168.1.1");
    }

    @Test
    void samlRequestShouldReturnConstructorValue() {
        SamlToken token = new SamlToken("host", "my-saml-request", false);
        assertThat(token.getSAMLRequest()).isEqualTo("my-saml-request");
    }

    @Test
    void rememberMeShouldReturnConstructorValue() {
        SamlToken tokenTrue = new SamlToken("host", "req", true);
        SamlToken tokenFalse = new SamlToken("host", "req", false);
        assertThat(tokenTrue.isRememberMe()).isTrue();
        assertThat(tokenFalse.isRememberMe()).isFalse();
    }
}
