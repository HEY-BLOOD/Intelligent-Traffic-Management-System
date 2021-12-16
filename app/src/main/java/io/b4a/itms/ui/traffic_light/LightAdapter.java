package io.b4a.itms.ui.traffic_light;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.b4a.itms.databinding.TrafficLightItemBinding;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightInfoHolder> {

    private List<LightInfoRes> mDataList;

    public void submitList(List<LightInfoRes> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public LightAdapter(List<LightInfoRes> dataList) {
        submitList(dataList);
    }

    @NonNull
    @Override
    public LightInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TrafficLightItemBinding binding = TrafficLightItemBinding.inflate(layoutInflater, parent, false);
        return new LightInfoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LightInfoHolder holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class LightInfoHolder extends RecyclerView.ViewHolder {
        private TrafficLightItemBinding mBinding;

        public LightInfoHolder(TrafficLightItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindView(LightInfoRes lightInfoRes) {
            if (lightInfoRes.getResult().equals("S")) {

                mBinding.roadId.setText(String.valueOf(lightInfoRes.getRoadId()));
                mBinding.redLight.setText(lightInfoRes.getRedTime());
                mBinding.yellowLight.setText(lightInfoRes.getYellowTime());
                mBinding.greenLight.setText(lightInfoRes.getGreenTime());
            }
        }

    }
}
