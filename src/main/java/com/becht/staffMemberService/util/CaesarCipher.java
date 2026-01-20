package com.becht.staffMemberService.util;

import io.vavr.control.Option;
import lombok.NonNull;

public class CaesarCipher {

    private static final int ALPHABET_COUNT = 26;

    public static boolean isLowerCaseLetter(char c) {
        return (Character.isLetter(c) && Character.isLowerCase(c)) || Character.isWhitespace(c);
    }

    /**
     * Implementation of "Caesar Cipher"
     *   - shifts the whole message by the given offset
     *   - incase a char exceeds the alphabet when shifting it stars at the beginning of the Alphabet i.e z +2 -> b
     *   - Input:
     *       - offset must be bigger than 0, and cant be multiple of 26
     *       - must only contain lowercase letters and whitespaces
     */
    public static Option<String> encrypte(@NonNull final String message, int offset) {
        if (offset <= 0 || offset % ALPHABET_COUNT == 0) {
            //Invalid Input
            return Option.none();
        }

        StringBuilder builder = new StringBuilder();
        offset = offset % ALPHABET_COUNT;

        final var messageCharArray = message.toCharArray();
        for (char c : messageCharArray) {
            if (Character.isWhitespace(c)) {
                builder.append(c);
            } else if (isLowerCaseLetter(c)) {
                //perform shift
                final var encryptedCharacter = (char) ((c + offset > 'z') ? (c + offset - ALPHABET_COUNT) : (c + offset));
                builder.append(encryptedCharacter);
            } else {
                //Invalid Input
                return Option.none();
            }
        }

        return Option.of(builder.toString());
    }


}
