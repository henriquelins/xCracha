package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Operador;

public class OperadorDAO {

	public Operador login(String login, String senha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		Operador operador = new Operador();

		try {

			sql = "SELECT * FROM operador WHERE login = ? AND senha = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, login);
			insereST.setString(2, senha);
			ResultSet resultado = insereST.executeQuery();

			while (resultado.next()) {

				operador.setId_operador(resultado.getInt("id_operador"));
				operador.setNome(resultado.getString("nome"));
				operador.setAcesso(resultado.getString("acesso"));
				operador.setSetor(resultado.getString("setor"));
				operador.setLogin(resultado.getString("login"));
				operador.setSenha(resultado.getString("senha"));

			}

			return operador;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o Operador. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return operador;

	}

	public int adicionarOperador(Operador operador) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;

		int id_operador = -1;

		try {
			
			int index = 1;

			sql = "INSERT INTO operador (nome, acesso, setor,  login, senha) VALUES (?, ?, ?, ?, ?) returning id_operador ";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, operador.getNome());
			insereST.setString(++ index, operador.getAcesso());
			insereST.setString(++ index, operador.getSetor());
			insereST.setString(++ index, operador.getLogin());
			insereST.setString(++ index, operador.getSenha());

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_operador = rs.getInt("id_operador");

			}

			JOptionPane.showMessageDialog(null, "O operador foi inserido com sucesso!");

			return id_operador;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o Operador. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return id_operador;

	}

	public void excluirOperador(Operador operador) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "DELETE FROM operador WHERE id_operador = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, operador.getId_operador());

			insereST.execute();

			JOptionPane.showMessageDialog(null, "Operador excluído");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao excluir o Operador. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public int alterarDadosOperador(Operador operador) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;

		int id_operador = -1;

		try {
			
			int index = 1;

			sql = "UPDATE operador SET nome = ?,  acesso = ?, setor = ?, login = ?, senha = ?  WHERE id_usuario = ?  returning id_usuario";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, operador.getNome());
			insereST.setString(++ index, operador.getAcesso());
			insereST.setString(++ index, operador.getSetor());
			insereST.setString(++ index, operador.getLogin());
			insereST.setString(++ index, operador.getSenha());
			insereST.setInt(++ index, operador.getId_operador());

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_operador = rs.getInt("id_operador");

			}

			insereST.execute();

			return id_operador;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao alterar o Operador. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return id_operador;

	}

	public List<Operador> listaOperador() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<Operador> ListaOperador = new ArrayList<Operador>();

		try {

			sql = "SELECT * FROM operador ORDER BY login ASC";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Operador operador = new Operador();

				operador.setId_operador(rs.getInt("id_operador"));
				operador.setNome(rs.getString("nome"));
				operador.setAcesso(rs.getString("acesso"));
				operador.setSetor(rs.getString("setor"));
				operador.setLogin(rs.getString("login"));
				operador.setSenha(rs.getString("Senha"));

				ListaOperador.add(operador);

			}

			return ListaOperador;

		} catch (Exception e) {

			//JOptionPane.showMessageDialog(null, "Erro ao gerar a lista. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				//JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return ListaOperador;

	}

	public Operador pesquisarOperador(int id_operador) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		Operador operador = new Operador();

		try {

			sql = "SELECT * FROM operador";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				operador.setId_operador(rs.getInt("id_usuario"));
				operador.setNome(rs.getString("nome"));
				operador.setAcesso(rs.getString("acesso"));
				operador.setSetor(rs.getString("setor"));
				operador.setLogin(rs.getString("login"));

			}

			return operador;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a lista. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return operador;

	}

	public List<Operador> pesquisarOperadorMultiplosCampos(String campo, String pesquisa) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<Operador> ListaOperador = new ArrayList<Operador>();

		try {

			sql = "SELECT * FROM operador WHERE " + campo + " like ? ";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, pesquisa + "%");
			rs = insereST.executeQuery();

			while (rs.next()) {

				Operador operador = new Operador();

				operador.setId_operador(rs.getInt("id_operador"));
				operador.setNome(rs.getString("nome"));
				operador.setAcesso(rs.getString("acesso"));
				operador.setSetor(rs.getString("setor"));
				operador.setLogin(rs.getString("login"));

				ListaOperador.add(operador);

			}

			return ListaOperador;

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

		return ListaOperador;

	}

	public Operador buscarOperador (String nome) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		Operador operador = new Operador();

		try {

			sql = "SELECT * FROM operador WHERE nome = ? ";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, nome);
			rs = insereST.executeQuery();

			while (rs.next()) {

				

				operador.setId_operador(rs.getInt("id_operador"));
				operador.setNome(rs.getString("nome"));
				operador.setAcesso(rs.getString("acesso"));
				operador.setSetor(rs.getString("setor"));
				operador.setLogin(rs.getString("login"));
				operador.setSenha(rs.getString("senha"));


			}

			return operador;

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

		return operador;

	}

	public void editarOperador(Operador operador) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {
			
			int index = 1;
			
			sql = "UPDATE operador SET nome = ?, acesso = ?, setor = ?, login = ?, senha = ?  WHERE id_operador = ?  ";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, operador.getNome());
			insereST.setString(++ index, operador.getAcesso());
			insereST.setString(++ index, operador.getSetor());
			insereST.setString(++ index, operador.getLogin());
			insereST.setString(++ index, operador.getSenha());
			insereST.setInt(++ index, operador.getId_operador());

			JOptionPane.showMessageDialog(null, "Operador editado com sucesso!");

			insereST.execute();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao alterar o Operador. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}
	
	
	public List<Operador> pesquisarLoginOperador(String pesquisa) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		Operador operador = new Operador();
		List<Operador> ListaOperador = new ArrayList<Operador>();

		try {

			sql = "SELECT * FROM operador WHERE login like ? order by login asc";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, "%" + pesquisa + "%");
			rs = insereST.executeQuery();

			while (rs.next()) {

				

				operador.setId_operador(rs.getInt("id_operador"));
				operador.setNome(rs.getString("nome"));
				operador.setAcesso(rs.getString("acesso"));
				operador.setSetor(rs.getString("setor"));
				operador.setLogin(rs.getString("login"));

				ListaOperador.add(operador);

			}
			
			JOptionPane.showMessageDialog(null, ListaOperador.toString());
			
			return ListaOperador;

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

		return ListaOperador;

	}

}
