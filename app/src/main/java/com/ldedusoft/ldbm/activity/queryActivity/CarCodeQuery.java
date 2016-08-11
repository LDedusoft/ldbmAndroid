package com.ldedusoft.ldbm.activity.queryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectCarCodeAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 车辆信息
 * Created by wangjianwei on 2016/6/29.
 */
public class CarCodeQuery extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ArrayList<CarCode> listData;
    private ListView carCodeListView;
    private SelectCarCodeAdapter adapter;
    private int inputListPosition;
    private String intentParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_carcode);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        intentParam = getIntent().getStringExtra("param");
        initListView();
        initData();
        initQueryToolBar();
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.query_carcode_toolbar);
        queryToolBar.showAddBtn();
        queryToolBar.setTitle(this.getResources().getString(R.string.carcode_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
                Intent intent = new Intent("activity.repair.form.NewCarActivity");
                CarCodeQuery.this.startActivityForResult(intent, 1);
//                Toast.makeText(CarCodeQuery.this,"TODO-跳转新建车辆页面",Toast.LENGTH_SHORT).show();
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

        carCodeListView = (ListView)findViewById(R.id.query_carcode_list);
        listData = new ArrayList<CarCode>();
        adapter = new SelectCarCodeAdapter(this,R.layout.ldbm_selected_carcode_item,listData);
        carCodeListView.setAdapter(adapter);
        carCodeListView.setDividerHeight(1); //分割线粗为1
        if(!"notReturn".equals(intentParam)) { //判断是否可以返回值
            carCodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CarCode car = new CarCode();
                    car = listData.get(position);
                    //返回数据到上一个活动
                    Intent intent = new Intent();
                    intent.putExtra("inputListPosition", inputListPosition);//表单传来的item位置，返回回去
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", car);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_CarCode();
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_CarCodeResult);
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
        InterfaceResault.getPub_CarCodeResult(listData,result);
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
