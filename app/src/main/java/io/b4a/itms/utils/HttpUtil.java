package io.b4a.itms.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private final static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();

    public static RequestBody createRequestBody(JSONObject jsonParams) {
        return RequestBody.create(MediaType.parse("application/json"), jsonParams.toString());
    }

    public static <T> T sendHttpRequest(Context context, String urlPath, JSONObject jsonParams, Class<T> resClass) {

        try {
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request request = new Request.Builder()
                .url(UrlUtil.getUrl(context, urlPath))
                .post(requestBody)
                .build();

        T t = null;

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            String jsonString = response.body().string();

            t = GsonUtil.fromJson(jsonString, resClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }    public static String sendHttpRequest(Context context, String urlPath, JSONObject jsonParams) {

        try {
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request request = new Request.Builder()
                .url(UrlUtil.getUrl(context, urlPath))
                .post(requestBody)
                .build();

        String jsonString = null;
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
