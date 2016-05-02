/*
 * Created by VTLocator Group on 2016.05.01  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */
package com.vtlocator.jsfclasses.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class handles all encryption operations for the VTLocator application.
 */
public class CipherService {

    /**
     * This method hashes a string that is passed to it with a one-way algorithm
     * that is the combination of the MD5 and SHA1 algorithms.
     * @param plaintext the String to be hashed
     * @return the hashed plaintext String
     */
    public String hash(String plaintext) {
        return MD5(SHA1(plaintext));
    }

    /**
     * Adapted from http://www.mkyong.com/java/java-md5-hashing-example/
     * This method performs the MD5 algorithm on the parameterized String
     * @param plaintext the string to be encrypted
     * @return the encrypted String
     */
    public String MD5(String plaintext) {
        try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plaintext.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Adapted from http://www.java2s.com/Tutorial/Java/0490__Security/Encryptapassword.htm
     * This method performs the SHA-1 algorithm on the parameterized String
     * @param plaintext the string to be encrypted
     * @return the encrypted String
     */
    public String SHA1(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(plaintext.getBytes());

            byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(byteData[i]);
        }

        return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}