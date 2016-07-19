package com.ldedusoft.ldbm.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangjianwei on 2016/7/16.
 */
public class MyApplication extends Application {
    private Context myContext;
    private static MyApplication myApplication;


    public static MyApplication getInstance(){
        if(myApplication==null){
            myApplication = new MyApplication();
        }
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setContext(Context context){
        this.myContext = context;
    }

    public  String getStr(int id){
        return myContext.getResources().getString(id);
    }

    public String[] getArray(int id){
        return myContext.getResources().getStringArray(id);
    }
}
