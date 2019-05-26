package br.com.crachas.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.ProdutoDAO;

public class ProdutoRN {
	
	ProdutoDAO produtoDAO = new ProdutoDAO();
	
	public void adicionarProduto(Produto produto){
		
		produtoDAO.adicionarProduto(produto);
		
	}
	
	public void editarProduto (Produto produto){
		
		produtoDAO.editarProduto(produto);
		
	}
	
	public void excluirProduto (Produto produto){
		
		produtoDAO.excluirProduto(produto);
		
	}
	
	public List <Produto> listarProduto (){
		
		List <Produto> listaProduto = new ArrayList <Produto> ();
		
		listaProduto = produtoDAO.listaProduto();
		
		return listaProduto;
		
	}
	

}
