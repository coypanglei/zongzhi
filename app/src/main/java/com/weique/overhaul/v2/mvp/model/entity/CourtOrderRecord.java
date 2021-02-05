package com.weique.overhaul.v2.mvp.model.entity;

public class CourtOrderRecord {

    private String CourtOrderID;
    private String Content;
    private String ImgsInJson;

    public String getCourtOrderID() {
        return CourtOrderID;
    }

    public void setCourtOrderID(String courtOrderID) {
        CourtOrderID = courtOrderID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImgsInJson() {
        return ImgsInJson;
    }

    public void setImgsInJson(String imgsInJson) {
        ImgsInJson = imgsInJson;
    }
}
