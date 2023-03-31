package by.a1.unauthorizeddeliveries.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtil {
    public static double un(int n) {
        double factor = 1.0 / factorial(n);
        double partialSum = 0.0;
        for (int i = 1; i <= n; i++) {
            partialSum += factorial(i);
        }
        return factor * partialSum;
    }
    public static int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n-1);
    }
}
