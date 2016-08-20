package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.FixCarType;

import java.util.List;

/**
 * 配件列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {"ID":5,"LeiBie":"脚垫类","BianHao":"001001"}
 */
public class SelectFixCarTypeAdapter extends ArrayAdapter<FixCarType> {
    private int resourceId;
    private TextView idText;
    private TextView xinghaoText;
    private TextView bianmaText;
    private FixCarType fixCarType;

    public SelectFixCarTypeAdapter(Context context, int textViewResourceId, List<FixCarType> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fixCarType = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(fixCarType!=null){
            idText = (TextView)view.findViewById(R.id.fixcartype_item_id);
            xinghaoText = (TextView)view.findViewById(R.id.fixcartype_item_xinghao);
            bianmaText =  (TextView)view.findViewById(R.id.fixcartype_item_bianma);
            idText.setText(String.valueOf(fixCarType.getID()));
            xinghaoText.setText(fixCarType.getXingHao());
            bianmaText.setText(fixCarType.getBianMa());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }
}
