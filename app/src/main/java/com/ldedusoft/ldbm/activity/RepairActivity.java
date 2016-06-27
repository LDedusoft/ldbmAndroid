package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.util.InitParamUtil;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class RepairActivity extends BaseActivity {

    private ArrayList<MenuItem> menuDataList;

    private ListView repairListView ;

    private MenuListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_repair);
        initRepairMenuList();
    }

    private void initRepairMenuList(){
        menuDataList = new ArrayList<MenuItem>();
        menuDataList = InitParamUtil.initRepairList();
        repairListView = (ListView)findViewById(R.id.repair_menu_list);
        adapter = new MenuListAdapter(this,R.layout.ldbm_menu_item,menuDataList);
        repairListView.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
