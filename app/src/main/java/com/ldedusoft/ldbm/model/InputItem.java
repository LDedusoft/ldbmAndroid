package com.ldedusoft.ldbm.model;

/**
 * Created by wangjianwei on 2016/6/28.
 */
public class InputItem {

    /*id标示*/
    private String itemId;
    /*标题，左侧显示*/
    private String itemTitle;
    /*输入类型 0,文本不可修改 1,输入框 2,文本点击跳转 3,日期 4,单选 5,复选 6输入框限定数字*/
    private int inputType;
    /*默认值*/
    private String defaultValue;
    /*实际值*/
    private String value;
    /*提示*/
    private String hint;
    /*跳转路径*/
    private String intentPath;
    /*跳转唯一值（一组数据中该值需唯一）*/
    private int intentRequestCode;

    public int getIntentRequestCode() {
        return intentRequestCode;
    }

    public void setIntentRequestCode(int intentRequestCode) {
        this.intentRequestCode = intentRequestCode;
    }

    public String getIntentPath() {
        return intentPath;
    }

    public void setIntentPath(String intentPath) {
        this.intentPath = intentPath;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
