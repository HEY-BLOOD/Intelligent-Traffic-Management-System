package vip.zhonghui.edu.ui.fragment01;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.Fragment01Binding;
import vip.zhonghui.edu.ui.BaseFragment;
import vip.zhonghui.edu.utils.GsonUtil;
import vip.zhonghui.edu.utils.HttpUtil;
import vip.zhonghui.edu.utils.SharedPreferencesUtil;
import vip.zhonghui.edu.utils.UrlUtil;

/**
 * ETC充值
 */
// COMPLETED Step 2.1 通过点击侧滑菜单中的【ETC充值】按钮进入本模块。
public class Fragment01 extends BaseFragment {

    private static final String RECHARGE_RES_KEY = "rechargeRes";
    private static final String SEARCH_RES_KEY = "searchRes";
    private Fragment01Binding mBinding;
    private String[] mCarIdArray = {"1号", "2号", "3号"};
    private String[] mMoneyArray = {"100", "200", "300"};

    private int mNowCarId = 1;
    private int mNowMoney = 100;

    private List<RecordRes> mRecordResList = new ArrayList<>();


    private Handler mRechargeHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();

            RechargeRes rechargeRes = data.getParcelable(RECHARGE_RES_KEY);
            if (rechargeRes.getResult().equals("S")) {
                Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
                new SearchTask().start();
                // COMPLETED Step 2.7 每次充值成功后更新顶部最近一次充值记录
                new RecordTask().start();
            } else {
                Toast.makeText(getContext(), "充值失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler mSearchHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            SearchRes searchRes = data.getParcelable(SEARCH_RES_KEY);
            if (searchRes.getResult().equals("S")) {
                mBinding.accountRecharge.restMoney.setText(searchRes.getBalance() + "");
                Toast.makeText(getContext(), "查询成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "查询失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler mRecordHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            updateRecordUI();
        }
    };

    private void updateRecordUI() {
        if (mRecordResList.isEmpty()) {
            mBinding.recordMessage.setText(R.string.no_recharge_history);
        } else {
            // 按充值时间排序
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mRecordResList.sort(Comparator.comparing(RecordRes::getTime));
            }
            // 显示最近的一条充值记录
            RecordRes recordRes = mRecordResList.get(mRecordResList.size() - 1);
            String message = String.format("%s%d号小车充值%d元", recordRes.getFormattedDate(),
                    recordRes.getCarId(), recordRes.getCost());
            mBinding.recordMessage.setText(message);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // COMPLETED Step 2.2 完成ETC充值模块界面的布局（详见布局文件）
        mBinding = Fragment01Binding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // COMPLETED 2.4 完成余额查询功能，在小车ETC充值处 的车号下拉框中选择车辆好（1-3号）
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mCarIdArray);
        mBinding.accountRecharge.cardIdSpinner.setAdapter(idAdapter);

        ArrayAdapter<String> moneyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mMoneyArray);
        mBinding.accountRecharge.moneySpinner.setAdapter(moneyAdapter);

        initListeners();

        // COMPLETED Step 2.8 进入本模块默认显示1号小车的余额
        new SearchTask().start();

        // COMPLETED Step 2.3 顶部显示最近一次充值记录…………
        new RecordTask().start();

    }

