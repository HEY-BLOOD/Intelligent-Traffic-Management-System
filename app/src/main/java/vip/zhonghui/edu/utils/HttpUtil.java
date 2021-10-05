package vip.zhonghui.edu.utils;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpUtil {
    public static RequestBody createRequestBody(JSONObject jsonParams) {
        return RequestBody.create(MediaType.parse("application/json"), jsonParams.toString());
    }
}
