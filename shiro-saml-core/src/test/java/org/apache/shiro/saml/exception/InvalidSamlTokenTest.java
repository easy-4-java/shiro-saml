package org.apache.shiro.saml.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link InvalidSamlToken}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class InvalidSamlTokenTest {

    @Test
    void shouldExtendAuthenticationException() {
        assertThat(new InvalidSamlToken()).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void defaultConstructorShouldCreateException() {
        InvalidSamlToken ex = new InvalidSamlToken();
        assertThat(ex.getMessage()).isNull();
    }

    @Test
    void messageConstructorShouldPreserveMessage() {
        InvalidSamlToken ex = new InvalidSamlToken("invalid");
        assertThat(ex.getMessage()).isEqualTo("invalid");
    }

    @Test
    void causeConstructorShouldPreserveCause() {
        RuntimeException cause = new RuntimeException("root");
        InvalidSamlToken ex = new InvalidSamlToken(cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    void messageAndCauseConstructorShouldPreserveBoth() {
        RuntimeException cause = new RuntimeException("root");
        InvalidSamlToken ex = new InvalidSamlToken("invalid", cause);
        assertThat(ex.getMessage()).isEqualTo("invalid");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
