package com.CRUD.integrador.controlador;

import com.CRUD.integrador.colecciones.Colecciones;
import com.CRUD.integrador.modelo.Consulta;
import com.CRUD.integrador.modelo.Usuario;
import com.CRUD.integrador.servicio.DepartamentoServicio;
import com.CRUD.integrador.servicio.PaisServicio;
import com.CRUD.integrador.servicio.UsuarioServicio;
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
import java.util.List;

/**
 * @author flavius
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

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
	@GetMapping("/alta")
	public ModelAndView devuelveFormularioAlta(HttpServletRequest req) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		Usuario Usuario = new Usuario();

		infoConexion(mav, req);
		parametrosAlta(mav, Usuario);

		mav.setViewName("altaUsuario");

		return mav;
	}

	/**
	 * @param Usuario
	 * @param resultado
	 * @param req
	 * @param res
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PostMapping("/alta")
	public ModelAndView recibirParametroAlta(
			@Valid
			@ModelAttribute ("Usuario") Usuario Usuario,
			BindingResult resultado,
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		infoConexion(mav, req);

		if(!Usuario.getClave().equals(Usuario.getConfirmClave())){
			mav.addObject("errorClave", "Las contraseñas no coinciden");
			parametrosAlta(mav, Usuario);
			mav.setViewName("altaUsuario");
		}else{
			if(resultado.hasErrors()) {
				parametrosAlta(mav, Usuario);
				mav.setViewName("altaUsuario");
			}else{
				usuarioServicio.addUsuario(Usuario);
				crearCookies(mav, res, req, Usuario, true);
				mav.setViewName("redirect:/usuario/listado");
			}
		}
		return mav;
	}
	
	/**
	 * @param req
	 * @param res
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("/listado")
	public ModelAndView devuelveListado(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		if(req.getSession().getAttribute("usr") == null){
			mav.setViewName("redirect:/acceso/login");
		}else{
			Usuario Usuario = new Usuario();

			Consulta Consulta = new Consulta();
			mav.addObject("Consulta", Consulta);
			mav.addObject("listaCampos", Colecciones.listaCampos);

			infoConexion(mav, req);
			crearCookies(mav, res, req, Usuario, false);
			List <Usuario> usuarios = usuarioServicio.getUsuarios();
			mav.addObject("lista",usuarios);

			mav.setViewName("listadoUsuarios");
		}

		return mav;
	}

	@PostMapping("/parametrizado")
	public ModelAndView devuelveListadoParametrizado(
			@ModelAttribute("Consulta") Consulta Consulta,
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		if(req.getSession().getAttribute("usr") == null){
			mav.setViewName("redirect:/acceso/login");
		}else {
			Usuario Usuario = new Usuario();
			List<Usuario> usuarios = usuarioServicio.buscarPorCampo(Consulta.getCampo(), Consulta.getValor());

			infoConexion(mav, req);
			crearCookies(mav, res, req, Usuario, false);

			mav.addObject("listaCampos", Colecciones.listaCampos);
			mav.addObject("lista", usuarios);
			mav.setViewName("redirect:/usuario/listado");
		}

		return mav;
	}
	
	/**
	 * @param idUsuario
	 * @return ModelAndView
	 */
	@GetMapping("/modificacion/{idUsuario}")
    public ModelAndView modUsuario(
			@PathVariable("idUsuario") long idUsuario,
			HttpServletRequest req ) throws Exception {

        ModelAndView mav = new ModelAndView();

		if(req.getSession().getAttribute("usr") == null){
			mav.setViewName("redirect:/acceso/login");
		}else {
			infoConexion(mav, req);

			parametrosAlta(mav, usuarioServicio.findUsuario(idUsuario));

			mav.setViewName("altaUsuario");
		}
        return mav;
	}
	
	/**
	 * @param idUsuario
	 * @return ModelAndView
	 */
	@GetMapping("/baja/{idUsuario}")
    public ModelAndView bajaUsuario(
			HttpServletRequest req,
			@PathVariable("idUsuario") int idUsuario) 
			throws Exception {

        ModelAndView mav = new ModelAndView();

		if(req.getSession().getAttribute("usr") == null){
			mav.setViewName("redirect:/acceso/login");
		}else{
			mav.setViewName("redirect:/usuario/listado");
			if (idUsuario == 1)
				return mav;
			if (req.getSession().getAttribute("usr") == usuarioServicio.findUsuario(idUsuario).getUsuario())
				mav.setViewName("redirect:/usuario/login");
			usuarioServicio.removeUsuario(idUsuario);
		}
        return mav;
	}

	public void parametrosAlta(ModelAndView mav, Usuario Usuario){
		mav.addObject("Usuario",Usuario);
		mav.addObject("listaDepartamento", departamentoService.buscarTodos());
		mav.addObject("listaGeneros",Colecciones.listaGeneros);
		mav.addObject("listaPaises", paisServicio.buscarTodos());
	}

	public void crearCookies(ModelAndView mav, HttpServletResponse res, HttpServletRequest req, Usuario Usuario, boolean crear){
		String id = "";
		String contador = "";

		if(crear){
			req.getSession().setAttribute("usr",Usuario.getIdUsuario());
			req.getSession().setAttribute("cont","0");
		}else{
			id = req.getSession().getAttribute("usr").toString();
			contador = String.valueOf(Integer.parseInt(req.getSession().getAttribute("cont").toString())+1);
			req.getSession().setAttribute("cont", contador);
		}

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