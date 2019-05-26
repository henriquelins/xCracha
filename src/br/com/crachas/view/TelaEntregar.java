package br.com.crachas.view;

import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;
import br.com.crachas.controller.EntregarPedidos;
import br.com.crachas.controller.EstadoOS;
import br.com.crachas.controller.EstadoOSRN;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.OrdemServicoRN;
import br.com.crachas.controller.PedidoFinalizado;
import br.com.crachas.controller.Relatorio;
import br.com.crachas.uteis.ExportarCsv;
import net.sf.jasperreports.engine.JRException;

public class TelaEntregar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -952039353080322951L;
	private final JPanel cpRecepcao = new JPanel();
	public static List<EntregarPedidos> listaEntregar;
	public static List<PedidoFinalizado> listaPedidosFinalizados;
	public static List<EntregarPedidos> listaRelatorio;
	private EstadoOS estadoOS;
	private JTable tabEntregar;
	private static int idOS;
	private static boolean salvou = false;
	private JTable tabPedidosFinalizados;
	private JLabel lblLogado;
	private JTextField tfClienteFinalizado;
	private JTextField tfClienteEntregar;
	private JButton btnPesquisarPorPeriodoEntregar;
	private java.sql.Date DataTemp = null;
	private java.sql.Date DataTemp2 = null;
	private String clienteTemp;
	private static String numeroOSTemp;
	private boolean osPesq = false;
	private boolean todosPesq = false;
	private boolean nomeEdatas = false;
	private boolean apenasDatas = false;
	public static List<OrdemServico> listaOS;
	private JButton btnSair;
	private JDateChooser dcDataInicialEntregar;
	private JDateChooser dcDataFinalEntregar;
	private JDateChooser dcDataInicialFinalizado;
	private JDateChooser dcDataFinalFinalizado;
	private JTextField tfNumeroOs;
	private JLabel lblTotalPedido;
	private JLabel lblQuantidadeTotal;
	private JTextField tfPedidoFinalizado;
	private JTable tbRelatorios;
	private JDateChooser dcRelatorios;
	private JComboBox<String> cbRelatorios;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaEntregar dialog = new TelaEntregar();
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
	public TelaEntregar() {

		setResizable(false);
		setModal(true);
		setTitle("Entregar Pedido");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaEntregar.class.getResource("/br/com/crachas/image/ne.png")));
		setBounds(100, 100, 960, 630);

		estadoOS = new EstadoOS();

		NumberFormat tresDigitos = new DecimalFormat("000");
		NumberFormat cincoDigitos = new DecimalFormat("00000");
		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

		getContentPane().setLayout(new BorderLayout());
		cpRecepcao.setBorder(null);
		getContentPane().add(cpRecepcao, BorderLayout.CENTER);
		cpRecepcao.setLayout(null);

		JTabbedPane tpEntregarPedido = new JTabbedPane(JTabbedPane.TOP);

		tpEntregarPedido.setBorder(null);
		tpEntregarPedido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tpEntregarPedido.setBounds(0, 0, 954, 534);
		cpRecepcao.add(tpEntregarPedido);

		JPanel pEntregar = new JPanel();
		pEntregar.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {

				DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
				model.setNumRows(0);

				tfClienteEntregar.setText("");
				dcDataInicialEntregar.setDate(null);

			}
		});
		tpEntregarPedido.addTab("Entregar", new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/entregar.png")),
				pEntregar, null);
		pEntregar.setLayout(null);

		JPanel pEntrega = new JPanel();
		pEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pEntrega.setLayout(null);
		pEntrega.setBounds(0, 0, 949, 490);
		pEntregar.add(pEntrega);

		JScrollPane spEntregar = new JScrollPane();
		spEntregar.setAutoscrolls(true);
		spEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		spEntregar.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spEntregar.setBounds(12, 72, 927, 361);
		pEntrega.add(spEntregar);

		tabEntregar = new JTable();
		tabEntregar.setAutoCreateRowSorter(true);
		tabEntregar.setGridColor(Color.LIGHT_GRAY);
		tabEntregar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabEntregar.setDefaultRenderer(Object.class, new TabRecepcao());

		// criando o cabeçalho cinza e linhas
		JTableHeader anHeader = tabEntregar.getTableHeader();
		anHeader.setBackground(Color.LIGHT_GRAY);
		// anHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.PLAIN , 10));
		// anHeader.setForeground(new Color(0,0,205));

		// criando o alinhamento do cabeçalho da tabela
		TableCellRenderer rendererFromHeader = tabEntregar.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);

		tabEntregar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tabEntregar.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "N\u00BA OS", "CLIENTE", "VENDEDOR", "DATA ENTRADA", "DATA PREVISTA ENTREGA",
						"TOTAL DO PEDIDO", "ENTREGAR NO CLIENTE", "OBSERVA\u00C7\u00D5ES DO PEDIDO" }));
		tabEntregar.getColumnModel().getColumn(0).setResizable(false);
		tabEntregar.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabEntregar.getColumnModel().getColumn(1).setResizable(false);
		tabEntregar.getColumnModel().getColumn(2).setResizable(false);
		tabEntregar.getColumnModel().getColumn(2).setPreferredWidth(300);
		tabEntregar.getColumnModel().getColumn(3).setResizable(false);
		tabEntregar.getColumnModel().getColumn(3).setPreferredWidth(150);
		tabEntregar.getColumnModel().getColumn(4).setResizable(false);
		tabEntregar.getColumnModel().getColumn(4).setPreferredWidth(150);
		tabEntregar.getColumnModel().getColumn(5).setPreferredWidth(150);
		tabEntregar.getColumnModel().getColumn(6).setPreferredWidth(150);
		tabEntregar.getColumnModel().getColumn(7).setPreferredWidth(130);
		tabEntregar.getColumnModel().getColumn(8).setPreferredWidth(500);

		// Sorter
		DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
		model.setNumRows(0);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		tabEntregar.setRowSorter(sorter);
		//

		spEntregar.setViewportView(tabEntregar);

		JPanel pDataEntrada = new JPanel();
		pDataEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pDataEntrada.setLayout(null);
		pDataEntrada.setBorder(
				new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Pesquisar N\u00BA Ordem de Servi\u00E7o",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pDataEntrada.setBounds(159, 4, 217, 62);
		pEntrega.add(pDataEntrada);

		JButton btnPesquisaOs = new JButton("Pesquisar");
		btnPesquisaOs.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisaOs.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnPesquisaOs.setBorder(null);
		btnPesquisaOs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnPesquisaOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listaEntregar = null;

				int registros = 0;
				int total = 0;

				String dataBr, dataBr2 = null;

				DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
				model.setNumRows(0);

				OrdemServicoRN osRN = new OrdemServicoRN();

				if (tfNumeroOs.getText().equals("") == true) {

					JOptionPane.showMessageDialog(null, "Digite o número da OS!", "Entregar Pedidos",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					listaEntregar = osRN.listarEntregarOS(tfNumeroOs.getText());

					if (listaEntregar.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "OS não encontrada!", "Entregar Pedidos",
								JOptionPane.INFORMATION_MESSAGE);

						tfNumeroOs.setText("");

						lblTotalPedido.setText("000");
						lblQuantidadeTotal.setText("000");

					} else {

						numeroOSTemp = tfNumeroOs.getText();

						apenasDatas = false;
						nomeEdatas = false;
						todosPesq = false;
						osPesq = true;

						for (EntregarPedidos r : listaEntregar) {

							dataBr = formatBR.format(r.getDtExpedicao());
							dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

							Object[] linha = { tresDigitos.format(registros + 1),
									cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
									r.getCliente().getVendedor(), dataBr, dataBr2,
									tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
									r.getObsevacoes() };

							if (r.getTotalFinal() == null) {

								r.setTotalFinal("000");

							}

							total = Integer.valueOf(r.getTotalFinal()) + total++;

							registros++;

							model.addRow(linha);

						}

						tfNumeroOs.setText("");
						lblTotalPedido.setText(tresDigitos.format(registros));
						lblQuantidadeTotal.setText(tresDigitos.format(total));

					}

				}
			}
		});
		btnPesquisaOs.setBounds(90, 19, 115, 35);
		pDataEntrada.add(btnPesquisaOs);

		tfNumeroOs = new JTextField();
		tfNumeroOs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnPesquisaOs);

			}

			@Override
			public void focusLost(FocusEvent arg0) {

				getRootPane().setDefaultButton(null);

			}
		});
		tfNumeroOs.setHorizontalAlignment(SwingConstants.CENTER);
		tfNumeroOs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfNumeroOs.setColumns(10);
		tfNumeroOs.setBounds(10, 24, 70, 25);
		pDataEntrada.add(tfNumeroOs);

		tfNumeroOs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {

				String caracteres = "0987654321";

				if (!caracteres.contains(ev.getKeyChar() + "")) {

					ev.consume();

				}

			}

		});

		JButton btnEntregar = new JButton("Entregar");
		btnEntregar.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/entregar.png")));
		btnEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEntregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tabEntregar.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma busca!",
							"Entregar Pedido", JOptionPane.INFORMATION_MESSAGE);

				} else {

					Date hoje = new Date(System.currentTimeMillis());
					Time hora = new Time(System.currentTimeMillis());

					OrdemServicoRN osRN = new OrdemServicoRN();

					int posicao = tabEntregar.getSelectedRow();

					if (posicao == -1) {

						JOptionPane.showMessageDialog(null, "Selecione um registro!", "Entregar Pedido",
								JOptionPane.INFORMATION_MESSAGE);

					} else {

						int resposta = JOptionPane.showConfirmDialog(null, "Entregar o Pedido?", "Cancelar",
								JOptionPane.YES_NO_OPTION);

						if (resposta == JOptionPane.OK_OPTION) {

							posicao = tabEntregar.getSelectedRow();

							int id_os = Integer.parseInt(tabEntregar.getValueAt(posicao, 1).toString());

							setIdOS(id_os);

							try {
								UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							TelaPortador JWindow = new TelaPortador();
							JWindow.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							JWindow.setLocationRelativeTo(null);
							JWindow.setVisible(true);

							if (salvou == true) {

								EstadoOSRN estadoOSRN = new EstadoOSRN();

								int estagio = estadoOSRN.buscarEstagio(id_os);

								int proximoEstado = 0;

								if (estagio == 3) {

									proximoEstado = Integer.valueOf(estagio) + 2;

								} else if (estagio == 4) {

									proximoEstado = Integer.valueOf(estagio) + 1;

								}

								estadoOS.setId_os(id_os);
								estadoOS.setEstagio(proximoEstado);
								estadoOS.setDataInicial(hoje);
								estadoOS.setHoraInicial(hora);
								estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

								estadoOSRN.criarEstadoOS(estadoOS);

								estagio = estadoOSRN.buscarEstagio(id_os);

								String mensagem = String.valueOf(estagio);

								mensagem = TelaOrdemServico.estagioOS(mensagem);

								osRN.atualizarStatus(id_os, mensagem);

								int registros = 0;
								int total = 0;

								String dataBr, dataBr2 = "";

								DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
								model.setNumRows(0);

								if (todosPesq == true) {

									listaEntregar = osRN.listarPedidoEntregar();

									for (EntregarPedidos r : listaEntregar) {

										dataBr = formatBR.format(r.getDtExpedicao());
										dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

										Object[] linha = { tresDigitos.format(registros + 1),
												cincoDigitos.format(r.getOrdemServico().getId_os()),
												r.getCliente().getNome(), r.getCliente().getVendedor(), dataBr, dataBr2,
												tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
												r.getObsevacoes() };

										if (r.getTotalFinal() == null) {

											r.setTotalFinal("000");

										}

										total = Integer.valueOf(r.getTotalFinal()) + total++;

										registros++;

										model.addRow(linha);

									}

									todosPesq = false;

									lblTotalPedido.setText(tresDigitos.format(registros));
									lblQuantidadeTotal.setText(tresDigitos.format(total));

								} else if (osPesq == true) {

									listaEntregar = osRN.listarEntregarOS(numeroOSTemp);

									for (EntregarPedidos r : listaEntregar) {

										dataBr = formatBR.format(r.getDtExpedicao());
										dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

										Object[] linha = { tresDigitos.format(registros + 1),
												cincoDigitos.format(r.getOrdemServico().getId_os()),
												r.getCliente().getNome(), r.getCliente().getVendedor(), dataBr, dataBr2,
												tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
												r.getObsevacoes() };

										if (r.getTotalFinal() == null) {

											r.setTotalFinal("000");

										}

										total = Integer.valueOf(r.getTotalFinal()) + total++;

										registros++;

										model.addRow(linha);

									}

									osPesq = false;
									numeroOSTemp = "";

									lblTotalPedido.setText(tresDigitos.format(registros));
									lblQuantidadeTotal.setText(tresDigitos.format(total));

								} else if (apenasDatas == true) {

									listaEntregar = osRN.listarEntregarPeriodo(DataTemp, DataTemp2);

									for (EntregarPedidos r : listaEntregar) {

										dataBr = formatBR.format(r.getDtExpedicao());
										dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

										Object[] linha = { tresDigitos.format(registros + 1),
												cincoDigitos.format(r.getOrdemServico().getId_os()),
												r.getCliente().getNome(), r.getCliente().getVendedor(), dataBr, dataBr2,
												tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
												r.getObsevacoes() };

										if (r.getTotalFinal() == null) {

											r.setTotalFinal("000");

										}

										total = Integer.valueOf(r.getTotalFinal()) + total++;

										registros++;

										model.addRow(linha);

									}

									DataTemp = null;
									DataTemp2 = null;
									apenasDatas = false;

									lblTotalPedido.setText(tresDigitos.format(registros));
									lblQuantidadeTotal.setText(tresDigitos.format(total));

								} else if (nomeEdatas == true) {

									listaEntregar = osRN.listarEntregarPeriodoENome(clienteTemp.toUpperCase(), DataTemp,
											DataTemp2);

									for (EntregarPedidos r : listaEntregar) {

										dataBr = formatBR.format(r.getDtExpedicao());
										dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

										Object[] linha = { tresDigitos.format(registros + 1),
												cincoDigitos.format(r.getOrdemServico().getId_os()),
												r.getCliente().getNome(), r.getCliente().getVendedor(), dataBr, dataBr2,
												tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
												r.getObsevacoes() };

										if (r.getTotalFinal() == null) {

											r.setTotalFinal("000");

										}

										total = Integer.valueOf(r.getTotalFinal()) + total++;

										registros++;

										model.addRow(linha);

									}

									DataTemp = null;
									DataTemp2 = null;
									nomeEdatas = false;
									clienteTemp = "";

									lblTotalPedido.setText(tresDigitos.format(registros));
									lblQuantidadeTotal.setText(tresDigitos.format(total));

								}

							} else {

								// JOptionPane.showMessageDialog(null, "O portador não foi salvo!", "Entregar
								// Pedido",
								// JOptionPane.INFORMATION_MESSAGE);

							}

						}

					}

				}
			}

		});
		btnEntregar.setBounds(427, 444, 115, 35);
		pEntrega.add(btnEntregar);

		JButton btnImprimirEntregar = new JButton("Imprimir");
		btnImprimirEntregar.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/imprimir.png")));
		btnImprimirEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnImprimirEntregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Relatorio relatorio = new Relatorio();

				if (tabEntregar.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Planilha vazia!", "Impressão Planilha",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					try {

						relatorio.gerarPlanilhaRecepcaoEntregar();

					} catch (JRException e1) {

						e1.printStackTrace();
					}

				}

			}

		});
		btnImprimirEntregar.setBounds(677, 444, 115, 35);
		pEntrega.add(btnImprimirEntregar);

		JPanel pPesClienteRecepcao = new JPanel();
		pPesClienteRecepcao.setLayout(null);
		pPesClienteRecepcao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPesClienteRecepcao.setBorder(
				new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Pesquisar Clientes na Recep\u00E7\u00E3o",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPesClienteRecepcao.setBounds(384, 4, 556, 62);
		pEntrega.add(pPesClienteRecepcao);

		JLabel lblClienteEntregar = new JLabel("Cliente");
		lblClienteEntregar.setBounds(14, 6, 169, 30);
		pPesClienteRecepcao.add(lblClienteEntregar);
		lblClienteEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfClienteEntregar = new JTextField();
		tfClienteEntregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				/*
				 * listaEntregar.clear(); dcDataRecepcao = null; dataRecep = false;
				 */

			}
		});
		tfClienteEntregar.setBounds(14, 29, 169, 25);
		pPesClienteRecepcao.add(tfClienteEntregar);
		tfClienteEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfClienteEntregar.setColumns(10);

		JLabel label_1 = new JLabel("Data Inicial");
		label_1.setBounds(197, 7, 99, 30);
		pPesClienteRecepcao.add(label_1);
		label_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDataInicialEntregar = new JDateChooser();
		dcDataInicialEntregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				/*
				 * listaEntregar.clear(); dcDataRecepcao = null; dataRecep = false;
				 */

			}
		});
		dcDataInicialEntregar.setBounds(197, 29, 100, 25);
		pPesClienteRecepcao.add(dcDataInicialEntregar);
		dcDataInicialEntregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcDataInicialEntregar.setFocusTraversalPolicyProvider(true);
		dcDataInicialEntregar.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		JLabel label_2 = new JLabel("Data Final");
		label_2.setBounds(312, 7, 99, 30);
		pPesClienteRecepcao.add(label_2);
		label_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDataFinalEntregar = new JDateChooser();
		dcDataFinalEntregar.setBounds(311, 29, 100, 25);
		pPesClienteRecepcao.add(dcDataFinalEntregar);
		dcDataFinalEntregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcDataFinalEntregar.setFocusTraversalPolicyProvider(true);
		dcDataFinalEntregar.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		btnPesquisarPorPeriodoEntregar = new JButton("Pesquisar");
		btnPesquisarPorPeriodoEntregar.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisarPorPeriodoEntregar.setBounds(425, 19, 115, 35);
		pPesClienteRecepcao.add(btnPesquisarPorPeriodoEntregar);
		btnPesquisarPorPeriodoEntregar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (tfClienteEntregar.getText().equals("")) {

					DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
					model.setNumRows(0);

					if (dcDataInicialEntregar.getDate() == null) {

						JOptionPane.showMessageDialog(null, "Digite a data inicial", "Entregar Pedidos",
								JOptionPane.INFORMATION_MESSAGE);

						model.setNumRows(0);

					} else if (dcDataFinalEntregar.getDate() == null) {

						JOptionPane.showMessageDialog(null, "Digite a data final", "Entregar Pedidos",
								JOptionPane.INFORMATION_MESSAGE);

						model.setNumRows(0);

					} else if (dcDataFinalEntregar.getDate().before(dcDataInicialEntregar.getDate())) {

						JOptionPane.showMessageDialog(null, "A data final não pode ser anterior a data inicial",
								"Entregar Pedidos", JOptionPane.INFORMATION_MESSAGE);

						model.setNumRows(0);

					} else {

						java.util.Date dataUtil = dcDataInicialEntregar.getDate();
						java.util.Date dataUtil2 = dcDataFinalEntregar.getDate();
						java.sql.Date DataInicial = new java.sql.Date(dataUtil.getTime());
						java.sql.Date DataFinal = new java.sql.Date(dataUtil2.getTime());

						int registros = 0;
						int total = 0;

						String dataBr, dataBr2 = null;

						model.setNumRows(0);

						OrdemServicoRN osRN = new OrdemServicoRN();

						listaEntregar = osRN.listarEntregarPeriodo(DataInicial, DataFinal);

						if (listaEntregar.isEmpty() == true) {

							JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!", "Entregar Pedidos",
									JOptionPane.INFORMATION_MESSAGE);

							model.setNumRows(0);

							tfClienteEntregar.setText("");
							dcDataInicialEntregar.setDate(null);
							dcDataFinalEntregar.setDate(null);

							apenasDatas = false;

							DataTemp = null;
							DataTemp2 = null;

							lblTotalPedido.setText("000");
							lblQuantidadeTotal.setText("000");

						} else {

							DataTemp = DataInicial;
							DataTemp2 = DataFinal;

							apenasDatas = true;
							nomeEdatas = false;
							todosPesq = false;
							osPesq = false;

							for (EntregarPedidos r : listaEntregar) {

								dataBr = formatBR.format(r.getDtExpedicao());
								dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

								Object[] linha = { tresDigitos.format(registros + 1),
										cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
										r.getCliente().getVendedor(), dataBr, dataBr2,
										tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
										r.getObsevacoes() };

								if (r.getTotalFinal() == null) {

									r.setTotalFinal("000");

								}

								total = Integer.valueOf(r.getTotalFinal()) + total++;

								registros++;

								model.addRow(linha);

							}

							tfClienteEntregar.setText("");
							dcDataInicialEntregar.setDate(null);
							dcDataFinalEntregar.setDate(null);

							lblTotalPedido.setText(tresDigitos.format(registros));
							lblQuantidadeTotal.setText(tresDigitos.format(total));

						}

					}

				} else {

					if ((!tfClienteFinalizado.equals("") && dcDataInicialEntregar.getDate() == null)
							|| (!tfClienteFinalizado.equals("") && dcDataFinalEntregar.getDate() == null)) {

						JOptionPane.showMessageDialog(null, "Digite ambas as datas!");

						DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
						model.setNumRows(0);

					} else {

						java.util.Date dataUtil = dcDataInicialEntregar.getDate();
						java.util.Date dataUtil2 = dcDataFinalEntregar.getDate();
						java.sql.Date DataInicial = new java.sql.Date(dataUtil.getTime());
						java.sql.Date DataFinal = new java.sql.Date(dataUtil2.getTime());

						int registros = 0;
						int total = 0;

						String dataBr, dataBr2 = null;

						DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
						model.setNumRows(0);

						OrdemServicoRN osRN = new OrdemServicoRN();

						listaEntregar = osRN.listarEntregarPeriodoENome(tfClienteEntregar.getText().toUpperCase(),
								DataInicial, DataFinal);

						if (listaEntregar.isEmpty() == true) {

							JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!", "Entregar Pedidos",
									JOptionPane.INFORMATION_MESSAGE);

							model.setNumRows(0);

							tfClienteEntregar.setText("");
							dcDataInicialEntregar.setDate(null);
							dcDataFinalEntregar.setDate(null);

							lblTotalPedido.setText("000");
							lblQuantidadeTotal.setText("000");

						} else {

							DataTemp = DataInicial;
							DataTemp2 = DataFinal;
							clienteTemp = tfClienteEntregar.getText().toUpperCase();

							apenasDatas = false;
							nomeEdatas = true;
							todosPesq = false;
							osPesq = false;

							for (EntregarPedidos r : listaEntregar) {

								dataBr = formatBR.format(r.getDtExpedicao());
								dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

								Object[] linha = { tresDigitos.format(registros + 1),
										cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
										r.getCliente().getVendedor(), dataBr, dataBr2,
										tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
										r.getObsevacoes() };

								if (r.getTotalFinal() == null) {

									r.setTotalFinal("000");

								}

								total = Integer.valueOf(r.getTotalFinal()) + total++;

								registros++;

								model.addRow(linha);

							}

							tfClienteEntregar.setText("");
							dcDataInicialEntregar.setDate(null);
							dcDataFinalEntregar.setDate(null);

							lblTotalPedido.setText(tresDigitos.format(registros));
							lblQuantidadeTotal.setText(tresDigitos.format(total));

						}

					}

				}

			}
		});
		btnPesquisarPorPeriodoEntregar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnPesquisarPorPeriodoEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnPesquisarPorPeriodoEntregar.setBorder(null);

		JButton btnVisualizarOsEntregar = new JButton("Visualizar OS");
		btnVisualizarOsEntregar.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/ordem servico 2.png")));
		btnVisualizarOsEntregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				OrdemServicoRN osRN = new OrdemServicoRN();
				OrdemServico os = new OrdemServico();
				String mensagem = "";

				if (tabEntregar.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma busca!");

				} else {

					int posicao = tabEntregar.getSelectedRow();

					if (posicao == -1) {

						JOptionPane.showMessageDialog(null, "Selecione um registro!");

					} else {

						TelaOrdemServico telaOS = new TelaOrdemServico();

						int id_os = Integer.parseInt(tabEntregar.getValueAt(posicao, 1).toString());

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

							mensagem = String.valueOf(estagio);

							mensagem = TelaOrdemServico.estagioOS(mensagem);

							telaOS.getLblOS().setText("ORDEM DE SERVIÇO Nº " + cincoDigitos.format(os.getId_os())
									+ " - " + cliente.getNome().toUpperCase());

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

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}

			}
		});
		btnVisualizarOsEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVisualizarOsEntregar.setBounds(801, 444, 138, 35);
		pEntrega.add(btnVisualizarOsEntregar);

		JButton btnMostarTodos = new JButton("Mostrar Todos");
		btnMostarTodos.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnMostarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int registros = 0;
				int total = 0;

				String dataBr, dataBr2 = null;

				DefaultTableModel model = (DefaultTableModel) tabEntregar.getModel();
				model.setNumRows(0);

				OrdemServicoRN osRN = new OrdemServicoRN();

				listaEntregar = osRN.listarPedidoEntregar();

				if (listaEntregar.isEmpty() == true) {

					JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

					lblTotalPedido.setText("000");
					lblQuantidadeTotal.setText("000");

				} else {

					apenasDatas = false;
					nomeEdatas = false;
					todosPesq = true;
					osPesq = false;

					for (EntregarPedidos r : listaEntregar) {

						dataBr = formatBR.format(r.getDtExpedicao());
						dataBr2 = formatBR.format(r.getOrdemServico().getDtPrevista());

						Object[] linha = { tresDigitos.format(registros + 1),
								cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
								r.getCliente().getVendedor(), dataBr, dataBr2,
								tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getEntrega(),
								r.getObsevacoes() };

						if (r.getTotalFinal() == null) {

							r.setTotalFinal("000");

						}

						total = Integer.valueOf(r.getTotalFinal()) + total++;

						registros++;

						model.addRow(linha);

					}

					lblTotalPedido.setText(tresDigitos.format(registros));
					lblQuantidadeTotal.setText(tresDigitos.format(total));

				}

			}
		});
		btnMostarTodos.setToolTipText("Mostrar Todos");
		btnMostarTodos.setActionCommand("");
		btnMostarTodos.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnMostarTodos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnMostarTodos.setBorder(null);
		btnMostarTodos.setBounds(12, 19, 139, 35);
		pEntrega.add(btnMostarTodos);

		JLabel lblTotalPedidoTexto = new JLabel("N\u00DAMERO TOTAL DE PEDIDOS DA PESQUISA:");
		lblTotalPedidoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblTotalPedidoTexto.setBounds(12, 444, 241, 22);
		pEntrega.add(lblTotalPedidoTexto);

		lblTotalPedido = new JLabel("");
		lblTotalPedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblTotalPedido.setBounds(249, 444, 115, 22);
		pEntrega.add(lblTotalPedido);

		JLabel lblQuantidadeTotalTexto = new JLabel("QUANTIDADE TOTAL:");
		lblQuantidadeTotalTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblQuantidadeTotalTexto.setBounds(12, 464, 120, 22);
		pEntrega.add(lblQuantidadeTotalTexto);

		lblQuantidadeTotal = new JLabel("");
		lblQuantidadeTotal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblQuantidadeTotal.setBounds(133, 465, 102, 21);
		pEntrega.add(lblQuantidadeTotal);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/EXPORTAR.png")));
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tabEntregar.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!");

				} else {

					if (listaEntregar.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "Planilha vazia", "Exportar entrega de pedidos",
								JOptionPane.ERROR_MESSAGE);

					} else {

						ExportarCsv exportarCsv = new ExportarCsv();

						String caminho = "C:/NE.Crachás/EntregarPedidos";

						boolean sucesso;

						try {

							sucesso = exportarCsv.exportarEntregarPedidosCsv(caminho, listaEntregar);

							if (sucesso == true) {

								try {

									// Executa o excel
									Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + caminho + ".csv");

								} catch (IOException e1) {

									JOptionPane.showMessageDialog(null,
											"Caminho do arquivo esta errado, contate os Desenvolvedores " + e1,
											"Exportar tabela", JOptionPane.ERROR_MESSAGE);

								}

							}

						} catch (HeadlessException | IOException e2) {

							JOptionPane.showMessageDialog(null, "Erro, o arquivo já está aberto!\n " + e2,
									"Exportar Lançambr/com/crachas/ento", JOptionPane.ERROR_MESSAGE);

						}

					}

				}

			}
		});
		btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnExportar.setBounds(552, 444, 115, 35);
		pEntrega.add(btnExportar);
		JPanel pPedidosFinalizados = new JPanel();
		pPedidosFinalizados.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {

				DefaultTableModel model = (DefaultTableModel) tabPedidosFinalizados.getModel();
				model.setNumRows(0);

				tfPedidoFinalizado.setText("");

				tfClienteFinalizado.setText("");
				dcDataInicialFinalizado.setDate(null);
				dcDataFinalFinalizado.setDate(null);

			}
		});
			
		tpEntregarPedido.addTab("Pedidos Finalizados", new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/finalizado.png")),
		pPedidosFinalizados, null);
		pPedidosFinalizados.setLayout(null);

		JPanel pFinalizado = new JPanel();
		pFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pFinalizado.setLayout(null);
		pFinalizado.setBounds(0, 0, 949, 490);
		pPedidosFinalizados.add(pFinalizado);

		JScrollPane spPedidoFinalizado = new JScrollPane();
		spPedidoFinalizado.setAutoscrolls(true);
		spPedidoFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		spPedidoFinalizado.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spPedidoFinalizado.setBounds(12, 70, 927, 402);
		pFinalizado.add(spPedidoFinalizado);

		tabPedidosFinalizados = new JTable();
		tabPedidosFinalizados.setAutoCreateRowSorter(true);
		tabPedidosFinalizados.setGridColor(Color.LIGHT_GRAY);
		tabPedidosFinalizados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabPedidosFinalizados.setDefaultRenderer(Object.class, new TabRecepcao());

		// criando o cabeçalho cinza e linhas
		JTableHeader anHeader2 = tabPedidosFinalizados.getTableHeader();
		anHeader2.setBackground(Color.LIGHT_GRAY);
		// anHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.PLAIN , 10));
		// anHeader.setForeground(new Color(0,0,205));

		// criando o alinhamento do cabeçalho da tabela
		TableCellRenderer rendererFromHeader2 = tabPedidosFinalizados.getTableHeader().getDefaultRenderer();
		JLabel headerLabel2 = (JLabel) rendererFromHeader2;
		headerLabel2.setHorizontalAlignment(JLabel.CENTER);

		tabPedidosFinalizados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabPedidosFinalizados.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tabPedidosFinalizados.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nº OS", "CLIENTE",
				"DATA ENTREGA", "TOTAL FINAL", "OBSERVA\u00C7\u00D5ES", "PORTADOR", "DOC. IDENTIFICA\u00C7\u00C3O" }));
		tabPedidosFinalizados.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabPedidosFinalizados.getColumnModel().getColumn(1).setPreferredWidth(75);
		tabPedidosFinalizados.getColumnModel().getColumn(2).setPreferredWidth(300);
		tabPedidosFinalizados.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabPedidosFinalizados.getColumnModel().getColumn(4).setPreferredWidth(100);
		tabPedidosFinalizados.getColumnModel().getColumn(5).setPreferredWidth(500);
		tabPedidosFinalizados.getColumnModel().getColumn(6).setPreferredWidth(200);
		tabPedidosFinalizados.getColumnModel().getColumn(7).setPreferredWidth(150);

		// Sorter
		model = (DefaultTableModel) tabPedidosFinalizados.getModel();
		model.setNumRows(0);

		sorter = new TableRowSorter<TableModel>(model);
		tabPedidosFinalizados.setRowSorter(sorter);
		//

		spPedidoFinalizado.setViewportView(tabPedidosFinalizados);

		JPanel pPesquisaPedidosFinalizados = new JPanel();
		pPesquisaPedidosFinalizados.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPesquisaPedidosFinalizados.setLayout(null);
		pPesquisaPedidosFinalizados.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
				"Pesquisa Ordem de Servi\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPesquisaPedidosFinalizados.setBounds(164, 3, 231, 62);
		pFinalizado.add(pPesquisaPedidosFinalizados);

		JButton lblPesquisaPedidosFinalizados = new JButton("Pesquisar");
		lblPesquisaPedidosFinalizados.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		lblPesquisaPedidosFinalizados.setBorder(null);
		lblPesquisaPedidosFinalizados.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPesquisaPedidosFinalizados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int registros = 0;
				int total = 0;

				String dataBr = null;

				DefaultTableModel model = (DefaultTableModel) tabPedidosFinalizados.getModel();
				model.setNumRows(0);

				if (tfPedidoFinalizado.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite o número da OS!", "Pedido Finalizados",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					OrdemServicoRN osRN = new OrdemServicoRN();

					listaPedidosFinalizados = osRN.listarPedidosFinalizadosOS(tfPedidoFinalizado.getText());

					if (listaPedidosFinalizados.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

						tfPedidoFinalizado.setText("");

					}

					for (PedidoFinalizado r : listaPedidosFinalizados) {

						dataBr = formatBR.format(r.getDtExpedicao());

						Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(r.getId_os()),
								r.getCliente().getNome(), dataBr,
								tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getObsevacoes(),
								r.getPortador().getNomePortador(), r.getPortador().getIdentificacaoPortador() };

						if (r.getTotalFinal() == null) {

							r.setTotalFinal("0");

						}

						total = Integer.valueOf(r.getTotalFinal()) + total++;

						registros++;

						model.addRow(linha);

					}

					tfPedidoFinalizado.setText("");

				}
			}

		});
		lblPesquisaPedidosFinalizados.setBounds(100, 17, 115, 35);
		pPesquisaPedidosFinalizados.add(lblPesquisaPedidosFinalizados);

		tfPedidoFinalizado = new JTextField();
		tfPedidoFinalizado.setHorizontalAlignment(SwingConstants.CENTER);
		tfPedidoFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfPedidoFinalizado.setBounds(19, 25, 70, 25);
		pPesquisaPedidosFinalizados.add(tfPedidoFinalizado);
		tfPedidoFinalizado.setColumns(10);

		tfNumeroOs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {

				String caracteres = "0987654321";

				if (!caracteres.contains(ev.getKeyChar() + "")) {

					ev.consume();

				}

			}

		});

		JPanel pPesquisaClientesFinalizados = new JPanel();
		pPesquisaClientesFinalizados.setLayout(null);
		pPesquisaClientesFinalizados.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPesquisaClientesFinalizados.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
				"Pesquisa Clientes Finalizados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPesquisaClientesFinalizados.setBounds(405, 3, 534, 62);
		pFinalizado.add(pPesquisaClientesFinalizados);

		tfClienteFinalizado = new JTextField();
		tfClienteFinalizado.setBounds(10, 28, 169, 25);
		pPesquisaClientesFinalizados.add(tfClienteFinalizado);
		tfClienteFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfClienteFinalizado.setColumns(10);

		dcDataInicialFinalizado = new JDateChooser();
		dcDataInicialFinalizado.setBounds(189, 28, 100, 25);
		pPesquisaClientesFinalizados.add(dcDataInicialFinalizado);
		dcDataInicialFinalizado.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dcDataInicialFinalizado.setFocusTraversalPolicyProvider(true);
		dcDataInicialFinalizado.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		dcDataFinalFinalizado = new JDateChooser();
		dcDataFinalFinalizado.setBounds(299, 28, 100, 25);
		pPesquisaClientesFinalizados.add(dcDataFinalFinalizado);
		dcDataFinalFinalizado.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dcDataFinalFinalizado.setFocusTraversalPolicyProvider(true);
		dcDataFinalFinalizado.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		JButton btnPesquisaPorPeriodoFinalizado = new JButton("Pesquisar");
		btnPesquisaPorPeriodoFinalizado.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisaPorPeriodoFinalizado.setBounds(408, 19, 115, 35);
		pPesquisaClientesFinalizados.add(btnPesquisaPorPeriodoFinalizado);
		btnPesquisaPorPeriodoFinalizado.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnPesquisaPorPeriodoFinalizado.setBorder(null);
		btnPesquisaPorPeriodoFinalizado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tfClienteFinalizado.getText().equals("")) {

					if (dcDataInicialFinalizado.getDate() == null) {

						JOptionPane.showMessageDialog(null, "Digite a data inicial");

					} else if (dcDataFinalFinalizado.getDate() == null) {

						JOptionPane.showMessageDialog(null, "Digite a data final");

					} else if (dcDataFinalFinalizado.getDate().before(dcDataInicialFinalizado.getDate())) {

						JOptionPane.showMessageDialog(null, "A data final não pode ser anterior a data inicial");

					} else {

						java.util.Date dataUtil = dcDataInicialFinalizado.getDate();
						java.util.Date dataUtil2 = dcDataFinalFinalizado.getDate();
						java.sql.Date DataInicial = new java.sql.Date(dataUtil.getTime());
						java.sql.Date DataFinal = new java.sql.Date(dataUtil2.getTime());

						int registros = 0;
						int total = 0;

						String dataBr = null;

						DefaultTableModel model = (DefaultTableModel) tabPedidosFinalizados.getModel();
						model.setNumRows(0);

						OrdemServicoRN osRN = new OrdemServicoRN();

						listaPedidosFinalizados = osRN.listarPedidosFinalizadosPeriodo(DataInicial, DataFinal);

						if (listaPedidosFinalizados.isEmpty() == true) {

							JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

							tfClienteFinalizado.setText("");
							dcDataInicialFinalizado.setDate(null);
							dcDataFinalFinalizado.setDate(null);

						}

						for (PedidoFinalizado r : listaPedidosFinalizados) {

							dataBr = formatBR.format(r.getDtExpedicao());

							Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(r.getId_os()),
									r.getCliente().getNome(), dataBr,
									tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getObsevacoes(),
									r.getPortador().getNomePortador(), r.getPortador().getIdentificacaoPortador() };

							if (r.getTotalFinal() == null) {

								r.setTotalFinal("0");

							}

							total = Integer.valueOf(r.getTotalFinal()) + total++;

							registros++;

							model.addRow(linha);

						}

						tfClienteFinalizado.setText("");
						dcDataInicialFinalizado.setDate(null);
						dcDataFinalFinalizado.setDate(null);

					}

				} else {

					if ((!tfClienteFinalizado.equals("") && dcDataInicialFinalizado.getDate() == null)
							|| (!tfClienteFinalizado.equals("") && dcDataFinalFinalizado.getDate() == null)) {

						JOptionPane.showMessageDialog(null, "Digite ambas as datas!");

					} else {

						java.util.Date dataUtil = dcDataInicialFinalizado.getDate();
						java.util.Date dataUtil2 = dcDataFinalFinalizado.getDate();
						java.sql.Date DataInicial = new java.sql.Date(dataUtil.getTime());
						java.sql.Date DataFinal = new java.sql.Date(dataUtil2.getTime());

						DataTemp = new java.sql.Date(dataUtil.getTime());

						int registros = 0;
						int total = 0;

						String dataBr = null;

						DefaultTableModel model = (DefaultTableModel) tabPedidosFinalizados.getModel();
						model.setNumRows(0);

						OrdemServicoRN osRN = new OrdemServicoRN();

						listaPedidosFinalizados = osRN.listarPedidosFinalizadosPeriodoENome(
								tfClienteFinalizado.getText().toUpperCase(), DataInicial, DataFinal);

						if (listaPedidosFinalizados.isEmpty() == true) {

							JOptionPane.showMessageDialog(null, "Pesquisa não encontrada!");

							tfClienteFinalizado.setText("");
							dcDataInicialFinalizado.setDate(null);
							dcDataFinalFinalizado.setDate(null);

						}

						for (PedidoFinalizado r : listaPedidosFinalizados) {

							dataBr = formatBR.format(r.getDtExpedicao());

							Object[] linha = { tresDigitos.format(registros + 1), cincoDigitos.format(r.getId_os()),
									r.getCliente().getNome(), dataBr,
									tresDigitos.format(Integer.valueOf(r.getTotalFinal())), r.getObsevacoes(),
									r.getPortador().getNomePortador(), r.getPortador().getIdentificacaoPortador() };

							if (r.getTotalFinal() == null) {

								r.setTotalFinal("0");

							}

							total = Integer.valueOf(r.getTotalFinal()) + total++;

							registros++;

							model.addRow(linha);

						}

						tfClienteFinalizado.setText("");
						dcDataInicialFinalizado.setDate(null);
						dcDataFinalFinalizado.setDate(null);

					}
				}
			}
		});
		btnPesquisaPorPeriodoFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 5, 86, 30);
		pPesquisaClientesFinalizados.add(lblCliente);
		lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JLabel lblDataInicial = new JLabel("Data Inicial");
		lblDataInicial.setBounds(189, 6, 86, 30);
		pPesquisaClientesFinalizados.add(lblDataInicial);
		lblDataInicial.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JLabel lblDataFinal = new JLabel("Data Final");
		lblDataFinal.setBounds(299, 6, 86, 30);
		pPesquisaClientesFinalizados.add(lblDataFinal);
		lblDataFinal.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JButton button_2 = new JButton("Visualizar OS");
		button_2.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/ordem servico 2.png")));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				OrdemServicoRN osRN = new OrdemServicoRN();
				OrdemServico os = new OrdemServico();
				String mensagem = "";

				if (tabPedidosFinalizados.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma busca!",
							"Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (tabPedidosFinalizados.getSelectedRow() == -1) {

						JOptionPane.showMessageDialog(null, "Selecione o registro na planiha!",
								"Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

					} else {

						int posicao = tabPedidosFinalizados.getSelectedRow();

						TelaOrdemServico telaOS = new TelaOrdemServico();
												
						int id_os = Integer.parseInt(tabPedidosFinalizados.getValueAt(posicao, 1).toString());

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

							mensagem = String.valueOf(estagio);

							mensagem = TelaOrdemServico.estagioOS(mensagem);

							telaOS.getLblOS().setText("ORDEM DE SERVIÇO Nº " + cincoDigitos.format(os.getId_os())
									+ " - " + cliente.getNome().toUpperCase());

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

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});
		button_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		//pRelatóriosDeEntrega. 11));
		button_2.setBounds(12, 18, 138, 35);
		pFinalizado.add(button_2);

		
		JPanel pRelatóriosDeEntrega = new JPanel();
		pRelatóriosDeEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pRelatóriosDeEntrega.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
			}
			public void ancestorMoved(AncestorEvent arg0) {
			}
			public void ancestorRemoved(AncestorEvent arg0) {
				
				dcRelatorios.setDate(null);
				cbRelatorios.setSelectedIndex(0);

				DefaultTableModel model = (DefaultTableModel) tbRelatorios.getModel();
				model.setNumRows(0);

		
			}
		});
				
		tpEntregarPedido.addTab("Relat\u00F3rios", new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/planilha.png")),
		pRelatóriosDeEntrega, null);
		pRelatóriosDeEntrega.setLayout(null);

		JPanel pRelatorios = new JPanel();
		pRelatorios.setLayout(null);
		pRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pRelatorios.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Pesquisar Relat\u00F3rios",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pRelatorios.setBounds(454, 2, 484, 69);
		pRelatóriosDeEntrega.add(pRelatorios);

		dcRelatorios = new JDateChooser();
		dcRelatorios.setFont(new Font("Segoe UI", Font.BOLD, 13));
		dcRelatorios.setFocusTraversalPolicyProvider(true);
		dcRelatorios.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		dcRelatorios.setBounds(23, 28, 100, 25);
		pRelatorios.add(dcRelatorios);

		cbRelatorios = new JComboBox<String>();
		cbRelatorios.setModel(new DefaultComboBoxModel<String>(
				new String[] { "SELECIONE...", "PEDIDOS RECEBIDOS NO DIA", "PEDIDOS PRODUZIDOS NO DIA",
						"PEDIDOS LAMINADOS OU EXPEDIDOS NO DIA", "PEDIDOS ENTREGUES NO DIA" }));
		cbRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbRelatorios.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbRelatorios.setBounds(130, 28, 287, 25);
		pRelatorios.add(cbRelatorios);

		JButton btnPesquisarRelatorios = new JButton("Pesquisar");
		btnPesquisarRelatorios.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisarRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (dcRelatorios.getDate() == null) {

					JOptionPane.showMessageDialog(null, "Digite a data para a pesquisa!", "Relatórios",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (cbRelatorios.getSelectedItem().equals("SELECIONE...")) {

					JOptionPane.showMessageDialog(null, "Selecione a condição da pesquisa!", "Relatórios",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					listaRelatorio = new ArrayList<EntregarPedidos>();

					java.util.Date dataUtil = dcRelatorios.getDate();
					java.sql.Date dataRelatorio = new java.sql.Date(dataUtil.getTime());

					String condicaoDaPesquisa = String.valueOf(cbRelatorios.getSelectedItem()).toUpperCase();

					OrdemServicoRN ordemServicoRN = new OrdemServicoRN();
					listaRelatorio = ordemServicoRN.listarRelatorios(dataRelatorio, condicaoDaPesquisa);

					if (listaRelatorio.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "A lista está vazia!", "Relatórios",
								JOptionPane.INFORMATION_MESSAGE);

						dcRelatorios.setDate(null);
						cbRelatorios.setSelectedIndex(0);

						DefaultTableModel model = (DefaultTableModel) tbRelatorios.getModel();
						model.setNumRows(0);

					} else {

						int registros = 0;
						String dataBr = null;

						DefaultTableModel model = (DefaultTableModel) tbRelatorios.getModel();
						model.setNumRows(0);

						String totalFinal = "";

						for (EntregarPedidos r : listaRelatorio) {

							dataBr = formatBR.format(r.getDtExpedicao());

							if (r.getOrdemServico().getTotalFinal() == null) {

								totalFinal = "N/D";

								Object[] linha = { tresDigitos.format(registros + 1),
										cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
										r.getCliente().getVendedor(),dataBr,
										tresDigitos.format(Integer.valueOf(r.getOrdemServico().getTotal())), totalFinal,
										r.getObsevacoes() };

								registros++;

								model.addRow(linha);

							} else {

								totalFinal = r.getOrdemServico().getTotalFinal();

								Object[] linha = { tresDigitos.format(registros + 1),
										cincoDigitos.format(r.getOrdemServico().getId_os()), r.getCliente().getNome(),
										r.getCliente().getVendedor(), dataBr,
										tresDigitos.format(Integer.valueOf(r.getOrdemServico().getTotal())),
										tresDigitos.format(Integer.valueOf(totalFinal)), r.getObsevacoes() };

								registros++;

								model.addRow(linha);

							}

						}
						
						dcRelatorios.setDate(null);
					cbRelatorios.setSelectedIndex(0);

					}

				}

			}
		});
		btnPesquisarRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnPesquisarRelatorios.setFocusPainted(false);
		btnPesquisarRelatorios.setBorder(null);
		btnPesquisarRelatorios.setBounds(423, 23, 100, 35);
		pRelatorios.add(btnPesquisarRelatorios);

		JButton btnVisualizarOsRelatorios = new JButton("Visualizar OS");
		btnVisualizarOsRelatorios.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/ordem servico 2.png")));
		btnVisualizarOsRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OrdemServicoRN osRN = new OrdemServicoRN();
				OrdemServico os = new OrdemServico();
				String mensagem = "";

				if (tbRelatorios.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma busca!",
							"Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (tbRelatorios.getSelectedRow() == -1) {

						JOptionPane.showMessageDialog(null, "Selecione o registro na planiha!",
								"Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

					} else {

						int posicao = tbRelatorios.getSelectedRow();

						TelaOrdemServico telaOS = new TelaOrdemServico();
	
						int id_os = Integer.parseInt(tbRelatorios.getValueAt(posicao, 1).toString());

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

							mensagem = String.valueOf(estagio);

							mensagem = TelaOrdemServico.estagioOS(mensagem);

							telaOS.getLblOS().setText("ORDEM DE SERVIÇO Nº " + cincoDigitos.format(os.getId_os())
									+ " - " + cliente.getNome().toUpperCase());

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

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			
				
				
			}
		});
		btnVisualizarOsRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVisualizarOsRelatorios.setBounds(800, 444, 138, 35);
		pRelatóriosDeEntrega.add(btnVisualizarOsRelatorios);

		JScrollPane spRelatorios = new JScrollPane();
		spRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		spRelatorios.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spRelatorios.setAutoscrolls(true);
		spRelatorios.setBounds(10, 76, 927, 357);
		pRelatóriosDeEntrega.add(spRelatorios);

		tbRelatorios = new JTable();
		tbRelatorios.setDefaultRenderer(Object.class, new TabRecepcao());
		tbRelatorios.setAutoCreateRowSorter(true);
		tbRelatorios.setGridColor(Color.LIGHT_GRAY);
		tbRelatorios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// criando o cabeçalho cinza e linhas
		anHeader = tbRelatorios.getTableHeader();
		anHeader.setBackground(Color.LIGHT_GRAY);
		// anHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.PLAIN , 10));
		// anHeader.setForeground(new Color(0,0,205));

		// criando o alinhamento do cabeçalho da tabela
		rendererFromHeader = tbRelatorios.getTableHeader().getDefaultRenderer();
		headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);

		tbRelatorios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tbRelatorios.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "N\u00BA OS", "CLIENTE",
				"VENDEDOR", "DATA ENTRADA", "TOTAL ENTRADA", "TOTAL EXPEDIDO", "OBSERVA\u00C7\u00D5ES DO PEDIDO" }));
		tbRelatorios.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbRelatorios.getColumnModel().getColumn(2).setPreferredWidth(300);
		tbRelatorios.getColumnModel().getColumn(3).setPreferredWidth(150);
		tbRelatorios.getColumnModel().getColumn(4).setPreferredWidth(150);
		tbRelatorios.getColumnModel().getColumn(5).setPreferredWidth(150);
		tbRelatorios.getColumnModel().getColumn(6).setPreferredWidth(150);
		tbRelatorios.getColumnModel().getColumn(7).setPreferredWidth(500);

		// Sorter
		model = (DefaultTableModel) tbRelatorios.getModel();
		model.setNumRows(0);

		sorter = new TableRowSorter<TableModel>(model);
		tbRelatorios.setRowSorter(sorter);
		//

		spRelatorios.setViewportView(tbRelatorios);
		
		JButton btnExportarRelatorio = new JButton("Exportar");
		btnExportarRelatorio.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/EXPORTAR.png")));
		btnExportarRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (tbRelatorios.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!");

				} else {

					if (listaRelatorio.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "Planilha vazia", "Exportar entrega de pedidos",
								JOptionPane.ERROR_MESSAGE);

					} else {

						ExportarCsv exportarCsv = new ExportarCsv();

						String caminho = "C:/NE.Crachás/Relatorio" ;

						boolean sucesso;

						try {
							
							sucesso = exportarCsv.exportarRelatorioCsv(caminho, listaRelatorio);

							if (sucesso == true) {

								try {

									// Executa o excel
									Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + caminho + ".csv");

								} catch (IOException e1) {

									JOptionPane.showMessageDialog(null,
											"Caminho do arquivo esta errado, contate os Desenvolvedores " + e1,
											"Exportar tabela", JOptionPane.ERROR_MESSAGE);

								}

							} 

						} catch (HeadlessException e2) {

							JOptionPane.showMessageDialog(null, "Erro, o arquivo já está aberto!\n " + e2,
									"Exportar Lançamento", JOptionPane.ERROR_MESSAGE);

						}

					}

				}

			
			}
		});
		btnExportarRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnExportarRelatorio.setBounds(550, 444, 115, 35);
		pRelatóriosDeEntrega.add(btnExportarRelatorio);
		
		JButton btnImprimirRelatorio = new JButton("Imprimir");
		btnImprimirRelatorio.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/imprimir.png")));
		btnImprimirRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Relatorio relatorio = new Relatorio();

				if (tbRelatorios.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Planilha vazia!", "Impressão Planilha",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					relatorio.gerarPlanilhaRelatorio();

				}

			}
		});
		btnImprimirRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnImprimirRelatorio.setBounds(675, 444, 115, 35);
		pRelatóriosDeEntrega.add(btnImprimirRelatorio);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
						"Pesquisa Clientes Finalizados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 2, 534, 69);
		pRelatóriosDeEntrega.add(panel);
		
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(10, 28, 169, 25);
		panel.add(textField);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dateChooser.setFocusTraversalPolicyProvider(true);
		dateChooser.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		dateChooser.setBounds(189, 28, 100, 25);
		panel.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dateChooser_1.setFocusTraversalPolicyProvider(true);
		dateChooser_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		dateChooser_1.setBounds(299, 28, 100, 25);
		panel.add(dateChooser_1);
		
		JButton button = new JButton("Pesquisar");
		button.setHorizontalTextPosition(SwingConstants.RIGHT);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		button.setBorder(null);
		button.setBounds(408, 19, 115, 35);
		panel.add(button);
		
		JLabel label = new JLabel("Cliente");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		label.setBounds(10, 5, 86, 30);
		panel.add(label);
		
		JLabel label_3 = new JLabel("Data Inicial");
		label_3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		label_3.setBounds(189, 6, 86, 30);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Data Final");
		label_4.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		label_4.setBounds(299, 6, 86, 30);
		panel.add(label_4);

		btnSair = new JButton("Sair");
		btnSair.setIcon(new ImageIcon(TelaEntregar.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnSair.setBorder(null);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}
		});

		btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSair.setFocusPainted(false);
		btnSair.setBounds(824, 545, 115, 35);
		cpRecepcao.add(btnSair);
		lblLogado = new JLabel("Logado: null / null");
		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setBounds(10, 566, 600, 25);
		cpRecepcao.add(lblLogado);
	}

	public static int getIdOS() {
		return idOS;
	}

	public static void setIdOS(int idOS) {
		TelaEntregar.idOS = idOS;
	}

	public static boolean isSalvou() {
		return salvou;
	}

	public static void setSalvou(boolean salvou) {
		TelaEntregar.salvou = salvou;
	}

	public JButton getBtnVoltar() {
		return btnSair;
	}

	public void setBtnVoltar(JButton btnVoltar) {
		this.btnSair = btnVoltar;
	}
}
