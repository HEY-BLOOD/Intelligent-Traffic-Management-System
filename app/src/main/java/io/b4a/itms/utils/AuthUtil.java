package io.b4a.itms.utils;

import android.text.TextUtils;

public final class AuthUtil {

    public static boolean isUsernameValid(String username) {
        boolean isValid = true;
        if (TextUtils.isEmpty(username) || username == "") {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email) || email == "") {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isPasswordValid(String password) {
        boolean isValid = true;
        if (TextUtils.isEmpty(password) || password == "") {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isAccount(String username, String password) {
        boolean isValid = true;
        isValid = isUsernameValid(username) && isPasswordValid(password);
        return isValid;
    }

    public static boolean isPasswordConfirmValid(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
}
