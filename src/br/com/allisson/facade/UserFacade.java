package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.EmpresaDAO;
import br.com.allisson.dao.UserDAO;
import br.com.allisson.modelo.Empresa;
import br.com.allisson.modelo.Mensagem;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;
import br.com.allisson.util.EmailUtils;
import br.com.allisson.util.Geral;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String login, String senha){
		userDAO.beginTransaction();
		User user = userDAO.findUser(login);
		
		if (user == null || !user.getSenha().equals(senha)){
			return null;
		}
		else
			return user;
		
	}
	
	public void createUser(User user){
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commit();
		
		
		List<String> corpoEmail = new ArrayList<String>();
		
		
		if (!user.getEmail().equals("")) {
			
			
			EmpresaDAO empresaDao = new EmpresaDAO();
			Empresa empresa = new Empresa();
			
			empresa = empresaDao.DadosTransportadora();
			

			Mensagem msgCliente = new Mensagem();

			msgCliente.setTitulo("Portal de Servi�os Web. Cadastro Efetuado.");
			msgCliente.setDestino(user.getEmail());

			corpoEmail.clear();
			
			
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			corpoEmail.add("<div align='center'>");       
			
			String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
			
			corpoEmail.add("<img src="+imgHttp+">");  
			corpoEmail.add("</div>");  

			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<p>Prezado(a),"+user.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este � um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("Voc� efetuou com sucesso o seu cadastro em nosso Portal!");                   
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<strong>AGUARDE A LIBERA��O DE SEU ACESSO.</strong><br/>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>**** FAVOR N�O RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  
            
			msgCliente
					.setMensagens(corpoEmail);
			
				  
			try {
				// Executa aqui o codigo perigoso, que d� exce��o
				EmailUtils.enviaEmailHtml(msgCliente);
			} catch (Exception e) {
				// Depois grava no contexto do Faces

			}
			
			
			
		}
		
		
		if (ContaEmailFacade.getDadosContaEmail()!=null){
			Mensagem msgAdmin = new Mensagem();
			
			msgAdmin.setTitulo("Conta criada com sucesso");
			msgAdmin.setDestino(ContaEmailFacade.getDadosContaEmail().getEmailadmin());
			
			corpoEmail.clear();
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<p>Prezado(a),</p>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<strong>Cliente: </strong>"+user.getCliente().getNome()+"<br/>");  
			corpoEmail.add("<strong>Login  : </strong>"+user.getLogin()+"<br/>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Efetuou cadastro no Portal Web</center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>"); 
			
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Para autorizar agora mesmo. <a href="+Geral.getCaminhoURL()+"pages/public/autorizarAcessoURL.jsf?"+Criptografia.md5("idUser")+"="+Criptografia.criptografa(String.valueOf(user.getId()), 25)+">Autorizar</a></center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			
			      
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			msgAdmin.setMensagens(corpoEmail);
			
			
			try {
				// Executa aqui o codigo perigoso, que d� exce��o
				EmailUtils.enviaEmailHtml(msgAdmin);
			} catch (Exception e) {
				// Depois grava no contexto do Faces

			}

		}
		
			
		
		
		
		
	}
	
	public boolean isExists(String login){
		userDAO.beginTransaction();
		
		User user = userDAO.findUser(login);
		
		if (user == null){
			return false;
		}
		else
			return true;
		
	}
	
	public User userPorEmail(String email) {
		
		userDAO.beginTransaction();
		User user = userDAO.findUserEmail(email);
		userDAO.closeTransaction();
		
		return user;
	}
	
	public List<User> listAll(){
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllUser();
		userDAO.closeTransaction();
		return result;
	}
	
	public List<User> listAllNaoAutorizados() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllUserNaoAutorizados();
		userDAO.closeTransaction();
		return result;
	}

	public User findUser(int id){
		userDAO.beginTransaction();
		User result = userDAO.find(id);
		userDAO.closeTransaction();
		return result;
	}
	
	
	public void deleteUser(User user){
		
		System.out.println(user.getLogin());
		
		userDAO.beginTransaction();
		User persistedUser = userDAO.findReferenceOnly(user.getId());
		userDAO.delete(persistedUser);
		userDAO.commitAndCloseTransaction();
	
	}
	
	public void updateUser(User user){
		userDAO.beginTransaction();
		userDAO.update(user);
		
		userDAO.commitAndCloseTransaction();
	}
	
	
	public void autorizaUsuario(User usuario){
		usuario.setAcesso_autorizado(true);
		this.updateUser(usuario);
		this.enviarEmailAcessoAutorizado(usuario);
	}
	
	
	public void enviarEmailAcessoAutorizado(User usuario) {

		EmpresaDAO empresaDao = new EmpresaDAO();
		Empresa empresa = new Empresa();

		empresa = empresaDao.DadosTransportadora();

		if (!usuario.getEmail().equals("")) {
			Mensagem msgAdmin = new Mensagem();

			msgAdmin.setTitulo("Acesso ao Portal Web");
			msgAdmin.setDestino(usuario.getEmail());

			List<String> corpoEmail = new ArrayList<String>();
			
			corpoEmail.clear();
			
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			corpoEmail.add("<div align='center'>");                      
            
			String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
			
			corpoEmail.add("<img src="+imgHttp+">");
			
			corpoEmail.add("</div>");  

			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<p>Prezado(a),"+usuario.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este � um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");  
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("Informamos que seu usu�rio: " + usuario.getLogin()
					+ " foi autorizado a acessar nosso Portal Web");
			corpoEmail.add("</div>");  
			                   
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Para acessar agora mesmo <a href="+Geral.getCaminhoURL()+">Acesse</a></center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>**** FAVOR N�O RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			
			msgAdmin.setMensagens(corpoEmail);

			EmailUtils.enviaEmailHtml(msgAdmin);

			
		}

	}
	
	public void enviarEmailRecuperarSenha(User usuario) {
		
		Mensagem msgAdmin = new Mensagem();

		msgAdmin.setTitulo("Cria��o de nova senha");
		//msgAdmin.setDestino(usuario.getEmail());
		msgAdmin.setDestino("felipe@allisson.com.br");
		
		List<String> corpoEmail = new ArrayList<String>();
		
		corpoEmail.clear();
		
		corpoEmail.add("<html>");
		corpoEmail.add("<head>");
		corpoEmail.add("<style type='text/css'>");
		corpoEmail.add("#div {");
		corpoEmail.add("	width: 850px; /* Tamanho da Largura da Div */");
		corpoEmail.add("	height: 200px; /* Tamanho da Altura da Div */");
		corpoEmail.add("	position: absolute;");
		corpoEmail.add("	top: 50%;");
		corpoEmail.add("	margin-top: -400px;");
		
		corpoEmail.add("	left: 20%;");
		corpoEmail.add("	margin-left: -250px;");
			
		corpoEmail.add("	;");
		corpoEmail.add("}");
		corpoEmail.add("</style>");
		corpoEmail.add("</head>");
		corpoEmail.add("<body  bgcolor='#EBEBEB'>");
		corpoEmail.add("  <div id='div'>");		
		corpoEmail.add("       <div align='left'>");
		
		String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
		
		corpoEmail.add("            <img src="+imgHttp+" height='170' width='200'>");
		            
		corpoEmail.add("            </div>");

		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("            <p>Prezado(a),Cliente</p>");
		corpoEmail.add("            <p>Este � um email de <strong>Transportadora</strong>.</p>");
		corpoEmail.add("            </div>");
		            
		            
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Voc� nos informou que esqueceu sua senha de acesso ao site www.portal.br");
		corpoEmail.add("            </div>");
		corpoEmail.add("	    <br/>");
		corpoEmail.add("	    <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Use o link a seguir nas pr�ximas horas para criar uma nova senha:");	
		corpoEmail.add("            </div>");
		corpoEmail.add("	    <br/>");
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("		link para trocar de senha");
		corpoEmail.add("            </div>");
		                               
		            
		corpoEmail.add("	    <br/>");
		            
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Se n�o foi voc� que solicitou a cria��o de uma nova senha, n�o se preocupe, apenas ignore esta mensagem.");
		corpoEmail.add("            </div>");
		corpoEmail.add("            <br/>");
		corpoEmail.add("	    Obrigado.");	
		corpoEmail.add("            <br/>");
		corpoEmail.add("            <br/>");
		            
		            
		corpoEmail.add("            <div colspan='1' height='30' align='center'>");
		corpoEmail.add("            <b><center>**** FAVOR N�O RETORNAR ESTE EMAIL ****</center></b>");
		corpoEmail.add("            </div>");

		corpoEmail.add("	</div>");                  
		corpoEmail.add("            </body>");
		corpoEmail.add("            </html>");
		
		msgAdmin.setMensagens(corpoEmail);

		EmailUtils.enviaEmailHtml(msgAdmin);
		
	}
	
	
	

}



