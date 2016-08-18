package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.util.ScreenShot;
import com.ldedusoft.ldbm.view.SignatureView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangjianwei on 2016/8/12.
 */
public class ShouXieActivity extends BaseActivity{
    private Button cleanBtn,submitBtn;
    private SignatureView signatureView;
    private String danHao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_custom_shouxie);
        danHao = getIntent().getStringExtra("danHao");
        initView();
    }

    private void initView(){
        signatureView = (SignatureView)findViewById(R.id.signature_view);
        cleanBtn = (Button)findViewById(R.id.shouxie_clear);
        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });
        submitBtn = (Button)findViewById(R.id.shouxie_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanBtn.setVisibility(View.GONE);
                submitBtn.setVisibility(View.GONE);
                ScreenShot.createSDCardDir("LDBM");
                ScreenShot.savePic(ScreenShot.takeScreenShot(ShouXieActivity.this), "sdcard/LDBM/" + danHao + ".png");
                Toast.makeText(ShouXieActivity.this, "完成签名", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);
            }
        });
    }
}


