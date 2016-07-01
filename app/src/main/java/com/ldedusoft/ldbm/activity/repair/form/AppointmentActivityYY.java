package com.ldedusoft.ldbm.activity.repair.form;

import android.content.Context;
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
import com.ldedusoft.ldbm.model.InputItem;
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
public class AppointmentActivityYY extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private TextView submitText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_repair_appointment);
        String value =  getIntent().getStringExtra("param");
        listData = InitParamUtil.initRP_ReceptionNew_YY();
        submitText = (TextView)findViewById(R.id.apponintemnt_submit);
        submitText.setOnClickListener(this);
        getData();
        initList();
    }

    /*启动活动方法*/
    public static void actionStart(Context context,String data){
        Intent intent = new Intent(context,AppointmentActivityYY.class);
        intent.putExtra("param", data);
        context.startActivity(intent);
    }

    private void initList(){
        inputListView = (ListView)findViewById(R.id.appointment_YY_list);
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        inputListView.setAdapter(adapter);
    }

    private void getData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String typeValue =  getIntent().getStringExtra("param");
        String paramXml = InterfaceParam.getInstance().getRP_ReceptionNew(typeValue);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   TextView tv = (TextView) findViewById(R.id.appointment_text);
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
            case 1:
                if(resultCode == RESULT_OK){
                    String resultData = data.getStringExtra("data_return");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(resultData,inputListPosition);
                }
                break;

            default:
                if(resultCode == RESULT_OK){
                    String resultData = data.getStringExtra("data_return");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(resultData, inputListPosition);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apponintemnt_submit:
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
        String paramXml = InterfaceParam.getInstance().getAP_AppointmentSave(number, info);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityYY.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityYY.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointmentActivityYY.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
