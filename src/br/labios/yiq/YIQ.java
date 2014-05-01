package br.labios.yiq;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import br.labios.YCbCr.PixelYCbCr;
import br.labios.util.Histograma;
import br.labios.util.PixelManager;

public class YIQ extends PixelManager {

	ArrayList<PixelYIQ> aLista = new ArrayList<PixelYIQ>();

	//int histograma[] = new int[ 256 ];
	Histograma oHistograma = new Histograma( 254 );
	int maxHist = 0;
	
	
	public YIQ(BufferedImage i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	
	public int getLimiar() {
		return oHistograma.superT;
	}
	
	public BufferedImage geraImagem() {
		return geraImagem( oHistograma.superT );
	}
	
	
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
	
	@Override
	public void processaPixel(int[] pix, int x, int y) {
		PixelYIQ p = new PixelYIQ( x, y );
		p.setRgb( pix );
		aLista.add( p );
	}

	@Override
	public void init() throws Exception {
		percorraTodosPixels();
	}

	@Override
	public void execute() {
		for( PixelYIQ p : aLista ) {
			oHistograma.soma( p.yiq[ p.Q ] + 127 ); // aqui to somando 127 pq o Q tem a escala [-127, +127]
		}
		int t = oHistograma.getOtsuThreshold();	
	}

}
