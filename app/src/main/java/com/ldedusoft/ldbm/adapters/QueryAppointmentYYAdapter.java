package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Appointment;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/30.
 */
public class QueryAppointmentYYAdapter extends ArrayAdapter<Appointment> {
    private int resourceId;
    private Appointment appointment;
    public QueryAppointmentYYAdapter(Context context, int textViewResourceId, List<Appointment> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        appointment = getItem(position);
        View view ;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view = convertView;
        }
        if(appointment!=null){
            TextView idText = (TextView)view.findViewById(R.id.appointment_id);
            TextView carCodeText = (TextView)view.findViewById(R.id.appointment_carCode);
            TextView danHaoText = (TextView)view.findViewById(R.id.appointment_danHao);
            TextView djTimeText = (TextView)view.findViewById(R.id.appointment_djTime);
            TextView mingChengText = (TextView)view.findViewById(R.id.appointment_mingCheng);
            TextView wxFangShiText = (TextView)view.findViewById(R.id.appointment_wxFangShi);
            TextView ywLeiBieText = (TextView)view.findViewById(R.id.appointment_ywLeiBie);
            TextView yyTimeText = (TextView)view.findViewById(R.id.appointment_yyTime);

            idText.setText(String.valueOf(appointment.getId()));
            carCodeText.setText(appointment.getCarCode());
            danHaoText.setText(appointment.getDanHao());
            djTimeText.setText(appointment.getDjTime());
            mingChengText.setText(appointment.getMingCheng());
            wxFangShiText.setText(appointment.getWxFangShi());
            ywLeiBieText.setText(appointment.getYwLeiBie());
            yyTimeText.setText(appointment.getYyTime());
        }
        return view;
    }


}
