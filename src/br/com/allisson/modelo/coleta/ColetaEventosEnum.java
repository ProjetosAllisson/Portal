package br.com.allisson.modelo.coleta;

public enum ColetaEventosEnum {
	
	DIGITADO("Digitado"),
	EMAPROVACAO("Em Aprovação"),
	APROVADA("Aprovada"),
	ROTADECOLETA("Em Rota de Coleta");
	
	private final String value;
	
	ColetaEventosEnum(String value) {
		this.value = value;
	}
	
	public String getLabel(){
		return this.value;
	}
	
	public static ColetaEventosEnum fromValue(String value){
		if (value != null){
			for (ColetaEventosEnum ev : values()) {
				if (ev.value.equals(value)){
					return ev;
				}
			}
		}
		
		return getDefault();
	}
	
	public String toValue(){
		return value;
	}
	
	private static ColetaEventosEnum getDefault(){
		return DIGITADO;
	}

}
