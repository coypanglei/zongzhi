package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class DynamicFormOrmBean {

    /**
     * 扫描
     */
    public static final int SCAN_TYPE = 0;
    /**
     * 合并表
     */
    public static final int MERGE_TYPE = 1;

    /**
     * sourceTypeFiredFieldName : SingleLine9
     * sourceTypeId : 6f056441-d710-4083-b217-cd01e1e4 5502
     * targetTypeId : 1267dbb8-b8fd-42ea-8a90-b344d57ebe71
     * mapList : [{"sourceid":"name","targeti d":"name"},{"sourceid":"SingleLine9","targetid":"SingleLine18"},{"sourceid":"SingleLine10","ta rgetid":"SingleLine23"},{"sourceid":"addr","targetid":"memo"}]
     */

    private String sourceTypeFiredFieldName;
    private String sourceTypeId;
    private String targetTypeId;
    private List<MapListBean> mapList;

    public String getSourceTypeFiredFieldName() {
        return sourceTypeFiredFieldName;
    }

    public void setSourceTypeFiredFieldName(String sourceTypeFiredFieldName) {
        this.sourceTypeFiredFieldName = sourceTypeFiredFieldName;
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public List<MapListBean> getMapList() {
        return mapList;
    }

    public void setMapList(List<MapListBean> mapList) {
        this.mapList = mapList;
    }

    public static class MapListBean {
        /**
         * sourceid : name
         * targeti d : name
         * targetid : SingleLine18
         * ta rgetid : SingleLine23
         */

        private String sourceid;
        private String targetid;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSourceid() {
            return sourceid;
        }

        public void setSourceid(String sourceid) {
            this.sourceid = sourceid;
        }

        public String getTargetid() {
            return targetid;
        }

        public void setTargetid(String targetid) {
            this.targetid = targetid;
        }
    }
}
