package br.labios.yiq;

import java.awt.image.BufferedImage;

import br.labios.util.OsPixel;
import br.labios.util.PixelManager2;

public class YIQ extends PixelManager2<PixelYIQ> {

	public YIQ(BufferedImage i) throws Exception {
		super( PixelYIQ.class, i, OsPixel.pixBrco, OsPixel.pixPreto );
	}

	@Override
	public int getHistogramSize() {
		return 255;
	}

	/*
	public BufferedImage geraImagem( int limiar ) {
		//BufferedImage out = oHistograma.geraImagem();
		BufferedImage outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		WritableRaster outRaster = outImg.getRaster();
		Raster r = _img.getData();
		
		final int[] pixPreto = { 0, 0, 0, 255 };
		final int[] pixBrco = { 255, 255, 255, 255 };
		
		for( PixelYIQ py : aLista ) {
			int [] umPixel = pixPreto;
			if( py.yiq[ py.Q ] + 127 > limiar )
				umPixel = pixBrco;
			outRaster.setPixel( py.x, py.y, umPixel );
		}
		
		return outImg;
	}
	*/
	
	/*
	public void processaPixel(int[] pix, int x, int y) {
		PixelYIQ p = new PixelYIQ( x, y );
		p.setRgb( pix );
		aLista.add( p );
	}

	@Override
	public void init() throws Exception {
		percorraTodosPixels();
	}
	*/
	
	public void execute() {
		for( PixelYIQ p : aLista ) {
			int vlr = p.getHistogramValue();
			oHistograma.soma( vlr ); // aqui to somando 127 pq o Q tem a escala [-127, +127]
		}
		oHistograma.getOtsuThreshold();	
	}

}
