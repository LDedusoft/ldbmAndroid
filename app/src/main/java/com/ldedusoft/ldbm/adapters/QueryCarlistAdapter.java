package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.CarList;

import java.util.List;

/**
 * 在库车辆查询
 * Created by wangjianwei on 2016/6/30.
 */
public class QueryCarlistAdapter extends ArrayAdapter<CarList> {
    private int resourceId;
    private CarList item;
    public QueryCarlistAdapter(Context context, int textViewResourceId, List<CarList> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item = getItem(position);
        View view ;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view = convertView;
        }
        if(item!=null){
//            CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；
//            * Brand：品牌；Series：车系；Type：车型}

            TextView  CarCodeText = (TextView)view.findViewById(R.id.carlist_item_CarCode);
            TextView  ColorText = (TextView)view.findViewById(R.id.carlist_item_Color);
            TextView  DanJiaText = (TextView)view.findViewById(R.id.carlist_item_DanJia);
            TextView  VinText = (TextView)view.findViewById(R.id.carlist_item_Vin);
            TextView  EngineNoText = (TextView)view.findViewById(R.id.carlist_item_EngineNo);
            TextView  BrandText = (TextView)view.findViewById(R.id.carlist_item_Brand);
            TextView  SeriesText = (TextView)view.findViewById(R.id.carlist_item_Series);
            TextView  TypeText = (TextView)view.findViewById(R.id.carlist_item_Type);

            CarCodeText.setText(item.getCarCode());
            ColorText.setText(item.getColor());
            DanJiaText.setText(item.getDanJia());
            VinText.setText(item.getVin());
            EngineNoText.setText(item.getEngineNo());
            BrandText.setText(item.getBrand());
            SeriesText.setText(item.getSeries());
            TypeText.setText(item.getType());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }


}
