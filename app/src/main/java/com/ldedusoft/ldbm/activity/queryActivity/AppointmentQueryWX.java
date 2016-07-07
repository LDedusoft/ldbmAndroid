package com.ldedusoft.ldbm.activity.queryActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.QueryAppointmentWXAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.Reception;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import java.util.ArrayList;

/**
 * 维修接待查询
 * Created by wangjianwei on 2016/6/29.
 */
public class AppointmentQueryWX extends BaseActivity {
    private ArrayList<Reception> listData; //!!
    private ListView selectListView;
    private QueryAppointmentWXAdapter adapter; //!!
    private int inputListPosition;
    private QueryToolBar queryToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_appointment_wx);//!!
        queryToolBar = (QueryToolBar)findViewById(R.id.query_appointment_wx_toolbar);
        queryToolBar.setTitle(this.getResources().getString(R.string.appointment_wx_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
            }
            @Override
            public void OnBackClick() { finish(); }
        });
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData();
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.query_appointment_wx_list); //!!
        listData = new ArrayList<Reception>();//!!
        adapter = new QueryAppointmentWXAdapter(this,R.layout.ldbm_query_appointment_wx_item,listData);//!!
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
        String paramXml = InterfaceParam.getInstance().getRP_ShowReception(); //!!接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //!!接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.RP_ShowReceptionResult);
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
        listData = InterfaceResault.getRP_ShowReceptionResult(listData, result);
        adapter.notifyDataSetChanged();
    }
}
