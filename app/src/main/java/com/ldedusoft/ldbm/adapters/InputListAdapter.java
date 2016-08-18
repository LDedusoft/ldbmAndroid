package com.ldedusoft.ldbm.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
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
import com.ldedusoft.ldbm.interfaces.InputItemDelListener;
import com.ldedusoft.ldbm.model.InputItem;

import org.json.JSONObject;

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
    private LinearLayout actionLayout,actionCarLayout;
    private LinearLayout customLayout,customCarLayout;
    private TextView fixName,carBrand;
    private TextView fixId,carId,carCangku,carColor,carType,carSeries;
    private EditText fixPirce,carPirce ;
    private EditText fixNumber,carDingjin ;
    private TextView delButton,delCarButton;
    private LinearLayout delLayout,delCarLayout;
    private TextView unitBtn;


    private InputItemDelListener inputItemDelListener;
    private int touchedPosition=-1;//缓存当前位置
    private String touchedId;//缓存当前点击的id

    //用来存放EditText的值，Key是position
    SparseArray<String> textHashMap = new SparseArray<String>();

    //用来存放时间的值，Key是position
    SparseArray<String> timeHashMap = new  SparseArray<String>();

    //用来存放复选框状态，Key是position
    SparseArray<String> checkBoxMap = new  SparseArray<String>();

    //用来存放数据对象，Key是position
    HashMap<Integer, InputItem> inputItemMap = new HashMap<Integer, InputItem>();

    //存放fix组件的价格值
    HashMap<Integer, String> fixPriceHashMap = new HashMap<Integer, String>();
    //存放fix组件的数量值
    HashMap<Integer, String> fixNumberHashMap = new HashMap<Integer, String>();
    //存放car组件的价格值
    HashMap<Integer, String> carPriceHashMap = new HashMap<Integer, String>();
    //存放car组件的订金值
    HashMap<Integer, String> carDingjinHashMap = new HashMap<Integer, String>();
    //存放car组件的仓库值
    HashMap<Integer, String> carCangkuHashMap = new HashMap<Integer, String>();
    //存放car组件的颜色值
    HashMap<Integer, String> carColorHashMap = new HashMap<Integer, String>();





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
            actionLayout = (LinearLayout)view.findViewById(R.id.input_item_add_layout);
            actionCarLayout = (LinearLayout)view.findViewById(R.id.input_item_addCar_layout);
            customLayout = (LinearLayout)view.findViewById(R.id.input_item_custom_layout);
            customCarLayout = (LinearLayout)view.findViewById(R.id.input_item_custom_car_layout);
             fixName = (TextView) view.findViewById(R.id.input_list_item_fix_name);
             fixId = (TextView) view.findViewById(R.id.input_list_item_fix_id);
             fixPirce = (EditText) view.findViewById(R.id.input_list_item_fix_price);
             fixNumber = (EditText) view.findViewById(R.id.input_list_item_fix_number);
            delButton = (TextView)view.findViewById(R.id.input_list_item_fix_delBtn);
            delCarButton = (TextView)view.findViewById(R.id.input_list_item_car_delBtn);
            delLayout = (LinearLayout)view.findViewById(R.id.input_list_item_fix_del_layout);
            delCarLayout = (LinearLayout)view.findViewById(R.id.input_list_item_car_del_layout);
            unitBtn = (TextView)view.findViewById(R.id.input_item_unit);
            carId = (TextView) view.findViewById(R.id.input_list_item_car_id);
            carBrand = (TextView) view.findViewById(R.id.input_list_item_car_brand);
            carSeries = (TextView) view.findViewById(R.id.input_list_item_car_series);
            carType = (TextView) view.findViewById(R.id.input_list_item_car_type);
            carPirce = (EditText) view.findViewById(R.id.input_list_item_car_price);
            carDingjin = (EditText) view.findViewById(R.id.input_list_item_car_dingjin);
            carCangku = (TextView) view.findViewById(R.id.input_list_item_car_cangku);
            carColor= (TextView) view.findViewById(R.id.input_list_item_car_color);

            if(!TextUtils.isEmpty(inputItem.getUnit())){
                unitBtn.setVisibility(View.VISIBLE);
                unitBtn.setText(inputItem.getUnit());
            }
            titleView.setText(inputItem.getItemTitle());
            if(inputItem.isRequired()){
                pointImg.setVisibility(View.VISIBLE);
            }else{
                pointImg.setVisibility(View.INVISIBLE);
            }

            switch (type){
                case 0:
                    valueText.setVisibility(View.VISIBLE); //不可修改文本
                    valueText.setHint(inputItem.getHint());
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
                        if(!TextUtils.isEmpty(inputItem.getDispValue())){
                            clickText.setText(inputItem.getDispValue()); //显示值
                        }else {
                            clickText.setText(inputItem.getValue());
                        }
                    }else{
                        clickText.setHint(inputItem.getHint()); //提示值
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
                                    Toast.makeText(v.getContext(),"请填写"+relationItem,Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            String path = tempItem.getIntentPath();
                            if(!TextUtils.isEmpty(path)){
                                Log.d("跳转路径：", path);
                                Intent intent = new Intent(path);
                                intent.putExtra("position",position);
                                intent.putExtra("id", tempItem.getItemId());
                                intent.putExtra("title", tempItem.getItemTitle());
                                intent.putExtra("param",tempItem.getIntentParam());
                                intent.putExtra("relation",relationValue); //传入关联值
                                mContext.startActivityForResult(intent, tempItem.getIntentRequestCode()); //跳转 需要返回数据
                            }

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
                                    Log.d("设置时间：",year + "-" + (mounth + 1) + "-" + day + " 00:00:00");
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
                    InputItem ti = inputItemMap.get(position);
                    if("true".equals(ti.getValue())){
                        checkBox.setChecked(true);
                    }else if("false".equals(ti.getValue())) {
                        checkBox.setChecked(false);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        InputItem tempItem = inputItemMap.get(position);
                        String val = "false";
                        if (isChecked) {
                            val = "true";
                        } else {
                            val = "false";
                        }
                        tempItem.setValue(val);
                        checkBoxMap.put(position, val);
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
                case 10: //添加组件按钮
                    itemlayout.setVisibility(View.GONE);
                    actionLayout.setVisibility(View.VISIBLE);
                    actionLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputItem tempItem = inputItemMap.get(position);
                            String path = tempItem.getIntentPath();
                            if (!TextUtils.isEmpty(path)) {
                                Log.d("跳转路径：", path);
                                Intent intent = new Intent(path);
                                intent.putExtra("position", position);
                                intent.putExtra("id", tempItem.getItemId());
                                intent.putExtra("title", tempItem.getItemTitle());
                                intent.putExtra("param", tempItem.getIntentParam());
                                intent.putExtra("relation", ""); //传入关联值
                                mContext.startActivityForResult(intent, tempItem.getIntentRequestCode()); //跳转 需要返回数据
                            }

                        }
                    });
                    break;
                case 11: //增加配件
                    itemlayout.setVisibility(View.GONE);
                    actionLayout.setVisibility(View.GONE);
                    customLayout.setVisibility(View.VISIBLE);
                    delButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputItemDelListener.OnDelClick(position);
                        }
                    });
                    delLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputItemDelListener.OnDelClick(position);
                        }
                    });

                    String valueStr = inputItem.getValue();
                    //{"Name":""+name+"\",\"Id\":\""+id+"\",\"Num\":\""+number+"\",\"Price\":\""+price+"\"}");
                    try {
                        JSONObject valueJson = new JSONObject(valueStr);
                        fixName.setText(valueJson.getString("Name"));
                        fixId.setText(valueJson.getString("Id"));
                        fixPirce.setText(valueJson.getString("Price"));
                        fixPriceHashMap.put(position, valueJson.getString("Price")); //加入缓存
                        fixNumber.setText(valueJson.getString("Num"));
                        fixNumberHashMap.put(position, valueJson.getString("Num")); //加入缓存
                    }catch (Exception e){e.printStackTrace();}
                    /*保存焦点位置*/
                    fixPirce.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "fixPirce";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    /*保存焦点位置*/
                    fixNumber.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "fixNumber";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    //输入文字改变后保存到map中，防止滚动后错乱
                    fixPirce.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //将editText中改变的值设置的HashMap中
                            fixPriceHashMap.put(position, s.toString());
                            InputItem tempItem = inputItemMap.get(position);
                            String tempValue = tempItem.getValue();
                            try{
                                JSONObject obj = new JSONObject(tempValue);
                                obj.put("Price",s.toString());
                                tempItem.setValue(obj.toString());
                            }catch (Exception e){}
                            inputItemMap.put(position,tempItem);
                        }
                    });
                    //输入文字改变后保存到map中，防止滚动后错乱
                    fixNumber.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //将editText中改变的值设置的HashMap中
                            fixNumberHashMap.put(position, s.toString());
                            InputItem tempItem = inputItemMap.get(position);
                            String tempValue = tempItem.getValue();
                            try{
                                JSONObject obj = new JSONObject(tempValue);
                                obj.put("Num",s.toString());
                                tempItem.setValue(obj.toString());
                            }catch (Exception e){}
                            inputItemMap.put(position,tempItem);
                        }
                    });
                    break;
                 case 12: //添加车辆按钮
                    itemlayout.setVisibility(View.GONE);
                    actionCarLayout.setVisibility(View.VISIBLE);
                     actionCarLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputItem tempItem = inputItemMap.get(position);
                            String path = tempItem.getIntentPath();
                            if (!TextUtils.isEmpty(path)) {
                                Log.d("跳转路径：", path);
                                Intent intent = new Intent(path);
                                intent.putExtra("position", position);
                                intent.putExtra("id", tempItem.getItemId());
                                intent.putExtra("title", tempItem.getItemTitle());
                                intent.putExtra("param", tempItem.getIntentParam());
                                intent.putExtra("relation", ""); //传入关联值
                                mContext.startActivityForResult(intent, tempItem.getIntentRequestCode()); //跳转 需要返回数据
                            }

                        }
                    });
                    break;
                case 13: //增加车辆
                    itemlayout.setVisibility(View.GONE);
                    actionLayout.setVisibility(View.GONE);
                    customCarLayout.setVisibility(View.VISIBLE);
                    delCarButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputItemDelListener.OnDelCarClick(position);
                        }
                    });
                    delCarLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputItemDelListener.OnDelCarClick(position);
                        }
                    });

                    String carValueStr = inputItem.getValue();
                    //{CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；Brand：品牌；Series：车系；Type：车型} dingjin:订金 cangku:""
                    try {
                        JSONObject valueJson = new JSONObject(carValueStr);
                        carBrand.setText(valueJson.getString("Brand"));
                        carSeries.setText(valueJson.getString("Series"));
                        carType.setText(valueJson.getString("Type"));
                        carId.setText(valueJson.getString("CarCode"));
                        carPirce.setText(valueJson.getString("DanJia"));
                        carPriceHashMap.put(position, valueJson.getString("DanJia")); //加入缓存
                        carDingjin.setText(valueJson.getString("dingjin"));
                        carDingjinHashMap.put(position, valueJson.getString("dingjin")); //加入缓存
                        carColor.setText(valueJson.getString("Color"));
                        carColorHashMap.put(position, valueJson.getString("Color")); //加入缓存
                        carCangku.setText(valueJson.getString("cangku"));
                        carCangkuHashMap.put(position, valueJson.getString("cangku")); //加入缓存
                    }catch (Exception e){e.printStackTrace();}
                     /*保存焦点位置*/
                    carPirce.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "fixPirce";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    /*保存焦点位置*/
                    carDingjin.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "fixNumber";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    /*选择仓库*/
                    carCangku.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputItem tempItem = inputItemMap.get(position);
                            String path = "activity.selectActivity.CarWarehouseSelect";
                            if(!TextUtils.isEmpty(path)){
                                Log.d("跳转路径：", path);
                                Intent intent = new Intent(path);
                                intent.putExtra("position",position);
                                intent.putExtra("id", tempItem.getItemId());
                                intent.putExtra("title", "仓库列表");
                                intent.putExtra("param","");
                                intent.putExtra("relation",""); //传入关联值
                                mContext.startActivityForResult(intent,4); //跳转 需要返回数据
                            }

                        }
                    });
                    /*选择颜色*/
                    carColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputItem tempItem = inputItemMap.get(position);
                            String path = "activity.selectActivity.CarColorSelect";
                            if (!TextUtils.isEmpty(path)) {
                                Log.d("跳转路径：", path);
                                Intent intent = new Intent(path);
                                intent.putExtra("position", position);
                                intent.putExtra("id", tempItem.getItemId());
                                intent.putExtra("title", "车辆颜色");
                                intent.putExtra("param", "");
                                intent.putExtra("relation", ""); //传入关联值
                                mContext.startActivityForResult(intent, 5); //跳转 需要返回数据
                            }

                        }
                    });




                    /*保存焦点位置*/
                    carPirce.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "carPirce";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    /*保存焦点位置*/
                    carDingjin.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchedId = "carDingjin";
                                touchedPosition = position;
                            }
                            return false;
                        }
                    });
                    //输入文字改变后保存到map中，防止滚动后错乱
                    carPirce.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //将editText中改变的值设置的HashMap中
                            carPriceHashMap.put(position, s.toString());
                            InputItem tempItem = inputItemMap.get(position);
                            String tempValue = tempItem.getValue();
                            try{
                                JSONObject obj = new JSONObject(tempValue);
                                obj.put("DanJia",s.toString());
                                tempItem.setValue(obj.toString());
                            }catch (Exception e){}
                            inputItemMap.put(position,tempItem);
                        }
                    });
                    //输入文字改变后保存到map中，防止滚动后错乱
                    carDingjin.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //将editText中改变的值设置的HashMap中
                            carDingjinHashMap.put(position, s.toString());
                            InputItem tempItem = inputItemMap.get(position);
                            String tempValue = tempItem.getValue();
                            try{
                                JSONObject obj = new JSONObject(tempValue);
                                obj.put("dingjin",s.toString());
                                tempItem.setValue(obj.toString());
                            }catch (Exception e){}
                            inputItemMap.put(position, tempItem);
                        }
                    });
                    break;
                default:
                    break;
            }


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
        //如果hashMap不为空，就设置editText
        if(textHashMap.get(position) != null){
            editText.setText(textHashMap.get(position));
            numberEdit.setText(textHashMap.get(position));
            inputItem.setValue(textHashMap.get(position));
        }
        if(fixPriceHashMap.get(position) != null||fixNumberHashMap.get(position) != null){
            fixPirce.setText(fixPriceHashMap.get(position));
            fixNumber.setText(fixNumberHashMap.get(position));
            String tempValuePic = "{\"Name\":\""+fixName.getText()+"\",\"Id\":\""+ fixId.getText()+"\",\"Num\":\""+fixNumber.getText()+"\",\"Price\":\""+fixPirce.getText()+"\"}";
            inputItem.setValue(tempValuePic);
        }
        if(carPriceHashMap.get(position) != null||carDingjinHashMap.get(position) != null||carCangkuHashMap.get(position) != null||carColorHashMap.get(position) != null){
            carPirce.setText(carPriceHashMap.get(position));
            carDingjin.setText(carDingjinHashMap.get(position));
            carCangku.setText(carCangkuHashMap.get(position));
            carColor.setText(carColorHashMap.get(position));
            //{CarCode：车牌号；Color：颜色；DanJia：价格；Vin：Vin码；EngineNo：发动机号；Brand：品牌；Series：车系；Type：车型 ;dingjin:订金 ;cangku:仓库}
            String tempValuePic = "{\"CarCode\":\""+ carId.getText()+"\",\"Color\":\""+ carColor.getText()+"\",\"DanJia\":\""+carPirce.getText()+"\"," +
                    "\"Brand\":\""+carBrand.getText()+"\",\"Series\":\""+carSeries.getText()+"\",\"Type\":\""+carType.getText()+"\"," +
                    "\"dingjin\":\""+carDingjin.getText()+"\",\"cangku\":\""+carCangku.getText()+"\"}";
            inputItem.setValue(tempValuePic);
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

        if (touchedPosition!=-1 && touchedPosition == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            editText.requestFocus();
            numberEdit.requestFocus();
            if(inputItemMap.get(position).getInputType()==11) {
                if("fixPirce".equals(touchedId)){
                    fixPirce.requestFocus();
                }else{
                    fixNumber.requestFocus();
                }
            }
            if(inputItemMap.get(position).getInputType()==13) {
                if("carPirce".equals(touchedId)){
                    carPirce.requestFocus();
                }else{
                    carDingjin.requestFocus();
                }
            }
        }else {
            editText.clearFocus();
            numberEdit.clearFocus();
            fixNumber.clearFocus();
            fixPirce.clearFocus();
            carPirce.clearFocus();
            carDingjin.clearFocus();
        }
        return view;
    }

    /**
     * 更新缓存
     * 查找比position位置大的值，往前移动一位
     * @param position
     */
    public void updateCache(int position){
        /*更新inputItem缓存*/
        HashMap<Integer, InputItem> temp_inputItemMap = new HashMap<Integer, InputItem>();
        for (Integer key:inputItemMap.keySet()){
            if(key>position){
                temp_inputItemMap.put(key - 1, inputItemMap.get(key));
            }
        }
        inputItemMap.remove(position);
        for (Integer key:temp_inputItemMap.keySet()){
            inputItemMap.remove(key+1);//删除后一位的值
            inputItemMap.put(key,temp_inputItemMap.get(key));//将缓存的后一位值写入
        }
//        /*更新配件价格缓存*/
//        HashMap<Integer, String> temp_fixPriceHashMap= new HashMap<Integer, String>();
//        for (Integer key : fixPriceHashMap.keySet()) {
//            if (key > position) {
//                temp_fixPriceHashMap.put(key - 1, fixPriceHashMap.get(key));
//            }
//        }
//        fixPriceHashMap.remove(position);
//        for (Integer key : temp_fixPriceHashMap.keySet()) {
//            fixPriceHashMap.remove(key+1);//删除后一位的值
//            fixPriceHashMap.put(key, temp_fixPriceHashMap.get(key));
//        }
//        /*更新配件数量缓存*/
//        HashMap<Integer, String> temp_fixNumberHashMap = new HashMap<Integer, String>();
//        for (Integer key : fixNumberHashMap.keySet()) {
//            if (key > position) {
//                temp_fixNumberHashMap.put(key - 1, fixNumberHashMap.get(key));
//            }
//        }
//        fixNumberHashMap.remove(position);
//        for (Integer key : temp_fixNumberHashMap.keySet()) {
//            fixNumberHashMap.remove(key+1);//删除后一位的值
//            fixNumberHashMap.put(key, temp_fixNumberHashMap.get(key));
//        }

        fixPriceHashMap =  delListItem(fixPriceHashMap,position);
        fixNumberHashMap =  delListItem(fixNumberHashMap,position);
        carPriceHashMap =  delListItem(carPriceHashMap,position);
        carDingjinHashMap=  delListItem(carDingjinHashMap,position);
        carCangkuHashMap=  delListItem(carCangkuHashMap,position);
        carColorHashMap=  delListItem(carColorHashMap,position);


    }

    /**
     * 从list中删除元素
     * * @param thisMap
     * @param position
     * @return
     */
    private HashMap<Integer, String> delListItem(HashMap<Integer, String> thisMap,int position){
         /*更新配件数量缓存*/
        HashMap<Integer, String> temp_fixNumberHashMap = new HashMap<Integer, String>();
        for (Integer key : thisMap.keySet()) {
            if (key > position) {
                temp_fixNumberHashMap.put(key - 1, thisMap.get(key));
            }
        }
        thisMap.remove(position);
        for (Integer key : temp_fixNumberHashMap.keySet()) {
            thisMap.remove(key+1);//删除后一位的值
            thisMap.put(key, temp_fixNumberHashMap.get(key));
        }
        return thisMap;
    }

    public void setInputItemDelListener(InputItemDelListener inputItemDelListener){
        this.inputItemDelListener = inputItemDelListener;
    }
}
