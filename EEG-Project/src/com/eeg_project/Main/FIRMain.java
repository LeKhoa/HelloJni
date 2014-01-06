package com.eeg_project.Main;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.eeg_project.R;
import com.eeg_project.FFT.FFTTest;
import com.eeg_project.FIR.FIRFilter;
import com.eeg_project.FIR.Filter.FilterTypes;
import com.eeg_project.Main.FIRTest;
import com.eeg_project.math.Convolution;
import com.eeg_project.signalwindows.WindowFactory.WINDOWS;
import com.eeg_project.utils.Arrays;
import com.eeg_project.utils.LoadData;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FIRMain extends Activity {
	private GraphicalView mChart;
	public static final int SETTING_ID = Menu.FIRST;
	public static final int CHANEL = Menu.FIRST + 1;
	double[] testSignal = new double[512];
	public static double[] filterCoeff, convolution, periodicConvolution;
	public static ArrayList<Double> conv = new ArrayList<Double>();
	public static int chanel_id = 1;
	private int fLM = 128, above = 30, bellow = 3;

	// public double[] testSignal = new double[1000];
	TextView tv;
	LoadData loadData = new LoadData();
	FIRFilter filtr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.textView);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tv.setText("");

		SharedPreferences myPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		chanel_id = Integer.parseInt(myPreferences.getString("id_chanel", "1"));
		fLM = Integer.parseInt(myPreferences.getString("flm_value", "128"));
		above = Integer.parseInt(myPreferences.getString("above_cutoff_value",
				"40"));
		bellow = Integer.parseInt(myPreferences.getString(
				"bellow_cutoff_value", "3"));
		Log.e("id", "" + chanel_id + " fLM=" + fLM + "bellow= " + bellow
				+ "above= " + above);

		loadData.loadEEG();
		filtr = new FIRFilter(4, FilterTypes.BANDTH_PASS,
				WINDOWS.RECTANGULAR_WINDOW, bellow, above, fLM);
		// Fir tranform
		filtr.firFilterDesign();
		filterCoeff = filtr.getAllFIRFilterCoeff();
		tv.append("FIR filter coeff.:\n");
		tv.append(filtr.allFIRFilterCoeffToString("\n"));

		// tv.append("\nSignal Ratio: " + filtr.getSignalRatio() + " [dB]\n");

		tv.append("\nTest signal:\n");

		// load data = origin signal+ noise

	//	double[] testSignal = new double[loadData.sinal.length];

		for (int i = 0; i < loadData.sinal.length; i++) {
			testSignal[i] = loadData.sinal[i];

		}

		tv.append(Arrays.toString(testSignal, "\n"));

		convolution = Convolution.convolution(testSignal, filterCoeff);
		Log.e("CONVOLUTION", "" + convolution.length);
		for (int j = 0; j < convolution.length; j++) {
			conv.add(convolution[j]);

		}

		// chap vong:
		periodicConvolution = Convolution.periodicConvolution(testSignal,
				filterCoeff);
		mChart=null;
		drawChart();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.eegtest, menu);

		menu.add(Menu.NONE, SETTING_ID, Menu.NONE, "SETTING").setIcon(
				R.drawable.ic_launcher);
		return true;
	}

	// Vẽ đồ thị:

	public void drawChart() {
		double[] x = LoadData.arraytime;
		double[] y = testSignal;

		TimeSeries series = new TimeSeries("EEG signal");
		for (int i = 0; i < 512; i++) {
			series.add(x[i], y[i]);
		}
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("EEG signal");
		mRenderer.setXTitle("Time");
		mRenderer.setYTitle("microVolt");

		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.RED);
		mRenderer.addSeriesRenderer(renderer);
		LinearLayout chart_container = (LinearLayout) findViewById(R.id.Chart_layout);
		mChart = (GraphicalView) ChartFactory.getLineChartView(
				getBaseContext(), dataset, mRenderer);

		chart_container.addView(mChart);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case SETTING_ID:

			Intent setting = new Intent(getApplicationContext(),
					SetPreference.class);
			startActivity(setting);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);

	}
}
