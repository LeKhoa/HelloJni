package eeg.DWT;

import java.util.Arrays;

import eeg.utils.Power2Utils;

/**
 * T��da diskr�tn� waveletov� transfromace.
 */
public class DWT
{
	//typ waveletu
	private WaveletDWT wavelet;
	
	//d�lka waveletu
	private int waveletLength;
	
	//d�lka waveletu
	private int majorWaveletLengthOfPow2;
	
	//pole koeficient� vypo�ten�ch dwt
	private double[] dwtData;
	
	//pole hodnot rekonstruovan�ho sign�lu
	private double[] reconstructedSignal;
	
	/**
	 * Konstruktor DWT_algotithm.
	 * 
	 * @param wavelet - wavelet.
	 */
	public DWT(WaveletDWT wavelet)
	{
		this.wavelet = wavelet;
		this.waveletLength = wavelet.getScaleArray().length;
		this.majorWaveletLengthOfPow2 = Power2Utils.newMajorNumberOfPowerBase2(waveletLength);
	}
	
	
	/**
	 * Metoda upravuje sign�l a spou�t� transformaci, pokud m� sign�l d�lku waveletu nebo v�t��.
	 * Kone�n� �rove� transformace je odvozena od d�lky sign�lu.
	 * 
	 * @param inputSignal - origin�ln� vstupn� sign�l.  
	 * @return sign�l po transformaci.
	 */
	public double[] transform(double[] inputSignal)
	{		
		dwtData = Power2Utils.signalPowerBase2(inputSignal);		

		for (int last = dwtData.length; last >= majorWaveletLengthOfPow2; last /= 2)
		{
			transform(dwtData, last);
		}
		
		return dwtData;
	}

	/**
	 * Metoda spou�t� inverzn� transformaci, pokud m� pole koeficient� d�lku waveletu nebo v�t��. 
	 * 
	 * @param inputCoef - transformovan� vstupn� koeficienty.  
	 * @return sign�l po inverzn� transformaci.
	 */
	public double[] invTransform(double[] inputCoef)
	{
		reconstructedSignal = Power2Utils.signalPowerBase2(inputCoef);		
		
		for (int last = majorWaveletLengthOfPow2; last <= reconstructedSignal.length; last *= 2)
		{
			invTransform(reconstructedSignal, last);
		}
		
		return reconstructedSignal;
	}
	
	/**
	 * Metoda transformuj�c� sign�l pomoc� nastaven�ho waveletu.
	 * V prvn� polovin� �seku transformovan�ho sign�lu je ukl�d�n� aproxima�n� slo�ka
	 * z�skan� �k�lov�mi koeficienty a v druh� polovin� je ukl�d�na detailn� slo�ka
	 * z�skan� pomoc� waveletov�ch koeficient�.
	 * 
	 * 
	 * @param signal - transformovan� sign�l.
	 * @param last - d�lka transformovan�ho �seku sign�lu.
	 */
	private void transform(double[] signal, int last)
	{
		int half = last/2;
		double tmp[] = new double[last];
		int i = 0, j, k;
			
		for (j = 0; j < last; j += 2)
		{
			for(k = 0; k < waveletLength; k++)
			{
				tmp[i] += signal[(j+k)%last] * wavelet.getScaleCoef(k);					
				tmp[i + half] += signal[(j+k)%last] * wavelet.getWaveletCoef(k);
			}
			
			i++;
		}
			
		
		for (i = 0; i < last; i++)
		{
			signal[i] = tmp[i];
		}		
	} 

	/**
	 * Metoda transformuj�c� koeficienty zp�tky na origin�ln� sign�l 
	 * pomoc� nastaven�ho waveletu. 
	 * Skl�d� p�vodn� sign�l z aproxima�n�ch a detailn�ch slo�ek
	 * transformovan�ho sign�lu.
	 * 
	 * p�:
	 * tmp[0] = [s6....s0 s2 s4 | s7....s1 s3 s5]
	 * tmp[1] = [w6....w0 w2 w4 | w7....w1 w3 w5]
	 * ...
	 * tmp[.] = [s0 s2 s4 s6 .. | s1 s3 s5 s7 ..]
	 * tmp[.] = [w0 w2 w4 w6 .. | w1 w3 w5 w7 ..]
	 * ...
	 * tmp[n-2] = [.. s0 s2 s4 s6 | .. s1 s3 s5 s7]
	 * tmp[n-1] = [.. w0 w2 w4 w6 | .. w1 w3 w5 w7]
	 * 
	 * @param coef - transformovan� sign�l.
	 * @param last - d�lka transformovan�ho �seku sign�lu.
	 */
	private void invTransform(double[] coef, int last)
	{
		double tmp[] = new double[last];
		int half = last/2;
		int i, j = 0, k, index;
		
		i = half - ((waveletLength/2) - 1);
			
		for (index = 0; index < half; index++)
		{
			for(k = 0; k < waveletLength - 1; k += 2)
			{
				tmp[j] += 	coef[(i+(k/2))%half] * wavelet.getIScaleCoef(k) + 
							coef[((i+(k/2))%half) + half] * wavelet.getIScaleCoef(k+1);
				
				tmp[j + 1] += 	coef[(i+(k/2))%half] * wavelet.getIWaveletCoef(k) + 
								coef[((i+(k/2))%half) + half] * wavelet.getIWaveletCoef(k+1);
			}
			
			j += 2;
			
			i++;
		}
			
		for (i = 0; i < last; i++)
		{
			coef[i] = tmp[i];
		}		
	}		
}