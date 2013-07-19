package br.com.allisson.Listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;



public class FaseListener implements PhaseListener {

   private static final long serialVersionUID = 1L;
   

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		 
		boolean isLoginPage = (currentPage.lastIndexOf("login.jsf") > -1);
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		Object currentUser = session.getAttribute("usuarioAutenticado");
		 
		if (!isLoginPage && currentUser == null) {
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		nh.handleNavigation(facesContext, null, "login");
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
