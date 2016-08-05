package com.ldedusoft.ldbm.activity.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.activity.home.HomeActivity;
import com.ldedusoft.ldbm.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.component.customComp.TopBar;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuAddClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuTitleClickListioner;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.view.DragLayout;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class RepairActivity extends BaseActivity implements OnMenuTitleClickListioner,OnMenuAddClickListioner {

    private ArrayList<MenuItem> menuDataList;

    private ListView repairListView ;

    private MenuListAdapter adapter;

    private TopBar repairTopBar;

    private TextView iv_icon;
    private DragLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_repair);
        initTopBar();
        initRepairMenuList();
        initDragLayout();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);

        iv_icon = (TextView) findViewById(R.id.top_bar_menu);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
    }

    private void initTopBar(){
        repairTopBar = (TopBar)findViewById(R.id.repair_top_bar);
        repairTopBar.setTitle(getString(R.string.repair_title));
    }
    private void initRepairMenuList(){
        menuDataList = SysProperty.getInstance().getRepairMenuList();//从系统属性中获取菜单列表。登录时已初始化
        repairListView = (ListView)findViewById(R.id.repair_menu_list);
        adapter = new MenuListAdapter(this,R.layout.ldbm_menu_item,menuDataList);
        repairListView.setAdapter(adapter);
        repairListView.setDividerHeight(1); //分割线粗为1
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
