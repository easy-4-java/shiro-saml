package org.apache.shiro.saml.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Saml2Token}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class Saml2TokenTest {

    @Test
    void shouldImplementHostAuthenticationToken() {
        Saml2Token token = new Saml2Token("127.0.0.1", "saml-request", false);
        assertThat(token).isInstanceOf(HostAuthenticationToken.class);
    }

    @Test
    void principalShouldReturnSAMLRequest() {
        Saml2Token token = new Saml2Token("127.0.0.1", "saml-req-value", false);
        assertThat(token.getPrincipal()).isEqualTo("saml-req-value");
    }

    @Test
    void credentialsShouldReturnSAMLRequest() {
        Saml2Token token = new Saml2Token("127.0.0.1", "saml-req-value", false);
        assertThat(token.getCredentials()).isEqualTo("saml-req-value");
    }

    @Test
    void hostShouldReturnConstructorValue() {
        Saml2Token token = new Saml2Token("192.168.1.1", "req", true);
        assertThat(token.getHost()).isEqualTo("192.168.1.1");
    }

    @Test
    void samlRequestShouldReturnConstructorValue() {
        Saml2Token token = new Saml2Token("host", "my-saml-request", false);
        assertThat(token.getSAMLRequest()).isEqualTo("my-saml-request");
    }

    @Test
    void rememberMeShouldReturnConstructorValue() {
        Saml2Token tokenTrue = new Saml2Token("host", "req", true);
        Saml2Token tokenFalse = new Saml2Token("host", "req", false);
        assertThat(tokenTrue.isRememberMe()).isTrue();
        assertThat(tokenFalse.isRememberMe()).isFalse();
    }
}
