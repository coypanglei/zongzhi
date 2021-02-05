package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class BusinessInformationDetailBean {


    /**
     * enterpriseInf : {"Name":"quis nulla dolore aute","FullPath":"occaecat culpa non ipsum dolore","EnumEnType":"elit","EnumIndustry":"amet sunt pariatur el","Code":"nostrud","Address":"reprehenderit","LegalPerson":"nisi incididunt quis","Tel":"sed do aute","LiaisonMan":"tempor","LiaisonTel":"ex","ReturnWorkTime":"esse tempor ut sint"}
     * people : {"pageCount":6.540595013646296E7,"list":[{"Name":"dolore cupidatat laborum labore","Gender":"ad minim aute","Tel":"est cillum pariatur","CurrentAddress":"sed cillum s"},{"Name":"cupidatat sit officia","Gender":"est reprehenderit nostrud do","Tel":"anim occaecat minim laboris et","CurrentAddress":"deserunt laborum"},{"Name":"laboris consectetur Excepteur voluptate","Gender":"proident","Tel":"dolor quis sunt","CurrentAddress":"laboris sunt dolore"},{"Name":"qui ullamco consectetur","Gender":"sunt ut","Tel":"dolore","CurrentAddress":"esse minim mollit Duis"},{"Name":"voluptate","Gender":"officia ut","Tel":"laborum","CurrentAddress":"quis mollit"}]}
     */

    private EnterpriseInfBean enterpriseInf;
    private PeopleBean people;

    public EnterpriseInfBean getEnterpriseInf() {
        return enterpriseInf;
    }

    public void setEnterpriseInf(EnterpriseInfBean enterpriseInf) {
        this.enterpriseInf = enterpriseInf;
    }

    public PeopleBean getPeople() {
        return people;
    }

    public void setPeople(PeopleBean people) {
        this.people = people;
    }

    public static class EnterpriseInfBean {
        /**
         * Name : quis nulla dolore aute
         * FullPath : occaecat culpa non ipsum dolore
         * EnumEnType : elit
         * EnumIndustry : amet sunt pariatur el
         * Code : nostrud
         * Address : reprehenderit
         * LegalPerson : nisi incididunt quis
         * Tel : sed do aute
         * LiaisonMan : tempor
         * LiaisonTel : ex
         * ReturnWorkTime : esse tempor ut sint
         */

        private String Name;
        private String FullPath;
        private String EnumEnType;
        private String EnumIndustry;
        private String Code;
        private String Address;
        private String LegalPerson;
        private String Tel;
        private String LiaisonMan;
        private String LiaisonTel;
        private String ReturnWorkTime;
        private String peopleCount;

        public String getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(String peopleCount) {
            this.peopleCount = peopleCount;
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

        public String getEnumEnType() {
            return EnumEnType;
        }

        public void setEnumEnType(String EnumEnType) {
            this.EnumEnType = EnumEnType;
        }

        public String getEnumIndustry() {
            return EnumIndustry;
        }

        public void setEnumIndustry(String EnumIndustry) {
            this.EnumIndustry = EnumIndustry;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getLegalPerson() {
            return LegalPerson;
        }

        public void setLegalPerson(String LegalPerson) {
            this.LegalPerson = LegalPerson;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getLiaisonMan() {
            return LiaisonMan;
        }

        public void setLiaisonMan(String LiaisonMan) {
            this.LiaisonMan = LiaisonMan;
        }

        public String getLiaisonTel() {
            return LiaisonTel;
        }

        public void setLiaisonTel(String LiaisonTel) {
            this.LiaisonTel = LiaisonTel;
        }

        public String getReturnWorkTime() {
            return ReturnWorkTime;
        }

        public void setReturnWorkTime(String ReturnWorkTime) {
            this.ReturnWorkTime = ReturnWorkTime;
        }
    }

    public static class PeopleBean {
        /**
         * pageCount : 6.540595013646296E7
         * list : [{"Name":"dolore cupidatat laborum labore","Gender":"ad minim aute","Tel":"est cillum pariatur","CurrentAddress":"sed cillum s"},{"Name":"cupidatat sit officia","Gender":"est reprehenderit nostrud do","Tel":"anim occaecat minim laboris et","CurrentAddress":"deserunt laborum"},{"Name":"laboris consectetur Excepteur voluptate","Gender":"proident","Tel":"dolor quis sunt","CurrentAddress":"laboris sunt dolore"},{"Name":"qui ullamco consectetur","Gender":"sunt ut","Tel":"dolore","CurrentAddress":"esse minim mollit Duis"},{"Name":"voluptate","Gender":"officia ut","Tel":"laborum","CurrentAddress":"quis mollit"}]
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
             * Name : dolore cupidatat laborum labore
             * Gender : ad minim aute
             * Tel : est cillum pariatur
             * CurrentAddress : sed cillum s
             */

            private String Name;
            private String Id;
            private String Gender;
            private String Tel;
            private String CurrentAddress;
            private String Age;

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String age) {
                Age = age;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getGender() {
                return Gender;
            }

            public void setGender(String Gender) {
                this.Gender = Gender;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String Tel) {
                this.Tel = Tel;
            }

            public String getCurrentAddress() {
                return CurrentAddress;
            }

            public void setCurrentAddress(String CurrentAddress) {
                this.CurrentAddress = CurrentAddress;
            }
        }
    }
}
