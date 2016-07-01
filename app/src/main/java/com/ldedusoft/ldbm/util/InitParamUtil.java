package com.ldedusoft.ldbm.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.UserProperty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/27.
 */
public class InitParamUtil {

    private SharedPreferences pref; //保存文件
    private Context mContent;
    public InitParamUtil(Context context){
     mContent = context;
    }
    /**
     * 从assets中读取txt
     */
    private String readConfig(String fileName) {
        String content = "";
        try {
            InputStream is = mContent.getAssets().open(fileName);
            content = readTextFromFile(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private String readTextFromFile(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /*初始化全部菜单*/
    public void initAllMenuList() {
        ArrayList<MenuItem> allMenuList = new ArrayList<MenuItem>();
       String config = readConfig("allMenuItem.txt");
        try {
            JSONArray jsonArray = new JSONArray(config);
            int length = jsonArray.length();
            for (int i=0;i<length;i++){
                MenuItem menuItem = new MenuItem();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Class<com.ldedusoft.ldbm.R.drawable> cls = R.drawable.class;
                int iconId = cls.getDeclaredField(jsonObject.getString("iconId")).getInt(null);
                menuItem.setMenuTitle(jsonObject.getString("menuTitle"));
                menuItem.setMenuDescribe(jsonObject.getString("menuDescribe"));
                menuItem.setIconId(iconId);
                menuItem.setAllowCreate(jsonObject.getBoolean("allowCreate"));
                menuItem.setValue(jsonObject.getString("value"));
                menuItem.setTitleIntentPath(jsonObject.getString("titleIntentPath"));
                menuItem.setCreateIntentPath(jsonObject.getString("createIntentPath"));
                allMenuList.add(menuItem);
            }
            SysProperty.getInstance().setAllMenuList(allMenuList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*初始化首页菜单*/
    public void initHomeMenuList() {
        pref = PreferenceManager.getDefaultSharedPreferences(mContent);
        ArrayList<MenuItem> homeMenuItemList = new ArrayList<MenuItem>();
        ArrayList<MenuItem> allMenuItemList = SysProperty.getInstance().getAllMenuList();
        String userItemsStr = pref.getString(UserProperty.getInstance().getUserName(), "");//获取用户配置
        String[] userItemsArray = mContent.getResources().getStringArray(R.array.home_menu_item);//从配置中获取默认的菜单项
        if(!TextUtils.isEmpty(userItemsStr)){  //如果用户已修改菜单，则使用用户配置
            userItemsArray = userItemsStr.split(","); //用户配置文件中保存的菜单项
        }
        for (String itemName : userItemsArray) {
            for (MenuItem itemObj : allMenuItemList ) {
                if (itemName.equals(itemObj.getMenuTitle())) {
                    itemObj.setIsHomeMenu(true); //加入首页时需要设置标志
                    homeMenuItemList.add(itemObj);
                    break;
                }
            }
        }
        homeMenuItemList.add(null); //添加快捷功能按钮
        SysProperty.getInstance().setHomeMenuList(homeMenuItemList);
    }

    /*初始化维修页面菜单*/
    public void initRepairMenuList() {
        ArrayList<MenuItem> repairMenuItemList = new ArrayList<MenuItem>();
        ArrayList<MenuItem> allMenuItemList = SysProperty.getInstance().getAllMenuList();
        String[] userItemsArray = mContent.getResources().getStringArray(R.array.repair_menu_item);//从配置中获取默认的菜单项
        for (String itemName : userItemsArray) {
            for (MenuItem itemObj : allMenuItemList ) {
                if (itemName.equals(itemObj.getMenuTitle())) {
                    repairMenuItemList.add(itemObj);
                    break;
                }
            }
        }
        SysProperty.getInstance().setRepairMenuList(repairMenuItemList);
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
