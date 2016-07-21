package com.ldedusoft.ldbm.component.customComp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private Context mContext;
    private LinearLayout timeCondition;
    private LinearLayout salesmanContidion;

    public CommonQueryBar(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ldbm_commonquery_condition, this);
        selectBtn= (TextView)findViewById(R.id.common_query_selBtn);
        startTime = (TextView)findViewById(R.id.common_query_startTime);
        endTime = (TextView)findViewById(R.id.common_query_endTime);
        salesman = (TextView)findViewById(R.id.common_query_salesman);
        timeCondition = (LinearLayout)findViewById(R.id.common_query_timeCondition);
        salesmanContidion = (LinearLayout)findViewById(R.id.common_query_salesmanCondition);
        /*开始时间（上月）*/
        Calendar c = Calendar.getInstance();
        startTime.setText(c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)+ "-" + c.get(Calendar.DAY_OF_MONTH));
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

        salesman.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commonQueryBarListener.OnSalesmanSelect();
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

    public void setConditionType(int type){
        switch (type){
            case 1:
                timeCondition.setVisibility(VISIBLE);
                salesmanContidion.setVisibility(GONE);
                break;
            case 2:
                timeCondition.setVisibility(VISIBLE);
                salesmanContidion.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }
}
