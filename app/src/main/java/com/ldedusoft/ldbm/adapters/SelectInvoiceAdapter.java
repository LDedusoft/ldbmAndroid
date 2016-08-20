package com.ldedusoft.ldbm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.Invoice;

import java.util.List;

/**
 * 发票信息
 * Created by wangjianwei on 2016/6/29.
 */
public class SelectInvoiceAdapter extends ArrayAdapter<Invoice> {
    private int resourceId;
    private Invoice invoice;

    public SelectInvoiceAdapter(Context context, int textViewResourceId, List<Invoice> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        invoice = getItem(position);
        View view;
        ViewHolderInvoice viewHolder;
        if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolderInvoice();
            viewHolder.idText = (TextView) view.findViewById(R.id.invoice_item_id);
            viewHolder.faPiaoText = (TextView) view.findViewById(R.id.invoice_item_FaPiao);
            viewHolder.shuiLvText = (TextView) view.findViewById(R.id.invoice_item_ShuiLv);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder =(ViewHolderInvoice)view.getTag();
        }

        if(invoice!=null){
//            idText = (TextView)view.findViewById(R.id.invoice_item_id);
//            faPiaoText =  (TextView)view.findViewById(R.id.invoice_item_FaPiao);
//            shuiLvText =(TextView)view.findViewById(R.id.invoice_item_ShuiLv);
//            idText.setText(String.valueOf(invoice.getID()));
//            faPiaoText.setText(invoice.getFaPiao());
//            shuiLvText.setText(invoice.getShuiLv());
            viewHolder.idText.setText(String.valueOf(invoice.getID()));
            viewHolder.faPiaoText.setText(invoice.getFaPiao());
            viewHolder.shuiLvText.setText(invoice.getShuiLv());
        }
        if(position%2!=0){
            view.setBackgroundResource(R.color.lisetItemBackground);
        }
        return view;
    }

    class ViewHolderInvoice{
         TextView faPiaoText;
         TextView shuiLvText;
         TextView idText;
    }
}
