package com.ldedusoft.ldbm.adapters.common;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.myAdapter.CommonAdapter;
import com.ldedusoft.ldbm.component.myAdapter.ViewHolder;
import com.ldedusoft.ldbm.interfaces.CommonNormalDetailListener;
import com.ldedusoft.ldbm.model.common.CommonNormal;

import java.util.List;

/**
 * 通用列表adapter
 * Created by wangjianwei on 2016/7/18.
 */
public class CommonNormalAdapter extends CommonAdapter {
    public CommonNormalAdapter(Context context, int textViewResourceId, List objects) {
            super(context, textViewResourceId, objects);
        }
    public void convert(ViewHolder viewHolder, Object item)
    {
        viewHolder.setText(R.id.commonNormal_item_name1,((CommonNormal)item).name1);
        viewHolder.setText(R.id.commonNormal_item_name2,((CommonNormal)item).name2);
        viewHolder.setText(R.id.commonNormal_item_name3,((CommonNormal)item).name3);
        viewHolder.setText(R.id.commonNormal_item_name4,((CommonNormal)item).name4);
        viewHolder.setText(R.id.commonNormal_item_value1,((CommonNormal)item).value1);
        viewHolder.setText(R.id.commonNormal_item_value2, ((CommonNormal) item).value2);
        viewHolder.setText(R.id.commonNormal_item_value3, ((CommonNormal) item).value3);
        viewHolder.setText(R.id.commonNormal_item_value4, ((CommonNormal) item).value4);
        if(TextUtils.isEmpty(((CommonNormal) item).name2)) { //如果没有第二行标题，则隐藏
            viewHolder.hiddenLayout(R.id.commonNormal_item_subTitle, View.GONE);
        }
        if(TextUtils.isEmpty(((CommonNormal) item).name3)) { //如果没有第二行标题，则隐藏
            viewHolder.hiddenLayout(R.id.commonNormal_item_thdTitle, View.GONE);
        }
        if(TextUtils.isEmpty(((CommonNormal) item).name4)) { //如果没有第二行标题，则隐藏
        viewHolder.hiddenLayout(R.id.commonNormal_item_fourTitle, View.GONE);
    }
        if(((CommonNormal)item).details==false) { //如果没有详情，则隐藏按钮
            viewHolder.hiddenLayout(R.id.commonNormal_item_actionLayout, View.INVISIBLE);
        }else{ //有详情，增加点击事件
            viewHolder.hiddenLayout(R.id.commonNormal_item_actionLayout, View.VISIBLE);
            String dataSource = ((CommonNormal) item).dataSource;
            String title = ((CommonNormal) item).title;
            viewHolder.setLayoutClickAction(R.id.commonNormal_item_actionLayout, dataSource,title, new CommonNormalDetailListener() {
                @Override
                public void OnDetailClick(String ds,String title) {
                    Intent intent = new Intent("activity.queryActivity.CommonQuery");
                    intent.putExtra("dataSource", ds);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.setLayoutClickAction(R.id.commonNormal_item_all, dataSource,title, new CommonNormalDetailListener() {
                @Override
                public void OnDetailClick(String ds,String title) {
                    Intent intent = new Intent("activity.queryActivity.CommonQuery");
                    intent.putExtra("dataSource", ds);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}


