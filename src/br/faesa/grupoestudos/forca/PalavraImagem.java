package br.faesa.grupoestudos.forca;

import java.util.StringTokenizer;

public class PalavraImagem {
	
		private String segredo; //referente ao segredo
		private String figura; //referente à figura do segredo
		
		//método construtor que recebe como parâmetro o segredo e o nome da figura separadamente
		public PalavraImagem (String s, String f) {
			this.segredo = s;
			this.figura = f;
		}
		
		//método construtor que recebe a linha inteira do arquivo e separa em segredo e figura
		public PalavraImagem (String linha) {
			StringTokenizer frase = new StringTokenizer (linha,";");
			this.segredo = frase.nextToken();
			this.figura = frase.nextToken();
		}
		
		//método construtor que inicializa os atributos com uma string vazia
		public PalavraImagem () {
			this.segredo = "";
			this.figura = "";
		}
		
		//getters
		public String getSegredo () {
			return this.segredo;
		}
		
		public String getFigura () {
			return this.figura;
		}
}
