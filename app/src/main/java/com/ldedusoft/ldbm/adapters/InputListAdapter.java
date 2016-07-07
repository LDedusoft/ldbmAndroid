package com.ldedusoft.ldbm.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText numberEdit;
    private LinearLayout itemlayout;
    private CheckBox checkBox;
    private ImageView pointImg;
    private int touchedPosition;//缓存当前位置

    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> textHashMap = new HashMap<Integer, String>();

    //定义一个HashMap，用来存放时间的值，Key是position
    HashMap<Integer, String> timeHashMap = new HashMap<Integer, String>();

    //定义一个HashMap，用来存放复选框状态，Key是position
    HashMap<Integer, String> checkBoxMap = new HashMap<Integer, String>();

    //定义一个HashMap，用来存放数据对象，Key是position
    HashMap<Integer, InputItem> inputItemMap = new HashMap<Integer, InputItem>();



    public InputListAdapter(Activity context,int textViewResourceId, List<InputItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    /**
     * 获取关联项的值
     * @param relationItem
     * @return
     */
    private String getRelationValue(String relationItem){
        String relationValue = "";
        for (Integer key:inputItemMap.keySet()){
            InputItem item = inputItemMap.get(key);
            if(item.getItemTitle().equals(relationItem)){
                relationValue = item.getValue();
                break;
            }
        }
        return relationValue;
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
            itemlayout = (LinearLayout)view.findViewById(R.id.input_item_layout);
             titleView = (TextView) view.findViewById(R.id.input_item_title);
             editText = (EditText)view.findViewById(R.id.input_item_edittext);
             valueText = (TextView)view.findViewById(R.id.input_item_textview);
            clickText = (TextView)view.findViewById(R.id.input_item_clicktext);
            timeText = (TextView)view.findViewById(R.id.input_item_timetext);
            numberEdit = (EditText)view.findViewById(R.id.input_item_edittext_number);
            checkBox = (CheckBox)view.findViewById(R.id.input_item_checkbox);
            pointImg = (ImageView)view.findViewById(R.id.input_item_pointImg);


            titleView.setText(inputItem.getItemTitle());
            if(inputItem.isRequired()){
                pointImg.setVisibility(View.VISIBLE);
            }else{
                pointImg.setVisibility(View.INVISIBLE);
            }

            switch (type){
                case 0:
                    valueText.setVisibility(View.VISIBLE); //不可修改文本
                    valueText.setText(inputItem.getValue());
                    break;
                case 1:
                    editText.setVisibility(View.VISIBLE);  //输入框
                    editText.setHint(inputItem.getHint());
                    editText.setText(inputItem.getValue());
                    /*保存焦点位置*/
                    editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    break;
                case 2:
                    clickText.setVisibility(View.VISIBLE); //文本可点击
                    String value = inputItem.getValue();
                    if(!TextUtils.isEmpty(value)) {
                        clickText.setText(inputItem.getValue());
                    }else{
                        clickText.setHint(inputItem.getHint());
                    }
                    clickText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            InputItem tempItem = inputItemMap.get(position);
                            String relationItem = tempItem.getRelationItem();//获取关联项title
                            String relationValue ="";
                            if(!TextUtils.isEmpty(relationItem)){ //如果有关联项
                                relationValue = getRelationValue(relationItem);//获取关联值
                                if(TextUtils.isEmpty(relationValue)){//如果关联值为空，提示
                                    Toast.makeText(v.getContext(),"请先填写"+relationItem,Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            String path = tempItem.getIntentPath();
                            Log.d("跳转路径：", path);
                            Intent intent = new Intent(path);
                            intent.putExtra("position",position);
                            intent.putExtra("id", tempItem.getItemId());
                            intent.putExtra("title", tempItem.getItemTitle());
                            intent.putExtra("param",tempItem.getIntentParam());
                            intent.putExtra("relation",relationValue); //传入关联值
                            mContext.startActivityForResult(intent, tempItem.getIntentRequestCode()); //跳转 需要返回数据
                        }
                    });
                    /*保存焦点位置*/
                    clickText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    break;
                case 3:
                    timeText.setVisibility(View.VISIBLE); //日期文本
                    String timeValue = inputItem.getValue();
                    if(!TextUtils.isEmpty(timeValue)) {
                        timeText.setText(inputItem.getValue());
                    }else{
                        timeText.setHint(inputItem.getHint());
                    }
                    timeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                                    timeHashMap.put(position, year + "-" + (mounth + 1) + "-" + day + " 00:00:00");
                                    notifyDataSetChanged();
                                }
                            },
                                    c.get(Calendar.YEAR),
                                    c.get(Calendar.MONTH),
                                    c.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                    /*保存焦点位置*/
                    timeText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    break;
                case 4:
                    break;
                case 5: //复选
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            InputItem tempItem = inputItemMap.get(position);
                            String val ="false";
                            if (isChecked){
                                val = "true";
                            }else {
                                val = "false";
                            }
                            tempItem.setValue(val);
                            checkBoxMap.put(position,val);
                        }
                    });
                    /*保存焦点位置*/
                    checkBox.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    break;
                case 6: //输入数字
                    numberEdit.setVisibility(View.VISIBLE);
                    numberEdit.setHint(inputItem.getHint());
                    numberEdit.setText(inputItem.getValue());
                    /*保存焦点位置*/
                    numberEdit.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    break;
                default:
                    break;
            }
//            /*保存焦点位置*/
//            editText.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        touchedPosition = position;
//                    }
//                    return false;
//                }
//            });
//
//            numberEdit.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        touchedPosition = position;
//                    }
//                    return false;
//                }
//            });

            //输入文字改变后保存到map中，防止滚动后错乱
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    //将editText中改变的值设置的HashMap中
                    textHashMap.put(position, s.toString());
                    InputItem tempItem = inputItemMap.get(position);
                    tempItem.setValue(s.toString());
                }
            });
            //输入框文字改变后保存到map中，防止滚动后错乱
            numberEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    //将editText中改变的值设置的HashMap中
                    textHashMap.put(position, s.toString());
                    InputItem tempItem = inputItemMap.get(position);
                    tempItem.setValue(s.toString());
                }
            });

        }
        //如果hashMap不为空，就设置的editText
        if(textHashMap.get(position) != null){
            editText.setText(textHashMap.get(position));
            numberEdit.setText(textHashMap.get(position));
            inputItem.setValue(textHashMap.get(position));
        }
        if(timeHashMap.get(position) != null){
            timeText.setText(timeHashMap.get(position));
            inputItem.setValue(timeHashMap.get(position));
        }
        if(checkBoxMap.get(position) != null){
            if("true".equals(checkBoxMap.get(position))){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
            inputItem.setValue(checkBoxMap.get(position));
        }

        if (touchedPosition == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            editText.requestFocus();
            numberEdit.requestFocus();
        }else {
            editText.clearFocus();
            numberEdit.clearFocus();
        }
        return view;
    }
}
