package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.ClientCategory;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectClientCategoryAdapter extends ArrayAdapter<ClientCategory> {
    private int resourceId;
    private TextView NameText;
    private TextView idText;
    private Context mContext;
    private ClientCategory client;

    public SelectClientCategoryAdapter(Context context, int textViewResourceId, List<ClientCategory> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        client = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(client!=null){
            idText = (TextView)view.findViewById(R.id.client_category_id);
            NameText =  (TextView)view.findViewById(R.id.client_category_name);
            NameText.setText(client.getCategoryName());
            idText.setText(String.valueOf(client.getCategoryId()));
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }
}
