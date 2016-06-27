package com.ldedusoft.ldbm.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.widge.sideslip.OnDeleteListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuAddClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuShortcutClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnMenuTitleClickListioner;
import com.ldedusoft.ldbm.component.widge.sideslip.OnSettopListioner;
import com.ldedusoft.ldbm.model.MenuItem;

import java.util.List;

/**
 * Created by wangjianwei on 2016/6/25.
 */
public class MenuListAdapter extends ArrayAdapter<MenuItem> {
    private int resourceId;
    private OnDeleteListioner mOnDeleteListioner;
    private OnSettopListioner mOnSettopListioner;
    private OnMenuTitleClickListioner mOnMenuTitleClickListioner;
    private OnMenuAddClickListioner mOnMenuAddClickListioner;
    private OnMenuShortcutClickListioner mOnMenuShortcutClickListioner;


    public MenuListAdapter(Context context,int textViewResourceId, List<MenuItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MenuItem menuItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        if(menuItem!=null) {
            ImageView menuItemIcon = (ImageView) view.findViewById(R.id.menu_item_icon);
            TextView menuItemTitle = (TextView) view.findViewById(R.id.menu_item_title);
            TextView menuItemDescribe = (TextView) view.findViewById(R.id.menu_item_describe);
            LinearLayout menuItemCreateLayout = (LinearLayout) view.findViewById(R.id.menu_item_createLayout);
            Button menuItemCreateButton = (Button) view.findViewById(R.id.menu_item_createButton);
            LinearLayout menu_item_title_layout = (LinearLayout)view.findViewById(R.id.menu_item_title_layout);

            //标题区域点击监听
            menu_item_title_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnMenuTitleClickListioner != null){
                        mOnMenuTitleClickListioner.OnMenuTitleClick(position);
                    }
                }
            });

            //新建按钮点击监听
            menuItemCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnMenuAddClickListioner!=null){
                        mOnMenuAddClickListioner.OnMenuAddClick(position);
                    }
                }
            });

            TextView deleteAction = (TextView)view.findViewById(R.id.delete_action);
            TextView settopAction = (TextView)view.findViewById(R.id.top_action);

            deleteAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnDeleteListioner != null)
                        mOnDeleteListioner.onDelete(position);
                }
            });

            settopAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSettopListioner != null) {
                        mOnSettopListioner.onSettop(position);
                    }
                }
            });


            menuItemIcon.setImageResource(menuItem.getIconId());
            menuItemTitle.setText(menuItem.getMenuTitle());
            menuItemDescribe.setText(menuItem.getMenuDescribe());
            if (menuItem.isAllowCreate()) {
                menuItemCreateLayout.setVisibility(View.VISIBLE);
            } else {
                menuItemCreateLayout.setVisibility(View.GONE);
            }

        }else {
            LinearLayout menuItemBody = (LinearLayout)view.findViewById(R.id.menu_item_body);
            menuItemBody.setVisibility(View.GONE);
            //显示添加快捷功能按钮
            LinearLayout menuItemFoot = (LinearLayout)view.findViewById(R.id.menu_item_foot);
            menuItemFoot.setVisibility(View.VISIBLE);
            TextView deleteAction = (TextView)view.findViewById(R.id.delete_action);
            deleteAction.setVisibility(View.GONE);
            TextView settopAction = (TextView)view.findViewById(R.id.top_action);
            settopAction.setVisibility(View.GONE);

            menuItemFoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnMenuShortcutClickListioner!=null){
                        mOnMenuShortcutClickListioner.OnMenuShortcutClick(position);
                    }
                }
            });
        }
        return view;
    }
    public void setOnDeleteListioner(OnDeleteListioner mOnDeleteListioner) {
        this.mOnDeleteListioner = mOnDeleteListioner;
    }

    public void setmOnSettopListioner(OnSettopListioner mOnSettopListioner){
        this.mOnSettopListioner = mOnSettopListioner;
    }

    public void setOnMenuTitleClickListioner(OnMenuTitleClickListioner mOnMenuTitleClickListioner){
        this.mOnMenuTitleClickListioner = mOnMenuTitleClickListioner;
    }

    public void setOnMenuAddClickListioner(OnMenuAddClickListioner mOnMenuAddClickListioner){
        this.mOnMenuAddClickListioner = mOnMenuAddClickListioner;
    }

    public void setOnMenuShortcutClickListioner(OnMenuShortcutClickListioner mOnMenuShortcutClickListioner){
        this.mOnMenuShortcutClickListioner = mOnMenuShortcutClickListioner;
    }


}
