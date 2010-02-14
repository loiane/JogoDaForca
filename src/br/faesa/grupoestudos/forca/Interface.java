package br.faesa.grupoestudos.forca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

/**
 * @author Loiane Groner
 *
 * Esta classe desenvolve toda a interface gráfica do Jogo da Forca
 * bem como todos os eventos relacionados aos botões do jogo
 * 
 * Janeiro 2006
 */
public class Interface extends JFrame implements ActionListener, ItemListener {
	
	private JMenuBar barraMenu; //barra de menu
	private JMenu arquivo, nivel, opcoes; //itens principais da barra de menu
	private JMenuItem sobre, sair, solucao, proximo; //subitens dos menus arquivo e opções
	private JRadioButtonMenuItem niveis[] = new JRadioButtonMenuItem [4] ; //itens do menu níveis
	private ButtonGroup radioGroup; //grupo dos botoes de escolha
	private JButton vetbotao[] = new JButton [26]; //vetor dos botoes do alfabeto
	private JTextField segredo[]; //vetor das letras que correspondem ao segredo
	private ImageIcon vetletras[] = new ImageIcon [26]; //vetor das figuras dos botoes do alfabeto
	private ImageIcon enforcado; //imagem do enforcado
	private ImageIcon jogoDaForca; //imagem "Jogo da Forca"
	private ImageIcon imgSegredo; //imagem do segredo
	private JPanel painelInicio; //painel que será mostrado na tela de abertura do jogo
	private JPanel painelLetras; //painel onde ficarão os botões com as letras;
	private JPanel painelFigura; //painel onde vai ficar a figura do segredo
	private JPanel painelSegredo; //painel onde fica o vetor do segredo
	private JPanel painelCentral; //painel do vetor do segredo completo (com label)
	private JLabel titulo; //label que contém a imagem do titulo
	private JLabel msg1, msg2,msg3, msg4, msg5; // labels que contém as mensagens do painel inicial
	private JLabel labelEnforcado; //label que contém a figura do enforcado
	private JLabel labelSegredo; //label que contém a palavra "Descubra do segredo!"
	private JLabel atnivel; //label uqe vai mostar qual nivel está sendo jogado
	private JLabel labelfigura; //label que contém a figura do segredo
	private Jogo jogo; //referente ao jogo em si
	private int nivelAtual; //último nível escolhido pelo jogador
			
	/**
	 * Método construtor para inicializar e montar os painéis
	 * Monta a tela de entrada (até que o ususário realize uma ação)
	 */
	public Interface () {
		super ("Jogo da Forca");
		configuraJFrame ();
		criaMenu ();
		fazTitulo ();
		fazPainelBotoes ();
		this.painelLetras.setVisible(false);
		fazPainelEnforcado ();
		fazPainelInicial ();
		fazPainelCentral ();
		fazPainelFigura ("");
		this.painelCentral.setVisible(false);
		this.painelSegredo.setVisible(false);
		this.nivelAtual = 0; //inicializado para comparação caso o menu das opções seja ativado
	}
	
	//método que configura o JFrame
	private void configuraJFrame () {
		setSize (1000,700);
		setResizable (false);
		setLocation(10,10);
		getContentPane().setLayout(null);
	}
	
