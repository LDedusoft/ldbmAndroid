package com.ldedusoft.ldbm.activity.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.activity.LoginActivity;
import com.ldedusoft.ldbm.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.component.widge.sideslip.DelSlideListView;
import com.ldedusoft.ldbm.component.widge.sideslip.ListViewonSingleTapUpListenner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnDeleteListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuAddClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuShortcutClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuTitleClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnSettopListioner;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.InitParamUtil;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class HomeActivity extends BaseActivity implements OnDeleteListioner,OnSettopListioner, ListViewonSingleTapUpListenner,OnMenuAddClickListioner,OnMenuTitleClickListioner,OnMenuShortcutClickListioner{

    private DelSlideListView menuListView;

    private MenuListAdapter adapter;

    private SharedPreferences pref; //保存文件

    private SharedPreferences.Editor editor;

    private ArrayList<MenuItem> menuDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_home);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        initMenuListData();
        initListView();
    }

    /**
     * 初始化列表
     */
    private void initListView(){
        adapter = new MenuListAdapter(HomeActivity.this,R.layout.ldbm_menu_item,menuDataList);
        menuListView = (DelSlideListView)findViewById(R.id.home_menu_list);
        menuListView.setDeleteListioner(this);
        menuListView.setSettopListioner(this);
        menuListView.setSingleTapUpListenner(this);
        adapter.setOnDeleteListioner(this);
        adapter.setmOnSettopListioner(this);
        adapter.setOnMenuTitleClickListioner(this);
        adapter.setOnMenuAddClickListioner(this);
        adapter.setOnMenuShortcutClickListioner(this);
        menuListView.setAdapter(adapter);
        adapter.setOnMenuTitleClickListioner(this);
        adapter.setOnMenuAddClickListioner(this);
    }

    @Override
    public void OnMenuTitleClick(int ID) {
        String value = menuDataList.get(ID).getValue();
        String intentPath =  menuDataList.get(ID).getTitleIntentPath();
        if(!TextUtils.isEmpty(intentPath)) {
            Intent intent = new Intent(intentPath);
            intent.putExtra("param", value);
            startActivity(intent);
        }
    }

    @Override
    public void OnMenuAddClick(int ID) {
        String value = menuDataList.get(ID).getValue();
        String intentPath =  menuDataList.get(ID).getCreateIntentPath();
        if(!TextUtils.isEmpty(intentPath)) {
            Intent intent = new Intent(intentPath);
            intent.putExtra("param", value);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSingleTapUp() {

    }


    @Override
    public boolean isCandelete(int position) {
        return true;
    }


    /**
     * 列表项删除
     * @param ID
     */
    @Override
    public void onDelete(int ID) {
        menuDataList.remove(ID);
        menuListView.deleteItem();
        adapter.notifyDataSetChanged();
        //暂存到全局变量
       // SysProperty.getInstance().setHomeMenuList(menuDataList);
        //TODO-更新用户配置
        updateUserConfig();
    }

    /**
     * 列表项置顶
     * @param ID
     */
    @Override
    public void onSettop(int ID) {
        MenuItem item = menuDataList.get(ID);
        menuDataList.remove(ID);
        menuDataList.add(0, item);
        menuListView.settopItem();
        adapter.notifyDataSetChanged();
        //暂存到全局变量
       // SysProperty.getInstance().setHomeMenuList(menuDataList);
        //TODO-更新用户配置
        updateUserConfig();
    }

    /**
     * 更新用户配置
     */
    private void updateUserConfig(){
        StringBuilder menuItems = new StringBuilder();
        for(MenuItem item: menuDataList){
            if(item!=null) {
                menuItems.append(item.getMenuTitle());
                menuItems.append(",");
            }
        }
        String menuItemsStr = menuItems.toString();
        if(!TextUtils.isEmpty(menuItemsStr)){
            menuItemsStr =  menuItemsStr.substring(0,menuItemsStr.length()-1);
        }
        editor = pref.edit();
        editor.putString(UserProperty.getInstance().getUserName(), menuItems.toString());
        editor.commit();
    }

    @Override
    public void onBack() {

    }

//    @Override
//    public void OnMenuTitleClick(int ID) {
//        Toast.makeText(this,"选中标题"+menuDataList.get(ID).getMenuTitle(),Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void OnMenuAddClick(int ID) {
//        Toast.makeText(this,"添加"+menuDataList.get(ID).getMenuTitle(),Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void OnMenuShortcutClick(int ID) {
        Toast.makeText(this,"快捷功能",Toast.LENGTH_SHORT).show();
    }

    private void initMenuListData(){
        InitParamUtil initParam = new InitParamUtil(this);
        if(SysProperty.getInstance().getAllMenuList() == null) {
            initParam.initAllMenuList();
            initParam.initHomeMenuList();
            initParam.initRepairMenuList();
        }else {
            initParam.initHomeMenuList();
        }
        menuDataList = SysProperty.getInstance().getHomeMenuList();
//
//
//        menuDataList = new ArrayList<MenuItem>();
//        ArrayList<MenuItem> allMenuItemList = InitParamUtil.initMenuList();//初始化菜单
//        SysProperty.getInstance().setHomeMenuList(allMenuItemList);//全部菜单项设置到系统属性
//        String userItemsStr = pref.getString(UserProperty.getInstance().getUserName(), "");//获取用户配置
//        String[] userItemsArray = this.getResources().getStringArray(R.array.home_menu_item);//从配置中获取默认的菜单项
//        if(!TextUtils.isEmpty(userItemsStr)){
//            userItemsArray = userItemsStr.split(","); //用户配置文件中保存的菜单项
//        }
//            for (String itemName : userItemsArray) {
//                for (MenuItem itemObj : allMenuItemList ) {
//                    if (itemName.equals(itemObj.getMenuTitle())) {
//                        menuDataList.add(itemObj);
//                        break;
//                    }
//                }
//            }
//
//        menuDataList.add(null); //添加快捷功能按钮




    }
}
