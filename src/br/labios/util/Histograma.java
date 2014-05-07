package br.labios.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Histograma {
	int [] histograma;
	int total = 0;
	int max;
	public int superT = 0;
	
	public Histograma() {
		histograma = new int[ 256 ];
	}
	
	public Histograma( int qtde ) {
		histograma = new int[ qtde ];
	}
	
	public Histograma( int[] h ) {
		histograma = h;
	}
	
	public void soma( int indice ) {
		if( indice < 0 )
			indice = 0;
		if( indice >= histograma.length )
			indice = histograma.length - 1;

		histograma[ indice ]++;
		int aa = histograma[ indice ];
		if( aa > max )
			max = histograma[ indice ];
		total++;
	}
	
	public int getOtsuThreshold() {
		float minVar = 999999;
		for( int t=1; t<histograma.length; t++ ) {
			float wb = 0, ub=0;
			float wf = 0, uf=0;
			int somaIntervalo = 0;
			for( int i=0; i<=t; i++ ) {
				wb += histograma[ i ];
				ub += i * histograma[ i ];
				somaIntervalo += histograma[ i ];
			}
			wb = wb / total;
			ub = ub / somaIntervalo;
			somaIntervalo = 0;
			for( int i=t+1; i<histograma.length; i++ ) {
				wf += histograma[ i ];
				uf += i * histograma[ i ];
				somaIntervalo += histograma[ i ];
			}
			wf = wf / total;
			uf = uf / somaIntervalo;
			
			float varb = 0;
			float varf = 0;
			float varInterno = 0;
			somaIntervalo = 0;
			for( int i=0; i<=t; i++ ) {
				varb += ( Math.pow( i - ub, 2 ) * histograma[ i ] );
				somaIntervalo += histograma[ i ];
			}
			varb = varb / somaIntervalo;
			somaIntervalo = 0;
			
			for( int i=t+1; i<histograma.length; i++ ) {
				varf += ( Math.pow( i-uf, 2 ) * histograma[ i ] );
				somaIntervalo += histograma[ i ];
			}
			varf = varf / somaIntervalo;
			
			varInterno = wb*varb + wf*varf;
			if( varInterno < minVar ) {
				superT = t;
				minVar = varInterno;
			}
		}
		superT++;
		return superT;
	}
	
	public BufferedImage geraImagem() {
		BufferedImage out = new BufferedImage( 256*2, 150, BufferedImage.TYPE_INT_RGB );
		//int[] pix = { 0, 0, 0, 255 };

	    Graphics2D g = out.createGraphics();
	    g.setColor( Color.WHITE );
	    g.fillRect( 0, 0, out.getWidth(), out.getHeight() );
	    g.setColor( Color.BLACK );
	    for( int i=0; i<256; i++ ) {
		    g.fillRect( i*2, 0, 2, histograma[ i ] );
	    }
	    g.dispose();
	    
		return out;
	}

	public void normalizeHistograma() {
		for( int i=0; i<histograma.length; i++ ) {
			float valor = histograma[ i ];
			valor = valor / max ;
			// valor *= 1000; //TODO: porque normalizar o histograma?? Acho q não precisa
			histograma[ i ] = ( int )valor;
		}
	}
	
	
}
