package com.weique.overhaul.v2.mvp.model.entity;

public class PointsDetailBean {


    /**
     * Id : 1aa170d2-f84f-4bc6-8271-0ed8df9bdd90
     * Name : 费艳
     * ElementTypeName : 网格员
     * DepartmentFullPath : 徐州市沛县/沛城街道/汉台居/汉台网格4/
     * ElementTypeId : 5332f9fc-c1eb-49f9-aa64-04d045b165f1
     */

    private String Id;
    private String Name;
    private String ElementTypeName;
    private String DepartmentFullPath;
    private String ElementTypeId;

    private String CustId;

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
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

    public String getElementTypeName() {
        return ElementTypeName;
    }

    public void setElementTypeName(String ElementTypeName) {
        this.ElementTypeName = ElementTypeName;
    }

    public String getDepartmentFullPath() {
        return DepartmentFullPath;
    }

    public void setDepartmentFullPath(String DepartmentFullPath) {
        this.DepartmentFullPath = DepartmentFullPath;
    }

    public String getElementTypeId() {
        return ElementTypeId;
    }

    public void setElementTypeId(String ElementTypeId) {
        this.ElementTypeId = ElementTypeId;
    }
}
