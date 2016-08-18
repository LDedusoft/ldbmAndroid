package com.ldedusoft.ldbm.activity.part.form;

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
import com.ldedusoft.ldbm.model.Client;
import com.ldedusoft.ldbm.model.FixingInfo;
import com.ldedusoft.ldbm.model.InputItem;
import com.ldedusoft.ldbm.model.Invoice;
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
 * 保存配件销售
 * Created by wangjianwei on 2016/6/28.
 */
public class NewFixSaleActivity extends BaseActivity implements View.OnClickListener,InputItemDelListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    private String dataSource;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_fix_sale); //!!
        String value =  getIntent().getStringExtra("param");
        dataSource =  getIntent().getStringExtra("dataSource");//编辑时会传此参数
        formToolBar = (FormToolBar)findViewById(R.id.new_fixsale_toolbar);
      formToolBar.setTitle(this.getResources().getString(R.string.save_fix_sale_title));

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
        listData = InitParamUtil.getInstance(this).initSC_SaveSaleFixings(); //!!  初始化表单内容
        initList();
        getData();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.new_fixsale_listview); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        adapter.setInputItemDelListener(this); //删除项目监听
        inputListView.setAdapter(adapter);
        inputListView.setDividerHeight(1); //分割线粗为1
    }

    private void addListItem(FixingInfo fixingInfo){
        InputItem item = new InputItem();
        String name = fixingInfo.getMingCheng();
        String id = String.valueOf(fixingInfo.getID());
        String number = "";
        String price = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", name);
            jsonObject.put("Id", id);
            jsonObject.put("Num", number);
            jsonObject.put("Price", price);
        }catch (Exception e){}
        item.setValue(jsonObject.toString());
        item.setInputType(11);
