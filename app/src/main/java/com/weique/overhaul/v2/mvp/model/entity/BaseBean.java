package com.weique.overhaul.v2.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author GK
 */
public class BaseBean<T> {
    @SerializedName(value = "message", alternate = {"Message"})
    String message;
    @SerializedName(value = "code", alternate = {"Code", "ResponseCode"})
    int code;
    @SerializedName(value = "data", alternate = {"Data"})
    T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
