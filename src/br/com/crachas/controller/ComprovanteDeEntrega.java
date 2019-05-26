package br.com.crachas.controller;

public class ComprovanteDeEntrega {
		
	private String idOs;
	private String cliente;
	private String vendedor;
	private String totalFinalDoPedido;
	private String observaçõesDoPedido;
	private String detalhesdoLayout;
	private String unidade;
	private String entragamos;
	private String contatoEmailFone;
	private String dataDeRecebimento;
	private String dataExpedição;
	private String portador;

	public ComprovanteDeEntrega(){}

	public ComprovanteDeEntrega(String idOs, String cliente, String vendedor, String totalFinalDoPedido,
			String observaçõesDoPedido, String detalhesdoLayout, String unidade, String entragamos,
			String contatoEmailFone, String dataDeRecebimento, String dataExpedição, String portador) {
		
		this.idOs = idOs;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.totalFinalDoPedido = totalFinalDoPedido;
		this.observaçõesDoPedido = observaçõesDoPedido;
		this.detalhesdoLayout = detalhesdoLayout;
		this.unidade = unidade;
		this.entragamos = entragamos;
		this.contatoEmailFone = contatoEmailFone;
		this.dataDeRecebimento = dataDeRecebimento;
		this.dataExpedição = dataExpedição;
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

	public String getObservaçõesDoPedido() {
		return observaçõesDoPedido;
	}

	public void setObservaçõesDoPedido(String observaçõesDoPedido) {
		this.observaçõesDoPedido = observaçõesDoPedido;
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

	public String getDataExpedição() {
		return dataExpedição;
	}

	public void setDataExpedição(String dataExpedição) {
		this.dataExpedição = dataExpedição;
	}

	public String getPortador() {
		return portador;
	}

	public void setPortador(String portador) {
		this.portador = portador;
	}

		

}
