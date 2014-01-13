package eeg.utils;

import java.util.Arrays;

/**
 * T��da pro matematick� operace.
 * 
 * @author Petr Soukal
 */
public class Power2Utils {
    //konstanty

    public final static int CONST_2 = 2;
    public final static int ZERO = 0;

    /**
     * Metoda vypo��t�v� logaritmus o z�kladu 2 z vlo�en�ho ��sla.
     *
     * @param x - ��slo ze kter�ho se po��t� logaritmus o z�kladu 2.
     * @return log2 z x.
     */
    public static double log2(double x) {
        return Math.log(x) / Math.log(CONST_2);
    }

    /**
	 * Pokud neni vlo�en� ��slo mocninou z�kladu 2, tak vr�t� prvn� v�t�� ��slo,
	 * kter� je mocninou z�kladu 2.
	 * 
	 * @param x - ��slo, u kter�ho se zji��uje zda je z�kladu 2.
	 * @return ��slo x nebo prvn� v�t�� ��slo, kter� je mocninou z�kladu 2.
	 */
	public static int newMajorNumberOfPowerBase2(int x){
		double number = log2(x);
		int temp = (int)number;
		
		if(number%temp == 0)		
			return x;
		else
		{
			temp += 1;
			int newNumber = (int) Math.pow(CONST_2, temp);
			return newNumber;
		}
	}
	
	/**
	 * Pokud neni vlo�en� ��slo mocninou z�kladu 2, tak vr�t� prvn� men�� ��slo,
	 * kter� je mocninou z�kladu 2.
	 * 
	 * @param x - ��slo, u kter�ho se zji��uje zda je z�kladu 2.
	 * @return ��slo x nebo prvn� men�� ��slo, kter� je mocninou z�kladu 2.
	 */
	public static int newMinorNumberOfPowerBase2(int x){
		double number = (int)log2(x);
		
		return (int) Math.pow(CONST_2, number);
	}

	/**
	 * Metoda prodlu�uje vstupn� sign�l na d�lku (2^n) pokud takovou d�lku nem�
	 * a nov� m�sto se vypln� nulami.
	 */
	public static double[] signalPowerBase2(double[] inputSignal)
	{
		int newSignalLength = Power2Utils.newMajorNumberOfPowerBase2(inputSignal.length);
		
		double[] signal = new double[newSignalLength];
		
		if(newSignalLength != inputSignal.length)
		{			
			
			signal = Arrays.copyOf(inputSignal, newSignalLength);
			Arrays.fill(signal, inputSignal.length, signal.length, Power2Utils.ZERO);
		}
		else
			signal = inputSignal.clone();
		
		return signal;
	}
	
    public static double[] createPower2LengthArray(double[] inputSignal) {
        double[] signal;

        int newSignalLength = Power2Utils.newMajorNumberOfPowerBase2(inputSignal.length);

        if (newSignalLength != inputSignal.length) {
            signal = Arrays.copyOf(inputSignal, newSignalLength);
            Arrays.fill(signal, inputSignal.length, signal.length, Power2Utils.ZERO);
        } else {
            signal = inputSignal.clone();
        }

        return signal;
    }
    
    public static double euclideanNorm(double[] array)
    {
    	double retval = 0;
    	
    	for (int i = 0; i < array.length; i++)
    		retval += Math.pow(array[i], 2);
    	
    	return Math.sqrt(retval);
    }
}
