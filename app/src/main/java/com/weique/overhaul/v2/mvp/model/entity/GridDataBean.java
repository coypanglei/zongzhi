package com.weique.overhaul.v2.mvp.model.entity;

public class GridDataBean {


    /**
     * Id : adipisicing
     * Name : laboris dolore
     * FullPath : culpa fugiat
     * Count : 8.661639277482113E7
     */
    private int Level;
    private String Id;
    private String Name;
    private String FullPath;
    private int Count;

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
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

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String FullPath) {
        this.FullPath = FullPath;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }


    public GridDataBean(String name, int count) {
        Name = name;
        Count = count;
    }
}
