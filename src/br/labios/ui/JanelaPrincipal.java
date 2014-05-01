package br.labios.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

import br.labios.YCbCr.YCbCr;
import br.labios.i3.I3;
import br.labios.pseudohue.PseudoHue;
import br.labios.yiq.YIQ;

// vou usar as fotos de: http://fei.edu.br/~cet/facedatabase.html

public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;

	public void clickI3() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		try {
			I3 i3 = new I3( ti.getImage() );
			i3.execute();
			BufferedImage out = i3.geraImagem();
			ti.addNewImage(out);
		} catch (Exception e) {
			int aa = 0;
			aa++;
		}
	}
	
	
	public void clickPseudoHue() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		try {
			PseudoHue ph = new PseudoHue( ti.getImage() );
			ph.execute();
			BufferedImage out = ph.geraImagem();
			ti.addNewImage(out);
		} catch (Exception e) {
			int aa = 0;
			aa++;
		}
	}
	
	public void clickYIQ() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		try {
			YIQ yiq = new YIQ(ti.getImage());
			//yiq.init();
			yiq.execute();
			BufferedImage out = yiq.geraImagem();
			ti.addNewImage(out);
		} catch (Exception e) {
			// TODO::
		}
		/*
		 * YCbCr ycc = new YCbCr( ti.getImage() ); ycc.init(); ycc.execute();
		 * int limiar = ycc.getLimiar(); BufferedImage out = ycc.geraImagem(
		 * limiar ); ti.addNewImage( out );
		 */
	}

	public void clickProcessaTudo() {
		// TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		JInternalFrame[] todas = contentPane.getAllFrames();
		try {
			for (JInternalFrame tela : todas) {
				TelaInterna ti = (TelaInterna) tela;
				YCbCr ycc = new YCbCr(ti.getImage());
				ycc.execute();
				BufferedImage out = ycc.geraImagem();
				ti.addNewImage(out);
			}
		} catch (Exception e) {

		}
	}

	public void clickCarregaArquivos() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setMultiSelectionEnabled(true);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File[] files = fileChooser.getSelectedFiles();
		for (File f : files) {
			BufferedImage imagem = ImageIO.read(f);
			TelaInterna interno = new TelaInterna(imagem);
			contentPane.add(interno);
			interno.setVisible(true);
		}
	}

	public void clickYCbCr() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		// img = ti.getImage();
		try {
			YCbCr ycc = new YCbCr(ti.getImage());
			ycc.execute();
			BufferedImage out = ycc.geraImagem();
			ti.addNewImage(out);
		} catch (Exception e) {

		}
		// mostraImagem( out );

		/*
		 * limiar -= 50; for( int i=0; i<20; i++ ) { out = ycc.geraImagem(
		 * limiar ); mostraImagem( out ); limiar+= 5; }
		 */
	}

	public void mostraImagem(String titulo, BufferedImage imgOut) {
		TelaInterna interno = new TelaInterna(titulo, imgOut);
		contentPane.add(interno);
		interno.setVisible(true);
	}

	public void mostraImagem(BufferedImage imgOut) {
		mostraImagem("", imgOut);
	}

	public BufferedImage getImage() {
		// pega a janela ativa...
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		BufferedImage img;
		img = ti.getImage();
		return img;
	}

	public BufferedImage[] get2Image() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		BufferedImage[] bi = ti.get2Image();
		return bi;
	}

	public boolean has2Image() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		return ti.has2Image();
	}

	public void clickSave() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File salvar = fileChooser.getSelectedFile();

		if (!has2Image()) {
			BufferedImage img = getImage();
			ImageIO.write(img, "bmp", salvar);
			return;
		}
		BufferedImage[] img = get2Image();
		String path = salvar.getAbsolutePath() + "-1.bmp";
		// String nome = salvar.getName() + "-1.bmp";
		ImageIO.write(img[0], "bmp", salvar);
		File s2 = new File(path);
		ImageIO.write(img[1], "bmp", s2);
	}

	public void clickOnLoad() throws Exception {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		File umDir = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(umDir);
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		BufferedImage imagem = ImageIO.read(file);
		TelaInterna interno = new TelaInterna(imagem);
		contentPane.add(interno);
		interno.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 399);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Carrega Imagem...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickOnLoad();
				} catch (Exception ex) {

				}
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Salva Imagem...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickSave();
				} catch (Exception ex) {

				}
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmCarregaPastaCom = new JMenuItem(
				"Carrega Pasta com Imagens...");
		mntmCarregaPastaCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickCarregaArquivos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmCarregaPastaCom);

		JMenu mnImagens = new JMenu("Imagens");
		menuBar.add(mnImagens);

		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);

		JMenuItem mntmYcbcr = new JMenuItem("YCbCr");
		mntmYcbcr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickYCbCr();
			}
		});
		mnProcessamento.add(mntmYcbcr);

		JMenuItem mntmFechaTudo = new JMenuItem("YCbCr em Lote...");
		mntmFechaTudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickProcessaTudo();
			}
		});
		mnProcessamento.add(mntmFechaTudo);

		JMenuItem mntmYiqq = new JMenuItem("YIQ-Q");
		mntmYiqq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickYIQ();
			}
		});
		mnProcessamento.add(mntmYiqq);
		
		JMenuItem mntmPseudoHue = new JMenuItem("Pseudo Hue");
		mntmPseudoHue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickPseudoHue();
			}
		});
		mnProcessamento.add(mntmPseudoHue);
		
		JMenuItem mntmI = new JMenuItem("I3");
		mntmI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickI3();
			}
		});
		mnProcessamento.add(mntmI);
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
