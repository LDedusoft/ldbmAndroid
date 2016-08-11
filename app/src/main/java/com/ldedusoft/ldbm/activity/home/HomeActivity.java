package com.ldedusoft.ldbm.activity.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ldedusoft.ldbm.Application.MyApplication;
import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
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
import com.ldedusoft.ldbm.util.ActivityCollector;
import com.ldedusoft.ldbm.util.InitParamUtil;
import com.ldedusoft.ldbm.view.DragLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class HomeActivity extends BaseActivity implements OnDeleteListioner,OnSettopListioner, ListViewonSingleTapUpListenner,OnMenuAddClickListioner,OnMenuTitleClickListioner,OnMenuShortcutClickListioner{

    private DragLayout dl;
    private DelSlideListView menuListView;

    private MenuListAdapter adapter;

    private SharedPreferences pref; //保存文件

    private SharedPreferences.Editor editor;

    private boolean exitFlag;

    private ImageView iv_icon;
    private ListView lv;

    private ArrayList<MenuItem> menuDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_home);
        MyApplication myApplication = MyApplication.getInstance();
        myApplication.setContext(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        exitFlag = false;
        initDragLayout();
        initMenuListData();
        initListView();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        /*右侧缩小动画*/
//        lv =  lv = (ListView) findViewById(R.id.lv);
//        dl.setDragListener(new DragLayout.DragListener() {
//            @Override
//            public void onOpen() {
//                lv.smoothScrollToPosition(new Random().nextInt(30));
//            }
//
//            @Override
//            public void onClose() {
//                shake();
//            }
//
//
//            @Override
//            public void onDrag(float percent) {
//                ViewHelper.setAlpha(iv_icon, 1 - percent);
//            }
//        });

        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
    }

    /*图片抖动*/
    private void shake() {
//        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
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
        menuListView.setDividerHeight(1); //分割线粗为1
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
        String title = menuDataList.get(ID).getMenuTitle();
        String value = menuDataList.get(ID).getValue();
        String intentPath =  menuDataList.get(ID).getTitleIntentPath();
        if(!TextUtils.isEmpty(intentPath)) {
            Intent intent = new Intent(intentPath);
            intent.putExtra("param", value);
            intent.putExtra("title",title);
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
        if(!exitFlag){
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            exitFlag = true; //设置为可以退出
            //延时两秒后，设置为false。 两秒内有效
            TimerTask task = new TimerTask(){
                public void run(){
                    exitFlag = false;
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }else{
            ActivityCollector.finishAll();
        }
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
        menuListView.refresh();
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


    @Override
    public void OnMenuShortcutClick(int ID) {
        Intent intent = new Intent("activity.AllMenuListActivity");
        startActivityForResult(intent, 1);
//        Toast.makeText(this,"快捷功能",Toast.LENGTH_SHORT).show();
    }

    private void initMenuListData(){
        InitParamUtil initParam = new InitParamUtil(this);
        if(SysProperty.getInstance().getAllMenuList() == null) {
            initParam.initAllMenuList();//初始化菜单项,只初始一次
            initParam.initMenu();//初始化每页的菜单
        }else {
            initParam.initHomeMenuList();//根据用户配置重新生成首页菜单，每次访问首页执行
        }
        menuDataList = SysProperty.getInstance().getHomeMenuList();
    }
    /**
     * 活动返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: //快捷菜单
                if(resultCode == RESULT_OK) {
                    String selectedMenu = data.getStringExtra("result");
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    updateMenuList(selectedMenu);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加快捷方式返回更新列表
     * @param selectedMenu
     */
    private void updateMenuList(String selectedMenu){
        ArrayList<String> nowMenuList = getNowMenuList();
        ArrayList<String> selectMenuList =  strToList(selectedMenu);
        ArrayList<String> delMenus = getDelMenus(nowMenuList,selectMenuList);
        ArrayList<String> addMenus = getAddMenus(nowMenuList,selectMenuList);
        doDelMenus(delMenus);
        doAddMenus(addMenus);
        menuListView.refresh();
        adapter.notifyDataSetChanged();
        //TODO-更新用户配置
        updateUserConfig();
    }

    /**
     * 删除菜单项
     * @param delMenus
     */
    private void doDelMenus(ArrayList<String> delMenus){
//        for(String menuName :delMenus){
//            for(MenuItem menuItem:menuDataList){
//                if(menuItem!=null){
//                    if(menuName.equals(menuItem.getMenuTitle())) {
//                        menuDataList.remove(menuItem);
//                    }
//                }
//            }
//        }
        for(String menuName :delMenus){
            for(int i=menuDataList.size()-1;i>=0;i--){
                if(menuDataList.get(i)!=null) {
                    MenuItem menuItem = menuDataList.get(i);
                    if (menuName.equals(menuItem.getMenuTitle())) {
                        menuDataList.remove(i);
                    }
                }
            }
        }
    }

    /**
     * 添加菜单
     * @param addMenus
     */
    private void doAddMenus(ArrayList<String> addMenus){
        for (String itemName : addMenus) {
            ArrayList<MenuItem> sysAllMenu = SysProperty.getInstance().getAllMenuList();
            for (MenuItem itemObj : sysAllMenu) {
                if (itemName.equals(itemObj.getMenuTitle())) {
                    menuDataList.add(menuDataList.size()-1,itemObj);
                    break;
                }
            }
        }
    }

    /**
     * 获取当前菜单列表
     * @return
     */
    private ArrayList<String> getNowMenuList(){
        ArrayList<String> nowMenuList = new ArrayList<String>();
        for(MenuItem item:menuDataList){
            if(item!=null) {
                nowMenuList.add(item.getMenuTitle());
            }
        }
        return  nowMenuList;
    }

    /**
     * 字符串转list
     * @param menuStr
     * @return
     */
    private ArrayList<String> strToList(String menuStr){
        ArrayList<String> selectMenuList = new ArrayList<String>();
        String[] menuArray = menuStr.split(",");
        for(String menu:menuArray){
            selectMenuList.add(menu);
        }
        return selectMenuList;
    }

    /**
     * 获取需要删除的菜单
     * @param nowMenuList
     * @param selectMenuList
     * @return
     */
    private ArrayList<String> getDelMenus(ArrayList<String> nowMenuList,ArrayList<String> selectMenuList){
        ArrayList<String> delMenus = new ArrayList<String>();
        for(String item:nowMenuList){
            if(!selectMenuList.contains(item)){
                delMenus.add(item);
            }
        }
        return delMenus;
    }

    /**
     * 要添加的菜单
     * @param nowMenuList
     * @param selectMenuList
     * @return
     */
    private ArrayList<String> getAddMenus(ArrayList<String> nowMenuList,ArrayList<String> selectMenuList){
        ArrayList<String> addMenus = new ArrayList<String>();
        for(String item:selectMenuList){
            if(!nowMenuList.contains(item)){
                addMenus.add(item);
            }
        }
        return addMenus;
    }


}
