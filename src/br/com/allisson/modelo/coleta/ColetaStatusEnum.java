package br.com.allisson.modelo.coleta;

public enum ColetaStatusEnum {
	EMITIDO("EMITIDO"),
	CANCELADO("CANCELADO");

	private String descricao;
	
	ColetaStatusEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	
	
	
}
