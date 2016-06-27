package com.ldedusoft.ldbm.model;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/23.
 */
public class SysProperty {

    private static SysProperty sysp;

    /*登录模式*/
    private String mode;

    /*首页列表*/
    private ArrayList<MenuItem> homeMenuList;

    private SysProperty(){}

    public static SysProperty getInstance(){
        if(sysp == null){
            sysp = new SysProperty();
        }
        return sysp;
    }

    public ArrayList<MenuItem> getHomeMenuList() {
        return homeMenuList;
    }

    public void setHomeMenuList(ArrayList<MenuItem> homeMenuList) {
        this.homeMenuList = homeMenuList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
