package br.com.crachas.controller;

import br.com.crachas.model.PortadorDAO;

public class PortadorRN {
	
	
	public boolean novoPortador(Portador portador){
		
		boolean salvou = false;
		
		PortadorDAO portadorDAO = new PortadorDAO();
		
		salvou = portadorDAO.novoPortador(portador);
		
		return salvou;
		
	}
	

}
