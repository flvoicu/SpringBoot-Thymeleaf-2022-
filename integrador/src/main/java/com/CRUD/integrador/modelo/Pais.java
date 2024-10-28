package com.CRUD.integrador.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author flavius
 */
@Entity
@Table(name = "Paises")
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long idPais;
	@Column(name = "siglas")
	@NotNull(message="{pais.siglas.notnull")
	@NotBlank(message = "{pais.siglas.notblank}")
	private String siglas;
	@Column(name = "nombre")
	@NotNull(message = "{pais.nombre.notnull}")
	@NotBlank(message = "{pais.nombre.notblank}")
	private String nombre;
	@Column(name = "poblacion")
	@NotNull(message = "{pais.poblacion.notnull}")
	@NotBlank(message = "{pais.poblacion.notblank}")
	@Pattern(regexp = "^[0-9]*$", message = "{pais.poblacion.notnum}")
	private String poblacion;
	@Column(name = "superficie")
	@NotNull(message = "{pais.superficie.notnull}")
	@NotBlank(message = "{pais.superficie.notnull}")
	@Pattern(regexp = "^[0-9]*$", message = "{pais.superficie.notnotnum}")
	private String superficie;
	@OneToMany(mappedBy = "usuario")
	private List<Usuario> usuario;
	@ManyToMany(targetEntity = Pais.class)
	private List<Pais> frontera;

	public Pais() {

	}

	/**
	 * @param siglas
	 * @param nombre
	 * @param poblacion
	 * @param superficie
	 * @param usuario
	 * @param frontera
	 */
	public Pais(String siglas,
				String nombre,
				String poblacion,
				String superficie,
				List<Usuario> usuario, List<Pais> frontera){

		this.siglas = siglas;
		this.nombre=nombre;
		this.poblacion = poblacion;
		this.superficie = superficie;
		this.usuario = usuario;
		this.frontera = frontera;
	}


	public long getIdPais() {
		return idPais;
	}

	public void setIdPais(long idUsuario) {
		this.idPais = idUsuario;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public List<Pais> getFrontera() { return frontera; }

	public void setFrontera(List<Pais> frontera) { this.frontera = frontera; }

	public List<Usuario> getUsuario() { return usuario; }

	public void setUsuario(List<Usuario> usuario) { this.usuario = usuario; }
}

