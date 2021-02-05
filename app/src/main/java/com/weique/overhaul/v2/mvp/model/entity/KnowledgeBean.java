package com.weique.overhaul.v2.mvp.model.entity;

public class KnowledgeBean {
    /**
     * Id : 6dc197c7-8dee-44ee-8b31-fc82caa4fcb6
     * Name : 法律法规
     */

    private String Id;
    private String Name;


    public KnowledgeBean(String id, String name) {
        Id = id;
        Name = name;
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
}
