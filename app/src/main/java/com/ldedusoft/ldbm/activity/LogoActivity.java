package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ldedusoft.ldbm.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangjianwei on 2016/6/23.
 */
public class LogoActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_logo);
        final Intent intent = new Intent();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                intent.setClass(LogoActivity.this,LoginActivity.class);
                LogoActivity.this.startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,1000*3);

    }
}
