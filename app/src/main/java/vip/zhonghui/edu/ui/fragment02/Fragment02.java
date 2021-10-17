package vip.zhonghui.edu.ui.fragment02;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.Fragment02Binding;
import vip.zhonghui.edu.ui.BaseFragment;
import vip.zhonghui.edu.ui.fragment01.RechargeRes;
import vip.zhonghui.edu.utils.GsonUtil;
import vip.zhonghui.edu.utils.HttpUtil;
import vip.zhonghui.edu.utils.SharedPreferencesUtil;
import vip.zhonghui.edu.utils.UrlUtil;

/**
 * 红绿灯管理
 */
public class Fragment02 extends BaseFragment {

    private Fragment02Binding mBinding;

    private int[] mImageSrcArr = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3};

//    private Handler mImageHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            changeImage(msg.what);
//        }
//    };

    private Handler mLightHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mLightAdapter.submitList(mLightList);
        }
    };

    private ArrayList<LightInfoRes> mLightList = new ArrayList<>();
    private LightAdapter mLightAdapter;

    private static final int ROAD_COUNT = 3;

    private OkHttpClient mHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = Fragment02Binding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnimationDrawable animationDrawable = (AnimationDrawable) getContext().getDrawable(R.drawable.anim_light);
        mBinding.lightImage.setImageDrawable(animationDrawable);
        animationDrawable.start();

        mLightAdapter = new LightAdapter(mLightList);
        mBinding.lightRecycler.setAdapter(mLightAdapter);

        mBinding.sortGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    switch (checkedId) {
                        case R.id.road_asc:
                            if (mBinding.roadAsc.isChecked()) {
                                mBinding.sortGroup2.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getRoadId));
                            break;
                        case R.id.red_asc:
                            if (mBinding.redAsc.isChecked()) {
                                mBinding.sortGroup2.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getRedTime));
                            break;
                        case R.id.yellow_asc:
                            if (mBinding.yellowAsc.isChecked()) {
                                mBinding.sortGroup2.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getYellowTime));
                            break;
                        case R.id.green_asc:
                            if (mBinding.greenDesc.isChecked()) {
                                mBinding.sortGroup2.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getGreenTime));
                            break;
                    }
                }
                mLightAdapter.submitList(mLightList);
            }
        });

        mBinding.sortGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    switch (checkedId) {
                        case R.id.road_desc:
                            if (mBinding.roadDesc.isChecked()) {
                                mBinding.sortGroup1.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getRoadId).reversed());
                            break;
                        case R.id.red_desc:
                            if (mBinding.redDesc.isChecked()) {
                                mBinding.sortGroup1.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getRedTime).reversed());
                            break;
                        case R.id.yellow_desc:
                            if (mBinding.yellowDesc.isChecked()) {
                                mBinding.sortGroup1.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getYellowTime).reversed());
                            break;
                        case R.id.green_desc:
                            if (mBinding.greenDesc.isChecked()) {
                                mBinding.sortGroup1.clearCheck();
                            }
                            mLightList.sort(Comparator.comparing(LightInfoRes::getGreenTime).reversed());
                            break;
                    }
                }
                mLightAdapter.submitList(mLightList);
            }
        });

//        new ImageTask().start();
        new LightTask().start();
    }

//    private class ImageTask extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            int n = 0;
//            while (true) {
//                int idx = n % 3;
//                Message message = new Message();
//                message.what = idx;
//                mImageHandler.sendMessage(message);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                n++;
//            }
//        }
//    }

    private class LightTask extends Thread {
        @Override
        public void run() {
            super.run();
            mLightList.clear();
            for (int i = 1; i <= ROAD_COUNT; i++) {
                LightInfoRes lightInfoRes = sendLightRequest(i);
                if (lightInfoRes != null) {
                    mLightList.add(lightInfoRes);
                }
            }
            mLightHandler.sendMessage(new Message());
        }
    }

    private LightInfoRes sendLightRequest(int roadId) {

        JSONObject jsonParams = new JSONObject();
        try {
            // {"TrafficLightId":1, "UserName":"user1"}
            jsonParams.put("TrafficLightId", roadId);
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LightInfoRes lightInfoRes = HttpUtil.sendHttpRequest(getContext(),
                "get_trafficlight_config",
                jsonParams,
                LightInfoRes.class);


//        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

//        Request lightRequest = new Request.Builder()
//                .url(UrlUtil.getUrl(getContext(), "get_trafficlight_config"))
//                .post(requestBody)
//                .build();
        // COMPLETED Send a http request
//        try {
//            Response response = mHttpClient.newCall(lightRequest).execute();
//            String jsonString = response.body().string();
//            Log.d("LightRes-RESPONSE", jsonString);
//            lightInfoRes = GsonUtil.fromJson(jsonString, LightInfoRes.class);
//
//            lightInfoRes.setRoadId(roadId);
//            Log.d("LightInfoRes", lightInfoRes.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (lightInfoRes != null) {
            lightInfoRes.setRoadId(roadId);
        }
        return lightInfoRes;
    }


}