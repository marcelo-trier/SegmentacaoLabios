package br.labios.i3;

import br.labios.util.OsPixel;

public class PixelI3 extends OsPixel {

	public float I3 = 0;
	public static float min = 999;
	public static float max = -1;

	public float I3Norm = 0;
	
	@Override
	public void setRgb(int[] rgb) {
		float R = rgb[0];
		float G = rgb[1];
		float B = rgb[2];

		float calculo = (float) (2 * G - R - 0.5 * B);
		calculo = (float) (calculo * 0.25);
		I3 = calculo;
		
		
		if( I3 < min )
			min = I3;
		if( I3 > max )
			max = I3;
	}

	public void normalize() {
		I3Norm = I3 - min;
		I3Norm = I3Norm / (max - min);
	}
	
	@Override
	public int getHistogramValue() {
		return (int) (I3Norm * 100);
	}

}
