package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.adapters.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面：ViewPagerViewPager不在android sdk 自带jar包中，来源google 的补充组件android-support-v4.jar
 */
public class WelcomeViewActivity extends BaseActivity {
    private ViewPager mViewPager;
    List<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_welcome_layout);

        LayoutInflater mInflater = getLayoutInflater().from(this);
        try {
            View v1 = mInflater.inflate(R.layout.ldbm_welcome1, null);
            ImageView image1 = (ImageView)v1.findViewById(R.id.welcome_1_image);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_img1,options);
            image1.setImageBitmap(bmp);

            View v2 = mInflater.inflate(R.layout.ldbm_welcome2, null);
            ImageView image2 = (ImageView)v2.findViewById(R.id.welcome_2_image);
            Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_img2, options);
            image2.setImageBitmap(bmp2);

            View v3 = mInflater.inflate(R.layout.ldbm_welcome3, null);
            ImageView image3 = (ImageView)v3.findViewById(R.id.welcome_3_image);
            Bitmap bmp3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_img3, options);
            image3.setImageBitmap(bmp3);
            //添加页面数据
            viewList = new ArrayList<View>();
            viewList.add(v1);
            viewList.add(v2);
            viewList.add(v3);
            //实例化适配器
            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            mViewPager.setAdapter(new MyPagerAdapter(viewList));
            mViewPager.setCurrentItem(0); //设置默认当前页

            View view2 = viewList.get(2);
            Button button = (Button) view2.findViewById(R.id.button_1);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(WelcomeViewActivity.this, LoginActivity.class);
                    v.getContext().startActivity(intent);
                    System.gc();
                    System.runFinalization();
                    finish();
                }
            });

            View view0 = viewList.get(0);
            Button button0 = (Button) view0.findViewById(R.id.welcome_pass);
            button0.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(WelcomeViewActivity.this, LoginActivity.class);
                    v.getContext().startActivity(intent1);
                    System.gc();
                    System.runFinalization();
                    finish();
                }
            });
        }catch (Exception e){
            Intent intent1 = new Intent(WelcomeViewActivity.this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
    }
}
