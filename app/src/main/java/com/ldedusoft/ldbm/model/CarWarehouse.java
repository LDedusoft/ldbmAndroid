package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 整车仓库
 * Created by wangjianwei on 2016/7/7.
 * {ID：id；Name：仓库名称；Code：仓库编号}
 */
public class CarWarehouse implements Serializable {
    private int ID;
    private String Name;
    private String Code;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
