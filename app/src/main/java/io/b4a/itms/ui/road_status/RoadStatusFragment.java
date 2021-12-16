package io.b4a.itms.ui.road_status;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.b4a.itms.base.BaseFragment;
import io.b4a.itms.databinding.FragmentRoadStatusBinding;
import io.b4a.itms.utils.GsonUtil;
import io.b4a.itms.utils.HttpUtil;
import io.b4a.itms.utils.SharedPreferencesUtil;

/**
 * 主页模块
 */
// COMPLETED Step 1.1 用户登录成功后跳转到【主页】界面
public class RoadStatusFragment extends BaseFragment {

    private static final String SENSE_RES_KEY = "searchRes";
    private FragmentRoadStatusBinding binding;

    final List<RoadStatusRes> mRoadStatusResList = new ArrayList<>();

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
    private Handler mStatusHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < mRoadStatusResList.size(); i++) {
                int roadId = i + 1;
                int status = mRoadStatusResList.get(i).getStatus();
                updateStatus(roadId, status);
            }
        }
    };

    private void updateStatus(int roadId, int status) {
        try {
            int color = getStatusColor(status);
            switch (roadId) {
                case 1:
                    binding.homeRoadStatus.homeView11.setBackgroundColor(color);
                    binding.homeRoadStatus.homeView12.setBackgroundColor(color);
                    binding.homeRoadStatus.homeView13.setBackgroundColor(color);
                    binding.homeRoadStatus.homeView14.setBackgroundColor(color);
                    break;
                case 2:
                    binding.homeRoadStatus.homeView2.setBackgroundColor(color);
                    break;
                case 3:
                    binding.homeRoadStatus.homeView3.setBackgroundColor(color);
                    break;
            }
        } catch (IllegalArgumentException e) {
            // nothing to do
        }
    }

    private int getStatusColor(int status) throws IllegalArgumentException {
        int color = 0;
        switch (status) {
            case 1:
                color = Color.parseColor("#0ebd12");
                break;
            case 2:
                color = Color.parseColor("#98ed1f");
                break;
            case 3:
                color = Color.parseColor("#ffff01");
                break;
            case 4:
                color = Color.parseColor("#ff0103");
                break;
            case 5:
                color = Color.parseColor("#4c060e");
                break;
        }
        return color;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // COMPLETED Step 1.2 完成主页模块的布局（详见布局文件）
        binding = FragmentRoadStatusBinding.inflate(inflater, container, false);
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

        // COMPLETED Step 1.5 实时（每隔3秒）显示三条道路的路况信息查询，并根据拥堵值进行颜色标记。
        new RoadStatusTask().start();
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

//        SenseRes senseRes = HttpUtil.sendHttpRequest(getContext(),
//                "get_all_sense",
//                jsonParams,
//                SenseRes.class);

        // FIXME Fake data
        SenseRes senseRes = GsonUtil.fromJson("{\"RESULT\":\"S\",\"ERRMSG\":\"成功\",\"pm2.5\":30,\"" +
                        "co2\":5919,\"LightIntensity\":1711,\"humidity\":60,\"temperature\":10}",
                SenseRes.class);

        return senseRes;
    }


    private void receiveSense(SenseRes senseRes) {
        if (senseRes != null) {
            String RES = senseRes.getResult();
            if (RES.equalsIgnoreCase("S")) {
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

    class RoadStatusTask extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {

                mRoadStatusResList.clear();
                // fetch three roads status
                for (int i = 1; i <= 3; i++) {
                    RoadStatusRes roadStatus = sendStatusRequest(i);
                    if (roadStatus != null) {
                        mRoadStatusResList.add(roadStatus);
                    }
                }

                mStatusHandler.sendEmptyMessage(1);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private RoadStatusRes sendStatusRequest(int roadId) {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("RoadId", roadId);
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RoadStatusRes roadStatusRes = HttpUtil.sendHttpRequest(getContext(),
                "get_road_status",
                jsonParams,
                RoadStatusRes.class);

        return roadStatusRes;
    }

}