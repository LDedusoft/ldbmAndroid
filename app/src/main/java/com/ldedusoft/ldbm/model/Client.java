package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 客户信息
 * Created by wangjianwei on 2016/7/5.
 * {BianHao：编号；LinkMan：联系人；Name：名称；Mobilephone：手机}
 */
public class Client implements Serializable {
    private String BianHao;
    private String LinkMan;
    private String Name;
    private String Mobilephone;

    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        BianHao = bianHao;
    }

    public String getLinkMan() {
        return LinkMan;
    }

    public void setLinkMan(String linkMan) {
        LinkMan = linkMan;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobilephone() {
        return Mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        Mobilephone = mobilephone;
    }
}
