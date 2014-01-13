package eeg.DWT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eeg.FIR.FIRMain;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class LoadEEG {
	public static double[] arrayData = new double[900];

	public static double[] loadEEG() {
		try {

			// SharedPreferences myPreferences = PreferenceManager
			// .getDefaultSharedPreferences(this);

			ArrayList<String> arrayList = new ArrayList<String>();

			File f = new File(Environment.getExternalStorageDirectory()
					+ "/fp1.txt");
			FileInputStream fIn = new FileInputStream(f);
			BufferedReader bf = new BufferedReader(new InputStreamReader(fIn));

			String data;
			// bf.readLine();
			while (!(data = bf.readLine()).equals("")) {
//				while ((data=bf.readLine())!=null) {
				String[] str = data.split(" ");
				for (int i = 0; i < str.length; i++) {
					arrayList.add(str[i]);

				}
			}
			Log.d("DATA", "" + arrayList.size());

			for (int i = 0; i < arrayList.size(); i++) {
				arrayData[i] = Float.parseFloat(arrayList.get(i));
			}

			Log.e("Size", "" + arrayList.size());

			fIn.close();
			bf.close();
			// System.out.println("-----------------");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return arrayData;
	}

}
