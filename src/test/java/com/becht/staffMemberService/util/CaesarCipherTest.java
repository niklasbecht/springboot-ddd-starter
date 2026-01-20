package com.becht.staffMemberService.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {

    @ParameterizedTest
    @CsvSource({"test,1,uftu", "test,500,zkyz","clark,5,hqfwp"})
    void encrypt(String message,int offset,String expected) {
        final var encryptedMessageOpt= CaesarCipher.encrypte(message,offset);
        assertTrue(encryptedMessageOpt.isDefined());
        assertEquals(expected,encryptedMessageOpt.get());
    }
    @Test
    void encrypt_emptyString() {
        final var encryptedMessageOpt= CaesarCipher.encrypte("",2);
        assertTrue(encryptedMessageOpt.isDefined());
        assertEquals("",encryptedMessageOpt.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE,-30,-1,0,26,52})
    void fail_encrypt_by_offset(int offset){
        final var encryptedMessageOpt= CaesarCipher.encrypte("I am a message in clear text",offset);
        assertTrue(encryptedMessageOpt.isEmpty());
    }

    @Test
    void fail_encrypt_by_input(){
        final var encryptedMessageOpt= CaesarCipher.encrypte("I am a  invalid message 34 ! $ in clear text",3);
        assertTrue(encryptedMessageOpt.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"3,abc", "522,zab","5,cde"})
    void encrypt_with_overflow(int offset,String expected){
        final var encryptedMessageOpt= CaesarCipher.encrypte("xyz",offset);
        assertEquals(expected,encryptedMessageOpt.get());
    }

}