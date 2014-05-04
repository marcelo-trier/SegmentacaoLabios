package br.labios.util;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class GerenteArquivos {

	JFileChooser fc = new JFileChooser();
	Component parent;
	
	public GerenteArquivos( Component umaJanela ) {
		parent = umaJanela;
	}
	
	public BufferedImage carregaImagem() throws IOException {
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(umDir);
		if ( fc.showOpenDialog( parent ) != JFileChooser.APPROVE_OPTION) {
			throw new IOException();
		}
		File file = fc.getSelectedFile();
		return ImageIO.read(file);
	}

	public File[] carregaArquivos() throws Exception  {
		//JFileChooser fileChooser = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setMultiSelectionEnabled(true);
		File umDir = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(umDir);
		if (fc.showOpenDialog( parent ) != JFileChooser.APPROVE_OPTION) {
			throw new IOException();
		}
		return fc.getSelectedFiles();
	}
	
	public BufferedImage[] carregaImagens() throws Exception {
		File files[] = carregaArquivos();

		BufferedImage imgs[] = new BufferedImage[ files.length  ];
		for( int i=0; i<files.length; i++ ) {
			imgs[i] = ImageIO.read( files[i] );
		}
		return imgs;
	}
	
	public void save( BufferedImage img ) throws Exception {
		File salvar = getSaveFile();
		ImageIO.write(img, "bmp", salvar);
	}

	public void save( BufferedImage imgs[] ) throws Exception {
		File salvar = getSaveFile();
		ImageIO.write( imgs[0], "bmp", salvar );

		String path = salvar.getAbsolutePath() + "-1.bmp";
		salvar = new File(path);
		ImageIO.write( imgs[1], "bmp", salvar );
	}
	
	public File getSaveFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showSaveDialog( parent ) != JFileChooser.APPROVE_OPTION) {
			throw new IOException();
		}
		return fileChooser.getSelectedFile();
	}
	
}
