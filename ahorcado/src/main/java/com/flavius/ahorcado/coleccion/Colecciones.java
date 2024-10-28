package com.flavius.ahorcado.coleccion;


import com.flavius.ahorcado.model.Letra;
import com.flavius.ahorcado.model.Palabra;
import com.flavius.ahorcado.model.UsuarioLoginDTO;

import java.util.*;

public class Colecciones {

    public static List<Palabra> listaPalabras = new ArrayList<>();
    public static List<Letra> listaLetras = new ArrayList<com.flavius.ahorcado.model.Letra>();
    public static List<UsuarioLoginDTO> listaUsuarios = new ArrayList<>();

    static {
        listaPalabras.add(new Palabra("albaricoque"));
        listaUsuarios.add(new UsuarioLoginDTO("root@gmail.com","Root1@"));
    }
}
