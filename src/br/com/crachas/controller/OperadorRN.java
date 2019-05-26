package br.com.crachas.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.OperadorDAO;

public class OperadorRN {

	OperadorDAO operadorDAO = new OperadorDAO();

	public void adicionarOperador(Operador operador) {

		operadorDAO.adicionarOperador(operador);

	}

	public void editarOperador(Operador operador) {

		operadorDAO.editarOperador(operador);

	}

	public List<Operador> listaOperador() {

		List<Operador> listaOperador = new ArrayList<Operador>();

		listaOperador = operadorDAO.listaOperador();

		return listaOperador;

	}

	public Operador login(String login, String senha) {

		Operador operador = new Operador();

		operador = operadorDAO.login(login, senha);

		return operador;

	}

	public Operador buscarOperador(String nome) {

		Operador operador = new Operador();

		operador = operadorDAO.buscarOperador(nome);

		return operador;

	}

	public void excluirOperador(Operador operador) {

		operadorDAO.excluirOperador(operador);

	}
	
	public List <Operador> pesquisarLoginOperador(String pesquisa){
		
		List <Operador> listaOperador = new ArrayList<Operador>();
		
		listaOperador = operadorDAO.pesquisarLoginOperador(pesquisa);
		
		return listaOperador;
		
	}

}
