package br.com.crachas.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class TelaCadastroClientes extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972932495900568182L;
	private List<Cliente> listaCliente;
	private ClienteRN clienteRN;
	private Cliente cliente;
	private JLabel lblLogado;
	private JTextField tfNomeCliente;
	private JTextField tfVendedor;
	private JTextField tfContato;
	private JTextField tfEmail;
	private JTextField tfFone1;
	private JTextField tfFone2;
	private JEditorPane epDescricaoLayout;
	private JComboBox<String> cbEntrega;
	private JComboBox<String> cbUnidade;

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
	public TelaCadastroClientes() {
		getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 11));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);

		clienteRN = new ClienteRN();
		cliente = new Cliente();

		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaConfiguracoes.class.getResource("/image/ne.png")));
		setTitle("Novo Cliente");
		setModal(true);
		setBounds(100, 100, 600, 488);

		getContentPane().setLayout(null);

		lblLogado = new JLabel("");
		lblLogado.setBounds(10, 417, 574, 32);
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());
		getContentPane().add(lblLogado);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TelaProducao telaProducao = new TelaProducao();

				if (tfNomeCliente.getText().equals("") || epDescricaoLayout.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "O nome do cliente e discrição do layout são obrigatórios!");

				} else if (cbEntrega.getSelectedItem().equals("SELECIONE...")) {

					JOptionPane.showMessageDialog(null, "Selecione se nosso portador fará a entrega!");

				} else if (cbUnidade.getSelectedItem().equals("SELECIONE...")) {

					JOptionPane.showMessageDialog(null, "Selecione a unidade da empresa!");

				} else {
					
					boolean entrega = true;
					
					if (cbEntrega.getSelectedItem().equals("SIM")){
													
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

					try {

						telaProducao.getCbEmpresa().removeAllItems();

						for (Cliente c : listaCliente) {

							telaProducao.getCbEmpresa().addItem(c.getNome());

						}

					} catch (Exception e) {

						JOptionPane.showMessageDialog(null, "Erro ao criar o ComboBox!");

					}
					
					dispose();

				}
			}
		});
		btnSalvar.setIcon(new ImageIcon(TelaCadastroClientes.class.getResource("/image/salvar.png")));
		btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvar.setBounds(114, 371, 126, 35);
		getContentPane().add(btnSalvar);

		JPanel pClientes = new JPanel();
		pClientes.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dados do Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pClientes.setBounds(10, 11, 574, 327);
		getContentPane().add(pClientes);
		pClientes.setLayout(null);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblCliente.setBounds(19, 31, 46, 14);
		pClientes.add(lblCliente);

		tfNomeCliente = new JTextField();
		tfNomeCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfNomeCliente.setDisabledTextColor(Color.WHITE);
		tfNomeCliente.setColumns(10);
		tfNomeCliente.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfNomeCliente.setBackground(Color.WHITE);
		tfNomeCliente.setBounds(19, 53, 270, 25);
		pClientes.add(tfNomeCliente);

		JLabel lblVendedor = new JLabel("Vendedor");
		lblVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblVendedor.setBounds(316, 31, 65, 14);
		pClientes.add(lblVendedor);

		tfVendedor = new JTextField();
		tfVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfVendedor.setDisabledTextColor(Color.WHITE);
		tfVendedor.setColumns(10);
		tfVendedor.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfVendedor.setBackground(Color.WHITE);
		tfVendedor.setBounds(316, 53, 231, 25);
		pClientes.add(tfVendedor);

		JLabel lblContato = new JLabel("Contato");
		lblContato.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblContato.setBounds(19, 88, 46, 14);
		pClientes.add(lblContato);

		tfContato = new JTextField();
		tfContato.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfContato.setDisabledTextColor(Color.WHITE);
		tfContato.setColumns(10);
		tfContato.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfContato.setBackground(Color.WHITE);
		tfContato.setBounds(19, 110, 270, 25);
		pClientes.add(tfContato);

		JScrollPane spDescLayout = new JScrollPane();
		spDescLayout.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spDescLayout.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spDescLayout.setBounds(316, 110, 236, 191);
		pClientes.add(spDescLayout);

		epDescricaoLayout = new JEditorPane();
		epDescricaoLayout.setForeground(Color.RED);
		epDescricaoLayout.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		epDescricaoLayout.setDisabledTextColor(Color.WHITE);
		epDescricaoLayout.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
		spDescLayout.setViewportView(epDescricaoLayout);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEmail.setBounds(19, 198, 46, 14);
		pClientes.add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfEmail.setDisabledTextColor(Color.WHITE);
		tfEmail.setColumns(10);
		tfEmail.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfEmail.setBackground(Color.WHITE);
		tfEmail.setBounds(19, 219, 270, 25);
		pClientes.add(tfEmail);

		JLabel lblFone1 = new JLabel("Fone 01");
		lblFone1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblFone1.setBounds(19, 255, 46, 14);
		pClientes.add(lblFone1);

		tfFone1 = new JTextField();
		tfFone1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfFone1.setDisabledTextColor(Color.WHITE);
		tfFone1.setColumns(10);
		tfFone1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfFone1.setBackground(Color.WHITE);
		tfFone1.setBounds(19, 276, 130, 25);
		pClientes.add(tfFone1);

		JLabel lblFone2 = new JLabel("Fone 02");
		lblFone2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblFone2.setBounds(159, 255, 46, 14);
		pClientes.add(lblFone2);

		tfFone2 = new JTextField();
		tfFone2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfFone2.setDisabledTextColor(Color.WHITE);
		tfFone2.setColumns(10);
		tfFone2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfFone2.setBackground(Color.WHITE);
		tfFone2.setBounds(159, 276, 130, 25);
		pClientes.add(tfFone2);

		JLabel lblDescrioDoLayout = new JLabel("Descri\u00E7\u00E3o do Layout");
		lblDescrioDoLayout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblDescrioDoLayout.setBounds(316, 89, 160, 14);
		pClientes.add(lblDescrioDoLayout);
		
		JLabel lblEntrega = new JLabel("Entrega");
		lblEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEntrega.setBounds(19, 146, 46, 14);
		pClientes.add(lblEntrega);
		
		cbEntrega = new JComboBox<String>();
		cbEntrega.setModel(new DefaultComboBoxModel<String>(new String[] {"SELECIONE...", "SIM", "N\u00C3O"}));
		cbEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbEntrega.setBounds(19, 167, 130, 25);
		pClientes.add(cbEntrega);
		
		JComboBox<String> cbUnidade = new JComboBox<String>();
		cbUnidade.setModel(new DefaultComboBoxModel<String>(new String[] {"SELECIONE...", "PE", "JP"}));
		cbUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cbUnidade.setBounds(159, 167, 130, 25);
		pClientes.add(cbUnidade);
		
		JLabel lblUnidade = new JLabel("Entrega");
		lblUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblUnidade.setBounds(159, 146, 46, 14);
		pClientes.add(lblUnidade);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		btnSair.setIcon(new ImageIcon(TelaCadastroClientes.class.getResource("/image/cancelar.png")));
		btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSair.setBounds(354, 371, 126, 35);
		getContentPane().add(btnSair);

	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}
