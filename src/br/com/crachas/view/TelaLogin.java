package br.com.crachas.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import br.com.crachas.controller.LerBancoDados;
import br.com.crachas.controller.Operador;
import br.com.crachas.controller.OperadorRN;
import br.com.crachas.controller.Producao;
import br.com.crachas.uteis.PesquisarComboBox;
import br.com.crachas.uteis.TamanhoMaxTextField;


public class TelaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5922907749894607031L;
	private JPanel contentPane;
	private JPasswordField pfSenha;
	private JComboBox<String> cbLogin;
	private static Operador operador = new Operador();
	private List<Producao> lista;
	private JPanel panelLogin;
	private JButton btnConfirmar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaLogin.class.getResource("/br/com/crachas/image/ne.png")));
		setType(Type.POPUP);
		
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 214);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		TamanhoMaxTextField TamanhoMax = new TamanhoMaxTextField();
		TamanhoMax.setMaxChars(7);

		panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Acesso", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelLogin.setBounds(10, 11, 236, 143);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);

		cbLogin = new JComboBox<String>();
		cbLogin.setToolTipText("Digite o Login");
		cbLogin.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		cbLogin.setBounds(79, 22, 142, 25);
		panelLogin.add(cbLogin);
		cbLogin.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cbLogin.setEditable(true);
		cbLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		cbLogin.setEditable(true);
		new PesquisarComboBox(cbLogin);

		pfSenha = new JPasswordField();
		pfSenha.setForeground(Color.BLACK);
		pfSenha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnConfirmar);

			}

			@Override
			public void focusLost(FocusEvent arg0) {

				getRootPane().setDefaultButton(null);

			}
		});
		pfSenha.setToolTipText("Digite a senha");
		pfSenha.setBounds(79, 62, 142, 25);
		panelLogin.add(pfSenha);
		pfSenha.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pfSenha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		pfSenha.setDocument(TamanhoMax);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(14, 22, 55, 25);
		panelLogin.add(lblLogin);
		lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(14, 65, 58, 25);
		panelLogin.add(lblSenha);
		lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		btnConfirmar = new JButton("Entrar");
		btnConfirmar.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/crachas/image/login.png")));
		btnConfirmar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnConfirmar.setBounds(14, 100, 97, 33);
		panelLogin.add(btnConfirmar);
		btnConfirmar.setToolTipText("Entrar");

		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (cbLogin.getSelectedItem().equals("")
						&& String.valueOf(pfSenha.getPassword()).equals("")) {

					JOptionPane.showMessageDialog(null, "Selecione o Login e digite a senha!", "Abrir Programa",
							JOptionPane.INFORMATION_MESSAGE);
					
					cbLogin.requestFocus();
					

				} else if (cbLogin.getSelectedItem().equals("")) {

					JOptionPane.showMessageDialog(null, "Selecione o Login!");
					cbLogin.setSelectedIndex(0);
					cbLogin.requestFocus();
					
				} else if (String.valueOf(pfSenha.getPassword()).equals("")) {

					JOptionPane.showMessageDialog(null, "Digite a senha!");
					pfSenha.setText("");
					pfSenha.requestFocus();
					
				} else {

					OperadorRN operadorRN = new OperadorRN();

					operador = operadorRN.login(String.valueOf(cbLogin.getSelectedItem()).toUpperCase(),
							String.valueOf(pfSenha.getPassword()));

					if (operador.getLogin() != null) {

						
							try {

								UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e) {

								JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.WARNING_MESSAGE);
								e.printStackTrace();

							}

							TelaProducao telaProducao = new TelaProducao();
							telaProducao.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							telaProducao.getBtnEditar().setEnabled(true);
							telaProducao.getBtnConfiguracoes().setEnabled(true);

							telaProducao.setLocationRelativeTo(null);
							telaProducao.setVisible(true);

							dispose();

						
					} else {

						JOptionPane.showMessageDialog(null, "Login não confirmado");
						
						cbLogin.setSelectedIndex(0);
						cbLogin.requestFocus();
						pfSenha.setText("");
						
					}

				}

			}
		});
		btnConfirmar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		JButton btnFechar = new JButton("Sair");
		btnFechar.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnFechar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnFechar.setToolTipText("Sair");
		btnFechar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnFechar.setBounds(131, 100, 90, 33);
		panelLogin.add(btnFechar);

		JButton btnConfigurarBancoDados = new JButton("Conex\u00E3o com o banco de dados");
		btnConfigurarBancoDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

				try {

					TelaConfigurarBanco cb = new TelaConfigurarBanco();
					cb.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					cb.setLocationRelativeTo(null);

					LerBancoDados lerBancoDados = new LerBancoDados();
					lerBancoDados = LerBancoDados.lerDadosBanco();

					cb.getTfHost().setText(lerBancoDados.getHost());
					cb.getTfPorta().setText(lerBancoDados.getPorta());
					cb.getTfBanco().setText(lerBancoDados.getBanco());

					if (cbLogin.getSelectedItem().equals("")) {

						try {

							LerBancoDados.GravarDadosBanco(cb.getTfHost().getText().toUpperCase(),
									cb.getTfPorta().getText().toUpperCase(), cb.getTfBanco().getText().toUpperCase());

						} catch (IOException e1) {

							e1.printStackTrace();

						}

					} else {

					}

					cb.setVisible(true);

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		btnConfigurarBancoDados.setContentAreaFilled(false);
		btnConfigurarBancoDados.setBorderPainted(false);
		btnConfigurarBancoDados.setForeground(Color.BLUE);
		btnConfigurarBancoDados.setHorizontalTextPosition(SwingConstants.CENTER);
		btnConfigurarBancoDados.setActionCommand("");
		btnConfigurarBancoDados.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		btnConfigurarBancoDados.setBounds(10, 154, 236, 29);
		contentPane.add(btnConfigurarBancoDados);

		OperadorRN operadorRN = new OperadorRN();
		List<Operador> listaOperador = new ArrayList<Operador>();

		listaOperador = operadorRN.listaOperador();

		for (Operador op : listaOperador) {

			cbLogin.addItem(op.getLogin());

		}
	}

	public static Operador getOperador() {
		return operador;
	}

	public static void setOperador(Operador operador) {
		TelaLogin.operador = operador;
	}

	public List<Producao> getLista() {
		return lista;
	}

	public void setLista(List<Producao> lista) {
		this.lista = lista;
	}

	public JComboBox<String> getCbLogin() {
		return cbLogin;
	}

	public void setCbLogin(JComboBox<String> cbLogin) {
		this.cbLogin = cbLogin;
	}
}
