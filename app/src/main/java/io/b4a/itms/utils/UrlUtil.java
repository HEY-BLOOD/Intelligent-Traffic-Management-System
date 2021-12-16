package io.b4a.itms.utils;

import android.content.Context;

public class UrlUtil {
    public static String getUrl(Context context, String path) {
        String ip = SharedPreferencesUtil.getIp(context);
        String port = SharedPreferencesUtil.getPort(context);
        return String.format("http://%s:%s/api/v2/%s", ip, port, path);
    }
}
