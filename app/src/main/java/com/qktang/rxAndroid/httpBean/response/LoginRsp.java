package com.qktang.rxAndroid.httpBean.response;

import com.google.gson.annotations.SerializedName;
import com.qktang.rxAndroid.httpBean.response.BaseRsp;

/**
 * Created by qktang on 2017/7/31.
 */

public class LoginRsp extends BaseRsp {


    @SerializedName("F_data")
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public String toString() {
        return "LoginRsp{" +
                "userInfo=" + userInfo +
                '}';
    }

    public static class UserInfo{

        @SerializedName("sessionName")
        private String sessionName;
        @SerializedName("sessionId")
        private String sessionId;
        @SerializedName("userId")
        private String studentId;
        @SerializedName("userAccount")
        private String userAccount;
        @SerializedName("userNo")
        private String userNo;
        @SerializedName("userName")
        private String studentName;
        @SerializedName("userAvatar")
        private String studentAvatar;
        @SerializedName("userSchoolName")
        private String userSchoolName;
        @SerializedName("userClassName")
        private String userClassName;
        @SerializedName("userGender")
        private String userGender;


        public String getSessionName() {
            return sessionName;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public String getUserNo() {
            return userNo;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getStudentAvatar() {
            return studentAvatar;
        }

        public String getUserSchoolName() {
            return userSchoolName;
        }

        public String getUserClassName() {
            return userClassName;
        }

        public String getUserGender() {
            return userGender;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "sessionName='" + sessionName + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", studentId='" + studentId + '\'' +
                    ", userAccount='" + userAccount + '\'' +
                    ", userNo='" + userNo + '\'' +
                    ", studentName='" + studentName + '\'' +
                    ", studentAvatar='" + studentAvatar + '\'' +
                    ", userSchoolName='" + userSchoolName + '\'' +
                    ", userClassName='" + userClassName + '\'' +
                    ", userGender='" + userGender + '\'' +
                    '}';
        }
    }


}
