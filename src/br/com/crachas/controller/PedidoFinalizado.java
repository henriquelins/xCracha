package br.com.crachas.controller;

import java.sql.Date;

public class PedidoFinalizado {
	
	private int id_os;
	private OrdemServico Os;
	private Cliente cliente;
	private Date dtExpedicao;
	private String totalFinal;
	private String obsevacoes;
	private Portador portador;
	
	
	
	public PedidoFinalizado (){}


	public PedidoFinalizado(int id_os, Cliente cliente, Date dtExpedicao, String totalFinal, String obsevacoes,
			Portador portador) {
		
		this.id_os = id_os;
		this.cliente = cliente;
		this.dtExpedicao = dtExpedicao;
		this.totalFinal = totalFinal;
		this.obsevacoes = obsevacoes;
		this.portador = portador;
	}
	
	
	

	public PedidoFinalizado(int id_os, OrdemServico os, Cliente cliente, Date dtExpedicao, String totalFinal,
			String obsevacoes, Portador portador) {
		
		this.id_os = id_os;
		this.Os = os;
		this.cliente = cliente;
		this.dtExpedicao = dtExpedicao;
		this.totalFinal = totalFinal;
		this.obsevacoes = obsevacoes;
		this.portador = portador;
	}


	public int getId_os() {
		return id_os;
	}


	public void setId_os(int id_os) {
		this.id_os = id_os;
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


	public Portador getPortador() {
		return portador;
	}


	public void setPortador(Portador portador) {
		this.portador = portador;
	}


	public OrdemServico getOs() {
		return Os;
	}


	public void setOs(OrdemServico os) {
		Os = os;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Os == null) ? 0 : Os.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dtExpedicao == null) ? 0 : dtExpedicao.hashCode());
		result = prime * result + id_os;
		result = prime * result + ((obsevacoes == null) ? 0 : obsevacoes.hashCode());
		result = prime * result + ((portador == null) ? 0 : portador.hashCode());
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
		PedidoFinalizado other = (PedidoFinalizado) obj;
		if (Os == null) {
			if (other.Os != null)
				return false;
		} else if (!Os.equals(other.Os))
			return false;
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
		if (id_os != other.id_os)
			return false;
		if (obsevacoes == null) {
			if (other.obsevacoes != null)
				return false;
		} else if (!obsevacoes.equals(other.obsevacoes))
			return false;
		if (portador == null) {
			if (other.portador != null)
				return false;
		} else if (!portador.equals(other.portador))
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
		builder.append("PedidoFinalizado [id_os=").append(id_os).append(", Os=").append(Os).append(", cliente=")
				.append(cliente).append(", dtExpedicao=").append(dtExpedicao).append(", totalFinal=").append(totalFinal)
				.append(", obsevacoes=").append(obsevacoes).append(", portador=").append(portador).append("]");
		return builder.toString();
	}
	
	

}
