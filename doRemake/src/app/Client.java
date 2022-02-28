package app;

import stringmanipulation.IRemake;
import stringmanipulation.Remake;

import java.util.Arrays;

/**
 * Client class is used for client test purposes.
 */
public class Client {

    public static void main(String[] args) {
        // just for convenience, please, add a new string to the below array - if desirable.
        // it will play all the cases - in terms of before - and - after the modifications.
        String [] initialText = {"Cats are great pets.",
                                 "Tom got a small piece of pie.",
                                 "He told us a very exciting tale."} ;
        // inits the Remark class
        IRemake remake = new Remake();
        // does a for-each loop of the above array of strings
        Arrays.stream(initialText).forEach(text -> {
            //does the actual work for string modification according to the exercise requirements.
            // calls the doRemark method, passing each time a string of above array.
            String modifiedText = remake.doRemake(text);
            // prints the initial text/string
            System.out.println("\ninitial Text:\t" + text);
            // prints the modified text/string
            System.out.println("modified Text:\t"+modifiedText);
        });
    }
}
