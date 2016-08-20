package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.TrafficClass;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectTrafficClassAdapter extends ArrayAdapter<TrafficClass> {
    private int resourceId;
    private TextView nameText;
    private Context mContext;
    private TrafficClass trafficClass;

    public SelectTrafficClassAdapter(Context context, int textViewResourceId, List<TrafficClass> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        trafficClass = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(trafficClass!=null){
            nameText = (TextView)view.findViewById(R.id.trafficClass_name);//!!
            nameText.setText(trafficClass.getTypeName());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }
}
