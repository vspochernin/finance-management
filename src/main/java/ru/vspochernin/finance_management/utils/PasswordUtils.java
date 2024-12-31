package ru.vspochernin.finance_management.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    private static final BCrypt.Hasher HASHER = BCrypt.withDefaults();
    private static final int DEFAULT_COST = 12;
    private static final BCrypt.Verifyer VERIFYER = BCrypt.verifyer();

    public static String hashPassword(String password) {
        return HASHER.hashToString(DEFAULT_COST, password.toCharArray());
    }

    public static boolean checkPassword(String password, String bcryptHash) {
        return VERIFYER.verify(password.toCharArray(), bcryptHash.toCharArray()).verified;
    }
}
