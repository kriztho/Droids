package com.example.droids;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class LaunchDemo extends Activity {
	
	private static final String TAG = LaunchDemo.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//request to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//reading information passed to this activity
        //Get the intent that started this activity
        Intent i = getIntent();
        
        //returns -1 if not initialized by calling activity        
        int demoId = i.getIntExtra("demoId", -1);
        
        switch ( demoId ) {
        
        	case 1:	        		
        		setContentView(new Droidz(getApplicationContext()));
        		break;
        	case 2:
        		setContentView(new AnimatedElaine(getApplicationContext()));
        		break;
        	case 3:
        		setContentView(new Fireworks(getApplicationContext()));
        		break;
        	default:
        		setContentView(null);
        		break;
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_launch_demo, menu);
		return true;
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "Destroying...");
		finish();
		super.onDestroy();
	}
	
	@Override
	protected void onStop(){
		Log.d(TAG, "Stopping...");
		super.onStop();
	}

}
