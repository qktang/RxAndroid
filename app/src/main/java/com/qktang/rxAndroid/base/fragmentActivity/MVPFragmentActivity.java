package com.qktang.rxAndroid.base.fragmentActivity;

import android.os.Bundle;

import com.qktang.rxAndroid.base.BaseModel;
import com.qktang.rxAndroid.base.BasePresenter;
import com.qktang.rxAndroid.base.BaseView;
import com.qktang.rxAndroid.utils.TUtil;

import butterknife.ButterKnife;


/**
 * Created by trs on 16-11-4.
 */
public abstract class MVPFragmentActivity<T extends BasePresenter, M extends BaseModel> extends BaseFragmentActivity {
    public T mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInstanceState(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView)
            mPresenter.setModelAndView(mModel, this);
        init();
    }

    protected void getInstanceState(Bundle savedInstanceState){

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.unSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            mPresenter.onDestroy();
        }
    }

    /**
     * 设置layout id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 代替onCreate
     */
    public abstract void init();
}