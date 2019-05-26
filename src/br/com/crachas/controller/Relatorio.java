package br.com.crachas.controller;

import java.io.InputStream;

import javax.swing.JDialog;

import br.com.crachas.view.TelaEntregar;
import br.com.crachas.view.TelaOrdemServico;
import br.com.crachas.view.TelaPesquisarOrdemServico;
import br.com.crachas.view.TelaProducao;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {

	public void gerarPlanilhaProducao() throws JRException {

		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/PlanilhaProducao.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaProducao.listaProducao);

		JasperReport report = JasperCompileManager.compileReport(fonte);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, jrds);
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);

	}
	
	public void gerarPesquisaOrdemServico() throws JRException {

		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/PesquisaOrdemServiço.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaPesquisarOrdemServico.listaOS);

		JasperReport report = JasperCompileManager.compileReport(fonte);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, jrds);
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);

	}
		
	public void gerarPlanilhaRecepcaoEntregar() throws JRException {

		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/PlanilhaEntregar.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaEntregar.listaEntregar);

		JasperReport report = JasperCompileManager.compileReport(fonte);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, jrds);
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);

	}
	
	public void gerarPlanilhaClienteEmProducao() throws JRException {

		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/PlanilhaClienteEmProducao.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaEntregar.listaOS);

		JasperReport report = JasperCompileManager.compileReport(fonte);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, jrds);
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);

	}

	
	public void gerarComprovanteDeEntrega() {
		
		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Comprovante de Entrega", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/ComprovanteDeEntrega.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaOrdemServico.getListaComprovanteEntrega());
		
		JasperReport report = null;
		
		try {
			
			report = JasperCompileManager.compileReport(fonte);
			
		} catch (JRException e) {
			
			e.printStackTrace();
		}
		
		JasperPrint jasperPrint = null;
		
		try {
			
			jasperPrint = JasperFillManager.fillReport(report, null, jrds);
			
		} catch (JRException e) {
			
			e.printStackTrace();
			
		}
		
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);

		
		
		
	}

	public void gerarPlanilhaRelatorio() {
		
		JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
		viewer.setSize(1200, 800);
		viewer.setLocationRelativeTo(null);

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/Relatorio.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(TelaEntregar.listaRelatorio);

		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(fonte);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(report, null, jrds);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JasperViewer jrViewer = new JasperViewer(jasperPrint, false);

		viewer.getContentPane().add(jrViewer.getContentPane());
		viewer.setVisible(true);
		
	}
	
	
}
