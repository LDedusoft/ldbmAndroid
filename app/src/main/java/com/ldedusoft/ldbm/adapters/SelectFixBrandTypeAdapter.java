package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Brand;

import java.util.List;

/**
 * 配件列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {"ID":5,"PinPai":"宝马"}
 */
public class SelectFixBrandTypeAdapter extends ArrayAdapter<Brand> {
    private int resourceId;
    private TextView idText;
    private TextView pinpaiText;
    private Brand brand;

    public SelectFixBrandTypeAdapter(Context context, int textViewResourceId, List<Brand> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        brand = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(brand!=null){
            idText = (TextView)view.findViewById(R.id.fixbrand_item_id);
            pinpaiText = (TextView)view.findViewById(R.id.fixbrand_item_pinpai);
            idText.setText(String.valueOf(brand.getID()));
            pinpaiText.setText(brand.getPinPai());
        }
        return view;
    }
}
