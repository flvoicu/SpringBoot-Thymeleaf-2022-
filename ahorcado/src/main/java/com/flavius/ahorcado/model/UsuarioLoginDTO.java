package com.flavius.ahorcado.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UsuarioLoginDTO {
	
	@NotNull(message = "El campo Usuario no puede ser nulo")
	@NotBlank(message = "El campo Usuario no puede estar en blanco")
	@Email(regexp = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+", message = "El Usuario no tiene el formato de un email")
	String usuario;
	@NotNull(message = "El campo Clave no puede ser nulo")
	@NotBlank(message = "El campo Clave no puede estar en blanco")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,12}$", message = "La Clave no tiene el formato adecuado")
	String clave;
	
	public UsuarioLoginDTO() {
		
	}
	
	public UsuarioLoginDTO(String usuario, String clave){
		super();
		this.usuario = usuario;
		this.clave = clave;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public String toString() {
		return "UsuarioLoginDTO{" +
				"usuario='" + usuario + '\'' +
				", clave='" + clave + '\'' +
				'}';
	}
}
