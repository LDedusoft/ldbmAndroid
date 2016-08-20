package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.FixingsType;

import java.util.List;

/**
 * 配件列表adapter
 * Created by wangjianwei on 2016/6/29.
 * {"ID":5,"LeiBie":"脚垫类","BianHao":"001001"}
 */
public class SelectFixingsTypeAdapter extends ArrayAdapter<FixingsType> {
    private int resourceId;
    private TextView idText;
    private TextView LeiBieText;
    private TextView BianHaoText;
    private FixingsType fixingsType;

    public SelectFixingsTypeAdapter(Context context, int textViewResourceId, List<FixingsType> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fixingsType = getItem(position);
        View view;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view = convertView;
        }

        if(fixingsType!=null){
            idText = (TextView)view.findViewById(R.id.fixingstype_item_id);
            LeiBieText = (TextView)view.findViewById(R.id.fixingstype_item_leibie);
            BianHaoText =  (TextView)view.findViewById(R.id.fixingstype_item_bianhao);
            idText.setText(String.valueOf(fixingsType.getID()));
            LeiBieText.setText(fixingsType.getLeiBie());
            BianHaoText.setText(fixingsType.getBianHao());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }
}
