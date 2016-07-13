package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.CarColor;

import java.util.List;

/**
 * 车辆颜色列表adapter
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectCarColorAdapter extends ArrayAdapter<CarColor> {
    private int resourceId;
    private TextView colorText;
    private CarColor carColor;

    public SelectCarColorAdapter(Context context, int textViewResourceId, List<CarColor> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        carColor = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(carColor!=null){
            colorText = (TextView)view.findViewById(R.id.carcolor_Color);
            colorText.setText(carColor.getColor());
        }
        return view;
    }
}
