package com.weique.overhaul.v2.mvp.model.entity;

import android.text.Html;

public class ProjectInfoBean {


    /**
     * MProjectName : null
     * Address : 第三方第三方
     * StartDate : 2020-08-21
     * FinishDate : 2020-08-21
     * PhotoUrls : /img/nonePic.jpg
     * Desc : <p>付水电费第三方</p>
     * TotalInvestment : 1111.0
     * PlotInvestment : 1111.0
     * Invested : 1110.0
     */

    private String PointInJSON;

    public String getPointInJSON() {
        return PointInJSON;
    }

    public void setPointInJSON(String pointInJSON) {
        PointInJSON = pointInJSON;
    }

    private String Name;
    private String Address;
    private String StartDate;
    private String FinishDate;
    private String PhotoUrls;
    private String Desc;
    private double TotalInvestment;
    private double PlotInvestment;
    private double Invested;


    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getFinishDate() {
        return FinishDate;
    }

    public void setFinishDate(String FinishDate) {
        this.FinishDate = FinishDate;
    }

    public String getPhotoUrls() {
        return PhotoUrls;
    }

    public void setPhotoUrls(String PhotoUrls) {
        this.PhotoUrls = PhotoUrls;
    }

    public String getDesc() {
        return Html.fromHtml(Desc).toString();
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public double getTotalInvestment() {
        return TotalInvestment;
    }

    public void setTotalInvestment(double TotalInvestment) {
        this.TotalInvestment = TotalInvestment;
    }

    public double getPlotInvestment() {
        return PlotInvestment;
    }

    public void setPlotInvestment(double PlotInvestment) {
        this.PlotInvestment = PlotInvestment;
    }

    public double getInvested() {
        return Invested;
    }

    public void setInvested(double Invested) {
        this.Invested = Invested;
    }
}
