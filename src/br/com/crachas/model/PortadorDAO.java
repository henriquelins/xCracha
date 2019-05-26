package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Portador;

public class PortadorDAO {

	public boolean novoPortador(Portador portador) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();
		
		boolean salvou = false;

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "INSERT INTO portador (nome_portador, identificacao_portador, id_os ) VALUES (?, ?, ?)";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, portador.getNomePortador());
			insereST.setString(++index, portador.getIdentificacaoPortador());
			insereST.setInt(++index, portador.getId_os());
			
			salvou = true;
			
			insereST.executeUpdate();		

			JOptionPane.showMessageDialog(null, "Portador salvo!");
			
			return salvou;
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o portador!" + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão! Mensagem: " + e2.getMessage());

			}

		}
		return salvou;
	}

}
