package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ldedusoft.ldbm.model.UserProperty;
import com.ldedusoft.ldbm.util.ActivityCollector;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getClass().getName().indexOf("WelcomeViewActivity")==-1&&this.getClass().getName().indexOf("LoginActivity")==-1){
           try {
               if(UserProperty.getInstance().getUserName()==null){
                   Log.d("BaseActivity","进程被销毁，重新登录");
                   Intent intent = new Intent("activity.LoginActivity");
                   startActivity(intent);
               }
           }catch (Exception e){
               Intent intent = new Intent("activity.LoginActivity");
               startActivity(intent);
           }
        }

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
