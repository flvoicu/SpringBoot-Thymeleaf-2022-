package com.flavius.ahorcado.controller;

import com.flavius.ahorcado.coleccion.Colecciones;
import com.flavius.ahorcado.funciones.Funciones;
import com.flavius.ahorcado.model.Letra;
import com.flavius.ahorcado.model.Palabra;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/ahorcado")
public class JuegoControlador {

    @GetMapping("/palabra")
    public ModelAndView devuelveFormularioPalabra(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        // comprueba si estas logueado, de no ser asi te redirige al login
        if(req.getSession().getAttribute("_id") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            // devuelve el formulario de palabra
            mav.addObject("Palabra", new Palabra());
            mav.setViewName("palabra");
        }
        return mav;
    }

    @PostMapping("/palabra")
    public ModelAndView recibirPalabra(
            @Valid
            @ModelAttribute("Palabra") Palabra palabra,
            BindingResult resultado){
        ModelAndView mav = new ModelAndView();

        // si hay errores devuelve la vista, si no te devuelve a jugar
        if(resultado.hasErrors()){
            mav.setViewName("palabra");
        }else {
            Funciones.addPalabra(palabra);
            mav.setViewName("redirect:volverJugar");
        }
        return mav;
    }

    @GetMapping("/juego")
    public ModelAndView devuelveAhorcado(
            HttpServletRequest req){
        ModelAndView mav = new ModelAndView();

        // comprueba si estas logueado, de no ser asi te redirige al login
        if(req.getSession().getAttribute("_id") == null){
            mav.setViewName("redirect:/acceso/login");
        }else {
            // recoge la informacion de la sesion y crea una palabra
            getInfo(mav, req, false);
            Palabra palabra;

            // si la palabra es nula escoge una aleatoria si no la guarda
            if (req.getSession().getAttribute("Palabra") == null) {
                palabra = Funciones.getPalabraRandom();
                req.getSession().setAttribute("Palabra", palabra);
            } else {
                palabra = (Palabra) req.getSession().getAttribute("Palabra");
            }
            // si los intenos son nulos los establece a 6, ya que es el número de imágines que tenemos
            if (req.getSession().getAttribute("Intentos") == null) {
                req.getSession().setAttribute("Intentos", 6);
            }
            // recogemos los intentos
            int intentos = (int) req.getSession().getAttribute("Intentos");

            // añadimos informacion que necesitamos para la vista
            mav.addObject("Letra", new Letra());
            mav.addObject("Palabra", palabra);
            mav.addObject("arr", palabra.getArr());
            mav.addObject("listaLetras", Funciones.listaLetras());
            mav.addObject("Intentos", Funciones.intentosString(intentos));
            mav.addObject("numIntentos", intentos);
            mav.addObject("Ganador", Funciones.checkGanar(palabra.getArr(), palabra));
            mav.setViewName("ahorcado");
        }
        return mav;
    }

    @PostMapping("/juego")
    public ModelAndView recibirLetra(
            @Valid
            @ModelAttribute("Letra") Letra letra,
            BindingResult resultado,
            HttpServletRequest req){
        ModelAndView mav = new ModelAndView();

        // info de la sesion
        getInfo(mav,req, true);

        //cogemos la letra y la pasamos a minúscula
        String let = letra.getLetra();
        letra.setLetra(let.toLowerCase(Locale.ROOT));

        // recogemos la palabra y los intentos
        Palabra palabra = (Palabra) req.getSession().getAttribute("Palabra");
        int intentos = (int) req.getSession().getAttribute("Intentos");

        // si el resustado tiene errores se devuelve la vista y le pasamos los parametros necesarios
        if(resultado.hasErrors()){
            mav.setViewName("ahorcado");
            mav.addObject("arr", palabra.getArr());
            mav.addObject("Palabra", palabra);
            mav.addObject("listaLetras", Funciones.listaLetras());
            mav.addObject("Intentos", Funciones.intentosString(intentos));
            mav.addObject("numIntentos", intentos);
        }else{
            // si la letra esta repetida devuelve la vista, añadimos un error y pasamos los parametros necesarios
            if(Funciones.checkRepetirLetra(letra)){
                mav.addObject("error","Ya has introducido esa letra");
                mav.addObject("arr", palabra.getArr());
                mav.addObject("Palabra", palabra);
                mav.addObject("listaLetras", Funciones.listaLetras());
                mav.addObject("Intentos", Funciones.intentosString(intentos));
                mav.addObject("numIntentos", intentos);
                mav.setViewName("ahorcado");
            }else{
                // añade la letra a la lista de letras y comprueba si esta en la palabra
                Funciones.addLetra(letra);
                if(!Funciones.checkLetra(letra, palabra)){
                    intentos = intentos - 1;
                    req.getSession().setAttribute("Intentos", intentos);
                }
                mav.setViewName("redirect:juego");
            }
        }
        req.getSession().setAttribute("Palabra", palabra);

        return mav;
    }

    @GetMapping("/volverJugar")
    public ModelAndView volverJugar(HttpServletRequest req){
        ModelAndView mav =new ModelAndView();

        // borra la palabra y los intentos de la sesion, vacia la lista de letras
        req.getSession().setAttribute("Palabra", null);
        req.getSession().setAttribute("Intentos", null);
        Colecciones.listaLetras.clear();

        mav.setViewName("redirect:juego");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest req){
        ModelAndView mav =new ModelAndView();

        // invalida la sesion y vacia la lista de letras
        req.getSession().invalidate();
        Colecciones.listaLetras.clear();

        mav.setViewName("redirect:/acceso/login");
        return mav;
    }

    public void getInfo(ModelAndView mav, HttpServletRequest req, boolean post){
        String ip = (String) req.getSession().getAttribute("ip");
        String equipo = (String) req.getSession().getAttribute("equipo");
        mav.addObject("ip",ip);
        mav.addObject("equipo", equipo);

        // si el metodo es post no se actualiza el contador
        if(post){
            String id = (String) req.getSession().getAttribute("_id");
            int contador = (int) req.getSession().getAttribute("_contador");
            mav.addObject("_id",id);
            mav.addObject("_contador",contador);
        }else {
            String id = (String) req.getSession().getAttribute("_id");
            int contador = (int) req.getSession().getAttribute("_contador") + 1;
            req.getSession().setAttribute("_contador", contador);
            mav.addObject("_id", id);
            mav.addObject("_contador", contador);
        }
    }
}
