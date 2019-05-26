package br.com.crachas.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import br.com.crachas.controller.EstadoOS;

public class EstadoOSDAO {

	public void criarEstadoOS(EstadoOS estadoOS) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			int index = 1;

			sql = "insert into estadoos (id_os, estagio, data_inicio, hora_inicio, operador) values (?, ?, ?, ?, ?)";

			java.sql.Date dataSql = new java.sql.Date(estadoOS.getDataInicial().getTime());
			java.sql.Time horaSql = new java.sql.Time(estadoOS.getHoraInicial().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(index, estadoOS.getId_os());
			insereST.setInt(++index, estadoOS.getEstagio());
			insereST.setDate(++index, dataSql);
			insereST.setTime(++index, horaSql);
			insereST.setString(++index, estadoOS.getOperador());

			insereST.executeUpdate();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar o estado da OS. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public int buscarEstado(int id_os) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;
		int estagio = 0;

		try {

			sql = "SELECT * FROM estadoos WHERE id_os = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, id_os);

			rs = insereST.executeQuery();

			while (rs.next()) {

				estagio = rs.getInt("estagio");

			}

			return estagio;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Cliente não encontrado! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return estagio;
	}

	public Date buscarDataDoEstagio(int idOS, int estagio) {
		
		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";
		ResultSet rs = null;
		Date dataDoEstagio = null;

		try {

			sql = "SELECT * FROM estadoos WHERE id_os = ? and estagio = ?";

			insereST = conexao.prepareStatement(sql);

			insereST.setInt(1, idOS);
			insereST.setInt(2, estagio);

			rs = insereST.executeQuery();

			while (rs.next()) {

				dataDoEstagio = rs.getDate("data_inicio");

			}

			return dataDoEstagio;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Data do estágio não encontrada! Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				rs.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return dataDoEstagio;
		
	}

}
