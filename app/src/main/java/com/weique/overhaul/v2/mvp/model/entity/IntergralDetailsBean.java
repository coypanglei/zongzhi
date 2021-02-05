package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class IntergralDetailsBean {


    /**
     * CurrentMonthSumScore : 11
     * list : [{"EnumAssessmentTypeStr":"签到","AssessmentName":"签到","Score":1,"CreateTime":"2020年10月16日 14:50"},{"EnumAssessmentTypeStr":"后台手动加分","AssessmentName":"后台手动加分","Score":10,"CreateTime":"2020年10月16日 14:49"}]
     */

    private int CurrentMonthSumScore;
    private List<ListBean> list;

    public int getCurrentMonthSumScore() {
        return CurrentMonthSumScore;
    }

    public void setCurrentMonthSumScore(int CurrentMonthSumScore) {
        this.CurrentMonthSumScore = CurrentMonthSumScore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * EnumAssessmentTypeStr : 签到
         * AssessmentName : 签到
         * Score : 1
         * CreateTime : 2020年10月16日 14:50
         */

        private String EnumAssessmentTypeStr;
        private String AssessmentName;
        private int Score;
        private String CreateTime;

        public String getMaxScore() {
            return MaxScore;
        }

        public void setMaxScore(String maxScore) {
            MaxScore = maxScore;
        }

        private String MaxScore;

        public String getEnumAssessmentTypeStr() {
            return EnumAssessmentTypeStr;
        }

        public void setEnumAssessmentTypeStr(String EnumAssessmentTypeStr) {
            this.EnumAssessmentTypeStr = EnumAssessmentTypeStr;
        }

        public String getAssessmentName() {
            return AssessmentName;
        }

        public void setAssessmentName(String AssessmentName) {
            this.AssessmentName = AssessmentName;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
