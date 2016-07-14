package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 新配件信息
 * Created by wangjianwei on 2016/7/14.
 * {Name：名称；TypeId：配件类别id；TuHao：图号；CarTypeId：车型id；BrandId：品牌id}
 */
public class Fixing implements Serializable{
    private String Name;
    private String TypeId;
    private String TuHao;
    private String CarTypeId;
    private String BrandId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getTuHao() {
        return TuHao;
    }

    public void setTuHao(String tuHao) {
        TuHao = tuHao;
    }

    public String getCarTypeId() {
        return CarTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        CarTypeId = carTypeId;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }
}
