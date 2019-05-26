package br.com.crachas.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.ClienteDAO;

public class ClienteRN {

	ClienteDAO clienteDAO = new ClienteDAO();
	Cliente cliente = new Cliente();
	List<Cliente> listaCliente = new ArrayList<Cliente>();

	public void adicionarCliente(Cliente cliente) {

		clienteDAO.adicionarCliente(cliente);

	}

	public List<Cliente> listarClientes() {

		listaCliente = clienteDAO.listarClientes();

		return listaCliente;
	}

	public void editarcliente(Cliente cliente) {

		clienteDAO.editarcliente(cliente);

	}

	public Cliente selecionarCliente(int linha) {

		cliente = clienteDAO.selecionarCliente(linha);

		return cliente;

	}

	public List<Cliente> pesquisarCliente(String pesquisarCliente) {

		listaCliente = clienteDAO.pesquisarCliente(pesquisarCliente);

		return listaCliente;
	}

	public int clienteOS(String nomeCliente) {

		int id_cliente = clienteDAO.clienteOS(nomeCliente);

		return id_cliente;
	}

	public Cliente mostrarCliente(int id_cliente) {

		cliente = clienteDAO.mostrarCliente(id_cliente);

		return cliente;

	}

	public boolean pesquisaCombo(String cliente) {

		boolean contem = clienteDAO.pesquisaCombo(cliente);

		return contem;

	}

	public String mostrarId_cliente(String nome) {
		
		String id_cliente = clienteDAO.mostrarId_cliente(nome);
		
		return id_cliente;
	}

	public void excluirCliente(int id_cliente) {
		
		clienteDAO.excluirCliente(id_cliente);
		
	}

}
