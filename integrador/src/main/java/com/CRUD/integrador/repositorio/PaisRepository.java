package com.CRUD.integrador.repositorio;


import com.CRUD.integrador.modelo.Departamento;
import com.CRUD.integrador.modelo.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface que sirve para realizar operaciones con la tabla de Pais de la base de datos
 * Incluye JPARepository
 *
 * @author Flavius
 */
@Repository
public interface PaisRepository extends JpaRepository<Pais,Long> {

    Departamento findByNombre(String nombre);

    List<Pais> findAllByOrderByNombreAsc();
}
