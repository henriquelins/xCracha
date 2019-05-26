package br.com.crachas.controller;

import java.net.ServerSocket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.crachas.model.ConectarBanco;
import br.com.crachas.view.TelaLogin;

public class IniciarAplicativoCrachas {

	public static Email email;
	private static ServerSocket s;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		try {

			// impede que seja criada uma nova instância do programa
			setS(new ServerSocket(10001));
		

			boolean sucesso = ConectarBanco.TesteConexão();

			if (sucesso) {

				IniciarAplicativoCrachas iniaciarAplicativo = new IniciarAplicativoCrachas();
				iniaciarAplicativo.Iniciar();
				
				EmailRN emailRN = new EmailRN();
				email = emailRN.buscarEmail();

			} else {

				JOptionPane.showMessageDialog(null, "Não foi possível abrir o banco de dados,\n   configure uma nova conexão!", "Abrir Programa",
						JOptionPane.INFORMATION_MESSAGE);

				IniciarAplicativoCrachas iniaciarAplicativo = new IniciarAplicativoCrachas();
				iniaciarAplicativo.Iniciar();

			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Já existe outra instância do aplicativo executando!", "Abrir Programa",
					JOptionPane.INFORMATION_MESSAGE);

		}

	}

	public void Iniciar() {

		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaLogin telaLogin = new TelaLogin();
			telaLogin.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			telaLogin.setVisible(true);
			telaLogin.setLocationRelativeTo(null);
	
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static ServerSocket getS() {
		return s;
	}

	public static void setS(ServerSocket s) {
		IniciarAplicativoCrachas.s = s;
	}
}
