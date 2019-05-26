package br.com.crachas.controller;

import java.sql.Date;

public class OrdemServico {

	private int id_os;
	private String status;
	private String total;
	private Date dtRecebimento;
	private Date dtPrevista;
	private int id_cliente;
	private Cliente cliente;
	private Planilha planilha;
	private String totalFinal;
	private String motivo;

	public OrdemServico() {
	}

	public OrdemServico(String status, String total, Date dtRecebimento, Date dtPrevista, int id_cliente) {
		
		this.status = status;
		this.total = total;
		this.dtRecebimento = dtRecebimento;
		this.dtPrevista = dtPrevista;
		this.id_cliente = id_cliente;
	}

	public OrdemServico(int id_os, String status, String total, Date dtRecebimento, Date dtPrevista, int id_cliente) {
		
		this.id_os = id_os;
		this.status = status;
		this.total = total;
		this.dtRecebimento = dtRecebimento;
		this.dtPrevista = dtPrevista;
		this.id_cliente = id_cliente;
	}
	
	
	public OrdemServico(int id_os, String status, String total, Date dtRecebimento, Date dtPrevista, int id_cliente, Cliente cliente) {
		
		this.id_os = id_os;
		this.status = status;
		this.total = total;
		this.dtRecebimento = dtRecebimento;
		this.dtPrevista = dtPrevista;
		this.id_cliente = id_cliente;
		this.setCliente(cliente);
	}
	
	

	public OrdemServico(int id_os, String status, String total, Date dtRecebimento, Date dtPrevista, int id_cliente,
			Cliente cliente, String totalFinal, String motivo) {
		
		this.id_os = id_os;
		this.status = status;
		this.total = total;
		this.dtRecebimento = dtRecebimento;
		this.dtPrevista = dtPrevista;
		this.id_cliente = id_cliente;
		this.setCliente(cliente);
		this.totalFinal = totalFinal;
		this.motivo = motivo;
	}

	public int getId_os() {
		return id_os;
	}

	public void setId_os(int id_os) {
		this.id_os = id_os;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Date getDtRecebimento() {
		return dtRecebimento;
	}

	public void setDtRecebimento(Date dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}

	public Date getDtPrevista() {
		return dtPrevista;
	}

	public void setDtPrevista(Date dtPrevista) {
		this.dtPrevista = dtPrevista;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getTotalFinal() {
		return totalFinal;
	}

	public void setTotalFinal(String totalFinal) {
		this.totalFinal = totalFinal;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dtPrevista == null) ? 0 : dtPrevista.hashCode());
		result = prime * result + ((dtRecebimento == null) ? 0 : dtRecebimento.hashCode());
		result = prime * result + id_cliente;
		result = prime * result + id_os;
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		OrdemServico other = (OrdemServico) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dtPrevista == null) {
			if (other.dtPrevista != null)
				return false;
		} else if (!dtPrevista.equals(other.dtPrevista))
			return false;
		if (dtRecebimento == null) {
			if (other.dtRecebimento != null)
				return false;
		} else if (!dtRecebimento.equals(other.dtRecebimento))
			return false;
		if (id_cliente != other.id_cliente)
			return false;
		if (id_os != other.id_os)
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
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
		builder.append("OrdemServico [id_os=").append(id_os).append(", status=").append(status).append(", total=")
				.append(total).append(", dtRecebimento=").append(dtRecebimento).append(", dtPrevista=")
				.append(dtPrevista).append(", id_cliente=").append(id_cliente).append(", cliente=").append(cliente)
				.append(", totalFinal=").append(totalFinal).append(", motivo=").append(motivo).append("]");
		return builder.toString();
	}

	public Planilha getPlanilha() {
		return planilha;
	}

	public void setPlanilha(Planilha planilha) {
		this.planilha = planilha;
	}

	
	
}
