package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectSalesManAdapter;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import java.util.ArrayList;

/**
 * 选择经手人页面
 * Created by wangjianwei on 2016/6/29.
 */
public class SalesmanSelect extends BaseActivity {
    private ArrayList<SalesMan> listData;
    private ListView selectListView;
    private SelectSalesManAdapter adapter;
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_salesman);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_salesman_list);
        listData = new ArrayList<SalesMan>();
        adapter = new SelectSalesManAdapter(this,R.layout.ldbm_selected_salesman_item,listData);
        selectListView.setAdapter(adapter);
        selectListView.setDividerHeight(1); //分割线粗为1
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SalesMan salesMan = new SalesMan();
                salesMan = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", salesMan);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_Salesman();
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_SalesmanResult);
                        updateListView(result);
                    }
                });
            }

            @Override
            public void onWarning(String warning) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void updateListView(String result){
        listData = InterfaceResault.getPub_SalesmanResult(listData,result);
        adapter.notifyDataSetChanged();
    }
}
