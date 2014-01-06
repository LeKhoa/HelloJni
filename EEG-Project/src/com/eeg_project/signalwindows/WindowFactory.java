package com.eeg_project.signalwindows;

/**
 * Tovarni trida poskytujici okna pro filtraci
 * 
 * @author Michal Nykl, 2008
 */
public class WindowFactory {

	/**
	 * V��et v�ech oken, kter� tato t��da poskytuje.
	 * 
	 * @author Martin �imek
	 * */
	public static enum WINDOWS {
		RECTANGULAR_WINDOW, HAMMING_WINDOW, HANN_WINDOW, HANNING_WINDOW, COSINE_WINDOW, LANCZOS_WINDOW, BARTLETT_WINDOW, TRIANGULAR_WINDOW, GAUSS_WINDOW, BARTLET_HANN_WINDOW, BLACKMAN_WINDOW, KAISER_WINDOW, NUTTALL_WINDOW, BLACKMAN_HARRIS_WINDOW, BLACKMAN_NUTTALL_WINDOW, FLAT_TOP_WINDOW, BOHMAN_WINDOW, TUKEY_WINDOW, PARZEN_WINDOW, NONE
	};

	/**
	 * P�et�en� metoda pro z�sk�� instance n�jak�ho okna na
	 * z�klad� vyjmenovan�ho typu WINDOWS a s voliteln�mi parametry pro
	 * okna, kter� jsou parametrizovn�.
	 * 
	 * Chcete-li nap��klad GaussWindow pro 100 prvk� mus�te z�rove�
	 * zadat rozptyl, kter� po�aduje. A metodu tedy zavol�te
	 * 
	 * <code> getFilterWindow(GAUSS_WINDOW, new Double(4.135)) </code>
	 * 
	 * @author Martin �imek
	 * */
	public Window getFilterWindow(WINDOWS windowType, Object... params) {
		switch (windowType) {
		case RECTANGULAR_WINDOW:
			return new RectangularWindow();

		case HAMMING_WINDOW:
			return new HammingWindow();

		case GAUSS_WINDOW:
			if (params.length == 1 && params[0] instanceof Double) {
				return new GaussWindow(((Double) params[0]).doubleValue());
			} else {
				return new GaussWindow();
			}

		case BLACKMAN_WINDOW:
			return new BlackmanWindow();

		case BOHMAN_WINDOW:
			return new BohmanWindow();

		case NONE:
		default:
			return null;
		}

		/* pro p��pad �e by nasala n�jak� chyba */
		// return null;

	}
}
