package com.eeg_project.Main;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eeg_project.R;
import com.eeg_project.Main.FIRMain;
import com.eeg_project.utils.Arrays;
import com.eeg_project.utils.LoadData;

public class FIRTest extends Activity {
	FIRMain eegtest = new FIRMain();
	TextView tvFited;
	private GraphicalView firChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filted);

		// tvFited.append(Arrays.toString(eegtest.periodicConvolution, "\n"));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tvFited = (TextView) findViewById(R.id.tvFir);
		tvFited.setText("");
		tvFited.append("\nConvolution:\n");
		// double[] convolution = Convolution.convolution(testSignal,
		// filterCoeff);
		tvFited.append(Arrays.toString(eegtest.convolution, "\n"));
		tvFited.append("\nPeriodic convolution:\n");
		drawChart();
		super.onResume();
	}

	public void drawChart() {
		double[] x = LoadData.arraytime;
		double[] y = eegtest.convolution;

		TimeSeries series = new TimeSeries("EEG signal");
		for (int i = 0; i < 512; i++) {
			series.add(x[i], y[i]);
		}
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("EEG filted");
		mRenderer.setXTitle("Time(s)");
		mRenderer.setYTitle("microVolt");

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		LinearLayout firChart_container = (LinearLayout) findViewById(R.id.firChart);
		firChart = (GraphicalView) ChartFactory.getLineChartView(
				getBaseContext(), dataset, mRenderer);

		firChart_container.addView(firChart);
	}

}