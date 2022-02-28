package app;

import summation.IRecursiveSummation;
import summation.RecursiveSummation;

/**
 * Client class is used for client test purposes.
 */
public class Client {

    public static void main(String[] args) {

        // Recursive class initiation
        IRecursiveSummation recursiveSummation = new RecursiveSummation();

        /**
         * 10 -> 1 + 0 = 1 ... returns 1
         * 38 -> 3 + 8 = 11 -> 1 + 1 = 2 ... returns 2
         * 785 -> 7 + 8 + 5 = 20 -> 2 + 0 = 2  returns 2
         * 99892 -> 9 + 9 + 8 + 9 + 2 = 37 -> 3 + 7 = 10 -> 1 + 0 = 1 returns 1
         */
        // calculation of the recursive summation
        int summation = recursiveSummation.digitSum(99892);

        // printing out the final summation result.
        System.out.println("\n\tdigitSum:\t(" + summation+")" );
    }
}
