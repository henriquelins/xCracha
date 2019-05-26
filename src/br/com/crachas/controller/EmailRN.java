package br.com.crachas.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.crachas.model.EmailDAO;

public class EmailRN {
	
	
	EmailDAO emailDAO = new EmailDAO();
	Email email = new Email();
	
	public void adicionarEmail(Email email) {
		
		emailDAO.adicionarEmail(email);
		
	}
	
	public List <Email> listarEmailsCadastrados(){
		
		List <Email> listaEmail = new ArrayList <Email> ();
		
		listaEmail = emailDAO.listarEmailsCadastrados();
		
		return listaEmail;
		
	}
	
	public Email buscarEmail(){
						
		email = emailDAO.buscarEmail();
		
		return email;
		
	}

	public Email excluirEmail(String excluir_Email) {
						
		email = emailDAO.excluirEmail(excluir_Email);
		
		return null;
	}
	
	public Email editarEmail(Email email){
		
		email = emailDAO.editarEmail(email);
		
		return email;
	}
	

}
