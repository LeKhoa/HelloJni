package com.eeg_project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.eeg_project.Main.FIRMain;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class LoadData {
	public static int size;
	public static double[] arrayData = new double[512];
	public static double[] noise = new double[512];
	public static double[] sinal = new double[512];
	public static ArrayList<Double> array = new ArrayList<Double>();
	public static double[] arraytime = new double[512];

	public static double[] times = new double[512];

	public static void loadEEG() {
		try {
			
//			SharedPreferences myPreferences = PreferenceManager
//					.getDefaultSharedPreferences(this);

			ArrayList<String> arrayList = new ArrayList<String>();
			ArrayList<String> timeList = new ArrayList<String>();
			
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/data.txt");
			FileInputStream fIn = new FileInputStream(f);
			BufferedReader bf = new BufferedReader(new InputStreamReader(fIn));

			String data;
			bf.readLine();

			while (!(data = bf.readLine()).equals("")) {
				String[] str = data.split(",");

				arrayList.add(str[FIRMain.chanel_id]);
				
				timeList.add(str[0]);			
				
			}
			for (int i = 0; i < arrayList.size(); i++) {
				arrayData[i] = Double.parseDouble(arrayList.get(i));
//				Log.i("ARRAYDATA", "" + arrayData[i]);
				
				arraytime[i] = Double.parseDouble(timeList.get(i));

			}

			Log.e("Size", "" + arrayList.size());

			fIn.close();
			bf.close();
			System.out.println("-----------------");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// read noise
		try {
			ArrayList<String> arraynoise = new ArrayList<String>();
			FileInputStream fIn_noise = new FileInputStream(
					Environment.getExternalStorageDirectory()
							+ "/sinsignal.txt");

			BufferedReader bf_noise = new BufferedReader(new InputStreamReader(
					fIn_noise));
			String data_noise;
			int j = 0;
			while ((data_noise = bf_noise.readLine()) != null) {

				noise[j] = Double.parseDouble(data_noise);
	//			Log.e("Noise" + j, "" + noise[j]);
				j++;

			}
			bf_noise.close();
			fIn_noise.close();

		} catch (IOException ex) {
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		// read noise

		for (int k = 0; k < 512; k++) {
//			sinal[k] = arrayData[k] + noise[k];
			
			
			sinal[k] = arrayData[k];
			array.add(sinal[k]);

		}
	}

}
