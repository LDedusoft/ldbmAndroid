package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectCommonAdapter;

import java.util.ArrayList;

/**
 * 选择经手人页面
 * Created by wangjianwei on 2016/6/29.
 */
public class CommonSelect extends BaseActivity {
    private ArrayList<String> listData;
    private ListView selectListView;
    private SelectCommonAdapter adapter;
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_common);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_common_list);
        listData = new ArrayList<String>();
        adapter = new SelectCommonAdapter(this,R.layout.ldbm_selected_common_item,listData);
        selectListView.setAdapter(adapter);
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition", inputListPosition);//表单传来的item位置，返回回去
                intent.putExtra("result", itemValue);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initData(){
        String title = getIntent().getStringExtra("title");
        String selectType = getIntent().getStringExtra("param");
        int resId = 0;
        Class<com.ldedusoft.ldbm.R.array> cls = R.array.class;
        try {
            resId = cls.getDeclaredField(selectType).getInt(null);//通过名称获取资源id
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] array = this.getResources().getStringArray(resId);
        for(String val:array){
            listData.add(val);
        }
        adapter.notifyDataSetChanged();
    }

}
