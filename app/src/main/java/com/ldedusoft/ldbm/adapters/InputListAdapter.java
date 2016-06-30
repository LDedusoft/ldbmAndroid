package com.ldedusoft.ldbm.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.InputItem;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangjianwei on 2016/6/28.
 */
public class InputListAdapter extends ArrayAdapter<InputItem> {

    private int resourceId;
    private TextView titleView;
    private  EditText editText;
    private TextView valueText;
    private InputItem inputItem;
    private TextView clickText;
    private TextView timeText;
    private Activity mContext;

    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    //定义一个HashMap，用来存放时间的值，Key是position
    HashMap<Integer, String> timeHashMap = new HashMap<Integer, String>();


    //定义一个HashMap，用来存放数据对象，Key是position
    HashMap<Integer, InputItem> inputItemMap = new HashMap<Integer, InputItem>();

    public InputListAdapter(Activity context,int textViewResourceId, List<InputItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
          inputItem = getItem(position);
        inputItemMap.put(position,inputItem);
        View view;
//        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//        }else {
//            view = convertView;
//        }

        if (inputItem != null) {
            int type = inputItem.getInputType();
             titleView = (TextView) view.findViewById(R.id.input_item_title);
             editText = (EditText)view.findViewById(R.id.input_item_edittext);
             valueText = (TextView)view.findViewById(R.id.input_item_textview);
            clickText = (TextView)view.findViewById(R.id.input_item_clicktext);
            timeText = (TextView)view.findViewById(R.id.input_item_timetext);
            titleView.setText(inputItem.getItemTitle());
            switch (type){
                case 0:
                    valueText.setVisibility(View.VISIBLE); //不可修改文本
                    valueText.setText(inputItem.getValue());
                    break;
                case 1:
                    editText.setVisibility(View.VISIBLE);  //输入框
                    editText.setHint(inputItem.getHint());
                    editText.setText(inputItem.getValue());
                    break;
                case 2:
                    clickText.setVisibility(View.VISIBLE); //文本可点击
                    String value = inputItem.getValue();
                    if(!TextUtils.isEmpty(value)) {
                        clickText.setText(inputItem.getValue());
                    }
                    clickText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputItem tempItem = inputItemMap.get(position);
                            String path = tempItem.getIntentPath();
                            Log.d("跳转路径：", path);
                            Intent intent = new Intent(path);
                            intent.putExtra("position",position);
                            intent.putExtra("itemId", inputItem.getItemId());
                            mContext.startActivityForResult(intent,tempItem.getIntentRequestCode()); //跳转 需要返回数据
                        }
                    });
                    break;
                case 3:
                    timeText.setVisibility(View.VISIBLE); //日期文本
                    timeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                                            timeHashMap.put(position , year + "-" + (mounth + 1) + "-" + day);
                                            notifyDataSetChanged();
                                        }
                                    },
                                    c.get(Calendar.YEAR),
                                    c.get(Calendar.MONTH),
                                    c.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                default:
                    break;
            }





            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //将editText中改变的值设置的HashMap中
                    hashMap.put(position, s.toString());
                    InputItem tempItem = inputItemMap.get(position);
                    tempItem.setValue(s.toString());
                }
            });
        }
        //如果hashMap不为空，就设置的editText
        if(hashMap.get(position) != null){
            editText.setText(hashMap.get(position));
            inputItem.setValue(hashMap.get(position));
        }
        if(timeHashMap.get(position) != null){
            timeText.setText(timeHashMap.get(position));
            inputItem.setValue(timeHashMap.get(position));
        }
        return view;
    }
}
