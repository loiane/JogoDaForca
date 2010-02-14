package br.faesa.grupoestudos.forca;

import java.util.Random;

public class VetorPalavraImagem {
	
	private int tamanho; //referente ao tamanho atual do arquivo
	private PalavraImagem vetor[]; //referente ao segredo e à figura que serão armazenados no vetor
	
	//método contrutor que cria um vetor de registro com o tamanho dado, mas com tamana=ho atual 0
	public VetorPalavraImagem (int t) {
		this.vetor = new PalavraImagem [t];
		this.tamanho = 0;
	}
	
	//método que insere um registro no vetor
	public void insereVetor(PalavraImagem registro){
		this.vetor[this.tamanho] = registro;
		this.tamanho++;
	}
	
	//método para sortear aleatoriamente um elemento no vetor
	public PalavraImagem sorteio () {
		Random posicao = new Random ();
		int ind = posicao.nextInt (this.tamanho);
		return this.vetor[ind];
	}
	
	//método que retorna o tamanho do vetor
	public int getTamanho () {
		return this.vetor.length;
	}
	
	//método que retorna uma determinada posição do vetor
	public PalavraImagem getIndVetor (int ind) {
		return this.vetor[ind];
	}
}
