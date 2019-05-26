package br.com.crachas.controller;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Email {

	private int id_email;
	private String email;
	private String descricao;
	private String endereco_imagem;

	public Email() {
	}

	public Email(int id_email, String email, String descricao, String endereco_imagem) {

		this.id_email = id_email;
		this.email = email;
		this.descricao = descricao;
		this.endereco_imagem = endereco_imagem;

	}

	public int getId_email() {
		return id_email;
	}

	public void setId_email(int id_email) {
		this.id_email = id_email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco_imagem() {
		return endereco_imagem;
	}

	public void setEndereco_imagem(String endereco_imagem) {
		this.endereco_imagem = endereco_imagem;
	}

	public void runPedidoPronto(Cliente cliente, OrdemServico os) throws FileNotFoundException {

		try {

			try {

				pedidoPronto(cliente, os);

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		} catch (EmailException e) {

			e.printStackTrace();

			try {

				avisoEmailNãoEnviado(cliente, e);

			} catch (EmailException e1) {

				e1.printStackTrace();

			}

		}

	}

	public void runPedidoRecebido(Cliente cliente) throws FileNotFoundException {

		try {

			pedidoRecebido(cliente);

		} catch (EmailException e) {

			e.printStackTrace();

			try {

				avisoEmailNãoEnviado(cliente, e);

			} catch (EmailException e1) {

				e1.printStackTrace();

			}

		}

	}

	public void pedidoPronto(Cliente cliente, OrdemServico os) throws EmailException, FileNotFoundException {

		new Thread() {

			@Override
			public void run() {

				// email html
				HtmlEmail email = new HtmlEmail();
				// host de envio smtp
				email.setHostName("smtp.uhserver.com");
				// porta smtp
				email.setSmtpPort(587);
				// login e senha do e-mail
				email.setAuthentication(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail(), "!.Nesolution.!");
				// seu e-mail e nome
				try {
					email.setFrom(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail(),
							br.com.crachas.controller.IniciarAplicativoCrachas.email.getDescricao());
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// assunto do e-mail
				email.setSubject("Prezado Cliente " + cliente.getContato());
				// mensagem

				String id = null;
				try {
					id = email.embed(new File(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEndereco_imagem()));
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// imagem

				StringBuilder builder = new StringBuilder();
				builder.append("<h1><font color=00044E>Seu pedido está pronto!</h1></ font>");
				builder.append("<h1></h1>");
				builder.append("<p><font color=00044E>O pedido de crachás da empresa <b>" + cliente.getNome()
						+ "</b> está pronto, total de " + os.getTotalFinal() + " crachá(s)!</ font><p>");

				if (cliente.getUnidade().equalsIgnoreCase("JP")) {

					String vendedor = cliente.getVendedor();

					if (vendedor.equalsIgnoreCase("Tiago Silva")) {

						try {
							email.addTo("comercialjp2@nesolution.com.br", "Tiago");
						} catch (EmailException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					} else if (vendedor.equalsIgnoreCase("Pierre")) {

						try {
							email.addTo("andrepierrepb.ne@gmail.com", "Pierre");
						} catch (EmailException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					} else {

						try {
							email.addTo("comercialjp@nesolution.com.br", "Valdilene");
						} catch (EmailException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					}

				} else {

					if (cliente.isEntrega() == true) {

						builder.append("<p><font color=00044E>Nosso portador fará a entrega em breve!</ font><p>");

					} else {

						builder.append(
								"<p><font color=00044E>Seu portador já pode retirar seu pedido na recepção!</ font><p>");
					}

					builder.append("<html> - <img src=\"cid:" + id + "\"></html>");

					try {
						email.setHtmlMsg(builder.toString());
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// e-mail a ser enviado e nome
					try {
						email.addTo(cliente.getEmail(), cliente.getNome());
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						email.addCc(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail(),
								br.com.crachas.controller.IniciarAplicativoCrachas.email.getDescricao());
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// enviar
				try {
					email.send();
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "E-mail de pedido finalizado enviado com sucesso!");

			}

		}.start();

	}

	public void avisoEmailNãoEnviado(Cliente cliente, EmailException e1) throws EmailException {

		new Thread() {

			@Override
			public void run() {

				// email html
				HtmlEmail email = new HtmlEmail();
				// host de envio smtp
				email.setHostName("smtp.uhserver.com");
				// porta smtp
				email.setSmtpPort(587);
				// login e senha do e-mail
				email.setAuthentication("crachas@nesolution.com.br", "!.Nesolution.!");
				// seu e-mail e nome
				try {
					email.setFrom("crachas@nesolution.com.br", "Crachás NESolution");
				} catch (EmailException e) {

					e.printStackTrace();
				}
				// assunto do e-mail
				email.setSubject("Bom dia " + br.com.crachas.controller.IniciarAplicativoCrachas.email.getDescricao() + " O email do cliente "
						+ cliente.getContato());
				// mensagem

				String id = "";
				try {
					id = email.embed(new File(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEndereco_imagem()));
				} catch (EmailException e) {
					e.printStackTrace();
				}

				StringBuilder builder = new StringBuilder();
				builder.append("<h1><font color=00044E>Email não enviado!</h1></ font>");
				builder.append("<h1></h1>");
				builder.append("<p><font color=00044E>O email não pôde ser enviado ao cliente <b>" + cliente.getNome()
						+ "</b>, pois seu email está inválido ou vazio!</ font><p>");

				builder.append(
						"<p><font color=00044E>Por gentileza verificar e enviar o e-mail manualmente!</ font><p>");

				builder.append("<p><font color=00044E>Erro: " + e1 + "!</ font><p>");

				builder.append("<html> - <img src=\"cid:" + id + "\"></html>");

				try {

					email.setHtmlMsg(builder.toString());

					// e-mail a ser enviado e nome

					email.addCc("crachas@nesolution.com.br", "Crachas NESolution");

					// enviar
					email.send();

					JOptionPane.showMessageDialog(null, "E-mail de aviso enviado \npara o setor de crachás!");

				} catch (EmailException e) {

					e.printStackTrace();
				}

			}

		}.start();

	}

	public void pedidoRecebido(Cliente cliente) throws EmailException {

		new Thread() {

			@Override
			public void run() {

				// email html
				HtmlEmail email = new HtmlEmail();
				// host de envio smtp
				email.setHostName("smtp.uhserver.com");
				// porta smtp
				email.setSmtpPort(587);
				// login e senha do e-mail
				email.setAuthentication(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail(), "!.Nesolution.!");
				// seu e-mail e nome
				try {
					email.setFrom(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEmail(),
							br.com.crachas.controller.IniciarAplicativoCrachas.email.getDescricao());
				} catch (EmailException e) {

					e.printStackTrace();
				}
				// assunto do e-mail
				email.setSubject("Prezado Cliente " + cliente.getContato());
				// mensagem

				String id = null;
				try {
					id = email.embed(new File(br.com.crachas.controller.IniciarAplicativoCrachas.email.getEndereco_imagem()));
				} catch (EmailException e) {

					e.printStackTrace();
				}

				StringBuilder builder = new StringBuilder();
				builder.append("<h1><font color=00044E>Seu pedido foi recebido!</h1></ font>");
				builder.append("<h1></h1>");
				builder.append("<p><font color=00044E>O pedido da empresa <b>" + cliente.getNome()
						+ "</b> foi recebido e está em análise, caso haja algum problema entraremos em contato!</ font><p>");

				builder.append(
						"<p><font color=00044E>Assim que o pedido estiver pronto enviaremos um e-mail confirmando!</ font><p>");

				builder.append("<html> - <img src=\"cid:" + id + "\"></html>");

				try {
					email.setHtmlMsg(builder.toString());
				} catch (EmailException e) {

					e.printStackTrace();
				}

				if (cliente.getUnidade().equals("")) {

					JOptionPane.showMessageDialog(null,
							"Não foi possível enviar o e-mail! \nCadastre a Unidade da Empresa!");

					try {
						avisoEmailNãoEnviado(cliente, null);
					} catch (EmailException e) {

						e.printStackTrace();
					}

				} else

				if (cliente.getUnidade().equalsIgnoreCase("JP")) {

					String vendedor = cliente.getVendedor();

					if (vendedor.equalsIgnoreCase("Tiago Silva")) {

						try {
							email.addTo("comercialjp2@nesolution.com.br", "Tiago Silva");
						} catch (EmailException e) {

							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					} else if (vendedor.equalsIgnoreCase("Pierre")) {

						try {
							email.addTo("andrepierrepb.ne@gmail.com", "Pierre");
						} catch (EmailException e) {

							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					} else {

						try {
							email.addTo("comercialjp@nesolution.com.br", "Valdilene");
						} catch (EmailException e) {

							e.printStackTrace();
						}
						builder.append("<p><font color=00044E>Seu pedido será entrega em breve!</ font><p>");

					}

				} else if (cliente.getUnidade().equalsIgnoreCase("PE")) {

					// e-mail a ser enviado e nome
					try {
						email.addTo(cliente.getEmail(), cliente.getContato());
					} catch (EmailException e) {

						e.printStackTrace();
					}

				}

				// enviar
				try {
					email.send();
				} catch (EmailException e) {

					e.printStackTrace();
				}

				// Mensagem de envio correto
				 JOptionPane.showMessageDialog(null, "E-mail de recebimento de pedido\n enviado com sucesso!");

			}

		}.start();

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Email [email=").append(email).append(", descricao=").append(descricao)
				.append(", endereco_imagem=").append(endereco_imagem).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco_imagem == null) ? 0 : endereco_imagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco_imagem == null) {
			if (other.endereco_imagem != null)
				return false;
		} else if (!endereco_imagem.equals(other.endereco_imagem))
			return false;
		return true;
	}

}
