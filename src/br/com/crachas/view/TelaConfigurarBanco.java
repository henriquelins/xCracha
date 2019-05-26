package br.com.crachas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import br.com.crachas.controller.IniciarAplicativoCrachas;
import br.com.crachas.controller.LerBancoDados;

public class TelaConfigurarBanco extends JDialog {

	private static final long serialVersionUID = 1320955535683077595L;

	private final JPanel contentPanel = new JPanel();
	private JTextField tfHost;
	private JTextField tfPorta;
	private JTextField tfBanco;
	private JButton btnCancelar;
	private JButton btnEditar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TelaConfigurarBanco dialog = new TelaConfigurarBanco();
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
	public TelaConfigurarBanco() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TelaConfigurarBanco.class.getResource("/image/maos.png")));
		setTitle("Configura\u00E7\u00E3o do Banco de Dados");
		setResizable(false);
		setType(Type.POPUP);
		setBounds(100, 100, 320, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.menu);
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		tfHost = new JTextField();
		tfHost.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfHost.setBackground(Color.YELLOW);
		tfHost.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfHost.setEditable(false);
		tfHost.setBounds(30, 43, 250, 21);
		contentPanel.add(tfHost);
		tfHost.setColumns(10);

		JLabel lblHost = new JLabel("Host");
		lblHost.setForeground(Color.BLACK);
		lblHost.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblHost.setBounds(30, 24, 250, 14);
		contentPanel.add(lblHost);

		JLabel lblPorta = new JLabel("Porta");
		lblPorta.setForeground(Color.BLACK);
		lblPorta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPorta.setBounds(30, 75, 250, 14);
		contentPanel.add(lblPorta);

		tfPorta = new JTextField();
		tfPorta.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfPorta.setBackground(Color.YELLOW);
		tfPorta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfPorta.setEditable(false);
		tfPorta.setColumns(10);
		tfPorta.setBounds(31, 93, 249, 21);
		contentPanel.add(tfPorta);

		JLabel lblBanco = new JLabel("Banco");
		lblBanco.setForeground(Color.BLACK);
		lblBanco.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblBanco.setBounds(30, 126, 250, 14);
		contentPanel.add(lblBanco);

		tfBanco = new JTextField();
		tfBanco.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfBanco.setBackground(Color.YELLOW);
		tfBanco.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfBanco.setEditable(false);
		tfBanco.setColumns(10);
		tfBanco.setBounds(31, 144, 249, 21);
		contentPanel.add(tfBanco);
		
				btnEditar = new JButton("Editar");
				btnEditar.setBounds(31, 191, 89, 23);
				contentPanel.add(btnEditar);
				btnEditar.setForeground(Color.BLACK);
				//btnEditar.setRolloverIcon(new ImageIcon(TelaConfigurarBanco.class.getResource("/image/botao2.jpg")));
				btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
				//btnEditar.setIcon(new ImageIcon(ConfigurarBancoView.class.getResource("/image/botao.jpg")));
				btnEditar.setBorder(null);
				btnEditar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				
						btnCancelar = new JButton("Sair");
						btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
						btnCancelar.setBounds(192, 191, 89, 23);
						contentPanel.add(btnCancelar);
						btnCancelar.setForeground(Color.BLACK);
						btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
						
								getRootPane().setDefaultButton(btnCancelar);
								btnCancelar.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (btnEditar.getText().equals("Salvar")) {

											tfHost.setEditable(false);
											tfHost.setBackground(Color.YELLOW);
											tfPorta.setEditable(false);
											tfPorta.setBackground(Color.YELLOW);
											tfBanco.setEditable(false);
											tfBanco.setBackground(Color.YELLOW);

											btnEditar.setText("Editar");

										} else {

											dispose();

											IniciarAplicativoCrachas iniaciarAplicativo = new IniciarAplicativoCrachas();
											iniaciarAplicativo.Iniciar();

										}

									}
								});
				btnEditar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (btnEditar.getText().equals("Editar")) {

							tfHost.setEditable(true);
							tfHost.setBackground(Color.WHITE);
							tfPorta.setEditable(true);
							tfPorta.setBackground(Color.WHITE);
							tfBanco.setEditable(true);
							tfBanco.setBackground(Color.WHITE);

							btnEditar.setText("Salvar");

						} else {

							getRootPane().setDefaultButton(btnEditar);

							try {

								LerBancoDados.GravarDadosBanco(tfHost.getText().toUpperCase(), tfPorta.getText().toUpperCase(),
										tfBanco.getText().toUpperCase());

								JOptionPane.showMessageDialog(null, "Nova configuração salva!", "Configurar banco de dados",
										JOptionPane.INFORMATION_MESSAGE);

							} catch (FileNotFoundException e1) {

								JOptionPane.showMessageDialog(null, "Erro " + e1);

							} catch (IOException e1) {

								JOptionPane.showMessageDialog(null, "Erro " + e1);
							}

							dispose();

							IniciarAplicativoCrachas iniaciarAplicativo = new IniciarAplicativoCrachas();
							iniaciarAplicativo.Iniciar();

						}

					}
				});
	}

	public JTextField getTfHost() {
		return tfHost;
	}

	public void setTfHost(JTextField tfHost) {
		this.tfHost = tfHost;
	}

	public JTextField getTfPorta() {
		return tfPorta;
	}

	public void setTfPorta(JTextField tfPorta) {
		this.tfPorta = tfPorta;
	}

	public JTextField getTfBanco() {
		return tfBanco;
	}

	public void setTfBanco(JTextField tfBanco) {
		this.tfBanco = tfBanco;
	}
}
