package br.com.allisson.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String md5(String senha) {
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte messageDigest[] = null;
		try {
			messageDigest = algorithm.digest(senha.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		return hexString.toString();

	}
	
	
	public static String criptografa(String s, int cifra) {
		int i, n = s.length();
		String saux = "";

		for (i = 0; i < n; i++) {
			saux = saux + (char) (s.charAt(i) + cifra);
		}

		return (saux);
	}
	
	
	public static String descriptografa(String s, int cifra) {
		int i, n = s.length();
		String saux = "";

		for (i = 0; i < n; i++) {
			saux = saux + (char) (s.charAt(i) - cifra);
		}

		return (saux);
	}

}