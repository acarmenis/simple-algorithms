package app;

import browse.Search;

/**
 * Client class is used for client test purposes.
 */
public class Client {

    public static void main(String[] args) {

        // The integer array - It can be changed to contain any integer value(s)
        int [] numbers = {1,9,3,4,55,647};

        //Initiating the search class
        Search searchTo = new Search();

        // It calls the findSeven method and returns true or false accordingly
        // depending on the finds.
        boolean answer = searchTo.findSeven( numbers,7);

        // Printing the appropriate message
        System.out.println("\n\n\tAnswer:\t" + (answer ? "Found" :"There is no (7) in the array!"));
    }
}
