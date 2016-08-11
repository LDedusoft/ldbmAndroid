package com.ldedusoft.ldbm.activity.repair.form;

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
import com.ldedusoft.ldbm.model.Appointment;
import com.ldedusoft.ldbm.model.CarCode;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.RepaireType;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.TrafficClass;
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
 * 维修接待
 * Created by wangjianwei on 2016/6/28.
 */
public class AppointmentActivityWX extends BaseActivity implements View.OnClickListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    private String dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_repair_appointment_wx); //!!
        String value =  getIntent().getStringExtra("param");
        dataSource =  getIntent().getStringExtra("dataSource");//编辑时会传此参数
        formToolBar = (FormToolBar)findViewById(R.id.appointment_wx_toolbar);
        formToolBar.setTitle(this.getResources().getString(R.string.appointment_wx));
        formToolBar.showImportBtn();//显示导入按钮
        formToolBar.setFormToolBarListener(new FormToolBarListener() {
            @Override
            public void OnSaveClick() {
                inputListView.clearFocus();//清除列表的焦点
                saveAppointment();
            }

            @Override
            public void OnBackClick() {
                finish();
            }

            @Override
            public void OnImportClick() {
                //选择预约单
               Intent intent = new Intent("activity.queryActivity.AppointmentQueryYY");
                AppointmentActivityWX.this.startActivityForResult(intent, 10);
            }
        });
        listData = InitParamUtil.getInstance(this).initRP_ReceptionNew_WX(); //!!
        getData();
        initList();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.appointment_WX_list); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        inputListView.setAdapter(adapter);
        inputListView.setDividerHeight(1); //分割线粗为1
    }

    private void getData(){
        if(!TextUtils.isEmpty(dataSource)){
            updateListViewWithDS(dataSource);
            return;
        }
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
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(car.getCarCode(), inputListPosition);
//                    String code = car.getCarCode();
//                    String linkMan = car.getLinkMan();
//                    String telephone = car.getTelephone();
//                    for (int i=0;i<listData.size();i++){
//                        InputItem item = listData.get(i);
//                        if("CarCode".equals(item.getItemId())){
//                            item.setValue(code);
//                        }else if ("Driver".equals(item.getItemId())){
//                            item.setValue(linkMan);
//                        }else if ("PhoneNum".equals(item.getItemId())){
//                            item.setValue(telephone);
//                        }
//                        listData.set(i, item);
//                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case 2: //维修类型
                if(resultCode == RESULT_OK){
                    RepaireType repaireType = (RepaireType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
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
            case 4://油量,油品
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra("result");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(result,inputListPosition);
                }
                break;
            case 5://定损员
                if(resultCode == RESULT_OK){
                    SalesMan salesMan = (SalesMan)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(salesMan.getName(),inputListPosition);
                }
                break;
            case 6://业务员
                if(resultCode == RESULT_OK){
                    SalesMan salesMan = (SalesMan)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(salesMan.getName(),inputListPosition);
                }
                break;
            case 7://保险公司
                if(resultCode == RESULT_OK){
                    Client client = (Client)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition",-1);
                    updateListItem(client.getName(),inputListPosition);
                }
                break;
            case 10://预约单
                if(resultCode == RESULT_OK){
                    Appointment appointment = (Appointment)data.getSerializableExtra("item");
                    for (int i=0;i<listData.size();i++){
                        InputItem item = listData.get(i);
                        if("CarCode".equals(item.getItemId())){
                            item.setValue(appointment.getCarCode());
                        }else if ("RepairType".equals(item.getItemId())){
                            item.setValue(appointment.getWxFangShi());
                        }else if ("BusinessType".equals(item.getItemId())){
                            item.setValue(appointment.getYwLeiBie());
                        }else if ("Number".equals(item.getItemId())){
                           //单号不做处理
                        }else{
                            item.setValue("");//其他项目置空
                        }
                        listData.set(i, item);
                    }
                    adapter.notifyDataSetChanged();
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

    //修改时更新列表
    //列表值(提交接口)
    // {CarCode:车牌号,Driver:送修人,PhoneNum:送修人电话,RepairType:维修类型,BusinessType:业务类型,OilAmount:油量,
    // OilQuality:油品,Mileage:里程,NextDate:下次保养时间,NextMileage:下次保养里程,FinishTime:预计完工时间,
    // InsuranceCom:保险公司,PEDS:定损员,Remarks:备注,Salesman:业务员,IsMaintain:是否为保养}

    // 查询数据{
    //{Id：id；DanHao：单号；jdTime：接待时间；CarCode：车牌号；MingCheng：客户名称；WeiXiuWay：维修方式；
    // ywLeiBie：业务类别;SongXiuPeople:送修人；SongXiuPhone：送修人电话；CarOils：油品；BenCiCunYou：油量；
    // BenCiMileage：里程；NextByTime：下次保养时间；ChengBaoGongSi：承包公司；DingSunYuan：定损员；YeWuBeiZhu：备注；
    // JingShouRen：业务员；ZhiDanRen：制单人；IsBywh：是否是保养；NextByMileage：下次保养里程；YuJiWanGong：预计完工时间}
    private void  updateListViewWithDS(String ds){
        try{
            JSONObject jsonObject = new JSONObject(ds);
            String danhao = jsonObject.getString("DanHao");
            // String formMaker = jsonObject.getString("FormMaker");
            for (InputItem item : listData){
                if("Number".equals(item.getItemId())){
                    item.setValue(jsonObject.getString("DanHao"));
                }else if("CarCode".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("CarCode"));
                }else if("Driver".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("SongXiuPeople"));
                }else if("PhoneNum".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("SongXiuPhone"));
                }else if("RepairType".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("WeiXiuWay"));
                }else if("BusinessType".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("ywLeiBie"));
                }else if("OilAmount".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("BenCiCunYou"));
                }else if("OilQuality".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("CarOils"));
                }else if("Mileage".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("BenCiMileage"));
                }else if("NextDate".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("NextByTime"));
                }else if("NextMileage".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("NextByMileage"));
                }else if("FinishTime".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("YuJiWanGong"));
                }else if("InsuranceCom".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("ChengBaoGongSi"));
                }else if("PEDS".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("DingSunYuan"));
                }else if("Remarks".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("YeWuBeiZhu"));
                }else if("Salesman".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("JingShouRen"));
                }else if("IsMaintain".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("IsBywh"));
                }
            }
            adapter.notifyDataSetChanged();
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void onClick(View v) {
    }

    private void saveAppointment(){
        try {
            JSONObject dataJsonObj = new JSONObject();
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                dataJsonObj.put(item.getItemId(), item.getValue());
            }
            String number = dataJsonObj.getString("Number");
            dataJsonObj.remove("Number");
            String info = dataJsonObj.toString();
            Log.d("保存信息：", info);
            saveHandler(number, info);
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
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = ParseXML.getItemValueWidthName(response, "RP_ReceptionSaveResult");
                        if("false".equals(result)||TextUtils.isEmpty(result)) {
                            Toast.makeText(AppointmentActivityWX.this, getString(R.string.save_fail), Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d("维修接待保存返回值：", response);
                            Toast.makeText(AppointmentActivityWX.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                            //发送刷新列表广播
                            Intent intent = new Intent(SysProperty.getInstance().Broadcast_commlist_refresh);
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(AppointmentActivityWX.this);
                            localBroadcastManager.sendBroadcast(intent);//发送广播
                            TimerTask task = new TimerTask() {
                                public void run() {
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
