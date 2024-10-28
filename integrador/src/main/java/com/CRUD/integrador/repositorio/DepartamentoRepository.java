package com.CRUD.integrador.repositorio;


import com.CRUD.integrador.modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que sirve para realizar operaciones con la tabla de Departamento de la base de datos
 * Incluye JPARepository
 *
 * @author Flavius
 */
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {

    Departamento findByNombre(String nombre);
}
