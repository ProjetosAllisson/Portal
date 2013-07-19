package br.com.allisson.modelo;



public class Empresa {

	private String nome;
	private String endereco;
	private byte[] logo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Empresa) {
			Empresa empresa = (Empresa) obj;
			return empresa.getNome() == nome;
		}

		return false;
	}

	
	
	
}
