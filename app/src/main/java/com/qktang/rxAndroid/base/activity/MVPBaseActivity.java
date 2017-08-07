package com.qktang.rxAndroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qktang.rxAndroid.base.BaseModel;
import com.qktang.rxAndroid.base.BasePresenter;
import com.qktang.rxAndroid.base.BaseView;
import com.qktang.rxAndroid.utils.TUtil;

import butterknife.ButterKnife;

/**
 * Created by qktang on 2017/8/1.
 */

public abstract class MVPBaseActivity<T extends BasePresenter, M extends BaseModel> extends BaseActivity {

    public T mPresenter;
    public M mModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setModelAndView(mModel, this);
        }

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            mPresenter.onDestroy();
        }
    }

    /**代替onCreate*/
    protected abstract void init();

    /** 设置layout id*/
    protected abstract int getLayoutId();
}
