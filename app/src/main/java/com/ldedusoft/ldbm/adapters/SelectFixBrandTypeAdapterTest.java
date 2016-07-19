package com.ldedusoft.ldbm.adapters;

import android.content.Context;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.myAdapter.CommonAdapter;
import com.ldedusoft.ldbm.component.myAdapter.ViewHolder;
import com.ldedusoft.ldbm.model.Brand;

import java.util.List;

/**
 * 配件列表adapter
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectFixBrandTypeAdapterTest extends CommonAdapter {

    public SelectFixBrandTypeAdapterTest(Context context, int textViewResourceId, List objects) {
            super(context, textViewResourceId, objects);
        }
    public void convert(ViewHolder viewHolder, Object item)
    {
        viewHolder.setText(R.id.fixbrand_item_id,((Brand)item).getID());
        viewHolder.setText(R.id.fixbrand_item_pinpai, ((Brand) item).getPinPai());

    }
}


