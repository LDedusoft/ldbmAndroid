package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.interfaces.FormToolBarListener;

/**
 * Created by wangjianwei on 2016/7/4.
 */
public class FormToolBar extends LinearLayout {
    private TextView back;
    private TextView titleText;
    private TextView backText;
    private TextView saveBtn;
    private TextView importBtn;
    private FormToolBarListener formToolBarListener;
    private LinearLayout backLayout;
    public FormToolBar(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ldbm_top_form_bar, this); //!!
        backLayout = (LinearLayout)findViewById(R.id.form_tool_bar_back_layout);
        back  = (TextView)findViewById(R.id.form_tool_bar_back);
        titleText = (TextView)findViewById(R.id.form_tool_bar_title);
        backText = (TextView)findViewById(R.id.form_tool_bar_text);
        importBtn = (TextView)findViewById(R.id.form_tool_bar_import);
        importBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formToolBarListener!=null){
                    formToolBarListener.OnImportClick();
                }
            }
        });
        saveBtn = (TextView)findViewById(R.id.form_tool_bar_save);
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formToolBarListener!=null){
                    formToolBarListener.OnSaveClick();
                }
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formToolBarListener!=null){
                    formToolBarListener.OnBackClick();
                }
            }
        });

        backLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formToolBarListener!=null){
                    formToolBarListener.OnBackClick();
                }
            }
        });

        backText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formToolBarListener!=null){
                    formToolBarListener.OnBackClick();
                }
            }
        });

    }

    public void showImportBtn(){
        importBtn.setVisibility(VISIBLE);
    }
    public void setTitle(String title){
        titleText.setText(title);
    }

    public void setFormToolBarListener(FormToolBarListener formToolBarListener) {
        this.formToolBarListener = formToolBarListener;
    }

    public void showSaveBtn(){
        saveBtn.setVisibility(VISIBLE);
    }

    public void hiddenSvaeBtn(){
        saveBtn.setVisibility(GONE);
    }
}
