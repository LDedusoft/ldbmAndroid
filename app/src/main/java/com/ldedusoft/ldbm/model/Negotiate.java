package com.ldedusoft.ldbm.model;

/**
 * Created by wangjianwei on 2016/7/6.
 * Number：单号；FormMaker：制单人
 *Name：客户名称；Sex：客户性别；cType：客户类型：Phone：电话；Time：方便时间
 * Plan：购车方案；WantCar：意向车型的id；WantTime：预购时间}
 */
public class Negotiate  {
    private String Number;
    private String FormMaker;
    private String Name;
    private String Sex;
    private String cType;
    private String Phone;
    private String Time;
    private String Plan;
    private String WantCar;
    private String WantTime;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getFormMaker() {
        return FormMaker;
    }

    public void setFormMaker(String formMaker) {
        FormMaker = formMaker;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPlan() {
        return Plan;
    }

    public void setPlan(String plan) {
        Plan = plan;
    }

    public String getWantCar() {
        return WantCar;
    }

    public void setWantCar(String wantCar) {
        WantCar = wantCar;
    }

    public String getWantTime() {
        return WantTime;
    }

    public void setWantTime(String wantTime) {
        WantTime = wantTime;
    }
}
