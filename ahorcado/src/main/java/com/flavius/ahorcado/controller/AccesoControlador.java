package com.flavius.ahorcado.controller;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.flavius.ahorcado.funciones.Funciones;
import com.flavius.ahorcado.model.UsuarioLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/acceso")
public class AccesoControlador {
	
	@GetMapping("/login")
	public ModelAndView devuelveFormularioLogin(HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();

		// devuelve el formulario de login y añade información varia
		String ipEquipo = InetAddress.getLocalHost().getHostAddress();
		String ip = "Tu IP es: "+ ipEquipo;
		
		String nomEquipo = InetAddress.getLocalHost().getHostName();
		String equipo = "Tu equipo es: "+ nomEquipo;

		req.getSession().setAttribute("ip",ip);
		req.getSession().setAttribute("equipo",equipo);
		
		mav.addObject("ip", ip);
		mav.addObject("equipo", equipo);

		mav.addObject("UsuarioLoginDTO", new UsuarioLoginDTO());
		mav.setViewName("login");
		return mav;
	}
	
	@PostMapping("/login")
	public ModelAndView recibirCredencialesLogin(
			@Valid 
			@ModelAttribute ("UsuarioLoginDTO") UsuarioLoginDTO usuarioDTO,
			BindingResult resultado,
			HttpServletRequest req){
		
		ModelAndView mav = new ModelAndView();

		// recoge la informacion, por si hay errores que no colapse el panel de control
		String ip = (String) req.getSession().getAttribute("ip");
		String equipo = (String) req.getSession().getAttribute("equipo");
		mav.addObject("ip",ip);
		mav.addObject("equipo", equipo);

		// si hay errores devuelve a la vista
		if(resultado.hasErrors()) {
			mav.setViewName("login");
		}else {
			// si existe el usuario creamos la sesion y el contador de vistas, despues se redirige al juego, si no devolvemos un error y la vista
			if(Funciones.checkUsuario(usuarioDTO.getUsuario(), usuarioDTO.getClave())){
				mav.setViewName("redirect:/ahorcado/juego");
				req.getSession().setAttribute("_id", usuarioDTO.getUsuario());
				req.getSession().setAttribute("_contador", 0);
			}else{
				mav.addObject("error", "Usuario y/o clave inválidos");
				mav.setViewName("login");
			}
		}
		return mav;
	}
	
}
