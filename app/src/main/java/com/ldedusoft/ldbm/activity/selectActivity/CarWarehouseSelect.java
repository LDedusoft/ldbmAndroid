package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectCarWarehouseAdapter;
import com.ldedusoft.ldbm.model.CarWarehouse;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 车型选择
 * Created by wangjianwei on 2016/6/29.
 */
public class CarWarehouseSelect extends BaseActivity {
    private ArrayList<CarWarehouse> listData;
    private ListView carCodeListView;
    private SelectCarWarehouseAdapter adapter; //!!
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_carwarehouse);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        carCodeListView = (ListView)findViewById(R.id.selected_carwarehouse_list);
        listData = new ArrayList<CarWarehouse>();
        adapter = new SelectCarWarehouseAdapter(this,R.layout.ldbm_selected_carwarehouse_item,listData);
        carCodeListView.setAdapter(adapter);
        carCodeListView.setDividerHeight(1); //分割线粗为1
        carCodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarWarehouse ch = new CarWarehouse();
                ch = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",ch);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_CarWarehouseList();
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_CarWarehouseListResult);
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
        InterfaceResault.getPub_CarWarehouseListResult(listData,result);
        adapter.notifyDataSetChanged();
    }
}
