package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 *  预约维修信息
 * Created by wangjianwei on 2016/6/30.
 * {Id：id；DanHao：单号；djTime：登记时间；yyTime：预约时间；CarCode：车牌号；
 * MingCheng：客户名称；wxFangShi：维修方式；ywLeiBie：业务类别}
 */
public class Appointment implements Serializable {
    private int id;
    private String danHao;
    private String djTime;
    private String yyTime;
    private String CarCode;
    private String MingCheng;
    private String wxFangShi;
    private String ywLeiBie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDanHao() {
        return danHao;
    }

    public void setDanHao(String danHao) {
        this.danHao = danHao;
    }

    public String getDjTime() {
        return djTime;
    }

    public void setDjTime(String djTime) {
        this.djTime = djTime;
    }

    public String getYyTime() {
        return yyTime;
    }

    public void setYyTime(String yyTime) {
        this.yyTime = yyTime;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getMingCheng() {
        return MingCheng;
    }

    public void setMingCheng(String mingCheng) {
        MingCheng = mingCheng;
    }

    public String getWxFangShi() {
        return wxFangShi;
    }

    public void setWxFangShi(String wxFangShi) {
        this.wxFangShi = wxFangShi;
    }

    public String getYwLeiBie() {
        return ywLeiBie;
    }

    public void setYwLeiBie(String ywLeiBie) {
        this.ywLeiBie = ywLeiBie;
    }
}
