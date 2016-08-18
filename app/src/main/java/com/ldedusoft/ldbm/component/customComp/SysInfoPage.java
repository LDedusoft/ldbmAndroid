package com.ldedusoft.ldbm.component.customComp;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.UserProperty;

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

        TextView userTxt = (TextView)findViewById(R.id.sys_info_user);
        userTxt.setText(UserProperty.getInstance().getUserName());
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter(context,
                R.layout.item_text, new String[] { "首页", "关于",
                "用户信息" }));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position){
                    case 0:
                        Intent intent = new Intent("activity.HomeActivity");
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
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

class MyAdapter extends ArrayAdapter<String>{
    private int resourceId;
        public MyAdapter(Context context,int resId,String[] objects){
            super(context,resId,objects);
            this.resourceId = resId;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        View view = convertView;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        TextView tv = (TextView)view.findViewById(R.id.menu_title);
        tv.setText(item);
        return view;
    }
}
