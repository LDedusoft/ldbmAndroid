package com.ldedusoft.ldbm.activity.queryActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.QueryAppointmentAdapter;
import com.ldedusoft.ldbm.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import java.util.ArrayList;

/**
 * 选择业务类别
 * Created by wangjianwei on 2016/6/29.
 */
public class AppointmentQuery extends BaseActivity {
    private ArrayList<Appointment> listData; //!!
    private ListView selectListView;
    private QueryAppointmentAdapter adapter; //!!
    private int inputListPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_appointment);//!!
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.query_appointment_list); //!!
        listData = new ArrayList<Appointment>();//!!
        adapter = new QueryAppointmentAdapter(this,R.layout.ldbm_query_appointment_item,listData);//!!
        selectListView.setAdapter(adapter);
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //TODO 点击事件
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getAP_AppointmentList(); //!!接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //!!接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.AP_AppointmentListResult);
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
        //!!接口返回值解析
        listData = InterfaceResault.getAP_AppointmentListResult(listData, result);
        adapter.notifyDataSetChanged();
    }
}