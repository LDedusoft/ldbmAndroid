package com.ldedusoft.ldbm.activity.repair.form;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.InputListAdapter;
import com.ldedusoft.ldbm.component.customComp.FormToolBar;
import com.ldedusoft.ldbm.interfaces.FormToolBarListener;
import com.ldedusoft.ldbm.model.ClientCategory;
import com.ldedusoft.ldbm.model.ClientType;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.InitParamUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 保存客户信息
 * Created by wangjianwei on 2016/6/28.
 */
public class NewClientActivity extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_client); //!!
        String value =  getIntent().getStringExtra("param");
        formToolBar = (FormToolBar)findViewById(R.id.new_client_toolbar);
      formToolBar.setTitle(this.getResources().getString(R.string.new_client));
        formToolBar.setFormToolBarListener(new FormToolBarListener() {
            @Override
            public void OnSaveClick() {
                inputListView.clearFocus();//清除列表的焦点
                saveInfo();
            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
        listData = InitParamUtil.getInstance(this).initPub_SaveClient(); //!!  初始化表单内容
        initList();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.new_client_listview); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        inputListView.setAdapter(adapter);
        inputListView.setDividerHeight(1); //分割线粗为1
    }


    private void updateListItem(String dispValue,String data,int position){
        InputItem item = listData.get(position);
        item.setValue(data);
        item.setDispValue(dispValue);
        listData.set(position, item);
        adapter.notifyDataSetChanged();
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
            case 1: //客户类别选择
                if(resultCode == RESULT_OK){
                    ClientCategory clientCategory = (ClientCategory)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(clientCategory.getCategoryName(),String.valueOf(clientCategory.getCategoryId()), inputListPosition); //编号存入
                }
                break;
            case 2: //客户类型选择
                if(resultCode == RESULT_OK){
                    ClientType clientType = (ClientType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(clientType.getLeiBie(),String.valueOf(clientType.getID()), inputListPosition); //编号存入
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
    }

    private void saveInfo(){
        try {
            JSONObject dataJsonObj = new JSONObject();
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                dataJsonObj.put(item.getItemId(), item.getValue());
            }

            String info = dataJsonObj.toString();
            Log.d("保存客户信息：", info);
            saveHandler(info);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param info
     */
    private void saveHandler(String info){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPub_SaveClient(UserProperty.getInstance().getUserName(),info);//!!
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("保存车辆信息返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.Pub_SaveClientResult);
                       if(TextUtils.isEmpty(result)){
                           Toast.makeText(NewClientActivity.this,getString(R.string.save_fail),Toast.LENGTH_LONG).show();
                       }else if("false".equals(result)){
                            Toast.makeText(NewClientActivity.this,getString(R.string.save_fail),Toast.LENGTH_LONG).show();
                        }else if("true".equals(result)){
                            Toast.makeText(NewClientActivity.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                            TimerTask task = new TimerTask(){
                                public void run(){
                                    finish();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 700);
                        }

                    }
                });
            }

            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewClientActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewClientActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
