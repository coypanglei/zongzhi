package com.weique.overhaul.v2.mvp.model.entity;



public class EdittextAndPosBean {
    public EdittextAndPosBean(String editTextString, int pos) {
        this.editTextString = editTextString;
        this.pos = pos;
    }

    String editTextString;
    int pos;

    public String getEditTextString() {
        return editTextString;
    }

    public void setEditTextString(String editTextString) {
        this.editTextString = editTextString;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
