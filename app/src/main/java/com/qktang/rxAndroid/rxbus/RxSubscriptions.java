package com.qktang.rxAndroid.rxbus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 管理 CompositeSubscription,MVP结构中Presenter已经又这个功能，所以MVP的结构不需要使用这个
 * Created by qktang on 2017/8/7.
 */
public class RxSubscriptions {

    private static CompositeSubscription mSubscriptions = new CompositeSubscription();

    public static boolean isUnsubscribed() {
        return mSubscriptions.isUnsubscribed();
    }

    public static void add(Subscription s) {
        if (s != null) {
            mSubscriptions.add(s);
        }
    }

    public static void remove(Subscription s) {
        if (s != null) {
            mSubscriptions.remove(s);
        }
    }

    public static void clear() {
        mSubscriptions.clear();
    }

    public static void unsubscribe() {
        mSubscriptions.unsubscribe();
    }

    public static boolean hasSubscriptions() {
        return mSubscriptions.hasSubscriptions();
    }
}