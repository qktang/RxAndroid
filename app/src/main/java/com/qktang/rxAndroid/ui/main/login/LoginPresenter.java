package com.qktang.rxAndroid.ui.main.login;

import android.util.Log;

import com.qktang.rxAndroid.AppEn;
import com.qktang.rxAndroid.httpBean.request.LoginReq;
import com.qktang.rxAndroid.httpBean.response.BaseRsp;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by qktang on 2017/8/7.
 */

public class LoginPresenter extends LoginContract.Presenter {

    Subscription loginSubscription;

    @Override
    public void login(LoginReq loginReq) {

        if (loginSubscription != null) {
            removeSubscription(loginSubscription);
        }

        loginSubscription = mModel.login(loginReq)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d(AppEn.TAG, "login--start");
                        mView.showLoading(true);
                    }
                })
                .subscribe(new Subscriber<LoginRsp>() {
                    @Override
                    public void onCompleted() {
                        Log.d(AppEn.TAG, "login--completed");
                        mView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(AppEn.TAG, "login--error");
                        mView.showLoading(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(LoginRsp loginRsp) {
                        mView.showLoading(false);
                        if (loginRsp != null) {
                            if (loginRsp.getResponseNo() == BaseRsp.RES_SUCCESSFUL) {
                                mView.showLoginResult(true, loginRsp);
                                AppEn.hasLogged = true;
                            } else {
                                mView.showLoginResult(false,null);
                            }
                        } else {
                            mView.showLoginResult(false, null);
                        }

                    }

                });
        addSubscription(loginSubscription);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (loginSubscription != null) {
            removeSubscription(loginSubscription);
            loginSubscription = null;
        }
    }
}
