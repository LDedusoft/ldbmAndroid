package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectCommonAdapter extends ArrayAdapter<String> {
    private int resourceId;
    private TextView nameText;
    private Context mContext;
    private String itemValue;

    public SelectCommonAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemValue = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(itemValue!=null){
            nameText = (TextView)view.findViewById(R.id.common_item_name);
            nameText.setText(itemValue);
        }
        return view;
    }
}
