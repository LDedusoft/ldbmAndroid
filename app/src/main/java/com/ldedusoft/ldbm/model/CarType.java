package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * Created by wangjianwei on 2016/7/6.
 * {ID：id；Type：车型；Series：车系；Brand：品牌;PriceOut1:价格}
 */
public class CarType implements Serializable{
    private int ID;
    private String Type;
    private String Series;
    private String Brand;
    private String PriceOut1;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getPriceOut1() {
        return PriceOut1;
    }

    public void setPriceOut1(String priceOut1) {
        PriceOut1 = priceOut1;
    }
}
