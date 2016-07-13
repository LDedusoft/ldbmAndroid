package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.FixingInfo;

import java.util.List;

/**
 * 配件信息
 * Created by wangjianwei on 2016/6/29.
 *  * {ID：id；BianHao：编号；MingCheng：名称；TuHao：图号；CangKu：仓库；KuCun：库存数量；
 * KuCunJinE：库存金额；KuCunJunJia：库存均价；DanWei：单位}
 */
public class SelectFixingAdapter extends ArrayAdapter<FixingInfo> {
    private int resourceId;
    private FixingInfo fixing;

    public SelectFixingAdapter(Context context, int textViewResourceId, List<FixingInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fixing = getItem(position);
        View view;
        ViewHolderFixing viewHolder;
        if(convertView==null){
             view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolderFixing();
            viewHolder.IDText = (TextView) view.findViewById(R.id.fixings_item_ID);
            viewHolder.BianHaoText = (TextView) view.findViewById(R.id.fixings_item_BianHao);
            viewHolder.MingChengText = (TextView) view.findViewById(R.id.fixings_item_MingCheng);
            viewHolder.TuHaoText = (TextView) view.findViewById(R.id.fixings_item_TuHao);
            viewHolder.CangKuText = (TextView) view.findViewById(R.id.fixings_item_CangKu);
            viewHolder.KuCunText = (TextView) view.findViewById(R.id.fixings_item_KuCun);
            viewHolder.KuCunJinEText = (TextView) view.findViewById(R.id.fixings_item_KuCunJinE);
            viewHolder.KuCunJunJiaText = (TextView) view.findViewById(R.id.fixings_item_KuCunJunJia);
            viewHolder.DanWeiText = (TextView) view.findViewById(R.id.fixings_item_DanWei);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder =(ViewHolderFixing)view.getTag();
        }

        if(fixing!=null){
            viewHolder.IDText.setText(String.valueOf(fixing.getID()));
            viewHolder.BianHaoText.setText(fixing.getBianHao());
            viewHolder.MingChengText.setText(fixing.getMingCheng());
            viewHolder.TuHaoText.setText(fixing.getTuHao());
            viewHolder.CangKuText.setText(fixing.getCangKu());
            viewHolder.KuCunText.setText(fixing.getKuCun());
            viewHolder.KuCunJinEText.setText(fixing.getKuCunJinE());
            viewHolder.KuCunJunJiaText.setText(fixing.getKuCunJunJia());
            viewHolder.DanWeiText.setText(fixing.getDanWei());
        }
        return view;
    }

    class ViewHolderFixing{
         TextView IDText;
         TextView BianHaoText;
         TextView MingChengText;
         TextView TuHaoText;
         TextView CangKuText;
         TextView KuCunText;
         TextView KuCunJinEText;
         TextView KuCunJunJiaText;
         TextView DanWeiText;
    }
}
