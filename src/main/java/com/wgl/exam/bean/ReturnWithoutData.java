package com.wgl.exam.bean;

/**
 * Created by wuguilin on 12/14/2016.
 */
public class ReturnWithoutData {
    String msg,code;

    public ReturnWithoutData(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }



    public void setMsg(String msg) {
        this.msg = msg;
    }


}
