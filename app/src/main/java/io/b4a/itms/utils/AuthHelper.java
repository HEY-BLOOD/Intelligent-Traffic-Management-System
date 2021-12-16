package io.b4a.itms.utils;

import android.content.Context;
import android.content.Intent;

import com.parse.ParseUser;

import io.b4a.itms.MainActivity;
import io.b4a.itms.R;
import io.b4a.itms.auth.AuthActivity;

public class AuthHelper {
    public static void logout(final Context context) {
        ParseUser.logOutInBackground(e -> {
            if (e == null) {
                ToastUtil.showLongToast(context,
                        context.getString(R.string.logout_success_helper_text));
                launchAuth(context);
            } else {
                ToastUtil.showLongToast(context, e.getMessage());
            }
        });
    }

    public static void launchAuth(final Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);
    }

    public static void launchDashboard(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
