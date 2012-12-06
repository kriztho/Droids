package com.example.droids;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

//Called when the activity is created
public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//request to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set our MainGamePanel as the View
		//setContentView(R.layout.activity_main);
		setContentView(new MainGamePanel(this));
		Log.d(TAG, "View Added");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}
	
	@Override
	protected void onStop(){
		Log.d(TAG, "Stopping...");
		super.onStop();
	}

}