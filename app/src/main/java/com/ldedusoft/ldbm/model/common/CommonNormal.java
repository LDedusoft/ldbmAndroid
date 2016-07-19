package com.ldedusoft.ldbm.model.common;

import java.io.Serializable;

/**
 * 简单通用模型
 * Created by wangjianwei on 2016/7/18.
 */
public class CommonNormal implements Serializable {
    public  String name1;
    public String name2;
    public String value1;
    public String value2;
    public boolean details; //是否有详情
    public String dataSource; //当前节点数据

}
