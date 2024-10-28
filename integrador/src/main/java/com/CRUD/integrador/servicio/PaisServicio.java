package com.CRUD.integrador.servicio;

import com.CRUD.integrador.modelo.Pais;
import com.CRUD.integrador.repositorio.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Clase que servira de Servicio para la aplicacion, incluye la implementacion de
 * la interface PaisService, Se implementaran sus metodos al controlador de Pais
 *
 * @author Flavius
 */
@Service
public class PaisServicio {

    @Autowired
    private PaisRepository paisRepository;

    public void creaPais() {
        List<Pais> lista = paisRepository.findAll();

        if(lista.isEmpty()) {
            Pais pais=new Pais("Root", "root","1","1", null, null);
            paisRepository.save(pais);
        }
    }

    public List<Pais> buscarTodos(){
        return paisRepository.findAll();
    }

    /**Metodo implementado que usa la operacion de insertar por ID de la tabla Pais
     * de la base de datos
     *
     * @param pais un objeto Pais para guardar en la base de datos*/
    public void addPais(Pais pais) throws Exception {

        try {
            paisRepository.save(pais);
        } catch (Exception e) {
            System.err.println(e.getCause());
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Metodo implementado que usa la operacion de actualizar por ID de la tabla
     * Pais de la base de datos
     *
     * @param id un numero ID para buscar la entidad especifica
     */
    public Pais findPais(long id) {
        Pais pais = new Pais();

        if(paisRepository.existsById(id)){
            pais = paisRepository.findById(id).get();
        }
        return pais;
    }

    /**
     * Metodo implementado que usa la operacion de eliminar por ID de la tabla Pais
     * de la base de datos
     *
     * @param               id un numero ID  a buscar para su eliminacion
     * @throws Exception    un error general caso de fallo de la operacion
     */
    public void eliminarPais(long id) throws Exception {
        Pais pais = paisRepository.findById(id).get();

        try {
            paisRepository.delete(pais);

        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getStackTrace());
            throw e;

        }
    }

    /**
     * Metodo implmentado que usa la operacion de eliminar todos los Paiss
     * de la base de datos
     *
     * @throws Exception un error general en caso de fallo
     */
    public void eliminarTodoPais() throws Exception {
        try {
            paisRepository.deleteAll();

        } catch (Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getStackTrace());
            throw e;
        }
    }

    public List<Pais> buscarTodosNombre(){
        return paisRepository.findAllByOrderByNombreAsc();
    }
}
