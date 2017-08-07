package com.qktang.rxAndroid.api.service;

import com.qktang.rxAndroid.httpBean.response.LoginRsp;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qktang on 2017/7/31.
 */

public interface LoginService {

    String BASE_URL = "http://dreamtest.strongwind.cn:7280";

    @Headers("Cache-Control: public, max-age=0")
    @POST("/v1/auth/login")
    Observable<LoginRsp> login(@Query("userType") String role,
                               @Query("userAccount") String userName,
                               @Query("password") String password,
                               @Query("platform") String platform);
}
