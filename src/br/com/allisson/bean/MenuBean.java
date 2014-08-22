package br.com.allisson.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name="menuBean")
@RequestScoped
public class MenuBean {
	
	private MenuModel menuModel;
	
	@PostConstruct
	public void init(){
		menuModel = new DefaultMenuModel();
		
		DefaultSubMenu opp = new DefaultSubMenu("Menu");
		DefaultMenuItem rastreamento = new DefaultMenuItem("Rastreamento");
		DefaultMenuItem financeiro   = new DefaultMenuItem("Demonstrativo Financeiro");
		
		rastreamento.setUrl("/pages/protected/defaultUser/documentos.jsf");
		rastreamento.setImmediate(true);
		rastreamento.setIcon("ui-icon-search");
		
		opp.addElement(rastreamento);
		opp.addElement(financeiro);
		
		DefaultSubMenu admin = new DefaultSubMenu("Admininstrador");
		DefaultMenuItem user = new DefaultMenuItem("Todos os Usuários");
		DefaultMenuItem aut  = new DefaultMenuItem("Autorizar Usuários");
		DefaultMenuItem conta = new DefaultMenuItem("Conta de Email");
		admin.addElement(user);
		admin.addElement(aut);
		admin.addElement(conta);
		
		DefaultSubMenu close = new DefaultSubMenu();
		DefaultMenuItem sair = new DefaultMenuItem("Sair");
		close.addElement(sair);
		
		menuModel.addElement(opp);
		menuModel.addElement(admin);
		menuModel.addElement(close);
		
	}
	

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	

}
