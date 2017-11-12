package com.wgl.exam.bean;

/**
 * Created by wuguilin on 12/14/2016.
 */
public class ReturnWithData<T> extends ReturnWithoutData {
    private T data;
    public ReturnWithData(String msg, String code,T data) {
        super(msg, code);
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
