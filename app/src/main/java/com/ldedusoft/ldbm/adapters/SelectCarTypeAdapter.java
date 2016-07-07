package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.CarType;

import java.util.List;

/**
 * 车型列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {ID：id；Type：车型；Series：车系；Brand：品牌;PriceOut1:价格}
 */
public class SelectCarTypeAdapter extends ArrayAdapter<CarType> {
    private int resourceId;
    private TextView idText;
    private TextView SeriesText;
    private TextView BrandText;
    private TextView PriceOut1Text;
    private Context mContext;
    private CarType carType;

    public SelectCarTypeAdapter(Context context, int textViewResourceId, List<CarType> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        carType = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(carType!=null){
            idText = (TextView)view.findViewById(R.id.carType_ID);
            SeriesText = (TextView)view.findViewById(R.id.carType_Series);
            BrandText = (TextView)view.findViewById(R.id.carType_Brand);
            PriceOut1Text = (TextView)view.findViewById(R.id.carType_PriceOut1);
            idText.setText(String.valueOf(carType.getID()));
            SeriesText.setText(carType.getSeries());
            BrandText.setText(carType.getBrand());
            PriceOut1Text.setText(carType.getPriceOut1());
        }
        return view;
    }
}
