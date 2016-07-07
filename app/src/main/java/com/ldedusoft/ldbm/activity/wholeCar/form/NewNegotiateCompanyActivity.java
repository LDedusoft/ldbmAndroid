package com.ldedusoft.ldbm.activity.wholeCar.form;

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
import com.ldedusoft.ldbm.model.CarType;
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
 * 保存公司洽谈
 * Created by wangjianwei on 2016/6/28.
 */
public class NewNegotiateCompanyActivity extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_negotiate_company); //!!
        String value =  getIntent().getStringExtra("param");
        formToolBar = (FormToolBar)findViewById(R.id.new_company_nego_toolbar);
        formToolBar.setTitle(this.getResources().getString(R.string.negotiate_company_title));
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
        listData = InitParamUtil.getInstance(this).initSC_SaveNegotiate_company(); //!!  初始化表单内容
        initList();
        getData();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.new_company_nego_listview); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        inputListView.setAdapter(adapter);
        inputListView.setDividerHeight(1); //分割线粗为1
    }


    private void updateListItem(String data,int position){
        InputItem item = listData.get(position);
        item.setValue(data);
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
            case 1:
                if(resultCode == RESULT_OK){
                    String result= data.getStringExtra("result");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(result, inputListPosition);
                }
                break;
            case 2: //车型选择
                if(resultCode == RESULT_OK){
                    CarType carType = (CarType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(String.valueOf(carType.getID()), inputListPosition);
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
            Log.d("保存公司洽谈信息：", info);
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
        String paramXml = InterfaceParam.getInstance().getSC_SaveNegotiate(UserProperty.getInstance().getUserName(), info, "公司");//!!
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("保存公司洽谈返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_SaveNegotiateResult);
                        if("false".equals(result)||TextUtils.isEmpty(result)){
                            Toast.makeText(NewNegotiateCompanyActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                        }else if("true".equals(result)){
                            Toast.makeText(NewNegotiateCompanyActivity.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                            TimerTask task = new TimerTask(){
                                public void run(){
                                    finish();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 1000);
                        }

                    }
                });
            }

            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewNegotiateCompanyActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewNegotiateCompanyActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String typeValue =  "公司";
        String paramXml = InterfaceParam.getInstance().getSC_NewNegotiate(UserProperty.getInstance().getUserName(),typeValue);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_NewNegotiateResult);
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
        listData = InterfaceResault.getSC_NewNegotiateResult(listData,result);
        adapter.notifyDataSetChanged();
    }
}
