package io.b4a.itms.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import io.b4a.itms.R;

public class DialogUtil {
    private static AlertDialog mLoadingDialog;

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    public static void showLoadingDialog(Context context) {
        if (mLoadingDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
            builder.setView(view);
            builder.setCancelable(false);
            mLoadingDialog = builder.create();

            mLoadingDialog.show();
        }

    }

    /**
     * Method is used to Hide the Custom Progress Dialog.
     */
    public static void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
