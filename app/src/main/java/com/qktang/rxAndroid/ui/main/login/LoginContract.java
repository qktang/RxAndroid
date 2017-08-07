package com.qktang.rxAndroid.ui.main.login;

import com.qktang.rxAndroid.base.BaseModel;
import com.qktang.rxAndroid.base.BasePresenter;
import com.qktang.rxAndroid.base.BaseView;
import com.qktang.rxAndroid.httpBean.request.LoginReq;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;

import rx.Observable;

/**
 * Created by qktang on 2017/8/7.
 */

public interface LoginContract {

    interface Model extends BaseModel {

        Observable<LoginRsp> login(LoginReq loginReq);

    }

    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void showLoginResult(boolean isSuccess, LoginRsp loginRsp);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void login(LoginReq loginReq);
    }

}
