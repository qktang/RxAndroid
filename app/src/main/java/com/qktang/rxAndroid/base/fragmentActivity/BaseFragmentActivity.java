package com.qktang.rxAndroid.base.fragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by trs on 16-11-4.
 */
public class BaseFragmentActivity extends FragmentActivity {
    protected Context context;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        fragmentManager = getSupportFragmentManager();
    }
}
