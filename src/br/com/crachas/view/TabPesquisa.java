package br.com.crachas.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TabPesquisa extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * @Override public Component getTableCellRendererComponent(JTable table,
	 * Object value, boolean isSelected, boolean hasFocus, int row, int column)
	 * { JLabel label = (JLabel) super.getTableCellRendererComponent(table,
	 * value, isSelected, hasFocus, row, column); Color c = Color.WHITE; Object
	 * text = table.getValueAt(row, 3); if (text != null &&
	 * "VERMELHO".equals(text.toString())) c = Color.RED;
	 * label.setBackground(c); return label; }
	 */

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component renderer = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);

		Color fundo = null;
		Color fonte = null;

		Object text = table.getValueAt(row, 7);
				
		
		if (text != null && text.equals("ORDEM DE SERVIÇO CANCELADA")){
			
			fundo = Color.RED;
			fonte = Color.WHITE;
			
		} else if (text != null && text.equals("AGUARDANDO O CLIENTE")) {
			
			fundo = Color.YELLOW;
			fonte = Color.BLUE;
			
		} else {
			
			fundo = Color.WHITE;
			fonte = Color.BLUE;
			
		}

		if (isSelected){
			
			fundo = Color.BLUE;
			fonte = Color.WHITE;
		}

		renderer.setBackground(fundo);
		renderer.setForeground(fonte);

		return renderer;
	}
}