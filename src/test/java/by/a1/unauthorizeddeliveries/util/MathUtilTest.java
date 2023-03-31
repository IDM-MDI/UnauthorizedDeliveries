package by.a1.unauthorizeddeliveries.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MathUtilTest {
    @Nested
    class Un {
        @Test
        void unShouldReturnExpectedValue() {
            Assertions.assertThat(MathUtil.un(5)).isEqualTo(1.275);
        }

        @Test
        void unShouldThrowIllegalArgumentExceptionForNegativeInput() {
            Assertions.assertThatThrownBy(() -> MathUtil.un(-1)).isInstanceOf(IllegalArgumentException.class);
        }
    }
    @Nested
    class Factorial {
        @Test
        void factorialShouldReturnExpectedValue() {
            Assertions.assertThat(MathUtil.factorial(4)).isEqualTo(24);
        }

        @Test
        void factorialShouldThrowIllegalArgumentExceptionForNegativeInput() {
            Assertions.assertThatThrownBy(() -> MathUtil.factorial(-1)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}