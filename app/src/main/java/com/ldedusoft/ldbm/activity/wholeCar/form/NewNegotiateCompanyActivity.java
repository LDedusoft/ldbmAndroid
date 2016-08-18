package com.ldedusoft.ldbm.activity.wholeCar.form;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.ldedusoft.ldbm.model.SysProperty;
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
    private String dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_negotiate_company); //!!
        String value =  getIntent().getStringExtra("param");
        dataSource =  getIntent().getStringExtra("dataSource");//编辑时会传此参数
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


    private void updateListItem(String dispValue,String data,int position){
        InputItem item = listData.get(position);
        item.setDefaultValue(dispValue);
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
                    updateListItem(result,result, inputListPosition);
                }
                break;
            case 2: //车型选择
                if(resultCode == RESULT_OK){
                    CarType carType = (CarType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(carType.getType(),String.valueOf(carType.getID()), inputListPosition);
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
            String danHao= "";
            JSONObject dataJsonObj = new JSONObject();
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if("Number".equals(item.getItemId())){
                    danHao = item.getValue();
                }
                dataJsonObj.put(item.getItemId(), item.getValue());
            }

            String info = dataJsonObj.toString();
            Log.d("保存公司洽谈信息：", info);
            saveHandler(danHao,info);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param info
     */
    private void saveHandler(String danHao,String info){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_SaveNegotiate(danHao,UserProperty.getInstance().getUserName(), info, "公司");//!!
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
                            //发送刷新列表广播
                            Intent intent = new Intent(SysProperty.getInstance().Broadcast_commlist_refresh);
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(NewNegotiateCompanyActivity.this);
                            localBroadcastManager.sendBroadcast(intent);//发送广播
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
        if(!TextUtils.isEmpty(dataSource)){
            updateListViewWithDS(dataSource);
            return;
        }
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

    //修改时更新列表
    //列表值(提交接口)
    //{Name：公司名称；Sex：负责人；Phone：电话；cType：联系人；Time：联系人电话；Plan：购车方案；WantCar：意向车型的id；WantTime：预购时间}

    // 查询数据
   // {Name：公司名称；Boss：负责人；Phone：电话；LinkMan：联系人；lPhone：联系人电话；Plan：购车方案；WantCar：意向车型的id；
   // WantTime：预购时间；DanHao：单号；Date：日期；jsr：经手人；zdr：制单人}
    private void  updateListViewWithDS(String ds){
        try{
            JSONObject jsonObject = new JSONObject(ds);
            for (InputItem item : listData){

                if("FormMaker".equals(item.getItemId())){
                    item.setValue(jsonObject.getString("zdr"));
                }else  if("Number".equals(item.getItemId())){
                    item.setValue(jsonObject.getString("DanHao"));
                }else if("Name".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("Name"));
                }else if("Sex".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("Boss"));
                }else if("cType".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("LinkMan"));
                }else if("Phone".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("Phone"));
                }else if("Time".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("lPhone"));
                }else if("Plan".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("Plan"));
                }else if("WantCar".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("WantCar"));
                }else if("WantTime".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("WantTime"));
                }
            }
            adapter.notifyDataSetChanged();
        }catch(Exception e){e.printStackTrace();}
    }
}
