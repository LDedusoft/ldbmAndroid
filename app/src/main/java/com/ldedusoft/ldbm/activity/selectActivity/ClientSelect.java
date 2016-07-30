package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectClientAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 选择客户信息
 * Created by wangjianwei on 2016/6/29.
 */
public class ClientSelect extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ArrayList<Client> listData;
    private ListView selectListView;
    private SelectClientAdapter adapter;
    private int inputListPosition;
    private String intentParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_client);//!!
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        intentParam = getIntent().getStringExtra("param");
        initListView();
        initData();
        initQueryToolBar();
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.selected_client_toolbar);
        queryToolBar.showAddBtn();
        queryToolBar.setTitle(this.getResources().getString(R.string.client_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
                Intent intent = new Intent("activity.repair.form.NewClientActivity");
                ClientSelect.this.startActivityForResult(intent,1);
            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_client_list); //!!
        listData = new ArrayList<Client>();
        adapter = new SelectClientAdapter(this,R.layout.ldbm_selected_client_item,listData);//!!
        selectListView.setAdapter(adapter);
        if(!"notReturn".equals(intentParam)) { //判断是否可以返回值
            selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = new Client();
                    client = listData.get(position);
                    //返回数据到上一个活动
                    Intent intent = new Intent();
                    intent.putExtra("inputListPosition", inputListPosition);//表单传来的item位置，返回回去
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", client);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
       String clientName = "";
        if("insurance_com".equals(intentParam)){
        }
        String paramXml = InterfaceParam.getInstance().getPub_ClientList(clientName); //!!接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //!!接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_ClientListResult);
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
        String clientType = "";
        if("insuranceCom".equals(intentParam)){
            clientType = "保险公司";
        }
        listData = InterfaceResault.getPub_ClientListResult(listData, result,clientType);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                initData();
                break;
            default:
                break;
        }
    }

}
