package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.RepaireType;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectRepairTypeAdapter extends ArrayAdapter<RepaireType> {
    private int resourceId;
    private TextView nameText;
    private Context mContext;
    private RepaireType repaireType;

    public SelectRepairTypeAdapter(Context context, int textViewResourceId, List<RepaireType> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        repaireType = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(repaireType!=null){
            nameText = (TextView)view.findViewById(R.id.repairType_name);
            nameText.setText(repaireType.getTypeName());
        }
        return view;
    }
}
