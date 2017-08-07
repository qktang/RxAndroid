package com.qktang.rxAndroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qktang.rxAndroid.base.BaseModel;
import com.qktang.rxAndroid.base.BasePresenter;
import com.qktang.rxAndroid.base.BaseView;
import com.qktang.rxAndroid.utils.TUtil;

import butterknife.ButterKnife;

/**
 * Created by qktang on 2017/8/1.
 */

public abstract class MVPBaseFragment<T extends BasePresenter, M extends BaseModel> extends BaseFragment {

    protected T mPresenter;
    protected M mModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setModelAndView(mModel, this);
        }

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            mPresenter.onDestroy();
        }
    }

    /**
     * 代替onCreate
     */
    protected abstract void init();

    /**
     * 设置layout id
     */
    protected abstract int getLayoutId();
}
