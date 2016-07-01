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

    public static InitParamUtil getInstance(Context context){
        return new InitParamUtil(context);
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
    public  ArrayList<InputItem> initRP_ReceptionNew_YY() {
        String config = readConfig("repairYY.txt");
        return createInputItemList(config);
    }

    /*维修接待*/
    public  ArrayList<InputItem> initRP_ReceptionNew_WX() {
        String config = readConfig("repairWX.txt");
        return createInputItemList(config);
    }

    /**
     * 生成输入表单item列表
     * @param config
     * @return
     */
    private ArrayList<InputItem>  createInputItemList(String config){
        ArrayList<InputItem> itemList = new ArrayList<InputItem>();
        try {
            JSONArray jsonArray = new JSONArray(config);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                InputItem inputItem = new InputItem();
                inputItem.setItemTitle(jsonObject.getString("itemTitle"));
                inputItem.setValue(jsonObject.getString("value"));
                inputItem.setHint(jsonObject.getString("hint"));
                inputItem.setDefaultValue(jsonObject.getString("defaultValue"));
                inputItem.setInputType(jsonObject.getInt("inputType"));
                inputItem.setIntentPath(jsonObject.getString("intentPath"));
                inputItem.setIntentRequestCode(jsonObject.getInt("intentRequestCode"));
                inputItem.setItemId(jsonObject.getString("itemId"));
                itemList.add(inputItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
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


}
