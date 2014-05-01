package br.labios.pseudohue;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import br.labios.util.Histograma;
import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;
import br.labios.yiq.PixelYIQ;

public class PseudoHue extends PixelManager2<PixelPseudoHue> {

	public PseudoHue( BufferedImage i) throws Exception {
		super( PixelPseudoHue.class, i, OsPixel.pixBrco, OsPixel.pixPreto );
	}

	public void execute() {
		for( PixelPseudoHue p : aLista ) {
			int vlr = p.getHistogramValue();
			oHistograma.soma( vlr );
		}
		int t = oHistograma.getOtsuThreshold();	
	}

	@Override
	public int getHistogramSize() {
		return 100;
	}
}
