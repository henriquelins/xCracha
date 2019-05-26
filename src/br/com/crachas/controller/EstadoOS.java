package br.com.crachas.controller;

import java.sql.Date;
import java.sql.Time;

public class EstadoOS {
	
	private int id_esdadoOS;
	private int id_os;
	private int estagio;
	private Date dataInicial;
	private Time horaInicial;
	private String operador;
	
	public EstadoOS() {
		
	}

	public EstadoOS(int id_esdadoOS, int id_os, int estagio, Date dataInicial, Time horaInicial, String operador) {
		
		this.id_esdadoOS = id_esdadoOS;
		this.id_os = id_os;
		this.estagio = estagio;
		this.dataInicial = dataInicial;
		this.horaInicial = horaInicial;
		this.operador = operador;
	}

	public int getId_esdadoOS() {
		return id_esdadoOS;
	}

	public void setId_esdadoOS(int id_esdadoOS) {
		this.id_esdadoOS = id_esdadoOS;
	}

	public int getId_os() {
		return id_os;
	}

	public void setId_os(int id_os) {
		this.id_os = id_os;
	}

	public int getEstagio() {
		return estagio;
	}

	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Time getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(Time horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + estagio;
		result = prime * result + ((horaInicial == null) ? 0 : horaInicial.hashCode());
		result = prime * result + id_esdadoOS;
		result = prime * result + id_os;
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
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
		EstadoOS other = (EstadoOS) obj;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (estagio != other.estagio)
			return false;
		if (horaInicial == null) {
			if (other.horaInicial != null)
				return false;
		} else if (!horaInicial.equals(other.horaInicial))
			return false;
		if (id_esdadoOS != other.id_esdadoOS)
			return false;
		if (id_os != other.id_os)
			return false;
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstadoOS [id_esdadoOS=").append(id_esdadoOS)
				.append(", id_os=").append(id_os)
				.append(", estagio=").append(estagio)
				.append(", dataInicial=").append(dataInicial)
				.append(", horaInicial=").append(horaInicial)
				.append(", operador=").append(operador).append("]");
		return builder.toString();
	}

	
	

}
