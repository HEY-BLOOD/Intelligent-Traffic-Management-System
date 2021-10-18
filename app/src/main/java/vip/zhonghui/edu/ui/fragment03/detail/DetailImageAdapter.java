package vip.zhonghui.edu.ui.fragment03.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vip.zhonghui.edu.databinding.DataDetailItemBinding;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.DataDetailViewHolder> {


    private List<ImageBean> mDataList = new ArrayList<>();

    private OnDetailItemClickListener mOnClickListener;

    public DetailImageAdapter(List<ImageBean> dataList, OnDetailItemClickListener listener) {
        mDataList = dataList;
        notifyDataSetChanged();
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public DataDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DataDetailItemBinding binding = DataDetailItemBinding.inflate(layoutInflater, parent, false);
        return new DataDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataDetailViewHolder holder, int position) {

        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class DataDetailViewHolder extends RecyclerView.ViewHolder {
        DataDetailItemBinding mBinding = null;

        public DataDetailViewHolder(@NonNull DataDetailItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(ImageBean imageBean) {
            mBinding.title.setText("违章图片" + (getAdapterPosition() + 1) + "：");
            mBinding.image.setImageResource(imageBean.imageResId);
            mBinding.info.setText(imageBean.info);

            mBinding.image.setOnClickListener(v -> mOnClickListener.onItemClick(imageBean.imageResId));
        }
    }
}
