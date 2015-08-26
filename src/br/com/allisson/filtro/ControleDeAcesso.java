package br.com.allisson.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.allisson.util.Geral;

@WebFilter(servletNames = { "Faces Servlet" })
public class ControleDeAcesso implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		
		try {
			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpSession session = httpReq.getSession(true);

			String url = httpReq.getRequestURL().toString();

			String uri = httpReq.getRequestURI().toString();

			if (Geral.getCaminhoURL() == null) {

				String url_principal = "";

				int index = url.indexOf(uri);
				int iBarras = 0;
				if (index > 0) {
					for (int i = 0; i < index; i++) {
						url_principal += url.charAt(i);
					}

					for (int i = 0; i < uri.length(); i++) {

						String barra = "";

						barra += uri.charAt(i);

						if (barra.equals("/")) {
							iBarras += 1;
						}

						url_principal += uri.charAt(i);

						if (iBarras == 2) {
							break;
						}

					}
				}

				Geral.setCaminhoURL(url_principal);
			}

			System.out.println(Geral.getCaminhoURL());

			if (session.getAttribute("usuarioAutenticado") == null
					&& precisaAutenticar(url)) {

				String contextPath = ((HttpServletRequest) request)
						.getContextPath();
				((HttpServletResponse) response).sendRedirect(contextPath
						+ "/pages/public/login.jsf");

			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean precisaAutenticar(String url) {

		
		return !url.endsWith("javax.faces.resource/theme.css.jsf")
				&& !url.endsWith("http://primefaces.org/ui")

				&& !url.endsWith("http://superfish.com/ws/sf_main.jsp")

				&& !url.endsWith("javax.faces.resource/ilogin.png.jsf")
				&& !url.contains("javax.faces.resource/primefaces.css.jsf")

				&& !url.contains("primefaces.css.jsf")
				&& !url.contains("primefaces.js")
				&& !url.contains("jquery-plugins.js")
				&& !url.contains("watermark.js")
				&& !url.contains("watermark.css")
				&& !url.contains("password-meter")

				&& !url.endsWith("javax.faces.resource/images/header.png.jsf")
				&& !url.endsWith("javax.faces.resource/master.css.jsf")
				&& !url.endsWith("javax.faces.resource/images/ui-icons_72a7cf_256x240.png.jsf")
				&& !url.endsWith("org.primefaces.context.DefaultRequestContext@1aab1bb")
				&& !url.endsWith("javax.faces.resource/layout/layout.css.jsf")
				&& !url.endsWith("javax.faces.resource/style.css.jsf")
				&& !url.endsWith("javax.faces.resource/theme.css.jsf")
				&& !url.contains("login.jsf")
				&& !url.endsWith(".css")
				&& !url.contains("loginFrame.jsf")
				&& !url.contains("loginSemAcesso.jsf")
				&& !url.endsWith(".js")
				&& !url.endsWith(".jpg")
				&& !url.endsWith(".gif")
				&& !url.endsWith(".js.jsf")
				&& !url.endsWith("consultaPublicaCpf.jsf")
				&& !url.endsWith("consultaPublicaCnpj.jsf")
				&& !url.endsWith("usuario.jsf")
				&& !url.endsWith("usuario_sucesso.jsf")
				&& !url.endsWith("autorizarAcessoURL.jsf")
				&& !url.endsWith("esqueceuSenha.jsf")

				&& !url.endsWith("javax.faces.resource/layout/layout.css.jsf")
				&& !url.endsWith("javax.faces.resource/watermark/watermark.css.jsf")
				&& !url.endsWith("javax.faces.resource/normalize.css.jsf")
				&& !url.contains("jquery.js.jsf")

				&& !url.endsWith("javax.faces.resource/resources/imagens/bg.gif");

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
