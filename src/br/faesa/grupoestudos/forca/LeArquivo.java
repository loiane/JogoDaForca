package br.faesa.grupoestudos.forca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LeArquivo {
	
	private Scanner input;
	
	//método para abrir o arquivo
	//parâmetro - nome do arquivo a ser lido
	public void openFile (String arquivo) throws FileNotFoundException {
		try {
			input = new Scanner (new File ("arquivos\\" + arquivo));
		}
		catch (FileNotFoundException erro){
			throw new FileNotFoundException ("ARQUIVO NÃO ENCONTRADO");
		}
	}
	
	//método que lê o arquivo e armazena as informções no registro
	public VetorPalavraImagem readFile () throws Exception {
		PalavraImagem reg; 
		VetorPalavraImagem vetor;
		String linha;
		int tam;
		try {
			linha = input.nextLine();
			tam = Integer.parseInt(linha);
			vetor = new VetorPalavraImagem (tam);
			while (input.hasNext()) {
				linha = input.nextLine();
				try{
					reg = new PalavraImagem(linha);
				}
				catch (Exception erro){
					throw new Exception ("LINHA DO ARQUIVO NÃO CONTÉM TODOS OS COMPONENTES");
				}
				vetor.insereVetor(reg);
			}
			input.close();
			return vetor;
		}
		catch (NoSuchElementException erro){
			throw new NoSuchElementException ("ARQUIVO VAZIO");
		}
		catch (IllegalStateException erro){
			throw new IllegalStateException ("ERRO AO LER O ARQUIVO");
		}
		catch (ArrayIndexOutOfBoundsException erro){
			throw new ArrayIndexOutOfBoundsException ("VETOR MENOR QUE NÚMERO DE ELEMENTOS DO ARQUIVO");
		}
		
	}
	
	//método para fechar o arquivo
	public void closeFile () {
		if (input != null) {
			input.close ();
		}
	}
}
