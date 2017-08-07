package com.qktang.rxAndroid.httpBean.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qktang on 2017/8/1.
 */

public class BaseRsp {

    /**一系列请求业务码*/
    public static final int RES_SUCCESSFUL = 10000; //操作成功


    /**
     * 业务错误码
     */
    @SerializedName("F_responseNo")
    int responseNo;

    /**
     * 业务错误描述
     */
    @SerializedName("F_responseMsg")
    String responseMsg;

    public int getResponseNo() {
        return responseNo;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    @Override
    public String toString() {
        return "BaseRsp{" +
                "responseNo=" + responseNo +
                ", responseMsg='" + responseMsg + '\'' +
                '}';
    }
}

