package br.com.crachas.controller;

import java.sql.Date;

import br.com.crachas.model.EstadoOSDAO;

public class EstadoOSRN {
	
	EstadoOSDAO estadoOSDAO = new EstadoOSDAO();
	
	public void criarEstadoOS(EstadoOS estadoOS){
		
		estadoOSDAO.criarEstadoOS(estadoOS);
		
	}

	public int buscarEstagio(int id_os) {
		
		int estagio = estadoOSDAO.buscarEstado(id_os);
		
		return estagio;
		
	}

	public Date buscarDataDoEstagio(Integer idOS, int estagio) {
	
		Date dataDoEstagio = null;
		
		dataDoEstagio = estadoOSDAO.buscarDataDoEstagio(idOS, estagio);
		
		return dataDoEstagio;
	}
	
	
	
	
	

}
