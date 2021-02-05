package com.weique.overhaul.v2.mvp.model.entity;

public class AreaDetailsListBean {


    /**
     * Id : bb25830d-f8fe-4b33-ab31-1cc08a5bc106
     * Name : 丰财街道办事处
     * FullPath : 徐州市鼓楼区/丰财街道办事处/
     */

    private String Id;
    private String Name;
    private String FullPath;



    private String CollectionCount;
    private String VisitCount;
    private String InspectionCount;
    private String EventCount;
    private String MediateCount;
    private String Level;

    private String Type;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getCollectionCount() {
        return CollectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        CollectionCount = collectionCount;
    }

    public String getVisitCount() {
        return VisitCount;
    }

    public void setVisitCount(String visitCount) {
        VisitCount = visitCount;
    }

    public String getInspectionCount() {
        return InspectionCount;
    }

    public void setInspectionCount(String inspectionCount) {
        InspectionCount = inspectionCount;
    }

    public String getEventCount() {
        return EventCount;
    }

    public void setEventCount(String eventCount) {
        EventCount = eventCount;
    }

    public String getMediateCount() {
        return MediateCount;
    }

    public void setMediateCount(String mediateCount) {
        MediateCount = mediateCount;
    }

    public AreaDetailsListBean(boolean isClick) {
        this.isClick = isClick;
    }

    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String FullPath) {
        this.FullPath = FullPath;
    }
}
