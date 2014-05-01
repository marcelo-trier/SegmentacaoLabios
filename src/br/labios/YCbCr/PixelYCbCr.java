package br.labios.YCbCr;

import br.labios.util.OsPixel;


/*
R = 1.164(Y - 16) + 1.596(Cr - 128)
G = 1.164(Y - 16) - 0.813(Cr - 128) - 0.391(Cb - 128)
B = 1.164(Y - 16) + 2.018(Cb - 128)
*/

/*
Y = (0.257 * R) + (0.504 * G) + (0.098 * B) + 16        
Cr = (0.439 * R) - (0.368 * G) - (0.071 * B) + 128      
Cb = -(0.148 * R) - (0.291 * G) + (0.439 * B) + 128  
*/

public class PixelYCbCr extends OsPixel {
	static final int Y=0, CB=1, CR=2;
	float rgb2ycbcr[][] = {
			{ 0.257f, 0.504f, 0.098f, 16f },
			{ 0.439f, -0.368f, -0.071f, 128 },
			{ -0.148f, -0.291f, 0.439f, 128 }
	};

	static final boolean USAR_YCBCR_NORMALIZADO_PARA_CALCULO_I = false;
	static final boolean NORMALIZAR_VALOR_I = true;

	// E as faixas foram para o canal Y [16, 236], para o canal Cr e Cb[16,241] 
//	static final int minYCbCr = 16;
//	static final int maxYCbCr[] = { 236, 241, 241 };
		
	static float maxI = 0f;
	static float minI = 999f;
	
	/*
		The resultant signals range from 16 to 235 for Y' (Cb and Cr range from 16 to 240); 
		the values from 0 to 15 are called footroom, while the values from 
		236 to 255 are called headroom.
	 */
	
	float ycbcr[] = { 0, 0, 0 };
	//float ycbcrNorm[] = { 0.0f, 0.0f, 0.0f };
	float I;
	float INorm = 0;
//	int ICinza = 0;
	
	public void setRgb( int[] rgb ) {
		for( int line=0; line<3; line++ )
		{
			float valor = 0;
			for( int canal=0; canal<3; canal++ ) { // canais == 4, pois coloquei o alfa = 1;
				valor += rgb2ycbcr[ line ][canal] * rgb[canal]; // calculo das matrizes
			}

			valor += rgb2ycbcr[ line ][ 3 ];
			ycbcr[ line ] = valor;
			//setValor( line, valor );
		}
		float tmp = 0;
		tmp = ycbcr[ CR ] / ycbcr[ CB ];
		I = tmp - ( float ) Math.pow( ycbcr[ CR ], 2 );
		
		if( I < minI )
			minI = I;
		if( I > maxI )
			maxI = I;
	}
/*
	public void calculoI() {
		float values[] = ycbcr;
		float tmp = 0;

		if( USAR_YCBCR_NORMALIZADO_PARA_CALCULO_I )
			values = ycbcrNorm;

		tmp = values[ CR ] / values[ CB ];
		I = tmp - ( float )Math.pow( values[ CR ], 2 );

		if( I < minI )
			minI = I;
		if( I >= maxI )
			maxI = I;
	}
*/
	public void normalizeI() {
		INorm = ( I - minI ) / ( maxI - minI );
		//I = ( I - minI ) / ( maxI - minI );
	}
	
	
//	public void calculoICinza() {	}

	
/*	public void setValor( int ch, float v ) {
		float norm = 0;
		int valor = ( int )v;
		ycbcr[ ch ] = valor;
		norm = valor - minYCbCr;
		norm /= ( maxYCbCr[ ch ] - minYCbCr );
		ycbcrNorm[ ch ] = norm; // jah tem um vetor normalizado...

	} */

	@Override
	public int getHistogramValue() {
		float valor = I;
		
		if( NORMALIZAR_VALOR_I )
			valor = INorm;

		int ICinza = 0;
		// TODO: o professor disse para mudar abaixo o valor para 255
		ICinza = ( int )( valor * 255 ); // niveis de cinza

		if( ICinza < 0 )
			ICinza = 0;
		if( ICinza > 255 )
			ICinza = 255;		
		return ( int ) ICinza;
	}
	
}
