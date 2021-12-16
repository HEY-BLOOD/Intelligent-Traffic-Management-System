package io.b4a.itms.ui.learn;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.b4a.itms.R;
import io.b4a.itms.databinding.ActivityLearnBinding;

public class LearnActivity extends AppCompatActivity {

    private final static String TAG = LearnActivity.class.getSimpleName();

    private ActivityLearnBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLearnBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            Log.d(TAG, "有 action bar");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_videos, R.id.navigation_images)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_data_detail);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, navController);
    }
}