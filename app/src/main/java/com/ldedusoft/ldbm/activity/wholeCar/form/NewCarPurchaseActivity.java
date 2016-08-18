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
import com.ldedusoft.ldbm.interfaces.InputItemDelListener;
import com.ldedusoft.ldbm.model.CarColor;
import com.ldedusoft.ldbm.model.CarList;
import com.ldedusoft.ldbm.model.CarType;
import com.ldedusoft.ldbm.model.CarWarehouse;
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.SalesMan;
import com.ldedusoft.ldbm.model.SysProperty;
import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.HttpCallbackListener;
import com.ldedusoft.ldbm.util.HttpUtil;
import com.ldedusoft.ldbm.util.InitParamUtil;
import com.ldedusoft.ldbm.util.ParseXML;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceParam;
import com.ldedusoft.ldbm.util.interfacekits.InterfaceResault;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 保存整车销售
 * Created by wangjianwei on 2016/6/28.
 */
public class NewCarPurchaseActivity extends BaseActivity implements View.OnClickListener,InputItemDelListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    private String dataSource;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_carpurchase); //!!
        String value =  getIntent().getStringExtra("param");
        dataSource =  getIntent().getStringExtra("dataSource");//编辑时会传此参数
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
        adapter.setInputItemDelListener(this);
        inputListView.setAdapter(adapter);
        inputListView.setDividerHeight(1); //分割线粗为1
    }


    private void updateListItem(String dispValue,String data,int position){
        InputItem item = listData.get(position);
        item.setDispValue(dispValue);
        item.setValue(data);
        listData.set(position, item);
        adapter.notifyDataSetChanged();
    }

    private void updateListCustomItem(String itemName,String dispValue,String data,int position){
        InputItem item = listData.get(position);
        String valStr = item.getValue();
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(valStr);
            jsonObject.put(itemName,data);
            item.setDispValue(jsonObject.toString());
            item.setValue(jsonObject.toString());
        }catch (Exception e){e.printStackTrace();}
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
                    updateListItem(client.getBianHao(),client.getBianHao(), inputListPosition);
                }
                break;
            case 2: //经手人
                if(resultCode == RESULT_OK){
                    SalesMan salesMan = new SalesMan();
                    salesMan = (SalesMan)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(salesMan.getName(),salesMan.getName(), inputListPosition);
                }
                break;
            case 3: //车型选择
                if(resultCode == RESULT_OK){
                    CarType carType = (CarType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(carType.getType(),String.valueOf(carType.getID()), inputListPosition);
                }
                break;
            case 4: //仓库
                if(resultCode == RESULT_OK){
                    CarWarehouse carWarehouse = new CarWarehouse();
                    carWarehouse = (CarWarehouse)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListCustomItem("cangku", carWarehouse.getName(), carWarehouse.getName(), inputListPosition); //!!!仓库传值，未确定
                }
                break;
            case 5: //颜色
                if(resultCode == RESULT_OK){
                    CarColor carColor = new CarColor();
                    carColor = (CarColor)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListCustomItem("Color", carColor.getColor(), carColor.getColor(), inputListPosition); //!!
                }
                break;
            case 6: //添加车辆
                if(resultCode == RESULT_OK){
                    CarList car = (CarList)data.getSerializableExtra("item");
                    addListItem(car);
                }
                break;
            default:
                break;
        }
    }

    ////{CarCode：车牌号；Color：颜色；DanJia：价格；
    // Brand：品牌；Series：车系；Type：车型; dingjin:订金:cangku:仓库}
    private void addListItem(CarList car){
        InputItem item = new InputItem();


        String Brand = car.getBrand();
        String CarCode =car.getCarCode();
        String Color = car.getColor();
        String DanJia = car.getDanJia();
        String dingjin = "0.00";
        String cangku = "";
        String Series = car.getSeries();
        String Type = car.getType();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Brand", Brand);
            jsonObject.put("CarCode", CarCode);
            jsonObject.put("Color", Color);
            jsonObject.put("DanJia", DanJia);
            jsonObject.put("dingjin", dingjin);
            jsonObject.put("cangku", cangku);
            jsonObject.put("Series", Series);
            jsonObject.put("Type", Type);
        }catch (Exception e){}
        item.setValue(jsonObject.toString());
        item.setInputType(13);
        listData.add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
    }

    private void saveInfo(){
        try {
            JSONObject personJsonObj = new JSONObject();
            JSONArray carJsonArray = new JSONArray();
            String number = "";
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                //ClientId：客户编号；SaleMan：经手人
                if("ClientId".equals(item.getItemId())||"SaleMan".equals(item.getItemId())){
                    personJsonObj.put(item.getItemId(), item.getValue());
                }
                if("Number".equals(item.getItemId())){
                    number = item.getValue();
                }
                if(item.getValue().indexOf("{")==0){
                    //value数据{CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；Brand：品牌；Series：车系；Type：车型} dingjin:订金 cangku:""
                    //提交格式{Code：车辆编号；Price：单价；Deposit：订金；Warehouse：仓库；Color：颜色}
                    JSONObject json = new JSONObject(item.getValue());
                    JSONObject submitJson = new JSONObject();
                    submitJson.put("Code",json.getString("CarCode"));
                    submitJson.put("Price",json.getString("DanJia"));
                    submitJson.put("Deposit",json.getString("dingjin"));
                    submitJson.put("Warehouse",json.getString("cangku"));
                    submitJson.put("Color",json.getString("Color"));
                    carJsonArray.put(submitJson);
                }
            }

            String cInfo = carJsonArray.toString();
            String pInfo = personJsonObj.toString();
            Log.d("保存整车销售单信息cInfo ：", cInfo);
            Log.d("保存整车销售单信息pInfo ：", pInfo);
            saveHandler(number,cInfo,pInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param number
     * @param cInfo
     * @param pInfo
     */
    private void saveHandler(String number,String cInfo,String pInfo){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_SavePurchase(number,pInfo,cInfo);//!!
        Log.d("保存整车销售单参数：",paramXml);
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
                            //发送刷新列表广播
                            Intent intent = new Intent(SysProperty.getInstance().Broadcast_commlist_refresh);
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(NewCarPurchaseActivity.this);
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
        if(!TextUtils.isEmpty(dataSource)){
            updateListViewWithDS(dataSource);
            return;
        }
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_NewPurchase(UserProperty.getInstance().getUserName());
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("新建整车销售返回值：",response);
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

    //修改时更新列表
    //列表值(提交接口)
    //{ClientId：客户编号；SaleMan：经手人}</Describe>
    //{Code：车辆编号；Price：单价；Deposit：订金；Warehouse：仓库；Color：颜色}

    // 查询数据

    // {ID：ID；DanHao：单号；Date：时间；dPrice：订金总额；Num：订单数量；Price：订单金额；ZhiDanRen：制单人；JingShouRen：经手人 BianHao 客户编号}
    private void  updateListViewWithDS(String ds){
        try{
            JSONObject jsonObject = new JSONObject(ds);
            id = jsonObject.getString("ID");
            for (InputItem item : listData){
                if("FormMaker".equals(item.getItemId())){
                    item.setValue(jsonObject.getString("ZhiDanRen"));
                }else  if("Number".equals(item.getItemId())){
                    item.setValue(jsonObject.getString("DanHao"));
                }else if("SaleMan".equals(item.getItemId())) {
                    item.setValue(jsonObject.getString("JingShouRen"));
                }else if("ClientId".equals(item.getItemId())){
                    item.setValue(jsonObject.has("BianHao")?jsonObject.getString("BianHao"):""); //查询数据中没有返回客户编号
                }
            }
            //获取车辆列表
            //每个json生成inputItem, json字符串放入item的value里
            //item加入listData
            getCarListData();
//            adapter.notifyDataSetChanged(); //添加车辆列表完毕后的方法里执行
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * 修改时查询车辆列表
     */
    private void getCarListData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_PurchaseCarList(id);//!!
        Log.d("获取整车销售车辆列表参数：",paramXml);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("获取整车销售车辆列表返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_PurchaseCarListResult);
                        addCarList(result);
                        }
                });
            }

            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewCarPurchaseActivity.this,"获取车辆列表失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewCarPurchaseActivity.this,"获取车辆列表失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 车辆添加到列表
     * @param result
     * 查询返回的数据：{ID：ID；CarCode：车辆编号；CangKu：仓库；Color：颜色；DPrice：订金；Price：单价}
     * 需要转换为：CarCode  Color  DanJia  Brand  Series  Type  dingjin  cangku
     * String tempValuePic = "{\"CarCode\":\""+ carId.getText()+"\",\"Color\":\""+ carColor.getText()+"\",\"DanJia\":\""+carPirce.getText()+"\"," +
    "\"Brand\":\""+carBrand.getText()+"\",\"Series\":\""+carSeries.getText()+"\",\"Type\":\""+carType.getText()+"\"," +
    "\"dingjin\":\""+carDingjin.getText()+"\",\"cangku\":\""+carCangku.getText()+"\"}";
     */
    private void addCarList(String result){
        if(TextUtils.isEmpty(result)){
            adapter.notifyDataSetChanged();
            return;
        }
        try{
            InputItem item;
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject newJson = new JSONObject();
                newJson.put("CarCode",jsonObject.get("CarCode"));
                newJson.put("Color",jsonObject.get("Color"));
                newJson.put("DanJia",jsonObject.get("Price"));
                newJson.put("Brand",jsonObject.get("Brand"));
                newJson.put("Series",jsonObject.get("Series"));
                newJson.put("Type",jsonObject.get("Type"));
                newJson.put("dingjin",jsonObject.get("DPrice"));
                newJson.put("cangku",jsonObject.get("CangKu"));
                item = new InputItem();
                item.setValue(newJson.toString());
                item.setInputType(13);
                listData.add(item);
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){e.printStackTrace();}

    }
    @Override
    public void OnDelCarClick(int position) {
        Iterator<InputItem> it = listData.iterator();
        int i=0;
        while (it.hasNext()) {
            InputItem item = it.next();
            if(i==position){
                it.remove();
            }
            i++;
        }
        adapter.updateCache(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnDelClick(int position) {

    }
}
