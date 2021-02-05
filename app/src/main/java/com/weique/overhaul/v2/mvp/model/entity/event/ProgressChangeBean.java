package com.weique.overhaul.v2.mvp.model.entity.event;

public class ProgressChangeBean {
    private int pos;
    private int progress;

    public ProgressChangeBean(int pos, int progress) {
        this.pos = pos;
        this.progress = progress;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
