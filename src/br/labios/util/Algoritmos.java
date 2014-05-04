package br.labios.util;

import java.awt.image.BufferedImage;

import br.labios.YCbCr.YCbCr;
import br.labios.pseudohue.PseudoHue;
import br.labios.yiq.YIQ;
import br.labios.i3.I3;

public enum Algoritmos {
	YCBCR, YIQ, PSEUDOHUE, I3;
	
	public static PixelManager2<?> novoAlgoritmo( Algoritmos alg, BufferedImage img ) throws Exception {
		PixelManager2<?> novoObj;
		switch( alg ) {
		case YCBCR:
			novoObj = new YCbCr( img );
			break;
		case YIQ:
			novoObj = new YIQ( img );
			break;
		case PSEUDOHUE:
			novoObj = new PseudoHue( img );
			break;
		case I3:
		default:
			novoObj = new I3( img );
			break;
		}
		return novoObj;
	}
}
