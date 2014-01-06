package com.eeg_project.Main;

import com.eeg_project.R;
import com.eeg_project.FFT.FFTTest;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);

		TabHost tabHost = getTabHost();

		// Tab for read
		TabSpec readspec = tabHost.newTabSpec("ReadData");
		// setting Title and Icon for the Tab
		readspec.setIndicator("DATA",
				getResources().getDrawable(R.drawable.fft));
		Intent readIntent = new Intent(this, FIRMain.class);
		readspec.setContent(readIntent);

		// Tab fir
		TabSpec firspec = tabHost.newTabSpec("FIR");
		firspec.setIndicator("FIR", getResources().getDrawable(R.drawable.fir));
		Intent firIntent = new Intent(this, FIRTest.class);
		firspec.setContent(firIntent);

		// Tab for fft
		TabSpec fftspec = tabHost.newTabSpec("DWT");
		fftspec.setIndicator("DWT", getResources().getDrawable(R.drawable.fft));
		Intent fftIntent = new Intent(this, FFTTest.class);
		fftspec.setContent(fftIntent);

		TabSpec viewGraph = tabHost.newTabSpec("GRAPH");
		viewGraph.setIndicator("GRAPH");
		Intent graphIntent = new Intent(this, GraphView.class);
		viewGraph.setContent(graphIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(readspec); 
		tabHost.addTab(firspec); 
		tabHost.addTab(viewGraph);
		tabHost.addTab(fftspec); // Adding videos tab
	}
}
