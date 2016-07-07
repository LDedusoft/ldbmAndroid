package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Client;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectClientAdapter extends ArrayAdapter<Client> {
    private int resourceId;
    private TextView NameText;
    private TextView LinkNameText;
    private TextView BianHaoText;
    private TextView MobilephoneText;
    private Context mContext;
    private Client client;

    public SelectClientAdapter(Context context, int textViewResourceId, List<Client> objects) {
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
            BianHaoText = (TextView)view.findViewById(R.id.client_BianHao);
            LinkNameText = (TextView)view.findViewById(R.id.client_LinkMan);
            MobilephoneText = (TextView)view.findViewById(R.id.client_Mobilephone);
            NameText = (TextView)view.findViewById(R.id.client_Name);
            BianHaoText.setText(client.getBianHao());
            LinkNameText.setText(client.getLinkMan());
            MobilephoneText.setText(client.getMobilephone());
            NameText.setText(client.getName());
        }
        return view;
    }
}
