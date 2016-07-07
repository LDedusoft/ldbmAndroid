package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectClientCategoryAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.ClientCategory;

import java.util.ArrayList;

/**
 * 选择客户信息
 * Created by wangjianwei on 2016/6/29.
 */
public class ClientCategorySelect extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ArrayList<ClientCategory> listData;
    private ListView selectListView;
    private SelectClientCategoryAdapter adapter;
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_client_category);//!!
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
        initQueryToolBar();
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.selected_client_category_toolbar);
//        queryToolBar.showAddBtn();
//        queryToolBar.setTitle(this.getResources().getString(R.string.carcode_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_client_category_list); //!!
        listData = new ArrayList<ClientCategory>();
        adapter = new SelectClientCategoryAdapter(this,R.layout.ldbm_selected_client_category_item,listData);//!!
        selectListView.setAdapter(adapter);
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientCategory clientCategory = new ClientCategory();
                clientCategory = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition", inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", clientCategory);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initData(){
        ClientCategory item1 = new ClientCategory();
        item1.setCategoryId(2);
        item1.setCategoryName("客户");
        ClientCategory item2 = new ClientCategory();
        item2.setCategoryId(1);
        item2.setCategoryName("供应商");
        ClientCategory item3 = new ClientCategory();
        item3.setCategoryId(0);
        item3.setCategoryName("两者都是");
        listData.add(item1);
        listData.add(item2);
        listData.add(item3);
        adapter.notifyDataSetChanged();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                break;
            default:
                break;
        }
    }

}
