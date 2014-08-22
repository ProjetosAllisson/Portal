package br.com.allisson.util;

import java.io.Serializable;

public class Geral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String CaminhoURL;
	
	public static String LimpaString(String sString, String sSujeiras){
		
		String result = null;
		
		result = sString.replaceAll(sSujeiras, "");
		
		return result;
	}

	public static String getCaminhoURL() {
		return CaminhoURL;
	}

	public static void setCaminhoURL(String caminhoURL) {
		CaminhoURL = caminhoURL;
	}
	
	

}
