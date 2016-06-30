package com.ldedusoft.ldbm.model;

/**
 * 单据
 * Created by wangjianwei on 2016/6/30.
 */
public class Bill {
    private String DanHao;
    private String FormMaker;

    public String getDanHao() {
        return DanHao;
    }

    public void setDanHao(String danHao) {
        DanHao = danHao;
    }

    public String getFormMaker() {
        return FormMaker;
    }

    public void setFormMaker(String formMaker) {
        FormMaker = formMaker;
    }
}
