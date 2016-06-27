package com.ldedusoft.ldbm.util;


import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.MenuItem;
import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/27.
 */
public class InitParamUtil {

    public static ArrayList<MenuItem> initMenuList(){
        ArrayList<MenuItem> homeMenuList = new ArrayList<MenuItem>();
        MenuItem  menuItem = new MenuItem();
        menuItem.setIconId(R.drawable.list_icon_1);
        menuItem.setMenuTitle("供应商");
        menuItem.setMenuDescribe("商品采购渠道");
        menuItem.setAllowCreate(true);
        homeMenuList.add(menuItem);

        MenuItem  menuItem2 = new MenuItem();
        menuItem2.setIconId(R.drawable.list_icon_2);
        menuItem2.setMenuTitle("库存查询1");
        menuItem2.setMenuDescribe("商品售价、剩余库存查询");
        menuItem2.setAllowCreate(false);
        homeMenuList.add(menuItem2);

        MenuItem  menuItem3 = new MenuItem();
        menuItem3.setIconId(R.drawable.list_icon_3);
        menuItem3.setMenuTitle("库存查询2");
        menuItem3.setMenuDescribe("商品售价、剩余库存查询");
        menuItem3.setAllowCreate(true);
        homeMenuList.add(menuItem3);

        MenuItem  menuItem4 = new MenuItem();
        menuItem4.setIconId(R.drawable.list_icon_4);
        menuItem4.setMenuTitle("库存查询3");
        menuItem4.setMenuDescribe("商品售价、剩余库存查询");
        menuItem4.setAllowCreate(false);
        homeMenuList.add(menuItem4);

        MenuItem  menuItem5 = new MenuItem();
        menuItem5.setIconId(R.drawable.list_icon_3);
        menuItem5.setMenuTitle("库存查询4");
        menuItem5.setMenuDescribe("商品售价、剩余库存查询");
        menuItem5.setAllowCreate(false);
        homeMenuList.add(menuItem5);

        MenuItem  menuItem6 = new MenuItem();
        menuItem6.setIconId(R.drawable.list_icon_2);
        menuItem6.setMenuTitle("库存查询5");
        menuItem6.setMenuDescribe("商品售价、剩余库存查询");
        menuItem6.setAllowCreate(true);
        homeMenuList.add(menuItem6);

        MenuItem  menuItem7 = new MenuItem();
        menuItem7.setIconId(R.drawable.list_icon_2);
        menuItem7.setMenuTitle("库存查询6");
        menuItem7.setMenuDescribe("商品售价、剩余库存查询");
        menuItem7.setAllowCreate(true);
        homeMenuList.add(menuItem7);

        MenuItem  menuItem8 = new MenuItem();
        menuItem8.setIconId(R.drawable.list_icon_1);
        menuItem8.setMenuTitle("库存查询7");
        menuItem8.setMenuDescribe("商品售价、剩余库存查询");
        menuItem8.setAllowCreate(false);
        homeMenuList.add(menuItem8);

        MenuItem  menuItem9 = new MenuItem();
        menuItem9.setIconId(R.drawable.list_icon_4);
        menuItem9.setMenuTitle("库存查询8");
        menuItem9.setMenuDescribe("商品售价、剩余库存查询");
        menuItem9.setAllowCreate(false);
        homeMenuList.add(menuItem9);

        MenuItem  menuItem0 = new MenuItem();
        menuItem0.setIconId(R.drawable.list_icon_4);
        menuItem0.setMenuTitle("库存查询9");
        menuItem0.setMenuDescribe("商品售价、剩余库存查询");
        menuItem0.setAllowCreate(false);
        homeMenuList.add(menuItem0);
        return homeMenuList;
    }

    public static ArrayList<MenuItem> initRepairList(){
        ArrayList<MenuItem> repairMenuList = new ArrayList<MenuItem>();
        MenuItem  menuItem = new MenuItem();
        menuItem.setIconId(R.drawable.list_icon_1);
        menuItem.setMenuTitle("预约维修");
        menuItem.setMenuDescribe("商品采购渠道");
        menuItem.setAllowCreate(true);
        repairMenuList.add(menuItem);

        MenuItem  menuItem2 = new MenuItem();
        menuItem2.setIconId(R.drawable.list_icon_2);
        menuItem2.setMenuTitle("维修接待");
        menuItem2.setMenuDescribe("商品售价、剩余库存查询");
        menuItem2.setAllowCreate(true);
        repairMenuList.add(menuItem2);

        MenuItem  menuItem3 = new MenuItem();
        menuItem3.setIconId(R.drawable.list_icon_3);
        menuItem3.setMenuTitle("维修预约查询");
        menuItem3.setMenuDescribe("商品售价、剩余库存查询");
        menuItem3.setAllowCreate(false);
        repairMenuList.add(menuItem3);

        MenuItem  menuItem4 = new MenuItem();
        menuItem4.setIconId(R.drawable.list_icon_4);
        menuItem4.setMenuTitle("维修接待查询");
        menuItem4.setMenuDescribe("商品售价、剩余库存查询");
        menuItem4.setAllowCreate(false);
        repairMenuList.add(menuItem4);
        return repairMenuList;
    }
}
