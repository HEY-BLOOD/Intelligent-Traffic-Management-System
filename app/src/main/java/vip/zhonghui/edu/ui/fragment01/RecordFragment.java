package vip.zhonghui.edu.ui.fragment01;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import vip.zhonghui.edu.databinding.FragmentRecordBinding;
import vip.zhonghui.edu.ui.BaseFragment;

/**
 * 充值记录
 */
public class RecordFragment extends BaseFragment {

    private FragmentRecordBinding mBinding;

    private List<RecordRes> mRecordResList = new ArrayList<>();
    private List<RecordRes> mRecordResFilteredList = new ArrayList<>();

    private RecordAdapter mRecordAdapter;

    private boolean isBox1Checked = true, isBox2Checked = false, isBox3Checked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentRecordBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecordResList.clear();
        mRecordResList = getArguments().getParcelableArrayList(RECORD_LIST_KEY);

        int listSize = mRecordResList.size();
        Toast.makeText(getContext(), "total size: " + listSize, Toast.LENGTH_SHORT).show();

        mRecordAdapter = new RecordAdapter(mRecordResFilteredList);
        mBinding.recordRecycler.setAdapter(mRecordAdapter);

        // COMPLETED Step 2B.1 顶部具有筛选功能（1号、2号、3号），默认勾选 1号小车
        onFilterOptionsSelected(1, true);

        mBinding.recordFilterOptions.checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBox1Checked = isChecked;
            onFilterOptionsSelected(1, isChecked);
        });
        mBinding.recordFilterOptions.checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBox2Checked = isChecked;
            onFilterOptionsSelected(2, isChecked);
        });
        mBinding.recordFilterOptions.checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBox3Checked = isChecked;
            onFilterOptionsSelected(3, isChecked);
        });

    }

    private void onFilterOptionsSelected(int carId, boolean isChecked) {

        // COMPLETED Step 2B.2 筛选功能需要实现单选多选，不能出现三项都不选的情况
        if (!isBox1Checked && !isBox2Checked && !isBox3Checked) {
            Toast.makeText(getContext(), "至少选择一项", Toast.LENGTH_SHORT).show();
            switch (carId) {
                case 1:
                    mBinding.recordFilterOptions.checkBox1.setChecked(true);
                    isBox1Checked = true;
                    break;
                case 2:
                    mBinding.recordFilterOptions.checkBox2.setChecked(true);
                    isBox2Checked = true;
                    break;
                case 3:
                    mBinding.recordFilterOptions.checkBox3.setChecked(true);
                    isBox3Checked = true;
                    break;
            }
            return;
        }


        // COMPLETED Step 2B.3 列表根据筛选的选项，自动显示筛选小车的充值记录（时间、充值金额、充值小车）。
        if (isChecked) {
            for (RecordRes recordRes : mRecordResList) {
                if (recordRes.getCarId() == carId) {
                    mRecordResFilteredList.add(recordRes);
                }
            }
        } else {
            for (int i = mRecordResFilteredList.size() - 1; i >= 0; i--) {
                RecordRes recordRes = mRecordResFilteredList.get(i);
                if (recordRes.getCarId() == carId) {
                    mRecordResFilteredList.remove(recordRes);
                }
            }
        }

        // COMPLETED Step 2B.4 列表按照充值时间显示。
        sortRecordList();

        mRecordAdapter.submitList(mRecordResFilteredList);
    }

    /**
     * 默认按照充值时间降序排列
     */
    private void sortRecordList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mRecordResFilteredList.sort(Comparator.comparing(RecordRes::getTime).reversed());
        }
    }

    public static final String RECORD_LIST_KEY = "recordArrayList";
}
