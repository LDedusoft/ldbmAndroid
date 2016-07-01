package com.ldedusoft.ldbm.activity.repair.form;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.BaseActivity;
import com.ldedusoft.ldbm.adapters.InputListAdapter;
import com.ldedusoft.ldbm.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.interfacekits.InterfaceResault;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.RepaireType;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.TrafficClass;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.InitParamUtil;
import com.ldedusoft.ldbm.util.ParseXML;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 预约维修
 * Created by wangjianwei on 2016/6/28.
 */
public class AppointmentActivityWX extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private TextView submitText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_repair_appointment_wx); //!!
        String value =  getIntent().getStringExtra("param");
        listData = InitParamUtil.getInstance(this).initRP_ReceptionNew_WX(); //!!
        submitText = (TextView)findViewById(R.id.apponintemnt_WX_submit);//!!
        submitText.setOnClickListener(this);
        getData();
        initList();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.appointment_WX_list); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        inputListView.setAdapter(adapter);
    }

    private void getData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String typeValue =  getIntent().getStringExtra("param"); //菜单初始化时定义了type 为：WX
        String paramXml = InterfaceParam.getInstance().getRP_ReceptionNew(typeValue);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.RP_ReceptionNewResult);
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
        listData = InterfaceResault.getRP_ReceptionNewResult_YY(listData,result);
        adapter.notifyDataSetChanged();
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
            case 1: //车牌选择
                if(resultCode == RESULT_OK){
                    CarCode car = (CarCode)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(car.getCarCode(), inputListPosition);
                }
                break;
            case 2: //维修类型
                if(resultCode == RESULT_OK){
                    RepaireType repaireType = (RepaireType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(repaireType.getTypeName(), inputListPosition);
                }
                break;
            case 3: //业务类别
                if(resultCode == RESULT_OK){
                    TrafficClass trafficClass = (TrafficClass)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(trafficClass.getTypeName(), inputListPosition);
                }
                break;
            case 4://业务员
                if(resultCode == RESULT_OK){
                    SalesMan salesMan = (SalesMan)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(salesMan.getName(),inputListPosition);
                }
                break;

            default:
                if(resultCode == RESULT_OK){
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem("", inputListPosition);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apponintemnt_WX_submit:
                inputListView.clearFocus();//清除列表的焦点
                saveAppointment();
                break;
            default:
                break;
        }
    }

    private void saveAppointment(){
        try {
            JSONObject dataJsonObj = new JSONObject();
            for (InputItem item : listData) {
                dataJsonObj.put(item.getItemId(), item.getValue());
            }
            String number = dataJsonObj.getString("Number");
            dataJsonObj.remove("Number");
            String info = dataJsonObj.toString();
            Toast.makeText(this,info,Toast.LENGTH_LONG).show();
            Log.d("保存信息：", info);
          //  saveHandler(number,info);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param number
     * @param info
     */
    private void saveHandler(String number, String info){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getRP_ReceptionSave(number, info);//!!
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityWX.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityWX.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityWX.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
