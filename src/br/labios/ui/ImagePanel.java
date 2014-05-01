package br.labios.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	BufferedImage imagem = null;
	
	public ImagePanel( BufferedImage img ) {
		super();
		imagem = img;
		//this.addMouseListener( this );
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
