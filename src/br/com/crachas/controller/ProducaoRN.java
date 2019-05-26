package br.com.crachas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.ProducaoDAO;

public class ProducaoRN {

	ProducaoDAO producaoDAO = new ProducaoDAO();

	public int inserirProducaoRN(Producao producao, int id_planilha) {
		
		int id_producao = 0;
		
		id_producao = producaoDAO.insereProducao(producao, id_planilha);
		
		return id_producao;

	}

	public List<Producao> listaProducaoRN() {

		List<Producao> listaProducao = new ArrayList<Producao>();

		listaProducao = producaoDAO.ListaProducao();

		return listaProducao;

	}

	public List<Producao> listaProducaoData(int id_planilha) {

		List<Producao> listaProducao = new ArrayList<Producao>();

		listaProducao = producaoDAO.ListaProducaoData(id_planilha);

		return listaProducao;

	}

	public List<Producao> listaDataEntrada(String campo, String informarPequisa, Date dataInicial, Date dataFinal) {

		List<Producao> listaProducao = new ArrayList<Producao>();

		listaProducao = producaoDAO.listaDataEntrada(campo, informarPequisa, dataInicial, dataFinal);

		return listaProducao;

	}

	public Producao pesquisarID(int id_producao) {

		Producao producao = new Producao();
		
		producao = producaoDAO.pesquisarID(id_producao);

		return producao;

	}
	
	
	public Producao pesquisarIDOS(int id_os) {

		Producao producao = new Producao();
		
		producao = producaoDAO.pesquisarIDOS(id_os);

		return producao;

	}
	
	public void atualizarProducao(Producao producao) {

		producaoDAO.atualizarProducao(producao);

	}

	public void excluirProducao(Producao producao) {
		
		producaoDAO.excluirProducao(producao);
		
		
	}

	public List<Producao> listaEntrada(Date dataEntrada) {
	
		List<Producao> listaProducao = new ArrayList<Producao>();

		listaProducao = producaoDAO.listaEntrada(dataEntrada);

		return listaProducao;
	}

}
