package br.com.crachas.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;
import br.com.crachas.controller.EstadoOSRN;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.OrdemServicoRN;
import br.com.crachas.controller.Planilha;
import br.com.crachas.controller.PlanilhaRN;
import br.com.crachas.controller.Producao;
import br.com.crachas.controller.ProducaoRN;
import br.com.crachas.controller.Relatorio;
import br.com.crachas.uteis.ExportarCsv;
import br.com.crachas.uteis.TamanhoMaxTextField;
import net.sf.jasperreports.engine.JRException;

public class TelaPesquisarOrdemServico extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8658788527150680291L;
	private JTable tbPos;
	private JTextField tfPesquisarCampoOS;
	private JComboBox<String> cbListarTodas;
	private JTextField tfInformar;
	private TelaOrdemServico telaOS;
	public static List<OrdemServico> listaOS;
	private JLabel lblTextoPesq;
	private JLabel lblTexReg;
	private JLabel lblTextoTotal;
	private JLabel lblPeriodo;
	private JButton btnPesquisarIDOS;
	private JButton btnListarTodas;
	private JButton btnPesquisarOS;
	private JDateChooser dcDtInicialOS;
	private JDateChooser dcDtFinalOS;
	private JLabel lblLogado;
	private JButton btnVoltar;
	private static String temp = "";
	private static String pesquisa1 = "";
	private JDateChooser dcDataEntrada;
	private static String campo2 = "";
	private static String pesquisa2 = "";
	private static java.sql.Date dataI2;
	private static java.sql.Date dataF2;
	private static java.sql.Date dataF3;
	private static String campo3 = "";
	private static String campo4 = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaPesquisarOrdemServico dialog = new TelaPesquisarOrdemServico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("serial")
	public TelaPesquisarOrdemServico() {
		setFont(new Font("Segoe UI", Font.PLAIN, 12));

		telaOS = new TelaOrdemServico();
		listaOS = new ArrayList<OrdemServico>();

		NumberFormat tresDigitos = new DecimalFormat("000");
		NumberFormat cincoDigitos = new DecimalFormat("00000");
		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

		setTitle("Pesquisar Ordem de Servi\u00E7o");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/ne.png")));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 960, 630);
		getContentPane().setLayout(null);

		JScrollPane spPos = new JScrollPane();
		spPos.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spPos.setViewportBorder(null);
		spPos.setBounds(12, 139, 930, 350);
		getContentPane().add(spPos);

		tbPos = new JTable();
		tbPos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tbPos.setGridColor(Color.LIGHT_GRAY);
		tbPos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbPos.setDefaultRenderer(Object.class, new TabPesquisarOrdemServico());

		// criando o cabeçalho cinza e linhas
		JTableHeader anHeader = tbPos.getTableHeader();
		anHeader.setBackground(Color.LIGHT_GRAY);
		// anHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.PLAIN , 10));
		// anHeader.setForeground(new Color(0,0,205));

		// criando o alinhamento do cabeçalho da tabela
		TableCellRenderer rendererFromHeader = tbPos.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);

		tbPos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "N\u00DAMERO OS", "CLIENTE", "VENDEDOR", "QUANT RECEBIDA", "QUANT FINAL",
						"DATA ENTRADA", "DATA DA PLANILHA", "SITUA\u00C7\u00C3O DO PEDIDO",
						"OBSERVA\u00C7\u00D5ES DO PEDIDO" }) {
			boolean[] columnEditables = new boolean[] { true, false, false, false, false, true, false, true, false,
					true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbPos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbPos.getColumnModel().getColumn(2).setPreferredWidth(300);
		tbPos.getColumnModel().getColumn(3).setPreferredWidth(150);
		tbPos.getColumnModel().getColumn(4).setPreferredWidth(100);
		tbPos.getColumnModel().getColumn(5).setPreferredWidth(100);
		tbPos.getColumnModel().getColumn(6).setPreferredWidth(120);
		tbPos.getColumnModel().getColumn(7).setPreferredWidth(120);
		tbPos.getColumnModel().getColumn(8).setPreferredWidth(300);
		tbPos.getColumnModel().getColumn(9).setPreferredWidth(500);

		// Sorter
		DefaultTableModel model = (DefaultTableModel) tbPos.getModel();
		model.setNumRows(0);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		tbPos.setRowSorter(sorter);

		spPos.setViewportView(tbPos);

		JButton button = new JButton("Imprimir");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (tbPos.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!",
							"Imprimir Planilha", JOptionPane.INFORMATION_MESSAGE);

				} else {
					
					Relatorio relatorio = new Relatorio();

					try {

						relatorio.gerarPesquisaOrdemServico();

					} catch (JRException e1) {

						e1.printStackTrace();
					}

				}

			}
		});
		button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		button.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/imprimir.png")));
		button.setFocusPainted(false);
		button.setBorder(null);
		button.setActionCommand("Cancel");
		button.setBounds(836, 96, 105, 35);
		getContentPane().add(button);

		btnVoltar = new JButton("Sair");
		btnVoltar.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}
		});
		btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVoltar.setFocusPainted(false);
		btnVoltar.setActionCommand("Cancel");
		btnVoltar.setBounds(829, 562, 115, 35);
		getContentPane().add(btnVoltar);

		JPanel pPesquisarOS = new JPanel();
		pPesquisarOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPesquisarOS.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
				"Pesquisar ID Ordem Servi\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPesquisarOS.setBounds(10, 5, 215, 65);
		getContentPane().add(pPesquisarOS);
		pPesquisarOS.setLayout(null);

		JLabel lbIDOS = new JLabel("Id OS");
		lbIDOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbIDOS.setBounds(12, 5, 48, 30);
		pPesquisarOS.add(lbIDOS);

		TamanhoMaxTextField TamanhoMax = new TamanhoMaxTextField();
		TamanhoMax.setMaxChars(7);

		tfPesquisarCampoOS = new JTextField();
		tfPesquisarCampoOS.setHorizontalAlignment(SwingConstants.CENTER);
		tfPesquisarCampoOS.setForeground(Color.BLACK);
		tfPesquisarCampoOS.setBounds(12, 28, 70, 25);
		pPesquisarOS.add(tfPesquisarCampoOS);
		tfPesquisarCampoOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfPesquisarCampoOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfPesquisarCampoOS.setColumns(10);

		// Setar focus em cbEmpresa

		final JTextField jtf = tfPesquisarCampoOS;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jtf.requestFocusInWindow();
			}
		});

		tfPesquisarCampoOS.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnPesquisarIDOS);

			}

			@Override
			public void focusLost(FocusEvent e) {

				getRootPane().setDefaultButton(null);

			}
		});

		tfPesquisarCampoOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {

				String caracteres = "0987654321";

				if (!caracteres.contains(ev.getKeyChar() + "")) {

					ev.consume();

				}

			}

		});
		tfPesquisarCampoOS.setDocument(TamanhoMax);

		btnPesquisarIDOS = new JButton("Pesquisar");
		btnPesquisarIDOS.setFocusPainted(false);
		btnPesquisarIDOS.setBorder(null);
		btnPesquisarIDOS.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisarIDOS.setBounds(96, 19, 100, 35);
		pPesquisarOS.add(btnPesquisarIDOS);
		btnPesquisarIDOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Resultados", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 475, 934, 87);
		getContentPane().add(panel);

		lblTextoPesq = new JLabel("");
		lblTextoPesq.setBounds(74, 11, 498, 30);
		panel.add(lblTextoPesq);
		lblTextoPesq.setForeground(Color.BLACK);
		lblTextoPesq.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		lblTextoTotal = new JLabel("");
		lblTextoTotal.setBounds(129, 54, 52, 30);
		panel.add(lblTextoTotal);
		lblTextoTotal.setForeground(Color.BLACK);
		lblTextoTotal.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		JLabel lblRespPesq = new JLabel("Pesquisa:");
		lblRespPesq.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblRespPesq.setBounds(10, 11, 69, 30);
		panel.add(lblRespPesq);

		JLabel lblPesTotal = new JLabel("Total de Recebidos:");
		lblPesTotal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPesTotal.setBounds(10, 54, 118, 30);
		panel.add(lblPesTotal);

		lblPeriodo = new JLabel("");
		lblPeriodo.setForeground(Color.BLACK);
		lblPeriodo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPeriodo.setBounds(198, 33, 374, 30);
		panel.add(lblPeriodo);

		JLabel lblResReg = new JLabel("Registros:");
		lblResReg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblResReg.setBounds(10, 33, 69, 30);
		panel.add(lblResReg);

		lblTexReg = new JLabel("");
		lblTexReg.setForeground(Color.BLACK);
		lblTexReg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblTexReg.setBounds(74, 33, 45, 30);
		panel.add(lblTexReg);

		lblLogado = new JLabel("");
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());
		lblLogado.setBounds(10, 567, 600, 25);
		getContentPane().add(lblLogado);

		JPanel pEstagio = new JPanel();
		pEstagio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pEstagio.setLayout(null);
		pEstagio.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Pesquisar Est\u00E1gios",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pEstagio.setBounds(235, 5, 561, 65);
		getContentPane().add(pEstagio);

		JLabel lblDataEntrada = new JLabel("Data Entrada");
		lblDataEntrada.setBounds(14, 5, 92, 30);
		pEstagio.add(lblDataEntrada);
		lblDataEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDataEntrada = new JDateChooser();
		dcDataEntrada.setBounds(14, 28, 100, 25);
		pEstagio.add(dcDataEntrada);
		dcDataEntrada.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcDataEntrada.setFocusTraversalPolicyProvider(true);
		dcDataEntrada.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		JLabel lblEstagioOS = new JLabel("Est\u00E1gio");
		lblEstagioOS.setBounds(128, 5, 46, 30);
		pEstagio.add(lblEstagioOS);
		lblEstagioOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		cbListarTodas = new JComboBox<String>();
		cbListarTodas.setBounds(128, 28, 285, 25);
		pEstagio.add(cbListarTodas);
		cbListarTodas.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbListarTodas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbListarTodas.setModel(new DefaultComboBoxModel<String>(
				new String[] { "SELECIONE...", "IMPRESS\u00C3O", "LAMINA\u00C7\u00C3O / EXPEDI\u00C7\u00C3O",
						"ENTREGAR", "PEDIDO FINALIZADO", "ORDEM DE SERVI\u00C7O CANCELADA" }));

		btnListarTodas = new JButton("Pesquisar");
		btnListarTodas.setBounds(427, 19, 100, 35);
		pEstagio.add(btnListarTodas);
		btnListarTodas.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnListarTodas.setFocusPainted(false);
		btnListarTodas.setBorder(null);
		btnListarTodas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnListarTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				lblTextoPesq.setText("");
				lblTextoTotal.setText("");
				lblTexReg.setText("");
				lblPeriodo.setText("");

				campo3 = "";
				campo4 = "";
				dataF3 = null;

				OrdemServicoRN osRN = new OrdemServicoRN();

				DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

				model.setNumRows(0);

				if (cbListarTodas.getSelectedItem().equals("Selecione...")) {

					JOptionPane.showMessageDialog(null, "Selecione um estágio!");

				} else if (dcDataEntrada.getDate() == null) {

					int total = 0;
					int registros = 0;

					String dataBr, dataBr2 = null;

					listaOS = osRN.listarOrdemServico(String.valueOf(cbListarTodas.getSelectedItem()).toUpperCase());

					campo3 = String.valueOf(cbListarTodas.getSelectedItem());
					temp = "pesquisar_estagio_sem_data";

					if (listaOS.isEmpty() == true) {

						lblTextoPesq.setText("");
						lblTextoTotal.setText("");
						lblTexReg.setText("");
						lblPeriodo.setText("");

						cbListarTodas.setSelectedIndex(0);
						tfPesquisarCampoOS.setText("");

						dcDataEntrada.setDate(null);

						JOptionPane.showMessageDialog(null, "Não há clientes nesse estágio!");

					} else {

						for (OrdemServico o : listaOS) {

							dataBr = formatBR.format(o.getDtRecebimento());
							dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

							String naoDefinido = "N/D";

							if (o.getTotalFinal() == null) {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							} else {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())),
										tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							}

						}

						dcDataEntrada.setDate(null);

						lblTextoPesq.setText(String.valueOf(cbListarTodas.getSelectedItem()).toUpperCase());
						lblTextoTotal.setText(String.valueOf(tresDigitos.format(total)));
						lblTexReg.setText(String.valueOf(tresDigitos.format(registros)));

						cbListarTodas.setSelectedIndex(0);

					}

				} else {

					java.util.Date dataUtil = dcDataEntrada.getDate();
					java.sql.Date dataEntrada = new java.sql.Date(dataUtil.getTime());
					dataF3 = new java.sql.Date(dataUtil.getTime());

					int total = 0;
					int registros = 0;

					String dataBr, dataBr2 = null;

					listaOS = osRN.listarPesquisarDataEntrada(
							String.valueOf(cbListarTodas.getSelectedItem()).toUpperCase(), dataEntrada);

					campo4 = String.valueOf(cbListarTodas.getSelectedItem());
					temp = "pesquisar_estagio_com_data";

					model.setNumRows(0);

					if (listaOS.isEmpty() == true) {

						lblTextoPesq.setText("");
						lblTextoTotal.setText("");
						lblTexReg.setText("");
						lblPeriodo.setText("");

						cbListarTodas.setSelectedIndex(0);
						tfPesquisarCampoOS.setText("");
						dcDataEntrada.setDate(null);

						JOptionPane.showMessageDialog(null, "Não há clientes nesse estágio!","Pesquisar Ordem de Servico", JOptionPane.OK_CANCEL_OPTION);

					} else {

						for (OrdemServico o : listaOS) {

							dataBr = formatBR.format(o.getDtRecebimento());
							dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

							String naoDefinido = "N/D";

							if (o.getTotalFinal() == null) {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							} else {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())),
										tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							}

						}

						dcDataEntrada.setDate(null);

						lblTextoPesq.setText(String.valueOf(cbListarTodas.getSelectedItem()).toUpperCase());
						lblTextoTotal.setText(String.valueOf(total));
						lblTexReg.setText(String.valueOf(registros));

						cbListarTodas.setSelectedIndex(0);

					}

				}

			}
		});

		cbListarTodas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnListarTodas);

			}

			@Override
			public void focusLost(FocusEvent e) {

				getRootPane().setDefaultButton(null);

			}
		});

		JPanel pPesquisarCompleto = new JPanel();
		pPesquisarCompleto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPesquisarCompleto.setLayout(null);
		pPesquisarCompleto.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
				"Pesquisar Ordem de Servi\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPesquisarCompleto.setBounds(10, 68, 709, 65);
		getContentPane().add(pPesquisarCompleto);

		JLabel lblCampoPesquisa = new JLabel("Campos da pesquisa");
		lblCampoPesquisa.setBounds(10, 5, 138, 30);
		pPesquisarCompleto.add(lblCampoPesquisa);
		lblCampoPesquisa.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JComboBox<Object> cbCampoInformar = new JComboBox<Object>();
		cbCampoInformar.setBounds(10, 29, 175, 25);
		pPesquisarCompleto.add(cbCampoInformar);
		cbCampoInformar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbCampoInformar.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		cbCampoInformar
				.setModel(new DefaultComboBoxModel<Object>(new String[] { "SELECIONE O CAMPO", "STATUS", "CLIENTE" }));

		JLabel lblInformar = new JLabel("Informar na pesquisa");
		lblInformar.setBounds(194, 7, 145, 30);
		pPesquisarCompleto.add(lblInformar);
		lblInformar.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfInformar = new JTextField();
		tfInformar.setBounds(195, 29, 165, 25);
		pPesquisarCompleto.add(tfInformar);
		tfInformar.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfInformar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfInformar.setColumns(10);

		JLabel lblDtInicialOS = new JLabel("Data Inicial");
		lblDtInicialOS.setBounds(369, 7, 86, 30);
		pPesquisarCompleto.add(lblDtInicialOS);
		lblDtInicialOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDtInicialOS = new JDateChooser();
		dcDtInicialOS.setBounds(370, 29, 100, 25);
		pPesquisarCompleto.add(dcDtInicialOS);
		dcDtInicialOS.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcDtInicialOS.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		JLabel lblDtFinalOS = new JLabel("Data Final");
		lblDtFinalOS.setBounds(479, 7, 86, 30);
		pPesquisarCompleto.add(lblDtFinalOS);
		lblDtFinalOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDtFinalOS = new JDateChooser();
		dcDtFinalOS.setBounds(480, 29, 100, 25);
		pPesquisarCompleto.add(dcDtFinalOS);
		dcDtFinalOS.setFocusTraversalPolicyProvider(true);
		dcDtFinalOS.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcDtFinalOS.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		btnPesquisarOS = new JButton("Pesquisar");
		btnPesquisarOS.setBounds(590, 19, 105, 35);
		pPesquisarCompleto.add(btnPesquisarOS);
		btnPesquisarOS.setFocusPainted(false);
		btnPesquisarOS.setBorder(null);
		btnPesquisarOS.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisarOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JButton btnVisualizarOs = new JButton("Visualizar OS");
		btnVisualizarOs
				.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/ordem servico 2.png")));
		btnVisualizarOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int posicao = tbPos.getSelectedRow();

				if (tbPos.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!",
							"Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (tbPos.getSelectedRow() == -1) {

						JOptionPane.showMessageDialog(null, "Selecione o registro na planiha!");

					} else {

						int id_os = Integer.parseInt(tbPos.getValueAt(posicao, 1).toString());

						OrdemServicoRN osRN = new OrdemServicoRN();
						OrdemServico os = new OrdemServico();

						os = osRN.mostrarOS(id_os);

						EstadoOSRN estadoOSRN = new EstadoOSRN();

						int estagio = estadoOSRN.buscarEstagio(id_os);

						if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("CRACHÁS / IMPRESSÃO")) {

							telaOS.mudançaEstagiosOperadorImpressao(estagio);

						} else if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("CRACHÁS / LAMINAÇÃO")) {

							telaOS.mudançaEstagiosOperadorLaminacao(estagio);

						} else if (TelaLogin.getOperador().getAcesso().equalsIgnoreCase("OBSERVADOR")) {

							telaOS.mudançaEstagiosObservador(estagio);

						} else if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("RECEPÇÃO")) {

							telaOS.mudançaEstagiosRecepcionista(estagio);

						}

						if (estagio == 0) {

							estagio = 1;
						}

						try {

							String entrega = "";

							ClienteRN clienteRN = new ClienteRN();
							Cliente cliente = new Cliente();

							cliente = clienteRN.mostrarCliente(os.getId_cliente());

							String mensagem = String.valueOf(estagio);

							mensagem = TelaOrdemServico.estagioOS(mensagem);

							telaOS.getLblOS().setText("ORDEM DE SERVIÇO Nº " + cincoDigitos.format(os.getId_os())
									+ " - " + cliente.getNome().toUpperCase());

							telaOS.setEstagio(estagio);

							telaOS.getTfOS().setText(String.valueOf(cincoDigitos.format(os.getId_os())));

							telaOS.getTfMensagem().setText(mensagem);
							telaOS.getTfTotal().setText(tresDigitos.format(Integer.valueOf(os.getTotal())));
							telaOS.getDtRecebimento().setText(formatBR.format(os.getDtRecebimento()));
							telaOS.getDtPrevista().setText(formatBR.format(os.getDtPrevista()));

							if (os.getTotalFinal() != null) {

								telaOS.getTfTotalExpedido()
										.setText(tresDigitos.format(Integer.valueOf(os.getTotalFinal())));

							} else {

								telaOS.getTfTotalExpedido().setText(os.getTotalFinal());

							}

							telaOS.getTpMotivo().setText(os.getMotivo());

							telaOS.getTfClienteOS().setText(cliente.getNome());
							telaOS.getTfVendedorOS().setText(cliente.getVendedor());
							telaOS.getTfContatoOS().setText(cliente.getContato());
							telaOS.getTfEmailOS().setText(cliente.getEmail().toLowerCase());
							telaOS.getTfFone1OS().setText(cliente.getFone1());
							telaOS.getTfFone2OS().setText(cliente.getFone2());
							telaOS.getTpDetalhesOS().setText(cliente.getDetalhes());

							if (cliente.isEntrega() == true) {

								entrega = "Sim";

							} else {

								entrega = "Não";
							}

							telaOS.getCbEntrega().setSelectedItem(entrega);
							telaOS.getTfEntrega().setText(entrega);
							telaOS.getCbUnidade().setSelectedItem(cliente.getUnidade());
							telaOS.getTfUnidade().setText(cliente.getUnidade());

							telaOS.setLocationRelativeTo(null);
							telaOS.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}

						if (temp.equalsIgnoreCase("numero")) {

							listaOS = osRN.listarPesquisarOS(pesquisa1);

							DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

							model.setNumRows(0);

							int total = 0;
							int registros = 0;

							String dataBr, dataBr2 = null;

							for (OrdemServico o : listaOS) {

								dataBr = formatBR.format(o.getDtRecebimento());
								dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

								String naoDefinido = "N/D";

								if (o.getTotalFinal() == null) {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr,
											dataBr2, o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								} else {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())),
											tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
											o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								}

							}

							try {

								tbPos.setRowSelectionInterval(posicao, posicao);

							} catch (Exception e) {

								e.printStackTrace();

							}

						} else if (temp.equalsIgnoreCase("completa")) {

							listaOS = osRN.pesquisarOScompleta(pesquisa2.toUpperCase(), campo2.toUpperCase(), dataI2,
									dataF2);

							DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

							model.setNumRows(0);

							int total = 0;
							int registros = 0;

							String dataBr, dataBr2 = null;

							for (OrdemServico o : listaOS) {

								dataBr = formatBR.format(o.getDtRecebimento());
								dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

								String naoDefinido = "N/D";

								if (o.getTotalFinal() == null) {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr,
											dataBr2, o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								} else {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())),
											tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
											o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								}

							}

							if (tbPos.getRowCount() != 0) {

								tbPos.setRowSelectionInterval(posicao, posicao);

							}

						} else if (temp.equalsIgnoreCase("pesquisar_estagio_sem_data")) {

							listaOS = osRN.listarOrdemServico(campo3.toUpperCase());

							DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

							model.setNumRows(0);

							int total = 0;
							int registros = 0;

							String dataBr, dataBr2 = null;

							for (OrdemServico o : listaOS) {

								dataBr = formatBR.format(o.getDtRecebimento());
								dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

								String naoDefinido = "N/D";

								if (o.getTotalFinal() == null) {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr,
											dataBr2, o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								} else {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())),
											tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
											o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								}

							}

							tbPos.setRowSelectionInterval(posicao, posicao);

							lblTextoPesq.setText(String.valueOf(campo3.toUpperCase()));
							lblTextoTotal.setText(String.valueOf(total));
							lblTexReg.setText(String.valueOf(registros));

							cbListarTodas.setSelectedIndex(0);

						} else if (temp.equalsIgnoreCase("pesquisar_estagio_com_data")) {

							listaOS = osRN.listarPesquisarDataEntrada(campo4.toUpperCase(), dataF3);

							DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

							model.setNumRows(0);

							int total = 0;
							int registros = 0;

							String dataBr, dataBr2 = null;

							for (OrdemServico o : listaOS) {

								dataBr = formatBR.format(o.getDtRecebimento());
								dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

								String naoDefinido = "N/D";

								if (o.getTotalFinal() == null) {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr,
											dataBr2, o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								} else {

									Object[] linha = { tresDigitos.format(registros + 1),
											cincoDigitos.format(o.getId_os()), o.getCliente().getNome(),
											o.getCliente().getVendedor(),
											tresDigitos.format(Integer.valueOf(o.getTotal())),
											tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
											o.getStatus(), o.getMotivo() };

									total = Integer.valueOf(o.getTotal()) + total++;

									registros++;

									model.addRow(linha);

								}

							}

							if (tbPos.getRowCount() != 0) {

								tbPos.setRowSelectionInterval(posicao, posicao);

							}

							lblTextoPesq.setText(String.valueOf(campo4.toUpperCase()));
							lblTextoTotal.setText(String.valueOf(total));
							lblTexReg.setText(String.valueOf(registros));

							cbListarTodas.setSelectedIndex(0);

						}

					}

				}

			}
		});
		btnVisualizarOs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVisualizarOs.setBounds(806, 54, 135, 35);
		getContentPane().add(btnVisualizarOs);

		JButton btnAbrirPlanilha = new JButton("Abrir Planilha");
		btnAbrirPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int posicao = tbPos.getSelectedRow();

				int linhaSelecionada = 0;

				if (tbPos.getSelectedRow() == -1) {

					JOptionPane.showMessageDialog(null, "Selecione o registro na planiha!", "Abrir Planilha", JOptionPane.INFORMATION_MESSAGE);

				} else {

					int id_os = Integer.parseInt(tbPos.getValueAt(posicao, 1).toString());

					int resposta = JOptionPane.showConfirmDialog(null, "Deseja abrir a planilha deste dia?",
							"Abrir Planilha", JOptionPane.OK_CANCEL_OPTION);

					if (resposta == JOptionPane.OK_OPTION) {

						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

						java.util.Date dataEntrada = null;

						try {

							dataEntrada = formato.parse(tbPos.getValueAt(posicao, 7).toString());

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

						java.sql.Date dataEscolhida = new java.sql.Date(dataEntrada.getTime());

						PlanilhaRN planilhaRN = new PlanilhaRN();

						Planilha planilha = new Planilha();

						planilha = planilhaRN.verificarPlanilha(dataEscolhida);

						ProducaoRN producaoRN = new ProducaoRN();

						int entradas = 0;
						int valorTotal = 0;

						String dataBr = formatBR.format(planilha.getDataPlanilha());

						DefaultTableModel model = (DefaultTableModel) TelaProducao.tbProducao.getModel();
						model.setNumRows(0);

						RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
						TelaProducao.tbProducao.setRowSorter(sorter);

						TelaProducao.listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

						for (Producao p : TelaProducao.listaProducao) {

							dataBr = formatBR.format(p.getDataEntrada());

							Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
									p.getEmpresa(), p.getTipo(), p.getOperador(),
									tresDigitos.format(Integer.valueOf(p.getQuantidade())), dataBr,
									p.getOrdemservico().getStatus(), p.getObservacoes() };

							if (p.getQuantidade() == null) {

								valorTotal = valorTotal + 0;

							} else {

								valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

							}

							if (p.getOrdemservico().getId_os() == id_os) {

								linhaSelecionada = entradas;
							}

							entradas++;

							model.addRow(linha);

						}

						if (TelaProducao.tbProducao.getRowCount() != 0) {

							TelaProducao.tbProducao.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);

						}

						TelaProducao.lblDataPlanilha.setText(dataBr);
						TelaProducao.lblValorTotal.setText(tresDigitos.format(valorTotal));
						TelaProducao.lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

						dispose();

					}

				}

			}
		});
		btnAbrirPlanilha.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/planilha.png")));
		btnAbrirPlanilha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAbrirPlanilha.setBounds(806, 11, 135, 35);
		getContentPane().add(btnAbrirPlanilha);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tbPos.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!", "Exportar Planilha", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (listaOS.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Planilha vazia", "Exportar Planilha",
								JOptionPane.ERROR_MESSAGE);

					} else {

						ExportarCsv exportarCsv = new ExportarCsv();

						String caminho = "C:/NE.Crachás/pesquisaOS";

						boolean sucesso;

						try {

							sucesso = exportarCsv.exportarPesquisarOsCsv(caminho, listaOS);

							if (sucesso == true) {

								try {

									// Executa o excel
									Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + caminho + ".csv");

								} catch (IOException e1) {

									JOptionPane.showMessageDialog(null,
											"Caminho do Arquivo esta errado, contate os Desenvolvedores " + e1,
											"Exportar tabela", JOptionPane.ERROR_MESSAGE);

								}

							}

						} catch (HeadlessException | IOException e2) {

							JOptionPane.showMessageDialog(null, "Erro, o arquivo já está aberto!\n " + e2,
									"Exportar Lançamento", JOptionPane.ERROR_MESSAGE);

						}

					}

				}

			}

		});
		btnExportar.setIcon(new ImageIcon(TelaPesquisarOrdemServico.class.getResource("/br/com/crachas/image/EXPORTAR.png")));
		btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnExportar.setFocusPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setActionCommand("Cancel");
		btnExportar.setBounds(724, 96, 105, 35);
		getContentPane().add(btnExportar);
		btnPesquisarOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				pesquisa2 = "";
				campo2 = "";
				dataI2 = null;
				dataF2 = null;

				DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

				model.setNumRows(0);

				if (tfInformar.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite a informação a ser pesquisa!","Pesquisar Ordem de Servico", JOptionPane.OK_CANCEL_OPTION);

				} else if (cbCampoInformar.getSelectedItem().equals("Selecione o campo")) {

					JOptionPane.showMessageDialog(null, "Selecione o campo para fazer a pesquisa!","Pesquisar Ordem de Servico", JOptionPane.OK_CANCEL_OPTION);

				} else if (dcDtInicialOS.getDate() == null || dcDtFinalOS.getDate() == null) {

					JOptionPane.showMessageDialog(null, "Digite as datas inicial e final!");

				} else if (dcDtFinalOS.getDate().before(dcDtInicialOS.getDate())) {

					JOptionPane.showMessageDialog(null, "A data final não pode ser anterior a data inicial!","Pesquisar Ordem de Servico", JOptionPane.OK_CANCEL_OPTION);

				} else {

					int total = 0;
					int registros = 0;

					String dataBr, dataBr2 = null;

					OrdemServicoRN osRN = new OrdemServicoRN();

					java.util.Date dataUtil = dcDtInicialOS.getDate();
					java.sql.Date dataInicial = new java.sql.Date(dataUtil.getTime());

					java.util.Date dataUtil2 = dcDtFinalOS.getDate();
					java.sql.Date dataFinal = new java.sql.Date(dataUtil2.getTime());

					listaOS = osRN.pesquisarOScompleta(tfInformar.getText().toUpperCase(),
							String.valueOf(cbCampoInformar.getSelectedItem()).toUpperCase(), dataInicial, dataFinal);

					temp = "completa";

					pesquisa2 = tfInformar.getText().toUpperCase();
					campo2 = String.valueOf(cbCampoInformar.getSelectedItem());
					dataI2 = dataInicial;
					dataF2 = dataFinal;

					if (listaOS.isEmpty() == true) {

						tfInformar.setText("");
						cbCampoInformar.setSelectedIndex(0);
						dcDtInicialOS.setDate(null);
						dcDtFinalOS.setDate(null);

						lblTextoPesq.setText("");
						lblTextoTotal.setText("");
						lblTexReg.setText("");
						lblPeriodo.setText("");

						model.setNumRows(0);

					} else {

						for (OrdemServico o : listaOS) {

							dataBr = formatBR.format(o.getDtRecebimento());
							dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

							String naoDefinido = "N/D";

							if (o.getTotalFinal() == null) {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							} else {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())),
										tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							}

						}

						dataBr = formatBR.format(dataInicial);
						dataBr2 = formatBR.format(dataFinal);

						lblTextoPesq
								.setText("CAMPO -  " + String.valueOf(cbCampoInformar.getSelectedItem()).toUpperCase()
										+ "  //  PESQUISA -  " + tfInformar.getText().toUpperCase());
						lblTexReg.setText(String.valueOf(registros));
						lblTextoTotal.setText(String.valueOf(total));
						lblPeriodo.setText("PERÍODO DE  " + dataBr + "  ATÉ  " + dataBr2);

						tfInformar.setText("");
						cbCampoInformar.setSelectedIndex(0);
						dcDtInicialOS.setDate(null);
						dcDtFinalOS.setDate(null);

					}

				}

			}
		});
		btnPesquisarIDOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				lblTextoPesq.setText("");
				lblTextoTotal.setText("");
				lblTexReg.setText("");
				lblPeriodo.setText("");
				cbListarTodas.setSelectedIndex(0);

				pesquisa1 = "";

				int total = 0;
				int registros = 0;

				if (tfPesquisarCampoOS.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite a informação a pesquisar!", "Pesquisar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

					DefaultTableModel model = (DefaultTableModel) tbPos.getModel();
					model.setNumRows(0);

				} else {

					String dataBr, dataBr2 = null;

					OrdemServicoRN osRN = new OrdemServicoRN();

					listaOS = osRN.listarPesquisarOS(tfPesquisarCampoOS.getText());

					temp = "numero";
					pesquisa1 = tfPesquisarCampoOS.getText();

					DefaultTableModel model = (DefaultTableModel) tbPos.getModel();

					model.setNumRows(0);

					if (listaOS.isEmpty() == true) {

						lblTextoPesq.setText("");
						lblTextoTotal.setText("");
						lblTexReg.setText("");
						lblPeriodo.setText("");

						tfPesquisarCampoOS.setText("");

					} else {

						for (OrdemServico o : listaOS) {

							dataBr = formatBR.format(o.getDtRecebimento());
							dataBr2 = formatBR.format(o.getPlanilha().getDataPlanilha());

							String naoDefinido = "N/D";

							if (o.getTotalFinal() == null) {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())), naoDefinido, dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							} else {

								Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(o.getId_os()),
										o.getCliente().getNome(), o.getCliente().getVendedor(),
										tresDigitos.format(Integer.valueOf(o.getTotal())),
										tresDigitos.format(Integer.valueOf(o.getTotalFinal())), dataBr, dataBr2,
										o.getStatus(), o.getMotivo() };

								total = Integer.valueOf(o.getTotal()) + total++;

								registros++;

								model.addRow(linha);

							}

						}

						lblTextoPesq.setText("ID DA OS INFORMADA - " + tfPesquisarCampoOS.getText());
						lblTextoTotal.setText(String.valueOf(tresDigitos.format(total)));
						lblTexReg.setText(String.valueOf(tresDigitos.format(registros)));

						tfPesquisarCampoOS.setText("");

					}

				}

			}
		});
	}

	public JButton getBtnVoltar() {
		return btnVoltar;
	}

	public void setBtnVoltar(JButton btnVoltar) {
		this.btnVoltar = btnVoltar;
	}
}
