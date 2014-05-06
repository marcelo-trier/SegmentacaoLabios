package br.labios.testes;

import java.awt.image.BufferedImage;

import br.labios.pseudohue.PixelPseudoHue;
import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;

public class TesteManager extends PixelManager2<PixelTeste> {

	public TesteManager( BufferedImage i ) throws Exception {
		super( PixelTeste.class, i, OsPixel.pixPreto, OsPixel.pixBrco );
	}

	@Override
	public void execute() {
		for( PixelTeste p : aLista ) {
			int vlr = p.getHistogramValue();
			oHistograma.soma( vlr );
		}
		oHistograma.getOtsuThreshold();	
	}

	@Override
	public int getHistogramSize() {
		return 255;
	}

}
