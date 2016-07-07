package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.CarCode;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectCarCodeAdapter extends ArrayAdapter<CarCode> {
    private int resourceId;
    private TextView codeText;
    private TextView brandText;
    private TextView nameText;
    private Context mContext;
    private CarCode carCode;

    public SelectCarCodeAdapter(Context context, int textViewResourceId, List<CarCode> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        carCode = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(carCode!=null){
            nameText = (TextView)view.findViewById(R.id.carcode_name);
            brandText = (TextView)view.findViewById(R.id.carcode_brand);
            codeText = (TextView)view.findViewById(R.id.carcode_code);
            nameText.setText(carCode.getName());
            brandText.setText(carCode.getBrand());
            codeText.setText(carCode.getCarCode());
        }
        return view;
    }
}
