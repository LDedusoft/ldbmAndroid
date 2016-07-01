package com.ldedusoft.ldbm.util;


import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.MenuItem;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/27.
 */
public class InitParamUtil {


    /**
     * 首页菜单
     * @return
     */
    public static ArrayList<MenuItem> initMenuList(){
        ArrayList<MenuItem> homeMenuList = new ArrayList<MenuItem>();

        MenuItem  menuItem = new MenuItem();
        menuItem.setIconId(R.drawable.list_icon_1);
        menuItem.setMenuTitle("预约维修");
        menuItem.setMenuDescribe("商品采购渠道");
        menuItem.setAllowCreate(true);//是否可以新建
        menuItem.setIsHomeMenu(true);//是否是首页菜单，可以置顶移除
        menuItem.setCreateIntentPath("activity.repair.AppointmentActivity");
        menuItem.setTitleIntentPath("activity.queryActivity.AppointmentQuery");
        menuItem.setValue("YY");
        homeMenuList.add(menuItem);



        MenuItem  menuItem2 = new MenuItem();
        menuItem2.setIconId(R.drawable.list_icon_2);
        menuItem2.setMenuTitle("库存查询1");
        menuItem2.setMenuDescribe("商品售价、剩余库存查询");
        menuItem2.setAllowCreate(false);
        menuItem2.setIsHomeMenu(true);
        homeMenuList.add(menuItem2);

        MenuItem  menuItem3 = new MenuItem();
        menuItem3.setIconId(R.drawable.list_icon_3);
        menuItem3.setMenuTitle("库存查询2");
        menuItem3.setMenuDescribe("商品售价、剩余库存查询");
        menuItem3.setAllowCreate(true);
        menuItem3.setIsHomeMenu(true);
        homeMenuList.add(menuItem3);

        MenuItem  menuItem4 = new MenuItem();
        menuItem4.setIconId(R.drawable.list_icon_4);
        menuItem4.setMenuTitle("库存查询3");
        menuItem4.setMenuDescribe("商品售价、剩余库存查询");
        menuItem4.setAllowCreate(false);
        menuItem4.setIsHomeMenu(true);
        homeMenuList.add(menuItem4);

        MenuItem  menuItem5 = new MenuItem();
        menuItem5.setIconId(R.drawable.list_icon_3);
        menuItem5.setMenuTitle("库存查询4");
        menuItem5.setMenuDescribe("商品售价、剩余库存查询");
        menuItem5.setAllowCreate(false);
        menuItem5.setIsHomeMenu(true);
        homeMenuList.add(menuItem5);

        MenuItem  menuItem6 = new MenuItem();
        menuItem6.setIconId(R.drawable.list_icon_2);
        menuItem6.setMenuTitle("库存查询5");
        menuItem6.setMenuDescribe("商品售价、剩余库存查询");
        menuItem6.setAllowCreate(true);
        menuItem6.setIsHomeMenu(true);
        homeMenuList.add(menuItem6);

        MenuItem  menuItem7 = new MenuItem();
        menuItem7.setIconId(R.drawable.list_icon_2);
        menuItem7.setMenuTitle("库存查询6");
        menuItem7.setMenuDescribe("商品售价、剩余库存查询");
        menuItem7.setAllowCreate(true);
        menuItem7.setIsHomeMenu(true);
        homeMenuList.add(menuItem7);

        MenuItem  menuItem8 = new MenuItem();
        menuItem8.setIconId(R.drawable.list_icon_1);
        menuItem8.setMenuTitle("库存查询7");
        menuItem8.setMenuDescribe("商品售价、剩余库存查询");
        menuItem8.setAllowCreate(false);
        menuItem8.setIsHomeMenu(true);
        homeMenuList.add(menuItem8);

        MenuItem  menuItem9 = new MenuItem();
        menuItem9.setIconId(R.drawable.list_icon_4);
        menuItem9.setMenuTitle("库存查询8");
        menuItem9.setMenuDescribe("商品售价、剩余库存查询");
        menuItem9.setAllowCreate(false);
        menuItem9.setIsHomeMenu(true);
        homeMenuList.add(menuItem9);

        MenuItem  menuItem0 = new MenuItem();
        menuItem0.setIconId(R.drawable.list_icon_4);
        menuItem0.setMenuTitle("库存查询9");
        menuItem0.setMenuDescribe("商品售价、剩余库存查询");
        menuItem0.setAllowCreate(false);
        menuItem0.setIsHomeMenu(true);
        homeMenuList.add(menuItem0);
        return homeMenuList;
    }

