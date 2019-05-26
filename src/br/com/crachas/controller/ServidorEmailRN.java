package br.com.crachas.controller;

import br.com.crachas.model.ServidorEmailDAO;

public class ServidorEmailRN {

	ServidorEmailDAO servidorEmailDAO = new ServidorEmailDAO();
	ServidorEmail servidorEmail = new ServidorEmail();
	
	
	public void adicionarEmail(ServidorEmail servidorEmail) {
		
		servidorEmailDAO.adicionarEmail(servidorEmail);
		
	}
	
	
	public void editarServidorEmail(ServidorEmail servidorEmail){
		
		servidorEmailDAO.editarServidorEmail(servidorEmail);
				
	}
	
	
	public ServidorEmail buscarServidorEmail(){
		
		servidorEmail = servidorEmailDAO.buscarServidorEmail();
		
		return servidorEmail;
		
	}
	
	
}
