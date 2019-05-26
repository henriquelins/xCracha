package br.com.crachas.controller;

public class ServidorEmail {
	
	private int id_ServidorEmail;
	private String nomeServidor;
	private String hostName;
	private int smtpPort;
	private String authentication;
	
	public ServidorEmail() {};
	
	public ServidorEmail(int id_ServidorEmail, String nomeServidor, String hostName, int smtpPort,
			String authentication) {
		
		this.id_ServidorEmail = id_ServidorEmail;
		this.nomeServidor = nomeServidor;
		this.hostName = hostName;
		this.smtpPort = smtpPort;
		this.authentication = authentication;
	}

	public int getId_ServidorEmail() {
		return id_ServidorEmail;
	}

	public void setId_ServidorEmail(int id_ServidorEmail) {
		this.id_ServidorEmail = id_ServidorEmail;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServidorEmail [id_ServidorEmail=").append(id_ServidorEmail).append(", nomeServidor=")
				.append(nomeServidor).append(", hostName=").append(hostName).append(", smtpPort=").append(smtpPort)
				.append(", authentication=").append(authentication).append("]");
		return builder.toString();
	}
	
	
}
