package com.ldedusoft.ldbm.model;

/**
 * Created by wangjianwei on 2016/6/23.
 */
public class SysProperty {

    private static SysProperty sysp;

    private String mode;

    private SysProperty(){}

    public static SysProperty getInstance(){
        if(sysp == null){
            sysp = new SysProperty();
        }
        return sysp;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
