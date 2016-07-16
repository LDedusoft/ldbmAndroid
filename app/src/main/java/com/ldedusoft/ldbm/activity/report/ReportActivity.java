package com.ldedusoft.ldbm.activity.report;

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
public class ReportActivity extends BaseActivity implements OnMenuTitleClickListioner,OnMenuAddClickListioner {

    private ArrayList<MenuItem> menuDataList;

    private ListView reportListView ;

    private MenuListAdapter adapter;

    private TopBar reportTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_report);
        initTopBar();
        initRepairMenuList();
    }

    private void initTopBar(){
        reportTopBar = (TopBar)findViewById(R.id.report_top_bar);
        reportTopBar.setTitle(getString(R.string.report_title));
    }
    private void initRepairMenuList(){
        menuDataList = SysProperty.getInstance().getRepairMenuList();//从系统属性中获取菜单列表。登录时已初始化
        reportListView = (ListView)findViewById(R.id.report_menu_list);
        adapter = new MenuListAdapter(this,R.layout.ldbm_menu_item,menuDataList);
        reportListView.setAdapter(adapter);
        reportListView.setDividerHeight(1); //分割线粗为1
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
