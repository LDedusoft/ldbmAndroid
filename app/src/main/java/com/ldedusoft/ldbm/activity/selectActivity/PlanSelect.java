package com.ldedusoft.ldbm.activity.selectActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectPlanAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.Plan;
import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 采购计划列表
 * Created by wangjianwei on 2016/6/29.
 */
public class PlanSelect extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ArrayList<Plan> listData;
    private ListView listView;
    private SelectPlanAdapter adapter;
    private int inputListPosition;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_plan);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        title = getIntent().getStringExtra("title");

        initListView();
        initData();
        initQueryToolBar();
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.selected_plan_toolbar);
//        queryToolBar.showAddBtn();
        queryToolBar.setTitle(title);
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

        listView = (ListView)findViewById(R.id.selected_plan_list);
        listData = new ArrayList<Plan>();
        adapter = new SelectPlanAdapter(this,R.layout.ldbm_selected_plan_item,listData);
        listView.setAdapter(adapter);
        listView.setDividerHeight(1); //分割线粗为1
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan plan = new Plan();
                plan = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",plan);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });*/
    }
    private void initData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPT_PlanList(UserProperty.getInstance().getUserName());
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.PT_PlanListResult);
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
        InterfaceResault.getPT_PlanListResult(listData, result);
        adapter.notifyDataSetChanged();
    }
}
