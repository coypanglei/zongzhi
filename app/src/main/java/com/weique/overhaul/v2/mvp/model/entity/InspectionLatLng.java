package com.weique.overhaul.v2.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class InspectionLatLng {

    private int pageCount;
    private boolean IsComplete;

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }

    private List<InspectionLatLng.ListBean> inspectionWithEvents;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getInspectionWithEvents() {
        return inspectionWithEvents;
    }

    public void setInspectionWithEvents(List<ListBean> inspectionWithEvents) {
        this.inspectionWithEvents = inspectionWithEvents;
    }

    public static class ListBean implements Serializable {
        private String Id;
        private String PointInJson;
        private String IRImageUrlsInJson;
        private String Memo;
        private String Time;

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getPointInJson() {
            return PointInJson;
        }

        public void setPointInJson(String pointInJson) {
            PointInJson = pointInJson;
        }

        public String getIRImageUrlsInJson() {
            return IRImageUrlsInJson;
        }

        public void setIRImageUrlsInJson(String IRImageUrlsInJson) {
            this.IRImageUrlsInJson = IRImageUrlsInJson;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String memo) {
            Memo = memo;
        }
    }


}
