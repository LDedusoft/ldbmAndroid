package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectCarColorAdapter;
import com.ldedusoft.ldbm.model.CarColor;
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
public class CarColorSelect extends BaseActivity {
    private ArrayList<CarColor> listData;
    private ListView carCodeListView;
    private SelectCarColorAdapter adapter; //!!
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_carcolor);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        carCodeListView = (ListView)findViewById(R.id.selected_carcolor_list);
        listData = new ArrayList<CarColor>();
        adapter = new SelectCarColorAdapter(this,R.layout.ldbm_selected_carcolor_item,listData);
        carCodeListView.setAdapter(adapter);
        carCodeListView.setDividerHeight(1); //分割线粗为1
        carCodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarColor cl = new CarColor();
                cl = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",cl);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_CarColorList();
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_CarColorListResult);
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
        InterfaceResault.getPub_CarColorListResult(listData,result);
        adapter.notifyDataSetChanged();
    }
}
