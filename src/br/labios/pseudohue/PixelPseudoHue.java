package br.labios.pseudohue;

import java.awt.Point;

import br.labios.util.OsPixel;

public class PixelPseudoHue extends OsPixel {

	float pseudoHue = 0;
	static float min = 999;
	static float max = -1;

	float normalizado = 0;

	@Override
	public void setRgb(int[] rgb) {
		float soma = rgb[1] + rgb[0];
		float tmp = rgb[0];
		tmp = tmp / soma;
		pseudoHue = tmp;

		if (pseudoHue < min)
			min = pseudoHue;
		if (pseudoHue > max)
			max = pseudoHue;
	}

	public void normalize() {
		normalizado = pseudoHue - min;
		normalizado = normalizado / (max - min);
	}

	public int compareTo(int limite) {
		int vlr = (int) (pseudoHue * 100);
		if (vlr < limite)
			return -1;
		if (vlr > limite)
			return 1;
		return 0;
	}

}
