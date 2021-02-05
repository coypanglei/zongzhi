package com.weique.overhaul.v2.mvp.model.entity;

import java.io.Serializable;

public class GridInformationBean implements Serializable {

    /**
     * EnumCommentLevel : 0
     * Name : 1号网格
     * Code : 0011
     * PinYin : 1HWG
     * PId : fefbc595-b4a8-414e-8429-56382ff7fdf6
     * ChargerId : 00000000-0000-0000-0000-000000000000
     * FullPath : 合肥市瑶海区/牌楼街道/牌楼社区/1号网格
     * PointsInJSON : {"polygoys":[{"path":[{"lng":117.18363958980963,"lat":34.303782141952546},{"  lng":117.1934131537582,"lat":34.3049749429459},{"lng":117.19405993372538,"lat":34.30026327940134},{"ln  g":117.18453789531961,"lat":34.29886165711834}],"styleOptions":{"strokeColor":"#ff6700","fillColor"  :"#ff6700","strokeWeight":1,"strokeOpacity":0.6,"fillOpacity":0.4,"strokeStyle":"solid"}}]}
     * LayerLevel : 0
     * CSS : null
     * Id : 27a8465b-4c18-47e0-b677-d12e6b414a44
     * CreateUserId : a6e16929-efba-43f3-8808-209c87ea7519
     * UpdateUserId : a6e16929-efba-43f3-8808-209c87ea7519
     * CreateTime : /Date(1574818522457)/
     * UpdateTime : /Date(1574818522457)/
     * Memo : null
     * IsDeleted : false
     * DeletedTime : /Date(1574847918027)/
     */

    private int EnumCommunityLevel;
    private String Name;
    private String Code;
    private String PinYin;
    private String PId;
    private String ChargerId;
    private String FullPath;
    private String PointsInJSON;
    private int LayerLevel;
    private String CSS;
    private String Id;
    private String CreateUserId;
    private String UpdateUserId;
    private String CreateTime;
    private String UpdateTime;
    private String Memo;
    private boolean IsDeleted;
    private String DeletedTime;

    public int getEnumCommunityLevel() {
        return EnumCommunityLevel;
    }

    public void setEnumCommentLevel(int EnumCommunityLevel) {
        this.EnumCommunityLevel = EnumCommunityLevel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getChargerId() {
        return ChargerId;
    }

    public void setChargerId(String ChargerId) {
        this.ChargerId = ChargerId;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String FullPath) {
        this.FullPath = FullPath;
    }

    public String getPointsInJSON() {
        return PointsInJSON;
    }

    public void setPointsInJSON(String PointsInJSON) {
        this.PointsInJSON = PointsInJSON;
    }

    public int getLayerLevel() {
        return LayerLevel;
    }

    public void setLayerLevel(int LayerLevel) {
        this.LayerLevel = LayerLevel;
    }

    public String getCSS() {
        return CSS;
    }

    public void setCSS(String CSS) {
        this.CSS = CSS;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getUpdateUserId() {
        return UpdateUserId;
    }

    public void setUpdateUserId(String UpdateUserId) {
        this.UpdateUserId = UpdateUserId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getDeletedTime() {
        return DeletedTime;
    }

    public void setDeletedTime(String DeletedTime) {
        this.DeletedTime = DeletedTime;
    }
}
