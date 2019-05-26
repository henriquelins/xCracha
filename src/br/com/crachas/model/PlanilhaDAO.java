package br.com.crachas.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import br.com.crachas.controller.Planilha;

public class PlanilhaDAO {

	public void criarPlanilha(Planilha planilha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "insert into planilha (dataPlanilha, total, status) values (?, ?, ?)";

			
			insereST = conexao.prepareStatement(sql);

			insereST.setDate(1, planilha.getDataPlanilha());
			insereST.setString(2, planilha.getTotal());
			insereST.setBoolean(3, planilha.getStatus());

			insereST.execute();
			
			

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar a Planilha. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public void atualizarPlanilha(Planilha planilha) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		String sql = "";

		try {

			sql = "update planilha set dataPlanilha = ?, total = ?, status = ? where id_planilha = ?";

			java.sql.Date dataSql = new java.sql.Date(planilha.getDataPlanilha().getTime());

			insereST = conexao.prepareStatement(sql);

			insereST.setDate(1, dataSql);
			insereST.setString(2, planilha.getTotal());
			insereST.setBoolean(3, planilha.getStatus());
			insereST.setInt(4, planilha.getId_planilha());

			insereST.executeUpdate();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao salvar a Planilha. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

	}

	public Planilha verificarPlanilha(Date hoje) {

		ConectarBanco conectarBanco = new ConectarBanco();
		Connection conexao = conectarBanco.geraConexao();

		PreparedStatement insereST = null;
		ResultSet rs = null;
		String sql = "";

		Planilha planilha = new Planilha();

		try {

			sql = "SELECT * FROM planilha WHERE dataPlanilha = ?";
			insereST = conexao.prepareStatement(sql);
			insereST.setDate(1, hoje);
			rs = insereST.executeQuery();

			while (rs.next()) {

				planilha.setId_planilha(rs.getInt("id_planilha"));
				planilha.setDataPlanilha(rs.getDate("dataPlanilha"));
				planilha.setTotal(rs.getString("total"));
				planilha.setStatus(rs.getBoolean("status"));

			}

			rs.close();
			insereST.close();
			conexao.close();

			return planilha;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao salvar a Planilha. Mensagem: " + e.getMessage());

		} finally {

			try {

				insereST.close();
				conexao.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

			}

		}

		return planilha;
	}

	public Planilha buscarPlanilha(int id_planilha) {
		
			ConectarBanco conectarBanco = new ConectarBanco();
			Connection conexao = conectarBanco.geraConexao();

			PreparedStatement insereST = null;
			ResultSet rs = null;
			String sql = "";

			Planilha planilha = new Planilha();

			try {

				sql = "SELECT * FROM planilha WHERE id_planilha = ?";
				insereST = conexao.prepareStatement(sql);
				insereST.setInt(1, id_planilha);
				rs = insereST.executeQuery();

				while (rs.next()) {

					planilha.setId_planilha(rs.getInt("id_planilha"));
					planilha.setDataPlanilha(rs.getDate("dataPlanilha"));
					planilha.setTotal(rs.getString("total"));
					planilha.setStatus(rs.getBoolean("status"));

				}

				rs.close();
				insereST.close();
				conexao.close();

				return planilha;

			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "Erro ao salvar a Planilha. Mensagem: " + e.getMessage());

			} finally {

				try {

					insereST.close();
					conexao.close();

				} catch (Exception e2) {

					JOptionPane.showMessageDialog(null, "Erro ao encerrar a Conexão. Mensagem: " + e2.getMessage());

				}

			}

			return planilha;
		

	}

	

}
