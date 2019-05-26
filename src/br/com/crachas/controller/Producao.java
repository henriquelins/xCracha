package br.com.crachas.controller;

import java.sql.Date;

public class Producao {

	private int id_producao;
	private String empresa;
	private String tipo;
	private String operador;
	private String quantidade;
	private Date dataEntrada;
	private String observacoes;
	private int id_planilha;
	private Planilha planilha;
	private int os;
	private OrdemServico ordemservico;
	private Cliente cliente;

	public Producao() {

		this.empresa = "-";
		this.tipo = "-";
		this.operador = "-";
		this.quantidade = "-";

		this.observacoes = "-";

	}

	public Producao(String empresa, String tipo, String operador, String quantidade, Date dataEntrada,
			String observacoes, int os) {

		this();

		this.empresa = empresa;
		this.tipo = tipo;
		this.operador = operador;
		this.quantidade = quantidade;

		this.dataEntrada = dataEntrada;
		this.observacoes = observacoes;
		this.os = os;

	}

	public Producao(String empresa, String tipo, String operador, String quantidade,
			Date dataEntrada, String observacoes, int id_planilha, int os) {

		this();

		this.empresa = empresa;
		this.tipo = tipo;
		this.operador = operador;
		this.quantidade = quantidade;
		this.dataEntrada = dataEntrada;
		this.observacoes = observacoes;
		this.id_planilha = id_planilha;
		this.os = os;

	}

	public Producao(String empresa, String tipo, String operador, String quantidade, 
			Date dataEntrada, String observacoes, int id_planilha, Planilha planilha, int os) {

		this();

		this.empresa = empresa;
		this.tipo = tipo;
		this.operador = operador;
		this.quantidade = quantidade;
		this.dataEntrada = dataEntrada;
		this.observacoes = observacoes;
		this.id_planilha = id_planilha;
		this.planilha = planilha;
		this.os = os;

	}
	
	public Producao(String empresa, String tipo, String operador, String quantidade, 
			Date dataEntrada, String observacoes, int id_planilha, Planilha planilha, int os, OrdemServico ordemservico) {

		this();

		this.empresa = empresa;
		this.tipo = tipo;
		this.operador = operador;
		this.quantidade = quantidade;
		this.dataEntrada = dataEntrada;
		this.observacoes = observacoes;
		this.id_planilha = id_planilha;
		this.planilha = planilha;
		this.os = os;
		this.ordemservico = ordemservico;

	}
	

	public int getId_producao() {
		return id_producao;
	}

	public void setId_producao(int id_producao) {
		this.id_producao = id_producao;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public int getId_planilha() {
		return id_planilha;
	}

	public void setId_planilha(int id_planilha) {
		this.id_planilha = id_planilha;
	}

	public Planilha getPlanilha() {
		return planilha;
	}

	public void setPlanilha(Planilha planilha) {
		this.planilha = planilha;
	}

	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public OrdemServico getOrdemservico() {
		return ordemservico;
	}

	public void setOrdemservico(OrdemServico ordemservico) {
		this.ordemservico = ordemservico;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataEntrada == null) ? 0 : dataEntrada.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + id_planilha;
		result = prime * result + id_producao;
		result = prime * result + ((observacoes == null) ? 0 : observacoes.hashCode());
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
		result = prime * result + ((ordemservico == null) ? 0 : ordemservico.hashCode());
		result = prime * result + os;
		result = prime * result + ((planilha == null) ? 0 : planilha.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producao other = (Producao) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataEntrada == null) {
			if (other.dataEntrada != null)
				return false;
		} else if (!dataEntrada.equals(other.dataEntrada))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id_planilha != other.id_planilha)
			return false;
		if (id_producao != other.id_producao)
			return false;
		if (observacoes == null) {
			if (other.observacoes != null)
				return false;
		} else if (!observacoes.equals(other.observacoes))
			return false;
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		if (ordemservico == null) {
			if (other.ordemservico != null)
				return false;
		} else if (!ordemservico.equals(other.ordemservico))
			return false;
		if (os != other.os)
			return false;
		if (planilha == null) {
			if (other.planilha != null)
				return false;
		} else if (!planilha.equals(other.planilha))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Producao [id_producao=").append(id_producao).append(", empresa=").append(empresa)
				.append(", tipo=").append(tipo).append(", operador=").append(operador).append(", quantidade=")
				.append(quantidade).append(", dataEntrada=").append(dataEntrada).append(", observacoes=")
				.append(observacoes).append(", id_planilha=").append(id_planilha).append(", planilha=").append(planilha)
				.append(", os=").append(os).append(", ordemservico=").append(ordemservico).append(", cliente=")
				.append(cliente).append("]");
		return builder.toString();
	}

	

	

	
}
