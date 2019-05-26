package br.com.crachas.controller;

public class ComprovanteDeEntrega {
		
	private String idOs;
	private String cliente;
	private String vendedor;
	private String totalFinalDoPedido;
	private String observa��esDoPedido;
	private String detalhesdoLayout;
	private String unidade;
	private String entragamos;
	private String contatoEmailFone;
	private String dataDeRecebimento;
	private String dataExpedi��o;
	private String portador;

	public ComprovanteDeEntrega(){}

	public ComprovanteDeEntrega(String idOs, String cliente, String vendedor, String totalFinalDoPedido,
			String observa��esDoPedido, String detalhesdoLayout, String unidade, String entragamos,
			String contatoEmailFone, String dataDeRecebimento, String dataExpedi��o, String portador) {
		
		this.idOs = idOs;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.totalFinalDoPedido = totalFinalDoPedido;
		this.observa��esDoPedido = observa��esDoPedido;
		this.detalhesdoLayout = detalhesdoLayout;
		this.unidade = unidade;
		this.entragamos = entragamos;
		this.contatoEmailFone = contatoEmailFone;
		this.dataDeRecebimento = dataDeRecebimento;
		this.dataExpedi��o = dataExpedi��o;
		this.portador = portador;
	}

	public String getIdOs() {
		return idOs;
	}

	public void setIdOs(String idOs) {
		this.idOs = idOs;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getTotalFinalDoPedido() {
		return totalFinalDoPedido;
	}

	public void setTotalFinalDoPedido(String totalFinalDoPedido) {
		this.totalFinalDoPedido = totalFinalDoPedido;
	}

	public String getObserva��esDoPedido() {
		return observa��esDoPedido;
	}

	public void setObserva��esDoPedido(String observa��esDoPedido) {
		this.observa��esDoPedido = observa��esDoPedido;
	}

	public String getDetalhesdoLayout() {
		return detalhesdoLayout;
	}

	public void setDetalhesdoLayout(String detalhesdoLayout) {
		this.detalhesdoLayout = detalhesdoLayout;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getEntragamos() {
		return entragamos;
	}

	public void setEntragamos(String entragamos) {
		this.entragamos = entragamos;
	}

	public String getContatoEmailFone() {
		return contatoEmailFone;
	}

	public void setContatoEmailFone(String contatoEmailFone) {
		this.contatoEmailFone = contatoEmailFone;
	}

	public String getDataDeRecebimento() {
		return dataDeRecebimento;
	}

	public void setDataDeRecebimento(String dataDeRecebimento) {
		this.dataDeRecebimento = dataDeRecebimento;
	}

	public String getDataExpedi��o() {
		return dataExpedi��o;
	}

	public void setDataExpedi��o(String dataExpedi��o) {
		this.dataExpedi��o = dataExpedi��o;
	}

	public String getPortador() {
		return portador;
	}

	public void setPortador(String portador) {
		this.portador = portador;
	}

		

}
