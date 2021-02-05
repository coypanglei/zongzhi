package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class collectBean {


    /**
     * 标签 : 所属街道
     * 类型 : System.Guid
     * 字段名称 : DepartmentId
     * 是否必填 : 否
     * 字符长度要求 : 无
     * 枚举列表 : null
     */

    private String 标签;
    private String 类型;
    private String 字段名称;
    private String 是否必填;
    private String 字符长度要求;
    private List<String> 枚举列表;

    public List<String> get枚举列表() {
        return 枚举列表;
    }

    public void set枚举列表(List<String> 枚举列表) {
        this.枚举列表 = 枚举列表;
    }

    public String get标签() {
        return 标签;
    }

    public void set标签(String 标签) {
        this.标签 = 标签;
    }

    public String get类型() {
        return 类型;
    }

    public void set类型(String 类型) {
        this.类型 = 类型;
    }

    public String get字段名称() {
        return 字段名称;
    }

    public void set字段名称(String 字段名称) {
        this.字段名称 = 字段名称;
    }

    public String get是否必填() {
        return 是否必填;
    }

    public void set是否必填(String 是否必填) {
        this.是否必填 = 是否必填;
    }

    public String get字符长度要求() {
        return 字符长度要求;
    }

    public void set字符长度要求(String 字符长度要求) {
        this.字符长度要求 = 字符长度要求;
    }


}
