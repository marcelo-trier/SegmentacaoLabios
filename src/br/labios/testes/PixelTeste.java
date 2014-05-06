package br.labios.testes;

import br.labios.util.OsPixel;

public class PixelTeste extends OsPixel {

	int cor;
	
	@Override
	public void setRgb(int[] rgb) {
		// pegando só um canal de cor
		cor = rgb[ 0 ];
	}

	@Override
	public int getHistogramValue() {
		return cor;
	}

}
