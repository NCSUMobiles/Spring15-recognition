package com.istudy.activity;

import java.util.Random;
import com.example.istudy.R;
import com.istudy.bean.Albums;
import com.istudy.dao.GamePlayDataSource;
import com.istudy.dataset.DataSet;
import com.istudy.helper.ActivityHelper;
import com.istudy.helper.Utils;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private static final int GAME_REQUEST_CODE = 101;
	private static final int ALBUM_REQUEST_CODE = 102;
	private static final int SCORE_REQUEST_CODE = 103;
	private static final int RESULT_COMPLETED = 2;
	private static final int RESULT_INCOMPLETE = 3;
	
			
	private String next_theme;
	private int location;
	private ImageView imgRepresent;
	private TextView albumTitle;
	private LinearLayout trendsView;
	private ImageView albums;
	private ImageView play_game;
	private TextView welcomeText;
	
	private GamePlayDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityHelper.initialize(MainActivity.this);
		setContentView(R.layout.activity_main);
		
		ImageView settings = (ImageView) findViewById(R.id.settings_view);
		albums = (ImageView) findViewById(R.id.albums_view);
		play_game = (ImageView) findViewById(R.id.play_game);
		imgRepresent = (ImageView) findViewById(R.id.img_represent);
		albumTitle = (TextView) findViewById(R.id.album_title);
		trendsView = (LinearLayout) findViewById(R.id.trends_view);
		welcomeText = (TextView) findViewById(R.id.welcome_text);
		
		
		datasource = new GamePlayDataSource(this);
	    datasource.open();
		
		
		Utils.applicationContext = getApplicationContext();
		Utils.masterList = Utils.createMasterList();
		Utils.createAlbumRandomSet(datasource.getAvailableThemes());
		
		
		
		//next_theme = DataSet.themeIdArray[theme_no];
		location = Utils.getNextAlbum(); // Corresponding to theme
		
		//imgRepresent.setImageResource(Utils.getResId(next_theme+"_represent", "drawable"));
		
		
		//Listeners
		settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Toast.makeText(MainActivity.this, "Open Options Menu", Toast.LENGTH_SHORT).show();				
			}
		});
							
		
	}
	
	@Override
	protected void onResume(){
		datasource.open();
		super.onResume();
		Random rand = new Random();
		int randomNumber = rand.nextInt(12) + 1;
		String welcomeStringName = "welcome" + randomNumber; 
		int resourceId = getResources().getIdentifier(welcomeStringName, "string", getPackageName());
		String welcomeString = getResources().getString(resourceId);
		welcomeText.setText(welcomeString);
		next_theme = DataSet.themeIdArray[location];
		String album_title = DataSet.themeTitleArray[location];
		albumTitle.setText(album_title, TextView.BufferType.NORMAL);
		imgRepresent.setImageResource(Utils.getResId(next_theme+"_represent", "drawable"));
		trendsView.removeAllViews();
		for(int i=0;i<DataSet.trendArray.length;i++){
			trendsView.addView(createTrendItem(Utils.getResId(DataSet.trendArray[i]+"_trend", "drawable"),DataSet.trendArray[i],i));
		}
		
		albums.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "Open Albums to select next Album", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
				intent.putExtra("location", location);
				startActivityForResult(intent, ALBUM_REQUEST_CODE);
			}
		});
		
		play_game.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				Intent intent = new Intent(MainActivity.this, GameManagerActivity.class);
				Log.d("MainActivity","location: "+location);
				intent.putExtra("location", location);
				intent.putExtra("step", 0);
				startActivityForResult(intent, GAME_REQUEST_CODE);
				
			}
		});
		
		ObjectAnimator animator=ObjectAnimator.ofInt(trendsView, "scrollX", 0, 1200, 0 );
		animator.setDuration(10000);
		animator.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch(requestCode){
		case GAME_REQUEST_CODE:
			if(resultCode == RESULT_OK){
				Intent scoreIntent = new Intent(MainActivity.this,ScoreActivity.class);				
				scoreIntent.putExtra("just_location", data.getIntExtra("just_location", 0));
				scoreIntent.putExtra("total_score", data.getIntExtra("total_score", 0));
				startActivityForResult(scoreIntent, SCORE_REQUEST_CODE);				
			}else if(resultCode == RESULT_INCOMPLETE){
				Intent intent = new Intent(MainActivity.this,GameManagerActivity.class);
				intent.putExtra("total_score", data.getIntExtra("total_score", 0));
				intent.putExtra("location", location);
				intent.putExtra("step", data.getIntExtra("step", 0));
				//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivityForResult(intent, GAME_REQUEST_CODE);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}			
		break;
		case ALBUM_REQUEST_CODE:
			if(resultCode == RESULT_OK){
				location = data.getIntExtra("location", -1);
				next_theme = DataSet.themeIdArray[location];
				Log.d("MainActivity","RESULT_OK - location: "+location);
				imgRepresent.setImageResource(Utils.getResId(next_theme+"_represent", "drawable"));
			}else if(resultCode == RESULT_COMPLETED){
				location = data.getIntExtra("location", -1);
				next_theme = DataSet.themeIdArray[location];
				Log.d("MainActivity","RESULT_COMPLETED - location: "+location);
				imgRepresent.setImageResource(Utils.getResId(next_theme+"_represent", "drawable"));
				
				Intent intent = new Intent(MainActivity.this, GameManagerActivity.class);				
				intent.putExtra("location", location);
				intent.putExtra("step", 0);
				
				startActivityForResult(intent, GAME_REQUEST_CODE);
			}
		break;
		case SCORE_REQUEST_CODE:
			if(resultCode == RESULT_OK){
				location = Utils.getNextAlbum();
				next_theme = DataSet.themeIdArray[location];
				Log.d("MainActivity","location: "+location);
				imgRepresent.setImageResource(Utils.getResId(next_theme+"_represent", "drawable"));
			}else if(resultCode == RESULT_COMPLETED){
				Intent intent = new Intent(MainActivity.this, GameManagerActivity.class);
				Log.d("MainActivity","Replay: location: "+location);
				intent.putExtra("location", location);
				intent.putExtra("step", 0);
				startActivityForResult(intent, GAME_REQUEST_CODE);
			}
		break;
		}
		
	}
	
	private View createTrendItem(int resId, final String tag, int miniAlbumLocation){
		LinearLayout layout = new LinearLayout (MainActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 10, 0);
		layout.setPadding(5, 5, 5, 5);
		layout.setBackgroundColor(Color.parseColor("#f6921e"));
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(params);
		
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,170));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageResource(resId);
		
		imageView.setTag(tag);
		
		layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				location = Utils.indexForAlbum(tag);
				openAlbumDialog(v, tag);
			}
		});
		
		TextView textView = new TextView(getApplicationContext());
		textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,50));
		textView.setTextSize(10);
		textView.setTypeface(Typeface.DEFAULT_BOLD);
		textView.setTextColor(Color.BLACK);
		textView.setPadding(0, 10, 0, 5);
		textView.setBackgroundColor(Color.parseColor("#f6921e"));
		textView.setText(DataSet.themeTitleArray[miniAlbumLocation]);
		
		layout.addView(imageView);
		layout.addView(textView);
		
		return layout;
	}
	
	protected void openAlbumDialog(View view, String tag) {

	//builds modal

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
		alertDialogBuilder.setTitle(DataSet.themeTitleArray[location]);
		Albums al = datasource.getGamePlayRecord(location);
		int hs = al.getHighscore();
		alertDialogBuilder.setMessage("High Score: " + hs);
		alertDialogBuilder.setPositiveButton(R.string.play, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(MainActivity.this, GameManagerActivity.class);
				intent.putExtra("location", location);
				intent.putExtra("step", 0);
				
				startActivityForResult(intent, GAME_REQUEST_CODE);
			}
		});
		
		alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	    }

	    });


	AlertDialog dialog = alertDialogBuilder.create();

	dialog.show();

	}

	@Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

}
