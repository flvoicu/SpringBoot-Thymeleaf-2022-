package com.CRUD.integrador.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author flavius
 */
@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long idUsuario;
	@Column(name = "usuario")
	@NotNull(message = "{usuario.notnull}")
	@NotBlank(message = "{usuario.notblank}")
	@Email(regexp = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+", message = "{usuario.email}")
	private String usuario;
	@Column(name = "clave")
	@NotNull(message = "{clave.notnull}")
	@NotBlank(message = "{clave.notblank}")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,12}$", message = "{clave.regexp}")
	private String clave;
	@Transient
	@NotNull(message = "{clave.notnull}")
	@NotBlank(message = "{clave.notblank}")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,12}$", message = "{clave.regexp}")
	private String confirmClave;
	@Column(name = "apellido1")
	@NotNull(message = "{apellido1.notnull}")
	@NotBlank(message = "{apellido1.notblank}")
	private String apellido1;
	@Column(name = "apellido2")
	private String apellido2;
	@Column(name = "nombre")
	@NotNull(message = "{nombre.notnull}")
	@NotBlank(message = "{nombre.notblank}")
	private String nombre;
	@Column(name = "genero")
	@NotNull(message="{sigGenero.notnull}")
	private String sigGenero;
	@ManyToOne()
	@JoinColumn(name = "pais_id", nullable = false)
	private Pais pais;
	@Column(name = "fecha_Creacion")
	private String fechCreacion = LocalDate.now().toString();
	@ManyToOne()
	@JoinColumn(name = "deptn_id", nullable = false)
	private Departamento departamento;
	@OneToOne(targetEntity = Departamento.class)
	private Departamento jefeDepartamento;

	public Usuario() {
		
	}
	
	/**
	 * @param usuario
	 * @param clave
	 * @param confirmClave
	 * @param apellido1
	 * @param apellido2
	 * @param nombre
	 * @param sigGenero
	 * @param pais
	 * @param fechCreacion
	 */
	public Usuario(String usuario,
				   String clave,
				   String confirmClave,
				   String apellido1,
				   String apellido2,
				   String nombre,
				   String sigGenero,
				   Pais pais,
				   String fechCreacion,
				   Departamento departamento,
				   Departamento jefeDepartamento){

		this.usuario=usuario;
		this.clave=clave;
		this.confirmClave=confirmClave;
		this.apellido1=apellido1;
		this.apellido2=apellido2;
		this.nombre=nombre;
		this.sigGenero=sigGenero;
		this.pais = pais;
		this.fechCreacion=fechCreacion;
		this.departamento=departamento;
		this.jefeDepartamento=jefeDepartamento;
	}

	/**
	 * @param usuario
	 * @param clave
	 */
	public Usuario(String usuario,
                   String clave){
		
		this.usuario=usuario;
		this.clave=clave;
	}

	/**
	 * @param apellido1
	 * @param apellido2
	 * @param nombre
	 * @param sigGenero
	 */
	public Usuario(String apellido1,
                   String apellido2,
                   String nombre,
                   String sigGenero){
		
		this.apellido1=apellido1;
		this.apellido2=apellido2;
		this.nombre=nombre;
		this.sigGenero=sigGenero;
		
	}

	public long getIdUsuario() { return idUsuario; }

	public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }

	public String getUsuario() { return usuario; }

	public void setUsuario(String usuario) { this.usuario = usuario; }

	public String getClave() { return clave; }

	public void setClave(String clave) { this.clave = clave; }
	public String getConfirmClave() { return confirmClave; }

	public void setConfirmClave(String confirmClave) { this.confirmClave = confirmClave; }

	public String getApellido1() { return apellido1; }

	public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

	public String getApellido2() { return apellido2; }

	public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

	public String getNombre() { return nombre; }

	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getSigGenero() { return sigGenero; }

	public void setSigGenero(String sigGenero) { this.sigGenero = sigGenero; }

	public String getFechCreacion() { return fechCreacion; }

	public void setFechCreacion(String fechCreacion) { this.fechCreacion = fechCreacion; }

	public Departamento getDepartamento() { return departamento; }

	public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

	public Departamento getJefeDepartamento() { return jefeDepartamento; }

	public void setJefeDepartamento(Departamento departamento) { this.jefeDepartamento = departamento; }

	public Pais getPais() { return pais; }

	public void setPais(Pais pais) { this.pais = pais; }
}

