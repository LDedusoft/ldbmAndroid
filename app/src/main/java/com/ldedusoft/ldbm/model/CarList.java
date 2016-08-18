package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 在库车辆
 * Created by wangjianwei on 2016/7/8.
 * {CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；
 * Brand：品牌；Series：车系；Type：车型}
 */
public class CarList implements Serializable {
    private String CarCode;
    private String Color;
    private String DanJia;
    private String Vin;
    private String EngineNo;
    private String Brand;
    private String Series;
    private String Type;

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDanJia() {
        return DanJia;
    }

    public void setDanJia(String danJia) {
        DanJia = danJia;
    }

    public String getVin() {
        return Vin;
    }

    public void setVin(String vin) {
        Vin = vin;
    }

    public String getEngineNo() {
        return EngineNo;
    }

    public void setEngineNo(String engineNo) {
        EngineNo = engineNo;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
