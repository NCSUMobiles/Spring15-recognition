package com.istudy.fragment;

import com.example.istudy.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountdownFragment extends Fragment {
	private int timeleft;
	private TextView tView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      View view = inflater.inflate(R.layout.countdown, container, false);
      timeleft = 10;
      tView = (TextView)view.findViewById(R.id.countdown);
      tView.setText("");
      
            
      return view;
    }
	
	public void updateTimeLeft(){
		timeleft--;
		
		switch(timeleft){
		case 6: 
			tView.setTextColor(Color.rgb(238,200,35));
			break;
		case 5: 
			tView.setTextColor(Color.rgb(238,170,35));
			break;
		case 4: 
			tView.setTextColor(Color.rgb(238,130,35));
			break;
		case 3: 
			tView.setTextColor(Color.rgb(238,85,35));
			break;
		case 2: 
			tView.setTextColor(Color.rgb(238,40,35));
			break;
		case 1:
			tView.setTextColor(Color.rgb(238,10,35));
			break;
		}
		
		if(timeleft < 7 && timeleft > 0){
			tView.setText(""+(timeleft - 1));
		}
		
	}


}
