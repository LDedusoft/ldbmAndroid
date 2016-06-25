package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.os.Bundle;

import com.ldedusoft.ldbm.R;

/**
 * Created by wangjianwei on 2016/6/24.
 */
public class PartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_part);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
