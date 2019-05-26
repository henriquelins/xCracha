package br.com.crachas.controller;

import java.sql.Date;

public class Planilha {
	
	private int id_planilha;
	private Date dataPlanilha;
	private String total;
	private Boolean status;
	
	public Planilha(){};
	
	public Planilha(Date dataPlanilha) {
		
		this.dataPlanilha = dataPlanilha;
		
	}

	public int getId_planilha() {
		return id_planilha;
	}

	public void setId_planilha(int id_planilha) {
		this.id_planilha = id_planilha;
	}

	public Date getDataPlanilha() {
		return dataPlanilha;
	}

	public void setDataPlanilha(Date dataPlanilha) {
		this.dataPlanilha = dataPlanilha;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Planilha [id_planilha=" + id_planilha + ", dataPlanilha=" + dataPlanilha + ", total=" + total
				+ ", status=" + status + "]";
	}
	
	


}
