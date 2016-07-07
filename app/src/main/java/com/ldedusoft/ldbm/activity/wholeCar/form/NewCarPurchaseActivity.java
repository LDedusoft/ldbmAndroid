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
import com.ldedusoft.ldbm.model.Client;
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
 * 保存个人洽谈
 * Created by wangjianwei on 2016/6/28.
 */
public class NewCarPurchaseActivity extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_carpurchase); //!!
        String value =  getIntent().getStringExtra("param");
        formToolBar = (FormToolBar)findViewById(R.id.new_carpurchase_toolbar);
      formToolBar.setTitle(this.getResources().getString(R.string.save_car_purchase_title));

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
        listData = InitParamUtil.getInstance(this).initSC_SavePurchase(); //!!  初始化表单内容
        initList();
        getData();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.new_carpurchase_listview); //!!
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
            case 1: //选择客户信息 设置客户编号
                if(resultCode == RESULT_OK){
                    Client client = new Client();
                    client = (Client)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(client.getBianHao(), inputListPosition);
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
            JSONObject carJsonObj = new JSONObject();
            JSONObject personJsonObj = new JSONObject();
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                //ClientId：客户编号；SaleMan：经手人
                if("ClientId".equals(item.getItemId())||"SaleMan".equals(item.getItemId())){
                    personJsonObj.put(item.getItemId(), item.getValue());
                }
                carJsonObj.put(item.getItemId(), item.getValue());
            }

            String cInfo = carJsonObj.toString();
            String pInfo = personJsonObj.toString();
            Log.d("保存整车销售单信息cInfo ：", cInfo);
            Log.d("保存整车销售单信息pInfo ：", pInfo);
           // saveHandler(cInfo,pInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param cInfo
     * @param pInfo
     */
    private void saveHandler(String cInfo,String pInfo){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_SavePurchase(UserProperty.getInstance().getUserName(),pInfo,cInfo);//!!
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("保存整车销售单返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_SavePurchaseResult);
                        if("false".equals(result)||TextUtils.isEmpty(result)){
                            Toast.makeText(NewCarPurchaseActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                        }else if("true".equals(result)){
                            Toast.makeText(NewCarPurchaseActivity.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewCarPurchaseActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewCarPurchaseActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_NewPurchase(UserProperty.getInstance().getUserName());
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_NewPurchaseResult);
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
        listData = InterfaceResault.getSC_NewPurchaseResult(listData, result);
        adapter.notifyDataSetChanged();
    }
}
