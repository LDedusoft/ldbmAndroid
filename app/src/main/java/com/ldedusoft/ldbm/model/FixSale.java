package com.ldedusoft.ldbm.model;

/**
 * Created by wangjianwei on 2016/7/8.
 * Number FormMaker
 * {ClientId：客户编号；SaleMan：经手人；Invoice：发票类型}
 * {Id：配件id；Num：数量；Price：含税单价}
 */
public class FixSale {
    private String ClientId;
    private String SaleMan;
    private String Invoice;
    private String Id;
    private String Num;
    private String Price;
    private String Number;
    private String FormMaker;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getSaleMan() {
        return SaleMan;
    }

    public void setSaleMan(String saleMan) {
        SaleMan = saleMan;
    }

    public String getInvoice() {
        return Invoice;
    }

    public void setInvoice(String invoice) {
        Invoice = invoice;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

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
}
