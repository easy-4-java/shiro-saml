package org.apache.shiro.spring.boot.saml.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link NotObtainedSamlException}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class NotObtainedSamlExceptionTest {

    @Test
    void shouldExtendAuthenticationException() {
        assertThat(new NotObtainedSamlException()).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void defaultConstructorShouldCreateException() {
        NotObtainedSamlException ex = new NotObtainedSamlException();
        assertThat(ex.getMessage()).isNull();
    }

    @Test
    void messageConstructorShouldPreserveMessage() {
        NotObtainedSamlException ex = new NotObtainedSamlException("not obtained");
        assertThat(ex.getMessage()).isEqualTo("not obtained");
    }

    @Test
    void causeConstructorShouldPreserveCause() {
        RuntimeException cause = new RuntimeException("root");
        NotObtainedSamlException ex = new NotObtainedSamlException(cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    void messageAndCauseConstructorShouldPreserveBoth() {
        RuntimeException cause = new RuntimeException("root");
        NotObtainedSamlException ex = new NotObtainedSamlException("not obtained", cause);
        assertThat(ex.getMessage()).isEqualTo("not obtained");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
