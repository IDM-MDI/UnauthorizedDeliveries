package by.a1.unauthorizeddeliveries.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IPv4Converter {
    public static long toIntIP(String ip) {
        String[] octets = ip.split("\\.");

        long result = 0;
        for (int i = 0; i < 4; i++) {
            int octet = Integer.parseInt(octets[i]);
            result |= ((long) octet << ((3 - i) * 8));
        }
        return result;
    }

    public static String toStringIP(long ip) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append((ip >> ((3 - i) * 8)) & 0xff);
            if (i != 3) {
                builder.append(".");
            }
        }
        return builder.toString();
    }
}
