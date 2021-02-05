package com.weique.overhaul.v2.mvp.model.entity;

/**
 * @author Administrator
 */
public class TimeSelectBean {
    public TimeSelectBean(String precision, int year, int month, int day) {
        this.precision = precision;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * 精度
     */
    private String precision;
    /**
     * 提前或者延迟的年
     */
    private int year;

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    /**
     * 提前或者延迟的月
     */
    private int month;

    /**
     * 提前或者延迟的日
     */
    private int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
