package com.weique.overhaul.v2.mvp.model.entity;

public class QuestionStudyItemBean {


    /**
     * Title : 一元一次方程
     * SubjectCoverImgUrl : /Uploads/CustomerData/图片/file_1573271953000637088975532676484.png
     * Content : <p>1+1=2</p>
     * EnumContentTypeValue : 0
     * VideoLink : null
     * Period : 60
     * Point : 5
     * ExamDetailCount : 5
     */

    private String Title;
    private String SubjectCoverImgUrl;
    private String Content;
    private int EnumContentTypeValue;
    private String  VideoLink;
    private int Period;
    private int Point;
    private int ExamDetailCount;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSubjectCoverImgUrl() {
        return SubjectCoverImgUrl;
    }

    public void setSubjectCoverImgUrl(String SubjectCoverImgUrl) {
        this.SubjectCoverImgUrl = SubjectCoverImgUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getEnumContentTypeValue() {
        return EnumContentTypeValue;
    }

    public void setEnumContentTypeValue(int EnumContentTypeValue) {
        this.EnumContentTypeValue = EnumContentTypeValue;
    }

    public String  getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String VideoLink) {
        this.VideoLink = VideoLink;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int Period) {
        this.Period = Period;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public int getExamDetailCount() {
        return ExamDetailCount;
    }

    public void setExamDetailCount(int ExamDetailCount) {
        this.ExamDetailCount = ExamDetailCount;
    }
}
