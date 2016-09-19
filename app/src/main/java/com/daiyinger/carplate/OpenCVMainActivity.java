package com.daiyinger.carplate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ldedusoft.ldbm.R;
import com.ldedusoft.ldbm.activity.ShowBigPictureActivity;
import com.ldedusoft.ldbm.activity.ShowPictureActivity;
import com.ldedusoft.ldbm.model.SysProperty;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpenCVMainActivity extends Activity {
	private String carCode="";
	private Uri imageUri;
	private String imgName = "pic_car_code.png";
	private int window_width, window_height;// 控件宽度
	private int state_height;// 状态栏的高度
	private DragImageView  mZoomView = null;
	private ViewTreeObserver viewTreeObserver;
	private Bitmap bmp = null;
	private Button btnTrain = null;
	private Button btnPic = null;
	private EditText m_text = null;
	private String path = null; //SDCARD 根目录
	private Button btnOk;
	String imgpath = null;
	boolean selected_img_flag = false;
	@SuppressWarnings({ "deprecation" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opencv_main);
		m_text = (EditText) findViewById(R.id.myshow);
		btnTrain = (Button) findViewById(R.id.btn_plate);
		btnPic = (Button) findViewById(R.id.btn_pick);
		mZoomView = (DragImageView)findViewById(R.id.imageview);
		btnOk = (Button) findViewById(R.id.btn_ok);

		newPhoto(); //进入页面立即打开相机

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					carCode= m_text.getText().toString();
					if (carCode.indexOf("识别失败")!=-1){
						carCode = "";
					}
					SysProperty.carCode = carCode;
					Intent ii = new Intent();
					ii.putExtra("carCode", carCode);
					setResult(RESULT_OK, ii);
					finish();
			}
		});

		btnTrain.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(selected_img_flag == false)
				{
					File file=new File(imgpath);
					if(!file.exists())
					{
						SendMsgText("未选择图片 且默认路径 "+imgpath+"图片不存在!", 2);
						return;
					}
					bmp = getLoacalBitmap(imgpath);
					mZoomView.setImageBitmap(bmp);
					selected_img_flag = true;

				}
				new MyTask().execute();
			}});
		btnPic.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// 从相册选择照片
