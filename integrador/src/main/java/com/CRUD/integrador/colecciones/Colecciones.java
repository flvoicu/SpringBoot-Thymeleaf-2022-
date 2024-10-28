package com.CRUD.integrador.colecciones;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Clase que se usará para almacenar las opciones de tipo SELECT, SELECT MULTIPLE,
 * CHECKBOX Y RADIO
 *
 * @author flavius
 */
public class Colecciones {


	public static SortedMap<String, String> listaPaises = new TreeMap<String, String>();
	public static SortedMap<String, String> listaGeneros = new TreeMap<String, String>();
	public static SortedMap<String, String> listaCampos= new TreeMap<String, String>();

	static {

		listaGeneros.put("H", "Hombre");
		listaGeneros.put("F", "Fluido");
		listaGeneros.put("M", "Mujer");
		listaGeneros.put("O", "Otro");

		listaPaises.put("AN", "Andorra");
		listaPaises.put("ES", "España");
		listaPaises.put("FR", "Francia");
		listaPaises.put("IT", "Italia");
		listaPaises.put("PT", "Portugal");

		listaCampos.put("N", "Nombre");
		listaCampos.put("A1", "Apellido1");
		listaCampos.put("A2", "Apellido2");
		listaCampos.put("F", "Fecha Nacimiento");
		listaCampos.put("G", "Género");
		listaCampos.put("P","País");

	}
}
