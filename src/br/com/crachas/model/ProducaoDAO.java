package br.com.crachas.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.Planilha;
import br.com.crachas.controller.Producao;

public class ProducaoDAO {

	public int insereProducao(Producao producao, int id_planilha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;
		int id_producao = 0;

		try {

			int index = 1;

			sql = "insert into producao (empresa, tipo, operador, quantidade, dataEntrada, observacoes, id_planilha, os) values (?, ?, ?, ?, ?, ?, ?, ?) returning id_producao";

			java.sql.Date dataSql = new java.sql.Date(producao.getDataEntrada().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, producao.getEmpresa());
			insereST.setString(++index, producao.getTipo());
			insereST.setString(++index, producao.getOperador());
			insereST.setString(++index, producao.getQuantidade());
			insereST.setDate(++index, dataSql);
			insereST.setString(++index, producao.getObservacoes());
			insereST.setInt(++index, id_planilha);
			insereST.setInt(++index, producao.getOs());

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_producao = rs.getInt("id_producao");

			}

			return id_producao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao inserir Produção. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return id_producao;
	}

	public List<Producao> ListaProducao() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<Producao> listaProducao = new ArrayList<Producao>();

		try {

			sql = "select * from producao";
			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Producao producao = new Producao();

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));

				listaProducao.add(producao);

			}

			return listaProducao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

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

	public List<Producao> ListaProducaoData(int id_planilha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<Producao> listaProducao = new ArrayList<Producao>();

		try {

			//sql = "select * from producao where id_planilha = ? order by id_producao asc";
			sql = "select * from producao as pr inner join ordemservico as os on pr.os = os.id_os inner join planilha as pl on pr.id_planilha = pl.id_planilha where pr.id_planilha = ? order by id_producao asc";
			
						
			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, id_planilha);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Producao producao = new Producao();
				OrdemServico ordemservico = new OrdemServico();
				Planilha planilha = new Planilha();

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));
				producao.setOs(rs.getInt("os"));
				
				ordemservico.setId_os(rs.getInt("id_os"));
				ordemservico.setStatus(rs.getString("status"));
				ordemservico.setId_cliente(rs.getInt("id_cliente" ));
				
				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));
				planilha.setStatus(rs.getBoolean("status"));
				planilha.setTotal(rs.getString("total"));
				
				producao.setOrdemservico(ordemservico);
				producao.setPlanilha(planilha);

				listaProducao.add(producao);

			}

			return listaProducao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return listaProducao;
	}

	public List<Producao> listaDataEntrada(String campo, String informarPesquisa, Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<Producao> listaProducao = new ArrayList<Producao>();
		
		if (campo.equals("Cliente")){
			
			campo = "empresa";
		}

		try {

					
			sql = "select * from producao as pr inner join ordemservico as os on pr.os = os.id_os inner join planilha pl on pr.id_planilha = pl.id_planilha  inner join cliente as cl on os.id_cliente = cl.id_cliente where " + campo + " like ? and dataentrada between ? and ? order by empresa asc";
			
			insereST = conexao.prepareStatement(sql);
			insereST.setString(1,"%" +informarPesquisa + "%");
			insereST.setDate(2, dataInicial);
			insereST.setDate(3, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				Producao producao = new Producao();
				OrdemServico ordemservico = new OrdemServico();
				Planilha planilha = new Planilha();
				Cliente cliente = new Cliente();

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));
				producao.setOs(rs.getInt("os"));
				
				ordemservico.setId_os(rs.getInt("id_os"));
				ordemservico.setStatus(rs.getString("status"));
				ordemservico.setId_cliente(rs.getInt("id_cliente" ));
				
				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));
				planilha.setStatus(rs.getBoolean("status"));
				planilha.setTotal(rs.getString("total"));
				
				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setVendedor(rs.getString("vendedor"));
				
				producao.setOrdemservico(ordemservico);
				producao.setPlanilha(planilha);
				producao.setCliente(cliente);

				listaProducao.add(producao);

			}

			return listaProducao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

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

	public Producao pesquisarID(int id_producao) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		Producao producao = new Producao();
		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		try {

			sql = "select * from producao where id_producao = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, id_producao);

			rs = insereST.executeQuery();

			while (rs.next()) {

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));
				producao.setId_planilha(rs.getInt("id_planilha"));
				producao.setOs(rs.getInt("os"));

			}

			return producao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao localizar o pedido. Mensagem: 1" + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return producao;
	}

	public void atualizarProducao(Producao producao) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "update producao set empresa = ?, tipo = ?, operador = ?, quantidade = ?, dataentrada = ?, observacoes = ?, id_planilha = ?, os = ? where id_producao = ?";

			java.sql.Date dataSql = new java.sql.Date(producao.getDataEntrada().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, producao.getEmpresa());
			insereST.setString(++index, producao.getTipo());
			insereST.setString(++index, producao.getOperador());
			insereST.setString(++index, producao.getQuantidade());
			insereST.setDate(++index, dataSql);
			insereST.setString(++index, producao.getObservacoes());
			insereST.setInt(++index, producao.getId_planilha());
			insereST.setInt(++index, producao.getOs());
			insereST.setInt(++index, producao.getId_producao());

			insereST.executeUpdate();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro 1 ao atualizar o pedido. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

	}

	public void excluirProducao(Producao producao) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "DELETE FROM producao WHERE id_producao = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, producao.getId_producao());

			insereST.execute();

			JOptionPane.showMessageDialog(null, "Pedido excluido com sucesso!. Mensagem: ");

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro 1 ao excluir o pedido. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

	}

	public List<Producao> listaEntrada(Date date) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<Producao> listaProducao = new ArrayList<Producao>();

		try {

			//sql = "select * from producao as pr inner join planilha pl on pr.id_planilha = pl.id_planilha  where dataentrada = ? order by empresa asc";
			sql = "select * from producao as pr inner join ordemservico as os on pr.os = os.id_os inner join planilha pl on pr.id_planilha = pl.id_planilha  where pr.dataentrada = ? order by pr.empresa asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, date);

			rs = insereST.executeQuery();

			while (rs.next()) {

				Producao producao = new Producao();
				OrdemServico ordemservico = new OrdemServico();
				Planilha planilha = new Planilha();

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));
				producao.setOs(rs.getInt("os"));
				
				ordemservico.setId_os(rs.getInt("id_os"));
				ordemservico.setStatus(rs.getString("status"));

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));
				planilha.setStatus(rs.getBoolean("status"));
				planilha.setTotal(rs.getString("total"));
				
				producao.setOrdemservico(ordemservico);
				producao.setPlanilha(planilha);

				listaProducao.add(producao);

			}

			return listaProducao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

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

	public Producao pesquisarIDOS(int os) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		Producao producao = new Producao();
		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		try {

			sql = "select * from producao where os = ?";

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, os);

			rs = insereST.executeQuery();

			while (rs.next()) {

				producao.setId_producao(rs.getInt("id_producao"));
				producao.setEmpresa(rs.getString("empresa"));
				producao.setTipo(rs.getString("tipo"));
				producao.setOperador(rs.getString("operador"));
				producao.setQuantidade(rs.getString("quantidade"));
				producao.setDataEntrada(rs.getDate("dataentrada"));
				producao.setObservacoes(rs.getString("observacoes"));
				producao.setId_planilha(rs.getInt("id_planilha"));
				producao.setOs(rs.getInt("os"));

			}

			return producao;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao localizar o pedido. Mensagem: 1" + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return producao;
		
	}
}
