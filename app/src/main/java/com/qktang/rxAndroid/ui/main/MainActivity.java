package com.qktang.rxAndroid.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.qktang.rxAndroid.AppEn;
import com.qktang.rxAndroid.R;
import com.qktang.rxAndroid.base.fragment.BaseFragment;
import com.qktang.rxAndroid.base.fragmentActivity.BaseFragmentActivity;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;
import com.qktang.rxAndroid.rxbus.RxBus;
import com.qktang.rxAndroid.rxbus.RxBusSubscriber;
import com.qktang.rxAndroid.rxbus.RxSubscriptions;
import com.qktang.rxAndroid.rxbus.events.LoginEvent;
import com.qktang.rxAndroid.ui.main.login.LoginFragment;
import com.qktang.rxAndroid.ui.main.mainContent.MainContentFragment;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends BaseFragmentActivity {

    private Subscription loginEvent;
    private LoginRsp userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerEvents();
        if (AppEn.hasLogged) {
            loadMainContentFragment();
        } else {
            loadLoginFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginEvent != null) {
            RxSubscriptions.remove(loginEvent);
        }
    }

    private void registerEvents() {
        if (loginEvent != null) {
            RxSubscriptions.remove(loginEvent);
        }

        loginEvent = RxBus.getDefault()
                .toObservableSticky(LoginEvent.class)
                .map(new Func1<LoginEvent, Object>() {
                    @Override
                    public Object call(LoginEvent loginEvent) {
                        return loginEvent;
                    }
                })
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void onEvent(Object o) {
                        if (o != null) {
                            LoginEvent loginEvent = (LoginEvent) o;
                            userInfo = loginEvent.getLoginRsp();
                            Log.d(AppEn.TAG, "----loginEvent:" + loginEvent.getLoginRsp());
                            loadMainContentFragment();
                        }
                    }
                });
        RxSubscriptions.add(loginEvent);
    }

    private void loadLoginFragment() {
        if (!(getUIFragment() instanceof LoginFragment)) {
            switchFragment(new LoginFragment());
        }

    }

    private void loadMainContentFragment() {
        if (!(getUIFragment() instanceof MainContentFragment) && userInfo != null) {
            MainContentFragment mainContentFragment = new MainContentFragment();
            mainContentFragment.setUserInfo(userInfo);
            switchFragment(mainContentFragment);
        }

    }

    private void switchFragment(BaseFragment baseFragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, baseFragment)
                .commitAllowingStateLoss();
    }

    private BaseFragment getUIFragment() {
        return (BaseFragment) fragmentManager.findFragmentById(R.id.main_container);
    }
}
