package com.ldedusoft.ldbm.activity.wholeCar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.activity.home.HomeActivity;
import com.ldedusoft.ldbm.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.component.customComp.TopBar;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuAddClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuTitleClickListioner;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class WholecarActivity extends BaseActivity implements OnMenuTitleClickListioner,OnMenuAddClickListioner {
    private ArrayList<MenuItem> menuDataList;

    private ListView listView ;

    private MenuListAdapter adapter;

    private TopBar topBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_wholecar);
        initTopBar();
        initRepairMenuList();
    }

    private void initTopBar(){
        topBar = (TopBar)findViewById(R.id.wholecar_top_bar);
        topBar.setTitle(getString(R.string.wholecar_title));
    }
    private void initRepairMenuList(){
        menuDataList = SysProperty.getInstance().getCarMenuList();//从系统属性中获取菜单列表。登录时已初始化
        listView = (ListView)findViewById(R.id.wholecar_menu_list);
        adapter = new MenuListAdapter(this,R.layout.ldbm_menu_item,menuDataList);
        listView.setAdapter(adapter);
        listView.setDividerHeight(1); //分割线粗为1
        adapter.setOnMenuTitleClickListioner(this);
        adapter.setOnMenuAddClickListioner(this);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
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
}