//        listData.add(listData.size() - 1, item);
        listData.add( item);
        adapter.notifyDataSetChanged();
    }
    private void updateListItem(String dispValue,String data,int position){
        InputItem item = listData.get(position);
        item.setDispValue(dispValue);
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
                    Client client ;
                    client = (Client)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(client.getBianHao(),client.getBianHao(), inputListPosition);
                }
                break;
            case 2: //经手人
                if(resultCode == RESULT_OK){
                    SalesMan salesMan;
                    salesMan = (SalesMan)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(salesMan.getName(),salesMan.getName(), inputListPosition);
                }
                break;
            case 3: //发票
                if(resultCode == RESULT_OK){
                    Invoice invoice;
                    invoice = (Invoice)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(invoice.getFaPiao(),invoice.getFaPiao(), inputListPosition);
                }
                break;
            case 10: //添加配件
                if(resultCode == RESULT_OK){
                    FixingInfo fixingInfo = (FixingInfo)data.getSerializableExtra("item");
                    addListItem(fixingInfo);
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
            JSONObject jsonObj = new JSONObject();
            JSONArray fixJsonArray = new JSONArray();
            String number = "";
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(item.getValue().indexOf("{")==0){
                    JSONObject json = new JSONObject(item.getValue());
                    json.remove("Name");
                    fixJsonArray.put(json);
                }
                //ClientId：客户编号；SaleMan：经手人；Invoice：发票类型}
                if("ClientId".equals(item.getItemId())||"SaleMan".equals(item.getItemId())||"Invoice".equals(item.getItemId())){
                    jsonObj.put(item.getItemId(), item.getValue());
                }
                if("Number".equals(item.getItemId())){
                    number = item.getValue();
                }

            }

            String info = jsonObj.toString();
            String fInfo = fixJsonArray.toString();
            Log.d("保存配件销售单信息info ：", info);
            Log.d("保存配件销售单信息fInfo ：", fInfo);
            saveHandler(number, info, fInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存信息到服务器
     * @param number
     * @param info
     * @param fInfo
     */
    private void saveHandler(String number,String info,String fInfo){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_SaveSaleFixings(number, info, fInfo);//!!
        Log.d("保存配件销售单：",paramXml);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("保存配件销售单返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_SaveSaleFixingsResult);
                        if("false".equals(result)||TextUtils.isEmpty(result)){
                            Toast.makeText(NewFixSaleActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                        }else if("true".equals(result)){
                            Toast.makeText(NewFixSaleActivity.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
                            //发送刷新列表广播
                            Intent intent = new Intent(SysProperty.getInstance().Broadcast_commlist_refresh);
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(NewFixSaleActivity.this);
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
                        Toast.makeText(NewFixSaleActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFixSaleActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getData(){
        if(!TextUtils.isEmpty(dataSource)){
            //// TODO: 2016/8/12  需要获取当前订单的配件列表
            updateListViewWithDS(dataSource);
            return;
        }
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getPT_NewSaleFixings(UserProperty.getInstance().getUserName());
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("新建配件销售单返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.PT_NewSaleFixingsResult);
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
        listData = InterfaceResault.getPT_NewSaleFixingsResult(listData, result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnDelCarClick(int position) {

    }

    @Override
    public void OnDelClick(int position) {
        Iterator<InputItem> it = listData.iterator();
        int i=0;
        while (it.hasNext()) {
            InputItem item = it.next();
            if(i==position){
                it.remove();
            }
            i++;
        }

      //  listData.remove(position);
//        for (int i = listData.size()-1; i >=0; i--) {
//            if(i==position){
//                listData.remove(i);
//            }
//        }
        adapter.updateCache(position);
        adapter.notifyDataSetChanged();
    }

    //修改时更新列表
    //列表值(提交接口)
    //
    // 查询数据
    //列表值(提交接口)
    //<Describe>{ClientId：客户编号；SaleMan：经手人；Invoice：发票类型   FormMaker  Number
   // <Describe>{ Id：配件id；Num：数量；Price：含税单价}</Describe>
    // 查询数据
    //{ID：ID；DanHao：单号；RiQi：日期；JinE：单据金额；Num：单据数量；GongFang：供方单位；JingShouRen：经手人；ZhiDanRen：制单人}BianHao 客户编号

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
                } else  if("Invoice".equals(item.getItemId())) {
                    item.setValue(jsonObject.has("FaPiao")?jsonObject.getString("FaPiao"):""); //查询数据中没有返回发票类型
                }
            }
            //获取配件列表
            //每个json生成inputItem, json字符串放入item的value里
            //item加入listData
            getFixListData();
//            adapter.notifyDataSetChanged(); //添加配件列表完毕后的方法里执行
        }catch(Exception e){e.printStackTrace();}

    }

    /**
     * 修改时查询配件列表
     */
    private void getFixListData(){
        String serverPath = InterfaceParam.SERVER_PATH;
        String paramXml = InterfaceParam.getInstance().getSC_SaleFixingsFixingsList(id);//!!
        Log.d("获取配件销售配件列表参数：",paramXml);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("获取配件销售配件列表返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.SC_SaleFixingsFixingsListResult);
                        addFixList(result);
                    }
                });
            }
            @Override
            public void onWarning(String warning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFixSaleActivity.this,"获取配件列表失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFixSaleActivity.this,"获取配件列表失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 配件添加到列表
     * @param result
     * 查询返回的数据：{ID：ID；BianId：配件ID；MingCheng：配件名称；Num：数量；HanShuiDanJIa：含税单价；HanShuiJinE：含税金额；CangKu：仓库}
     * 需要转换为：Name  Id  Num  Price
     * //{"Name":""+name+"\",\"Id\":\""+id+"\",\"Num\":\""+number+"\",\"Price\":\""+price+"\"}");
     */
    private void addFixList(String result){
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
                newJson.put("Name",jsonObject.get("MingCheng"));
                newJson.put("Id",jsonObject.get("BianId"));
                newJson.put("Num",jsonObject.get("Num"));
                newJson.put("Price",jsonObject.get("HanShuiDanJIa"));
                item = new InputItem();
                item.setValue(newJson.toString());
                item.setInputType(11);//配件
                listData.add(item);
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){e.printStackTrace();}

    }
}
