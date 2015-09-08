package com.rainbow.lightnote.model;

/**
 * Created by weijuner on 2015/9/6.
 */
public class Lable {
    int labelId;
    String lableName;

    public Lable(int labelId, String lableName) {
        this.labelId = labelId;
        this.lableName = lableName;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
}
