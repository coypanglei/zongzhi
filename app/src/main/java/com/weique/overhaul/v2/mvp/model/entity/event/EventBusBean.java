package com.weique.overhaul.v2.mvp.model.entity.event;

public class EventBusBean<T> {
    public EventBusBean() {
    }

    public EventBusBean(T data) {
        this.data = data;
    }

    public EventBusBean(int code) {
        this.code = code;
    }

    public EventBusBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public EventBusBean(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public EventBusBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    int code;
    String message;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
