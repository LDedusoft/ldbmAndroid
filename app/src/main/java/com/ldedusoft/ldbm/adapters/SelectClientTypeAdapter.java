package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.ClientType;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectClientTypeAdapter extends ArrayAdapter<ClientType> {
    private int resourceId;
    private TextView NameText;
    private TextView bianHaoText;
    private TextView idText;
    private Context mContext;
    private ClientType client;

    public SelectClientTypeAdapter(Context context, int textViewResourceId, List<ClientType> objects) {
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
            idText = (TextView)view.findViewById(R.id.client_type_ID);
            NameText =  (TextView)view.findViewById(R.id.client_type_LeiBie);
            bianHaoText =(TextView)view.findViewById(R.id.client_type_BianHao);
            NameText.setText(client.getLeiBie());
            idText.setText(String.valueOf(client.getID()));
            bianHaoText.setText(client.getBianHao());
        }
        return view;
    }
}
