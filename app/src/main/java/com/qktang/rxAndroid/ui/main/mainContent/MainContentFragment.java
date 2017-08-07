package com.qktang.rxAndroid.ui.main.mainContent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qktang.rxAndroid.R;
import com.qktang.rxAndroid.base.fragment.BaseFragment;
import com.qktang.rxAndroid.httpBean.response.LoginRsp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class MainContentFragment extends BaseFragment {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.school)
    TextView school;
    @BindView(R.id.class_name)
    TextView className;

    private LoginRsp userInfo;

    public MainContentFragment() {
        // Required empty public constructor
    }

    public void setUserInfo(LoginRsp loginRsp) {
        userInfo = loginRsp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (userInfo != null && userInfo.getUserInfo() != null) {
            name.setText("姓名：" + userInfo.getUserInfo().getStudentName());
            gender.setText("性别：" + userInfo.getUserInfo().getUserGender());
            account.setText("学号：" + userInfo.getUserInfo().getUserAccount());
            school.setText("学校：" + userInfo.getUserInfo().getUserSchoolName());
            className.setText("班级：" + userInfo.getUserInfo().getUserClassName());
        }

    }
}