//				Intent i = new Intent(
//						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//				startActivityForResult(i, 1);

				newPhoto();
			}});
		//将汽车完整图像加载程序中并进行显示
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ai);
		mZoomView.setImageBitmap(bmp);
		/** 获取可見区域高度 **/
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
		mZoomView.setmActivity(this);//注入Activity.
		/** 测量状态栏高度 **/
		viewTreeObserver = mZoomView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							// 获取状况栏高度
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							mZoomView.setScreen_H(window_height);
							mZoomView.setScreen_W(window_width);
						}

					}
				});
		path = Environment.getExternalStorageDirectory().getAbsolutePath();//获取跟目录
		imgpath = path+"/ai/plate_locate.jpg";
		System.out.println(path);
		InitEnv();
	}

	private void newPhoto(){

		File outputImage = new File(Environment.getExternalStorageDirectory()+"/LDBM/",imgName);
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
		startActivityForResult(intent, 3);
	}

	//查询xml资源是否存在 如果不存在则从assets进行拷贝
	@SuppressLint("ShowToast")
	void InitEnv()
	{
		try {
			String lastVersion = null;
			SharedPreferences sharedPreferences;
			sharedPreferences = getSharedPreferences("info",Activity.MODE_PRIVATE);
			if(sharedPreferences.contains("version") == true)
			{
				lastVersion = sharedPreferences.getString("version","0.0");
			}
			String curVersion = PlaneUtil.getVersion(getApplicationContext());
			if(!curVersion.equals(lastVersion))
			{
				String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File dir = new File(sdpath + "/ai");
				if(!dir.isDirectory())
				{
					dir.mkdir();
				}
				PlaneUtil.copyBigDataToSD(getApplicationContext(),
						"ann.xml",sdpath + "/ai/ann.xml");
				PlaneUtil.copyBigDataToSD(getApplicationContext(),
						"svm.xml",sdpath + "/ai/svm.xml");
				PlaneUtil.copyBigDataToSD(getApplicationContext(),
						"plate_locate.jpg",sdpath + "/ai/plate_locate.jpg");
				dir = new File(sdpath + "/ai/etc/");
				if(!dir.isDirectory())
				{
					dir.mkdir();
				}
				PlaneUtil.copyBigDataToSD(getApplicationContext(),
						"province_mapping",sdpath + "/ai/etc/province_mapping");

				SharedPreferences.Editor editor = sharedPreferences.edit();
				//用putString的方法保存数据
				editor.putString("version",curVersion);
				editor.commit();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 加载本地图片
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	//OpenCV类库加载并初始化成功后的回调函数，在此我们不进行任何操作
	private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:{
					System.loadLibrary("imageproc");
				} break;
				default:{
					super.onManagerConnected(status);
				} break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK ) { //相册选择
			if(requestCode == 1)
			{
				Uri uri = data.getData();
				ContentResolver cr = this.getContentResolver();
				try {
				//	bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
					bmp = ShowBigPictureActivity.getimage(getImagePath(uri,null));//压缩
					//bmp= BitmapFactory.decodeFile(getImagePath(uri,null));//原图
					mZoomView.setImageBitmap(bmp);
					imgpath = getRealFilePath(getApplicationContext(), uri);
				} catch (Exception e) {
					//Log.e("Exception", e.getMessage(),e);
				}
			}
			else if(requestCode == 2)
			{

			}else if(requestCode == 3){//拍照返回
				try{
				//	Uri uri = data.getData();

					String filePath = Environment.getExternalStorageDirectory()+"/LDBM/"+imgName;
					String newFilePath = Environment.getExternalStorageDirectory()+"/LDBM/temp.png";
					Bitmap bitmap = ShowBigPictureActivity.getimageForCarCode(filePath);
					saveFile(bitmap,newFilePath);
					m_text.setText("");
					carCode = "";
					mZoomView.setImageBitmap(bitmap);
					imgpath = newFilePath;
					selected_img_flag = true;

					//自动开始识别
					new MyTask().execute();
					//	imageArray[index].setImageBitmap(bitmap);
					//	picNameArr[index] = filePath;
					//	index++;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}



	public void saveFile(Bitmap mBitmap,String fileName){
		File f = new File(fileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("在保存图片时出错："+e.toString());
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getImagePath(Uri uri,String selection){
		String path = null;
		Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
		if(cursor!=null){
			if(cursor.moveToFirst()) {
				path = cursor.getString(
						cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
				);
			}
			cursor.close();
			}
			return  path;
		}


	/**
	 * Try to return the absolute file path from the given Uri
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	private class MyTask extends AsyncTask<String, Integer, String> {
		//onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
		}

		//doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected String doInBackground(String... params) {
			try {
				String resultImgDirPath = path +"/ai/tmp/";
				String logpath = path+"/ai/ai_log.log";
				String svmpath = path+"/ai/svm.xml";
				String annpath = path+"/ai/ann.xml";
				String imagepath =  new String(imgpath.getBytes(),"gbk");
				System.out.println("entering the jni");
				SendMsgText("正在识别.....", 1);
				Thread.sleep(100);
				String result = null;
				byte[] resultByte = CarPlateDetection.ImageProc(path, logpath, imagepath, svmpath, annpath);
//			    System.out.println(result);
				if(resultByte != null)
				{
					bmp = BitmapFactory.decodeFile(resultImgDirPath+"result.jpg");
					SendMsgRefresh(3);
					result = new String(resultByte,"UTF-8");
					SendMsgText(result,1);
					SendMsgText(result,2);
				}
				else
				{
					SendMsgText("识别失败!",1);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				SendMsgText("entering the detect error"+e.getMessage(),2);
			}
			return null;
		}
	}
	public Handler mHandler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 1:
					carCode = (String)msg.obj;
					carCode = carCode.substring(carCode.indexOf(":")+1);
					m_text.setText(carCode);
					break;
				case 2:

					carCode = (String)msg.obj;
					carCode = carCode.substring(carCode.indexOf(":")+1);
					//Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
					break;
				case 3:
					mZoomView.setImageBitmap(bmp);
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

	public void SendMsgText(String str, int id)
	{
		Message message=new Message();
		message.what=id;
		message.obj = str;
		mHandler.sendMessage(message);
	}

	public void SendMsgRefresh(int id)
	{
		Message message=new Message();
		message.what=id;
		message.obj = null;
		mHandler.sendMessage(message);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//通过OpenCV引擎服务加载并初始化OpenCV类库，所谓OpenCV引擎服务即是
		//OpenCV_2.4.3.2_Manager_2.4_*.apk程序包，存在于OpenCV安装包的apk目录中
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);

//		if (!OpenCVLoader.initDebug()) {
//			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
//		} else {
//			mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		File root;
		Uri uri;
		Intent intent;
		switch (item.getItemId()) {

			case R.id.action_settings:
				//intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//startActivityForResult(intent, 2);
				break;
			case R.id.action_view_image:
				root = new File(Environment.getExternalStorageDirectory().getPath()
						+ "/ai/tmp/result.jpg");
				uri = Uri.fromFile(root);
				intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(uri , "image/*");
				startActivity(intent);
				break;
			case R.id.action_about:
				Toast.makeText(getApplicationContext(),"daiyinger", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}