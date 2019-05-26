package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Email;

public class EmailDAO {

	public void adicionarEmail(Email email) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "INSERT INTO email (email, descricao, endereco_imagem) VALUES (?, ?, ?)";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, email.getEmail());
			insereST.setString(++index, email.getDescricao());
			insereST.setString(++index, email.getEndereco_imagem());

			insereST.executeUpdate();

			JOptionPane.showMessageDialog(null, "O email foi inserido com sucesso!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o email!" + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão! Mensagem: " + e2.getMessage());

			}

		}

	}

	public List<Email> listarEmailsCadastrados() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<Email> listaEmail = new ArrayList<Email>();

		try {

			sql = "SELECT * FROM email ORDER BY email ASC";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Email email = new Email();

				email.setId_email(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setDescricao(rs.getString("descricao"));
				email.setEndereco_imagem(rs.getString("endereco_imagem"));

				listaEmail.add(email);

			}

			return listaEmail;

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

		return listaEmail;

	}

	public Email buscarEmail() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		Email email = new Email();

		try {

			sql = "SELECT * FROM email";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				email.setId_email(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setDescricao(rs.getString("descricao"));
				email.setEndereco_imagem(rs.getString("endereco_imagem"));

			}

			return email;

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

		return email;
	}

	
	public Email buscarEmail2 (String encontrar_Email) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		Email email = new Email();

		try {

			sql = "SELECT * FROM email where email = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, encontrar_Email);
			rs = insereST.executeQuery();

			while (rs.next()) {

				email.setId_email(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setDescricao(rs.getString("descricao"));
				email.setEndereco_imagem(rs.getString("endereco_imagem"));

			}

			return email;

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

		return email;
	}

	public Email excluirEmail(String excluir_Email) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		Email email = new Email();

		try {

			sql = "DELETE FROM email WHERE email = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, excluir_Email);

			insereST.execute();

			JOptionPane.showMessageDialog(null, "E-mail excluido com sucesso!");

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro 1 ao excluir o e-mail. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

		return email;
	}

	public Email editarEmail(Email email) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "UPDATE email SET email = ?, descricao = ?, endereco_imagem = ? WHERE id_email = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, email.getEmail());
			insereST.setString(++index, email.getDescricao());
			insereST.setString(++index, email.getEndereco_imagem());
			insereST.setInt(++index, email.getId_email());
			
			insereST.execute();
			
			return email;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao Salvar o e-mail. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}
		return email;

	}

}