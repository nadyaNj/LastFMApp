package com.mlab.trainings.lastfmapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout.LayoutParams;

import com.mlab.trainings.lastfmapp.R;

public class SplashActivity extends Activity
{
	private View logo;
	private View text;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		logo = findViewById(R.id.logo);
		text = findViewById(R.id.text);
		
		text.setVisibility(View.INVISIBLE);
		
		LayoutParams params = (LayoutParams) text.getLayoutParams();
		params.setMargins(100, 100, 0, 0);
		text.requestLayout();
		
		Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
		logo.startAnimation(scaleAnimation);
		
		
		Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_anim);
		textAnim.setAnimationListener(new AnimationListener()
		{
			
			@Override
			public void onAnimationStart(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation)
			{
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				finish();
				
			}
		});
		text.startAnimation(textAnim);
		
//		new Thread(new Runnable()
//		{
//			
//			@Override
//			public void run()
//			{
//				try
//				{
//					Thread.sleep(3000);
//					startActivity(new Intent(SplashActivity.this, MainActivity.class));
//					finish();
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				
//				
//			}
//		}).start();
	}
}
