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

@WebFilter(servletNames = { "Faces Servlet" })
public class ControleDeAcesso implements Filter {


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// HttpServletRequest req = (HttpServletRequest) request;
		// HttpSession session = req.getSession();

		// System.out.println(req.getRequestURI());

		try {
			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpServletResponse httpRes = (HttpServletResponse) response;
			HttpSession session = httpReq.getSession(true);
			String url = httpReq.getRequestURL().toString();

			System.out.println(url);
			//chain.doFilter(request, response);

			if (session.getAttribute("usuarioAutenticado") == null
					&& precisaAutenticar(url)) {
				
				System.out.println(httpReq.getContextPath());
				
				httpRes.sendRedirect(httpReq.getContextPath() + "/pages/public/login.jsf");
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if (session.getAttribute("usuarioAutenticado") != null ||
		 * req.getRequestURI().endsWith("login.jsf") ||
		 * req.getRequestURI().endsWith("consultaPublicaCpf.jsf") ||
		 * req.getRequestURI().endsWith("consultaPublicaCnpj.jsf") ||
		 * req.getRequestURI().endsWith(".css") ||
		 * req.getRequestURI().endsWith(".js") ||
		 * req.getRequestURI().endsWith(".jpg") ||
		 * req.getRequestURI().endsWith(".gif") ) { chain.doFilter(request,
		 * response); } else {
		 * 
		 * HttpServletResponse res = (HttpServletResponse) response;
		 * 
		 * // Desativa o cache do browser res.setHeader("Cache-Control",
		 * "no-store"); res.setHeader("Pragma", "no-cache");
		 * res.setDateHeader("Expires", 0);
		 * 
		 * 
		 * res.sendRedirect("login.jsf"); }
		 */
	}

	private boolean precisaAutenticar(String url) {

		return !url.endsWith("javax.faces.resource/theme.css.jsf")
				&& !url.endsWith("http://primefaces.org/ui")
				&& !url.endsWith("javax.faces.resource/ilogin.png.jsf")
				&& !url.endsWith("javax.faces.resource/primefaces.css.jsf")
				&& !url.endsWith("javax.faces.resource/images/header.png.jsf")
				&& !url.endsWith("javax.faces.resource/master.css.jsf")
				&& !url.endsWith("javax.faces.resource/images/ui-icons_72a7cf_256x240.png.jsf")
				&& !url.endsWith("org.primefaces.context.DefaultRequestContext@1aab1bb")
				&& !url.endsWith("javax.faces.resource/layout/layout.css.jsf")
				&& !url.endsWith("javax.faces.resource/style.css.jsf")
				&& !url.endsWith("javax.faces.resource/theme.css.jsf")
				&& !url.contains("login.jsf") && !url.endsWith(".css")
				&& !url.endsWith(".js") && !url.endsWith(".jpg")
				&& !url.endsWith(".gif") && !url.endsWith(".js.jsf")
				&& !url.endsWith("consultaPublicaCpf.jsf")
				&& !url.endsWith("consultaPublicaCnpj.jsf")
				&& !url.endsWith("usuario.jsf")
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
