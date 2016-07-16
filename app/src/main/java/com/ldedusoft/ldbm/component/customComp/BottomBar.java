package com.ldedusoft.ldbm.component.customComp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.home.HomeActivity;
import com.ldedusoft.ldbm.activity.part.PartActivity;
import com.ldedusoft.ldbm.activity.repair.RepairActivity;
import com.ldedusoft.ldbm.activity.report.ReportActivity;
import com.ldedusoft.ldbm.activity.wholeCar.WholecarActivity;
import com.ldedusoft.ldbm.component.iconfont.IconfontView;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class BottomBar extends LinearLayout implements View.OnClickListener {
    public BottomBar(Context context,AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ldbm_bottom_bar, this);
        TextView homeTxt = (TextView)findViewById(R.id.txt_toolbar_home);
        TextView partTxt = (TextView)findViewById(R.id.txt_bar_peijian);
        TextView repairTxt = (TextView)findViewById(R.id.txt_bar_weixiu);
        TextView wholecarTxt = (TextView)findViewById(R.id.txt_bar_zhengche);
        TextView reportTxt = (TextView)findViewById(R.id.txt_bar_baobiao);
        homeTxt.setOnClickListener(this);
        partTxt.setOnClickListener(this);
        repairTxt.setOnClickListener(this);
        wholecarTxt.setOnClickListener(this);
        reportTxt.setOnClickListener(this);

        LinearLayout homeLayout = (LinearLayout)findViewById(R.id.layout_toolbar_home);
        LinearLayout partLayout = (LinearLayout)findViewById(R.id.layout_toolbar_part);
        LinearLayout repairLayout = (LinearLayout)findViewById(R.id.layout_toolbar_repair);
        LinearLayout wholecarLayout = (LinearLayout)findViewById(R.id.layout_toolbar_wholecar);
        LinearLayout reportLayout = (LinearLayout)findViewById(R.id.layout_toolbar_report);
        homeLayout.setOnClickListener(this);
        partLayout.setOnClickListener(this);
        repairLayout.setOnClickListener(this);
        wholecarLayout.setOnClickListener(this);
        reportLayout.setOnClickListener(this);

        IconfontView homeBtn = (IconfontView)findViewById(R.id.btn_toolbar_home); //主页
        IconfontView partBtn = (IconfontView)findViewById(R.id.btn_toolbar_part); //配件
        IconfontView repairBtn = (IconfontView)findViewById(R.id.btn_toolbar_repair); //修理
        IconfontView wholecarBtn = (IconfontView)findViewById(R.id.btn_toolbar_wholecar); //整车
        IconfontView reportBtn = (IconfontView)findViewById(R.id.btn_toolbar_report); //报表
        homeBtn.setOnClickListener(this);
        partBtn.setOnClickListener(this);
        repairBtn.setOnClickListener(this);
        wholecarBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);

        String activityName = context.getClass().getName();
        if(activityName.indexOf("HomeActivity")!=-1){
           // homeBtn.setBackgroundResource(R.drawable.bottom_bar_home_selected);
            homeBtn.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
            homeTxt.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
        }else if (activityName.indexOf("RepairActivity")!=-1){
//            repairBtn.setBackgroundResource(R.drawable.bottom_bar_weixiu_selected);
            repairBtn.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
            repairTxt.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
        }else if (activityName.indexOf("WholecarActivity")!=-1||activityName.indexOf("NegotiateActivity")!=-1){
//            wholecarBtn.setBackgroundResource(R.drawable.bottom_bar_zhengche_selected);
            wholecarBtn.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
            wholecarTxt.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
        }else if (activityName.indexOf("PartActivity")!=-1){
//            partBtn.setBackgroundResource(R.drawable.bottom_bar_peijian_selected);
            partBtn.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
            partTxt.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
        }else if (activityName.indexOf("ReportActivity")!=-1){
//            reportBtn.setBackgroundResource(R.drawable.bottom_bar_baobiao_selected);
            reportBtn.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
            reportTxt.setTextColor(this.getResources().getColor(R.color.bootomBarTextSelected));
        }
    }

    /**
     * 按钮点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        String activityName = v.getContext().getClass().getName();
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_toolbar_home:
                if(activityName.indexOf("HomeActivity")==-1) {
                    intent.setClass(v.getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.layout_toolbar_home:
                if(activityName.indexOf("HomeActivity")==-1) {
                    intent.setClass(v.getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.txt_toolbar_home:
                if(activityName.indexOf("HomeActivity")==-1) {
                    intent.setClass(v.getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_toolbar_repair:
                if(activityName.indexOf("RepairActivity")==-1) {
                    intent.setClass(v.getContext(), RepairActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.layout_toolbar_repair:
                if(activityName.indexOf("RepairActivity")==-1) {
                    intent.setClass(v.getContext(), RepairActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.txt_bar_weixiu:
                if(activityName.indexOf("RepairActivity")==-1) {
                    intent.setClass(v.getContext(), RepairActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_toolbar_wholecar:
                if(activityName.indexOf("WholecarActivity")==-1) {
                    intent.setClass(v.getContext(), WholecarActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.layout_toolbar_wholecar:
                if(activityName.indexOf("WholecarActivity")==-1) {
                    intent.setClass(v.getContext(), WholecarActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.txt_bar_zhengche:
                if(activityName.indexOf("WholecarActivity")==-1) {
                    intent.setClass(v.getContext(), WholecarActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_toolbar_part:
                if(activityName.indexOf("PartActivity")==-1) {
                    intent.setClass(v.getContext(), PartActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.layout_toolbar_part:
                if(activityName.indexOf("PartActivity")==-1) {
                    intent.setClass(v.getContext(), PartActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.txt_bar_peijian:
                if(activityName.indexOf("PartActivity")==-1) {
                    intent.setClass(v.getContext(), PartActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_toolbar_report:
                if(activityName.indexOf("ReportActivity")==-1) {
                    intent.setClass(v.getContext(), ReportActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.layout_toolbar_report:
                if(activityName.indexOf("ReportActivity")==-1) {
                    intent.setClass(v.getContext(), ReportActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.txt_bar_baobiao:
                if(activityName.indexOf("ReportActivity")==-1) {
                    intent.setClass(v.getContext(), ReportActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            default:
                break;
        }
    }
}
