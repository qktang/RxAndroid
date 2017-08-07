package com.qktang.rxAndroid;

import android.app.Application;
import android.content.Context;

import com.qktang.rxAndroid.api.ApiFactory;

/**
 *
 * Created by qktang on 2017/8/1.
 */

public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        ApiFactory.setAppContext(mApp);

    }

    public static Context getContext() {
        return mApp;
    }
}
