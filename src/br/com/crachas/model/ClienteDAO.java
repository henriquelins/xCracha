package br.com.crachas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Cliente;

public class ClienteDAO {

	public void adicionarCliente(Cliente cliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "INSERT INTO cliente (nome, vendedor, contato, email, fone1, fone2, detalhes, entrega, unidade  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, cliente.getNome());
			insereST.setString(++index, cliente.getVendedor());
			insereST.setString(++index, cliente.getContato());
			insereST.setString(++index, cliente.getEmail());
			insereST.setString(++index, cliente.getFone1());
			insereST.setString(++index, cliente.getFone2());
			insereST.setString(++index, cliente.getDetalhes());
			insereST.setBoolean(++index, cliente.isEntrega());
			insereST.setString(++index, cliente.getUnidade());

			insereST.executeUpdate();

			JOptionPane.showMessageDialog(null, "O cliente foi inserido com sucesso!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir o cliente!" + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão! Mensagem: " + e2.getMessage());

			}

		}

	}

	public List<Cliente> listarClientes() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<Cliente> listaCliente = new ArrayList<Cliente>();

		try {

			sql = "SELECT * FROM cliente ORDER BY nome ASC";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Cliente cliente = new Cliente();

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));
				cliente.setUnidade(rs.getString("unidade"));

				listaCliente.add(cliente);

			}

			return listaCliente;

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

		return listaCliente;

	}

	public void editarcliente(Cliente cliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "UPDATE cliente SET nome = ?,  vendedor  = ?, contato = ?, email = ?, fone1 = ?, fone2 = ?, detalhes = ?, entrega = ?, unidade = ?  WHERE id_cliente = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, cliente.getNome());
			insereST.setString(++index, cliente.getVendedor());
			insereST.setString(++index, cliente.getContato());
			insereST.setString(++index, cliente.getEmail());
			insereST.setString(++index, cliente.getFone1());
			insereST.setString(++index, cliente.getFone2());
			insereST.setString(++index, cliente.getDetalhes());
			insereST.setBoolean(++index, cliente.isEntrega());
			insereST.setString(++index, cliente.getUnidade());
			insereST.setInt(++index, cliente.getId_cliente());

			insereST.executeUpdate();

			JOptionPane.showMessageDialog(null, "Cliente Alterado!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao alterar o cliente. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public Cliente selecionarCliente(int linha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		Cliente cliente = new Cliente();

		try {

			sql = "SELECT * FROM cliente";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));
				cliente.setUnidade(rs.getString("unidade"));

			}

			return cliente;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao selecionar o cliente. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return cliente;

	}

	public List<Cliente> pesquisarCliente(String pesquisarCliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<Cliente> listaCliente = new ArrayList<Cliente>();

		try {

			sql = "SELECT * FROM cliente WHERE nome like ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, "%" + pesquisarCliente + "%");

			rs = insereST.executeQuery();

			while (rs.next()) {

				Cliente cliente = new Cliente();

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));
				cliente.setUnidade(rs.getString("unidade"));

				listaCliente.add(cliente);

			}

			return listaCliente;

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

		return listaCliente;

	}

	public int clienteOS(String nomecliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		int id_cliente = 0;

		try {

			sql = "SELECT * FROM cliente WHERE nome = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, nomecliente);

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_cliente = rs.getInt("id_cliente");

			}

			return id_cliente;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Cliente não encontrado! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return id_cliente;

	}

	public Cliente mostrarCliente(int id_cliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		Cliente cliente = new Cliente();

		try {

			sql = "SELECT * FROM cliente WHERE id_cliente = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, id_cliente);

			rs = insereST.executeQuery();

			while (rs.next()) {

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));
				cliente.setUnidade(rs.getString("unidade"));

			}

			return cliente;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Cliente não encontrado! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return cliente;

	}

	public boolean pesquisaCombo(String cliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		boolean contem = false;
		String nome = "";

		try {

			sql = "SELECT * FROM cliente WHERE nome = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, cliente);

			rs = insereST.executeQuery();

			while (rs.next()) {

				nome = rs.getString("nome");

				if (!nome.equals(""))
					;
				{

					contem = true;
				}

			}

			return contem;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Cliente não encontrado! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return contem;

	}

	public String mostrarId_cliente(String nome) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		String id_cliente = null;

		try {

			sql = "SELECT * FROM cliente WHERE nome = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, nome);

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_cliente = String.valueOf((rs.getInt("id_cliente")));

			}

			return id_cliente;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Cliente não encontrado! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return id_cliente;

	}

	public void excluirCliente(int id_cliente) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "DELETE FROM cliente WHERE id_cliente = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, id_cliente);

			insereST.execute();
			insereST.close();
			
			JOptionPane.showMessageDialog(null, "O Cliente foi excluído do Banco de Dados!");

		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();

			JOptionPane.showMessageDialog(null, "O Cliente não foi excluído do  Banco de Dados! " + e);
		}

	}

}
