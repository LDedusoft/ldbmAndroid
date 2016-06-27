package com.ldedusoft.ldbm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ldedusoft.ldbm.R;

public class WelcomeActivity extends Activity implements OnGestureListener {
	//实现拖动类效果属性声明
	private ViewFlipper flipper;
	private GestureDetector detector;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldbm_welcome);
		//注册一个用于手势识别的类
		detector = new GestureDetector(this);
		flipper = (ViewFlipper)this.findViewById(R.id.Viewflipper);

		flipper.addView(addView(R.layout.welcome_1));
		flipper.addView(addView(R.layout.welcome_2));
		flipper.addView(addView(R.layout.welcome_3));

		Button welcomeBtn = (Button)flipper.findViewById(R.id.welcome_btn);
		welcomeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	//添加视图
	private View addView(int layout){
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, null);
		return view;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event);
	}

	//用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
	//	Toast.makeText(this, "onDown",Toast.LENGTH_SHORT).show();
		return false;
	}

	//用户按下触摸屏，快速移动后松开，由1个MotionEvent ACTION_DOWN,多个ACTION_MOVE,1个ACTION_UP触发
	//参数解释
	//e1:第1个ACTION_DOWN MotionEvent
	//e2:最后一个ACTION_MOVE MotionEvent
	//velocityX:X轴上的移动速度，像素/秒
	//velocityY:Y轴上的移动速度，像素/秒
	//触发条件：X轴的坐标位移大于FLING_MIN_DISTANCE,且移动速度大于FLING_MIN_VELOCITY个像素/秒
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		// TODO Auto-generated method stub
		Toast.makeText(this,String.valueOf(this.flipper.getDisplayedChild()),Toast.LENGTH_SHORT).show();
		if(e1.getX()-e2.getX()>120){
			//Fling left
			//Toast.makeText(this, "Fling Left",Toast.LENGTH_SHORT).show();
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
			this.flipper.showNext();
			return true;
		}else if(e1.getX() - e2.getX() < -120){
			//Fling right
			//Toast.makeText(this, "Fling Right",Toast.LENGTH_SHORT).show();
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
			this.flipper.showPrevious();
			return true;
		}
		return false;
	}

	//用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	//用户按下触摸屏，并拖动，由1个Motion ACTION_DOWN,多个ACTION_MOVE触发
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	//用户轻触触摸屏，尚未松开或拖动，由一个MotionEvent ACTION_DOWN触发
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	//用户（轻触触摸屏后）松开，由一个MotionEvent ACTION_UP触发
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}