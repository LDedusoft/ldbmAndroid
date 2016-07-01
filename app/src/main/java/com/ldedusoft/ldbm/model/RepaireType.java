package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * Created by wangjianwei on 2016/6/30.
 */
public class RepaireType implements Serializable {
    private int id;
    private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
