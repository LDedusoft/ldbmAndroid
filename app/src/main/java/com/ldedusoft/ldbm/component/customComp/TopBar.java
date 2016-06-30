package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class TopBar extends LinearLayout {
    private TextView topBarTitle;
    public TopBar(Context context, AttributeSet attrs){
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.ldbm_top_bar, this);
        TextView topBarMenu = (TextView)findViewById(R.id.top_bar_menu);
         topBarTitle = (TextView)findViewById(R.id.top_bar_title);
    }

    public void setTitle(String title){
        topBarTitle.setText(title);
    }

}
