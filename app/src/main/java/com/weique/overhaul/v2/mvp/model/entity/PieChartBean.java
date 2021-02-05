package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class PieChartBean {


    /**
     * data : [{"Name":"丰财街道办事处","size":349},{"Name":"鼓楼数字产业园","size":2},{"Name":"环城街道办事处","size":283},{"Name":"黄楼街道办事处","size":221},{"Name":"九里办事处","size":6272},{"Name":"牌楼街道办事处","size":191},{"Name":"琵琶街道办事处","size":155},{"Name":"铜沛街道办事处","size":241}]
     * data2 : [{"Name":"丰财街道办事处","size":0},{"Name":"鼓楼数字产业园","size":0},{"Name":"环城街道办事处","size":0},{"Name":"黄楼街道办事处","size":0},{"Name":"九里办事处","size":0},{"Name":"牌楼街道办事处","size":0},{"Name":"琵琶街道办事处","size":0},{"Name":"铜沛街道办事处","size":0}]
     * data3 : [{"Name":"丰财街道办事处","size":2},{"Name":"鼓楼数字产业园","size":0},{"Name":"环城街道办事处","size":0},{"Name":"黄楼街道办事处","size":0},{"Name":"九里办事处","size":0},{"Name":"牌楼街道办事处","size":0},{"Name":"琵琶街道办事处","size":0},{"Name":"铜沛街道办事处","size":0}]
     * data4 : [{"Name":"丰财街道办事处","size":2},{"Name":"鼓楼数字产业园","size":0},{"Name":"环城街道办事处","size":0},{"Name":"黄楼街道办事处","size":0},{"Name":"九里办事处","size":0},{"Name":"牌楼街道办事处","size":0},{"Name":"琵琶街道办事处","size":0},{"Name":"铜沛街道办事处","size":0}]
     * totalCount : 7714
     * totalCount2 : 0
     * totalCount3 : 2
     * totalCount4 : 2
     */

    private int totalCount;
    private int totalCount2;
    private int totalCount3;
    private int totalCount4;
    private List<DataBean> data;
    private List<Data2Bean> data2;
    private List<Data3Bean> data3;
    private List<Data4Bean> data4;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount2() {
        return totalCount2;
    }

    public void setTotalCount2(int totalCount2) {
        this.totalCount2 = totalCount2;
    }

    public int getTotalCount3() {
        return totalCount3;
    }

    public void setTotalCount3(int totalCount3) {
        this.totalCount3 = totalCount3;
    }

    public int getTotalCount4() {
        return totalCount4;
    }

    public void setTotalCount4(int totalCount4) {
        this.totalCount4 = totalCount4;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<Data2Bean> getData2() {
        return data2;
    }

    public void setData2(List<Data2Bean> data2) {
        this.data2 = data2;
    }

    public List<Data3Bean> getData3() {
        return data3;
    }

    public void setData3(List<Data3Bean> data3) {
        this.data3 = data3;
    }

    public List<Data4Bean> getData4() {
        return data4;
    }

    public void setData4(List<Data4Bean> data4) {
        this.data4 = data4;
    }

    public static class DataBean {
        /**
         * Name : 丰财街道办事处
         * size : 349
         */

        private String Name;
        private int size;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class Data2Bean {
        /**
         * Name : 丰财街道办事处
         * size : 0
         */

        private String Name;
        private int size;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class Data3Bean {
        /**
         * Name : 丰财街道办事处
         * size : 2
         */

        private String Name;
        private int size;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class Data4Bean {
        /**
         * Name : 丰财街道办事处
         * size : 2
         */

        private String Name;
        private int size;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
