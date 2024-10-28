package com.CRUD.integrador.controlador;

import com.CRUD.integrador.modelo.Pais;
import com.CRUD.integrador.servicio.PaisServicio;
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
@RequestMapping("/pais")
public class PaisController {

    @Autowired
    PaisServicio paisService; //para poder usar las operaciones crud implementadas

    /**Metodo GET que devolvera al usuario el formulario a cumplimentar
     *
     * @return  la vista altaPais con los objetos necesarios
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
            Pais pais = new Pais();

            mav.addObject("pais", pais);
            mav.addObject("listaPais", paisService.buscarTodos());
            mav.addObject("listaErrores", new HashMap<String, String>());

            mav.setViewName("altaPais");
        }
        return mav;
    }

    /**
     * Metodo POST que recibira el usuario una vez que este envie un formulario
     * en este metodo se realizara las comprobaciones de los campos, devolviendo la misma pagina
     * con los errores realizados o dirigiendole al listado  de Pais
     *
     * @param Pais  objeto que guardara la informacion introducida en el formulario por el usuario
     * @param resultado     objeto que guardara todos los errores que haya acumulado en Pais
     @return              la vista a mostrar para el usuario con todos los objetos necesarios para su correcta visualizacion
      * */
    @PostMapping("/alta")
    public ModelAndView recibirParametroAlta(
            @Valid @ModelAttribute("Pais")Pais Pais,
            BindingResult resultado,
            HttpServletRequest req ) throws Exception{

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            if (resultado.hasErrors()) {  // hay errores de lógica de negocio
                mav.setViewName("altaPais");
            }else{
                paisService.addPais(Pais);
                mav.setViewName("redirect:/pais/listado");
            }
        }
        return mav;
    }

    /**
     * Metodo que devolvera la vista con el Pais seleccionado por su ID
     *
     * @param id numero por el cual se tendra referencia al eliminar la entidad
     * @return
     * */
    @GetMapping("/modificar/{id}")
    public ModelAndView modPais(
            @PathVariable("id") long id,
            HttpServletResponse res,
            HttpServletRequest req ) throws Exception{

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            actualizarCookies(mav,res, req);
            infoConexion(mav, req);

            mav.addObject("pais", paisService.findPais(id));
            mav.addObject("listaPais", paisService.buscarTodos());
            mav.setViewName("altaPais");
        }
        return mav;
    }

    /**
     * Listará todos los Paiss creadas
     * @return  devolverá la vista listarPais
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
                mav.addObject("listaPais", paisService.buscarTodos());
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar listar todos los Paiss");
            }

            mav.setViewName("listarPais");
        }
        return mav;
    }

    /**
     * Metodo que se usara cuando el usuario quiera eliminar un Pais de la lista
     *
     * @param id numero por el cual se tendra referencia al eliminar la entidad
     * @return  la vista del listado con la informacion eliminada y actualizada en la base de datos
     */
    @GetMapping("/baja/{id}")
    public ModelAndView bajaPais(
            @PathVariable("id") long id,
            HttpServletRequest req ){

        ModelAndView mav = new ModelAndView();

        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            try {
                paisService.eliminarPais(id);
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar eliminar al Pais con ID " + id);
            }

            mav.setViewName("redirect:/pais/listar");
        }
        return mav;
    }

    /**
     * Eliminacion total del contenido Pais
     *
     * @return la vista con todas las entidades Paiss eliminadas, quedando una lista vacia en su lugar
     */

    @GetMapping("/bajaTodo")
    public ModelAndView bajaTodoPais(HttpServletRequest req){

        ModelAndView mav = new ModelAndView();
        if(req.getSession().getAttribute("usr") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            try {
                paisService.eliminarTodoPais();
            } catch (Exception e) {
                System.err.println("Ocurrió un error al intentar eliminar todos los Paises");
            }

            mav.setViewName("redirect:/pais/listar");
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