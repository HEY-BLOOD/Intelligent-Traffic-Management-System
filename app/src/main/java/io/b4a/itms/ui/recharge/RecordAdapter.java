package io.b4a.itms.ui.recharge;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.b4a.itms.databinding.RecordItemBinding;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordItemHolder> {

    private List<RecordRes> mDataList;

    public RecordAdapter(List<RecordRes> dataList) {
        submitList(dataList);
    }

    public void submitList(List<RecordRes> recordResList) {
        mDataList = recordResList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecordItemBinding recordItemBinding = RecordItemBinding.inflate(layoutInflater, parent, false);

        return new RecordItemHolder(recordItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordItemHolder holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class RecordItemHolder extends RecyclerView.ViewHolder {

        private RecordItemBinding mBinding;

        public RecordItemHolder(@NonNull RecordItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindView(RecordRes recordRes) {
            mBinding.cardIdValue.setText(String.valueOf(recordRes.getCarId()));
            mBinding.costValue.setText(String.valueOf(recordRes.getCost()));
            mBinding.dateTimeValue.setText(recordRes.getFormattedDateTime());
        }
    }
}
