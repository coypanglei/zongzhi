package com.weique.overhaul.v2.mvp.model.entity;

public class LoginInfoBean {
    String name;
    String password;

    public LoginInfoBean(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return '{' +
                "\"name\"=\"" + name + "\"" +
                ",\"password\"=\"" + password + "\"" +
                '}';
    }
}
