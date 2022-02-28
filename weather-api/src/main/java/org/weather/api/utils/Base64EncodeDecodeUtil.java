package org.weather.api.utils;

import java.util.Base64;
import java.util.Optional;

/**
 * Base64EncodeDecodeUtil encode-decode util
 */
public class Base64EncodeDecodeUtil {

    /**
     * The method encodes based on the Base64 algorithm.
     * It encodes with padding.
     * @param originalInput
     * @return
     */
    public static String encode(String originalInput) {
        String encoded = "";
        // wraps by an optional to avoid any parameter absence
        Optional<String> checkNull = Optional.ofNullable(originalInput);
        // checks for parameter presence
        if(checkNull.isPresent()) {
            // encodes
            encoded = Base64.getEncoder().encodeToString(checkNull.get().getBytes());
        }
        // return the encoded input
        return encoded;
    }

    /**
     * The method encodes based on the Base64 algorithm.
     * It encodes without padding.
     * @param originalInput
     * @return
     */
    public static String encodeWithoutPadding(String originalInput) {
        String encoded = "";
        // wraps by an optional to avoid any parameter absence
        Optional<String> checkNull = Optional.ofNullable(originalInput);
        // checks for parameter presence
        if(checkNull.isPresent()) {
            // encodes
            encoded = Base64.getEncoder().withoutPadding().encodeToString(checkNull.get().getBytes());
        }
        // return the encoded input
        return encoded;
    }

    /**
     * The method decodes the encoded input
     * @param encodedString
     * @return
     */
    public static String deCode(String encodedString) {
        String decoded = "";
        // wraps by an optional to avoid any parameter absence
        Optional<String> checkNull = Optional.ofNullable(encodedString);
        // checks for parameter presence
        if(checkNull.isPresent()) {
            // decodes
            byte[] decodedBytes = Base64.getDecoder().decode(checkNull.get());
            decoded = new String(decodedBytes);
        }
        // return the decoded input
        return decoded;
    }
}
