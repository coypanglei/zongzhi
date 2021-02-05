package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class AddressBookItemBean {
    /**
     * pageCount : 13
     * list : [{"Id":"8579cad7-26bd-41b7-868d-16a26730d68f","Name":"琵琶花园南院第五网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第五网格","EnumCommunityLevel":0},{"Id":"66053c1a-104b-4033-b9d4-406e6112080e","Name":"琵琶花园南院第六网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第六网格","EnumCommunityLevel":0},{"Id":"7b2715bf-9ab6-45e6-83d6-74c580b412ee","Name":"琵琶花园南院第四网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第四网格","EnumCommunityLevel":0},{"Id":"64f67a54-d5c0-4948-bf00-8226317c8a39","Name":"琵琶花园南院第一网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第一网格","EnumCommunityLevel":0},{"Id":"afeb4321-731e-441b-b795-83246b6146e7","Name":"琵琶花园北院网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园北院网格","EnumCommunityLevel":0},{"Id":"9345aa4c-86b1-4256-ab37-ca868a67b79c","Name":"琵琶花园南院第三网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第三网格","EnumCommunityLevel":0},{"Id":"85e8c55b-9cf0-41be-a9fd-d2d447e70bba","Name":"琵琶花园南院第二网格","FullPath":"徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第二网格","EnumCommunityLevel":0}]
     * users : [{"Id":"e71989fe-fba3-480e-946a-70922ebd76a9","Name":"肖来","DepartName":"鼓楼区公安局","Tel":"18361251133"},{"Id":"695c0711-fcbf-4e08-980f-96bb1a7524b8","Name":"万桂芹","DepartName":"琵琶花园社区","Tel":"15050003427"},{"Id":"22f1ec7b-d26d-4b03-abc1-f2561482935b","Name":"徐晓根","DepartName":"琵琶花园社区","Tel":"15896426472"},{"Id":"eac03627-8e9f-492e-a8d1-157a4ebccb0c","Name":"高建忠","DepartName":"琵琶花园社区","Tel":"15150025553"},{"Id":"b82b505b-bf15-4b4b-9c99-b2ec3f04ea5b","Name":"刘贵","DepartName":"琵琶花园社区","Tel":"15862187022"},{"Id":"4f3bf81f-4675-4bca-901f-af426449f489","Name":"刘松","DepartName":"琵琶花园社区","Tel":"17705212227"},{"Id":"7de23be9-825b-436c-9065-22cef9b032a2","Name":"付鸿燕","DepartName":"琵琶花园社区","Tel":"13685180341"},{"Id":"89629ca8-5e04-4e3b-be9a-671fab3e4985","Name":"岳从得","DepartName":"琵琶花园社区","Tel":"15162239650"},{"Id":"2fbf6c9d-8d57-4b2a-9c7f-a83a478666ca","Name":"曹吉祥","DepartName":"琵琶花园社区","Tel":"13372208261"},{"Id":"bebbfca7-c735-454d-bd26-cbd899e00823","Name":"朱方法","DepartName":"琵琶花园社区","Tel":"13196815230"}]
     */

    private int pageCount;
    private List<ListBean> list;
    private List<UsersBean> users;

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

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class ListBean extends SignStaffListCommonBean{
        /**
         * Id : 8579cad7-26bd-41b7-868d-16a26730d68f
         * Name : 琵琶花园南院第五网格
         * FullPath : 徐州市鼓楼区/琵琶街道办事处/琵琶花园社区/琵琶花园南院第五网格
         * EnumCommunityLevel : 0
         */


        private String FullPath;
        private int EnumCommunityLevel;

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public int getEnumCommunityLevel() {
            return EnumCommunityLevel;
        }

        public void setEnumCommunityLevel(int EnumCommunityLevel) {
            this.EnumCommunityLevel = EnumCommunityLevel;
        }
    }

    public static class UsersBean {
        /**
         * Id : e71989fe-fba3-480e-946a-70922ebd76a9
         * Name : 肖来
         * DepartName : 鼓楼区公安局
         * Tel : 18361251133
         */

        private String Id;
        private String Name;
        private String DepartName;
        private String Tel;
        private String HeadUrl;

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String headUrl) {
            HeadUrl = headUrl;
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

        public String getDepartName() {
            return DepartName;
        }

        public void setDepartName(String DepartName) {
            this.DepartName = DepartName;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }
    }

//    private int pageCount;
//    private List<DataBean> list;
//
//    public int getPageCount() {
//        return pageCount;
//    }
//
//    public void setPageCount(int pageCount) {
//        this.pageCount = pageCount;
//    }
//
//    public List<DataBean> getList() {
//        return list;
//    }
//
//    public void setList(List<DataBean> list) {
//        this.list = list;
//    }
//
//    public static class DataBean {
//        private String Id;
//        private String Name;
//        private String FullPath;
//        private String EnumCommunityLevel;
//
//        public String getId() {
//            return Id;
//        }
//
//        public void setId(String id) {
//            Id = id;
//        }
//
//        public String getName() {
//            return Name;
//        }
//
//        public void setName(String name) {
//            Name = name;
//        }
//
//        public String getFullPath() {
//            return FullPath;
//        }
//
//        public void setFullPath(String fullPath) {
//            FullPath = fullPath;
//        }
//
//        public String getEnumCommentLevel() {
//            return EnumCommunityLevel;
//        }
//
//        public void setEnumCommentLevel(String enumCommentLevel) {
//            EnumCommunityLevel = enumCommentLevel;
//        }
//    }


}

