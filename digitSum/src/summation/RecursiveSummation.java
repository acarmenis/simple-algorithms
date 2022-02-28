package summation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RecursiveSummation class, implements the IRecursiveSummation interface.
 * It adds the recursive logic to the calculation of summation.
 */
public class RecursiveSummation implements IRecursiveSummation {

    /**
     * SUM_LIMIT_CONDITION, enforces the sum limitation to 9.
     * Giving the option of further calculations.
     */
    private static final int SUM_LIMIT_CONDITION = 9;

    @Override
    public int digitSum(int number) {

        // doesn't allow negative numbers
        if (number <= 1) return 1;

        // conversion from int to string
        final String numberAsString = Integer.toString(number);

        // list of integers which has every single digit of the initial given integer
        List<Integer> listOfDigits = numberAsString.chars()
                                                   .mapToObj(c -> (char) c)
                                                   .map(Character::getNumericValue)
                                                   .collect(Collectors.toList());

        // summation of every single digits of the initial integer
        int sum = listOfDigits.stream().reduce(0, Integer::sum);

        // applies the restriction to greater than 9 according to the exercise requirements.
        // if less or equals to 9, then, it returns its value,
        // otherwise, it undergoes the recursive method again
        return  (sum > SUM_LIMIT_CONDITION) ? digitSum(sum):sum;
    }

}
