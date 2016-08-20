package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Plan;

import java.util.List;

/**
 * 采购计划
 * Created by wangjianwei on 2016/6/29.
 * {DanHao：单号;RiQi：日期；Num：单据数量；JinE：单据金额;MingCheng:供方名称}
 */
public class SelectPlanAdapter extends ArrayAdapter<Plan> {
    private int resourceId;
    private Plan plan;

    public SelectPlanAdapter(Context context, int textViewResourceId, List<Plan> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        plan = getItem(position);
        View view;
        ViewHolderFixing viewHolder;
        if(convertView==null){
             view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolderFixing();
            viewHolder.DanHaoText = (TextView) view.findViewById(R.id.plan_item_DanHao);
            viewHolder.RiQiText = (TextView) view.findViewById(R.id.plan_item_RiQi);
            viewHolder.MingChengText = (TextView) view.findViewById(R.id.plan_item_MingCheng);
            viewHolder.NumText = (TextView) view.findViewById(R.id.plan_item_Num);
            viewHolder.JinEText = (TextView) view.findViewById(R.id.plan_item_JinE);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder =(ViewHolderFixing)view.getTag();
        }

        if(plan!=null){
            viewHolder.DanHaoText.setText(plan.DanHao);
            viewHolder.RiQiText.setText(plan.RiQi);
            viewHolder.MingChengText.setText(plan.MingCheng);
            viewHolder.JinEText.setText(plan.JinE);
            viewHolder.NumText.setText(plan.Num);
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }

    class ViewHolderFixing{
         TextView DanHaoText;
         TextView RiQiText;
         TextView NumText;
         TextView JinEText;
         TextView MingChengText;
    }
}
