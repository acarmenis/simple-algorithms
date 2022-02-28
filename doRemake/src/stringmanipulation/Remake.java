package stringmanipulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Remark class implements the doRemake inherited method.
 * Does string conversion according to the exercise (3) requirements.
 */
public class Remake implements IRemake {

    // used for token which starts with a consonant.
    // It is added at the token's end.
    private static final String AY = "ay";
    // used for token which starts with a vowel.
    // It is added at the token's end.
    private static final String WAY = "way";

    @Override
    public String doRemake(String text) {
        // Checks if mistakenly is given any empty string.
        if(text.isEmpty()){
            // then, the normal flow of the application is interrupted.
            // by throwing a relative exception for debugging purposes.
           throw new NullPointerException("Error: Empty string input");
        }
        // the converted string to list of tokens.
        List<String> words = split(text);
        // new list of modified tokens.
        List<String> changedWords = new ArrayList<>();
        // iterating over the unmodified list of tokens.
        words.forEach(word -> {
            // temporary placeholder string, holds the processed current token.
            String modifiedWord = "";
            // checks if the current token, starts with any vowel.
            if(vowels().contains(word.charAt(0))){
               // the variant of vowel starting word
               modifiedWord = modifyTokenBasedOnPunctuationExistence(word, WAY);
            } else {
                // the variant of consonant starting word
                if(Character.isUpperCase(word.charAt(0))){
                    // capitalizes the first character of the string.
                    word = String.valueOf(word.charAt(0)).toLowerCase().concat(word.substring(1));
                }
                // the variant of consonant starting word.
                modifiedWord = modifyTokenBasedOnPunctuationExistence(word, AY);
                // gets the latest index of AY occurrence.
                int lastIndex = modifiedWord.lastIndexOf(AY);
                // if found, then changes the first char position in the token.
                if(lastIndex != -1){
                    // then calls the addChar to do the char position change
                   modifiedWord = addChar(modifiedWord, word.charAt(0), lastIndex);
                }
            }
            // add modified token to the new list of modified tokens.
            changedWords.add(modifiedWord);
        });
        // sets the char of the first token of the list capitalized.
        changedWords.set(0, String.valueOf(changedWords.get(0).charAt(0)).toUpperCase().concat(changedWords.get(0).substring(1))) ;
        // returns the built new modified string.
        return String.join(" ", changedWords);
    }

    /**
     *
     * @param token: The token means each word of the whole string in at a given time.
     * @param subToken: Param which sets at the end a sub token depending on if
     *                  the token starts with vowel or consonant.
     * @return modified token.
     */
    private String modifyTokenBasedOnPunctuationExistence(String token, String subToken){
        // latest index of the current token
        int lastIndex = (token.length()-1);
        // checks if at the end of the current word exists any punctuation
        // or not and create the right new string, and returns the modified token.
        return (punctuations().contains(token.charAt(lastIndex)))
                                            // if any punctuation, then, get the substring up to that point, add the
                                            // 'any' token and then, add again the existed punctuation.
                                            ? token.substring(0, lastIndex) // up to last index - not including.
                                            .concat(subToken)
                                            .concat(String.valueOf(token.charAt(lastIndex)))
                                            // if it doesn't exist any such punctuation, simply, paste at the end the 'any' token.
                                            : token.concat(subToken);
    }

    /**
     *
     * @param str: The whole inputted text parameter;
     * @return List<String> tokenized.
     */
    private List<String> split(String str){
        return Stream.of(str.split(" ")).map(String::new).collect(Collectors.toList());
    }

    /**
     *
     * @return A list of vowels.
     */
    private List<Character> vowels(){
        return Arrays.asList('a','e','i','o','u','A','E','I','O','U');
    }

    /**
     *
     * @return A list of punctuations.
     */
    private List<Character> punctuations(){
        return Arrays.asList('.',',','!','?',':',';');
    }

    /**
     *
     * @param str: The token
     * @param c: The invasive character.
     * @param position: The position in which the first char of the text gets transferred.
     * @return: The new modified token with the invasive character.
     */
    public String addChar(String str, char c, int position) {
        // String builder
        StringBuilder sb = new StringBuilder(str);
        // char invasion
        sb.insert(position, c);
        // first char is deleted - not needed any more.
        sb.deleteCharAt(0);
        // returns the new token.
        return sb.toString();
    }

}
