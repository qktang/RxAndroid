package com.qktang.rxAndroid.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 *
 * Created by qktang on 2017/8/7.
 */

public class ProgressDlgUtil {

    private static ProgressDialog progressDialog;

    private static String tag = null;

    public static void showProgressDlg(Activity activity, String title) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    public static void setProgressDialog(ProgressDialog progressDialog) {
        ProgressDlgUtil.progressDialog = progressDialog;
    }

    public static void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        if (progressDialog != null) {
            progressDialog.setOnDismissListener(dismissListener);
        }
    }

    public static void setCancelable(boolean isCancelable) {
        if (progressDialog == null)
            return;
        progressDialog.setCancelable(isCancelable);
    }

    //tag用于区分是哪个请求
    public static void setTag (String tagName) {
        tag = tagName;
    }

    public static String getTag() {
        return tag;
    }

    public static void dismissDlg() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    public static boolean isShowing() {
        if (progressDialog == null)
            return false;
        return progressDialog.isShowing();
    }
}
