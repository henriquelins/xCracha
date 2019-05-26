package br.com.crachas.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;
import br.com.crachas.controller.Email;
import br.com.crachas.controller.EmailRN;
import br.com.crachas.controller.Operador;
import br.com.crachas.controller.OperadorRN;
import br.com.crachas.controller.Produto;
import br.com.crachas.controller.ProdutoRN;
import br.com.crachas.controller.ServidorEmail;
import br.com.crachas.controller.ServidorEmailRN;
import br.com.crachas.uteis.PesquisarComboBox;

public class TelaConfiguracoes extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972932495900568182L;
	private JPasswordField pfSenha;
	private JPasswordField pfConfirmarSenha;
	private JTextField tfNome;
	private JTextField tfLogin;
	private JComboBox<String> cbNivelAcesso;
	private JButton btnNovoOperador;
	private JButton btnEditarOperador;
	private JButton btnExcluirOperador;
	private JComboBox<String> cbNome;
	private JButton btnSalvarOperador;
	private JButton btnCancelarOperador;
	private JButton btnSalvarProdutos;
	private JButton btnCancelarProdutos;
	private JTextField tfNomeProduto;
	private JTextField tfDescricaoProduto;
	private static List<Operador> listaOperador;
	private static int posicao;
	private static int posicaoProduto;
	private JComboBox<String> cbNomeProduto;
	private List<Produto> listaProduto;
	private JButton btnAdicionarProduto;
	private JButton btnEditarProduto;
	private JButton btnExcluirProduto;
	private JTextField tfNomeCliente;
	private JTextField tfVendedor;
	private JTextField tfContato;
	private JTextField tfEmail;
	private JTextField tfFone1;
	private JTextField tfFone2;
	private JButton btnNovoCliente;
	private JTable tbCliente;
	private List<Cliente> listaCliente;
	private ClienteRN clienteRN;
	private Cliente cliente;
	private JEditorPane epDescricaoLayout;
	private JButton btnEditarCliente;
	private JButton btnPesquisarCliente;
	private JPanel pDadosClientes;
	private JButton btnVoltarCliente;
	private OperadorRN operadorRN;
	private JComboBox<String> cbSetor;
	private JLabel lblLogado;
	private JComboBox<String> cbEntrega;
	private JPanel pServidorDeEmail;
	private JTextField tfEmailDesc;
	private JTextField tfEmailNome;
	private JTextField tfEndImg;
	private JLabel lblImagem;
	private JButton btnMostrarEmailSalvo;
	private Email email;
	private JButton btnSalvarEmail;
	private JButton btnEditarEmail;
	private JButton btnCancelarEmail;
	private JButton btnAbrir;
	private JButton btnExcluirCliente;
	private JLabel lblImagemNãoEncontrada;
	private JComboBox<Object> cbUnidade;
	private JTextField tfServidorEmail;
	private JTextField tfHost;
	private JTextField tfSmtp;
	private JButton btnSalvarServidor;
	private JButton btnSairServidor;
	private JButton btnEditarServidor;
	private JButton btnCancelarServidor;
	private JPasswordField tfAutenticacao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaConfiguracoes dialog = new TelaConfiguracoes();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TelaConfiguracoes() {
		setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);

		// telaProducao = new TelaProducao();

		clienteRN = new ClienteRN();
		cliente = new Cliente();
		email = new Email();

		operadorRN = new OperadorRN();
		listaOperador = operadorRN.listaOperador();
		posicao = 0;

		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaConfiguracoes.class.getResource("/br/com/crachas/image/ne.png")));
		setTitle("Configura\u00E7\u00F5es");
		setModal(true);
		setBounds(100, 100, 620, 540);
		getContentPane().setLayout(null);

		JTabbedPane tabpaneConfiguaracoes = new JTabbedPane(JTabbedPane.TOP);
		tabpaneConfiguaracoes.setFont(new Font("Segoe UI ", Font.PLAIN, 12));
		tabpaneConfiguaracoes.setBounds(0, 0, 614, 451);
		getContentPane().add(tabpaneConfiguaracoes);

		JPanel pnCadastroClientes = new JPanel();
		pnCadastroClientes.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
			}

			public void ancestorMoved(AncestorEvent arg0) {
			}

			public void ancestorRemoved(AncestorEvent arg0) {

				DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();
				model.setNumRows(0);

				tfNomeCliente.setText("");
				tfVendedor.setText("");
				tfContato.setText("");
				tfEmail.setText("");
				tfFone1.setText("");
				tfFone2.setText("");
				epDescricaoLayout.setText("");

			}
		});
		pnCadastroClientes.setFont(new Font("Segoe UI ", Font.BOLD, 12));
		tabpaneConfiguaracoes.addTab("Cadastro de Clientes", new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cadastro_usuario.png")), pnCadastroClientes, null);
		pnCadastroClientes.setLayout(null);

		btnNovoCliente = new JButton("Novo");
		btnNovoCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/48px-add.png")));
		btnNovoCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				cbEntrega.setEnabled(true);
				cbUnidade.setEnabled(true);

				tfNomeCliente.setEditable(true);
				tfVendedor.setEditable(true);
				tfContato.setEditable(true);
				tfEmail.setEditable(true);
				tfFone1.setEditable(true);
				tfFone2.setEditable(true);
				epDescricaoLayout.setEditable(true);

				btnNovoCliente.setEnabled(false);
				btnEditarCliente.setText("Salvar");
				btnEditarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/salvar.png")));
				btnVoltarCliente.setText("Cancelar");
				btnVoltarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
				btnPesquisarCliente.setEnabled(false);
				btnExcluirCliente.setEnabled(false);

				tfNomeCliente.setText("");
				tfVendedor.setText("");
				tfContato.setText("");
				tfEmail.setText("");
				tfFone1.setText("");
				tfFone2.setText("");
				epDescricaoLayout.setText("");

				DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();

				model.setNumRows(0);

			}
		});
		btnNovoCliente.setBounds(9, 369, 100, 35);
		pnCadastroClientes.add(btnNovoCliente);

		btnEditarCliente = new JButton("Editar");
		btnEditarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar.png")));

		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnEditarCliente.getText().equals("Salvar")) {

					if (tfNomeCliente.getText().equals("") || epDescricaoLayout.getText().equals("")
							|| cbEntrega.getSelectedItem().equals("Selecione...")
							|| cbUnidade.getSelectedItem().equals("")) {

						JOptionPane.showMessageDialog(null,
								"O nome do cliente, a descrição e o entrega são campos obrigatórios!",
								"Salvar Cliente!", JOptionPane.INFORMATION_MESSAGE);

					} else {

						if (cbUnidade.getSelectedItem().equals("UF")) {

							JOptionPane.showMessageDialog(null, "Selecione a Unidade que atende o cliente",
									"Salvar Cliente!", JOptionPane.INFORMATION_MESSAGE);

						} else {

							boolean entrega = true;

							if (cbEntrega.getSelectedItem().equals("SIM")) {

								entrega = true;

							} else {

								entrega = false;

							}

							cliente.setNome(tfNomeCliente.getText().toUpperCase());
							cliente.setVendedor(tfVendedor.getText().toUpperCase());
							cliente.setContato(tfContato.getText().toUpperCase());
							cliente.setEmail(tfEmail.getText().toUpperCase());
							cliente.setFone1(tfFone1.getText());
							cliente.setFone2(tfFone2.getText());
							cliente.setDetalhes(epDescricaoLayout.getText().toUpperCase());
							cliente.setEntrega(entrega);
							cliente.setUnidade(String.valueOf(cbUnidade.getSelectedItem()));

							clienteRN.adicionarCliente(cliente);

							listaCliente = clienteRN.listarClientes();

							tfNomeCliente.setText("");
							tfVendedor.setText("");
							tfContato.setText("");
							tfEmail.setText("");
							tfFone1.setText("");
							tfFone2.setText("");
							epDescricaoLayout.setText("");
							cbEntrega.setSelectedIndex(0);
							cbUnidade.setSelectedIndex(0);

							tfNomeCliente.setEditable(false);
							tfVendedor.setEditable(false);
							tfContato.setEditable(false);
							tfEmail.setEditable(false);
							tfFone1.setEditable(false);
							tfFone2.setEditable(false);
							epDescricaoLayout.setEditable(false);

							cbEntrega.setEnabled(false);
							cbUnidade.setEnabled(false);

							btnNovoCliente.setEnabled(true);
							btnExcluirCliente.setEnabled(true);
							btnEditarCliente.setText("Editar");
							btnEditarCliente
									.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar.png")));
							btnVoltarCliente.setText("voltar");
							btnVoltarCliente
									.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));
							btnPesquisarCliente.setEnabled(true);

						}

					}

				} else if (btnEditarCliente.getText().equals("Editado")) {

					boolean entrega = true;

					if (cbEntrega.getSelectedItem().equals("Sim")) {

						entrega = true;

					} else {

						entrega = false;

					}

					btnEditarCliente.setText("Editado");
					btnNovoCliente.setEnabled(false);
					btnExcluirCliente.setEnabled(false);
					btnPesquisarCliente.setEnabled(false);
					btnVoltarCliente.setText("Cancelar");
					btnVoltarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir.png")));

					int editarLinha = tbCliente.getSelectedRow();

					cliente.setId_cliente(listaCliente.get(editarLinha).getId_cliente());
					cliente.setNome(tfNomeCliente.getText().toUpperCase());
					cliente.setVendedor(tfVendedor.getText().toUpperCase());
					cliente.setContato(tfContato.getText().toUpperCase());
					cliente.setEmail(tfEmail.getText().toUpperCase());
					cliente.setFone1(tfFone1.getText());
					cliente.setFone2(tfFone2.getText());
					cliente.setDetalhes(epDescricaoLayout.getText().toUpperCase());
					cliente.setEntrega(entrega);
					cliente.setUnidade(String.valueOf(cbUnidade.getSelectedItem()));

					clienteRN.editarcliente(cliente);

					listaCliente = clienteRN.listarClientes();

					tfNomeCliente.setText("");
					tfVendedor.setText("");
					tfContato.setText("");
					tfEmail.setText("");
					tfFone1.setText("");
					tfFone2.setText("");
					epDescricaoLayout.setText("");
					cbEntrega.setSelectedIndex(0);
					cbUnidade.setSelectedIndex(0);

					tfNomeCliente.setEditable(false);
					tfVendedor.setEditable(false);
					tfContato.setEditable(false);
					tfEmail.setEditable(false);
					tfFone1.setEditable(false);
					tfFone2.setEditable(false);
					epDescricaoLayout.setEditable(false);

					cbEntrega.setEnabled(false);
					cbUnidade.setEnabled(false);

					btnEditarCliente.setText("Editar");
					btnNovoCliente.setEnabled(true);
					btnPesquisarCliente.setEnabled(true);
					btnExcluirCliente.setEnabled(true);
					btnVoltarCliente.setText("Voltar");
					btnVoltarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

					DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();

					model.setNumRows(0);

				} else {

					if (tbCliente.getRowCount() == 0) {

						JOptionPane.showMessageDialog(null, "Faça uma pesquisa primeiro!");

					} else {

						if (tfNomeCliente.getText().equals("") || epDescricaoLayout.getText().equals("")) {

							JOptionPane.showMessageDialog(null, "Selecione o cliente na planilha!");

						} else {

							btnEditarCliente.setText("Editado");
							btnNovoCliente.setEnabled(false);
							btnVoltarCliente.setText("Cancelar");
							btnVoltarCliente
									.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
							btnPesquisarCliente.setEnabled(false);
							btnExcluirCliente.setEnabled(false);

							tfNomeCliente.setEditable(true);
							tfVendedor.setEditable(true);
							tfContato.setEditable(true);
							tfEmail.setEditable(true);
							tfFone1.setEditable(true);
							tfFone2.setEditable(true);
							epDescricaoLayout.setEditable(true);

							cbEntrega.setEnabled(true);
							cbUnidade.setEnabled(true);

						}

					}

				}

			}
		});
		btnEditarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEditarCliente.setBounds(119, 369, 105, 35);
		pnCadastroClientes.add(btnEditarCliente);

		btnPesquisarCliente = new JButton("Pesquisar");
		btnPesquisarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/pesquisar.png")));
		btnPesquisarCliente.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				tfNomeCliente.setText("");
				tfVendedor.setText("");
				tfContato.setText("");
				tfEmail.setText("");
				tfFone1.setText("");
				tfFone2.setText("");
				epDescricaoLayout.setText("");
				cbEntrega.setSelectedIndex(0);
				cbUnidade.setSelectedIndex(0);

				cbEntrega.setEnabled(false);
				cbUnidade.setEnabled(false);

				tfNomeCliente.setEditable(false);
				tfVendedor.setEditable(false);
				tfContato.setEditable(false);
				tfEmail.setEditable(false);
				tfFone1.setEditable(false);
				tfFone2.setEditable(false);
				epDescricaoLayout.setEditable(false);

				DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();

				model.setNumRows(0);

				try {

					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					e.printStackTrace();

				}

				TelaPesquisaCliente telaPesquisaCliente = new TelaPesquisaCliente();

				telaPesquisaCliente.setLocationRelativeTo(null);
				telaPesquisaCliente.setVisible(true);

				listaCliente = telaPesquisaCliente.getListaCliente();

				if (listaCliente.isEmpty()) {

					tfNomeCliente.setText("");
					tfVendedor.setText("");
					tfContato.setText("");
					tfEmail.setText("");
					tfFone1.setText("");
					tfFone2.setText("");
					epDescricaoLayout.setText("");
					cbEntrega.setSelectedIndex(0);
					cbUnidade.setSelectedIndex(0);

					model = (DefaultTableModel) tbCliente.getModel();

					model.setNumRows(0);

				} else {

					model = (DefaultTableModel) tbCliente.getModel();

					model.setNumRows(0);

					for (Cliente c : listaCliente) {

						String entrada = "";

						if (c.isEntrega() == true) {

							entrada = "Sim";

						} else {

							entrada = "Não";
						}

						Object[] linha = { c.getNome(), c.getVendedor(), c.getContato(), c.getEmail(), c.getFone1(),
								c.getFone2(), c.getDetalhes(), entrada, c.getUnidade() };

						model.addRow(linha);

					}

				}

			}
		});
		btnPesquisarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnPesquisarCliente.setBounds(361, 369, 116, 35);
		pnCadastroClientes.add(btnPesquisarCliente);

		pDadosClientes = new JPanel();
		pDadosClientes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pDadosClientes.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Clientes",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		pDadosClientes.setBounds(8, 0, 592, 201);
		pnCadastroClientes.add(pDadosClientes);
		pDadosClientes.setLayout(null);

		JLabel lblCliente = new JLabel("Nome Fantasia");
		lblCliente.setBounds(9, 15, 256, 14);
		pDadosClientes.add(lblCliente);
		lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfNomeCliente = new JTextField();
		tfNomeCliente.setDisabledTextColor(Color.LIGHT_GRAY);
		tfNomeCliente.setEditable(false);
		tfNomeCliente.setBounds(9, 34, 270, 25);
		pDadosClientes.add(tfNomeCliente);
		tfNomeCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfNomeCliente.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfNomeCliente.setColumns(10);

		JLabel lblVendedor = new JLabel("Vendedor");
		lblVendedor.setBounds(289, 15, 65, 14);
		pDadosClientes.add(lblVendedor);
		lblVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfVendedor = new JTextField();
		tfVendedor.setDisabledTextColor(Color.LIGHT_GRAY);
		tfVendedor.setEditable(false);
		tfVendedor.setBounds(289, 34, 133, 25);
		pDadosClientes.add(tfVendedor);
		tfVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfVendedor.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfVendedor.setColumns(10);

		JLabel lblContato = new JLabel("Contato");
		lblContato.setBounds(432, 15, 147, 14);
		pDadosClientes.add(lblContato);
		lblContato.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(8, 62, 46, 14);
		pDadosClientes.add(lblEmail);
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfEmail = new JTextField();
		tfEmail.setDisabledTextColor(Color.LIGHT_GRAY);
		tfEmail.setEditable(false);
		tfEmail.setBounds(8, 81, 201, 25);
		pDadosClientes.add(tfEmail);
		tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfEmail.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfEmail.setColumns(10);

		JLabel lblFone1 = new JLabel("Celular");
		lblFone1.setBounds(219, 62, 88, 14);
		pDadosClientes.add(lblFone1);
		lblFone1.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfFone1 = new JTextField();
		tfFone1.setDisabledTextColor(Color.LIGHT_GRAY);
		tfFone1.setEditable(false);
		tfFone1.setBounds(219, 81, 88, 25);
		pDadosClientes.add(tfFone1);
		tfFone1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfFone1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfFone1.setColumns(10);

		JLabel lblFone2 = new JLabel("Fixo");
		lblFone2.setBounds(314, 62, 88, 14);
		pDadosClientes.add(lblFone2);
		lblFone2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfFone2 = new JTextField();
		tfFone2.setDisabledTextColor(Color.LIGHT_GRAY);
		tfFone2.setEditable(false);
		tfFone2.setBounds(314, 81, 88, 25);
		pDadosClientes.add(tfFone2);
		tfFone2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfFone2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfFone2.setColumns(10);

		JLabel lblDescrioDoLayout = new JLabel("Descri\u00E7\u00E3o do Layout");
		lblDescrioDoLayout.setBounds(9, 108, 197, 14);
		pDadosClientes.add(lblDescrioDoLayout);
		lblDescrioDoLayout.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfContato = new JTextField();
		tfContato.setDisabledTextColor(Color.LIGHT_GRAY);
		tfContato.setEditable(false);
		tfContato.setBounds(432, 34, 147, 25);
		pDadosClientes.add(tfContato);
		tfContato.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfContato.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfContato.setColumns(10);

		JScrollPane spDescLayout = new JScrollPane();
		spDescLayout.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spDescLayout.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spDescLayout.setBounds(9, 125, 570, 65);
		pDadosClientes.add(spDescLayout);

		epDescricaoLayout = new JEditorPane();
		epDescricaoLayout.setForeground(Color.BLACK);
		spDescLayout.setViewportView(epDescricaoLayout);
		epDescricaoLayout.setDisabledTextColor(Color.WHITE);
		epDescricaoLayout.setEditable(false);
		epDescricaoLayout.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		epDescricaoLayout.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

		cbEntrega = new JComboBox<String>();
		cbEntrega.setEnabled(false);
		cbEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbEntrega.setModel(new DefaultComboBoxModel<String>(new String[] { "SELECIONE", "SIM", "N\u00C3O" }));
		cbEntrega.setBounds(412, 81, 88, 24);
		pDadosClientes.add(cbEntrega);

		JLabel lblEntrega = new JLabel("Entrega");
		lblEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEntrega.setBounds(412, 62, 46, 14);
		pDadosClientes.add(lblEntrega);

		cbUnidade = new JComboBox<Object>();
		cbUnidade.setEnabled(false);
		cbUnidade.setModel(new DefaultComboBoxModel<Object>(new String[] { "UF", "PE", "JP" }));
		cbUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbUnidade.setBounds(510, 81, 68, 24);
		pDadosClientes.add(cbUnidade);

		JLabel lblUnidade = new JLabel("Unidade");
		lblUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblUnidade.setBounds(510, 62, 68, 14);
		pDadosClientes.add(lblUnidade);

		JScrollPane spCliente = new JScrollPane();
		spCliente.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spCliente.setBounds(10, 206, 589, 159);
		pnCadastroClientes.add(spCliente);
		spCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tbCliente = new JTable();
		tbCliente.setForeground(Color.BLUE);
		tbCliente.setSelectionBackground(Color.BLUE);
		tbCliente.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				String entrega = "";

				int linha = tbCliente.getSelectedRow();

				tfNomeCliente.setText(listaCliente.get(linha).getNome());
				tfVendedor.setText(listaCliente.get(linha).getVendedor());
				tfContato.setText(listaCliente.get(linha).getContato());
				tfEmail.setText(listaCliente.get(linha).getEmail());
				tfFone1.setText(listaCliente.get(linha).getFone1());
				tfFone2.setText(listaCliente.get(linha).getFone2());
				epDescricaoLayout.setText(listaCliente.get(linha).getDetalhes());

				if (listaCliente.get(linha).isEntrega() == true) {

					entrega = "Sim";

				} else {

					entrega = "Não";

				}

				cbEntrega.setSelectedItem(entrega);
				cbUnidade.setSelectedItem(listaCliente.get(linha).getUnidade());

			}
		});
		tbCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbCliente.setFont(new Font("Segoe UI ", Font.PLAIN, 11));
		tbCliente.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Cliente", "Vendedor", "Contato",
				"E-mail", "Fone 1", "Fone 2", "Detalhes", "Entrega", "Unidade" }));
		tbCliente.getColumnModel().getColumn(0).setResizable(false);
		tbCliente.getColumnModel().getColumn(0).setPreferredWidth(180);
		tbCliente.getColumnModel().getColumn(1).setResizable(false);
		tbCliente.getColumnModel().getColumn(1).setPreferredWidth(140);
		tbCliente.getColumnModel().getColumn(2).setResizable(false);
		tbCliente.getColumnModel().getColumn(2).setPreferredWidth(140);
		tbCliente.getColumnModel().getColumn(3).setResizable(false);
		tbCliente.getColumnModel().getColumn(3).setPreferredWidth(180);
		tbCliente.getColumnModel().getColumn(4).setResizable(false);
		tbCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
		tbCliente.getColumnModel().getColumn(5).setResizable(false);
		tbCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
		tbCliente.getColumnModel().getColumn(6).setResizable(false);
		tbCliente.getColumnModel().getColumn(6).setPreferredWidth(250);

		spCliente.setViewportView(tbCliente);

		btnVoltarCliente = new JButton("Sair");
		btnVoltarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVoltarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnVoltarCliente.getText().equals("Cancelar")) {

					btnEditarCliente.setText("Editar");
					btnEditarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar.png")));
					btnVoltarCliente.setText("Voltar");
					btnVoltarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));
					btnNovoCliente.setEnabled(true);
					btnPesquisarCliente.setEnabled(true);
					btnExcluirCliente.setEnabled(true);

					tfNomeCliente.setText("");
					tfVendedor.setText("");
					tfContato.setText("");
					tfEmail.setText("");
					tfFone1.setText("");
					tfFone2.setText("");
					epDescricaoLayout.setText("");
					cbEntrega.setSelectedIndex(0);
					cbUnidade.setSelectedIndex(0);

					cbEntrega.setEnabled(false);
					cbUnidade.setEnabled(false);

					tfNomeCliente.setEditable(false);
					tfVendedor.setEditable(false);
					tfContato.setEditable(false);
					tfEmail.setEditable(false);
					tfFone1.setEditable(false);
					tfFone2.setEditable(false);
					epDescricaoLayout.setEditable(false);

					DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();

					model.setNumRows(0);

				} else {

					dispose();

				}

			}
		});
		btnVoltarCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnVoltarCliente.setBounds(487, 369, 113, 35);
		pnCadastroClientes.add(btnVoltarCliente);

		btnExcluirCliente = new JButton("Excluir");
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tbCliente.getRowCount() == 0) {

					JOptionPane.showMessageDialog(null, "Faça uma pesquisa primeiro!");

				} else {

					if (tfNomeCliente.getText().equals("") || epDescricaoLayout.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Selecione o cliente na planilha!");

					} else {

						int resposta = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir esse cliente?",
								"Excluir", JOptionPane.OK_CANCEL_OPTION);

						if (resposta == JOptionPane.OK_OPTION) {

							int editarLinha = tbCliente.getSelectedRow();
							int id_cliente = listaCliente.get(editarLinha).getId_cliente();

							ClienteRN clienteRN = new ClienteRN();
							clienteRN.excluirCliente(id_cliente);

							tfNomeCliente.setText("");
							tfVendedor.setText("");
							tfContato.setText("");
							tfEmail.setText("");
							tfFone1.setText("");
							tfFone2.setText("");
							epDescricaoLayout.setText("");
							cbEntrega.setSelectedIndex(0);
							cbUnidade.setSelectedIndex(0);

							cbEntrega.setEnabled(false);
							cbUnidade.setEnabled(false);

							tfNomeCliente.setEditable(false);
							tfVendedor.setEditable(false);
							tfContato.setEditable(false);
							tfEmail.setEditable(false);
							tfFone1.setEditable(false);
							tfFone2.setEditable(false);
							epDescricaoLayout.setEditable(false);

							DefaultTableModel model = (DefaultTableModel) tbCliente.getModel();

							model.setNumRows(0);

						}

					}

				}
			}
		});
		btnExcluirCliente.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir.png")));
		btnExcluirCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnExcluirCliente.setBounds(238, 369, 113, 35);
		pnCadastroClientes.add(btnExcluirCliente);

		JPanel pnCadastroOperadores = new JPanel();
		pnCadastroOperadores.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {

				cbNome.setSelectedIndex(0);
				cbNivelAcesso.setSelectedIndex(0);
				cbSetor.setSelectedIndex(0);
				tfLogin.setText("");
				pfSenha.setText("");
				pfConfirmarSenha.setText("");
				tfNome.setText("");

			}
		});
		tabpaneConfiguaracoes.addTab("Cadastro de Operadores", new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/adicionar_usuario_pq.png")), pnCadastroOperadores, null);
		pnCadastroOperadores.setLayout(null);

		JPanel panel = new JPanel();
		panel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Operadores", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(21, 11, 565, 355);
		pnCadastroOperadores.add(panel);
		panel.setLayout(null);

		cbNome = new JComboBox<String>();
		cbNome.setEditable(true);

		cbNome.addItem("");

		for (Operador op : listaOperador) {

			cbNome.addItem(op.getNome());

		}

		cbNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				listaOperador = operadorRN.listaOperador();

				posicao = cbNome.getSelectedIndex();

				if (posicao <= 0) {

					posicao = 0;

					cbNivelAcesso.setSelectedItem("");
					tfLogin.setText("");
					pfSenha.setText("");
					pfConfirmarSenha.setText("");
					cbSetor.setSelectedItem("");

				} else {

					cbNivelAcesso.setSelectedItem(listaOperador.get(posicao - 1).getAcesso());
					cbSetor.setSelectedItem(listaOperador.get(posicao - 1).getSetor());
					tfLogin.setText(listaOperador.get(posicao - 1).getLogin());
					pfSenha.setText(listaOperador.get(posicao - 1).getSenha());
					pfConfirmarSenha.setText(listaOperador.get(posicao - 1).getSenha());

				}

			}
		});

		cbNome.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbNome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbNome.setBounds(28, 152, 164, 25);
		panel.add(cbNome);

		new PesquisarComboBox(cbNome);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(26, 127, 46, 25);
		panel.add(lblNome);
		lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		cbNivelAcesso = new JComboBox<String>();
		cbNivelAcesso.setEditable(true);

		cbNivelAcesso.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbNivelAcesso.setBounds(210, 152, 139, 25);
		panel.add(cbNivelAcesso);
		cbNivelAcesso.setModel(
				new DefaultComboBoxModel<String>(new String[] {"", "ADMINISTRADOR", "OPERADOR", "COMERCIAL"}));
		cbNivelAcesso.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		cbNivelAcesso.setSelectedIndex(cbNome.getSelectedIndex());

		new PesquisarComboBox(cbNivelAcesso);

		JLabel lblLogin_1 = new JLabel("Login");
		lblLogin_1.setBounds(47, 188, 47, 25);
		panel.add(lblLogin_1);
		lblLogin_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		tfLogin = new JTextField();
		tfLogin.setText("");
		tfLogin.setEditable(false);
		tfLogin.setBounds(47, 214, 164, 25);
		panel.add(tfLogin);
		tfLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tfLogin.setColumns(10);
		tfLogin.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
		lblConfirmarSenha.setBounds(410, 194, 102, 14);
		panel.add(lblConfirmarSenha);
		lblConfirmarSenha.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		pfSenha = new JPasswordField();
		pfSenha.setText("");

		pfSenha.setEditable(false);
		pfSenha.setBounds(258, 214, 105, 25);
		panel.add(pfSenha);
		pfSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pfSenha.setBorder(new LineBorder(Color.LIGHT_GRAY));

		pfConfirmarSenha = new JPasswordField();
		pfConfirmarSenha.setText("");

		pfConfirmarSenha.setEditable(false);
		pfConfirmarSenha.setBounds(410, 214, 105, 25);
		panel.add(pfConfirmarSenha);
		pfConfirmarSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pfConfirmarSenha.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JLabel lblAcesso = new JLabel("N\u00EDvel Acesso");
		lblAcesso.setBounds(211, 127, 84, 25);
		panel.add(lblAcesso);
		lblAcesso.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		btnNovoOperador = new JButton("");
		btnNovoOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnNovoOperador.setEnabled(false);
				btnEditarOperador.setEnabled(false);
				btnExcluirOperador.setEnabled(false);
				btnSalvarOperador.setVisible(true);
				btnCancelarOperador.setText("Cancelar");
				btnCancelarOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));

				cbNome.setVisible(false);
				tfNome.setVisible(true);
				tfNome.setEnabled(true);
				tfNome.setText("");
				cbNivelAcesso.setSelectedIndex(0);
				tfLogin.setEditable(true);
				tfLogin.setText("");
				pfSenha.setEditable(true);
				pfSenha.setText("");
				pfConfirmarSenha.setEditable(true);
				pfConfirmarSenha.setText("");
				cbSetor.setSelectedItem(null);

			}
		});
		btnNovoOperador.setBounds(80, 40, 75, 68);
		panel.add(btnNovoOperador);
		btnNovoOperador
				.setRolloverIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/adicionar_usuario_2.png")));
		btnNovoOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/adicionar_usuario.png")));
		btnNovoOperador.setToolTipText("Novo Operador");
		btnNovoOperador.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNovoOperador.setFocusable(false);
		btnNovoOperador.setContentAreaFilled(false);
		btnNovoOperador.setBorderPainted(false);

		btnEditarOperador = new JButton("");
		btnEditarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (cbNome.getSelectedItem().toString().equals("")) {

					JOptionPane.showMessageDialog(null, "Selecione um operador para Editar!");

				} else {

					cbNome.setEnabled(false);
					cbNome.setVisible(false);
					tfNome.setEnabled(true);
					tfNome.setVisible(true);
					tfNome.setEditable(true);
					tfNome.setText(cbNome.getSelectedItem().toString());
					pfSenha.setEditable(true);
					pfSenha.setEnabled(true);
					pfConfirmarSenha.setEditable(true);
					tfLogin.setEditable(true);

					btnSalvarOperador.setVisible(true);
					btnCancelarOperador.setText("Cancelar");
					btnCancelarOperador
							.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));

					btnNovoOperador.setEnabled(false);
					btnEditarOperador.setEnabled(false);
					btnExcluirOperador.setEnabled(false);

				}

				btnSalvarOperador.setText("Aplicar");

			}
		});
		btnEditarOperador.setBounds(235, 40, 84, 68);
		panel.add(btnEditarOperador);
		btnEditarOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar_usuario.png")));
		btnEditarOperador
				.setRolloverIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar_usuario_2.png")));
		btnEditarOperador.setToolTipText("Editar Operador");
		btnEditarOperador.setFocusable(false);
		btnEditarOperador.setContentAreaFilled(false);
		btnEditarOperador.setBorderPainted(false);

		btnExcluirOperador = new JButton("");

		if (cbNome.getSelectedIndex() <= 0) {

			btnEditarOperador.setVisible(true);

		} else {

			btnEditarOperador.setVisible(false);

		}

		btnExcluirOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (cbNome.getSelectedItem().toString().equals("")) {

					JOptionPane.showMessageDialog(null, "Selecione um operador para Excluir!");

				} else {

					int resposta = 0;

					resposta = JOptionPane.showConfirmDialog(null, "Você deseja excluir o operador?", "Excluir",
							JOptionPane.YES_NO_OPTION);

					if (resposta == 0) {

						operadorRN = new OperadorRN();
						Operador operador = new Operador();

						operador = operadorRN.buscarOperador(cbNome.getSelectedItem().toString());

						operadorRN.excluirOperador(operador);

						listaOperador.clear();
						cbNome.removeAllItems();
						cbNome.setSelectedItem(null);
						cbSetor.setSelectedItem(null);

						listaOperador = operadorRN.listaOperador();

						cbNome.addItem("");

						for (Operador op : listaOperador) {

							cbNome.addItem(op.getNome());

						}

					}
				}

			}
		});
		btnExcluirOperador.setBounds(399, 40, 84, 68);
		panel.add(btnExcluirOperador);
		btnExcluirOperador
				.setRolloverIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir_usuario_2.png")));
		btnExcluirOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir_usuario.png")));
		btnExcluirOperador.setToolTipText("Excluir Operador");
		btnExcluirOperador.setFocusable(false);
		btnExcluirOperador.setContentAreaFilled(false);
		btnExcluirOperador.setBorderPainted(false);

		tfNome = new JTextField();
		tfNome.setBackground(Color.WHITE);
		tfNome.setVisible(false);
		tfNome.setBounds(28, 152, 164, 25);
		panel.add(tfNome);
		tfNome.setFont(new Font("Segoe UI ", Font.BOLD, 12));
		tfNome.setColumns(10);
		tfNome.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JLabel lblSenhaN = new JLabel("Senha");
		lblSenhaN.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblSenhaN.setBounds(258, 194, 69, 14);
		panel.add(lblSenhaN);

		btnSalvarOperador = new JButton("Salvar");
		btnSalvarOperador.setVisible(false);
		btnSalvarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (btnSalvarOperador.getText().equals("Aplicar")) {

					int resposta = 0;

					resposta = JOptionPane.showConfirmDialog(null, "Você deseja editar o operador?", "Editar",
							JOptionPane.YES_NO_OPTION);

					if (resposta == 0) {

						if (tfNome.getText().equals("")) {

							JOptionPane.showMessageDialog(null, "Digite o nome do operador!");
							tfNome.requestFocus();

						} else if (cbNivelAcesso.getSelectedItem().equals("Selecione...")) {

							JOptionPane.showMessageDialog(null, "Selecione o nível de acesso!");
							cbNivelAcesso.requestFocus();

						} else if (cbNivelAcesso.getSelectedItem().equals("Selecione...")) {

							JOptionPane.showMessageDialog(null, "Selecione o setor!");
							cbSetor.requestFocus();

						} else if (tfLogin.getText().equals("")) {

							JOptionPane.showMessageDialog(null, "Digite o login do operador!");
							tfLogin.requestFocus();

						} else if (String.valueOf(pfSenha.getPassword()).equals("")) {

							JOptionPane.showMessageDialog(null, "Digite a senha!");
							pfSenha.requestFocus();

						} else if (String.valueOf(pfConfirmarSenha.getPassword()).equals("")) {

							JOptionPane.showMessageDialog(null, "Digite a confirmação da senha!");
							pfConfirmarSenha.requestFocus();

						} else if (!String.valueOf(pfConfirmarSenha.getPassword())
								.equals(String.valueOf(pfSenha.getPassword()))) {

							JOptionPane.showMessageDialog(null, "A confirmação não está igual a senha!");
							pfConfirmarSenha.requestFocus();

						} else {

							operadorRN = new OperadorRN();
							Operador operador = new Operador();

							operador = operadorRN.buscarOperador(cbNome.getSelectedItem().toString());

							operador.setNome(tfNome.getText().toUpperCase());
							operador.setAcesso(String.valueOf(cbNivelAcesso.getSelectedItem()).toUpperCase());
							operador.setSetor(String.valueOf(cbSetor.getSelectedItem()).toUpperCase());
							operador.setLogin(tfLogin.getText());
							operador.setSenha(String.valueOf(pfSenha.getPassword()));

							operadorRN.editarOperador(operador);

							cbSetor.setSelectedItem("");
							cbNome.setSelectedItem("");
							cbNome.setVisible(true);
							cbNome.setEnabled(true);
							tfNome.setText("");
							tfNome.setVisible(false);

							tfLogin.setText("");
							tfLogin.setEditable(false);
							pfSenha.setText("");
							pfConfirmarSenha.setText("");

							btnSalvarOperador.setVisible(false);
							btnSalvarOperador.setText("Salvar");
							btnCancelarOperador.setText("Voltar");
							btnCancelarOperador
									.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

							btnNovoOperador.setEnabled(true);
							btnEditarOperador.setEnabled(true);
							btnExcluirOperador.setEnabled(true);

							pfSenha.setEditable(false);
							pfConfirmarSenha.setEditable(false);

							listaOperador.clear();
							cbNome.removeAllItems();
							cbNome.setSelectedItem(null);

							listaOperador = operadorRN.listaOperador();

							cbNome.addItem("");

							for (Operador op : listaOperador) {

								cbNome.addItem(op.getNome());

							}

						}

					}

				} else {

					if (tfNome.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o nome do operador!");
						tfNome.requestFocus();

					} else if (cbNivelAcesso.getSelectedItem().equals("Selecione...")) {

						JOptionPane.showMessageDialog(null, "Selecione o nível de acesso!");
						cbNivelAcesso.requestFocus();

					} else if (tfLogin.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o login do operador!");
						tfLogin.requestFocus();

					} else if (String.valueOf(pfSenha.getPassword()).equals("")) {

						JOptionPane.showMessageDialog(null, "Digite a senha!");
						pfSenha.requestFocus();

					} else if (String.valueOf(pfConfirmarSenha.getPassword()).equals("")) {

						JOptionPane.showMessageDialog(null, "Digite a confirmação da senha!");
						pfConfirmarSenha.requestFocus();

					} else if (!String.valueOf(pfConfirmarSenha.getPassword())
							.equals(String.valueOf(pfSenha.getPassword()))) {

						JOptionPane.showMessageDialog(null, "A confirmação não está igual a senha!");
						pfConfirmarSenha.requestFocus();

					} else {

						operadorRN = new OperadorRN();
						Operador operador = new Operador();

						operador.setNome(tfNome.getText().toUpperCase());
						operador.setAcesso(String.valueOf(cbNivelAcesso.getSelectedItem()).toUpperCase());
						operador.setSetor(String.valueOf(cbSetor.getSelectedItem()).toUpperCase());
						operador.setLogin(tfLogin.getText().toUpperCase());
						operador.setSenha(String.valueOf(pfSenha.getPassword()));

						operadorRN.adicionarOperador(operador);

						cbSetor.setSelectedIndex(0);
						cbNome.setVisible(true);
						cbNome.setSelectedIndex(0);
						tfNome.setVisible(false);
						tfNome.setText("");
						cbNivelAcesso.setSelectedIndex(0);
						tfLogin.setEditable(false);
						tfLogin.setText("");
						pfSenha.setEditable(false);
						pfSenha.setText("");
						pfConfirmarSenha.setEditable(false);
						pfConfirmarSenha.setText("");

						cbSetor.setSelectedItem("");
						btnNovoOperador.setEnabled(true);
						btnEditarOperador.setEnabled(true);
						btnExcluirOperador.setEnabled(true);
						btnSalvarOperador.setVisible(false);
						btnCancelarOperador.setText("Voltar");
						btnCancelarOperador
								.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

						listaOperador.clear();
						cbNome.removeAllItems();
						cbNome.setSelectedItem(null);

						listaOperador = operadorRN.listaOperador();

						cbNome.addItem("");

						for (Operador op : listaOperador) {

							cbNome.addItem(op.getNome());

						}

					}

				}

			}
		});
		btnSalvarOperador.setFocusPainted(false);
		btnSalvarOperador.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvarOperador.setBorder(null);
		btnSalvarOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/salvar.png")));
		btnSalvarOperador.setBounds(121, 285, 100, 35);
		panel.add(btnSalvarOperador);

		btnCancelarOperador = new JButton("Sair");
		btnCancelarOperador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnCancelarOperador.getText().equals("Cancelar")) {

					btnNovoOperador.setEnabled(true);
					btnEditarOperador.setEnabled(true);
					btnExcluirOperador.setEnabled(true);
					btnSalvarOperador.setVisible(false);
					btnSalvarOperador.setText("Salvar");
					btnCancelarOperador.setText("Voltar");
					btnCancelarOperador
							.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

					tfLogin.setEditable(false);
					pfSenha.setEditable(false);
					pfConfirmarSenha.setEditable(false);

					cbSetor.setSelectedItem("");
					cbNome.setSelectedItem("");
					cbNome.setVisible(true);
					cbNome.setEnabled(true);
					tfNome.setText("");
					tfNome.setVisible(false);

					tfLogin.setText("");
					tfLogin.setEditable(false);
					pfSenha.setText("");
					pfConfirmarSenha.setText("");

					btnSalvarOperador.setVisible(false);
					btnSalvarOperador.setText("Salvar");
					btnCancelarOperador.setText("Voltar");
					btnCancelarOperador
							.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

					btnNovoOperador.setEnabled(true);
					btnEditarOperador.setEnabled(true);
					btnExcluirOperador.setEnabled(true);

					pfSenha.setEditable(false);
					pfConfirmarSenha.setEditable(false);

					if (posicao <= 0) {

						posicao = 0;

						cbNivelAcesso.setSelectedItem("");
						tfLogin.setText("");
						pfSenha.setText("");
						pfConfirmarSenha.setText("");

					} else {

						tfNome.setVisible(false);
						tfNome.setText("");
						cbNome.setSelectedItem(listaOperador.get(posicao - 1).getNome());
						cbSetor.setSelectedItem(listaOperador.get(posicao - 1).getAcesso());
						cbNivelAcesso.setSelectedItem(listaOperador.get(posicao - 1).getAcesso());
						tfLogin.setEditable(false);
						tfLogin.setText(listaOperador.get(posicao - 1).getLogin());
						pfSenha.setEditable(false);
						pfSenha.setText(listaOperador.get(posicao - 1).getSenha());
						pfConfirmarSenha.setEditable(false);
						pfConfirmarSenha.setText(listaOperador.get(posicao - 1).getSenha());

					}

					cbNome.setEnabled(true);
					cbNome.setVisible(true);

				} else {

					dispose();

				}

			}
		});
		btnCancelarOperador.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnCancelarOperador.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelarOperador.setFocusPainted(false);
		btnCancelarOperador.setBorder(null);
		btnCancelarOperador.setBounds(342, 285, 100, 35);
		panel.add(btnCancelarOperador);

		cbSetor = new JComboBox<String>();
		cbSetor.setModel(new DefaultComboBoxModel<String>(new String[] {"", "PRODU\u00C7\u00C3O", "COMERCIAL", "ADMINISTRATIVO"}));
		cbSetor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbSetor.setEditable(true);
		cbSetor.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbSetor.setBounds(364, 152, 171, 25);
		panel.add(cbSetor);

		new PesquisarComboBox(cbSetor);

		JLabel lblSetor = new JLabel("Setor");
		lblSetor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblSetor.setBounds(364, 127, 46, 25);
		panel.add(lblSetor);

		JPanel pnCadastroProdutos = new JPanel();

		pnCadastroProdutos.setBorder(null);
		tabpaneConfiguaracoes.addTab("Cadastro de Produtos", new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/produto_pq.png")), pnCadastroProdutos, null);
		pnCadastroProdutos.setLayout(null);

		JPanel pCadastroProdutos = new JPanel();
		pCadastroProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pCadastroProdutos.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Produtos",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pCadastroProdutos.setBounds(21, 11, 567, 355);
		pnCadastroProdutos.add(pCadastroProdutos);
		pCadastroProdutos.setLayout(null);

		JLabel lblNomeProdutos = new JLabel("Nome");
		lblNomeProdutos.setBounds(108, 27, 89, 14);
		pCadastroProdutos.add(lblNomeProdutos);
		lblNomeProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		cbNomeProduto = new JComboBox<String>();
		cbNomeProduto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbNomeProduto.setEditable(true);
		cbNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbNomeProduto.setBounds(108, 52, 351, 27);

		new PesquisarComboBox(cbNomeProduto);

		pCadastroProdutos.add(cbNomeProduto);

		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(108, 105, 86, 14);
		pCadastroProdutos.add(lblDescricao);
		lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		tfDescricaoProduto = new JTextField();
		tfDescricaoProduto.setBounds(108, 130, 351, 25);
		pCadastroProdutos.add(tfDescricaoProduto);
		tfDescricaoProduto.setEditable(false);
		tfDescricaoProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfDescricaoProduto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfDescricaoProduto.setColumns(10);

		tfNomeProduto = new JTextField();
		tfNomeProduto.setBounds(108, 52, 351, 25);
		pCadastroProdutos.add(tfNomeProduto);
		tfNomeProduto.setVisible(false);
		tfNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfNomeProduto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfNomeProduto.setColumns(10);

		btnAdicionarProduto = new JButton("Novo");
		btnAdicionarProduto.setBounds(105, 176, 126, 35);
		pCadastroProdutos.add(btnAdicionarProduto);
		btnAdicionarProduto.setBorder(null);
		btnAdicionarProduto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAdicionarProduto.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/48px-add.png")));

		btnEditarProduto = new JButton("      Editar");
		btnEditarProduto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEditarProduto.setBounds(336, 176, 126, 35);
		pCadastroProdutos.add(btnEditarProduto);
		btnEditarProduto.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/48px-edit.png")));
		btnEditarProduto.setBorder(null);

		btnExcluirProduto = new JButton("    Excluir");
		btnExcluirProduto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnExcluirProduto.setBounds(105, 233, 126, 35);
		pCadastroProdutos.add(btnExcluirProduto);
		btnExcluirProduto.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/48px-excluir.png")));

		btnSalvarProdutos = new JButton("    Salvar");
		btnSalvarProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvarProdutos.setBounds(336, 233, 126, 35);
		pCadastroProdutos.add(btnSalvarProdutos);
		btnSalvarProdutos.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/salvar.png")));
		btnSalvarProdutos.setEnabled(false);

		btnCancelarProdutos = new JButton("Sair");
		btnCancelarProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelarProdutos.setBounds(220, 294, 126, 35);
		pCadastroProdutos.add(btnCancelarProdutos);
		btnCancelarProdutos.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnCancelarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnCancelarProdutos.getText().equals("Cancelar")) {

					btnAdicionarProduto.setEnabled(true);
					btnEditarProduto.setEnabled(true);
					btnExcluirProduto.setEnabled(true);

					tfNomeProduto.setText("");
					tfNomeProduto.setVisible(false);

					btnSalvarProdutos.setText("Salvar");
					btnSalvarProdutos.setEnabled(false);

					cbNomeProduto.setVisible(true);

					btnCancelarProdutos.setText("Voltar");
					btnCancelarProdutos
							.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));

					tfDescricaoProduto.setText(listaProduto.get(posicaoProduto).getDescricao());
					tfDescricaoProduto.setEditable(false);

				} else {

					dispose();
				}

			}
		});
		btnSalvarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (btnSalvarProdutos.getText().equals("Editado")) {

					if (tfNomeProduto.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o nome do produto");

					} else if (tfDescricaoProduto.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite a descrição do produto");

					} else {

						ProdutoRN produtoRN = new ProdutoRN();
						Produto produto = new Produto();

						produto.setId_produto(listaProduto.get(posicaoProduto).getId_produto());
						produto.setNome(tfNomeProduto.getText().toUpperCase());
						produto.setDescricao(tfDescricaoProduto.getText().toUpperCase());
						produtoRN.editarProduto(produto);

						tfNomeProduto.setVisible(false);
						tfNomeProduto.setText("");
						cbNomeProduto.setVisible(true);
						tfDescricaoProduto.setEditable(false);
						tfDescricaoProduto.setText("");

						btnSalvarProdutos.setText("Salvar");
						btnSalvarProdutos.setEnabled(false);
						btnAdicionarProduto.setEnabled(true);
						btnCancelarProdutos.setText("Voltar");
						btnCancelarProdutos
								.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));
						btnEditarProduto.setEnabled(true);
						btnExcluirProduto.setEnabled(true);

						listaProduto.clear();
						cbNomeProduto.removeAllItems();
						cbNomeProduto.setSelectedItem(null);

						listaProduto = produtoRN.listarProduto();

						for (Produto pr : listaProduto) {

							cbNomeProduto.addItem(pr.getNome());

						}

					}

				} else {

					if (tfNomeProduto.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o nome do produto");

					} else if (tfDescricaoProduto.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite a descrição do produto");

					} else {

						ProdutoRN produtoRN = new ProdutoRN();
						Produto produto = new Produto();
						produto.setNome(tfNomeProduto.getText().toUpperCase());
						produto.setDescricao(tfDescricaoProduto.getText().toUpperCase());
						produtoRN.adicionarProduto(produto);

						tfNomeProduto.setVisible(false);
						tfNomeProduto.setText("");
						cbNomeProduto.setVisible(true);
						tfDescricaoProduto.setEditable(false);
						tfDescricaoProduto.setText("");
						btnSalvarProdutos.setEnabled(false);

						btnAdicionarProduto.setEnabled(true);
						btnEditarProduto.setEnabled(true);
						btnExcluirProduto.setEnabled(true);
						btnCancelarProdutos.setText("Voltar");
						btnCancelarProdutos
								.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/voltar.png")));
						btnCancelarProdutos.setEnabled(true);

						listaProduto.clear();
						cbNomeProduto.removeAllItems();
						cbNomeProduto.setSelectedItem(null);

						listaProduto = produtoRN.listarProduto();

						for (Produto pr : listaProduto) {

							cbNomeProduto.addItem(pr.getNome());

						}

					}

				}

			}
		});
		btnExcluirProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int resposta = 0;

				resposta = JOptionPane.showConfirmDialog(null, "Você deseja excluir o produto?", "Excluir",
						JOptionPane.YES_NO_OPTION);

				if (resposta == 0) {

					ProdutoRN produtoRN = new ProdutoRN();
					Produto produto = new Produto();
					produto.setId_produto(listaProduto.get(posicaoProduto).getId_produto());
					produto.setNome(tfNomeProduto.getText().toUpperCase());
					produto.setDescricao(tfDescricaoProduto.getText().toUpperCase());
					produtoRN.excluirProduto(produto);

					listaProduto.clear();
					cbNomeProduto.removeAllItems();
					cbNomeProduto.setSelectedItem(null);

					listaProduto = produtoRN.listarProduto();

					for (Produto pr : listaProduto) {

						cbNomeProduto.addItem(pr.getNome());

					}

				}

			}
		});
		btnEditarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnCancelarProdutos.setText("Cancelar");
				btnCancelarProdutos.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
				btnSalvarProdutos.setText("Editado");
				btnSalvarProdutos.setEnabled(true);

				btnAdicionarProduto.setEnabled(false);
				btnEditarProduto.setEnabled(false);
				btnExcluirProduto.setEnabled(false);

				cbNomeProduto.setVisible(false);

				tfNomeProduto.setVisible(true);
				tfNomeProduto.setText(listaProduto.get(posicaoProduto).getNome());

				tfDescricaoProduto.setEditable(true);
				tfDescricaoProduto.setText(listaProduto.get(posicaoProduto).getDescricao());

			}
		});
		btnAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnSalvarProdutos.setEnabled(true);
				btnCancelarProdutos.setText("Cancelar");
				btnCancelarProdutos.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));

				btnAdicionarProduto.setEnabled(false);
				btnEditarProduto.setEnabled(false);
				btnExcluirProduto.setEnabled(false);

				cbNomeProduto.setVisible(false);

				tfNomeProduto.setVisible(true);
				tfNomeProduto.setText("");

				tfDescricaoProduto.setEditable(true);
				tfDescricaoProduto.setText("");

			}
		});
		cbNomeProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				posicaoProduto = cbNomeProduto.getSelectedIndex();

				if (posicaoProduto == -1) {

					posicaoProduto = 0;

				} else {

					posicaoProduto = cbNomeProduto.getSelectedIndex();

					tfDescricaoProduto.setText(listaProduto.get(posicaoProduto).getDescricao());

				}

			}
		});
		
		
		pServidorDeEmail = new JPanel();
		tabpaneConfiguaracoes.addTab("E-mail", new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/email.png")), pServidorDeEmail, null);
		pServidorDeEmail.setLayout(null);

		JPanel pCadastroEmail = new JPanel();
		pCadastroEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pCadastroEmail.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Cadastro de e-mail",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pCadastroEmail.setBounds(10, 0, 589, 245);
		pServidorDeEmail.add(pCadastroEmail);
		pCadastroEmail.setLayout(null);

		tfEmailDesc = new JTextField();
		tfEmailDesc.setText("E-mail Teste");
		tfEmailDesc.setEditable(false);
		tfEmailDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfEmailDesc.setBounds(315, 35, 256, 25);
		pCadastroEmail.add(tfEmailDesc);
		tfEmailDesc.setColumns(10);

		tfEmailNome = new JTextField();
		tfEmailNome.setText("teste@nesolution.com.br");
		tfEmailNome.setEditable(false);
		tfEmailNome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfEmailNome.setColumns(10);
		tfEmailNome.setBounds(18, 35, 279, 25);
		pCadastroEmail.add(tfEmailNome);

		tfEndImg = new JTextField();
		tfEndImg.setText("c:\\teste\\teste.jpg");
		tfEndImg.setEditable(false);
		tfEndImg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfEndImg.setColumns(10);
		tfEndImg.setBounds(18, 80, 279, 25);
		pCadastroEmail.add(tfEndImg);

		btnAbrir = new JButton("Abrir");
		btnAbrir.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAbrir.setEnabled(false);
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser escolherArquivo = new JFileChooser();
				FileNameExtensionFilter filtroExtensao = new FileNameExtensionFilter(".jpg", "jpg");
				escolherArquivo.addChoosableFileFilter(filtroExtensao);

				int retorno = escolherArquivo.showDialog(null, "Selecionar Imagem");

				if (retorno == JFileChooser.APPROVE_OPTION) {

					File arquivo = null;
					arquivo = escolherArquivo.getSelectedFile();
					escolherArquivo.setSelectedFile(arquivo);

					JOptionPane.showMessageDialog(null, arquivo);
					tfEndImg.setText(arquivo.getPath());

				}

				String imagem = String.valueOf(tfEndImg.getText());

				ImageIcon iconFrente = new ImageIcon(imagem);

				int numero = iconFrente.getImageLoadStatus();

				if (numero == 4) {

					lblImagemNãoEncontrada.setVisible(true);

				} else {

					lblImagem.setIcon(new ImageIcon(iconFrente.getImage().getScaledInstance(lblImagem.getWidth(),
							lblImagem.getHeight(), Image.SCALE_DEFAULT)));
				}

				lblImagem.setIcon(new ImageIcon(iconFrente.getImage().getScaledInstance(lblImagem.getWidth(),
						lblImagem.getHeight(), Image.SCALE_DEFAULT)));

			}
		});
		btnAbrir.setBounds(315, 79, 87, 27);
		pCadastroEmail.add(btnAbrir);

		JLabel lblEmailCad = new JLabel("E-mail");
		lblEmailCad.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEmailCad.setBounds(18, 18, 263, 14);
		pCadastroEmail.add(lblEmailCad);

		JLabel EmailCad = new JLabel("Descri\u00E7\u00E3o");
		EmailCad.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		EmailCad.setBounds(315, 18, 256, 14);
		pCadastroEmail.add(EmailCad);

		JLabel lblEndDaImagem = new JLabel("Endere\u00E7o da Imagem");
		lblEndDaImagem.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEndDaImagem.setBounds(18, 65, 263, 14);
		pCadastroEmail.add(lblEndDaImagem);

		btnSalvarEmail = new JButton("Salvar");
		btnSalvarEmail.setVisible(false);
		btnSalvarEmail.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/salvar.png")));
		btnSalvarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EmailRN emailRN = new EmailRN();

				if (tfEmailDesc.getText().toString().equals("") || tfEmailNome.getText().equals("")
						|| tfEndImg.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite as informações que estão faltando");

				} else {

					int resposta = JOptionPane.showConfirmDialog(null, "Deseja salvar o e-mail?", "Salvar Email!",
							JOptionPane.YES_NO_OPTION);

					if (resposta == JOptionPane.YES_OPTION) {

						email.setId_email(1);
						email.setEmail(tfEmailNome.getText().toLowerCase());
						email.setDescricao(tfEmailDesc.getText().toUpperCase());
						email.setEndereco_imagem(tfEndImg.getText().toUpperCase());

						emailRN.editarEmail(email);

						email = new Email();

						email = emailRN.buscarEmail();

						tfEmailNome.setText(email.getEmail().toLowerCase());
						tfEmailDesc.setText(email.getDescricao().toUpperCase());
						tfEndImg.setText(email.getEndereco_imagem());

						btnEditarEmail.setVisible(true);
						btnMostrarEmailSalvo.setEnabled(false);

						cancelar();

					} else {

						tfEmailNome.setEditable(false);
						tfEmailDesc.setEditable(false);
						tfEndImg.setEditable(false);
						btnAbrir.setEnabled(false);
						btnSalvarEmail.setVisible(false);
						btnCancelarEmail.setVisible(false);
						btnEditarEmail.setVisible(false);
						btnMostrarEmailSalvo.setEnabled(true);
					}

				}

				tfEmailNome.setEditable(false);
				tfEmailDesc.setEditable(false);
				tfEndImg.setEditable(false);
				btnAbrir.setEnabled(false);
				btnSalvarEmail.setVisible(false);
				btnCancelarEmail.setVisible(false);
				btnEditarEmail.setVisible(false);
				btnMostrarEmailSalvo.setEnabled(true);

			}
		});
		btnSalvarEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvarEmail.setFocusPainted(false);
		btnSalvarEmail.setBorder(null);
		btnSalvarEmail.setBounds(28, 195, 100, 35);
		pCadastroEmail.add(btnSalvarEmail);

		btnEditarEmail = new JButton("Editar");
		btnEditarEmail.setVisible(false);
		btnEditarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfEmailNome.setEditable(true);
				tfEmailDesc.setEditable(true);
				tfEndImg.setEditable(true);
				btnAbrir.setEnabled(true);

				btnSalvarEmail.setVisible(true);
				btnCancelarEmail.setVisible(true);

			}
		});
		btnEditarEmail.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar.png")));
		btnEditarEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEditarEmail.setFocusPainted(false);
		btnEditarEmail.setBorder(null);
		btnEditarEmail.setBounds(315, 195, 100, 35);
		pCadastroEmail.add(btnEditarEmail);

		JButton btnVoltarEmail = new JButton("Sair");
		btnVoltarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnVoltarEmail.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnVoltarEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnVoltarEmail.setFocusPainted(false);
		btnVoltarEmail.setBorder(null);
		btnVoltarEmail.setBounds(449, 195, 100, 35);
		pCadastroEmail.add(btnVoltarEmail);

		lblImagem = new JLabel("");
		lblImagem.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblImagem.setBounds(124, 111, 340, 73);
		pCadastroEmail.add(lblImagem);

		btnMostrarEmailSalvo = new JButton("Mostrar e-mail salvo");
		btnMostrarEmailSalvo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				lblImagemNãoEncontrada.setVisible(false);

				email = new Email();
				EmailRN emailRN = new EmailRN();

				email = emailRN.buscarEmail();

				tfEmailNome.setText(email.getEmail().toLowerCase());
				tfEmailDesc.setText(email.getDescricao().toUpperCase());
				tfEndImg.setText(email.getEndereco_imagem());

				String imagem = String.valueOf(email.getEndereco_imagem());

				ImageIcon iconFrente = new ImageIcon(imagem);

				int numero = iconFrente.getImageLoadStatus();

				if (numero == 4) {

					lblImagemNãoEncontrada.setVisible(true);

				} else {

					lblImagem.setIcon(new ImageIcon(iconFrente.getImage().getScaledInstance(lblImagem.getWidth(),
							lblImagem.getHeight(), Image.SCALE_DEFAULT)));
				}

				btnEditarEmail.setVisible(true);
				btnMostrarEmailSalvo.setEnabled(false);

			}
		});
		btnMostrarEmailSalvo.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/login.png")));
		btnMostrarEmailSalvo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnMostrarEmailSalvo.setFocusPainted(false);
		btnMostrarEmailSalvo.setBorder(null);
		btnMostrarEmailSalvo.setBounds(412, 74, 159, 33);
		pCadastroEmail.add(btnMostrarEmailSalvo);

		btnCancelarEmail = new JButton("Cancelar");
		btnCancelarEmail.setVisible(false);
		btnCancelarEmail.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir.png")));
		btnCancelarEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				cancelar();

			}
		});
		btnCancelarEmail.setBounds(162, 195, 119, 35);
		pCadastroEmail.add(btnCancelarEmail);

		lblImagemNãoEncontrada = new JLabel("Imagem n\u00E3o encontrada!");
		lblImagemNãoEncontrada.setVisible(false);
		lblImagemNãoEncontrada.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagemNãoEncontrada.setForeground(Color.RED);
		lblImagemNãoEncontrada.setFont(new Font("Segoe UI ", Font.BOLD, 16));
		lblImagemNãoEncontrada.setBounds(18, 125, 553, 42);
		pCadastroEmail.add(lblImagemNãoEncontrada);

		JPanel pServidorEmail = new JPanel();
		pServidorEmail.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Servidor de e-mail",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pServidorEmail.setBounds(10, 245, 589, 159);
		pServidorDeEmail.add(pServidorEmail);
		pServidorEmail.setLayout(null);

		JLabel lblServidorEmail = new JLabel("Nome do servidor de e-mail");
		lblServidorEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblServidorEmail.setBounds(35, 14, 237, 14);
		pServidorEmail.add(lblServidorEmail);

		tfServidorEmail = new JTextField();
		tfServidorEmail.setText("TESTE HOST");
		tfServidorEmail.setEditable(false);
		tfServidorEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfServidorEmail.setColumns(10);
		tfServidorEmail.setBounds(35, 31, 247, 25);
		pServidorEmail.add(tfServidorEmail);

		JLabel lblHost = new JLabel("HOST");
		lblHost.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblHost.setBounds(309, 14, 206, 14);
		pServidorEmail.add(lblHost);

		tfHost = new JTextField();
		tfHost.setText("smtp.teste.com");
		tfHost.setEditable(false);
		tfHost.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfHost.setColumns(10);
		tfHost.setBounds(309, 31, 247, 25);
		pServidorEmail.add(tfHost);

		JLabel lblSmtp = new JLabel("Porta SMTP");
		lblSmtp.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblSmtp.setBounds(35, 62, 133, 14);
		pServidorEmail.add(lblSmtp);

		tfSmtp = new JTextField();
		tfSmtp.setText("000");
		tfSmtp.setEditable(false);
		tfSmtp.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfSmtp.setColumns(10);
		tfSmtp.setBounds(35, 79, 247, 25);
		pServidorEmail.add(tfSmtp);

		JLabel lblAutenticacao = new JLabel("Autentica\u00E7\u00E3o");
		lblAutenticacao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblAutenticacao.setBounds(309, 62, 204, 14);
		pServidorEmail.add(lblAutenticacao);

		btnSalvarServidor = new JButton("Salvar");
		btnSalvarServidor.setBorder(null);
		btnSalvarServidor.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/salvar.png")));
		btnSalvarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ServidorEmail servidorEmail = new ServidorEmail();
				ServidorEmailRN servidorEmailRN = new ServidorEmailRN();
				
				if (tfServidorEmail.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite o nome do servido de e-mail!",
							"Salvar servidor de e-mail", JOptionPane.INFORMATION_MESSAGE);

				} else if (tfHost.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite o host do servidor!", "Salvar servidor de e-mail",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (tfSmtp.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite o número da porta SMTP!", "Salvar servidor de e-mail",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (tfAutenticacao.getPassword().equals("")) {

					JOptionPane.showMessageDialog(null, "Digite a autenticação do servidor!",
							"Salvar servidor de e-mail", JOptionPane.INFORMATION_MESSAGE);

				} else {
					
					servidorEmail = servidorEmailRN.buscarServidorEmail();
			
					if (servidorEmail.getId_ServidorEmail() == 0) {
						
						servidorEmail.setNomeServidor(tfServidorEmail.getText().toUpperCase());
						servidorEmail.setHostName(tfHost.getText());
						servidorEmail.setSmtpPort(Integer.valueOf(tfSmtp.getText()));
						servidorEmail.setAuthentication(String.valueOf(tfAutenticacao.getPassword()));
						
						servidorEmailRN.adicionarEmail(servidorEmail);

					} else {
												
						servidorEmail.setNomeServidor(tfServidorEmail.getText().toUpperCase());
						servidorEmail.setHostName(tfHost.getText());
						servidorEmail.setSmtpPort(Integer.valueOf(tfSmtp.getText()));
						servidorEmail.setAuthentication(String.valueOf(tfAutenticacao.getPassword()));
						
						servidorEmailRN.editarServidorEmail(servidorEmail);

					}
					
					tfServidorEmail.setText("TESTE HOST");
					tfHost.setText("smtp.teste.com");
					tfSmtp.setText("000");
					tfAutenticacao.setText("*************");
										
					tfServidorEmail.setEditable(false);
					tfHost.setEditable(false);
					tfSmtp.setEditable(false);
					tfAutenticacao.setEditable(false);
					
					btnSalvarServidor.setVisible(false);
					btnCancelarServidor.setVisible(false);

				}

			}
		});
		btnSalvarServidor.setVisible(false);
		btnSalvarServidor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvarServidor.setBounds(35, 113, 100, 35);
		pServidorEmail.add(btnSalvarServidor);

		btnSairServidor = new JButton("Sair");
		btnSairServidor.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnSairServidor.setBorder(null);
		btnSairServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnSairServidor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSairServidor.setBounds(456, 113, 100, 35);
		pServidorEmail.add(btnSairServidor);

		btnEditarServidor = new JButton("Editar");
		btnEditarServidor.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/editar.png")));
		btnEditarServidor.setBorder(null);
		btnEditarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfServidorEmail.setEditable(true);
				tfHost.setEditable(true);
				tfSmtp.setEditable(true);
				tfAutenticacao.setEditable(true);

				btnSalvarServidor.setVisible(true);
				btnCancelarServidor.setVisible(true);

				ServidorEmail servidorEmail = new ServidorEmail();
				ServidorEmailRN servidorEmailRN = new ServidorEmailRN();

				servidorEmail = servidorEmailRN.buscarServidorEmail();

				if (servidorEmail.getId_ServidorEmail() == 0) {

					tfServidorEmail.setText("");
					tfHost.setText("");
					tfSmtp.setText("");
					tfAutenticacao.setText("");

				} else {

					tfServidorEmail.setText(servidorEmail.getNomeServidor());
					tfHost.setText(servidorEmail.getHostName());
					tfSmtp.setText(String.valueOf(servidorEmail.getSmtpPort()));
					tfAutenticacao.setText(servidorEmail.getAuthentication());

				}

			}
		});
		btnEditarServidor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnEditarServidor.setBounds(322, 113, 100, 35);
		pServidorEmail.add(btnEditarServidor);

		btnCancelarServidor = new JButton("Cancelar");
		btnCancelarServidor.setIcon(new ImageIcon(TelaConfiguracoes.class.getResource("/br/com/crachas/image/excluir.png")));
		btnCancelarServidor.setBorder(null);
		btnCancelarServidor.setVisible(false);
		btnCancelarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfServidorEmail.setEditable(false);
				tfHost.setEditable(false);
				tfSmtp.setEditable(false);
				tfAutenticacao.setEditable(false);

				btnSalvarServidor.setVisible(false);
				btnCancelarServidor.setVisible(false);

				tfServidorEmail.setText("TESTE HOST");
				tfHost.setText("smtp.teste.com");
				tfSmtp.setText("000");
				tfAutenticacao.setText("*************");

			}
		});
		btnCancelarServidor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelarServidor.setBounds(169, 113, 119, 35);
		pServidorEmail.add(btnCancelarServidor);
		
		tfAutenticacao = new JPasswordField();
		tfAutenticacao.setEditable(false);
		tfAutenticacao.setBounds(309, 79, 247, 25);
		pServidorEmail.add(tfAutenticacao);

		lblLogado = new JLabel("");
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());
		lblLogado.setBounds(11, 461, 537, 32);
		getContentPane().add(lblLogado);

		ProdutoRN produtoRN = new ProdutoRN();

		listaProduto = produtoRN.listarProduto();

		for (Produto pr : listaProduto) {

			cbNomeProduto.addItem(pr.getNome());

		}

	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public void cancelar() {

		tfEmailNome.setEditable(false);
		tfEmailNome.setText("");
		tfEmailDesc.setEditable(false);
		tfEmailDesc.setText("");
		tfEndImg.setEditable(false);
		tfEndImg.setText("");
		btnAbrir.setEnabled(false);
		btnSalvarEmail.setVisible(false);
		btnCancelarEmail.setVisible(false);
		btnEditarEmail.setVisible(false);
		btnMostrarEmailSalvo.setEnabled(true);
		lblImagemNãoEncontrada.setVisible(false);

		ImageIcon iconFrente = new ImageIcon("");

		lblImagem.setIcon(new ImageIcon(iconFrente.getImage().getScaledInstance(lblImagem.getWidth(),
				lblImagem.getHeight(), Image.SCALE_DEFAULT)));
	}
}
