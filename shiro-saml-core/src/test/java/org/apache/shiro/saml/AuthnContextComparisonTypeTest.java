package org.apache.shiro.saml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AuthnContextComparisonType}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class AuthnContextComparisonTypeTest {

    @Test
    void shouldHaveFourValues() {
        assertThat(AuthnContextComparisonType.values()).hasSize(4);
    }

    @Test
    void shouldContainExact() {
        assertThat(AuthnContextComparisonType.valueOf("exact")).isEqualTo(AuthnContextComparisonType.exact);
    }

    @Test
    void shouldContainMinimum() {
        assertThat(AuthnContextComparisonType.valueOf("minimum")).isEqualTo(AuthnContextComparisonType.minimum);
    }

    @Test
    void shouldContainMaximum() {
        assertThat(AuthnContextComparisonType.valueOf("maximum")).isEqualTo(AuthnContextComparisonType.maximum);
    }

    @Test
    void shouldContainBetter() {
        assertThat(AuthnContextComparisonType.valueOf("better")).isEqualTo(AuthnContextComparisonType.better);
    }
}
