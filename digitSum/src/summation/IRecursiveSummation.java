package summation;

/**
 * interface IRecursiveSummation, exposes a digitSum method,
 * which accepts an integer number, and calculates the sum of its digits.
 */
public interface IRecursiveSummation {

    /**
     *
     * @param number: any integer number
     * @return: int as the summation of its digits.
     */
    int digitSum(int number);
}
