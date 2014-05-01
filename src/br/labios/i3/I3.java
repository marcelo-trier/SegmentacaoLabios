package br.labios.i3;

import java.awt.image.BufferedImage;

import br.labios.pseudohue.PixelPseudoHue;
import br.labios.util.Histograma;
import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;

public class I3 extends PixelManager2<PixelI3> {

	public I3( BufferedImage i) throws Exception {
		super(PixelI3.class, i, OsPixel.pixPreto, OsPixel.pixBrco );
	}

	@Override
	public void execute() {
		for( PixelI3 p : aLista ) {
			p.normalize();
//			int vlr = ( int ) ( p.I3Norm * 100 );
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
