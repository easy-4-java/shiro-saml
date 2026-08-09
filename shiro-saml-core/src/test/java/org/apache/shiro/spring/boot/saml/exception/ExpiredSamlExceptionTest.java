package org.apache.shiro.spring.boot.saml.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ExpiredSamlException}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ExpiredSamlExceptionTest {

    @Test
    void shouldExtendAuthenticationException() {
        assertThat(new ExpiredSamlException()).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void defaultConstructorShouldCreateException() {
        ExpiredSamlException ex = new ExpiredSamlException();
        assertThat(ex.getMessage()).isNull();
    }

    @Test
    void messageConstructorShouldPreserveMessage() {
        ExpiredSamlException ex = new ExpiredSamlException("expired");
        assertThat(ex.getMessage()).isEqualTo("expired");
    }

    @Test
    void causeConstructorShouldPreserveCause() {
        RuntimeException cause = new RuntimeException("root");
        ExpiredSamlException ex = new ExpiredSamlException(cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    void messageAndCauseConstructorShouldPreserveBoth() {
        RuntimeException cause = new RuntimeException("root");
        ExpiredSamlException ex = new ExpiredSamlException("expired", cause);
        assertThat(ex.getMessage()).isEqualTo("expired");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
