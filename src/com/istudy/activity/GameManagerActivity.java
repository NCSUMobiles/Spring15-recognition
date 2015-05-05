package com.istudy.activity;


import com.example.istudy.R;
import com.istudy.bean.GameImageBean;
import com.istudy.dataset.DataSet;
import com.istudy.fragment.CountdownFragment;
import com.istudy.fragment.ImageFragment;
import com.istudy.fragment.ProgressBarFragment;
import com.istudy.helper.ActivityHelper;
import com.istudy.helper.Utils;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameManagerActivity extends FragmentActivity implements ImageFragment.OnItemClickListener{

	private static final int RESULT_INCOMPLETE = 3;
	
	private int i_time_left;
	private int i_total_score;
	private int i_score;
	private int i_score_diff = 1;
	private int location = 0;
	private int max;
	private static int counter = 0;
	private int step;	
	private boolean option_clicked;
	private LinearLayout parentLayout;
	private ImageView rightOrWrong;
	private ImageView iv;
	SharedPreferences prefs;
	

	
	//private TimerTask mTimerTask;
	final Handler handler = new Handler();
	final Handler delayHandler = new Handler();
	
	private Runnable runnable; 
	//Timer t = new Timer();
	ProgressBarFragment pfag;
	ImageFragment ifag;
	CountdownFragment countdown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityHelper.initialize(GameManagerActivity.this);
		setContentView(R.layout.activity_game_manager);
		
		Intent intent = getIntent();
		location = intent.getIntExtra("location", -1);
		step = intent.getIntExtra("step", 0);
		i_total_score = intent.getIntExtra("total_score", 0);
		max = Utils.masterList.get(location).size();				
		prefs = getSharedPreferences("MyPrefs", Context.MODE_MULTI_PROCESS);

		if(step>=max){
			Log.d("GameManagerActivity", "step: "+step+" max: "+max);
			result_ok();
		}else{
		
		
			// Check that the activity is using the layout version with
	        // the fragment_container FrameLayout
			if (findViewById(R.id.fragment1) != null) {
				
	            // However, if we're being restored from a previous state,
	            // then we don't need to do anything and should return or else
	            // we could end up with overlapping fragments.
	            if (savedInstanceState != null) {
	                return;
	            }
	            GameImageBean bean = new GameImageBean(Utils.masterList.get(location).get(step));
	            // Create a new Fragment to be placed in the activity layout
	            pfag = new ProgressBarFragment();
	            ifag = ImageFragment.newInstance(bean);
	            countdown = new CountdownFragment();
	    		 
	            // Add the fragment to the 'fragment_container' FrameLayout
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.fragment1, pfag).commit();
	            
	            getSupportFragmentManager().beginTransaction()
	            .add(R.id.fragment1, countdown).commit();
	            
	            getSupportFragmentManager().beginTransaction()
	            .add(R.id.fragment2, ifag).commit();
	        }
		}
				
	}

	
	
	@Override
	protected void onResume(){
		super.onResume();
		gameManager(counter,false);

	}
	
	public void initialize(){
		//t = new Timer();
		i_time_left = 11;
		i_score = 11;
		option_clicked = false;
		//counter++;
	}
	
	public void gameManager(int step, boolean flag){
		//if(counter < max){
			initialize();
			if(flag)
				fragmentControl(step);
			startGame();
		//}else{											
		//	result_ok();
		//}
	}
	
	public void fragmentControl(int step){

		ifag = ImageFragment.newInstance(Utils.masterList.get(location).get(step));
		getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment2, ifag).commit();
		
		pfag = new ProgressBarFragment();
		getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment1, pfag).commit();
		
		countdown = new CountdownFragment();
		getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment1, countdown).commit();
	}
	
	public void updateView(){
		i_time_left -= 1;
		if(i_time_left <= 0){
			Log.d("GameManagerActivity ","Time Left : "+i_time_left+" Runnable: "+runnable);
			handler.removeCallbacks(runnable);
			pfag.updateTimeLeft();
			countdown.updateTimeLeft();
			timeout_false();
		}else /*if(i_time_left%2 == 0)*/{
			i_score -= i_score_diff;
			Log.d("ImageFragment","Time Left: "+i_time_left);
			pfag.updateTimeLeft();
			countdown.updateTimeLeft();
			ifag.updateView();			
		}
	}
	
	public void startGame(){
		
		runnable = new Runnable() {
			
			@Override
			public void run() {
				updateView();
				handler.postDelayed(this, 1000);
			}
		};
				
		runnable.run();						
		
	}
	

	@Override
	public void onOptionItemClicked(final View v, final String id, final String correctOption, final View correctView) {
		if (option_clicked == false) {
			handler.removeCallbacks(runnable);
			option_clicked = true;
			v.setBackgroundResource(R.drawable.option_selected);
			iv = (ImageView)v;
			delayHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if(id.compareTo(correctOption) == 0){
						i_total_score += i_score;
						v.setBackgroundColor(Color.parseColor("#28ce00"));
						iv.setImageResource (R.drawable.check);
						//check if sounds is on/off 
						if(prefs.getBoolean("sounds", true))
						{
						final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
						mp.start();
						}
					}else{
						v.setBackgroundResource(R.drawable.option_wrong);
						iv.setImageResource (R.drawable.wrong);
						if(prefs.getBoolean("sounds", true))
						{
						final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
						mp.start();
						}
					}
					timeout_false();
				}
			}, 1000);
		}
	}
	
	private void timeout_false(){
		ifag.getCorrectView().setBackgroundResource(R.drawable.option_correct);
		String aName = ifag.getAlbumName();
		TextView tView = (TextView)findViewById(R.id.pictureFact);
		int factResourceId = getResources().getIdentifier(aName + "_fact", "string", getPackageName());
		if(factResourceId != 0){
			String content = getResources().getString(factResourceId);
			tView.setText("\""+content+"\"");
		}
		
		delayHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {			
				//gameManager(counter,true);
				Intent data = new Intent();
				data.putExtra("step", ++step);
				data.putExtra("total_score", i_total_score);
				data.putExtra("location", location);
				setResult(RESULT_INCOMPLETE, data);
				finish();
			}
		}, 3500);//time delay after an answer choice is selected
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		handler.removeCallbacks(runnable);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();		
	}
	
	private void result_ok() {
		Intent intent = new Intent();
		intent.putExtra("just_location", location);
		intent.putExtra("total_score", i_total_score);
		setResult(RESULT_OK,intent);
		finish();
	}

}
