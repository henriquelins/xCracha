package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import br.com.crachas.controller.ServidorEmail;

public class ServidorEmailDAO {

	public void adicionarEmail(ServidorEmail servidorEmail) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "INSERT INTO servidoremail (nomeServidor, hostName, smtpPort, authentication) VALUES (?, ?, ?, ?)";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, servidorEmail.getNomeServidor());
			insereST.setString(++index, servidorEmail.getHostName());
			insereST.setInt(++index, servidorEmail.getSmtpPort());
			insereST.setString(++index, servidorEmail.getAuthentication());

			insereST.executeUpdate();

			JOptionPane.showMessageDialog(null, "Servidor de email salvo com sucesso!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o servidor de email!" + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão! Mensagem: " + e2.getMessage());

			}

		}

	}

	public void editarServidorEmail(ServidorEmail servidorEmail) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "UPDATE servidoremail SET nomeServidor = ?, hostName = ?, smtpPort = ?, authentication = ? WHERE id_servidoremail = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, servidorEmail.getNomeServidor());
			insereST.setString(++index, servidorEmail.getHostName());
			insereST.setInt(++index, servidorEmail.getSmtpPort());
			insereST.setString(++index, servidorEmail.getAuthentication());
			insereST.setInt(++index, servidorEmail.getId_ServidorEmail());

			insereST.execute();
			
			JOptionPane.showMessageDialog(null, "Servidor de email salvo com sucesso!");
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao salvar o servidor de e-mail. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		

	}
	
	public ServidorEmail buscarServidorEmail() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		ServidorEmail servidorEmail = new ServidorEmail();

		try {

			sql = "SELECT * FROM servidoremail";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {
				
				servidorEmail.setId_ServidorEmail(rs.getInt("id_servidoremail"));
				servidorEmail.setNomeServidor(rs.getString("nomeservidor"));
				servidorEmail.setHostName(rs.getString("hostname"));
				servidorEmail.setSmtpPort(rs.getInt("smtpport"));
				servidorEmail.setAuthentication(rs.getString("authentication"));

			}

			return servidorEmail;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a lista. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return null;
	}
	


}
