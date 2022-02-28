package browse;

/**
 * The SearchValidation class, deals with the input validations
 * for the user's inputs.
 */
public class SearchValidation {

    /**
     * The checkForTheCorrectInput method, gets the required number,
     * in our case the 7, which is bound to that due to exercise,
     * and if is not equal to that, it will be thrown a wrong message input.
     * @param wantedNumber is bound to specific number which is required: 7
     * @return void
     */
    public void checkForTheCorrectInput(int wantedNumber){
        try {
            Integer.parseInt(Integer.toString(wantedNumber));
        } catch(RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            if(e instanceof NumberFormatException){
                throw new  NumberFormatException("Error: Number Format Exception!");
            } else if(e instanceof IllegalArgumentException){
                throw new  IllegalArgumentException("Error: Illegal Argument Exception!");
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param numbers: The array of integers in which will be
     *                 searching for the wanted number.
     * @return: true || false
     */
    public boolean isArrayNotValid(int [] numbers){
        return ((numbers == null)||(numbers.length<=0));
    }
}
