package com.ldedusoft.ldbm.adapters;

import android.content.Context;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.myAdapter.CommonAdapter;
import com.ldedusoft.ldbm.component.myAdapter.ViewHolder;
import com.ldedusoft.ldbm.model.FixingInfo;

import java.util.List;

/**
 * 配件信息
 * Created by wangjianwei on 2016/6/29.
 *  * {ID：id；BianHao：编号；MingCheng：名称；TuHao：图号；CangKu：仓库；KuCun：库存数量；
 * KuCunJinE：库存金额；KuCunJunJia：库存均价；DanWei：单位}
 */
public class SelectFixingAdapter extends CommonAdapter {
    public SelectFixingAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }
    public void convert(ViewHolder viewHolder, Object fixing)
    {
        viewHolder.setText(R.id.fixings_item_ID,((FixingInfo)fixing).getID());
        viewHolder.setText(R.id.fixings_item_BianHao,((FixingInfo)fixing).getBianHao());
        viewHolder.setText(R.id.fixings_item_MingCheng,((FixingInfo)fixing).getMingCheng());
        viewHolder.setText(R.id.fixings_item_TuHao,((FixingInfo)fixing).getTuHao());
        viewHolder.setText(R.id.fixings_item_CangKu,((FixingInfo)fixing).getCangKu());
        viewHolder.setText(R.id.fixings_item_KuCun,((FixingInfo)fixing).getKuCun());
        viewHolder.setText(R.id.fixings_item_KuCunJinE,((FixingInfo)fixing).getKuCunJinE());
        viewHolder.setText(R.id.fixings_item_KuCunJunJia,((FixingInfo)fixing).getKuCunJunJia());
        viewHolder.setText(R.id.fixings_item_DanWei,((FixingInfo)fixing).getDanWei());
    }
}
