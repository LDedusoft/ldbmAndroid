package com.ldedusoft.ldbm.activity.part.form;

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
import com.ldedusoft.ldbm.interfaces.InputItemDelListener;
import com.ldedusoft.ldbm.model.Brand;
import com.ldedusoft.ldbm.model.FixCarType;
import com.ldedusoft.ldbm.model.FixingsType;
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
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 保存配件信息
 * Created by wangjianwei on 2016/6/28.
 */
public class NewFixInfoActivity extends BaseActivity implements View.OnClickListener,InputItemDelListener{
    private ArrayList<InputItem> listData;
    private ListView inputListView;
    private InputListAdapter adapter;
    private FormToolBar formToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_new_fix_info); //!!
        String value =  getIntent().getStringExtra("param");
        formToolBar = (FormToolBar)findViewById(R.id.new_fixinfo_toolbar);
      formToolBar.setTitle(this.getResources().getString(R.string.save_fix_info_title));

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
        listData = InitParamUtil.getInstance(this).initPT_NewFixings(); //!!  初始化表单内容
        initList();
//        getData();
    }


    private void initList(){
        inputListView = (ListView)findViewById(R.id.new_fixinfo_listview); //!!
        adapter = new InputListAdapter(this,R.layout.ldbm_input_item,listData);
        adapter.setInputItemDelListener(this); //删除项目监听
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
    /**
     * 活动返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1: //配件类型
                if(resultCode == RESULT_OK){
                    FixingsType fixingsType ;
                    fixingsType = (FixingsType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(fixingsType.getLeiBie(),String.valueOf(fixingsType.getID()), inputListPosition);
                }
                break;
            case 2: //配件车型
                if(resultCode == RESULT_OK){
                    FixCarType fixCarType;
                    fixCarType = (FixCarType)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(fixCarType.getXingHao(),String.valueOf(fixCarType.getID()), inputListPosition);
                }
                break;
            case 3: //品牌
                if(resultCode == RESULT_OK){
                    Brand brand;
                    brand = (Brand)data.getSerializableExtra("item");
                    int inputListPosition = data.getIntExtra("inputListPosition", -1);
                    updateListItem(brand.getPinPai(),String.valueOf(brand.getID()), inputListPosition);
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
            for (InputItem item : listData) {
                if(item.isRequired()&&TextUtils.isEmpty(item.getValue())){ //提交数据检查
                    Toast.makeText(this,"请填写"+item.getItemTitle(),Toast.LENGTH_SHORT).show();
                    return;
                }
                jsonObj.put(item.getItemId(), item.getValue());

            }
            String info = jsonObj.toString();
            Log.d("保存配件信息info ：", info);
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
        String paramXml = InterfaceParam.getInstance().getPT_NewFixings(UserProperty.getInstance().getUserName(), info);//!!
        Log.d("保存配件信息：",paramXml);
        HttpUtil.sendHttpRequest(serverPath, paramXml, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("保存配件信息返回值：",response);
                        String result = ParseXML.getItemValueWidthName(response, InterfaceResault.PT_NewFixingsResult);
                        if("false".equals(result)||TextUtils.isEmpty(result)){
                            Toast.makeText(NewFixInfoActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                        }else if("true".equals(result)){
                            Toast.makeText(NewFixInfoActivity.this,getString(R.string.save_success),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewFixInfoActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFixInfoActivity.this,getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
        adapter.updateCache(position);
        adapter.notifyDataSetChanged();
    }
}
