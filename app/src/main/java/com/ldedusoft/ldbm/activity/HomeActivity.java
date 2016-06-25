package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class HomeActivity extends BaseActivity {

    private TextView userName;
    private TextView userType;
    private TextView sysmode;

    private List<MenuItem> menuDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_home);

        initMenuListData();
        MenuListAdapter adapter = new MenuListAdapter(HomeActivity.this,R.layout.ldbm_menu_item,menuDataList);
        ListView menuListView = (ListView)findViewById(R.id.home_menu_list);
        menuListView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initMenuListData(){
        menuDataList = new ArrayList<MenuItem>();
        MenuItem  menuItem = new MenuItem();
        menuItem.setIconId(R.drawable.list_icon_1);
        menuItem.setMenuTitle("供应商");
        menuItem.setMenuDescribe("商品采购渠道");
        menuItem.setAllowCreate(true);
        menuDataList.add(menuItem);

        MenuItem  menuItem2 = new MenuItem();
        menuItem2.setIconId(R.drawable.list_icon_2);
        menuItem2.setMenuTitle("库存查询");
        menuItem2.setMenuDescribe("商品售价、剩余库存查询");
        menuItem2.setAllowCreate(false);
        menuDataList.add(menuItem2);

        MenuItem  menuItem3 = new MenuItem();
        menuItem3.setIconId(R.drawable.list_icon_3);
        menuItem3.setMenuTitle("库存查询");
        menuItem3.setMenuDescribe("商品售价、剩余库存查询");
        menuItem3.setAllowCreate(true);
        menuDataList.add(menuItem3);

        MenuItem  menuItem4 = new MenuItem();
        menuItem4.setIconId(R.drawable.list_icon_4);
        menuItem4.setMenuTitle("库存查询");
        menuItem4.setMenuDescribe("商品售价、剩余库存查询");
        menuItem4.setAllowCreate(false);
        menuDataList.add(menuItem4);

        MenuItem  menuItem5 = new MenuItem();
        menuItem5.setIconId(R.drawable.list_icon_3);
        menuItem5.setMenuTitle("库存查询");
        menuItem5.setMenuDescribe("商品售价、剩余库存查询");
        menuItem5.setAllowCreate(false);
        menuDataList.add(menuItem5);

        MenuItem  menuItem6 = new MenuItem();
        menuItem6.setIconId(R.drawable.list_icon_2);
        menuItem6.setMenuTitle("库存查询");
        menuItem6.setMenuDescribe("商品售价、剩余库存查询");
        menuItem6.setAllowCreate(true);
        menuDataList.add(menuItem6);

        MenuItem  menuItem7 = new MenuItem();
        menuItem7.setIconId(R.drawable.list_icon_2);
        menuItem7.setMenuTitle("库存查询");
        menuItem7.setMenuDescribe("商品售价、剩余库存查询");
        menuItem7.setAllowCreate(true);
        menuDataList.add(menuItem7);

        MenuItem  menuItem8 = new MenuItem();
        menuItem8.setIconId(R.drawable.list_icon_1);
        menuItem8.setMenuTitle("库存查询");
        menuItem8.setMenuDescribe("商品售价、剩余库存查询");
        menuItem8.setAllowCreate(false);
        menuDataList.add(menuItem8);

        MenuItem  menuItem9 = new MenuItem();
        menuItem9.setIconId(R.drawable.list_icon_4);
        menuItem9.setMenuTitle("库存查询");
        menuItem9.setMenuDescribe("商品售价、剩余库存查询");
        menuItem9.setAllowCreate(false);
        menuDataList.add(menuItem9);

        MenuItem  menuItem0 = new MenuItem();
        menuItem0.setIconId(R.drawable.list_icon_4);
        menuItem0.setMenuTitle("库存查询");
        menuItem0.setMenuDescribe("商品售价、剩余库存查询");
        menuItem0.setAllowCreate(false);
        menuDataList.add(menuItem0);

        menuDataList.add(null);




    }
}
