package com.ldedusoft.ldbm.model;

/**
 * 进度查询
 * Created by wangjianwei on 2016/7/4.
 * DanHao：单号；JcDate：进出日期；JdDate：接待日期；WgDate：完工日期；
 * JinDu：进度；MingCheng：客户名称；CarCode：车牌号
 */
public class Progress {
    private String DanHao;
    private String JcDate;
    private String JdDate;
    private String WgDate;
    private String JinDu;
    private String MingCheng;
    private String CarCode;

    public String getDanHao() {
        return DanHao;
    }

    public void setDanHao(String danHao) {
        DanHao = danHao;
    }

    public String getJcDate() {
        return JcDate;
    }

    public void setJcDate(String jcDate) {
        JcDate = jcDate;
    }

    public String getJdDate() {
        return JdDate;
    }

    public void setJdDate(String jdDate) {
        JdDate = jdDate;
    }

    public String getWgDate() {
        return WgDate;
    }

    public void setWgDate(String wgDate) {
        WgDate = wgDate;
    }

    public String getJinDu() {
        return JinDu;
    }

    public void setJinDu(String jinDu) {
        JinDu = jinDu;
    }

    public String getMingCheng() {
        return MingCheng;
    }

    public void setMingCheng(String mingCheng) {
        MingCheng = mingCheng;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }
}
