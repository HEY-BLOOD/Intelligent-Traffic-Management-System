package io.b4a.itms.ui.park;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.b4a.itms.R;
import io.b4a.itms.data.model.Park;
import io.b4a.itms.databinding.FragmentParkItemBinding;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Park}.
 */
public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {

    private final List<Park> mValues;

    private Context mContext;

    public ParkRecyclerViewAdapter(Context context, List<Park> items) {
        mContext = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentParkItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentParkItemBinding mBinding;

        public ViewHolder(FragmentParkItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Park item) {

            mBinding.parkImage.setImageResource(item.getImageId());
            mBinding.parkName.setText(item.getParkingName());
            mBinding.openType.setText(item.getOpenType());

            String distance = mContext.getString(R.string.park_distance, item.getDistance());
            mBinding.distance.setText(distance);

            String freeTotalNumber = mContext.getString(R.string.park_free_total_number, item.getFreeParkingNumber(), item.getTotalParkingNumber());
            mBinding.parkFreeTotalNumber.setText(freeTotalNumber);

            String address = mContext.getString(R.string.park_address, item.getParkingAddress());
            mBinding.address.setText(address);

            String recharge = mContext.getString(R.string.park_recharge,item.getChargingReference());
            mBinding.chargeReference.setText(recharge);

        }

    }
}