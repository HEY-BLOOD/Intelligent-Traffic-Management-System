package io.b4a.itms.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.b4a.itms.R;
import io.b4a.itms.base.BaseFragment;
import io.b4a.itms.data.parse_model.ParseUserExtensions;
import io.b4a.itms.databinding.FragmentDashboardBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentDashboardBinding mBinding;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        mBinding.welcomeUser.setText(getString(R.string.welcome_username, mCurrentUser.getUsername()));
        mBinding.emailAddress.setText(mCurrentUser.getEmail());

        // loading user avatar
        ParseUserExtensions.loadAvatar(getContext(), mCurrentUser, mBinding.avatar);

//        mBinding.todoList.setOnClickListener(v -> ToastUtil.showShortToast(getContext(), "TODO 待办"));
        mBinding.profile.setOnClickListener(v -> navigateToProfile(mCurrentUser));

        mBinding.funcRoadStatus.setOnClickListener(v -> navigateToRoadStatus());
        mBinding.funcRecharge.setOnClickListener(v -> navigateToRecharge());
        mBinding.funcPark.setOnClickListener(v -> navigateToPark());

        mBinding.funcLearn.setOnClickListener(v -> navigateToLearn());
        mBinding.funcTrafficLight.setOnClickListener(v -> navigateToTrafficLight());

        mBinding.funcHelp.setOnClickListener(v -> navigateToHelp());

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_power:
                onClickPowerAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}