package br.com.crachas.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.mail.EmailException;

import br.com.crachas.controller.Cliente;
import br.com.crachas.controller.ClienteRN;
import br.com.crachas.controller.ComprovanteDeEntrega;
import br.com.crachas.controller.Email;
import br.com.crachas.controller.EstadoOS;
import br.com.crachas.controller.EstadoOSRN;
import br.com.crachas.controller.OrdemServico;
import br.com.crachas.controller.OrdemServicoRN;
import br.com.crachas.controller.Producao;
import br.com.crachas.controller.ProducaoRN;
import br.com.crachas.controller.Relatorio;
import br.com.crachas.uteis.ImpressaoJpanel;
import br.com.crachas.uteis.TamanhoMaxTextField;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings("unused")
public class TelaOrdemServico extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4045848918832266108L;
	private static List<OrdemServico> listOS;
	private static List<ComprovanteDeEntrega> listaComprovanteEntrega;
	private JTextField tfOS;
	private JTextField tfClienteOS;
	private JTextField tfContatoOS;
	private JTextField tfFone1OS;
	private JTextField tfFone2OS;
	private JTextField tfVendedorOS;
	private JTextField tfTotal;
	private JTextField tfEmailOS;
	private JTextPane tpDetalhesOS;
	private JButton btnEditarOS;
	private JButton btnTotalExpedido;
	private JPanel pOS;
	private JButton btnIniciar;
	private JTextField tfMensagem;
	private JButton btnConfirmar;
	private JTextField tfTotalExpedido;
	private EstadoOS estadoOS;
	private EstadoOSRN estadoOSRN;
	private int estagio;
	private JLabel lblLogado;
	private JTextPane tpMotivo;
	private JButton btnCancelar;
	private JButton btnSalvar;
	private JLabel lblEntrega;
	private JComboBox<String> cbEntrega;
	private JTextField tfEntrega;
	private JComboBox<String> cbUnidade;
	private JTextField tfUnidade;
	private JTextField dtRecebimento;
	private JTextField dtPrevista;
	private JLabel lblOS;
	private static OrdemServico os;
	private JButton btnImprimirOS;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			TelaOrdemServico dialog = new TelaOrdemServico();
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
	public TelaOrdemServico() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaOrdemServico.class.getResource("/br/com/crachas/image/ne.png")));
		
		listOS = new ArrayList<>();
		listaComprovanteEntrega = new ArrayList<>();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);

		estadoOS = new EstadoOS();
		estadoOSRN = new EstadoOSRN();
		setOs(new OrdemServico());

		setTitle("Ordem de Servi\u00E7o");
		setModal(true);
		setBounds(100, 100, 647, 630);
		getContentPane().setLayout(null);

		btnConfirmar = new JButton("Sair");
		btnConfirmar.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/cancelar.png")));
		btnConfirmar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (btnSalvar.isVisible() == false | btnCancelar.isVisible() == false) {

					tfTotalExpedido.setText("");
					tpMotivo.setText("");

					dispose();

				} else {

					JOptionPane.showMessageDialog(null, "Para sair finalize antes a operação!");

				}

			}
		});
		btnConfirmar.setBorder(null);

		btnConfirmar.setBounds(513, 555, 113, 35);
		getContentPane().add(btnConfirmar);
		{
			pOS = new JPanel();
			pOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			pOS.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dados da Ordem de Servi\u00E7o",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pOS.setBounds(15, 11, 611, 443);
			getContentPane().add(pOS);
			pOS.setLayout(null);
			{
				JLabel lblNOS = new JLabel("N\u00BA da OS: ");
				lblNOS.setBounds(21, 49, 65, 21);
				pOS.add(lblNOS);
				lblNOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfOS = new JTextField();
				tfOS.setBackground(Color.WHITE);
				tfOS.setForeground(Color.BLACK);
				tfOS.setBounds(18, 70, 64, 20);
				pOS.add(tfOS);
				tfOS.setEditable(false);
				tfOS.setDisabledTextColor(Color.WHITE);
				tfOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
				tfOS.setColumns(10);
			}
			{
				JLabel lblTotal = new JLabel("Total Recebido");
				lblTotal.setBounds(18, 95, 100, 21);
				pOS.add(lblTotal);
				lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfTotal = new JTextField();
				tfTotal.setBackground(Color.WHITE);
				tfTotal.setForeground(Color.BLACK);
				tfTotal.setBounds(18, 116, 118, 20);
				pOS.add(tfTotal);
				tfTotal.setEditable(false);
				tfTotal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfTotal.setColumns(10);
				tfTotal.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}
			{
				JLabel lblDtRecebimento = new JLabel("Dt Recebimento");
				lblDtRecebimento.setBounds(92, 49, 90, 21);
				pOS.add(lblDtRecebimento);
				lblDtRecebimento.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				JLabel lblDtPrevista = new JLabel("Dt Prevista");
				lblDtPrevista.setBounds(190, 49, 82, 21);
				pOS.add(lblDtPrevista);
				lblDtPrevista.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				JLabel lblCliente = new JLabel("Cliente");
				lblCliente.setBounds(18, 178, 100, 25);
				pOS.add(lblCliente);
				lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfClienteOS = new JTextField();
				tfClienteOS.setBackground(Color.WHITE);
				tfClienteOS.setBounds(18, 201, 261, 20);
				pOS.add(tfClienteOS);
				tfClienteOS.setEditable(false);
				tfClienteOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfClienteOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
				tfClienteOS.setColumns(10);
			}

			JLabel lblVendedor = new JLabel("Vendedor");
			lblVendedor.setBounds(449, 178, 145, 21);
			pOS.add(lblVendedor);
			lblVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 11));

			tfVendedorOS = new JTextField();
			tfVendedorOS.setBackground(Color.WHITE);
			tfVendedorOS.setBounds(449, 201, 145, 20);
			pOS.add(tfVendedorOS);
			tfVendedorOS.setEditable(false);
			tfVendedorOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfVendedorOS.setColumns(10);
			tfVendedorOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
			{
				JLabel lblContato = new JLabel("Contato");
				lblContato.setBounds(294, 178, 145, 21);
				pOS.add(lblContato);
				lblContato.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfContatoOS = new JTextField();
				tfContatoOS.setBackground(Color.WHITE);
				tfContatoOS.setBounds(294, 201, 145, 20);
				pOS.add(tfContatoOS);
				tfContatoOS.setEditable(false);
				tfContatoOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfContatoOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
				tfContatoOS.setColumns(10);
			}
			{
				JLabel lblEmail = new JLabel("E-mail");
				lblEmail.setBounds(18, 220, 82, 21);
				pOS.add(lblEmail);
				lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfEmailOS = new JTextField();
				tfEmailOS.setBackground(Color.WHITE);
				tfEmailOS.setBounds(18, 240, 303, 20);
				pOS.add(tfEmailOS);
				tfEmailOS.setEditable(false);
				tfEmailOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfEmailOS.setColumns(10);
				tfEmailOS.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}
			{
				JLabel lblFone1 = new JLabel("Fone 01");
				lblFone1.setBounds(331, 220, 49, 21);
				pOS.add(lblFone1);
				lblFone1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			}
			{
				tfFone1OS = new JTextField();
				tfFone1OS.setBackground(Color.WHITE);
				tfFone1OS.setBounds(331, 240, 128, 20);
				pOS.add(tfFone1OS);
				tfFone1OS.setEditable(false);
				tfFone1OS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tfFone1OS.setBorder(new LineBorder(Color.LIGHT_GRAY));
				tfFone1OS.setColumns(10);
			}

			JLabel lblFone2 = new JLabel("Fone 02");
			lblFone2.setBounds(466, 220, 49, 21);
			pOS.add(lblFone2);
			lblFone2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

			tfFone2OS = new JTextField();
			tfFone2OS.setBackground(Color.WHITE);
			tfFone2OS.setBounds(466, 240, 128, 20);
			pOS.add(tfFone2OS);
			tfFone2OS.setEditable(false);
			tfFone2OS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfFone2OS.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tfFone2OS.setColumns(10);

			JLabel lblDetalhes = new JLabel("Detalhes do Layout");
			lblDetalhes.setBounds(18, 263, 128, 21);
			pOS.add(lblDetalhes);
			lblDetalhes.setFont(new Font("Segoe UI", Font.PLAIN, 11));

			JLabel lblTotalExpedido = new JLabel("Total Expedido");
			lblTotalExpedido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			lblTotalExpedido.setBounds(152, 95, 92, 21);
			pOS.add(lblTotalExpedido);

			tfTotalExpedido = new JTextField();
			tfTotalExpedido.setBackground(Color.WHITE);
			tfTotalExpedido.setForeground(Color.BLACK);
			tfTotalExpedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfTotalExpedido.setEditable(false);
			tfTotalExpedido.setColumns(10);
			tfTotalExpedido.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tfTotalExpedido.setBounds(152, 116, 122, 20);
			pOS.add(tfTotalExpedido);

			TamanhoMaxTextField TamanhoMax = new TamanhoMaxTextField();
			TamanhoMax.setMaxChars(7);

			tfTotalExpedido.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent ev) {

					String caracteres = "0987654321,.-+";

					if (!caracteres.contains(ev.getKeyChar() + "")) {

						ev.consume();

					}

				}

			});
			
			tfTotalExpedido.setDocument(TamanhoMax);

			JLabel lblMotivo = new JLabel("Observa\u00E7\u00F5es");
			lblMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			lblMotivo.setBounds(293, 49, 181, 21);
			pOS.add(lblMotivo);
			
			{

				JScrollPane spDetalhesLayout = new JScrollPane();
				spDetalhesLayout.setBorder(new LineBorder(Color.LIGHT_GRAY));
				spDetalhesLayout.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				spDetalhesLayout.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				spDetalhesLayout.setBounds(18, 282, 456, 139);
				pOS.add(spDetalhesLayout);

				tpDetalhesOS = new JTextPane();
				tpDetalhesOS.setForeground(Color.BLACK);
				tpDetalhesOS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				spDetalhesLayout.setViewportView(tpDetalhesOS);
				tpDetalhesOS.setEditable(false);
				tpDetalhesOS.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));

				JScrollPane spMotivo = new JScrollPane();
				spMotivo.setBorder(new LineBorder(Color.LIGHT_GRAY));
				spMotivo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				spMotivo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				spMotivo.setBounds(293, 70, 300, 110);
				pOS.add(spMotivo);

				tpMotivo = new JTextPane();
				tpMotivo.setForeground(Color.BLACK);
				tpMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				tpMotivo.setEditable(false);
				spMotivo.setViewportView(tpMotivo);
				tpMotivo.setBorder(new LineBorder(Color.LIGHT_GRAY));
						
			JLabel lblOSLogo = new JLabel("");
			lblOSLogo.setHorizontalAlignment(SwingConstants.CENTER);
			lblOSLogo.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/icone-servicos-oito.png")));
			lblOSLogo.setBounds(476, 282, 118, 89);
			pOS.add(lblOSLogo);

			lblEntrega = new JLabel("Entrega");
			lblEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			lblEntrega.setBounds(18, 141, 46, 14);
			pOS.add(lblEntrega);

			cbEntrega = new JComboBox<String>();
			cbEntrega.setVisible(false);
			cbEntrega.setAutoscrolls(true);
			cbEntrega.setForeground(Color.BLACK);
			cbEntrega.setModel(new DefaultComboBoxModel<String>(new String[] { "SELECIONE...", "SIM", "N\u00C3O" }));
			cbEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			cbEntrega.setBounds(18, 160, 114, 20);
			pOS.add(cbEntrega);

			tfEntrega = new JTextField();
			tfEntrega.setForeground(Color.BLACK);
			tfEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfEntrega.setEditable(false);
			tfEntrega.setColumns(10);
			tfEntrega.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tfEntrega.setBackground(Color.WHITE);
			tfEntrega.setBounds(18, 160, 115, 20);
			pOS.add(tfEntrega);

			lblOS = new JLabel("");
			lblOS.setForeground(Color.BLACK);
			lblOS.setBounds(25, 0, 562, 52);
			pOS.add(lblOS);
			lblOS.setHorizontalAlignment(SwingConstants.CENTER);
			lblOS.setFont(new Font("Segoe UI", Font.BOLD, 18));

			cbUnidade = new JComboBox<String>();
			cbUnidade.setVisible(false);
			cbUnidade.setForeground(Color.BLACK);
			cbUnidade.setModel(new DefaultComboBoxModel<String>(new String[] { "SELECIONE...", "PE", "JP" }));
			cbUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			cbUnidade.setAutoscrolls(true);
			cbUnidade.setBounds(152, 160, 122, 20);
			pOS.add(cbUnidade);

			tfUnidade = new JTextField();
			tfUnidade.setForeground(Color.BLACK);
			tfUnidade.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			tfUnidade.setEditable(false);
			tfUnidade.setColumns(10);
			tfUnidade.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tfUnidade.setBackground(Color.WHITE);
			tfUnidade.setBounds(152, 160, 122, 20);
			pOS.add(tfUnidade);

			JLabel Unidade = new JLabel("Unidade");
			Unidade.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			Unidade.setBounds(150, 141, 46, 14);
			pOS.add(Unidade);

			dtRecebimento = new JTextField();
			dtRecebimento.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			dtRecebimento.setBounds(92, 70, 86, 20);
			pOS.add(dtRecebimento);
			dtRecebimento.setColumns(10);

			dtPrevista = new JTextField();
			dtPrevista.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			dtPrevista.setColumns(10);
			dtPrevista.setBounds(190, 70, 86, 20);
			pOS.add(dtPrevista);
			
			btnImprimirOS = new JButton("Imprimir OS");
			btnImprimirOS.setVisible(false);
			btnImprimirOS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					listaComprovanteEntrega.clear();
					
					DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
					
					Relatorio relatorio = new Relatorio();
								
					ComprovanteDeEntrega comprovanteDeEntrega = new ComprovanteDeEntrega();
					
					comprovanteDeEntrega.setIdOs(tfOS.getText());
					comprovanteDeEntrega.setDataDeRecebimento(dtRecebimento.getText());
					comprovanteDeEntrega.setTotalFinalDoPedido(tfTotalExpedido.getText());
					comprovanteDeEntrega.setEntragamos(tfEntrega.getText());
					comprovanteDeEntrega.setUnidade(tfUnidade.getText());
					comprovanteDeEntrega.setCliente(tfClienteOS.getText());
					comprovanteDeEntrega.setContatoEmailFone(tfContatoOS.getText() + " " + tfEmailOS.getText().toLowerCase() + " " + tfFone1OS.getText() + " / " + tfFone2OS.getText() );
					comprovanteDeEntrega.setVendedor(tfVendedorOS.getText());
					comprovanteDeEntrega.setObservaçõesDoPedido(tpMotivo.getText());
					comprovanteDeEntrega.setDetalhesdoLayout(tpDetalhesOS.getText());
					
					EstadoOS estadoOS = new EstadoOS();
					EstadoOSRN estadoOSRN = new EstadoOSRN();
					int estagio = estadoOSRN.buscarEstagio(Integer.valueOf(tfOS.getText()));
					Date dataDoEstagio = estadoOSRN.buscarDataDoEstagio(Integer.valueOf(tfOS.getText()), estagio);
					
					comprovanteDeEntrega.setDataExpedição(formatBR.format(dataDoEstagio.getTime()));
					
					comprovanteDeEntrega.setPortador(TelaLogin.getOperador().getNome().toUpperCase());
										
					listaComprovanteEntrega.add(comprovanteDeEntrega);
					listaComprovanteEntrega.add(comprovanteDeEntrega);
										
					if (listaComprovanteEntrega == null || listaComprovanteEntrega.isEmpty() == true) {
						
						JOptionPane.showMessageDialog(null, "Erro, lista vazia!", "Comprovante de Entrega", JOptionPane.INFORMATION_MESSAGE);
						
					} else {
						
						relatorio.gerarComprovanteDeEntrega();
						
					}
					
				}
			});
			btnImprimirOS.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			btnImprimirOS.setBounds(484, 395, 110, 26);
			pOS.add(btnImprimirOS);
		}

		btnIniciar = new JButton("Iniciar");
		btnIniciar.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/iniciar.png")));
		btnIniciar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				OrdemServicoRN osRN = new OrdemServicoRN();
				
				int resposta = JOptionPane.showConfirmDialog(null, "Deseja avançar a " + tfMensagem.getText() + "?",
						"Estágio da Produção", JOptionPane.OK_CANCEL_OPTION);

				if (resposta == JOptionPane.OK_OPTION) {

					int id_os = Integer.valueOf(tfOS.getText());

					estagio = estadoOSRN.buscarEstagio(id_os);

					if (estagio == 0) {

						estagio = 1;

					}

					if (estagio == 2 && tfTotalExpedido.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Preencha primeiro o Total Expedido\n e/ou a observação!");

						btnTotalExpedido.setEnabled(false);
						btnEditarOS.setEnabled(false);
						btnIniciar.setEnabled(false);

						tfTotalExpedido.setEditable(true);
						tpMotivo.setEditable(true);

						btnSalvar.setVisible(true);
						btnCancelar.setVisible(true);

					} else if (estagio <= 2) {

						int proximoEstado = 0;
						
						if(estagio == 3) {
														
							proximoEstado = Integer.valueOf(estagio) + 2;
						
						} else {
							
							proximoEstado = Integer.valueOf(estagio) + 1;
						}

						Date hoje = new Date(System.currentTimeMillis());
						Time hora = new Time(System.currentTimeMillis());
 
						estadoOS.setId_os(id_os);
						estadoOS.setEstagio(proximoEstado);
						estadoOS.setDataInicial(hoje);
						estadoOS.setHoraInicial(hora);
						estadoOS.setOperador(String.valueOf(TelaLogin.getOperador().getNome()));

						estadoOSRN.criarEstadoOS(estadoOS);

						estagio = estadoOSRN.buscarEstagio(id_os);
					

						if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("CRACHÁS / IMPRESSÃO")) {

							mudançaEstagiosOperadorImpressao(estagio);

						} else if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("CRACHÁS / LAMINAÇÃO")) {

							mudançaEstagiosOperadorLaminacao(estagio);

						} else if (TelaLogin.getOperador().getAcesso().equalsIgnoreCase("OBSERVADOR")) {

							mudançaEstagiosObservador(estagio);

						} else if (TelaLogin.getOperador().getSetor().equalsIgnoreCase("RECEPÇÃO")) {

							mudançaEstagiosRecepcionista(estagio);

						}

						String mensagem = String.valueOf(estagio);

						mensagem = estagioOS(mensagem);

						tfMensagem.setText(mensagem);

						osRN.atualizarStatus(id_os, mensagem);
												
						
					} if (estagio == 3) {
						
						br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail();

						int resposta2 = JOptionPane.showConfirmDialog(null,
								"Deseja enviar um e-mail de pedido pronto?", "Pedido pronto",
								JOptionPane.YES_NO_OPTION);

						if (resposta2 == JOptionPane.YES_OPTION) {

							Email email = new Email();
							
							Cliente cliente = new Cliente();
							ClienteRN clienteRN = new ClienteRN();
							
							setOs(osRN.mostrarOS(id_os));
							
							cliente = clienteRN.mostrarCliente(getOs().getId_cliente());
													

							try {

								try {
									email.pedidoPronto(cliente, getOs());
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (EmailException e1) {

								JOptionPane.showMessageDialog(null,
										"O e-mail não foi enviado! \n" + e1.getMessage());

								try {
									email.avisoEmailNãoEnviado(cliente, e1);
								} catch (EmailException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							}
						} 
						
					} 

				}

			}

		});
		btnIniciar.setBorder(null);
		btnIniciar.setBounds(263, 465, 115, 35);

		getContentPane().add(btnIniciar);

		tfMensagem = new JTextField();
		tfMensagem.setBackground(Color.WHITE);
		tfMensagem.setHorizontalAlignment(SwingConstants.CENTER);
		tfMensagem.setForeground(Color.BLUE);
		tfMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tfMensagem.setEnabled(false);
		tfMensagem.setDisabledTextColor(Color.BLUE);
		tfMensagem.setColumns(10);
		tfMensagem.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfMensagem.setBounds(15, 510, 611, 35);
		getContentPane().add(tfMensagem);

		lblLogado = new JLabel("");
		lblLogado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogado.setBounds(15, 566, 370, 23);

		lblLogado.setText("Logado: " + TelaLogin.getOperador().getNome() + " / " + TelaLogin.getOperador().getSetor());

		getContentPane().add(lblLogado);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/excluir.png")));
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCancelar.setVisible(false);
		btnCancelar.setBorder(null);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tfTotalExpedido.setEditable(false);
				tpMotivo.setEditable(false);

				btnSalvar.setVisible(false);
				btnCancelar.setVisible(false);

				btnTotalExpedido.setEnabled(true);
				btnEditarOS.setEnabled(true);

				if (TelaLogin.getOperador().getSetor().equals("COMERCIAL")
						|| TelaLogin.getOperador().getSetor().equals("ADMINISTRATIVO")) {

					btnIniciar.setVisible(false);

				} else {

					btnIniciar.setVisible(true);

				}

				tfClienteOS.setEditable(false);
				tfVendedorOS.setEditable(false);
				tfContatoOS.setEditable(false);
				tfEmailOS.setEditable(false);
				tfFone1OS.setEditable(false);
				tfFone2OS.setEditable(false);
				tpDetalhesOS.setEditable(false);

				cbEntrega.setVisible(false);
				tfEntrega.setVisible(true);

				tfTotalExpedido.setText("");

			}
		});
		btnCancelar.setBounds(511, 465, 115, 35);
		getContentPane().add(btnCancelar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/salvar.png")));
		btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSalvar.setVisible(false);
		btnSalvar.setBorder(null);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tfTotalExpedido.isEditable() == true) {

					if (tfTotalExpedido.getText().equals("")) {

						JOptionPane.showMessageDialog(null, "Para salvar preencha o total expedido!");

					} else {

						int resposta = JOptionPane.showConfirmDialog(null,
								"Deseja salvar o total expedido e/ou a observação?", "Total Expedido",
								JOptionPane.OK_CANCEL_OPTION);

						if (resposta == JOptionPane.OK_OPTION) {

							OrdemServico os = new OrdemServico();
							OrdemServicoRN osRN = new OrdemServicoRN();
							
							NumberFormat tresDigitos = new DecimalFormat("000");
							
							if (tfTotalExpedido.getText() != null) {
								
								osRN.atualizarTotalFinal(Integer.valueOf(tfOS.getText()), tresDigitos.format(Integer.parseInt( tfTotalExpedido.getText())),
										tpMotivo.getText().toUpperCase());
								
							} else {
								
								osRN.atualizarTotalFinal(Integer.valueOf(tfOS.getText()), tfTotalExpedido.getText(),
										tpMotivo.getText().toUpperCase());
								
								
							}
							
							tfTotalExpedido.setText(tresDigitos.format(Integer.valueOf(tfTotalExpedido.getText())));
							
							btnTotalExpedido.setEnabled(true);
							btnEditarOS.setEnabled(true);

							btnIniciar.setEnabled(true);

							btnSalvar.setVisible(false);
							btnCancelar.setVisible(false);

							tfTotalExpedido.setEditable(false);
							tpMotivo.setEditable(false);

							int id_os = Integer.valueOf(tfOS.getText());

							estagio = estadoOSRN.buscarEstagio(id_os);

							if (estagio == 2) {

								JOptionPane.showMessageDialog(null,
										"Lembre-se de apertar novamente \no botão Laminação!");
							}

						}

					}

				} else {

					if (cbEntrega.getSelectedItem().equals("Selecione...")) {

						JOptionPane.showMessageDialog(null, "Selecione se nosso portador fará a entrega!");

					} else if (cbUnidade.getSelectedItem().equals("Selecione...")) {

						JOptionPane.showMessageDialog(null, "Selecione a unidade da empresa!");

					} else {

						OrdemServico os = new OrdemServico();
						OrdemServicoRN osRN = new OrdemServicoRN();

						ClienteRN clienteRN = new ClienteRN();
						Cliente cliente = new Cliente();

						os = osRN.mostrarOS(Integer.valueOf(tfOS.getText()));

						boolean entrega = false;

						if (cbEntrega.getSelectedItem().equals("Sim")) {

							entrega = true;

						} else {

							entrega = false;

						}

						cliente.setId_cliente(os.getId_cliente());
						cliente.setNome(tfClienteOS.getText().toUpperCase());
						cliente.setVendedor(tfVendedorOS.getText().toUpperCase());
						cliente.setContato(tfContatoOS.getText().toUpperCase());
						cliente.setEmail(tfEmailOS.getText().toLowerCase());
						cliente.setFone1(tfFone1OS.getText());
						cliente.setFone2(tfFone2OS.getText());
						cliente.setDetalhes(tpDetalhesOS.getText());
						cliente.setEntrega(entrega);
						cliente.setUnidade(String.valueOf(cbUnidade.getSelectedItem()));

						clienteRN.editarcliente(cliente);

						tfTotalExpedido.setEditable(false);
						tpMotivo.setEditable(false);

						btnSalvar.setVisible(false);
						btnCancelar.setVisible(false);

						btnEditarOS.setEnabled(true);

						if (TelaLogin.getOperador().getSetor().equals("COMERCIAL")
								|| TelaLogin.getOperador().getSetor().equals("ADMINISTRATIVO")) {

							btnIniciar.setVisible(false);

						} else {

							btnIniciar.setVisible(true);

						}

						btnTotalExpedido.setEnabled(true);

						tfClienteOS.setEditable(false);
						tfVendedorOS.setEditable(false);
						tfContatoOS.setEditable(false);
						tfEmailOS.setEditable(false);
						tfFone1OS.setEditable(false);
						tfFone2OS.setEditable(false);
						tpDetalhesOS.setEditable(false);

						cbEntrega.setVisible(false);
						tfEntrega.setVisible(true);

						cbUnidade.setVisible(false);
						tfUnidade.setVisible(true);

						cliente = clienteRN.mostrarCliente(os.getId_cliente());

						String portador = "";

						if (cliente.isEntrega() == true) {

							portador = "Sim";

						} else {

							portador = "Não";

						}

						tfClienteOS.setText(cliente.getNome());
						tfVendedorOS.setText(String.valueOf(cliente.getVendedor()));
						tfContatoOS.setText(cliente.getContato());
						tfEmailOS.setText(cliente.getEmail());
						tfFone1OS.setText(cliente.getFone1());
						tfFone2OS.setText(cliente.getFone2());
						tpDetalhesOS.setText(cliente.getDetalhes());
						tfEntrega.setText(portador);
						tfUnidade.setText(cliente.getUnidade());

						Producao producao = new Producao();
						ProducaoRN producaoRN = new ProducaoRN();

						producao = producaoRN.pesquisarIDOS(os.getId_os());

						producao.setEmpresa(cliente.getNome());
						producao.setObservacoes(os.getMotivo());

						producaoRN.atualizarProducao(producao);

					}

				}

			}
		});
		btnSalvar.setBounds(387, 465, 115, 35);
		getContentPane().add(btnSalvar);
		btnTotalExpedido = new JButton("Total  e Obs");
		btnTotalExpedido.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/status.png")));
		btnTotalExpedido.setBounds(15, 465, 115, 35);
		getContentPane().add(btnTotalExpedido);
		btnTotalExpedido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTotalExpedido.setBorder(null);

		btnEditarOS = new JButton("Editar OS");
		btnEditarOS.setBounds(139, 465, 115, 35);
		getContentPane().add(btnEditarOS);
		btnEditarOS.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/editar_os.png")));
		btnEditarOS.setBorder(null);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/imprimir.png")));
		btnImprimir.setBounds(395, 555, 108, 35);
		getContentPane().add(btnImprimir);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ImpressaoJpanel imp = new ImpressaoJpanel(pOS);

				PrinterJob job = PrinterJob.getPrinterJob();
				job.setJobName(" Imprimindo Ordem de Serviço ");
				job.setPrintable(imp);
				boolean ok = job.printDialog();
				if (ok) {
					try {
						job.print();
					} catch (PrinterException ex) {
						/* The job did not successfully complete */
						JOptionPane.showMessageDialog(null, "Falha na impressão do documento!");
					}

				}
			}

		});
		btnEditarOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int resposta = JOptionPane.showConfirmDialog(null, "Você deseja editar a OS?", "Editar OS",
						JOptionPane.OK_CANCEL_OPTION);

				if (resposta == JOptionPane.OK_OPTION) {

					btnSalvar.setVisible(true);
					btnCancelar.setVisible(true);

					tfClienteOS.setEditable(true);
					tfVendedorOS.setEditable(true);
					tfContatoOS.setEditable(true);
					tfEmailOS.setEditable(true);
					tfFone1OS.setEditable(true);
					tfFone2OS.setEditable(true);
					tpDetalhesOS.setEditable(true);

					cbEntrega.setVisible(true);
					cbUnidade.setVisible(true);
					tfEntrega.setVisible(false);
					tfUnidade.setVisible(false);

					btnTotalExpedido.setEnabled(false);
					btnEditarOS.setEnabled(false);
					btnIniciar.setEnabled(false);

				}

			}
		});
		btnTotalExpedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int resposta = JOptionPane.showConfirmDialog(null,
						"Você deseja inserir o total expedido e/ou a observação?", "Total Expedido",
						JOptionPane.OK_CANCEL_OPTION);

				if (resposta == JOptionPane.OK_OPTION) {

					btnTotalExpedido.setEnabled(false);
					btnEditarOS.setEnabled(false);
					btnIniciar.setEnabled(false);

					tfTotalExpedido.setEditable(true);
					tpMotivo.setEditable(true);

					btnSalvar.setVisible(true);
					btnCancelar.setVisible(true);

				}
			}
					
		});
		
		
		}
	}

	public static List<OrdemServico> getListOS() {
		return listOS;
	}

	public static void setListOS(List<OrdemServico> listOS) {
		TelaOrdemServico.listOS = listOS;
	}

	public JTextField getDtRecebimento() {
		return dtRecebimento;
	}

	public void setDtRecebimento(JTextField dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}

	public JTextField getDtPrevista() {
		return dtPrevista;
	}

	public void setDtPrevista(JTextField dtPrevista) {
		this.dtPrevista = dtPrevista;
	}

	public JTextField getTfOS() {
		return tfOS;
	}

	public void setTfOS(JTextField tfOS) {
		this.tfOS = tfOS;
	}

	public JTextField getTfClienteOS() {
		return tfClienteOS;
	}

	public void setTfClienteOS(JTextField tfClienteOS) {
		this.tfClienteOS = tfClienteOS;
	}

	public JTextField getTfContatoOS() {
		return tfContatoOS;
	}

	public void setTfContatoOS(JTextField tfContatoOS) {
		this.tfContatoOS = tfContatoOS;
	}

	public JTextField getTfFone1OS() {
		return tfFone1OS;
	}

	public void setTfFone1OS(JTextField tfFone1OS) {
		this.tfFone1OS = tfFone1OS;
	}

	public JTextField getTfFone2OS() {
		return tfFone2OS;
	}

	public void setTfFone2OS(JTextField tfFone2OS) {
		this.tfFone2OS = tfFone2OS;
	}

	public JTextField getTfVendedorOS() {
		return tfVendedorOS;
	}

	public void setTfVendedorOS(JTextField tfVendedorOS) {
		this.tfVendedorOS = tfVendedorOS;
	}

	public JTextField getTfTotal() {
		return tfTotal;
	}

	public void setTfTotal(JTextField tfTotal) {
		this.tfTotal = tfTotal;
	}

	public JTextField getTfEmailOS() {
		return tfEmailOS;
	}

	public void setTfEmailOS(JTextField tfEmailOS) {
		this.tfEmailOS = tfEmailOS;
	}

	public JTextPane getTpDetalhesOS() {
		return tpDetalhesOS;
	}

	public void setTpDetalhesOS(JTextPane tpDetalhesOS) {
		this.tpDetalhesOS = tpDetalhesOS;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public void setBtnIniciar(JButton btnIniciar) {
		this.btnIniciar = btnIniciar;
	}

	public void mudançaEstagiosOperadorImpressao(int estagio) {

		switch (estagio) {

		case 1:

			btnEditarOS.setVisible(true);
			btnTotalExpedido.setVisible(true);
			btnIniciar.setVisible(true);
			btnIniciar.setText("Impressão");
			btnIniciar.setIcon(new ImageIcon(TelaOrdemServico.class.getResource("/br/com/crachas/image/iniciar.png")));

			btnImprimirOS.setVisible(false);
			
			break;

		case 2:

			btnEditarOS.setVisible(true);
			btnTotalExpedido.setVisible(true);
			btnIniciar.setVisible(true);
			btnIniciar.setText("Laminação");
			
			btnImprimirOS.setVisible(false);

			break;

		case 3:

			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 4:

			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 5:

			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 6:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 7:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		}

	}

	public void mudançaEstagiosOperadorLaminacao(int estagio) {

		switch (estagio) {

		/*case 0:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;*/

		case 1:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 2:

			btnTotalExpedido.setVisible(true);
			btnEditarOS.setVisible(true);
			btnIniciar.setVisible(true);
			btnIniciar.setText("Laminação");
				
			btnImprimirOS.setVisible(false);

			break;

		case 3:

			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 4:

			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 5:

			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			btnIniciar.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 6:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 7:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		}

	}

	public void mudançaEstagiosRecepcionista(int estagio) {

		switch (estagio) {

		/*case 0:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;*/

		case 1:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 2:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 3:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 4:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(true);

			break;

		case 5:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 6:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		case 7:

			btnIniciar.setVisible(false);
			btnTotalExpedido.setVisible(false);
			btnEditarOS.setVisible(false);
			
			btnImprimirOS.setVisible(false);

			break;

		}

	}

	public void mudançaEstagiosObservador(int estagio) {

		switch (estagio) {

		/*case 0:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;*/

		case 1:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;

		case 2:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;

		case 3:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(true);

			break;

		case 4:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(true);

			break;

		case 5:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;

		case 6:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;

		case 7:

			btnIniciar.setVisible(false);
			btnEditarOS.setVisible(false);
			btnTotalExpedido.setVisible(false);

			btnEditarOS.setVisible(true);
			
			btnImprimirOS.setVisible(false);

			break;

		}

	}

	public static String estagioOS(String mensagem) {

		switch (mensagem)

		{

		/*case "0":

			mensagem = "PEDIDO RECEBIDO";

			break;*/

		case "1":

			mensagem = "IMPRESSÃO";

			break;

		case "2":

			mensagem = "LAMINAÇÃO / EXPEDIÇÃO";

			break;
			
		case "3":

			mensagem = "ENTREGAR";

			break;

				
		case "4":

			mensagem = "ENTREGAR";

			break;

		case "5":

			mensagem = "PEDIDO FINALIZADO";

			break;

		case "6":

			mensagem = "AGUARDANDO O CLIENTE";

			break;

		case "7":

			mensagem = "ORDEM DE SERVIÇO CANCELADA";

			break;

		}
		return mensagem;

	}

	public JTextField getTfStatusAtual() {
		return tfMensagem;
	}

	public void setTfStatusAtual(JTextField tfStatusAtual) {
		this.tfMensagem = tfStatusAtual;
	}

	public JTextField getTfMensagem() {
		return tfMensagem;
	}

	public void setTfMensagem(JTextField tfMensagem) {
		this.tfMensagem = tfMensagem;
	}

	public int getEstagio() {
		return estagio;
	}

	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}

	public JTextField getTfTotalExpedido() {
		return tfTotalExpedido;
	}

	public void setTfTotalExpedido(JTextField tfTotalExpedido) {
		this.tfTotalExpedido = tfTotalExpedido;
	}

	public JTextPane getTpMotivo() {
		return tpMotivo;
	}

	public void setTpMotivo(JTextPane tpMotivo) {
		this.tpMotivo = tpMotivo;
	}

	public JComboBox<String> getCbEntrega() {
		return cbEntrega;
	}

	public void setCbEntrega(JComboBox<String> cbEntrega) {
		this.cbEntrega = cbEntrega;
	}

	public JTextField getTfEntrega() {
		return tfEntrega;
	}

	public void setTfEntrega(JTextField tfEntrega) {
		this.tfEntrega = tfEntrega;
	}

	public JComboBox<String> getCbUnidade() {
		return cbUnidade;
	}

	public void setCbUnidade(JComboBox<String> cbUnidade) {
		this.cbUnidade = cbUnidade;
	}

	public JTextField getTfUnidade() {
		return tfUnidade;
	}

	public void setTfUnidade(JTextField tfUnidade) {
		this.tfUnidade = tfUnidade;
	}
	
	public JLabel getLblOS() {
		return lblOS;
	}

	public void setLblOS(JLabel lblOS) {
		this.lblOS = lblOS;
	}

	public static OrdemServico getOs() {
		return os;
	}

	public static void setOs(OrdemServico os) {
		TelaOrdemServico.os = os;
	}

	public static List<ComprovanteDeEntrega> getListaComprovanteEntrega() {
		return listaComprovanteEntrega;
	}

	public static void setListaComprovanteEntrega(List<ComprovanteDeEntrega> listaComprovanteEntrega) {
		TelaOrdemServico.listaComprovanteEntrega = listaComprovanteEntrega;
	}
}
