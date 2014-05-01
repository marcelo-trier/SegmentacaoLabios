package br.labios.pseudohue;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import br.labios.util.Histograma;
import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;
import br.labios.yiq.PixelYIQ;

public class PseudoHue extends PixelManager2<PixelPseudoHue> {

	//Histograma oHistograma = new Histograma( 100 );
	
	
	public PseudoHue( BufferedImage i) throws Exception {
		super( PixelPseudoHue.class, i, OsPixel.pixBrco, OsPixel.pixPreto );
		oHistograma = new Histograma( 100 );
		percorraTodosPixels();
	}

	public void execute() {
		for( PixelPseudoHue p : aLista ) {
			int vlr = ( int ) ( p.pseudoHue * 100 );
			oHistograma.soma( vlr );
		}
		int t = oHistograma.getOtsuThreshold();	
	}
}
