package com.example.droids;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class FireworksActivity extends Activity {
	
	private static final String TAG = FireworksActivity.class.getSimpleName();
	private Fireworks gamePanel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//request to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		gamePanel = new Fireworks(getApplicationContext());
		setContentView(gamePanel);			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fireworks, menu);
		return true;
	}

	public void makeToast(CharSequence text) {
		Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void updateFireworks(int option) {
		/*
		//makeToast("Settings Page!");
		Intent preference = new Intent(getApplicationContext(), PrefDroidz.class);
		
		makeToast("Speed: "+gamePanel.getAvgFps());
		
		preference.putExtra("speed", gamePanel.getAvgFps());
		//preference.putExtra("numberDroids", gamePanel.get);
		//preference.putExtra("fps", gamePanel.getAvgFps());
		startActivity(preference);
		*/
		
		int size = gamePanel.getFireworksSize();
		
		// Increase fireworks
		if ( option == 0 ) {
			size++;
			gamePanel.setFireworksSize(size);
		} else { // decrease fireworks
			size--;
			gamePanel.setFireworksSize(size);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.decFireworks:
	            updateFireworks(0); // increase
	            return true;
	        case R.id.incFireworks:
	            updateFireworks(1); // decrease
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
