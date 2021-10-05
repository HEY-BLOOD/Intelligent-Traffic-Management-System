package vip.zhonghui.edu.ui;

import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseFragment extends Fragment {
    public OkHttpClient mHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();
}


