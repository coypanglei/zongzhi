package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GreatKing
 */
public class ContradictionItemBean {


    /**
     * pageCount : 1
     * list : [{"Id":"817820a8-d1e1-495d-b9ff-44ed40e744a8","STANDARDADDRESSID":"8397accf-797e-40bb-9bce-184597ab9176","Address":"御山雅苑网格","Code":"MDD000003","EnumResolveType":0,"FullPath":"御山雅苑网格","Memo":"哈哈","Time":"2020/01/03 14:04","EnumCAEventOrderSaveStatus":1},{"Id":"a03a0237-2c30-41be-88e6-4974ba88dc7f","STANDARDADDRESSID":"7be40274-924a-4d3e-9799-1c55644e0eb9","Address":"静香园","Code":"MDD000002","EnumResolveType":1,"FullPath":"静香园","Memo":"打架","Time":"2020/01/03 13:49","EnumCAEventOrderSaveStatus":1},{"Id":"39c8d95a-58a2-4a0f-b13d-c4ce44f59df0","STANDARDADDRESSID":"8397accf-797e-40bb-9bce-184597ab9176","Address":"御山雅苑网格","Code":"MDD000001","EnumResolveType":0,"FullPath":"御山雅苑网格","Memo":"21212","Time":"2020/01/03 10:37","EnumCAEventOrderSaveStatus":1}]
     */

    private int pageCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * Id : 817820a8-d1e1-495d-b9ff-44ed40e744a8
         * STANDARDADDRESSID : 8397accf-797e-40bb-9bce-184597ab9176
         * Address : 御山雅苑网格
         * Code : MDD000003
         * EnumResolveType : 0
         * FullPath : 御山雅苑网格
         * Memo : 哈哈
         * Time : 2020/01/03 14:04
         * EnumCAEventOrderSaveStatus : 1
         */

        private String Id;
        private String StandardAddressId;
        private String Address;
        private String Code;
        private int EnumResolveType;
        private String FullPath;
        private String Memo;
        private String Time;
        private int EnumCAEventOrderSaveStatus;
        private String PointsInJSON;

        public String getPointsInJSON() {
            return PointsInJSON;
        }

        public void setPointsInJSON(String pointsInJSON) {
            PointsInJSON = pointsInJSON;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getStandardAddressId() {
            return StandardAddressId;
        }

        public void setStandardAddressId(String StandardAddressId) {
            this.StandardAddressId = StandardAddressId;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public int getEnumResolveType() {
            return EnumResolveType;
        }

        public void setEnumResolveType(int EnumResolveType) {
            this.EnumResolveType = EnumResolveType;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public int getEnumCAEventOrderSaveStatus() {
            return EnumCAEventOrderSaveStatus;
        }

        public void setEnumCAEventOrderSaveStatus(int EnumCAEventOrderSaveStatus) {
            this.EnumCAEventOrderSaveStatus = EnumCAEventOrderSaveStatus;
        }
    }
}
