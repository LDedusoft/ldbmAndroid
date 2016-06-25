package com.ldedusoft.ldbm.component.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.model.MenuItem;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/25.
 */
public class MenuListAdapter extends ArrayAdapter<MenuItem> {
    private int resourceId;

    public MenuListAdapter(Context context,int textViewResourceId, List<MenuItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuItem menuItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        if(menuItem!=null) {
            ImageView menuItemIcon = (ImageView) view.findViewById(R.id.menu_item_icon);
            TextView menuItemTitle = (TextView) view.findViewById(R.id.menu_item_title);
            TextView menuItemDescribe = (TextView) view.findViewById(R.id.menu_item_describe);
            LinearLayout menuItemCreateLayout = (LinearLayout) view.findViewById(R.id.menu_item_createLayout);
            Button menuItemCreateButton = (Button) view.findViewById(R.id.menu_item_createButton);
            menuItemIcon.setImageResource(menuItem.getIconId());
            menuItemTitle.setText(menuItem.getMenuTitle());
            menuItemDescribe.setText(menuItem.getMenuDescribe());
            if (menuItem.isAllowCreate()) {
                menuItemCreateLayout.setVisibility(View.VISIBLE);
            } else {
                menuItemCreateLayout.setVisibility(View.GONE);
            }
            menuItemCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MenuListAdapter", menuItem.getMenuTitle());
                }
            });
        }else {
            LinearLayout menuItemBody = (LinearLayout)view.findViewById(R.id.menu_item_body);
            menuItemBody.setVisibility(View.GONE);
            LinearLayout menuItemFoot = (LinearLayout)view.findViewById(R.id.menu_item_foot);
            menuItemFoot.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
