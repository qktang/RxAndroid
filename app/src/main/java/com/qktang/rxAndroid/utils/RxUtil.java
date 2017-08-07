package com.qktang.rxAndroid.utils;


import android.content.Intent;
import android.util.Log;

import com.qktang.rxAndroid.App;
import com.qktang.rxAndroid.httpBean.response.BaseRsp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qktang on 2017/8/1.
 */

public class RxUtil {

    /**
     *  subscribe 在io线程，observe 在主线程
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> io2Main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 返回状态码请求，以便统一处理
     * @param <T>
     * @return
     */
    public static <T extends BaseRsp>Observable.Transformer<BaseRsp,T> handleWebStatus() {
        return new Observable.Transformer<BaseRsp, T>() {
            @Override
            public Observable<T> call(Observable<BaseRsp> baseRspObservable) {
                return (Observable<T>) baseRspObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Action1<BaseRsp>() {
                            @Override
                            public void call(BaseRsp baseRsp) {
                                Log.d("RxUtil", "handleWebStatus:" + baseRsp.getResponseNo());
                                Intent intent = new Intent();
                                intent.setAction("com.qktang.rxAndroid.WEB_STATUS");
                                intent.putExtra("WEB_STATUS", baseRsp.getResponseNo());
                                App.getContext().sendBroadcast(intent);
                            }
                        });
            }

        };
    }

}
