package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.FixingsWarehouse;

import java.util.List;

/**
 * 配件仓库列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {ID：id;CangKu：仓库名称；BianHao：编号}
 */
public class SelectFixWarehouseAdapter extends ArrayAdapter<FixingsWarehouse> {
    private int resourceId;
    private TextView IDText;
    private TextView CangKuText;
    private TextView BianHaoText;
    private Context mContext;
    private FixingsWarehouse fixWarehouse;

    public SelectFixWarehouseAdapter(Context context, int textViewResourceId, List<FixingsWarehouse> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fixWarehouse = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(fixWarehouse!=null){
            CangKuText = (TextView)view.findViewById(R.id.fixwarehouse_CangKu);
            IDText = (TextView)view.findViewById(R.id.fixwarehouse_ID);
            BianHaoText = (TextView)view.findViewById(R.id.fixwarehouse_BianHao);
            IDText.setText(String.valueOf(fixWarehouse.getId()));
            CangKuText.setText(fixWarehouse.getCangKu());
            BianHaoText.setText(fixWarehouse.getBianHao());
        }
        return view;
    }
}
