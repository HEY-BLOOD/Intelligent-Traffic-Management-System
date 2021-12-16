package io.b4a.itms.url;

import android.content.Context;

import io.b4a.itms.utils.SharedPreferencesUtil;

public class Api {
    public static final String URL_LOGIN = "/api/v2/user_login";

    public static String getRootURL(Context context) {
        return "http://" + SharedPreferencesUtil.getString(context, "ip", "192.168.0.100") + ":"
                + SharedPreferencesUtil.getString(context, "port", "8080");
    }
}
