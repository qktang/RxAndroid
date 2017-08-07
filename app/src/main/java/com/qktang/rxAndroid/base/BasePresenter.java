package com.qktang.rxAndroid.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by qktang on 2017/8/1.
 */

public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    public void setModelAndView(M model,V view) {
        mModel = model;
        mView = view;
    }

    public void onDestroy() {
        removeSubscription();
    }

    /**
     * 将Subscription加入记录，最后退出时自动清除Subscription
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.add(s);
        }
    }


    /**
     * 将Subscription从记录中移除，退出activity时不会自动执行unSubscribe
     * @param s
     */
    public void removeSubscription(Subscription s) {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.remove(s);
        }
    }


    public abstract void subscribe();

    public abstract void unSubscribe();






    protected void removeSubscription(){

    }
}
