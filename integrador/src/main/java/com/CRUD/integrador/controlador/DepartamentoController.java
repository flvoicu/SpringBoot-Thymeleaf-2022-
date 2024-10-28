package com.CRUD.integrador.controlador;

import com.CRUD.integrador.modelo.Departamento;
import com.CRUD.integrador.servicio.DepartamentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Esta clase sirve como intermediario entre la base de datos utilizando Service y la vista
 * para el cliente con Thymeleaf, tambien servira para comprobar la logica de negocio,
 * comprobando si el cliente introdujo datos correctamente.
 *
 * @author Flavius
 * */
@RestController
@RequestMapping("/departamento")
public class DepartamentoController {

    @Autowired
    DepartamentoServicio departamentoService; //para poder usar las operaciones crud implementadas

    /**Metodo GET que devolvera al usuario el formulario a cumplimentar
     *
     * @return  la vista altaDepartamento con los objetos necesarios
     *          para su correcta visualizacion*/
    @GetMapping("/alta")
    public ModelAndView devuelveFormularioAlta(
            HttpServletResponse res,
            HttpServletRequest req ){

        ModelAndView mav = new ModelAndView();
        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            actualizarCookies(mav,res, req);
            try {
                infoConexion(mav, req);
            } catch (UnknownHostException e) {
                System.err.println("No se ha podido conseguir la informacion");
            }
            Departamento departamento = new Departamento();

            mav.addObject("departamento", departamento);
            mav.addObject("listaErrores", new HashMap<String, String>());

            mav.setViewName("altaDepartamento");
        }
        return mav;
    }

    /**
     * Metodo POST que recibira el usuario una vez que este envie un formulario
     * en este metodo se realizara las comprobaciones de los campos, devolviendo la misma pagina
     * con los errores realizados o dirigiendole al listado  de Departamento
     *
     * @param departamento  objeto que guardara la informacion introducida en el formulario por el usuario
     * @param resultado     objeto que guardara todos los errores que haya acumulado en Departamento
     @return              la vista a mostrar para el usuario con todos los objetos necesarios para su correcta visualizacion
      * */
    @PostMapping("/alta")
    public ModelAndView recibirParametroAlta(
            @Valid @ModelAttribute("departamento")Departamento departamento,
            BindingResult resultado,
            HttpServletRequest req ) throws Exception{

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            if (resultado.hasErrors()) {  // hay errores de lógica de negocio
                mav.setViewName("altaDepartamento");
            }else{
                departamentoService.addDepartamento(departamento);
                mav.setViewName("redirect:/departamento/listar");
            }
        }
        return mav;
    }

    /**
     * Metodo que devolvera la vista con el departamento seleccionado por su ID
     *
     * @param departamento  objeto que usara para la modificacion del Departamento
     * @return
     * */
    @GetMapping("/modificar/{id}")
    public ModelAndView modDepartamento(
            @PathVariable("id") long id,
            Departamento departamento,
            HttpServletResponse res,
            HttpServletRequest req ) throws Exception{

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            actualizarCookies(mav,res, req);
            infoConexion(mav, req);

            mav.addObject("departamento", departamentoService.findDepartamento(id));
            mav.setViewName("altaDepartamento");
        }
        return mav;
    }

    /**
     * Listará todos los departamentos creadas
     * @return  devolverá la vista listarDepartamento
     * */
    @GetMapping("/listado")
    public ModelAndView devuelveListado(
            HttpServletResponse res,
            HttpServletRequest req ){

        ModelAndView mav = new ModelAndView();
        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            actualizarCookies(mav,res, req);
            try {
                infoConexion(mav, req);
            } catch (UnknownHostException e) {
                System.err.println("No se ha podido conseguir la informacion");
            }
            try {
                mav.addObject("listaDepartamento", departamentoService.buscarTodos());
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar listar todos los departamentos");
            }

            mav.setViewName("listarDepartamento");
        }
        return mav;
    }

    /**
     * Metodo que se usara cuando el usuario quiera eliminar un Departamento de la lista
     *
     * @param dptoId numero por el cual se tendra referencia al eliminar la entidad
     * @return       la vista del listado con la informacion eliminada y actualizada en la base de datos
     */
    @GetMapping("/baja/{dptoId}")
    public ModelAndView bajaDepartamento(
            @PathVariable("dptoId") long dptoId,
            HttpServletRequest req ){

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            try {
                departamentoService.eliminarDepartamento(dptoId);
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar eliminar al departamento con ID " + dptoId);
            }

            mav.setViewName("redirect:/departamento/listar");
        }
        return mav;
    }

    /**
     * Eliminacion total del contenido Departamento
     *
     * @return la vista con todas las entidades Departamentos eliminadas, quedando una lista vacia en su lugar
     */

    @GetMapping("/bajaTodo")
    public ModelAndView bajaTodoDepartammento(HttpServletRequest req){

        ModelAndView mav = new ModelAndView();
        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            try {
                departamentoService.eliminarTodoDepartamento();
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar eliminar todos los departamentos");
            }

            mav.setViewName("redirect:/departamento/listar");
        }
        return mav;
    }
    public void actualizarCookies(ModelAndView mav, HttpServletResponse res, HttpServletRequest req){

        String contador = String.valueOf(Integer.parseInt(req.getSession().getAttribute("cont").toString())+1);
        req.getSession().setAttribute("cont", contador);

        Cookie cookie2 = new Cookie("_contador", contador);
        res.addCookie(cookie2);

        mav.addObject("_contador", contador);
    }

    public void infoConexion(ModelAndView mav, HttpServletRequest req) throws UnknownHostException {
        InetAddress lh = InetAddress.getLocalHost();
        mav.addObject("ip", lh.getHostAddress());
        mav.addObject("equipo", req.getHeader("User-Agent"));
    }

}