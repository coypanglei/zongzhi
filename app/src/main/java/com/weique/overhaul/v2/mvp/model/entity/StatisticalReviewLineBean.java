package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class StatisticalReviewLineBean {


    //日期
    private List<String> date;

    //签到数量
    private List<Integer> counts;

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public void setCounts(List<Integer> counts) {
        this.counts = counts;
    }
}
