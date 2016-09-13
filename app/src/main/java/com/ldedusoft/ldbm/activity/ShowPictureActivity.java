package com.ldedusoft.ldbm.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.component.customComp.QueryToolBar;
import com.ldedusoft.ldbm.interfaces.QueryToolBarListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by wangjianwei on 2016/8/13.
 */
public class ShowPictureActivity extends BaseActivity {
    private Uri imageUri;
    private int index = 0;
    private String danHao;
    private boolean hasPic;
    private QueryToolBar topBar;
    private Button addBtn,backBtn,saveBtn;
    private ImageView image1,image2,image3,image4;//image5,image6,image7,image8,image9;
    private ImageView[] imageArray = {image1,image2,image3,image4};//image5,image6,image7,image8,image9};
    private int[] imgIdArray = {R.id.picture_1,R.id.picture_2,R.id.picture_3,R.id.picture_4};//R.id.picture_5,R.id.picture_6,R.id.picture_7,R.id.picture_8,R.id.picture_9};
    String[] picNameArr = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldbm_show_picture);
        danHao = getIntent().getStringExtra("danHao");
        hasPic = getIntent().getBooleanExtra("hasPic",false);
        initView();
        initAction();
    }

    private void initAction(){
        if(index==0&&hasPic==false){
            newPhoto(); //首次进入直接打开相机
        }else{
            //// TODO: 2016/8/15 获取图片
           String picName = searchFile(danHao+"_pic_");
           String[] tempName = picName.split(",");
            for(int i=0;i<tempName.length;i++){
                if(i>3)break;
                picNameArr[i] = tempName[i];
                String filePath = tempName[i];
                Bitmap bitmap = getimage(filePath);
                if(bitmap!=null) {
                    imageArray[i].setImageBitmap(bitmap);
                    index++;
                }
            }
        }
    }

    private void initView(){
        topBar = (QueryToolBar)findViewById(R.id.show_pic_topbar);
        topBar.setQueryToolBarListener(new QueryToolBarListener() {
            @Override
            public void OnAddClick() {}
            @Override
            public void OnModifyClick() {}
            @Override
            public void OnBackClick() {
                finish();
            }
        });
        topBar.setTitle("查看照片");
        addBtn = (Button)findViewById(R.id.show_pic_add);
        backBtn =(Button)findViewById(R.id.show_pic_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveBtn =(Button)findViewById(R.id.show_pic_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        for(int i=0;i<imageArray.length;i++){
            imageArray[i]=(ImageView)findViewById(imgIdArray[i]);
            imageArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPicture(v.getId());//打开图片
                }
            });
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhoto();
            }
        });
    }

    private void openPicture(int id){
            int index = 0;
            switch (id) {
                case R.id.picture_1:
                    index = 0;
                    break;
                case R.id.picture_2:
                    index = 1;
                    break;
                case R.id.picture_3:
                    index = 2;
                    break;
                case R.id.picture_4:
                    index = 3;
                    break;
                default:
                    break;
            }
        if(index>=this.index){
            return;
        }else {
            Intent intent = new Intent("activity.ShowBigPictureActivity");
            intent.putExtra("fileName", picNameArr[index]);
            startActivity(intent);
        }
    }

    private void newPhoto(){
        if(index>3){
            Toast.makeText(this,"最多添加4张照片",Toast.LENGTH_SHORT).show();
            return;
        }
       File outputImage = new File(Environment.getExternalStorageDirectory()+"/LDBM/",danHao+"_pic_"+index+".png");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }

    protected void  onActivityResult(int requestCode,int resultCode,Intent data){
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            String filePath = Environment.getExternalStorageDirectory()+"/LDBM/"+danHao+"_pic_"+index+".png";
            Bitmap bitmap = getimage(filePath);
            imageArray[index].setImageBitmap(bitmap);
            picNameArr[index] = filePath;
            index++;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 200f;//这里设置高度为800f
        float ww = 100f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        System.out.println("缩放比例"+be);
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if(bitmap==null){
            return null;
        }
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        System.out.println("图片大小:"+baos.toByteArray().length);
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        System.out.println("图片压缩比例:"+options+" 压缩后大小:"+baos.toByteArray().length);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        bitmap = rotaingImageView(90, bitmap);//旋转角度,有些手机需要旋转
        return bitmap;
    }

    /*
    * 旋转图片
    * @param angle
    * @param bitmap
    * @return Bitmap
    */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 获取文件
     * @param fName
     * @return
     */
    public String searchFile(String fName) {
        String filePath = Environment.getExternalStorageDirectory()+"/LDBM";
        String result = "";
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            if (file.getName().indexOf(fName) >= 0) {
                result += ","+file.getPath();
            }
        }
        if(result.length()>0){
            result = result.substring(1,result.length());
        }
        return result;
    }

}
