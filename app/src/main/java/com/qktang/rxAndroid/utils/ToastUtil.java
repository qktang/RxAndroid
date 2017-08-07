package com.qktang.rxAndroid.utils;

import android.os.Handler;
import android.widget.Toast;

import com.qktang.rxAndroid.App;

/**
 * toast提示
 * Created by qktang on 2017/7/31.
 */
public class ToastUtil {

    public static Toast mToast;
    private static Handler mHandler;
    private static boolean mCancel = false;

    public static void showShort(int resId) {
        String message = App.getContext().getString(resId);
        showShort(message);
    }

    public static void showShort(String message) {
        showShortToast(message);
    }

    public static void showLong(int resId) {

        String message = App.getContext().getString(resId);
        showLongToast(message);
    }

    public static void showLong(String message) {
        showLongToast(message);
    }

    public static void show10sLong(final String message) {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        showLongToast(message);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLongToast(message);
            }
        }, 3000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLongToast(message);
            }
        }, 6000);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCancel = true;
                mToast.cancel();
            }
        }, 10000);
    }

    private static void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    private static void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    private static void showToast(String message, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
