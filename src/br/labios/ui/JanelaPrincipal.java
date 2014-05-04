package br.labios.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

import br.labios.YCbCr.YCbCr;
import br.labios.i3.I3;
import br.labios.pseudohue.PseudoHue;
import br.labios.util.Algoritmos;
import br.labios.util.GerenteArquivos;
import br.labios.util.PixelManager2;
import br.labios.yiq.YIQ;

// vou usar as fotos de: http://fei.edu.br/~cet/facedatabase.html

public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	protected boolean imagemMesmaJanela = false;
	protected GerenteArquivos fileManager = new GerenteArquivos(this);

	public void clickFazTudo() throws Exception {
		BufferedImage imagem;
		File files[] = fileManager.carregaArquivos();
		for ( File f : files ) {
			imagem = ImageIO.read( f );
			for( Algoritmos alg : Algoritmos.values() ) {
				PixelManager2<?> obj = Algoritmos.novoAlgoritmo( alg, imagem );
				obj.execute();
			}
		}
	}

	public void clickAlgoritmo( Algoritmos alg ) throws Exception {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::" + alg.toString();
		PixelManager2<?> obj = Algoritmos.novoAlgoritmo( alg, getImage() );
		obj.execute();
		mostraImagem( msg, obj.geraImagem() );
	}
	
	public void clickI3() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::I3";
		try {
			I3 i3 = new I3(ti.getImage());
			i3.execute();
			BufferedImage out = i3.geraImagem();
			if (imagemMesmaJanela)
				ti.addNewImage(out);
			else
				mostraImagem(msg, out);
		} catch (Exception e) {
			// TODO:
		}
	}

	public void clickPseudoHue() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::PseudoHue";
		try {
			PseudoHue ph = new PseudoHue(ti.getImage());
			ph.execute();
			BufferedImage out = ph.geraImagem();
			if (imagemMesmaJanela)
				ti.addNewImage(out);
			else
				mostraImagem(msg, out);
		} catch (Exception e) {
			// TODO:
		}
	}

	public void clickYIQ() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::YIQ";
		try {
			YIQ yiq = new YIQ(ti.getImage());
			// yiq.init();
			yiq.execute();
			BufferedImage out = yiq.geraImagem();
			if (imagemMesmaJanela)
				ti.addNewImage(out);
			else
				mostraImagem(msg, out);
		} catch (Exception e) {
			// TODO::
		}
	}

	public void clickYCbCr() {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::YCbCr";
		try {
			YCbCr ycc = new YCbCr(ti.getImage());
			ycc.execute();
			BufferedImage out = ycc.geraImagem();
			if (imagemMesmaJanela)
				ti.addNewImage(out);
			else
				mostraImagem(msg, out);
		} catch (Exception e) {

		}
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

	public void clickProcessaTudo() {
		JInternalFrame[] todas = contentPane.getAllFrames();
		try {
			for (JInternalFrame tela : todas) {
				TelaInterna ti = (TelaInterna) tela;
				YCbCr ycc = new YCbCr(ti.getImage());
				ycc.execute();
				mostraImagem(ycc.geraImagem());
			}
		} catch (Exception e) {

		}
	}

	public void clickCarregaArquivos() throws Exception {
		BufferedImage imgs[] = fileManager.carregaImagens();

		for (BufferedImage imagem : imgs)
			mostraImagem(imagem);
	}

	public void clickSave() throws Exception {
		if (has2Image())
			fileManager.save(get2Image());
		else
			fileManager.save(getImage());
	}

	public void clickLoad() throws Exception {
		mostraImagem(fileManager.carregaImagem());
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
					clickLoad();
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

		JMenuItem mntmCarregaProcessa = new JMenuItem(
				"Carrega / Processa e Salva em Lote...");
		mntmCarregaProcessa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickFazTudo();
				} catch (Exception ex) {
					// TODO:
				}
			}
		});
		mnImagens.add(mntmCarregaProcessa);

		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);

		JMenuItem mntmYcbcr = new JMenuItem("YCbCr");
		mntmYcbcr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickAlgoritmo( Algoritmos.YCBCR );
				} catch( Exception ex ) {
					// TODO: 
				}
				//clickYCbCr();
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
				try {
					clickAlgoritmo( Algoritmos.YIQ );
				} catch( Exception ex ) {
					// TODO;
				}
				//clickYIQ();
			}
		});
		mnProcessamento.add(mntmYiqq);

		JMenuItem mntmPseudoHue = new JMenuItem("Pseudo Hue");
		mntmPseudoHue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickAlgoritmo( Algoritmos.PSEUDOHUE );
				} catch( Exception ex ) {
					// TODO:
				}
				//clickPseudoHue();
			}
		});
		mnProcessamento.add(mntmPseudoHue);

		JMenuItem mntmI = new JMenuItem("I3");
		mntmI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					clickAlgoritmo( Algoritmos.I3 );
				} catch( Exception ex ) {
					// TODO: 
				}
			}
		});
		mnProcessamento.add(mntmI);
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
