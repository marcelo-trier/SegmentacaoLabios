package br.labios.YCbCr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.labios.ui.JanelaPrincipal;
import br.labios.util.Histograma;
import br.labios.util.PixelManager;

public class YCbCr extends PixelManager {
	ArrayList<PixelYCbCr> aLista = new ArrayList<PixelYCbCr>();

	//int histograma[] = new int[ 256 ];
	Histograma oHistograma = new Histograma( 256 );
	int maxHist = 0;
//	PixelYCbCr matriz[][];
//	static final boolean normalize = true;
	
	public YCbCr(BufferedImage i) {
		super(i);
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
		
		int[] pixPreto = { 0, 0, 0, 255 };
		int[] pixBrco = { 255, 255, 255, 255 };
		
		for( PixelYCbCr py : aLista ) {
			int [] umPixel = pixPreto;
			if( py.ICinza > limiar )
				umPixel = pixBrco;
			outRaster.setPixel( py.x, py.y, umPixel );
		}
		
		return outImg;
	}

/*	
	public BufferedImage geraImagem() {
		BufferedImage outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		WritableRaster outRaster = outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( PixelKnn pk : todosPixels ) {
			if( pk.aClasse == null )
				umPixel[ 0 ] = umPixel[ 1 ] = umPixel[ 2 ] = 0;
			else {
				Color cor = Classe.Cores[ pk.aClasse.valor ];
				umPixel[ 0 ] = cor.getRed();
				umPixel[ 1 ] = cor.getGreen();
				umPixel[ 2 ] = cor.getBlue();
			}
			outRaster.setPixel( pk.x, pk.y, umPixel );
		}
		
		return outImg;
	}
*/	

	public void processaPixel(int[] pix, int x, int y) {
		PixelYCbCr p = new PixelYCbCr( x, y );
		p.setRgb( pix );
		aLista.add( p );
	}

	public void init() throws Exception {
		percorraTodosPixels();
	}

	public void execute() {
		//int histograma[] = new int[ 256 ];
		// normaliza Y Cb e Cr
		for( PixelYCbCr p : aLista ) {
			p.calculoI();
		}
		
		for( PixelYCbCr p : aLista ) {
			p.normalizeI();// --> TODO: normalizar o I?? --->>> acho q não
			p.calculoICinza();
			oHistograma.soma( p.ICinza );
		}
		
		//normalizeHistograma();
		// TODO: aqui vai o otsu
		int t = oHistograma.getOtsuThreshold();
		//JOptionPane.showMessageDialog( null , "t = " + t );
	}
}
