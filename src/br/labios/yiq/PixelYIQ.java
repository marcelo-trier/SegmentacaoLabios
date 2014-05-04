package br.labios.yiq;

import br.labios.util.OsPixel;

// YIQ RANGE:
//	Y		->	[ 0, 255 ]
//	I e Q 	->	[ -127, +127 ]

public class PixelYIQ extends OsPixel {
	static final int Y=0, I=1, Q=2;
	float rgb2yiq[][] = {
			{ 0.299f, 0.587f, 0.114f },
			{ 0.596f, -0.275f, -0.321f },
			{ -0.212f, -0.523f, 0.311f }
	};

	int yiq[] = { 0, 0, 0 };
	float yiqNorm[] = { 0.0f, 0.0f, 0.0f };

	static final int minYIQ[] = { 0, -127, -127 };
	static final int maxYIQ[] = { 255, 127, 127 };

	
	public void setRgb( int[] rgb ) {
		for( int line=0; line<3; line++ )
		{
			float valor = 0;
			for( int canal=0; canal<3; canal++ ) { // canais == 4, pois coloquei o alfa = 1;
				valor += rgb2yiq[ line ][canal] * rgb[canal]; // calculo das matrizes
			}

			setValor( line, valor );
		}
	}
	
	public void setValor( int ch, float v ) {
		float norm = 0;
		int valor = ( int )v;
		yiq[ ch ] = valor;

		norm = valor - minYIQ[ ch ];
		norm = norm / ( maxYIQ[ ch ] - minYIQ[ ch ] );
		yiqNorm[ ch ] = norm; // jah tem um vetor normalizado...
	}

	@Override
	public int getHistogramValue() {
		return yiq[ Q ] + 127;
	}
	
}
