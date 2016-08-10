package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.customComp.FormToolBar;
import com.ldedusoft.ldbm.interfaces.FormToolBarListener;
import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.InitParamUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AllMenuListActivity extends Activity {
    private List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
    private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    private ExpandableListView exListView;
    private Context context = this;
    private MyAdapter adapter;
    private SharedPreferences pref; //保存文件
    private FormToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_allmenu_list);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        initData();
        setListener();
        exListView.requestFocus();
    }

    /**
     * 记录正在选中的子listview的item条目 用hashset是为了去除重复值
     */
    private HashSet<String> hashSet;

    private void setListener()
    {
        exListView.setOnGroupExpandListener(new OnGroupExpandListener()
        {

            @Override
            public void onGroupExpand(int groupPosition)
            {
                //存取已选定的集合
                hashSet = new HashSet<String>();
            }
        });
        // ExpandableListView的Group的点击事件
        exListView.setOnGroupClickListener(new OnGroupClickListener()
        {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id)
            {
                // 可以写点击后实现的功能

                return false;
            }
        });
        // ExpandableListView的child的点击事件

        exListView.setOnChildClickListener(new OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id)
            {
                Map<String, String> map = childData.get(groupPosition).get(
                        childPosition);
                String childChecked = map.get("isChecked");
                if ("No".equals(childChecked)||childChecked==null)
                {
                    map.put("isChecked", "Yes");
                    hashSet.add("选定" + childPosition);
                } else
                {
                    map.put("isChecked", "No");
                    if (hashSet.contains("选定" + childPosition))
                    {
                        hashSet.remove("选定" + childPosition);
                    }
                }
                System.out.println("选定的长度==1" + hashSet.size());
                System.out.println("选定的长度==2"
                        + childData.get(groupPosition).size());
                if (hashSet.size() == childData.get(groupPosition).size())
                {
                    parentList.get(groupPosition).put("isGroupCheckd", "Yes");
                } else
                {
                    parentList.get(groupPosition).put("isGroupCheckd", "No");
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    // 初始化数据
    private void initData()
    {
//        String[] sysItemsArray = getResources().getStringArray(R.array.home_menu_item);//从配置中获取默认的菜单项
        String userItemsStr = pref.getString(UserProperty.getInstance().getUserName(), "");//获取用户配置
        String[]   userItemsArray = userItemsStr.split(","); //用户配置文件中保存的菜单项

       parentList = InitParamUtil.getGroupMenuList();
        childData = InitParamUtil.getChildMenuList(userItemsArray);
        adapter = new MyAdapter();
        exListView.setAdapter(adapter);
        exListView.expandGroup(0);
        hashSet = new HashSet<String>();
    }

    private void initView()
    {
        toolBar = (FormToolBar)findViewById(R.id.allmenu_top_bar);
        exListView = (ExpandableListView) findViewById(R.id.exlistview);
        toolBar.showSaveBtn();
        toolBar.setFormToolBarListener(new FormToolBarListener() {
            @Override
            public void OnSaveClick() {
                String selectedMenu = getSelectedMenu();
                Intent intent = new Intent("activity.HomeActivity");
                intent.putExtra("result",selectedMenu);
                setResult(RESULT_OK,intent);
                finish();

            }

            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }

    public String getSelectedMenu(){
        String selectedMenu="";
        for (int i = 0; i < childData.size(); i++) {
            List<Map<String, String>> groupList = childData.get(i);
            for (int j = 0; j < groupList.size(); j++) {
                Map<String, String> map = groupList.get(j);
                String menuName = map.get("childItem");
                String isCheck = map.get("isChecked");
                if("Yes".equals(isCheck)){
                    selectedMenu+=","+menuName;
                }
            }
        }
        if (selectedMenu.length()>0){
            selectedMenu = selectedMenu.substring(1,selectedMenu.length());
        }
//        Toast.makeText(this,selectedMenu,Toast.LENGTH_SHORT).show();
        return selectedMenu;
    }

    /**
     * 适配adapter
     */

    private class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            // TODO Auto-generated method stub
            return childData.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_allmenu_child,
                        null);
                holder.childText = (TextView) convertView
                        .findViewById(R.id.id_text);
                holder.childBox = (CheckBox) convertView
                        .findViewById(R.id.id_checkbox);
                holder.childBox.clearFocus();
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.childText.setText(childData.get(groupPosition)
                    .get(childPosition).get("childItem"));
            String isChecked = childData.get(groupPosition).get(childPosition)
                    .get("isChecked");
            if ("Yes".equals(isChecked))
            {
                holder.childBox.setChecked(true);
            } else
            {
                holder.childBox.setChecked(false);

            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            // TODO Auto-generated method stub
            return childData.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return parentList.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            // TODO Auto-generated method stub
            return parentList.size();
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition,
                                 final boolean isExpanded, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_allmenu_group, null);
                holder.groupText = (TextView) convertView
                        .findViewById(R.id.id_group_text);
                holder.groupBox = (CheckBox) convertView
                        .findViewById(R.id.id_group_checkbox);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.groupText.setText(parentList.get(groupPosition).get(
                    "groupText"));
            final String isGroupCheckd = parentList.get(groupPosition).get(
                    "isGroupCheckd");

            if ("No".equals(isGroupCheckd))
            {
                holder.groupBox.setChecked(false);
            } else
            {
                holder.groupBox.setChecked(true);
            }

			/*
			 * groupListView的点击事件
			 */
            holder.groupBox.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    CheckBox groupBox = (CheckBox) v
                            .findViewById(R.id.id_group_checkbox);
                    if (!isExpanded)
                    {
                        //展开某个group view
                        exListView.expandGroup(groupPosition);
                    } else
                    {
                        //关闭某个group view
                        exListView.collapseGroup(groupPosition);
                    }

                    if ("No".equals(isGroupCheckd))
                    {
                        exListView.expandGroup(groupPosition);
                        groupBox.setChecked(true);
                        parentList.get(groupPosition).put("isGroupCheckd",
                                "Yes");
                        List<Map<String, String>> list = childData
                                .get(groupPosition);
                        for (Map<String, String> map : list)
                        {
                            map.put("isChecked", "Yes");
                        }
                    } else
                    {
                        groupBox.setChecked(false);
                        parentList.get(groupPosition)
                                .put("isGroupCheckd", "No");
                        List<Map<String, String>> list = childData
                                .get(groupPosition);
                        for (Map<String, String> map : list)
                        {
                            map.put("isChecked", "No");
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

    }

    private class ViewHolder {
        TextView groupText, childText;
        CheckBox groupBox, childBox;
    }
}
