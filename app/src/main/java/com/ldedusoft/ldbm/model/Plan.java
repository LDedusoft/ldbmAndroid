package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 采购计划
 * Created by wangjianwei on 2016/7/15.
 * {DanHao：单号;RiQi：日期；Num：单据数量；JinE：单据金额;MingCheng:供方名称}
 */
public class Plan implements Serializable{
    public String DanHao;
    public String RiQi;
    public String Num;
    public String JinE;
    public String MingCheng;

}
