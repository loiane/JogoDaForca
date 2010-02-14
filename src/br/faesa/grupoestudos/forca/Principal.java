package br.faesa.grupoestudos.forca;

import javax.swing.JFrame;

public class Principal  {
	public static void main(String[] args) {
		
		Interface Calculadora = new Interface();
		Calculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Calculadora.setVisible(true);
	}
}

