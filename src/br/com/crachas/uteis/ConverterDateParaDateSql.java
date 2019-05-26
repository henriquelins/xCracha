package br.com.crachas.uteis;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ConverterDateParaDateSql {

	private java.sql.Date dataSql;
	private java.util.Date dataUtil1;
	private GregorianCalendar dataUtil2;

	public ConverterDateParaDateSql() {

	}

	public java.util.Date getDataUtil1() {
		return dataUtil1;
	}

	public void setDataUtil1(java.util.Date dataUtil1) {
		this.dataUtil1 = dataUtil1;
	}

	public java.sql.Date getDataSql() {
		return dataSql;
	}

	public void setDataSql(java.sql.Date dataSql) {
		this.dataSql = dataSql;
	}

	public GregorianCalendar getDataUtil2() {
		return dataUtil2;
	}

	public void setDataUtil2(GregorianCalendar dataUtil2) {
		this.dataUtil2 = dataUtil2;
	}

	public Date converteSqlDate(Date dataEntrada) {

	this.dataUtil1 = dataEntrada;
	this.dataSql = new java.sql.Date(dataUtil1.getTime());

		return dataSql;

	}

	public Date converteSqlGregorianCalendar(GregorianCalendar dtPrevisao) {
		
		this.dataUtil2 = dtPrevisao;
		this.dataUtil1 =  this.dataUtil2.getTime();
		this.dataSql = new java.sql.Date(dataUtil1.getTime());

		return dataSql;

	}

}
