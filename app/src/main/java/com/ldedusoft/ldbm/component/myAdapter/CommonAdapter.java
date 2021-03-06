package com.ldedusoft.ldbm.component.myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用适配器
 * Created by wangjianwei on 2016/7/16.
 */
public abstract class CommonAdapter extends BaseAdapter{
protected LayoutInflater mInflater;
protected Context mContext;
protected List mDatas;
protected final int mItemLayoutId;

        public CommonAdapter(Context context, int itemLayoutId, List mDatas)
        {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
            this.mDatas = mDatas;
            this.mItemLayoutId = itemLayoutId;
        }

        @Override
        public int getCount()
        {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position)
        {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder viewHolder = getViewHolder(position, convertView,
                    parent);
            convert(viewHolder, getItem(position));
            return viewHolder.getConvertView();

        }

        public abstract void convert(ViewHolder helper, Object item);

        private ViewHolder getViewHolder(int position, View convertView,
                                         ViewGroup parent)
        {
            return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                    position);
        }

}
