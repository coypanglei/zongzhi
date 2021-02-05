package com.weique.overhaul.v2.mvp.model.entity;

/**
 * 个人工作日志
 */
public class WorkLogBean {


    /**
     * ElementCount : 4
     * EventCount : 1
     * InspectionInterView : 2
     */

    private String ElementCount;
    private String EventCount;
    private String InspectionInterView;

    public String getElementCount() {
        return ElementCount;
    }

    public void setElementCount(String elementCount) {
        ElementCount = elementCount;
    }

    public String getEventCount() {
        return EventCount;
    }

    public void setEventCount(String eventCount) {
        EventCount = eventCount;
    }

    public String getInspectionInterView() {
        return InspectionInterView;
    }

    public void setInspectionInterView(String inspectionInterView) {
        InspectionInterView = inspectionInterView;
    }
}
