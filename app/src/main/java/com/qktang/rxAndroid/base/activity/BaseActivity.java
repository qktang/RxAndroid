package com.qktang.rxAndroid.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Activity 基类
 * Created by qktang on 2017/8/1.
 */
public class BaseActivity extends Activity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        Log.d("RxAndroid", "finish");
    }
}
