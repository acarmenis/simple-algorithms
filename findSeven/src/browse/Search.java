package browse;

import java.util.Arrays;

public class Search implements IFindNumber {

    /**
     * The validation class field, is used to have the 'validations' seperated
     * from the main purpose of Search class.
     */
    private final SearchValidation validation = new SearchValidation();

    @Override
    public boolean findSeven(int[] integers, int digit) {

        // It checks the status of declared array
        // if it is an empty one, will be written an error message
        // and throws a relative exception.
        if(validation.isArrayNotValid(integers)){
            String errorMessage = " :: array 'integers' was not initiated inside method 'findSeven'.";
            System.out.println("Error: " + errorMessage);
            throw new NullPointerException(errorMessage);
        }

        // This check, could be avoided/missed.
        // It is just given, because the exercise is bounded to a specific int number. That of 7 one.
        // Please, comment it out in case of parameter de-coupling.
        // validation.checkForTheCorrectInput(digit);

        // stringifiedNumbers array, is converted from the original integer array
        // in order, to easy the way of searching feature.
        String [] stringifiedNumbers = Arrays.stream(integers)
                                             .mapToObj(String::valueOf)
                                             .toArray(String[]::new);

        //  The method, does return the result of finding: true || false.
        return Arrays.stream(stringifiedNumbers).anyMatch(s -> s.contains(Integer.toString(digit)));
    }

}
