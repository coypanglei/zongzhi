package com.weique.overhaul.v2.mvp.model.entity;

/**
 * @author GK
 * @description:
 * @date :2020/11/10 17:51
 */
public class HelperListItemBean {
    String Id;
    String Name;
    String UpdateTime;
    String issue;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
