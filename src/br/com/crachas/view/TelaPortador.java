package br.com.crachas.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import br.com.crachas.controller.Portador;
import br.com.crachas.controller.PortadorRN;

public class TelaPortador extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2461136321084765815L;
	private JTextField tfPortador;
	private JTextField tfRgCpf;
	private int id_os;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaPortador JWindow = new TelaPortador();
			JWindow.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			JWindow.setLocationRelativeTo(null);
			JWindow.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TelaPortador() {
		setFont(new Font("Segoe UI", Font.PLAIN, 12));

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);

		getContentPane().setFont(new Font("Arial", Font.PLAIN, 11));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPortador.class.getResource("/image/ne.png")));
		setTitle("Identifica\u00E7\u00E3o do Portador");
		setBounds(100, 100, 446, 140);
		getContentPane().setLayout(null);

		new TelaEntregar();
		id_os = TelaEntregar.getIdOS();

		JLabel lblRgCpf = new JLabel("RG ou CPF");
		lblRgCpf.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblRgCpf.setBounds(10, 55, 141, 14);
		getContentPane().add(lblRgCpf);

		JLabel lblNewLabel = new JLabel("Nome do Portador");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 11, 141, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(TelaPortador.class.getResource("/image/cadastro_usuario_2.png")));
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(319, 5, 121, 96);
		getContentPane().add(lblNewLabel_1);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFocusable(false);
		btnSalvar.setBorder(null);
		btnSalvar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvar.setIcon(new ImageIcon(TelaPortador.class.getResource("/image/salva1.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					if (tfPortador.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o nome do Portador!","Identificação do Portador",
								JOptionPane.INFORMATION_MESSAGE);

					} else if (tfRgCpf.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o RG ou o CPF do portador!","Identificação do Portador",
								JOptionPane.INFORMATION_MESSAGE);

					} else {

						PortadorRN portadorRN = new PortadorRN();
						Portador portador = new Portador();

						portador.setNomePortador(tfPortador.getText().toUpperCase());
						portador.setIdentificacaoPortador(tfRgCpf.getText());
						portador.setId_os(id_os);

						boolean salvou = portadorRN.novoPortador(portador);

						new TelaEntregar();
						TelaEntregar.setSalvou(salvou);
						
						dispose();

					}

				} catch (Exception e1) {

					JOptionPane.showMessageDialog(null, "O portador não foi salvo!","Identificação do Portador",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}
		});
		btnSalvar.setBounds(216, 21, 98, 33);
		getContentPane().add(btnSalvar);

		tfPortador = new JTextField();
		tfPortador.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfPortador.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfPortador.setBounds(10, 25, 190, 25);
		getContentPane().add(tfPortador);
		tfPortador.setColumns(10);

		tfRgCpf = new JTextField();
		tfRgCpf.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfRgCpf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tfRgCpf.setBounds(10, 69, 190, 25);
		getContentPane().add(tfRgCpf);
		tfRgCpf.setColumns(10);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
			}
		});
		btnSair.setIcon(new ImageIcon(TelaPortador.class.getResource("/image/voltar.png")));
		btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSair.setBounds(216, 65, 98, 33);
		getContentPane().add(btnSair);
		tfRgCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				getRootPane().setDefaultButton(btnSalvar);

			}

			@Override
			public void focusLost(FocusEvent arg0) {

				getRootPane().setDefaultButton(null);

			}
		});
		getContentPane()
				.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tfPortador, tfRgCpf, btnSalvar }));
	}

	public int getId_os() {
		return id_os;
	}

	public void setId_os(int id_os) {
		this.id_os = id_os;
	}
}
