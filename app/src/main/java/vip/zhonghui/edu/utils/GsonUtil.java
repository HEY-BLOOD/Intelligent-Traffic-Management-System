package vip.zhonghui.edu.utils;

import com.google.gson.Gson;

public class GsonUtil {

    private static Gson mGson = new Gson();

    public static <T> T fromJson(String json, Class<T> tClass) {
        T t = mGson.fromJson(json, tClass);
        return t;
    }
}
