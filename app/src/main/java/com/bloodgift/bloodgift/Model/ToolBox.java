package com.bloodgift.bloodgift.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ToolBox {
        public static String getHash(String value) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-512");
                byte[] digest = md.digest(value.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        return "";
    }
}
