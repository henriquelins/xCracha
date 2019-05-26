package br.com.crachas.uteis;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.crachas.controller.EntregarPedidos;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.Producao;

public class ExportarCsv {

	public boolean exportarProducaoCsv(String caminho, List<Producao> listaProducao)
			throws HeadlessException, IOException {

		File arquivoCompleto = null;

		try {

			arquivoCompleto = new File(caminho.toString() + ".csv");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar o arquivo!", "Exportar Lançamento",
					JOptionPane.ERROR_MESSAGE);

		}

		OutputStreamWriter bufferOut = null;

		boolean sucesso = false;

		try {

			bufferOut = new OutputStreamWriter(new FileOutputStream(arquivoCompleto), StandardCharsets.ISO_8859_1);

			int contador = 0;

			bufferOut.write(" ID" + ";" + "NÚMERO OS" + ";" + "CLIENTE" + ";" + "TIPO" + ";" + "OPERADOR" + ";"
					+ "QUANT RECEBIDA" + ";" + "DATA ENTRADA" + ";" + "SITUAÇÃO DO PEDIDO" + ";"
					+ "OBSERVAÇÕES DO PEDIDO\n");

			NumberFormat tresDigitos = new DecimalFormat("000");

			try {

				DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
				String dataBr = "";

				for (Producao p : listaProducao) {

					dataBr = formatBR.format(p.getDataEntrada());

					String linha = tresDigitos.format(contador + 1) + ";" + tresDigitos.format(p.getOs()) + ";"
							+ p.getEmpresa() + ";" + p.getTipo() + ";" + p.getOperador() + ";"
							+ tresDigitos.format(Integer.valueOf(p.getQuantidade())) + ";" + dataBr + ";"
							+ p.getOrdemservico().getStatus() + ";" + p.getObservacoes();

					bufferOut.write(linha + "\n");

					contador++;
				}

				sucesso = true;
				bufferOut.close();

			} catch (Exception e) {

				sucesso = false;
				bufferOut.close();
				arquivoCompleto.delete();
				return sucesso;

			}

			bufferOut.close();
			return sucesso;

		} catch (Exception f) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo", "Exportar Lançamento",
					JOptionPane.ERROR_MESSAGE);

		}

		return sucesso;

	}

	public boolean exportarPesquisarOsCsv(String caminho, List<OrdemServico> listaOS)
			throws HeadlessException, IOException {

		File arquivoCompleto = null;

		try {

			arquivoCompleto = new File(caminho.toString() + ".csv");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar o arquivo!", "Exportar Pesquisa OS",
					JOptionPane.ERROR_MESSAGE);

		}

		OutputStreamWriter bufferOut = null;

		boolean sucesso = false;

		try {

			bufferOut = new OutputStreamWriter(new FileOutputStream(arquivoCompleto), StandardCharsets.ISO_8859_1);

			int contador = 0;

			bufferOut.write(" ID" + ";" + "NÚMERO OS" + ";" + "CLIENTE" + ";" + "VENDEDOR" + ";" + "QUANT RECEBIDA"
					+ ";" + "QUANT FINAL" + ";" + "DATA ENTRADA" + ";" + "DATA DA PLANILHA" + ";"
					+ "SITUAÇÃO DO PEDIDO"  + ";" + "OBSERVAÇÕES DO PEDIDO\n");

			NumberFormat tresDigitos = new DecimalFormat("000");
			NumberFormat cincoDigitos = new DecimalFormat("00000");

			try {

				DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
				String dataBr, dataBr2 = "";

				for (OrdemServico o : listaOS) {

					dataBr = formatBR.format(o.getDtRecebimento());
					dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

					String naoDefinido = "N/D";

					if (o.getTotalFinal() == null) {

						String linha = tresDigitos.format(contador + 1) + ";" + cincoDigitos.format(o.getId_os()) + ";"
								+ o.getCliente().getNome() + ";" + o.getCliente().getVendedor() + ";"
								+ tresDigitos.format(Integer.valueOf(o.getTotal())) + ";" + naoDefinido + ";" + dataBr
								+ ";" + dataBr2 + ";" + o.getStatus() + ";" + o.getMotivo();

						bufferOut.write(linha + "\n");

					} else {

						String linha = tresDigitos.format(contador + 1) + ";" + cincoDigitos.format(o.getId_os()) + ";"
								+ o.getCliente().getNome() + ";" + o.getCliente().getVendedor() + ";"
								+ tresDigitos.format(Integer.valueOf(o.getTotal())) + ";"
								+ tresDigitos.format(Integer.valueOf(o.getTotalFinal())) + ";" + dataBr + ";" + dataBr2
								+ ";" + o.getStatus() + ";" + o.getMotivo();

						bufferOut.write(linha + "\n");

					}

					contador++;

				}

				sucesso = true;
				bufferOut.close();

			} catch (Exception e) {

				sucesso = false;
				bufferOut.close();
				arquivoCompleto.delete();
				return sucesso;

			}

			bufferOut.close();
			return sucesso;

		} catch (Exception f) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo!", "Exportar Lançamento",
					JOptionPane.ERROR_MESSAGE);

		}

		return sucesso;

	}

	public boolean exportarEntregarPedidosCsv(String caminho, List <EntregarPedidos> ListarEntregar ) throws HeadlessException, IOException {

		File arquivoCompleto = null;

		try {

			arquivoCompleto = new File(caminho.toString() + ".csv");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar o arquivo!", "Exportar Pesquisa OS",
					JOptionPane.ERROR_MESSAGE);

		}

		OutputStreamWriter bufferOut = null;

		boolean sucesso = false;

		try {

			bufferOut = new OutputStreamWriter(new FileOutputStream(arquivoCompleto), StandardCharsets.ISO_8859_1);

			int contador = 0;
			int total = 0;

			bufferOut.write(" ID" + ";" + "NÚMERO OS" + ";" + "CLIENTE" + ";" + "VENDEDOR" + ";" + "DATA ENTRADA" + ";"
					+ "DATA PREVISTA ENTREGA" + ";" + "TOTAL DO PEDIDO" + ";" + "ENTREGAR NO CLIENTE" + ";"
					+ "OBESERVAÇÕES DO PEDIDO\n");

			NumberFormat tresDigitos = new DecimalFormat("000");
			NumberFormat cincoDigitos = new DecimalFormat("00000");

			try {

				DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
				String dataBr, dataBr2 = "";

				for (EntregarPedidos r : ListarEntregar) {

					dataBr = formatBR.format(r.getDtExpedicao());
					dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

					String linha = tresDigitos.format(contador + 1) + ";"
							+ cincoDigitos.format(r.getOrdemServico().getId_os()) + ";" + r.getCliente().getNome() + ";"
							+ r.getCliente().getVendedor() + ";" + dataBr + ";" + dataBr2 + ";"
							+ tresDigitos.format(Integer.valueOf(r.getTotalFinal())) + ";" + r.getEntrega() + ";"
							+ r.getObsevacoes();

					if (r.getTotalFinal() == null) {

						r.setTotalFinal("000");

					}

					total = Integer.valueOf(r.getTotalFinal()) + total++;

					bufferOut.write(linha + "\n");

					contador++;

				}

				sucesso = true;
				bufferOut.close();

			} catch (Exception e) {

				sucesso = false;
				bufferOut.close();
				arquivoCompleto.delete();
				return sucesso;

			}

			bufferOut.close();
			return sucesso;

		} catch (Exception f) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo!", "Exportar Lançamento",
					JOptionPane.ERROR_MESSAGE);

		}

		return sucesso;

	}

	public boolean exportarRelatorioCsv(String caminho, List<EntregarPedidos> listaRelatorio) {
		File arquivoCompleto = null;

		try {

			arquivoCompleto = new File(caminho.toString() + ".csv");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao criar o arquivo!", "Exportar Pesquisa OS",
					JOptionPane.ERROR_MESSAGE);

		}

		OutputStreamWriter bufferOut = null;

		boolean sucesso = false;

		try {

			bufferOut = new OutputStreamWriter(new FileOutputStream(arquivoCompleto), StandardCharsets.ISO_8859_1);

			int contador = 0;
			

			bufferOut.write(" ID" + ";" + "NÚMERO OS" + ";" + "CLIENTE" + ";" + "VENDEDOR" + ";" + "DATA ENTRADA" + ";"
					+ "TOTAL ENTRADA" + ";" + "TOTAL EXPEDIDO" + ";" + "OBESERVAÇÕES DO PEDIDO\n");

			NumberFormat tresDigitos = new DecimalFormat("000");
			NumberFormat cincoDigitos = new DecimalFormat("00000");

			try {

				DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
				String dataBr = "";
				
				String totalFinal = "";

				for (EntregarPedidos r : listaRelatorio) {

					dataBr = formatBR.format(r.getDtExpedicao());
					
					if (r.getOrdemServico().getTotalFinal() == null) {
						
						totalFinal = "N/D";
						
						String linha = tresDigitos.format(contador + 1) + ";"
								+ cincoDigitos.format(r.getOrdemServico().getId_os()) + ";" + r.getCliente().getNome() + ";"
								+ r.getCliente().getVendedor() + ";" + dataBr + ";" + tresDigitos.format(Integer.valueOf(r.getOrdemServico().getTotal())) +  " ; " 
								+ totalFinal + ";" 	+ r.getObsevacoes();
						
						bufferOut.write(linha + "\n");

						contador++;
						
						
					} else {
						
						
						totalFinal = r.getOrdemServico().getTotalFinal();
						
						String linha = tresDigitos.format(contador + 1) + ";"
								+ cincoDigitos.format(r.getOrdemServico().getId_os()) + ";" + r.getCliente().getNome() + ";"
								+ r.getCliente().getVendedor() + ";" + dataBr + ";" + tresDigitos.format(Integer.valueOf(r.getOrdemServico().getTotal())) +  " ; " 
								+ totalFinal + ";" 	+ r.getObsevacoes();
						
						bufferOut.write(linha + "\n");

						contador++;
						
						
						
					}
					
					

				}

				sucesso = true;
				bufferOut.close();

			} catch (Exception e) {

				sucesso = false;
				bufferOut.close();
				arquivoCompleto.delete();
				return sucesso;

			}

			bufferOut.close();
			return sucesso;

		} catch (Exception f) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar o arquivo!", "Exportar Lançamento",
					JOptionPane.ERROR_MESSAGE);

		}

		return sucesso;
	}

}
