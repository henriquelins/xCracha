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
import br.com.crachas.controller.EntregarPedidos;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.PedidoFinalizado;
import br.com.crachas.controller.Planilha;
import br.com.crachas.controller.Portador;

public class OrdemServicoDAO {

	public int criarOrdemServico(OrdemServico ordemServico) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;
		int id_os = 0;

		try {

			int index = 1;

			sql = "insert into ordemservico (status, total, dtrecebimento, dtprevista, id_cliente, motivo) values (?, ?, ?, ?, ?, ?) returning id_os";

			java.sql.Date dataSql1 = new java.sql.Date(ordemServico.getDtRecebimento().getTime());
			java.sql.Date dataSql2 = new java.sql.Date(ordemServico.getDtPrevista().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, ordemServico.getStatus());
			insereST.setString(++index, ordemServico.getTotal());
			insereST.setDate(++index, dataSql1);
			insereST.setDate(++index, dataSql2);
			insereST.setInt(++index, ordemServico.getId_cliente());
			insereST.setString(++index, ordemServico.getMotivo());

			rs = insereST.executeQuery();

			while (rs.next()) {

				id_os = rs.getInt("id_os");

			}

			return id_os;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar a Ordem de Serviço. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}
		return id_os;

	}

	public void atualizarOrdemServico(OrdemServico ordemServico) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "update ordemservico set status = ?, total = ?, dtrecebimento = ?, dtprevista = ?, id_cliente = ?, total_final = ?, motivo = ? where id_os = ?";

			java.sql.Date dataSql1 = new java.sql.Date(ordemServico.getDtRecebimento().getTime());
			java.sql.Date dataSql2 = new java.sql.Date(ordemServico.getDtPrevista().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, ordemServico.getStatus());
			insereST.setString(++index, ordemServico.getTotal());
			insereST.setDate(++index, dataSql1);
			insereST.setDate(++index, dataSql2);
			insereST.setInt(++index, ordemServico.getId_cliente());
			insereST.setString(++index, ordemServico.getTotalFinal());
			insereST.setString(++index, ordemServico.getMotivo());
			insereST.setInt(++index, ordemServico.getId_os());

			insereST.execute();

			JOptionPane.showMessageDialog(null, "Pedido atualizado!");

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

	public List<OrdemServico> listarOrdemServico(String listarTodas) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		if (listarTodas.equals("TODOS OS ESTÁGIOS")) {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where not os.status = 'PEDIDO FINALIZADO' and not  os.status = 'ORDEM DE SERVIÇO CANCELADA' order by os.id_os asc";

			try {
				insereST = conexao.prepareStatement(sql);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		} else if (listarTodas.equals("IMPRESSÃO")) {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = 'IMPRESSÃO' OR os.status = 'AGUARDANDO O CLIENTE' order by os.id_os asc";

			try {
				insereST = conexao.prepareStatement(sql);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		} else {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = ? order by os.id_os asc";

			try {
				insereST = conexao.prepareStatement(sql);
				insereST.setString(1, listarTodas);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		try {

			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Planilha planilha = new Planilha();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));

				os.setCliente(cliente);
				os.setPlanilha(planilha);

				listaOS.add(os);

			}

			return listaOS;

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

		return listaOS;

	}

	public OrdemServico mostrarOS(int id_os) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		OrdemServico os = new OrdemServico();
		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		try {

			sql = "select * from ordemservico where id_os = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, id_os);

			rs = insereST.executeQuery();

			while (rs.next()) {

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

			}

			return os;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao localizar a ordem de serviço. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return os;
	}

	public List<OrdemServico> listarPesquisarOS(String pesquisaCampo) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where os.id_os = ? order by os.id_os asc";

			int pesquisaID = Integer.valueOf(pesquisaCampo);

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, pesquisaID);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Planilha planilha = new Planilha();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));

				os.setCliente(cliente);
				os.setPlanilha(planilha);

				listaOS.add(os);

			}

			if (listaOS.isEmpty() == true) {

				JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

			}

			return listaOS;

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

		return listaOS;

	}

	public void atualizarStatus(int id_os, String status) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "update ordemservico set status = ? where id_os = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, status);
			insereST.setInt(++index, id_os);

			insereST.execute();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro 1 ao atualizar o Status do pedido. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

	}

	public void atualizarTotal(int id_os, String total, int id_cliente, String observacoes) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "update ordemservico set total = ?, id_cliente = ?, motivo = ? where id_os = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, total);
			insereST.setInt(++index, id_cliente);
			insereST.setString(++index, observacoes);
			insereST.setInt(++index, id_os);

			insereST.execute();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro 1 ao atualizar o total do pedido. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

	}

	public List<OrdemServico> pesquisarOSCompleta(String informarPesquisa, String campo, Date dataInicial,
			Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			if (campo.equals("STATUS")) {

				campo = "os.status";

			} else if (campo.equals("CLIENTE")) {

				campo = "cl.nome";

			} else if (campo.equals("VENDEDOR")) {

				campo = "cl.vendedor";

			}

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where "
					+ campo + " like ? and os.dtrecebimento between ? and ? order by os.id_os asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, "%" + informarPesquisa + "%");
			insereST.setDate(2, dataInicial);
			insereST.setDate(3, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Planilha planilha = new Planilha();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));

				os.setCliente(cliente);
				os.setPlanilha(planilha);

				listaOS.add(os);

			}

			return listaOS;

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

	public void atualizarTotalFinal(int id_os, String totalFinal, String motivo) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "update ordemservico set total_final = ?,  motivo = ? where id_os = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(index, totalFinal);
			insereST.setString(++index, motivo);
			insereST.setInt(++index, id_os);

			insereST.execute();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null,
					"Erro 1 ao atualizar o total expedido do pedido. Mensagem: " + e.getMessage());

		} finally {
			try {

				insereST.close();
				conexao.close();

			} catch (Throwable e) {

				JOptionPane.showMessageDialog(null, "Erro 2 ao fechar a conexão. Mensagem: " + e.getMessage());

			}
		}

	}

	public List<OrdemServico> listarPesquisarDataEntrada(String pesquisaCampo, Date dataEntrada) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		if (pesquisaCampo.equals("TODOS OS ESTÁGIOS")) {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where not os.status = 'PEDIDO FINALIZADO' and not  (os.status = 'ORDEM DE SERVIÇO CANCELADA') and os.dtrecebimento = ? order by os.id_os asc";

			try {
				insereST = conexao.prepareStatement(sql);
				insereST.setDate(1, dataEntrada);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		} else {

			sql = "select * from ordemservico as os inner join producao pr on os.id_os = pr.os inner join planilha pl on pr.id_planilha = pl.id_planilha inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = ? and os.dtrecebimento = ? order by os.id_os asc";

			try {
				insereST = conexao.prepareStatement(sql);
				insereST.setString(1, pesquisaCampo);
				insereST.setDate(2, dataEntrada);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		try {

			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Planilha planilha = new Planilha();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataplanilha"));

				os.setCliente(cliente);
				os.setPlanilha(planilha);

				listaOS.add(os);

			}

			return listaOS;

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

		return listaOS;

	}

	public List<EntregarPedidos> listarPedidoRecepcao() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();
		String entrega;

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where estagio = '3' and status = 'RECEPÇÃO' order by nome";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.getOrdemServico().setId_os(rs.getInt("id_os"));
				recepcao.getCliente().setNome(rs.getString("nome"));
				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setObsevacoes(rs.getString("motivo"));
				recepcao.setEntrega(entrega);

				listaRecepcao.add(recepcao);

			}

			return listaRecepcao;

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

		return listaRecepcao;

	}

	public List<EntregarPedidos> listarPesquisarDataEntrada(Date dataEntrada) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();
		String entrega;

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where (data_inicio = ?) and ((status = 'RECEPÇÃO') and (estagio = '3')) order by nome";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataEntrada);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.getOrdemServico().setId_os(rs.getInt("id_os"));
				recepcao.getCliente().setNome(rs.getString("nome"));
				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setObsevacoes(rs.getString("motivo"));
				recepcao.setEntrega(entrega);

				listaRecepcao.add(recepcao);

			}

			return listaRecepcao;

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

		return listaRecepcao;

	}

	public List<EntregarPedidos> listarPedidoEntregar() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		String entrega = "";

		try {

			sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where estagio = '3' and status = 'ENTREGAR' order by cliente.nome";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos entregarPedidos = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				entregarPedidos.setDtExpedicao(rs.getDate("data_inicio"));
				entregarPedidos.setTotalFinal(rs.getString("total_final"));
				entregarPedidos.setEntrega(entrega);
				entregarPedidos.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));

				entregarPedidos.setCliente(cliente);
				entregarPedidos.setOrdemServico(ordemServico);

				listaRecepcao.add(entregarPedidos);

			}

			insereST.close();
			rs.close();
			conexao.close();

			if (listaRecepcao.isEmpty()) {

				sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where estagio = '4' and status = 'ENTREGAR' order by cliente.nome";

				insereST = conexao.prepareStatement(sql);
				rs = insereST.executeQuery();
				while (rs.next()) {

					EntregarPedidos recepcao = new EntregarPedidos();
					Cliente cliente = new Cliente();
					OrdemServico ordemServico = new OrdemServico();

					if (rs.getBoolean("entrega") == true) {

						entrega = "Sim";

					} else {

						entrega = "Não";

					}

					recepcao.setDtExpedicao(rs.getDate("data_inicio"));
					recepcao.setTotalFinal(rs.getString("total_final"));
					recepcao.setEntrega(entrega);
					recepcao.setObsevacoes(rs.getString("motivo"));

					cliente.setNome(rs.getString("nome"));
					cliente.setVendedor(rs.getString("vendedor"));

					ordemServico.setId_os(rs.getInt("id_os"));
					ordemServico.setDtPrevista(rs.getDate("dtprevista"));

					recepcao.setCliente(cliente);
					recepcao.setOrdemServico(ordemServico);

					listaRecepcao.add(recepcao);

				}

			}

			return listaRecepcao;

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

		return listaRecepcao;

	}

	public List<EntregarPedidos> listarPedidoEntregarData(Date dataRecepcao) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		String entrega = "";

		try {

			sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where (data_inicio = ?) and ((status = 'ENTREGAR') and (estagio = '4')) or (data_inicio = ?) and ((status = 'ENTREGAR') and (estagio = '3')) order by nome";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataRecepcao);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setEntrega(entrega);
				recepcao.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));

				recepcao.setCliente(cliente);
				recepcao.setOrdemServico(ordemServico);

				listaRecepcao.add(recepcao);

				listaRecepcao.add(recepcao);

			}

			return listaRecepcao;

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

		return listaRecepcao;

	}

	public List<PedidoFinalizado> listarPedidosFinalizados() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, portador.id_portador, portador.nome_portador, portador.identificacao_portador, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os inner join portador on ordemservico.id_os = portador.id_os where estagio = '5' and status = 'PEDIDO FINALIZADO' order by nome asc";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setCliente(cliente);

				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			if (listaPedidosFinalizados.isEmpty() == true) {

				JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

			}

			return listaPedidosFinalizados;

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

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosData(Date dataPedidosFinalizados) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, portador.id_portador, portador.nome_portador, portador.identificacao_portador, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os inner join portador on ordemservico.id_os = portador.id_os where (data_inicio = ?) and ((status = 'PEDIDO FINALIZADO') and (estagio = '5')) order by nome";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataPedidosFinalizados);
			rs = insereST.executeQuery();

			while (rs.next()) {

				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setCliente(cliente);

				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

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

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodo(Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, portador.id_portador, portador.nome_portador, portador.identificacao_portador, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os inner join portador on ordemservico.id_os = portador.id_os where ((data_inicio between ? and ? )) and ((status = 'PEDIDO FINALIZADO') and (estagio = '5')) order by nome";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataInicial);
			insereST.setDate(2, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setCliente(cliente);

				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

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

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodoENome(String nome, Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select ordemservico.id_os, cliente.nome, estadoos.data_inicio, ordemservico.total_final, ordemservico.motivo, portador.id_portador, portador.nome_portador, portador.identificacao_portador, cliente.entrega from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os inner join portador on ordemservico.id_os = portador.id_os where cliente.nome like ? and (data_inicio between ? and ?) and ((status = 'PEDIDO FINALIZADO') and (estagio = '5')) order by nome";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, "%" + nome + "%");
			insereST.setDate(2, dataInicial);
			insereST.setDate(3, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setCliente(cliente);

				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

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

		return listaPedidosFinalizados;

	}

	public List<EntregarPedidos> listarEntregarPeriodo(Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listarEntregar = new ArrayList<EntregarPedidos>();

		String entrega = "";

		try {

			sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where ((data_inicio between ? and ?)) and ((status = 'ENTREGAR') and (estagio = '3')) order by cliente.nome";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataInicial);
			insereST.setDate(2, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setEntrega(entrega);
				recepcao.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));

				recepcao.setCliente(cliente);
				recepcao.setOrdemServico(ordemServico);

				listarEntregar.add(recepcao);

			}

			insereST.close();
			rs.close();
			conexao.close();

			if (listarEntregar.isEmpty() == true) {

				sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where ((data_inicio between ? and ?)) and ((status = 'ENTREGAR') and (estagio = '4')) order by cliente.nome";

				insereST = conexao.prepareStatement(sql);
				insereST.setDate(1, dataInicial);
				insereST.setDate(2, dataFinal);
				rs = insereST.executeQuery();

				while (rs.next()) {

					EntregarPedidos recepcao = new EntregarPedidos();
					Cliente cliente = new Cliente();
					OrdemServico ordemServico = new OrdemServico();

					if (rs.getBoolean("entrega") == true) {

						entrega = "Sim";

					} else {

						entrega = "Não";

					}

					recepcao.setDtExpedicao(rs.getDate("data_inicio"));
					recepcao.setTotalFinal(rs.getString("total_final"));
					recepcao.setEntrega(entrega);
					recepcao.setObsevacoes(rs.getString("motivo"));

					cliente.setNome(rs.getString("nome"));
					cliente.setVendedor(rs.getString("vendedor"));

					ordemServico.setId_os(rs.getInt("id_os"));
					ordemServico.setDtPrevista(rs.getDate("dtprevista"));

					recepcao.setCliente(cliente);
					recepcao.setOrdemServico(ordemServico);

					listarEntregar.add(recepcao);

				}

			}

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " +
			// e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				// JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: "
				// + e2.getMessage());

			}

		}

		return listarEntregar;

	}

	public List<EntregarPedidos> listarEntregarPeriodoENome(String nome, Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaEntregar = new ArrayList<EntregarPedidos>();

		String entrega = "";

		try {

			sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where cliente.nome like ? and ((data_inicio between ? and ?)) and ((status = 'ENTREGAR') and (estagio = '3')) order by cliente.nome";

			insereST = conexao.prepareStatement(sql);

			insereST.setString(1, "%" + nome + "%");
			insereST.setDate(2, dataInicial);
			insereST.setDate(3, dataFinal);

			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setEntrega(entrega);
				recepcao.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));

				recepcao.setCliente(cliente);
				recepcao.setOrdemServico(ordemServico);

				listaEntregar.add(recepcao);

			}

			insereST.close();
			rs.close();
			conexao.close();

			if (listaEntregar.isEmpty() == true) {

				sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where cliente.nome like ? and ((data_inicio between ? and ?)) and ((status = 'ENTREGAR') and (estagio = '4')) order by cliente.nome";

				insereST = conexao.prepareStatement(sql);

				insereST.setString(1, "%" + nome + "%");
				insereST.setDate(2, dataInicial);
				insereST.setDate(3, dataFinal);

				rs = insereST.executeQuery();

				while (rs.next()) {

					EntregarPedidos recepcao = new EntregarPedidos();
					Cliente cliente = new Cliente();
					OrdemServico ordemServico = new OrdemServico();

					if (rs.getBoolean("entrega") == true) {

						entrega = "Sim";

					} else {

						entrega = "Não";

					}

					recepcao.setDtExpedicao(rs.getDate("data_inicio"));
					recepcao.setTotalFinal(rs.getString("total_final"));
					recepcao.setEntrega(entrega);
					recepcao.setObsevacoes(rs.getString("motivo"));

					cliente.setNome(rs.getString("nome"));
					cliente.setVendedor(rs.getString("vendedor"));

					ordemServico.setId_os(rs.getInt("id_os"));
					ordemServico.setDtPrevista(rs.getDate("dtprevista"));

					recepcao.setCliente(cliente);
					recepcao.setOrdemServico(ordemServico);

					listaEntregar.add(recepcao);

				}

			}

			return listaEntregar;

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " +
			// e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				// JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: "
				// + e2.getMessage());

			}

		}

		return listaEntregar;

	}

	public List<OrdemServico> listarOrdemServico() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente  where status  = 'LAMINAÇÃO / EXPEDIÇÃO' order by id_os asc";

			insereST = conexao.prepareStatement(sql);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				listaOS.add(os);

			}

			return listaOS;

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

	public List<OrdemServico> listarPedidoLaminacaoData(Date dataEntrada) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente  where DtRecebimento = ? and status  = 'LAMINAÇÃO / EXPEDIÇÃO' order by id_os asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataEntrada);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));

				os.setCliente(cliente);

				listaOS.add(os);

			}

			return listaOS;

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

		return listaOS;

	}

	public List<OrdemServico> pesquisarNomeCliente(String nome) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where cl.nome like ? order by os.dtrecebimento asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, "%" + nome + "%");
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				if (!os.getStatus().equals("PEDIDO FINALIZADO")
						&& !os.getStatus().equals("ORDEM DE SERVIÇO CANCELADA")) {

					listaOS.add(os);
				}

			}

			return listaOS;

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

		return listaOS;

	}

	public List<OrdemServico> pesquisarPorData(Date dtInicial, Date dtFinal, String estagio) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			if (estagio.equals("IMPRESSÃO")) {

				sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = 'IMPRESSÃO' or os.status = 'AGUARDANDO O CLIENTE' order by cl.nome asc";

				insereST = conexao.prepareStatement(sql);

				rs = insereST.executeQuery();

			} else {

				sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = ? order by cl.nome asc";

				insereST = conexao.prepareStatement(sql);

				insereST.setString(1, estagio);

				rs = insereST.executeQuery();

			}

			/*
			 * sql =
			 * "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where status like ? and dtrecebimento between ? and ? order by cl.nome asc"
			 * ;
			 * 
			 * insereST = conexao.prepareStatement(sql); insereST.setString(1, estagio +
			 * "%"); insereST.setDate(2, dtInicial); insereST.setDate(3, dtFinal); rs =
			 * insereST.executeQuery();
			 */

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				if (!os.getStatus().equals("PEDIDO FINALIZADO")
						&& !os.getStatus().equals("ORDEM DE SERVIÇO CANCELADA")) {

					listaOS.add(os);
				}

			}

			return listaOS;

		} catch (

		Exception e) {

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

	public List<OrdemServico> listarOrdemServicoEmProducao(String listarTodas) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			if (listarTodas.equals("IMPRESSÃO")) {

				sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = 'IMPRESSÃO' or os.status = 'AGUARDANDO O CLIENTE' order by cl.nome asc";

				insereST = conexao.prepareStatement(sql);

				rs = insereST.executeQuery();

			} else {

				sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where os.status = ? order by cl.nome asc";

				insereST = conexao.prepareStatement(sql);

				insereST.setString(1, listarTodas);

				rs = insereST.executeQuery();

			}

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				if (!os.getStatus().equals("PEDIDO FINALIZADO")
						&& !os.getStatus().equals("ORDEM DE SERVIÇO CANCELADA")) {

					listaOS.add(os);
				}
			}

			return listaOS;

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

		return listaOS;

	}

	public List<OrdemServico> listarOrdemServicoTodosEstagios() {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where not status = 'PEDIDO FINALIZADO' and not  status = 'ORDEM DE SERVIÇO CANCELADA' order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);

			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				listaOS.add(os);

			}

			return listaOS;

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

		return listaOS;

	}

	public List<OrdemServico> listarOrdemServicoTodosEstagiosComData(Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		try {

			sql = "select * from ordemservico as os inner join cliente cl on os.id_cliente = cl.id_cliente where not status = 'PEDIDO FINALIZADO' and not  status = 'ORDEM DE SERVIÇO CANCELADA' and dtrecebimento between ? and ? order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataInicial);
			insereST.setDate(2, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				os.setCliente(cliente);

				if (!os.getStatus().equals("PEDIDO FINALIZADO")
						&& !os.getStatus().equals("ORDEM DE SERVIÇO CANCELADA")) {

					listaOS.add(os);
				}

			}

			return listaOS;

		} catch (

		Exception e) {

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

		return listaOS;

	}

	public List<PedidoFinalizado> pesquisarNomeVendedorAdm(String nome) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select * from ordemservico as os inner join cliente as cl on os.id_cliente = cl.id_cliente inner join estadoos as es on os.id_os = es.id_os inner join portador as po on os.id_os = po.id_os where (estagio = '5' and status = 'PEDIDO FINALIZADO') and cl.vendedor like ? order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, "%" + nome + "%");
			rs = insereST.executeQuery();

			while (rs.next()) {

				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();
				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setOs(os);

				pedidoFinalizado.setCliente(cliente);

				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

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

		return listaPedidosFinalizados;
	}

	public List<PedidoFinalizado> pesquisarNomeClienteAdm(String nome) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			sql = "select * from ordemservico as os inner join cliente as cl on os.id_cliente = cl.id_cliente inner join estadoos as es on os.id_os = es.id_os inner join portador as po on os.id_os = po.id_os where (estagio = '5' and status = 'PEDIDO FINALIZADO') and cl.nome like ? order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, "%" + nome + "%");
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();
				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				pedidoFinalizado.setOs(os);
				pedidoFinalizado.setCliente(cliente);
				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

			return listaPedidosFinalizados;

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodoAdm(String nome, Date dataInicial, Date dataFinal) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		try {

			// sql = "select * from ordemservico as os inner join cliente as cl on
			// os.id_cliente = cl.id_cliente inner join estadoos as es on os.id_os =
			// es.id_os inner join portador as po on os.id_os = po.id_os where (estagio =
			// '5' and status = 'PEDIDO FINALIZADO') and cl.nome like ? order by cl.nome
			// asc";
			sql = "select * from ordemservico as os inner join cliente as cl on os.id_cliente = cl.id_cliente inner join estadoos as es on os.id_os = es.id_os inner join portador as po on os.id_os = po.id_os where cl.nome like ? and (data_inicio between ? and ?) and ((status = 'PEDIDO FINALIZADO') and (estagio = '5')) order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setString(1, "%" + nome + "%");
			insereST.setDate(2, dataInicial);
			insereST.setDate(3, dataFinal);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();
				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				pedidoFinalizado.setOs(os);
				pedidoFinalizado.setCliente(cliente);
				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

			return listaPedidosFinalizados;

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public List<EntregarPedidos> listarEntregarOS(String numeroOS) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		String entrega = "";

		int id_os = Integer.valueOf(numeroOS);

		try {

			sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where ordemservico.id_os  = ? and estagio = '3' and status = 'ENTREGAR' order by cliente.nome";
			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, id_os);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos recepcao = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				if (rs.getBoolean("entrega") == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				recepcao.setDtExpedicao(rs.getDate("data_inicio"));
				recepcao.setTotalFinal(rs.getString("total_final"));
				recepcao.setEntrega(entrega);
				recepcao.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));

				recepcao.setCliente(cliente);
				recepcao.setOrdemServico(ordemServico);

				listaRecepcao.add(recepcao);

			}

			insereST.close();
			rs.close();
			conexao.close();

			if (listaRecepcao.isEmpty()) {

				sql = "select * from ordemservico inner join cliente on ordemservico.id_cliente = cliente.id_cliente inner join estadoos on ordemservico.id_os = estadoos.id_os where ordemservico.id_os  = ? and estagio = '4' and status = 'ENTREGAR' order by cliente.nome";
				insereST = conexao.prepareStatement(sql);
				insereST.setInt(1, id_os);
				rs = insereST.executeQuery();

				while (rs.next()) {

					EntregarPedidos recepcao = new EntregarPedidos();
					Cliente cliente = new Cliente();
					OrdemServico ordemServico = new OrdemServico();

					if (rs.getBoolean("entrega") == true) {

						entrega = "Sim";

					} else {

						entrega = "Não";

					}

					recepcao.setDtExpedicao(rs.getDate("data_inicio"));
					recepcao.setTotalFinal(rs.getString("total_final"));
					recepcao.setEntrega(entrega);
					recepcao.setObsevacoes(rs.getString("motivo"));

					cliente.setNome(rs.getString("nome"));
					cliente.setVendedor(rs.getString("vendedor"));

					ordemServico.setId_os(rs.getInt("id_os"));
					ordemServico.setDtPrevista(rs.getDate("dtprevista"));

					recepcao.setCliente(cliente);
					recepcao.setOrdemServico(ordemServico);

					listaRecepcao.add(recepcao);

				}

			}

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " +
			// e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				// JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: "
				// + e2.getMessage());

			}

		}

		return listaRecepcao;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosOS(String numeroOS) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		int id_os = Integer.valueOf(numeroOS);

		try {

			sql = "select * from ordemservico as os inner join cliente as cl on os.id_cliente = cl.id_cliente inner join estadoos as es on os.id_os = es.id_os inner join portador as po on os.id_os = po.id_os where os.id_os = ? and estagio = '5'order by cl.nome asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setInt(1, id_os);
			rs = insereST.executeQuery();

			while (rs.next()) {

				OrdemServico os = new OrdemServico();
				Cliente cliente = new Cliente();
				Portador portador = new Portador();
				PedidoFinalizado pedidoFinalizado = new PedidoFinalizado();

				os.setId_os(rs.getInt("id_os"));
				os.setStatus(rs.getString("status"));
				os.setTotal(rs.getString("total"));
				os.setDtRecebimento(rs.getDate("dtrecebimento"));
				os.setDtPrevista(rs.getDate("dtprevista"));
				os.setId_cliente(rs.getInt("id_cliente"));
				os.setTotalFinal(rs.getString("total_final"));
				os.setMotivo(rs.getString("motivo"));

				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));
				cliente.setContato(rs.getString("contato"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone1(rs.getString("fone1"));
				cliente.setFone2(rs.getString("fone2"));
				cliente.setDetalhes(rs.getString("detalhes"));
				cliente.setEntrega(rs.getBoolean("entrega"));

				portador.setId_portador(rs.getInt("id_portador"));
				portador.setNomePortador(rs.getString("nome_portador"));
				portador.setIdentificacaoPortador(rs.getString("identificacao_portador"));

				pedidoFinalizado.setId_os((rs.getInt("id_os")));
				pedidoFinalizado.setDtExpedicao(rs.getDate("data_inicio"));
				pedidoFinalizado.setTotalFinal(rs.getString("total_final"));
				pedidoFinalizado.setObsevacoes(rs.getString("motivo"));

				pedidoFinalizado.setOs(os);
				pedidoFinalizado.setCliente(cliente);
				pedidoFinalizado.setPortador(portador);

				listaPedidosFinalizados.add(pedidoFinalizado);

			}

			return listaPedidosFinalizados;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar a Lista. Mensagem: " + e.getMessage());

			return listaPedidosFinalizados;

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public List<EntregarPedidos> listarRelatorios(Date dataRelatorio, String condicaoDaPesquisa) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";
		List<EntregarPedidos> listaRelatorio = new ArrayList<EntregarPedidos>();

		int estagio = 0;

		switch (condicaoDaPesquisa) {

		case "PEDIDOS RECEBIDOS NO DIA":

			estagio = 1;

			break;

		case "PEDIDOS PRODUZIDOS NO DIA":

			estagio = 2;

			break;

		case "PEDIDOS LAMINADOS OU EXPEDIDOS NO DIA":

			estagio = 3;

			break;

		case "PEDIDOS ENTREGUES NO DIA":

			estagio = 5;

			break;

		}

		try {

			sql = "select * from estadoos as es inner join ordemservico as os on es.id_os = os.id_os inner join producao as pr on pr.os = os.id_os inner join cliente as cl on cl.id_cliente = os.id_cliente where es.data_inicio = ? and es.estagio = ? order by pr.empresa asc";

			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, dataRelatorio);
			insereST.setInt(2, estagio);
			rs = insereST.executeQuery();

			while (rs.next()) {

				EntregarPedidos entregarPedidos = new EntregarPedidos();
				Cliente cliente = new Cliente();
				OrdemServico ordemServico = new OrdemServico();

				entregarPedidos.setDtExpedicao(rs.getDate("data_inicio"));
				entregarPedidos.setTotalFinal(rs.getString("total_final"));
				entregarPedidos.setObsevacoes(rs.getString("motivo"));

				cliente.setNome(rs.getString("nome"));
				cliente.setVendedor(rs.getString("vendedor"));

				ordemServico.setId_os(rs.getInt("id_os"));
				ordemServico.setDtPrevista(rs.getDate("dtprevista"));
				ordemServico.setTotal(rs.getString("total"));
				ordemServico.setTotalFinal(rs.getString("total_final"));

				entregarPedidos.setCliente(cliente);
				entregarPedidos.setOrdemServico(ordemServico);

				listaRelatorio.add(entregarPedidos);
		

			}

			if (estagio == 3 && listaRelatorio.isEmpty()) {

				estagio = 4;

				sql = "select * from estadoos as es inner join ordemservico as os on es.id_os = os.id_os inner join producao as pr on pr.os = os.id_os inner join cliente as cl on cl.id_cliente = os.id_cliente where es.data_inicio = ? and es.estagio = ? order by pr.empresa asc";

				
				insereST.setDate(1, dataRelatorio);
				insereST.setInt(2, estagio);
				

				while (rs.next()) {

					EntregarPedidos entregarPedidos = new EntregarPedidos();
					Cliente cliente = new Cliente();
					OrdemServico ordemServico = new OrdemServico();

					entregarPedidos.setDtExpedicao(rs.getDate("data_inicio"));
					entregarPedidos.setTotalFinal(rs.getString("total_final"));
					entregarPedidos.setObsevacoes(rs.getString("motivo"));

					cliente.setNome(rs.getString("nome"));
					cliente.setVendedor(rs.getString("vendedor"));

					ordemServico.setId_os(rs.getInt("id_os"));
					ordemServico.setDtPrevista(rs.getDate("dtprevista"));
					ordemServico.setTotal(rs.getString("total"));
					ordemServico.setTotalFinal(rs.getString("total_final"));

					entregarPedidos.setCliente(cliente);
					entregarPedidos.setOrdemServico(ordemServico);

					listaRelatorio.add(entregarPedidos);
					
					return listaRelatorio;

				}

			} else {

				return listaRelatorio;

			}

			
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

		return listaRelatorio;

	}

}
