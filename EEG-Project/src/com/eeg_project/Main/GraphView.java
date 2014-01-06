package com.eeg_project.Main;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.eeg_project.R;
import com.eeg_project.utils.LoadData;

public class GraphView extends Activity {
	private XYPlot plot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
				WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.graph);
		// init graph;
		plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
		/*
		 * couple ARRAY of Y to plot LoadData.arrayData.(type[])
		 * collection.toArray(new type[collection.size()]) turn the arrays into
		 * XY:
		 */
		// sum signal = noise + origin
		// double[] doubles1 = LoadData.sinal;
		// Number[] sum = new Number[doubles1.length];
		// for (int i = 0; i < doubles1.length; i++) {
		// sum[i] = doubles1[i];
		// }

		// origin
		double[] doubles2 = LoadData.arrayData;
		Number[] origin = new Number[doubles2.length];
		for (int i = 0; i < doubles2.length; i++) {
			origin[i] = doubles2[i];
		}
		// signal after filted
		double[] doubles3 = FIRMain.convolution;
		Number[] result = new Number[doubles3.length];
		for (int i = 0; i < doubles3.length; i++) {
			result[i] = doubles3[i];
		}
//		XYSeries series1 = new SimpleXYSeries(Arrays.asList(sum),
//				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
		XYSeries series2 = new SimpleXYSeries(Arrays.asList(origin),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "origin signal");
		XYSeries series3 = new SimpleXYSeries(Arrays.asList(result),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "signal filted");
		// create format to use to draw
//
//		LineAndPointFormatter formatter1 = new LineAndPointFormatter();
//		formatter1.setPointLabelFormatter(new PointLabelFormatter());
//		formatter1.configure(getApplicationContext(), R.xml.line_formater1);
//		// add to plot:
//		plot.addSeries(series1, formatter1);

		LineAndPointFormatter formatter2 = new LineAndPointFormatter();
		formatter2.setPointLabelFormatter(new PointLabelFormatter());
		formatter2.configure(getApplicationContext(), R.xml.line_formater2);
		plot.addSeries(series2, formatter2);

		LineAndPointFormatter formatter3 = new LineAndPointFormatter();
		formatter3.setPointLabelFormatter(new PointLabelFormatter());
		formatter3.configure(getApplicationContext(), R.xml.line_formater3);
		plot.addSeries(series3, formatter3);

		// reduce the number of range labels
		plot.setTicksPerRangeLabel(2);
		plot.getGraphWidget().setDomainLabelOrientation(-45);
	}
}
