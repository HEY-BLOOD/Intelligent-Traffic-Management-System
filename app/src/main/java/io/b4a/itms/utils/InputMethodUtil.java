package io.b4a.itms.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

final public class InputMethodUtil {
    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
