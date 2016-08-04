package com.ldedusoft.ldbm.activity.wholeCar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.MenuListAdapter;
import com.ldedusoft.ldbm.component.customComp.FormToolBar;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuAddClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuTitleClickListioner;
import com.ldedusoft.ldbm.interfaces.FormToolBarListener;
import com.ldedusoft.ldbm.model.MenuItem;
import com.ldedusoft.ldbm.model.SysProperty;

import java.util.ArrayList;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class NegotiateActivity extends BaseActivity implements OnMenuTitleClickListioner,OnMenuAddClickListioner {
    private ArrayList<MenuItem> menuDataList;

    private ListView listView ;

    private MenuListAdapter adapter;

    private FormToolBar topBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_negotiate);
        initTopBar();
        initRepairMenuList();
    }

    private void initTopBar(){
        topBar = (FormToolBar)findViewById(R.id.negotiate_top_bar);
        topBar.setTitle(getString(R.string.negotiate_title));
        topBar.hiddenSvaeBtn();
        topBar.setFormToolBarListener(new FormToolBarListener() {
            @Override
            public void OnSaveClick() {

            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }
    private void initRepairMenuList(){
        menuDataList = SysProperty.getInstance().getNegotiateMenuList();//从系统属性中获取菜单列表。登录时已初始化
        listView = (ListView)findViewById(R.id.negotiate_menu_list);
        adapter = new MenuListAdapter(this,R.layout.ldbm_menu_item,menuDataList);
        listView.setAdapter(adapter);
        listView.setDividerHeight(1); //分割线粗为1
        adapter.setOnMenuTitleClickListioner(this);
        adapter.setOnMenuAddClickListioner(this);

    }


    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this,HomeActivity.class);
//        startActivity(intent);
        finish();
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
}
