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
      timeleft = 11;
      tView = (TextView)view.findViewById(R.id.countdown);
//      tView.setText("");
           
      return view;
    }
	
	public void updateTimeLeft(){
		timeleft--;
		
		switch(timeleft-1){
		case 10: 
			tView.setTextColor(Color.rgb(50,250,0));
			break;
		case 9: 
			tView.setTextColor(Color.rgb(100,250,0));
			break;
		case 8: 
			tView.setTextColor(Color.rgb(150,250,0));
			break;
		case 7: 
			tView.setTextColor(Color.rgb(200,200,0));
			break;
		case 6: 
			tView.setTextColor(Color.rgb(250,150,0));
			break;
		case 5: 
			tView.setTextColor(Color.rgb(250,100,0));
			break;
		case 4: 
			tView.setTextColor(Color.rgb(250,75,0));
			break;
		case 3: 
			tView.setTextColor(Color.rgb(250,50,0));
			break;
		case 2:
			tView.setTextColor(Color.rgb(250,25,0));
			break;
		case 1:
			tView.setTextColor(Color.rgb(250, 0, 0));
		}
		
		if(timeleft < 11 && timeleft > 0){
			tView.setText(" "+(timeleft-1) + " ");
		}	
	}


}
