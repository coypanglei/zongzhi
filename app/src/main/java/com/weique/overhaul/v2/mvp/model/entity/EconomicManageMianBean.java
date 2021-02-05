package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class EconomicManageMianBean {


    /**
     * summary : {"allList":[{"num":5,"name":"企业总数"},{"num":3,"name":"五上企业总数"},{"num":0,"name":"规上企业总数"},{"num":2,"name":"重点项目数量"},{"num":2,"name":"已启动重点项目"},{"num":0,"name":"正在施工项目"}]}
     * statistic : {"myChart1":[{"value":1,"name":"F 交通运输、仓储和邮政业"},{"value":1,"name":"C 制造业"},{"value":1,"name":"E 建筑业"},{"value":2,"name":"D 电力、热力、燃气及水生产和供应业"},{"value":2,"name":"其他行业"}],"myChart2":[{"name":"港澳台","value":0},{"name":"中外合资合作企业","value":0},{"name":"国有企业","value":5},{"name":"集体企业","value":0}]}
     */

    private SummaryBean summary;
    private StatisticBean statistic;

    public SummaryBean getSummary() {
        return summary;
    }

    public void setSummary(SummaryBean summary) {
        this.summary = summary;
    }

    public StatisticBean getStatistic() {
        return statistic;
    }

    public void setStatistic(StatisticBean statistic) {
        this.statistic = statistic;
    }

    public static class SummaryBean {
        private List<AllListBean> allList;

        public List<AllListBean> getAllList() {
            return allList;
        }

        public void setAllList(List<AllListBean> allList) {
            this.allList = allList;
        }

        public static class AllListBean {
            /**
             * num : 5
             * name : 企业总数
             */

            private int num;
            private String name;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class StatisticBean {
        private List<CommentBean> myChart1;
        private List<CommentBean> myChart2;

        public List<CommentBean> getMyChart1() {
            return myChart1;
        }

        public void setMyChart1(List<CommentBean> myChart1) {
            this.myChart1 = myChart1;
        }

        public List<CommentBean> getMyChart2() {
            return myChart2;
        }

        public void setMyChart2(List<CommentBean> myChart2) {
            this.myChart2 = myChart2;
        }

    }
}
