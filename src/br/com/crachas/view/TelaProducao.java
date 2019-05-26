package br.com.crachas.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.SystemColor;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.mail.EmailException;

import com.toedter.calendar.JDateChooser;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;
import br.com.crachas.controller.Email;
import br.com.crachas.controller.EstadoOS;
import br.com.crachas.controller.EstadoOSRN;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.OrdemServicoRN;
import br.com.crachas.controller.Planilha;
import br.com.crachas.controller.PlanilhaRN;
import br.com.crachas.controller.Producao;
import br.com.crachas.controller.ProducaoRN;
import br.com.crachas.controller.Produto;
import br.com.crachas.controller.ProdutoRN;
import br.com.crachas.controller.Relatorio;
import br.com.crachas.uteis.ExportarCsv;
import br.com.crachas.uteis.PesquisarComboBox;
import br.com.crachas.uteis.TamanhoMaxTextField;
import net.sf.jasperreports.engine.JRException;
import java.awt.Toolkit;

public class TelaProducao extends TelaLogin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8406743785886924850L;
	private JPanel contentPane;
	private JTextField tfQuantidade;
	public static JTable tbProducao;
	private JDateChooser dcDataEntrada;
	private JEditorPane epProducao;
	private JComboBox<String> cbEmpresa;
	private JComboBox<String> cbTipo;
	public static List<Producao> listaProducao;
	public static JLabel lblDataPlanilha;
	public static JLabel lblValorTotal;
	public static JLabel lblNumero;
	private Producao producao;
	private ProducaoRN producaoRN;
	public static Planilha planilha;
	private PlanilhaRN planilhaRN;
	private JButton btnEditar;
	private JButton btnAguardarCliente;
	private JButton btnImprimir;
	private JButton btnSalvar;
	private JButton btnExportar;
	private JButton btnMostrar;
	private JButton btnConfiguracoes;
	private JButton btnCriarPlanilha;
	private JButton btnCancelar;
	public static int id_os;
	private JButton btnOrdemServiço;
	private JLabel lblLogado;
	private int estagio;
	private JScrollPane scrollPane;
	private OrdemServicoRN osRN;
	private OrdemServico os;
	private JButton btnVoltar;
	private JButton btnVisualizar;
	private JButton brnFechar;
	private JDateChooser dcDatasAnteriores;
	private int entradas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaProducao frame = new TelaProducao();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaProducao() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaProducao.class.getResource("/br/com/crachas/image/ne.png")));
		setTitle("Controle da Produ\u00E7\u00E3o de Crach\u00E1s");

		setType(Type.NORMAL);

		osRN = new OrdemServicoRN();
		os = new OrdemServico();

		NumberFormat tresDigitos = new DecimalFormat("000");
		NumberFormat cincoDigitos = new DecimalFormat("00000");
		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
		DateFormat formatBR2 = new SimpleDateFormat("dd-MM-YYYY");

		tbProducao = new JTable();
		tbProducao.setAutoCreateRowSorter(true);
		tbProducao.setGridColor(Color.LIGHT_GRAY);
		tbProducao.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbProducao.setDefaultRenderer(Object.class, new TabProducao());

		// criando o cabeçalho cinza e linhas
		JTableHeader anHeader = tbProducao.getTableHeader();
		anHeader.setBackground(Color.LIGHT_GRAY);
		// anHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.PLAIN , 10));
		// anHeader.setForeground(new Color(0,0,205));

		// criando o alinhamento do cabeçalho da tabela
		TableCellRenderer rendererFromHeader = tbProducao.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);

		lblLogado = new JLabel("");
		lblLogado.setBounds(10, 566, 393, 25);
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());

		setProducao(new Producao());
		setProducaoRN(new ProducaoRN());
		planilha = new Planilha();
		planilhaRN = new PlanilhaRN();

		setBounds(new Rectangle(100, 100, 300, 145));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 640);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setBackground(SystemColor.control);
		setContentPane(contentPane);

		TamanhoMaxTextField TamanhoMax = new TamanhoMaxTextField();
		TamanhoMax.setMaxChars(7);

		JScrollPane spProducao = new JScrollPane();
		spProducao.setAutoscrolls(true);
		spProducao.setBounds(9, 249, 934, 306);
		spProducao.setAlignmentX(Component.RIGHT_ALIGNMENT);
		spProducao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		spProducao.setBorder(new LineBorder(new Color(192, 192, 192)));

		tbProducao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				int posicao = tbProducao.getSelectedRow();
				int idOS = 0;

				if (posicao == -1) {

					posicao = 0;

				} else {

					idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

				}

				Producao producao = new Producao();
				ProducaoRN producaoRN = new ProducaoRN();
				producao = producaoRN.pesquisarIDOS(idOS);

				int id_producao = producao.getId_producao();

				if (id_producao < 0) {

				} else {

					producao = producaoRN.pesquisarID(id_producao);

					String status = String.valueOf(tbProducao.getValueAt(posicao, 7));

					if (btnEditar.getText().equals("Salvar")) {

						switch (status) {

						case "ORDEM DE SERVIÇO CANCELADA":

							cbEmpresa.setSelectedItem(null);
							cbTipo.setSelectedItem(null);
							tfQuantidade.setText("");

							dcDataEntrada.setDate(null);
							epProducao.setText("");

							btnEditar.setEnabled(false);
							btnCancelar.setEnabled(false);
							btnAguardarCliente.setEnabled(false);
							btnAguardarCliente.setText("Aguardar");

							break;

						case "AGUARDANDO O CLIENTE":

							cbEmpresa.setEnabled(false);
							cbTipo.setEnabled(false);
							tfQuantidade.setEnabled(false);

							dcDataEntrada.setEnabled(false);
							epProducao.setEnabled(false);

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(false);
							btnCancelar.setEnabled(false);

							btnAguardarCliente.setText("Continuar");
							btnAguardarCliente.setEnabled(true);

							break;

						case "IMPRESSÃO":

							btnAguardarCliente.setText("Aguardar");

							cbEmpresa.setEnabled(true);
							cbTipo.setEnabled(true);
							tfQuantidade.setEnabled(true);

							dcDataEntrada.setEnabled(true);
							epProducao.setEnabled(true);

							cbEmpresa.setEditable(true);
							cbTipo.setEditable(true);
							tfQuantidade.setEditable(true);

							dcDataEntrada.setEnabled(true);
							epProducao.setEditable(true);

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(true);
							btnCancelar.setEnabled(true);
							btnAguardarCliente.setEnabled(true);

							break;

						case "LAMINAÇÃO / EXPEDIÇÃO":

							btnAguardarCliente.setText("Aguardar");

							cbEmpresa.setEnabled(true);
							cbTipo.setEnabled(true);
							tfQuantidade.setEnabled(true);

							dcDataEntrada.setEnabled(true);
							epProducao.setEnabled(true);

							cbEmpresa.setEditable(true);
							cbTipo.setEditable(true);
							tfQuantidade.setEditable(true);

							dcDataEntrada.setEnabled(true);
							epProducao.setEditable(true);

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(true);
							btnCancelar.setEnabled(true);
							btnAguardarCliente.setEnabled(true);

							break;

						case "RECEPÇÃO":

							cbEmpresa.setEnabled(false);
							cbEmpresa.setEditable(false);
							cbTipo.setEnabled(false);
							tfQuantidade.setEnabled(false);
							dcDataEntrada.setEnabled(false);
							epProducao.setEnabled(false);

							cbEmpresa.setEditable(false);
							cbTipo.setEditable(false);
							tfQuantidade.setEditable(false);
							dcDataEntrada.setEnabled(false);
							epProducao.setEditable(false);

							cbEmpresa.setSelectedItem(null);
							cbTipo.setSelectedItem(null);
							tfQuantidade.setText("");

							dcDataEntrada.setDate(null);
							epProducao.setText("");

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(false);
							btnCancelar.setEnabled(false);
							btnAguardarCliente.setEnabled(false);

							break;

						case "ENTREGAR":

							cbEmpresa.setEditable(false);
							cbTipo.setEditable(false);
							tfQuantidade.setEditable(false);
							dcDataEntrada.setEnabled(false);
							epProducao.setEditable(false);

							cbEmpresa.setSelectedItem(null);
							cbTipo.setSelectedItem(null);
							tfQuantidade.setText("");

							dcDataEntrada.setDate(null);
							epProducao.setText("");

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(false);
							btnCancelar.setEnabled(false);
							btnAguardarCliente.setEnabled(false);

							break;

						case "PEDIDO FINALIZADO":

							cbEmpresa.setEditable(false);
							cbTipo.setEditable(false);
							tfQuantidade.setEditable(false);
							dcDataEntrada.setEnabled(false);
							epProducao.setEditable(false);

							cbEmpresa.setSelectedItem(null);
							cbTipo.setSelectedItem(null);
							tfQuantidade.setText("");

							dcDataEntrada.setDate(null);
							epProducao.setText("");

							cbEmpresa.setSelectedItem(producao.getEmpresa());
							cbTipo.setSelectedItem(producao.getTipo());
							tfQuantidade.setText(producao.getQuantidade());
							dcDataEntrada.setDate((Date) producao.getDataEntrada());
							epProducao.setText(producao.getObservacoes());

							btnEditar.setEnabled(false);
							btnCancelar.setEnabled(false);
							btnAguardarCliente.setEnabled(false);

							break;

						}

					}

				}
			}
		});

		tbProducao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProducao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tbProducao.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "N\u00BA OS", "CLIENTE", "TIPO", "OPERADOR", "QUANT RECEBIDA", "DATA ENTRADA",
						"SITUA\u00C7\u00C3O DO PEDIDO", "OBSERVA\u00C7\u00D5ES DO PEDIDO" }));
		tbProducao.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbProducao.getColumnModel().getColumn(1).setPreferredWidth(75);
		tbProducao.getColumnModel().getColumn(2).setPreferredWidth(300);
		tbProducao.getColumnModel().getColumn(3).setPreferredWidth(150);
		tbProducao.getColumnModel().getColumn(4).setPreferredWidth(110);
		tbProducao.getColumnModel().getColumn(5).setPreferredWidth(110);
		tbProducao.getColumnModel().getColumn(6).setPreferredWidth(120);
		tbProducao.getColumnModel().getColumn(7).setPreferredWidth(200);
		tbProducao.getColumnModel().getColumn(8).setPreferredWidth(500);

		// Sorter
		DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();
		model.setNumRows(0);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		tbProducao.setRowSorter(sorter);
		//

		spProducao.setViewportView(tbProducao);

		Date hoje = new Date(System.currentTimeMillis());
		java.sql.Date dataEscolhida = new java.sql.Date(hoje.getTime());

		planilhaRN = new PlanilhaRN();

		planilha = planilhaRN.verificarPlanilha(dataEscolhida);

		ProducaoRN producaoRN = new ProducaoRN();

		listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

		model = (DefaultTableModel) tbProducao.getModel();

		if (planilha.getDataPlanilha() == null) {

			String dateBr = formatBR.format(hoje);

			JOptionPane.showMessageDialog(null, "Não foi criada uma planilha nesta data!", "Criar Planilha?",
					JOptionPane.INFORMATION_MESSAGE);

			model.setNumRows(0);

			int resposta = JOptionPane.showConfirmDialog(null, "Você deseja criar a planilha do dia " + dateBr + " ?",
					"Criar planilha?", JOptionPane.YES_NO_OPTION);

			if (resposta == 0) {

				planilha.setId_planilha(planilha.getId_planilha() + 1);
				planilha.setStatus(true);
				planilha.setDataPlanilha(hoje);
				planilha.setTotal("0");

				planilhaRN.criarPlanilha(planilha);
				
				JOptionPane.showMessageDialog(null, "Planilha do dia " + dateBr + " criada!", "Abrir Programa",
						JOptionPane.INFORMATION_MESSAGE);

			} else {

				JOptionPane.showMessageDialog(null, "Não foi criada a planilha do dia" + dateBr, "Abrir Programa",
						JOptionPane.INFORMATION_MESSAGE);

				planilha.setTotal("000");

			}

		} else {

			listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

			entradas = 0;

			model = (DefaultTableModel) tbProducao.getModel();

			model.setNumRows(0);

			String dataBr = "";

			for (Producao p : listaProducao) {

				dataBr = formatBR.format(p.getDataEntrada());
				String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
						
				Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
						p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
						dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};
				
				entradas++;

				model.addRow(linha);

			}

		}

		JPanel pNomePlanilha = new JPanel();
		pNomePlanilha.setBounds(0, 0, 968, 61);
		pNomePlanilha.setBackground(Color.BLACK);
		pNomePlanilha.setLayout(null);

		btnCriarPlanilha = new JButton("Nova");
		btnCriarPlanilha.setSelectedIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/48px-Crystal_Clear_action_edit_add - 2.png")));
		btnCriarPlanilha.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/48px-Crystal_Clear_action_edit_add.png")));
		btnCriarPlanilha.setToolTipText("Nova Planilha");
		btnCriarPlanilha.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnCriarPlanilha.setFocusPainted(false);
		btnCriarPlanilha.setForeground(Color.WHITE);
		btnCriarPlanilha.setContentAreaFilled(false);
		btnCriarPlanilha.setBorder(null);
		btnCriarPlanilha.setBounds(13, -3, 97, 65);
		pNomePlanilha.add(btnCriarPlanilha);

		JLabel lblPlanilhaDeProducao = new JLabel("CONTROLE DA PRODU\u00C7\u00C3O DE CRACH\u00C1S");
		lblPlanilhaDeProducao.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPlanilhaDeProducao.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlanilhaDeProducao.setForeground(Color.WHITE);
		lblPlanilhaDeProducao.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblPlanilhaDeProducao.setBounds(114, 0, 739, 63);
		pNomePlanilha.add(lblPlanilhaDeProducao);

		btnConfiguracoes = new JButton("Configura\u00E7\u00F5es");
		btnConfiguracoes.setSelectedIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/48px-Crystal_Clear_action_configure - 2.png")));
		btnConfiguracoes.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/48px-Crystal_Clear_action_configure.png")));
		btnConfiguracoes.setToolTipText("Configura\u00E7\u00F5es");
		btnConfiguracoes.setEnabled(false);
		btnConfiguracoes.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnConfiguracoes.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnConfiguracoes.setFocusPainted(false);
		btnConfiguracoes.setContentAreaFilled(false);
		btnConfiguracoes.setForeground(Color.WHITE);
		btnConfiguracoes.setHorizontalTextPosition(SwingConstants.LEADING);
		btnConfiguracoes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaConfiguracoes dialog = new TelaConfiguracoes();
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

				cbEmpresa.removeAllItems();

				List<Cliente> listaCliente = new ArrayList<Cliente>();
				ClienteRN clienteRN = new ClienteRN();
				listaCliente = clienteRN.listarClientes();

				cbEmpresa.addItem("");

				for (Cliente c : listaCliente) {

					cbEmpresa.addItem(c.getNome());

				}

				cbTipo.removeAllItems();

				List<Produto> listaProduto = new ArrayList<Produto>();
				ProdutoRN produtoRN = new ProdutoRN();
				listaProduto = produtoRN.listarProduto();

				cbTipo.addItem("");

				for (Produto pr : listaProduto) {

					cbTipo.addItem(pr.getNome());

				}

			}
		});
		btnConfiguracoes.setBorder(null);
		btnConfiguracoes.setBounds(796, -1, 147, 62);
		pNomePlanilha.add(btnConfiguracoes);

		JPanel pEntradaDePedido = new JPanel();
		pEntradaDePedido.setBounds(7, 62, 782, 124);
		pEntradaDePedido.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pEntradaDePedido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pEntradaDePedido.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Entrada de Pedido",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JLabel lblEmpresa = new JLabel("Empresa");
		lblEmpresa.setBounds(10, 7, 81, 27);
		lblEmpresa.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		cbEmpresa = new JComboBox<String>();
		cbEmpresa.setEditable(true);
		cbEmpresa.setBounds(10, 28, 390, 25);
		cbEmpresa.setName("");
		cbEmpresa.setToolTipText("Digite o nome da Empresa");
		cbEmpresa.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		cbEmpresa.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		cbEmpresa.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		// Setar focus em cbEmpresa

		final JComboBox<String> cb = cbEmpresa;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cb.requestFocusInWindow();
			}
		});

		List<Cliente> listaCliente = new ArrayList<Cliente>();
		ClienteRN clienteRN = new ClienteRN();
		listaCliente = clienteRN.listarClientes();

		for (Cliente c : listaCliente) {

			cbEmpresa.addItem(c.getNome());

		}
		new PesquisarComboBox(cbEmpresa);

		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(710, 7, 66, 27);
		lblQuantidade.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfQuantidade = new JTextField();
		tfQuantidade.setToolTipText("Digite a quantidade");
		tfQuantidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnSalvar);

			}

			@Override
			public void focusLost(FocusEvent arg0) {

				getRootPane().setDefaultButton(null);

			}
		});
		tfQuantidade.setBounds(710, 28, 62, 25);
		tfQuantidade.setBorder(new LineBorder(new Color(192, 192, 192)));
		tfQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		tfQuantidade.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tfQuantidade.setForeground(Color.BLACK);
		tfQuantidade.setColumns(10);

		tfQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {

				String caracteres = "0987654321";

				if (!caracteres.contains(ev.getKeyChar() + "")) {

					ev.consume();

				}

			}

		});
		tfQuantidade.setDocument(TamanhoMax);

		JLabel lblTipo = new JLabel("Tipo de Produto");
		lblTipo.setBounds(410, 7, 186, 27);
		lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		cbTipo = new JComboBox<String>();
		cbTipo.setBackground(Color.WHITE);
		cbTipo.setBounds(410, 28, 186, 25);
		cbTipo.setToolTipText("Digite o tipo do produto");
		cbTipo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		cbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		ProdutoRN produtoRN = new ProdutoRN();

		List<Produto> listaProduto = new ArrayList<Produto>();
		listaProduto = produtoRN.listarProduto();

		for (Produto pr : listaProduto) {

			cbTipo.addItem(pr.getNome());

		}

		new PesquisarComboBox(cbTipo);

		JLabel lblDataEntrada = new JLabel("Data de Entrada");
		lblDataEntrada.setBounds(603, 7, 110, 27);
		lblDataEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dcDataEntrada = new JDateChooser();
		dcDataEntrada.setToolTipText("");
		dcDataEntrada.setBounds(604, 28, 100, 25);
		dcDataEntrada.setDateFormatString("dd/MM/yyyy");
		dcDataEntrada.setBorder(new LineBorder(new Color(192, 192, 192), 0, true));
		dcDataEntrada.setFont(new Font("Segoe UI", Font.BOLD, 13));

		btnExportar = new JButton("Exportar");
		btnExportar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/EXPORTAR.png")));
		btnExportar.setToolTipText("Exportar");
		btnExportar.setBounds(548, 74, 110, 35);
		btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		btnImprimir = new JButton("Imprimir");
		btnImprimir.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/imprimir.png")));
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setBounds(662, 74, 110, 35);
		btnImprimir.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Relatorio relatorio = new Relatorio();

				if (tbProducao.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Planilha vazia!", "Impressão Planilha",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					try {

						relatorio.gerarPlanilhaProducao();

					} catch (JRException e) {

						e.printStackTrace();
					}

				}

			}
		});
		btnImprimir.setActionCommand("");

		JPanel pPlanilhaAnteriores = new JPanel();
		pPlanilhaAnteriores.setBounds(699, 184, 247, 61);
		pPlanilhaAnteriores.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pPlanilhaAnteriores.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)),
				"Escolher Planilhas Anteriores", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pPlanilhaAnteriores.setLayout(null);

		dcDatasAnteriores = new JDateChooser();
		dcDatasAnteriores.setBounds(10, 22, 100, 25);
		dcDatasAnteriores.setDateFormatString("dd/MM/yyyy");
		pPlanilhaAnteriores.add(dcDatasAnteriores);
		dcDatasAnteriores.setBorder(new LineBorder(Color.LIGHT_GRAY, 0, true));
		dcDatasAnteriores.setFont(new Font("Segoe UI", Font.BOLD, 13));

		btnMostrar = new JButton("Pesquisar");
		btnMostrar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnMostrar.setToolTipText("Pesquisar");
		btnMostrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnMostrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnMostrar.setBounds(120, 16, 115, 35);
		pPlanilhaAnteriores.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (dcDatasAnteriores.getDate() == null) {

					JOptionPane.showMessageDialog(null, "Escolha uma data!");

				} else {

					java.util.Date dataUtil = dcDatasAnteriores.getDate();
					java.sql.Date dataEscolhida = new java.sql.Date(dataUtil.getTime());

					PlanilhaRN planilhaRN = new PlanilhaRN();

					planilha = planilhaRN.verificarPlanilha(dataEscolhida);

					ProducaoRN producaoRN = new ProducaoRN();

					listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

					if (listaProducao.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Não foi criada uma planilha nesta data ou está vazia!");

						DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

						model.setNumRows(0);

						lblDataPlanilha.setText("00/00/0000");
						lblValorTotal.setText("000");
						lblNumero.setText(String.valueOf("000"));
						dcDatasAnteriores.setDate(null);

					} else {

						listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

						entradas = 0;

						String dataBr = formatBR.format(planilha.getDataPlanilha());

						DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

						int valorTotal = 0;

						model.setNumRows(0);

						for (Producao p : listaProducao) {

							dataBr = formatBR.format(p.getDataEntrada());
							String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
									
							Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
									p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
									dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

							entradas++;

							if (p.getQuantidade() == null) {

								valorTotal = valorTotal + 0;

							} else {

								valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

							}

							model.addRow(linha);

						}

						lblDataPlanilha.setText(dataBr);
						lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
						lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

						dcDatasAnteriores.setDate(null);

					}

				}
			}
		});

		JPanel pDadosDaPlanilha = new JPanel();
		pDadosDaPlanilha.setBounds(485, 184, 205, 61);
		pDadosDaPlanilha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pDadosDaPlanilha.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dados da Planilha",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JLabel lblPlanilhaDia = new JLabel("Planilha do Dia:");
		lblPlanilhaDia.setBounds(7, 8, 103, 30);
		lblPlanilhaDia.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		String dataBr3 = "";

		if (planilha.getDataPlanilha() == null) {

			dataBr3 = "";

		} else {

			dataBr3 = formatBR.format(planilha.getDataPlanilha());

		}

		lblDataPlanilha = new JLabel("");
		lblDataPlanilha.setText(dataBr3);
		lblDataPlanilha.setBounds(117, 8, 81, 30);
		lblDataPlanilha.setIgnoreRepaint(true);
		lblDataPlanilha.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDataPlanilha.setHorizontalAlignment(SwingConstants.LEFT);
		lblDataPlanilha.setForeground(Color.BLACK);
		lblDataPlanilha.setFont(new Font("Segoe UI", Font.BOLD, 14));

		JLabel lblN = new JLabel("N\u00BA Registros:");
		lblN.setBounds(7, 30, 90, 30);
		lblN.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		lblNumero = new JLabel("");
		lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));
		lblNumero.setBounds(94, 30, 40, 30);
		lblNumero.setForeground(Color.BLACK);
		lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 14));

		lblValorTotal = new JLabel("");
		lblValorTotal.setText(tresDigitos.format(Integer.valueOf(planilha.getTotal())));
		lblValorTotal.setBounds(168, 30, 34, 30);
		lblValorTotal.setForeground(Color.BLACK);
		lblValorTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(127, 30, 48, 30);
		lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JPanel pEditarPlanilha = new JPanel();
		pEditarPlanilha.setBounds(7, 184, 469, 61);
		pEditarPlanilha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pEditarPlanilha.setLayout(null);
		pEditarPlanilha.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Editar Planilha",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/editar1.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditar.setEnabled(false);
		btnEditar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEditar.setBounds(10, 15, 98, 35);
		pEditarPlanilha.add(btnEditar);

		btnAguardarCliente = new JButton("Aguardar");
		btnAguardarCliente.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/pause.png")));
		btnAguardarCliente.setToolTipText("Aguardar");
		btnAguardarCliente.setHorizontalAlignment(SwingConstants.LEFT);
		btnAguardarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAguardarCliente.setEnabled(false);
		btnAguardarCliente.setBounds(228, 15, 120, 35);
		pEditarPlanilha.add(btnAguardarCliente);

		btnCancelar = new JButton("Excluir");
		btnCancelar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/excluir.png")));
		btnCancelar.setToolTipText("Excluir");
		btnCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int resposta = 0;

				resposta = JOptionPane.showConfirmDialog(null, "Você deseja mesmo cancelar o pedido?", "Cancelar",
						JOptionPane.YES_NO_OPTION);

				if (resposta == 0) {

					int posicao = tbProducao.getSelectedRow();
					int idOS = 0;

					if (posicao == -1) {

						posicao = 0;

					} else {

						idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

					}

					Producao producao = new Producao();
					ProducaoRN producaoRN = new ProducaoRN();
					producao = producaoRN.pesquisarIDOS(idOS);

					int id_planilha = producao.getId_planilha();

					java.util.Date dataUtil = dcDataEntrada.getDate();
					java.sql.Date DataSql = new java.sql.Date(dataUtil.getTime());

					producao.setEmpresa(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());
					producao.setTipo(String.valueOf(cbTipo.getSelectedItem()).toUpperCase());
					producao.setQuantidade(String.valueOf(0));
					producao.setDataEntrada(DataSql);

					DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

					model.setNumRows(0);

					String dataBr;

					int valorTotal = 0;

					planilha = planilhaRN.buscarPlanilha(id_planilha);

					dataBr = formatBR.format(planilha.getDataPlanilha());

					btnEditar.setText("Editar");
					tbProducao.setSelectionBackground(Color.BLUE);

					String nome = String.valueOf(cbEmpresa.getSelectedItem());

					cbEmpresa.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					tfQuantidade.setText("");
					dcDataEntrada.setDate(null);
					epProducao.setText("");

					btnSalvar.setEnabled(true);
					btnExportar.setEnabled(true);
					btnCriarPlanilha.setEnabled(true);
					btnMostrar.setEnabled(true);
					btnImprimir.setEnabled(true);
					btnAguardarCliente.setEnabled(false);
					btnCancelar.setEnabled(false);

					Date hoje = new Date(System.currentTimeMillis());
					Time hora = new Time(System.currentTimeMillis());

					EstadoOS estadoOS = new EstadoOS();
					EstadoOSRN estadoOSRN = new EstadoOSRN();

					estadoOS.setId_os(producao.getOs());
					estadoOS.setEstagio(7);
					estadoOS.setDataInicial(hoje);
					estadoOS.setHoraInicial(hora);
					estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

					entradas = 0;

					estadoOSRN.criarEstadoOS(estadoOS);

					estagio = estadoOSRN.buscarEstagio(producao.getOs());

					String mensagem = String.valueOf(estagio);

					mensagem = TelaOrdemServico.estagioOS(mensagem);

					ClienteRN clienteRN = new ClienteRN();

					String id_cliente = clienteRN.mostrarId_cliente(nome);

					osRN.atualizarTotal(producao.getOs(), producao.getQuantidade(), Integer.valueOf(id_cliente),
							producao.getObservacoes());

					osRN.atualizarStatus(producao.getOs(), mensagem);

					os = osRN.mostrarOS(id_os);

					producao.setOrdemservico(os);

					producaoRN.atualizarProducao(producao);

					listaProducao = producaoRN.listaProducaoData(producao.getId_planilha());

					for (Producao p : listaProducao) {

						dataBr = formatBR.format(p.getDataEntrada());
						String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
								
						Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
								p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
								dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

						if (p.getQuantidade() == null) {

							valorTotal = valorTotal + 0;

						} else {

							valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

						}

						entradas++;

						model.addRow(linha);

					}

					lblDataPlanilha.setText(String.valueOf(dataBr));
					lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
					lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

					planilha.setTotal(String.valueOf(valorTotal));

					planilhaRN.atualizarPlanilha(planilha);

					try {

						TelaOrdemServico telaOS = new TelaOrdemServico();

						osRN = new OrdemServicoRN();
						os = new OrdemServico();

						os = osRN.mostrarOS(producao.getOs());

						Cliente cliente = new Cliente();

						cliente = clienteRN.mostrarCliente(os.getId_cliente());

						telaOS.getTfOS().setText(String.valueOf(os.getId_os()));
						telaOS.getTfTotal().setText(os.getTotal());
						telaOS.getDtRecebimento().setText(String.valueOf(os.getDtRecebimento()));
						telaOS.getDtPrevista().setText(String.valueOf(os.getDtPrevista()));

						telaOS.getTfClienteOS().setText(cliente.getNome());
						telaOS.getTfVendedorOS().setText(cliente.getVendedor());
						telaOS.getTfContatoOS().setText(cliente.getContato());
						telaOS.getTfEmailOS().setText(cliente.getEmail());
						telaOS.getTfFone1OS().setText(cliente.getFone1());
						telaOS.getTfFone2OS().setText(cliente.getFone2());
						telaOS.getTpDetalhesOS().setText(cliente.getDetalhes());

						telaOS.setLocationRelativeTo(null);

					} catch (Exception e) {
						e.printStackTrace();
					}

					btnVoltar.setEnabled(false);
					btnSalvar.setEnabled(true);
					btnOrdemServiço.setEnabled(true);
					btnVisualizar.setEnabled(true);

				} else {

				}

			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelar.setBounds(118, 15, 100, 35);
		pEditarPlanilha.add(btnCancelar);
		btnAguardarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnAguardarCliente.getText().equalsIgnoreCase("Aguardar")) {

					int resposta = 0;

					resposta = JOptionPane.showConfirmDialog(null, "Observação Aguardar o Cliente?", "Aguardar Cliente",
							JOptionPane.YES_NO_OPTION);

					if (resposta == 0) {

						java.util.Date dataUtil = dcDataEntrada.getDate();
						java.sql.Date DataSql = new java.sql.Date(dataUtil.getTime());

						int posicao = tbProducao.getSelectedRow();
						int idOS = 0;

						if (posicao == -1) {

							posicao = 0;

						} else {

							idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

						}

						Producao producao = new Producao();
						ProducaoRN producaoRN = new ProducaoRN();
						producao = producaoRN.pesquisarIDOS(idOS);

						producao.setEmpresa(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());
						producao.setTipo(String.valueOf(cbTipo.getSelectedItem()).toUpperCase());
						producao.setQuantidade(tfQuantidade.getText());
						producao.setDataEntrada(DataSql);
						producao.setObservacoes(epProducao.getText());

						producaoRN.atualizarProducao(producao);

						int id_planilha = producao.getId_planilha();

						producao.setId_planilha(id_planilha);

						DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

						model.setNumRows(0);

						String dataBr = null;

						int valorTotal = 0;

						planilha = planilhaRN.buscarPlanilha(id_planilha);

						dataBr = formatBR.format(planilha.getDataPlanilha());

						OrdemServicoRN osRN = new OrdemServicoRN();

						ClienteRN clienteRN = new ClienteRN();

						String id_cliente = clienteRN
								.mostrarId_cliente(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());

						osRN.atualizarTotal(producao.getOs(), producao.getQuantidade(), Integer.valueOf(id_cliente),
								producao.getObservacoes());

						btnEditar.setText("Editar");
						tbProducao.setSelectionBackground(Color.BLUE);

						cbEmpresa.setSelectedIndex(0);
						cbTipo.setSelectedIndex(0);
						tfQuantidade.setText("");
						dcDataEntrada.setDate(null);
						epProducao.setText("");

						cbEmpresa.setEnabled(true);
						// cbTipo.setEnabled(true);
						tfQuantidade.setEnabled(true);
						dcDataEntrada.setEnabled(true);
						epProducao.setEnabled(true);

						cbEmpresa.setEditable(true);
						// cbTipo.setEditable(true);
						tfQuantidade.setEditable(true);
						dcDataEntrada.setEnabled(true);
						epProducao.setEditable(true);

						btnSalvar.setEnabled(true);
						btnExportar.setEnabled(true);
						btnCriarPlanilha.setEnabled(true);
						btnMostrar.setEnabled(true);
						btnImprimir.setEnabled(true);
						btnAguardarCliente.setEnabled(false);
						btnCancelar.setEnabled(false);

						btnVoltar.setEnabled(false);

						dcDatasAnteriores.setEnabled(true);

						Date hoje = new Date(System.currentTimeMillis());
						Time hora = new Time(System.currentTimeMillis());

						EstadoOS estadoOS = new EstadoOS();
						EstadoOSRN estadoOSRN = new EstadoOSRN();

						estadoOS.setId_os(producao.getOs());
						estadoOS.setEstagio(6);
						estadoOS.setDataInicial(hoje);
						estadoOS.setHoraInicial(hora);
						estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

						entradas = 0;

						estadoOSRN.criarEstadoOS(estadoOS);

						estagio = estadoOSRN.buscarEstagio(producao.getOs());

						String mensagem = String.valueOf(estagio);

						mensagem = TelaOrdemServico.estagioOS(mensagem);

						osRN.atualizarStatus(producao.getOs(), mensagem);

						os = osRN.mostrarOS(id_os);

						producao.setOrdemservico(os);

						producaoRN.atualizarProducao(producao);

						listaProducao = producaoRN.listaProducaoData(producao.getId_planilha());

						for (Producao p : listaProducao) {

							dataBr = formatBR.format(p.getDataEntrada());
							String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
									
							Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
									p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
									dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

							if (p.getQuantidade() == null) {

								valorTotal = valorTotal + 0;

							} else {

								valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

							}

							entradas++;

							model.addRow(linha);

						}

						planilha.setTotal(String.valueOf(valorTotal));

						planilhaRN.atualizarPlanilha(planilha);

						lblDataPlanilha.setText(String.valueOf(dataBr));
						lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
						lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

						try {

							TelaOrdemServico telaOS = new TelaOrdemServico();

							osRN = new OrdemServicoRN();
							os = new OrdemServico();

							os = osRN.mostrarOS(producao.getOs());

							Cliente cliente = new Cliente();

							cliente = clienteRN.mostrarCliente(os.getId_cliente());

							telaOS.getTfOS().setText(String.valueOf(os.getId_os()));
							telaOS.getTfTotal().setText(os.getTotal());
							telaOS.getDtRecebimento().setText(String.valueOf(os.getDtRecebimento()));
							telaOS.getDtPrevista().setText(String.valueOf(os.getDtPrevista()));

							telaOS.getTfClienteOS().setText(cliente.getNome());
							telaOS.getTfVendedorOS().setText(cliente.getVendedor());
							telaOS.getTfContatoOS().setText(cliente.getContato());
							telaOS.getTfEmailOS().setText(cliente.getEmail());
							telaOS.getTfFone1OS().setText(cliente.getFone1());
							telaOS.getTfFone2OS().setText(cliente.getFone2());
							telaOS.getTpDetalhesOS().setText(cliente.getDetalhes());

							telaOS.setLocationRelativeTo(null);

						} catch (Exception e) {
							e.printStackTrace();

							JOptionPane.showMessageDialog(null, "Erro " + e);
						}

						btnVoltar.setEnabled(false);
						btnSalvar.setEnabled(true);
						btnOrdemServiço.setEnabled(true);
						btnVisualizar.setEnabled(true);

					} else {

						cbEmpresa.setSelectedIndex(0);
						cbTipo.setSelectedIndex(0);
						tfQuantidade.setText("");
						dcDataEntrada.setDate(null);
						epProducao.setText("");

						cbEmpresa.setEnabled(true);
						// cbTipo.setEnabled(true);
						tfQuantidade.setEnabled(true);
						dcDataEntrada.setEnabled(true);
						epProducao.setEnabled(true);

						cbEmpresa.setEditable(true);
						// cbTipo.setEditable(true);
						tfQuantidade.setEditable(true);
						dcDataEntrada.setEnabled(true);
						epProducao.setEditable(true);

						btnSalvar.setEnabled(true);
						btnExportar.setEnabled(true);
						btnCriarPlanilha.setEnabled(true);
						btnMostrar.setEnabled(true);
						btnImprimir.setEnabled(true);
						btnAguardarCliente.setEnabled(false);
						btnCancelar.setEnabled(false);

						btnVoltar.setEnabled(false);

						btnSalvar.setEnabled(true);

						dcDatasAnteriores.setEnabled(true);

					}

				} else if (btnAguardarCliente.getText().equalsIgnoreCase("Continuar")) {

					int posicao = tbProducao.getSelectedRow();
					int idOS = 0;

					if (posicao == -1) {

						posicao = 0;

					} else {

						idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

					}

					Producao producao = new Producao();
					ProducaoRN producaoRN = new ProducaoRN();
					producao = producaoRN.pesquisarIDOS(idOS);

					int id_planilha = producao.getId_planilha();

					java.util.Date dataUtil = dcDataEntrada.getDate();
					java.sql.Date DataSql = new java.sql.Date(dataUtil.getTime());

					producao.setEmpresa(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());
					producao.setTipo(String.valueOf(cbTipo.getSelectedItem()).toUpperCase());
					producao.setQuantidade(tfQuantidade.getText());
					producao.setDataEntrada(DataSql);
					producao.setObservacoes(epProducao.getText());

					producaoRN.atualizarProducao(producao);

					producao.setId_planilha(id_planilha);

					DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

					model.setNumRows(0);

					String dataBr;

					entradas = 0;

					int valorTotal = 0;

					planilha = planilhaRN.buscarPlanilha(id_planilha);

					dataBr = formatBR.format(planilha.getDataPlanilha());

					OrdemServicoRN osRN = new OrdemServicoRN();

					ClienteRN clienteRN = new ClienteRN();

					String id_cliente = clienteRN
							.mostrarId_cliente(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());

					osRN.atualizarTotal(producao.getOs(), producao.getQuantidade(), Integer.valueOf(id_cliente),
							producao.getObservacoes());

					btnEditar.setText("Editar");
					tbProducao.setSelectionBackground(Color.BLUE);

					cbEmpresa.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					tfQuantidade.setText("");
					dcDataEntrada.setDate(null);
					epProducao.setText("");

					btnSalvar.setEnabled(true);
					btnExportar.setEnabled(true);
					btnCriarPlanilha.setEnabled(true);
					btnMostrar.setEnabled(true);
					btnImprimir.setEnabled(true);
					btnAguardarCliente.setEnabled(false);
					btnCancelar.setEnabled(false);

					btnVoltar.setEnabled(false);
					btnEditar.setEnabled(true);

					Date hoje = new Date(System.currentTimeMillis());
					Time hora = new Time(System.currentTimeMillis());

					EstadoOS estadoOS = new EstadoOS();
					EstadoOSRN estadoOSRN = new EstadoOSRN();

					estadoOS.setId_os(producao.getOs());
					estadoOS.setEstagio(1);
					estadoOS.setDataInicial(hoje);
					estadoOS.setHoraInicial(hora);
					estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

					estadoOSRN.criarEstadoOS(estadoOS);

					estagio = estadoOSRN.buscarEstagio(producao.getOs());

					String mensagem = String.valueOf(estagio);

					mensagem = TelaOrdemServico.estagioOS(mensagem);

					osRN.atualizarStatus(producao.getOs(), mensagem);

					os = osRN.mostrarOS(id_os);

					producao.setOrdemservico(os);

					producaoRN.atualizarProducao(producao);

					listaProducao = producaoRN.listaProducaoData(producao.getId_planilha());

					for (Producao p : listaProducao) {

						dataBr = formatBR.format(p.getDataEntrada());
						String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
								
						Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
								p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
								dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

						if (p.getQuantidade() == null) {

							valorTotal = valorTotal + 0;

						} else {

							valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

						}

						entradas++;

						model.addRow(linha);

					}

					planilha.setTotal(String.valueOf(valorTotal));

					planilhaRN.atualizarPlanilha(planilha);

					lblDataPlanilha.setText(String.valueOf(dataBr));
					lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
					lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

					try {

						TelaOrdemServico telaOS = new TelaOrdemServico();

						osRN = new OrdemServicoRN();
						os = new OrdemServico();

						os = osRN.mostrarOS(producao.getOs());

						Cliente cliente = new Cliente();

						cliente = clienteRN.mostrarCliente(os.getId_cliente());

						telaOS.getTfOS().setText(String.valueOf(os.getId_os()));
						telaOS.getTfTotal().setText(os.getTotal());
						telaOS.getDtRecebimento().setText(String.valueOf(os.getDtRecebimento()));
						telaOS.getDtPrevista().setText(String.valueOf(os.getDtPrevista()));

						telaOS.getTfClienteOS().setText(cliente.getNome());
						telaOS.getTfVendedorOS().setText(cliente.getVendedor());
						telaOS.getTfContatoOS().setText(cliente.getContato());
						telaOS.getTfEmailOS().setText(cliente.getEmail());
						telaOS.getTfFone1OS().setText(cliente.getFone1());
						telaOS.getTfFone2OS().setText(cliente.getFone2());
						telaOS.getTpDetalhesOS().setText(cliente.getDetalhes());

						telaOS.setLocationRelativeTo(null);

					} catch (Exception e) {
						e.printStackTrace();
					}

					btnAguardarCliente.setText("Aguardar");

					cbEmpresa.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					tfQuantidade.setText("");
					dcDataEntrada.setDate(null);
					epProducao.setText("");

					cbEmpresa.setEnabled(true);
					// cbTipo.setEnabled(true);
					tfQuantidade.setEnabled(true);
					dcDataEntrada.setEnabled(true);
					epProducao.setEnabled(true);

					cbEmpresa.setEditable(true);
					// cbTipo.setEditable(true);
					tfQuantidade.setEditable(true);
					dcDataEntrada.setEnabled(true);
					epProducao.setEditable(true);

					btnSalvar.setEnabled(true);
					btnExportar.setEnabled(true);
					btnCriarPlanilha.setEnabled(true);
					btnMostrar.setEnabled(true);
					btnImprimir.setEnabled(true);
					btnAguardarCliente.setEnabled(false);
					btnCancelar.setEnabled(false);

					btnVoltar.setEnabled(false);
					btnEditar.setEnabled(true);

					btnVoltar.setEnabled(false);
					btnSalvar.setEnabled(true);
					btnOrdemServiço.setEnabled(true);
					btnVisualizar.setEnabled(true);

				} else {

					cbEmpresa.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					tfQuantidade.setText("");
					dcDataEntrada.setDate(null);
					epProducao.setText("");

					cbEmpresa.setEnabled(true);
					// cbTipo.setEnabled(true);
					tfQuantidade.setEnabled(true);
					dcDataEntrada.setEnabled(true);
					epProducao.setEnabled(true);

					cbEmpresa.setEditable(true);
					// cbTipo.setEditable(true);
					tfQuantidade.setEditable(true);
					dcDataEntrada.setEnabled(true);
					epProducao.setEditable(true);

					btnSalvar.setEnabled(true);
					btnExportar.setEnabled(true);
					btnCriarPlanilha.setEnabled(true);
					btnMostrar.setEnabled(true);
					btnImprimir.setEnabled(true);
					btnAguardarCliente.setEnabled(false);
					btnCancelar.setEnabled(false);

					btnVoltar.setEnabled(false);
					btnEditar.setEnabled(false);

					btnVoltar.setEnabled(false);
					btnSalvar.setEnabled(true);
					btnOrdemServiço.setEnabled(true);
					btnVisualizar.setEnabled(true);

				}

			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int posicao = tbProducao.getSelectedRow();
				int idOS = 0;

				if (posicao == -1) {

					posicao = 0;

				} else {

					idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

				}

				Producao producao = new Producao();
				ProducaoRN producaoRN = new ProducaoRN();
				producao = producaoRN.pesquisarIDOS(idOS);

				if (listaProducao == null) {

					JOptionPane.showMessageDialog(null, "Selecione o planilha e depois o pedido que quer editar!");

				} else if (tbProducao.getSelectedRow() == -1) {

					JOptionPane.showMessageDialog(null, "Selecione o pedido que quer editar!");

				} else {

					if (btnEditar.getText().equals("Editar")) {

						int resposta = 0;

						resposta = JOptionPane.showConfirmDialog(null, "Você deseja mesmo editar um pedido?", "Editar",
								JOptionPane.YES_NO_OPTION);

						if (resposta == 0) {

							btnEditar.setText("Salvar");
							btnVoltar.setEnabled(true);
							btnSalvar.setEnabled(false);
							btnImprimir.setEnabled(false);
							btnMostrar.setEnabled(false);
							btnExportar.setEnabled(false);
							btnOrdemServiço.setEnabled(false);
							btnVisualizar.setEnabled(false);

							if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 7).equals("AGUARDANDO O CLIENTE")) {

								btnEditar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnAguardarCliente.setEnabled(true);
								btnAguardarCliente.setText("Continuar");

								cbEmpresa.setEditable(false);
								tfQuantidade.setEditable(false);
								dcDataEntrada.setEnabled(false);
								epProducao.setEditable(false);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 7)
									.equals("ORDEM DE SERVIÇO CANCELADA")) {

								btnEditar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnAguardarCliente.setEnabled(false);

								cbEmpresa.setSelectedItem(null);
								// cbTipo.setSelectedItem(null);
								tfQuantidade.setText("");
								dcDataEntrada.setDate(null);
								epProducao.setText("");

								cbEmpresa.setEditable(false);
								// cbTipo.setEditable(false);
								tfQuantidade.setEditable(false);
								dcDataEntrada.setEnabled(false);
								epProducao.setEditable(false);

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 7).equals("IMPRESSÃO")) {

								cbEmpresa.setEditable(true);
								// cbTipo.setEditable(true);
								tfQuantidade.setEditable(true);
								dcDataEntrada.setEnabled(true);
								epProducao.setEditable(true);

								btnAguardarCliente.setVisible(true);

								btnSalvar.setEnabled(false);

								btnEditar.setEnabled(true);
								btnCancelar.setEnabled(true);
								btnAguardarCliente.setEnabled(true);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 7)
									.equals("LAMINAÇÃO / EXPEDIÇÃO")) {

								cbEmpresa.setEditable(true);
								// cbTipo.setEditable(true);
								tfQuantidade.setEditable(true);
								dcDataEntrada.setEnabled(true);
								epProducao.setEditable(true);

								btnAguardarCliente.setVisible(true);

								btnSalvar.setEnabled(false);

								btnEditar.setEnabled(true);
								btnCancelar.setEnabled(true);
								btnAguardarCliente.setEnabled(true);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 6).equals("RECEPÇÃO")) {

								cbEmpresa.setEditable(false);
								tfQuantidade.setEditable(false);
								dcDataEntrada.setEnabled(false);
								epProducao.setEditable(false);
								btnAguardarCliente.setVisible(true);

								btnSalvar.setEnabled(false);

								btnEditar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnAguardarCliente.setEnabled(false);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 6).equals("ENTREGAR")) {

								cbEmpresa.setEditable(false);
								tfQuantidade.setEditable(false);
								dcDataEntrada.setEnabled(false);
								epProducao.setEditable(false);
								btnAguardarCliente.setVisible(true);

								btnSalvar.setEnabled(false);

								btnEditar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnAguardarCliente.setEnabled(false);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							} else if (tbProducao.getValueAt(tbProducao.getSelectedRow(), 7)
									.equals("PEDIDO FINALIZADO")) {

								cbEmpresa.setEditable(false);
								tfQuantidade.setEditable(false);
								dcDataEntrada.setEnabled(false);
								epProducao.setEditable(false);
								btnAguardarCliente.setVisible(true);

								btnSalvar.setEnabled(false);

								btnEditar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnAguardarCliente.setEnabled(false);

								dcDataEntrada.setDate(producao.getDataEntrada());

								cbEmpresa.setSelectedItem(producao.getEmpresa());
								cbTipo.setSelectedItem(producao.getTipo());
								tfQuantidade.setText(producao.getQuantidade());
								epProducao.setText(producao.getObservacoes());

							}

						} else {

							btnSalvar.setEnabled(true);
							btnOrdemServiço.setEnabled(true);
							btnVisualizar.setEnabled(true);

						}

					} else {

						int resposta = 0;

						resposta = JOptionPane.showConfirmDialog(null, "Você deseja salvar o pedido Editado?",
								"Salvar Editado", JOptionPane.YES_NO_OPTION);

						if (resposta == 0) {

							posicao = tbProducao.getSelectedRow();
							idOS = 0;

							if (posicao == -1) {

								posicao = 0;

							} else {

								idOS = Integer.valueOf((String) tbProducao.getValueAt(posicao, 1));

							}

							producao = producaoRN.pesquisarIDOS(idOS);

							entradas = 0;

							int id_planilha = producao.getId_planilha();

							java.util.Date dataUtil = dcDataEntrada.getDate();
							java.sql.Date DataSql = new java.sql.Date(dataUtil.getTime());

							producao.setEmpresa(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());
							producao.setTipo(String.valueOf(cbTipo.getSelectedItem()).toUpperCase());
							producao.setQuantidade(tfQuantidade.getText());
							producao.setDataEntrada(DataSql);
							producao.setObservacoes(epProducao.getText().toUpperCase());

							producaoRN.atualizarProducao(producao);

							producao.setId_planilha(id_planilha);

							listaProducao = producaoRN.listaProducaoData(producao.getId_planilha());

							DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

							model.setNumRows(0);

							String dataBr;

							int valorTotal = 0;

							for (Producao p : listaProducao) {

								dataBr = formatBR.format(p.getDataEntrada());
								String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
										
								Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
										p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
										dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

								entradas++;

								if (p.getQuantidade() == null) {

									valorTotal = valorTotal + 0;

								} else {

									valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

								}

								model.addRow(linha);

							}

							planilha = planilhaRN.buscarPlanilha(id_planilha);

							planilha.setTotal(String.valueOf(valorTotal));

							planilhaRN.atualizarPlanilha(planilha);

							dataBr = formatBR.format(planilha.getDataPlanilha());

							OrdemServicoRN osRN = new OrdemServicoRN();

							ClienteRN clienteRN = new ClienteRN();

							String id_cliente = clienteRN
									.mostrarId_cliente(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());

							osRN.atualizarTotal(producao.getOs(), producao.getQuantidade(), Integer.valueOf(id_cliente),
									producao.getObservacoes());

							lblDataPlanilha.setText(String.valueOf(dataBr));
							lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
							lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

							btnEditar.setText("Editar");
							tbProducao.setSelectionBackground(Color.BLUE);

							cbEmpresa.setSelectedIndex(0);
							cbTipo.setSelectedIndex(0);
							tfQuantidade.setText("");
							dcDataEntrada.setDate(null);
							epProducao.setText("");

							btnSalvar.setEnabled(true);
							btnExportar.setEnabled(true);
							btnCriarPlanilha.setEnabled(true);
							btnMostrar.setEnabled(true);
							btnImprimir.setEnabled(true);
							btnAguardarCliente.setEnabled(false);
							btnCancelar.setEnabled(false);

							btnVoltar.setEnabled(false);

							btnSalvar.setEnabled(true);
							btnOrdemServiço.setEnabled(true);
							btnVisualizar.setEnabled(true);

						} else {

							btnEditar.setText("Editar");
							tbProducao.setSelectionBackground(Color.BLUE);

							cbEmpresa.setSelectedIndex(0);
							cbTipo.setSelectedIndex(0);
							tfQuantidade.setText("");
							dcDataEntrada.setDate(null);
							epProducao.setText("");

							btnSalvar.setEnabled(true);
							btnExportar.setEnabled(true);
							btnCriarPlanilha.setEnabled(true);
							btnMostrar.setEnabled(true);
							btnImprimir.setEnabled(true);
							btnAguardarCliente.setEnabled(false);
							btnCancelar.setEnabled(false);

							btnVoltar.setEnabled(false);

							btnSalvar.setEnabled(true);
							btnOrdemServiço.setEnabled(true);
							btnVisualizar.setEnabled(true);

						}

					}

				}
			}

		});
		pDadosDaPlanilha.setLayout(null);
		pDadosDaPlanilha.add(lblNumero);
		pDadosDaPlanilha.add(lblPlanilhaDia);
		pDadosDaPlanilha.add(lblTotal);
		pDadosDaPlanilha.add(lblN);
		pDadosDaPlanilha.add(lblValorTotal);
		pDadosDaPlanilha.add(lblDataPlanilha);
		pEntradaDePedido.setLayout(null);
		pEntradaDePedido.add(lblEmpresa);
		pEntradaDePedido.add(lblQuantidade);
		pEntradaDePedido.add(tfQuantidade);
		pEntradaDePedido.add(cbEmpresa);
		pEntradaDePedido.add(lblTipo);
		pEntradaDePedido.add(lblDataEntrada);
		pEntradaDePedido.add(cbTipo);
		pEntradaDePedido.add(dcDataEntrada);
		pEntradaDePedido.add(btnExportar);
		pEntradaDePedido.add(btnImprimir);
		contentPane.setLayout(null);
		contentPane.add(pNomePlanilha);
		contentPane.add(pEntradaDePedido);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 71, 428, 44);
		pEntradaDePedido.add(scrollPane);

		epProducao = new JEditorPane();
		epProducao.setToolTipText("Digite as observar\u00E7\u00F5es");
		scrollPane.setViewportView(epProducao);
		epProducao.setForeground(Color.BLACK);
		epProducao.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		epProducao.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/salva1.png")));
		btnSalvar.setToolTipText("Salvar");
		btnSalvar.setBounds(444, 74, 100, 35);
		pEntradaDePedido.add(btnSalvar);
		btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JLabel lblObservações = new JLabel("Observa\u00E7\u00F5es");
		lblObservações.setBounds(10, 47, 114, 30);
		pEntradaDePedido.add(lblObservações);
		lblObservações.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int valorTotal = 0;

				OrdemServicoRN osRN = new OrdemServicoRN();
				OrdemServico os = new OrdemServico();

				Date hoje = new Date(System.currentTimeMillis());
				Time hora = new Time(System.currentTimeMillis());

				PlanilhaRN planilhaRN = new PlanilhaRN();

				planilha = planilhaRN.verificarPlanilha(hoje);

				if (planilha.getDataPlanilha() == null) {

					JOptionPane.showMessageDialog(null, "Não existe planilha criada ainda na data de hoje!",
							"Planilha de Crachás", JOptionPane.YES_NO_OPTION);
					cbEmpresa.setSelectedIndex(0);
					cbTipo.setSelectedIndex(0);
					dcDataEntrada.setDate(null);
					tfQuantidade.setText("");

				} else if (cbEmpresa.getSelectedItem().equals("")) {

					JOptionPane.showMessageDialog(null, "Qual o nome da empresa?");

				} else if (cbTipo.getSelectedItem().equals("")) {

					JOptionPane.showMessageDialog(null, "Qual o tipo de produto?");

				} else if (dcDataEntrada.getDate() == null) {

					JOptionPane.showMessageDialog(null, "Qual a data de entrada do pedido?");

				} else if (tfQuantidade.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Qual a quantidade o pedido?");

				} else if (dcDataEntrada.getDate().after(hoje)) {

					JOptionPane.showMessageDialog(null, "A data de entrada não pode ser maior que o dia atual!");

					dcDataEntrada.setDate(null);

				} else {

					java.util.Date dataUtil = dcDataEntrada.getDate();
					java.sql.Date DataSql = new java.sql.Date(dataUtil.getTime());

					Producao producao = new Producao(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase(),
							String.valueOf(cbTipo.getSelectedItem()), TelaLogin.getOperador().getLogin(),
							tfQuantidade.getText(), DataSql, epProducao.getText().toUpperCase(), id_os);

					ClienteRN clienteRN = new ClienteRN();
					boolean contem = clienteRN.pesquisaCombo(producao.getEmpresa());

					if (contem == false) {

						JOptionPane.showMessageDialog(null, "Cliente não cadastrado, cadastre-o primeiro!");

						cbEmpresa.setSelectedItem(null);
						cbTipo.setSelectedItem(null);

						try {

							UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
							TelaCadastroClientes dialog = new TelaCadastroClientes();
							dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}

						cbEmpresa.removeAllItems();

						List<Cliente> listaCliente = new ArrayList<Cliente>();
						listaCliente = clienteRN.listarClientes();

						cbEmpresa.addItem("");

						for (Cliente c : listaCliente) {

							cbEmpresa.addItem(c.getNome());

						}

						cbTipo.removeAllItems();

						List<Produto> listaProduto = new ArrayList<Produto>();
						ProdutoRN produtoRN = new ProdutoRN();
						listaProduto = produtoRN.listarProduto();

						cbTipo.addItem("");

						for (Produto pr : listaProduto) {

							cbTipo.addItem(pr.getNome());

						}

					} else {

						Cliente cliente = new Cliente();

						ProducaoRN producaoRN = new ProducaoRN();

						int id_producao = producaoRN.inserirProducaoRN(producao, planilha.getId_planilha());

						producao = producaoRN.pesquisarID(id_producao);

						// lista que vai servir para criar a tabela producao

						DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

						model.setNumRows(0);

						String dataBr;

						// atualiza a planilha com novos valores

						dataBr = formatBR.format(planilha.getDataPlanilha());

						// criação da ordem de serviço
						OrdemServicoRN ordemServicoRN = new OrdemServicoRN();
						OrdemServico ordemServico = new OrdemServico();

						ordemServico.setStatus("IMPRESSÃO");
						ordemServico.setTotal(tfQuantidade.getText());
						ordemServico.setDtRecebimento(DataSql);

						GregorianCalendar dtPrevisao = new GregorianCalendar();
						dtPrevisao.setTime(DataSql);
						int inteiro = 7;
						dtPrevisao.add(Calendar.DAY_OF_MONTH, inteiro);
						java.util.Date dataUtil2 = dtPrevisao.getTime();
						java.sql.Date DataSql2 = new java.sql.Date(dataUtil2.getTime());

						ordemServico.setDtPrevista(DataSql2);

						int id_cliente = clienteRN.clienteOS(String.valueOf(cbEmpresa.getSelectedItem()).toUpperCase());

						ordemServico.setId_cliente(id_cliente);

						ordemServico.setMotivo(epProducao.getText().toUpperCase());

						id_os = ordemServicoRN.criarOrdemServico(ordemServico);

						entradas = 0;

						producao.setOs(id_os);

						planilha.setId_planilha(producao.getId_planilha());

						planilhaRN.atualizarPlanilha(planilha);
						producaoRN.atualizarProducao(producao);

						producao.setOrdemservico(os);

						listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

						for (Producao p : listaProducao) {

							dataBr = formatBR.format(p.getDataEntrada());
							String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
									
							Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
									p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
									dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};

							entradas++;

							if (p.getQuantidade() == null) {

								valorTotal = valorTotal + 0;

							} else {

								valorTotal = valorTotal + Integer.valueOf(p.getQuantidade());

							}

							model.addRow(linha);

						}

						// atualiza o valor total

						planilha.setTotal(String.valueOf(valorTotal));

						producaoRN.atualizarProducao(producao);
						planilhaRN.atualizarPlanilha(planilha);

						String dataBr3 = formatBR.format(planilha.getDataPlanilha());

						lblDataPlanilha.setText(String.valueOf(dataBr3));
						lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
						lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

						EstadoOS estadoOS = new EstadoOS();
						EstadoOSRN estadoOSRN = new EstadoOSRN();

						estadoOS.setId_os(id_os);
						estadoOS.setEstagio(1);
						estadoOS.setDataInicial(hoje);
						estadoOS.setHoraInicial(hora);
						estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

						estadoOSRN.criarEstadoOS(estadoOS);

						estagio = estadoOSRN.buscarEstagio(id_os);

						String mensagem = String.valueOf(estagio);

						mensagem = TelaOrdemServico.estagioOS(mensagem);

						osRN.atualizarStatus(id_os, mensagem);

						try {

							TelaOrdemServico telaOS = new TelaOrdemServico();

							osRN = new OrdemServicoRN();
							os = new OrdemServico();

							os = osRN.mostrarOS(TelaProducao.id_os);

							cliente = clienteRN.mostrarCliente(os.getId_cliente());

							telaOS.getTfOS().setText(String.valueOf(os.getId_os()));
							telaOS.getTfTotal().setText(os.getTotal());
							telaOS.getDtRecebimento().setText(String.valueOf(os.getDtRecebimento()));
							telaOS.getDtPrevista().setText(String.valueOf(os.getDtPrevista()));
							telaOS.getTfClienteOS().setText(cliente.getNome());
							telaOS.getTfVendedorOS().setText(cliente.getVendedor());
							telaOS.getTfContatoOS().setText(cliente.getContato());
							telaOS.getTfEmailOS().setText(cliente.getEmail());
							telaOS.getTfFone1OS().setText(cliente.getFone1());
							telaOS.getTfFone2OS().setText(cliente.getFone2());
							telaOS.getTpDetalhesOS().setText(cliente.getDetalhes());

							String entrega;

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

						} catch (Exception e) {
							e.printStackTrace();
						}

						cbEmpresa.setSelectedIndex(0);
						cbTipo.setSelectedIndex(0);

						tfQuantidade.setText("");
						dcDataEntrada.setDate(null);
						epProducao.setText("");

						cbEmpresa.requestFocus();

						// Enviar E-mail
						int resposta = JOptionPane.showConfirmDialog(null,
								"Deseja enviar um e-mail de confirmação de recebimento?", "Email de Confirmação",
								JOptionPane.YES_NO_OPTION);

						if (resposta == JOptionPane.YES_OPTION) {

							Email email = new Email();

							try {

								email.pedidoRecebido(cliente);

							} catch (EmailException e) {

								JOptionPane.showMessageDialog(null, "O e-mail não foi enviado! \n" + e.getMessage());
							}

						}

					}

				}

			}
		});
		contentPane.add(pDadosDaPlanilha);
		contentPane.add(pPlanilhaAnteriores);
		contentPane.add(pEditarPlanilha);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/voltar.png")));
		btnVoltar.setToolTipText("Voltar");
		btnVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVoltar.setEnabled(false);
		btnVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				btnEditar.setText("Editar");
				btnEditar.setEnabled(true);
				tbProducao.setSelectionBackground(Color.BLUE);

				cbEmpresa.setSelectedIndex(0);
				cbTipo.setSelectedIndex(0);
				tfQuantidade.setText("");
				dcDataEntrada.setDate(null);
				epProducao.setText("");

				cbEmpresa.setEnabled(true);
				cbTipo.setEnabled(true);
				tfQuantidade.setEnabled(true);
				dcDataEntrada.setEnabled(true);
				epProducao.setEnabled(true);

				cbEmpresa.setEditable(true);
				// cbTipo.setEditable(true);
				tfQuantidade.setEditable(true);
				dcDataEntrada.setEnabled(true);
				epProducao.setEnabled(true);

				btnSalvar.setEnabled(true);
				btnExportar.setEnabled(true);
				btnCriarPlanilha.setEnabled(true);
				btnMostrar.setEnabled(true);
				btnImprimir.setEnabled(true);

				btnCancelar.setEnabled(false);
				btnAguardarCliente.setEnabled(false);
				btnVoltar.setEnabled(false);
				btnOrdemServiço.setEnabled(true);
				btnVisualizar.setEnabled(true);

			}
		});
		btnVoltar.setBounds(358, 15, 100, 35);
		pEditarPlanilha.add(btnVoltar);
		contentPane.add(lblLogado);
		contentPane.add(spProducao);

		JPanel pOS = new JPanel();
		pOS.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Ordem de Servi\u00E7o",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pOS.setBounds(796, 62, 150, 124);
		contentPane.add(pOS);
		pOS.setLayout(null);

		btnOrdemServiço = new JButton("Pesquisar OS");
		btnOrdemServiço.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnOrdemServiço.setToolTipText("Pesquisar OS");
		btnOrdemServiço.setHorizontalAlignment(SwingConstants.LEFT);
		btnOrdemServiço.setBounds(9, 14, 135, 32);
		pOS.add(btnOrdemServiço);
		btnOrdemServiço.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnOrdemServiço.setFocusPainted(false);
		btnOrdemServiço.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		btnVisualizar = new JButton("Visualizar OS");
		btnVisualizar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/ordem servico 2.png")));
		btnVisualizar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnVisualizar.setToolTipText("Visualizar OS");
		btnVisualizar.setHorizontalAlignment(SwingConstants.LEFT);
		btnVisualizar.setBounds(9, 48, 135, 32);
		btnVisualizar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pOS.add(btnVisualizar);

		JButton btnEntregar = new JButton("Entregar");
		btnEntregar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/entregar.png")));
		btnEntregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaEntregar telaEntregar = new TelaEntregar();
					telaEntregar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					telaEntregar.setLocationRelativeTo(null);
					telaEntregar.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnEntregar.setToolTipText("Entregar pedido");
		btnEntregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEntregar.setBounds(9, 82, 135, 32);
		pOS.add(btnEntregar);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/exit.png")));
		btnLogout.setToolTipText("Logout");
		btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer o Logout?", "Logout",
						JOptionPane.OK_CANCEL_OPTION);

				if (resposta == JOptionPane.OK_OPTION) {

					TelaLogin.getOperador().setId_operador(0);
					TelaLogin.getOperador().setNome("");
					TelaLogin.getOperador().setAcesso("");
					TelaLogin.getOperador().setLogin("");
					TelaLogin.getOperador().setSenha("");

					dispose();

					try {

						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						TelaLogin frame = new TelaLogin();
						frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);

					} catch (Exception e) {

						e.printStackTrace();

					}

				}

			}
		});
		btnLogout.setBounds(728, 561, 105, 35);
		contentPane.add(btnLogout);

		brnFechar = new JButton("Sair");
		brnFechar.setIcon(new ImageIcon(TelaProducao.class.getResource("/br/com/crachas/image/cancelar.png")));
		brnFechar.setToolTipText("Sair");
		brnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.exit(0);

			}
		});
		brnFechar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		brnFechar.setBounds(843, 561, 100, 35);
		contentPane.add(brnFechar);
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TelaOrdemServico telaOS = new TelaOrdemServico();

				telaOS.getBtnIniciar().setEnabled(true);

				int posicao = 0;

				if (tbProducao.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!", "Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (tbProducao.getSelectedRow() == -1) {

						JOptionPane.showMessageDialog(null, "Selecione o registro na planiha!", "Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

					} else {

						posicao = tbProducao.getSelectedRow();

						id_os = Integer.parseInt(tbProducao.getValueAt(posicao, 1).toString());

						os = osRN.mostrarOS(id_os);

						EstadoOSRN estadoOSRN = new EstadoOSRN();

						estagio = estadoOSRN.buscarEstagio(id_os);

						telaOS.setEstagio(estagio);

						String mensagem = String.valueOf(estagio);

						mensagem = TelaOrdemServico.estagioOS(mensagem);

						osRN.atualizarStatus(id_os, mensagem);

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
							
							String total = os.getTotal().replaceAll("[^0-9]", "");
							
							telaOS.getTfTotal().setText(tresDigitos.format(Integer.valueOf(total)));
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

					}

					java.sql.Date dataEscolhida;

					try {

						dataEscolhida = listaProducao.get(0).getPlanilha().getDataPlanilha();
						planilha = planilhaRN.verificarPlanilha(dataEscolhida);

					} catch (Exception e) {

					}

					int valorTotal = 0;

					listaProducao = producaoRN.listaProducaoData(planilha.getId_planilha());

					entradas = 0;

					String dataBr = "";

					DefaultTableModel model = (DefaultTableModel) tbProducao.getModel();

					model.setNumRows(0);

					for (Producao p : listaProducao) {

						
						dataBr = formatBR.format(p.getDataEntrada());
						String quantidade = p.getQuantidade().replaceAll("[^0-9]", "");
								
						Object[] linha = { tresDigitos.format(entradas + 1), cincoDigitos.format(p.getOs()),
								p.getEmpresa(), p.getTipo(), p.getOperador(), tresDigitos.format(Integer.valueOf(quantidade)), 
								dataBr, p.getOrdemservico().getStatus(), p.getObservacoes()};
						
						entradas++;

						if (quantidade == "") {

							valorTotal = valorTotal + 0;

						} else {

							valorTotal = valorTotal + Integer.valueOf(quantidade);

						}

						model.addRow(linha);

					}

					try {

						tbProducao.setRowSelectionInterval(posicao, posicao);

					} catch (Exception e) {

						e.printStackTrace();

					}

					lblDataPlanilha.setText(dataBr);
					lblValorTotal.setText(String.valueOf(tresDigitos.format(valorTotal)));
					lblNumero.setText(String.valueOf(tresDigitos.format(entradas)));

				}

			}
		});

		btnOrdemServiço.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					TelaPesquisarOrdemServico dialog = new TelaPesquisarOrdemServico();
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnExportar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (tbProducao.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Não há uma planilha aberta, faça uma pesquisa!", "Visualizar ordem de serviço", JOptionPane.INFORMATION_MESSAGE);

				} else {

					if (listaProducao.isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "Planilha vazia", "Exportar Planilha",
								JOptionPane.ERROR_MESSAGE);

					} else {

						ExportarCsv exportarCsv = new ExportarCsv();

						String caminho = "C:/NE.Crachás/planilha_" + formatBR2.format(planilha.getDataPlanilha());

						boolean sucesso;

						try {

							sucesso = exportarCsv.exportarProducaoCsv(caminho, listaProducao);

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
		btnCriarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Date hoje = new Date(System.currentTimeMillis());
				Planilha planilha = new Planilha(hoje);
				PlanilhaRN planilhaRN = new PlanilhaRN();

				planilha = planilhaRN.verificarPlanilha(hoje);

				String dateBr = formatBR.format(hoje);

				if (planilha.getDataPlanilha() != null) {

					JOptionPane.showMessageDialog(null, "A planilha do dia " + dateBr + " já foi criada!");

				} else {

					int resposta = 0;

					resposta = JOptionPane.showConfirmDialog(null,
							"Você deseja criar a planilha do dia " + dateBr + " ?", "Criar planilha?",
							JOptionPane.YES_NO_OPTION);

					if (resposta == 0) {

						planilha.setId_planilha(planilha.getId_planilha() + 1);
						planilha.setStatus(true);
						planilha.setDataPlanilha(hoje);
						planilha.setTotal("0");

						planilhaRN.criarPlanilha(planilha);

					} else {

						JOptionPane.showMessageDialog(null, "Não foi criada a planilha " + dateBr);

					}

				}

			}
		});
	}

	public static JTable getTbProducao() {
		return tbProducao;
	}

	public static void setTbProducao(JTable tbProducao) {
		TelaProducao.tbProducao = tbProducao;
	}

	public TelaProducao(JTable tbProducao) {
		super();
		TelaProducao.tbProducao = tbProducao;
	}

	public JComboBox<String> getCbEmpresa() {
		return cbEmpresa;
	}

	public void setCbEmpresa(JComboBox<String> cbEmpresa) {
		this.cbEmpresa = cbEmpresa;
	}

	public List<Producao> getListaProducao() {
		return listaProducao;
	}

	public void setListaProducao(List<Producao> listaProducao) {
		TelaProducao.listaProducao = listaProducao;
	}

	public JLabel getLblDataPlanilha() {
		return lblDataPlanilha;
	}

	public void setLblDataPlanilha(JLabel lblDataPlanilha) {
		TelaProducao.lblDataPlanilha = lblDataPlanilha;
	}

	public JLabel getLblValorTotal() {
		return lblValorTotal;
	}

	public void setLblValorTotal(JLabel lblValorTotal) {
		TelaProducao.lblValorTotal = lblValorTotal;
	}

	public JLabel getLblNumero() {
		return lblNumero;
	}

	public void setLblNumero(JLabel lblNumero) {
		TelaProducao.lblNumero = lblNumero;
	}

	public JLabel getLblLogado() {
		return lblLogado;
	}

	public void setLblLogado(JLabel lblLogado) {
		this.lblLogado = lblLogado;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public JButton getBtnConfiguracoes() {
		return btnConfiguracoes;
	}

	public void setBtnConfiguracoes(JButton btnConfiguracoes) {
		this.btnConfiguracoes = btnConfiguracoes;
	}

	public Producao getProducao() {
		return producao;
	}

	public void setProducao(Producao producao) {
		this.producao = producao;
	}

	public ProducaoRN getProducaoRN() {
		return producaoRN;
	}

	public void setProducaoRN(ProducaoRN producaoRN) {
		this.producaoRN = producaoRN;
	}
}