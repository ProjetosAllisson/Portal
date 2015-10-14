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

			msgCliente.setTitulo("Portal de Serviços Web. Cadastro Efetuado.");
			msgCliente.setDestino(user.getEmail());

			corpoEmail.clear();
			
			
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			corpoEmail.add("<div align='center'>");       
			
			String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
			
			corpoEmail.add("<img src="+imgHttp+" height='100' width='150'>");  
			corpoEmail.add("</div>");  

			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<p>Prezado(a),"+user.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este é um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("Você efetuou com sucesso o seu cadastro em nosso Portal!");                   
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<strong>AGUARDE A LIBERAÇÃO DE SEU ACESSO.</strong><br/>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>**** FAVOR NÃO RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  
            
			msgCliente
					.setMensagens(corpoEmail);
			
				  
			try {
				// Executa aqui o codigo perigoso, que dá exceção
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
			corpoEmail.add("<strong>O cliente: </strong>"+user.getCliente().getCgc()+" - "+ user.getCliente().getNome()+"<br/>");  
			corpoEmail.add("</div>");
			
			corpoEmail.add("<br/>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Efetuou cadastro no Portal Web, </center></b><br/>");
			corpoEmail.add("<strong>com o Login  : </strong>"+user.getLogin()+"<br/>");
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>"); 
			
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Para autorizar o acesso agora mesmo, clique ao lado. <a href="+Geral.getCaminhoURL()+"pages/public/autorizarAcessoURL.jsf?"+Criptografia.md5("idUser")+"="+Criptografia.criptografa(String.valueOf(user.getId()), 25)+">Autorizar acesso</a></center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			
			      
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			msgAdmin.setMensagens(corpoEmail);
			
			
			try {
				// Executa aqui o codigo perigoso, que dá exceção
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
	
	public List<User> userPorEmail(String email) {
		
		userDAO.beginTransaction();
		List<User> result = userDAO.findUserEmail(email);
		userDAO.closeTransaction();
		
		return result;
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
			
			corpoEmail.add("<img src="+imgHttp+" height='100' width='150'>");
			
			corpoEmail.add("</div>");  

			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<p>Prezado(a),"+usuario.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este é um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");  
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("Informamos que seu usuário: " + usuario.getLogin()
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
			corpoEmail.add("<b><center>**** FAVOR NÃO RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			
			msgAdmin.setMensagens(corpoEmail);

			EmailUtils.enviaEmailHtml(msgAdmin);

			
		}

	}
	
	public void enviarEmailRecuperarSenha(List<User> usuarios) {
		
		EmpresaDAO empresaDao = new EmpresaDAO();
		Empresa empresa = new Empresa();

		empresa = empresaDao.DadosTransportadora();
		
		Mensagem msgAdmin = new Mensagem();

		msgAdmin.setTitulo("Criação de nova senha");
		msgAdmin.setDestino(usuarios.get(0).getEmail());
		
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
		
		corpoEmail.add("            <img src="+imgHttp+" height='100' width='150'>");
		            
		corpoEmail.add("            </div>");

		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("            <p>Prezado(a),"+usuarios.get(0).getCliente().getNome()+"</p>");
		corpoEmail.add("            <p>Este é um email de <strong>"+empresa.getNome()+"</strong>.</p>");
		corpoEmail.add("            </div>");
		            
		            
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Você nos informou que esqueceu sua senha de acesso ao <a href="+Geral.getCaminhoURL()+">Portal"+"</a>");
		corpoEmail.add("            </div>");
		corpoEmail.add("	    <br/>");
		corpoEmail.add("	    <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Use o(s) link(s) a seguir nas próximas horas para criar uma nova senha:");	
		corpoEmail.add("            </div>");
		corpoEmail.add("	    <br/>");
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		
		
		for (User usuario : usuarios) {
			corpoEmail.add("Login = "+usuario.getLogin()+  ", <a href="+Geral.getCaminhoURL()+"pages/public/novaSenha.jsf?"+Criptografia.md5("idUser")+"="+Criptografia.criptografa(String.valueOf(usuario.getId()), 25)+">Trocar senha</a><br/>");	
		}
		
		
				
		corpoEmail.add("            </div>");
		                               
		            
		corpoEmail.add("	    <br/>");
		            
		corpoEmail.add("            <div colspan='1' height='30' align='left'>");
		corpoEmail.add("               Se não foi você que solicitou a criação de uma nova senha, não se preocupe, apenas ignore esta mensagem.");
		corpoEmail.add("            </div>");
		corpoEmail.add("            <br/>");
		corpoEmail.add("	    Obrigado.");	
		corpoEmail.add("            <br/>");
		corpoEmail.add("            <br/>");
		            
		            
		corpoEmail.add("            <div colspan='1' height='30' align='center'>");
		corpoEmail.add("            <b><center>**** FAVOR NÃO RETORNAR ESTE EMAIL ****</center></b>");
		corpoEmail.add("            </div>");

		corpoEmail.add("	</div>");                  
		corpoEmail.add("            </body>");
		corpoEmail.add("            </html>");
		
		msgAdmin.setMensagens(corpoEmail);

		EmailUtils.enviaEmailHtml(msgAdmin);
		
	}
	
	
	

}



