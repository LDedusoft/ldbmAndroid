package com.ldedusoft.ldbm.model;

/**
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
    /*是否是子标题*/
    private boolean isSubTitle = false;
    /*菜单值（用于向下传递）*/
    private String value;
    /*跳转页面路径*/
    private String intentPath;


    public String getIntentPath() {
        return intentPath;
    }

    public void setIntentPath(String intentPath) {
        this.intentPath = intentPath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public boolean isSubTitle() {
        return isSubTitle;
    }

    public void setIsSubTitle(boolean isSubTitle) {
        this.isSubTitle = isSubTitle;
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

    /*是否可以新建*/
    private boolean allowCreate;

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
