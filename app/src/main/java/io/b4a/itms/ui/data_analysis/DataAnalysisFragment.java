package io.b4a.itms.ui.data_analysis;

import android.graphics.Color;
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

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import io.b4a.itms.base.BaseFragment;
import io.b4a.itms.databinding.FragmentDataAnalysisBinding;
import io.b4a.itms.utils.GsonUtil;
import io.b4a.itms.utils.HttpUtil;
import io.b4a.itms.utils.SharedPreferencesUtil;
import io.b4a.itms.utils.UrlUtil;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 数据分析
 */
// COMPLETED Step 3.1 通过点击侧滑菜单中的【数据分析】按钮进入本模块。
public class DataAnalysisFragment extends BaseFragment {

    private static final String ALL_CAR_KEY = "allCarRes";
    private static final String PEC_CAR_KEY = "pecCarRes";
    private FragmentDataAnalysisBinding binding;

    private float mAllCarCount = -1;
    private float mPecCarCount = -1;

    private Handler mAllCarHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            AllCarRes allCarRes = data.getParcelable(ALL_CAR_KEY);
            if (allCarRes != null) {
                receiveAllCar(allCarRes);
            }
        }
    };
    private List<PecCarRes.ROWSDETAILDTO> mPecCarList = new ArrayList();
    private List<AllCarRes.ROWSDETAILDTO> mAllCarList = new ArrayList();

    private void receiveAllCar(AllCarRes allCarRes) {
        if (allCarRes.getResult().equals("S")) {
            mAllCarList = allCarRes.getRowsDetail();
            mAllCarCount = allCarRes.getRowsDetail().size();
            updateChartUI();
        }
    }

    private Handler mPecCarHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            PecCarRes pecCarRes = data.getParcelable(PEC_CAR_KEY);
            if (pecCarRes != null) {
                receivePecCar(pecCarRes);
            }
        }
    };

    private void receivePecCar(PecCarRes pecCarRes) {
        if (pecCarRes.getResult().equals("S")) {
            List carList = new ArrayList<String>();
            mPecCarList = pecCarRes.getRowsDetail();
            for (PecCarRes.ROWSDETAILDTO row : pecCarRes.getRowsDetail()) {
                String carNumber = row.getCarnumber();
                if (!carList.contains(carNumber)) {
                    carList.add(carNumber);
                }
            }
            mPecCarCount = carList.size();
            updateChartUI();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // COMPLETED Step 3.2 完成数据分析模块的布局（详见布局文件）
        binding = FragmentDataAnalysisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // FIXME Fake data
        setupPieChart(2000, 360);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // COMPLETED Step 3.3 根据提供的接口进行数据分析，分析有误违章的占比。
        new AllCarTask().start();
        new PecCarTask().start();

    }

    private void updateChartUI() {
        if (mAllCarCount > -1 && mPecCarCount > -1) {

            // 所有违章车辆的车牌
            LinkedHashSet pecCarSet = new LinkedHashSet();
            for (PecCarRes.ROWSDETAILDTO pec : mPecCarList) {
                pecCarSet.add(pec.getCarnumber());
            }

            // 筛选违章的黑车
            LinkedHashSet blackCarSet = new LinkedHashSet(pecCarSet);
            LinkedHashSet notBlackCarSet = new LinkedHashSet();
            for (AllCarRes.ROWSDETAILDTO car : mAllCarList) {
                notBlackCarSet.add(car.getCarnumber());
            }
            blackCarSet.removeAll(notBlackCarSet);

            // 车辆总数 加上 黑车数
            mAllCarCount = mAllCarCount + blackCarSet.size();

            setupPieChart(mAllCarCount, mPecCarCount);
        }
    }

    private void setupPieChart(float all, float pec) {
        float pecPer = pec / all * 100;
        float notPecPer = 100 - pecPer;

        // DataSet
        List<Entry> strings = new ArrayList<>();
        strings.add(new Entry(pecPer, 0));
        strings.add(new Entry(notPecPer, 1));
        PieDataSet dataSet = new PieDataSet(strings, null);

        // Colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#A304FC"));
        colors.add(Color.parseColor("#FD0000"));
        dataSet.setColors(colors);

        // Labels
        String[] labelArray = {"", ""};  // 不显示标签
        List<String> labels = Arrays.asList(labelArray);

        // PieData
        PieData pieData = new PieData(labels, dataSet);
        pieData.setDrawValues(true);  // optional
        // 小数点后二位
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.00");
                return format.format(v) + "%";
            }
        });
        pieData.setValueTextSize(15f);

        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();

        binding.pieChart.setDescription(null);
        binding.pieChart.setHoleRadius(0f);
        binding.pieChart.setTransparentCircleRadius(0f);
        binding.pieChart.getLegend().setEnabled(false);// 不显示图例
    }

    private AllCarRes sendAllCarRequest() {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request rechargeRequest = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "get_car_info"))
                .post(requestBody)
                .build();

        AllCarRes allCarRes = null;

        // COMPLETED Send a http request
        try {
            Response response = mHttpClient.newCall(rechargeRequest).execute();
            String jsonString = response.body().string();
            Log.d("AllCarRes-RESPONSE", jsonString);
            allCarRes = GsonUtil.fromJson(jsonString, AllCarRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allCarRes;
    }


    private PecCarRes sendPecCarRequest() {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request rechargeRequest = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "get_all_car_peccancy"))
                .post(requestBody)
                .build();

        PecCarRes pecCarRes = null;

        // COMPLETED Send a http request
        try {
            Response response = mHttpClient.newCall(rechargeRequest).execute();
            String jsonString = response.body().string();
            Log.d("PecCarRes-RESPONSE", jsonString);
            pecCarRes = GsonUtil.fromJson(jsonString, PecCarRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pecCarRes;
    }


    class AllCarTask extends Thread {
        @Override
        public void run() {
            super.run();

            AllCarRes allCarRes = sendAllCarRequest();
            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable(ALL_CAR_KEY, allCarRes);
            message.setData(data);
            mAllCarHandler.sendMessage(message);
        }
    }

    class PecCarTask extends Thread {
        @Override
        public void run() {
            super.run();

            PecCarRes pecCarRes = sendPecCarRequest();
            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable(PEC_CAR_KEY, pecCarRes);
            message.setData(data);
            mPecCarHandler.sendMessage(message);
        }
    }

}