package eeg.DWT;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.eeg_project.R;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import eeg.utils.LoadData;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DWTTest extends Activity {
	private GraphicalView dwtChart, detetctChart;
	TextView tv;
	LoadData loadData = new LoadData();
	private static final int WAVELET = Menu.FIRST;
	private static final int DETECT_EYEBLINK = Menu.FIRST + 1;
	private static final int DETECT_GLANCE = Menu.FIRST + 2;
	private static final int REMOVE_EYEBLINK = Menu.FIRST + 4;
	LinearLayout Chart_container;
	// LoadEEG loadEEG = new LoadEEG();
	double[] x = LoadEEG.loadEEG();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ffttest);
		tv = (TextView) findViewById(R.id.tv);
		Chart_container = (LinearLayout) findViewById(R.id.dwtChart);

		// double[] input = new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		// loadData.loadEEG();
		//
		// double[] input = new double[loadData.arrayData.length];
		// System.arraycopy(loadData.arrayData, 0, input, 0,
		// loadData.arrayData.length);
		//
		// DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);
		// double[] fft = new double[input.length * 2];
		// System.arraycopy(input, 0, fft, 0, input.length);
		// fftDo.realForwardFull(fft);
		//
		// for (int i = 0; i < fft.length; i += 2) {
		//
		// tv.append("X" + i / 2 + "=" + String.valueOf(fft[i]) + " + "
		// + String.valueOf(fft[i + 1]) + "i" + "\n");
		//
		// }

		drawChart();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// menu.add(Menu.NONE, DETECT_EYEBLINK, Menu.NONE, "DETECT EYEBLINK");
		menu.add(Menu.NONE, DETECT_GLANCE, Menu.NONE, "DETECT GLANCE");
		menu.add(Menu.NONE, REMOVE_EYEBLINK, Menu.NONE, "REMOVE EYEBLINK");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case DETECT_EYEBLINK:

			break;
		case DETECT_GLANCE:
			detect();
			break;
		case REMOVE_EYEBLINK:
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void drawChart() {

		TimeSeries series = new TimeSeries("Origin signal");
		for (int i = 0; i < x.length; i++) {
			series.add(i, x[i]);
		}
		double[] y = new double[x.length];
		DWT dwt = new DWT(new Daubechies4());
		
		double[] dwtTransform = dwt.transform(x);
		
		for (int i = 0; i < x.length; i++) {
			Log.e("DWT", "" + dwtTransform[i]);

			if (dwtTransform[i] > 50 | dwtTransform[i] < -50)
				dwtTransform[i] = 0;
		}	

		double[] s = dwt.invTransform(dwtTransform);

		TimeSeries series2 = new TimeSeries("EEG Denoised");
		for (int i = 0; i < x.length; i++) {
			series2.add(i, s[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(series2);
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("DENOISE");
		mRenderer.setXTitle("Samples");
		mRenderer.setYTitle("microVolt");

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.BLUE);

		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(Color.RED);

		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);

		dwtChart = (GraphicalView) ChartFactory.getLineChartView(
				getBaseContext(), dataset, mRenderer);

		Chart_container.addView(dwtChart);
	}

	public void detect() {

		TimeSeries series = new TimeSeries("Origin signal");

		for (int i = 0; i < x.length; i++) {
			series.add(i, 1000);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		seriesRenderer.setColor(Color.GREEN);
		seriesRenderer.setPointStyle(PointStyle.DIAMOND);
		seriesRenderer.setFillPoints(true);
		seriesRenderer.setLineWidth(10);

		renderer.addSeriesRenderer(seriesRenderer);
		detetctChart = (GraphicalView) ChartFactory.getScatterChartView(
				getBaseContext(), dataset, renderer);
		Chart_container.addView(detetctChart);
		Log.e("DETECT ", "OK");

	}
}
