package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.util.Util;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class SysInfoPage extends LinearLayout {
    private ImageView iv_icon;
    private ListView lv;
    private Context mContext;
    public SysInfoPage(final Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ldbm_sys_info, this);
        mContext = context;
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(context,
                R.layout.item_text, new String[] { "首页", "关于",
                "用户信息" }));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Util.t(mContext, "click " + position);
            }
        });
//        iv_icon = (ImageView) findViewById(R.id.iv_icon);
//        iv_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                dl.open();
//            }
//        });
    }


}