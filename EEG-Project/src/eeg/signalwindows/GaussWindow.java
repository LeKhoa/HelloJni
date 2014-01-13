package eeg.signalwindows;

import java.util.Arrays;

/**
 * Gaussovo okno podle Matlabu.
 * Matlab pou?�v� implementaci podle: 
 * 		fredric j. harris [sic], On the Use of Windows for Harmonic 
 * 		Analysis with the Discrete Fourier Transform, Proceedings of 
 * 		the IEEE, Vol. 66, No. 1, January 1978
 * 
 * 		w(i) = w(i) = exp(-1/2 * (alpha * (i - (N - 1)/2) / (N/2))^2),
 * 
 *  kde alpha je p�evr�cen� hodnota rozptylu. I p�es naprosto shodnou 
 *  implementaci s matalbem 7.0.1 dosahuje odchylka zde generovan�ho okna
 *  oproti matlabu a? 10^-6. Pravd�podobn� je to t�m, ?e matlab stejn� jako
 *  java pou?�v� nativn� funkci exp(), kter� se v obou implementac�ch li?�.
 *
 * @param signal je pole kter� se m� p�en�sobit oknem.
 * @param alpha je p�evr�cen� hodnota rozptylu.
 * */
public class GaussWindow implements Window {

	//private double sigma; //pro implementaci dle wiki
	private double alpha;
	
	public GaussWindow(){
		//mo?n� sem dod�l�m defaultn� alpha = 2.5 jako je v matlabu
		this.alpha = 2.5; 
	}
	
	public GaussWindow(double alpha){
		this.alpha = alpha;
	}
	
	/**
	 * Vr�t� hodnotu rozptylu pou?itou v 
	 * */
	public double getAlpha(){
		return this.alpha;
	}
	
	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getSignalRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double[] getWinSequence(int orderRate) {
		//vytvo�en� pole pro okno
		double[] retval = new double[orderRate];
		
		//vypln�n� jedni�kou
		Arrays.fill(retval, 1);
		
		//p�en�soben� oknem, co? je jako zaps�n� okna
		multiplyByGaussWindow(retval, this.alpha);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * Vyn�sob� sign�l Gaussov�m oknem podle definice z: Matlabu
	 * 	
	 * Matlab pou?�v� implementaci podle: 
	 * 		fredric j. harris [sic], On the Use of Windows for Harmonic 
	 * 		Analysis with the Discrete Fourier Transform, Proceedings of 
	 * 		the IEEE, Vol. 66, No. 1, January 1978
	 * 
	 * 		w(i) = w(i) = exp(-1/2 * (alpha * (i - (N - 1)/2) / (N/2))^2),
	 * 
	 *  kde alpha je p�evr�cen� hodnota rozptylu. I p�es naprosto shodnou 
	 *  implementaci s matalbem 7.0.1 dosahuje odchylka zde generovan�ho okna
	 *  oproti matlabu a? 10^-6. Pravd�podobn� je to t�m, ?e matlab stejn� jako
	 *  java pou?�v� nativn� funkci exp(), kter� se v obou implementac�ch li?�.
	 *
	 * @param signal je pole kter� se m� p�en�sobit oknem.
	 * @param alpha je p�evr�cen� hodnota rozptylu.
	 * */
	public static void multiplyByGaussWindow(double[] signal, double alpha){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatn� ��sti vzorce
		double konst1 = (double)(signal.length - 1) / (double)2;
		double konst3 = (double)signal.length / (double)2;
		//double konst2 = 1 / (sigma * konst1);
		double konst2 = alpha / konst3; 
		double frac;
		
		for(int i = 0; i < signal.length; i += 1){
			// frac = (i - konst1) * konst2;   //implementace podle wiki
			// signal[i] *= Math.exp(-0.5 * frac * frac);
			
			frac = (i - konst1) * konst2;   //implementace podle matlabu
			signal[i] *= Math.exp(-0.5 * frac * frac);
		}
	}

        public static double applyGaussWindow(double t) {
            return Math.exp(-Math.PI * Math.pow(t, 2));
        }

}
