package vip.zhonghui.edu.ui.fragment03.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vip.zhonghui.edu.R;

public class DataImagesFragment extends Fragment implements OnDetailItemClickListener {

    private vip.zhonghui.edu.databinding.FragmentDataDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = vip.zhonghui.edu.databinding.FragmentDataDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<ImageBean> dataList = new ArrayList<>();
        dataList.add(new ImageBean(R.drawable.pec_image_1, getString(R.string.pec_info_1)));
        dataList.add(new ImageBean(R.drawable.pec_image_2, getString(R.string.pec_info_2)));
        dataList.add(new ImageBean(R.drawable.pec_image_3, getString(R.string.pec_info_3)));
        dataList.add(new ImageBean(R.drawable.pec_image_4, getString(R.string.pec_info_4)));

        DetailImageAdapter adapter = new DetailImageAdapter(dataList, this);
        binding.recyclerDataDetail.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int resId) {
        Toast.makeText(getContext(), "查看图片: " + resId, Toast.LENGTH_SHORT).show();
    }

}