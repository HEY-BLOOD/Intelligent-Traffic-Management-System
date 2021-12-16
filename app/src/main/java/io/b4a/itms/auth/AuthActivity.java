package io.b4a.itms.auth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.parse.ParseUser;

import io.b4a.itms.R;
import io.b4a.itms.utils.AuthHelper;

public class AuthActivity extends AppCompatActivity {
    NavController mNavController;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        // Use a user who is already logged in and hasn't logged out yet
        if (ParseUser.getCurrentUser() != null) {
            AuthHelper.launchDashboard(this);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // set Windows Flags to Full Screen
        // using setFlags function
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_auth);
        mNavController = navHostFragment.getNavController();

    }

}