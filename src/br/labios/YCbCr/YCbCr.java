package br.labios.YCbCr;

import java.awt.image.BufferedImage;

import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;

public class YCbCr extends PixelManager2<PixelYCbCr> {
	
	public YCbCr(BufferedImage i) throws Exception {
		super( PixelYCbCr.class, i , OsPixel.pixPreto, OsPixel.pixBrco );
	}

	public void execute() {
		// normaliza Y Cb e Cr
		/*for( PixelYCbCr p : aLista ) {
			p.calculoI();
		}*/
		
		for( PixelYCbCr p : aLista ) {
			p.normalizeI();// --> TODO: normalizar o I?? --->>> acho q não
			oHistograma.soma( p.getHistogramValue() );
		}
		
		// TODO: aqui vai o otsu
		int t = oHistograma.getOtsuThreshold();
	}

	@Override
	public int getHistogramSize() {
		return 256;
	}
}
