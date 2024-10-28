package com.CRUD.integrador.repositorio;

import com.CRUD.integrador.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interface que sirve para realizar operaciones con la tabla de Usuario de la base de datos
 * Incluye JPARepository
 *
 * @author Flavius
 */
@Repository
public interface UsuarioRepositorio
        extends JpaRepository<Usuario, Long> {

    /**
     * Metodo de interface que usara las sentencias de BBDD para listar todos
     * los Usuarios por el nombre
     *
     * @param nombre un nombre a buscar
     * @return       una List de tipo Usuario
     */
    List<Usuario> findAllByNombre(String nombre);

    /**
     * Metodo de interface que usara las sentencias de BBDD para listar todos
     * los Usuarios por el primer apellido
     *
     * @param apellido1  el primer apellido
     * @return           una List de tipo Usuario
     */
    List<Usuario> findAllByApellido1(String apellido1);

    /**
     * Metodo de interface que usara las sentencias de BBDD para listar todos los
     * Usuario por el segundo apellido
     *
     * @param apellido2  el segundo apellido
     * @return           una List de tipo Usuario
     */
    List<Usuario> findAllByApellido2(String apellido2);

    /**
     * Metodo de interface que usara las sentencias de BBDD para listar todos
     * los Usuarios por el Genero
     *
     * @param sigGenero  la incial del genero a buscar
     * @return           una List de tipo Usuario
     */
    List<Usuario> findAllBySigGenero(String sigGenero);

    /**
     *
     * Metodo de interface que usara las sentencias de BBDD para listar todos
     * los Usuarios por su usuario
     *
     * @param usuario  usuario a buscar
     * @return         una List de tipo Usuario
     */
    List<Usuario> findByUsuario(String usuario);



}
