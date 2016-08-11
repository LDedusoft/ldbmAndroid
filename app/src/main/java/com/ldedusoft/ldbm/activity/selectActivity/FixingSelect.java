package com.ldedusoft.ldbm.activity.selectActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.SelectFixingAdapter;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.component.myAdapter.CommonAdapter;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;
import com.ldedusoft.ldbm.model.FixingInfo;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import java.util.ArrayList;

/**
 * 配件选择
 * Created by wangjianwei on 2016/6/29.
 */
public class FixingSelect extends BaseActivity implements QueryToolBarListener {
    private ArrayList<FixingInfo> listData;
    private ListView listView;
    private CommonAdapter adapter;
    private int inputListPosition;
    private int page = 0;//页数
    private TextView nextPageBtn;
    private TextView backPageBtn;
    private TextView currentPageBtn;
    private EditText pageEdit;
    private TextView jumpPage;
    private QueryToolBar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_selected_fixings);
        inputListPosition = getIntent().getIntExtra("position",-1);//接收参数
        initListView();
        initData(page);
        initToolBar();
        initPageBar();
    }

    private void initToolBar(){
        toolBar = (QueryToolBar)findViewById(R.id.selected_fixings_toolbar);
        toolBar.setQueryToolBarListener(this);
    }

    @Override
    public void OnBackClick() {
        finish();
    }

    @Override
    public void OnAddClick() {

    }
    @Override
    public void OnModifyClick() {

    }
    private void initPageBar(){
        nextPageBtn = (TextView)findViewById(R.id.fixing_next_page);
        backPageBtn = (TextView)findViewById(R.id.fixing_back_page);
       currentPageBtn = (TextView)findViewById(R.id.fixing_current_page);
        pageEdit = (EditText)findViewById(R.id.fixing_edit_page);
        jumpPage = (TextView)findViewById(R.id.fixing_jump);
        showPage();
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                initData(page);
                showPage();
            }
        });
        backPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 0) {
                    Toast.makeText(FixingSelect.this, "当前已是第一页", Toast.LENGTH_SHORT).show();
                    return;
                }
                page--;
                initData(page);
                showPage();
            }
        });

        jumpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPage = pageEdit.getText().toString();
                Log.d("输入值：", newPage);
                if(TextUtils.isEmpty(newPage)){
                    return;
                }
                int pageNum =1;
                try {
                    pageNum = Integer.parseInt(newPage);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(pageNum<1){
                    return;
                }
                page = pageNum-1;



                initData(page);
                showPage();
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
            }
        });
    }

    private void showPage(){
        pageEdit.setText("");
        pageEdit.clearFocus();
        currentPageBtn.setText("第" + (page+1) + "页");
    }

    private void initListView(){
        listView = (ListView)findViewById(R.id.selected_fixings_list);
        listData = new ArrayList();
        adapter = new SelectFixingAdapter(this,R.layout.ldbm_selected_fixings_item,listData);
        listView.setAdapter(adapter);

        listView.setDividerHeight(1); //分割线粗为1
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FixingInfo fixingInfo = new FixingInfo();
                fixingInfo = listData.get(position);
                //返回数据到上一个活动
                Intent intent = new Intent();
                intent.putExtra("inputListPosition",inputListPosition);//表单传来的item位置，返回回去
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",fixingInfo);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void initData(int iPage){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPT_FixingsList(String.valueOf(iPage));
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.PT_FixingsListResult);
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
        InterfaceResault.getPT_FixingsListResult(listData, result);
        adapter.notifyDataSetChanged();
    }
}
