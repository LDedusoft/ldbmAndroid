package com.ldedusoft.ldbm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;

import java.io.File;

/**
 * Created by wangjianwei on 2016/8/13.
 */
public class showSignature extends BaseActivity{
    private ImageView imageView;
    private Button btn;
    private String danHao;
    private Button back;
    private QueryToolBar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_show_signature);
        initView();
        getImage();
    }

    private void initView(){
        imageView = (ImageView)findViewById(R.id.show_sig_img);
        btn = (Button)findViewById(R.id.show_sig_btn);
        back = (Button)findViewById(R.id.show_sig_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        danHao = getIntent().getStringExtra("danHao");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intentPath = "activity.ShouXieActivity";
                Intent intent = new Intent(intentPath);
                intent.putExtra("danHao", danHao);
                startActivity(intent);
                finish();
            }
        });

        toolBar = (QueryToolBar)findViewById(R.id.show_sig_topBar);
        toolBar.setTitle("查看签名");
        toolBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {
            }
            @Override
            public void OnModifyClick() {
            }
            @Override
            public void OnBackClick() {
                finish();
            }
        });
    }

    private void getImage(){
        File outputImage = new File(Environment.getExternalStorageDirectory()+"/LDBM/",danHao + ".png");
        Uri imageUri = Uri.fromFile(outputImage);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