    private void initListeners() {
        mBinding.accountRecharge.cardIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNowCarId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });

        mBinding.accountRecharge.moneySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNowMoney = (position + 1) * 100;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });

        // COMPLETED 2.5 点击查询按钮，显示账户余额，并弹出“查询成功”/”查询失败“的提示
        mBinding.accountRecharge.searchButton.setOnClickListener(v -> new SearchTask().start());

        // COMPLETED 2.6 完成ETC账号充值功能…………
        mBinding.accountRecharge.rechargeButton.setOnClickListener(v -> new RechargeTask().start());

        // COMPLETED 2.9 点击【更多】按钮跳转到【充值记录】模块界面
        mBinding.recordButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecordFragment.RECORD_LIST_KEY, (ArrayList<? extends Parcelable>) mRecordResList);
            Navigation.findNavController(v).navigate(R.id.action_nav_fragment_01_to_recordFragment, bundle);
        });
    }

    private SearchRes sendSearchRequest() {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("CarId", mNowCarId);
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request rechargeRequest = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "get_car_account_balance"))
                .post(requestBody)
                .build();

        SearchRes searchRes = null;

        // COMPLETEDs Send a http request
        try {
            Response response = mHttpClient.newCall(rechargeRequest).execute();
            String jsonString = response.body().string();
            Log.d("SearchRes-RESPONSE", jsonString);
            searchRes = GsonUtil.fromJson(jsonString, SearchRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FIXME Fake data
//        String[] resArr = {"S", "F"};
//        searchRes = GsonUtil.fromJson("{\"RESULT\":" + resArr[new Random().nextInt(resArr.length)] + ",\"ERRMSG\":\"成功\",\"Balance\":" + new Random().nextInt(5000) + "}", SearchRes.class);

        return searchRes;
    }


    private RechargeRes sendRechargeRequest() {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("CarId", mNowCarId);
            jsonParams.put("Money", mNowMoney);
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request rechargeRequest = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "set_car_account_recharge"))
                .post(requestBody)
                .build();

        RechargeRes rechargeRes = null;
        // COMPLETED Send a http request
        try {
            Response response = mHttpClient.newCall(rechargeRequest).execute();
            String jsonString = response.body().string();
            Log.d("RechargeRes-RESPONSE", jsonString);
            rechargeRes = GsonUtil.fromJson(jsonString, RechargeRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FIXME Fake data
//        String[] resArr = {"S", "F"};
//        rechargeRes = GsonUtil.fromJson("{\"RESULT\":" + resArr[new Random().nextInt(resArr.length)] + ",\"ERRMSG\":\"失败\"}", RechargeRes.class);


        return rechargeRes;
    }


    private List<RecordRes> sendRecordListRequest(int carId) {
        JSONObject jsonParams = new JSONObject();
        try {
            // {"UserName":"user1","CarId":1}
            jsonParams.put("UserName", SharedPreferencesUtil.getUserName(getContext()));
            jsonParams.put("CarId", carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = HttpUtil.createRequestBody(jsonParams);

        Request request = new Request.Builder()
                .url(UrlUtil.getUrl(getContext(), "get_car_account_record"))
                .post(requestBody)
                .build();

        List<RecordRes> recordResList = new ArrayList<>();
        try {
            // COMPLETED Send a http request
            Response response = mHttpClient.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("RecordRes-RESPONSE", jsonString);
            JSONObject jsonRes = new JSONObject(jsonString);

            // FIXME Fake data
//            String jsonString = "{\"ERRMSG\":\"成功\",\"ROWS_DETAIL\":[{\"CarId\":" + carId + ",\"Time\":\"2017-11-26 " +
//                    "04:58:11\",\"Cost\":10},{\"CarId\":" + carId + ",\"Time\":\"2017-11-26 " +
//                    "04:58:19\",\"Cost\":20},{\"CarId\":" + carId + ",\"Time\":\"2017-11-26 " +
//                    "04:58:24\",\"Cost\":30},{\"CarId\":" + carId + ",\"Time\":\"2017-11-26 " +
//                    "04:58:28\",\"Cost\":40}],\"RESULT\":\"S\"}";
//            JSONObject jsonRes = new JSONObject(jsonString);

            if (jsonRes.optString("RESULT").equals("S")) {
                JSONArray rowsDetail = jsonRes.optJSONArray("ROWS_DETAIL");
                for (int i = 0; i < rowsDetail.length(); i++) {
                    JSONObject record = rowsDetail.getJSONObject(i);
                    RecordRes recordRes = GsonUtil.fromJson(record.toString(), RecordRes.class);
                    recordResList.add(recordRes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recordResList;
    }


    class SearchTask extends Thread {
        @Override
        public void run() {
            super.run();
            SearchRes searchRes = sendSearchRequest();

            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable(SEARCH_RES_KEY, searchRes);

            message.setData(data);
            mSearchHandler.sendMessage(message);
        }
    }

    class RechargeTask extends Thread {
        @Override
        public void run() {
            RechargeRes rechargeRes = sendRechargeRequest();

            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable(RECHARGE_RES_KEY, rechargeRes);

            message.setData(data);
            mRechargeHandler.sendMessage(message);
        }
    }

    class RecordTask extends Thread {
        @Override
        public void run() {
            super.run();
            mRecordResList.clear();
            for (int i = 1; i <= RECORD_CARD_ID_COUNT; i++) {
                List<RecordRes> recordResList = sendRecordListRequest(i);
                mRecordResList.addAll(recordResList);
            }
            Log.d("RecordTask", "size - " + mRecordResList.size());
            mRecordHandler.sendMessage(new Message());
        }
    }

    private static final int RECORD_CARD_ID_COUNT = 3;

}