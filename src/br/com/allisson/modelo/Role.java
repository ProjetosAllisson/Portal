package br.com.allisson.modelo;

public enum Role {
	ADMIN("Administrador"), 
	USER("Usuário");
	
	private final String value;
	
	Role(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public static Role fromValue(String value){
		if(value != null){
			for (Role r  : values()) {
				if (r.value.equals(value)){
					return r;
				}
			}
		}
		
		return getDefault();
	}
	
	public String toValue(){
		return value;
	}
	
	private static Role getDefault(){
		return USER;
	}

}
