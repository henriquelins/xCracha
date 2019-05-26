package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Produto;

public class ProdutoDAO {

	public void adicionarProduto(Produto produto) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
			
		try {
			
			int index = 1;

			sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, produto.getNome());
			insereST.setString(++index, produto.getDescricao());
			
			insereST.executeUpdate();

			JOptionPane.showMessageDialog(null, "O produto foi inserido com sucesso!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o produto! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão! Mensagem: " + e2.getMessage());

			}

		}

	}

	public void editarProduto(Produto produto) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {
			
			int index = 1;
			
			sql = "UPDATE produto SET nome = ?, descricao = ? WHERE id_produto = ?  ";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, produto.getNome());
			insereST.setString(++index, produto.getDescricao());
			insereST.setInt(++index, produto.getId_produto());

			JOptionPane.showMessageDialog(null, "Produto editado com sucesso!");

			insereST.execute();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao editar o Produto. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}
		
		
		
		

	}

	public void excluirProduto(Produto produto) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "DELETE FROM produto WHERE id_produto = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, produto.getId_produto());
					
			insereST.execute();
			
			JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!" );

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro ao excluir o produto! Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

		
		

	}

	public List<Produto> listaProduto() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List <Produto> ListaProduto = new ArrayList <Produto> ();

		try {

			sql = "SELECT * FROM produto ORDER BY nome ASC";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Produto produto = new Produto();

				produto.setId_produto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				
				ListaProduto.add(produto);

			}

			return ListaProduto;

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

		return ListaProduto;


	}

}
