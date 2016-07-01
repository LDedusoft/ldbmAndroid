package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectRepairTypeAdapter;
import com.ldedusoft.ldbm.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.RepaireType;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import java.util.ArrayList;

/**
 * 选择经手人页面
 * Created by wangjianwei on 2016/6/29.
 */
public class RepairTypeSelect extends BaseActivity {
    private ArrayList<RepaireType> listData;
    private ListView selectListView;
    private SelectRepairTypeAdapter adapter;
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_repairtype);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_repairType_list);
        listData = new ArrayList<RepaireType>();
        adapter = new SelectRepairTypeAdapter(this,R.layout.ldbm_selected_repairtype_item,listData);
        selectListView.setAdapter(adapter);
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RepaireType repaireType = new RepaireType();
                repaireType = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",repaireType);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getRP_RepaireType(); //接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.RP_RepaireTypeResult);
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
        //接口返回值解析
        listData = InterfaceResault.getRP_RepaireType(listData, result);
        adapter.notifyDataSetChanged();
    }
}
