package com.example.istudy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class SettingsActivity extends Activity implements OnCheckedChangeListener {
	
	private RadioGroup radioGroup; 
	private Switch soundSwitch;
	
	String MY_PREFERENCES = "MyPrefs";
	String difficultyKey = "difficulty";
	String soundKey = "sounds";
	SharedPreferences prefs;
	Editor editor;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_MULTI_PROCESS);
	    editor = prefs.edit();
	    
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		soundSwitch = (Switch)findViewById(R.id.soundSwitch);
		
		soundSwitch.setChecked(prefs.getBoolean(soundKey, true));
		
		int diff = prefs.getInt(difficultyKey, 3);
		
		switch (diff) {
		case 1:radioGroup.check(R.id.radioEasy);
			break;
		case 2:radioGroup.check(R.id.radioMed);
		break;
		case 3:radioGroup.check(R.id.radioHard);
		break;
		default:
			break;
		}
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		            @Override
		            public void onCheckedChanged(RadioGroup group, int checkedId) {
		                // find which radio button is selected
		                if(checkedId == R.id.radioEasy) {
		                    Toast.makeText(getApplicationContext(), "Easy",Toast.LENGTH_SHORT).show();
		                    editor.putInt(difficultyKey, 1);
		                } else if(checkedId == R.id.radioMed) {
		
		                    Toast.makeText(getApplicationContext(), "Medium", Toast.LENGTH_SHORT).show();
		                    editor.putInt(difficultyKey, 2);
		
		                } else {
		
		                    Toast.makeText(getApplicationContext(), "Hard", Toast.LENGTH_SHORT).show();
		                    editor.putInt(difficultyKey, 3);
	
		                }
		                
		                editor.commit();
	
		            }
		            
		           	
		        });
		
		soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					editor.putBoolean(soundKey, true);
				}
				else
				{
					editor.putBoolean(soundKey, false);
				}
				
				editor.commit();
			}			
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}
}
