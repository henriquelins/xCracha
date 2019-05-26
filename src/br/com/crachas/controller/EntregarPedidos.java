package br.com.crachas.controller;

import java.sql.Date;

public class EntregarPedidos {

	private OrdemServico ordemServico;
	private Cliente cliente;
	private Date dtExpedicao;
	private String totalFinal;
	private String obsevacoes;
	private String entrega;

	public EntregarPedidos() {
	}

	public EntregarPedidos(OrdemServico ordemServico, Cliente cliente, Date dtExpedicao, String totalFinal, String obsevacoes,
			String entrega) {

		this.ordemServico = ordemServico;
		this.cliente = cliente;
		this.dtExpedicao = dtExpedicao;
		this.totalFinal = totalFinal;
		this.obsevacoes = obsevacoes;
		this.entrega = entrega;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDtExpedicao() {
		return dtExpedicao;
	}

	public void setDtExpedicao(Date dtExpedicao) {
		this.dtExpedicao = dtExpedicao;
	}

	public String getTotalFinal() {
		return totalFinal;
	}

	public void setTotalFinal(String totalFinal) {
		this.totalFinal = totalFinal;
	}

	public String getObsevacoes() {
		return obsevacoes;
	}

	public void setObsevacoes(String obsevacoes) {
		this.obsevacoes = obsevacoes;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dtExpedicao == null) ? 0 : dtExpedicao.hashCode());
		result = prime * result + ((entrega == null) ? 0 : entrega.hashCode());
		result = prime * result + ((obsevacoes == null) ? 0 : obsevacoes.hashCode());
		result = prime * result + ((ordemServico == null) ? 0 : ordemServico.hashCode());
		result = prime * result + ((totalFinal == null) ? 0 : totalFinal.hashCode());
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
		EntregarPedidos other = (EntregarPedidos) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dtExpedicao == null) {
			if (other.dtExpedicao != null)
				return false;
		} else if (!dtExpedicao.equals(other.dtExpedicao))
			return false;
		if (entrega == null) {
			if (other.entrega != null)
				return false;
		} else if (!entrega.equals(other.entrega))
			return false;
		if (obsevacoes == null) {
			if (other.obsevacoes != null)
				return false;
		} else if (!obsevacoes.equals(other.obsevacoes))
			return false;
		if (ordemServico == null) {
			if (other.ordemServico != null)
				return false;
		} else if (!ordemServico.equals(other.ordemServico))
			return false;
		if (totalFinal == null) {
			if (other.totalFinal != null)
				return false;
		} else if (!totalFinal.equals(other.totalFinal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Recepcao [ordemServico=").append(ordemServico).append(", cliente=").append(cliente)
				.append(", dtExpedicao=").append(dtExpedicao).append(", totalFinal=").append(totalFinal)
				.append(", obsevacoes=").append(obsevacoes).append(", entrega=").append(entrega).append("]");
		return builder.toString();
	}

}
