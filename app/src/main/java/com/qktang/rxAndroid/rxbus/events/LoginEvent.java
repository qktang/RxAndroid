package com.qktang.rxAndroid.rxbus.events;

import com.qktang.rxAndroid.httpBean.response.LoginRsp;

/**
 * 登录结果事件
 * Created by qktang on 2017/8/7.
 */
public class LoginEvent {


    private LoginRsp loginRsp;

    public LoginEvent(LoginRsp loginRsp) {
        this.loginRsp = loginRsp;
    }

    public LoginRsp getLoginRsp() {
        return loginRsp;
    }
}
