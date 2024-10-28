package com.CRUD.integrador.servicio;

import com.CRUD.integrador.modelo.Departamento;
import com.CRUD.integrador.modelo.Pais;
import com.CRUD.integrador.modelo.Usuario;
import com.CRUD.integrador.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class UsuarioServicio {

	private Usuario usu;
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	/**
	 *  Crea un nuevo usuario por defecto (root)
	 */
	public void creaUsuario(Departamento departamento, Pais pais) {
		List<Usuario> lista = usuarioRepositorio.findAll();
		
		if(lista.isEmpty()) {
			usu=new Usuario("root@gmail.com",
							"Root1@",
							"Root1@" ,
							"root",
							"root",
							"root",
							"root",
							pais,
							"root",
							departamento,
							departamento
							);

			usuarioRepositorio.save(usu);
		}
	}

	/**
	 * @param usu
	 * Añade un usuario 
	 */
	public void addUsuario(Usuario usu) {

		usuarioRepositorio.save(usu);
	}
	/**
	 * @param usuario
	 * @param clave
	 * @return boolean 
	 * comprueba si existe un usuario 
	 */
	public boolean checkUsuario(String usuario, String clave) {
		boolean comprobacion = false;

		List<Usuario> lista = new ArrayList<>();

		try{
			lista = usuarioRepositorio.findByUsuario(usuario);
		}catch (Exception e){

		}

		for (int i=0; i < lista.size(); i++) {
			usu = lista.get(i);

			if(usu.getUsuario().equals(usuario) && usu.getClave().equals(clave)) {
				comprobacion = true;
			}
		}
		return comprobacion;
	}

	public List<Usuario> getUsuarios(){
		return usuarioRepositorio.findAll();
	}
	
	/**
	 * @param id
	 * @return Usuario usu
	 * devuelve los parametros de un usuario para poder editarlo
	 */
	public Usuario findUsuario(long id) {
		Usuario usu1 = new Usuario();
		
		if(usuarioRepositorio.existsById(id)){
			usu1  = usuarioRepositorio.findById(id).get();
		}
		return usu1;
	}

	public boolean removeUsuario(long id) {

		if(usuarioRepositorio.existsById(id)){
			usuarioRepositorio.deleteById(id);
			return true;
		}else{
			return false;
		}
	}

	public List<Usuario> buscarPorCampo(String campo, String valor){
		List<Usuario> usuarios = new ArrayList<>();
		switch (campo){
			case "N":
				usuarios = usuarioRepositorio.findAllByNombre(valor);
				break;
			case "A1":
				usuarios = usuarioRepositorio.findAllByApellido1(valor);
				break;
			case "A2":
				usuarios = usuarioRepositorio.findAllByApellido2(valor);
				break;
			case "G":
				usuarios = usuarioRepositorio.findAllBySigGenero(valor);
				break;
		}

		return usuarios;
	}
}