package br.labios.util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import br.labios.yiq.PixelYIQ;

public abstract class PixelManager2<T extends OsPixel> {
	
	protected ArrayList<T> aLista = new ArrayList<T>();
	protected Histograma oHistograma;

	protected BufferedImage _img;
	protected Class<? extends OsPixel> PixelClass;

	protected int pixBinary1[];
	protected int pixBinary2[];

	public PixelManager2(Class<? extends OsPixel> clazz, BufferedImage i, int pix1[], int pix2[] ) throws Exception {
		PixelClass = clazz;
		_img = i;
		pixBinary1 = pix1;
		pixBinary2 = pix2;
		
		oHistograma = new Histograma( getHistogramSize() );
		percorraTodosPixels();
	}

	public void percorraTodosPixels() throws Exception {
		int w = _img.getWidth();
		int h = _img.getHeight();
		Raster raster = _img.getData();
		int[] pix = new int[raster.getNumBands()];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pix = raster.getPixel(x, y, pix);
				T p = (T) PixelClass.newInstance();
				p.setLocation(x, y);
				// T p = ( T )PixelClass.getConstructor( OsPixel.class
				// ).newInstance( x, y );
				p.setRgb(pix);
				aLista.add(p);

				// processaPixel(pix, x, y);
			}
		}
	}

	public BufferedImage geraImagem() {
		return getImage(oHistograma.superT);
	}

	public BufferedImage getImage(int limiar) {
		BufferedImage outImg = new BufferedImage(_img.getWidth(),
				_img.getHeight(), _img.getType());
		WritableRaster outRaster = outImg.getRaster();
		Raster r = _img.getData();

		//final int[] pixPreto = { 0, 0, 0, 255 };
		//final int[] pixBrco = { 255, 255, 255, 255 };

		for (T p : aLista) {
			int umPixel[] = pixBinary1;
			if (p.compareTo(limiar) > 0)
				umPixel = pixBinary2;
			outRaster.setPixel(p.x, p.y, umPixel);
		}
		/*
		 * for( T p : aLista ) { int[] umPixel = pixBrco; if( p.maiorLimiar(
		 * limiar ) ) umPixel = pixPreto; outRaster.setPixel( p.x, p.y, umPixel
		 * ); }
		 */
		return outImg;
	}

	public abstract void execute();
	public abstract int getHistogramSize();
}
