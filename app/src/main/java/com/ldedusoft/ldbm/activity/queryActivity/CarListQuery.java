package com.ldedusoft.ldbm.activity.queryActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.QueryCarlistAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.CarList;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 在库车辆查询
 * Created by wangjianwei on 2016/7/4.
 */
public class CarListQuery extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ListView listView;
    private ArrayList<CarList> listData;
    private QueryCarlistAdapter adapter;
    private Button queryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_carlist);
        initQueryToolBar();
        initListView();
        initData();
    }


    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_CarList(); //!!接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //!!接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_CarListResult);
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
        listData = InterfaceResault.getPub_CarListResult(listData, result);
        adapter.notifyDataSetChanged();
    }

    private void initListView(){
        listData = new ArrayList<CarList>();//!!
        listView = (ListView)findViewById(R.id.query_carlist_list);
        listView.setDividerHeight(1); //分割线粗为1
        adapter = new QueryCarlistAdapter(this,R.layout.ldbm_query_carlist_item,listData);
        listView.setAdapter(adapter);
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.query_carlist_toolbar);
        queryToolBar.setTitle(this.getResources().getString(R.string.carlist_query));
        queryToolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {

            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }
}
