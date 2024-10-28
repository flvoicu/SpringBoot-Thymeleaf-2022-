package com.CRUD.integrador.modelo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author flavius
 */
public class UsuarioLoginDTO {
	
	@NotBlank(message = "{usuario.notblank}")
	@Email(regexp = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+", message = "{usuario.email}")
	String usuario;
	@NotBlank(message = "{clave.notblank}")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,12}$", message = "{clave.regexp}")
	String clave;

	
	public UsuarioLoginDTO() {
		
	}
	
	/**
	 * @param usuario
	 * @param clave
	 */
	public UsuarioLoginDTO(String usuario,
                           String clave) {
		
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
	

}
