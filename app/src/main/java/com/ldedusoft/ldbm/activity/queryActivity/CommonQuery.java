package com.ldedusoft.ldbm.activity.queryActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.common.CommonNormalAdapter;
import com.ldedusoft.ldbm.component.customComp.CommonQueryBar;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.CommonQueryBarListener;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.common.CommonNormal;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 通用列表
 * Created by wangjianwei on 2016/7/16.
 */
public class CommonQuery extends BaseActivity implements QueryToolBarListener {
    private ArrayList listData;
    private ListView listView;
    private CommonNormalAdapter adapter;
    private int inputListPosition;
    private QueryToolBar toolBar;
    private String interfaceName;
    private String dataSource;
    private CommonQueryBar conditionBar;
    private String intentParam;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_common);
        showProgressDialog();
        inputListPosition = getIntent().getIntExtra("position", -1);//接收参数
        interfaceName = getIntent().getStringExtra("title");
        intentParam = getIntent().getStringExtra("param");
        dataSource = getIntent().getStringExtra("dataSource"); //数据源，如果不为空，则不从服务器取值
        initConditionBar();
        initListView();
        initToolBar();
        initData(createDefaultParam());

    }

    /*生成初始化参数*/
    private JSONObject createDefaultParam(){
        Calendar cal = Calendar.getInstance();
        String startTime =String.valueOf(cal.get(Calendar.YEAR)) + "-" + String.valueOf(cal.get(Calendar.MONTH)) + "-" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = sDateFormat.format(new java.util.Date());
        JSONObject param =new JSONObject();
        try {
            //必须加入默认参数
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("salesMan", "");
            param.put("clientBianHao","");
            param.put("restockType","1");
        }catch (Exception e){}
        return  param;
    }

    private void initConditionBar(){
        conditionBar = (CommonQueryBar)findViewById(R.id.selected_commonQuery_conditionbar);
        if(!TextUtils.isEmpty(dataSource)){
            conditionBar.setVisibility(View.GONE);
            return;
        }
        conditionBar.setCommonQueryBarListener(new CommonQueryBarListener() {
            @Override
            public void OnSelectClick(JSONObject paramObj) {
                showProgressDialog();
                initData(paramObj);
            }

            @Override
            public void OnSalesmanSelect() {
                Intent intent = new Intent("activity.selectActivity.SalesmanSelect");
                startActivityForResult(intent, 1);
            }

            @Override
            public void OnClientSelect() {
                Intent intent = new Intent("activity.selectActivity.ClientSelect");
                startActivityForResult(intent, 2);
            }

            @Override
            public void OnRestockTypeSelect() {
                Intent intent = new Intent("activity.selectActivity.CommonSelect");
                intent.putExtra("param","restock_type");
                startActivityForResult(intent, 3);
            }
        });

        conditionBar.setConditionType(Integer.parseInt(intentParam));
    }

    private void initToolBar(){
        toolBar = (QueryToolBar)findViewById(R.id.selected_commonQuery_toolbar);
        toolBar.setQueryToolBarListener(this);
        toolBar.setTitle(interfaceName);
    }

    @Override
    public void OnBackClick() {
        finish();
    }

    @Override
    public void OnAddClick() {

    }


    private void initListView(){
        listView = (ListView)findViewById(R.id.selected_commonQuery_list);
        listData = new ArrayList();
        //设置通用适配器
        adapter = new CommonNormalAdapter(this,R.layout.ldbm_query_common_item,listData);
        listView.setAdapter(adapter);
        listView.setDividerHeight(1); //分割线粗为1
    }
    private void initData(final JSONObject param){
//        listData = new ArrayList();
        listData.clear();
        if(TextUtils.isEmpty(dataSource)) {
            String serverPath = InterfaceParam.SERVER_PATH;
            String paramXml = InterfaceParam.getInstance().getCommonParam(interfaceName,param);
            HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String result = ParseXML.getItemValueWidthName(response, InterfaceResault.getCommonRsName(interfaceName));
                            updateListView(result, interfaceName);
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
        }else{
            //遍历字典linkedHashMap()有序 获取key，取中文
            try {
                JSONObject dsJson = new JSONObject(dataSource);
                LinkedHashMap<String,String> dicKeyMap = SysProperty.getInstance().getReportKeyDic();
                for(String dicKey:dicKeyMap.keySet()){
                    Iterator iterator = dsJson.keys();
                    while(iterator.hasNext()){
                        String jsonKey = (String) iterator.next();
                        if(dicKey.equals(interfaceName+jsonKey)) {
                            CommonNormal cn = new CommonNormal();
                            cn.name1 = dicKeyMap.get(dicKey);//取中文名
                            cn.value1 = dsJson.getString(jsonKey);
                            listData.add(cn);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                closeProgressDialog();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void updateListView(String result,String inName){
        InterfaceResault.getCommonResault(listData, result, inName);
        adapter.notifyDataSetChanged();
        closeProgressDialog();
    }

    /**
     * 活动返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1: //经手人选择
                if(resultCode == RESULT_OK){
                    SalesMan salesMan = (SalesMan)data.getSerializableExtra("item");
                    conditionBar.setSalesmanValue(salesMan.getName());
                }
                break;
            case 2: //客户信息选择
                if(resultCode == RESULT_OK){
                    Client client = (Client)data.getSerializableExtra("item");
                    conditionBar.setClientValue(client.getName(), client.getBianHao());
                }
                break;
            case 3: //汇总类型
                if(resultCode == RESULT_OK){
                    String type = data.getStringExtra("item");
                    conditionBar.setRestockTypeValue(type);
                }
                break;
            default:

                break;
        }
    }

    /**
     * 进度框
     */
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载数据");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
