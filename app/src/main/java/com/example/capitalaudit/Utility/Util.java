package com.example.capitalaudit.Utility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Util {

    public static String cred_hasher(String cred) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(cred.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes)
            {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);

            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;

        }
    }




}
