package com.eeg_project.FFT;

import com.eeg_project.R;
import com.eeg_project.utils.LoadData;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class FFTTest extends Activity {
	TextView tv;
	LoadData loadData = new LoadData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ffttest);
		tv = (TextView) findViewById(R.id.tv);

		// double[] input = new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		loadData.loadEEG();

		double[] input = new double[loadData.arrayData.length];
		System.arraycopy(loadData.arrayData, 0, input, 0,
				loadData.arrayData.length);

		DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);
		double[] fft = new double[input.length * 2];
		System.arraycopy(input, 0, fft, 0, input.length);
		fftDo.realForwardFull(fft);

		for (int i = 0; i < fft.length; i += 2) {

			tv.append("X" + i / 2 + "=" + String.valueOf(fft[i]) + " + "
					+ String.valueOf(fft[i + 1]) + "i" + "\n");

		}

	}

}
