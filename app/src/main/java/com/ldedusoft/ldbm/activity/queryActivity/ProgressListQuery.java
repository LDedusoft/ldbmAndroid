package com.ldedusoft.ldbm.activity.queryActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.QueryProgresslistAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.Progress;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 进度查询
 * Created by wangjianwei on 2016/7/4.
 */
public class ProgressListQuery extends BaseActivity {
    private QueryToolBar queryToolBar;
    private ListView listView;
    private ArrayList<Progress> listData;
    private QueryProgresslistAdapter adapter;
    private String carCodeParam = "";
    private EditText carcodeText;
    private Button queryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_query_progresslist);
        initQueryToolBar();
        initListView();
        initData(carCodeParam);
        initAction();
    }

    private void initAction(){

        carcodeText = (EditText)findViewById(R.id.progresslist_editText);
        carcodeText.clearFocus();
        carcodeText.setText(SysProperty.carCode);
        carcodeText.setSelected(false);
        queryBtn = (Button)findViewById(R.id.progresslist_select_btn);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carcodeText.clearFocus();
                carcodeText.setSelected(false);
//                if(!TextUtils.isEmpty(carcodeText.getText().toString())){
                    carCodeParam = carcodeText.getText().toString();
                    initData(carCodeParam); //刷新列表
//                }
            }
        });
    }

    private void initData(String param){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getRP_ProgressList(param); //!!接口参数
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //!!接口返回值 属性
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.RP_ProgressListResult);
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
        listData = InterfaceResault.getRP_ProgressListResult(listData, result);
        adapter.notifyDataSetChanged();
    }

    private void initListView(){
        listData = new ArrayList<Progress>();//!!
        listView = (ListView)findViewById(R.id.query_progresslist_listview);
        listView.setDividerHeight(1); //分割线粗为1
        adapter = new QueryProgresslistAdapter(this,R.layout.ldbm_query_progresslist_item,listData);
        listView.setAdapter(adapter);
    }

    private void initQueryToolBar(){
        queryToolBar = (QueryToolBar)findViewById(R.id.query_progresslist_toolbar);
        queryToolBar.setTitle(this.getResources().getString(R.string.progresslist_query));
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
}
