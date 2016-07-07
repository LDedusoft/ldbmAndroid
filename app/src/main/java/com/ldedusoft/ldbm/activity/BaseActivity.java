package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public static void finishAll(){
        //TODO 结束所有活动
    }
}
