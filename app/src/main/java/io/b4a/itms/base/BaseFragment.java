package io.b4a.itms.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.parse.ParseUser;

import java.util.concurrent.TimeUnit;

import io.b4a.itms.ui.chat.ChatFragmentDirections;
import io.b4a.itms.ui.learn.LearnActivity;
import io.b4a.itms.ui.park.ParkFragmentDirections;
import io.b4a.itms.ui.profile.ProfileActivity;
import io.b4a.itms.ui.recharge.RechargeFragmentDirections;
import io.b4a.itms.ui.road_status.RoadStatusFragmentDirections;
import io.b4a.itms.ui.traffic_light.TrafficLightFragmentDirections;
import io.b4a.itms.utils.AuthHelper;
import okhttp3.OkHttpClient;

public class BaseFragment extends Fragment {

    public NavController mNavController;

    protected ParseUser mCurrentUser = ParseUser.getCurrentUser();

    public OkHttpClient mHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment.findNavController(this);
    }


    public void onClickPowerAction() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("退出应用");
        builder.setMessage("确定要退出吗？如果退出，将不会保留账户的任何信息。");
        // add the buttons
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AuthHelper.logout(getContext());
                getActivity().finish();
            }
        });

        builder.setNegativeButton("取消", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void navigateToRoadStatus() {
        NavDirections directions = RoadStatusFragmentDirections.actionGlobalRoadStatusFragment();
         NavHostFragment.findNavController(this).navigate(directions);
    }

    public void navigateToRecharge() {
        NavDirections directions = RechargeFragmentDirections.actionGlobalRechargeFragment();
         NavHostFragment.findNavController(this).navigate(directions);
    }

    /**
     * Help is chat fragment
     */
    public void navigateToHelp() {
        NavDirections directions = ChatFragmentDirections.actionGlobalChatFragment();
         NavHostFragment.findNavController(this).navigate(directions);
    }

    public void navigateToTrafficLight() {

        NavDirections directions = TrafficLightFragmentDirections.actionGlobalTrafficLightFragment();
         NavHostFragment.findNavController(this).navigate(directions);
    }

    public void navigateToPark() {
        NavDirections directions = ParkFragmentDirections.actionGlobalParkFragment();
         NavHostFragment.findNavController(this).navigate(directions);
    }

    public void navigateToProfile(ParseUser sender) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_OBJECT_ID, sender.getObjectId());
        startActivity(intent);
    }

    public void navigateToLearn() {
        Intent intent = new Intent(getContext(), LearnActivity.class);
        startActivity(intent);
    }
}


