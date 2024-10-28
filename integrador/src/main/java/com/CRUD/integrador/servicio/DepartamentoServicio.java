package com.CRUD.integrador.servicio;

import com.CRUD.integrador.modelo.Departamento;

import  com.CRUD.integrador.repositorio.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Clase que servira de Servicio para la aplicacion, incluye la implementacion de
 * la interface DepartamentoService, Se implementaran sus metodos al controlador de Departamento
 *
 * @author Flavius
 */
@Service
public class DepartamentoServicio {


    @Autowired
    private DepartamentoRepository departamentoRepository;

    public void creaDepartamento() {
        List<Departamento> lista = departamentoRepository.findAll();

        if(lista.isEmpty()) {
            Departamento depn=new Departamento("1","root", "root");

            departamentoRepository.save(depn);
        }
    }

    public List<Departamento> buscarTodos(){
        return departamentoRepository.findAll();
    }

    /**Metodo implementado que usa la operacion de insertar por ID de la tabla Departamento
     * de la base de datos
     *
     * @param departamento un objeto Departamento para guardar en la base de datos*/
    public void addDepartamento(Departamento departamento) throws Exception {

        try {
            departamentoRepository.save(departamento);
        } catch (Exception e) {
            System.err.println(e.getCause());
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Metodo implementado que usa la operacion de actualizar por ID de la tabla
     * Departamento de la base de datos
     *
     * @param id            un numero ID para buscar la entidad especifica
     */
    public Departamento findDepartamento(long id) {
        Departamento dp1 = new Departamento();

        if(departamentoRepository.existsById(id)){
            dp1 = departamentoRepository.findById(id).get();
        }
        return dp1;
    }

    /**
     * Metodo implementado que usa la operacion de eliminar por ID de la tabla Departamento
     * de la base de datos
     *
     * @param               id un numero ID  a buscar para su eliminacion
     * @throws Exception    un error general caso de fallo de la operacion
     */
    public void eliminarDepartamento(long id) throws Exception {
        Departamento departamento = departamentoRepository.findById(id).get();

        try {
            departamentoRepository.delete(departamento);

        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getStackTrace());
            throw e;

        }
    }

    /**
     * Metodo implmentado que usa la operacion de eliminar todos los Departamentos
     * de la base de datos
     *
     * @throws Exception un error general en caso de fallo
     */
    public void eliminarTodoDepartamento() throws Exception {
        try {
            departamentoRepository.deleteAll();

        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getStackTrace());
            throw e;
        }
    }
}
