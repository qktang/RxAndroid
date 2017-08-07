package com.qktang.rxAndroid.ui.main.login;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.qktang.rxAndroid.AppEn;
import com.qktang.rxAndroid.R;
import com.qktang.rxAndroid.base.fragment.MVPBaseFragment;
import com.qktang.rxAndroid.httpBean.request.LoginReq;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;
import com.qktang.rxAndroid.rxbus.RxBus;
import com.qktang.rxAndroid.rxbus.events.LoginEvent;
import com.qktang.rxAndroid.utils.NetworkUtil;
import com.qktang.rxAndroid.utils.ProgressDlgUtil;
import com.qktang.rxAndroid.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends MVPBaseFragment<LoginPresenter, LoginModel> implements LoginContract.View {

    @BindView(R.id.login_name)
    EditText mLoginName;
    @BindView(R.id.login_pwd)
    EditText mLoginPwd;
    @BindView(R.id.login_btn)
    TextView mLoginBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    protected void init() {

        RxView.clicks(mLoginBtn)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        doLogin();
                    }
                });

    }

    /**
     * 登录的测试账号：14200000030 123456
     * 该账号随时可能被清掉
     *
     */
    private void doLogin() {
        String name = mLoginName.getText().toString();
        String password = mLoginPwd.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("用户名不能为空！");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("密码不能为空！");
            return;
        }
        if (!NetworkUtil.isNetworkConnecting(getActivity())) {
            ToastUtil.showShort("网络不可用，请检查网络是否连接！");
            return;
        }
        LoginReq loginReq = new LoginReq();
        loginReq.setUserName(name);
        loginReq.setPassword(password);
        mPresenter.login(loginReq);
    }


    @Override
    public void showLoading(boolean isLoading) {
        Log.d(AppEn.TAG, "showLoading -- isLoading=" + isLoading);
        if (isLoading && getActivity() != null) {
            ProgressDlgUtil.showProgressDlg(getActivity(), "拼命登录中...");
        } else {
            ProgressDlgUtil.dismissDlg();
        }

    }

    @Override
    public void showLoginResult(boolean isSuccess, LoginRsp loginRsp) {
        if (isSuccess) {
            Log.d(AppEn.TAG, "登录成功！");
            RxBus.getDefault().postSticky(new LoginEvent(loginRsp));
        } else {
            ToastUtil.showLong("登录失败啦，请重试！");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ProgressDlgUtil.dismissDlg();
    }
}
