package br.com.crachas.controller;

import java.sql.Date;

import br.com.crachas.model.PlanilhaDAO;

public class PlanilhaRN {

	PlanilhaDAO planilhaDAO = new PlanilhaDAO();
	Planilha planilha = new Planilha();

	public void criarPlanilha(Planilha planilha) {

		planilhaDAO.criarPlanilha(planilha);

	}

	public void atualizarPlanilha(Planilha planilha) {

		planilhaDAO.atualizarPlanilha(planilha);

	}

	public Planilha verificarPlanilha(Date hoje) {

		this.planilha = planilhaDAO.verificarPlanilha(hoje);

		return planilha;

	}
	
	public Planilha buscarPlanilha(int id_planilha){
		
		this.planilha = planilhaDAO.buscarPlanilha(id_planilha);
				
		return this.planilha;
			
	}
	

}
