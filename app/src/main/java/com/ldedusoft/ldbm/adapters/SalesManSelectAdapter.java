package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.SalesMan;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/29.
 */
public class SalesManSelectAdapter extends ArrayAdapter<SalesMan> {
    private int resourceId;
    private TextView idText;
    private TextView nameText;
    private TextView numberText;
    private TextView departmentText;
    private TextView companyText;
    private Context mContext;
    private SalesMan salesMan;

    public SalesManSelectAdapter(Context context, int textViewResourceId, List<SalesMan> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        salesMan = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(salesMan!=null){
            nameText = (TextView)view.findViewById(R.id.salesman_name);
            idText = (TextView)view.findViewById(R.id.salesman_id);
            numberText = (TextView)view.findViewById(R.id.salesman_number);
            departmentText = (TextView)view.findViewById(R.id.salesman_department);
            companyText = (TextView)view.findViewById(R.id.salesman_company);

            idText.setText(String.valueOf(salesMan.getId()));
            numberText.setText(salesMan.getNumber());
            nameText.setText(salesMan.getName());
            departmentText.setText(salesMan.getDepartment());
            companyText.setText(salesMan.getCompany());
        }
        return view;
    }
}
