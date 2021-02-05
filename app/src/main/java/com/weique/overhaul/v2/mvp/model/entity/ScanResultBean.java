package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class ScanResultBean {


    /**
     * StandarAddress : {"Department":{"EnumCommunityLevel":0,"Name":"鹿楼村网格1","Code":"320305001204001","PinYin":"LLC","PId":"1d8fb2ff-8a76-4e35-bd72-2863f3b7f7a9","ChargerId":"00000000-0000-0000-0000-000000000000","FullPath":"徐州市贾汪区/茱萸山派出所/鹿楼村/鹿楼村网格1","PointsInJSON":"{\"polygoys\":[{\"path\":[{\"lng\":117.49684165067883,\"lat\":34.40099797867469},{\"lng\":117.49728631190621,\"lat\":34.39762100947385},{\"lng\":117.49731326107151,\"lat\":34.39760983950955},{\"lng\":117.49728182037866,\"lat\":34.39760983950955},{\"lng\":117.49726834579602,\"lat\":34.39760983950955},{\"lng\":117.49727732885111,\"lat\":34.397606116187774},{\"lng\":117.49486987008437,\"lat\":34.39761356283115},{\"lng\":117.49465427676198,\"lat\":34.39542422087624},{\"lng\":117.49456444621097,\"lat\":34.387634460784284},{\"lng\":117.48970012187445,\"lat\":34.38391060765226},{\"lng\":117.48953842688265,\"lat\":34.39358483178515},{\"lng\":117.48940368105616,\"lat\":34.3975614363135},{\"lng\":117.49023012212534,\"lat\":34.39763590275725},{\"lng\":117.48996304297691,\"lat\":34.39878553537261},{\"lng\":117.48967558521372,\"lat\":34.3999918657187},{\"lng\":117.48960372077292,\"lat\":34.40034929356176},{\"lng\":117.48953185633212,\"lat\":34.4005577924253},{\"lng\":117.48949592411172,\"lat\":34.40061736343288},{\"lng\":117.48947795800152,\"lat\":34.40065459529086},{\"lng\":117.48946897494642,\"lat\":34.40063970254968},{\"lng\":117.48949592411172,\"lat\":34.400721612593124},{\"lng\":117.48946897494642,\"lat\":34.400729058956706},{\"lng\":117.49682817609612,\"lat\":34.401091072205645}],\"styleOptions\":{\"strokeColor\":\"#8080ff\",\"fillColor\":\"#8080ff\",\"strokeWeight\":1,\"strokeOpacity\":0.6,\"fillOpacity\":0.4,\"strokeStyle\":\"solid\"}}]}","LayerLevel":0,"CSS":null,"EnumDepartmentProperty":1,"EnumDepartmentType":1,"Id":"eeb577b9-6817-4ec3-848d-c55dbeb7ec66","CreateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","UpdateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","CreateTime":"/Date(1582179694993)/","UpdateTime":"/Date(1582685835150)/","Memo":null,"IsDeleted":false,"DeletedTime":null},"StandardAddressType":{"Name":"户、室","PinYin":"H、S","CIndex":3,"Id":"b5880ee1-ba45-41c8-a510-c15e444692ce","CreateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","UpdateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","CreateTime":"/Date(1582031907900)/","UpdateTime":"/Date(1582031907900)/","Memo":null,"IsDeleted":false,"DeletedTime":null},"Name":"153号","Code":"320305001204001001001048","PinYin":"153H","PId":"75d1b029-ea2b-49c6-bef3-29612b044bbe","FullPath":"鹿楼村/1组/153号","DepartmentId":"eeb577b9-6817-4ec3-848d-c55dbeb7ec66","DepartmentName":null,"StandardAddressTypeId":"b5880ee1-ba45-41c8-a510-c15e444692ce","StandardAddressTypeName":null,"GisLocations":"","Css":null,"Id":"1edbe15b-7dbf-4151-85fd-b412a464d631","CreateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","UpdateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","CreateTime":"/Date(1582214431447)/","UpdateTime":"/Date(1582214431447)/","Memo":null,"IsDeleted":false,"DeletedTime":null}
     * List : [{"ElementTypeName":"人口信息登记","elements":[{"Id":"af96fb5e-1f22-4c0b-b709-1147fa679840","name":"孙景河","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"2a30d5fe-7b03-4b85-a43f-a456a65c5322","name":"孙明明","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"e8c6fa0c-ff77-4cf9-8632-36102ba8a5bc","name":"孙婵","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"69621f7a-8dcb-440f-ab1f-c76d791522b7","name":"孙亮亮","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"56d19cf2-be71-4c36-9fbe-71a32b3f6349","name":"庄桂芹","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"}]}]
     */
    private boolean isHavePermission;

    public boolean isHavePermission() {
        return isHavePermission;
    }

    public void setHavePermission(boolean havePermission) {
        isHavePermission = havePermission;
    }

    private StandarAddressBean StandarAddress;
    private List<ListBean> List;

    public StandarAddressBean getStandarAddress() {
        return StandarAddress;
    }

    public void setStandarAddress(StandarAddressBean StandarAddress) {
        this.StandarAddress = StandarAddress;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class StandarAddressBean {
        /**
         * Department : {"EnumCommunityLevel":0,"Name":"鹿楼村网格1","Code":"320305001204001","PinYin":"LLC","PId":"1d8fb2ff-8a76-4e35-bd72-2863f3b7f7a9","ChargerId":"00000000-0000-0000-0000-000000000000","FullPath":"徐州市贾汪区/茱萸山派出所/鹿楼村/鹿楼村网格1","PointsInJSON":"{\"polygoys\":[{\"path\":[{\"lng\":117.49684165067883,\"lat\":34.40099797867469},{\"lng\":117.49728631190621,\"lat\":34.39762100947385},{\"lng\":117.49731326107151,\"lat\":34.39760983950955},{\"lng\":117.49728182037866,\"lat\":34.39760983950955},{\"lng\":117.49726834579602,\"lat\":34.39760983950955},{\"lng\":117.49727732885111,\"lat\":34.397606116187774},{\"lng\":117.49486987008437,\"lat\":34.39761356283115},{\"lng\":117.49465427676198,\"lat\":34.39542422087624},{\"lng\":117.49456444621097,\"lat\":34.387634460784284},{\"lng\":117.48970012187445,\"lat\":34.38391060765226},{\"lng\":117.48953842688265,\"lat\":34.39358483178515},{\"lng\":117.48940368105616,\"lat\":34.3975614363135},{\"lng\":117.49023012212534,\"lat\":34.39763590275725},{\"lng\":117.48996304297691,\"lat\":34.39878553537261},{\"lng\":117.48967558521372,\"lat\":34.3999918657187},{\"lng\":117.48960372077292,\"lat\":34.40034929356176},{\"lng\":117.48953185633212,\"lat\":34.4005577924253},{\"lng\":117.48949592411172,\"lat\":34.40061736343288},{\"lng\":117.48947795800152,\"lat\":34.40065459529086},{\"lng\":117.48946897494642,\"lat\":34.40063970254968},{\"lng\":117.48949592411172,\"lat\":34.400721612593124},{\"lng\":117.48946897494642,\"lat\":34.400729058956706},{\"lng\":117.49682817609612,\"lat\":34.401091072205645}],\"styleOptions\":{\"strokeColor\":\"#8080ff\",\"fillColor\":\"#8080ff\",\"strokeWeight\":1,\"strokeOpacity\":0.6,\"fillOpacity\":0.4,\"strokeStyle\":\"solid\"}}]}","LayerLevel":0,"CSS":null,"EnumDepartmentProperty":1,"EnumDepartmentType":1,"Id":"eeb577b9-6817-4ec3-848d-c55dbeb7ec66","CreateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","UpdateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","CreateTime":"/Date(1582179694993)/","UpdateTime":"/Date(1582685835150)/","Memo":null,"IsDeleted":false,"DeletedTime":null}
         * StandardAddressType : {"Name":"户、室","PinYin":"H、S","CIndex":3,"Id":"b5880ee1-ba45-41c8-a510-c15e444692ce","CreateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","UpdateUserId":"f84bb90c-d733-44d5-ba67-3ec35dda8100","CreateTime":"/Date(1582031907900)/","UpdateTime":"/Date(1582031907900)/","Memo":null,"IsDeleted":false,"DeletedTime":null}
         * Name : 153号
         * Code : 320305001204001001001048
         * PinYin : 153H
         * PId : 75d1b029-ea2b-49c6-bef3-29612b044bbe
         * FullPath : 鹿楼村/1组/153号
         * DepartmentId : eeb577b9-6817-4ec3-848d-c55dbeb7ec66
         * DepartmentName : null
         * StandardAddressTypeId : b5880ee1-ba45-41c8-a510-c15e444692ce
         * StandardAddressTypeName : null
         * GisLocations :
         * Css : null
         * Id : 1edbe15b-7dbf-4151-85fd-b412a464d631
         * CreateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
         * UpdateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
         * CreateTime : /Date(1582214431447)/
         * UpdateTime : /Date(1582214431447)/
         * Memo : null
         * IsDeleted : false
         * DeletedTime : null
         */

        private DepartmentBean Department;
        private StandardAddressTypeBean StandardAddressType;
        private String Name;
        private String Code;
        private String PinYin;
        private String PId;
        private String FullPath;
        private String DepartmentId;
        private String DepartmentName;
        private String StandardAddressTypeId;
        private String StandardAddressTypeName;
        private String GisLocations;
        private String Css;
        private String Id;
        private String CreateUserId;
        private String UpdateUserId;
        private String CreateTime;
        private String UpdateTime;
        private String Memo;
        private boolean IsDeleted;
        private String DeletedTime;

        public DepartmentBean getDepartment() {
            return Department;
        }

        public void setDepartment(DepartmentBean Department) {
            this.Department = Department;
        }

        public StandardAddressTypeBean getStandardAddressType() {
            return StandardAddressType;
        }

        public void setStandardAddressType(StandardAddressTypeBean StandardAddressType) {
            this.StandardAddressType = StandardAddressType;
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

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getStandardAddressTypeId() {
            return StandardAddressTypeId;
        }

        public void setStandardAddressTypeId(String StandardAddressTypeId) {
            this.StandardAddressTypeId = StandardAddressTypeId;
        }

        public String getStandardAddressTypeName() {
            return StandardAddressTypeName;
        }

        public void setStandardAddressTypeName(String StandardAddressTypeName) {
            this.StandardAddressTypeName = StandardAddressTypeName;
        }

        public String getGisLocations() {
            return GisLocations;
        }

        public void setGisLocations(String GisLocations) {
            this.GisLocations = GisLocations;
        }

        public String getCss() {
            return Css;
        }

        public void setCss(String Css) {
            this.Css = Css;
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

        public static class DepartmentBean {
            /**
             * EnumCommunityLevel : 0
             * Name : 鹿楼村网格1
             * Code : 320305001204001
             * PinYin : LLC
             * PId : 1d8fb2ff-8a76-4e35-bd72-2863f3b7f7a9
             * ChargerId : 00000000-0000-0000-0000-000000000000
             * FullPath : 徐州市贾汪区/茱萸山派出所/鹿楼村/鹿楼村网格1
             * PointsInJSON : {"polygoys":[{"path":[{"lng":117.49684165067883,"lat":34.40099797867469},{"lng":117.49728631190621,"lat":34.39762100947385},{"lng":117.49731326107151,"lat":34.39760983950955},{"lng":117.49728182037866,"lat":34.39760983950955},{"lng":117.49726834579602,"lat":34.39760983950955},{"lng":117.49727732885111,"lat":34.397606116187774},{"lng":117.49486987008437,"lat":34.39761356283115},{"lng":117.49465427676198,"lat":34.39542422087624},{"lng":117.49456444621097,"lat":34.387634460784284},{"lng":117.48970012187445,"lat":34.38391060765226},{"lng":117.48953842688265,"lat":34.39358483178515},{"lng":117.48940368105616,"lat":34.3975614363135},{"lng":117.49023012212534,"lat":34.39763590275725},{"lng":117.48996304297691,"lat":34.39878553537261},{"lng":117.48967558521372,"lat":34.3999918657187},{"lng":117.48960372077292,"lat":34.40034929356176},{"lng":117.48953185633212,"lat":34.4005577924253},{"lng":117.48949592411172,"lat":34.40061736343288},{"lng":117.48947795800152,"lat":34.40065459529086},{"lng":117.48946897494642,"lat":34.40063970254968},{"lng":117.48949592411172,"lat":34.400721612593124},{"lng":117.48946897494642,"lat":34.400729058956706},{"lng":117.49682817609612,"lat":34.401091072205645}],"styleOptions":{"strokeColor":"#8080ff","fillColor":"#8080ff","strokeWeight":1,"strokeOpacity":0.6,"fillOpacity":0.4,"strokeStyle":"solid"}}]}
             * LayerLevel : 0
             * CSS : null
             * EnumDepartmentProperty : 1
             * EnumDepartmentType : 1
             * Id : eeb577b9-6817-4ec3-848d-c55dbeb7ec66
             * CreateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
             * UpdateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
             * CreateTime : /Date(1582179694993)/
             * UpdateTime : /Date(1582685835150)/
             * Memo : null
             * IsDeleted : false
             * DeletedTime : null
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
            private int EnumDepartmentProperty;
            private int EnumDepartmentType;
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

            public void setEnumCommunityLevel(int EnumCommunityLevel) {
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

            public int getEnumDepartmentProperty() {
                return EnumDepartmentProperty;
            }

            public void setEnumDepartmentProperty(int EnumDepartmentProperty) {
                this.EnumDepartmentProperty = EnumDepartmentProperty;
            }

            public int getEnumDepartmentType() {
                return EnumDepartmentType;
            }

            public void setEnumDepartmentType(int EnumDepartmentType) {
                this.EnumDepartmentType = EnumDepartmentType;
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

        public static class StandardAddressTypeBean {
            /**
             * Name : 户、室
             * PinYin : H、S
             * CIndex : 3
             * Id : b5880ee1-ba45-41c8-a510-c15e444692ce
             * CreateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
             * UpdateUserId : f84bb90c-d733-44d5-ba67-3ec35dda8100
             * CreateTime : /Date(1582031907900)/
             * UpdateTime : /Date(1582031907900)/
             * Memo : null
             * IsDeleted : false
             * DeletedTime : null
             */

            private String Name;
            private String PinYin;
            private int CIndex;
            private String Id;
            private String CreateUserId;
            private String UpdateUserId;
            private String CreateTime;
            private String UpdateTime;
            private String Memo;
            private boolean IsDeleted;
            private String DeletedTime;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getPinYin() {
                return PinYin;
            }

            public void setPinYin(String PinYin) {
                this.PinYin = PinYin;
            }

            public int getCIndex() {
                return CIndex;
            }

            public void setCIndex(int CIndex) {
                this.CIndex = CIndex;
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
    }

    public static class ListBean {
        /**
         * ElementTypeName : 人口信息登记
         * elements : [{"Id":"af96fb5e-1f22-4c0b-b709-1147fa679840","name":"孙景河","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"2a30d5fe-7b03-4b85-a43f-a456a65c5322","name":"孙明明","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"e8c6fa0c-ff77-4cf9-8632-36102ba8a5bc","name":"孙婵","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"69621f7a-8dcb-440f-ab1f-c76d791522b7","name":"孙亮亮","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"},{"Id":"56d19cf2-be71-4c36-9fbe-71a32b3f6349","name":"庄桂芹","PointsInJson":"{\"lng\":117.4600752300,\"lat\":34.4490287900}","ElementTypeFullPath":"全部分类/人口/实有人口/人口信息登记","ElementTypeName":"人口信息登记"}]
         */

        private String ElementTypeName;
        private List<ElementsBean> elements;

        public String getElementTypeName() {
            return ElementTypeName;
        }

        public void setElementTypeName(String ElementTypeName) {
            this.ElementTypeName = ElementTypeName;
        }

        public List<ElementsBean> getElements() {
            return elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }

        public static class ElementsBean {
            /**
             * Id : af96fb5e-1f22-4c0b-b709-1147fa679840
             * name : 孙景河
             * PointsInJson : {"lng":117.4600752300,"lat":34.4490287900}
             * ElementTypeFullPath : 全部分类/人口/实有人口/人口信息登记
             * ElementTypeName : 人口信息登记
             */

            private String Id;
            private String name;
            private String PointsInJson;
            private String ElementTypeFullPath;
            private String ElementTypeName;
            private String cover;
            private String DataStructureInJson;
            private String ElementTypeId;

            public String getDataStructureInJson() {
                return DataStructureInJson;
            }

            public void setDataStructureInJson(String dataStructureInJson) {
                DataStructureInJson = dataStructureInJson;
            }

            public String getElementTypeId() {
                return ElementTypeId;
            }

            public void setElementTypeId(String elementTypeId) {
                ElementTypeId = elementTypeId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPointsInJson() {
                return PointsInJson;
            }

            public void setPointsInJson(String PointsInJson) {
                this.PointsInJson = PointsInJson;
            }

            public String getElementTypeFullPath() {
                return ElementTypeFullPath;
            }

            public void setElementTypeFullPath(String ElementTypeFullPath) {
                this.ElementTypeFullPath = ElementTypeFullPath;
            }

            public String getElementTypeName() {
                return ElementTypeName;
            }

            public void setElementTypeName(String ElementTypeName) {
                this.ElementTypeName = ElementTypeName;
            }
        }
    }
}
