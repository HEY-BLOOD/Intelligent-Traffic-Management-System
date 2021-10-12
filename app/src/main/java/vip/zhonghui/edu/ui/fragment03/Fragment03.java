package vip.zhonghui.edu.ui.fragment03;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.Fragment03Binding;
import vip.zhonghui.edu.ui.BaseFragment;
import vip.zhonghui.edu.utils.GsonUtil;
import vip.zhonghui.edu.utils.HttpUtil;
import vip.zhonghui.edu.utils.SharedPreferencesUtil;
import vip.zhonghui.edu.utils.UrlUtil;

/**
 * 数据分析
 */
// COMPLETED Step 3.1 通过点击侧滑菜单中的【数据分析】按钮进入本模块。
public class Fragment03 extends BaseFragment {

    private static final String ALL_CAR_KEY = "allCarRes";
    private static final String PEC_CAR_KEY = "pecCarRes";
    private Fragment03Binding binding;

    float mAllCarCount = 0;
    float mPecCarCount = 0;

    private Handler mAllCarHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            AllCarRes allCarRes = data.getParcelable(ALL_CAR_KEY);
            if (allCarRes.getResult().equals("S")) {
                mAllCarCount = allCarRes.getRowsDetail().size();
                updateChartUI();
            }
        }
    };

    private Handler mPecCarHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            PecCarRes pecCarRes = data.getParcelable(PEC_CAR_KEY);

            if (pecCarRes.getResult().equals("S")) {
                List carList = new ArrayList<String>();
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
    };

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
        binding = Fragment03Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        if (mAllCarCount > 0 && mPecCarCount > 0) {
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
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);

        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();

        binding.pieChart.setDescription(null);
        binding.pieChart.setHoleRadius(0f);
        binding.pieChart.setTransparentCircleRadius(0f);
        binding.pieChart.getLegend().setEnabled(false);// 不显示图例
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_03, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // COMPLETED Step 3.4 点击右上角【详情按钮】，可以跳转到违章管理界面
            case R.id.action_detail:
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_fragment_03_to_detailFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        // FIXME Fake data
//        allCarRes = GsonUtil.fromJson("{\"RESULT\":\"S\",\"ERRMSG\":\"成功\",\"ROWS_DETAIL\":[" +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}," +
//                "{\"carnumber\":\"鲁 B10001\",\"number\":1,\" pcardid \":\"370101196101011001\",\"buydata\":\"2016.5.1\",\"carbrand\":\"audi\"}" +
//                "]}", AllCarRes.class);

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

        // FIXME Fake data
//        pecCarRes = GsonUtil.fromJson("{\"RESULT\":\"S\",\"ERRMSG\":\"成功\",\"ROWS_DETAIL\":[" +
//                "{\"carnumber\":\"鲁B10001\",\"pcode\":\"1001A\",\"paddr\":\"学院路\",\"datetime\":\" 2016/5/21 8:19:21\"}," +
//                "{\"carnumber\":\"鲁B10001\",\"pcode\":\"1001A\",\"paddr\":\"学院路\",\"datetime\":\" 2016/5/21 8:19:21\"}" +
//                "]}", PecCarRes.class);

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