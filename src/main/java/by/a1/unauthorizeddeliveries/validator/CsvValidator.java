package by.a1.unauthorizeddeliveries.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvValidator {
    public static boolean isLineNotEmpty(@NonNull String[] strings) {
        return Arrays.stream(strings)
                .anyMatch(value -> value != null && value.length() > 0);
    }
}
