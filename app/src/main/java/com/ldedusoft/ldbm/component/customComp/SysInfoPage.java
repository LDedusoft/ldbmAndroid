package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ldedusoft.ldbm.R;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class SysInfoPage extends LinearLayout {
    public SysInfoPage(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ldbm_sys_info, this);
    }


}
