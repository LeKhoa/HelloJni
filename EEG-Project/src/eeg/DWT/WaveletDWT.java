package eeg.DWT;

/**
 * Rozhran� waveletu pro diskr�tn� waveletovou transformaci.
 */
public abstract class WaveletDWT
{
	private String name;
	private double[] scale;
	private double[] wavelet;
	private double[] iScale;
	private double[] iWavelet;
	
	
	/**
	 * Konstruktor WaveletDWT.
	 */
	public WaveletDWT(String name, double[] scale, double[] wavelet, double[] iScale, double[] iWavelet)
	{
		this.name = name;
		this.scale = scale;
		this.wavelet = wavelet;
		this.iScale = iScale;
		this.iWavelet = iWavelet;
	}
	
	/**
	 * @return pole �k�lov�ch keoficient�.
	 */
    public double[] getScaleArray()
    {
    	return scale;
    }
    
    /**
	 * @return pole waveletov�ch koeficient�.
	 */
    public double[] getWaveletArray()
    {
    	return wavelet;
    }
    
    /**
	 * @return pole �k�lov�ch koeficient� pro inverzn� dwt.
	 */
    public double[] getIScaleArray()
    {
    	return iScale;
    }
    
    /**
	 * @return pole wavelet�ch koeficient� pro inverzn� dwt.
	 */
    public double[] getIWaveletArray()
    {
    	return iWavelet;
    }
    
    /**
	 * @return �k�lov� koeficient na indexu i.
	 */
    public double getScaleCoef(int i)
    {
    	return scale[i];
    }
    
    /**
	 * @return waveletov� koeficient na indexu i.
	 */
    public double getWaveletCoef(int i)
    {
    	return wavelet[i];
    }
    
    /**
	 * @return �k�lov� koeficient pro inverzn� dwt na indexu i.
	 */
    public double getIScaleCoef(int i)
    {
    	return iScale[i];
    }
    
    /**
	 * @return waveletov� koeficient pro inverzn� dwt na indexu i.
	 */
    public double getIWaveletCoef(int i)
    {
    	return iWavelet[i];
    }    
    
    /**
     * @return n�zev waveletu.
    */
    public String getName()
    {
        return name;
    }
}
