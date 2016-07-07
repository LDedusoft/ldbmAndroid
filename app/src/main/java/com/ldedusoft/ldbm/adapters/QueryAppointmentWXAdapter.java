package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Reception;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/30.
 */
public class QueryAppointmentWXAdapter extends ArrayAdapter<Reception> {
    private int resourceId;
    private Reception reception;
    public QueryAppointmentWXAdapter(Context context, int textViewResourceId, List<Reception> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        reception = getItem(position);
        View view ;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view = convertView;
        }
        if(reception!=null){
//            <!--{Id：id；DanHao：单号；jdTime：接待时间；CarCode：车牌号；
//            MingCheng：客户名称；WeiXiuWay：维修方式；ywLeiBie：业务类别}-->

            TextView idText = (TextView)view.findViewById(R.id.appointment_wx_id);
            TextView carCodeText = (TextView)view.findViewById(R.id.appointment_wx_CarCode);
            TextView danHaoText = (TextView)view.findViewById(R.id.appointment_wx_danHao);
            TextView djTimeText = (TextView)view.findViewById(R.id.appointment_wx_jdTime);
            TextView mingChengText = (TextView)view.findViewById(R.id.appointment_wx_MingCheng);
            TextView weiXiuWayText = (TextView)view.findViewById(R.id.appointment_wx_WeiXiuWay);
            TextView ywLeiBieText = (TextView)view.findViewById(R.id.appointment_wx_ywLeiBie);

            idText.setText(String.valueOf(reception.getId()));
            carCodeText.setText(reception.getCarCode());
            danHaoText.setText(reception.getDanHao());
            djTimeText.setText(reception.getJdTime());
            mingChengText.setText(reception.getMingCheng());
            weiXiuWayText.setText(reception.getWeiXiuWay());
            ywLeiBieText.setText(reception.getYwLeiBie());
        }
        return view;
    }


}
