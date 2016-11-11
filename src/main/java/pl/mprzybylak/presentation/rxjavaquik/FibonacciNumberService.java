package pl.mprzybylak.presentation.rxjavaquik;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * This class provides feature of calculation of fibonacci number
 */
public class FibonacciNumberService {

    /**
     * Calculates n-th fibonacci number
     * @param nth which fibonacci number to calculate
     * @return {@link Future} that will eventually contains result of calculation of n-th fibonacci number
     */
    public Future<Integer> countNthFibonacciNumber(int nth) {
        return CompletableFuture.supplyAsync(() -> nthFibonacci(nth));
    }

    private int nthFibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return nthFibonacci(n-1) + nthFibonacci(n-2);
    }

}
