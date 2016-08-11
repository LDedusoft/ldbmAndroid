package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectClientTypeAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.ClientType;
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
public class ClientTypeSelect extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ArrayList<ClientType> listData;
    private ListView selectListView;
    private SelectClientTypeAdapter adapter;
    private int inputListPosition;
    private String relationValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_client_type);//!!
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        relationValue = getIntent().getStringExtra("relation");//接收关联值
        initListView();
        initData();
        initQueryToolBar();
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.selected_client_type_toolbar);
//        queryToolBar.showAddBtn();
//        queryToolBar.setTitle(this.getResources().getString(R.string.carcode_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
            }
            @Override
            public void OnModifyClick() {

            }
            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }

    private void initListView(){

        selectListView = (ListView)findViewById(R.id.selected_client_type_list); //!!
        listData = new ArrayList<ClientType>();
        adapter = new SelectClientTypeAdapter(this,R.layout.ldbm_selected_client_type_item,listData);//!!
        selectListView.setAdapter(adapter);
        selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientType clientType = new ClientType();
                clientType = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition", inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", clientType);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPubClientTypeList(relationValue);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.PubClientTypeListResult);
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
        InterfaceResault.getPubClientTypeListResult(listData,result);
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
