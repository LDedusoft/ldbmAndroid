package com.ldedusoft.ldbm.activity.queryActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.QueryAppointmentYYAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import java.util.ArrayList;

/**
 * 选择业务类别
 * Created by wangjianwei on 2016/6/29.
 */
public class AppointmentQueryYY extends BaseActivity {
    private ArrayList<Appointment> listData; //!!
    private ListView selectListView;
    private QueryAppointmentYYAdapter adapter; //!!
    private int inputListPosition;
    private QueryToolBar queryToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_appointment);//!!
        queryToolBar = (QueryToolBar)findViewById(R.id.query_appointment_toolbar);
        queryToolBar.setTitle(this.getResources().getString(R.string.appointment_yy_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {

            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.query_appointment_list); //!!
        listData = new ArrayList<Appointment>();//!!
        adapter = new QueryAppointmentYYAdapter(this,R.layout.ldbm_query_appointment_item,listData);//!!
        selectListView.setAdapter(adapter);
        selectListView.setDividerHeight(1); //分割线粗为1
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
