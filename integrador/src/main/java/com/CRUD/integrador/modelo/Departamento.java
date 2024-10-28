package com.CRUD.integrador.modelo;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * POJO Departamento
 * @author flavius*/
@Entity
@Table(name ="departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private long idDepartamento;
    @Column(name="depno")
    @NotNull(message ="{departamentoAlta.depno.notnull}")
    @NotBlank(message ="{departamentoAlta.depno.notblank}")
    @Pattern(regexp = "^[0-9]*$", message = "{departamentoAlta.depno.notnum}")
    private String depno;
    @Column(name="nombre")
    @NotNull(message ="{departamentoAlta.nombre.notnull}")
    @NotBlank(message ="{departamentoAlta.nombre.notblank}")
    private String nombre;
    @Column(name="localizacion")
    @NotNull(message ="{departamentoAlta.localizacion.notnull}")
    @NotBlank(message ="{departamentoAlta.localizacion.notblank}")
    private String localizacion;
    @OneToMany(mappedBy = "usuario")
    private List<Usuario> usuario;

    /**
     * Constructor vacio Departamento
     */
    public Departamento(){}

    /**
     * Constructor que se usara para obtener y establecer valores desde la vista y almacenarla/mostrarla en la BBDD
     *
     * @param depno         numero Departamento
     * @param nombre        nombre Departamento
     * @param localizacion  lugar establecido el Departamento
     */
    public Departamento(String depno,
                        String nombre,
                        String localizacion) {
        this.depno = depno;
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    public Departamento(String depno,
                        String nombre,
                        String localizacion,
                        List<Usuario> usuario) {
        this.depno = depno;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.usuario = usuario;
    }

    /**
     * GETTER para departamento por id
     * @return devuelve el numero de id de un Departamento
     */
    public long getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * SETTER para departamento por ID
     * @param id una ID para Departamento
     */
    public void setIdDepartamento(long id) {
        this.idDepartamento = id;
    }

    /**
     * GETTER para numero Departamento
     * @return devuelve un String con el numero de Departamento
     */
    public String getDepno() {
        return depno;
    }

    /**
     * SETTER para numero Departamento
     * @param depno un numero de Departamento para Departamento
     */
    public void setDepno(String depno) {
        this.depno = depno;
    }

    /**
     * GETTER para nombre Departamento
     * @return devuelve un String con el nombre de Departamento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * SETTER para nombre Departamento
     * @param nombre un nombre para Departamento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * GETTER para localizacion Departamento
     * @return devuelve un String con la localizacion de Departamento
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * SETTER para localizacion Departamento
     * @param localizacion un String para Departamento
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public List<Usuario> getUsuario() { return usuario; }

    public void setUsuario(List<Usuario> usuario) { this.usuario = usuario; }


}
