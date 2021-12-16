package io.b4a.itms.ui.traffic_light;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.b4a.itms.R;
import io.b4a.itms.base.BaseFragment;
import io.b4a.itms.databinding.FragmentTrafficLightBinding;
import io.b4a.itms.utils.GsonUtil;
import io.b4a.itms.utils.SharedPreferencesUtil;
import okhttp3.OkHttpClient;

/**
 * 红绿灯管理
 */
public class TrafficLightFragment extends BaseFragment {

    private FragmentTrafficLightBinding mBinding;

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
        mBinding = FragmentTrafficLightBinding.inflate(inflater, container, false);

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

        new LightTask().start();
    }

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

//        LightInfoRes lightInfoRes = HttpUtil.sendHttpRequest(getContext(),
//                "get_trafficlight_config",
//                jsonParams,
//                LightInfoRes.class);

        // FIXME Fake data
        Random random = new Random();
        int n = random.nextInt(45) + 10;
        String repoStr = String.format("{\"RESULT\":\"S\",\"ERRMSG\":\"设置成功\",\n" +
                "\"RedTime\":\"%d\",\"GreenTime\":\"%d\",\"YellowTime\":\"%d\"}", n - 5, n, n + 5);
        LightInfoRes lightInfoRes = GsonUtil.fromJson(repoStr, LightInfoRes.class);

        if (lightInfoRes != null) {
            lightInfoRes.setRoadId(roadId);
        }
        return lightInfoRes;
    }


}