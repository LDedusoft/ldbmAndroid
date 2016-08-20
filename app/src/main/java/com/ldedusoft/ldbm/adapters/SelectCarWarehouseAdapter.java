package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.CarWarehouse;

import java.util.List;

/**
 * 整车仓库列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {ID：id；Name：仓库名称；Code：仓库编号}
 */
public class SelectCarWarehouseAdapter extends ArrayAdapter<CarWarehouse> {
    private int resourceId;
    private TextView idText;
    private TextView nameText;
    private TextView codeText;
    private Context mContext;
    private CarWarehouse carWarehouse;

    public SelectCarWarehouseAdapter(Context context, int textViewResourceId, List<CarWarehouse> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        carWarehouse = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(carWarehouse!=null){
            idText = (TextView)view.findViewById(R.id.carwarehouse_ID);
            nameText = (TextView)view.findViewById(R.id.carwarehouse_Name);
            codeText = (TextView)view.findViewById(R.id.carwarehouse_Code);
            idText.setText(String.valueOf(carWarehouse.getID()));
            nameText.setText(carWarehouse.getName());
            codeText.setText(carWarehouse.getCode());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }
}
