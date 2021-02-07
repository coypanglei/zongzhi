package com.weique.overhaul.v2.mvp.model.entity;

import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

public class GlobalUserDepartmentBean implements Cloneable, NameAndIdInterface {


    /**
     * DepartmentId : 3affff73-a1ce-43c7-95c4-6581caf43c05
     * DepartmentName : 独流镇
     * DepartmentFullPath : 静海镇/热力图工具专用区域/独流镇/
     * EnumCommunityLevel : 2
     */

    private String DepartmentId;
    private String DepartmentName;
    private String DepartmentFullPath;
    private int EnumCommunityLevel;

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

    public String getDepartmentFullPath() {
        return DepartmentFullPath;
    }

    public void setDepartmentFullPath(String DepartmentFullPath) {
        this.DepartmentFullPath = DepartmentFullPath;
    }

    public int getEnumCommunityLevel() {
        return EnumCommunityLevel;
    }

    public void setEnumCommunityLevel(int EnumCommunityLevel) {
        this.EnumCommunityLevel = EnumCommunityLevel;
    }

    @Override
    public String getId() {
        return DepartmentId;
    }

    @Override
    public String getId2() {
        return null;
    }

    @Override
    public String getName() {
        return DepartmentName;
    }
}