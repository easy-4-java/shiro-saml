package org.apache.shiro.saml.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link IncorrectSamlException}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class IncorrectSamlExceptionTest {

    @Test
    void shouldExtendAuthenticationException() {
        assertThat(new IncorrectSamlException()).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void defaultConstructorShouldCreateException() {
        IncorrectSamlException ex = new IncorrectSamlException();
        assertThat(ex.getMessage()).isNull();
    }

    @Test
    void messageConstructorShouldPreserveMessage() {
        IncorrectSamlException ex = new IncorrectSamlException("incorrect");
        assertThat(ex.getMessage()).isEqualTo("incorrect");
    }

    @Test
    void causeConstructorShouldPreserveCause() {
        RuntimeException cause = new RuntimeException("root");
        IncorrectSamlException ex = new IncorrectSamlException(cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    void messageAndCauseConstructorShouldPreserveBoth() {
        RuntimeException cause = new RuntimeException("root");
        IncorrectSamlException ex = new IncorrectSamlException("incorrect", cause);
        assertThat(ex.getMessage()).isEqualTo("incorrect");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
