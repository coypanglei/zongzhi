package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GreatKing
 */
public class ContradictionAddSearchItemBean {

    /**
     * pageCount : 39
     * department : [{"Id":"b7101d02-f429-4019-8e08-002a160dd09d","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"A11栋","Code":"320302001012001003014","GisLocations":"","FullPath":"徐州人家生态居住区/A11栋"},{"Id":"be995fb3-f3e3-46a8-a900-01393266e095","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"2单元","Code":"320302001012001003022053","GisLocations":"","FullPath":"徐州人家生态居住区/A12栋/2单元"},{"Id":"d7706177-0aa3-4738-9ac3-0151f27e6a94","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"3单元","Code":"320302001012001003009017","GisLocations":"","FullPath":"徐州人家生态居住区/A6栋/3单元"},{"Id":"216a8971-1fc6-429e-a1e6-01bed4d3d747","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"202号","Code":"320302001012001003005009047","GisLocations":"","FullPath":"徐州人家生态居住区/A1栋/3单元/202号"},{"Id":"79c47a3c-acef-4ccf-834c-029de23fe738","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"501号","Code":"320302001012001005015034119","GisLocations":"","FullPath":"香山庭院/1栋/2单元/501号"},{"Id":"ecbd279f-0ab0-48ce-9ac0-02fe50778df9","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"3单元","Code":"320302001012001007021050","GisLocations":"","FullPath":"锦绣滨湖/2栋/3单元"},{"Id":"88dcb485-58c2-417c-a91a-0328ffdffea5","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"2单元","Code":"320302001012001003012026","GisLocations":"","FullPath":"徐州人家生态居住区/A9栋/2单元"},{"Id":"fbf44292-d1c4-49ee-a2d6-03ef181c0bb3","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"3栋","Code":"320302001012001005017","GisLocations":"","FullPath":"香山庭院/3栋"},{"Id":"406ea11c-1f1c-44d6-b32b-049e257b7679","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"1单元","Code":"320302001012001001002004","GisLocations":"","FullPath":"白云路4号/4栋/1单元"},{"Id":"81847621-4844-4bfd-83d5-04a8f96e9cfd","DepartmentId":"49f7822c-0ec3-47b4-980d-d6c4a1dad689","Name":"3单元","Code":"320302001012001003014051","GisLocations":"","FullPath":"徐州人家生态居住区/A11栋/3单元"}]
     */

    private int pageCount;
    private List<DepartmentBean> department;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<DepartmentBean> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentBean> department) {
        this.department = department;
    }

    public static class DepartmentBean {
        /**
         * Id : b7101d02-f429-4019-8e08-002a160dd09d
         * DepartmentId : 49f7822c-0ec3-47b4-980d-d6c4a1dad689
         * Name : A11栋
         * Code : 320302001012001003014
         * GisLocations :
         * FullPath : 徐州人家生态居住区/A11栋
         */

        private String Id;
        private String DepartmentId;
        private String Name;
        private String Code;
        private String GisLocations;
        private String FullPath;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
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

        public String getGisLocations() {
            return GisLocations;
        }

        public void setGisLocations(String GisLocations) {
            this.GisLocations = GisLocations;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }
    }
}
