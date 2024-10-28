package com.CRUD.integrador.controlador;

import com.CRUD.integrador.servicio.DepartamentoServicio;
import com.CRUD.integrador.servicio.PaisServicio;
import com.CRUD.integrador.servicio.UsuarioServicio;
import com.CRUD.integrador.modelo.UsuarioLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Esta clase sirve como intermediario entre la base de datos utilizando Service y la vista
 * para el cliente con Thymeleaf, tambien servira para comprobar la logica de negocio,
 * comprobando si el cliente introdujo datos correctamente.
 *
 * @author Flavius
 */
@Controller
@RequestMapping("acceso")
public class AccesoController {

	@Autowired
	private UsuarioServicio usuarioServicio;
	@Autowired
	private DepartamentoServicio departamentoService;
	@Autowired
	private PaisServicio paisServicio;

		/**
	 * @param req
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("login")
	public ModelAndView devuelveFormularioLogin(HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();

		infoConexion(mav,req);
		
		UsuarioLoginDTO UsuarioLoginDTO = new UsuarioLoginDTO();
		departamentoService.creaDepartamento();
		paisServicio.creaPais();
		usuarioServicio.creaUsuario(departamentoService.findDepartamento(1), paisServicio.findPais(1));
		
		mav.addObject("UsuarioLoginDTO",UsuarioLoginDTO);
		
		mav.setViewName("login");
		return mav;
	}
	
    /**
     * @param req
     * @return ModelAndView
     */
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mAV = new ModelAndView();

        req.getSession().invalidate();
        Cookie cookie1 = new Cookie("_id", "");
		res.addCookie(cookie1);
		Cookie cookie2 = new Cookie("_contador", "0");
		res.addCookie(cookie2);

        mAV.setViewName("redirect:/acceso/login");

        return mAV;
    }
	
	/**
	 * @param UsuarioLoginDTO
	 * @param resultado
	 * @param req
	 * @param res
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PostMapping("login")
	public ModelAndView recibeCredencialesLogin(
			@Valid
			@ModelAttribute ("UsuarioLoginDTO") UsuarioLoginDTO UsuarioLoginDTO,
			BindingResult resultado,
			HttpServletRequest req,
			HttpServletResponse res
			) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		infoConexion(mav,req);
		mav.addObject("UsuarioLoginDTO",UsuarioLoginDTO);
		
		if(resultado.hasErrors()) {
			mav.setViewName("login");
		}else {
			if (usuarioServicio.checkUsuario(UsuarioLoginDTO.getUsuario(), UsuarioLoginDTO.getClave())) {
				comprobarCookies(mav, res, req , UsuarioLoginDTO);
				mav.setViewName("redirect:/usuario/listado");
			} else {
				mav.addObject("error", "Usuario y/o contraseña no son válidos");
				mav.setViewName("login");
			}

		}
		
		return mav;
	}

	public void comprobarCookies(ModelAndView mav, HttpServletResponse res, HttpServletRequest req, UsuarioLoginDTO Usuario){

		String id = Usuario.getUsuario();
		String contador =  "0";

		req.getSession().setAttribute("usr",id);
		req.getSession().setAttribute("cont",contador);

		Cookie cookie1 = new Cookie("_id", id);
		res.addCookie(cookie1);
		Cookie cookie2 = new Cookie("_contador", contador);
		res.addCookie(cookie2);

		mav.addObject("_id", id);
		mav.addObject("_contador", contador);
	}

	public void infoConexion(ModelAndView mav, HttpServletRequest req) throws UnknownHostException {
		InetAddress lh = InetAddress.getLocalHost();
		mav.addObject("ip", lh.getHostAddress());
		mav.addObject("equipo", req.getHeader("User-Agent"));
	}
	
}
