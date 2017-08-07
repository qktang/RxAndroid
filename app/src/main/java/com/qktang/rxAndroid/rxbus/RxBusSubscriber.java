package com.qktang.rxAndroid.rxbus;

import rx.Subscriber;

/**
 * RxBus使用的Subscriber，onNext经try catch之后给onEvent
 * 另外两个不需要处理了
 * Created by qktang on 2017/8/7.
 */

public abstract class RxBusSubscriber<T> extends Subscriber<T> {



    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

    }

    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onEvent(T t);
}
