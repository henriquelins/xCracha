package br.com.crachas.controller;

public class Produto {

	private int id_produto;
	private String nome;
	private String descricao;
	
	
	public Produto(){}
	
	public Produto(String nome, String descricao) {

		this.nome = nome;
		this.descricao = descricao;

	}

	public Produto(int id_produto, String nome, String descricao) {
				
		this.id_produto = id_produto;
		this.nome = nome;
		this.descricao = descricao;


	}

	
	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id_produto;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id_produto != other.id_produto)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Produto [id_produto=").append(id_produto).append(", nome=").append(nome).append(", descricao=")
				.append(descricao).append("]");
		return builder.toString();
	}

}
