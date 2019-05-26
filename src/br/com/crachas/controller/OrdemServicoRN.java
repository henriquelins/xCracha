package br.com.crachas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.OrdemServicoDAO;

public class OrdemServicoRN {

	OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();

	public int criarOrdemServico(OrdemServico ordemServico) {

		int id_os;

		id_os = ordemServicoDAO.criarOrdemServico(ordemServico);

		return id_os;
	}

	public void atualizarOrdemServico(OrdemServico ordemServico) {

		ordemServicoDAO.atualizarOrdemServico(ordemServico);

	}

	public void atualizarTotal(int id_os, String total, int id_cliente, String observacoes) {

		ordemServicoDAO.atualizarTotal(id_os, total, id_cliente, observacoes);

	}

	public List<OrdemServico> listarOrdemServico(String listarTodas) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarOrdemServico(listarTodas);

		return listaOS;
	}

	public OrdemServico mostrarOS(int id_os) {

		OrdemServico os = new OrdemServico();

		os = ordemServicoDAO.mostrarOS(id_os);

		return os;
	}

	public List<OrdemServico> listarPesquisarOS(String pesquisaCampo) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarPesquisarOS(pesquisaCampo);

		return listaOS;
	}

	public List<OrdemServico> listarPesquisarDataEntrada(String pesquisaCampo, Date DataEntrada) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarPesquisarDataEntrada(pesquisaCampo, DataEntrada);

		return listaOS;
	}

	public void atualizarStatus(int id, String status) {

		ordemServicoDAO.atualizarStatus(id, status);

	}

	public void atualizarTotalFinal(int id, String totalFinal, String motivo) {

		ordemServicoDAO.atualizarTotalFinal(id, totalFinal, motivo);

	}

	public List<OrdemServico> pesquisarPorData(Date dtInicial, Date dtFinal, String estagio) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.pesquisarPorData(dtInicial, dtFinal, estagio);

		return listaOS;
	}

	public List<OrdemServico> pesquisarOScompleta(String informar, String campoInformado, Date dtInicial,
			Date dtFinal) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.pesquisarOSCompleta(informar, campoInformado, dtInicial, dtFinal);

		return listaOS;
	}

	public List<EntregarPedidos> listarPedidoRecepcao() {

		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		listaRecepcao = ordemServicoDAO.listarPedidoRecepcao();

		return listaRecepcao;

	}

	public List<EntregarPedidos> listarPedidoRecepcaoData(Date DataEntrada) {

		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		listaRecepcao = ordemServicoDAO.listarPesquisarDataEntrada(DataEntrada);

		return listaRecepcao;
	}

	public List<EntregarPedidos> listarPedidoEntregar() {

		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		listaRecepcao = ordemServicoDAO.listarPedidoEntregar();

		return listaRecepcao;

	}

	public List<EntregarPedidos> listarPedidoEntregarData(Date dataRecepcao) {

		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		listaRecepcao = ordemServicoDAO.listarPedidoEntregarData(dataRecepcao);

		return listaRecepcao;
	}

	public List<PedidoFinalizado> listarPedidosFinalizados() {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizados();

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosData(Date dataPedidosFinalizados) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosData(dataPedidosFinalizados);

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodo(Date dataInicial, Date dataFinal) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosPeriodo(dataInicial, dataFinal);

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodoENome(String cliente, Date dataInicial,
			Date dataFinal) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosPeriodoENome(cliente, dataInicial, dataFinal);

		return listaPedidosFinalizados;

	}

	public List<EntregarPedidos> listarEntregarPeriodo(Date dataInicial, Date dataFinal) {

		List<EntregarPedidos> listarEntregar = new ArrayList<EntregarPedidos>();

		listarEntregar = ordemServicoDAO.listarEntregarPeriodo(dataInicial, dataFinal);

		return listarEntregar;

	}

	public List<EntregarPedidos> listarEntregarPeriodoENome(String cliente, Date dataInicial, Date dataFinal) {

		List<EntregarPedidos> listarEntregar = new ArrayList<EntregarPedidos>();

		listarEntregar = ordemServicoDAO.listarEntregarPeriodoENome(cliente, dataInicial, dataFinal);

		return listarEntregar;

	}

	public List<OrdemServico> listarPedidoLaminacao() {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarOrdemServico();

		return listaOS;

	}

	public List<OrdemServico> listarPedidoLaminacaoData(Date dataEntrada) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarPedidoLaminacaoData(dataEntrada);

		return listaOS;

	}

	public List<PedidoFinalizado> pesquisarNomeVendedorAdm(String nome) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.pesquisarNomeVendedorAdm(nome);

		return listaPedidosFinalizados;
	}

	public List<PedidoFinalizado> pesquisarNomeClienteAdm(String nome) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.pesquisarNomeClienteAdm(nome);

		return listaPedidosFinalizados;
	}

	public List<OrdemServico> pesquisarNomeCliente(String nome) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.pesquisarNomeCliente(nome);

		return listaOS;
	}

	public List<OrdemServico> listarOrdemServicoEmProducao(String estagio) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarOrdemServicoEmProducao(estagio);

		return listaOS;
	}

	public List<OrdemServico> listarOrdemServicoTodosEstagios() {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarOrdemServicoTodosEstagios();

		return listaOS;
	}

	public List<OrdemServico> listarOrdemServicoTodosEstagiosComData(Date dataInicial, Date dataFinal) {

		List<OrdemServico> listaOS = new ArrayList<OrdemServico>();

		listaOS = ordemServicoDAO.listarOrdemServicoTodosEstagiosComData(dataInicial, dataFinal);

		return listaOS;
	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodoENomeAdm(String cliente, Date dataInicial,
			Date dataFinal) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosPeriodoENome(cliente, dataInicial, dataFinal);

		return listaPedidosFinalizados;

	}

	public List<PedidoFinalizado> listarPedidosFinalizadosPeriodoAdm(String cliente, Date dataInicial, Date dataFinal) {

		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosPeriodoAdm(cliente, dataInicial, dataFinal);

		return listaPedidosFinalizados;

	}

	public List<EntregarPedidos> listarEntregarOS(String numeroOS) {
		
		List<EntregarPedidos> listaRecepcao = new ArrayList<EntregarPedidos>();

		listaRecepcao = ordemServicoDAO.listarEntregarOS(numeroOS);

		return listaRecepcao;
		
	}

	public List<PedidoFinalizado> listarPedidosFinalizadosOS(String numeroOS) {
		
		List<PedidoFinalizado> listaPedidosFinalizados = new ArrayList<PedidoFinalizado>();

		listaPedidosFinalizados = ordemServicoDAO.listarPedidosFinalizadosOS(numeroOS);

		return listaPedidosFinalizados;
	}

	public List<EntregarPedidos> listarRelatorios(Date dataRelatorio, String condicaoDaPesquisa) {
		
		List<EntregarPedidos> listaRelatorio = new ArrayList<EntregarPedidos>();

		listaRelatorio = ordemServicoDAO.listarRelatorios(dataRelatorio, condicaoDaPesquisa );

		return listaRelatorio;
	}

	
}
