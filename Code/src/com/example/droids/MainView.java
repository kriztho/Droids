package com.example.droids;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainView extends View {
	
	// Declare the UI components
	private ListView demosListView;

	// Declare an array to store data to fill the list
	private String[] demosArray;

	// Declare an ArrayAdapter that we use to join the data set and the ListView
	// is the way of type safe, means you only can pass Strings to this array
	//Anyway ArrayAdapter supports only TextView
	private ArrayAdapter<String> demosArrayAdapter;

	public MainView(Context context) {
		super(context);
		
		demosListView = new ListView(context);	// Create the List View
		demosArray = new String[3];				// Create the string
		demosArray[0] = "1. Droidz";
		demosArray[1] = "2. Animated Elaine";
		demosArray[2] = "3. Fireworks";

	}
}
