package br.com.crachas.controller;

public class Cliente {
	
	private int id_cliente;
	private String nome;
	private String vendedor;
	private String contato;
	private String email;
	private String fone1;
	private String fone2;
	private String detalhes;
	private boolean entrega;
	private String unidade;
	
	public Cliente(){}
	
	public Cliente(int id_cliente, String nome, String vendedor, String contato, String email, String fone1,
			String fone2, String detalhes, boolean entrega) {
		
		this.id_cliente = id_cliente;
		this.nome = nome;
		this.vendedor = vendedor;
		this.contato = contato;
		this.email = email;
		this.fone1 = fone1;
		this.fone2 = fone2;
		this.detalhes = detalhes;
		this.setEntrega(entrega);
	}
	
	
	
	
	public Cliente(int id_cliente, String nome, String vendedor, String contato, String email, String fone1,
			String fone2, String detalhes, boolean entrega, String unidade) {
		
		this.id_cliente = id_cliente;
		this.nome = nome;
		this.vendedor = vendedor;
		this.contato = contato;
		this.email = email;
		this.fone1 = fone1;
		this.fone2 = fone2;
		this.detalhes = detalhes;
		this.entrega = entrega;
		this.unidade = unidade;
		
	}

	public Cliente(String nome, String vendedor, String contato, String email, String fone1,
			String fone2, String detalhes) {
				
		this.nome = nome;
		this.vendedor = vendedor;
		this.contato = contato;
		this.email = email;
		this.fone1 = fone1;
		this.fone2 = fone2;
		this.detalhes = detalhes;
	
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFone1() {
		return fone1;
	}

	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}

	public String getFone2() {
		return fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public boolean isEntrega() {
		return entrega;
	}

	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contato == null) ? 0 : contato.hashCode());
		result = prime * result + ((detalhes == null) ? 0 : detalhes.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (entrega ? 1231 : 1237);
		result = prime * result + ((fone1 == null) ? 0 : fone1.hashCode());
		result = prime * result + ((fone2 == null) ? 0 : fone2.hashCode());
		result = prime * result + id_cliente;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		result = prime * result + ((vendedor == null) ? 0 : vendedor.hashCode());
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
		Cliente other = (Cliente) obj;
		if (contato == null) {
			if (other.contato != null)
				return false;
		} else if (!contato.equals(other.contato))
			return false;
		if (detalhes == null) {
			if (other.detalhes != null)
				return false;
		} else if (!detalhes.equals(other.detalhes))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (entrega != other.entrega)
			return false;
		if (fone1 == null) {
			if (other.fone1 != null)
				return false;
		} else if (!fone1.equals(other.fone1))
			return false;
		if (fone2 == null) {
			if (other.fone2 != null)
				return false;
		} else if (!fone2.equals(other.fone2))
			return false;
		if (id_cliente != other.id_cliente)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		if (vendedor == null) {
			if (other.vendedor != null)
				return false;
		} else if (!vendedor.equals(other.vendedor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [id_cliente=").append(id_cliente).append(", nome=").append(nome).append(", vendedor=")
				.append(vendedor).append(", contato=").append(contato).append(", email=").append(email)
				.append(", fone1=").append(fone1).append(", fone2=").append(fone2).append(", detalhes=")
				.append(detalhes).append(", entrega=").append(entrega).append(", unidade=").append(unidade).append("]");
		return builder.toString();
	}

	
	
}
