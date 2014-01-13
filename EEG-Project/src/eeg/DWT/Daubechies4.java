package eeg.DWT;

/**
 * T��da waveletu Daubechies4.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Daubechies4 extends WaveletDWT
{
	//n�zev waveletu
	private final static String NAME = "Daubechies_4";
	
	//parametry pro vypo��t�n� koeficient� waveletu
	private final static double SQRT_3 = Math.sqrt(3);
	private final static double DENOM = 4 * Math.sqrt(2);
	
	//�k�lov� koeficienty
	private final static double[] SCALE = 	{	(1 + SQRT_3) / DENOM,
												(3 + SQRT_3) / DENOM,
												(3 - SQRT_3) / DENOM,
												(1 - SQRT_3) / DENOM};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	SCALE[3], -SCALE[2],
												SCALE[1], -SCALE[0]	};
	
	
	//�k�lov� koeficienty pro inverzn� transformaci
	private final static double[] I_SCALE = 	{	SCALE[2], WAVELET[2],
													SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzn� transformaci
	private final static double[] I_WAVELET = 	{	SCALE[3], WAVELET[3],
													SCALE[1], WAVELET[1]};	
	
	/**
	 * Konstruktor waveletu Daubechies4.
	 */
	public Daubechies4()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
