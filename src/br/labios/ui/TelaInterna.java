package br.labios.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;


public class TelaInterna extends JInternalFrame {
	static int contadorJanela = 0;
	public int id = 0;
	private ImagePanel panel;
	public BufferedImage imagem = null;
	public BufferedImage img2 = null;
	int cantoX = 0, cantoY = 0;

	public void addNewImage( BufferedImage img ) {
		setBounds(cantoX, cantoY, img.getWidth()*2 + 20, img.getHeight() + 50 );
		ImagePanel p = new ImagePanel( img );
		p.setPreferredSize( new Dimension(img.getWidth(), img.getHeight() ) );
		getContentPane().add( p, BorderLayout.CENTER );
		img2 = img;
		panel.invalidate();
		this.invalidate();
		panel.repaint();
		this.repaint();
	}

	public boolean has2Image() {
		return ( img2 != null );
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public TelaInterna(BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( "", img);
	}

	public void initTela( String titulo, BufferedImage img ) {
		String oTitulo = "[JanelaId: " + contadorJanela + "] :: ";
		oTitulo += titulo;
		this.setTitle( oTitulo );
		id = contadorJanela;
		cantoX = 50 * id;
		cantoY = 50 * id;
		if( cantoY > 300 ) {
			cantoY %= 300;
		}

		
		setBounds(cantoX, cantoY, img.getWidth() + 20, img.getHeight() + 50 );
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		imagem = img;
		panel = new ImagePanel( img );
		panel.setPreferredSize( new Dimension(img.getWidth(), img.getHeight() ) );
		getContentPane().add(panel, BorderLayout.WEST);
		contadorJanela++;
	}
	
	public TelaInterna( String titulo, BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( titulo, img);
	}
	
	public BufferedImage getImage() {
		return imagem;
	}
	
	public BufferedImage[] get2Image() {
		BufferedImage[] bi = new BufferedImage[ 2 ];
		bi[ 0 ] = imagem;
		bi[ 1 ] = img2;
		return bi;
	}
	
}
