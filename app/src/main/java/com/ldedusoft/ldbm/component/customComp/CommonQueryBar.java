package com.ldedusoft.ldbm.component.customComp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.interfaces.CommonQueryBarListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wangjianwei on 2016/7/19.
 */
public class CommonQueryBar extends LinearLayout {
    private CommonQueryBarListener commonQueryBarListener;
    private TextView selectBtn;
    private TextView startTime;
    private TextView endTime;
    private TextView salesman;
    private TextView clientName;
    private TextView clientBianHao;
    private Context mContext;
    private TextView restockType;
    private LinearLayout timeCondition;
    private LinearLayout salesmanCondition;
    private LinearLayout clientCondition;
    private LinearLayout restockCondition,allCondition;

    public CommonQueryBar(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ldbm_commonquery_condition, this);
        selectBtn= (TextView)findViewById(R.id.common_query_selBtn);
        startTime = (TextView)findViewById(R.id.common_query_startTime);
        endTime = (TextView)findViewById(R.id.common_query_endTime);
        salesman = (TextView)findViewById(R.id.common_query_salesman);
        clientName = (TextView)findViewById(R.id.common_query_client);
        clientBianHao = (TextView)findViewById(R.id.common_query_clientBianhao);
        restockType = (TextView)findViewById(R.id.common_query_restock_type);
        timeCondition = (LinearLayout)findViewById(R.id.common_query_timeCondition);
        salesmanCondition = (LinearLayout)findViewById(R.id.common_query_salesmanCondition);
        clientCondition =  (LinearLayout)findViewById(R.id.common_query_ClientCondition);
        restockCondition =(LinearLayout)findViewById(R.id.common_query_restockCondition);
        allCondition = (LinearLayout)findViewById(R.id.common_query_All_condition);
        /*开始时间（上月）*/
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month =  c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(month==6||month==9||month==11){
            if(day>30){
                day=30;
            }
        }
        if(month==2){
            if(day>28){
                day =28;
            }
        }
        startTime.setText(year + "-" + month+ "-" + day);
        /*结束时间（当前）*/
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        endTime.setText(date);



        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                        startTime.setText(year + "-" + (mounth + 1) + "-" + day);
                    }
                },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                        endTime.setText(year + "-" + (mounth + 1) + "-" + day);
                    }
                },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        clientName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commonQueryBarListener.OnClientSelect();
            }
        });

        clientBianHao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commonQueryBarListener.OnClientSelect();
            }
        });

        salesman.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commonQueryBarListener.OnSalesmanSelect();
            }
        });

        restockType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commonQueryBarListener.OnRestockTypeSelect();
            }
        });

        selectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("startTime", startTime.getText());
                    paramObj.put("endTime",endTime.getText());
                    paramObj.put("salesMan",salesman.getText());
                    paramObj.put("clientName",clientName.getText());
                    paramObj.put("clientBianHao",clientBianHao.getText());
                    String typeId="1";
                    if("按仓库汇总".equals(restockType.getText())){
                        typeId = "1";
                    }else
                    if("按配件类别汇总".equals(restockType.getText())){
                        typeId = "2";
                    }else
                    if("按供应商汇总".equals(restockType.getText())){
                        typeId = "3";
                    }
                    paramObj.put("restockType",typeId);
                    commonQueryBarListener.OnSelectClick(paramObj);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setSalesmanValue(String value){
        salesman.setText(value);
    }

    public void setCommonQueryBarListener(CommonQueryBarListener commonQueryBarListener) {
        this.commonQueryBarListener = commonQueryBarListener;
    }

    public void setClientValue(String name,String bianhao){
        clientName.setText(name);
        clientBianHao.setText(bianhao);
    }

    public void setRestockTypeValue(String value){
        restockType.setText(value);
    }
    public void setConditionType(int type){
        switch (type){
            case 0://隐藏所有条件
                allCondition.setVisibility(GONE);
            case 1: //只显示时间
                timeCondition.setVisibility(VISIBLE);
                salesmanCondition.setVisibility(GONE);
                clientCondition.setVisibility(GONE);
                restockCondition.setVisibility(GONE);
                break;
            case 2://显示时间和经手人
                timeCondition.setVisibility(VISIBLE);
                salesmanCondition.setVisibility(VISIBLE);
                clientCondition.setVisibility(GONE);
                restockCondition.setVisibility(GONE);
                break;
            case 3://只显示客户信息
                timeCondition.setVisibility(GONE);
                salesmanCondition.setVisibility(GONE);
                clientCondition.setVisibility(VISIBLE);
                restockCondition.setVisibility(GONE);
                break;
            case 4://显示汇总方式和时间
                timeCondition.setVisibility(VISIBLE);
                salesmanCondition.setVisibility(GONE);
                clientCondition.setVisibility(GONE);
                restockCondition.setVisibility(VISIBLE);
            default:
                break;
        }
    }
}
