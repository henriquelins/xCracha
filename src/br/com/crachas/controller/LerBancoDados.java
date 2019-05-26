package br.com.crachas.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class LerBancoDados {

	private String host = "192.168.0.186";
	private String porta = "5432";
	private String banco = "PRODUCAO";
	

	public LerBancoDados() {
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}
	
	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}


	public static void main(String args[]) throws FileNotFoundException {

		lerDadosBanco();

	}

	@Override
	public String toString() {
		return "LerBancoDados [host=" + host + ", porta=" + porta +" , banco=" + banco + "]";
	}

	public static LerBancoDados lerDadosBanco() throws FileNotFoundException {

		LerBancoDados lerBancoDados = new LerBancoDados();

		try {

			FileReader arquivo = new FileReader("C:/ControleCrachas/xb.txt");
			BufferedReader lerArquivo = new BufferedReader(arquivo);

			String linha = lerArquivo.readLine();

			while (linha != null) {

				String[] num = Pattern.compile(";").split(linha);
				String host = num[0];
				String porta = num[1];
				String banco = num[2];

				lerBancoDados.setHost(host);
				lerBancoDados.setPorta(porta);
				lerBancoDados.setBanco(banco);
				
				//System.out.println(host+" "+porta+ " " +banco);

				linha = lerArquivo.readLine();

			}

			arquivo.close();

		} catch (IOException e) {
						
			//JOptionPane.showMessageDialog(null, "Erro na abertura do arquivo!\n" + e , "Abrir Arquivo", JOptionPane.ERROR_MESSAGE);
						
			
		}

		

		return lerBancoDados;
	}

	public static void GravarDadosBanco(String host, String porta, String banco) throws IOException {
		
		File diretorio = new File("C:/ControleCrachas/");
		
		if (!diretorio.exists()) {
		   
			diretorio.mkdir();
			
		}
		
		File arquivo = new File("C:/ControleCrachas/xb.txt");
		
		if (!arquivo.exists()) {
			   
			arquivo.createNewFile();
			
		}
						
		OutputStreamWriter gravarArquivo = new OutputStreamWriter(new FileOutputStream(arquivo),
				StandardCharsets.ISO_8859_1);

		try {

			String linha = host + ";" + porta + ";" + banco;

			gravarArquivo.write(linha + "\n");

			gravarArquivo.close();

		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro "+e);
			
			gravarArquivo.close();

		}

	}

}
