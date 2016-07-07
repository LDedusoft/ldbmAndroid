package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * Created by wangjianwei on 2016/7/5.
 */
public class ClientCategory implements Serializable {
    private String categoryName;
    private int categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
