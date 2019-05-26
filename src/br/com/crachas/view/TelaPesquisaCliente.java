package br.com.crachas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class TelaPesquisaCliente extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4362430480132484521L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfPesquisarCliente;
	private JButton btnPesquisar;

	private List<Cliente> listaCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaPesquisaCliente dialog = new TelaPesquisaCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TelaPesquisaCliente() {

		listaCliente = new ArrayList<Cliente>();

		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Pesquisar Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPesquisaCliente.class.getResource("/br/com/crachas/image/ne.png")));
		setResizable(false);
		setBounds(100, 100, 463, 155);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			btnPesquisar = new JButton("Pesquisar");
			btnPesquisar.setIcon(new ImageIcon(TelaPesquisaCliente.class.getResource("/br/com/crachas/image/pesquisar.png")));
			btnPesquisar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (tfPesquisarCliente.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Digite o nome do Cliente!");

					} else {

						ClienteRN clienteRN = new ClienteRN();

						listaCliente = new ArrayList<Cliente>();

						listaCliente = clienteRN.pesquisarCliente(tfPesquisarCliente.getText().toUpperCase());

						tfPesquisarCliente.setText("");

						if (listaCliente.isEmpty()) {

							JOptionPane.showMessageDialog(null, "Cliente Não encontrado!");

						}

						dispose();

					}
				}
			});
			btnPesquisar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			btnPesquisar.setBounds(25, 80, 115, 35);
			contentPanel.add(btnPesquisar);
			btnPesquisar.setActionCommand("OK");
			getRootPane().setDefaultButton(btnPesquisar);
		}
		{
			JButton btnCancelar = new JButton("Sair");
			btnCancelar.setIcon(new ImageIcon(TelaPesquisaCliente.class.getResource("/br/com/crachas/image/cancelar.png")));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					listaCliente.clear();

					dispose();

				}
			});
			btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			btnCancelar.setBounds(202, 80, 115, 35);
			contentPanel.add(btnCancelar);
			btnCancelar.setActionCommand("Cancel");
		}
		{
			tfPesquisarCliente = new JTextField();
			tfPesquisarCliente.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tfPesquisarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfPesquisarCliente.setBounds(25, 36, 292, 25);
			contentPanel.add(tfPesquisarCliente);
			tfPesquisarCliente.setColumns(10);
			
			tfPesquisarCliente.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {

					getRootPane().setDefaultButton(btnPesquisar);

				}

				@Override
				public void focusLost(FocusEvent e) {

					getRootPane().setDefaultButton(null);

				}
			});
		}
		{
			JLabel lblCliente = new JLabel("Nome do Cliente");
			lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			lblCliente.setBounds(25, 11, 141, 14);
			contentPanel.add(lblCliente);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(TelaPesquisaCliente.class.getResource("/br/com/crachas/image/cadastro_usuario.png")));
		lblNewLabel.setBounds(330, 11, 117, 104);
		contentPanel.add(lblNewLabel);
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}
