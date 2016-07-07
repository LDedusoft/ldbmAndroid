package com.ldedusoft.ldbm.model;

/**
 * 菜单项
 * Created by wangjianwei on 2016/6/25.
 */
public class MenuItem {
    /*菜单标题*/
    private String menuTitle;
    /*菜单描述*/
    private String menuDescribe;
    /*图标id*/
    private int iconId;
    /*是否是首页菜单，可以删除和置顶*/
    private boolean isHomeMenu = false;
    /*菜单值（用于向下传递）*/
    private String value;
    /*标题点击跳转路径*/
    private String titleIntentPath;
    /*添加按钮跳转页面路径*/
    private String createIntentPath;
    /*是否是分组栏*/
    private boolean isGroup = false;
    /*分组标题*/
    private String groupTitle = "";

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    /*是否可以新建*/
    private boolean allowCreate;

    public String getTitleIntentPath() {
        return titleIntentPath;
    }

    public void setTitleIntentPath(String titleIntentPath) {
        this.titleIntentPath = titleIntentPath;
    }

    public String getCreateIntentPath() {
        return createIntentPath;
    }

    public void setCreateIntentPath(String createIntentPath) {
        this.createIntentPath = createIntentPath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




    public boolean isHomeMenu() {
        return isHomeMenu;
    }

    public void setIsHomeMenu(boolean isHomeMenu) {
        this.isHomeMenu = isHomeMenu;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }



    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuDescribe() {
        return menuDescribe;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }


    public boolean isAllowCreate() {
        return allowCreate;
    }

    public void setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
    }


}
