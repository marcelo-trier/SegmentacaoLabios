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
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import br.labios.YCbCr.YCbCr;
import br.labios.util.Algoritmos;
import br.labios.util.GerenteArquivos;
import br.labios.util.PixelManager2;

// vou usar as fotos de: http://fei.edu.br/~cet/facedatabase.html

public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	protected boolean imagemMesmaJanela = false;
	protected GerenteArquivos fileManager = new GerenteArquivos(this);
	private static final boolean MOSTRAR_OTSU = true;

	long initTime = 0;
	
	public void tempoInicio() {
		initTime = System.currentTimeMillis();
	}

	public float tempoFim( boolean mostrar ) {
		long fim = System.currentTimeMillis();
		float tempo = fim - initTime;
		tempo = ( float )tempo / 1000f;
		if( mostrar ) {
			String msg = String.format("Tempo demora: %.02f seg", tempo );
			JOptionPane.showMessageDialog( this, msg );
		}
		return tempo;
	}
	
	public void clickFazTudo() throws Exception {
		BufferedImage imagem;
		File files[] = fileManager.carregaArquivos();
		File dirDestino = fileManager.selecionaDiretorio();
		tempoInicio();
		for ( File f : files ) {
			imagem = ImageIO.read( f );
			for( Algoritmos alg : Algoritmos.values() ) {
				PixelManager2<?> obj = Algoritmos.novoAlgoritmo( alg, imagem );
				obj.execute();
				fileManager.saveAlgoritmo( obj.geraImagem(), f, dirDestino, alg );
			}
		}
		tempoFim( true );
	}

	public void clickAlgoritmo( Algoritmos alg ) throws Exception {
		TelaInterna ti = (TelaInterna) contentPane.getSelectedFrame();
		String msg = "From[ " + ti.id + " ] Algoritmo::" + alg.toString();
		PixelManager2<?> obj = Algoritmos.novoAlgoritmo( alg, getImage() );
		obj.execute();
		mostraImagem( msg, obj.geraImagem() );
		if( MOSTRAR_OTSU ) {
			int otsu = obj.oHistograma.getOtsuThreshold();
			String mensagem = "Otsu = " + otsu;
			JOptionPane.showMessageDialog( this, mensagem );
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

	public void clickProcessaTodosYCrCb() {
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
		
		JMenuItem mntmOtsu = new JMenuItem("Otsu");
		mntmOtsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickAlgoritmo( Algoritmos.TESTE_OTSU );
				} catch( Exception ex ) {
					
				}
			}
		});
		mnProcessamento.add(mntmOtsu);
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
