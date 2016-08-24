package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Progress;

import java.util.List;

/**
 * 进度查询adapter
 * Created by wangjianwei on 2016/6/30.
 */
public class QueryProgresslistAdapter extends ArrayAdapter<Progress> {
    private int resourceId;
    private Progress item;
    public QueryProgresslistAdapter(Context context, int textViewResourceId, List<Progress> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item = getItem(position);
        View view ;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view = convertView;
        }
        if(item!=null){
//            *  DanHao：单号；JcDate：进出日期；JdDate：接待日期；WgDate：完工日期；
//            * JinDu：进度；MingCheng：客户名称；CarCode：车牌号

            TextView DanHaoText = (TextView)view.findViewById(R.id.progresslist_DanHao);
            TextView JcDateText = (TextView)view.findViewById(R.id.progresslist_JcDate);
            TextView JdDateText = (TextView)view.findViewById(R.id.progresslist_JdDate);
            TextView WgDateText = (TextView)view.findViewById(R.id.progresslist_WgDate);
            TextView JinDuText = (TextView)view.findViewById(R.id.progresslist_JinDu);
            TextView MingChengText = (TextView)view.findViewById(R.id.progresslist_MingCheng);
            TextView CarCodeText = (TextView)view.findViewById(R.id.progresslist_CarCode);

            DanHaoText.setText(item.getDanHao());
            JcDateText.setText(item.getJcDate());
            JdDateText.setText(item.getJdDate());
            WgDateText.setText(item.getWgDate());
            JinDuText.setText(item.getJinDu());
            MingChengText.setText(item.getMingCheng());
            CarCodeText.setText(item.getCarCode());
        }
        if(position%2==0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }


}
