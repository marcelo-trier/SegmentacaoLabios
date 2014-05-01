package br.labios.i3;

import br.labios.util.OsPixel;

public class PixelI3 extends OsPixel {

	public float I3 = 0;

	@Override
	public void setRgb(int[] rgb) {
		float R = rgb[0];
		float G = rgb[1];
		float B = rgb[2];

		float calculo = (float) (2 * G - R - 0.5 * B);
		calculo = (float) (calculo * 0.25);
		I3 = calculo;
	}

	@Override
	public int compareTo(int limite) {
		if (I3 < limite)
			return -1;
		if (I3 > limite)
			return 1;
		return 0;
	}

}
