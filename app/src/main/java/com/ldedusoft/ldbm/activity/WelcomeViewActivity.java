package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.adapters.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面：ViewPagerViewPager不在android sdk 自带jar包中，来源google 的补充组件android-support-v4.jar
 */
public class WelcomeViewActivity extends Activity {

    private ViewPager mViewPager;
    List<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_welcome_layout);

        LayoutInflater mInflater = getLayoutInflater().from(this);

        View v1 = mInflater.inflate(R.layout.layout1, null);
        View v2 = mInflater.inflate(R.layout.layout2, null);
        View v3 = mInflater.inflate(R.layout.layout3, null);

        //添加页面数据
        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        //实例化适配器
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(viewList));
        mViewPager.setCurrentItem(0); //设置默认当前页

        View view = viewList.get(2);
        TextView textView = (TextView) view.findViewById(R.id.text_1);
        textView.setText("我是第3页");
        Button button = (Button) view.findViewById(R.id.button_1);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(WelcomeViewActivity.this,LoginActivity.class);
                v.getContext().startActivity(intent);
              //  finish();
            }
        });
    }
}
