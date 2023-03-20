package by.a1.unauthorizeddeliveries.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CsvValidatorTest {
    @Nested
    class IsLineNotEmpty {
        @Test
        void isLineNotEmptyShouldReturnFalseForEmptyArray() {
            String[] strings = new String[0];

            boolean result = CsvValidator.isLineNotEmpty(strings);

            Assertions.assertThat(result).isFalse();
        }

        @Test
        void isLineNotEmptyShouldReturnTrueForNonEmptyArray() {
            String[] strings = {"foo", "bar", "baz"};

            boolean result = CsvValidator.isLineNotEmpty(strings);

            Assertions.assertThat(result).isTrue();
        }

        @Test
        void isLineNotEmptyShouldReturnFalseForArrayWithNullValues() {
            String[] strings = {null, null, null};

            boolean result = CsvValidator.isLineNotEmpty(strings);

            Assertions.assertThat(result).isFalse();
        }

        @Test
        void isLineNotEmptyShouldReturnFalseForArrayWithEmptyValues() {
            String[] strings = {"", "", ""};

            boolean result = CsvValidator.isLineNotEmpty(strings);

            Assertions.assertThat(result).isFalse();
        }
    }
}