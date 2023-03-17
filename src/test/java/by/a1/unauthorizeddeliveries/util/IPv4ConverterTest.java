package by.a1.unauthorizeddeliveries.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IPv4ConverterTest {
    @Nested
    class ToIntIP {
        @Test
        void toIntIPWithZeroShouldConvertValidIpAddressToInteger() {
            Assertions.assertThat(IPv4Converter.toIntIP("0.0.0.0")).isEqualTo(0);
        }

        @Test
        void toIntIPShouldConvertValidIpAddressToInteger() {
            Assertions.assertThat(IPv4Converter.toIntIP("127.0.0.1")).isEqualTo(2130706433L);
        }
    }
    @Nested
    class ToStringIP {
        @Test
        void toStringIPShouldConvertValidIntegerToIpAddress() {
            Assertions.assertThat(IPv4Converter.toStringIP(2149583360L)).isEqualTo("128.32.10.0");
        }
        @Test
        void toStringIPWithSimpleValueShouldConvertValidIntegerToIpAddress() {
            Assertions.assertThat(IPv4Converter.toStringIP(255)).isEqualTo("0.0.0.255");
        }
    }
}