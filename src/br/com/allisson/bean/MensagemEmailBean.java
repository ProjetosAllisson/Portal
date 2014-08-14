package br.com.allisson.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.mail.EmailException;

import br.com.allisson.modelo.Mensagem;
import br.com.allisson.util.EmailUtils;

@ManagedBean(name = "mensagemEmailBean")
@RequestScoped
public class MensagemEmailBean {
	
	private Mensagem msgEmail;

	public Mensagem getMsgEmail() {
		return msgEmail;
	}

	public void setMsgEmail(Mensagem msgEmail) {
		this.msgEmail = msgEmail;
	}
	
	public void enviarEmail() throws EmailException{
		
		Mensagem msg = new Mensagem();
		
		msg.setDestino("felipe@allisson.com.br");
		//msg.setMensagem("Teste envio de e-mail");
		msg.setTitulo("Portal Web");
		
		EmailUtils.enviarEmail(msg);
		
		
		
	}

}