	//método que cria o menu
	private void criaMenu () {
		//itens do menu ARQUIVO
		this.sobre = new JMenuItem ("Sobre");
		this.sobre.addActionListener(this);
		this.sobre.setMnemonic('o');
		
		this.sair = new JMenuItem ("Sair");
		this.sair.addActionListener(this);
		this.sair.setMnemonic('r');
		
		//itens do menu OPÇÕES
		this.solucao = new JMenuItem ("Solução");
		this.solucao.addActionListener(this);
		this.solucao.setMnemonic('S');
		
		this.proximo = new JMenuItem ("Próximo");
		this.proximo.addActionListener(this);
		this.proximo.setMnemonic('P');
		
		//itens do Menu NÍVEis
		for (int i=0; i <= 3; i++) {
			this.niveis[i] = new JRadioButtonMenuItem ("Nível" + (i+1));
			this.niveis[i].addItemListener(this);
			this.niveis[i].setFocusable(false);
		}
		
		//grupo dos botoes dos menu níveis
		this.radioGroup = new ButtonGroup();
		this.radioGroup.add(this.niveis[0]);
		this.radioGroup.add(this.niveis[1]);
		this.radioGroup.add(this.niveis[2]);
		this.radioGroup.add(this.niveis[3]);
		
		//Itens do da barra de Menu
		this.arquivo = new JMenu ("Arquivo");
		this.arquivo.setMnemonic('A');
		this.arquivo.addActionListener(this);
		this.arquivo.add(this.sobre);
		this.arquivo.add(this.sair);
		
		this.nivel = new JMenu ("Níveis");
		this.nivel.setMnemonic('N');
		this.nivel.addActionListener(this);
		for (int i=0; i <= 3; i++) {
			this.nivel.add(this.niveis[i]);
		}
			
		this.opcoes = new JMenu ("Opções");
		this.opcoes.setMnemonic('O');
		this.opcoes.addActionListener(this);
		this.opcoes.add(this.solucao);
		this.opcoes.add(this.proximo);
		
		//Barra de Menu
		this.barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);
		this.barraMenu.add(this.arquivo);
		this.barraMenu.add(this.nivel);
		this.barraMenu.add(this.opcoes);
	}
	
	//método que faz o título
	private void fazTitulo () {
		this.jogoDaForca = new ImageIcon ("img\\jogodaforca.gif");
		this.titulo = new JLabel (jogoDaForca);
		this.titulo.setLocation(220,0);
		this.titulo.setSize(563,114);
		getContentPane().add(this.titulo);
	}
	
	//método que faz o painel do botões
	private void fazPainelBotoes () {
		this.painelLetras = new JPanel ();
		this.painelLetras.setSize (1000,200);
		this.painelLetras.setLocation(0,500);
		//this.painelLetras.setBackground(new Color (100,50,230));//teste
		this.painelLetras.setLayout (null);
		getContentPane().add(this.painelLetras);
		char letter = 'a';	
		for (byte i = 0; i < 26; i++) {
			letter = (char)(i + 'a');
			this.vetletras[i] = new ImageIcon ("img\\"+letter+".gif");
			this.vetbotao[i] = new JButton (vetletras[i]);
			//this.vetbotao[i].setBackground(Color.WHITE); //teste
			this.vetbotao[i].setFocusable(false);
			this.vetbotao[i].setRolloverEnabled(false);
			this.vetbotao[i].setSize(60,52);
			this.vetbotao[i].addActionListener(this);
			switch (i) {
			case 0: this.vetbotao[0].setLocation (45,10);
					break;
			case 1: this.vetbotao[1].setLocation (115,10);
					break;
			case 2: this.vetbotao[2].setLocation (185,10);
					break;
			case 3: this.vetbotao[3].setLocation (255,10);
					break;
			case 4: this.vetbotao[4].setLocation (325,10);
					break;
			case 5: this.vetbotao[5].setLocation (395,10);
					break;
			case 6: this.vetbotao[6].setLocation (465,10);
					break;
			case 7: this.vetbotao[7].setLocation (535,10);
					break;
			case 8: this.vetbotao[8].setLocation (605,10);
					break;
			case 9: this.vetbotao[9].setLocation (675,10);
					break;
			case 10: this.vetbotao[10].setLocation (745,10);
					break;
			case 11: this.vetbotao[11].setLocation (815,10);
					break;
			case 12: this.vetbotao[12].setLocation (885,10);
					break; 
			case 13: this.vetbotao[13].setLocation (45,70);
					break;
			case 14: this.vetbotao[14].setLocation (115,70);
					break;
			case 15: this.vetbotao[15].setLocation (185,70);
					break;
			case 16: this.vetbotao[16].setLocation (255,70);
					break;
			case 17: this.vetbotao[17].setLocation (325,70);
					break;
			case 18: this.vetbotao[18].setLocation (395,70);
					break;
			case 19: this.vetbotao[19].setLocation (465,70);
					break;
			case 20: this.vetbotao[20].setLocation (535,70);
					break;
			case 21: this.vetbotao[21].setLocation (605,70);
					break;
			case 22: this.vetbotao[22].setLocation (675,70);
					break;
			case 23: this.vetbotao[23].setLocation (745,70);
					break;
			case 24: this.vetbotao[24].setLocation (815,70);
					break;
			case 25: this.vetbotao[25].setLocation (885,70);	
					break;
			}			
			this.painelLetras.add(this.vetbotao[i]);
		}			
	}
	
	//método que habilita os painel dos botões para que um nvo nivel se inicie
	private void habilitaBotoes () {
		for (int i = 0; i < 26; i++) {
			this.vetbotao[i].setEnabled(true);
		}
	}
	
	//método que desabilita os botões caso a solução seja pedida pelo usuário
	private void desabilitaBotoes () {
		for (int i = 0; i < 26; i++) {
			this.vetbotao[i].setEnabled(false);
		}
	}
	
	//método que faz o Painel Inicial
	private void fazPainelInicial () {
		this.msg1 = new JLabel ("Olá!!!",JLabel.CENTER);
		this.msg1.setFont (new Font ("Comic Sans", Font.BOLD,40));
		this.msg2 = new JLabel ("Bem-vindo(a)!",JLabel.CENTER);
		this.msg2.setFont (new Font ("Comic Sans", Font.BOLD,40));
		this.msg3 = new JLabel ("Escolha no",JLabel.CENTER);
		this.msg3.setFont (new Font ("Comic Sans", Font.BOLD,40));
		this.msg4 = new JLabel ("menu um nível",JLabel.CENTER);
		this.msg4.setFont (new Font ("Comic Sans", Font.BOLD,40));
		this.msg5 = new JLabel ("para jogar!",JLabel.CENTER);
		this.msg5.setFont (new Font ("Comic Sans", Font.BOLD,40));
		this.painelInicio = new JPanel ();
		this.painelInicio.setSize(700,300);
		this.painelInicio.setLocation (280,200);
		//this.painelInicio.setBackground (new Color (150,0,250));//teste
		this.painelInicio.setLayout (new GridLayout (5,1));
		this.painelInicio.add(this.msg1);
		this.painelInicio.add(this.msg2);
		this.painelInicio.add(this.msg3);
		this.painelInicio.add(this.msg4);
		this.painelInicio.add(this.msg5);
		getContentPane().add(this.painelInicio);
	}
	
	//método que faz o painel da figura do enforcado
	private void fazPainelEnforcado () {
		this.enforcado = new ImageIcon ("img\\hmg5.gif");
		this.labelEnforcado = new JLabel (this.enforcado);
		this.labelEnforcado.setSize(206,248);
		this.labelEnforcado.setLocation(10,200);
		getContentPane().add(this.labelEnforcado);
	}
	
	//método que atualiza a figura do enforcado
	//int num = numero de erros para mostrar a imagem correta
	private void atualizaEnforcado (int num) {
		ImageIcon image  = new ImageIcon ("img\\hmg"+num+".gif");
		this.labelEnforcado.setIcon(image);
	}
	
	//método que faz o painel das caixinhas das letras do segredo
	private void fazPainelSegredo () {
		int i;
		this.segredo = new JTextField[20];
		this.painelSegredo = new JPanel();
		this.painelSegredo.setSize(480,50);
		this.painelSegredo.setLocation (260,260); //teste
		this.painelSegredo.setLayout(new FlowLayout(1));
		for (i = 0; i < 20; i++){
			this.segredo[i] = new JTextField(1);
			this.segredo[i].setFont(new Font ("Comic Sans", Font.BOLD, 16));
			this.segredo[i].setEditable(false);
			this.segredo[i].setBackground(Color.WHITE);
			this.segredo[i].setVisible(false);
			this.painelSegredo.add(this.segredo[i]);
		}
		getContentPane().add(this.painelSegredo);
	}
	
	//método que faz o painel Central completo
	private void fazPainelCentral () {
		this.painelCentral = new JPanel ();
		this.painelCentral.setSize (480,50);
		this.painelCentral.setLocation (260,200);
		this.painelCentral.setLayout (new GridLayout ());
		this.labelSegredo = new JLabel ("Descubra o segredo:",JLabel.CENTER);
		this.labelSegredo.setFont (new Font ("Comic Sans",Font.BOLD,26));
		this.painelCentral.add(this.labelSegredo);
		getContentPane().add(this.painelCentral);
		fazPainelSegredo ();
	}
	
	//método que faz o painel onde vai ficar a imagem do segredo
	private void fazPainelFigura (String img) {
		this.painelFigura = new JPanel ();
		this.painelFigura.setSize (230,300);
		this.painelFigura.setLocation(750,150);
		this.painelFigura.setLayout (new BorderLayout ());
		this.atnivel = new JLabel ("Nível: "+this.nivelAtual,JLabel.CENTER);
		this.atnivel.setFont (new Font ("Comic Sans",Font.BOLD,30));
		this.imgSegredo = new ImageIcon ("img\\"+img);
		this.labelfigura = new JLabel (this.imgSegredo,JLabel.CENTER);
		this.painelFigura.add(BorderLayout.CENTER,this.labelfigura);
		this.painelFigura.add(BorderLayout.SOUTH,this.atnivel);
		getContentPane().add(this.painelFigura);
	}
	
	//método que atualiza o painel da Figura
	private void atualizaPainelFigura (String img) {
		ImageIcon image  = new ImageIcon ("img\\"+img);
		this.labelfigura.setIcon(image);
		this.atnivel.setText("Nível "+this.nivelAtual);
	}
	
	//método que faz um novo nivel
	private void novoNivel (String seg, String img) {
		this.painelInicio.setVisible(false);
		atualizaEnforcado (0);
		atualizaPainelSegredo (seg);
		this.painelSegredo.setVisible(true);
		atualizaPainelFigura (img);
		this.painelFigura.setVisible(true);
		habilitaBotoes ();
		this.painelLetras.setVisible(true); //teste
		
	}
	
	
	//método que atualiza o painel dos segredo para que as letras possam aparecer assim que o usuário digitar
	public void atualizaPainelSegredo (String palavra){
		int tamanho = palavra.length();
		for (int i=0; i< 20; i++){
			if (i < tamanho){
				this.segredo[i].setVisible(true);
				if (Character.isLetter(palavra.charAt(i)))
					this.segredo[i].setText("");
				else
					if (palavra.charAt(i) == '-')
						this.segredo[i].setText("-");
			}
			else
				this.segredo[i].setVisible(false);
		}
	}
	
	//método que trata os eventos 
	public void actionPerformed(ActionEvent e) {
		eventoMenu (e);
		eventoBotaoLetras (e);
	}
	
	//método que trata dos eventos do menu
	public void eventoMenu (ActionEvent e) {
		if (e.getSource() == this.sobre){
			ImageIcon icon = new ImageIcon ("img//icone.gif");
			JOptionPane.showMessageDialog (null,"JOGO DA FORCA\nDesenvolvido por Loiane Groner\nVersão 1.5\nFevereiro/2006","Sobre",JOptionPane.INFORMATION_MESSAGE,icon);
		}
		if (e.getSource() == this.sair){
			System.exit(0);
		}
		if (e.getSource() == this.solucao){
			if (this.nivelAtual == 0) {
				JOptionPane.showMessageDialog (null,"Escolha um nível para jogar!","",JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog (null,"A SOLUÇÃO É "+this.jogo.getSegredo(),"Resposta",JOptionPane.INFORMATION_MESSAGE);
				desabilitaBotoes ();
			}
		}
		if (e.getSource() == this.proximo){
			if (this.nivelAtual == 0) {
				JOptionPane.showMessageDialog (null,"Escolha um nível para jogar!","",JOptionPane.WARNING_MESSAGE);
			}
			else {
				this.jogo.preparaJogo();
				novoNivel (this.jogo.getSegredo(),this.jogo.getImagem());
			}
		}
	}
	
	//método que trata os eventos dos botões das letras
	public void eventoBotaoLetras (ActionEvent e) {
		int quantErro;
		char letra;
		String secret;
		for (int i = 0; i < 26; i++) {
			if (e.getSource() == this.vetbotao[i]){
				this.vetbotao[i].setEnabled(false);
				letra = (char) ('A' + i);
				quantErro = this.jogo.getErros();
				this.jogo.acertou(letra);
				if (quantErro != this.jogo.getErros()) {
					quantErro++;
					atualizaEnforcado(quantErro);
				}
				secret = this.jogo.getPalavra();
				for (int aux = 0; aux < secret.length(); aux++){
					if (secret.charAt(aux)!= ' '){
						this.segredo[aux].setText(""+secret.charAt(aux));
					}
				}
				if (this.jogo.jogoAcabou()) {
					if (quantErro < 5){
						JOptionPane.showMessageDialog (null,"PARABÉNS! \nVocê adivinhou o segredo!","",JOptionPane.INFORMATION_MESSAGE);
						desabilitaBotoes ();
					}
					else {
						JOptionPane.showMessageDialog (null,"Tente de Novo! \nO Segredo era "+this.jogo.getSegredo(),"",JOptionPane.WARNING_MESSAGE);
						desabilitaBotoes ();
					}
				}
				break;
			}
		}
	}

	//método que trata dos eventos dos RadioButtons
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < 4; i++) {
			if (e.getSource() == this.niveis[i]){
				this.nivelAtual = i + 1;
				this.jogo = new Jogo (this.nivelAtual);
				novoNivel (this.jogo.getSegredo(),this.jogo.getImagem());
				break;
			}
		}
	}

}	