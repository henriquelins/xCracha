package br.com.crachas.controller;

public class Portador {
	
	int id_portador;
	String nomePortador;
	String identificacaoPortador;
	int id_os;
	
	
	public Portador (){
		
		
	}


	public Portador(int id_portador, String nomePortador, String identificacaoPortador, int id_os) {
		
		this.id_portador = id_portador;
		this.nomePortador = nomePortador;
		this.identificacaoPortador = identificacaoPortador;
		this.id_os = id_os;
	}


	public int getId_portador() {
		return id_portador;
	}


	public void setId_portador(int id_portador) {
		this.id_portador = id_portador;
	}


	public String getNomePortador() {
		return nomePortador;
	}


	public void setNomePortador(String nomePortador) {
		this.nomePortador = nomePortador;
	}


	public String getIdentificacaoPortador() {
		return identificacaoPortador;
	}


	public void setIdentificacaoPortador(String identificacaoPortador) {
		this.identificacaoPortador = identificacaoPortador;
	}


	public int getId_os() {
		return id_os;
	}


	public void setId_os(int id_os) {
		this.id_os = id_os;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_os;
		result = prime * result + id_portador;
		result = prime * result + ((identificacaoPortador == null) ? 0 : identificacaoPortador.hashCode());
		result = prime * result + ((nomePortador == null) ? 0 : nomePortador.hashCode());
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
		Portador other = (Portador) obj;
		if (id_os != other.id_os)
			return false;
		if (id_portador != other.id_portador)
			return false;
		if (identificacaoPortador == null) {
			if (other.identificacaoPortador != null)
				return false;
		} else if (!identificacaoPortador.equals(other.identificacaoPortador))
			return false;
		if (nomePortador == null) {
			if (other.nomePortador != null)
				return false;
		} else if (!nomePortador.equals(other.nomePortador))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Portador [id_portador=").append(id_portador).append(", nomePortador=").append(nomePortador)
				.append(", identificacaoPortador=").append(identificacaoPortador).append(", id_os=").append(id_os)
				.append("]");
		return builder.toString();
	}


	
	
	
}
