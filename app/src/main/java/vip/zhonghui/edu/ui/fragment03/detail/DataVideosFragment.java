package vip.zhonghui.edu.ui.fragment03.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.FragmentDataDetailBinding;


public class DataVideosFragment extends Fragment implements OnDetailItemClickListener {

    private FragmentDataDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDataDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<VideoBean> dataList = new ArrayList<>();
        dataList.add(new VideoBean(R.drawable.pec_video_1, R.raw.pec_video_1, getString(R.string.pec_info_1)));
        dataList.add(new VideoBean(R.drawable.pec_video_2, R.raw.pec_video_2, getString(R.string.pec_info_2)));
        dataList.add(new VideoBean(R.drawable.pec_video_3, R.raw.pec_video_3, getString(R.string.pec_info_3)));
        dataList.add(new VideoBean(R.drawable.pec_video_4, R.raw.pec_video_4, getString(R.string.pec_info_4)));

        DetailVideoAdapter adapter = new DetailVideoAdapter(dataList, this);
        binding.recyclerDataDetail.setAdapter(adapter);


    }

    @Override
    public void onItemClick(int resId) {
        Intent intent = new Intent(getContext(),ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHOW_VIDEO_RES_ID, resId);
        startActivity(intent);
//        Toast.makeText(getContext(), "播放视频: " + resId, Toast.LENGTH_SHORT).show();
    }
}