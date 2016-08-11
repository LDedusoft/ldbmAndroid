package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;

/**
 * Created by wangjianwei on 2016/7/4.
 */
public class QueryToolBar extends LinearLayout {
    private TextView back;
    private TextView titleText;
    private TextView backText;
    private TextView addBtn,modifyBtn;
    private QueryToolBarListener queryToolBarListener;
    private LinearLayout backLayout;
    public QueryToolBar(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ldbm_top_query_bar, this); //!!
        backLayout = (LinearLayout)findViewById(R.id.query_tool_bar_back_layout);
        back  = (TextView)findViewById(R.id.query_tool_bar_text);
        titleText = (TextView)findViewById(R.id.query_tool_bar_title);
        backText = (TextView)findViewById(R.id.query_tool_bar_back);
        addBtn = (TextView)findViewById(R.id.query_tool_bar_add);
        modifyBtn =(TextView)findViewById(R.id.query_tool_bar_modify);
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queryToolBarListener!=null){
                    queryToolBarListener.OnAddClick();
                }
            }
        });

        modifyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queryToolBarListener!=null){
                    queryToolBarListener.OnModifyClick();
                }
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queryToolBarListener!=null){
                    queryToolBarListener.OnBackClick();
                }
            }
        });

        backLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queryToolBarListener!=null){
                    queryToolBarListener.OnBackClick();
                }
            }
        });

        backText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queryToolBarListener!=null){
                    queryToolBarListener.OnBackClick();
                }
            }
        });

    }

    public void setTitle(String title){
        titleText.setText(title);
    }

    public void showAddBtn(){
        addBtn.setVisibility(VISIBLE);
    }
    public void showModifyBtn(){
        modifyBtn.setVisibility(VISIBLE);
    }

    public void hiddenAddBtn(){
        addBtn.setVisibility(GONE);
    }

    public void setQueryToolBarListener(QueryToolBarListener queryToolBarListener) {
        this.queryToolBarListener = queryToolBarListener;
    }
}
