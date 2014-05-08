package br.labios.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	BufferedImage imagem = null;
	
	public ImagePanel( BufferedImage img ) {
		super();
		imagem = img;
		setAutoscrolls( true );
		setPreferredSize( new Dimension( img.getWidth(), img.getHeight() ) );
	}
	
    @Override
    protected void paintComponent(Graphics gr ) {
    	super.paintComponent( gr );
    	
    	Graphics2D g = ( Graphics2D )gr;

    	if( imagem != null )
    	{
    		g.drawImage( imagem, 0, 0, null );
    	}
    }

}