    /*维修*/
    public static ArrayList<MenuItem> initRepairList(){
        ArrayList<MenuItem> repairMenuList = new ArrayList<MenuItem>();
        MenuItem  menuItem = new MenuItem();
        menuItem.setIconId(R.drawable.list_icon_1);
        menuItem.setMenuTitle("预约维修");
        menuItem.setMenuDescribe("商品采购渠道");
        menuItem.setAllowCreate(true);
        menuItem.setValue("YY");
        menuItem.setCreateIntentPath("activity.repair.AppointmentActivity");
        menuItem.setTitleIntentPath("activity.queryActivity.AppointmentQuery");
        repairMenuList.add(menuItem);

        MenuItem  menuItem2 = new MenuItem();
        menuItem2.setIconId(R.drawable.list_icon_2);
        menuItem2.setMenuTitle("维修接待");
        menuItem2.setMenuDescribe("商品售价、剩余库存查询");
        menuItem2.setAllowCreate(true);
        menuItem2.setValue("WX");
        repairMenuList.add(menuItem2);

        MenuItem  menuItem3 = new MenuItem();
        menuItem3.setIconId(R.drawable.list_icon_3);
        menuItem3.setMenuTitle("维修预约查询");
        menuItem3.setMenuDescribe("商品售价、剩余库存查询");
        menuItem3.setAllowCreate(false);
        menuItem.setTitleIntentPath("activity.queryActivity.AppointmentQuery");
        repairMenuList.add(menuItem3);

        MenuItem  menuItem4 = new MenuItem();
        menuItem4.setIconId(R.drawable.list_icon_4);
        menuItem4.setMenuTitle("维修接待查询");
        menuItem4.setMenuDescribe("商品售价、剩余库存查询");
        menuItem4.setAllowCreate(false);
        repairMenuList.add(menuItem4);
        return repairMenuList;
    }

    /*维修预约*/
    public static ArrayList<InputItem> initRP_ReceptionNew_YY(){
        ArrayList<InputItem> dataList = new ArrayList<InputItem>();

        InputItem inputItem = new InputItem();
        inputItem.setItemTitle("单号");
        inputItem.setItemId("Number");
        inputItem.setInputType(0);
        inputItem.setDefaultValue("123456789");
        dataList.add(inputItem);

        InputItem inputItem2 = new InputItem();
        inputItem2.setItemTitle("车牌号");
        inputItem2.setItemId("CarCode");
        inputItem2.setInputType(2);
        inputItem2.setValue("A88888");
        inputItem2.setHint("请选择");
        inputItem2.setIntentPath("activity.selectActivity.CarCodeSelect");
        inputItem2.setIntentRequestCode(1);
        dataList.add(inputItem2);

        InputItem inputItem22 = new InputItem();
        inputItem22.setItemTitle("维修类型");
        inputItem22.setItemId("RepairType");
        inputItem22.setInputType(2);
        inputItem22.setValue("");
        inputItem22.setIntentPath("activity.selectActivity.RepairTypeSelect");
        inputItem22.setIntentRequestCode(3);
        dataList.add(inputItem22);

        InputItem inputItem23 = new InputItem();
        inputItem23.setItemTitle("业务类型");
        inputItem23.setItemId("BusinessType");
        inputItem23.setInputType(2);
        inputItem23.setDefaultValue("");
        inputItem23.setIntentPath("activity.selectActivity.TrafficClassSelect");
        inputItem23.setIntentRequestCode(4);

        inputItem23.setHint("请输入");
        dataList.add(inputItem23);

        InputItem inputItem24 = new InputItem();
        inputItem24.setItemTitle("预约时间");
        inputItem24.setItemId("yyTime");
        inputItem24.setInputType(3);
        inputItem24.setDefaultValue("");
        inputItem24.setHint("请选择");
        dataList.add(inputItem24);

        InputItem inputItem25 = new InputItem();
        inputItem25.setItemTitle("业务员");
        inputItem25.setItemId("Salesman");
        inputItem25.setInputType(2);
        inputItem25.setDefaultValue("");
        inputItem25.setIntentPath("activity.selectActivity.SalesmanSelect");
        inputItem25.setIntentRequestCode(4);
        inputItem25.setHint("请输入");
        dataList.add(inputItem25);


        InputItem inputItem26 = new InputItem();
        inputItem26.setItemTitle("弹性时间");
        inputItem26.setItemId("dxTime");
        inputItem26.setInputType(1);
        inputItem26.setDefaultValue("");
        inputItem26.setHint("请输入");
        dataList.add(inputItem26);


        return dataList;
    }
}
