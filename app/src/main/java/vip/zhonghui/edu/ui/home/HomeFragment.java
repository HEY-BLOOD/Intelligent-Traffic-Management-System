package vip.zhonghui.edu.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vip.zhonghui.edu.databinding.FragmentHomeBinding;
import vip.zhonghui.edu.ui.BaseFragment;
import vip.zhonghui.edu.utils.GsonUtil;
import vip.zhonghui.edu.utils.HttpUtil;
import vip.zhonghui.edu.utils.SharedPreferencesUtil;
import vip.zhonghui.edu.utils.UrlUtil;

/**
 * 主页模块
 */
// COMPLETED Step 1.1 用户登录成功后跳转到【主页】界面
public class HomeFragment extends BaseFragment {

    private static final String SENSE_RES_KEY = "searchRes";
    private FragmentHomeBinding binding;

    final private Handler mSenseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();

            SenseRes senseRes = data.getParcelable(SENSE_RES_KEY);
            if (senseRes != null) {
                receiveSense(senseRes);
            }

        }

    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // COMPLETED Step 1.2 完成主页模块的布局（详见布局文件）
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // COMPLETED Step 1.3 显示当前的空气质量信息，包括 PM2.5、空气温度和湿度
        new SenseTask().start();

        // COMPLETED Step 1.4 点击右上角【刷新】按钮，可以刷新道路空气质量和信息
        binding.refreshButton.setOnClickListener(v -> new SenseTask().start());

        // TODO Step 1.5 实时（每隔3秒）显示三条道路的路况信息查询，并根据拥堵值进行颜色标记。

    }

    /**
     * @return
     * @throws IOException
     */
    private SenseRes sendSenseRequest() {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request request = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "get_all_sense"))
                .post(requestBody)
                .build();

        SenseRes senseRes = null;

        // COMPLETED Send a http request
        try {
            Response response = mHttpClient.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("SenseRes-RESPONSE", jsonString);

            senseRes = GsonUtil.fromJson(jsonString, SenseRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FIXME Fake data
//        String[] resArr = {"S", "F"};
//        senseRes = GsonUtil.fromJson("{\"RESULT\":" + resArr[new Random().nextInt(resArr.length)] + ",\"ERRMSG\":\"成功\",\"pm2.5\":" + new Random().nextInt(20) + ",\"co2\":5919,\"LightIntensity\":1711,\n" +
//                "\"humidity\":" + new Random().nextInt(100) + ",\"temperature\":" + new Random().nextInt(50) + "}", SenseRes.class);

        return senseRes;
    }


    private void receiveSense(SenseRes senseRes) {
        if (senseRes != null) {
            String RES = senseRes.getResult();
            if (RES.toUpperCase().equals("S")) {
                binding.senseInfo.pm25.setText(senseRes.getPm25() + "");
                binding.senseInfo.temp.setText(senseRes.getTemperature() + "");
                binding.senseInfo.hum.setText(senseRes.getHumidity() + "");
            }
        }

    }

    class SenseTask extends Thread {
        @Override
        public void run() {
            super.run();
            SenseRes senseRes = sendSenseRequest();
            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable(SENSE_RES_KEY, senseRes);

            message.setData(data);
            mSenseHandler.sendMessage(message);
        }
    }

}