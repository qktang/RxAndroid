package com.qktang.rxAndroid.ui.main.login;

import com.qktang.rxAndroid.api.ApiFactory;
import com.qktang.rxAndroid.api.service.LoginService;
import com.qktang.rxAndroid.httpBean.request.LoginReq;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;
import com.qktang.rxAndroid.utils.RxUtil;

import rx.Observable;

/**
 * Created by qktang on 2017/8/7.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginRsp> login(LoginReq loginReq) {
        return ApiFactory.getService(LoginService.class)
                .login("1", loginReq.getUserName(), loginReq.getPassword(), "1")
                .compose(RxUtil.<LoginRsp>io2Main());
    }

}
